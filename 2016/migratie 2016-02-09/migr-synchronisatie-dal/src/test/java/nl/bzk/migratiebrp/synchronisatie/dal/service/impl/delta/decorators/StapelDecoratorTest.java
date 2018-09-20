/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Betrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortBetrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Stapel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.StapelVoorkomen;

import org.junit.Test;

/**
 * Unittests voor {@link StapelDecoratorTest}.
 */
public class StapelDecoratorTest {

    private static final Persoon PERSOON = new Persoon(SoortPersoon.INGESCHREVENE);
    private static final Partij PARTIJ = new Partij("partij", 1);
    private static final AdministratieveHandeling ADMINISTRATIEVE_HANDELING = new AdministratieveHandeling(
        PARTIJ,
        SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL);

    @Test
    public void testDecorate() {
        assertNull(StapelDecorator.decorate(null));

        final String categorie = "01";
        final int volgnummer = 0;
        final Stapel stapel = new Stapel(PERSOON, categorie, volgnummer);

        final StapelDecorator stapelDecorator = StapelDecorator.decorate(stapel);
        assertNotNull(stapelDecorator);
        assertEquals(stapel, stapelDecorator.getStapel());
        assertEquals(categorie, stapelDecorator.getCategorie());
        assertEquals(volgnummer, stapelDecorator.getStapelNummer());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetActueelStapelNummer() {
        assertEquals(1, StapelDecorator.decorate(new Stapel(PERSOON, "01", 0)).getActueelCategorienummer());
        assertEquals(1, StapelDecorator.decorate(new Stapel(PERSOON, "51", 1)).getActueelCategorienummer());
        assertEquals(1, StapelDecorator.decorate(new Stapel(PERSOON, "73", 1)).getActueelCategorienummer());
    }

    @Test
    public void testRelatiesNietHuwelijkOfGp() {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Stapel stapel = new Stapel(PERSOON, "02", 0);
        stapel.addRelatie(relatie);
        relatie.addStapel(stapel);

        final StapelDecorator stapelDecorator = StapelDecorator.decorate(stapel);
        assertNotNull(stapelDecorator);
        assertEquals(stapel, stapelDecorator.getStapel());
        assertFalse(stapelDecorator.getRelaties().isEmpty());
        assertEquals(relatie, stapelDecorator.getRelatie().getRelatie());

    }

    @Test(expected = IllegalStateException.class)
    public void testRelatiesHuwelijkOfGp() {
        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        final Relatie relatie2 = new Relatie(SoortRelatie.HUWELIJK);
        final Stapel stapel = new Stapel(PERSOON, "05", 0);
        stapel.addRelatie(relatie);
        stapel.addRelatie(relatie2);

        relatie.addStapel(stapel);
        relatie.setDatumAanvang(20150101);
        relatie2.addStapel(stapel);
        relatie2.setDatumAanvang(20130101);
        relatie2.setDatumEinde(20140101);

        final StapelDecorator stapelDecorator = StapelDecorator.decorate(stapel);
        assertNotNull(stapelDecorator);
        final List<RelatieDecorator> relaties = stapelDecorator.getRelaties();
        assertFalse(relaties.isEmpty());
        assertEquals(2, relaties.size());

        stapelDecorator.getRelatie();
    }

    @Test
    public void testVoorkomens() {
        final Stapel stapel = new Stapel(PERSOON, "02", 0);
        final StapelVoorkomen voorkomen1 = new StapelVoorkomen(stapel, 0, ADMINISTRATIEVE_HANDELING);
        final StapelVoorkomen voorkomen2 = new StapelVoorkomen(stapel, 1, ADMINISTRATIEVE_HANDELING);
        final StapelDecorator stapelDecorator = StapelDecorator.decorate(stapel);

        assertTrue(stapelDecorator.getVoorkomens().isEmpty());

        stapel.addStapelVoorkomen(voorkomen1);
        stapel.addStapelVoorkomen(voorkomen2);

        final Set<StapelVoorkomenDecorator> stapelVoorkomenDecoratorSet = stapelDecorator.getVoorkomens();
        assertEquals(2, stapelVoorkomenDecoratorSet.size());
    }

    @Test
    public void testSetVoorkomensVoorkomenVerwijderd() {
        final Stapel stapel = new Stapel(PERSOON, "02", 0);
        final StapelVoorkomen voorkomen1 = new StapelVoorkomen(stapel, 0, ADMINISTRATIEVE_HANDELING);
        voorkomen1.setDatumAanvang(20150101);
        final StapelVoorkomen voorkomen2 = new StapelVoorkomen(stapel, 1, ADMINISTRATIEVE_HANDELING);
        voorkomen2.setDatumAanvang(20130101);
        voorkomen2.setDatumEinde(20140101);

        stapel.addStapelVoorkomen(voorkomen1);
        stapel.addStapelVoorkomen(voorkomen2);

        final StapelVoorkomen voorkomen1a = new StapelVoorkomen(stapel, 0, ADMINISTRATIEVE_HANDELING);
        voorkomen1.setDatumAanvang(20160101);

        final StapelDecorator decorator = StapelDecorator.decorate(stapel);
        final Set<StapelVoorkomenDecorator> teVerwijderenVoorkomens =
                decorator.setVoorkomens(Collections.singleton(StapelVoorkomenDecorator.decorate(voorkomen1a)), false);

        assertEquals(voorkomen1a.getDatumAanvang(), voorkomen1.getDatumAanvang());
        assertFalse(teVerwijderenVoorkomens.isEmpty());
        assertEquals(voorkomen2, teVerwijderenVoorkomens.iterator().next().getVoorkomen());
        assertEquals(2, stapel.getStapelvoorkomens().size());
    }

    @Test
    public void testSetVoorkomensVoorkomenDirectVerwijderd() {
        final Stapel stapel = new Stapel(PERSOON, "02", 0);
        final StapelVoorkomen voorkomen1 = new StapelVoorkomen(stapel, 0, ADMINISTRATIEVE_HANDELING);
        voorkomen1.setDatumAanvang(20150101);
        final StapelVoorkomen voorkomen2 = new StapelVoorkomen(stapel, 1, ADMINISTRATIEVE_HANDELING);
        voorkomen2.setDatumAanvang(20130101);
        voorkomen2.setDatumEinde(20140101);

        stapel.addStapelVoorkomen(voorkomen1);
        stapel.addStapelVoorkomen(voorkomen2);

        final StapelVoorkomen voorkomen1a = new StapelVoorkomen(stapel, 0, ADMINISTRATIEVE_HANDELING);
        voorkomen1.setDatumAanvang(20160101);

        final StapelDecorator decorator = StapelDecorator.decorate(stapel);
        final Set<StapelVoorkomenDecorator> teVerwijderenVoorkomens =
                decorator.setVoorkomens(Collections.singleton(StapelVoorkomenDecorator.decorate(voorkomen1a)), true);

        assertEquals(voorkomen1a.getDatumAanvang(), voorkomen1.getDatumAanvang());
        assertTrue(teVerwijderenVoorkomens.isEmpty());
        assertEquals(1, stapel.getStapelvoorkomens().size());
    }

    @Test
    public void testSetVoorkomensVoorkomenToegevoegd() {
        final Stapel stapel = new Stapel(PERSOON, "02", 0);
        final StapelVoorkomen voorkomen1 = new StapelVoorkomen(stapel, 0, ADMINISTRATIEVE_HANDELING);
        voorkomen1.setDatumAanvang(20150101);
        stapel.addStapelVoorkomen(voorkomen1);

        final StapelVoorkomen voorkomen1a = new StapelVoorkomen(stapel, 0, ADMINISTRATIEVE_HANDELING);
        voorkomen1.setDatumAanvang(20160101);

        final StapelVoorkomen voorkomen2 = new StapelVoorkomen(stapel, 1, ADMINISTRATIEVE_HANDELING);
        voorkomen2.setDatumAanvang(20130101);
        voorkomen2.setDatumEinde(20140101);

        assertEquals(1, stapel.getStapelvoorkomens().size());
        final StapelDecorator decorator = StapelDecorator.decorate(stapel);
        final Set<StapelVoorkomenDecorator> teVerwijderenVoorkomens =
                decorator.setVoorkomens(
                    new LinkedHashSet<>(Arrays.asList(StapelVoorkomenDecorator.decorate(voorkomen1a), StapelVoorkomenDecorator.decorate(voorkomen2))),
                    false);

        assertEquals(voorkomen1a.getDatumAanvang(), voorkomen1.getDatumAanvang());
        assertTrue(teVerwijderenVoorkomens.isEmpty());
        assertEquals(2, stapel.getStapelvoorkomens().size());
    }

    @Test
    public void testSetVoorkomensEnRelatie() {
        final Stapel stapel = new Stapel(PERSOON, "02", 0);
        final StapelVoorkomen voorkomen1 = new StapelVoorkomen(stapel, 0, ADMINISTRATIEVE_HANDELING);
        voorkomen1.setDatumAanvang(20150101);

        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        final StapelDecorator decorator = StapelDecorator.decorate(stapel);
        assertTrue(decorator.getRelaties().isEmpty());
        assertTrue(decorator.getVoorkomens().isEmpty());

        final Set<StapelVoorkomenDecorator> verwijderdeVoorkomens =
                decorator.setVoorkomensEnRelaties(
                    Collections.singleton(StapelVoorkomenDecorator.decorate(voorkomen1)),
                    Collections.singletonList(RelatieDecorator.decorate(relatie)),
                    true);

        assertTrue(verwijderdeVoorkomens.isEmpty());
        assertFalse(decorator.getRelaties().isEmpty());
        assertFalse(decorator.getVoorkomens().isEmpty());
    }

    @Test
    public void testIsTypeRelatieMethodes() {
        final StapelDecorator ouder1Stapel = StapelDecorator.decorate(new Stapel(PERSOON, "02", 0));
        final StapelDecorator ouder2Stapel = StapelDecorator.decorate(new Stapel(PERSOON, "03", 0));
        final StapelDecorator huwelijkOfGpStapel = StapelDecorator.decorate(new Stapel(PERSOON, "05", 0));
        final StapelDecorator kindStapel = StapelDecorator.decorate(new Stapel(PERSOON, "09", 0));
        final StapelDecorator gezagsverhoudingStapel = StapelDecorator.decorate(new Stapel(PERSOON, "11", 0));
        assertTrue(ouder1Stapel.isOuder1Stapel());
        assertTrue(ouder1Stapel.isOuderStapel());
        assertFalse(ouder1Stapel.isOuder2Stapel());
        assertFalse(ouder1Stapel.isHuwelijkOfGpStapel());
        assertFalse(ouder1Stapel.isKindStapel());
        assertFalse(ouder1Stapel.isGezagsverhoudingStapel());

        assertFalse(ouder2Stapel.isOuder1Stapel());
        assertTrue(ouder2Stapel.isOuder2Stapel());
        assertTrue(ouder2Stapel.isOuderStapel());
        assertFalse(ouder2Stapel.isHuwelijkOfGpStapel());
        assertFalse(ouder2Stapel.isKindStapel());
        assertFalse(ouder2Stapel.isGezagsverhoudingStapel());

        assertFalse(huwelijkOfGpStapel.isOuder1Stapel());
        assertFalse(huwelijkOfGpStapel.isOuder2Stapel());
        assertFalse(huwelijkOfGpStapel.isOuderStapel());
        assertTrue(huwelijkOfGpStapel.isHuwelijkOfGpStapel());
        assertFalse(huwelijkOfGpStapel.isKindStapel());
        assertFalse(huwelijkOfGpStapel.isGezagsverhoudingStapel());

        assertFalse(kindStapel.isOuder1Stapel());
        assertFalse(kindStapel.isOuder2Stapel());
        assertFalse(kindStapel.isOuderStapel());
        assertFalse(kindStapel.isHuwelijkOfGpStapel());
        assertTrue(kindStapel.isKindStapel());
        assertFalse(kindStapel.isGezagsverhoudingStapel());

        assertFalse(gezagsverhoudingStapel.isOuder1Stapel());
        assertFalse(gezagsverhoudingStapel.isOuder2Stapel());
        assertFalse(gezagsverhoudingStapel.isOuderStapel());
        assertFalse(gezagsverhoudingStapel.isHuwelijkOfGpStapel());
        assertFalse(gezagsverhoudingStapel.isKindStapel());
        assertTrue(gezagsverhoudingStapel.isGezagsverhoudingStapel());
    }

    @Test
    public void testGetActueelVoorkomen() {
        final Stapel stapel = new Stapel(PERSOON, "02", 0);
        final StapelVoorkomen voorkomen1 = new StapelVoorkomen(stapel, 0, ADMINISTRATIEVE_HANDELING);
        final StapelVoorkomen voorkomen2 = new StapelVoorkomen(stapel, 1, ADMINISTRATIEVE_HANDELING);
        stapel.addStapelVoorkomen(voorkomen2);

        final StapelDecorator stapelDecorator = StapelDecorator.decorate(stapel);
        assertNull(stapelDecorator.getActueelVoorkomen());

        stapel.addStapelVoorkomen(voorkomen1);
        assertEquals(voorkomen1, stapelDecorator.getActueelVoorkomen().getVoorkomen());
    }

    @Test
    public void testBevatStapelVoorkomen() {
        final int datumEinde = 20150101;
        final String aktenummer = "1A 2345";
        final String aktenummer2 = "1A 6789";
        final Stapel stapel = new Stapel(PERSOON, "02", 0);
        final StapelVoorkomen voorkomen1 = new StapelVoorkomen(stapel, 0, ADMINISTRATIEVE_HANDELING);
        final StapelVoorkomen voorkomen2 = new StapelVoorkomen(stapel, 1, ADMINISTRATIEVE_HANDELING);

        voorkomen1.setDatumEinde(datumEinde);
        voorkomen1.setAktenummer(aktenummer);
        voorkomen2.setAktenummer(aktenummer2);

        stapel.addStapelVoorkomen(voorkomen1);
        final StapelDecorator stapelDecorator = StapelDecorator.decorate(stapel);
        assertTrue(stapelDecorator.bevatStapelVoorkomen(StapelVoorkomenDecorator.decorate(voorkomen1)));
        assertFalse(stapelDecorator.bevatStapelVoorkomen(StapelVoorkomenDecorator.decorate(voorkomen2)));

        voorkomen2.setDatumEinde(datumEinde);
        assertTrue(stapelDecorator.bevatStapelVoorkomen(StapelVoorkomenDecorator.decorate(voorkomen2)));
    }

    @Test
    public void testToString() {
        final Stapel stapel = new Stapel(PERSOON, "02", 0);
        final StapelDecorator stapelDecorator = StapelDecorator.decorate(stapel);
        assertEquals("Categorie 02, stapel 0", stapelDecorator.toString());
    }

    @Test
    public void testIsVoorkomenMatch() {
        final Stapel stapel = new Stapel(PERSOON, "02", 0);
        final StapelVoorkomen voorkomen1 = new StapelVoorkomen(stapel, 0, ADMINISTRATIEVE_HANDELING);
        final StapelVoorkomen voorkomen2 = new StapelVoorkomen(stapel, 1, ADMINISTRATIEVE_HANDELING);
        final StapelVoorkomen voorkomen3 = new StapelVoorkomen(stapel, 2, ADMINISTRATIEVE_HANDELING);

        stapel.addStapelVoorkomen(voorkomen1);
        final StapelDecorator stapelDecorator = StapelDecorator.decorate(stapel);
        final Set<StapelVoorkomenDecorator> andereVoorkomens = Collections.singleton(StapelVoorkomenDecorator.decorate(voorkomen2));
        assertFalse(stapelDecorator.isVoorkomenSetMatch(andereVoorkomens, true));
        assertTrue(stapelDecorator.isVoorkomenSetMatch(andereVoorkomens, false));

        voorkomen2.setAktenummer("1A 2345");
        assertFalse(stapelDecorator.isVoorkomenSetMatch(andereVoorkomens, false));
        assertFalse(stapelDecorator.isVoorkomenSetMatch(Collections.singleton(StapelVoorkomenDecorator.decorate(voorkomen3)), false));

        stapel.addStapelVoorkomen(voorkomen2);
        voorkomen2.setAktenummer(null);
        assertFalse(stapelDecorator.isVoorkomenSetMatch(andereVoorkomens, false));
    }

    @Test
    public void testEqualsAndHashCode() {
        final StapelDecorator sd10 = StapelDecorator.decorate(new Stapel(PERSOON, "01", 0));
        final StapelDecorator sd10Andere = StapelDecorator.decorate(new Stapel(PERSOON, "01", 0));
        final StapelDecorator sd11 = StapelDecorator.decorate(new Stapel(PERSOON, "01", 1));
        final StapelDecorator sd20 = StapelDecorator.decorate(new Stapel(PERSOON, "02", 0));

        assertTrue(sd10.equals(sd10));
        assertTrue(sd10.equals(sd10));
        assertTrue(sd10.equals(sd10Andere));
        assertFalse(sd10.equals(new String("Ander object")));
        assertFalse(sd10.equals(sd11));
        assertFalse(sd10.equals(sd20));
        assertFalse(sd11.equals(sd10));
        assertFalse(sd11.equals(sd20));
        assertFalse(sd20.equals(sd10));
        assertFalse(sd20.equals(sd11));
        assertTrue(sd10.hashCode() == sd10.hashCode());
        assertTrue(sd10.hashCode() == sd10Andere.hashCode());
        assertFalse(sd10.hashCode() == sd11.hashCode());
        assertFalse(sd10.hashCode() == sd20.hashCode());
    }

    @Test
    public void testBevatRelatie() {
        final Stapel stapel = new Stapel(PERSOON, "02", 0);
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Betrokkenheid betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, relatie);
        final Betrokkenheid betrokkenheidNietInRelatie = new Betrokkenheid(SoortBetrokkenheid.KIND, relatie);

        relatie.addBetrokkenheid(betrokkenheid);
        stapel.addRelatie(relatie);

        final StapelDecorator sd = StapelDecorator.decorate(stapel);
        assertTrue(sd.bevatRelatie(BetrokkenheidDecorator.decorate(betrokkenheid)));
        assertFalse(sd.bevatRelatie(BetrokkenheidDecorator.decorate(betrokkenheidNietInRelatie)));
    }

