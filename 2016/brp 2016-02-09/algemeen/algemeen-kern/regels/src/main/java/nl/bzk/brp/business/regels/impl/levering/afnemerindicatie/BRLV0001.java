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
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.logisch.autaut.PersoonAfnemerindicatie;


/**
 * Een verwijzing in een bericht naar een voorkomen van Persoon/Afnemerindicatie dient correct en geldig te zijn.
 *
 * @brp.bedrijfsregel BRLV0001
 */
@Named("BRLV0001")
@Regels(Regel.R1401)
public final class BRLV0001 implements Bedrijfsregel<AfnemerindicatieRegelContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public Regel getRegel() {
        return Regel.BRLV0001;
    }

    @Override
    public boolean valideer(final AfnemerindicatieRegelContext context) {
        if (isVanJuisteSoort(context.getSoortAdministratieveHandeling())) {
            final Partij afnemer = context.getAfnemer();
            final Leveringsautorisatie leveringsautorisatie = context.getLeveringsautorisatie();

            boolean gevonden = false;
            for (final PersoonAfnemerindicatie indicatie : context.getHuidigeSituatie().getAfnemerindicaties()) {
                // check ontbreken
                if (indicatie.getAfnemer().getWaarde().getID().equals(afnemer.getID())
                    && (leveringsautorisatie == null && indicatie.getLeveringsautorisatie() == null || leveringsautorisatie != null
                        && leveringsautorisatie.getID() != null
                        && indicatie.getLeveringsautorisatie() != null
                        && indicatie.getLeveringsautorisatie().getWaarde() != null
                        && indicatie.getLeveringsautorisatie().getWaarde().getID() != null
                        && indicatie.getLeveringsautorisatie().getWaarde().getID()
                                .equals(leveringsautorisatie.getID())))
                {
                    gevonden = true;
                }
            }

            if (!gevonden) {
                LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0001, context.geefLogmeldingFout(getRegel()));
                return INVALIDE;
            }
        }
        LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0001, context.geefLogmeldingSucces(getRegel()));

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
            && soortAdministratieveHandeling.equals(SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE);
    }

}
