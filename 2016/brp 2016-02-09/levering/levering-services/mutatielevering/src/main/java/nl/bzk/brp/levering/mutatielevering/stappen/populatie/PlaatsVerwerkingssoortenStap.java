/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.levering.business.bericht.BerichtService;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.stappen.populatie.AbstractAfnemerVerwerkingStap;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;

/**
 * Stap verantwoordelijk voor het plaatsen van verwerkingssoort op instanties van {@link nl.bzk.brp.model.levering.MutatieBericht}.
 */
public class PlaatsVerwerkingssoortenStap extends AbstractAfnemerVerwerkingStap {

    @Inject
    private BerichtService berichtService;

    @Override
    public final boolean voerStapUit(final LeveringautorisatieStappenOnderwerp onderwerp, final LeveringsautorisatieVerwerkingContext context,
                                     final LeveringautorisatieVerwerkingResultaat resultaat)
    {
        final List<PersoonHisVolledigView> bijgehoudenPersoonViews = context.getBijgehoudenPersoonViews();
        berichtService.verwijderVerwerkingssoortenUitPersonen(bijgehoudenPersoonViews);

        berichtService.voegVerwerkingssoortenToe(bijgehoudenPersoonViews, onderwerp.getAdministratieveHandelingId());

        return DOORGAAN;
    }
}
