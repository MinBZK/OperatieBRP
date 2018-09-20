/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.stappen.administratievehandeling;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.business.stappen.StappenContext;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.springframework.transaction.TransactionStatus;


/**
 * De context zoals deze gebruikt wordt in de stappen voor het verwerkingsproces van mutaties naar aanleiding van Administratieve Handelingen.
 */
public interface AdministratieveHandelingVerwerkingContext extends StappenContext {

    /**
     * Geeft de huidige administratieve handeling.
     *
     * @return de huidige administratieve handeling
     */
    AdministratieveHandelingModel getHuidigeAdministratieveHandeling();

    /**
     * Zet de huidige administratieve handeling.
     *
     * @param huidigeAdministratieveHandeling de huidige administratieve handeling
     */
    void setHuidigeAdministratieveHandeling(final AdministratieveHandelingModel huidigeAdministratieveHandeling);

    /**
     * Geeft de bijgehouden persoon ids.
     *
     * @return de bijgehouden persoon ids
     */
    List<Integer> getBijgehoudenPersoonIds();

    /**
     * Zet de bijgehouden persoon ids.
     *
     * @param bijgehoudenPersoonIds de bijgehouden persoon ids
     */
    void setBijgehoudenPersoonIds(final List<Integer> bijgehoudenPersoonIds);

    /**
     * Geeft de bijgehouden personen volledig.
     *
     * @return de bijgehouden personen volledig
     */
    List<PersoonHisVolledig> getBijgehoudenPersonenVolledig();

    /**
     * Zet de bijgehouden personen volledig.
     *
     * @param bijgehoudenPersonenVolledig de bijgehouden personen volledig
     */
    void setBijgehoudenPersonenVolledig(final List<PersoonHisVolledig> bijgehoudenPersonenVolledig);

    /**
     * Geeft de transactie status.
     *
     * @return de transactie status
     */
    TransactionStatus getTransactionStatus();

    /**
     * Zet de transactie status.
     *
     * @param transactionStatus the transactie status
     */
    void setTransactionStatus(final TransactionStatus transactionStatus);

    /**
     * Gets jms transactie status.
     *
     * @return the jms transactie status
     */
    TransactionStatus getJmsTransactionStatus();

    /**
     * Zet de jms transactie status.
     *
     * @param jmsTransactionStatus de jms transactie status
     */
    void setJmsTransactionStatus(final TransactionStatus jmsTransactionStatus);

    /**
     * Geeft de levering populatie map.
     *
     * @return de levering populatie map
     */
    Map<Leveringinformatie, Map<Integer, Populatie>> getLeveringPopulatieMap();

    /**
     * Zet de levering populatie map.
     *
     * @param leveringPopulatieMap de levering populatie map
     */
    void setLeveringPopulatieMap(final Map<Leveringinformatie, Map<Integer, Populatie>> leveringPopulatieMap);

    /**
     * Verwijdert de businesstransactie uit het object.
     */
    void clearBusinessTransactionStatus();

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
    Map<Integer, Map<Integer, List<Attribuut>>>  getPersoonOnderzoekenMap();

    /**
     * Zet de personen attributen mapping. key is persoon id. Geneste map is gegeven in onderzoek id -> attribuut
     *
     * @param personenAttributenMap de personen onderzoeken map
     */
    void setPersoonOnderzoekenMap(final Map<Integer, Map<Integer, List<Attribuut>>>  personenAttributenMap);

}
