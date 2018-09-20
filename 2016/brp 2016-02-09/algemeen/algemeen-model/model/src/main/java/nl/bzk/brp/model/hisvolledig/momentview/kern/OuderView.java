/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.logisch.kern.OuderHisMoment;


/**
 * View klasse voor Ouder.
 */
public final class OuderView extends AbstractOuderView implements OuderHisMoment {

    /**
     * Constructor die een view aanmaakt met als formeel tijdstip nu en materiele datum vandaag.
     *
     * @param ouder ouder proxy
     */
    public OuderView(final OuderHisVolledig ouder) {
        this(ouder, DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());
    }

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param betrokkenheid       hisVolledig instantie voor deze view.
     * @param formeelPeilmoment   formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public OuderView(final OuderHisVolledig betrokkenheid, final DatumTijdAttribuut formeelPeilmoment,
        final DatumAttribuut materieelPeilmoment)
    {
        super(betrokkenheid, formeelPeilmoment, materieelPeilmoment);
    }

}
