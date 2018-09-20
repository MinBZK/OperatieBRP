/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.regels.autorisatie;

import java.util.Collection;
import java.util.Iterator;
import javax.inject.Named;
import nl.bzk.brp.business.regels.AutorisatieBedrijfsregel;
import nl.bzk.brp.business.regels.context.AutorisatieRegelContext;
import nl.bzk.brp.business.regels.impl.levering.AbstractLeveringRegel;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;

import static nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst.*;

/**
 * De afnemer moet voor de dienst Synchronisatie persoon ook een leveringsautorisatie hebben voor de dienst
 * 'Mutatielevering op basis van doelbinding' of 'Mutatielevering op basis van afnemerindicatie'
 *
 * @brp.bedrijfsregel BRLV0041
 * @brp.bedrijfsregel R1347
 */
@Named("BRLV0041")
@Regels(Regel.R1347)
public final class BRLV0041 extends AbstractLeveringRegel implements AutorisatieBedrijfsregel {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public boolean valideer(final AutorisatieRegelContext context) {
        final Collection<Dienst> diensten = context.getToegangLeveringsautorisatie().getLeveringsautorisatie().
            geefDiensten(MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING, MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        // verwijder geblokkeerde en ongeldige diensten
        final Iterator<Dienst> dienstIterator = diensten.iterator();
        while (dienstIterator.hasNext()) {
            final Dienst next = dienstIterator.next();
            if (next.isGeblokkeerd() || !next.isDienstGeldig()) {
                dienstIterator.remove();
            }
        }
        if (diensten.isEmpty()) {
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0041, context.geefLogmeldingFout(getRegel()));
            return INVALIDE;
        }

        LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0041, context.geefLogmeldingSucces(getRegel()));
        return VALIDE;
    }

    @Override
    public Regel getRegel() {
        return Regel.BRLV0041;
    }

    @Override
    public Class<AutorisatieRegelContext> getContextType() {
        return AutorisatieRegelContext.class;
    }

}
