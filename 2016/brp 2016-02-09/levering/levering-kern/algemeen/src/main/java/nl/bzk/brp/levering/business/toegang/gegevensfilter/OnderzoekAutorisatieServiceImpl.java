/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.gegevensfilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.brp.levering.algemeen.service.StamTabelService;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElement;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.HeeftMagLeverenOnderzoekPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.GegevenInOnderzoekHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import org.springframework.stereotype.Service;

/**
 * Onderzoek autorisatie service die autorisatie toepast op gegevens in onderzoek.
 * <p/>
 * Eerst autorisatie, dan predikaat
 */
@Service
public class OnderzoekAutorisatieServiceImpl implements OnderzoekAutorisatieService {

    @Inject
    private StamTabelService stamTabelService;


    @Override
    public final List<Attribuut> autoriseerOnderzoeken(final PersoonHisVolledigView persoonHisVolledigView,
        final Map<Integer, Map<Integer, List<Attribuut>>> persoonOnderzoekenMap)
    {

        final List<Attribuut> geraakteAttributen = new ArrayList<>();
        for (final PersoonOnderzoekHisVolledig persoonOnderzoekHisVolledig : persoonHisVolledigView.getOnderzoeken()) {
            final Map<Integer, List<Attribuut>> attribuutMap = persoonOnderzoekenMap.get(persoonHisVolledigView.getID());
            final OnderzoekHisVolledig onderzoek = persoonOnderzoekHisVolledig.getOnderzoek();

            // Er dient een onderzoekhistorie record getoond te worden voordat een gegeven in onderzoek getoond mag worden.
            // Als dit het geval is, dan worden vlaggen overgenomen van het gegeven op de persoonslijst dat in onderzoek staat.
            if (attribuutMap != null && !onderzoek.getOnderzoekHistorie().isLeeg()) {
                final List<Attribuut> geautoriseerdeAttributen = autoriseerOnderzoek(onderzoek, persoonHisVolledigView, attribuutMap);
                geraakteAttributen.addAll(geautoriseerdeAttributen);
            }
        }
        //we zouden predikaat alleen kunnen toevoegen als geraakte attr leeg?
        persoonHisVolledigView.voegPredikaatToeZonderBetrokkenhedenReset(new HeeftMagLeverenOnderzoekPredikaat());
        return geraakteAttributen;
    }

    /**
     * Autoriseer een onderzoek op basis van historische voorkomens in de persoonlijst van het gegeven dat in onderzoek staat.
     *
     * @param onderzoek              onderzoek
     * @param persoonHisVolledigView persoonHisVolledigView
     * @param attribuutMap           attribuutmap
     * @return lijst met geraakte attributen
     */
    private List<Attribuut> autoriseerOnderzoek(final OnderzoekHisVolledig onderzoek, final PersoonHisVolledigView persoonHisVolledigView,
        final Map<Integer, List<Attribuut>> attribuutMap)
    {
        final List<Attribuut> geraakteAttributen = new ArrayList<>();
        for (final GegevenInOnderzoekHisVolledig gegevenInOnderzoekHisVolledig : onderzoek.getGegevensInOnderzoek()) {
            final List<Attribuut> attributen = attribuutMap.get(gegevenInOnderzoekHisVolledig.getID());
            if (attributen != null) {
                for (final Attribuut attribuut : attributen) {
                    final boolean staatGegevenInPersoonslijst =
                        staatGegevenInPersoonslijst(gegevenInOnderzoekHisVolledig, persoonHisVolledigView, attribuut);
                    if (staatGegevenInPersoonslijst) {
                        final List<Attribuut> geautoriseerdeAttributen = autoriseerGegevenInOnderzoek(gegevenInOnderzoekHisVolledig);
                        geraakteAttributen.addAll(geautoriseerdeAttributen);

                        // Wanneer een attribuut van de lijst van attributen voorkomt in de persoonslijst, dan kan de conclusie worden getrokken dat het
                        // gegeven getoond wordt (attribuut, groep of objecttype) en dus zal dan ook de verwijzing vanuit gegevenInOnderzoek getoond
                        // worden.
                        break;
                    }
                }
            }
        }
        return geraakteAttributen;
    }

