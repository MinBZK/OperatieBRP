/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.voorkomenfilter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.levering.algemeen.service.DienstFilterExpressiesService;
import nl.bzk.brp.levering.business.expressietaal.ExpressieService;
import nl.bzk.brp.levering.business.expressietaal.MagGeleverdWordenVlaggenZetter;
import nl.bzk.brp.levering.business.expressietaal.impl.MagGeleverdWordenVlaggenZetterImpl;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.DienstbundelGroep;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.hisvolledig.predikaat.MagGroepTonenPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaat.MagHistorieTonenPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaat.PreRelatieGegevensNietTonenPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import org.springframework.stereotype.Service;


/**
 * Implementatie van de GroepFilterService. brp.bedrijfsregel VR00059 brp.bedrijfsregel VR00060 brp.bedrijfsregel VR00052
 */
@Service
@Regels({ Regel.VR00052, Regel.VR00059, Regel.VR00078 })
public class VoorkomenFilterServiceImpl implements VoorkomenFilterService {

    private static final Logger LOGGER                                               = LoggerFactory.getLogger();
    private static final String FOUT_BIJ_EVALUEREN_VAN_EXPRESSIE_VOOR_PERSOON_MET_ID = "Fout bij evalueren van expressie voor persoon met id: ";
    private static final String DIENST_ID                                            = ", levering dienst met id: ";

    private final MagGeleverdWordenVlaggenZetter magGeleverdWordenVlaggenZetter = new MagGeleverdWordenVlaggenZetterImpl();

    @Inject
    private VerplichtLeverenVerantwoordingVoorAboHandelingen verplichtLeverenVerantwoordingVoorAboHandelingen;

    @Inject
    private DienstFilterExpressiesService dienstFilterExpressiesService;

    @Inject
    private ExpressieService expressieService;

    @Override
    public final void voerVoorkomenFilterUit(final PersoonHisVolledigView persoonHisVolledigView, final Dienst dienst)
        throws ExpressieExceptie
    {
        voegPredikatenToeVoorGroepVoorkomenFiltering(persoonHisVolledigView, dienst, false);
        voerDienstbundelGroepenGroepenFilterExpressieUit(persoonHisVolledigView, dienst);
        logLegeTotaleLijstVanHisElemenetenOpPersoonslijstWarning(persoonHisVolledigView);
    }

    @Override
    public final void voerVoorkomenFilterUit(final PersoonHisVolledigView persoonHisVolledigView, final Dienst dienst,
        final Map<String, List<Attribuut>> attributenMap) throws ExpressieExceptie
    {
        voegPredikatenToeVoorGroepVoorkomenFiltering(persoonHisVolledigView, dienst, false);
        voerDienstbundelGroepenGroepenFilterExpressieUit(persoonHisVolledigView, dienst, attributenMap);
        logLegeTotaleLijstVanHisElemenetenOpPersoonslijstWarning(persoonHisVolledigView);
    }


    @Override
    public final void voerVoorkomenFilterUitVoorMutatieLevering(final PersoonHisVolledigView persoonHisVolledigView,
        final Dienst dienst) throws ExpressieExceptie
    {
        voegPredikatenToeVoorGroepVoorkomenFiltering(persoonHisVolledigView, dienst, true);
        voerDienstbundelGroepenGroepenFilterExpressieUit(persoonHisVolledigView, dienst);
    }

    @Override
    public final void voerVoorkomenFilterUitVoorMutatieLevering(final PersoonHisVolledigView persoonHisVolledigView,
        final Dienst dienst, final Map<String, List<Attribuut>> attributenMap)
        throws ExpressieExceptie
    {
        voegPredikatenToeVoorGroepVoorkomenFiltering(persoonHisVolledigView, dienst, true);
        voerDienstbundelGroepenGroepenFilterExpressieUit(persoonHisVolledigView, dienst, attributenMap);
    }


