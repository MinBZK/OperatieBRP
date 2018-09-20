/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.prot;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.prot.LeveringsaantekeningPersoonHisVolledig;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Leveringsaantekening \ Persoon.
 *
 */
public class LeveringsaantekeningPersoonHisVolledigView extends AbstractLeveringsaantekeningPersoonHisVolledigView implements
        LeveringsaantekeningPersoonHisVolledig
{

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param leveringsaantekeningPersoonHisVolledig leveringsaantekeningPersoon
     * @param predikaat predikaat
     */
    public LeveringsaantekeningPersoonHisVolledigView(
        final LeveringsaantekeningPersoonHisVolledig leveringsaantekeningPersoonHisVolledig,
        final Predicate predikaat)
    {
        super(leveringsaantekeningPersoonHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param leveringsaantekeningPersoonHisVolledig leveringsaantekeningPersoon
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public LeveringsaantekeningPersoonHisVolledigView(
        final LeveringsaantekeningPersoonHisVolledig leveringsaantekeningPersoonHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(leveringsaantekeningPersoonHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
