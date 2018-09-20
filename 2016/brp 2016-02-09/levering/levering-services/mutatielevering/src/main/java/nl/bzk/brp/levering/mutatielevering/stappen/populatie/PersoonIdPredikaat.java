/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import java.util.List;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import org.apache.commons.collections.Predicate;

/**
 * Predicate dat verfieert dat {@link PersoonHisVolledig#getID()} voorkomt in de lijst van dit predikaat.
 */
public class PersoonIdPredikaat implements Predicate {
    private final List<Integer> persoonIds;

    /**
     * Constructor voor dit Predicate.
     *
     * @param persoonIds de lijst van persoon ids die dit predikaat hanteert
     */
    public PersoonIdPredikaat(final List<Integer> persoonIds) {
        this.persoonIds = persoonIds;
    }

    @Override
    public final boolean evaluate(final Object object) {
        final PersoonHisVolledig persoonHisVolledig = (PersoonHisVolledig) object;

        return persoonIds.contains(persoonHisVolledig.getID());
    }
}
