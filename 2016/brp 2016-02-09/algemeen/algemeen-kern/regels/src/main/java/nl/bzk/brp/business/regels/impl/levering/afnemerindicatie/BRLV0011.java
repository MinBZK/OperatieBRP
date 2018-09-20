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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;


/**
 * Datum aanvang Materiele Periode mag, indien gevuld, niet in de toekomst liggen.
 *
 * @brp.bedrijfsregel BRLV0011
 */
@Named("BRLV0011")
@Regels(Regel.R1405)
public final class BRLV0011 implements Bedrijfsregel<AfnemerindicatieRegelContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public Regel getRegel() {
        return Regel.BRLV0011;
    }

    @Override
    public boolean valideer(final AfnemerindicatieRegelContext context) {
        if (isVanJuisteSoort(context.getSoortAdministratieveHandeling())) {

            final DatumEvtDeelsOnbekendAttribuut aanvang = context.getDatumAanvangMaterielePeriode();

            if (aanvang != null && !aanvang.voorOfOpDatumSoepel(DatumAttribuut.vandaag())) {
                LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0011, context.geefLogmeldingFout(getRegel()));
                return INVALIDE;
            }
        }

        LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0011, context.geefLogmeldingSucces(getRegel()));
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
