/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.PersoonCacheHisVolledig;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Persoon cache.
 *
 */
public final class PersoonCacheHisVolledigView extends AbstractPersoonCacheHisVolledigView implements PersoonCacheHisVolledig, ElementIdentificeerbaar {

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param persoonCacheHisVolledig persoonCache
     * @param predikaat predikaat
     */
    public PersoonCacheHisVolledigView(final PersoonCacheHisVolledig persoonCacheHisVolledig, final Predicate predikaat) {
        super(persoonCacheHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param persoonCacheHisVolledig persoonCache
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public PersoonCacheHisVolledigView(
        final PersoonCacheHisVolledig persoonCacheHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(persoonCacheHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
