package vn.sps.study.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter@Getter@AllArgsConstructor
public class ManualClassificationRequest extends AbstractDocument{
    public ManualClassificationRequest(String traceId, String eventId) {
        super(traceId,eventId);
    }

    public static ManualClassificationRequest from(String traceId, String eventId) {
        return new ManualClassificationRequest(traceId,eventId);
    }
}
