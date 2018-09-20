/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.impl.levering.autorisatie;

import nl.bzk.brp.business.regels.AutorisatieBedrijfsregel;
import nl.bzk.brp.business.regels.context.AutorisatieRegelContext;
import nl.bzk.brp.business.regels.impl.levering.AbstractLeveringRegel;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;

import javax.inject.Named;


/**
 * Bedrijfsregel die controleert of de opgegeven dienstbundel niet geblokkeerd is.
 *
 * @brp.bedrijfsregel R2056
 */
@Named("R2056")
@Regels(Regel.R2056)
public final class R2056 extends AbstractLeveringRegel implements AutorisatieBedrijfsregel {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public Regel getRegel() {
        return Regel.R2056;
    }

    @Override
    public boolean valideer(final AutorisatieRegelContext context) {
        final Dienst dienst = context.getDienst();
        if (dienst != null && dienst.getDienstbundel().isGeblokkeerd()) {
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_R2056, context.geefLogmeldingFout(getRegel()));
            return INVALIDE;

        }
        LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_R2056, context.geefLogmeldingSucces(getRegel()));
        return VALIDE;
    }

    @Override
    public Class<AutorisatieRegelContext> getContextType() {
        return AutorisatieRegelContext.class;
    }

}
