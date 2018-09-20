/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging;

import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.levering.business.toegang.gegevensfilter.VerantwoordingsinformatieFilter;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;


/**
 * Stap die de consistentie in de verantwoordingsinformatie onderaan de persoonhisvolledigview filtert, zodat deze alleen de relevante
 * verantwoordingsinformatie toont.
 */
public class FilterVerantwoordingsinformatieStap extends
    AbstractBerichtVerwerkingStap<BevragingsBericht, BevragingBerichtContextBasis, BevragingResultaat>
{

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private VerantwoordingsinformatieFilter verantwoordingsinformatieFilter;

    @Override
    public final boolean voerStapUit(final BevragingsBericht onderwerp, final BevragingBerichtContextBasis context,
        final BevragingResultaat resultaat)
    {
        final Set<PersoonHisVolledigView> persoonHisVolledigViews = resultaat.getGevondenPersonen();

        try {
            filterPersoonHisVolledigViews(persoonHisVolledigViews, context.getLeveringinformatie());
        } catch (final Exception exceptie) {
            final String foutmelding = "Het filteren van de verantwoordingsinformatie is mislukt.";
            LOGGER.error(FunctioneleMelding.ALGEMEEN_FOUT, foutmelding, exceptie);
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001, foutmelding));
            return STOPPEN;
        }

        return DOORGAAN;
    }

    /**
     * Filtert een lijst met persoon his volledig views. +
     *
     * @param persoonHisVolledigViews de persoon his volledig views
     * @param leveringautorisatie     de leveringautorisatie
     */
    private void filterPersoonHisVolledigViews(final Set<PersoonHisVolledigView> persoonHisVolledigViews, final Leveringinformatie leveringautorisatie) {
        for (final PersoonHisVolledigView persoonHisVolledigView : persoonHisVolledigViews) {
            LOGGER.info(FunctioneleMelding.BEVRAGING_FILTEREN_VERANTWOORDING_PERSOON,
                "Filteren van verantwoordingsinformatie voor persoon " + persoonHisVolledigView.getID());
            verantwoordingsinformatieFilter.filter(persoonHisVolledigView, leveringautorisatie);
        }
    }
}
