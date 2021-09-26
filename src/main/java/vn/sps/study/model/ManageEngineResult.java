package vn.sps.study.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter@Getter@AllArgsConstructor
public class ManageEngineResult extends AbstractDocument{

    private String ticketId;

    public ManageEngineResult(String traceId, String eventId) {
        super(traceId, eventId);
    }

    public static ManageEngineResult from(String traceId, String eventId) {
        return new ManageEngineResult(traceId,eventId);
    }
}
