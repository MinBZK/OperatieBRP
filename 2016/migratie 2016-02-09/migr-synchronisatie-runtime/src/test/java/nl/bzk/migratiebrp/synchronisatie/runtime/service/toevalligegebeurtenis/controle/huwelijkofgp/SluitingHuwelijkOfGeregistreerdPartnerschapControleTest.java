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
public class SluitingHuwelijkOfGeregistreerdPartnerschapControleTest {

    @Mock
    private Persoon persoon;

    private final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();

    @Mock
    private ToevalligeGebeurtenisControle persoonControle;

    @InjectMocks
    private SluitingHuwelijkOfGeregistreerdPartnerschapControle subject;

    @Test
    public void testControleTrue() {
        setup(true);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testControleFalse() {
        setup(false);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    private void setup(final boolean resultaatPersoonControle) {
        Mockito.when(persoonControle.controleer(Matchers.any(Persoon.class), Matchers.any(VerwerkToevalligeGebeurtenisVerzoekBericht.class))).thenReturn(
            resultaatPersoonControle);
    }

}
