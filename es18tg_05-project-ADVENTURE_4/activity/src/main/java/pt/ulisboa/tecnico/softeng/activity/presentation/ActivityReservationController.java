package pt.ulisboa.tecnico.softeng.activity.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.activity.services.local.ActivityInterface;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityData;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityOfferData;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityProviderData;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityReservationData;

@Controller
@RequestMapping(value = "/providers/{codeProvider}/activities/{codeActivity}/offers/{codeOffer}/reservations")
public class ActivityReservationController {
	private static Logger logger = LoggerFactory.getLogger(ActivityReservationController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String offerForm(Model model, @PathVariable String codeProvider, @PathVariable String codeActivity, @PathVariable String codeOffer) {
		logger.info("reservationForm providerCode:{}, activityCode: {}, offerCode: {} ", codeProvider, codeActivity, codeOffer);
		ActivityData activityData = ActivityInterface.getActivityDataByCode(codeProvider, codeActivity);

		if (activityData == null) {
			model.addAttribute("error", "Error: it does not exist an activity provider with the code " + codeProvider);
			model.addAttribute("provider", new ActivityProviderData());
			model.addAttribute("providers", ActivityInterface.getProviders());
			return "providers";
		}			
		
		model.addAttribute("offer", activityData.getOffer(codeOffer));
		model.addAttribute("activity", activityData);
		model.addAttribute("reservation",new ActivityReservationData());
		return "reservations";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String offerSubmit(Model model, @PathVariable String codeProvider, @PathVariable String codeActivity, @PathVariable String codeOffer,
			@ModelAttribute ActivityReservationData reservation) {
		logger.info("reservationSubmit codeProvider:{}, codeActivity:{}, codeOffer: {}, nif: {}, iban: {}", codeProvider, codeActivity, codeOffer ,reservation.getNif(),
				reservation.getIban());

		try {
			ActivityInterface.reserveActivity(codeProvider,codeActivity, codeOffer, reservation);
		} catch (ActivityException e) {
			model.addAttribute("error", "Error: it was not possible to create de offer");
			model.addAttribute("offer", ActivityInterface.getActivityDataByCode(codeProvider, codeActivity).getOffer(codeOffer));
			model.addAttribute("activity", ActivityInterface.getActivityDataByCode(codeProvider, codeActivity));
			model.addAttribute("reservation", new ActivityReservationData());
			return "reservations";
		}
		
		
		return "redirect:/providers/" + codeProvider + "/activities/" + codeActivity + "/offers/" + codeOffer + "/reservations";
	}

}
