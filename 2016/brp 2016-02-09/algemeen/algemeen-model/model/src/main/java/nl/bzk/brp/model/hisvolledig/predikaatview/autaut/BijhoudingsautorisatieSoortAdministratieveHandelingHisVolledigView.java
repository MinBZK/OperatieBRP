/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.autaut;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.autaut.BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledig;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Bijhoudingsautorisatie \ Soort administratieve handeling.
 *
 */
public class BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigView extends
        AbstractBijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigView implements
        BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledig
{

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param bijhoudingsautorisatieSoortAdministratieveHandelingHisVolledig
     *            bijhoudingsautorisatieSoortAdministratieveHandeling
     * @param predikaat predikaat
     */
    public BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigView(
        final BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledig bijhoudingsautorisatieSoortAdministratieveHandelingHisVolledig,
        final Predicate predikaat)
    {
        super(bijhoudingsautorisatieSoortAdministratieveHandelingHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param bijhoudingsautorisatieSoortAdministratieveHandelingHisVolledig
     *            bijhoudingsautorisatieSoortAdministratieveHandeling
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledigView(
        final BijhoudingsautorisatieSoortAdministratieveHandelingHisVolledig bijhoudingsautorisatieSoortAdministratieveHandelingHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(bijhoudingsautorisatieSoortAdministratieveHandelingHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
