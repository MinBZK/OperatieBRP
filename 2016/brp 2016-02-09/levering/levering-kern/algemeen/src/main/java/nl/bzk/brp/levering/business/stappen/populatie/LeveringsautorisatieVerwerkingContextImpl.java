/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.stappen.populatie;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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
public class LeveringsautorisatieVerwerkingContextImpl implements LeveringsautorisatieVerwerkingContext {


    private       Map<Integer, Map<String, List<Attribuut>>> persoonAttributenMap;
    /**
     * De administratieve handeling die wordt verwerkt.
     */
    private final AdministratieveHandelingModel              administratieveHandeling;

    /**
     *
     */

    private final List<PersoonHisVolledig> bijgehoudenPersonenVolledig;

    /**
     * Lijst van uitgaande berichten in string vorm (bv. XML of LO3).
     */
    private List<String> uitgaandePlatteTekstBerichten;

    /**
     * Lijst van berichten die geleverd gaan worden in deze context.
     */
    private List<SynchronisatieBericht> leveringBerichten;

    /**
     * View op bijgehoudenPersonen betrokken in de te verwerken administratieve handeling.
     */
    private List<PersoonHisVolledigView> bijgehoudenPersoonViews;

    /**
     *
     */
    private Map<Integer, Populatie> teLeverenPersoonIds = Collections.emptyMap();

    /**
     * Lijst van attributen die geleverd mogen worden in deze context.
     */
    private List<Attribuut> attributenDieGeleverdMogenWorden;

    private Map<Integer, Map<Integer, List<Attribuut>>> persoonOnderzoekenMap;



    /**
     * Instantieert een nieuwe leveringsautorisatie verwerking context.
     *
     * @param administratieveHandeling administratieve handeling
     * @param bijgehoudenPersonen      lijst met bijgehouden personen
     * @param populatieMap             populatie map
     * @param persoonAttributenMap     persoonAttributenMap zodat er maar eenmalig de attributen mapping hoeft worden aangelegd
     * @param persoonOnderzoekenMap    persoon onderzoeken map om snel van onderzoeken naar de gegevens in onderzoek te gaan.
     */
    public LeveringsautorisatieVerwerkingContextImpl(final AdministratieveHandelingModel administratieveHandeling,
        final List<PersoonHisVolledig> bijgehoudenPersonen,
        final Map<Integer, Populatie> populatieMap,
        final Map<Integer, Map<String, List<Attribuut>>> persoonAttributenMap,
        final Map<Integer, Map<Integer, List<Attribuut>>> persoonOnderzoekenMap)
    {
        this.administratieveHandeling = administratieveHandeling;
        this.bijgehoudenPersonenVolledig = bijgehoudenPersonen;
        this.teLeverenPersoonIds = populatieMap;
        this.persoonAttributenMap = persoonAttributenMap;
        this.persoonOnderzoekenMap = persoonOnderzoekenMap;
    }

    @Override
    public final Long getReferentieId() {
        if (administratieveHandeling != null) {
            return administratieveHandeling.getID();
        }

        return null;
    }

    @Override
    public final Long getResultaatId() {
        return null;
    }

    @Override
    public final AdministratieveHandelingModel getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    @Override
    public final List<PersoonHisVolledig> getBijgehoudenPersonenVolledig() {
        return bijgehoudenPersonenVolledig;
    }

    @Override
    public final Map<Integer, Populatie> getTeLeverenPersoonIds() {
        return teLeverenPersoonIds;
    }

    @Override
    public final List<String> getUitgaandePlatteTekstBerichten() {
        return uitgaandePlatteTekstBerichten;
    }

    @Override
    public final void setUitgaandePlatteTekstBerichten(final List<String> uitgaandePlatteTekstBerichten) {
        this.uitgaandePlatteTekstBerichten = uitgaandePlatteTekstBerichten;
    }

    @Override
    public final Map<Integer, Map<String, List<Attribuut>>> getPersoonAttributenMap() {
        return persoonAttributenMap;
    }

    @Override
    public final void setPersoonAttributenMap(final Map<Integer, Map<String, List<Attribuut>>> personenAttributenMap) {
        this.persoonAttributenMap = personenAttributenMap;
    }

    @Override
    public final List<SynchronisatieBericht> getLeveringBerichten() {
        return leveringBerichten;
    }

    @Override
    public final void setLeveringBerichten(final List<SynchronisatieBericht> leveringBerichten) {
        this.leveringBerichten = leveringBerichten;
    }

    @Override
    public final List<PersoonHisVolledigView> getBijgehoudenPersoonViews() {
        return bijgehoudenPersoonViews;
    }

    @Override
    public final void setBijgehoudenPersoonViews(final List<PersoonHisVolledigView> bijgehoudenPersonenViews) {
        this.bijgehoudenPersoonViews = bijgehoudenPersonenViews;
    }

    @Override
    public final List<Attribuut> getAttributenDieGeleverdMogenWorden() {
        return attributenDieGeleverdMogenWorden;
    }

    @Override
    public final void setAttributenDieGeleverdMogenWorden(final List<Attribuut> attributenDieGeleverdMogenWorden) {
        this.attributenDieGeleverdMogenWorden = attributenDieGeleverdMogenWorden;
    }

    @Override
    public Map<Integer, Map<Integer, List<Attribuut>>> getPersoonOnderzoekenMap() {
        return persoonOnderzoekenMap;
    }

    @Override
    public void setPersoonOnderzoekenMap(final Map<Integer, Map<Integer, List<Attribuut>>> persoonOnderzoekenMap) {
        this.persoonOnderzoekenMap = persoonOnderzoekenMap;
    }
}
