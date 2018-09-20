/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.huwelijkofgp;

import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
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

@RunWith(MockitoJUnitRunner.class)
public class OmzettingHuwelijkOfGeregistreerdPartnerschapControleTest {

    @Mock
    private Persoon persoon;

    private final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();

    @Mock
    private ToevalligeGebeurtenisControle persoonControle;

    @Mock
    private ToevalligeGebeurtenisControle huwelijkControle;

    @Mock
    private ToevalligeGebeurtenisControle soortVerbintenisOngelijkControle;

    @InjectMocks
    private OmzettingHuwelijkOfGeregistreerdPartnerschapControle subject;

    @Test
    public void testControleAllesTrue() {
        setup(true, true, true);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testControleAllesTrueBehalvePersoon() {
        setup(false, true, true);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testControleAllesTrueBehalveHuwelijk() {
        setup(true, false, true);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testControleAllesTrueBehalveSoortVerbintenis() {
        setup(true, true, false);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testControleAllesFalseBehalvePersoon() {
        setup(true, false, false);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testControleAllesFalseBehalveHuwelijk() {
        setup(false, true, false);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testControleAllesFalseBehalveSoortVerbintenis() {
        setup(false, false, true);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testControleAllesFalse() {
        setup(false, false, false);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    private void setup(final boolean resultaatPersoonControle, final boolean resultaatHuwelijkControle, final boolean resultaatSoortVerbintenisControle) {
        Mockito.when(persoonControle.controleer(Matchers.any(Persoon.class), Matchers.any(VerwerkToevalligeGebeurtenisVerzoekBericht.class))).thenReturn(
            resultaatPersoonControle);
        Mockito.when(huwelijkControle.controleer(Matchers.any(Persoon.class), Matchers.any(VerwerkToevalligeGebeurtenisVerzoekBericht.class))).thenReturn(
            resultaatHuwelijkControle);
        Mockito.when(
                   soortVerbintenisOngelijkControle.controleer(Matchers.any(Persoon.class), Matchers.any(VerwerkToevalligeGebeurtenisVerzoekBericht.class)))
               .thenReturn(resultaatSoortVerbintenisControle);
    }

}
