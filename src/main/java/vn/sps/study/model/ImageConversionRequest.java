package vn.sps.study.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ImageConversionRequest extends AbstractDocument{

    private String toFormat;

    public ImageConversionRequest(String traceId, String eventId, String toFormat) {
        super(traceId,eventId);
        setToFormat(toFormat);
    }


    public static ImageConversionRequest from(String traceId, String eventId, String toFormat) {

        return new ImageConversionRequest(traceId,eventId,toFormat);
    }
}
