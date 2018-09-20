/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonReisdocumentHisVolledig;
import org.apache.commons.collections.Predicate;


/**
 * Predikaat view voor Persoon \ Reisdocument.
 */
public final class PersoonReisdocumentHisVolledigView extends AbstractPersoonReisdocumentHisVolledigView implements
    PersoonReisdocumentHisVolledig
{

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param persoonReisdocumentHisVolledig persoonReisdocument
     * @param predikaat                      predikaat
     */
    public PersoonReisdocumentHisVolledigView(final PersoonReisdocumentHisVolledig persoonReisdocumentHisVolledig,
        final Predicate predikaat)
    {
        super(persoonReisdocumentHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param persoonReisdocumentHisVolledig persoonReisdocument
     * @param predikaat                      predikaat
     * @param peilmomentVoorAltijdTonenGroepen
     *                                       peilmomentVoorAltijdTonenGroepen
     */
    public PersoonReisdocumentHisVolledigView(final PersoonReisdocumentHisVolledig persoonReisdocumentHisVolledig,
        final Predicate predikaat, final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(persoonReisdocumentHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
