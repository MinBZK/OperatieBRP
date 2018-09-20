/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.autaut;

import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.autaut.PersoonAfnemerindicatieStandaardGroep;
import org.jibx.runtime.IUnmarshallingContext;


/**
 *
 *
 */
public final class PersoonAfnemerindicatieStandaardGroepBericht extends AbstractPersoonAfnemerindicatieStandaardGroepBericht
    implements Groep, PersoonAfnemerindicatieStandaardGroep, MetaIdentificeerbaar
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
