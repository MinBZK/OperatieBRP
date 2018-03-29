/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonDeelnameEuVerkiezingenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonMigratieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonOverlijdenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;

/**
 * Unittest voor {@link ActieConsolidatieData}.
 */
public class ActieConsolidatieDataTest {

    private ActieConsolidatieData data;
    private Partij partij;
    private AdministratieveHandeling administratieveHandeling;
    private Timestamp timestamp;
    private Persoon persoon;
    private LandOfGebied landOfGebied;

    @Before
    public void setUp() {
        SynchronisatieLogging.init();
        data = new ActieConsolidatieData();
        partij = new Partij("partij", "000001");
        administratieveHandeling =
                new AdministratieveHandeling(partij, SoortAdministratieveHandeling.GBA_INITIELE_VULLING, new Timestamp(System.currentTimeMillis()));
        persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        timestamp = Timestamp.valueOf("2015-01-01 01:00:00.000");
        landOfGebied = new LandOfGebied("0001", "Land");
    }

    @Test
    public void testBepaalTeVervangen() throws NoSuchFieldException {
        // Nodig: 5 BRPStapels, 2 rijen per stapel, 1 actie per rij. Aangezien stapels ook acties delen zijn er minder
        // dan 16 acties nodig.
        // Acties 0-3 zijn bestaande acties, acties 4-7 zijn voor de nieuwe versies, 8-11 zijn voor een BRPStapel die
        // niet geraakt wordt.

        final BRPActie[] acties = new BRPActie[12];
        for (int index = 0; index < acties.length; index++) {
            acties[index] = maakBRPActie((long) index);
        }

        /*
         * Historie stapels hebben onderling normaal gesproken geen verband (op adres en migratie na), alleen voor deze
         * test nu zo ingericht.
         */
        voegAdresStapelToe(acties[0], acties[1], acties[4], acties[5]);
        voegMigratieStapelToe(acties[1], acties[0], acties[5], acties[4]);
        voegIDHistorieToe(acties[1], acties[2], acties[5], acties[6]);
        voegOverlijdenHistorieToe(acties[2], acties[3], acties[6], acties[7]);
        voegDeelnameEuVerkiezingenHistorieToe(acties[8], acties[9], acties[10], acties[11]);

        final ConsolidatieData aangevuldeData = data.voegNieuweActiesToe(Collections.singleton(acties[4]));
        final Set<DeltaStapelMatch> deltaStapelMatches = aangevuldeData.bepaalTeVervangenStapels();
        assertEquals(4, deltaStapelMatches.size());

        boolean result = false;
        for (final DeltaStapelMatch match : deltaStapelMatches) {
            final Class<?> historie = match.getOpgeslagenRijen().iterator().next().getClass();
            if (historie.isAssignableFrom(PersoonAdresHistorie.class)
                    || historie.isAssignableFrom(PersoonMigratieHistorie.class)
                    || historie.isAssignableFrom(PersoonIDHistorie.class)
                    || historie.isAssignableFrom(PersoonOverlijdenHistorie.class)) {
                result = true;
            } else {
                result = false;
                break;
            }
        }
        assertTrue(result);
    }

    private BRPActie maakBRPActie(final long id) {
        final BRPActie actie = new BRPActie(SoortActie.CONVERSIE_GBA, administratieveHandeling, partij, timestamp);
        actie.setId(id + 1);
        return actie;
    }

    private void voegAdresStapelToe(final BRPActie oudeActie1, final BRPActie oudeActie2, final BRPActie nieuweActie1, final BRPActie nieuweActie2)
            throws NoSuchFieldException {
        final PersoonAdres bestaandeAdres = maakAdresStapel(oudeActie1, oudeActie2);
        final PersoonAdres nieuweAdres = maakAdresStapel(nieuweActie1, nieuweActie2);
        final Collection<?> bestaandeHistorie = bestaandeAdres.getPersoonAdresHistorieSet();
        final Collection<?> nieuweHistorie = nieuweAdres.getPersoonAdresHistorieSet();

        data.koppelNieuweActieMetOudeActie(nieuweActie1, oudeActie1);
        data.koppelNieuweActieMetOudeActie(nieuweActie2, oudeActie2);
        voegAanDataToe(
                (Collection<FormeleHistorie>) bestaandeHistorie,
                (Collection<FormeleHistorie>) nieuweHistorie,
                PersoonAdres.class.getDeclaredField("persoonAdresHistorieSet"));
    }

