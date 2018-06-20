package in.ashwanik.dshc.messaging;

import in.ashwanik.dshc.SiteHitCounterApplication;
import in.ashwanik.dshc.common.Constants;
import in.ashwanik.dshc.common.JsonUtils;
import net.openhft.chronicle.queue.ExcerptAppender;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueue;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueueBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Ashwani on 20/06/18.
 */
@Service
public class Producer {
    private SingleChronicleQueue queue = SingleChronicleQueueBuilder.binary(Constants.PATH).build();

    public void produce(Object data) {
        ExcerptAppender appender = queue.acquireAppender();
        appender.writeText(JsonUtils
                .toJson(Message
                        .builder()
                        .data(JsonUtils
                                .toJson(data))
                        .id(UUID.randomUUID().toString())
                        .timestamp(System.currentTimeMillis())
                        .producerId(SiteHitCounterApplication.ID)
                        .build()));
    }
}
