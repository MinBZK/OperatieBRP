/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.persoon;

import java.math.BigInteger;

import nl.bzk.migratiebrp.bericht.model.sync.generated.GeboorteGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.IdentificatienummersGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.LandOfGebied;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.ToevalligeGebeurtenisControle;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Helper klasse voor het inhoudelijk controleren van een naamgeslacht van een persoon tegen de persoon uit de BRP.
 */
@RunWith(MockitoJUnitRunner.class)
public final class PersoonControleTest {

    private static final Long ANUMMER = Long.valueOf(384131651L);
    private static final Integer BSN = Integer.valueOf("89124234");
    private static final BigInteger GEBOORTE_DATUM = BigInteger.valueOf(129871205L);
    private static final String GEMEENTE_CODE = "0599";
    private static final Gemeente GEBOORTE_PLAATS = new Gemeente(Short.valueOf(GEMEENTE_CODE), "Amsterdam", Short.valueOf(GEMEENTE_CODE), new Partij("Amsterdam", 599));
    private static final String LAND_CODE = "0630";
    private static final LandOfGebied GEBOORTE_LAND = new LandOfGebied(Short.valueOf(LAND_CODE), "Nederland");
    private static final String BUITENLANDSE_GEBOORTE_PLAATS = "Madrid";
    private static final LandOfGebied BUITENLANDSE_GEBOORTE_LAND = new LandOfGebied(Short.valueOf("1330"), "Spanje");

    @Mock
    private Persoon persoon;

    @Mock
    private ToevalligeGebeurtenisControle geslachtsaanduidingControle;

    @Mock
    private ToevalligeGebeurtenisControle geslachtsnaamComponentenControle;

    @InjectMocks
    private final PersoonControle subject = new PersoonControle();

