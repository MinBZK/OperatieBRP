/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.migblok;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.migblok.BlokkeringHisVolledig;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Blokkering.
 *
 */
public final class BlokkeringHisVolledigView extends AbstractBlokkeringHisVolledigView implements BlokkeringHisVolledig {

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param blokkeringHisVolledig blokkering
     * @param predikaat predikaat
     */
    public BlokkeringHisVolledigView(final BlokkeringHisVolledig blokkeringHisVolledig, final Predicate predikaat) {
        super(blokkeringHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param blokkeringHisVolledig blokkering
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public BlokkeringHisVolledigView(
        final BlokkeringHisVolledig blokkeringHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(blokkeringHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
