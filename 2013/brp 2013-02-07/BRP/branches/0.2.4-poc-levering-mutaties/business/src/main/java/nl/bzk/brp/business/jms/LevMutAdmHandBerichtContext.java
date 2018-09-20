/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.jms;

import java.util.List;
import java.util.Map;

import nl.bzk.brp.business.levering.LEVLeveringBijgehoudenPersoonLv;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import org.springframework.transaction.TransactionStatus;

public class LevMutAdmHandBerichtContext {

    private final Long administratieveHandelingId;

    /**
     * De actie waar deze context de verwerking van voorziet
     */

    private ActieModel actieModel;

    private TransactionStatus transactionStatus;

    /**
     * de partij ids welke betrokken zijn als potentieele ontvanger bij deze verweking
     */

    private List<Short> partijIds;

    private List<LEVLeveringBijgehoudenPersoonLv> maxBerichten;

    private List<String> betrokkenBsns;

    private boolean fout = false;
    private Map<Short, LEVLeveringBijgehoudenPersoonLv> uitBerichten;

    public ActieModel getActieModel() {
        return actieModel;
    }

    public void setActieModel(final ActieModel actieModel) {
        this.actieModel = actieModel;
    }

    public LevMutAdmHandBerichtContext(final Long administratieveHandelingId) {
        this.administratieveHandelingId = administratieveHandelingId;
    }

    public Long getAdministratieveHandelingId() {
        return administratieveHandelingId;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(final TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    /**
     * Verwijdert de businesstransactie uit het object.
     */
    public void clearBusinessTransactionStatus() {
        if (transactionStatus != null && !transactionStatus.isCompleted()) {
            throw new IllegalStateException(
                    "De businesstransactie mag niet ge-cleared worden als hij niet completed is");
        }
        transactionStatus = null;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ":administratieveHandelingId=" + getAdministratieveHandelingId();
    }

    public List<Short> getPartijIds() {
        return partijIds;
    }

    public void setPartijIds(final List<Short> partijIds) {
        this.partijIds = partijIds;
    }

    /**
     * geeft aan dat een technische(!) fout is in deze context, er wordt bijv. niet meer gecommit
     */
    public void fout() {
        fout = true;
    }

    public boolean isFout() {
        return fout;
    }

    public void setUitBerichten(final Map<Short, LEVLeveringBijgehoudenPersoonLv> uitBerichten) {
        this.uitBerichten = uitBerichten;
    }

    public Map<Short, LEVLeveringBijgehoudenPersoonLv> getUitBerichten() {
        return uitBerichten;
    }

    public List<String> getBetrokkenBsns() {
		return betrokkenBsns;
	}

	public void setBetrokkenBsns(final List<String> betrokkenBsns) {
		this.betrokkenBsns = betrokkenBsns;
	}

    public List<LEVLeveringBijgehoudenPersoonLv> getMaxBerichten() {
        return maxBerichten;
    }

    public void setMaxBerichten(final List<LEVLeveringBijgehoudenPersoonLv> maxBerichten) {
        this.maxBerichten = maxBerichten;
    }
}