    /**
     * Controleert of gegeven in persoonslijst voorkomt.
     *
     * @param gegevenInOnderzoekHisVolledig gegeven in onderzoek his volledig
     * @param persoonHisVolledigView        persoon his volledig view
     * @param attribuut                     attribuut
     * @return true als gegeven in persoonslijst voorkomt, anders false
     */
    private boolean staatGegevenInPersoonslijst(final GegevenInOnderzoekHisVolledig gegevenInOnderzoekHisVolledig,
        final PersoonHisVolledigView persoonHisVolledigView, final Attribuut attribuut)
    {
        if (gegevenInOnderzoekHisVolledig.getObjectSleutelGegeven() == null && gegevenInOnderzoekHisVolledig.getVoorkomenSleutelGegeven() == null) {
            //zou al gefilterd moeten zijn.
            return false;
        }
        final Element elementUitStamtabel = stamTabelService.geefElementById(gegevenInOnderzoekHisVolledig.getElement().getWaarde().getID());
        if (elementUitStamtabel.getSoort() == SoortElement.ATTRIBUUT
            && elementUitStamtabel.getGroep() != null
            && "Identiteit".equals(elementUitStamtabel.getGroep().getElementNaam().getWaarde()))
        {
            // Identificerende groep attributen
            return true;
        }
        for (final HistorieEntiteit historieEntiteit : persoonHisVolledigView.getTotaleLijstVanHisElementenOpPersoonsLijst()) {
            if (historieEntiteit instanceof ModelIdentificeerbaar) {
                final ModelIdentificeerbaar<?> modelIdentificeerbaar = (ModelIdentificeerbaar<?>) historieEntiteit;
                if (attribuut.getGroep() == modelIdentificeerbaar
                    && attribuutZitInGroep(attribuut, modelIdentificeerbaar))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Attribuut zit in groep en mag geleverd worden.
     *
     * @param attribuut             attribuut
     * @param modelIdentificeerbaar model identificeerbaar
     * @return true als attribuut in groep zit en geleverd mag worden, anders false
     */
    private boolean attribuutZitInGroep(final Attribuut attribuut, final ModelIdentificeerbaar<?> modelIdentificeerbaar) {
        final Groep groep = (Groep) modelIdentificeerbaar;
        for (final Attribuut attribuutVanGroep : groep.getAttributen()) {
            if (attribuut.equals(attribuutVanGroep) && attribuutVanGroep.isMagGeleverdWorden()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Autoriseert het gegeven in onderzoek op basis van de autorisatie van het gegeven dat in onderzoek staat.
     *
     * @param gegevenInOnderzoekHisVolledig gegeven in onderzoek his volledig
     * @return lijst met geraakte attributen
     */
    private List<Attribuut> autoriseerGegevenInOnderzoek(final GegevenInOnderzoekHisVolledig gegevenInOnderzoekHisVolledig) {
        final List<Attribuut> geraakteAttributen = new ArrayList<>();
        final GegevenInOnderzoekHisVolledigView gegevenInOnderzoekHisVolledigView = (GegevenInOnderzoekHisVolledigView) gegevenInOnderzoekHisVolledig;

        // Momenteel zijn er geen autorisatie regels van toepassing op leveren van attributen van gegevensInOnderzoek. Als dit punt bereikt wordt,
        // zullen alle attributen getoond worden.
        final boolean magGeleverdWorden = true;

        gegevenInOnderzoekHisVolledigView.getElement().setMagGeleverdWorden(magGeleverdWorden);
        geraakteAttributen.add(gegevenInOnderzoekHisVolledigView.getElement());

        if (gegevenInOnderzoekHisVolledigView.getObjectSleutelGegeven() != null) {
            gegevenInOnderzoekHisVolledigView.getObjectSleutelGegeven().setMagGeleverdWorden(magGeleverdWorden);
            geraakteAttributen.add(gegevenInOnderzoekHisVolledigView.getObjectSleutelGegeven());
        }

        if (gegevenInOnderzoekHisVolledigView.getVoorkomenSleutelGegeven() != null) {
            gegevenInOnderzoekHisVolledigView.getVoorkomenSleutelGegeven().setMagGeleverdWorden(magGeleverdWorden);
            geraakteAttributen.add(gegevenInOnderzoekHisVolledigView.getVoorkomenSleutelGegeven());
        }

        return geraakteAttributen;
    }
}
