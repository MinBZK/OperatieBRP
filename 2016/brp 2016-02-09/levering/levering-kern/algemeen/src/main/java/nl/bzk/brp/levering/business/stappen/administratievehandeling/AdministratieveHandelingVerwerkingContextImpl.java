/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.stappen.administratievehandeling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.springframework.transaction.TransactionStatus;


/**
 * De context zoals deze gebruikt wordt in de stappen voor het verwerkingsproces van mutaties naar aanleiding van Administratieve Handelingen.
 */
public class AdministratieveHandelingVerwerkingContextImpl implements AdministratieveHandelingVerwerkingContext {

    private AdministratieveHandelingModel huidigeAdministratieveHandeling;

    private List<Integer> bijgehoudenPersoonIds = new ArrayList<>();

    private List<PersoonHisVolledig> bijgehoudenPersonenVolledig;

    private TransactionStatus transactionStatus;

    private TransactionStatus jmsTransactionStatus;

    private Map<Leveringinformatie, Map<Integer, Populatie>> leveringPopulatieMap;

    private Map<Integer, Map<String, List<Attribuut>>> bijgehoudenPersonenAttributenMap;

    private Map<Integer, Map<Integer, List<Attribuut>>> persoonOnderzoekenMap;


    public final AdministratieveHandelingModel getHuidigeAdministratieveHandeling() {
        return huidigeAdministratieveHandeling;
    }

    public final Map<Integer, Map<String, List<Attribuut>>> getBijgehoudenPersonenAttributenMap() {
        return bijgehoudenPersonenAttributenMap;
    }

    @Override
    public Map<Integer, Map<Integer, List<Attribuut>>> getPersoonOnderzoekenMap() {
        return persoonOnderzoekenMap;
    }

    @Override
    public void setPersoonOnderzoekenMap(final Map<Integer, Map<Integer, List<Attribuut>>> persoonOnderzoekenMap) {
        this.persoonOnderzoekenMap = persoonOnderzoekenMap;
    }


    public final void setHuidigeAdministratieveHandeling(final AdministratieveHandelingModel huidigeAdministratieveHandeling) {
        this.huidigeAdministratieveHandeling = huidigeAdministratieveHandeling;
    }

    public final List<Integer> getBijgehoudenPersoonIds() {
        return bijgehoudenPersoonIds;
    }

    public final void setBijgehoudenPersoonIds(final List<Integer> bijgehoudenPersonenIds) {
        this.bijgehoudenPersoonIds = bijgehoudenPersonenIds;
    }

    public final List<PersoonHisVolledig> getBijgehoudenPersonenVolledig() {
        return bijgehoudenPersonenVolledig;
    }

    public final void setBijgehoudenPersonenVolledig(final List<PersoonHisVolledig> bijgehoudenPersonenVolledig) {
        this.bijgehoudenPersonenVolledig = bijgehoudenPersonenVolledig;
    }

    public final void setBijgehoudenPersonenAttributenMap(final Map<Integer, Map<String, List<Attribuut>>> bijgehoudenPersonenAttributenMap) {
        this.bijgehoudenPersonenAttributenMap = bijgehoudenPersonenAttributenMap;
    }

    public final TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public final void setTransactionStatus(final TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public final TransactionStatus getJmsTransactionStatus() {
        return jmsTransactionStatus;
    }

    public final void setJmsTransactionStatus(final TransactionStatus jmsTransactionStatus) {
        this.jmsTransactionStatus = jmsTransactionStatus;
    }

    @Override
    public final Long getReferentieId() {
        if (null == huidigeAdministratieveHandeling) {
            return null;
        }
        return huidigeAdministratieveHandeling.getID();
    }

    @Override
    public final Long getResultaatId() {
        return null;
    }

    public final Map<Leveringinformatie, Map<Integer, Populatie>> getLeveringPopulatieMap() {
        return leveringPopulatieMap;
    }

    public final void setLeveringPopulatieMap(final Map<Leveringinformatie, Map<Integer, Populatie>> leveringPopulatieMap) {
        this.leveringPopulatieMap = leveringPopulatieMap;
    }

    /**
     * Verwijdert de businesstransactie uit het object.
     */
    public final void clearBusinessTransactionStatus() {
        if (transactionStatus != null && !transactionStatus.isCompleted()) {
            throw new IllegalStateException(
                "De businesstransactie mag niet ge-cleared worden als hij niet completed is");
        }
        transactionStatus = null;

        if (jmsTransactionStatus != null && !jmsTransactionStatus.isCompleted()) {
            throw new IllegalStateException(
                "De jms transactie mag niet ge-cleared worden als hij niet completed is");
        }
        jmsTransactionStatus = null;
    }
}
