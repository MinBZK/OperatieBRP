/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.vergelijker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Set;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Stapel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.AbstractDeltaTest;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaRootEntiteitMatch;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.IstStapelMatch;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.TestPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators.PersoonDecorator;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators.StapelDecorator;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;

import org.junit.Before;
import org.junit.Test;

/**
 * Unittest voor {@link IstStapelEnRelatieMatcher}.
 */
public class IstStapelEnRelatieMatcherTest extends AbstractDeltaTest {

    private TestPersoon nieuwePersoon;
    private TestPersoon bestaandePersoon;
    private IstStapelEnRelatieMatcher matcher;
    private DeltaBepalingContext context;

    @Before
    public void setUp() throws Exception {
        matcher = new IstStapelEnRelatieMatcher();
        nieuwePersoon =
                new TestPersoon(
                    SoortPersoon.INGESCHREVENE,
                    Timestamp.valueOf("2014-01-01 00:00:00.0"),
                    "Vermeer",
                    2L,
                    SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL);
        bestaandePersoon = new TestPersoon(SoortPersoon.INGESCHREVENE);
        bestaandePersoon.setAdministratienummer(200L);
        nieuwePersoon.setAdministratienummer(200L);

        context = new DeltaBepalingContext(nieuwePersoon, bestaandePersoon, null, false);
        SynchronisatieLogging.init();
    }

    /**
     * Test de match waar bij beide versies van de PL exact 1 stapel/voorkomen bevat.
     */
    @Test
    public void testMatchEnkelvoudig() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(bestaandeStapel, true, 0);
        bestaandePersoon.addStapel(bestaandeStapel);

        final Stapel nieuwStapel = new Stapel(nieuwePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(nieuwStapel, true, 0);
        nieuwePersoon.addStapel(nieuwStapel);

        final Set<?> stapelMatchSet = vergelijk();
        controleerIstStapelMatches(stapelMatchSet, 1, 1, 0, 0, 0);
        final IstStapelMatch stapelMatch = (IstStapelMatch) stapelMatchSet.iterator().next();
        assertEquals("Matching stapel moet gelijk zijn", StapelDecorator.decorate(nieuwStapel), stapelMatch.getMatchingStapel());

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        final int aantalVerwachtGewijzigd = 1;
        final int aantalVerwachtVerwijderd = 0;
        final int aantalVerwachtToegevoegd = 0;

        testMatchResultaat(matches, aantalVerwachtGewijzigd, aantalVerwachtVerwijderd, aantalVerwachtToegevoegd);

        final DeltaRootEntiteitMatch match = matches.iterator().next();
        assertEquals(bestaandeStapel, match.getBestaandeDeltaRootEntiteit());
        assertEquals(nieuwStapel, match.getNieuweDeltaRootEntiteit());
        assertEquals(bestaandePersoon, match.getEigenaarEntiteit());
        assertEquals(Persoon.STAPELS, match.getEigenaarEntiteitVeldnaam());
    }

    /**
     * Test of er bij een
     * {@link nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.StapelMatchType#NON_UNIQUE_MATCH} de bestaande
     * stapel verwijderd en de 2 nieuwe stapels toegevoegd zijn.
     */
    @Test
    public void testMultipleMatch() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(bestaandeStapel, true, 0);
        bestaandePersoon.addStapel(bestaandeStapel);

        final Stapel nieuwStapel1 = new Stapel(nieuwePersoon, KIND_CATEGORIE, 0);
        final Stapel nieuwStapel2 = new Stapel(nieuwePersoon, KIND_CATEGORIE, 1);
        voegIstStapelVoorkomenToe(nieuwStapel1, false, 0);
        voegIstStapelVoorkomenToe(nieuwStapel1, true, 1, true);
        voegIstStapelVoorkomenToe(nieuwStapel2, true, 0);
        nieuwePersoon.addStapel(nieuwStapel1);
        nieuwePersoon.addStapel(nieuwStapel2);

        final Set<?> stapelMatchSet = vergelijk();
        controleerIstStapelMatches(stapelMatchSet, 1, 0, 1, 0, 0);

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        final int aantalVerwachtGewijzigd = 0;
        final int aantalVerwachtVerwijderd = 1;
        final int aantalVerwachtToegevoegd = 2;

