/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.autaut;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledig;
import nl.bzk.brp.model.logisch.autaut.PersoonAfnemerindicatieHisMoment;


/**
 * View klasse voor Persoon \ Afnemerindicatie.
 */
public final class PersoonAfnemerindicatieView extends AbstractPersoonAfnemerindicatieView
    implements PersoonAfnemerindicatieHisMoment
{

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoonAfnemerindicatie hisVolledig instantie voor deze view.
     * @param formeelPeilmoment       formeel peilmoment.
     * @param materieelPeilmoment     materieel peilmoment.
     */
    public PersoonAfnemerindicatieView(final PersoonAfnemerindicatieHisVolledig persoonAfnemerindicatie,
        final DatumTijdAttribuut formeelPeilmoment, final DatumAttribuut materieelPeilmoment)
    {
        super(persoonAfnemerindicatie, formeelPeilmoment, materieelPeilmoment);
    }

}
