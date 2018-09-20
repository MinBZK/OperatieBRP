/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.regels.autorisatie;

import javax.inject.Named;
import nl.bzk.brp.business.regels.AutorisatieBedrijfsregel;
import nl.bzk.brp.business.regels.context.AutorisatieRegelContext;
import nl.bzk.brp.business.regels.impl.levering.AbstractLeveringRegel;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.momentview.autaut.PersoonAfnemerindicatieView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;

/**
 * De persoon waarvoor de partij een synchronisatie verzoekt, moet een persoon zijn waarop de partij een afnemerindicatie heeft.
 *
 * Als binnen de Leveringsautorisatie waarvoor de Partij een verzoek 'Synchronisatie persoon' doet,
 * een geldige Dienst Mutatielevering op basis van afnemerindicatie aanwezig is, dan geldt dat de
 * bij Persoon waarvoor de Partij een synchronisatie verzoekt een Afnemerindicatie bestaat voor
 * de betreffende Leveringsautorisatie (er is een geldig voorkomen van Persoon \
 * Afnemerindicatie met de betreffende waarden van Persoon, Partij (Afnemer) en Leveringsautorisatie)
 *
 * @brp.bedrijfsregel BRLV0040
 * @brp.bedrijfsregel R1346
 */
@Named("BRLV0040")
@Regels(Regel.R1346)
public final class BRLV0040 extends AbstractLeveringRegel implements AutorisatieBedrijfsregel {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public Regel getRegel() {
        return Regel.BRLV0040;
    }

    @Override
    public boolean valideer(final AutorisatieRegelContext context) {

        final Leveringsautorisatie la = context.getToegangLeveringsautorisatie().getLeveringsautorisatie();
        final PersoonView huidigeSituatie = context.getHuidigeSituatie();

        //als la geen dienst MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE bevat is geen afnemerindicatie controle nodig
        if (la.geefDiensten(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE).isEmpty()) {
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0040, context.geefLogmeldingSucces(getRegel()));
            return VALIDE;
        }

        //Controleert of de persoon een afnemerindicatie heeft binnen het leveringsautorisatie.
        for (final PersoonAfnemerindicatieView afnemerIndicatie : huidigeSituatie.getAfnemerindicaties()) {
            if (afnemerIndicatie.getLeveringsautorisatie().getWaarde().getID().equals(la.getID())) {
                LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0040, context.geefLogmeldingSucces(getRegel()));
                return VALIDE;
            }
        }
        LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0040, context.geefLogmeldingFout(getRegel()));
        return INVALIDE;
    }

    @Override
    public Class<AutorisatieRegelContext> getContextType() {
        return AutorisatieRegelContext.class;
    }

}
