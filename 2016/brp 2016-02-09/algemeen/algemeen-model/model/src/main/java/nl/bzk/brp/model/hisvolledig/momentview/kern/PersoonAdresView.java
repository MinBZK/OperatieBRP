/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonAdresHisMoment;



/**
 * View klasse voor Persoon \ Adres.
 */
public final class PersoonAdresView extends AbstractPersoonAdresView implements PersoonAdresHisMoment {

    /**
     * Constructor die een view aanmaakt met als het meegegeven formele tijdstip. Het materiele moment, is de datum van het gegeven formele tijdstip.
     *
     * @param persoonAdres      hisVolledig instantie voor deze view.
     * @param formeelPeilmoment de datumTijd waarde voor formeelPeilmoment en materieelPeilmoment
     */
    public PersoonAdresView(final PersoonAdresHisVolledig persoonAdres, final DatumTijdAttribuut formeelPeilmoment) {
        this(persoonAdres, formeelPeilmoment, new DatumAttribuut(formeelPeilmoment.getWaarde()));
    }

    /**
     * Constructor die een view aanmaakt met als formeel tijdstip nu en materiele datum vandaag.
     *
     * @param persoonAdres hisVolledig instantie voor deze view.
     */
    public PersoonAdresView(final PersoonAdresHisVolledig persoonAdres) {
        this(persoonAdres, DatumTijdAttribuut.nu());
    }

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoonAdres        hisVolledig instantie voor deze view.
     * @param formeelPeilmoment   formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public PersoonAdresView(final PersoonAdresHisVolledig persoonAdres, final DatumTijdAttribuut formeelPeilmoment,
        final DatumAttribuut materieelPeilmoment)
    {
        super(persoonAdres, formeelPeilmoment, materieelPeilmoment);
    }

}