    private void voegMigratieStapelToe(final BRPActie oudeActie1, final BRPActie oudeActie2, final BRPActie nieuweActie1, final BRPActie nieuweActie2)
            throws NoSuchFieldException {
        final Collection<?> bestaandeHistorie = maakMigratieHistorie(oudeActie1, oudeActie2);
        final Collection<?> nieuweHistorie = maakMigratieHistorie(nieuweActie1, nieuweActie2);
        data.koppelNieuweActieMetOudeActie(nieuweActie1, oudeActie1);
        data.koppelNieuweActieMetOudeActie(nieuweActie2, oudeActie2);

        voegAanDataToe(
                (Collection<FormeleHistorie>) bestaandeHistorie,
                (Collection<FormeleHistorie>) nieuweHistorie,
                Persoon.class.getDeclaredField("persoonMigratieHistorieSet"));
    }

    private void voegIDHistorieToe(final BRPActie oudeActie1, final BRPActie oudeActie2, final BRPActie nieuweActie1, final BRPActie nieuweActie2)
            throws NoSuchFieldException {
        final Collection<?> bestaandeHistorie = maakIDHistorie(oudeActie1, oudeActie2);
        final Collection<?> nieuweHistorie = maakIDHistorie(nieuweActie1, nieuweActie2);

        data.koppelNieuweActieMetOudeActie(nieuweActie1, oudeActie1);
        data.koppelNieuweActieMetOudeActie(nieuweActie2, oudeActie2);

        voegAanDataToe(
                (Collection<FormeleHistorie>) bestaandeHistorie,
                (Collection<FormeleHistorie>) nieuweHistorie,
                Persoon.class.getDeclaredField("persoonIDHistorieSet"));

    }

    private void voegOverlijdenHistorieToe(final BRPActie oudeActie1, final BRPActie oudeActie2, final BRPActie nieuweActie1, final BRPActie nieuweActie2)
            throws NoSuchFieldException {
        final Collection<?> bestaandeHistorie = maakOverlijdenHistorie(oudeActie1, oudeActie2);
        final Collection<?> nieuweHistorie = maakOverlijdenHistorie(nieuweActie1, nieuweActie2);
        data.koppelNieuweActieMetOudeActie(nieuweActie1, oudeActie1);
        data.koppelNieuweActieMetOudeActie(nieuweActie2, oudeActie2);

        voegAanDataToe(
                (Collection<FormeleHistorie>) bestaandeHistorie,
                (Collection<FormeleHistorie>) nieuweHistorie,
                Persoon.class.getDeclaredField("persoonOverlijdenHistorieSet"));
    }

    private void voegDeelnameEuVerkiezingenHistorieToe(
            final BRPActie oudeActie1,
            final BRPActie oudeActie2,
            final BRPActie nieuweActie1,
            final BRPActie nieuweActie2) throws NoSuchFieldException {
        final Collection<?> bestaandeHistorie = maakDeelnameEuVerkiezingenHistorie(oudeActie1, oudeActie2);
        final Collection<?> nieuweHistorie = maakDeelnameEuVerkiezingenHistorie(nieuweActie1, nieuweActie2);
        data.koppelNieuweActieMetOudeActie(nieuweActie1, oudeActie1);
        data.koppelNieuweActieMetOudeActie(nieuweActie2, oudeActie2);

        voegAanDataToe(
                (Collection<FormeleHistorie>) bestaandeHistorie,
                (Collection<FormeleHistorie>) nieuweHistorie,
                Persoon.class.getDeclaredField("persoonDeelnameEuVerkiezingenHistorieSet"));
    }

    private void voegAanDataToe(final Collection<FormeleHistorie> bestaandeHistorie, final Collection<FormeleHistorie> nieuweHistorie, final Field field) {
        final DeltaStapelMatch deltaStapelMatchAdres = new DeltaStapelMatch(bestaandeHistorie, nieuweHistorie, persoon, null, field);
        for (final FormeleHistorie historie : bestaandeHistorie) {
            data.koppelVoorkomenMetStapel(historie, deltaStapelMatchAdres);
        }
        for (final FormeleHistorie historie : nieuweHistorie) {
            data.koppelVoorkomenMetStapel(historie, deltaStapelMatchAdres);
        }

    }

