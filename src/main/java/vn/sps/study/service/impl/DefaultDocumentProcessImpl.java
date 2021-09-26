package vn.sps.study.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.sps.study.model.*;
import vn.sps.study.service.DocumentProcessService;

@Service
@Slf4j
public class DefaultDocumentProcessImpl implements DocumentProcessService {
    @Override
    public UnzippedDocumentResult unzip(UnzippedDocumentRequest request) {
        return null;
    }

    @Override
    public DocumentValidationResult validate(DocumentValidationRequest request) {
        DocumentValidationResult result =DocumentValidationResult.from(request.getTraceId(),request.getEventId());
        result.setValid(true);

        return result;
    }

    @Override
    public SFTPDownloadResult sftpDownload(SFTPDownloadRequest request) {
        return null;
    }

    @Override
    public MetadataExtractionResult extractMetadata(MetadataExtractionRequest request) {

        MetadataExtractionResult result = MetadataExtractionResult.from(request.getTraceId(),request.getEventId());
        result.setHasCheck(request.getTraceId().contains("check"));
        return result;
    }

    @Override
    public ImageConversionResult convertImage(ImageConversionRequest request) {

        log.info("Converting image to {}", request.getToFormat());

        ImageConversionResult result=ImageConversionResult.from(request.getTraceId(),request.getEventId());

        return result;
    }

    @Override
    public AutoClassificationResult autoClassify(AutoClassificationRequest request) {
        return null;
    }

    @Override
    public ManualClassificationResult manualClassify(ManualClassificationRequest request) {
        return null;
    }

    @Override
    public CheckProcessingResult processCheck(CheckProcessingRequest request) {
        return null;
    }

    @Override
    public SFTPExportResult sftpExport(SFTPExportRequest request) {
        return null;
    }
}
