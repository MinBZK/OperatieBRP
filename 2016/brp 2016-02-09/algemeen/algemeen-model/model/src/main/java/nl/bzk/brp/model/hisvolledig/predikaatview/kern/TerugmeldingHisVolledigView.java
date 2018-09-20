/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.TerugmeldingHisVolledig;
import org.apache.commons.collections.Predicate;


/**
 * Predikaat view voor Terugmelding.
 */
public final class TerugmeldingHisVolledigView extends AbstractTerugmeldingHisVolledigView
    implements TerugmeldingHisVolledig
{

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param terugmeldingHisVolledig terugmelding
     * @param predikaat               predikaat
     */
    public TerugmeldingHisVolledigView(final TerugmeldingHisVolledig terugmeldingHisVolledig,
        final Predicate predikaat)
    {
        super(terugmeldingHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param terugmeldingHisVolledig terugmelding
     * @param predikaat               predikaat
     * @param peilmomentVoorAltijdTonenGroepen
     *                                peilmomentVoorAltijdTonenGroepen
     */
    public TerugmeldingHisVolledigView(final TerugmeldingHisVolledig terugmeldingHisVolledig,
        final Predicate predikaat, final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(terugmeldingHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