    @Test
    public void testTrue() {
        setup(true, true);
        Mockito.when(persoon.getAdministratienummer()).thenReturn(ANUMMER);
        Mockito.when(persoon.getBurgerservicenummer()).thenReturn(BSN);
        Mockito.when(persoon.getDatumGeboorte()).thenReturn(GEBOORTE_DATUM.intValue());
        Mockito.when(persoon.getGemeenteGeboorte()).thenReturn(GEBOORTE_PLAATS);
        Mockito.when(persoon.getLandOfGebiedGeboorte()).thenReturn(GEBOORTE_LAND);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final PersoonType persoonType = new PersoonType();
        final IdentificatienummersGroepType identificatieNummers = new IdentificatienummersGroepType();
        final GeboorteGroepType geboorte = new GeboorteGroepType();
        identificatieNummers.setANummer(String.valueOf(ANUMMER));
        identificatieNummers.setBurgerservicenummer(String.valueOf(BSN));
        geboorte.setDatum(GEBOORTE_DATUM);
        geboorte.setLand(LAND_CODE);
        geboorte.setPlaats(GEMEENTE_CODE);
        persoonType.setIdentificatienummers(identificatieNummers);
        persoonType.setGeboorte(geboorte);
        verzoek.setPersoon(persoonType);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseGeslacht() {
        setup(false, true);
        Mockito.when(persoon.getAdministratienummer()).thenReturn(ANUMMER);
        Mockito.when(persoon.getBurgerservicenummer()).thenReturn(BSN);
        Mockito.when(persoon.getDatumGeboorte()).thenReturn(GEBOORTE_DATUM.intValue());
        Mockito.when(persoon.getGemeenteGeboorte()).thenReturn(GEBOORTE_PLAATS);
        Mockito.when(persoon.getLandOfGebiedGeboorte()).thenReturn(GEBOORTE_LAND);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final PersoonType persoonType = new PersoonType();
        final IdentificatienummersGroepType identificatieNummers = new IdentificatienummersGroepType();
        final GeboorteGroepType geboorte = new GeboorteGroepType();
        identificatieNummers.setANummer(String.valueOf(ANUMMER));
        identificatieNummers.setBurgerservicenummer(String.valueOf(BSN));
        geboorte.setDatum(GEBOORTE_DATUM);
        geboorte.setLand(String.valueOf(GEBOORTE_LAND.getCode()));
        geboorte.setPlaats(String.valueOf(GEBOORTE_PLAATS.getCode()));
        persoonType.setIdentificatienummers(identificatieNummers);
        persoonType.setGeboorte(geboorte);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseNaam() {
        setup(true, false);
        Mockito.when(persoon.getAdministratienummer()).thenReturn(ANUMMER);
        Mockito.when(persoon.getBurgerservicenummer()).thenReturn(BSN);
        Mockito.when(persoon.getDatumGeboorte()).thenReturn(GEBOORTE_DATUM.intValue());
        Mockito.when(persoon.getGemeenteGeboorte()).thenReturn(GEBOORTE_PLAATS);
        Mockito.when(persoon.getLandOfGebiedGeboorte()).thenReturn(GEBOORTE_LAND);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final PersoonType persoonType = new PersoonType();
        final IdentificatienummersGroepType identificatieNummers = new IdentificatienummersGroepType();
        final GeboorteGroepType geboorte = new GeboorteGroepType();
        identificatieNummers.setANummer(String.valueOf(ANUMMER));
        identificatieNummers.setBurgerservicenummer(String.valueOf(BSN));
        geboorte.setDatum(GEBOORTE_DATUM);
        geboorte.setLand(String.valueOf(GEBOORTE_LAND.getCode()));
        geboorte.setPlaats(String.valueOf(GEBOORTE_PLAATS.getCode()));
        persoonType.setIdentificatienummers(identificatieNummers);
        persoonType.setGeboorte(geboorte);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testTrueBuitenland() {
        setup(true, true);
        Mockito.when(persoon.getAdministratienummer()).thenReturn(ANUMMER);
        Mockito.when(persoon.getBurgerservicenummer()).thenReturn(BSN);
        Mockito.when(persoon.getDatumGeboorte()).thenReturn(GEBOORTE_DATUM.intValue());
        Mockito.when(persoon.getBuitenlandsePlaatsGeboorte()).thenReturn(BUITENLANDSE_GEBOORTE_PLAATS);
        Mockito.when(persoon.getLandOfGebiedGeboorte()).thenReturn(BUITENLANDSE_GEBOORTE_LAND);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final PersoonType persoonType = new PersoonType();
        final IdentificatienummersGroepType identificatieNummers = new IdentificatienummersGroepType();
        final GeboorteGroepType geboorte = new GeboorteGroepType();
        identificatieNummers.setANummer(String.valueOf(ANUMMER));
        identificatieNummers.setBurgerservicenummer(String.valueOf(BSN));
        geboorte.setDatum(GEBOORTE_DATUM);
        geboorte.setLand(String.valueOf(BUITENLANDSE_GEBOORTE_LAND.getCode()));
        geboorte.setPlaats(BUITENLANDSE_GEBOORTE_PLAATS);
        persoonType.setIdentificatienummers(identificatieNummers);
        persoonType.setGeboorte(geboorte);
        verzoek.setPersoon(persoonType);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseOngelijkeBSN() {
        setup(true, true);
        Mockito.when(persoon.getAdministratienummer()).thenReturn(ANUMMER);
        Mockito.when(persoon.getBurgerservicenummer()).thenReturn(BSN);
        Mockito.when(persoon.getDatumGeboorte()).thenReturn(GEBOORTE_DATUM.intValue());
        Mockito.when(persoon.getGemeenteGeboorte()).thenReturn(GEBOORTE_PLAATS);
        Mockito.when(persoon.getLandOfGebiedGeboorte()).thenReturn(GEBOORTE_LAND);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final PersoonType persoonType = new PersoonType();
        final IdentificatienummersGroepType identificatieNummers = new IdentificatienummersGroepType();
        final GeboorteGroepType geboorte = new GeboorteGroepType();
        identificatieNummers.setANummer(String.valueOf(ANUMMER));
        identificatieNummers.setBurgerservicenummer(String.valueOf("87651231"));
        geboorte.setDatum(GEBOORTE_DATUM);
        geboorte.setLand(String.valueOf(GEBOORTE_LAND.getCode()));
        geboorte.setPlaats(String.valueOf(GEBOORTE_PLAATS.getCode()));
        persoonType.setIdentificatienummers(identificatieNummers);
        persoonType.setGeboorte(geboorte);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseOngelijkeGeboortedatum() {
        setup(true, true);
        Mockito.when(persoon.getAdministratienummer()).thenReturn(ANUMMER);
        Mockito.when(persoon.getBurgerservicenummer()).thenReturn(BSN);
        Mockito.when(persoon.getDatumGeboorte()).thenReturn(GEBOORTE_DATUM.intValue());
        Mockito.when(persoon.getGemeenteGeboorte()).thenReturn(GEBOORTE_PLAATS);
        Mockito.when(persoon.getLandOfGebiedGeboorte()).thenReturn(GEBOORTE_LAND);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final PersoonType persoonType = new PersoonType();
        final IdentificatienummersGroepType identificatieNummers = new IdentificatienummersGroepType();
        final GeboorteGroepType geboorte = new GeboorteGroepType();
        identificatieNummers.setANummer(String.valueOf(ANUMMER));
        identificatieNummers.setBurgerservicenummer("87651231");
        geboorte.setDatum(BigInteger.valueOf(20150101L));
        geboorte.setLand(String.valueOf(GEBOORTE_LAND.getCode()));
        geboorte.setPlaats(String.valueOf(GEBOORTE_PLAATS.getCode()));
        persoonType.setIdentificatienummers(identificatieNummers);
        persoonType.setGeboorte(geboorte);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseOngelijkeGeboorteplaats() {
        setup(true, true);
        Mockito.when(persoon.getAdministratienummer()).thenReturn(ANUMMER);
        Mockito.when(persoon.getBurgerservicenummer()).thenReturn(BSN);
        Mockito.when(persoon.getDatumGeboorte()).thenReturn(GEBOORTE_DATUM.intValue());
        Mockito.when(persoon.getGemeenteGeboorte()).thenReturn(GEBOORTE_PLAATS);
        Mockito.when(persoon.getLandOfGebiedGeboorte()).thenReturn(GEBOORTE_LAND);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final PersoonType persoonType = new PersoonType();
        final IdentificatienummersGroepType identificatieNummers = new IdentificatienummersGroepType();
        final GeboorteGroepType geboorte = new GeboorteGroepType();
        identificatieNummers.setANummer(String.valueOf(ANUMMER));
        identificatieNummers.setBurgerservicenummer(String.valueOf(BSN));
        geboorte.setDatum(GEBOORTE_DATUM);
        geboorte.setLand(String.valueOf(GEBOORTE_LAND.getCode()));
        geboorte.setPlaats(BUITENLANDSE_GEBOORTE_PLAATS);
        persoonType.setIdentificatienummers(identificatieNummers);
        persoonType.setGeboorte(geboorte);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseOngelijkeGeboorteplaatsBuitenland() {
        setup(true, true);
        Mockito.when(persoon.getAdministratienummer()).thenReturn(ANUMMER);
        Mockito.when(persoon.getBurgerservicenummer()).thenReturn(BSN);
        Mockito.when(persoon.getDatumGeboorte()).thenReturn(GEBOORTE_DATUM.intValue());
        Mockito.when(persoon.getBuitenlandsePlaatsGeboorte()).thenReturn(BUITENLANDSE_GEBOORTE_PLAATS);
        Mockito.when(persoon.getLandOfGebiedGeboorte()).thenReturn(GEBOORTE_LAND);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final PersoonType persoonType = new PersoonType();
        final IdentificatienummersGroepType identificatieNummers = new IdentificatienummersGroepType();
        final GeboorteGroepType geboorte = new GeboorteGroepType();
        identificatieNummers.setANummer(String.valueOf(ANUMMER));
        identificatieNummers.setBurgerservicenummer(String.valueOf(BSN));
        geboorte.setDatum(GEBOORTE_DATUM);
        geboorte.setLand(String.valueOf(GEBOORTE_LAND.getCode()));
        geboorte.setPlaats(String.valueOf(GEBOORTE_PLAATS.getCode()));
        persoonType.setIdentificatienummers(identificatieNummers);
        persoonType.setGeboorte(geboorte);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseLegeIdentificatienummers() {
        setup(true, true);
        Mockito.when(persoon.getAdministratienummer()).thenReturn(ANUMMER);
        Mockito.when(persoon.getBurgerservicenummer()).thenReturn(BSN);
        Mockito.when(persoon.getDatumGeboorte()).thenReturn(GEBOORTE_DATUM.intValue());
        Mockito.when(persoon.getBuitenlandsePlaatsGeboorte()).thenReturn(BUITENLANDSE_GEBOORTE_PLAATS);
        Mockito.when(persoon.getLandOfGebiedGeboorte()).thenReturn(BUITENLANDSE_GEBOORTE_LAND);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseLegePersoon() {
        setup(true, true);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final PersoonType persoonType = new PersoonType();
        final IdentificatienummersGroepType identificatieNummers = new IdentificatienummersGroepType();
        final GeboorteGroepType geboorte = new GeboorteGroepType();
        identificatieNummers.setANummer(String.valueOf(ANUMMER));
        identificatieNummers.setBurgerservicenummer(String.valueOf(BSN));
        geboorte.setDatum(GEBOORTE_DATUM);
        geboorte.setLand(String.valueOf(GEBOORTE_LAND.getCode()));
        geboorte.setPlaats(String.valueOf(GEBOORTE_PLAATS.getCode()));
        persoonType.setIdentificatienummers(identificatieNummers);
        persoonType.setGeboorte(geboorte);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalsePersoonZonderGeboorteGegevens() {
        setup(true, true);
        Mockito.when(persoon.getAdministratienummer()).thenReturn(ANUMMER);
        Mockito.when(persoon.getBurgerservicenummer()).thenReturn(BSN);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final PersoonType persoonType = new PersoonType();
        final IdentificatienummersGroepType identificatieNummers = new IdentificatienummersGroepType();
        final GeboorteGroepType geboorte = new GeboorteGroepType();
        identificatieNummers.setANummer(String.valueOf(ANUMMER));
        identificatieNummers.setBurgerservicenummer(String.valueOf(BSN));
        geboorte.setDatum(GEBOORTE_DATUM);
        geboorte.setLand(String.valueOf(GEBOORTE_LAND.getCode()));
        geboorte.setPlaats(String.valueOf(GEBOORTE_PLAATS.getCode()));
        persoonType.setIdentificatienummers(identificatieNummers);
        persoonType.setGeboorte(geboorte);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseOngeldigeIdentificatieCodes() {
        setup(true, true);
        Mockito.when(persoon.getAdministratienummer()).thenReturn(ANUMMER);
        Mockito.when(persoon.getBurgerservicenummer()).thenReturn(BSN);
        Mockito.when(persoon.getDatumGeboorte()).thenReturn(GEBOORTE_DATUM.intValue());
        Mockito.when(persoon.getGemeenteGeboorte()).thenReturn(GEBOORTE_PLAATS);
        Mockito.when(persoon.getLandOfGebiedGeboorte()).thenReturn(GEBOORTE_LAND);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final PersoonType persoonType = new PersoonType();
        final IdentificatienummersGroepType identificatieNummers = new IdentificatienummersGroepType();
        identificatieNummers.setANummer("anummer");
        identificatieNummers.setBurgerservicenummer("bsn");
        persoonType.setIdentificatienummers(identificatieNummers);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseOngeldigeGeboorteCodes() {
        setup(true, true);
        Mockito.when(persoon.getAdministratienummer()).thenReturn(ANUMMER);
        Mockito.when(persoon.getBurgerservicenummer()).thenReturn(BSN);
        Mockito.when(persoon.getDatumGeboorte()).thenReturn(GEBOORTE_DATUM.intValue());
        Mockito.when(persoon.getGemeenteGeboorte()).thenReturn(GEBOORTE_PLAATS);
        Mockito.when(persoon.getLandOfGebiedGeboorte()).thenReturn(GEBOORTE_LAND);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final PersoonType persoonType = new PersoonType();
        final IdentificatienummersGroepType identificatieNummers = new IdentificatienummersGroepType();
        final GeboorteGroepType geboorte = new GeboorteGroepType();
        identificatieNummers.setANummer(String.valueOf(ANUMMER));
        identificatieNummers.setBurgerservicenummer(String.valueOf(BSN));
        geboorte.setDatum(GEBOORTE_DATUM);
        geboorte.setLand("geboorteland");
        geboorte.setPlaats("geboorteplaats");
        persoonType.setIdentificatienummers(identificatieNummers);
        persoonType.setGeboorte(geboorte);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseGeenGeboorteDatum() {
        setup(true, true);
        Mockito.when(persoon.getAdministratienummer()).thenReturn(ANUMMER);
        Mockito.when(persoon.getBurgerservicenummer()).thenReturn(BSN);
        Mockito.when(persoon.getDatumGeboorte()).thenReturn(GEBOORTE_DATUM.intValue());
        Mockito.when(persoon.getGemeenteGeboorte()).thenReturn(GEBOORTE_PLAATS);
        Mockito.when(persoon.getLandOfGebiedGeboorte()).thenReturn(GEBOORTE_LAND);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final PersoonType persoonType = new PersoonType();
        final IdentificatienummersGroepType identificatieNummers = new IdentificatienummersGroepType();
        final GeboorteGroepType geboorte = new GeboorteGroepType();
        identificatieNummers.setANummer(String.valueOf(ANUMMER));
        identificatieNummers.setBurgerservicenummer(String.valueOf(BSN));
        geboorte.setLand(String.valueOf(GEBOORTE_LAND.getCode()));
        geboorte.setPlaats(String.valueOf(GEBOORTE_PLAATS.getCode()));
        persoonType.setIdentificatienummers(identificatieNummers);
        persoonType.setGeboorte(geboorte);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    public void setup(final boolean resultaatGeslacht, final boolean resultaatNaam) {
        Mockito.when(geslachtsaanduidingControle.controleer(Matchers.any(Persoon.class), Matchers.any(VerwerkToevalligeGebeurtenisVerzoekBericht.class)))
               .thenReturn(resultaatGeslacht);
        Mockito.when(
                   geslachtsnaamComponentenControle.controleer(Matchers.any(Persoon.class), Matchers.any(VerwerkToevalligeGebeurtenisVerzoekBericht.class)))
               .thenReturn(resultaatNaam);

    }
}
