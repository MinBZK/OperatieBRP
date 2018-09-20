/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.ErkenningOngeborenVruchtHisVolledig;
import nl.bzk.brp.model.logisch.kern.ErkenningOngeborenVrucht;


/**
 * View klasse voor Erkenning ongeboren vrucht.
 */
public final class ErkenningOngeborenVruchtView extends AbstractErkenningOngeborenVruchtView implements
    ErkenningOngeborenVrucht
{

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param relatie             hisVolledig instantie voor deze view.
     * @param formeelPeilmoment   formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public ErkenningOngeborenVruchtView(final ErkenningOngeborenVruchtHisVolledig relatie,
        final DatumTijdAttribuut formeelPeilmoment, final DatumAttribuut materieelPeilmoment)
    {
        super(relatie, formeelPeilmoment, materieelPeilmoment);
    }

}
