package pt.ulisboa.tecnico.softeng.bank.services.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.bank.services.local.BankInterface;
import pt.ulisboa.tecnico.softeng.bank.services.local.dataobjects.BankOperationData;

@RestController
@RequestMapping(value = "/banks/rest")
public class ProcessController {
    private static Logger logger = LoggerFactory.getLogger(ProcessController.class);

    @RequestMapping(value = "/accounts/{iban}/processPayment", method = RequestMethod.POST)
    public ResponseEntity<String> processPayment(@PathVariable String iban, @RequestParam int amount) {
        try {
            return new ResponseEntity<>(BankInterface.processPayment(iban, amount), HttpStatus.OK);
        } catch (BankException be) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value = "/accounts/cancelPayment", method = RequestMethod.POST)
    public ResponseEntity<String> processPayment(@RequestParam String reference) {
        try {
            return new ResponseEntity<>(BankInterface.cancelPayment(reference), HttpStatus.OK);
        } catch (BankException be) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/operationDta", method = RequestMethod.GET)
    public ResponseEntity<BankOperationData> getOperationData(@RequestParam String reference) {
        try {
            return new ResponseEntity<>(BankInterface.getOperationData(reference), HttpStatus.OK);
        } catch (BankException be) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
