/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.NaamskeuzeOngeborenVruchtHisVolledig;
import org.apache.commons.collections.Predicate;


/**
 * Predikaat view voor Naamskeuze ongeboren vrucht.
 */
public final class NaamskeuzeOngeborenVruchtHisVolledigView extends AbstractNaamskeuzeOngeborenVruchtHisVolledigView
    implements NaamskeuzeOngeborenVruchtHisVolledig
{

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param naamskeuzeOngeborenVruchtHisVolledig
     *                  relatie
     * @param predikaat predikaat
     */
    public NaamskeuzeOngeborenVruchtHisVolledigView(
        final NaamskeuzeOngeborenVruchtHisVolledig naamskeuzeOngeborenVruchtHisVolledig, final Predicate predikaat)
    {
        super(naamskeuzeOngeborenVruchtHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param naamskeuzeOngeborenVruchtHisVolledig
     *                  relatie
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen
     *                  peilmomentVoorAltijdTonenGroepen
     */
    public NaamskeuzeOngeborenVruchtHisVolledigView(
        final NaamskeuzeOngeborenVruchtHisVolledig naamskeuzeOngeborenVruchtHisVolledig, final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(naamskeuzeOngeborenVruchtHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
