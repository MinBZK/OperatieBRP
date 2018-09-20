/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteitStandaardGroep;
import org.jibx.runtime.IUnmarshallingContext;


/**
 * Vorm van historie: beiden. Motivatie: een persoon kan een bepaalde Nationaliteit verkrijgen, dan wel verliezen. Naast een formele historie ('wat stond
 * geregistreerd') is dus ook materi�le historie denkbaar ('over welke periode beschikte hij over de Nederlandse Nationaliteit'). We leggen beide vast, ��k
 * omdat dit van oudsher gebeurde vanuit de GBA. RvdP 17 jan 2012.
 */
public final class PersoonNationaliteitStandaardGroepBericht extends AbstractPersoonNationaliteitStandaardGroepBericht
    implements PersoonNationaliteitStandaardGroep
{

    /**
     * Hook voor Jibx om de communicatieId van de encapsulerende object te zetten. De standaard groep wordt niet geregistreerd in de CommunicatieIdMap.
     *
     * @param ctx de jibx context
     */
    public void jibxPostSetCommunicatieId(final IUnmarshallingContext ctx) {
        setCommunicatieID(((BerichtIdentificeerbaar) ctx.getStackObject(1)).getCommunicatieID());
    }

}
