/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.impl.levering;

import javax.inject.Named;

import nl.bzk.brp.business.regels.Bedrijfsregel;
import nl.bzk.brp.business.regels.context.HuidigeSituatieRegelContext;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;


/**
 * Leveren van een persoonslijst is alleen mogelijk bij ingeschreven personen.
 *
 * @brp.bedrijfsregel BRLV0003
 */
@Named("BRLV0006")
public final class BRLV0006 implements Bedrijfsregel<HuidigeSituatieRegelContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public Regel getRegel() {
        return Regel.BRLV0006;
    }

    @Override
    public boolean valideer(final HuidigeSituatieRegelContext context) {
        final PersoonView persoonView = context.getHuidigeSituatie();
        final SoortPersoon soortPersoon = persoonView.getSoort().getWaarde();

        if (SoortPersoon.INGESCHREVENE.equals(soortPersoon)) {
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0006, context.geefLogmeldingSucces(getRegel()));
            return VALIDE;
        } else {
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0006, context.geefLogmeldingFout(getRegel()));
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0006, "Persoon met id: {} is geen ingeschrevene, maar van soort: {}.",
                    persoonView.getID(), soortPersoon);
            return INVALIDE;
        }
    }

    @Override
    public Class<HuidigeSituatieRegelContext> getContextType() {
        return HuidigeSituatieRegelContext.class;
    }
}