    private PersoonAdres maakAdresStapel(final BRPActie actie1, final BRPActie actie2) {
        final PersoonAdres adres = new PersoonAdres(persoon);
        final PersoonAdresHistorie adresHistorie =
                new PersoonAdresHistorie(adres, SoortAdres.BRIEFADRES, landOfGebied, new RedenWijzigingVerblijf('I', "Iets"));
        adresHistorie.setActieInhoud(actie1);
        final PersoonAdresHistorie adresHistorie1 =
                new PersoonAdresHistorie(adres, SoortAdres.BRIEFADRES, landOfGebied, new RedenWijzigingVerblijf('I', "Iets"));
        adresHistorie1.setActieInhoud(actie2);

        adres.addPersoonAdresHistorie(adresHistorie);
        adres.addPersoonAdresHistorie(adresHistorie1);

        data.koppelActieMetVoorkomen(actie1, adresHistorie);
        data.koppelActieMetVoorkomen(actie2, adresHistorie1);
        return adres;
    }

    private Set<PersoonMigratieHistorie> maakMigratieHistorie(final BRPActie actie1, final BRPActie actie2) {
        final PersoonMigratieHistorie migratieHistorie = new PersoonMigratieHistorie(persoon, SoortMigratie.IMMIGRATIE);
        migratieHistorie.setActieInhoud(actie1);
        final PersoonMigratieHistorie migratieHistorie1 = new PersoonMigratieHistorie(persoon, SoortMigratie.IMMIGRATIE);
        migratieHistorie1.setActieInhoud(actie2);

        data.koppelActieMetVoorkomen(actie1, migratieHistorie);
        data.koppelActieMetVoorkomen(actie2, migratieHistorie1);

        return new LinkedHashSet<>(Arrays.asList(migratieHistorie, migratieHistorie1));
    }

    private Set<PersoonIDHistorie> maakIDHistorie(final BRPActie actie1, final BRPActie actie2) {
        final PersoonIDHistorie migratieHistorie = new PersoonIDHistorie(persoon);
        migratieHistorie.setActieInhoud(actie1);
        final PersoonIDHistorie migratieHistorie1 = new PersoonIDHistorie(persoon);
        migratieHistorie1.setActieInhoud(actie2);

        data.koppelActieMetVoorkomen(actie1, migratieHistorie);
        data.koppelActieMetVoorkomen(actie2, migratieHistorie1);

        return new LinkedHashSet<>(Arrays.asList(migratieHistorie, migratieHistorie1));
    }

    private Set<PersoonOverlijdenHistorie> maakOverlijdenHistorie(final BRPActie actie1, final BRPActie actie2) {
        final PersoonOverlijdenHistorie migratieHistorie = new PersoonOverlijdenHistorie(persoon, 2015_01_01, landOfGebied);
        migratieHistorie.setActieInhoud(actie1);
        final PersoonOverlijdenHistorie migratieHistorie1 = new PersoonOverlijdenHistorie(persoon, 2015_01_01, new LandOfGebied("0001", "Land"));
        migratieHistorie1.setActieInhoud(actie2);

        data.koppelActieMetVoorkomen(actie1, migratieHistorie);
        data.koppelActieMetVoorkomen(actie2, migratieHistorie1);

        return new LinkedHashSet<>(Arrays.asList(migratieHistorie, migratieHistorie1));
    }

    private Set<PersoonDeelnameEuVerkiezingenHistorie> maakDeelnameEuVerkiezingenHistorie(final BRPActie actie1, final BRPActie actie2) {
        final PersoonDeelnameEuVerkiezingenHistorie migratieHistorie = new PersoonDeelnameEuVerkiezingenHistorie(persoon, true);
        migratieHistorie.setActieInhoud(actie1);
        final PersoonDeelnameEuVerkiezingenHistorie migratieHistorie1 = new PersoonDeelnameEuVerkiezingenHistorie(persoon, true);
        migratieHistorie1.setActieInhoud(actie2);

        data.koppelActieMetVoorkomen(actie1, migratieHistorie);
        data.koppelActieMetVoorkomen(actie2, migratieHistorie1);

        return new LinkedHashSet<>(Arrays.asList(migratieHistorie, migratieHistorie1));
    }
}
