/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp.impl;

import static org.junit.Assert.assertEquals;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.SynchronisatieSignaalType;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

public class SynchronisatieSignaalBerichtTest {
    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testParse() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(SynchronisatieSignaalBerichtTest.class
                        .getResourceAsStream("synchronisatieSignaalBericht.xml"));
        final SynchronisatieSignaalBericht bericht =
                (SynchronisatieSignaalBericht) factory.getBericht(berichtOrigineel);

        assertEquals("SynchronisatieSignaal", bericht.getBerichtType());
        assertEquals("123456", bericht.getANummer());
        assertEquals("uc201", bericht.getStartCyclus());
    }

    @Test
    public void testFormat() throws Exception {
        final SynchronisatieSignaalType synchronisatieSignaalType = new SynchronisatieSignaalType();
        synchronisatieSignaalType.setANummer("123456");
        final SynchronisatieSignaalBericht antwoordBericht =
                new SynchronisatieSignaalBericht(synchronisatieSignaalType);
        final String geformat = antwoordBericht.format();
        // controleer of geformat bericht weer geparsed kan worden
        final SynchronisatieSignaalBericht format = (SynchronisatieSignaalBericht) factory.getBericht(geformat);
        assertEquals("SynchronisatieSignaal", format.getBerichtType());

        format.setMessageId(antwoordBericht.getMessageId());

        Assert.assertEquals(antwoordBericht, format);
    }

    @Test
    public void testEquals() throws Exception {
        final SynchronisatieSignaalType synchronisatieSignaalType = new SynchronisatieSignaalType();
        synchronisatieSignaalType.setANummer("123456");
        final SynchronisatieSignaalBericht synchronisatieSignaalBerichtOrigineel =
                new SynchronisatieSignaalBericht(synchronisatieSignaalType);
        final SynchronisatieSignaalBericht synchronisatieSignaalBerichtKopie =
                new SynchronisatieSignaalBericht(synchronisatieSignaalType);
        final SynchronisatieSignaalBericht synchronisatieSignaalBerichtObjectKopie =
                synchronisatieSignaalBerichtOrigineel;
        final SynchronisatieSignaalBericht synchronisatieSignaalBericht = new SynchronisatieSignaalBericht();

        synchronisatieSignaalBerichtKopie.setMessageId(synchronisatieSignaalBerichtOrigineel.getMessageId());
        synchronisatieSignaalBerichtKopie.setCorrelationId(synchronisatieSignaalBerichtOrigineel.getCorrelationId());

        assertEquals(true, synchronisatieSignaalBerichtObjectKopie.equals(synchronisatieSignaalBerichtOrigineel));
        assertEquals(false, synchronisatieSignaalBerichtOrigineel.equals(synchronisatieSignaalBericht));
        assertEquals(true, synchronisatieSignaalBerichtKopie.equals(synchronisatieSignaalBerichtOrigineel));
        assertEquals(synchronisatieSignaalBerichtObjectKopie.hashCode(),
                synchronisatieSignaalBerichtOrigineel.hashCode());
        assertEquals(synchronisatieSignaalBerichtKopie.hashCode(), synchronisatieSignaalBerichtOrigineel.hashCode());
        assertEquals(synchronisatieSignaalBerichtKopie.toString(), synchronisatieSignaalBerichtOrigineel.toString());
    }

    @Test(expected = BerichtInhoudException.class)
    public void testParseException() throws Exception {

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        final SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        // maak een resolver aan zodat alle gerefereerde xsd's ook gevonden kunnen worden.
        schemaFactory.setResourceResolver(new LSResourceResolver());

        factory.setSchema(schemaFactory.newSchema(new Source[] { new StreamSource(BrpBerichtFactory.class
                .getResourceAsStream("/xsd/BRP_Berichten.xsd")), }));

        final Document document =
                factory.newDocumentBuilder().parse(
                        SynchronisatieSignaalBerichtTest.class
                                .getResourceAsStream("synchronisatieSignaalBerichtSyntaxExceptionBericht.xml"));

        final SynchronisatieSignaalBericht synchronisatieSignaalBericht = new SynchronisatieSignaalBericht();

        try {
            synchronisatieSignaalBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final SynchronisatieSignaalBericht synchronisatieSignaalBericht = new SynchronisatieSignaalBericht();

        final String ANUMMER = "123456";
        synchronisatieSignaalBericht.setANummer(ANUMMER);

        assertEquals(ANUMMER, synchronisatieSignaalBericht.getANummer());
    }

    @Test
    public void testBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(SynchronisatieSignaalBerichtTest.class
                        .getResourceAsStream("synchronisatieSignaalBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }

}
