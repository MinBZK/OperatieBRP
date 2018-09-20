/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging.levering;

import javax.inject.Inject;

import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContextBasis;
import nl.bzk.brp.levering.business.toegang.voorkomenfilter.VoorkomenFilterService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;


/**
 * Stap die er voor zorgt dat de groepen gefilterd worden. Dat wil zeggen: aan de hand van de autorisatie worden
 * historische voorkomens weggelaten en
 * wordt verantwoordingsinformatie verborgen.
 */
public class VoorkomenFilterStap extends
        AbstractBerichtVerwerkingStap<BevragingsBericht, BevragingBerichtContextBasis, BevragingResultaat>
{

    private static final Logger    LOGGER = LoggerFactory.getLogger();

    @Inject
    private VoorkomenFilterService voorkomenFilterService;

    @Override
    public final boolean voerStapUit(final BevragingsBericht onderwerp, final BevragingBerichtContextBasis context,
            final BevragingResultaat resultaat)
    {
        try {
            for (final PersoonHisVolledigView persoon : resultaat.getGevondenPersonen()) {
                voorkomenFilterService.voerVoorkomenFilterUit(persoon, context.getLeveringinformatie().getDienst());
            }
        } catch (final ExpressieExceptie expressieExceptie) {
            LOGGER.error(FunctioneleMelding.BEVRAGING_FILTEREN_VOORKOMEN,
                    "Er is een fout opgetreden tijdens de uitvoer van het voorkomen filter.", expressieExceptie);
            return STOPPEN;
        }

        return DOORGAAN;
    }
}