    @Test
    public void testVoegNieuwActueelVoorkomenToe() {
        final Integer datum = 20150101;
        final Integer datum2 = 20160101;

        final Stapel stapel = new Stapel(PERSOON, "01", 0);
        final StapelDecorator sd = StapelDecorator.decorate(stapel);

        assertTrue(sd.getVoorkomens().isEmpty());
        final StapelVoorkomen sv = new StapelVoorkomen(stapel, 0, ADMINISTRATIEVE_HANDELING);
        final StapelVoorkomen sv1 = new StapelVoorkomen(stapel, 1, ADMINISTRATIEVE_HANDELING);
        sv.setDatumEinde(datum);
        final StapelVoorkomen sv2 = new StapelVoorkomen(stapel, 0, ADMINISTRATIEVE_HANDELING);
        sv2.setDatumEinde(datum2);

        final StapelVoorkomenDecorator svDecorator = StapelVoorkomenDecorator.decorate(sv);
        sd.voegNieuwActueelVoorkomenToe(svDecorator, Collections.<StapelVoorkomenDecorator>emptySet());
        assertEquals(1, sd.getVoorkomens().size());

        sd.voegNieuwActueelVoorkomenToe(StapelVoorkomenDecorator.decorate(sv2), Collections.singleton(svDecorator));
        assertEquals(1, sd.getVoorkomens().size());

        stapel.getStapelvoorkomens().clear();
        stapel.addStapelVoorkomen(sv);
        stapel.addStapelVoorkomen(sv1);
        final Set<StapelVoorkomenDecorator> verwijderdeVoorkomens = new HashSet<>();
        verwijderdeVoorkomens.add(StapelVoorkomenDecorator.decorate(sv1));
        sd.voegNieuwActueelVoorkomenToe(StapelVoorkomenDecorator.decorate(sv2), verwijderdeVoorkomens);
        assertEquals(2, sd.getVoorkomens().size());

        stapel.getStapelvoorkomens().clear();
        stapel.addStapelVoorkomen(sv);
        sd.voegNieuwActueelVoorkomenToe(StapelVoorkomenDecorator.decorate(sv1), Collections.<StapelVoorkomenDecorator>emptySet());
        assertEquals(2, sd.getVoorkomens().size());
    }

