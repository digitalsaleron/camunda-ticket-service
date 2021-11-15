package vn.sps.study.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

@Component
@Configuration
@Slf4j
@Profile("sftp")
public class SFTPScanner {

    @Value("${sftp.scanner.scheduler.enabled:false}")
    private boolean enabled;

    @Value("${sftp.parentDir:./data}")
    private Path sftpParentDir;

    @Value("${sftp.inputFolder:input}")
    private String inputFolder;

    @Value("${sftp.outputFolder:output}")
    private String outputFolder;

    @Value("${sftp.uploadingCaseEndsWith:uploading}")
    private String uploadingCaseEndsWith;

    @Value("${sftp.importedCaseEndsWith:imported}")
    private String importedCaseEndsWith;

    public SFTPScanner() {
    }

    @Scheduled(fixedDelayString = "${sftp.scanner.scheduler.fixedDelayString:PT5S}")
    public void scan(){
      log.debug("SFTP Scanner scheduler is checking");
      if (!enabled){
          log.debug("SFTP Scanner scheduler disabled");
          return;
      }
      final File parentDir = sftpParentDir.toFile();
      if (!parentDir.exists() || !parentDir.isDirectory()){
          log.warn("Invalid parent directory [{}]. It should be valid existing directory",sftpParentDir.toString());
          return;
      }
        for (File tenantDir : parentDir.listFiles()) {
            if (tenantDir.isDirectory()){
                scanTenantDir(tenantDir);
            }
        }
    }

    private void scanTenantDir(File tenantDir) {
        for (File caseDir : tenantDir.listFiles()) {
            if (caseDir.isDirectory()
                    && !caseDir.getName().endsWith(uploadingCaseEndsWith)
                    && !caseDir.getName().endsWith(importedCaseEndsWith)){
                log.info("Available case for import at [{}]",caseDir.toString());
                importCase(tenantDir.getName(), caseDir);
            }
        }

    }

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StreamBridge streamBridge;

    private void importCase(String tenant, File caseDir) {
        long eventId = System.nanoTime();

        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("tenantId",tenant);
        rootNode.put("eventId", eventId +"");
        rootNode.put("traceId", tenant+caseDir.getName()+eventId);
        rootNode.put("lob", tenant+"-default");
        rootNode.put("userGroup", "operator");
        ArrayNode files = objectMapper.createArrayNode();
        rootNode.set("files",files);
        final int pL = sftpParentDir.toString().length();
        for (File f : caseDir.listFiles()) {
            if (f.isFile()){
                ObjectNode fileNode = files.addObject();
                String filename = f.getName();
                fileNode.put("fileName", filename);
                fileNode.put("fileFormat", filename.substring(filename.lastIndexOf('.')));
                String filePath = f.toString().substring(pL);
                filePath=filePath.replace("\\","/");
                fileNode.put("filePath", filePath);
            }
        }
        streamBridge.send(tenant+".sftp-new-zip-file",rootNode);
        File importedCase = new File( caseDir.toString()+"_"+importedCaseEndsWith);
        caseDir.renameTo(importedCase);
        log.info("Imported case at [{}]",caseDir.toString());
    }
}
