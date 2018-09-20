/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.autaut;

import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledig;
import nl.bzk.brp.model.logisch.autaut.PersoonAfnemerindicatieHisMoment;
import org.apache.commons.collections.Predicate;

/**
 * View klasse voor Persoon \ Afnemerindicatie.
 */
public final class PersoonAfnemerindicatiePredikaatView extends AbstractPersoonAfnemerindicatiePredikaatView
    implements ModelMoment, PersoonAfnemerindicatieHisMoment
{

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoonAfnemerindicatie hisVolledig instantie voor deze view.
     * @param predikaat               het predikaat.
     */
    public PersoonAfnemerindicatiePredikaatView(final PersoonAfnemerindicatieHisVolledig persoonAfnemerindicatie, final Predicate predikaat) {
        super(persoonAfnemerindicatie, predikaat);
    }

}
