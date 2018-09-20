/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.levering.business.bericht.BerichtService;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.stappen.populatie.AbstractAfnemerVerwerkingStap;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import org.perf4j.aop.Profiled;


/**
 * Stap die lege personen, dwz personen die niet geleverd mogen of kunnen worden, uit de berichten haalt. Indien een leeg bericht overblijft wordt deze ook
 * verwijderd.
 * <p/>
 *
 * @brp.bedrijfsregel VR00089
 * @brp.bedrijfsregel VR00091
 * @brp.bedrijfsregel R1989
 * @brp.bedrijfsregel R1990
 */
@Regels({ Regel.VR00089, Regel.VR00091, Regel.R1989, Regel.R1990 })
public class FilterLegePersonenStap extends AbstractAfnemerVerwerkingStap {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private BerichtService berichtService;

    @Override
    @Profiled(tag = "FilterLegePersonenStap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(final LeveringautorisatieStappenOnderwerp onderwerp,
        final LeveringsautorisatieVerwerkingContext context, final LeveringautorisatieVerwerkingResultaat resultaat)
    {
        final Iterator<SynchronisatieBericht> berichtIterator = context.getLeveringBerichten().iterator();

        while (berichtIterator.hasNext()) {
            final SynchronisatieBericht leveringBericht = berichtIterator.next();
            if (SoortSynchronisatie.MUTATIEBERICHT.equals(leveringBericht.geefSoortSynchronisatie())) {
                berichtService.filterLegePersonen(leveringBericht);

                if (leveringBericht.getAdministratieveHandeling().getBijgehoudenPersonen().isEmpty()) {
                    berichtIterator.remove();
                    LOGGER.info("Geen personen over in het bericht na filtering. Bericht wordt niet verstuurd.");
                }
            }
        }

        logTotaalLegeBerichten(onderwerp, context);

        final boolean procesMoetDoorgaan;
        if (context.getLeveringBerichten().isEmpty()) {
            procesMoetDoorgaan = STOPPEN;
        } else {
            procesMoetDoorgaan = DOORGAAN;
        }

        return procesMoetDoorgaan;
    }

    /**
     * Controleert op totaal lege berichten en logt wanneer dit het geval is.
     *
     * @param onderwerp LeveringautorisatieStappenOnderwerp
     * @param context   de context
     */
    private void logTotaalLegeBerichten(final LeveringautorisatieStappenOnderwerp onderwerp,
        final LeveringsautorisatieVerwerkingContext context)
    {
        for (final SynchronisatieBericht leveringBericht : context.getLeveringBerichten()) {
            final AdministratieveHandelingSynchronisatie administratieveHandeling =
                leveringBericht.getAdministratieveHandeling();
            final List<PersoonHisVolledigView> personen = administratieveHandeling.getBijgehoudenPersonen();
            for (final PersoonHisVolledigView persoonHisVolledigView : personen) {
                if (persoonHisVolledigView.getTotaleLijstVanHisElementenOpPersoonsLijst().isEmpty()) {
                    LOGGER.info(
                        FunctioneleMelding.LEVERING_UITVOER_FILTER_LEGE_PERSONEN,
                        "Leverginsautorisatie met id: '{}' is fout geconfigureerd: het {} bevat geen gegevens voor persoon met id: {}.",
                        onderwerp.getLeveringinformatie().getToegangLeveringsautorisatie().getLeveringsautorisatie().getID(),
                        leveringBericht.geefSoortSynchronisatie().getNaam(),
                        persoonHisVolledigView.getID());
                }
            }
        }
    }
}
