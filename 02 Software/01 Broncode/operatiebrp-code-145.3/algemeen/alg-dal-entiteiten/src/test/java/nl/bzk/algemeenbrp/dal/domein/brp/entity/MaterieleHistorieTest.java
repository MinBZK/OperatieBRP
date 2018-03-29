/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import org.junit.Test;

/**
 * Testen voor {@link MaterieleHistorie}.
 */
public class MaterieleHistorieTest extends FormeleHistorieTest {

    @Test(expected = NullPointerException.class)
    public void testActueelVoorkomenisNietActueelDoorDatumAanvangGeldigheid() {
        // setup
        final Timestamp datumTijdRegistratie = maakDatumTijd(2013, 1, 1);
        final PersoonIDHistorie id = new PersoonIDHistorie(persoon);
        id.setDatumTijdRegistratie(datumTijdRegistratie);
        id.setActieInhoud(maakActie(maakAdministratieveHandling(datumTijdRegistratie)));
        MaterieleHistorie.voegNieuweActueleToe(id, persoon.getPersoonIDHistorieSet());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testActueelVoorkomenisNietActueelDoorActieAanpassingGeldigheid() {
        // setup
        final Timestamp datumTijdRegistratie = maakDatumTijd(2013, 1, 1);
        final PersoonIDHistorie id = maakIDVoorkomen(persoon, datumTijdRegistratie, 20130101, maakAdministratieveHandling(datumTijdRegistratie));
        id.setActieAanpassingGeldigheid(maakActie(maakAdministratieveHandling(datumTijdRegistratie)));
        MaterieleHistorie.voegNieuweActueleToe(id, persoon.getPersoonIDHistorieSet());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testActueelVoorkomenisNietActueelDoorOnjuisteGeldigheidsperiode() {
        // setup
        final Timestamp datumTijdRegistratie = maakDatumTijd(2013, 1, 1);
        final PersoonIDHistorie id = maakIDVoorkomen(persoon, datumTijdRegistratie, 20130101, maakAdministratieveHandling(datumTijdRegistratie));
        id.setDatumEindeGeldigheid(id.getDatumAanvangGeldigheid() - 1);
        MaterieleHistorie.voegNieuweActueleToe(id, persoon.getPersoonIDHistorieSet());
    }

    /**
     * <ul> <li>____________________________</li> <li>|_____A'__________|____B____</li> <li>|_____A_____________________</li> </ul> <p> 1-1-10 1-5-10
     * Beginsituatie: A record. B wordt toegevoegd. EindSituatie: 1 D laag record en 2 C laag records.
     */
    @Test
    public void testVoegMaterieleHistorieToe() {
        // setup
        final Timestamp datumTijdRegistratieA = maakDatumTijd(2010, 1, 2);
        final Timestamp datumTijdRegistratieB = maakDatumTijd(2010, 5, 2);
        final PersoonIDHistorie idA = maakIDVoorkomen(persoon, datumTijdRegistratieA, 20100101, maakAdministratieveHandling(datumTijdRegistratieA));
        final PersoonIDHistorie idB = maakIDVoorkomen(persoon, datumTijdRegistratieB, 20100501, maakAdministratieveHandling(datumTijdRegistratieB));
        // execute
        MaterieleHistorie.voegNieuweActueleToe(idA, persoon.getPersoonIDHistorieSet());
        MaterieleHistorie.voegNieuweActueleToe(idB, persoon.getPersoonIDHistorieSet());
        // valideer
        assertEquals(3, persoon.getPersoonIDHistorieSet().size());
        PersoonIDHistorie voorkomen1 = null;
        PersoonIDHistorie voorkomen2 = null;
        PersoonIDHistorie voorkomen3 = null;

        for (final PersoonIDHistorie voorkomen : persoon.getPersoonIDHistorieSet()) {
            if (voorkomen.isVervallen()) {
                voorkomen1 = voorkomen;
            } else if (voorkomen.getDatumEindeGeldigheid() != null) {
                voorkomen2 = voorkomen;
            } else {
                voorkomen3 = voorkomen;
            }
        }
        assertNotNull(voorkomen1);
        assertNotNull(voorkomen2);
        assertNotNull(voorkomen3);
        assertEquals(voorkomen1.getDatumTijdVerval(), voorkomen3.getDatumTijdRegistratie());
        assertEquals(datumTijdRegistratieB, voorkomen2.getDatumTijdRegistratie());
        assertEquals(voorkomen1.getDatumAanvangGeldigheid(), voorkomen2.getDatumAanvangGeldigheid());
        assertEquals(voorkomen2.getDatumEindeGeldigheid(), voorkomen3.getDatumAanvangGeldigheid());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testVoegMaterieleHistorieToeMetCorrectie() {
        // setup
        final Timestamp datumTijdRegistratieA = maakDatumTijd(2010, 2, 2);
        final Timestamp datumTijdRegistratieB = maakDatumTijd(2010, 1, 2);
        final PersoonIDHistorie idA = maakIDVoorkomen(persoon, datumTijdRegistratieA, 20100201, maakAdministratieveHandling(datumTijdRegistratieA));
        final PersoonIDHistorie idB = maakIDVoorkomen(persoon, datumTijdRegistratieA, 20100101, maakAdministratieveHandling(datumTijdRegistratieB));
        // execute
        MaterieleHistorie.voegNieuweActueleToe(idA, persoon.getPersoonIDHistorieSet());
        MaterieleHistorie.voegNieuweActueleToe(idB, persoon.getPersoonIDHistorieSet());
    }

    @Test
    public void testVoegMaterieleHistorieToeMetValideGeldigheid() {
        // setup
        final Timestamp datumTijdRegistratieA = maakDatumTijd(2010, 2, 2);
        final PersoonIDHistorie idA = maakIDVoorkomen(persoon, datumTijdRegistratieA, 20100201, maakAdministratieveHandling(datumTijdRegistratieA));
        idA.setDatumEindeGeldigheid(idA.getDatumAanvangGeldigheid() + 1);
        // execute
        MaterieleHistorie.voegNieuweActueleToe(idA, persoon.getPersoonIDHistorieSet());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testVoegMaterieleHistorieToeMetInValideGeldigheidStrengeControle() {
        // setup
        final Timestamp datumTijdRegistratieA = maakDatumTijd(2001, 1, 1);
        final Timestamp datumTijdRegistratieB = maakDatumTijd(2001, 1, 15);
        final PersoonIDHistorie idA = maakIDVoorkomen(persoon, datumTijdRegistratieA, 20010100, maakAdministratieveHandling(datumTijdRegistratieA));
        final PersoonIDHistorie idB = maakIDVoorkomen(persoon, datumTijdRegistratieB, 20010115, maakAdministratieveHandling(datumTijdRegistratieA));
        // execute
        MaterieleHistorie.voegNieuweActueleToe(idA, persoon.getPersoonIDHistorieSet());
        MaterieleHistorie.voegNieuweActueleToe(idB, persoon.getPersoonIDHistorieSet());
    }

    @Test
    public void testBeeindigHistorie() {
        final PersoonMigratieHistorie historie = new PersoonMigratieHistorie(persoon, SoortMigratie.EMIGRATIE);
        final Set<PersoonMigratieHistorie> migratieHistories = new LinkedHashSet<>();
        migratieHistories.add(historie);

        final Timestamp datumTijdRegistratie = maakDatumTijd(2010, 1, 2);
        final AdministratieveHandeling administratieveHandeling = maakAdministratieveHandling(datumTijdRegistratie);
        final BRPActie actie = maakActie(administratieveHandeling);

        final Integer datumEindeGeldigheid = 20160101;
        MaterieleHistorie.beeindigActueelVoorkomen(migratieHistories, actie, datumTijdRegistratie, datumEindeGeldigheid);

        assertEquals(2, migratieHistories.size());
        assertEquals(datumTijdRegistratie, historie.getDatumTijdVerval());
        assertEquals(actie, historie.getActieVerval());

        Iterator<PersoonMigratieHistorie> iter = migratieHistories.iterator();
        iter.next();
        final PersoonMigratieHistorie historieBeeindigd = iter.next();
        assertEquals(datumEindeGeldigheid, historieBeeindigd.getDatumEindeGeldigheid());
        assertEquals(actie, historieBeeindigd.getActieAanpassingGeldigheid());

        // Test dat als er geen actueel/geldige record is, dan moet de beeindiging niet opnieuw worden uitgevoerd
        MaterieleHistorie.beeindigActueelVoorkomen(migratieHistories, actie, datumTijdRegistratie, datumEindeGeldigheid);
        assertEquals(2, migratieHistories.size());
    }

    @Test
    public void testBepalenActuele() {
        final Set<PersoonIDHistorie> voorkomens = new LinkedHashSet<>();
        final Timestamp t1 = maakDatumTijd(2001, 1, 1);
        MaterieleHistorie.voegNieuweActueleToe(maakIDVoorkomen(persoon, t1, 20010101, maakAdministratieveHandling(t1)), voorkomens);
        final Timestamp t2 = maakDatumTijd(2003, 6, 15);
        MaterieleHistorie.voegNieuweActueleToe(maakIDVoorkomen(persoon, t2, 20030615, maakAdministratieveHandling(t2)), voorkomens);
        final Timestamp t3 = maakDatumTijd(2005, 6, 15);
        MaterieleHistorie.voegNieuweActueleToe(maakIDVoorkomen(persoon, t3, 20050603, maakAdministratieveHandling(t3)), voorkomens);
        final Timestamp t4 = maakDatumTijd(2009, 6, 15);
        MaterieleHistorie.voegNieuweActueleToe(maakIDVoorkomen(persoon, t4, 20090615, maakAdministratieveHandling(t4)), voorkomens);
        final Timestamp t5 = maakDatumTijd(2011, 6, 15);
        MaterieleHistorie.voegNieuweActueleToe(maakIDVoorkomen(persoon, t5, 20110615, maakAdministratieveHandling(t5)), voorkomens);
        assertNull(MaterieleHistorie.getGeldigVoorkomenOpPeildatum(voorkomens, 19990101));
        assertEquals(20010101, MaterieleHistorie.getGeldigVoorkomenOpPeildatum(voorkomens, 20020101).getDatumAanvangGeldigheid().intValue());
        assertEquals(20030615, MaterieleHistorie.getGeldigVoorkomenOpPeildatum(voorkomens, 20050600).getDatumAanvangGeldigheid().intValue());
        assertEquals(20050603, MaterieleHistorie.getGeldigVoorkomenOpPeildatum(voorkomens, 20050700).getDatumAanvangGeldigheid().intValue());
        assertEquals(20110615, MaterieleHistorie.getGeldigVoorkomenOpPeildatum(voorkomens, 20160701).getDatumAanvangGeldigheid().intValue());
    }

    @Test(expected = NullPointerException.class)
    public void testVoegNullVoorkomenToe() {
        final Set<PersoonIDHistorie> voorkomens = new LinkedHashSet<>();
        MaterieleHistorie.voegVoorkomenToe(null, voorkomens);
    }

    @Test()
    public void testVoegVoorkomenToe() {
        final Set<PersoonIDHistorie> voorkomens = new LinkedHashSet<>();
        final PersoonIDHistorie voorkomen = new PersoonIDHistorie(persoon);
        MaterieleHistorie.voegVoorkomenToe(voorkomen, voorkomens);
        assertEquals(1, voorkomens.size());
    }

    @Test()
    public void testVoegNieuwActueelVoorkomenToe() {
        final Set<PersoonIDHistorie> voorkomens = new LinkedHashSet<>();
        final PersoonIDHistorie voorkomen = new PersoonIDHistorie(persoon);
        voorkomen.setDatumAanvangGeldigheid(2010_01_01);

        final PersoonIDHistorie voorkomen2 = new PersoonIDHistorie(persoon);
        voorkomen2.setDatumAanvangGeldigheid(2016_01_01);

        MaterieleHistorie.voegVoorkomenToe(voorkomen, voorkomens);
        MaterieleHistorie.voegVoorkomenToe(voorkomen2, voorkomens);
        assertEquals(2, voorkomens.size());
        assertNull(voorkomen.getActieVerval());
        assertNull(voorkomen.getDatumEindeGeldigheid());
        assertNull(voorkomen2.getActieVerval());
    }

    @Test
    public void testIsDegGelijkAanDagToegestaan() {
        final Betrokkenheid betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING));

        assertTrue(new BetrokkenheidOuderHistorie(new BetrokkenheidOuderHistorie(betrokkenheid)).isDegGelijkAanDagToegestaan());
        assertTrue(new PersoonGeslachtsnaamcomponentHistorie(new PersoonGeslachtsnaamcomponent(persoon, 1), "Stam").isDegGelijkAanDagToegestaan());
        assertTrue(new PersoonNationaliteitHistorie(new PersoonNationaliteit(persoon, new Nationaliteit("nl", "0001"))).isDegGelijkAanDagToegestaan());
        assertTrue(new PersoonVoornaamHistorie(new PersoonVoornaam(persoon, 1), "Piet").isDegGelijkAanDagToegestaan());
        assertTrue(new PersoonBijhoudingHistorie(persoon, new Partij("partij", "000001"), Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL)
                .isDegGelijkAanDagToegestaan());
        assertTrue(new PersoonSamengesteldeNaamHistorie(persoon, "Stam", true, false).isDegGelijkAanDagToegestaan());
        assertFalse(new PersoonIDHistorie(persoon).isDegGelijkAanDagToegestaan());
        assertFalse(new PersoonMigratieHistorie(persoon, SoortMigratie.EMIGRATIE).isDegGelijkAanDagToegestaan());
        assertFalse(new BetrokkenheidOuderlijkGezagHistorie(betrokkenheid, true).isDegGelijkAanDagToegestaan());
        assertFalse(new PersoonAdresHistorie(new PersoonAdres(persoon), SoortAdres.BRIEFADRES, new LandOfGebied("0001", "NL"),
                new RedenWijzigingVerblijf('A', "Ambtshalve")).isDegGelijkAanDagToegestaan());
        assertFalse(new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.MAN).isDegGelijkAanDagToegestaan());
        assertFalse(new PersoonNummerverwijzingHistorie(persoon).isDegGelijkAanDagToegestaan());

        // Indicaties
        assertFalse(new PersoonIndicatieHistorie(new PersoonIndicatie(persoon, SoortIndicatie.BEHANDELD_ALS_NEDERLANDER), true).isDegGelijkAanDagToegestaan());
        assertFalse(
                new PersoonIndicatieHistorie(new PersoonIndicatie(persoon, SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER), true).isDegGelijkAanDagToegestaan());
        assertFalse(new PersoonIndicatieHistorie(new PersoonIndicatie(persoon, SoortIndicatie.ONDER_CURATELE), true).isDegGelijkAanDagToegestaan());
        assertFalse(new PersoonIndicatieHistorie(new PersoonIndicatie(persoon, SoortIndicatie.DERDE_HEEFT_GEZAG), true).isDegGelijkAanDagToegestaan());
        assertTrue(new PersoonIndicatieHistorie(new PersoonIndicatie(persoon, SoortIndicatie.STAATLOOS), true).isDegGelijkAanDagToegestaan());
        assertTrue(new PersoonIndicatieHistorie(new PersoonIndicatie(persoon, SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE), true)
                .isDegGelijkAanDagToegestaan());
    }


    @Test
    public void testZijnGatenInHistorieToegestaan() {
        assertTrue(new PersoonGeslachtsnaamcomponentHistorie(new PersoonGeslachtsnaamcomponent(persoon, 1), "Stam").moetHistorieAaneengeslotenZijn());
        assertTrue(new PersoonBijhoudingHistorie(persoon, new Partij("partij", "000001"), Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL)
                .moetHistorieAaneengeslotenZijn());
        assertTrue(new PersoonSamengesteldeNaamHistorie(persoon, "Stam", true, false).moetHistorieAaneengeslotenZijn());
        assertTrue(new PersoonMigratieHistorie(persoon, SoortMigratie.EMIGRATIE).moetHistorieAaneengeslotenZijn());
        assertTrue(new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.MAN).moetHistorieAaneengeslotenZijn());

        final Betrokkenheid betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING));
        assertFalse(new BetrokkenheidOuderHistorie(new BetrokkenheidOuderHistorie(betrokkenheid)).moetHistorieAaneengeslotenZijn());
        assertFalse(new BetrokkenheidOuderlijkGezagHistorie(betrokkenheid, true).moetHistorieAaneengeslotenZijn());
        assertFalse(new PersoonNationaliteitHistorie(new PersoonNationaliteit(persoon, new Nationaliteit("nl", "0001"))).moetHistorieAaneengeslotenZijn());
        assertFalse(new PersoonVoornaamHistorie(new PersoonVoornaam(persoon, 1), "Piet").moetHistorieAaneengeslotenZijn());
        assertFalse(new PersoonIDHistorie(persoon).moetHistorieAaneengeslotenZijn());
        assertFalse(new PersoonAdresHistorie(new PersoonAdres(persoon), SoortAdres.BRIEFADRES, new LandOfGebied("0001", "NL"),
                new RedenWijzigingVerblijf('A', "Ambtshalve")).moetHistorieAaneengeslotenZijn());
        assertFalse(new PersoonNummerverwijzingHistorie(persoon).moetHistorieAaneengeslotenZijn());

        // Indicaties
        assertTrue(new PersoonIndicatieHistorie(new PersoonIndicatie(persoon, SoortIndicatie.BEHANDELD_ALS_NEDERLANDER), true).moetHistorieAaneengeslotenZijn());
        assertTrue(
                new PersoonIndicatieHistorie(new PersoonIndicatie(persoon, SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER), true).moetHistorieAaneengeslotenZijn());
        assertFalse(new PersoonIndicatieHistorie(new PersoonIndicatie(persoon, SoortIndicatie.ONDER_CURATELE), true).moetHistorieAaneengeslotenZijn());
        assertFalse(new PersoonIndicatieHistorie(new PersoonIndicatie(persoon, SoortIndicatie.DERDE_HEEFT_GEZAG), true).moetHistorieAaneengeslotenZijn());
        assertFalse(new PersoonIndicatieHistorie(new PersoonIndicatie(persoon, SoortIndicatie.STAATLOOS), true).moetHistorieAaneengeslotenZijn());
        assertFalse(new PersoonIndicatieHistorie(new PersoonIndicatie(persoon, SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE), true)
                .moetHistorieAaneengeslotenZijn());
    }
}