        testMatchResultaat(matches, aantalVerwachtGewijzigd, aantalVerwachtVerwijderd, aantalVerwachtToegevoegd);
    }

    /**
     * Test of matching goed gaat als er een onjuist voorkomen is.
     */
    @Test
    public void testMatchBestaandeStapelMetOnjuistVoorkomen() {
        final Stapel bestaandeStapel1 = new Stapel(bestaandePersoon, KIND_CATEGORIE, 0);
        final Stapel bestaandeStapel2 = new Stapel(bestaandePersoon, KIND_CATEGORIE, 1);
        voegIstStapelVoorkomenToe(bestaandeStapel1, false, 0);
        voegIstStapelVoorkomenToe(bestaandeStapel1, true, 1, true);
        voegIstStapelVoorkomenToe(bestaandeStapel2, true, 0);
        bestaandePersoon.addStapel(bestaandeStapel1);
        bestaandePersoon.addStapel(bestaandeStapel2);

        final Stapel nieuwStapel1 = new Stapel(nieuwePersoon, KIND_CATEGORIE, 0);
        final Stapel nieuwStapel2 = new Stapel(nieuwePersoon, KIND_CATEGORIE, 1);
        voegIstStapelVoorkomenToe(nieuwStapel1, true, 0);
        voegIstStapelVoorkomenToe(nieuwStapel2, false, 0);
        nieuwePersoon.addStapel(nieuwStapel1);
        nieuwePersoon.addStapel(nieuwStapel2);

        final Set<?> stapelMatchSet = vergelijk();
        controleerIstStapelMatches(stapelMatchSet, 2, 2, 0, 0, 0);

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        final int aantalVerwachtGewijzigd = 2;
        final int aantalVerwachtVerwijderd = 0;
        final int aantalVerwachtToegevoegd = 0;

        testMatchResultaat(matches, aantalVerwachtGewijzigd, aantalVerwachtVerwijderd, aantalVerwachtToegevoegd);
    }

    /**
     * Test of er {@link nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.StapelMatchType#NON_UNIQUE_MATCH} kan
     * optreden als er in de nieuwe stapel een onjuist voorkomen voorkomt.
     */
    @Test
    public void testMatchNonUniqueNieuwStapelMetOnjuistVoorkomen() {
        final Stapel bestaandeStapel1 = new Stapel(bestaandePersoon, KIND_CATEGORIE, 0);
        final Stapel bestaandeStapel2 = new Stapel(bestaandePersoon, KIND_CATEGORIE, 1);
        voegIstStapelVoorkomenToe(bestaandeStapel1, true, 0);
        voegIstStapelVoorkomenToe(bestaandeStapel2, false, 0);
        bestaandePersoon.addStapel(bestaandeStapel1);
        bestaandePersoon.addStapel(bestaandeStapel2);

        final Stapel nieuwStapel1 = new Stapel(nieuwePersoon, KIND_CATEGORIE, 0);
        final Stapel nieuwStapel2 = new Stapel(nieuwePersoon, KIND_CATEGORIE, 1);
        voegIstStapelVoorkomenToe(nieuwStapel1, false, 0);
        voegIstStapelVoorkomenToe(nieuwStapel1, true, 1, true);
        voegIstStapelVoorkomenToe(nieuwStapel2, true, 0);
        nieuwePersoon.addStapel(nieuwStapel1);
        nieuwePersoon.addStapel(nieuwStapel2);

        final Set<?> stapelMatchSet = vergelijk();

        controleerIstStapelMatches(stapelMatchSet, 2, 0, 2, 0, 0);

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        final int aantalVerwachtGewijzigd = 0;
        final int aantalVerwachtVerwijderd = 2;
        final int aantalVerwachtToegevoegd = 2;

        testMatchResultaat(matches, aantalVerwachtGewijzigd, aantalVerwachtVerwijderd, aantalVerwachtToegevoegd);
    }

    /**
     * Test met een correctie in de stapel.
     */
    @Test
    public void testMatchNieuweStapelMetCorrectie() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(bestaandeStapel, true, 0);
        bestaandePersoon.addStapel(bestaandeStapel);

        final Stapel nieuwStapel = new Stapel(nieuwePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(nieuwStapel, false, 0);
        voegIstStapelVoorkomenToe(nieuwStapel, true, 1, true);
        nieuwePersoon.addStapel(nieuwStapel);

        final Set<?> stapelMatchSet = vergelijk();

        controleerIstStapelMatches(stapelMatchSet, 1, 1, 0, 0, 0);

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        final int aantalVerwachtGewijzigd = 1;
        final int aantalVerwachtVerwijderd = 0;
        final int aantalVerwachtToegevoegd = 0;

        testMatchResultaat(matches, aantalVerwachtGewijzigd, aantalVerwachtVerwijderd, aantalVerwachtToegevoegd);
    }

    /**
     * Test dat er een match gedetecteerd wordt als er meerdere voorkomens in de nieuwe stapel aanwezig zijn.
     */
    @Test
    public void testMatchedMeerdereVoorkomens() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(bestaandeStapel, true, 0);
        bestaandePersoon.addStapel(bestaandeStapel);

        final Stapel nieuwStapel = new Stapel(nieuwePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(nieuwStapel, false, 0);
        voegIstStapelVoorkomenToe(nieuwStapel, true, 1);
        nieuwePersoon.addStapel(nieuwStapel);

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        final int aantalVerwachtGewijzigd = 1;
        final int aantalVerwachtVerwijderd = 0;
        final int aantalVerwachtToegevoegd = 0;

        testMatchResultaat(matches, aantalVerwachtGewijzigd, aantalVerwachtVerwijderd, aantalVerwachtToegevoegd);
    }

    /**
     * Test dat er een match gedetecteerd wordt als de stapel in de nieuwe LO3 PL qua voorkomens omgedraaid is tov de
     * bestaande PL.
     */
    @Test
    public void testMatchedStapelOmgedraaid() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, KIND_CATEGORIE, 0);
        final Stapel bestaandeStapel2 = new Stapel(bestaandePersoon, KIND_CATEGORIE, 1);
        voegIstStapelVoorkomenToe(bestaandeStapel, true, 0);
        voegIstStapelVoorkomenToe(bestaandeStapel2, false, 0);
        bestaandePersoon.addStapel(bestaandeStapel);
        bestaandePersoon.addStapel(bestaandeStapel2);

        final Stapel nieuwStapel = new Stapel(nieuwePersoon, KIND_CATEGORIE, 0);
        final Stapel nieuwStapel2 = new Stapel(nieuwePersoon, KIND_CATEGORIE, 1);
        voegIstStapelVoorkomenToe(nieuwStapel, false, 0);
        voegIstStapelVoorkomenToe(nieuwStapel2, true, 0);
        nieuwePersoon.addStapel(nieuwStapel);
        nieuwePersoon.addStapel(nieuwStapel2);

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        final int aantalVerwachtGewijzigd = 2;
        final int aantalVerwachtVerwijderd = 0;
        final int aantalVerwachtToegevoegd = 0;

        testMatchResultaat(matches, aantalVerwachtGewijzigd, aantalVerwachtVerwijderd, aantalVerwachtToegevoegd);
    }

    /**
     * Test of de nieuwe stapel wordt gedetecteerd en wordt weggeschreven als match en bij 1 voorkomen er een sync uit
     * komt.
     */
    @Test
    public void testMatchNieuwStapel1Voorkomen() {
        final Stapel stapel = new Stapel(nieuwePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(stapel, true, 0);

        nieuwePersoon.addStapel(stapel);

        final Set<?> stapelMatchSet = vergelijk();
        controleerIstStapelMatches(stapelMatchSet, 1, 0, 0, 1, 0);

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        final int aantalVerwachtGewijzigd = 0;
        final int aantalVerwachtVerwijderd = 0;
        final int aantalVerwachtToegevoegd = 1;

        testMatchResultaat(matches, aantalVerwachtGewijzigd, aantalVerwachtVerwijderd, aantalVerwachtToegevoegd);

        final DeltaRootEntiteitMatch match = matches.iterator().next();
        assertNull(match.getBestaandeDeltaRootEntiteit());
        assertEquals(stapel, match.getNieuweDeltaRootEntiteit());
        assertEquals(bestaandePersoon, match.getEigenaarEntiteit());
        assertEquals(Persoon.STAPELS, match.getEigenaarEntiteitVeldnaam());
        assertFalse(context.isBijhoudingOverig());
    }

    /**
     * Test of de nieuwe stapel wordt gedetecteerd en wordt weggeschreven als match en als er meer dan 1 voorkomen is er
     * een resync uit komt.
     */
    @Test
    public void testMatchNieuwStapel2Voorkomens() {
        final Stapel stapel = new Stapel(nieuwePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(stapel, true, 0);
        voegIstStapelVoorkomenToe(stapel, true, 1);

        nieuwePersoon.addStapel(stapel);

        final Set<?> stapelMatchSet = vergelijk();
        controleerIstStapelMatches(stapelMatchSet, 1, 0, 0, 1, 0);

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        final int aantalVerwachtGewijzigd = 0;
        final int aantalVerwachtVerwijderd = 0;
        final int aantalVerwachtToegevoegd = 1;

        testMatchResultaat(matches, aantalVerwachtGewijzigd, aantalVerwachtVerwijderd, aantalVerwachtToegevoegd);

        final DeltaRootEntiteitMatch match = matches.iterator().next();
        assertNull(match.getBestaandeDeltaRootEntiteit());
        assertEquals(stapel, match.getNieuweDeltaRootEntiteit());
        assertEquals(bestaandePersoon, match.getEigenaarEntiteit());
        assertEquals(Persoon.STAPELS, match.getEigenaarEntiteitVeldnaam());
        assertTrue(context.isBijhoudingOverig());
    }

    /**
     * Test of de bestaande stapel, welke verwijderd is, als verwijderde match wordt gezien.
     */
    @Test
    public void testMatchGeenStapelMeer() {
        final Stapel stapel = new Stapel(bestaandePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(stapel, true, 0);
        bestaandePersoon.addStapel(stapel);

        final Set<?> stapelMatchSet = vergelijk();
        controleerIstStapelMatches(stapelMatchSet, 1, 0, 0, 0, 1);

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        final int aantalVerwachtGewijzigd = 0;
        final int aantalVerwachtVerwijderd = 1;
        final int aantalVerwachtToegevoegd = 0;

        testMatchResultaat(matches, aantalVerwachtGewijzigd, aantalVerwachtVerwijderd, aantalVerwachtToegevoegd);

        final DeltaRootEntiteitMatch match = matches.iterator().next();
        assertEquals(stapel, match.getBestaandeDeltaRootEntiteit());
        assertNull(match.getNieuweDeltaRootEntiteit());
        assertEquals(bestaandePersoon, match.getEigenaarEntiteit());
        assertEquals(Persoon.STAPELS, match.getEigenaarEntiteitVeldnaam());
    }

    @Test
    public void testMatchStapelVerwijderdEnNieuweToegevoegd() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(bestaandeStapel, true, 0);
        bestaandePersoon.addStapel(bestaandeStapel);

        final Stapel nieuwStapel = new Stapel(nieuwePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(nieuwStapel, false, 0);
        nieuwePersoon.addStapel(nieuwStapel);

        final Set<?> stapelMatchSet = vergelijk();
        controleerIstStapelMatches(stapelMatchSet, 2, 0, 0, 1, 1);

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        final int aantalVerwachtGewijzigd = 0;
        final int aantalVerwachtVerwijderd = 1;
        final int aantalVerwachtToegevoegd = 1;

        testMatchResultaat(matches, aantalVerwachtGewijzigd, aantalVerwachtVerwijderd, aantalVerwachtToegevoegd);
    }

    /**
     * Test de matching van ouder1 waar geen wijzigingen in zijn gedaan.
     */
    @Test
    public void testMatchOuder1GeenWijzigingen() {
        bestaandePersoon.voegOuderToe(true);
        nieuwePersoon.voegOuderToe(true);

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        // Stapel, Ik-betrokkenheide en gerelateerde Betrokkenheid en Persoon
        final int aantalVerwachtGewijzigd = 4;
        final int aantalVerwachtVerwijderd = 0;
        final int aantalVerwachtToegevoegd = 0;

        testMatchResultaat(matches, aantalVerwachtGewijzigd, aantalVerwachtVerwijderd, aantalVerwachtToegevoegd);
    }

    /**
     * Test de matching van ouder2 waar geen wijzigingen in zijn gedaan.
     */
    @Test
    public void testMatchOuder2GeenWijzigingen() {
        bestaandePersoon.voegOuderToe(false);
        nieuwePersoon.voegOuderToe(false);

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        // Stapel, Ik-betrokkenheid en gerelateerde Betrokkenheid en Persoon
        final int aantalVerwachtGewijzigd = 4;
        final int aantalVerwachtVerwijderd = 0;
        final int aantalVerwachtToegevoegd = 0;

        testMatchResultaat(matches, aantalVerwachtGewijzigd, aantalVerwachtVerwijderd, aantalVerwachtToegevoegd);
    }

    /**
     * Test matching als LO3 PL van RNI naar GBA gaat.
     */
    @Test
    public void testMatchOuderRniNaarLo3PL() {
        nieuwePersoon.voegOuderToe(true);
        nieuwePersoon.voegOuderToe(false);

        final IstStapelMatch stapelMatchSet = vergelijkOuder2();
        controleerIstStapelMatches(Collections.singleton(stapelMatchSet), 1, 0, 0, 1, 0);

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        final int aantalVerwachtGewijzigd = 0;
        final int aantalVerwachtVerwijderd = 0;
        // Stapel ouder1, Stapel ouder2, Ik-betrokkenheid
        final int aantalVerwachtToegevoegd = 3;

        testMatchResultaat(matches, aantalVerwachtGewijzigd, aantalVerwachtVerwijderd, aantalVerwachtToegevoegd);
    }

    /**
     * Test matching als LO3 PL van GBA naar RNI gaat en de ouders (in dit geval alleen ouder2) worden verwijderd van de
     * PL.
     */
    @Test
    public void testMatchOuderLo3PLNaarRni() {
        bestaandePersoon.voegOuderToe(true);
        bestaandePersoon.voegOuderToe(false);

        final IstStapelMatch stapelMatchSet = vergelijkOuder2();
        controleerIstStapelMatches(Collections.singleton(stapelMatchSet), 1, 0, 0, 0, 1);

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        final int aantalVerwachtGewijzigd = 0;
        // Stapel ouder1, Stapel ouder2
        final int aantalVerwachtVerwijderd = 2;
        final int aantalVerwachtToegevoegd = 0;

        testMatchResultaat(matches, aantalVerwachtGewijzigd, aantalVerwachtVerwijderd, aantalVerwachtToegevoegd);
    }

    /**
     * Test: Er bestaat reeds een cat03 (ouder2) en er wordt een cat02(ouder1) toegevoegd.
     */
    @Test
    public void testMatchOuder2BestaatOuder1Nieuw() {
        bestaandePersoon.voegOuderToe(false);
        nieuwePersoon.voegOuderToe(true);
        nieuwePersoon.voegOuderToe(false);

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        // Stapel ouder2, Ik-betrokkenheid, en ouder2 gerelateerde Betrokkenheid en Persoon
        final int aantalVerwachtGewijzigd = 4;
        final int aantalVerwachtVerwijderd = 0;
        // Stapel ouder1 + Betrokkenheid
        final int aantalVerwachtToegevoegd = 2;

        testMatchResultaat(matches, aantalVerwachtGewijzigd, aantalVerwachtVerwijderd, aantalVerwachtToegevoegd);
    }

    /**
     * Test: van punt-ouder(alleen gerelateerde betrokkenheid) naar een volledige ouder (gerelateerde betrokkenheid +
     * persoon). Gerelateerde betrokkenheid wordt gewijzigd ipv nieuwe toegevoegd omdat de ouder gewalgd wordt en er
     * geen datum aanvang is opgenomen.
     */
    @Test
    public void testMatchPuntOuderNaarVolledigeOuder() {
        bestaandePersoon.voegPuntouderToe(true);
        nieuwePersoon.voegOuderToe(true);

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        // Stapel ouder1, Ik-betrokkenheid, gerelateerde betrokkenheden
        final int aantalVerwachtGewijzigd = 3;
        final int aantalVerwachtVerwijderd = 0;
        // Gerelateerde persoon
        final int aantalVerwachtToegevoegd = 1;

        testMatchResultaat(matches, aantalVerwachtGewijzigd, aantalVerwachtVerwijderd, aantalVerwachtToegevoegd);
    }

    /**
     * Test: van juridisch geen ouder (alleen relatie) naar een volledige ouder (gerelateerde betrokkenheid + persoon).
     */
    @Test
    public void testMatchJuridischOuderNaarVolledigeOuder() {
        bestaandePersoon.voegJuridischGeenOuderToe(true);
        nieuwePersoon.voegOuderToe(true);

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        // Stapel ouder1, Ik-betrokkenheid
        final int aantalVerwachtGewijzigd = 2;
        final int aantalVerwachtVerwijderd = 0;
        // Gerelateerde persoon
        final int aantalVerwachtToegevoegd = 1;

        testMatchResultaat(matches, aantalVerwachtGewijzigd, aantalVerwachtVerwijderd, aantalVerwachtToegevoegd);
    }

    /**
     * Test: Verwijderen van ouder1 stapel, ouder2 stapel blijft bestaan
     */
    @Test
    public void testVerwijderenOuder1Ouder2Blijft() {
        bestaandePersoon.voegOuderToe(true);
        bestaandePersoon.voegOuderToe(false);
        nieuwePersoon.voegOuderToe(false);

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        // Stapel ouder2, Ik-betrokkenheid, gerelateerde betrokkenheid en persoon ouder 2
        final int aantalVerwachtGewijzigd = 4;
        // Stapel ouder1, gerelateerde betrokkenheid
        final int aantalVerwachtVerwijderd = 2;
        final int aantalVerwachtToegevoegd = 0;

        testMatchResultaat(matches, aantalVerwachtGewijzigd, aantalVerwachtVerwijderd, aantalVerwachtToegevoegd);
    }

    /**
     * Test: Nieuw huwelijk op de persoonslijst
     */
    @Test
    public void testNieuwHuwelijk() {
        nieuwePersoon.voegHuwelijkToe(20100101, null);

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        final int aantalVerwachtGewijzigd = 0;
        final int aantalVerwachtVerwijderd = 0;
        // Stapel, betrokkenheid
        final int aantalVerwachtToegevoegd = 2;

        testMatchResultaat(matches, aantalVerwachtGewijzigd, aantalVerwachtVerwijderd, aantalVerwachtToegevoegd);
    }

    /**
     * Test: Huwelijk verwijderd van de persoonslijst
     */
    @Test
    public void testVerwijderdHuwelijk() {
        bestaandePersoon.voegHuwelijkToe(20100101, null);

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        final int aantalVerwachtGewijzigd = 0;
        // Stapel, betrokkenheid
        final int aantalVerwachtVerwijderd = 2;
        final int aantalVerwachtToegevoegd = 0;

        testMatchResultaat(matches, aantalVerwachtGewijzigd, aantalVerwachtVerwijderd, aantalVerwachtToegevoegd);
    }

    /**
     * Test: Huwelijk niet aangepast.
     */
    @Test
    public void testHuwelijkOngewijzigd() {
        final int datumAanvang = 20100101;
        bestaandePersoon.voegHuwelijkToe(datumAanvang, null);
        nieuwePersoon.voegHuwelijkToe(datumAanvang, null);


        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        // Stapel, betrokkenheid, relatie en gerelateerde betrokkenheid en persoon
        final int aantalVerwachtGewijzigd = 5;
        final int aantalVerwachtVerwijderd = 0;
        final int aantalVerwachtToegevoegd = 0;

        testMatchResultaat(matches, aantalVerwachtGewijzigd, aantalVerwachtVerwijderd, aantalVerwachtToegevoegd);
    }

    /**
     * Test: nieuw gezagsverhoudingstapel
     */
    @Test
    public void testGezagsverhoudingNieuw() {
        nieuwePersoon.voegGezagsverhoudingToe();

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        testMatchResultaat(matches, 0, 0, 1);
    }

    /**
     * Test: gezagsverhoudingstapel verwijderd
     */
    @Test
    public void testGezagsverhoudingVerwijderd() {
        bestaandePersoon.voegGezagsverhoudingToe();

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        testMatchResultaat(matches, 0, 1, 0);
    }

    /**
     * Test: gezagsverhoudingstapel bestaat
     */
    @Test
    public void testGezagsverhoudingBestaat() {
        bestaandePersoon.voegGezagsverhoudingToe();
        nieuwePersoon.voegGezagsverhoudingToe();

        final Set<DeltaRootEntiteitMatch> matches = matcher.matchIstGegevens(context);
        assertNotNull(matches);
        assertFalse(matches.isEmpty());

        testMatchResultaat(matches, 1, 0, 0);
    }

    private Set<?> vergelijk() {
        Method method;
        try {
            method = IstStapelEnRelatieMatcher.class.getDeclaredMethod("matchStapels", Set.class, Set.class);
            method.setAccessible(true);
            final PersoonDecorator decoratedBestaandePersoon = PersoonDecorator.decorate(bestaandePersoon);
            final PersoonDecorator decoratedNieuwePersoon = PersoonDecorator.decorate(nieuwePersoon);
            return (Set<?>) method.invoke(matcher, decoratedBestaandePersoon.getKindStapels(), decoratedNieuwePersoon.getKindStapels());
        } catch (
            NoSuchMethodException
            | SecurityException
            | IllegalAccessException
            | IllegalArgumentException
            | InvocationTargetException e)
        {
            throw new IllegalStateException("Onverwachte fout tijdens uitvoeren vegelijken personen.", e);
        }
    }

    private void testMatchResultaat(
        final Set<DeltaRootEntiteitMatch> resultaat,
        final int aantalVerwachtGewijzigd,
        final int aantalVerwachtVerwijderd,
        final int aantalVerwachtToegevoegd)
    {
        int aantalGewijzigd = 0;
        int aantalVerwijderd = 0;
        int aantalToegevoegd = 0;
        for (final DeltaRootEntiteitMatch match : resultaat) {
            if (match.isDeltaRootEntiteitVerwijderd()) {
                aantalVerwijderd++;
            } else if (match.isDeltaRootEntiteitGewijzigd()) {
                aantalGewijzigd++;
            } else {
                aantalToegevoegd++;
            }
        }

        assertEquals("Aantal gewijzigd klopt niet", aantalVerwachtGewijzigd, aantalGewijzigd);
        assertEquals("Aantal verwijderd klopt niet", aantalVerwachtVerwijderd, aantalVerwijderd);
        assertEquals("Aantal toegevoegd klopt niet", aantalVerwachtToegevoegd, aantalToegevoegd);
    }

    private IstStapelMatch vergelijkOuder2() {
        Method method;
        try {
            method =
                    IstStapelEnRelatieMatcher.class.getDeclaredMethod(
                        "matchOuderStapel",
                        DeltaBepalingContext.class,
                        PersoonDecorator.class,
                        PersoonDecorator.class,
                        boolean.class);
            method.setAccessible(true);
            final PersoonDecorator decoratedBestaandePersoon = PersoonDecorator.decorate(bestaandePersoon);
            final PersoonDecorator decoratedKluizenaar = PersoonDecorator.decorate(nieuwePersoon);
            return (IstStapelMatch) method.invoke(matcher, context, decoratedBestaandePersoon, decoratedKluizenaar, false);
        } catch (
            NoSuchMethodException
            | SecurityException
            | IllegalAccessException
            | IllegalArgumentException
            | InvocationTargetException e)
        {
            throw new IllegalStateException("Onverwachte fout tijdens uitvoeren vergelijken ouder.", e);
        }
    }

    private void controleerIstStapelMatches(
        final Set<?> stapelMatchSet,
        final int aantalStapelMatches,
        final int verwachtAantalMatches,
        final int verwachtAantalNonUnique,
        final int verwachtAantalNieuw,
        final int verwachtAantalStapelVerwijderd)
    {
        assertEquals(aantalStapelMatches, stapelMatchSet.size());
        int aantalMatches = 0;
        int aantalNonUnique = 0;
        int aantalNieuw = 0;
        int aantalStapelVerwijderd = 0;

        int overig = 0;
        for (final Object istStapelMatchObject : stapelMatchSet) {
            final IstStapelMatch istStapelMatch = (IstStapelMatch) istStapelMatchObject;
            switch (istStapelMatch.getMatchType()) {
                case MATCHED:
                    aantalMatches++;
                    break;
                case NON_UNIQUE_MATCH:
                    aantalNonUnique++;
                    break;
                case STAPEL_NIEUW:
                    aantalNieuw++;
                    break;
                case STAPEL_VERWIJDERD:
                    aantalStapelVerwijderd++;
                    break;
                default:
                    overig++;
            }
        }
        assertEquals("Aantal MATCHED niet gelijk", verwachtAantalMatches, aantalMatches);
        assertEquals("Aantal NON_UNIQUE_MATCH niet gelijk", verwachtAantalNonUnique, aantalNonUnique);
        assertEquals("Aantal STAPEL_NIEUW niet gelijk", verwachtAantalNieuw, aantalNieuw);
        assertEquals("Aantal STAPEL_VERWIJDERD niet gelijk", verwachtAantalStapelVerwijderd, aantalStapelVerwijderd);
        assertEquals(0, overig);
    }
}
