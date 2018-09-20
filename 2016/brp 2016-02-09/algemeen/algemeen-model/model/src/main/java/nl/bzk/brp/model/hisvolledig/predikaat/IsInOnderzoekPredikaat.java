/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat dat bepaalt of een attribuut in onderzoek staat.
 */
public class IsInOnderzoekPredikaat implements Predicate {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public final boolean evaluate(final Object object) {
        if (object instanceof Groep) {
            final Groep groep = (Groep) object;
            for (final Attribuut attribuut : groep.getAttributen()) {
                // Het attribuut dient in onderzoek te staan en ook dient het attribuut geleverd te mogen worden.
                if (attribuut.isInOnderzoek() && attribuut.isMagGeleverdWorden()) {
                    return true;
                }
            }
        }
        return false;
    }
}
