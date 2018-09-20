/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.autaut;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.autaut.PartijFiatteringsuitzonderingHisVolledig;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Partij \ Fiatteringsuitzondering.
 *
 */
public class PartijFiatteringsuitzonderingHisVolledigView extends AbstractPartijFiatteringsuitzonderingHisVolledigView implements
        PartijFiatteringsuitzonderingHisVolledig
{

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param partijFiatteringsuitzonderingHisVolledig partijFiatteringsuitzondering
     * @param predikaat predikaat
     */
    public PartijFiatteringsuitzonderingHisVolledigView(
        final PartijFiatteringsuitzonderingHisVolledig partijFiatteringsuitzonderingHisVolledig,
        final Predicate predikaat)
    {
        super(partijFiatteringsuitzonderingHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param partijFiatteringsuitzonderingHisVolledig partijFiatteringsuitzondering
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public PartijFiatteringsuitzonderingHisVolledigView(
        final PartijFiatteringsuitzonderingHisVolledig partijFiatteringsuitzonderingHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(partijFiatteringsuitzonderingHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

}
