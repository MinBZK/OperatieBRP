/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging.levering;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.binding.bevraging.AbstractVraagBerichtBindingUitIntegratieTest;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.bevraging.levering.GeefDetailsPersoonAntwoordBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.predikaat.HuidigeSituatiePredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.RelatieHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.VerantwoordingTestUtil;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonIdZetter;
import org.custommonkey.xmlunit.XMLUnit;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.xml.sax.SAXException;


/**
 * Binding test voor geef details persoon antwoord berichten.
 */
public class GeefDetailsPersoonAntwoordBindingTest extends
    AbstractVraagBerichtBindingUitIntegratieTest<GeefDetailsPersoonAntwoordBericht>
{

    private static final String TIJDSTIP_VERZENDING = "tijdstipVerzending";
    private static final String T14_35_06_789_01_00 = "2012-03-25T14:35:06.789+01:00";
    private static final String REFERENTIE_NUMMER = "12345678-1234-1234-1234-123456789123";

    @Override
    public final Class<GeefDetailsPersoonAntwoordBericht> getBindingClass() {
        return GeefDetailsPersoonAntwoordBericht.class;
    }

    @BeforeClass
    public static void beforeClass() {
        XMLUnit.setIgnoreWhitespace(true);
    }

    @Test
    public void testOutBindingMetLeegResultaat() throws JiBXException, IOException, SAXException {
        final GeefDetailsPersoonAntwoordBericht response = new GeefDetailsPersoonAntwoordBericht();
        response.setStuurgegevens(maakStuurgegevensVoorAntwoordBericht(REFERENTIE_NUMMER,
                                                                       REFERENTIE_NUMMER));
        response.setResultaat(maakBerichtResultaat(SoortMelding.GEEN));
        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        valideerTegenSchema(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, TIJDSTIP_VERZENDING, T14_35_06_789_01_00);
        final String verwachteWaarde = bouwVerwachteAntwoordBericht("antwoordbericht_geefDetailsPersoon_leeg_resultaat.xml");
        assertXMLEqual(verwachteWaarde, xml);
    }

    @Test
    public void testOutBindingMetMelding() throws JiBXException, IOException, SAXException {
        final Melding melding = new Melding(SoortMelding.INFORMATIE, Regel.ALG0001, "TEST", null, null);

        final GeefDetailsPersoonAntwoordBericht response = new GeefDetailsPersoonAntwoordBericht();
        response.setStuurgegevens(maakStuurgegevensVoorAntwoordBericht(REFERENTIE_NUMMER,
                                                                       REFERENTIE_NUMMER));
        response.setResultaat(maakBerichtResultaat(SoortMelding.INFORMATIE));

        response.setMeldingen(maakBerichtMeldingenBericht(melding));
        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        valideerTegenSchema(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, TIJDSTIP_VERZENDING, T14_35_06_789_01_00);

        final String verwachteWaarde = bouwVerwachteAntwoordBericht("antwoordbericht_geefDetailsPersoon_met_melding.xml");
        assertXMLEqual(verwachteWaarde, xml);

        valideerTegenSchema(xml);
    }

    @Test
    public void testOutBindingMetPersoonInResultaat() throws Exception {
        final GeefDetailsPersoonAntwoordBericht response = new GeefDetailsPersoonAntwoordBericht();
        response.setStuurgegevens(maakStuurgegevensVoorAntwoordBericht(REFERENTIE_NUMMER,
                                                                       REFERENTIE_NUMMER));
        response.setResultaat(maakBerichtResultaat(SoortMelding.GEEN));

        final PersoonHisVolledigImpl antwoordPersoon = maakAntwoordPersoon(true);
        voegKindBetrokkenheidToeVoorAntwoordPersoon(antwoordPersoon, true);
        voegOuderBetrokkenheidToeVoorAntwoordPersoon(antwoordPersoon, true);
        voegPartnerBetrokkenheidToeVoorAntwoordPersoon(antwoordPersoon, true);
        final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(antwoordPersoon, new HuidigeSituatiePredikaat());
        zetObjectSleutels(persoonHisVolledigView);

        for (final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView: persoonHisVolledigView.getBetrokkenheden()) {
            ((RelatieHisVolledigView) betrokkenheidHisVolledigView.getRelatie()).setMagLeveren(true);
        }

        response.voegGevondenPersoonToe(persoonHisVolledigView);

        // Zorg ervoor dat de verantwoording niet wordt getoond:
        for (final AdministratieveHandelingHisVolledigImpl administratieveHandelingHisVolledig : antwoordPersoon.getAdministratieveHandelingen()) {
            administratieveHandelingHisVolledig.setMagGeleverdWorden(false);
        }

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        // Vervang alle tijdstipRegistratie en technischeSleutel waardes met een vaste waarde:
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipRegistratie", "2012-01-01T00:00:00.000+01:00");
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "datumTijdEindeVolgen", "2012-01-01T00:00:00.000+01:00");
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, TIJDSTIP_VERZENDING, "2012-01-01T00:00:00.000+01:00");
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipLaatsteWijziging", "2012-01-01T00:00:00.000+01:00");
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipLaatsteWijzigingGBASystematiek", "2012-01-01T00:00:00.000+01:00");
        // Objectsleutels en voorkomensleutels zijn random, dus even het response aanpassen:
        xml = xml.replaceAll("brp:objectSleutel=\"[0-9]*\"", "brp:objectSleutel=\"X\"");
        xml = xml.replaceAll("brp:voorkomenSleutel=\"[0-9]*\"", "brp:voorkomenSleutel=\"X\"");

        valideerTegenSchema(xml);

        final String verwachteWaarde = bouwVerwachteAntwoordBericht("antwoordbericht_geefDetailsPersoon.xml");

        assertXMLEqual(verwachteWaarde, xml);
    }

    @Test
    public void testOutBindingMetPersoonEnVerantwoordingInResultaat() throws Exception {
        final GeefDetailsPersoonAntwoordBericht response = new GeefDetailsPersoonAntwoordBericht();
        response.setStuurgegevens(maakStuurgegevensVoorAntwoordBericht(REFERENTIE_NUMMER,
                                                                       REFERENTIE_NUMMER));
        response.setResultaat(maakBerichtResultaat(SoortMelding.GEEN));

        final PersoonHisVolledigImpl antwoordPersoon = maakAntwoordPersoon(true);
        voegKindBetrokkenheidToeVoorAntwoordPersoon(antwoordPersoon, true);
        voegOuderBetrokkenheidToeVoorAntwoordPersoon(antwoordPersoon, true);
        voegPartnerBetrokkenheidToeVoorAntwoordPersoon(antwoordPersoon, true);

        final AdministratieveHandelingModel administratieveHandelingModel =
            VerantwoordingTestUtil.bouwAdministratieveHandeling(SoortAdministratieveHandeling.VERHUIZING_NAAR_BUITENLAND,
                                                                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM,
                                                                new OntleningstoelichtingAttribuut("Ontleend aan een feit"), DatumTijdAttribuut.nu());

        VerantwoordingTestUtil.voegGedeblokkeerdeMeldingToeAanHandeling(administratieveHandelingModel, Regel.BRAL0004, 120L);
        VerantwoordingTestUtil.voegGedeblokkeerdeMeldingToeAanHandeling(administratieveHandelingModel, Regel.BRBY0024, 150L);

        final DatumTijdAttribuut tsRegNu = DatumTijdAttribuut.nu();

        final ActieModel actieModel1 =
            VerantwoordingTestUtil.voegActieToeAanHandeling(administratieveHandelingModel,
                SoortActie.BEEINDIGING_VOORNAAM, StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM,
                new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.gisteren()), null, tsRegNu, null,
                1001L);

        final DocumentModel documentModel = VerantwoordingTestUtil.voegDocumentBronToeAanActie(actieModel1, "Een kassa bonnetje", 9824452L);

        final ActieModel actieModel2 =
            VerantwoordingTestUtil.voegActieToeAanHandeling(administratieveHandelingModel,
                SoortActie.REGISTRATIE_VOORNAAM, StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA,
                new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.gisteren()), null, tsRegNu, null,
                2002L);

        final ActieModel actieModel3 =
            VerantwoordingTestUtil.voegActieToeAanHandeling(administratieveHandelingModel, SoortActie.BEEINDIGING_BEHANDELD_ALS_NEDERLANDER,
                                                            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_SGRAVENHAGE,
                                                            new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.gisteren()), null, tsRegNu, null, 3003L);

        VerantwoordingTestUtil.voegRechtsgrondOmschrijvingBronToeAanActie(actieModel3, "Weet ik veel");

        // Let op, we nemen bewust hier nog een actie op gekoppeld aan dezelfde document bron; documentModel hierboven.
        // Deze bron (document) moet in de <bronnen> direct onder de handeling expliciet ÉÉN keer verschijnen:
        final ActieModel actieModel4 =
            VerantwoordingTestUtil.voegActieToeAanHandeling(administratieveHandelingModel,
                SoortActie.BEEINDIGING_OUDERSCHAP, StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_UTRECHT,
                new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.gisteren()), null, tsRegNu, null,
                4004L);

        VerantwoordingTestUtil.voegBestaandDocumentBronToeAanActie(actieModel4, documentModel);

        final List<AdministratieveHandelingModel> administratieveHandelingen = new ArrayList<>();
        administratieveHandelingen.add(administratieveHandelingModel);
        ReflectionTestUtils.setField(antwoordPersoon, "administratieveHandelingen", VerantwoordingTestUtil.converteer(administratieveHandelingen));

        final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(antwoordPersoon, new HuidigeSituatiePredikaat());
        zetObjectSleutels(persoonHisVolledigView);

        for (final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView: persoonHisVolledigView.getBetrokkenheden()) {
            ((RelatieHisVolledigView) betrokkenheidHisVolledigView.getRelatie()).setMagLeveren(true);
        }

        TestPersoonIdZetter.zetIds(antwoordPersoon);

        response.voegGevondenPersoonToe(persoonHisVolledigView);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        // Vervang alle tijdstipRegistratie en technischeSleutel waardes met een vaste waarde:
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipRegistratie", "2012-01-01T00:00:00.000+01:00");
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipLaatsteWijziging", "2012-01-01T00:00:00.000+01:00");
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipLaatsteWijzigingGBASystematiek", "2012-01-01T00:00:00.000+01:00");
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "datumEindeVolgen", "2012-01-01");
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, TIJDSTIP_VERZENDING, T14_35_06_789_01_00);
        // Objectsleutels en voorkomensleutels zijn random, dus even het response aanpassen:
        xml = xml.replaceAll("brp:objectSleutel=\"[0-9]*\"", "brp:objectSleutel=\"X\"");
        xml = xml.replaceAll("brp:voorkomenSleutel=\"[0-9]*\"", "brp:voorkomenSleutel=\"X\"");
        xml = xml.replaceAll("brp:communicatieID=\"[a-z]*[-]*[0-9]*\"", "brp:communicatieID=\"X\"");
        xml = xml.replaceAll("brp:referentieID=\"[a-z]*[-]*[0-9]*\"", "brp:referentieID=\"X\"");

        valideerTegenSchema(xml);
        final String verwachteWaarde = bouwVerwachteAntwoordBericht("antwoordbericht_geefDetailsPersoon_verantwoording.xml");

        assertXMLEqual(verwachteWaarde, xml);
    }

    @Override
    protected String getSchemaBestand() {
        return getSchemaUtils().getXsdLeveringBevragingBerichten();
    }
}
