package vn.sps.study.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter@Getter@AllArgsConstructor
public class AutoClassificationRequest extends AbstractDocument{
    public AutoClassificationRequest(String traceId, String eventId) {
        super(traceId,eventId);
    }

    public static AutoClassificationRequest from(String traceId, String eventId) {
        return new AutoClassificationRequest(traceId,eventId);
    }
}
