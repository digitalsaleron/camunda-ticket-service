package vn.sps.study.service;

import vn.sps.study.model.ResolvedTicketResult;
import vn.sps.study.model.TicketRequest;

public interface TicketService {

	boolean approve(TicketRequest ticket);

	ResolvedTicketResult resolve(TicketRequest ticket);
	
	boolean validate(TicketRequest ticket);

	void notify(TicketRequest ticket);
}
