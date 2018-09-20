/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.service;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.levering.VolledigBericht;


/**
 * Interface voor de context die gebruikt wordt in de afnemerindicaties webservice.
 */
public interface OnderhoudAfnemerindicatiesBerichtContext extends BerichtContext {

    /**
     * Retourneert het vul bericht.
     *
     * @return het vul bericht
     */
    VolledigBericht getVolledigBericht();

    /**
     * Plaatst het vul bericht op de context.
     *
     * @param volledigBericht het vul bericht
     */
    void setVolledigBericht(VolledigBericht volledigBericht);

    /**
     * Retourneert de levering autorisatie.
     *
     * @return de levering autorisatie
     */
    Leveringinformatie getLeveringinformatie();

    /**
     * Plaatst de levering autorisatie op de context.
     *
     * @param leveringAutorisatie de levering autorisatie
     */
    void setLeveringinformatie(Leveringinformatie leveringAutorisatie);

    /**
     * Retourneert de persoon his volledig.
     *
     * @return de persoon his volledig
     */
    PersoonHisVolledig getPersoonHisVolledig();

    /**
     * Plaatst de persoon his volledig op de context.
     *
     * @param persoonHisVolledig de persoon his volledig
     */
    void setPersoonHisVolledig(PersoonHisVolledig persoonHisVolledig);

    /**
     * Retourneert het xml bericht.
     *
     * @return het xml bericht
     */
    String getXmlBericht();

    /**
     * Plaatst het xml bericht op de context.
     *
     * @param xmlBericht het xml bericht
     */
    void setXmlBericht(String xmlBericht);

    /**
     * Retourneert de leveringsautorisatie.
     *
     * @return de leveringsautorisatie
     */
    Leveringsautorisatie getLeveringautorisatie();

    /**
     * Plaatst de zendende partij op de context. De is de partij die het verzoek inschiet.
     *
     * @param zendendePartij de zendende partij
     */
    void setZendendePartij(Partij zendendePartij);

    /**
     * Retourneert de zendende partij. De is de partij die het verzoek inschiet.
     *
     * @return de zendende partij
     */
    Partij getZendendePartij();

    /**
     * Plaatst de datum aanvang materiele periode op de context.
     *
     * @param datumAanvangMaterielePeriode the datum materieel selectie
     */
    void setDatumAanvangMaterielePeriode(DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode);

    /**
     * Retourneert de datum aanvang materiele periode.
     *
     * @return de datum materieel selectie
     */
    DatumEvtDeelsOnbekendAttribuut getDatumAanvangMaterielePeriode();

    /**
     * Zet de persoon attributen map.
     *
     * @param persoonAttributenMap de persoon attributen mapping
     */
    void setBijgehoudenPersonenAttributenMap(final Map<Integer, Map<String, List<Attribuut>>> persoonAttributenMap);

    /**
     * Geeft de persoon attributen map.
     *
     * @return persoonAtrributenMap
     */
    Map<Integer, Map<String, List<Attribuut>>> getBijgehoudenPersonenAttributenMap();

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
