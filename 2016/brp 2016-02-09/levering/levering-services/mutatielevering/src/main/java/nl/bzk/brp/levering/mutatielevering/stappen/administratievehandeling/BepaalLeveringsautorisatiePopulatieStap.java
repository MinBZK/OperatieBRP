/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.administratievehandeling;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.business.toegang.populatie.FilterNietTeLeverenPersonenService;
import nl.bzk.brp.levering.business.toegang.populatie.LeveringinformatieService;
import nl.bzk.brp.levering.business.toegang.populatie.PersoonPopulatieBepalingService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.levering.mutatielevering.stappen.AbstractAdministratieveHandelingVerwerkingStap;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.logging.MDC;
import nl.bzk.brp.logging.MDCVeld;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.perf4j.aop.Profiled;


/**
 * Deze stap bepaalt de leveringsautorisatie populatie voor de te controleren leveringsautorisaties.
 */
public class BepaalLeveringsautorisatiePopulatieStap extends AbstractAdministratieveHandelingVerwerkingStap {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final SoortDienst[] LEVEREN_MUTATIES_DIENST_SOORTEN = new SoortDienst[]{
        SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING,
        SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE,
        SoortDienst.ATTENDERING, };

    @Inject
    private LeveringinformatieService leveringinformatieService;

    @Inject
    private PersoonPopulatieBepalingService persoonPopulatieBepalingService;

    @Inject
    private FilterNietTeLeverenPersonenService filterNietTeLeverenPersonenService;

    @Override
    @Profiled(tag = "BepaalLeveringsautorisatiePopulatieStap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(final AdministratieveHandelingMutatie onderwerp,
        final AdministratieveHandelingVerwerkingContext context,
        final AdministratieveHandelingVerwerkingResultaat resultaat)
    {
        try {
            final Map<Leveringinformatie, Map<Integer, Populatie>> leveringPopulatieMap = new HashMap<>();
            final List<Leveringinformatie> leveringAutorisaties =
                leveringinformatieService.geefGeldigeLeveringinformaties(LEVEREN_MUTATIES_DIENST_SOORTEN);

            for (final Leveringinformatie leveringAutorisatie : leveringAutorisaties) {
                MDC.put(MDCVeld.MDC_LEVERINGAUTORISATIEID,
                    leveringAutorisatie.getToegangLeveringsautorisatie().getLeveringsautorisatie().getID().toString());
                MDC.put(MDCVeld.MDC_PARTIJ_CODE,
                    String.valueOf(leveringAutorisatie.getToegangLeveringsautorisatie().getGeautoriseerde().getPartij()
                        .getCode()));

                bepaalLeveringsautorisatiePopulatieMapVoorLeveringinformatie(leveringAutorisatie, leveringPopulatieMap,
                    context);

                MDC.remove(MDCVeld.MDC_LEVERINGAUTORISATIEID);
                MDC.remove(MDCVeld.MDC_PARTIJ_CODE);
            }

            context.setLeveringPopulatieMap(leveringPopulatieMap);
            return DOORGAAN;
        } finally {
            MDC.remove(MDCVeld.MDC_LEVERINGAUTORISATIEID);
            MDC.remove(MDCVeld.MDC_PARTIJ_CODE);
        }
    }

    /**
     * Voer de GeefPersoonPopulatieCorrelatieRunnable klasse uit voor een leveringsautorisatie cache element. Deze bepaalt de leveringsautorisatie
     * populatie voor het leveringsautorisatie cache element.
     *
     * @param leveringAutorisatie  leveringsautorisatie cache element
     * @param leveringPopulatieMap leveringsautorisatie populatie map
     * @param context              context
     */
    private void bepaalLeveringsautorisatiePopulatieMapVoorLeveringinformatie(final Leveringinformatie leveringAutorisatie,
        final Map<Leveringinformatie, Map<Integer, Populatie>> leveringPopulatieMap,
        final AdministratieveHandelingVerwerkingContext context)
    {
        final AdministratieveHandelingModel huidigeAdministratieHandeling =
            context.getHuidigeAdministratieveHandeling();
        final List<PersoonHisVolledig> bijgehoudenPersonenVolledig = context.getBijgehoudenPersonenVolledig();
        if (leveringAutorisatie != null && huidigeAdministratieHandeling != null && bijgehoudenPersonenVolledig != null) {
            final Map<Integer, Populatie> teLeverenPersonen = new HashMap<>();
            try {
                teLeverenPersonen.putAll(persoonPopulatieBepalingService.geefPersoonPopulatieCorrelatie(
                    huidigeAdministratieHandeling, bijgehoudenPersonenVolledig, leveringAutorisatie));

                filterNietTeLeverenPersonenService.filterNietTeLeverenPersonen(bijgehoudenPersonenVolledig,
                    teLeverenPersonen, leveringAutorisatie, huidigeAdministratieHandeling);

            } catch (final ExpressieExceptie expressieExceptie) {
                LOGGER.error(FunctioneleMelding.LEVERING_AUTORISATIE_FOUT_BEPALEN_POPULATIE,
                    "Er is een fout opgetreden tijdens het bepalen van de leveringsautorisatie populatie map.",
                    expressieExceptie);
                // Voor zekerheid worden de mogelijk te leveren personen verwijderd. Deze zijn mogelijk nog niet goed
                // gefilterd.
                teLeverenPersonen.clear();
            }

            if (!teLeverenPersonen.isEmpty()) {
                leveringPopulatieMap.put(leveringAutorisatie, teLeverenPersonen);
            }

        } else {
            LOGGER.error("Fout bij uitvoeren van bepalen van persoon populatie correlatie.",
                new InstantiationException("U heeft niet alle verplichte velden meegegeven."));
        }
    }
}
