/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.overlijden;

import java.math.BigInteger;
import java.util.Collections;

import nl.bzk.migratiebrp.bericht.model.sync.generated.IdentificatienummersGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.OverlijdenGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.OverlijdenType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonOverlijdenHistorie;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.VerwerkToevalligeGebeurtenisVerzoekHelper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Helper klasse voor het inhoudelijk controleren van een naamgeslacht van een persoon tegen de persoon uit de BRP.
 */
@RunWith(MockitoJUnitRunner.class)
public final class OverlijdenControleTest {

    private static final BigInteger DATUM_OVERLIJDEN = BigInteger.valueOf(20150920L);
    private static final String PLAATS = "0599";
    private static final String BUITENLANDSE_PLAATS = "Rome";
    private static final String BUITENLANDSE_PLAATS_LANG = "New York";
    private static final String LAND = "0630";
    private static final String BUITENLAND = "Verenigde Staten";
    private static final String ANUMMER = "35423423";
    private static final String BSN = "56356334";

    @Mock
    private Persoon persoon;

    @InjectMocks
    private final OverlijdenControle subject = new OverlijdenControle();

    @Test
    public void testTrue() {
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verzoek.setOverlijden(VerwerkToevalligeGebeurtenisVerzoekHelper.maakOverlijden(DATUM_OVERLIJDEN, LAND, PLAATS));
        final PersoonType persoonType = new PersoonType();
        final IdentificatienummersGroepType identificatienummers = new IdentificatienummersGroepType();
        identificatienummers.setANummer(ANUMMER);
        identificatienummers.setBurgerservicenummer(BSN);
        persoonType.setIdentificatienummers(identificatienummers);
        verzoek.setPersoon(persoonType);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testTrueBuitenland() {
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        verzoek.setOverlijden(VerwerkToevalligeGebeurtenisVerzoekHelper.maakOverlijden(DATUM_OVERLIJDEN, BUITENLAND, BUITENLANDSE_PLAATS));
        final PersoonType persoonType = new PersoonType();
        final IdentificatienummersGroepType identificatienummers = new IdentificatienummersGroepType();
        identificatienummers.setANummer(ANUMMER);
        identificatienummers.setBurgerservicenummer(BSN);
        persoonType.setIdentificatienummers(identificatienummers);
        verzoek.setPersoon(persoonType);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testTrueBuitenlandLangeNaam() {
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final PersoonType persoonType = new PersoonType();
        final IdentificatienummersGroepType identificatienummers = new IdentificatienummersGroepType();
        identificatienummers.setANummer(ANUMMER);
        identificatienummers.setBurgerservicenummer(BSN);
        persoonType.setIdentificatienummers(identificatienummers);
        verzoek.setOverlijden(VerwerkToevalligeGebeurtenisVerzoekHelper.maakOverlijden(DATUM_OVERLIJDEN, BUITENLAND, BUITENLANDSE_PLAATS_LANG));
        verzoek.setPersoon(persoonType);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseGeenPersoonInVerzoek() {
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseEerderAlOverleden() {
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final PersoonOverlijdenHistorie persoonOverlijdenHistorie = Mockito.mock(PersoonOverlijdenHistorie.class);
        Mockito.when(persoon.getPersoonOverlijdenHistorieSet()).thenReturn(Collections.singleton(persoonOverlijdenHistorie));
        final OverlijdenGroepType overlijden = new OverlijdenGroepType();
        overlijden.setDatum(DATUM_OVERLIJDEN);
        overlijden.setPlaats(BUITENLANDSE_PLAATS_LANG);
        overlijden.setLand(BUITENLAND);
        final OverlijdenType overlijdenType = new OverlijdenType();
        overlijdenType.setOverlijden(overlijden);
        verzoek.setOverlijden(overlijdenType);
        final PersoonType persoonType = new PersoonType();
        final IdentificatienummersGroepType identificatienummers = new IdentificatienummersGroepType();
        identificatienummers.setANummer(ANUMMER);
        identificatienummers.setBurgerservicenummer(BSN);
        persoonType.setIdentificatienummers(identificatienummers);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

}
