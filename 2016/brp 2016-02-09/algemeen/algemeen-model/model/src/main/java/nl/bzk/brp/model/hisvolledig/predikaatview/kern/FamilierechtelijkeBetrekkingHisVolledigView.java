/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledig;
import org.apache.commons.collections.Predicate;


/**
 * Predikaat view voor Familierechtelijke Betrekking.
 */
public final class FamilierechtelijkeBetrekkingHisVolledigView extends AbstractFamilierechtelijkeBetrekkingHisVolledigView
    implements FamilierechtelijkeBetrekkingHisVolledig
{

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param familierechtelijkeBetrekkingHisVolledig
     *                  relatie
     * @param predikaat predikaat
     */
    public FamilierechtelijkeBetrekkingHisVolledigView(
        final FamilierechtelijkeBetrekkingHisVolledig familierechtelijkeBetrekkingHisVolledig,
        final Predicate predikaat)
    {
        super(familierechtelijkeBetrekkingHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param familierechtelijkeBetrekkingHisVolledig
     *                  relatie
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen
     *                  peilmomentVoorAltijdTonenGroepen
     */
    public FamilierechtelijkeBetrekkingHisVolledigView(
        final FamilierechtelijkeBetrekkingHisVolledig familierechtelijkeBetrekkingHisVolledig,
        final Predicate predikaat, final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(familierechtelijkeBetrekkingHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
