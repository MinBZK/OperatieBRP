/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.service.bericht;

import nl.bzk.brp.levering.business.stappen.populatie.*;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.levering.mutatielevering.stappen.AfnemerStappenVerwerker;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.logging.MDC;
import nl.bzk.brp.logging.MDCVeld;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

/**
 * AfleverService voor BRP leveringen.
 */
@Service
public class BrpAfleverService extends AbstractAfleverService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    @Named(value = "afnemerStappenVerwerker")
    private AfnemerStappenVerwerker verwerker;

    @Override
    public final LeveringautorisatieVerwerkingResultaat leverBerichten(
        final AdministratieveHandelingModel administratieveHandeling,
        final List<PersoonHisVolledig> bijgehoudenPersonen,
        final Map<Leveringinformatie, Map<Integer, Populatie>> leveringinformatiePopulatieMap,
        final Map<Integer, Map<String, List<Attribuut>>> persoonAttributenMap,
        final Map<Integer, Map<Integer, List<Attribuut>>> persoonOnderzoekenMap)
    {
        final LeveringautorisatieVerwerkingResultaat resultaat = new LeveringautorisatieVerwerkingResultaat();

        for (final Map.Entry<Leveringinformatie, Map<Integer, Populatie>> mapEntry : leveringinformatiePopulatieMap.entrySet()) {
            final Leveringinformatie leveringAutorisatie = mapEntry.getKey();
            final Map<Integer, Populatie> populatieMap = mapEntry.getValue();

            final LeveringsautorisatieVerwerkingContext leveringsautorisatieVerwerkingContext =
                new LeveringsautorisatieVerwerkingContextImpl(administratieveHandeling, bijgehoudenPersonen, populatieMap, persoonAttributenMap,
                    persoonOnderzoekenMap);
            final ToegangLeveringsautorisatie toegangLeveringsautorisatie = leveringAutorisatie.getToegangLeveringsautorisatie();

            updateMDC(toegangLeveringsautorisatie);

            final Stelsel stelsel = toegangLeveringsautorisatie.getLeveringsautorisatie().getStelsel();
            if (stelsel == Stelsel.BRP) {
                MDC.put(MDCVeld.MDC_KANAAL, stelsel.getNaam());

                LOGGER.debug("Start stappenverwerking voor leveringsautorisatie van afnemer met code {} voor stelsel {}.",
                    toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode(), stelsel);

                final LeveringautorisatieStappenOnderwerp leveringautorisatieStappenOnderwerp =
                    new LeveringautorisatieStappenOnderwerpImpl(leveringAutorisatie, administratieveHandeling.getID(), stelsel);

                final LeveringautorisatieVerwerkingResultaat verwerkingResultaat = verwerker.verwerk(leveringautorisatieStappenOnderwerp,
                    leveringsautorisatieVerwerkingContext);
                if (!verwerkingResultaat.isSuccesvol()) {
                    LOGGER.debug("Stappenverwerking voor leveringsautorisatie van afnemer met code {} voor stelsel {} was niet succesvol.",
                        toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode(), stelsel);
                    resultaat.voegMeldingenToe(verwerkingResultaat.getMeldingen());
                }

                MDC.remove(MDCVeld.MDC_KANAAL);
            }
        }
        cleanMDC();


        return resultaat;
    }

}
