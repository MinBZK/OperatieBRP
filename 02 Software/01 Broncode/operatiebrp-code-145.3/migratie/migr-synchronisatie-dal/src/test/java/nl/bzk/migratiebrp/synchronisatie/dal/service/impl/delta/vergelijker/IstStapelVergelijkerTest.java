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

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.IstSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.AbstractDeltaTest;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators.StapelDecorator;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;

/**
 * Unittest voor RelatieVergelijker.
 */
public class IstStapelVergelijkerTest extends AbstractDeltaTest {

    private Persoon nieuwPersoon;
    private Persoon bestaandePersoon;
    private IstStapelVergelijker vergelijker;
    private DeltaBepalingContext context;

    @Before
    public void setUp() {
        vergelijker = new IstStapelVergelijker();
        nieuwPersoon = maakPersoon(false);
        bestaandePersoon = maakPersoon(true);

        context = new DeltaBepalingContext(nieuwPersoon, bestaandePersoon, null, false);
        SynchronisatieLogging.init();
    }

    /**
     * Test met 2 stapels zonder wijzigingen.
     */
    @Test
    public void testVergelijkGeenWijzigingen() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(bestaandeStapel, true, 0);
        voegIstStapelRelatieToe(bestaandeStapel, SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        final Stapel nieuweStapel = new Stapel(nieuwPersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(nieuweStapel, true, 0);
        voegIstStapelRelatieToe(nieuweStapel, SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        final VergelijkerResultaat resultaat = vergelijker.vergelijk(context, bestaandeStapel, nieuweStapel);
        assertNotNull(resultaat);
        assertTrue(resultaat.isLeeg());
    }

    /**
     * Test die een stapel met zichzelf vergelijkt.
     */
    @Test
    public void testVergelijkDezelfdeStapel() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(bestaandeStapel, true, 0);
        voegIstStapelRelatieToe(bestaandeStapel, SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        final VergelijkerResultaat resultaat = vergelijker.vergelijk(context, bestaandeStapel, bestaandeStapel);
        assertNotNull(resultaat);
        assertTrue(resultaat.isLeeg());
    }

    /**
     * Test die 2 stapels vergelijkt waarbij de volgnummer van de stapel is aangepast
     */
    @Test
    public void testVergelijkStapelVolgnummerAangepast() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(bestaandeStapel, true, 0);
        voegIstStapelRelatieToe(bestaandeStapel, SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        final Stapel nieuweStapel = new Stapel(nieuwPersoon, KIND_CATEGORIE, 1);
        voegIstStapelVoorkomenToe(nieuweStapel, true, 0);
        voegIstStapelRelatieToe(nieuweStapel, SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        final VergelijkerResultaat resultaat = vergelijker.vergelijk(context, bestaandeStapel, nieuweStapel);
        assertNotNull(resultaat);
        assertFalse(resultaat.isLeeg());
        final Set<Verschil> verschillen = resultaat.getVerschillen();
        assertEquals(1, verschillen.size());

        final Verschil verschil = verschillen.iterator().next();
        final Sleutel sleutel = verschil.getSleutel();
        assertTrue(sleutel instanceof IstSleutel);
        assertEquals(Stapel.VELD_VOLGNUMMER, sleutel.getVeld());
        assertEquals(VerschilType.ELEMENT_AANGEPAST, verschil.getVerschilType());
        assertEquals(StapelDecorator.decorate(bestaandeStapel), verschil.getOudeWaarde());
        assertEquals(StapelDecorator.decorate(nieuweStapel), verschil.getNieuweWaarde());
    }

    /**
     * Test waarbij het aantal voorkomens niet is veranderd, alleen de volgorde van voorkomens is anders.
     */
    @Test
    public void testVergelijkZelfdeAantalVoorkomensMaarOmgekeerdeVolgorde() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(bestaandeStapel, true, 0);
        voegIstStapelVoorkomenToe(bestaandeStapel, false, 1);
        voegIstStapelRelatieToe(bestaandeStapel, SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        final Stapel nieuweStapel = new Stapel(nieuwPersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(nieuweStapel, false, 0);
        voegIstStapelVoorkomenToe(nieuweStapel, true, 1);
        voegIstStapelRelatieToe(nieuweStapel, SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        final VergelijkerResultaat resultaat = vergelijker.vergelijk(context, bestaandeStapel, nieuweStapel);
        assertNotNull(resultaat);
        assertFalse(resultaat.isLeeg());
        assertEquals(2, resultaat.getVerschillen().size());

        final Verschil verschilVerwijderd = resultaat.getVerschil(VerschilType.ELEMENT_VERWIJDERD);
        final Sleutel sleutelVerwijderd = verschilVerwijderd.getSleutel();
        assertTrue(sleutelVerwijderd instanceof IstSleutel);
        assertNull(sleutelVerwijderd.getVeld());
        assertEquals(VerschilType.ELEMENT_VERWIJDERD, verschilVerwijderd.getVerschilType());
        assertNotNull(verschilVerwijderd.getOudeWaarde());
        assertNull(verschilVerwijderd.getNieuweWaarde());

        final Verschil verschilNieuw = resultaat.getVerschil(VerschilType.ELEMENT_NIEUW);
        final Sleutel sleutelNieuw = verschilNieuw.getSleutel();
        assertTrue(sleutelNieuw instanceof IstSleutel);
        assertNull(sleutelNieuw.getVeld());
        assertEquals(VerschilType.ELEMENT_NIEUW, verschilNieuw.getVerschilType());
        assertNull(verschilNieuw.getOudeWaarde());
        assertNotNull(verschilNieuw.getNieuweWaarde());
        assertTrue(context.isBijhoudingOverig());
    }

    /**
     * Test waarbij er 1 extra voorkomen is (bv actualisering). Het bestaande voorkomen is bij de nieuwe PL NIET
     * aangepast.
     */
    @Test
    public void testVergelijkNieuwVoorkomenOudeVoorkomenIsGelijk() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(bestaandeStapel, true, 0);
        voegIstStapelRelatieToe(bestaandeStapel, SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        final Stapel nieuweStapel = new Stapel(nieuwPersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(nieuweStapel, false, 0);
        voegIstStapelVoorkomenToe(nieuweStapel, true, 1);
        voegIstStapelRelatieToe(nieuweStapel, SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        final VergelijkerResultaat resultaat = vergelijker.vergelijk(context, bestaandeStapel, nieuweStapel);
        assertNotNull(resultaat);
        assertFalse(resultaat.isLeeg());
        final Set<Verschil> verschillen = resultaat.getVerschillen();
        assertEquals(1, verschillen.size());

        final Verschil verschil = verschillen.iterator().next();
        final Sleutel sleutel = verschil.getSleutel();
        assertTrue(sleutel instanceof IstSleutel);
        assertEquals(Stapel.STAPEL_VOORKOMENS, sleutel.getVeld());
        assertEquals(VerschilType.RIJ_TOEGEVOEGD, verschil.getVerschilType());
        assertNull(verschil.getOudeWaarde());
        assertNotNull(verschil.getNieuweWaarde());
        assertFalse(context.isBijhoudingOverig());
    }

    /**
     * Test waarbij er 1 extra voorkomen is (bv actualisering). Het bestaande voorkomen is bij de nieuwe PL WEL
     * aangepast.
     */
    @Test
    public void testVergelijkNieuwVoorkomenOudeVoorkomenIsOngelijk() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(bestaandeStapel, true, 0);
        voegIstStapelRelatieToe(bestaandeStapel, SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        final Stapel nieuweStapel = new Stapel(nieuwPersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(nieuweStapel, false, 0);
        voegIstStapelVoorkomenToe(nieuweStapel, true, 1, true);
        voegIstStapelRelatieToe(nieuweStapel, SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        final VergelijkerResultaat resultaat = vergelijker.vergelijk(context, bestaandeStapel, nieuweStapel);
        assertNotNull(resultaat);
        assertFalse(resultaat.isLeeg());
        assertEquals(2, resultaat.getVerschillen().size());

        final Verschil verschilVerwijderd = resultaat.getVerschil(VerschilType.ELEMENT_VERWIJDERD);
        final Sleutel sleutelVerwijderd = verschilVerwijderd.getSleutel();
        assertTrue(sleutelVerwijderd instanceof IstSleutel);
        assertNull(sleutelVerwijderd.getVeld());
        assertEquals(VerschilType.ELEMENT_VERWIJDERD, verschilVerwijderd.getVerschilType());
        assertNotNull(verschilVerwijderd.getOudeWaarde());
        assertNull(verschilVerwijderd.getNieuweWaarde());

        final Verschil verschilNieuw = resultaat.getVerschil(VerschilType.ELEMENT_NIEUW);
        final Sleutel sleutelNieuw = verschilNieuw.getSleutel();
        assertTrue(sleutelNieuw instanceof IstSleutel);
        assertNull(sleutelNieuw.getVeld());
        assertEquals(VerschilType.ELEMENT_NIEUW, verschilNieuw.getVerschilType());
        assertNull(verschilNieuw.getOudeWaarde());
        assertNotNull(verschilNieuw.getNieuweWaarde());
        assertTrue(context.isBijhoudingOverig());
    }

    /**
     * Test waarbij er meerdere extra voorkomens zijn toegevoegd.
     */
    @Test
    public void testVergelijkMeerdereNieuweVoorkomens() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(bestaandeStapel, true, 0);
        voegIstStapelRelatieToe(bestaandeStapel, SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        final Stapel nieuweStapel = new Stapel(nieuwPersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(nieuweStapel, false, 0);
        voegIstStapelVoorkomenToe(nieuweStapel, false, 1, false);
        voegIstStapelVoorkomenToe(nieuweStapel, true, 2, true);
        voegIstStapelRelatieToe(nieuweStapel, SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        final VergelijkerResultaat resultaat = vergelijker.vergelijk(context, bestaandeStapel, nieuweStapel);
        assertNotNull(resultaat);
        assertFalse(resultaat.isLeeg());
        assertEquals(2, resultaat.getVerschillen().size());

        final Verschil verschilVerwijderd = resultaat.getVerschil(VerschilType.ELEMENT_VERWIJDERD);
        final Sleutel sleutelVerwijderd = verschilVerwijderd.getSleutel();
        assertTrue(sleutelVerwijderd instanceof IstSleutel);
        assertNull(sleutelVerwijderd.getVeld());
        assertEquals(VerschilType.ELEMENT_VERWIJDERD, verschilVerwijderd.getVerschilType());
        assertNotNull(verschilVerwijderd.getOudeWaarde());
        assertNull(verschilVerwijderd.getNieuweWaarde());

        final Verschil verschilNieuw = resultaat.getVerschil(VerschilType.ELEMENT_NIEUW);
        final Sleutel sleutelNieuw = verschilNieuw.getSleutel();
        assertTrue(sleutelNieuw instanceof IstSleutel);
        assertNull(sleutelNieuw.getVeld());
        assertEquals(VerschilType.ELEMENT_NIEUW, verschilNieuw.getVerschilType());
        assertNull(verschilNieuw.getOudeWaarde());
        assertNotNull(verschilNieuw.getNieuweWaarde());
        assertTrue(context.isBijhoudingOverig());
    }

    /**
     * Test waarbij er een nieuwe relatie is toegevoegd aan de stapel
     */
    @Test
    public void testNieuweRelatie() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(bestaandeStapel, true, 0);
        voegIstStapelRelatieToe(bestaandeStapel, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);

        final Stapel nieuweStapel = new Stapel(nieuwPersoon, HUWELIJK_OF_GP_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(nieuweStapel, true, 0);
        voegIstStapelRelatieToe(nieuweStapel, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        voegIstStapelRelatieToe(nieuweStapel, SoortRelatie.HUWELIJK);

        final VergelijkerResultaat resultaat = vergelijker.vergelijk(context, bestaandeStapel, nieuweStapel);

        final Set<Verschil> verschillen = resultaat.getVerschillen();
        assertEquals(1, verschillen.size());

        final Verschil verschil = verschillen.iterator().next();
        final Sleutel sleutel = verschil.getSleutel();
        assertTrue(sleutel instanceof IstSleutel);
        assertEquals(Stapel.VELD_RELATIES, sleutel.getVeld());
        assertEquals(VerschilType.RIJ_TOEGEVOEGD, verschil.getVerschilType());
        assertNull(verschil.getOudeWaarde());
        assertNotNull(verschil.getNieuweWaarde());
        assertTrue(verschil.getNieuweWaarde() instanceof Relatie);
        assertTrue(((Relatie) verschil.getNieuweWaarde()).getStapels().isEmpty());
    }

    /**
     * Test waarbij er een nieuwe relatie is toegevoegd aan de stapel en de oude relatie is verdwenen
     */
    @Test
    public void testNieuweEnVerdwenenRelatie() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(bestaandeStapel, true, 0);
        voegIstStapelRelatieToe(bestaandeStapel, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);

        final Stapel nieuweStapel = new Stapel(nieuwPersoon, HUWELIJK_OF_GP_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(nieuweStapel, true, 0);
        voegIstStapelRelatieToe(nieuweStapel, SoortRelatie.HUWELIJK);

        final VergelijkerResultaat resultaat = vergelijker.vergelijk(context, bestaandeStapel, nieuweStapel);

        assertEquals(2, resultaat.getVerschillen().size());

        final Verschil verschilVerwijderd = resultaat.getVerschil(VerschilType.RIJ_VERWIJDERD);
        final Sleutel sleutelVerwijderd = verschilVerwijderd.getSleutel();
        assertTrue(sleutelVerwijderd instanceof IstSleutel);
        assertEquals(Stapel.VELD_RELATIES, sleutelVerwijderd.getVeld());
        assertEquals(VerschilType.RIJ_VERWIJDERD, verschilVerwijderd.getVerschilType());
        assertNotNull(verschilVerwijderd.getOudeWaarde());
        assertNull(verschilVerwijderd.getNieuweWaarde());

        final Verschil verschilNieuw = resultaat.getVerschil(VerschilType.RIJ_TOEGEVOEGD);
        final Sleutel sleutelNieuw = verschilNieuw.getSleutel();
        assertTrue(sleutelNieuw instanceof IstSleutel);
        assertEquals(Stapel.VELD_RELATIES, sleutelNieuw.getVeld());
        assertEquals(VerschilType.RIJ_TOEGEVOEGD, verschilNieuw.getVerschilType());
        assertNull(verschilNieuw.getOudeWaarde());
        assertNotNull(verschilNieuw.getNieuweWaarde());
        assertTrue(verschilNieuw.getNieuweWaarde() instanceof Relatie);
        assertTrue(((Relatie) verschilNieuw.getNieuweWaarde()).getStapels().isEmpty());
    }

    /**
     * Test waarbij er een extra relatie is toegevoegd aan de stapel.
     */
    @Test
    public void testExtraRelatie() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(bestaandeStapel, true, 0);
        voegIstStapelRelatieToe(bestaandeStapel, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);

        final Stapel nieuweStapel = new Stapel(nieuwPersoon, HUWELIJK_OF_GP_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(nieuweStapel, true, 0);
        voegIstStapelRelatieToe(nieuweStapel, SoortRelatie.HUWELIJK);
        voegIstStapelRelatieToe(nieuweStapel, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);

        final VergelijkerResultaat resultaat = vergelijker.vergelijk(context, bestaandeStapel, nieuweStapel);

        assertEquals(1, resultaat.getVerschillen().size());

        final Verschil verschilNieuw = resultaat.getVerschil(VerschilType.RIJ_TOEGEVOEGD);
        final Sleutel sleutelNieuw = verschilNieuw.getSleutel();
        assertTrue(sleutelNieuw instanceof IstSleutel);
        assertEquals(Stapel.VELD_RELATIES, sleutelNieuw.getVeld());
        assertEquals(VerschilType.RIJ_TOEGEVOEGD, verschilNieuw.getVerschilType());
        assertNull(verschilNieuw.getOudeWaarde());
        assertNotNull(verschilNieuw.getNieuweWaarde());
        assertTrue(verschilNieuw.getNieuweWaarde() instanceof Relatie);
        assertTrue(((Relatie) verschilNieuw.getNieuweWaarde()).getStapels().isEmpty());
    }

    /**
     * Test waarbij er een bestaande relatie wordt verwijderd van de stapel.
     */
    @Test
    public void testVerwijderdeRelatie() {
        final Stapel bestaandeStapel = new Stapel(bestaandePersoon, KIND_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(bestaandeStapel, true, 0);
        voegIstStapelRelatieToe(bestaandeStapel, SoortRelatie.HUWELIJK);
        voegIstStapelRelatieToe(bestaandeStapel, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);

        final Stapel nieuweStapel = new Stapel(nieuwPersoon, HUWELIJK_OF_GP_CATEGORIE, 0);
        voegIstStapelVoorkomenToe(nieuweStapel, true, 0);
        voegIstStapelRelatieToe(nieuweStapel, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);

        final VergelijkerResultaat resultaat = vergelijker.vergelijk(context, bestaandeStapel, nieuweStapel);

        assertEquals(1, resultaat.getVerschillen().size());

        final Verschil verschilNieuw = resultaat.getVerschil(VerschilType.RIJ_VERWIJDERD);
        final Sleutel sleutelNieuw = verschilNieuw.getSleutel();
        assertTrue(sleutelNieuw instanceof IstSleutel);
        assertEquals(Stapel.VELD_RELATIES, sleutelNieuw.getVeld());
        assertEquals(VerschilType.RIJ_VERWIJDERD, verschilNieuw.getVerschilType());
        assertNotNull(verschilNieuw.getOudeWaarde());
        assertNull(verschilNieuw.getNieuweWaarde());
        assertTrue(verschilNieuw.getOudeWaarde() instanceof Relatie);
    }
}
