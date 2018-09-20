/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.verconv;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.verconv.LO3MeldingHisVolledig;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor LO3 Melding.
 *
 */
public final class LO3MeldingHisVolledigView extends AbstractLO3MeldingHisVolledigView implements LO3MeldingHisVolledig {

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param lO3MeldingHisVolledig lO3Melding
     * @param predikaat predikaat
     */
    public LO3MeldingHisVolledigView(final LO3MeldingHisVolledig lO3MeldingHisVolledig, final Predicate predikaat) {
        super(lO3MeldingHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param lO3MeldingHisVolledig lO3Melding
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public LO3MeldingHisVolledigView(
        final LO3MeldingHisVolledig lO3MeldingHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(lO3MeldingHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
