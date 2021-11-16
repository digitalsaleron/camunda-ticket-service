package vn.sps.study.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter@Getter@AllArgsConstructor
public class SFTPExportRequest extends AbstractDocument{
    public SFTPExportRequest(String traceId, String eventId) {
        super(traceId,eventId);
    }

    public static SFTPExportRequest from(String traceId, String eventId) {
        return new SFTPExportRequest(traceId,eventId);
    }
}
