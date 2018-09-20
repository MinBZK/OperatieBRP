/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.ErkenningOngeborenVruchtHisVolledig;
import org.apache.commons.collections.Predicate;


/**
 * Predikaat view voor Erkenning ongeboren vrucht.
 */
public final class ErkenningOngeborenVruchtHisVolledigView extends AbstractErkenningOngeborenVruchtHisVolledigView implements
    ErkenningOngeborenVruchtHisVolledig
{

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param erkenningOngeborenVruchtHisVolledig
     *                  relatie
     * @param predikaat predikaat
     */
    public ErkenningOngeborenVruchtHisVolledigView(
        final ErkenningOngeborenVruchtHisVolledig erkenningOngeborenVruchtHisVolledig, final Predicate predikaat)
    {
        super(erkenningOngeborenVruchtHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param erkenningOngeborenVruchtHisVolledig
     *                  relatie
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen
     *                  peilmomentVoorAltijdTonenGroepen
     */
    public ErkenningOngeborenVruchtHisVolledigView(
        final ErkenningOngeborenVruchtHisVolledig erkenningOngeborenVruchtHisVolledig, final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(erkenningOngeborenVruchtHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
