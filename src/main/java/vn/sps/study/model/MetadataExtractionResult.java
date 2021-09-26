package vn.sps.study.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class MetadataExtractionResult extends AbstractDocument{
    private boolean hasCheck;

    public MetadataExtractionResult(String traceId, String eventId) {
        super(traceId, eventId);
    }

    public static MetadataExtractionResult from(String traceId, String eventId) {
        return new MetadataExtractionResult(traceId,eventId);
    }
}
