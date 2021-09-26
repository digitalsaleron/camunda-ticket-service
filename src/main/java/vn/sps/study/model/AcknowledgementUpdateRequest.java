package vn.sps.study.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter@Getter@AllArgsConstructor
public class AcknowledgementUpdateRequest extends AbstractDocument{
    public AcknowledgementUpdateRequest(String traceId, String eventId) {
        super(traceId,eventId);
    }

    public static AcknowledgementUpdateRequest from(String traceId, String eventId) {
        return new AcknowledgementUpdateRequest(traceId,eventId);
    }
}
