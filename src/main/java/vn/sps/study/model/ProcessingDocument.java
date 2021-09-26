package vn.sps.study.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ProcessingDocument {
    private String traceId;
    private String eventId;
    private Object caseData;
    private boolean isValid;

    public static ProcessingDocument from(String traceId, String eventId) {
        return new ProcessingDocument(traceId,eventId, null, false);
    }
}
