/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatie;
import org.apache.commons.collections.Predicate;

/**
 * View klasse voor Persoon \ Indicatie.
 */
public final class PersoonIndicatiePredikaatView extends AbstractPersoonIndicatiePredikaatView
    implements ModelMoment, PersoonIndicatie, ElementIdentificeerbaar
{

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoonIndicatie hisVolledig instantie voor deze view.
     * @param predikaat        het predikaat.
     */
    public PersoonIndicatiePredikaatView(final PersoonIndicatieHisVolledig persoonIndicatie, final Predicate predikaat) {
        super(persoonIndicatie, predikaat);
    }

}
