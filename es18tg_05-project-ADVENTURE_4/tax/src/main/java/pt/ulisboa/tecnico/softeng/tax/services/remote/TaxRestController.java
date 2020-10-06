package pt.ulisboa.tecnico.softeng.tax.services.remote;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;
import pt.ulisboa.tecnico.softeng.tax.services.local.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.InvoiceData;

@RestController
@RequestMapping(value = "/rest/taxpayers")
public class TaxRestController {
	private static Logger logger = LoggerFactory.getLogger(TaxRestController.class);

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public ResponseEntity<String> submitInvoice(@RequestParam String sellerNIF, @RequestParam String buyerNIF,
			@RequestParam String itemType, @RequestParam double value, @RequestParam String date) {
		logger.info("submitInvoice sellerNIF:{}, buyerNIF:{}, itemType:{}, value:{}, date:{}", sellerNIF, buyerNIF,
				itemType, value, date);

		try {
			// try to convert date
			LocalDate newDate = new LocalDate(date);

			return new ResponseEntity<String>(
					TaxInterface.submitInvoice(new InvoiceData(sellerNIF, buyerNIF, itemType, value, newDate)),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public ResponseEntity<String> cancelInvoice(@RequestParam String reference) {
		logger.info("cancelInvoice reference:{}", reference);
		try {
			TaxInterface.cancelInvoice(reference);
			return new ResponseEntity<>( HttpStatus.OK);
		} catch (TaxException be) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
