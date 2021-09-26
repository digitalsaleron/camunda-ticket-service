package vn.sps.study.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ImageConversionResult extends AbstractDocument{
    public ImageConversionResult(String traceId, String eventId) {
        super(traceId, eventId);
    }

    public static ImageConversionResult from(String traceId, String eventId) {
        return new ImageConversionResult(traceId, eventId);
    }
}
