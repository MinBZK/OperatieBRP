/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.utils.SchemaUtils;
import nl.bzk.brp.utils.XmlUtils;
import nl.bzk.brp.utils.junit.OverslaanBijInMemoryDatabase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@Category(OverslaanBijInMemoryDatabase.class)
public class BijhoudingBevragingKandidaatVaderIntegrationTest extends AbstractBevragingIntegrationTest {
    private static final String BRP_RESULTAAT_BRP_VERWERKING = "//brp:resultaat/brp:verwerking";
    private static final String      BRP_MELDINGEN_BRP_MELDING_BRP_SOORT_NAAM = "//brp:meldingen/brp:melding/brp:soortNaam";
    private static final String      BRP_MELDINGEN_BRP_MELDING_BRP_REGEL_CODE = "//brp:meldingen/brp:melding/brp:regelCode";
    private static final String      KANDIDAAT_VADER_KAN_NIET_WORDEN_BEPAALD  = "Kandidaat-vader kan niet worden bepaald.";
    private static final String      BRP_MELDINGEN_BRP_MELDING_BRP_MELDING    = "//brp:meldingen/brp:melding/brp:melding";
    public static final String PARTIJ_1_TRANSPORTEUR = "000101";
    public static final String PARTIJ_1_ONDERTEKENAAR = "000101";
    private final        SchemaUtils schemaUtils                              = new SchemaUtils();

    protected String getSchemaBestand() {
        return schemaUtils.getXsdBijhoudingBevragingBerichten();
    }

    @Test
    public final void testBepaalKandidaatVaderMoederOnbekend() throws Exception {
        final Node antwoord = verzendBerichtNaarService("bevragen_VraagKandidaatVader_MoederOnbekend.xml", PARTIJ_1_TRANSPORTEUR, PARTIJ_1_ONDERTEKENAAR);

        final String xml = XmlUtils.toXmlString(antwoord);
        final Document document = XmlUtils.bouwDocument(xml);

        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(), XmlUtils
            .getNodeWaarde(BRP_RESULTAAT_BRP_VERWERKING,
                document));
        // we verwachten dat we een INFO met de melding ALG0001 (== algemene fout).
        Assert.assertEquals(
                SoortMelding.INFORMATIE.getNaam(), XmlUtils.getNodeWaarde(BRP_MELDINGEN_BRP_MELDING_BRP_SOORT_NAAM,
                document));
        Assert.assertEquals(Regel.BRPUC50110.name(), XmlUtils.getNodeWaarde(BRP_MELDINGEN_BRP_MELDING_BRP_REGEL_CODE,
            document));

