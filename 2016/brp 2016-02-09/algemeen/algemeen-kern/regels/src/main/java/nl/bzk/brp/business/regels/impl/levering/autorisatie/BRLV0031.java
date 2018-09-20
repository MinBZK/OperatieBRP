/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.impl.levering.autorisatie;

import javax.inject.Named;
import nl.bzk.brp.business.regels.Bedrijfsregel;
import nl.bzk.brp.business.regels.context.VerstrekkingsbeperkingRegelContext;
import nl.bzk.brp.business.regels.impl.levering.AbstractLeveringRegel;
import nl.bzk.brp.business.regels.impl.levering.definitieregels.BRLV0035;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;


/**
 * Deze definitieregel controleert of de persoon een verstrekkingsbeperking voor de verzoekende partij heeft.
 *
 * @brp.bedrijfsregel BRLV0031
 */
@Named("BRLV0031")
@Regels(Regel.R1339)
public final class BRLV0031 extends AbstractLeveringRegel implements Bedrijfsregel<VerstrekkingsbeperkingRegelContext> {

    private static final Logger LOGGER   = LoggerFactory.getLogger();

    private final BRLV0035      brlv0035 = new BRLV0035();

    @Override
    public Regel getRegel() {
        return Regel.BRLV0031;
    }

    @Override
    public boolean valideer(final VerstrekkingsbeperkingRegelContext context) {
        final PersoonView huidigeSituatie = context.getHuidigeSituatie();
        final Partij verzoekendePartij = context.getPartij();

        if (brlv0035.isErEenGeldigeVerstrekkingsBeperking(huidigeSituatie, verzoekendePartij)) {
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0031, "Regel gefaald - BRLV0031: Er wordt een service-aanroep gedaan, "
                + "terwijl er een verstrekkingsbeperking aanwezig is. Partij met code {}, persoon met " + "id {}.",
                    verzoekendePartij.getCode().getWaarde(), huidigeSituatie.getID());
            return INVALIDE;
        }

        LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0031, context.geefLogmeldingSucces(getRegel()));
        return VALIDE;
    }

    @Override
    public Class<VerstrekkingsbeperkingRegelContext> getContextType() {
        return VerstrekkingsbeperkingRegelContext.class;
    }
}
