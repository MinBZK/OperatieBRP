/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.regels;

import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.brp.business.regels.Bedrijfsregel;
import nl.bzk.brp.business.regels.context.AutorisatieRegelContext;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.levering.business.expressietaal.ExpressieService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;


/**
 * De persoon waarbij de partij een afnemersindicatie wil plaatsen moet binnen de populatie van de overkoepelende leveringsautorisatie vallen.
 *
 * @brp.bedrijfsregel BRLV0014
 */
@Named("BRLV0014")
@Regels(Regel.R1407)
public final class BRLV0014 implements Bedrijfsregel<AutorisatieRegelContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private ExpressieService expressieService;

    @Override
    public Regel getRegel() {
        return Regel.BRLV0014;
    }

    @Override
    public Class<AutorisatieRegelContext> getContextType() {
        return AutorisatieRegelContext.class;
    }

    @Regels(Regel.VR00109)
    @Override
    public boolean valideer(final AutorisatieRegelContext context) {
        final Leveringsautorisatie la = context.getToegangLeveringsautorisatie().getLeveringsautorisatie();
        final PersoonView huidigeSituatie = context.getHuidigeSituatie();

        final Dienst dienst = context.getDienst();
        if (dienst != null) {
            final boolean valtPersoonBinnenPopulatieBeperking = valtPersoonBinnenPopulatieBeperking(
                la.getPopulatiebeperkingExpressieString(),
                context.getDienst().getDienstbundel().getNaderePopulatiebeperkingExpressieString(),
                context.getToegangLeveringsautorisatie().getNaderePopulatiebeperkingExpressieString(), huidigeSituatie, la);


            if (!valtPersoonBinnenPopulatieBeperking) {
                LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0023, context.geefLogmeldingFout(getRegel()));
                return INVALIDE;
            }
        }
        LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0023, context.geefLogmeldingSucces(getRegel()));
        return VALIDE;
    }


    /**
     * Controleert of de persoon binnen de populatiebeperking valt.
     *
     * @param leveringsautorisatie leveringsautorisatie waarvoor bericht is ingeschoten.
     * @param huidigeSituatie      de huidige situatie, persoonview van de persoon.
     * @return true als er een geldige dienst is, anders false.
     */
    @Regels(Regel.VR00109)
    private boolean valtPersoonBinnenPopulatieBeperking(final String populatieBepering, final String populatieBepalingDienstbundel, final String
        populatieBepalingToegangsleveringsautorisatie, final PersoonView huidigeSituatie, final Leveringsautorisatie leveringsautorisatie)
    {
        try {
            final Expressie expressieParsed = expressieService
                .geefPopulatiebeperking(populatieBepering, populatieBepalingDienstbundel, populatieBepalingToegangsleveringsautorisatie);
            final Expressie expressie =
                BRPExpressies.evalueer(expressieParsed, huidigeSituatie);
            if (expressie.isNull()) {
                LOGGER.info(
                    FunctioneleMelding.LEVERING_BEDRIJFSREGEL_VR00109,
                    "Populatiebeperking evalueert naar waarde NULL (onbekend) voor leveringsautorisatie: '{}' en persoon met id: {}",
                    leveringsautorisatie.getID(), huidigeSituatie.getID());
            }
            return expressie.alsBoolean();
        } catch (ExpressieExceptie expressieExceptie) {
            LOGGER.error(FunctioneleMelding.LEVERING_AUTORISATIE_FOUT_BEPALEN_POPULATIE,
                "Er is een fout opgetreden tijdens het bepalen van de leveringsautorisatie populatie map.", expressieExceptie);
            return false;
        }
    }
}
