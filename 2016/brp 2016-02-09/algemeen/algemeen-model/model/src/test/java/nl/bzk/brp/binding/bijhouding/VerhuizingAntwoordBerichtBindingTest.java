/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bijhouding;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.binding.AbstractBindingUitIntegratieTest;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentIdentificatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingBijgehoudenDocumentBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.DocumentBericht;
import nl.bzk.brp.model.bericht.kern.DocumentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVerhuizingBinnengemeentelijkBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bijhouding.RegistreerVerhuizingAntwoordBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.custommonkey.xmlunit.XMLUnit;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.xml.sax.SAXException;


/**
 * Unit test class voor de binding van de {@link nl.bzk.brp.model.bijhouding.RegistreerVerhuizingAntwoordBericht} class.
 */
public class VerhuizingAntwoordBerichtBindingTest extends
        AbstractBindingUitIntegratieTest<RegistreerVerhuizingAntwoordBericht>
{

    private static final String RESULTAAT_NODE_NAAM = "bhg_vbaRegistreerVerhuizing_R";

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public Class<RegistreerVerhuizingAntwoordBericht> getBindingClass() {
        return RegistreerVerhuizingAntwoordBericht.class;
    }

    @BeforeClass
    public static void beforeClass() {
        XMLUnit.setIgnoreWhitespace(true);
    }

    @Test
    public void testBindingMetGoedResultaatEnDocument() throws JiBXException, IOException, SAXException {
        final RegistreerVerhuizingAntwoordBericht antwoord = new RegistreerVerhuizingAntwoordBericht();
        antwoord.setStandaard(new BerichtStandaardGroepBericht());
        antwoord.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingBinnengemeentelijkBericht());
        antwoord.getAdministratieveHandeling().setPartij(StatischeObjecttypeBuilder.bouwPartij(1, "1"));
        antwoord.getAdministratieveHandeling().setTijdstipRegistratie(DatumTijdAttribuut.nu());

        final List<AdministratieveHandelingBijgehoudenDocumentBericht> bijgehoudenDocumenten = new ArrayList<>();
        final AdministratieveHandelingBijgehoudenDocumentBericht admHandelingDocumentbericht =
                new AdministratieveHandelingBijgehoudenDocumentBericht();
        admHandelingDocumentbericht.setAdministratieveHandeling(antwoord.getAdministratieveHandeling());
        final DocumentBericht documentBericht = new DocumentBericht();
        documentBericht.setCommunicatieID("document1");
        documentBericht.setReferentieID("123");
        documentBericht.setObjectSleutel("987");
        documentBericht.setSoortNaam("Mededeling Minister inzake verblijfsrecht");
        final DocumentStandaardGroepBericht standaard = new DocumentStandaardGroepBericht();
        standaard.setPartijCode("036101");
        standaard.setIdentificatie(new DocumentIdentificatieAttribuut("1a2b3c"));
        documentBericht.setStandaard(standaard);
        admHandelingDocumentbericht.setDocument(documentBericht);
        bijgehoudenDocumenten.add(admHandelingDocumentbericht);
        antwoord.getAdministratieveHandeling().setBijgehoudenDocumenten(bijgehoudenDocumenten);
        antwoord.setParameters(maakParametersVoorAntwoordBericht(Verwerkingswijze.BIJHOUDING));
        antwoord.setStuurgegevens(maakStuurgegevensVoorAntwoordBericht("12345678-1234-1234-1234-123456789123",
                "12345678-1234-1234-1234-123456789123"));
        antwoord.setResultaat(maakResultaatVoorAntwoordBericht(SoortMelding.GEEN, Verwerkingsresultaat.GESLAAGD,
                Bijhoudingsresultaat.VERWERKT));

        String xml = marshalObject(antwoord);
        Assert.assertNotNull(xml);

        LOGGER.info("<><><><><><><><><><><><><>");
        LOGGER.info(xml);
        LOGGER.info("<><><><><><><><><><><><><>");

        final String berichtResultaatTemplate =
                getBerichtResultaatTemplate(RESULTAAT_NODE_NAAM, "Geslaagd", "Geen", null, null,
                        Arrays.asList(documentBericht), "2013-02-13T14:42:01.680", "verhuizingBinnengemeentelijk",
                        true);

        LOGGER.info("<><><><><><><><><><><><><>");
        LOGGER.info(berichtResultaatTemplate);
        LOGGER.info("<><><><><><><><><><><><><>");

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipVerzending", "2012-03-25T14:35:06.789");
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipRegistratie", "2013-02-13T14:42:01.680");

        assertXMLEqual(berichtResultaatTemplate, xml);
        valideerTegenSchema(xml);
    }

    @Test
    public void testBindingMetGoedResultaatEnGeenMeldingen() throws JiBXException, IOException, SAXException {
        final RegistreerVerhuizingAntwoordBericht antwoord = new RegistreerVerhuizingAntwoordBericht();
        antwoord.setStandaard(new BerichtStandaardGroepBericht());
        antwoord.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingBinnengemeentelijkBericht());
        antwoord.getAdministratieveHandeling().setPartij(StatischeObjecttypeBuilder.bouwPartij(1, "1"));
        antwoord.getAdministratieveHandeling().setTijdstipRegistratie(DatumTijdAttribuut.nu());
        antwoord.setParameters(maakParametersVoorAntwoordBericht(Verwerkingswijze.BIJHOUDING));
        antwoord.setStuurgegevens(maakStuurgegevensVoorAntwoordBericht("12345678-1234-1234-1234-123456789123",
                "12345678-1234-1234-1234-123456789123"));
        antwoord.setResultaat(maakResultaatVoorAntwoordBericht(SoortMelding.GEEN, Verwerkingsresultaat.GESLAAGD,
                Bijhoudingsresultaat.VERWERKT));

        String xml = marshalObject(antwoord);
        Assert.assertNotNull(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipVerzending", "2012-03-25T14:35:06.789");
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipRegistratie", "2014-03-10T08:58:40.181");
        assertXMLEqual(
            getBerichtResultaatTemplate(RESULTAAT_NODE_NAAM, "Geslaagd", "Geen", null, null, null,
                "2014-03-10T08:58:40.181", "verhuizingBinnengemeentelijk", true), xml);
        valideerTegenSchema(xml);
    }

    @Test
    public void testBindingMetGoedResultaatEnEenMelding() throws JiBXException, IOException, SAXException {
        final BerichtIdentificeerbaar identificeerbaar = new PersoonBericht();
        identificeerbaar.setCommunicatieID("communicatieId");
        final Melding[] meldingen =
                { new Melding(SoortMelding.INFORMATIE, Regel.ALG0001, "Test omschrijving", identificeerbaar,
                        "attribuutNaam") };

        final RegistreerVerhuizingAntwoordBericht antwoord = new RegistreerVerhuizingAntwoordBericht();
        antwoord.setStandaard(new BerichtStandaardGroepBericht());
        antwoord.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingBinnengemeentelijkBericht());
        antwoord.getAdministratieveHandeling().setPartij(StatischeObjecttypeBuilder.bouwPartij(1, "1"));
        antwoord.getAdministratieveHandeling().setTijdstipRegistratie(DatumTijdAttribuut.nu());
        antwoord.setParameters(maakParametersVoorAntwoordBericht(Verwerkingswijze.BIJHOUDING));
        antwoord.setStuurgegevens(maakStuurgegevensVoorAntwoordBericht("12345678-1234-1234-1234-123456789123",
                "12345678-1234-1234-1234-123456789123"));
        antwoord.setResultaat(maakResultaatVoorAntwoordBericht(SoortMelding.INFORMATIE, Verwerkingsresultaat.GESLAAGD,
                Bijhoudingsresultaat.VERWERKT));

        antwoord.setMeldingen(maakBerichtMeldingenBericht(meldingen));
        String xml = marshalObject(antwoord);
        Assert.assertNotNull(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipVerzending", "2012-03-25T14:35:06.789");
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipRegistratie", "2014-03-10T08:58:40.181");
        assertXMLEqual(
            getBerichtResultaatTemplate(RESULTAAT_NODE_NAAM, "Geslaagd", "Informatie", meldingen, null, null,
                "2014-03-10T08:58:40.181", "verhuizingBinnengemeentelijk", true), xml);
        valideerTegenSchema(xml);
    }

    @Test
    public void testBindingMetFoutResultaatEnMeerdereMeldingen() throws JiBXException, IOException, SAXException {
        final BerichtIdentificeerbaar identificeerbaar = new PersoonBericht();
        identificeerbaar.setCommunicatieID("communicatieId");

        final Melding[] meldingen =
                {
                        new Melding(SoortMelding.FOUT, Regel.ALG0001, "Fout opgetreden", identificeerbaar,
                                "attribuutnaam"),
                        new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.ALG0001, "Nog een fout opgetreden",
                                identificeerbaar,
                                "attribuutnaam") };

        final RegistreerVerhuizingAntwoordBericht antwoord = new RegistreerVerhuizingAntwoordBericht();
        antwoord.setStandaard(new BerichtStandaardGroepBericht());
        antwoord.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingBinnengemeentelijkBericht());
        antwoord.getAdministratieveHandeling().setPartij(StatischeObjecttypeBuilder.bouwPartij(1, "1"));
        antwoord.getAdministratieveHandeling().setTijdstipRegistratie(DatumTijdAttribuut.nu());
        antwoord.setParameters(maakParametersVoorAntwoordBericht(null));
        antwoord.setStuurgegevens(maakStuurgegevensVoorAntwoordBericht("12345678-1234-1234-1234-123456789123",
                "12345678-1234-1234-1234-123456789123"));
        antwoord.setResultaat(maakResultaatVoorAntwoordBericht(SoortMelding.FOUT, Verwerkingsresultaat.FOUTIEF, null));
        antwoord.setMeldingen(maakBerichtMeldingenBericht(meldingen));
        String xml = marshalObject(antwoord);
        Assert.assertNotNull(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipVerzending", "2012-03-25T14:35:06.789");
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipRegistratie", "2014-03-10T08:58:40.181");
        assertXMLEqual(
            getBerichtResultaatTemplate(RESULTAAT_NODE_NAAM, "Foutief", "Fout", meldingen, null, null,
                "2014-03-10T08:58:40.181", "verhuizingBinnengemeentelijk", false), xml);
        valideerTegenSchema(xml);
    }

    @Override
    public String getSchemaBestand() {
        return getSchemaUtils().getXsdVerblijfAdresBerichten();
    }
}
