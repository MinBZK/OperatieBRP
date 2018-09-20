/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInTerugmeldingHisVolledig;
import org.apache.commons.collections.Predicate;


/**
 * Predikaat view voor Gegeven in terugmelding.
 */
public final class GegevenInTerugmeldingHisVolledigView extends AbstractGegevenInTerugmeldingHisVolledigView implements
    GegevenInTerugmeldingHisVolledig
{

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param gegevenInTerugmeldingHisVolledig
     *                  gegevenInTerugmelding
     * @param predikaat predikaat
     */
    public GegevenInTerugmeldingHisVolledigView(
        final GegevenInTerugmeldingHisVolledig gegevenInTerugmeldingHisVolledig, final Predicate predikaat)
    {
        super(gegevenInTerugmeldingHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param gegevenInTerugmeldingHisVolledig
     *                  gegevenInTerugmelding
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen
     *                  peilmomentVoorAltijdTonenGroepen
     */
    public GegevenInTerugmeldingHisVolledigView(
        final GegevenInTerugmeldingHisVolledig gegevenInTerugmeldingHisVolledig, final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(gegevenInTerugmeldingHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