        Assert.assertEquals(KANDIDAAT_VADER_KAN_NIET_WORDEN_BEPAALD, XmlUtils.getNodeWaarde(BRP_MELDINGEN_BRP_MELDING_BRP_MELDING,
            document));
        // schema validatie
        XmlUtils.valideerOutputTegenSchema(XmlUtils.toXmlString(antwoord.getFirstChild()), getSchemaBestand());
    }

    @Test
    public final void testBepaalKandidaatVaderMoederIsGeenVrouw() throws Exception {
        final Node antwoord = verzendBerichtNaarService("bevragen_VraagKandidaatVader_MoederIsGeenVrouw.xml", PARTIJ_1_TRANSPORTEUR, PARTIJ_1_ONDERTEKENAAR);

        final String xml = XmlUtils.toXmlString(antwoord);
        final Document document = XmlUtils.bouwDocument(xml);

        Assert.assertEquals(Verwerkingsresultaat.FOUTIEF.getNaam(), XmlUtils
                .getNodeWaarde(BRP_RESULTAAT_BRP_VERWERKING,
                    document));
        // we verwachten dat we een INFO met de melding ALG0001 (== algemene fout).
        Assert.assertEquals(
                SoortMelding.FOUT.getNaam(), XmlUtils.getNodeWaarde(BRP_MELDINGEN_BRP_MELDING_BRP_SOORT_NAAM,
                document));
        Assert.assertEquals(Regel.ALG0001.name(), XmlUtils.getNodeWaarde(BRP_MELDINGEN_BRP_MELDING_BRP_REGEL_CODE,
            document));
        Assert.assertEquals("De persoon is niet van het vrouwelijk geslacht.", XmlUtils
                .getNodeWaarde(BRP_MELDINGEN_BRP_MELDING_BRP_MELDING,
                    document));
        // schema validatie
        XmlUtils.valideerOutputTegenSchema(XmlUtils.toXmlString(antwoord.getFirstChild()), getSchemaBestand());
    }

    @Test
    public void testBepaalKandidaatVader295157537() throws Exception {
        final Node antwoord = verzendBerichtNaarService("bevragen_VraagKandidaatVader234567891.xml", PARTIJ_1_TRANSPORTEUR, PARTIJ_1_ONDERTEKENAAR);
        final String xml = XmlUtils.toXmlString(antwoord);

        final Document document = XmlUtils.bouwDocument(xml);
        Assert.assertTrue("bevragen_VraagKandidaatVader_Antwoord root node bestaat niet",
                XmlUtils.isNodeAanwezig("//brp:bhg_bvgBepaalKandidaatVader_R", document));
        Assert.assertTrue("resultaat node bestaat niet", XmlUtils.isNodeAanwezig("//brp:resultaat", document));
        // Geslaagd, namelijk melding dat er geen resultaten zijn (is niveau informatie).
        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD.getNaam(), XmlUtils
                .getNodeWaarde(BRP_RESULTAAT_BRP_VERWERKING,
                    document));
        // bsn is onbekend in de database.
        // schema validatie
        XmlUtils.valideerOutputTegenSchema(XmlUtils.toXmlString(antwoord.getFirstChild()), getSchemaBestand());
    }

    @Test
    public final void testBepaalKandidaatVaderOngetrouwd() throws Exception {
        final Node antwoord = verzendBerichtNaarService("bevragen_VraagKandidaatVader089444917.xml", PARTIJ_1_TRANSPORTEUR, PARTIJ_1_ONDERTEKENAAR);
        final String xml = XmlUtils.toXmlString(antwoord);

        final Document document = XmlUtils.bouwDocument(xml);
        Assert.assertEquals(KANDIDAAT_VADER_KAN_NIET_WORDEN_BEPAALD, XmlUtils
                .getNodeWaarde(BRP_MELDINGEN_BRP_MELDING_BRP_MELDING,
                    document));
        // schema validatie
        XmlUtils.valideerOutputTegenSchema(XmlUtils.toXmlString(antwoord.getFirstChild()), getSchemaBestand());
    }


    @Test
    public final void testBepaalKandidaatVaderNietIngezetene() throws Exception {
        // de vrouw met BSN =224302450 is 'getrouwd' met een niet ingezetene.
        // Deze functionaliteit moet nog geimplementeerd worden
        final Node antwoord = verzendBerichtNaarService("bevragen_VraagKandidaatVaderNietIngezetene.xml", PARTIJ_1_TRANSPORTEUR, PARTIJ_1_ONDERTEKENAAR);
        XmlUtils.toXmlString(antwoord);
        // schema validatie
        XmlUtils.valideerOutputTegenSchema(XmlUtils.toXmlString(antwoord.getFirstChild()), getSchemaBestand());
    }


    @Test
    public final void testBepaalKandidaatVaderOverledene() throws Exception {
        // de vrouw met BSN =224302450 is 'getrouwd' met vader die enkele weken geleden is overleden.
        // Deze functionaliteit moet nog geimplementeerd worden
        final Node antwoord = verzendBerichtNaarService("bevragen_VraagKandidaatVaderOverledene.xml", PARTIJ_1_TRANSPORTEUR, PARTIJ_1_ONDERTEKENAAR);
        // schema validatie
        XmlUtils.valideerOutputTegenSchema(XmlUtils.toXmlString(antwoord.getFirstChild()), getSchemaBestand());
    }
}
