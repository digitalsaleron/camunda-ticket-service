package vn.sps.study.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UnzippedDocumentRequest extends AbstractDocument{
    public UnzippedDocumentRequest(String traceId, String eventId) {
        super(traceId,eventId);
    }

    public static UnzippedDocumentRequest from(String traceId, String eventId) {
        return new UnzippedDocumentRequest(traceId,eventId);
    }
}
