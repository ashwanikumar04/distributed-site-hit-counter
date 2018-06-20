package in.ashwanik.dshc;

import in.ashwanik.dshc.common.Constants;
import in.ashwanik.dshc.messaging.Consumer;
import in.ashwanik.dshc.model.GCounter;
import net.openhft.chronicle.queue.DumpQueueMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.FileNotFoundException;
import java.util.UUID;

@SpringBootApplication
public class SiteHitCounterApplication {

    @Autowired
    private Consumer consumer;

    public static String ID;
    public static long SERVER_TIMESTAMP;

    public static void main(String[] args) throws FileNotFoundException {
        ID = UUID.randomUUID().toString();
        SERVER_TIMESTAMP = System.currentTimeMillis();
        SpringApplication.run(SiteHitCounterApplication.class, args);
        DumpQueueMain.dump(Constants.PATH);
    }

    @Bean
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setThreadNamePrefix("default_task_executor_thread");
        executor.initialize();
        return executor;
    }

    @Bean
    public GCounter counter() {
        return new GCounter(ID);
    }
}
