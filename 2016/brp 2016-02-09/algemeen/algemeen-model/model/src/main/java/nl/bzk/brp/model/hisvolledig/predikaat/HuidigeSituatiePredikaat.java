/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import nl.bzk.brp.model.FormeleHistoriePredikaat;
import nl.bzk.brp.model.MaterieleHistoriePredikaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat dat kan worden toegepast op instanties van {@link MaterieelHistorisch} en {@link FormeelHistorisch}.
 *
 * Het predikaat evalueert naar waar, als voorkomens {@code nu} geldig zijn.
 */
public class HuidigeSituatiePredikaat implements Predicate {

    @Override
    public boolean evaluate(final Object object) {
        if (object instanceof FormeelHistorisch) {
            return FormeleHistoriePredikaat.bekendOp(DatumTijdAttribuut.nu()).evaluate(object);
        } else if (object instanceof MaterieelHistorisch) {
            return MaterieleHistoriePredikaat.geldigOp(DatumTijdAttribuut.nu()).evaluate(object);
        } else {
            throw new IllegalArgumentException(String.format("Ongeldig object: '%s' van type: '%s'.", object, object.getClass()));
        }
    }

}
