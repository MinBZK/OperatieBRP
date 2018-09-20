/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonReisdocumentStandaardGroep;
import org.jibx.runtime.IUnmarshallingContext;


/**
 * 1. Vorm van historie: alleen formeel. Motivatie: de gegevens van een reisdocument zijn enerzijds de gegevens die in het document staan, zoals lengte van
 * de houder. Anderzijds zijn het gegevens die normaliter ��nmalig wijzigen, zoals reden vervallen. Omdat hetzelfde reisdocument niet tweemaal wordt
 * uitgegeven, is formele historie voldoende. RvdP 26 jan 2012.
 */
public final class PersoonReisdocumentStandaardGroepBericht extends AbstractPersoonReisdocumentStandaardGroepBericht
    implements PersoonReisdocumentStandaardGroep
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
