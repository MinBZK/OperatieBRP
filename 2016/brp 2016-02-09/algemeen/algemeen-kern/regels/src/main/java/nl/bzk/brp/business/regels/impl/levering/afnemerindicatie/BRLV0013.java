/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.impl.levering.afnemerindicatie;

import javax.inject.Named;

import nl.bzk.brp.business.regels.Bedrijfsregel;
import nl.bzk.brp.business.regels.context.AfnemerindicatieRegelContext;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;


/**
 * Datum/tijd einde volgen mag niet in het verleden liggen.
 *
 * @brp.bedrijfsregel BRLV0013
 */
@Named("BRLV0013")
@Regels(Regel.R1406)
public final class BRLV0013 implements Bedrijfsregel<AfnemerindicatieRegelContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public Regel getRegel() {
        return Regel.BRLV0013;
    }

    @Override
    public boolean valideer(final AfnemerindicatieRegelContext context) {
        if (isVanJuisteSoort(context.getSoortAdministratieveHandeling())) {
            final DatumAttribuut vandaag = DatumAttribuut.vandaag();
            final DatumAttribuut eindeVolgen = context.getDatumEindeVolgen();
            if (eindeVolgen != null && eindeVolgen.voorOfOp(vandaag)) {
                LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0013, context.geefLogmeldingFout(getRegel()));
                return INVALIDE;
            }
        }

        LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0013, context.geefLogmeldingSucces(getRegel()));
        return VALIDE;
    }

    @Override
    public Class<AfnemerindicatieRegelContext> getContextType() {
        return AfnemerindicatieRegelContext.class;
    }

    /**
     * Controleert of de administratieve handeling van het juiste soort is.
     *
     * @param soortAdministratieveHandeling soort administratieve handeling
     * @return true als soort overeenkomt
     */
    private boolean isVanJuisteSoort(final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        return soortAdministratieveHandeling != null
            && soortAdministratieveHandeling.equals(SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE);
    }
}
