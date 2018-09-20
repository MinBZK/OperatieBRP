/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.stappen;

import java.util.Date;
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.locking.LockingElement;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import org.springframework.transaction.TransactionStatus;

/**
 * Abstracte klasse voor de context rond een Bijhouding of Bevragings bericht. Deze context bevat additionele
 * (niet bericht-type specifieke) informatie zoals afzender, bericht id, authenticatiemiddel id etc.
 */
public abstract class AbstractBerichtContext implements BerichtContext {

    private final BerichtenIds berichtenIds;

    private TransactionStatus transactionStatus;
    private final Date tijdstipVerwerking;
    private final PartijAttribuut partij;
    private final String berichtReferentieNummer;

    private String xmlBericht;

    private CommunicatieIdMap identificeerbareObjecten;


    /**
     * Constructor die de basis van de bericht context direct initialiseert.
     *
     * @param berichtIds          de ids van inkomende en daaraan gerelateerd uitgaande bericht.
     * @param partij              de partij die de bericht verwerking heeft aangeroepen.
     * @param berichtReferentieNr Referentie nummer uit de stuurgegevens van het binnengekomen bericht.
     * @param identificeerbareObj map van identificeerbare objecten die zijn gevonden in het bericht.
     */
    public AbstractBerichtContext(final BerichtenIds berichtIds, final PartijAttribuut partij,
                                  final String berichtReferentieNr, final CommunicatieIdMap identificeerbareObj)
    {

        if (berichtIds == null) {
            throw new IllegalArgumentException("Opgegeven waardes mogen niet null zijn.");
        }

        this.partij = partij;
        this.berichtReferentieNummer = berichtReferentieNr;
        this.tijdstipVerwerking = new Date();
        this.berichtenIds = berichtIds;
        this.identificeerbareObjecten = identificeerbareObj;
    }

    /**
     * De partij die de vraag of bijhouding heeft ingeschoten.
     *
     * @return de partij
     */
    @Override
    public PartijAttribuut getPartij() {
        return partij;
    }

    /**
     * Referentienummer in het inkomende bericht die gebruikt kan worden in het uitgaande bericht.
     *
     * @return het referentienummer
     */
    @Override
    public String getBerichtReferentieNummer() {
        return berichtReferentieNummer;
    }

    /**
     * Geef de transactie status voor de stappen terug.
     *
     * @return de status
     */
    @Override
    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    @Override
    public void setTransactionStatus(final TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    /**
     * Verwijdert de businesstransactie uit het object.
     */
    @Override
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
    @Override
    public Date getTijdstipVerwerking() {
        return (Date) tijdstipVerwerking.clone();
    }


    /**
     * Haalt identificeerbare objecten op.
     *
     * @return identificeerbare objecten
     */
    @Override
    public CommunicatieIdMap getIdentificeerbareObjecten() {
        return identificeerbareObjecten;
    }

    /**
     * Slaat de identificeerbare objecten op.
     *
     * @param identificeerbareObjecten identificeerbare objecten
     */
    @Override
    public void setIdentificeerbareObjecten(final CommunicatieIdMap identificeerbareObjecten) {
        this.identificeerbareObjecten = identificeerbareObjecten;
    }

    /**
     * Retourneert het id van het inkomende bericht.
     *
     * @return het id van het inkomende bericht.
     */
    @Override
    public long getIngaandBerichtId() {
        return berichtenIds.getIngaandBerichtId();
    }

    /**
     * Retourneert het id van het uitgaande bericht.
     *
     * @return het id van het uitgaande bericht.
     */
    @Override
    public long getUitgaandBerichtId() {
        return berichtenIds.getUitgaandBerichtId();
    }

    @Override
    public Long getReferentieId() {
        //Default implementatie van het referentieId.
        //Geef het ingaande berichtId terug als referentie naar het bericht dat deze handeling startte.
        return getIngaandBerichtId();
    }

    @Override
    public Long getResultaatId() {
        //Default implementatie van het resultaatID is het id van het uirgaande bericht.
        return getUitgaandBerichtId();
    }

    public String getXmlBericht() {
        return xmlBericht;
    }

    public void setXmlBericht(final String xmlBericht) {
        this.xmlBericht = xmlBericht;
    }

    @Override
    public LockingElement getLockingElement() {
        return LockingElement.PERSOON;
    }

}

