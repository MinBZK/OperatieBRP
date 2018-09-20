/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging.levering;

import javax.inject.Inject;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.regels.logging.RegelLoggingUtil;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContextBasis;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.ToegangLeveringsautorisatieService;
import nl.bzk.brp.levering.business.toegang.populatie.LeveringinformatieService;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.LeveringsautorisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.bevraging.bijhouding.ZoekPersoonBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;


/**
 * De stap waarin extra gegevens opgehaald worden.
 */
public class BerichtVerrijkingsStap extends
    AbstractBerichtVerwerkingStap<BevragingsBericht, BevragingBerichtContextBasis, BevragingResultaat>
{

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private LeveringinformatieService leveringinformatieService;

    @Inject
    private ReferentieDataRepository referentieRepository;

    @Inject
    private ToegangLeveringsautorisatieService toegangLeveringsautorisatieService;

    /**
     * Voer stap uit.
     *
     * @param bericht   het bericht
     * @param context   de context
     * @param resultaat het resultaat
     * @return of de stap succesvol is uitgevoerd of niet
     * @brp.bedrijfsregel VR00050, R1260, R2054, R2055
     */
    @Regels({Regel.VR00050, Regel.R1260, Regel.R2054, Regel.R2055})
    @Override
    public final boolean voerStapUit(final BevragingsBericht bericht, final BevragingBerichtContextBasis context,
        final BevragingResultaat resultaat)
    {
        final Boolean stapResultaat = DOORGAAN;
        final String zendendePartijCodeString = bericht.getStuurgegevens().getZendendePartijCode();

        final Partij zendendePartij =
            referentieRepository.vindPartijOpCode(new PartijCodeAttribuut(Integer
                    .parseInt(zendendePartijCodeString)));
        bericht.getStuurgegevens().setZendendePartij(new PartijAttribuut(zendendePartij));

        try {
            final Integer zendendePartijCode = Integer.parseInt(zendendePartijCodeString);
            final int leveringsautorisatieID = Integer.parseInt(bericht.getParameters().getLeveringsautorisatieID());
            final ToegangLeveringsautorisatie tla =
                leveringinformatieService.geefToegangLeveringautorisatie(leveringsautorisatieID,
                        zendendePartijCode);

            final Integer berichtDienstID = Integer.parseInt(bericht.getParameters().getDienstID());
            //R2055 check (de opgegeven dienst bestaat)
            if (!toegangLeveringsautorisatieService.bestaatDienst(berichtDienstID)) {
                resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.R2055,
                    FunctioneleMelding.LEVERING_BEDRIJFSREGEL_R2055.getOmschrijving()));
                LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_R2055, RegelLoggingUtil.PREFIX_LOGMELDING_FOUT, Regel.R2055);
                return STOPPEN;
            }
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_R2055, RegelLoggingUtil.PREFIX_LOGMELDING_SUCCES, Regel.R2055);

            //R1260 check (de opgegeven leveringsautorisatie bevat de opgegeven dienst)
            final Dienst dienst = geefDienstOpBasisVanId(tla, berichtDienstID);
            if (dienst == null) {
                resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.R1260,
                        FunctioneleMelding.LEVERING_BEDRIJFSREGEL_R1260.getOmschrijving()));
                LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_R1260, RegelLoggingUtil.PREFIX_LOGMELDING_FOUT, Regel.R1260);
                return STOPPEN;
            }
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_R1260, RegelLoggingUtil.PREFIX_LOGMELDING_SUCCES, Regel.R1260);

            //R2054 check (is dienst van het verwachte type)
            final SoortDienst soortDienst = geefSoortDienst(bericht);
            if (dienst.getSoort() != soortDienst) {
                resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.R2054,
                        FunctioneleMelding.LEVERING_BEDRIJFSREGEL_R2054.getOmschrijving()));
                LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_R2054, RegelLoggingUtil.PREFIX_LOGMELDING_FOUT, Regel.R2054);
                return STOPPEN;
            }
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_R2054, RegelLoggingUtil.PREFIX_LOGMELDING_SUCCES, Regel.R2054);

            final Leveringinformatie leveringinformatie = new Leveringinformatie(tla, dienst);
            context.setLeveringinformatie(leveringinformatie);
            // TBV berichtarchivering wordt de gehele leveringsautorisatie aan het bericht meegegeven
            bericht.getParameters().setLeveringsautorisatie(
                    new LeveringsautorisatieAttribuut(tla.getLeveringsautorisatie()));
        } catch (final OnbekendeReferentieExceptie e) {
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.BRLV0007, Regel.BRLV0007.getOmschrijving()));
            return STOPPEN;
        }

        LOGGER.debug("BerichtverrijkingsStap.voerStapUit");

        return stapResultaat;
    }

    private static Dienst geefDienstOpBasisVanId(final ToegangLeveringsautorisatie tla, final int berichtDienstID) {
        Dienst gevondenDienst = null;
        for (final Dienst dienst : tla.getLeveringsautorisatie().geefDiensten()) {
            if (dienst.getID() == berichtDienstID) {
                gevondenDienst = dienst;
                break;
            }
        }
        return gevondenDienst;
    }

    /**
     * Geeft de catalogus optie op basis van het bericht.
     *
     * @param bericht het bericht
     * @return de catalogus optie
     */
    private static SoortDienst geefSoortDienst(final BerichtBericht bericht) {
        final SoortDienst soortDienst;
        if (bericht instanceof GeefDetailsPersoonBericht
            || bericht instanceof nl.bzk.brp.model.bevraging.levering.GeefDetailsPersoonBericht)
        {
            soortDienst = SoortDienst.GEEF_DETAILS_PERSOON;
        } else if (bericht instanceof ZoekPersoonBericht
            || bericht instanceof nl.bzk.brp.model.bevraging.levering.ZoekPersoonBericht)
        {
            soortDienst = SoortDienst.ZOEK_PERSOON;
        } else {
            throw new IllegalArgumentException("Er is een type bericht dat niet ondersteund wordt: "
                + bericht.getClass().getSimpleName());
        }
        return soortDienst;
    }
}
