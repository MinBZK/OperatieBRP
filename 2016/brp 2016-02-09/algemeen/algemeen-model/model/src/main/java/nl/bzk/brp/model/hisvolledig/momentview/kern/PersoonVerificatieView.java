/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerificatieHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonVerificatieHisMoment;


/**
 * View klasse voor Persoon \ Verificatie.
 */
public final class PersoonVerificatieView extends AbstractPersoonVerificatieView implements ModelMoment, PersoonVerificatieHisMoment {

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoonVerificatie  hisVolledig instantie voor deze view.
     * @param formeelPeilmoment   formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public PersoonVerificatieView(final PersoonVerificatieHisVolledig persoonVerificatie,
        final DatumTijdAttribuut formeelPeilmoment, final DatumAttribuut materieelPeilmoment)
    {
        super(persoonVerificatie, formeelPeilmoment, materieelPeilmoment);
    }

}
