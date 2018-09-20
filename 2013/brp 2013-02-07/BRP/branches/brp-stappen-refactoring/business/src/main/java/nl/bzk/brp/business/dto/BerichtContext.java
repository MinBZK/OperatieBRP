/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.bzk.brp.business.stappen.StappenContext;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.util.AttribuutTypeUtil;
import org.springframework.transaction.TransactionStatus;


/**
 * De context rond een BRP bericht. Deze context bevat additionele (niet bericht-type specifieke) informatie zoals
 * afzender, bericht id, authenticatiemiddel id etc.
 */
public class BerichtContext implements StappenContext {

    private final BerichtenIds  berichtenIds;
    private TransactionStatus   transactionStatus;
    private final Date          tijdstipVerwerking;
    private final Partij        partij;
    private final String        berichtReferentieNummer;

    // private final Partij partij;
    // private final String berichtReferentieNummer;
    // /** list van bsn van de hoofd personen en gerelateerde personen die door bericht zijn aangepast. */
    private final List<Persoon> hoofdPersonen = new ArrayList<Persoon>();
    private final List<Persoon> bijPersonen   = new ArrayList<Persoon>();

    /**
     * Constructor die de basis van de bericht context direct initialiseert.
     *
     * @param berichtenIds de ids van inkomende en daaraan gerelateerd uitgaande bericht.
     * @param berichtReferentieNummer Referentie nummer uit de stuurgegevens van het binnengekomen bericht.
     */
    public BerichtContext(final BerichtenIds berichtenIds, final Partij partij, final String berichtReferentieNummer) {
        if (berichtenIds == null  /* || partij == null */) {
            throw new IllegalArgumentException("Opgegeven waardes mogen niet null zijn.");
        }

        this.berichtenIds = berichtenIds;
        this.partij = partij;
        this.berichtReferentieNummer = berichtReferentieNummer;
        tijdstipVerwerking = new Date();
    }

    public Partij getPartij() {
        return partij;
    }

    public String getBerichtReferentieNummer() {
        return berichtReferentieNummer;
    }

    /**
     * Retourneert het id van het inkomende bericht.
     *
     * @return het id van het inkomende bericht.
     */
    public long getIngaandBerichtId() {
        return berichtenIds.getIngaandBerichtId();
    }

    /**
     * Retourneert het id van het uitgaande bericht.
     *
     * @return het id van het uitgaande bericht.
     */
    public long getUitgaandBerichtId() {
        return berichtenIds.getUitgaandBerichtId();
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(final TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    /** Verwijdert de businesstransactie uit het object. */
    public void clearBusinessTransactionStatus() {
        if (transactionStatus != null && !transactionStatus.isCompleted()) {
            throw new IllegalStateException(
                    "De businesstransactie mag niet ge-cleared worden als hij niet completed is");
        }
        transactionStatus = null;
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

    public List<Persoon> getHoofdPersonen() {
        return hoofdPersonen;
    }

    public List<Persoon> getBijPersonen() {
        return bijPersonen;
    }

    /**
     * Voeg de hoofdpersoon bsn toe aan de lijst van hoofdpersonen.
     *
     * @param persoon de persoon
     */
    public void voegHoofdPersoonToe(final Persoon persoon) {
        if (AttribuutTypeUtil.isNotBlank(persoon.getIdentificatienummers().getBurgerservicenummer())) {
            for (Persoon per : hoofdPersonen) {
                if (per.getIdentificatienummers().getBurgerservicenummer()
                        .equals(persoon.getIdentificatienummers().getBurgerservicenummer()))
                {
                    return;
                }
            }
            hoofdPersonen.add(persoon);
        }
    }

    /**
     * Voeg de bsn van gerelateerde personen toe aan de lijst van bijpersonen.
     *
     * @param persoon de persoon
     */
    public void voegBijPersoonToe(final Persoon persoon) {
        if (AttribuutTypeUtil.isNotBlank(persoon.getIdentificatienummers().getBurgerservicenummer())) {
            for (Persoon per : bijPersonen) {
                if (per.getIdentificatienummers().getBurgerservicenummer()
                        .equals(persoon.getIdentificatienummers().getBurgerservicenummer()))
                {
                    return;
                }
            }
            bijPersonen.add(persoon);
        }
    }

}
