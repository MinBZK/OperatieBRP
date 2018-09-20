/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.stappen.persoon;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.levering.business.toegang.voorkomenfilter.VoorkomenFilterService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieBerichtContext;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieResultaat;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonBericht;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;


/**
 * Stap die er voor zorgt dat de groepen gefilterd worden. Dat wil zeggen: aan de hand van de authorisatie worden
 * historische voorkomens weggelaten en
 * wordt verantwoordingsinformatie verborgen.
 */
public class VoorkomenFilterStap extends AbstractBerichtVerwerkingStap<GeefSynchronisatiePersoonBericht, SynchronisatieBerichtContext,
    SynchronisatieResultaat>
{

    private static final Logger    LOGGER = LoggerFactory.getLogger();

    @Inject
    private VoorkomenFilterService voorkomenFilterService;

    @Override
    public final boolean voerStapUit(final GeefSynchronisatiePersoonBericht onderwerp, final SynchronisatieBerichtContext context,
                                     final SynchronisatieResultaat resultaat)
    {
        final VolledigBericht volledigBericht = context.getVolledigBericht();
        final List<PersoonHisVolledigView> persoonHisVolledigViews = volledigBericht.getAdministratieveHandeling().getBijgehoudenPersonen();
        final Leveringinformatie leveringinformatie = context.getLeveringinformatie();
        final Leveringsautorisatie leveringsautorisatie = leveringinformatie.getToegangLeveringsautorisatie().getLeveringsautorisatie();

        LOGGER.debug("Groepenfilter uitvoeren voor leveringsautorisatie met id {}.", leveringsautorisatie.getID());

        for (final PersoonHisVolledigView persoonHisVolledigView : persoonHisVolledigViews) {
            try {
                voorkomenFilterService.voerVoorkomenFilterUit(persoonHisVolledigView, leveringinformatie.getDienst());
            } catch (final ExpressieExceptie expressieExceptie) {
                LOGGER.error(FunctioneleMelding.LEVERING_UITVOER_VOORKOMEN_FILTER_MISLUKT,
                        "Er is een fout opgetreden tijdens de uitvoer van het voorkomen filter.", expressieExceptie);
                return STOPPEN;
            }
        }

        return DOORGAAN;
    }

}
