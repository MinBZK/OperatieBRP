/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.PartijOnderzoekHisVolledig;
import nl.bzk.brp.model.logisch.kern.PartijOnderzoekHisMoment;
import org.apache.commons.collections.Predicate;

/**
 * View klasse voor Partij \ Onderzoek.
 */
public final class PartijOnderzoekPredikaatView extends AbstractPartijOnderzoekPredikaatView implements ModelMoment, PartijOnderzoekHisMoment,
    ElementIdentificeerbaar
{

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param partijOnderzoek hisVolledig instantie voor deze view.
     * @param predikaat       het predikaat.
     */
    public PartijOnderzoekPredikaatView(final PartijOnderzoekHisVolledig partijOnderzoek, final Predicate predikaat) {
        super(partijOnderzoek, predikaat);
    }

}
