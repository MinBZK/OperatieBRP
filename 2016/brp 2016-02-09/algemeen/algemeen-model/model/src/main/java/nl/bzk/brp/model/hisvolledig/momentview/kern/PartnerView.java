/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PartnerHisVolledig;
import nl.bzk.brp.model.logisch.kern.Partner;


/**
 * View klasse voor Partner.
 */
public final class PartnerView extends AbstractPartnerView implements Partner {

    /**
     * Constructor die een view aanmaakt met als formeel tijdstip nu en materiele datum vandaag.
     *
     * @param partner partner proxy
     */
    public PartnerView(final PartnerHisVolledig partner) {
        this(partner, DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());
    }

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param betrokkenheid       hisVolledig instantie voor deze view.
     * @param formeelPeilmoment   formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public PartnerView(final PartnerHisVolledig betrokkenheid, final DatumTijdAttribuut formeelPeilmoment,
        final DatumAttribuut materieelPeilmoment)
    {
        super(betrokkenheid, formeelPeilmoment, materieelPeilmoment);
    }

    @Override
    public boolean heeftActueleGegevens() {
        // De betrokkenheid is actueel als de relatie actueel is.
        return this.getRelatie().heeftActueleGegevens();
    }

}
