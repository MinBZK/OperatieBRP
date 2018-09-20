/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen;

import java.util.Collection;
import java.util.Date;
import nl.bzk.brp.locking.LockingElement;
import nl.bzk.brp.locking.LockingMode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import org.springframework.transaction.TransactionStatus;


/**
 * Algemene interface voor een context die voor bericht afhandeling binnen BRP request-reply webservices wordt gebruikt.
 */
public interface BerichtContext extends StappenContext {

    /**
     * De partij die de vraag of bijhouding heeft ingeschoten.
     *
     * @return de partij
     */
    PartijAttribuut getPartij();

    /**
     * Referentienummer in het inkomende bericht die gebruikt kan worden in het uitgaande bericht.
     *
     * @return het referentienummer
     */
    String getBerichtReferentieNummer();

    /**
     * Geef de transactie status voor de stappen terug.
     *
     * @return de status
     */
    TransactionStatus getTransactionStatus();

    /**
     * Geef een transactiestatus voor de stappen.
     *
     * @param transactionStatus De status van de transactie.
     */
    void setTransactionStatus(final TransactionStatus transactionStatus);

    /**
     * Verwijdert de businesstransactie uit het object.
     */
    void clearBusinessTransactionStatus();

    /**
     * Retourneert het tijdstip van verwerking; dit tijdstip wordt gezet zodra de context wordt geinitialiseerd en
     * heeft voor bijhouding en bevraging een verschillende betekenis.
     *
     * @return het tijdstip van verwerking; de betekenis hiervan verschilt tussen bevraging en bijhouding.
     */
    Date getTijdstipVerwerking();

    /**
     * Haalt identificeerbare objecten op.
     *
     * @return identificeerbare objecten
     */
    CommunicatieIdMap getIdentificeerbareObjecten();

    /**
     * Slaat de identificeerbare objecten op.
     *
     * @param identificeerbareObjecten identificeerbare objecten
     */
    void setIdentificeerbareObjecten(final CommunicatieIdMap identificeerbareObjecten);

    /**
     * Retourneert het id van het inkomende bericht.
     *
     * @return het id van het inkomende bericht.
     */
    long getIngaandBerichtId();

    /**
     * Retourneert het id van het uitgaande bericht.
     *
     * @return het id van het uitgaande bericht.
     */
    long getUitgaandBerichtId();

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
    void setXmlBericht(final String xmlBericht);

    /**
     * Database id's betrokken bij het Verzoek t.b.v. applicatief locking
     *
     * @return Collectie van id's waarvoor het verzoek een lock nodig heeft.
     */
    Collection<Integer> getLockingIds();

    /**
     * Het element soort waarvoor het verzoek een lock nodig heeft.
     *
     * @return LockingElement
     */
    LockingElement getLockingElement();

    /**
     * De locking modes die gewenst is.
     *
     * @return LockingMode
     */
    LockingMode getLockingMode();
}
