/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.KindHisVolledig;
import nl.bzk.brp.model.logisch.kern.Kind;
import org.apache.commons.collections.Predicate;

/**
 * View klasse voor Kind.
 */
public final class KindPredikaatView extends AbstractKindPredikaatView implements ModelMoment, Kind, ElementIdentificeerbaar {

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param betrokkenheid hisVolledig instantie voor deze view.
     * @param predikaat     het predikaat.
     */
    public KindPredikaatView(final KindHisVolledig betrokkenheid, final Predicate predikaat) {
        super(betrokkenheid, predikaat);
    }

    @Override
    public boolean heeftActueleGegevens() {
        // De betrokkenheid is actueel als de relatie actueel is.
        return this.getRelatie().heeftActueleGegevens();
    }

}
