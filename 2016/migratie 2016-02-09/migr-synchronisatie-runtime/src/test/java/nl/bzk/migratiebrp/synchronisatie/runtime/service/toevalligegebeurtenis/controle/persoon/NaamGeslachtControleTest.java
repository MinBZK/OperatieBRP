/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.persoon;

import nl.bzk.migratiebrp.bericht.model.sync.generated.GeslachtGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GeslachtsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.NaamGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Geslachtsaanduiding;
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
public final class NaamGeslachtControleTest {

    private static final String VOORNAAM = "Henk";

    @Mock
    private Persoon persoon;

    @Mock
    private ToevalligeGebeurtenisControle geslachtsaanduidingControle;

    @Mock
    private ToevalligeGebeurtenisControle geslachtsnaamComponentenControle;

    @InjectMocks
    private final NaamGeslachtControle subject = new NaamGeslachtControle();

    @Test
    public void testTrue() {
        setup(true, true);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final PersoonType persoonType = new PersoonType();
        final GeslachtGroepType geslacht = new GeslachtGroepType();
        geslacht.setGeslachtsaanduiding(GeslachtsaanduidingType.M);
        final NaamGroepType naam = new NaamGroepType();
        naam.setVoornamen(VOORNAAM);
        persoonType.setGeslacht(geslacht);
        persoonType.setNaam(naam);
        verzoek.setPersoon(persoonType);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseGeenGeslachtVerzoek() {
        setup(true, true);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final PersoonType persoonType = new PersoonType();
        final NaamGroepType naam = new NaamGroepType();
        naam.setVoornamen(VOORNAAM);
        persoonType.setNaam(naam);
        verzoek.setPersoon(persoonType);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseGeenNaamVerzoek() {
        setup(true, true);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final PersoonType persoonType = new PersoonType();
        final GeslachtGroepType geslacht = new GeslachtGroepType();
        geslacht.setGeslachtsaanduiding(GeslachtsaanduidingType.M);
        persoonType.setGeslacht(geslacht);
        verzoek.setPersoon(persoonType);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseGeenNaamVerzoekEnVerschillendGeslacht() {
        setup(false, true);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final PersoonType persoonType = new PersoonType();
        final GeslachtGroepType geslacht = new GeslachtGroepType();
        geslacht.setGeslachtsaanduiding(GeslachtsaanduidingType.M);
        Mockito.when(persoon.getGeslachtsaanduiding()).thenReturn(Geslachtsaanduiding.VROUW);
        persoonType.setGeslacht(geslacht);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalsenVerschillendGeslacht() {
        setup(true, true);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final PersoonType persoonType = new PersoonType();
        final GeslachtGroepType geslacht = new GeslachtGroepType();
        geslacht.setGeslachtsaanduiding(GeslachtsaanduidingType.M);
        Mockito.when(persoon.getGeslachtsaanduiding()).thenReturn(Geslachtsaanduiding.VROUW);
        final NaamGroepType naam = new NaamGroepType();
        naam.setVoornamen(VOORNAAM);
        persoonType.setGeslacht(geslacht);
        persoonType.setNaam(naam);
        verzoek.setPersoon(persoonType);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseGeenGeslachtGeenNaamVerzoek() {
        setup(true, true);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final PersoonType persoonType = new PersoonType();
        verzoek.setPersoon(persoonType);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseGeenPersoonVerzoek() {
        setup(true, true);
        Mockito.when(persoon.getGeslachtsaanduiding()).thenReturn(Geslachtsaanduiding.VROUW);
        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
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
