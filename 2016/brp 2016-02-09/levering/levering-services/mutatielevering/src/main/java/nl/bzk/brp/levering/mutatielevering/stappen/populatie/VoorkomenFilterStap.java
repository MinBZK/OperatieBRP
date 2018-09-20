/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.stappen.populatie.AbstractAfnemerVerwerkingStap;
import nl.bzk.brp.levering.business.toegang.voorkomenfilter.VoorkomenFilterService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import org.perf4j.aop.Profiled;


/**
 * Stap die er voor zorgt dat de groepen gefilterd worden. Dat wil zeggen: aan de hand van de authorisatie worden historische voorkomens weggelaten en
 * wordt verantwoordingsinformatie verborgen.
 */
public class VoorkomenFilterStap extends AbstractAfnemerVerwerkingStap {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private VoorkomenFilterService voorkomenFilterService;

    @Override
    @Profiled(tag = "VoorkomenFilterStap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(final LeveringautorisatieStappenOnderwerp onderwerp,
        final LeveringsautorisatieVerwerkingContext context, final LeveringautorisatieVerwerkingResultaat resultaat)
    {
        final Leveringsautorisatie leveringsautorisatie = onderwerp.getLeveringinformatie().getToegangLeveringsautorisatie().getLeveringsautorisatie();

        LOGGER.info("Groepenfilter uitvoeren voor leveringsautorisatie met id {}.", leveringsautorisatie.getID());
        try

        {
            final Map<Integer, Map<String, List<Attribuut>>> persoonAttributenMap = context.getPersoonAttributenMap();
            for (final SynchronisatieBericht leveringBericht : context.getLeveringBerichten()) {
                if (SoortSynchronisatie.MUTATIEBERICHT.equals(leveringBericht.geefSoortSynchronisatie())) {
                    for (final PersoonHisVolledigView bijgehoudenPersoon : leveringBericht.getAdministratieveHandeling().getBijgehoudenPersonen()) {
                        final Map<String, List<Attribuut>> attrCache = persoonAttributenMap.get(bijgehoudenPersoon.getID());
                        voorkomenFilterService.voerVoorkomenFilterUitVoorMutatieLevering(bijgehoudenPersoon, onderwerp.getLeveringinformatie().getDienst(), attrCache);
                    }
                } else if (SoortSynchronisatie.VOLLEDIGBERICHT.equals(leveringBericht.geefSoortSynchronisatie())) {
                    for (final PersoonHisVolledigView bijgehoudenPersoon : leveringBericht.getAdministratieveHandeling().getBijgehoudenPersonen()) {
                        final Map<String, List<Attribuut>> attrCache = persoonAttributenMap.get(bijgehoudenPersoon.getID());
                        voorkomenFilterService.voerVoorkomenFilterUit(bijgehoudenPersoon, onderwerp.getLeveringinformatie().getDienst(), attrCache);
                    }
                }
            }
        } catch (final ExpressieExceptie expressieExceptie) {
            LOGGER.error(FunctioneleMelding.LEVERING_UITVOER_VOORKOMEN_FILTER_MISLUKT,
                "Er is een fout opgetreden tijdens de uitvoer van het voorkomen filter.", expressieExceptie);
            return STOPPEN;
        }

        return DOORGAAN;
    }


}
