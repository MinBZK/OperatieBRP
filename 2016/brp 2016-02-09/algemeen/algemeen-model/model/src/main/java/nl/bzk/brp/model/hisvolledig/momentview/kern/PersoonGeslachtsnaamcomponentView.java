/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponentHisMoment;


/**
 * View klasse voor Persoon \ Geslachtsnaamcomponent.
 */
public final class PersoonGeslachtsnaamcomponentView extends AbstractPersoonGeslachtsnaamcomponentView implements
    PersoonGeslachtsnaamcomponentHisMoment
{

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoonGeslachtsnaamcomponent hisVolledig instantie voor deze view.
     * @param formeelPeilmoment             formeel peilmoment.
     * @param materieelPeilmoment           materieel peilmoment.
     */
    public PersoonGeslachtsnaamcomponentView(
        final PersoonGeslachtsnaamcomponentHisVolledig persoonGeslachtsnaamcomponent,
        final DatumTijdAttribuut formeelPeilmoment, final DatumAttribuut materieelPeilmoment)
    {
        super(persoonGeslachtsnaamcomponent, formeelPeilmoment, materieelPeilmoment);
    }

}
