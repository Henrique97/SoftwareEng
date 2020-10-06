package pt.ulisboa.tecnico.softeng.broker.domain;

import java.util.NoSuchElementException;

import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.CarInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentState extends AdventureState {
	public static final int MAX_REMOTE_ERRORS = 5;

	@Override
	public State getState() {
		return State.RENT_STATE;
	}

	@Override
	public void process(Adventure adventure) {
		try {
			if(adventure.getCar()) {
				try {
					Vehicle car = CarInterface.getAllAvailableCars(adventure.getBegin(), adventure.getEnd()).iterator().next();
					adventure.setCarConfirmation(
						(car.rent("lx1423", adventure.getBegin(), adventure.getEnd(), adventure.getBroker().getBuyerNIF(), adventure.getBroker().getIBAN())).getReference()
					);
				}
				catch (NoSuchElementException e) {
					adventure.setState(State.PROCESS_PAYMENT);
				}
			}
			else {
				adventure.setState(State.PROCESS_PAYMENT);
			}
			
		} catch (CarException he) {
			adventure.setState(State.UNDO);
			return;
		} catch (RemoteAccessException rae) {
			incNumOfRemoteErrors();
			if (getNumOfRemoteErrors() == MAX_REMOTE_ERRORS) {
				adventure.setState(State.UNDO);
			}
			return;
		}

		adventure.setState(State.PROCESS_PAYMENT);
	}

}