    @Test
    public void testKoppelOntkoppelRelatie() {
        final Stapel stapel = new Stapel(PERSOON, "05", 0);
        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        final RelatieDecorator rd = RelatieDecorator.decorate(relatie);

        final StapelDecorator sd = StapelDecorator.decorate(stapel);

        assertTrue(sd.getRelaties().isEmpty());
        sd.koppelRelatie(rd);
        assertFalse(sd.getRelaties().isEmpty());
        sd.ontkoppelRelatie(rd);
        assertTrue(sd.getRelaties().isEmpty());
    }

    @Test
    public void testOntkoppelRelaties() {
        final Stapel stapel = new Stapel(PERSOON, "05", 0);
        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        final RelatieDecorator rd = RelatieDecorator.decorate(relatie);

        final StapelDecorator sd = StapelDecorator.decorate(stapel);

        assertTrue(sd.getRelaties().isEmpty());
        sd.koppelRelatie(rd);
        assertFalse(sd.getRelaties().isEmpty());
        sd.ontkoppelRelaties();
        assertTrue(sd.getRelaties().isEmpty());
    }

    @Test
    public void testGetOverigeRelaties() {
        final Stapel stapel = new Stapel(PERSOON, "05", 0);
        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        relatie.setDatumAanvang(20150101);
        final Relatie relatie2 = new Relatie(SoortRelatie.HUWELIJK);
        relatie2.setDatumAanvang(20160101);

        stapel.addRelatie(relatie);
        stapel.addRelatie(relatie2);
        final StapelDecorator sd = StapelDecorator.decorate(stapel);

        assertFalse(sd.getOverigeRelaties(Collections.singleton(RelatieDecorator.decorate(relatie)), false).isEmpty());
    }

}