    /**
     * Voert de leveringsautorisatie groepen autorisatie uit op de groep voorkomens. Per groeps voorkomen wordt dus bepaald of je de Materiele / Formele
     * tijdsaspecten mag zien.
     *
     * @param persoonHisVolledigView de persoon waarop de filtering moet worden toegepast
     * @param dienst                 de dienst
     * @param attributenMap          de map met key expressie naar attributen
     */
    @Regels({ Regel.VR00081, Regel.VR00082, Regel.VR00083 })
    private void voerDienstbundelGroepenGroepenFilterExpressieUit(final PersoonHisVolledigView persoonHisVolledigView,
        final Dienst dienst, final Map<String, List<Attribuut>> attributenMap) throws ExpressieExceptie
    {
        final List<HistorieEntiteit> totaleLijstVanHisElementenOpPersoonsLijst = persoonHisVolledigView.getTotaleLijstVanHisElementenOpPersoonsLijst();

        resetAlleDienstbundelGroepenNaarDefaultWaarde(attributenMap);
        resetAlleVerantwoordingActies(totaleLijstVanHisElementenOpPersoonsLijst);
        //haal alle expressie strings op voor attributen map
        final List<String> dienstbundelGroepenExpressie =
            dienstFilterExpressiesService.geefExpressiesVoorHistorieEnVerantwoordingAttributenLijst(dienst);
        for (final String expressieStr : dienstbundelGroepenExpressie) {
            final List<Attribuut> attributen = attributenMap.get(expressieStr);
            if (attributen != null) {
                for (final Attribuut attribuut : attributen) {
                    attribuut.setMagGeleverdWorden(true);
                }
            }
        }
        verplichtLeverenVerantwoordingVoorAboHandelingen.voerRegelUit(totaleLijstVanHisElementenOpPersoonsLijst,
            persoonHisVolledigView.getAdministratieveHandelingen());
    }

    /**
     * Voert de leveringsautorisatie groepen autorisatie uit op de groep voorkomens. Per groeps voorkomen wordt dus bepaald of je de Materiele / Formele
     * tijdsaspecten mag zien.
     *
     * @param persoonHisVolledigView de persoon waarop de filtering moet worden toegepast
     * @param dienst                 de dienst van de afnemer
     */
    @Regels({ Regel.VR00081, Regel.VR00082, Regel.VR00083 })
    private void voerDienstbundelGroepenGroepenFilterExpressieUit(final PersoonHisVolledigView persoonHisVolledigView,
        final Dienst dienst) throws ExpressieExceptie
    {
        final List<HistorieEntiteit> totaleLijstVanHisElementenOpPersoonsLijst = persoonHisVolledigView.getTotaleLijstVanHisElementenOpPersoonsLijst();

        resetAlleDienstbundelGroepenNaarDefaultWaarde(persoonHisVolledigView, dienst);
        resetAlleVerantwoordingActies(totaleLijstVanHisElementenOpPersoonsLijst);

        final Expressie dienstbundelGroepenExpressieResultaat;
        // bouw een lijstexpressie op om materiele / formele en verantwoording attributen op te halen
        final Expressie dienstbundelGroepenExpressie =
            dienstFilterExpressiesService.geefExpressiesVoorHistorieEnVerantwoordingAttributen(dienst);
        try {
            dienstbundelGroepenExpressieResultaat =
                expressieService.evalueer(dienstbundelGroepenExpressie, persoonHisVolledigView);
        } catch (final ExpressieExceptie expressieExceptie) {
            throw new ExpressieExceptie(FOUT_BIJ_EVALUEREN_VAN_EXPRESSIE_VOOR_PERSOON_MET_ID
                + persoonHisVolledigView.getID() + DIENST_ID + dienst.getID(), expressieExceptie);
        }

        magGeleverdWordenVlaggenZetter.zetMagGeleverdWordenVlaggenOpWaarde(dienstbundelGroepenExpressieResultaat, true);

        verplichtLeverenVerantwoordingVoorAboHandelingen.voerRegelUit(totaleLijstVanHisElementenOpPersoonsLijst,
            persoonHisVolledigView.getAdministratieveHandelingen());
    }

    /**
     * Reset alle verantwoording acties. Deze functie zet alle magGeleverdWorden velden (vlaggetjes) op false.
     *
     * @param totaleLijstVanHisElementenOpPersoonsLijst De totale lijst van his elementen van persoon view.
     */
    @SuppressWarnings("unchecked")
    private void resetAlleVerantwoordingActies(final List<HistorieEntiteit> totaleLijstVanHisElementenOpPersoonsLijst) {
        for (final HistorieEntiteit historieEntiteit : totaleLijstVanHisElementenOpPersoonsLijst) {
            if (historieEntiteit instanceof MaterieelHistorisch) {
                final MaterieelHistorisch materieleHistorie = (MaterieelHistorisch) historieEntiteit;
                final MaterieelVerantwoordbaar<ActieModel> materieelVerantwoordbaar = (MaterieelVerantwoordbaar<ActieModel>) materieleHistorie;
                resetActieMagGeleverdWorden(materieelVerantwoordbaar.getVerantwoordingInhoud());
                resetActieMagGeleverdWorden(materieelVerantwoordbaar.getVerantwoordingAanpassingGeldigheid());
                resetActieMagGeleverdWorden(materieelVerantwoordbaar.getVerantwoordingVerval());
            } else {
                final FormeelHistorisch formeleHistorie = (FormeelHistorisch) historieEntiteit;
                final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar = (FormeelVerantwoordbaar<ActieModel>) formeleHistorie;
                resetActieMagGeleverdWorden(formeelVerantwoordbaar.getVerantwoordingInhoud());
                resetActieMagGeleverdWorden(formeelVerantwoordbaar.getVerantwoordingVerval());
            }
        }
    }

