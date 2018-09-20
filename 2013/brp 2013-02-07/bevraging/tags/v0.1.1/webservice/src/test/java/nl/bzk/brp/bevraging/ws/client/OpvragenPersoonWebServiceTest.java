/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.client;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.inject.Inject;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import nl.bzk.brp.bevraging.ws.basis.Adres;
import nl.bzk.brp.bevraging.ws.basis.Persoon;
import nl.bzk.brp.bevraging.ws.service.OpvragenPersoonPortType;
import nl.bzk.brp.bevraging.ws.service.model.OpvragenPersoonAntwoord;
import nl.bzk.brp.bevraging.ws.service.model.OpvragenPersoonVraag;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Testen tegen live webservices, gedeployed in een embedded Jetty Server.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class OpvragenPersoonWebServiceTest {

    @Inject
    private OpvragenPersoonPortType opvragenPersoon;

    /**
     * Integratietest voor de OpvragenPersoon Webservice.
     */
    @Test
    public void testOpvragenPersoonWebService() throws DatatypeConfigurationException {
        OpvragenPersoonVraag vraag = new OpvragenPersoonVraag();
        vraag.setBsn(BigDecimal.valueOf(123456789));
        vraag.setId(1);
        vraag.setAbonnementId(1);
        vraag.setAfzenderId(1);
        vraag.setTijdstipVerzonden(toXMLGregorianCalendar(new Date()));
        OpvragenPersoonAntwoord antwoord = opvragenPersoon.opvragenPersoon(vraag);

        Assert.assertEquals(0, antwoord.getAantalFouten());
        Assert.assertEquals(1, antwoord.getAantalPersonen());
        Assert.assertFalse(antwoord.getPersoon().isEmpty());

        Persoon persoon = antwoord.getPersoon().get(0);
        Assert.assertNotNull(persoon);
        Adres adres = persoon.getAdres();

        Assert.assertNotNull(adres);
        Assert.assertEquals("New Yorkweg", adres.getAfgekorteNaamOpenbareRuimte());
        Assert.assertEquals("Almere", adres.getGemeenteDeel());
        Assert.assertEquals("A", adres.getHuisletter());
        Assert.assertEquals(73, adres.getHuisnummer().longValue());
        Assert.assertEquals("sous", adres.getHuisnummerToevoeging());
        Assert.assertEquals("to", adres.getLocatieTOVAdres());
        Assert.assertEquals("1334NA", adres.getPostcode());
        Assert.assertEquals("W", adres.getSoortAdresCode());
        Assert.assertEquals("Woonadres", adres.getSoortAdresNaam());

        Assert.assertEquals("Amsterdam", persoon.getBijhoudingsGemeente());
        Assert.assertEquals(1, persoon.getBijhoudingsGemeenteId());
        Assert.assertEquals(123456789, persoon.getBsn().longValue());
        Assert.assertNull(persoon.getBuitenlandseGeboortePlaats());
        Assert.assertNull(persoon.getBuitenlandseGeboorteRegio());
        Assert.assertNull(persoon.getBuitenlandsePlaatsOverlijden());
        Assert.assertEquals(18890426, persoon.getDatumGeboorte().longValue());
        Assert.assertNull(persoon.getDatumOpschorting());
        Assert.assertEquals("Amsterdam", persoon.getGemeenteGeboorte());
        Assert.assertEquals(1, persoon.getGemeenteIdGeboorte());
        Assert.assertEquals("Man", persoon.getGeslachtsAanduiding());
        Assert.assertEquals("M", persoon.getGeslachtsAanduidingCode());
        Assert.assertEquals("Wittgenstein", persoon.getGeslachtsNaam());
        Assert.assertEquals("Nederland", persoon.getLandGeboorte());
        Assert.assertEquals(2, persoon.getLandIdGeboorte());
        Assert.assertEquals("52.429222,2.790527", persoon.getLocatieOmschrijvingGeboorte());
        Assert.assertEquals("Overleden", persoon.getRedenOpschorting());
        Assert.assertEquals("O", persoon.getRedenOpschortingCode());
        Assert.assertEquals("false", persoon.getVerstrekkingsBeperking());
        Assert.assertEquals("Ludwig Josef Johann", persoon.getVoornamen());
        Assert.assertNull(persoon.getVoorvoegsel());
        Assert.assertEquals("Amsterdam", persoon.getWoonplaatsGeboorte());
        Assert.assertEquals(1, persoon.getWoonplaatsIdGeboorte().longValue());
    }

    /**
     * Integratietest voor de OpvragenPersoon Webservice.
     */
    @Test
    public void testOpvragenPersoonNietBestaandBurgerservicenummer() throws DatatypeConfigurationException {
        OpvragenPersoonVraag vraag = new OpvragenPersoonVraag();
        vraag.setBsn(BigDecimal.valueOf(987654321));
        vraag.setId(1);
        vraag.setAbonnementId(1);
        vraag.setAfzenderId(1);
        vraag.setTijdstipVerzonden(toXMLGregorianCalendar(new Date()));
        OpvragenPersoonAntwoord antwoord = opvragenPersoon.opvragenPersoon(vraag);
        Assert.assertEquals(0, antwoord.getAantalPersonen());
        Assert.assertEquals(0, antwoord.getPersoon().size());
        Assert.assertEquals(0, antwoord.getAantalFouten());
    }

    /**
     * Vertaal een gewoon {@link Date} object naar een {@link XMLGregorianCalendar} object, dat geschikt is voor een XML
     * document.
     *
     * @param date Het {@link Date} object dat vertaald moet worden.
     * @return Het {@link XMLGregorianCalendar} object dat het resultaat is van de vertaling.
     * @throws BrzoException
     */
    protected XMLGregorianCalendar toXMLGregorianCalendar(final Date date) throws DatatypeConfigurationException {
        XMLGregorianCalendar xmlCalendar = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar) calendar);
        }
        return xmlCalendar;
    }
}
