/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.logging;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3BerichtenBron;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Voorkomen;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonDeelnameEuVerkiezingenHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIndicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIndicatieHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonInschrijvingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonPersoonskaartHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortIndicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces.DeltaProces;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces.LoggingDeltaProces;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoggingServiceImplTest {
    private static final Timestamp DATUMTIJD_STEMPEL = Timestamp.valueOf("1990-01-01 01:00:00.0");
    private static final Timestamp DATUMTIJD_CONVERSIE = Timestamp.valueOf("2015-01-01 01:00:00.0");
    private static final long VERSIENUMMER = 1L;
    private final Lo3Bericht vorigBericht = new Lo3Bericht("test-0", Lo3BerichtenBron.INITIELE_VULLING, DATUMTIJD_CONVERSIE, "TESTDATA", true);
    private final List<BRPActie> plActies = new LinkedList<>();
    private DeltaProces loggingDeltaProces;
    private AdministratieveHandeling administratieveHandeling;

    @Before
    public void setUp() {
        loggingDeltaProces = new LoggingDeltaProces();
        administratieveHandeling = new AdministratieveHandeling(maakPartij(), SoortAdministratieveHandeling.GBA_INITIELE_VULLING);
        administratieveHandeling.setDatumTijdRegistratie(DATUMTIJD_STEMPEL);
    }

    @Test
    public void testOntkoppelenLo3VoorkomensVanBericht() {
        final Persoon persoon = maakPersoon();
        final Lo3Bericht ouderBericht = new Lo3Bericht("oud-0", Lo3BerichtenBron.INITIELE_VULLING, DATUMTIJD_CONVERSIE, "TESTDATA", true);
        persoon.addLo3Bericht(ouderBericht);
        persoon.addLo3Bericht(vorigBericht);
        final DeltaBepalingContext context = new DeltaBepalingContext(persoon, persoon, null, false);

        loggingDeltaProces.bepaalVerschillen(context);

        final Set<Lo3Voorkomen> voorkomens = vorigBericht.getVoorkomens();

        Assert.assertFalse("Er moeten nog 2 voorkomens over zijn", voorkomens.isEmpty());
        Assert.assertEquals("2 voorkomens maximaal over", 2, voorkomens.size());
        for (final Lo3Voorkomen voorkomen : voorkomens) {
            final String categorie = voorkomen.getCategorie();
            Assert.assertTrue("Alleen cat07 of cat13 mogen nog voorkomen in de set", "13".equals(categorie) || "07".equals(categorie));
        }

        for (final BRPActie brpActie : plActies) {
            final Lo3Voorkomen voorkomen = brpActie.getLo3Voorkomen();
            if (voorkomen != null) {
                final String categorie = brpActie.getLo3Voorkomen().getCategorie();
                Assert.assertTrue("Alleen cat07 of cat13 mogen nog voorkomen in de set", "13".equals(categorie) || "07".equals(categorie));
            }
        }
    }

    private Persoon maakPersoon() {
        final SoortPersoon soortPersoon = SoortPersoon.INGESCHREVENE;
        final Timestamp timestamp = DATUMTIJD_STEMPEL;
        final long versienummer = VERSIENUMMER;
        final Persoon persoon = new Persoon(soortPersoon);
        persoon.setTijdstipLaatsteWijziging(timestamp);

        persoon.setAdministratieveHandeling(administratieveHandeling);
        persoon.getPersoonAfgeleidAdministratiefHistorieSet().add(maakAfgeleidAdministratiefHistorie(persoon, administratieveHandeling));
        persoon.getPersoonIndicatieSet().add(maakPersoonIndicatie(persoon));
        persoon.getPersoonInschrijvingHistorieSet().add(maakPersoonInschrijvingHistorie(persoon));
        persoon.getPersoonDeelnameEuVerkiezingenHistorieSet().add(maakDeelnameEuVerkiezingenHistorie(persoon));
        persoon.getPersoonPersoonskaartHistorieSet().add(maakPersoonskaartHistorie(persoon));

        persoon.setTijdstipLaatsteWijziging(timestamp);
        persoon.setVersienummer(versienummer);
        persoon.setDatumtijdstempel(timestamp);
        return persoon;
    }

    private Partij maakPartij() {
        return new Partij("naam", 1);
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
        pih.setActieInhoud(maakBrpActie(tijdstempel, "06"));
        return pih;
    }

    private BRPActie maakBrpActie(final Timestamp tijdstempel, final String categorie) {
        final BRPActie actieInhoud = new BRPActie(SoortActie.CONVERSIE_GBA, administratieveHandeling, administratieveHandeling.getPartij(), tijdstempel);
        vorigBericht.addVoorkomen(categorie, 0, 0, actieInhoud);
        plActies.add(actieInhoud);
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

    private PersoonAfgeleidAdministratiefHistorie maakAfgeleidAdministratiefHistorie(final Persoon persoon, final AdministratieveHandeling ah) {
        final Timestamp tijdstempel = DATUMTIJD_STEMPEL;
        final PersoonAfgeleidAdministratiefHistorie paah = new PersoonAfgeleidAdministratiefHistorie((short) 0, persoon, ah, tijdstempel, false);
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
