package vn.sps.study.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter@Getter@AllArgsConstructor
public class AcknowledgementUpdateResult extends AbstractDocument{

    private String batchId;

    public AcknowledgementUpdateResult(String traceId, String eventId) {
        super(traceId, eventId);
    }

    public static AcknowledgementUpdateResult from(String traceId, String eventId) {
        return new AcknowledgementUpdateResult(traceId,eventId);
    }
}
