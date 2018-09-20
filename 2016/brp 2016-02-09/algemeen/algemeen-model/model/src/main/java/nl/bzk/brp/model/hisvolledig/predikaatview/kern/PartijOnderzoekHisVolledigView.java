/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PartijOnderzoekHisVolledig;
import org.apache.commons.collections.Predicate;


/**
 * Predikaat view voor Partij \ Onderzoek.
 */
public final class PartijOnderzoekHisVolledigView extends AbstractPartijOnderzoekHisVolledigView implements
    PartijOnderzoekHisVolledig
{

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param partijOnderzoekHisVolledig partijOnderzoek
     * @param predikaat                  predikaat
     */
    public PartijOnderzoekHisVolledigView(final PartijOnderzoekHisVolledig partijOnderzoekHisVolledig,
        final Predicate predikaat)
    {
        super(partijOnderzoekHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param partijOnderzoekHisVolledig partijOnderzoek
     * @param predikaat                  predikaat
     * @param peilmomentVoorAltijdTonenGroepen
     *                                   peilmomentVoorAltijdTonenGroepen
     */
    public PartijOnderzoekHisVolledigView(final PartijOnderzoekHisVolledig partijOnderzoekHisVolledig,
        final Predicate predikaat, final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(partijOnderzoekHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
