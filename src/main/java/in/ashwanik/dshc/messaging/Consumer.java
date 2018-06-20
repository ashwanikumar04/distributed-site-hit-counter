package in.ashwanik.dshc.messaging;

import in.ashwanik.dshc.SiteHitCounterApplication;
import in.ashwanik.dshc.common.Constants;
import in.ashwanik.dshc.common.JsonUtils;
import in.ashwanik.dshc.model.GCounter;
import in.ashwanik.dshc.service.CounterService;
import lombok.extern.slf4j.Slf4j;
import net.openhft.chronicle.queue.ExcerptTailer;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueue;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueueBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by Ashwani on 20/06/18.
 */
@Service
@Slf4j
public class Consumer {
    private SingleChronicleQueue queue;
    private ExcerptTailer tailer;
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private CounterService counterService;

    @PostConstruct
    private void init() {
        queue = SingleChronicleQueueBuilder.binary(Constants.PATH).indexSpacing(1).build();
        tailer = queue.createTailer();
        QueueProcessor processor = new QueueProcessor();
        processor.start();
    }

    class QueueProcessor extends Thread {

        public void run() {
            while (!interrupted()) {
                try {
                    String msg = tailer.readText();
                    if (msg != null) {
                        Message message = JsonUtils.fromJsonToObject(msg, Message.class);
                        if (message != null && message.getTimestamp() > SiteHitCounterApplication.SERVER_TIMESTAMP) {
                            log.info(JsonUtils.toJson(JsonUtils.fromJsonToObject(message.getData(), GCounter.class)));
                            counterService.merge(JsonUtils.fromJsonToObject(message.getData(), GCounter.class));
                        }
                    } else {
                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    log.warn(Constants.APPLICATION, e);
                    this.interrupt();
                    break;
                }
            }

            ThreadPoolTaskExecutor tp = (ThreadPoolTaskExecutor) taskExecutor;
            tp.shutdown();
        }
    }

}
