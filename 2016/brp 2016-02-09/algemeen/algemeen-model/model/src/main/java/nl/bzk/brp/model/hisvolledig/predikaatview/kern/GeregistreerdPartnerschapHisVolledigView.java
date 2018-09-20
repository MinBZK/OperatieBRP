/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.GeregistreerdPartnerschapHisVolledig;
import org.apache.commons.collections.Predicate;


/**
 * Predikaat view voor Geregistreerd partnerschap.
 */
public final class GeregistreerdPartnerschapHisVolledigView extends AbstractGeregistreerdPartnerschapHisVolledigView
    implements GeregistreerdPartnerschapHisVolledig
{

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param geregistreerdPartnerschapHisVolledig
     *                  relatie
     * @param predikaat predikaat
     */
    public GeregistreerdPartnerschapHisVolledigView(
        final GeregistreerdPartnerschapHisVolledig geregistreerdPartnerschapHisVolledig, final Predicate predikaat)
    {
        super(geregistreerdPartnerschapHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param geregistreerdPartnerschapHisVolledig
     *                  relatie
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen
     *                  peilmomentVoorAltijdTonenGroepen
     */
    public GeregistreerdPartnerschapHisVolledigView(
        final GeregistreerdPartnerschapHisVolledig geregistreerdPartnerschapHisVolledig, final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(geregistreerdPartnerschapHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