    /**
     * Reset het magGeleverdWordenVlaggetje van een actie.
     *
     * @param actie de actie
     */
    private void resetActieMagGeleverdWorden(final ActieModel actie) {
        if (actie != null) {
            actie.setMagGeleverdWorden(false);
        }
    }

    /**
     * Voegt predikaten toe om voorkomens te filteren.
     *
     * @param persoonHisVolledigView De persoon view.
     * @param dienst                 de dienst
     * @param isMutatieLevering      De boolean of het in kader van een mutatielevering is.
     */
    @Regels(Regel.R1328)
    private void voegPredikatenToeVoorGroepVoorkomenFiltering(final PersoonHisVolledigView persoonHisVolledigView,
        final Dienst dienst, final boolean isMutatieLevering)
    {
        // predikaat voor algemene groep autorisatie filtering
        final Set<ElementEnum> geautoriseerdeGroepen = dienst.getDienstbundel().geefGeautoriseerdeGroepen();
        final MagGroepTonenPredikaat magGroepTonenPredikaat = new MagGroepTonenPredikaat(geautoriseerdeGroepen);
        persoonHisVolledigView.voegPredikaatToe(magGroepTonenPredikaat);

        // predikaat voor filteren van bepaalde historische voorkomens binnen een groep
        final Set<DienstbundelGroep> dienstbundelGroepen = dienst.getDienstbundel().getDienstbundelGroepen();
        final MagHistorieTonenPredikaat predikaat = new MagHistorieTonenPredikaat(dienstbundelGroepen, isMutatieLevering);
        persoonHisVolledigView.voegPredikaatToe(predikaat);
        persoonHisVolledigView.voegPredikaatToe(new PreRelatieGegevensNietTonenPredikaat());
    }

    /**
     * Reset alle waarden naar de default waardes, zodat we altijd met een schone objecten beginnen.
     *
     * @param persoonHisVolledigView De persoon.
     * @param dienst                 De dienst
     */
    private void resetAlleDienstbundelGroepenNaarDefaultWaarde(final PersoonHisVolledigView persoonHisVolledigView,
        final Dienst dienst) throws ExpressieExceptie
    {
        final Expressie resetExpressie =
            dienstFilterExpressiesService.geefAllExpressiesVoorHistorieEnVerantwoordingAttributen();
        final Expressie resetExpressieResultaat;

        try {
            resetExpressieResultaat = expressieService.evalueer(resetExpressie, persoonHisVolledigView);
        } catch (final ExpressieExceptie expressieExceptie) {
            throw new ExpressieExceptie(FOUT_BIJ_EVALUEREN_VAN_EXPRESSIE_VOOR_PERSOON_MET_ID
                + persoonHisVolledigView.getID() + DIENST_ID + dienst.getID(), expressieExceptie);
        }

        magGeleverdWordenVlaggenZetter.zetMagGeleverdWordenVlaggenOpWaarde(resetExpressieResultaat, false);
    }

    /**
     * Reset alle waarden naar de default waardes, zodat we altijd met een schone objecten beginnen.
     *
     * @param attributenMap de map met key expressie naar attributen
     * @throws ExpressieExceptie expressie exceptie
     */
    private void resetAlleDienstbundelGroepenNaarDefaultWaarde(final Map<String, List<Attribuut>> attributenMap)
        throws ExpressieExceptie
    {
        final List<String> dienstbundelGroepenExpressie =
            dienstFilterExpressiesService.geefAllExpressiesVoorHistorieEnVerantwoordingAttributenLijst();
        for (final String expressieStr : dienstbundelGroepenExpressie) {
            final List<Attribuut> attributen = attributenMap.get(expressieStr);
            if (attributen != null) {
                for (final Attribuut attribuut : attributen) {
                    attribuut.setMagGeleverdWorden(false);
                }
            }
        }
    }

    /**
     * Logt wanneer persoonlijst een lege totale lijst van his elemeneten heeft.
     *
     * @param persoonHisVolledigView persoon his volledig view
     */
    private void logLegeTotaleLijstVanHisElemenetenOpPersoonslijstWarning(final PersoonHisVolledigView persoonHisVolledigView) {
        if (persoonHisVolledigView.getTotaleLijstVanHisElementenOpPersoonsLijst().isEmpty()) {
            LOGGER.warn("Het volledigbericht voor persoon met id: {} is leeg, maar wordt toch verstuurd.",
                persoonHisVolledigView.getID());
        }
    }
}
