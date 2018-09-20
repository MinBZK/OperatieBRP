/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.regels.stamgegeven;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import nl.bzk.brp.business.regels.BerichtBedrijfsregel;
import nl.bzk.brp.business.regels.context.BerichtRegelContext;
import nl.bzk.brp.business.regels.impl.levering.AbstractLeveringRegel;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.StamgegevenAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.SynchronisatieStamgegeven;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;

/**
 * De gevraagde soort stamgegeven moet een soort stamgegeven zijn dat opgevraagd mag worden.
 *
 * @brp.bedrijfsregel BRLV0024
 */
@Named("BRLV0024")
public final class BRLV0024 extends AbstractLeveringRegel implements BerichtBedrijfsregel<BerichtRegelContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public List<BerichtIdentificeerbaar> valideer(final BerichtRegelContext context) {
        final List<BerichtIdentificeerbaar> objectenDieDeRegelOvertreden = new ArrayList<>();

        final BerichtBericht bericht = context.getBericht();

        final StamgegevenAttribuut stamgegeven = bericht.getParameters().getStamgegeven();
        if (stamgegeven != null) {
            final SynchronisatieStamgegeven synchronisatieStamgegeven =
                SynchronisatieStamgegeven.vindEnumVoorTabelNaam(stamgegeven.getWaarde());
            if (synchronisatieStamgegeven == null) {
                objectenDieDeRegelOvertreden.add(bericht.getParameters());
            }
        }

        if (objectenDieDeRegelOvertreden.isEmpty()) {
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0024, context.geefLogmeldingSucces(getRegel()));
        } else {
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0024, context.geefLogmeldingFout(getRegel()));
        }

        return objectenDieDeRegelOvertreden;
    }

    @Override
    public Class<BerichtRegelContext> getContextType() {
        return BerichtRegelContext.class;
    }

    @Override
    public Regel getRegel() {
        return Regel.BRLV0024;
    }
}
