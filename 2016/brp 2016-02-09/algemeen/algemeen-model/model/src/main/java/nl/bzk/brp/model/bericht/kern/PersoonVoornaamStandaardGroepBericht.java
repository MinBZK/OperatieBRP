/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import javax.validation.Valid;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaamStandaardGroep;
import org.jibx.runtime.IUnmarshallingContext;


/**
 * Vorm van historie: beiden. Motivatie: conform samengestelde naam kan een individuele voornaam in de loop van de tijd (c.q.: in de werkelijkheid)
 * veranderen, dus nog los van eventuele registratiefouten. Daarom dus beide vormen van historie. RvdP 17 jan 2012.
 */
public final class PersoonVoornaamStandaardGroepBericht extends AbstractPersoonVoornaamStandaardGroepBericht implements
    PersoonVoornaamStandaardGroep
{

    @Valid
    @nl.bzk.brp.model.validatie.constraint.GeenSpatie(dbObject = DatabaseObjectKern.PERSOON_VOORNAAM__NAAM)
    @Override
    public VoornaamAttribuut getNaam() {
        return super.getNaam();
    }

    /**
     * Hook voor Jibx om de communicatieId van de encapsulerende object te zetten. De standaard groep wordt niet geregistreerd in de CommunicatieIdMap.
     *
     * @param ctx de jibx context
     */
    public void jibxPostSetCommunicatieId(final IUnmarshallingContext ctx) {
        setCommunicatieID(((BerichtIdentificeerbaar) ctx.getStackObject(1)).getCommunicatieID());
    }
}
