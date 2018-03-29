/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.verwerker;

import java.sql.Timestamp;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import org.junit.Assert;
import org.junit.Test;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonDeelnameEuVerkiezingenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonInschrijvingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonPersoonskaartHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3BerichtenBron;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.DalUtil;

public class LoggingVerwerkerTest {

    private static final Timestamp DATUMTIJD_STEMPEL = Timestamp.valueOf("1990-01-01 01:00:00.0");
    private static final Timestamp DATUMTIJD_CONVERSIE = Timestamp.valueOf("2015-01-01 01:00:00.0");
    private static final long VERSIENUMMER = 1L;

    private final AdministratieveHandeling administratieveHandeling =
            new AdministratieveHandeling(maakPartij(), SoortAdministratieveHandeling.GBA_INITIELE_VULLING, new Timestamp(System.currentTimeMillis()));
    private final Lo3Bericht nieuwBericht = new Lo3Bericht("test-1", Lo3BerichtenBron.INITIELE_VULLING, DATUMTIJD_CONVERSIE, "TESTDATA", true);

    @Test
    public void testVerwerk() {
        final Persoon persoon = maakPersoon();
        final Lo3Bericht ouderBericht = new Lo3Bericht("oud-0", Lo3BerichtenBron.INITIELE_VULLING, DATUMTIJD_CONVERSIE, "TESTDATA", true);
        persoon.addLo3Bericht(ouderBericht);

        final BRPActie relatieActie = maakBrpActie(DATUMTIJD_STEMPEL, "05");
        final Lo3Voorkomen relatieHerkomst = relatieActie.getLo3Voorkomen();

        final Map<BRPActie, Lo3Voorkomen> herkomstMap = new IdentityHashMap<>();
        herkomstMap.put(
                persoon.getPersoonIndicatieSet().iterator().next().getPersoonIndicatieHistorieSet().iterator().next().getActieInhoud(),
                nieuwBericht.addVoorkomen("07", 0, 0, null));

        Assert.assertNotSame(
                persoon.getPersoonIndicatieSet().iterator().next().getPersoonIndicatieHistorieSet().iterator().next().getActieInhoud(),
                persoon.getPersoonAfgeleidAdministratiefHistorieSet().iterator().next().getActieInhoud());

        Assert.assertNotNull(relatieActie.getLo3Voorkomen());
        Assert.assertTrue(nieuwBericht.getVoorkomens().contains(relatieHerkomst));

        final Map<BRPActie, Set<FormeleHistorie>> persoonActies = DalUtil.verzamelActiesUitgezonderdRelaties(persoon);
        new LoggingVerwerker().verwerk(herkomstMap, persoonActies);

        Assert.assertNotNull(relatieActie.getLo3Voorkomen());
        Assert.assertTrue(nieuwBericht.getVoorkomens().contains(relatieHerkomst));
    }

    private Persoon maakPersoon() {
        final SoortPersoon soortPersoon = SoortPersoon.INGESCHREVENE;
        final Timestamp timestamp = DATUMTIJD_STEMPEL;
        final Persoon persoon = new Persoon(soortPersoon);
        persoon.setTijdstipLaatsteWijziging(timestamp);

        persoon.setAdministratieveHandeling(administratieveHandeling);
        persoon.getPersoonAfgeleidAdministratiefHistorieSet().add(maakAfgeleidAdministratiefHistorie(persoon, administratieveHandeling));
        persoon.getPersoonIndicatieSet().add(maakPersoonIndicatie(persoon));
        persoon.getPersoonInschrijvingHistorieSet().add(maakPersoonInschrijvingHistorie(persoon));
        persoon.getPersoonDeelnameEuVerkiezingenHistorieSet().add(maakDeelnameEuVerkiezingenHistorie(persoon));
        persoon.getPersoonPersoonskaartHistorieSet().add(maakPersoonskaartHistorie(persoon));
        persoon.addPersoonIDHistorie(maakPersoonIdHistorie(persoon));

        persoon.setTijdstipLaatsteWijziging(timestamp);
        persoon.setVersienummer(VERSIENUMMER);
        persoon.setDatumtijdstempel(timestamp);
        return persoon;
    }

