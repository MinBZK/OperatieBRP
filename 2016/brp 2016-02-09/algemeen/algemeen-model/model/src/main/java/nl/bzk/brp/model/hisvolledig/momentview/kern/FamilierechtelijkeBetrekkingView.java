/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledig;
import nl.bzk.brp.model.logisch.kern.FamilierechtelijkeBetrekking;


/**
 * View klasse voor Familierechtelijke Betrekking.
 */
public final class FamilierechtelijkeBetrekkingView extends AbstractFamilierechtelijkeBetrekkingView implements
    FamilierechtelijkeBetrekking
{

    /**
     * Constructor die een view aanmaakt met als formeel tijdstip nu en materiele datum vandaag.
     *
     * @param relatie relatie proxy
     */
    public FamilierechtelijkeBetrekkingView(final FamilierechtelijkeBetrekkingHisVolledig relatie) {
        this(relatie, DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());
    }

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param relatie             hisVolledig instantie voor deze view.
     * @param formeelPeilmoment   formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public FamilierechtelijkeBetrekkingView(final FamilierechtelijkeBetrekkingHisVolledig relatie,
        final DatumTijdAttribuut formeelPeilmoment, final DatumAttribuut materieelPeilmoment)
    {
        super(relatie, formeelPeilmoment, materieelPeilmoment);
    }

    @Override
    public boolean heeftActueleGegevens() {
        // vooralsnog beschouwen we de betrekking altijd als actueel
        return true;
    }

}
