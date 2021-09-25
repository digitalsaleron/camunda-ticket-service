package vn.sps.study.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.protobuf.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping("/processes/{processId}/start")
	public void request(
	        @PathVariable(name = "ticketId", required = false) String ticketId,
	        @RequestParam(name = "type", required = false, defaultValue = "Facility") String type,
	        @RequestParam(name = "amount", required = false, defaultValue = "1") int amount,
	        @RequestParam(name = "totalCostAmount", required = false, defaultValue = "500") int totalCostAmount,
	        @PathVariable(name = "processId", required = true) String processId,
	        @RequestBody(required = false) String envelope) {

		if (ticketId == null) {
			ticketId = idService.next("TicketId");
		}

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("ticketId", ticketId);
		variables.put("type", type);
		variables.put("amount", amount);
		variables.put("totalCostAmount", totalCostAmount);
		variables.put("envelope", envelope);

		client.newCreateInstanceCommand().bpmnProcessId(processId)
		        .latestVersion().variables(variables).send();
		log.info(
		        "Request a ticket with id = {}, type = {}, amount = {}, totalCostAmount = {}",
		        ticketId, type, amount, totalCostAmount);
	}

	@PostMapping("/messages/{messageName}/start")
	public void message(
			@PathVariable(name = "ticketId", required = false) String ticketId,
			@RequestParam(name = "type", required = false, defaultValue = "Facility") String type,
			@RequestParam(name = "amount", required = false, defaultValue = "1") int amount,
			@RequestParam(name = "totalCostAmount", required = false, defaultValue = "500") int totalCostAmount,
			@PathVariable(name = "messageName", required = true) String messageName,
			@RequestBody(required = false) String envelope) {

		if (ticketId == null) {
			ticketId = idService.next("TicketId");
		}

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("ticketId", ticketId);
		variables.put("type", type);
		variables.put("amount", amount);
		variables.put("totalCostAmount", totalCostAmount);
		variables.put("envelope", envelope);

		String correlationKey = UUID.randomUUID().toString();
		client.newPublishMessageCommand()
				.messageName(messageName)
				.correlationKey(correlationKey)


				.variables(variables).send();
		log.info(
				"Message from {} with correlation key {} - Request a ticket with id = {}, type = {}, amount = {}, totalCostAmount = {}",
				messageName,correlationKey , ticketId, type, amount, totalCostAmount);
	}

}