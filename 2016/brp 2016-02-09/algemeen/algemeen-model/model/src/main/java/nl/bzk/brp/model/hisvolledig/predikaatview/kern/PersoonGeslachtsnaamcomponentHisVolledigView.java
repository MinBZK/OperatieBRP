/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledig;
import org.apache.commons.collections.Predicate;


/**
 * Predikaat view voor Persoon \ Geslachtsnaamcomponent.
 */
public final class PersoonGeslachtsnaamcomponentHisVolledigView extends AbstractPersoonGeslachtsnaamcomponentHisVolledigView
    implements PersoonGeslachtsnaamcomponentHisVolledig
{

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param persoonGeslachtsnaamcomponentHisVolledig
     *                  persoonGeslachtsnaamcomponent
     * @param predikaat predikaat
     */
    public PersoonGeslachtsnaamcomponentHisVolledigView(
        final PersoonGeslachtsnaamcomponentHisVolledig persoonGeslachtsnaamcomponentHisVolledig,
        final Predicate predikaat)
    {
        super(persoonGeslachtsnaamcomponentHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param persoonGeslachtsnaamcomponentHisVolledig
     *                  persoonGeslachtsnaamcomponent
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen
     *                  peilmomentVoorAltijdTonenGroepen
     */
    public PersoonGeslachtsnaamcomponentHisVolledigView(
        final PersoonGeslachtsnaamcomponentHisVolledig persoonGeslachtsnaamcomponentHisVolledig,
        final Predicate predikaat, final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(persoonGeslachtsnaamcomponentHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
