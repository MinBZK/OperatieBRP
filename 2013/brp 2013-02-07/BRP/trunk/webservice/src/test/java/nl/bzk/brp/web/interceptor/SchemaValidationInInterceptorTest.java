/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.interceptor;

import java.io.IOException;
import java.util.Set;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import junit.framework.Assert;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.phase.Phase;
import org.apache.tools.ant.filters.StringInputStream;
import org.junit.Ignore;
import org.junit.Test;

public class SchemaValidationInInterceptorTest {

    @Test
    public void testCorrecteInitialisatieInterceptor() {
        SchemaValidationInInterceptor inInterceptor = new SchemaValidationInInterceptor();
        Assert.assertEquals(Phase.PRE_PROTOCOL, inInterceptor.getPhase());
        Set<String> afterInterceptors = inInterceptor.getAfter();
        Assert.assertTrue(afterInterceptors.contains(SAAJInInterceptor.class.getName()));
    }

    @Ignore
    @Test
    public void testVerhuisBerichtDatVoldoetAanSchema() throws SOAPException, IOException {
        String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
            + "                     xmlns:ns=\"http://www.bprbzk.nl/BRP/0001\">\n"
            + "   <soapenv:Header/>\n"
            + "   <soapenv:Body>\n"
            + "      <ns:migratie_Verhuizing_Bijhouding ns:cIDVerzendend=\"1\">\n"
            + "         <ns:stuurgegevens ns:cIDVerzendend=\"a\">\n"
            + "            <ns:organisatie>brp</ns:organisatie>\n"
            + "            <ns:applicatie>brp</ns:applicatie>\n"
            + "            <ns:referentienummer>123</ns:referentienummer>\n"
            + "         </ns:stuurgegevens>\n"
            + "\n"
            + "         <ns:acties>\n"
            + "            <ns:verhuizing ns:cIDVerzendend=\"1\">\n"
            + "               <ns:datumAanvangGeldigheid>5</ns:datumAanvangGeldigheid>\n"
            + "               <ns:tijdstipOntlening>5</ns:tijdstipOntlening>\n"
            + "               <ns:persoon ns:cIDVerzendend=\"1\">\n"
            + "                  <ns:identificatienummers ns:cIDVerzendend=\"1\">\n"
            + "                      <ns:burgerservicenummer>123456789</ns:burgerservicenummer>\n"
            + "                  </ns:identificatienummers>\n"
            + "                  <ns:afgeleidAdministratief ns:cIDVerzendend=\"1\">\n"
            + "                      <ns:tijdstipLaatsteWijziging>20111111111111</ns:tijdstipLaatsteWijziging>\n"
            + "                  </ns:afgeleidAdministratief>\n"
            + "                  <ns:adressen>\n"
            + "                      <ns:adres>\n"
            + "                        <ns:soortCode>W</ns:soortCode>\n"
            + "                        <ns:redenWijzigingCode>P</ns:redenWijzigingCode>\n"
            + "                        <ns:datumAanvangAdreshouding>20111111</ns:datumAanvangAdreshouding>\n"
            + "                        <ns:gemeenteCode>0034</ns:gemeenteCode>\n"
            + "                      </ns:adres>\n"
            + "                  </ns:adressen>\n"
            + "               </ns:persoon>\n"
            + "            </ns:verhuizing>\n"
            + "         </ns:acties>\n"
            + "      </ns:migratie_Verhuizing_Bijhouding>\n"
            + "   </soapenv:Body>\n"
            + "</soapenv:Envelope>";
        Message message = new MessageImpl();
        message.put("SOAPAction", "verhuizing");
        SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(null, new StringInputStream(xml));

        message.setContent(SOAPMessage.class, soapMessage);
        SchemaValidationInInterceptor inInterceptor = new SchemaValidationInInterceptor();
        inInterceptor.handleMessage(message);
    }

    /** Geen stuurgegevens zou een schema fout moeten opleveren. */
    @Test(expected = Fault.class)
    public void testVerhuisBerichtDatNietVoldoetAanSchema() throws SOAPException, IOException {
        String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
            + "                     xmlns:ns=\"http://www.bprbzk.nl/BRP/0001\">\n"
            + "   <soapenv:Header/>\n"
            + "   <soapenv:Body>\n"
            + "      <ns:migratie_Verhuizing_Bijhouding ns:cIDVerzendend=\"1\">\n"
            + "        \n"
            + "         <ns:acties>\n"
            + "            <ns:verhuizing ns:cIDVerzendend=\"1\">\n"
            + "               <ns:datumAanvangGeldigheid>5</ns:datumAanvangGeldigheid>\n"
            + "               <ns:tijdstipOntlening>5</ns:tijdstipOntlening>\n"
            + "    \n"
            + "               <ns:persoon ns:cIDVerzendend=\"1\">\n"
            + "                  <ns:identificatienummers ns:cIDVerzendend=\"1\">\n"
            + "                    \n"
            + "                  </ns:identificatienummers>\n"
            + "                  <ns:afgeleidAdministratief ns:cIDVerzendend=\"1\">\n"
            + "                   \n"
            + "                  </ns:afgeleidAdministratief>\n"
            + "                  <ns:adressen>\n"
            + "                    \n"
            + "                  </ns:adressen>\n"
            + "               </ns:persoon>\n"
            + "            </ns:verhuizing>\n"
            + "         </ns:acties>\n"
            + "      </ns:migratie_Verhuizing_Bijhouding>\n"
            + "   </soapenv:Body>\n"
            + "</soapenv:Envelope>";
        Message message = new MessageImpl();
        message.put("SOAPAction", "verhuizing");
        SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(null, new StringInputStream(xml));

        message.setContent(SOAPMessage.class, soapMessage);
        SchemaValidationInInterceptor inInterceptor = new SchemaValidationInInterceptor();
        inInterceptor.handleMessage(message);
    }

}
