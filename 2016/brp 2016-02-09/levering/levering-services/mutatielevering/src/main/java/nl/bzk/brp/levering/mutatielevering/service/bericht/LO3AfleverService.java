/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.service.bericht;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerpImpl;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.levering.mutatielevering.Lo3LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.mutatielevering.Lo3LeveringsautorisatieVerwerkingContextImpl;
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


/**
 * AflerverSerivce voor LO3 leveringen.
 */
@Service
public class LO3AfleverService extends AbstractAfleverService {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    @Named(value = "lo3AfnemerStappenVerwerker")
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
        final ConversieCache conversieCache = new ConversieCache();

        LOGGER.debug("Start LO3 verwerking voor administratieve handeling {}", administratieveHandeling.getID());
        for (final Map.Entry<Leveringinformatie, Map<Integer, Populatie>> mapEntry : leveringinformatiePopulatieMap.entrySet()) {
            final Leveringinformatie leveringAutorisatie = mapEntry.getKey();
            final Map<Integer, Populatie> populatieMap = mapEntry.getValue();

            final ToegangLeveringsautorisatie toegangLeveringsautorisatie = leveringAutorisatie.getToegangLeveringsautorisatie();

            LOGGER.debug("Controleer LO3 verwerking voor leveringsautorisatie {} van partij {}: ",
                toegangLeveringsautorisatie.getLeveringsautorisatie().getNaam().getWaarde(),
                toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode().getWaarde());
            final Lo3LeveringsautorisatieVerwerkingContext leveringsautorisatieVerwerkingContext =
                maakVerwerkingContext(administratieveHandeling, bijgehoudenPersonen, populatieMap, conversieCache);

            updateMDC(toegangLeveringsautorisatie);

            final Stelsel stelsel = toegangLeveringsautorisatie.getLeveringsautorisatie().getStelsel();
            LOGGER.debug("Leveringsautorisatie {} van partij {} heeft afleverwijze: ",
                toegangLeveringsautorisatie.getLeveringsautorisatie().getNaam().getWaarde(), toegangLeveringsautorisatie.getGeautoriseerde().getPartij()
                    .getCode().getWaarde(), stelsel);

            if (stelsel == Stelsel.GBA) {

                MDC.put(MDCVeld.MDC_KANAAL, String.valueOf(stelsel));

                LOGGER.debug("Start LO3 stappenverwerking voor leveringsautorisatie van afnemer met code {} voor stelsel {}.",
                    toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode(), stelsel);

                final Long administratieveHandelingId = administratieveHandeling.getID();
                final LeveringautorisatieStappenOnderwerp leveringautorisatieStappenOnderwerp =
                    new LeveringautorisatieStappenOnderwerpImpl(leveringAutorisatie, administratieveHandelingId, stelsel);

                final LeveringautorisatieVerwerkingResultaat verwerkingResultaat = verwerker
                    .verwerk(leveringautorisatieStappenOnderwerp, leveringsautorisatieVerwerkingContext);
                if (!verwerkingResultaat.isSuccesvol()) {
                    LOGGER.debug("Stappenverwerking voor leveringsautorisatie van afnemer met code {} voor stelsel {} "
                            + "was niet succesvol.",
                        toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode(), stelsel);
                    resultaat.voegMeldingenToe(verwerkingResultaat.getMeldingen());
                }

                MDC.remove(MDCVeld.MDC_KANAAL);
            }
        }
        cleanMDC();


        return resultaat;
    }

    /**
     * Maakt een context specifiek voor deze service.
     *
     * @param administratieveHandeling de administratieveHandeling
     * @param personen                 de geraakt bijgehoudenPersonen
     * @param populatieMap             map met key de persoonId en als value de positie die de persoon heeft voor de populatie van het
     *                                 leveringsautorisatie
     * @param conversieCache           een cache voor geconverteerde bijgehoudenPersonen
     * @return een nieuwe context
     */
    private Lo3LeveringsautorisatieVerwerkingContext maakVerwerkingContext(
        final AdministratieveHandelingModel administratieveHandeling,
        final List<PersoonHisVolledig> personen,
        final Map<Integer, Populatie> populatieMap, final ConversieCache conversieCache)
    {
        return new Lo3LeveringsautorisatieVerwerkingContextImpl(administratieveHandeling, personen, populatieMap, conversieCache);
    }
}
