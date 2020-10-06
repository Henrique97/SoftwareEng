package pt.ulisboa.tecnico.softeng.broker.domain;

import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.ActivityInterface;

import pt.ulisboa.tecnico.softeng.broker.interfaces.CarInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.TaxInterface;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class TaxPaymentState extends AdventureState {
	public static final int MAX_REMOTE_ERRORS = 3;

	@Override
	public State getState() {
		return State.TAX_PAYMENT;
	}

	@Override
	public void process(Adventure adventure) {
		//create invoice
		InvoiceData invoice;
		try {

			if (adventure.getRoomConfirmation()==null && adventure.getCar()==false) {
				invoice = new InvoiceData(adventure.getBroker().getSellerNIF(),
						adventure.getClient().getNIF(), "ADVENTURE", 
						(ActivityInterface.getActivityReservationData(
						adventure.getActivityConfirmation()).getAmount()
						*(1+adventure.getAmount())),adventure.getBegin());
			}
			
			else if(adventure.getRoomConfirmation()==null) {
				invoice = new InvoiceData(adventure.getBroker().getSellerNIF(),
						adventure.getClient().getNIF(), "ADVENTURE", 
						((ActivityInterface.getActivityReservationData(
						adventure.getActivityConfirmation()).getAmount()
						+(RentACar.getRenting(adventure.getCarConfirmation()).getAmount()))*(1+adventure.getAmount())),adventure.getBegin());
			}
			
			else if(adventure.getCar()==false) {
				invoice = new InvoiceData(adventure.getBroker().getSellerNIF(),
						adventure.getClient().getNIF(), "ADVENTURE", 
						((ActivityInterface.getActivityReservationData(
						adventure.getActivityConfirmation()).getAmount()
						+(HotelInterface.getRoomBookingData(adventure.getRoomConfirmation()).getAmount()))*(1+adventure.getAmount())),adventure.getBegin());
			}
			
			else {
				invoice = new InvoiceData(adventure.getBroker().getSellerNIF(),
					adventure.getClient().getNIF(), "ADVENTURE", 
					(ActivityInterface.getActivityReservationData(
					adventure.getActivityConfirmation()).getAmount()
					+(HotelInterface.getRoomBookingData(adventure.getRoomConfirmation()).getAmount()
					+RentACar.getRenting(adventure.getCarConfirmation()).getAmount()))*(1+adventure.getAmount()),adventure.getBegin());
			}
			adventure.setTaxConfirmation(TaxInterface.submitInvoice(invoice));
		} catch (TaxException be) {
			adventure.setState(State.CANCELLED);
			return;
		} catch (RemoteAccessException rae) {
			incNumOfRemoteErrors();
			if (getNumOfRemoteErrors() == MAX_REMOTE_ERRORS) {
				adventure.setState(State.CANCELLED);
			}
			return;
		}

		adventure.setState(State.CONFIRMED);
	}

}
