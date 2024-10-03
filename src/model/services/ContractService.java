package model.services;

import java.time.LocalDate;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {

	private OnlinePaymentService onlinePaymentService;

	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}
	
	public void processContract(Contract contract, int months) {
		double initialQuota = contract.getTotalValue() / months;
		for(int i = 1; i<= months; i++) {
			LocalDate dueDate = contract.getDate().plusMonths(i);
			double monthlyFee = onlinePaymentService.interest(initialQuota, i);
			double paymentFee = onlinePaymentService.paymentFee(monthlyFee);
			double finalQuota = initialQuota + monthlyFee + paymentFee;
			contract.getInstallments().add(new Installment(dueDate, finalQuota));
		}
	}
}