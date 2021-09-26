package vn.sps.study.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SFTPDownloadRequest extends AbstractDocument{

    private SFTPDownloadRequest(String traceId, String eventId){
        super(traceId,eventId);
    }

    public static SFTPDownloadRequest from(String traceId, String eventId) {
        return new SFTPDownloadRequest(traceId,eventId);
    }
}
