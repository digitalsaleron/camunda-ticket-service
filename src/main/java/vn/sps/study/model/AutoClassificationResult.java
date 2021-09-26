package vn.sps.study.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter@Getter@AllArgsConstructor
public class AutoClassificationResult extends AbstractDocument{
    public AutoClassificationResult(String traceId, String eventId) {
        super(traceId, eventId);
    }

    public static AutoClassificationResult from(String traceId, String eventId) {
        return new AutoClassificationResult(traceId,eventId);
    }
}
