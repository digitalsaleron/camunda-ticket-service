package vn.sps.study.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import vn.sps.study.app.ProfileNames;
import vn.sps.study.model.ResolvedTicketResult;
import vn.sps.study.model.TicketRequest;
import vn.sps.study.service.TicketService;

import java.util.Map;

@Component
@Profile(ProfileNames.ZEEBE_EVALUATE_MAPPING)
@Slf4j
public class EvaluateVariableMappingJobWorker {

	@Autowired
	private TicketService ticketService;

	@ZeebeWorker(type = "script")
	public void validate(final JobClient client, final ActivatedJob job) {

		ObjectNode variables = job.getVariablesAsType(ObjectNode.class);
		log.info(variables.toPrettyString());

		String traceId = variables.findValue("traceId").textValue();
		ArrayNode files = (ArrayNode) variables.get("files");

		log.info("Evaluate message {}", traceId);

		files.forEach(t->{
			((ObjectNode)t).put("processedValue","Processed");
		});

		client.newCompleteCommand(job.getKey()).variables(variables).send();

	}

}