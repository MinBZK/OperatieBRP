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
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.stappen.populatie.AbstractAfnemerVerwerkingStap;
import nl.bzk.brp.levering.business.toegang.gegevensfilter.OnderzoekAutorisatieService;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import org.perf4j.aop.Profiled;

/**
 * Deze stap zorgt voor filtering in onderzoeken die naar persoonsgegevens wijzen waarvoor de afnemer niet geautoriseerd is.
 *
 * @brp.bedrijfsregel R1561
 * @brp.bedrijfsregel R1562
 */
@Regels({ Regel.R1561, Regel.R1562 })
public class AutoriseerOnderzoekenStap extends AbstractAfnemerVerwerkingStap {

    @Inject
    private OnderzoekAutorisatieService onderzoekAutorisatieService;

    @Override
    @Profiled(tag = "OngeautoriseerdeOnderzoekenFilterStap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(final LeveringautorisatieStappenOnderwerp onderwerp, final LeveringsautorisatieVerwerkingContext context,
                                     final LeveringautorisatieVerwerkingResultaat resultaat)
    {
        final List<Attribuut> geraakteAttributen = autoriseerOnderzoekenVanPersonen(context);

        final List<Attribuut> attributenDieGeleverdMogenWorden = context.getAttributenDieGeleverdMogenWorden();
        attributenDieGeleverdMogenWorden.addAll(geraakteAttributen);
        context.setAttributenDieGeleverdMogenWorden(attributenDieGeleverdMogenWorden);

        return DOORGAAN;
    }

    /**
     * Filter ongeautoriseerde onderzoeken, dit zijn onderzoeken die naar gegevens wijzen die vanwege autorisatie filters niet in het bericht staan.
     *
     * @param context de context
     */
    private List<Attribuut> autoriseerOnderzoekenVanPersonen(final LeveringsautorisatieVerwerkingContext context) {
        final List<Attribuut> geraakteAttributen = new ArrayList<>();
        for (final SynchronisatieBericht leveringBericht : context.getLeveringBerichten()) {
            final List<PersoonHisVolledigView> bijgehoudenPersonen = leveringBericht.getAdministratieveHandeling().getBijgehoudenPersonen();

            for (final PersoonHisVolledigView persoonHisVolledigView : bijgehoudenPersonen) {
                final List<Attribuut> geautoriseerdeAttributen = autoriseerOnderzoeken(persoonHisVolledigView, context);
                geraakteAttributen.addAll(geautoriseerdeAttributen);
            }
        }
        return geraakteAttributen;
    }

    /**
     * Autoriseer de onderzoeken op basis van de autorisatie van het gegeven dat in onderzoek staat.
     *  @param persoonHisVolledigView persoon his volledig view
     * @param context context
     */
    private List<Attribuut> autoriseerOnderzoeken(final PersoonHisVolledigView persoonHisVolledigView, final LeveringsautorisatieVerwerkingContext context) {
        final Map<Integer, Map<Integer, List<Attribuut>>> persoonOnderzoekenMap = context.getPersoonOnderzoekenMap();

        return onderzoekAutorisatieService.autoriseerOnderzoeken(persoonHisVolledigView, persoonOnderzoekenMap);
    }
}
