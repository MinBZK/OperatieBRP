/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.brp.levering.business.bericht.BerichtFactory;
import nl.bzk.brp.levering.business.bericht.BerichtService;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.stappen.populatie.AbstractAfnemerVerwerkingStap;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.levering.mutatielevering.excepties.StapPreValidatieExceptie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.validatie.Melding;
import org.perf4j.aop.Profiled;

/**
 * Deze stap maakt het max kennisgevingbericht. Dit bericht bevat de maximale set aan gegevens die naar de afnemers verstuurd kunnen worden.
 */
public class MaakMaxKennisgevingBerichtStap extends AbstractAfnemerVerwerkingStap {

    /**
     * De Constante LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private BerichtFactory berichtFactory;

    @Inject
    private BerichtService berichtService;

    @Override
    @Profiled(tag = "MaakMaxKennisgevingBerichtStap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(final LeveringautorisatieStappenOnderwerp leveringautorisatieStappenOnderwerp,
        final LeveringsautorisatieVerwerkingContext context, final LeveringautorisatieVerwerkingResultaat resultaat)
    {
        prevalidatieVoerStapUit(context);
        final List<SynchronisatieBericht> leveringBerichten = maakLeveringBerichten(leveringautorisatieStappenOnderwerp, context);

        verwijderVerwerkingssoortUitVolledigBerichten(leveringBerichten);

        final boolean resultaatStap;

        if (leveringBerichten.isEmpty()) {
            final String foutmelding = "Het maken van het max kennisgevingbericht is mislukt.";
            LOGGER.error(foutmelding);
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001,
                "Het maken van de max leveringberichten is mislukt, er zijn geen leveringberichten gemaakt."));
            resultaatStap = STOPPEN;
        } else {
            final List<PersoonHisVolledigView> views = new ArrayList<>();
            for (final SynchronisatieBericht bericht : leveringBerichten) {
                views.addAll(bericht.getAdministratieveHandeling().getBijgehoudenPersonen());
            }
            context.setBijgehoudenPersoonViews(views);

            context.setLeveringBerichten(leveringBerichten);
            resultaatStap = DOORGAAN;
        }

        return resultaatStap;
    }

    /**
     * Verwijder verwerkingssoort uit volledigberichten.
     *
     * @param leveringBerichten De levering berichten
     */
    private void verwijderVerwerkingssoortUitVolledigBerichten(final List<SynchronisatieBericht> leveringBerichten) {
        for (final SynchronisatieBericht synchronisatieBericht : leveringBerichten) {
            if (synchronisatieBericht instanceof VolledigBericht) {
                berichtService.verwijderVerwerkingssoortenUitPersonen(synchronisatieBericht.getAdministratieveHandeling().getBijgehoudenPersonen());
            }
        }
    }

    /**
     * Maakt de leveringberichten voor een administratieve handeling.
     *
     * @param onderwerp Het stappenonderwerp.
     * @param context   De stappencontext.
     * @return De lijst met AbstractLeveringBericht objecten.
     */
    private List<SynchronisatieBericht> maakLeveringBerichten(final LeveringautorisatieStappenOnderwerp onderwerp,
        final LeveringsautorisatieVerwerkingContext context)
    {
        final Map<Integer, Populatie> populatieMap = context.getTeLeverenPersoonIds();
        final List<PersoonHisVolledigView> personen = maakLijstVanPersonenMetPopulatie(context, populatieMap);

        return berichtFactory.maakBerichten(personen, onderwerp.getLeveringinformatie(), populatieMap, context.getAdministratieveHandeling());
    }

    /**
     * Maakt een lijst van personen die een populatie hebben gekregen in een voorgaande stap.
     *
     * @param context      De context.
     * @param populatieMap De populatiemap die gemaakt is in een voorgaande stap.
     * @return De lijst van personen met populatie.
     */
    private List<PersoonHisVolledigView> maakLijstVanPersonenMetPopulatie(final LeveringsautorisatieVerwerkingContext context,
        final Map<Integer, Populatie> populatieMap)
    {
        final List<PersoonHisVolledigView> persoonHisVolledigViews = context.getBijgehoudenPersoonViews();
        final List<PersoonHisVolledigView> persoonHisVolledigViewsMetPopulatie = new ArrayList<>(populatieMap.size());
        for (final PersoonHisVolledigView persoonHisVolledigView : persoonHisVolledigViews) {
            if (populatieMap.get(persoonHisVolledigView.getID()) != null) {
                persoonHisVolledigViewsMetPopulatie.add(persoonHisVolledigView);
            }
        }
        return persoonHisVolledigViewsMetPopulatie;
    }

    /**
     * Valideert de stap voordat deze uitgevoerd wordt.
     *
     * @param context De context voor de stappen.
     */
    private void prevalidatieVoerStapUit(final LeveringsautorisatieVerwerkingContext context) {
        if (context.getBijgehoudenPersonenVolledig() == null) {
            throw new StapPreValidatieExceptie("Er zijn geen bijgehouden personen beschikbaar op de context.");
        }
        if (context.getAdministratieveHandeling() == null) {
            throw new StapPreValidatieExceptie("Er is geen administratieve handeling beschikbaar op de context.");
        }
    }
}