    private Partij maakPartij() {
        return new Partij("naam", "000001");
    }

    private PersoonIndicatie maakPersoonIndicatie(final Persoon persoon) {
        final PersoonIndicatie pi = new PersoonIndicatie(persoon, SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING);
        pi.addPersoonIndicatieHistorie(maakPersoonIndicatieHistorie(pi));
        return pi;
    }

    private PersoonIndicatieHistorie maakPersoonIndicatieHistorie(final PersoonIndicatie pi) {
        final PersoonIndicatieHistorie pih = new PersoonIndicatieHistorie(pi, true);
        final Timestamp tijdstempel = DATUMTIJD_STEMPEL;
        pih.setDatumTijdRegistratie(tijdstempel);
        pih.setActieInhoud(maakBrpActie(tijdstempel, null));
        return pih;
    }

    private BRPActie maakBrpActie(final Timestamp tijdstempel, final String categorie) {
        final BRPActie actieInhoud = new BRPActie(SoortActie.CONVERSIE_GBA, administratieveHandeling, administratieveHandeling.getPartij(), tijdstempel);
        if (categorie != null) {
            nieuwBericht.addVoorkomen(categorie, 0, 0, actieInhoud);
        }
        return actieInhoud;
    }

    private PersoonInschrijvingHistorie maakPersoonInschrijvingHistorie(final Persoon persoon) {
        final PersoonInschrijvingHistorie pih;
        pih = new PersoonInschrijvingHistorie(persoon, 20130801, VERSIENUMMER, DATUMTIJD_STEMPEL);
        return pih;
    }

    private PersoonPersoonskaartHistorie maakPersoonskaartHistorie(final Persoon persoon) {
        final PersoonPersoonskaartHistorie pph = new PersoonPersoonskaartHistorie(persoon, true);
        final Timestamp tijdstempel = DATUMTIJD_STEMPEL;
        pph.setDatumTijdRegistratie(tijdstempel);
        pph.setActieInhoud(maakBrpActie(tijdstempel, "01"));
        return pph;
    }

    private PersoonIDHistorie maakPersoonIdHistorie(final Persoon persoon) {
        final PersoonIDHistorie historie = new PersoonIDHistorie(persoon);
        final Timestamp tijdstempel = DATUMTIJD_STEMPEL;
        historie.setDatumTijdRegistratie(tijdstempel);
        historie.setActieInhoud(maakBrpActie(tijdstempel, "01"));
        historie.setAdministratienummer("1234567890");
        return historie;
    }

    private PersoonAfgeleidAdministratiefHistorie maakAfgeleidAdministratiefHistorie(final Persoon persoon, final AdministratieveHandeling ah) {
        final Timestamp tijdstempel = DATUMTIJD_STEMPEL;
        final PersoonAfgeleidAdministratiefHistorie paah = new PersoonAfgeleidAdministratiefHistorie((short) 0, persoon, ah, tijdstempel);
        paah.setDatumTijdRegistratie(tijdstempel);
        paah.setActieInhoud(maakBrpActie(tijdstempel, "07"));
        return paah;
    }

    private PersoonDeelnameEuVerkiezingenHistorie maakDeelnameEuVerkiezingenHistorie(final Persoon persoon) {
        final PersoonDeelnameEuVerkiezingenHistorie pdevh = new PersoonDeelnameEuVerkiezingenHistorie(persoon, true);
        final Timestamp tijdstempel = DATUMTIJD_STEMPEL;
        pdevh.setDatumTijdRegistratie(tijdstempel);
        pdevh.setActieInhoud(maakBrpActie(tijdstempel, "13"));
        return pdevh;
    }
}
