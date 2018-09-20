/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.integratie;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.KindHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderlijkGezagModel;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.XpathEngine;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SynchronisatieBerichtIntegratieTest extends AbstractSynchronisatieBerichtIntegratieTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String FOUTMELDING_VOLDOET_NIET_AAN_XSD = "De XML voldoet *niet* aan het Schema";
    private static final String BRP_OUDER_BRP_PERSOON = "//brp:ouder/brp:persoon";
    private static final String PERSOON = "persoon";
    private static final String BRP_OUDER = "//brp:ouder";

    @Inject
    private BlobifierService blobifierService;

    @Test
    public final void testOuderlijkGezagViaKind() {
        final PersoonHisVolledig testPersoon = blobifierService.leesBlob(46);
        final String testExpressie =
            "RMAP(GERELATEERDE_BETROKKENHEDEN(\"KIND\", \"FAMILIERECHTELIJKE_BETREKKING\", \"OUDER\"), "
                + "betr, $betr.ouderlijk_gezag.ouder_heeft_gezag)\n";
        final List<Attribuut> ouderlijkGezagAttributen = new ArrayList<>();

        final KindHisVolledig kindBetrokkenheid = testPersoon.getKindBetrokkenheid();
        for (final BetrokkenheidHisVolledig ouderBetrokkenheid : kindBetrokkenheid.getRelatie().getBetrokkenheden()) {
            if (ouderBetrokkenheid instanceof OuderHisVolledig) {
                final MaterieleHistorieSet<HisOuderOuderlijkGezagModel> ouderlijkgezagHistorie =
                    ((OuderHisVolledig) ouderBetrokkenheid).getOuderOuderlijkGezagHistorie();

                if (ouderlijkgezagHistorie != null && !ouderlijkgezagHistorie.isLeeg()) {
                    ouderlijkGezagAttributen.add(ouderlijkgezagHistorie.getActueleRecord().getIndicatieOuderHeeftGezag());
                }
            }
        }

        final LijstExpressie resultaat = (LijstExpressie) BRPExpressies.evalueer(testExpressie, testPersoon);
        assertFalse(resultaat.isFout());

        Attribuut attribuut1 = null;
        for (final nl.bzk.brp.expressietaal.Expressie element1 : resultaat.getElementen()) {
            for (final nl.bzk.brp.expressietaal.Expressie element2 : element1.getElementen()) {
                attribuut1 = element2.getAttribuut();
            }
        }

        assertTrue(ouderlijkGezagAttributen.get(0) == attribuut1);
    }

    /**
     * Deze test bewijst dat de expressie op dit moment via de ouderpersoon loopt, waardoor we in de verkeerde blob de magGeleverdWorden vlag zetten op de
     * ouderbetrokkenheid.
     */
    @Test
    public final void testOuderlijkGezagViaKindViaOuderPersoon() {
        final PersoonHisVolledig testPersoon = blobifierService.leesBlob(46);
        final String testExpressie =
            "PLATTE_LIJST(MAP(OUDERS(), o, MAP(o.betrokkenheden, ob, $ob.ouderlijk_gezag.ouder_heeft_gezag)))";

        final List<Attribuut> ouderlijkGezagAttributenViaPersoon = new ArrayList<>();
        final KindHisVolledig kindBetrokkenheid = testPersoon.getKindBetrokkenheid();
        for (final BetrokkenheidHisVolledig ouderBetrokkenheid : kindBetrokkenheid.getRelatie().getOuderBetrokkenheden()) {
            for (final BetrokkenheidHisVolledig ouderBetrokkenheidViaPersoon : ouderBetrokkenheid.getPersoon().getBetrokkenheden()) {
                if (ouderBetrokkenheidViaPersoon instanceof OuderHisVolledig) {
                    final MaterieleHistorieSet<HisOuderOuderlijkGezagModel> ouderlijkgezagHistorie =
                        ((OuderHisVolledig) ouderBetrokkenheidViaPersoon).getOuderOuderlijkGezagHistorie();

                    if (ouderlijkgezagHistorie != null && !ouderlijkgezagHistorie.isLeeg()) {
                        ouderlijkGezagAttributenViaPersoon.add(ouderlijkgezagHistorie.getActueleRecord().getIndicatieOuderHeeftGezag());
                    }
                }
            }
        }

        final LijstExpressie resultaat = (LijstExpressie) BRPExpressies.evalueer(testExpressie, testPersoon);
        assertFalse(resultaat.isFout());

        final Attribuut attribuut1 = resultaat.getElementen().get(0).getAttribuut();

        assertTrue(ouderlijkGezagAttributenViaPersoon.get(0) == attribuut1);

        assertEquals(1, resultaat.getElementen().size());
    }

    @Test
    public final void testZichtbaarheidOuderlijkGezagGroep() throws ExpressieExceptie {
        // given
        final List<PersoonHisVolledigView> betrokkenPersonen = getTestPersonen(46, 47);

        final VolledigBericht volledigBericht = (VolledigBericht) maakLeveringBericht(betrokkenPersonen, Arrays.asList(Populatie.BETREEDT, Populatie.BETREEDT));
        zetAlleAttributenOpMagGeleverdWorden(volledigBericht);
        zetBetrokkenOudersEnRelatieOpMagGeleverdWorden(volledigBericht);

        // when
        final String xmlString = bouwXmlString(volledigBericht);

        try {
            // then
            LOGGER.debug("Bericht in XML :\n" + xmlString);
            assertTrue(FOUTMELDING_VOLDOET_NIET_AAN_XSD, isValid(xmlString));

            final Document document = XMLUnit.buildControlDocument(xmlString);
            final XpathEngine engine = geefBrpXpathEngine();

            NodeList nodes;

            // vanuit kind: ouderlijkgezag mag je zien
            // in dit geval heeft 1 ouder gezag
            nodes = engine.getMatchingNodes(
                "//brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder/brp:ouderlijkGezag", document);
            assertThat(nodes.getLength(), is(1));

            // vanuit kind: ouderschap mag je zien
            nodes = engine.getMatchingNodes(
                "//brp:familierechtelijkeBetrekking/brp:betrokkenheden/brp:ouder/brp:ouderschap", document);
            assertThat(nodes.getLength(), is(2));

            // vanuit ouder: ouderlijkgezag mag niet worden geleverd
            nodes = engine.getMatchingNodes("//brp:persoon/brp:betrokkenheden/brp:ouder/brp:ouderlijkGezag", document);
            assertThat(nodes.getLength(), is(0));

            // vanuit ouder: ouderschap wordt niet meer geleverd (koppelvlak 15)
            nodes = engine.getMatchingNodes("//brp:persoon/brp:betrokkenheden/brp:ouder/brp:ouderschap", document);
            assertThat(nodes.getLength(), is(0));
        } catch (final XpathException | SAXException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void leveringPersoonMetOntbrekendeOuderIsCorrect() throws ExpressieExceptie {
        // given
        final List<PersoonHisVolledigView> betrokkenPersonen = getTestPersonen(9);

        final RelatieHisVolledig relatie = betrokkenPersonen.get(0).getKindBetrokkenheid().getRelatie();
        final BetrokkenheidHisVolledig ouder = relatie.getOuderBetrokkenheden().iterator().next();
        relatie.getBetrokkenheden().remove(ouder);

        final VolledigBericht kennisgevingBericht = (VolledigBericht) maakLeveringBericht(betrokkenPersonen, Collections.singletonList(Populatie.BETREEDT));
        zetAlleAttributenOpMagGeleverdWorden(kennisgevingBericht);
        zetBetrokkenOudersEnRelatieOpMagGeleverdWorden(kennisgevingBericht);

        // when
        final String xmlString = bouwXmlString(kennisgevingBericht);

        // then
        LOGGER.debug(xmlString);
        assertNotNull(xmlString);
        assertTrue(FOUTMELDING_VOLDOET_NIET_AAN_XSD, isValid(xmlString));

        try {
            final Document document = XMLUnit.buildControlDocument(xmlString);
            final XpathEngine engine = geefBrpXpathEngine();

            final NodeList nodes = engine.getMatchingNodes(BRP_OUDER_BRP_PERSOON, document);
            assertThat(nodes.getLength(), is(1));
        } catch (final XpathException | SAXException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void leveringPersoonMetOnbekendeOudersIsCorrect() throws ExpressieExceptie {
        // given
        final List<PersoonHisVolledigView> betrokkenPersonen = getTestPersonen(9);

        final RelatieHisVolledig relatieHisVolledig = betrokkenPersonen.get(0).getPersoon().getKindBetrokkenheid().getRelatie();
        for (final BetrokkenheidHisVolledig ouder : relatieHisVolledig.getOuderBetrokkenheden()) {
            ReflectionTestUtils.setField(ouder, PERSOON, null);
        }

        final VolledigBericht kennisgevingBericht = (VolledigBericht) maakLeveringBericht(betrokkenPersonen, Collections.singletonList(Populatie.BETREEDT));

        zetAlleAttributenOpMagGeleverdWorden(kennisgevingBericht);
        zetBetrokkenOudersEnRelatieOpMagGeleverdWorden(kennisgevingBericht);

        // when
        final String xmlString = bouwXmlString(kennisgevingBericht);

        // then
        LOGGER.debug(xmlString);
        assertNotNull(xmlString);
        assertTrue(FOUTMELDING_VOLDOET_NIET_AAN_XSD, isValid(xmlString));

        try {
            final Document document = XMLUnit.buildControlDocument(xmlString);
            final XpathEngine engine = geefBrpXpathEngine();

            NodeList nodes = engine.getMatchingNodes(BRP_OUDER, document);
            assertThat(nodes.getLength(), is(2));

            nodes = engine.getMatchingNodes(BRP_OUDER_BRP_PERSOON, document);
            assertThat(nodes.getLength(), is(0));
        } catch (final XpathException | SAXException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void leveringPersoonMetEenOnbekendeOuderIsCorrect() throws ExpressieExceptie {
        // given
        final List<PersoonHisVolledigView> betrokkenPersonen = getTestPersonen(9);

        final RelatieHisVolledig relatie = betrokkenPersonen.get(0).getPersoon().getKindBetrokkenheid().getRelatie();
        final BetrokkenheidHisVolledig ouder = relatie.getOuderBetrokkenheden().iterator().next();
        ReflectionTestUtils.setField(ouder, PERSOON, null);

        final VolledigBericht kennisgevingBericht = (VolledigBericht) maakLeveringBericht(betrokkenPersonen, Collections.singletonList(Populatie.BETREEDT));

        zetAlleAttributenOpMagGeleverdWorden(kennisgevingBericht);
        zetBetrokkenOudersEnRelatieOpMagGeleverdWorden(kennisgevingBericht);

        // when
        final String xmlString = bouwXmlString(kennisgevingBericht);

        // then
        LOGGER.debug(xmlString);
        assertNotNull(xmlString);
        assertTrue(FOUTMELDING_VOLDOET_NIET_AAN_XSD, isValid(xmlString));

        try {
            final Document document = XMLUnit.buildControlDocument(xmlString);
            final XpathEngine engine = geefBrpXpathEngine();

            NodeList nodes = engine.getMatchingNodes(BRP_OUDER, document);
            assertThat(nodes.getLength(), is(2));

            nodes = engine.getMatchingNodes(BRP_OUDER_BRP_PERSOON, document);
            assertThat(nodes.getLength(), is(1));
        } catch (final XpathException | SAXException | IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
