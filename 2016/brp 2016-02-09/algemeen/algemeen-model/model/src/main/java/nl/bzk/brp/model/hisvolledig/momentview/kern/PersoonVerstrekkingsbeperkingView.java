/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonVerstrekkingsbeperking;
import nl.bzk.brp.model.logisch.kern.PersoonVerstrekkingsbeperkingHisMoment;


/**
 * View klasse voor Persoon \ Verstrekkingsbeperking.
 */
public final class PersoonVerstrekkingsbeperkingView extends AbstractPersoonVerstrekkingsbeperkingView implements
    ModelMoment, PersoonVerstrekkingsbeperkingHisMoment
{

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoonVerstrekkingsbeperking hisVolledig instantie voor deze view.
     * @param formeelPeilmoment             formeel peilmoment.
     * @param materieelPeilmoment           materieel peilmoment.
     */
    public PersoonVerstrekkingsbeperkingView(
        final PersoonVerstrekkingsbeperkingHisVolledig persoonVerstrekkingsbeperking,
        final DatumTijdAttribuut formeelPeilmoment, final DatumAttribuut materieelPeilmoment)
    {
        super(persoonVerstrekkingsbeperking, formeelPeilmoment, materieelPeilmoment);
    }

}
