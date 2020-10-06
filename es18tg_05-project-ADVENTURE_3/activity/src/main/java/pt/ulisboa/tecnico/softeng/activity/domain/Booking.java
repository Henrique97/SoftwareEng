package pt.ulisboa.tecnico.softeng.activity.domain;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;

public class Booking extends Booking_Base {
	private static int counter = 0;

	private static final String SPORT_TYPE = "SPORT";

	public Booking(ActivityProvider provider, ActivityOffer offer, String buyerNif, String buyerIban) {
		checkArguments(provider, offer, buyerNif, buyerIban);

		setReference(offer.getActivity().getActivityProvider().getCode() + Integer.toString(++Booking.counter));

		setActivityOffer(offer);
		setProviderNif(provider.getNif());
		setNif(buyerNif);
		setIban(buyerIban);
		setAmount(offer.getPrice());
		setDate(offer.getBegin());
		setCancelledInvoice(false);
		setCancelledPaymentReference(null);

		offer.addBooking(this);
	}

	private void checkArguments(ActivityProvider provider, ActivityOffer offer, String buyerNIF, String buyerIban) {
		if (provider == null || offer == null || buyerNIF == null || buyerNIF.trim().length() == 0 || buyerIban == null
				|| buyerIban.trim().length() == 0) {
			throw new ActivityException();
		}

		if (offer.getCapacity() == offer.getNumberActiveOfBookings()) {
			throw new ActivityException();
		}
	}

	public void delete() {
		setActivityOffer(null);

		deleteDomainObject();
	}

	public String getType() {
		return SPORT_TYPE;
	}

	public String cancel() {
		setCancel("CANCEL" + getReference());
		setCancellationDate(new LocalDate());

		getActivityOffer().getActivity().getActivityProvider().getProcessor().submitBooking(this);

		return getCancel();
	}

	public boolean isCancelled() {
		return getCancel() != null;
	}

}
