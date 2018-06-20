package in.ashwanik.dshc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashwani on 20/06/18.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString()
public class GCounter {
    private String nodeId;
    private HashMap<String, Long> entries = new HashMap<>();

    public GCounter(String nodeId) {
        this.nodeId = nodeId;
    }

    @JsonIgnore
    public long get() {
        return entries.entrySet().stream().mapToLong(Map.Entry::getValue).sum();
    }

    public void increment() {
        increment(1L);
    }

    public void increment(long value) {
        if (value < 1L) {
            throw new IllegalArgumentException("Value needs to be a positive number.");
        }
        if (!entries.containsKey(nodeId)) {
            entries.put(nodeId, 0L);
        }
        entries.put(nodeId, entries.get(nodeId) + value);
    }

    public void merge(GCounter counter) {
        for (Map.Entry<String, Long> entry : counter.entries.entrySet()) {
            if (entries.containsKey(entry.getKey())) {
                entries.put(entry.getKey(), Math.max(entry.getValue(), entries.get(entry.getKey())));
            } else {
                entries.put(entry.getKey(), entry.getValue());
            }
        }
    }
}
