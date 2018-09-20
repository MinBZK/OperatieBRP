/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.kern.interceptor;

import java.io.IOException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.phase.Phase;
import org.apache.tools.ant.filters.StringInputStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class SchemaValidationInInterceptorTest {

    @Before
    public void setup() {
        System.setProperty("nl.bzk.brp.webservice.xsd.validatie", "in");
    }

    @Test
    public void testCorrecteInitialisatieInterceptor() {
        final SchemaValidationInInterceptor inInterceptor = new SchemaValidationInInterceptor();
        Assert.assertEquals(Phase.PRE_PROTOCOL, inInterceptor.getPhase());
    }

    @Test
    public void testDoetNietsMetUitBerichten() {
        System.setProperty("nl.bzk.brp.webservice.xsd.validatie", "uit");

        final SchemaValidationInInterceptor inInterceptor = new SchemaValidationInInterceptor();

        inInterceptor.handleMessage(null);
    }

    @Test(expected = Fault.class)
    public void testIOException() throws SOAPException {
        final SoapMessage message = Mockito.mock(SoapMessage.class);
        final SOAPMessage soapMessage = Mockito.mock(SOAPMessage.class);
        Mockito.when(message.getContent(SOAPMessage.class)).thenReturn(soapMessage);
        Mockito.when(soapMessage.getSOAPBody()).thenThrow(new SOAPException("dummy"));
        new SchemaValidationInInterceptor().handleMessage(message);
    }

    @Test
    public void testVerhuisBerichtDatVoldoetAanSchema() throws SOAPException, IOException {
        final String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
                + "                     xmlns:ns=\"http://www.bzk.nl/brp/brp0200\">\n"
                + "   <soapenv:Header/>\n"
                + "   <soapenv:Body>\n"
                + "<ns:bhg_vbaRegistreerVerhuizing xmlns:ns=\"http://www.bzk.nl/brp/brp0200\">\n"
                + "  <ns:stuurgegevens ns:communicatieID=\"stuurgegeven1\">\n"
                + "    <ns:zendendePartij>partij</ns:zendendePartij>\n"
                + "    <ns:zendendeSysteem>zendendeSysteem</ns:zendendeSysteem>\n"
                + "    <ns:referentienummer>12345678-1234-1234-1234-123456789123</ns:referentienummer>\n"
                + "    <ns:tijdstipVerzending>2012-01-01T00:00:00.000</ns:tijdstipVerzending>\n"
                + "  </ns:stuurgegevens>\n"
                + "  <ns:parameters ns:communicatieID=\"string\">\n"
                + "    <ns:verwerkingswijze>Bijhouding</ns:verwerkingswijze>\n"
                + "  </ns:parameters>\n"
                +
                "  <ns:verhuizingBinnengemeentelijk ns:objecttype=\"AdministratieveHandeling\" ns:communicatieID=\"string\">\n"
                + "    <ns:partijCode>00stri</ns:partijCode>\n"
                + "    <ns:acties>\n"
                + "      <ns:registratieAdres ns:objecttype=\"Actie\" ns:communicatieID=\"string\">\n"
                + "        <ns:datumAanvangGeldigheid>2012-01-01</ns:datumAanvangGeldigheid>\n"
                +
                "        <ns:persoon ns:objecttype=\"Persoon\" ns:objectSleutel=\"123456789\" ns:communicatieID=\"string\">\n"
                + "          <ns:adressen>\n"
                + "            <ns:adres ns:objecttype=\"PersoonAdres\" ns:communicatieID=\"string\">\n"
                + "              <ns:soortCode>W</ns:soortCode>\n"
                + "              <ns:redenWijzigingCode>B</ns:redenWijzigingCode>\n"
                + "              <ns:datumAanvangAdreshouding>2012-01-01</ns:datumAanvangAdreshouding>\n"
                + "            </ns:adres>\n"
                + "          </ns:adressen>\n"
                + "        </ns:persoon>\n"
                + "      </ns:registratieAdres>\n"
                + "    </ns:acties>\n"
                + "  </ns:verhuizingBinnengemeentelijk>\n"
                + "</ns:bhg_vbaRegistreerVerhuizing>\n"
                + "   </soapenv:Body>\n"
                + "</soapenv:Envelope>";
        final SoapMessage message = new SoapMessage(new MessageImpl());
        final SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(null, new StringInputStream(xml));

        message.setContent(SOAPMessage.class, soapMessage);
        final SchemaValidationInInterceptor inInterceptor = new SchemaValidationInInterceptor();
        inInterceptor.handleMessage(message);
    }

    /**
     * Geen stuurgegevens zou een schema fout moeten opleveren.
     */
    @Test(expected = Fault.class)
    public void testVerhuisBerichtDatNietVoldoetAanSchema() throws SOAPException, IOException {
        final String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
                + "                     xmlns:ns=\"http://www.bprbzk.nl/BRP/0001\">\n"
                + "   <soapenv:Header/>\n"
                + "   <soapenv:Body>\n"
                +
                "<ns:bhg_vbaRegistreerVerhuizing xmlns:ns=\"http://www.bzk.nl/brp/brp0200\">\n"
                + "  <ns:parameters ns:communicatieID=\"string\">\n"
                + "    <ns:verwerkingswijze>B</ns:verwerkingswijze>\n"
                + "  </ns:parameters>\n"
                + "  <!--You have a CHOICE of the next 2 items at this level-->\n"
                +
                "  <ns:registratieBinnengemeentelijkeVerhuizing ns:objecttype=\"AdministratieveHandeling\" ns:communicatieID=\"string\">\n"
                + "    <ns:partijCode>stri</ns:partijCode>\n"
                + "    <ns:tijdstipOntlening :exact=\"true\">2014-06-09T17:15:04+02:00</ns:tijdstipOntlening>\n"
                + "    <ns:acties>\n"
                + "      <ns:registratieAdres :objecttype=\"Actie\" ns:communicatieID=\"string\">\n"
                + "        <ns:datumAanvangGeldigheid :exact=\"true\">2012-01-01</ns:datumAanvangGeldigheid>\n"
                +
                "        <ns:persoon ns:objecttype=\"Persoon\" ns:objectSleutel=\"123456789\" ns:communicatieID=\"string\">\n"
                + "          <ns:adressen>\n"
                + "            <ns:adres ns:objecttype=\"PersoonAdres\" ns:communicatieID=\"string\">\n"
                + "              <ns:soortCode>W</ns:soortCode>\n"
                + "              <ns:redenWijzigingCode :noValue=\"nietGeautoriseerd\">B</ns:redenWijzigingCode>\n"
                +
                "              <ns:datumAanvangAdreshouding :exact=\"true\">2012-01-01</ns:datumAanvangAdreshouding>\n"
                + "            </ns:adres>\n"
                + "          </ns:adressen>\n"
                + "        </ns:persoon>\n"
                + "      </ns:registratieAdres>\n"
                + "    </ns:acties>\n"
                + "  </ns:bhg_vbaRegistreerVerhuizing>\n"
                + "</ns:MIG_RegistreerVerhuizing_B>\n"
                + "   </soapenv:Body>\n"
                + "</soapenv:Envelope>";
        final SoapMessage message = new SoapMessage(new MessageImpl());
        final SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(null, new StringInputStream(xml));

        message.setContent(SOAPMessage.class, soapMessage);
        final SchemaValidationInInterceptor inInterceptor = new SchemaValidationInInterceptor();
        inInterceptor.handleMessage(message);
    }

}
