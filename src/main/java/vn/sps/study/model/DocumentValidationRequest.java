package vn.sps.study.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DocumentValidationRequest extends AbstractDocument{

    public DocumentValidationRequest(String traceId, String eventId) {
        super(traceId, eventId);
    }

    public static DocumentValidationRequest from(String traceId, String eventId) {
        return new DocumentValidationRequest(traceId, eventId);
    }
}
