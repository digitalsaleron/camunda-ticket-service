package vn.sps.study.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
@Slf4j
public class KafkaMessageProducer {

    @Autowired
    private StreamBridge streamBridge;

    @PostMapping("/messages/topic/{topicName}")
    public void kafkaMessage(
            @PathVariable(name = "topicName", required = true) String topicName,
            @RequestHeader Map<String, Object> headers,
            @RequestBody JsonNode payload) {

        streamBridge.send(topicName,payload);

        log.info("Produce message to kafka topic {}", topicName);

    }
}
