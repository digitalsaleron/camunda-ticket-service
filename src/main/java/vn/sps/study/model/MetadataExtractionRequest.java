package vn.sps.study.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class MetadataExtractionRequest extends AbstractDocument{

    public MetadataExtractionRequest(String traceId, String eventId) {
        super(traceId, eventId);
    }

    public static MetadataExtractionRequest from(String traceId, String eventId) {
        return new MetadataExtractionRequest(traceId,eventId);
    }
}
