/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import org.apache.commons.collections.Predicate;


/**
 * Predikaat view voor Persoon \ Nationaliteit.
 */
public final class PersoonNationaliteitHisVolledigView extends AbstractPersoonNationaliteitHisVolledigView implements
    PersoonNationaliteitHisVolledig
{

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param persoonNationaliteitHisVolledig
     *                  persoonNationaliteit
     * @param predikaat predikaat
     */
    public PersoonNationaliteitHisVolledigView(final PersoonNationaliteitHisVolledig persoonNationaliteitHisVolledig,
        final Predicate predikaat)
    {
        super(persoonNationaliteitHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param persoonNationaliteitHisVolledig
     *                  persoonNationaliteit
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen
     *                  peilmomentVoorAltijdTonenGroepen
     */
    public PersoonNationaliteitHisVolledigView(final PersoonNationaliteitHisVolledig persoonNationaliteitHisVolledig,
        final Predicate predikaat, final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(persoonNationaliteitHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
