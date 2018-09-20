/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging.levering;

import java.util.ArrayList;
import javax.inject.Inject;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.stappen.StappenContext;
import nl.bzk.brp.business.stappen.StappenResultaat;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContextBasis;
import nl.bzk.brp.levering.business.toegang.gegevensfilter.AttributenFilterService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;


/**
 * VRLV0001: De stap die het attributenfilter toepast op de gevonden personen van de bevraging.
 */
@Regels(beschrijving = "VRLV0001")
public class AttributenFilterStap extends AbstractBerichtVerwerkingStap<BevragingsBericht, BevragingBerichtContextBasis, BevragingResultaat> {

    private static final Logger     LOGGER = LoggerFactory.getLogger();

    @Inject
    private AttributenFilterService attributenFilterService;

    /**
     * Voer stap uit.
     *
     * @param onderwerp onderwerp
     * @param context context
     * @param resultaat resultaat
     * @return true als stap geslaagd is, anders false.
     * @see AbstractBerichtVerwerkingStap#voerStapUit(BrpObject, StappenContext, StappenResultaat)
     * @brp.bedrijfsregel R2002
     */
    @Regels(Regel.R2002)
    @Override
    public final boolean voerStapUit(final BevragingsBericht onderwerp, final BevragingBerichtContextBasis context, final BevragingResultaat resultaat) {
        try {
            final Rol rol = context.getLeveringinformatie().getToegangLeveringsautorisatie().getGeautoriseerde().getRol();
            attributenFilterService.zetMagGeleverdWordenVlaggen(new ArrayList<>(resultaat.getGevondenPersonen()), context.getLeveringinformatie().getDienst(),
                rol);
        } catch (final ExpressieExceptie expressieExceptie) {
            LOGGER.error(FunctioneleMelding.BEVRAGING_FILTEREN_ATTRIBUTEN, "Er is een fout opgetreden tijdens de uitvoer van het attributenfilter. ",
                         expressieExceptie);
            return STOPPEN;
        }
        return DOORGAAN;
    }
}
