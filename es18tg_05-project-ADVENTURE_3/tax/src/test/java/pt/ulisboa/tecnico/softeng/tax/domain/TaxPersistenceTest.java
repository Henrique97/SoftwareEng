package pt.ulisboa.tecnico.softeng.tax.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Test;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;

import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;


public class TaxPersistenceTest  {

    private static final String BUYER_NIF = "123456789";
    private static final String SELLER_NIF = "120456788";
    private static final String BUYER_NAME = "JOSE";
    private static final String SELLER_NAME = "MARIA";
    private static final String BUYER_ADD = "RUA DO JOSE";
    private static final String SELLER_ADD = "RUA DA MARIA";
    private static final String ITEMTYPE_NAME = "ROUPA";
    private static final int ITEMTYPE_VAL = 23;
    private static final double INVOICE_VAL = 100;
    private static final LocalDate INVOICE_DATE = new LocalDate(2016, 12, 19);

    IRS irs;
    Seller seller;
    Buyer buyer;

    @Test
    public void success() {
        atomicProcess();
        atomicAssert();
    }

    @Atomic(mode = TxMode.WRITE)
    public void atomicProcess() {
        IRS irs = IRS.getIRS();
        ItemType itemType = new ItemType(IRS.getIRS(), ITEMTYPE_NAME, ITEMTYPE_VAL);
        seller = new Seller(irs, SELLER_NIF, SELLER_NAME, SELLER_ADD);
        buyer = new Buyer(irs, BUYER_NIF, BUYER_NAME, BUYER_ADD);
        InvoiceData invoiceData = new InvoiceData(SELLER_NIF, BUYER_NIF, ITEMTYPE_NAME, INVOICE_VAL, INVOICE_DATE);
        IRS.submitInvoice(invoiceData);
    }

    @Atomic(mode = TxMode.READ)
    public void atomicAssert() {
        IRS irs = IRS.getIRS();
        assertEquals(1, irs.getItemTypeSet().size());
        assertEquals(2, irs.getTaxPayerSet().size());

        List<ItemType> itemTypes=new ArrayList<>(irs.getItemTypeSet());
        List<TaxPayer> taxPayers=new ArrayList<>(irs.getTaxPayerSet());

        ItemType itemType0 = itemTypes.get(0);
        assertEquals(ITEMTYPE_NAME, itemType0.getName());
        assertEquals(ITEMTYPE_VAL, itemType0.getTax());

        TaxPayer buyer0 = taxPayers.get(0);
        TaxPayer seller1 = taxPayers.get(1);

        assertEquals(BUYER_ADD, buyer0.getAddress());
        assertEquals(BUYER_NAME, buyer0.getName());
        assertEquals(BUYER_NIF, buyer0.getNIF());

        assertEquals(SELLER_ADD, seller1.getAddress());
        assertEquals(SELLER_NAME, seller1.getName());
        assertEquals(SELLER_NIF, seller1.getNIF());

    }

    @After
    @Atomic(mode = TxMode.WRITE)
    public void tearDown() {
        IRS.getIRS().delete();
    }

}