/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand;
import nl.bzk.brp.bevraging.business.dto.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.BerichtVerwerkingsFoutCode;
import nl.bzk.brp.bevraging.business.dto.BerichtVerwerkingsFoutZwaarte;
import nl.bzk.brp.bevraging.business.toegangsbewaking.ToegangsBewakingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De stap in de uitvoering van een bericht waarin wordt gecontroleerd of de bevrager wel gerechtigd is de
 * bevraagde functionaliteit uit te voeren. Deze stap is onderdeel van de toegangsbewaking en stopt de
 * verdere afhandeling indien de bevrager geen rechten heeft tot de functionaliteit behorend bij het
 * af te handelen bericht.
 */
public class FunctioneleAutorisatieStap extends AbstractBerichtVerwerkingsStap {

    private static final Logger LOGGER = LoggerFactory.getLogger(FunctioneleAutorisatieStap.class);

    @Inject
    private ToegangsBewakingService toegangsBewakingService;

    /**
     * {@inheritDoc}
     * <br/><br/>
     * Deze stap controleert of de partij, op basis van het abonnement, gerechtigd is om het bericht command uit
     * te voeren. Indien dit niet zo is, dient de verdere verwerking te stoppen en wordt er een fout toegevoegd.
     */
    @Override
    public final boolean voerVerwerkingsStapUitVoorBericht(final BrpBerichtCommand bericht) {
        boolean toegang =
                toegangsBewakingService.isFunctioneelGeautoriseerd(bericht.getContext().getAbonnement(), bericht);

        final boolean result;
        if (toegang) {
            result = DOORGAAN_MET_VERWERKING;
        } else {
            bericht.voegFoutToe(new BerichtVerwerkingsFout(BerichtVerwerkingsFoutCode.FUNCTIONELE_AUTORISATIE_FOUT,
                    BerichtVerwerkingsFoutZwaarte.FOUT));
            LOGGER.warn("Partij {} functioneel niet gerechtigd tot uitvoer bericht {}.",
                    bericht.getContext().getPartijId(), bericht.getContext().getBerichtId());
            result = STOP_VERWERKING;
        }

        return result;
    }

}
