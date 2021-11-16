package vn.sps.study.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter@Getter@AllArgsConstructor
public class SFTPExportResult extends AbstractDocument{

    private String batchId;

    public SFTPExportResult(String traceId, String eventId) {
        super(traceId, eventId);
    }

    public static SFTPExportResult from(String traceId, String eventId) {
        return new SFTPExportResult(traceId,eventId);
    }
}
