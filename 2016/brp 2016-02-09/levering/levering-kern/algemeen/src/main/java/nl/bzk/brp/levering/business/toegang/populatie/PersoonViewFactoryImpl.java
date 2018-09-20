/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.populatie;

import javax.inject.Inject;
import nl.bzk.brp.levering.business.bepalers.SoortSynchronisatieBepaler;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.ToegangLeveringsautorisatieService;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.AdministratieveHandelingDeltaPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaat.HistorieVanafPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaat.VoorkomenLeveringMutatiePredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.PersoonHisVolledigViewUtil;
import org.apache.commons.collections.Predicate;
import org.springframework.stereotype.Component;

/**
 * De implementatie van de PersoonViewFactory die views maakt op personen.
 *
 * @brp.bedrijfsregel R1544
 */
@Component
public class PersoonViewFactoryImpl implements PersoonViewFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Dit predikaat is stateless, dus kan als field worden opgenomen
     */
    private final VoorkomenLeveringMutatiePredikaat voorkomenLeveringMutatiePredikaat = new VoorkomenLeveringMutatiePredikaat();

    @Inject
    private SoortSynchronisatieBepaler soortSynchronisatieBepaler;

    @Inject
    private ToegangLeveringsautorisatieService toegangLeveringsautorisatieService;

    @Override
    public PersoonHisVolledigView maakView(final PersoonHisVolledig persoonHisVolledig, final Leveringinformatie leveringAutorisatie,
        final Populatie populatie, final AdministratieveHandelingModel administratieveHandeling)
    {
        final PersoonHisVolledigView persoonHisVolledigView = maakLegeView(persoonHisVolledig);
        return voegPredikatenToe(persoonHisVolledigView, leveringAutorisatie, populatie, administratieveHandeling);
    }

    @Override
    public PersoonHisVolledigView maakLegeView(final PersoonHisVolledig persoonHisVolledig) {
        final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(persoonHisVolledig, null);
        PersoonHisVolledigViewUtil.initialiseerView(persoonHisVolledigView);
        return persoonHisVolledigView;
    }

    @Regels(Regel.VR00079)
    @Override
    public PersoonHisVolledigView voegPredikatenToe(final PersoonHisVolledigView persoonHisVolledigView, final Leveringinformatie leveringAutorisatie,
        final Populatie populatie, final AdministratieveHandelingModel administratieveHandeling)
    {
        final SoortDienst dienst = leveringAutorisatie.getSoortDienst();
        final SoortAdministratieveHandeling soortHandeling = administratieveHandeling.getSoort().getWaarde();

        final SoortSynchronisatie soortSynchronisatie = soortSynchronisatieBepaler.bepaalSoortSynchronisatie(populatie, dienst, soortHandeling);

        persoonHisVolledigView.voegPredikaatToe(maakMaterieleHistoriePredikaat(persoonHisVolledigView, leveringAutorisatie, soortHandeling));

        switch (soortSynchronisatie) {
            case VOLLEDIGBERICHT:
                persoonHisVolledigView.voegPredikaatToe(voorkomenLeveringMutatiePredikaat);
                break;
            case MUTATIEBERICHT:
                persoonHisVolledigView.setPeilmomentVoorAltijdTonenGroepen(administratieveHandeling.getTijdstipRegistratie());
                persoonHisVolledigView.voegPredikaatToe(new AdministratieveHandelingDeltaPredikaat(administratieveHandeling.getID()));
                break;
            default:
                LOGGER.error(FunctioneleMelding.LEVERING_ONGELDIGE_SOORT_SYNCHRONISATIE, "De soort synchronisatie wordt niet ondersteund.");
                break;
        }
        return persoonHisVolledigView;
    }

    /**
     * Bepaalt de materiële historie van de betrokken persoon op de tijdstip registratie van de administratieve handeling.
     *
     * @param persoonVolledig     de betrokken persoon volledig
     * @param leveringAutorisatie de leveringAutorisatie
     * @param soortHandeling      de soort administratieve handeling
     * @return de view van betrokken persoon
     */
    private Predicate maakMaterieleHistoriePredikaat(final PersoonHisVolledig persoonVolledig, final Leveringinformatie leveringAutorisatie,
        final SoortAdministratieveHandeling soortHandeling)
    {
        final DatumAttribuut materieelVanaf;
        if (moetRekeningHoudenMetMaterielePeriodeVanaf(soortHandeling, leveringAutorisatie)) {
            final Leveringsautorisatie la = leveringAutorisatie.getToegangLeveringsautorisatie().getLeveringsautorisatie();
            materieelVanaf = zoekGeplaatsteMaterieelVanafDatum(la, persoonVolledig);
        } else {
            materieelVanaf = null;
        }

        return HistorieVanafPredikaat.geldigOpEnNa(materieelVanaf);
    }

    /**
     * Bepaalt of rekening moet worden gehouden met de materiele periode vanaf op de afnemerindicatie. Dit geldt alleen voor het plaatsen van de
     * afnemerindicatie en voor synchronisatie verzoeken.
     *
     * @param soortAdministratieveHandeling Het soort administratieve handeling.
     * @param leveringAutorisatie           De leveringautorisatie
     * @return True als er rekening moet worden gehouden met de materiele periode vanaf, anders false.
     */
    private boolean moetRekeningHoudenMetMaterielePeriodeVanaf(final SoortAdministratieveHandeling soortAdministratieveHandeling,
        final Leveringinformatie leveringAutorisatie)
    {
        final boolean isAltijdVolledigBericht =
            soortSynchronisatieBepaler.geefHandelingenMetVolledigBericht().contains(soortAdministratieveHandeling);
        final boolean isMutatieleveringOpBasisVanAfnemerindicatie =
            leveringAutorisatie.getSoortDienst()
                .equals(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        return isAltijdVolledigBericht || isMutatieleveringOpBasisVanAfnemerindicatie;
    }

    /**
     * Zoekt de geplaatste afnemerindicatie op een persoon, die is geplaatst als gevolg van de gegeven administratieve handeling.
     *
     * @param gegevenLeveringsautorisatie de leveringsautorisatie waarvoor we de afnemerindicatie zoeken
     * @param persoonVolledig             de persoon met afnemerindicaties
     * @return de meegegeven datum aanvang materiele periode, of null
     */
    private DatumAttribuut zoekGeplaatsteMaterieelVanafDatum(final Leveringsautorisatie gegevenLeveringsautorisatie,
        final PersoonHisVolledig persoonVolledig)
    {
        for (final PersoonAfnemerindicatieHisVolledig afnemerindicatie : persoonVolledig.getAfnemerindicaties()) {
            //dit is een proxy zonder gevulde members want geen json annotaties
            final Leveringsautorisatie leveringsautorisatieProxy = afnemerindicatie.getLeveringsautorisatie().getWaarde();
            // afnemerindicaties kunnen geplaatst zijn op inmiddels verlopen leveringautorisaties,
            // vandaar de aanroep zonder controle. De null check is niet zo netjes, maar voorkomt problemen in het
            // geval de afnemerindicatietabel en afnemerindicaitieblob niet symmetrisch zijn.
            final Leveringsautorisatie leveringsautorisatie = toegangLeveringsautorisatieService.
                    geefLeveringautorisatieZonderControle(leveringsautorisatieProxy.getID());
            if (leveringsautorisatie != null && leveringsautorisatie.getID().equals(gegevenLeveringsautorisatie.getID())
                && afnemerindicatie.getPersoonAfnemerindicatieHistorie().heeftActueelRecord())
            {
                final DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode =
                    afnemerindicatie.getPersoonAfnemerindicatieHistorie().getActueleRecord()
                        .getDatumAanvangMaterielePeriode();
                if (datumAanvangMaterielePeriode != null) {
                    return new DatumAttribuut(datumAanvangMaterielePeriode);
                }
                break;
            }
        }

        return null;
    }

    @Override
    @Regels(Regel.R1544)
    public final PersoonHisVolledigView maakMaterieleHistorieView(final PersoonHisVolledig persoonHisVolledig,
        final Leveringinformatie leveringAutorisatie,
        final AdministratieveHandelingModel administratieveHandeling,
        final DatumAttribuut materieelVanaf)
    {
        final PersoonHisVolledigView view;
        if (soortSynchronisatieBepaler.geefHandelingenMetVolledigBericht().contains(administratieveHandeling.getSoort().getWaarde())) {
            view = maakView(persoonHisVolledig, leveringAutorisatie, null, administratieveHandeling);
        } else {
            view = new PersoonHisVolledigView(persoonHisVolledig, null, null);
            view.voegPredikaatToe(HistorieVanafPredikaat.geldigOpEnNa(materieelVanaf));
        }
        view.voegPredikaatToe(voorkomenLeveringMutatiePredikaat);
        PersoonHisVolledigViewUtil.initialiseerView(view);
        return view;
    }
}
