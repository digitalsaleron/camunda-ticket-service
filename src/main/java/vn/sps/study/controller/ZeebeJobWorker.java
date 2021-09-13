package vn.sps.study.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import lombok.extern.slf4j.Slf4j;
import vn.sps.study.app.ProfileNames;
import vn.sps.study.model.ResolvedTicketResult;
import vn.sps.study.model.TicketRequest;
import vn.sps.study.service.TicketService;

@Component
@Profile(ProfileNames.ZEEBE_CLIENT)
@Slf4j
public class ZeebeJobWorker {

	@Autowired
	private TicketService ticketService;

	@ZeebeWorker(type = "validatingTickets")
	public void validate(final JobClient client, final ActivatedJob job) {

		Map<String, Object> variables = job.getVariablesAsMap();
		variables.put("isValid", false);

		String ticketId = (String) variables.get("ticketId");
		String type = (String) variables.get("type");
		int amount = (int) variables.get("amount");
		int totalCostAmount = (int) variables.get("totalCostAmount");

		log.info("Polled ticket validation job for ticket {}", ticketId);

		TicketRequest ticket = TicketRequest.from(ticketId, type, amount,
		        totalCostAmount);
		variables.put("isValid", ticketService.validate(ticket));

		client.newCompleteCommand(job.getKey()).variables(variables).send();

	}

	@ZeebeWorker(type = "waitingForApprovalTickets")
	public void approve(final JobClient client, final ActivatedJob job) {

		Map<String, Object> variables = job.getVariablesAsMap();

		String ticketId = (String) variables.get("ticketId");
		String type = (String) variables.get("type");
		int amount = (int) variables.get("amount");
		int totalCostAmount = (int) variables.get("totalCostAmount");

		log.info("Polled ticket approval job for ticket {}", ticketId);

		TicketRequest ticket = TicketRequest.from(ticketId, type, amount,
		        totalCostAmount);
		ticketService.approve(ticket);
		variables.put("isApproved", ticket.isApproved());

		client.newCompleteCommand(job.getKey()).variables(variables).send();

	}

	@ZeebeWorker(type = "approvedTickets")
	public void resolve(final JobClient client, final ActivatedJob job) {

		Map<String, Object> variables = job.getVariablesAsMap();

		String ticketId = (String) variables.get("ticketId");
		String type = (String) variables.get("type");
		int amount = (int) variables.get("amount");
		int totalCostAmount = (int) variables.get("totalCostAmount");

		log.info("Polled ticket resolution job for ticket {}", ticketId);
		TicketRequest ticket = TicketRequest.from(ticketId, type, amount,
		        totalCostAmount);
		ResolvedTicketResult res = ticketService.resolve(ticket);

		variables.put("isResolved", res.isResolved());
		variables.put("resolution", res.getResolution());

		client.newCompleteCommand(job.getKey()).variables(variables).send();

	}

	@ZeebeWorker(type = "failedTickets")
	public void notifyFailedTicket(final JobClient client,
	        final ActivatedJob job) {

		Map<String, Object> variables = job.getVariablesAsMap();
		variables.put("isValid", false);

		String ticketId = (String) variables.get("ticketId");
		String type = (String) variables.get("type");
		int amount = (int) variables.get("amount");
		int totalCostAmount = (int) variables.get("totalCostAmount");

		log.info("Polled ticket failed notification job for ticket {}",
		        ticketId);

		TicketRequest ticket = TicketRequest.from(ticketId, type, amount,
		        totalCostAmount);
		ticketService.notify(ticket);

		client.newCompleteCommand(job.getKey()).variables(variables).send();

	}
}