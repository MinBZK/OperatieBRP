/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging.levering;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContextBasis;
import nl.bzk.brp.business.util.BevragingUtil;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.HistorievormAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.internbericht.ProtocolleringOpdracht;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.operationeel.lev.LeveringModel;
import nl.bzk.brp.model.operationeel.lev.LeveringPersoonModel;
import nl.bzk.brp.protocollering.publicatie.ProtocolleringPublicatieMisluktExceptie;
import nl.bzk.brp.protocollering.publicatie.ProtocolleringPublicatieService;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;


/**
 * Protocollering stap voor bevraging t.b.v. Leveringen.
 *
 * @brp.bedrijfsregel VR00041
 * @brp.bedrijfsregel R1613
 */
@Regels({ Regel.VR00041, Regel.R1613 })
public class ProtocolleringStap extends
    AbstractBerichtVerwerkingStap<BevragingsBericht, BevragingBerichtContextBasis, BevragingResultaat>
{

    private static final Logger LOGGER = LoggerFactory
        .getLogger();

    private static final Map<SoortBericht, SoortDienst> SOORT_BERICHT_CATALOGUS_OPTIE_MAP =
        new HashMap<SoortBericht, SoortDienst>() {

            {
                put(SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON,
                    SoortDienst.GEEF_DETAILS_PERSOON);
            }
        };

    @Inject
    private ProtocolleringPublicatieService protocolleringPublicatieService;

    /**
     * Voert de protocolleringstap uit.
     *
     * @param inkomendBericht Het inkomend bericht
     * @param context         De stappencontext
     * @param resultaat       Het stappenresultaat
     * @return DOORGAAN (true) als de stap succesvol is uitgevoerd, STOPPEN (false) als er een fout is opgetreden.
     * @brp.bedrijfsregel VR00045
     * @brp.bedrijfsregel VR00046
     * @brp.bedrijfsregel VR00047
     * @brp.bedrijfsregel VR00048
     */
    @Override
    @Regels({ Regel.VR00045, Regel.VR00046, Regel.VR00047, Regel.VR00048 })
    public final boolean voerStapUit(final BevragingsBericht inkomendBericht,
        final BevragingBerichtContextBasis context, final BevragingResultaat resultaat)
    {
        LOGGER.debug("Start protocollering.");
        final ToegangLeveringsautorisatie tla = context.getLeveringinformatie().getToegangLeveringsautorisatie();
        if (isProtocolleringToegestaan(tla.getLeveringsautorisatie()) && !resultaat.getGevondenPersonen().isEmpty()) {
            final Dienst dienst = bepaalAangeroepenDienst(inkomendBericht, tla.getLeveringsautorisatie());

            LeveringModel leveringModel;

            // Gebruik default waardes indien zaken niet gevuld zijn.
            HistorievormAttribuut historievormAttribuut = inkomendBericht.getParameters().getHistorievorm();
            DatumTijdAttribuut peilmomentFormeel = inkomendBericht.getParameters().getPeilmomentFormeelResultaat();
            DatumAttribuut peilmomentMaterieel = inkomendBericht.getParameters().getPeilmomentMaterieelResultaat();

            if (peilmomentMaterieel == null) {
                peilmomentMaterieel = BevragingUtil.getDefaultMaterieelPeilmomentVoorBevraging();
            }
            if (peilmomentFormeel == null) {
                peilmomentFormeel = BevragingUtil.getDefaultFormeelPeilmomentVoorBevraging();
            }
            if (historievormAttribuut == null) {
                historievormAttribuut = BevragingUtil.getDefaultHistorieVormVoorBevraging();
            }

            final Integer toegangLeveringsautorisatieId = tla.getID();
            final Integer dienstId = dienst.getID();
            final DatumTijdAttribuut datumTijdNu = DatumTijdAttribuut.nu();
            final DatumAttribuut dagNaPeilmomentMaterieel = new DatumAttribuut(peilmomentMaterieel.getWaarde());
            dagNaPeilmomentMaterieel.voegDagToe(1);

            switch (historievormAttribuut.getWaarde()) {
                case GEEN:
                    leveringModel =
                        new LeveringModel(toegangLeveringsautorisatieId, dienstId, datumTijdNu, null, peilmomentMaterieel,
                            dagNaPeilmomentMaterieel, peilmomentFormeel, peilmomentFormeel, null, null);
                    break;
                case MATERIEEL:
                    leveringModel =
                        new LeveringModel(toegangLeveringsautorisatieId, dienstId, datumTijdNu, null, null,
                            dagNaPeilmomentMaterieel, peilmomentFormeel, peilmomentFormeel, null, null);
                    break;
                case MATERIEEL_FORMEEL:
                    leveringModel =
                        new LeveringModel(toegangLeveringsautorisatieId, dienstId, datumTijdNu, null, null,
                            dagNaPeilmomentMaterieel, null, peilmomentFormeel, null, null);
                    break;
                default:
                    throw new IllegalStateException("Onbekende historievorm voor protocollering: "
                        + historievormAttribuut.getWaarde().getNaam());
            }

            final Set<LeveringPersoonModel> leveringPersonen = new HashSet<>(resultaat.getGevondenPersonen().size());

            for (final PersoonHisVolledigView persoonHisVolledigView : resultaat.getGevondenPersonen()) {
                // Eerste parameter van LeveringPersoonModel is null, omdat dit later in de verwerking correct wordt
                // opgeslagen door de protocollering service. (ProtocolleringVerwerkingService)
                final LeveringPersoonModel leveringPersoon =
                    new LeveringPersoonModel(null, persoonHisVolledigView.getID());
                leveringPersonen.add(leveringPersoon);
            }

            final ProtocolleringOpdracht protocolleringOpdracht =
                new ProtocolleringOpdracht(leveringModel, leveringPersonen);
            protocolleringOpdracht.setSoortDienst(SoortDienst.GEEF_DETAILS_PERSOON);
            protocolleringOpdracht.setHistorievorm(historievormAttribuut.getWaarde());

            try {
                protocolleringPublicatieService.publiceerProtocolleringGegevens(protocolleringOpdracht);
            } catch (final ProtocolleringPublicatieMisluktExceptie e) {
                LOGGER.error("Protocollering mislukt.", e);
                throw e;
            }
        }

        LOGGER.debug("Einde protocollering.");
        return DOORGAAN;
    }

    /**
     * Functie controlleert of er uberhaupt geprotocolleerd mag worden. Dit mag niet als het toegangsleveringsautorisatie geheime protocollering kent.
     * (Politie, AIVD, MIVD, NSA etc..)
     *
     * @param la leveringsautorisatie van de afnemer
     * @return true indien er geprotocolleerd mag worden anders false
     */
    private boolean isProtocolleringToegestaan(final Leveringsautorisatie la) {
        return !Protocolleringsniveau.GEHEIM.equals(la.getProtocolleringsniveau());
    }

    /**
     * Bepaalt de aangeroepen dienst op basis van het soort bericht.
     *
     * @param inkomendBericht het inkomend bericht
     * @param la              de leveringsautorisatie
     * @return de aangeroepen dienst uit de diensten lijst.
     */
    private Dienst bepaalAangeroepenDienst(final Bericht inkomendBericht, final Leveringsautorisatie la) {
        Dienst aangeroepenDienst = null;
        final SoortDienst soortDienst =
            SOORT_BERICHT_CATALOGUS_OPTIE_MAP.get(inkomendBericht.getSoort().getWaarde());

        if (soortDienst == null) {
            final String message =
                "Kan aangeroepen dienst niet bepalen voor soort bericht: "
                    + inkomendBericht.getSoort().getWaarde().getNaam();
            LOGGER.error(message);
            throw new IllegalStateException(message);
        } else {
            aangeroepenDienst = la.geefDienst(soortDienst);
        }
        return aangeroepenDienst;
    }
}
