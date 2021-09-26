package vn.sps.study.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter@Getter@AllArgsConstructor
public class CheckProcessingRequest extends AbstractDocument{
    public CheckProcessingRequest(String traceId, String eventId) {
        super(traceId,eventId);
    }

    public static CheckProcessingRequest from(String traceId, String eventId) {
        return new CheckProcessingRequest(traceId,eventId);
    }
}
