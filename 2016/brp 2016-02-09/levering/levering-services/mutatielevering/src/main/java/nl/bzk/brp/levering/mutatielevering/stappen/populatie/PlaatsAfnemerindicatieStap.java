/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.inject.Inject;
import nl.bzk.brp.levering.afnemerindicaties.model.AfnemerindicatieReedsAanwezigExceptie;
import nl.bzk.brp.levering.afnemerindicaties.service.AfnemerindicatiesZonderRegelsService;
import nl.bzk.brp.levering.business.stappen.populatie.AbstractAfnemerVerwerkingStap;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.logging.MDC;
import nl.bzk.brp.logging.MDCVeld;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.autaut.PersoonAfnemerindicatieView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import org.perf4j.aop.Profiled;


/**
 * Deze klasse plaatst een afnemerindicatie voor de afnemer op de bijgehouden persoon.
 *
 * @brp.bedrijfsregel VR00063
 * @brp.bedrijfsregel R1335
 * @brp.bedrijfsregel R1408
 */
@Regels({ Regel.VR00063, Regel.R1335, Regel.R1408 })
public class PlaatsAfnemerindicatieStap extends AbstractAfnemerVerwerkingStap {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private AfnemerindicatiesZonderRegelsService afnemerindicatiesZonderRegelsService;


    @Override
    @Profiled(tag = "PlaatsAfnemerindicatieStap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(final LeveringautorisatieStappenOnderwerp onderwerp, final LeveringsautorisatieVerwerkingContext
        context,
        final LeveringautorisatieVerwerkingResultaat resultaat)
    {
        if (moetenAfnemerIndicatiesGeplaatstWorden(onderwerp)) {
            final List<AfnemerindicatieOnderhoudOpdracht> opdrachten =
                maakAfnemerindicatieOnderhoudOpdrachten(onderwerp, context);
            for (final AfnemerindicatieOnderhoudOpdracht opdracht : opdrachten) {
                verwerkAfnemerindicatieOnderhoudOpdracht(opdracht);
                LOGGER.debug("Afnemerindicatie obv attendering wordt geplaatst voor de persoon met id: {}, partij"
                        + " met code: {} en leveringsautorisatie met naam: {}.",
                    opdracht.persoon,
                    opdracht.toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode(),
                    opdracht.toegangLeveringsautorisatie.getLeveringsautorisatie().getID());
            }
        }

        return DOORGAAN;
    }

    /**
     * Controleert of in deze stap de afnemerindicaties geplaatst dienen te worden.
     *
     * @param onderwerp Het onderwerp.
     * @return True als er afnemerindicatie geplaatst dienen te worden, anders false.
     */
    @Regels(Regel.R1335)
    private boolean moetenAfnemerIndicatiesGeplaatstWorden(final LeveringautorisatieStappenOnderwerp onderwerp) {
        final SoortDienst soortDienst =
            onderwerp.getLeveringinformatie().getDienst().getSoort();

        return soortDienst == SoortDienst.ATTENDERING
            && onderwerp.getLeveringinformatie().getDienst().getEffectAfnemerindicaties() == EffectAfnemerindicaties.PLAATSING;
    }


    /**
     * Maakt een afnemerindicatie onderhoud opdracht, dat het bericht vormt dat naar de queue gaat.
     *
     * @param onderwerp Het onderwerp.
     * @param context   De context.
     * @return De afnemerindicatie onderhoud opdracht.
     */
    private List<AfnemerindicatieOnderhoudOpdracht> maakAfnemerindicatieOnderhoudOpdrachten(
        final LeveringautorisatieStappenOnderwerp onderwerp, final LeveringsautorisatieVerwerkingContext context)
    {
        final List<AfnemerindicatieOnderhoudOpdracht> afnemerindicatieOnderhoudOpdrachten = new ArrayList<>();
        final Leveringinformatie leveringinformatie = onderwerp.getLeveringinformatie();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = leveringinformatie.getToegangLeveringsautorisatie();
        final int leveringsautorisatieId = toegangLeveringsautorisatie.getLeveringsautorisatie().getID();
        final PartijCodeAttribuut partijCode = toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode();
        final ReferentienummerAttribuut referentienummer = new ReferentienummerAttribuut(UUID.randomUUID().toString());
        final Set<PersoonHisVolledig> persoonHisVolledigSet = bepaalPersonenVoorAfnemerindicaties(context.getLeveringBerichten(), partijCode,
            leveringsautorisatieId,
            context.getBijgehoudenPersonenVolledig());
        for (final PersoonHisVolledig persoon : persoonHisVolledigSet) {
            final AfnemerindicatieOnderhoudOpdracht opdracht =
                new AfnemerindicatieOnderhoudOpdracht();
            opdracht.persoon = (PersoonHisVolledigImpl) persoon;
            opdracht.toegangLeveringsautorisatie = toegangLeveringsautorisatie;
            opdracht.effectAfnemerindicatie = leveringinformatie.getDienst().getEffectAfnemerindicaties();
            opdracht.referentienummer = referentienummer;
            opdracht.dienstId = leveringinformatie.getDienst().getID();
            opdracht.tijdstipRegistratie = context.getAdministratieveHandeling().getTijdstipRegistratie();
            afnemerindicatieOnderhoudOpdrachten.add(opdracht);
        }

        return afnemerindicatieOnderhoudOpdrachten;
    }


