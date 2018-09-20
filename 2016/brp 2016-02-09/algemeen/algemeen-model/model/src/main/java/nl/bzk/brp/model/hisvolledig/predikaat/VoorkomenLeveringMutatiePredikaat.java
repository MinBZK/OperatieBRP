/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.VerantwoordingTbvLeveringMutaties;
import org.apache.commons.collections.Predicate;

/**
 * Filtert voorkomens met voorkomenLeveringMutatie?=Ja
 * Dit predikaat dient enkel gebruikt te worden voor volledigberichten.
 */
@Regels(Regel.VR00125)
public class VoorkomenLeveringMutatiePredikaat implements Predicate {

    @Override
    public final boolean evaluate(final Object voorkomen) {
        if (voorkomen instanceof VerantwoordingTbvLeveringMutaties) {
            final VerantwoordingTbvLeveringMutaties verantwoordingTbvLeveringMutaties = (VerantwoordingTbvLeveringMutaties) voorkomen;
            final JaAttribuut jaAttribuut = verantwoordingTbvLeveringMutaties.getIndicatieVoorkomenTbvLeveringMutaties();
            if (jaAttribuut != null && jaAttribuut.getWaarde() == Ja.J) {
                return false;
            }
        }
        return voorkomen != null;
    }
}
