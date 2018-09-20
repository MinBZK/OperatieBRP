/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.lev;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.lev.LeveringHisVolledig;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Levering.
 *
 */
public final class LeveringHisVolledigView extends AbstractLeveringHisVolledigView implements LeveringHisVolledig {

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param leveringHisVolledig levering
     * @param predikaat predikaat
     */
    public LeveringHisVolledigView(final LeveringHisVolledig leveringHisVolledig, final Predicate predikaat) {
        super(leveringHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param leveringHisVolledig levering
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public LeveringHisVolledigView(
        final LeveringHisVolledig leveringHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(leveringHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
