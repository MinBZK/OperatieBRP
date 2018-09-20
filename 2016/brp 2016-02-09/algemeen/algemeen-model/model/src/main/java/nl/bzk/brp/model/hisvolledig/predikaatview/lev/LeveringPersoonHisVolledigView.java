/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.lev;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.lev.LeveringPersoonHisVolledig;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Levering \ Persoon.
 *
 */
public final class LeveringPersoonHisVolledigView extends AbstractLeveringPersoonHisVolledigView implements LeveringPersoonHisVolledig {

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param leveringPersoonHisVolledig leveringPersoon
     * @param predikaat predikaat
     */
    public LeveringPersoonHisVolledigView(final LeveringPersoonHisVolledig leveringPersoonHisVolledig, final Predicate predikaat) {
        super(leveringPersoonHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param leveringPersoonHisVolledig leveringPersoon
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public LeveringPersoonHisVolledigView(
        final LeveringPersoonHisVolledig leveringPersoonHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(leveringPersoonHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
