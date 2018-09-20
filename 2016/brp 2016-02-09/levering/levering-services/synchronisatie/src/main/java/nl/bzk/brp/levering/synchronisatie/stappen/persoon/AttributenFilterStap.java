/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.stappen.persoon;

import javax.inject.Inject;

import nl.bzk.brp.levering.business.toegang.gegevensfilter.AttributenFilterService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieBerichtContext;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieResultaat;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonBericht;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;


/**
 * Stap die er voor zorgt dat de magGeleverdWorden vlaggen gezet worden op de attributen. De bindings zorgen er uit-
 * eindelijk voor dat alleen de elementen
 * die deze vlag op true hebben staan in de uitgaande xml terecht komen.
 */
public class AttributenFilterStap
        extends
        AbstractBerichtVerwerkingStap<GeefSynchronisatiePersoonBericht, SynchronisatieBerichtContext, SynchronisatieResultaat>
{

    private static final Logger     LOGGER = LoggerFactory.getLogger();

    @Inject
    private AttributenFilterService attributenFilterService;

    @Override
    public final boolean voerStapUit(final GeefSynchronisatiePersoonBericht onderwerp,
            final SynchronisatieBerichtContext context, final SynchronisatieResultaat resultaat)
    {
        final VolledigBericht volledigBericht = context.getVolledigBericht();
        final PersoonHisVolledigView persoonView =
            volledigBericht.getAdministratieveHandeling().getBijgehoudenPersonen().get(0);

        try {
            final Rol rol = context.getLeveringinformatie().getToegangLeveringsautorisatie()
                .getGeautoriseerde().getRol();
            attributenFilterService.zetMagGeleverdWordenVlaggen(persoonView, context.getLeveringinformatie().getDienst(),
                rol);
        } catch (final ExpressieExceptie expressieExceptie) {
            LOGGER.error(FunctioneleMelding.LEVERING_UITVOER_ATTRIBUTEN_FILTER_MISLUKT,
                    "Er is een fout opgetreden tijdens de uitvoer van het attributenfilter. ", expressieExceptie);
            return STOPPEN;
        }
        LOGGER.debug("AttributenFilterStap uitgevoerd, de mag-geleverd-worden-vlaggen zijn gezet.");

        return DOORGAAN;
    }
}
