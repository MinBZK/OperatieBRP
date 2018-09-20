/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.VerantwoordingTbvLeveringMutaties;
import org.junit.Test;
import org.mockito.Mockito;

public class VoorkomenLeveringMutatiePredikaatTest {

    private final VoorkomenLeveringMutatiePredikaat predikaat = new VoorkomenLeveringMutatiePredikaat();

    @Test
    public void nullVoorkomenWordtNietGeleverd() {
        assertFalse(predikaat.evaluate(null));
    }

    @Test
    public void formeelVoorkomenWordtGeleverd() {
        final FormeelVerantwoordbaar voorkomen = Mockito.mock(FormeelVerantwoordbaar.class);
        assertTrue(predikaat.evaluate(voorkomen));
    }

    @Test
    public void indicatieJaMetWaardeNullWordtGeleverd() {
        final VerantwoordingTbvLeveringMutaties voorkomen = Mockito.mock(VerantwoordingTbvLeveringMutaties.class);
        Mockito.when(voorkomen.getIndicatieVoorkomenTbvLeveringMutaties()).thenReturn(null);
        assertTrue(predikaat.evaluate(voorkomen));
    }

    @Test
    public void indicatieJaMetWaardeJaWordtNietGeleverd() {
        final VerantwoordingTbvLeveringMutaties voorkomen = Mockito.mock(VerantwoordingTbvLeveringMutaties.class);
        Mockito.when(voorkomen.getIndicatieVoorkomenTbvLeveringMutaties()).thenReturn(new JaAttribuut(Ja.J));
        assertFalse(predikaat.evaluate(voorkomen));
    }
}
