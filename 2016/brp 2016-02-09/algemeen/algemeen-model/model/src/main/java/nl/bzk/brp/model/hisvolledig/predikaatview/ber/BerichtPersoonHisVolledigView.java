/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.ber;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.ber.BerichtPersoonHisVolledig;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Bericht \ Persoon.
 *
 */
public final class BerichtPersoonHisVolledigView extends AbstractBerichtPersoonHisVolledigView implements BerichtPersoonHisVolledig {

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param berichtPersoonHisVolledig berichtPersoon
     * @param predikaat predikaat
     */
    public BerichtPersoonHisVolledigView(final BerichtPersoonHisVolledig berichtPersoonHisVolledig, final Predicate predikaat) {
        super(berichtPersoonHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param berichtPersoonHisVolledig berichtPersoon
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public BerichtPersoonHisVolledigView(
        final BerichtPersoonHisVolledig berichtPersoonHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(berichtPersoonHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
