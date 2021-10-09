package vn.sps.study.service;

import vn.sps.study.model.*;

public interface DocumentProcessService {

    UnzippedDocumentResult unzip(UnzippedDocumentRequest request);

    DocumentValidationResult validate(DocumentValidationRequest request);

    SFTPDownloadResult sftpDownload(SFTPDownloadRequest request);

    MetadataExtractionResult extractMetadata(MetadataExtractionRequest request);

    ImageConversionResult convertImage(ImageConversionRequest request);

    AutoClassificationResult autoClassify(AutoClassificationRequest request);

    ManualClassificationResult manualClassify(ManualClassificationRequest request);

    CheckProcessingResult processCheck(CheckProcessingRequest request);

    SFTPExportResult sftpExport(SFTPExportRequest request);

    AcknowledgementUpdateResult updateAck(AcknowledgementUpdateRequest request);

    ManageEngineResult createTicket(ManageEngineRequest request);
}
