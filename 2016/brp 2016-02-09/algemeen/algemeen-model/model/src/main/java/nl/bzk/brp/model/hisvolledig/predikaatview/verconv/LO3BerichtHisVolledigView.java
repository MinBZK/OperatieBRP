/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.verconv;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.verconv.LO3BerichtHisVolledig;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor LO3 Bericht.
 *
 */
public final class LO3BerichtHisVolledigView extends AbstractLO3BerichtHisVolledigView implements LO3BerichtHisVolledig {

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param lO3BerichtHisVolledig lO3Bericht
     * @param predikaat predikaat
     */
    public LO3BerichtHisVolledigView(final LO3BerichtHisVolledig lO3BerichtHisVolledig, final Predicate predikaat) {
        super(lO3BerichtHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param lO3BerichtHisVolledig lO3Bericht
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public LO3BerichtHisVolledigView(
        final LO3BerichtHisVolledig lO3BerichtHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(lO3BerichtHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