    /**
     * Bepaalt de personen waarvoor afnemerindicaties geplaatst dienen te worden.
     *
     * @param synchronisatieBerichten De synchronisatieberichten.
     * @param partijCode              De partijcode.
     * @param leveringsautorisatieId          Het id van de leveringsautorisatie.
     * @param persoonHisVolledigs     De persoon his volledig views van de context.
     * @return Lijst met persoonIds waarvoor afnemerindicaties geplaatst dienen te worden.
     */
    private Set<PersoonHisVolledig> bepaalPersonenVoorAfnemerindicaties(final List<SynchronisatieBericht>
        synchronisatieBerichten,
        final PartijCodeAttribuut partijCode,
        final int leveringsautorisatieId,
        final List<PersoonHisVolledig>
            persoonHisVolledigs)
    {
        final Set<PersoonHisVolledig> personen = new HashSet<>();
        final Set<Integer> toegevoegdePersonen = new HashSet<>();
        final Map<Integer, PersoonHisVolledig> persoonHisVolledigMap = new HashMap<>();
        for (final PersoonHisVolledig persoonHisVolledig : persoonHisVolledigs) {
            persoonHisVolledigMap.put(persoonHisVolledig.getID(), persoonHisVolledig);
        }
        for (final SynchronisatieBericht synchronisatieBericht : synchronisatieBerichten) {
            if (SoortSynchronisatie.VOLLEDIGBERICHT.equals(synchronisatieBericht.geefSoortSynchronisatie())) {
                final List<PersoonHisVolledigView> bijgehoudenPersonen = synchronisatieBericht
                    .getAdministratieveHandeling().getBijgehoudenPersonen();

                for (final PersoonHisVolledigView persoonHisVolledigView : bijgehoudenPersonen) {
                    //is deze check echt nodig. Waarom geen equals in persoonHisVolledig, of is die er wel? Nakijken.
                    if (!toegevoegdePersonen.contains(persoonHisVolledigView.getID())) {
                        if (afnemerindicatieBestaatNogNiet(partijCode, leveringsautorisatieId, persoonHisVolledigView,
                            persoonHisVolledigs))
                        {
                            toegevoegdePersonen.add(persoonHisVolledigView.getID());
                            personen.add(persoonHisVolledigMap.get(persoonHisVolledigView.getID()));
                        }
                    }
                }
            }
        }

        return personen;
    }


    /**
     * Controleert voor een persoon of er (op dit moment) een afnemerindicatie is voor een partij / leveringsautorisatie combinatie.
     *
     * @param partijCode             De partijcode.
     * @param leveringsautorisatieId         De id van de leveringsautorisatie.
     * @param persoonHisVolledigView De persoon.
     * @param persoonHisVolledigs    De persoon his volledig views van de context.
     * @return True als de afnemerindicatie niet bestaat, anders false.
     */
    private boolean afnemerindicatieBestaatNogNiet(final PartijCodeAttribuut partijCode,
        final int leveringsautorisatieId,
        final PersoonHisVolledigView persoonHisVolledigView,
        final List<PersoonHisVolledig> persoonHisVolledigs)
    {
        final PersoonView persoonView = maakPersoonView(persoonHisVolledigView, persoonHisVolledigs);

        if (persoonView != null) {
            for (final PersoonAfnemerindicatieView afnemerindicatie : persoonView.getAfnemerindicaties()) {
                final boolean isZelfdeLeveringsautorisatie = afnemerindicatie.getLeveringsautorisatie().getWaarde().getID().equals(
                        leveringsautorisatieId);
                final boolean isZelfdeAfnemer = afnemerindicatie.getAfnemer().getWaarde().getCode().equals(partijCode);

                if (isZelfdeLeveringsautorisatie && isZelfdeAfnemer) {
                    LOGGER.debug("Afnemerindicatie obv attendering wordt niet geplaatst, omdat deze al bestaat voor de"
                            + " persoon met id: {}, partij met code: {} en leveringsautorisatie met naam: {}.",
                        persoonHisVolledigView.getID(), partijCode.getWaarde(), leveringsautorisatieId);
                    return false;
                }
            }
        }


        return true;
    }

