/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen.persoon;

import javax.inject.Inject;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtContext;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesResultaat;
import nl.bzk.brp.levering.business.toegang.voorkomenfilter.VoorkomenFilterService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;


/**
 * Stap die er voor zorgt dat de groepen gefilterd worden. Dat wil zeggen: aan de hand van de authorisatie worden
 * historische voorkomens weggelaten en
 * wordt verantwoordingsinformatie verborgen.
 */
public class VoorkomenFilterStap
        extends
        AbstractBerichtVerwerkingStap<RegistreerAfnemerindicatieBericht, OnderhoudAfnemerindicatiesBerichtContext, OnderhoudAfnemerindicatiesResultaat>
{

    private static final Logger    LOGGER = LoggerFactory.getLogger();

    @Inject
    private VoorkomenFilterService voorkomenFilterService;

    @Override
    public final boolean voerStapUit(final RegistreerAfnemerindicatieBericht onderwerp,
            final OnderhoudAfnemerindicatiesBerichtContext context,
            final OnderhoudAfnemerindicatiesResultaat resultaat)
    {
        final VolledigBericht volledigBericht = context.getVolledigBericht();
        final PersoonHisVolledigView persoonView =
                volledigBericht.getAdministratieveHandeling().getBijgehoudenPersonen().get(0);

        final Leveringinformatie leveringinformatie = context.getLeveringinformatie();
        final Leveringsautorisatie leveringsautorisatie = leveringinformatie.getToegangLeveringsautorisatie().getLeveringsautorisatie();

        LOGGER.debug("Voorkomenfilter uitvoeren voor leveringsautorisatie met id {}.", leveringsautorisatie.getID());

        try {
            voorkomenFilterService.voerVoorkomenFilterUit(persoonView, leveringinformatie.getDienst());
        } catch (final ExpressieExceptie expressieExceptie) {
            LOGGER.error(FunctioneleMelding.LEVERING_UITVOER_VOORKOMEN_FILTER_MISLUKT,
                    "Er is een fout opgetreden tijdens de uitvoer van het voorkomen filter.", expressieExceptie);
            return STOPPEN;
        }

        return DOORGAAN;
    }
}
