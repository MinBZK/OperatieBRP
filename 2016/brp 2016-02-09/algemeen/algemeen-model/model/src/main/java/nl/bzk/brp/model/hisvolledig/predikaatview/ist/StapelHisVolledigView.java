/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.ist;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.ist.StapelHisVolledig;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Stapel.
 *
 */
public final class StapelHisVolledigView extends AbstractStapelHisVolledigView implements StapelHisVolledig {

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param stapelHisVolledig stapel
     * @param predikaat predikaat
     */
    public StapelHisVolledigView(final StapelHisVolledig stapelHisVolledig, final Predicate predikaat) {
        super(stapelHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param stapelHisVolledig stapel
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public StapelHisVolledigView(
        final StapelHisVolledig stapelHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(stapelHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
