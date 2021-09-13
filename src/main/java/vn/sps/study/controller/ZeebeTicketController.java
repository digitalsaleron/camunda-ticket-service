package vn.sps.study.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.camunda.zeebe.client.ZeebeClient;
import lombok.extern.slf4j.Slf4j;
import vn.sps.study.service.IdService;

@RestController
@RequestMapping("tickets")
@Slf4j
public class ZeebeTicketController {

	@Autowired
	private ZeebeClient client;

	@Autowired
	private IdService idService;

	@PostMapping("/request")
	public void request(
	        @PathVariable(name = "ticketId", required = false) String ticketId,
	        @RequestParam(name = "type", required = false, defaultValue = "Facility") String type,
	        @RequestParam(name = "amount", required = false, defaultValue = "1") int amount,
	        @RequestParam(name = "totalCostAmount", required = false, defaultValue = "500") int totalCostAmount) {

		if (ticketId == null) {
			ticketId = idService.next("TicketId");
		}

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("ticketId", ticketId);
		variables.put("type", type);
		variables.put("amount", amount);
		variables.put("totalCostAmount", totalCostAmount);

		client.newCreateInstanceCommand().bpmnProcessId("TicketApprovalProcess")
		        .latestVersion().variables(variables).send();
		log.info(
		        "Request a ticket with id = {}, type = {}, amount = {}, totalCostAmount = {}",
		        ticketId, type, amount, totalCostAmount);
	}

}