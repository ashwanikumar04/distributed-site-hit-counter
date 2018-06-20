package in.ashwanik.dshc.messaging;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Ashwani on 20/06/18.
 */
@Getter
@Setter
@Builder
public class Message {
    private String id;
    private String producerId;
    private String messageType;
    private String data;
    private long timestamp;

}
