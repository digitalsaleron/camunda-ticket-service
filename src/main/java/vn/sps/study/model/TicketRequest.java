package vn.sps.study.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TicketRequest {
	private String id;
	private int amount;
	private int totalCostAmount;
	private boolean isValid;
	private boolean isApproved;
	private String type;
	private String exceptionStep;

	private TicketRequest(String ticketId, int amount, int totalCostAmount, boolean isValid, boolean isApproved, String type) {
		this(ticketId,amount,totalCostAmount,isValid,isApproved,type,null);
	}


	public static TicketRequest from(String ticketId, String type, int amount,
	        int totalCostAmount) {
		return new TicketRequest(ticketId, amount, totalCostAmount, false,
		        false, type);
	}
}
