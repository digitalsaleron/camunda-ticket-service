package vn.sps.study.controller;

import java.util.function.Function;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.config.ListenerContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.DefaultAfterRollbackProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.FixedBackOff;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;
import vn.sps.study.app.ProfileNames;
import vn.sps.study.model.ResolvedTicketResult;
import vn.sps.study.model.TicketRequest;
import vn.sps.study.service.TicketService;

@Component
@Configuration
@Slf4j
@Profile(ProfileNames.KAFKA_CLIENT)
public class KafkaStreamIntegrator {

	@Autowired
	private TicketService ticketService;

	@Transactional
	@Bean
	public Function<JsonNode, JsonNode> validateTicket() {
		return e -> {

			ObjectNode t = (ObjectNode) e;

			String ticketId = t.findValue("ticketId").textValue();
			String type = t.findValue("type").textValue();
			int amount = t.findValue("amount").intValue();
			int totalCostAmount = t.findValue("totalCostAmount").intValue();

			log.info("Received ticket validation event for ticket {}",
			        ticketId);

			TicketRequest ticket = TicketRequest.from(ticketId, type, amount,
			        totalCostAmount);
			ticketService.validate(ticket);
			t.put("isValid", ticket.isValid());

			return t;
		};
	}

	@Transactional
	@Bean
	public Function<JsonNode, JsonNode> approveTicket() {
		return e -> {

			ObjectNode t = (ObjectNode) e;

			String ticketId = t.findValue("ticketId").textValue();
			String type = t.findValue("type").textValue();
			int amount = t.findValue("amount").intValue();
			int totalCostAmount = t.findValue("totalCostAmount").intValue();

			log.info("Received ticket approval event for ticket {}", ticketId);

			TicketRequest ticket = TicketRequest.from(ticketId, type, amount,
			        totalCostAmount);
			ticketService.approve(ticket);
			t.put("isApproved", ticket.isApproved());

			return t;
		};
	}

	@Transactional
	@Bean
	public Function<JsonNode, JsonNode> resolveTicket() {
		return e -> {

			ObjectNode t = (ObjectNode) e;

			String ticketId = t.findValue("ticketId").textValue();
			String type = t.findValue("type").textValue();
			int amount = t.findValue("amount").intValue();
			int totalCostAmount = t.findValue("totalCostAmount").intValue();

			log.info("Received ticket resolution event for ticket {}",
			        ticketId);

			TicketRequest ticket = TicketRequest.from(ticketId, type, amount,
			        totalCostAmount);
			ResolvedTicketResult result = ticketService.resolve(ticket);

			t.put("isResolved", result.isResolved());
			t.put("resolution", result.getResolution());

			return t;
		};
	}

	@Transactional
	@Bean
	public Function<JsonNode, JsonNode> notifyFailedTicket() {
		return e -> {

			ObjectNode t = (ObjectNode) e;

			String ticketId = t.findValue("ticketId").textValue();
			String type = t.findValue("type").textValue();
			int amount = t.findValue("amount").intValue();
			int totalCostAmount = t.findValue("totalCostAmount").intValue();

			log.info("Received ticket resolution event for ticket {}",
			        ticketId);

			TicketRequest ticket = TicketRequest.from(ticketId, type, amount,
			        totalCostAmount);
			ticketService.notify(ticket);

			return t;
		};
	}

	@Bean
	public ListenerContainerCustomizer<AbstractMessageListenerContainer<byte[], byte[]>> customizer() {
		// Disable retry in the AfterRollbackProcessor
		return (container, destination, group) -> container
		        .setAfterRollbackProcessor(
		                new DefaultAfterRollbackProcessor<byte[], byte[]>(
		                        (record, exception) -> System.out.println(
		                                "Discarding failed record: " + record),
		                        new FixedBackOff(0L, 0)));
	}
}
