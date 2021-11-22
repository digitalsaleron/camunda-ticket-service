package vn.sps.study.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.protobuf.Duration;
import io.camunda.zeebe.client.api.command.CreateProcessInstanceCommandStep1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.camunda.zeebe.client.ZeebeClient;
import lombok.extern.slf4j.Slf4j;
import vn.sps.study.service.IdService;

@RestController
@Slf4j
@RequestMapping
public class ZeebeProcessController {

    @Autowired
    private ZeebeClient client;

    @Autowired
    private IdService idService;

    @PostMapping("/processes/{processId}/{version}/start")
    public void request(
            @PathVariable(name = "processId", required = true) String processId,
            @PathVariable(name = "version", required = false) Integer version,
            @RequestBody Map<String, Object> bodyParts) {

        final Map<String, Object> variables = new HashMap<String, Object>();
        variables.putAll(bodyParts);

        CreateProcessInstanceCommandStep1.CreateProcessInstanceCommandStep2 commandStep2 = client.newCreateInstanceCommand().bpmnProcessId(processId);
        CreateProcessInstanceCommandStep1.CreateProcessInstanceCommandStep3 commandStep3 = null;

        String versionS = "latest";
        if (version==null || version.intValue() <=0) {
            commandStep3 = commandStep2.latestVersion();
        }else {
            commandStep3 = commandStep2.version(version.intValue());
            versionS = version.intValue() + "";
        }

        commandStep3.variables(variables)
                .send();

        log.info("Start process [{}/{}]", processId, versionS);
    }

    @PostMapping("/messages/{messageName}/start")
    public void message(
            @RequestParam(name = "correlationKey", required = false) String correlationKey,
            @PathVariable(name = "messageName", required = true) String messageName,
            @RequestHeader Map<String, Object> headers,
            @RequestBody Map<String, Object> bodyParts) {

        final Map<String, Object> variables = new HashMap<String, Object>();
        variables.putAll(bodyParts);


        String correlationValue = (String) bodyParts.get(correlationKey);
        client.newPublishMessageCommand()
                .messageName(messageName)
                .correlationKey(correlationValue)
                .variables(variables)
                .send();

        log.info("Message from {} with correlation key {}", messageName, correlationValue);

    }

}