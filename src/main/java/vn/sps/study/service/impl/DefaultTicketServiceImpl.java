package vn.sps.study.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import vn.sps.study.model.ResolvedTicketResult;
import vn.sps.study.model.TicketRequest;
import vn.sps.study.service.TicketService;

@Service
@Slf4j
public class DefaultTicketServiceImpl implements TicketService {

	@Value("${approval.rule.defaultAmount:5000}")
	private long defaultApprovedAmount;

	@Override
	public boolean approve(TicketRequest ticket) {

		Assert.isTrue(ticket.getExceptionStep()==null || ticket.getExceptionStep().isEmpty(),"Unexpected exception");

		log.info("Approve ticket type = {}, amount = {}, totalCostAmount = {}",
		        ticket.getType(), ticket.getAmount(),
		        ticket.getTotalCostAmount());

		ticket.setApproved(
		        ticket.getTotalCostAmount() <= defaultApprovedAmount ? true
		                : false);

		return ticket.isApproved();
	}

	@Override
	public ResolvedTicketResult resolve(TicketRequest ticket) {

		Assert.isTrue(ticket.getExceptionStep()==null || ticket.getExceptionStep().isEmpty(),"Unexpected exception");

		log.info("Resolve ticket type = {}, amount = {}, totalCostAmount = {}",
		        ticket.getType(), ticket.getAmount(),
		        ticket.getTotalCostAmount());

		ResolvedTicketResult res = ResolvedTicketResult.from(ticket);

		res.setResolved(true);
		res.setResolution("Go ahead and purchase the required facilities");

		return res;
	}

	@Override
	public boolean validate(TicketRequest ticket) {

		Assert.isTrue(ticket.getExceptionStep()==null || ticket.getExceptionStep().isEmpty(),"Unexpected exception");

		log.info("Validate ticket type = {}, amount = {}, totalCostAmount = {}",
		        ticket.getType(), ticket.getAmount(),
		        ticket.getTotalCostAmount());

		ticket.setValid(ticket.getType() != null);
		ticket.setValid(ticket.getTotalCostAmount() > 0);
		if (ticket.getTotalCostAmount()==0){
			throw new IllegalArgumentException("Total amount should not be zero");
		}
		return ticket.isValid();
	}

	@Override
	public void notify(TicketRequest ticket) {

		log.warn("Failed ticket type = {}, amount = {}, totalCostAmount = {}",
		        ticket.getType(), ticket.getAmount(),
		        ticket.getTotalCostAmount());
	}
}