    /**
     * Maakt een persoonview voor de huidige tijd op basis van de persoonhisVolledigImpl in de lijst.
     *
     * @param persoonHisVolledigView De persoon his volledig waarvoor we een persoonview willen hebben.
     * @param persoonHisVolledigs    De lijst van persoon his volledigs.
     * @return De persoonView.
     */
    private PersoonView maakPersoonView(final PersoonHisVolledigView persoonHisVolledigView,
        final List<PersoonHisVolledig> persoonHisVolledigs)
    {
        for (final PersoonHisVolledig persoonHisVolledig : persoonHisVolledigs) {
            if (persoonHisVolledig.getID().equals(persoonHisVolledigView.getID())) {
                return new PersoonView(persoonHisVolledig);
            }
        }
        return null;
    }

    /**
     * Verwerk de afnemerindicatie onderhoud opdracht.
     *
     * @param opdracht De afnemerindicatie onderhoud opdracht.
     */
    private void verwerkAfnemerindicatieOnderhoudOpdracht(final AfnemerindicatieOnderhoudOpdracht opdracht)
    {
        MDC.put(MDCVeld.MDC_LEVERINGAUTORISATIEID, opdracht.toegangLeveringsautorisatie.getLeveringsautorisatie().getID().toString());
        MDC.put(MDCVeld.MDC_AANGEROEPEN_DIENST, String.valueOf(opdracht.dienstId));
        MDC.put(MDCVeld.MDC_PARTIJ_CODE, opdracht.toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode().getWaarde().toString());

        if (opdracht.referentienummer != null) {
            MDC.put(MDCVeld.MDC_REFERENTIE_NUMMER, opdracht.referentienummer.getWaarde());
        } else {
            MDC.put(MDCVeld.MDC_REFERENTIE_NUMMER, "");
        }

        if (EffectAfnemerindicaties.PLAATSING.equals(opdracht.effectAfnemerindicatie)) {
            plaatsAfnemerindicatie(opdracht);
        }
    }

    /**
     * Plaatst een afnemerindicatie.
     *
     * @param opdracht De afnemerindicatie onderhoud opdracht.
     */
    @Regels({ Regel.VR00066, Regel.R1408 })
    private void plaatsAfnemerindicatie(final AfnemerindicatieOnderhoudOpdracht opdracht) {
        try {
            final PersoonAfnemerindicatieHisVolledigImpl persoonAfnemerindicatieHisVolledig =
                afnemerindicatiesZonderRegelsService.plaatsAfnemerindicatie(
                    opdracht.toegangLeveringsautorisatie,
                    opdracht.persoon.getID(),
                    opdracht.dienstId,
                    null, null, opdracht.tijdstipRegistratie);
            if (persoonAfnemerindicatieHisVolledig != null) {
                //voeg afnemerindicatie toe. Deze worden gebruikt om te controleren of persoon al afnemerindicaties
                // heeft
                opdracht.persoon.getAfnemerindicaties().add(persoonAfnemerindicatieHisVolledig);
            }
        } catch (final AfnemerindicatieReedsAanwezigExceptie exceptie) {
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_VR00066, Regel.VR00066.getOmschrijving());
            // Deze situatie negeren we.
            LOGGER.debug("{} Indicatie bestond al voor persoon [{}]. Melding: {}", Regel.VR00066.getOmschrijving(),
                    opdracht.persoon.getID(), exceptie.getMessage());
        }

    }

    /**
     * AfnemerindicatieOnderhoudOpdracht.
     */
    private static class AfnemerindicatieOnderhoudOpdracht {
        private PersoonHisVolledigImpl    persoon;
        private ToegangLeveringsautorisatie toegangLeveringsautorisatie;
        private ReferentienummerAttribuut referentienummer;
        private EffectAfnemerindicaties   effectAfnemerindicatie;
        private Integer                   dienstId;
        private DatumTijdAttribuut tijdstipRegistratie;
    }
}
