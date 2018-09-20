/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.stappen.populatie;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.business.stappen.StappenContext;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;


/**
 * De context zoals deze gebruikt wordt in de stappen voor het verwerkingsproces van mutaties naar aanleiding van Administratieve Handelingen en specifiek
 * bij de stappen voor afhaneling van de geinteresseerde afnemers.
 */
public interface LeveringsautorisatieVerwerkingContext extends StappenContext {

    /**
     * Geeft de administratieve handeling.
     *
     * @return de administratieve handeling
     */
    AdministratieveHandelingModel getAdministratieveHandeling();

    /**
     * Geeft de bijgehouden personen volledig.
     *
     * @return de bijgehouden personen volledig
     */
    List<PersoonHisVolledig> getBijgehoudenPersonenVolledig();

    /**
     * Geeft de te leveren persoon ids.
     *
     * @return de te leveren persoon ids
     */
    Map<Integer, Populatie> getTeLeverenPersoonIds();

    /**
     * Geeft de uitgaande platte tekst berichten.
     *
     * @return de uitgaande platte tekst berichten
     */
    List<String> getUitgaandePlatteTekstBerichten();

    /**
     * Zet de uitgaande platte tekst berichten.
     *
     * @param uitgaandePlatteTekstBerichten de uitgaande platte tekst berichten
     */
    void setUitgaandePlatteTekstBerichten(final List<String> uitgaandePlatteTekstBerichten);

    /**
     * @return de persoon attributen mapping. key is persoon id. Geneste map is expressie string -> attribuut
     */
    Map<Integer, Map<String, List<Attribuut>>> getPersoonAttributenMap();

    /**
     * Zet de personen attributen mapping. key is persoon id. Geneste map is expressie string -> attribuut
     *
     * @param personenAttributenMap de personen attributen map
     */
    void setPersoonAttributenMap(final Map<Integer, Map<String, List<Attribuut>>> personenAttributenMap);

    /**
     * Geeft de levering berichten.
     *
     * @return de levering berichten
     */
    List<SynchronisatieBericht> getLeveringBerichten();

    /**
     * Zet de levering berichten.
     *
     * @param leveringBerichten de levering berichten
     */
    void setLeveringBerichten(final List<SynchronisatieBericht> leveringBerichten);

    /**
     * Geeft de betrokken personen views.
     *
     * @return de betrokken personen views
     */
    List<PersoonHisVolledigView> getBijgehoudenPersoonViews();

    /**
     * Zet de bijgehouden persoon views.
     *
     * @param bijgehoudenPersoonViews de bijgehouden persoon views
     */
    void setBijgehoudenPersoonViews(final List<PersoonHisVolledigView> bijgehoudenPersoonViews);

    /**
     * Geeft de attributen die geleverd mogen worden.
     *
     * @return the attributen die geleverd mogen worden
     */
    List<Attribuut> getAttributenDieGeleverdMogenWorden();

    /**
     * Zet de attributen die geleverd mogen worden.
     *
     * @param attributenDieGeleverdMogenWorden de attributen die geleverd mogen worden
     */
    void setAttributenDieGeleverdMogenWorden(final List<Attribuut> attributenDieGeleverdMogenWorden);

    /**
     * Geeft de persoon onderzoeken map.
     *
     * @return de persoon onderzoeken mapping. key is persoon id. Geneste map is gegeven in onderzoek id -> attribuut
     */
    Map<Integer, Map<Integer, List<Attribuut>>> getPersoonOnderzoekenMap();

    /**
     * Zet de personen attributen mapping. key is persoon id. Geneste map is gegeven in onderzoek id -> attribuut
     *
     * @param persoonOnderzoekenMap de personen onderzoeken map
     */
    void setPersoonOnderzoekenMap(final Map<Integer, Map<Integer, List<Attribuut>>> persoonOnderzoekenMap);
}
