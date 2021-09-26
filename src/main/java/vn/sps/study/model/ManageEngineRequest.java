package vn.sps.study.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter@Getter@AllArgsConstructor
public class ManageEngineRequest extends AbstractDocument{
    public ManageEngineRequest(String traceId, String eventId) {
        super(traceId,eventId);
    }

    public static ManageEngineRequest from(String traceId, String eventId) {
        return new ManageEngineRequest(traceId,eventId);
    }
}
