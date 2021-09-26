package vn.sps.study.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DocumentValidationResult extends AbstractDocument{
    private boolean isValid;

    public DocumentValidationResult(String traceId, String eventId) {
        super(traceId, eventId);
    }


    public static DocumentValidationResult from(String traceId, String eventId) {
        return new DocumentValidationResult(traceId,eventId);
    }
}
