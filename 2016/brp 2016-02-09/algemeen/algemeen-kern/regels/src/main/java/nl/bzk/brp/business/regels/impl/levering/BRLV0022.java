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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;


/**
 * BRLV0022: De gevraagde, opgegeven persoon mag niet foutief zijn.
 */
@Named("BRLV0022")
public final class BRLV0022 implements Bedrijfsregel<HuidigeSituatieRegelContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public boolean valideer(final HuidigeSituatieRegelContext regelContext) {
        boolean isValide = VALIDE;
        final PersoonView huidigeSituatie = regelContext.getHuidigeSituatie();
        if (huidigeSituatie != null && SoortPersoon.INGESCHREVENE == huidigeSituatie.getSoort().getWaarde()) {
            if (huidigeSituatie.getBijhouding() != null
                && huidigeSituatie.getBijhouding().getNadereBijhoudingsaard() != null)
            {
                isValide = NadereBijhoudingsaard.FOUT != huidigeSituatie.getBijhouding().getNadereBijhoudingsaard().getWaarde()
                    && NadereBijhoudingsaard.ONBEKEND != huidigeSituatie.getBijhouding().getNadereBijhoudingsaard().getWaarde();
            }
        }
        if (isValide) {
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0022, regelContext.geefLogmeldingSucces(getRegel()));
        } else {
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0022, regelContext.geefLogmeldingFout(getRegel()));
        }

        return isValide;
    }

    @Override
    public Class<HuidigeSituatieRegelContext> getContextType() {
        return HuidigeSituatieRegelContext.class;
    }

    @Override
    public Regel getRegel() {
        return Regel.BRLV0022;
    }
}
