/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.autaut;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledig;
import org.apache.commons.collections.Predicate;


/**
 * Predikaat view voor Persoon \ Afnemerindicatie.
 */
public final class PersoonAfnemerindicatieHisVolledigView extends AbstractPersoonAfnemerindicatieHisVolledigView implements
    PersoonAfnemerindicatieHisVolledig
{

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param persoonAfnemerindicatieHisVolledig
     *                  persoonAfnemerindicatie
     * @param predikaat predikaat
     */
    public PersoonAfnemerindicatieHisVolledigView(
        final PersoonAfnemerindicatieHisVolledig persoonAfnemerindicatieHisVolledig, final Predicate predikaat)
    {
        super(persoonAfnemerindicatieHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param persoonAfnemerindicatieHisVolledig
     *                  persoonAfnemerindicatie
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen
     *                  peilmomentVoorAltijdTonenGroepen
     */
    public PersoonAfnemerindicatieHisVolledigView(
        final PersoonAfnemerindicatieHisVolledig persoonAfnemerindicatieHisVolledig, final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(persoonAfnemerindicatieHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
