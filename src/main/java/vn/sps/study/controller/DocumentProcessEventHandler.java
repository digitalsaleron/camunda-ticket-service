package vn.sps.study.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.config.ListenerContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.DefaultAfterRollbackProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.FixedBackOff;
import vn.sps.study.app.ProfileNames;
import vn.sps.study.model.*;
import vn.sps.study.service.DocumentProcessService;

import javax.transaction.Transactional;
import java.util.function.Function;

@Component
@Configuration
@Slf4j
@Profile(ProfileNames.DOCUMENT_MANAGEMENT)
public class DocumentProcessEventHandler {

    @Autowired
    private DocumentProcessService service;

    @Transactional
    @Bean
    public Function<JsonNode, JsonNode> validateInput() {
        return e -> {

            ObjectNode t = (ObjectNode) e;

            String traceId = t.findValue("traceId").textValue();
            String eventId = t.findValue("eventId").textValue();


            log.info("Received input validation event for document {}",
                    traceId);
            DocumentValidationRequest request = DocumentValidationRequest.from(traceId, eventId);
            DocumentValidationResult result = service.validate(request);

            t.put("isValid", result.isValid());

            return t;
        };
    }

    @Transactional
    @Bean
    public Function<JsonNode, JsonNode> sftpDownload() {
        return e -> {

            ObjectNode t = (ObjectNode) e;

            String traceId = t.findValue("traceId").textValue();
            String eventId = t.findValue("eventId").textValue();


            log.info("Received sftp download event for document {}", traceId);

            SFTPDownloadRequest request = SFTPDownloadRequest.from(traceId, eventId);
            SFTPDownloadResult result = service.sftpDownload(request);

            return t;
        };
    }

    @Transactional
    @Bean
    public Function<JsonNode, JsonNode> unzip() {
        return e -> {

            ObjectNode t = (ObjectNode) e;

            String traceId = t.findValue("traceId").textValue();
            String eventId = t.findValue("eventId").textValue();

            log.info("Received unzip event for document {}", traceId);

            UnzippedDocumentRequest request = UnzippedDocumentRequest.from(traceId, eventId);
            UnzippedDocumentResult result = service.unzip(request);

            return t;
        };
    }

    @Transactional
    @Bean
    public Function<JsonNode, JsonNode> extractMetadata() {
        return e -> {

            ObjectNode t = (ObjectNode) e;

            String traceId = t.findValue("traceId").textValue();
            String eventId = t.findValue("eventId").textValue();

            log.info("Received metadata extraction event for document {}", traceId);

            MetadataExtractionRequest request = MetadataExtractionRequest.from(traceId, eventId);
            MetadataExtractionResult result = service.extractMetadata(request);
            t.put("hasCheck", result.isHasCheck());

            return t;
        };
    }

    @Transactional
    @Bean
    public Function<JsonNode, JsonNode> convertToJPG() {
        return e -> {

            ObjectNode t = (ObjectNode) e;

            String traceId = t.findValue("traceId").textValue();
            String eventId = t.findValue("eventId").textValue();
            String toFormat = "JPG";

            log.info("Received {} image conversion event for document {}", toFormat, traceId);

            ImageConversionRequest request = ImageConversionRequest.from(traceId, eventId, toFormat);
            ImageConversionResult result = service.convertImage(request);

            return t;
        };
    }

    @Transactional
    @Bean
    public Function<JsonNode, JsonNode> convertToTIF() {
        return e -> {

            ObjectNode t = (ObjectNode) e;

            String traceId = t.findValue("traceId").textValue();
            String eventId = t.findValue("eventId").textValue();
            String toFormat = "TIF";

            log.info("Received {} image conversion event for document {}", toFormat, traceId);

            ImageConversionRequest request = ImageConversionRequest.from(traceId, eventId, toFormat);
            ImageConversionResult result = service.convertImage(request);

            return t;
        };
    }

    @Transactional
    @Bean
    public Function<JsonNode, JsonNode> autoClassify() {
        return e -> {

            ObjectNode t = (ObjectNode) e;

            String traceId = t.findValue("traceId").textValue();
            String eventId = t.findValue("eventId").textValue();


            log.info("Received auto classification event for document {}", traceId);

            AutoClassificationRequest request = AutoClassificationRequest.from(traceId, eventId);
            AutoClassificationResult result = service.autoClassify(request);

            return t;
        };
    }

    @Transactional
    @Bean
    public Function<JsonNode, JsonNode> manualClassify() {
        return e -> {

            ObjectNode t = (ObjectNode) e;

            String traceId = t.findValue("traceId").textValue();
            String eventId = t.findValue("eventId").textValue();


            log.info("Received manual classification event for document {} eventId = {}", traceId, eventId);

            ManualClassificationRequest request = ManualClassificationRequest.from(traceId, eventId);
            ManualClassificationResult result = service.manualClassify(request);

            return t;
        };
    }

    @Transactional
    @Bean
    public Function<JsonNode, JsonNode> processCheck() {
        return e -> {

            ObjectNode t = (ObjectNode) e;

            String traceId = t.findValue("traceId").textValue();
            String eventId = t.findValue("eventId").textValue();


            log.info("Received check processing event for document {}", traceId);

            CheckProcessingRequest request = CheckProcessingRequest.from(traceId, eventId);
            CheckProcessingResult result = service.processCheck(request);

            return t;
        };
    }

    @Transactional
    @Bean
    public Function<JsonNode, JsonNode> sftpExport() {
        return e -> {

            ObjectNode t = (ObjectNode) e;

            String traceId = t.findValue("traceId").textValue();
            String eventId = t.findValue("eventId").textValue();


            log.info("Received sftp export event for document {}", traceId);

            SFTPExportRequest request = SFTPExportRequest.from(traceId, eventId);
            SFTPExportResult result = service.sftpExport(request);

            t.put("batchId", result.getBatchId());

            log.info("Exported batch with id {}",result.getBatchId());

            return t;
        };
    }

    @Transactional
    @Bean
    public Function<JsonNode, JsonNode> updateAckStatus() {
        return e -> {

            ObjectNode t = (ObjectNode) e;

            String traceId = t.findValue("traceId").textValue();
            String eventId = t.findValue("eventId").textValue();


            log.info("Received acknowledgement update event for document {}", traceId);

            AcknowledgementUpdateRequest request = AcknowledgementUpdateRequest.from(traceId, eventId);
            AcknowledgementUpdateResult result = service.updateAck(request);

            return t;
        };
    }

    @Transactional
    @Bean
    public Function<JsonNode, JsonNode> createMETicket() {
        return e -> {

            ObjectNode t = (ObjectNode) e;

            String traceId = t.findValue("traceId").textValue();
            String eventId = t.findValue("eventId").textValue();


            log.info("Received ME creation event for document {}", traceId);

            ManageEngineRequest request = ManageEngineRequest.from(traceId, eventId);
            ManageEngineResult result = service.createTicket(request);
            t.put("ticketId",result.getTicketId());

            return t;
        };
    }


    @Transactional
    @Bean
    public Function<JsonNode, JsonNode> notifyBusiness() {
        return e -> {

            ObjectNode t = (ObjectNode) e;

            String traceId = t.findValue("traceId").textValue();
            String eventId = t.findValue("eventId").textValue();


            log.info("Received business error notification event for document {}", traceId);

            SFTPExportRequest request = SFTPExportRequest.from(traceId, eventId);
            SFTPExportResult result = service.sftpExport(request);

            return t;
        };
    }


}
