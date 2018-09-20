/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.prot;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.prot.LeveringsaantekeningHisVolledig;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Leveringsaantekening.
 *
 */
public class LeveringsaantekeningHisVolledigView extends AbstractLeveringsaantekeningHisVolledigView implements LeveringsaantekeningHisVolledig {

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param leveringsaantekeningHisVolledig leveringsaantekening
     * @param predikaat predikaat
     */
    public LeveringsaantekeningHisVolledigView(final LeveringsaantekeningHisVolledig leveringsaantekeningHisVolledig, final Predicate predikaat) {
        super(leveringsaantekeningHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param leveringsaantekeningHisVolledig leveringsaantekening
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public LeveringsaantekeningHisVolledigView(
        final LeveringsaantekeningHisVolledig leveringsaantekeningHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(leveringsaantekeningHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
