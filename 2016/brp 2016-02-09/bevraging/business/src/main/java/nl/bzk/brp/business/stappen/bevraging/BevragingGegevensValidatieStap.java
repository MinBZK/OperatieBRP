/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging;

import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtValidatieStap;


/**
 * De stap in de uitvoering van een bericht waarin wordt gecontroleerd of het binnenkomende bericht valide is.
 * Hierbij wordt gecontroleerd of de benodigde parameters aanwezig zijn, er geen tegenstrijdigheden in zitten
 * en of er geen ongeldige waardes tussen zitten. Eventueel geconstateerde invalide waardes worden, inclusief
 * bericht melding en zwaarte, toegevoegd aan de lijst van fouten binnen het antwoord.
 */
public class BevragingGegevensValidatieStap extends
        AbstractBerichtValidatieStap<BevragingsBericht, BevragingsBericht, BevragingBerichtContext,
                BevragingResultaat>
{

    @Override
    public final boolean voerStapUit(final BevragingsBericht bericht,
                               final BevragingBerichtContext context,
                               final BevragingResultaat resultaat)
    {
        valideer(bericht, resultaat);
        return DOORGAAN;
    }

}
