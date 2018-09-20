/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.integratie;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.bericht.ber.MeldingBericht;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.MutatieBericht;
import nl.bzk.brp.model.levering.VolledigBericht;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.XpathEngine;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 */
public class SynchronisatieBerichtBindingIntegratieTest extends AbstractSynchronisatieBerichtIntegratieTest {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String FOUTMELDING_VOLDOET_NIET_AAN_XSD = "De XML voldoet *niet* aan het Schema";

    @Inject
    private BlobifierService blobifierService;

    @Test
    public final void bindingMutatieBericht() throws ExpressieExceptie {
        // given
        final List<PersoonHisVolledigView> bijgehoudenPersonen = getTestPersonen();

        final MutatieBericht kennisgevingBericht =
            (MutatieBericht) maakLeveringBericht(bijgehoudenPersonen, Arrays.asList(Populatie.BINNEN, Populatie.BINNEN));

        zetAlleAttributenOpMagGeleverdWorden(kennisgevingBericht);

        // when
        final String xmlString = bouwXmlString(kennisgevingBericht);

        // then
        LOGGER.debug(xmlString);
        assertNotNull(xmlString);
        assertTrue(FOUTMELDING_VOLDOET_NIET_AAN_XSD, isValid(xmlString));

        try {
            // then: geen meldingen in de XML
            final Document document = XMLUnit.buildControlDocument(xmlString);
            final XpathEngine engine = geefBrpXpathEngine();

            final NodeList nodes = engine.getMatchingNodes("//brp:meldingen", document);
            assertThat(nodes.getLength(), is(0));
        } catch (final XpathException | SAXException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test(expected = IllegalStateException.class)
    public final void testBerichtInvalideZonderLeveringsautorisatieNaamParameter() throws ExpressieExceptie {
        // given
        final List<PersoonHisVolledigView> bijgehoudenPersonen = getTestPersonen();

        final MutatieBericht kennisgevingBericht =
            (MutatieBericht) maakLeveringBericht(bijgehoudenPersonen, Arrays.asList(Populatie.BINNEN, Populatie.BINNEN));

        zetAlleAttributenOpMagGeleverdWorden(kennisgevingBericht);
        kennisgevingBericht.setBerichtParameters(null);

        // when
        bouwXmlString(kennisgevingBericht);
    }

    @Test
    public final void testBerichtMetMeerdereMeldingenIsValide() throws ExpressieExceptie {
        // given
        final List<PersoonHisVolledigView> bijgehoudenPersonen = getTestPersonen();

        final MutatieBericht kennisgevingBericht = (MutatieBericht) maakLeveringBericht(bijgehoudenPersonen,
                                                                                        Arrays.asList(Populatie.BINNEN, Populatie.BINNEN));

        zetAlleAttributenOpMagGeleverdWorden(kennisgevingBericht);

        MeldingBericht melding = new MeldingBericht();
        melding.setRegel(new RegelAttribuut(Regel.BRLV0027));
        melding.setSoort(new SoortMeldingAttribuut(SoortMelding.WAARSCHUWING));
        melding.setMelding(new MeldingtekstAttribuut(Regel.BRLV0027.getOmschrijving()));
        melding.setReferentieID("foo-ref-0001");
        kennisgevingBericht.addMelding(melding);

        melding = new MeldingBericht();
        melding.setRegel(new RegelAttribuut(Regel.BRLV0028));
        melding.setSoort(new SoortMeldingAttribuut(SoortMelding.WAARSCHUWING));
        melding.setMelding(new MeldingtekstAttribuut(Regel.BRLV0028.getOmschrijving()));
        kennisgevingBericht.addMelding(melding);

        // when
        final String xmlString = bouwXmlString(kennisgevingBericht);

        // then: de xml string is gemaakt, en volgens het XSD valide
        assertNotNull(xmlString);
        assertTrue(FOUTMELDING_VOLDOET_NIET_AAN_XSD, isValid(xmlString));

        try {
            // then: 2 meldingen in de XML
            LOGGER.debug(xmlString);

            final Document document = XMLUnit.buildControlDocument(xmlString);
            final XpathEngine engine = geefBrpXpathEngine();

            NodeList nodes = engine.getMatchingNodes("//@brp:referentieID", document);
            assertThat(nodes.getLength(), is(1));

            nodes = engine.getMatchingNodes("//brp:meldingen/brp:melding", document);
            assertThat(nodes.getLength(), is(2));
        } catch (final XpathException | SAXException | IOException e) {
            throw new IllegalStateException(e);
        }
    }


    @Test
    public final void personenZijnGesorteerdInHetKennisgevingBericht() throws ExpressieExceptie {
        // given: bijgehouden personen als "ingelezen" blob
        final List<PersoonHisVolledigView> bijgehoudenPersonenVanuitBlob = getTestPersonen(47, 46);

        // when
        final MutatieBericht kennisgevingBerichtVanuitBlob =
            (MutatieBericht) maakLeveringBericht(bijgehoudenPersonenVanuitBlob, Arrays.asList(Populatie.BINNEN, Populatie.BINNEN));
        zetAlleAttributenOpMagGeleverdWorden(kennisgevingBerichtVanuitBlob);

        // then: de personen zijn gesorteerd in het Java object kennisgevingbericht
        assertThat(kennisgevingBerichtVanuitBlob.getAdministratieveHandeling().getBijgehoudenPersonen().get(0).getID(), is(46));
        assertThat(kennisgevingBerichtVanuitBlob.getAdministratieveHandeling().getBijgehoudenPersonen().get(1).getID(), is(47));
    }

    @Test
    public final void bindingVanuitBlobGeeftHetzelfdeResultaatAlsVanuitEntity() {

        try {
            // given: bijgehouden personen vanuit ingelezen blob
            final List<PersoonHisVolledigView> bijgehoudenPersonenVanuitBlob = getTestPersonen();
            final MutatieBericht kennisgevingBerichtVanuitBlob =
                (MutatieBericht) maakLeveringBericht(bijgehoudenPersonenVanuitBlob, Arrays.asList(Populatie.BINNEN, Populatie.BINNEN));

            zetAlleAttributenOpMagGeleverdWorden(kennisgevingBerichtVanuitBlob);

            verwijderBlobsUitDatabase();

            // given: bijgehouden personen vanuit blob in het geheugen
            final List<PersoonHisVolledigView> bijgehoudenPersonenVanuitBlobInGeheugen = getTestPersonen();
            final MutatieBericht kennisgevingBerichtVanuitBlobInGeheugen =
                (MutatieBericht) maakLeveringBericht(bijgehoudenPersonenVanuitBlobInGeheugen, Arrays.asList(Populatie.BINNEN, Populatie.BINNEN));

            zetAlleAttributenOpMagGeleverdWorden(kennisgevingBerichtVanuitBlobInGeheugen);

            // when
            final String xmlStringVanuitBlobInGeheugen = bouwXmlString(kennisgevingBerichtVanuitBlobInGeheugen);
            final String xmlStringVanuitBlob = bouwXmlString(kennisgevingBerichtVanuitBlob);

            // then: de xml string is gemaakt, en volgens het XSD valide
            assertNotNull(xmlStringVanuitBlobInGeheugen);
            assertTrue("De XML (van Blob) voldoet *niet* aan het Schema", isValid(xmlStringVanuitBlobInGeheugen));
            assertTrue("De XML (van Entity) voldoet *niet* aan het Schema", isValid(xmlStringVanuitBlob));

            // then: de xmlstring van een "blob personen"-bericht is gelijk aan de xmlstring van een "database personen"-bericht
            final Diff diff = new Diff(xmlStringVanuitBlob, xmlStringVanuitBlobInGeheugen);
            final DetailedDiff detailedDiff = new DetailedDiff(diff);

            assertThat(detailedDiff.getAllDifferences().size(), is(2));

            final Difference difference = (Difference) detailedDiff.getAllDifferences().get(0);
            assertThat(difference.getTestNodeDetail().getXpathLocation(), equalTo(
                "/lvg_synVerwerkPersoon[1]/stuurgegevens[1]/referentienummer[1]/text()[1]"));

            final Difference difference2 = (Difference) detailedDiff.getAllDifferences().get(1);
            assertThat(difference2.getTestNodeDetail().getXpathLocation(), equalTo(
                "/lvg_synVerwerkPersoon[1]/stuurgegevens[1]/tijdstipVerzending[1]/text()[1]"));

            assertTrue(xmlStringVanuitBlobInGeheugen.length() == xmlStringVanuitBlob.length());
        } catch (final SQLException | SAXException | IOException e) {
            throw new IllegalStateException(e);
        } catch (final ExpressieExceptie expressieExceptie) {
            expressieExceptie.printStackTrace();
        }
    }

    @Test
    public final void bindingVanuitBlobNaarXml() throws ExpressieExceptie {
        // given: bijgehouden personen als "ingelezen" blob
        final List<PersoonHisVolledigView> bijgehoudenPersonenVanuitBlob = getTestPersonen();

        final MutatieBericht kennisgevingBerichtVanuitBlob =
            (MutatieBericht) maakLeveringBericht(bijgehoudenPersonenVanuitBlob, Arrays.asList(Populatie.BINNEN, Populatie.BINNEN));

        zetAlleAttributenOpMagGeleverdWorden(kennisgevingBerichtVanuitBlob);

        // when
        final String xmlString = bouwXmlString(kennisgevingBerichtVanuitBlob);
        LOGGER.debug(xmlString);

        // then: de xml string is gemaakt, en volgens het XSD valide
        assertNotNull(xmlString);
        assertTrue(FOUTMELDING_VOLDOET_NIET_AAN_XSD, isValid(xmlString));

        try {
            // then: personen staan in de juiste volgorde in het xml bericht
            final Document document = XMLUnit.buildControlDocument(xmlString);
            final XpathEngine engine = geefBrpXpathEngine();

            String value = engine.evaluate("//brp:persoon[1]/brp:identificatienummers/brp:burgerservicenummer/text()", document);
            assertThat(value, is("302533928"));

            value = engine.evaluate("//brp:persoon[2]/brp:identificatienummers/brp:burgerservicenummer/text()", document);
            assertThat(value, is("265234761"));
        } catch (final XpathException | SAXException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public final void bindingVolledigBericht() throws ExpressieExceptie {
        // given
        final List<PersoonHisVolledigView> bijgehoudenPersonen = new ArrayList<>();
        final PersoonHisVolledig persoonHisVolledig = blobifierService.leesBlob(1);

        bijgehoudenPersonen.add(new PersoonHisVolledigView(persoonHisVolledig, null));

        final VolledigBericht volledigBericht = (VolledigBericht) maakLeveringBericht(bijgehoudenPersonen, Collections.singletonList(Populatie.BETREEDT));
        zetAlleAttributenOpMagGeleverdWorden(volledigBericht);

        // when
        final String xmlString = bouwXmlString(volledigBericht);

        // then
        LOGGER.debug(xmlString);
        assertNotNull(xmlString);
        assertTrue(FOUTMELDING_VOLDOET_NIET_AAN_XSD, isValid(xmlString));
    }

    @Test
    public final void testBerichtZonderAttributenDieGeleverdMogenWorden() {
        // given
        final List<PersoonHisVolledigView> bijgehoudenPersonen = getTestPersonen();

        final MutatieBericht kennisgevingBericht =
            (MutatieBericht) maakLeveringBericht(bijgehoudenPersonen, Arrays.asList(Populatie.BINNEN, Populatie.BINNEN));

        final String xmlString = bouwXmlString(kennisgevingBericht);

        assertNotNull(xmlString);
        assertTrue(FOUTMELDING_VOLDOET_NIET_AAN_XSD, isValid(xmlString));
    }

    @Test
    public final void betrokkenenHebbenGeenAfgeleidAdministratief() throws ExpressieExceptie {
        // given
        final List<PersoonHisVolledigView> bijgehoudenPersonen = getTestPersonen(47);
        final VolledigBericht volledigBericht = (VolledigBericht) maakLeveringBericht(bijgehoudenPersonen, Collections.singletonList(Populatie.BETREEDT));
        zetAlleAttributenOpMagGeleverdWorden(volledigBericht);

        // when
        final String xmlString = bouwXmlString(volledigBericht);

        try {
            // then
            LOGGER.debug(xmlString);
            final Document document = XMLUnit.buildControlDocument(xmlString);
            final XpathEngine engine = geefBrpXpathEngine();

            final NodeList nodes = engine.getMatchingNodes("brp:betrokkenheden//brp:persoon/brp:afgeleidAdministratief", document);
            assertThat(nodes.getLength(), is(0));
        } catch (final XpathException | SAXException | IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
