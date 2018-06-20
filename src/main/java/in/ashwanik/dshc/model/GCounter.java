package in.ashwanik.dshc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashwani on 20/06/18.
 */
@Getter
@Setter
public class GCounter {
    private String nodeId;
    private HashMap<String, Integer> entries = new HashMap<>();

    public GCounter(String nodeId) {
        this.nodeId = nodeId;
    }

    @JsonIgnore
    public long get() {
        return entries.entrySet().stream().mapToLong(Map.Entry::getValue).sum();
    }

    public void increment() {
        increment(1);
    }

    public void increment(int value) {
        if (value < 1L) {
            throw new IllegalArgumentException("Value needs to be a positive number.");
        }
        if (!entries.containsKey(nodeId)) {
            entries.put(nodeId, 0);
        }
        entries.put(nodeId, entries.get(nodeId) + value);
    }

    public void merge(GCounter counter) {
        entries.forEach((k, v) -> counter.entries.merge(k, v, Math::max));
    }
}
