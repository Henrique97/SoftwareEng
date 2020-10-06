package pt.ulisboa.tecnico.softeng.broker.domain;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.ActivityInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;

public class ProcessPaymentState extends AdventureState {
	public static final int MAX_REMOTE_ERRORS = 3;

	@Override
	public State getState() {
		return State.PROCESS_PAYMENT;
	}

	@Override
	public void process(Adventure adventure) {
		try {
			double a = ActivityInterface.getActivityReservationData(adventure.getActivityConfirmation()).getAmount();
			Integer temp = Integer.valueOf((int) Math.round(a));
			int amountd = temp + (adventure.getAmount());
			
			if (adventure.getRoomConfirmation() != null) {
				amountd += HotelInterface.getRoomBookingData(adventure.getRoomConfirmation()).getAmount();
			}
			adventure.setPaymentConfirmation(BankInterface.processPayment(adventure.getIBAN(), amountd));
			
		} catch (BankException be) {
			adventure.setState(State.CANCELLED);
			return;
		} catch (RemoteAccessException rae) {
			incNumOfRemoteErrors();
			if (getNumOfRemoteErrors() == MAX_REMOTE_ERRORS) {
				adventure.setState(State.CANCELLED);
			}
			return;
		}

		adventure.setState(State.TAX_PAYMENT);
	}

}
