/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkHisVolledig;
import nl.bzk.brp.model.logisch.kern.Huwelijk;


/**
 * View klasse voor Huwelijk.
 */
public final class HuwelijkView extends AbstractHuwelijkView implements Huwelijk {

    /**
     * Constructor die een view aanmaakt met als het meegegeven formele tijdstip. Het materiele moment, is de datum van het gegeven formele tijdstip.
     *
     * @param relatie           de relatieVolledig instantie die de informatie bevat
     * @param formeelPeilmoment de datumTijd waarde voor formeelPeilmoment en materieelPeilmoment
     */
    public HuwelijkView(final HuwelijkHisVolledig relatie, final DatumTijdAttribuut formeelPeilmoment) {
        this(relatie, formeelPeilmoment, new DatumAttribuut(formeelPeilmoment.getWaarde()));
    }

    /**
     * Constructor die een view aanmaakt met als formeel tijdstip nu en materiele datum vandaag.
     *
     * @param relatie relatie proxy
     */
    public HuwelijkView(final HuwelijkHisVolledig relatie) {
        this(relatie, DatumTijdAttribuut.nu());
    }

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param relatie             hisVolledig instantie voor deze view.
     * @param formeelPeilmoment   formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public HuwelijkView(final HuwelijkHisVolledig relatie, final DatumTijdAttribuut formeelPeilmoment,
        final DatumAttribuut materieelPeilmoment)
    {
        super(relatie, formeelPeilmoment, materieelPeilmoment);
    }

}
