/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.impl.levering.autorisatie;

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


/**
 * Bedrijfsregel die controleert of de opgegeven dienst niet geblokkeerd is.
 *
 * @brp.bedrijfsregel R1264
 */
@Named("R1264")
@Regels(Regel.R1264)
public final class R1264 extends AbstractLeveringRegel implements AutorisatieBedrijfsregel {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public Regel getRegel() {
        return Regel.R1264;
    }

    @Override
    public boolean valideer(final AutorisatieRegelContext context) {
        final Dienst dienst = context.getDienst();
        if (dienst != null && dienst.isGeblokkeerd()) {
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_R1264, context.geefLogmeldingFout(getRegel()));
            return INVALIDE;
        }
        LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_R1264, context.geefLogmeldingSucces(getRegel()));
        return VALIDE;
    }

    @Override
    public Class<AutorisatieRegelContext> getContextType() {
        return AutorisatieRegelContext.class;
    }

}
