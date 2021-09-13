package vn.sps.study.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResolvedTicketResult {

	private final TicketRequest ticket;
	private boolean isResolved;
	private String resolution;

	public static ResolvedTicketResult from(TicketRequest ticket) {
		return new ResolvedTicketResult(ticket, false, null);
	}

}
