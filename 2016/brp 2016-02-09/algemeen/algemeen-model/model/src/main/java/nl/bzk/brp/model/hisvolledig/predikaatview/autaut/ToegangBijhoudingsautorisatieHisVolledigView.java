/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.autaut;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.autaut.ToegangBijhoudingsautorisatieHisVolledig;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Toegang bijhoudingsautorisatie.
 *
 */
public class ToegangBijhoudingsautorisatieHisVolledigView extends AbstractToegangBijhoudingsautorisatieHisVolledigView implements
        ToegangBijhoudingsautorisatieHisVolledig
{

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param toegangBijhoudingsautorisatieHisVolledig toegangBijhoudingsautorisatie
     * @param predikaat predikaat
     */
    public ToegangBijhoudingsautorisatieHisVolledigView(
        final ToegangBijhoudingsautorisatieHisVolledig toegangBijhoudingsautorisatieHisVolledig,
        final Predicate predikaat)
    {
        super(toegangBijhoudingsautorisatieHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param toegangBijhoudingsautorisatieHisVolledig toegangBijhoudingsautorisatie
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public ToegangBijhoudingsautorisatieHisVolledigView(
        final ToegangBijhoudingsautorisatieHisVolledig toegangBijhoudingsautorisatieHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(toegangBijhoudingsautorisatieHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
