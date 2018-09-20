/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand;


/**
 * De stap in de uitvoering van een bericht waarin de werkelijke executie van het bericht wordt uitgevoerd. In deze
 * stap wordt de door het bericht opgegeven verzoek uitgevoerd en het antwoord/resultaat geformuleerd en aan het
 * bericht toegevoegd. Hiervoor wordt dan ook de {@link BrpBerichtCommand#voerUit()} methode aangeroepen.
 */
public class BerichtUitvoerStap extends AbstractBerichtVerwerkingsStap {

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean voerVerwerkingsStapUitVoorBericht(final BrpBerichtCommand bericht) {
        bericht.voerUit();
        return DOORGAAN_MET_VERWERKING;
    }

}
