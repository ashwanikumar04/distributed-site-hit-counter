package in.ashwanik.dshc.service;

import in.ashwanik.dshc.messaging.Producer;
import in.ashwanik.dshc.model.GCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ashwani Kumar on 20/06/18.
 */
@Service
public class CounterService {
    @Autowired
    private Producer producer;
    @Autowired
    private GCounter counter;

    public long getCount() {
        return counter.get();
    }

    public void increment() {
        counter.increment();
        producer.produce(counter);
    }

    public void merge(GCounter gCounter) {
        counter.merge(gCounter);
    }
}
