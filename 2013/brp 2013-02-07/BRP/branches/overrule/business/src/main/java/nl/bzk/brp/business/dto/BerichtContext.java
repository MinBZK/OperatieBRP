/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto;

import java.util.Date;

import nl.bzk.brp.model.gedeeld.Partij;
import org.springframework.transaction.TransactionStatus;

/**
 * De context rond een BRP bericht. Deze context bevat additionele (niet bericht-type specifieke) informatie zoals
 * afzender, bericht id, authenticatiemiddel id etc.
 */
public class BerichtContext {

    private final BerichtenIds berichtenIds;
    private final int authenticatieMiddelId;
    private final Partij partij;
    private TransactionStatus transactionStatus;
    private String berichtReferentieNummer;
    private final Date tijdstipVerwerking;

    /**
     * Constructor die de basis van de bericht context direct initialiseert.
     * @param berichtenIds de ids van inkomende en daaraan gerelateerd uitgaande bericht.
     * @param authenticatieMiddelId de id van het authenticatiemiddel waarmee de bevrager zich heeft geauthenticeerd.
     * @param partij de bevragende partij.
     * @param berichtReferentieNummer Referentie nummer uit de stuurgegevens van het binnengekomen bericht.
     */
    public BerichtContext(final BerichtenIds berichtenIds, final Integer authenticatieMiddelId,
                          final Partij partij, final String berichtReferentieNummer)
    {
        if (berichtenIds == null || authenticatieMiddelId == null || partij == null) {
            throw new IllegalArgumentException("Opgegeven waardes mogen niet null zijn.");
        }

        this.berichtenIds = berichtenIds;
        this.authenticatieMiddelId = authenticatieMiddelId;
        this.partij = partij;
        this.berichtReferentieNummer = berichtReferentieNummer;
        this.tijdstipVerwerking = new Date();
    }

    /**
     * Retourneert het id van het inkomende bericht.
     * @return het id van het inkomende bericht.
     */
    public long getIngaandBerichtId() {
        return berichtenIds.getIngaandBerichtId();
    }

    /**
     * Retourneert het id van het uitgaande bericht.
     * @return het id van het uitgaande bericht.
     */
    public long getUitgaandBerichtId() {
        return berichtenIds.getUitgaandBerichtId();
    }

    /**
     * Retourneert het id van het authenticatiemiddel waarmee de uitvoerende partij zich heeft geauthenticeerd.
     * @return het id van het authenticatiemiddel waarmee de uitvoerende partij zich heeft geauthenticeerd.
     */
    public Integer getAuthenticatieMiddelId() {
        return authenticatieMiddelId;
    }

    /**
     * Retourneert de partij die het bericht heeft verstuurd.
     * @return de partij die het bericht heeft verstuurd.
     */
    public Partij getPartij() {
        return partij;
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

    public String getBerichtReferentieNummer() {
        return berichtReferentieNummer;
    }

    /**
     * Retourneert het tijdstip van verwerking; dit tijdstip wordt gezet zodra de context wordt geinitialiseerd en
     * heeft voor bijhouding en bevraging een verschillende betekenis.
     *
     * @return het tijdstip van verwerking; de betekenis hiervan verschilt tussen bevraging en bijhouding.
     */
    public Date getTijdstipVerwerking() {
        return (Date) tijdstipVerwerking.clone();
    }

}
