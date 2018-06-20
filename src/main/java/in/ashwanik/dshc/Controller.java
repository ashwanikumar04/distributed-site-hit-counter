package in.ashwanik.dshc;

import in.ashwanik.dshc.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ashwani on 20/06/18.
 */
@RestController
@RequestMapping(value = "/")
public class Controller {
    @Autowired
    private CounterService counterService;

    @RequestMapping(method = RequestMethod.GET, path = "increment")
    public ResponseEntity<?> incrementCounter() {
        counterService.increment();
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, path = "counter")
    public ResponseEntity<?> getCounter() {
        return ResponseEntity.ok(counterService.getCount());
    }
}
