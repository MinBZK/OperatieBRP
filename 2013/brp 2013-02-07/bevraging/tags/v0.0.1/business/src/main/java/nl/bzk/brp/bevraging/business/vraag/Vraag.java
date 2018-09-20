/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.vraag;


/**
 * Standaard vraag object.
 *
 * @param <T> Type/Class van het object dat de vraag bevat.
 */
public class Vraag<T> {

    private T    vraagObject;
    private Long afzenderId;
    private Long abonnementId;

    /**
     * Retourneert het vraag object.
     * @return het vraag object.
     */
    public final T getVraag() {
        return vraagObject;
    }

    /**
     * Zet het vraag object.
     * @param obj het vraag object.
     */
    public final void setVraag(final T obj) {
        this.vraagObject = obj;
    }

    /**
     * Retourneet de id van de afzender.
     * @return de id van de afzender.
     */
    public final Long getAfzenderId() {
        return afzenderId;
    }

    /**
     * Zet de id van de afzender.
     * @param id de id van de afzender.
     */
    public final void setAfzenderId(final Long id) {
        this.afzenderId = id;
    }

    /**
     * Retourneert de id van de abonnement waaronder de vraag wordt gesteld.
     * @return de id van de abonnement waaronder de vraag wordt gesteld.
     */
    public final Long getAbonnementId() {
        return abonnementId;
    }

    /**
     * Zet de id van de abonnement waaronder de vraag wordt gesteld.
     * @param id de id van de abonnement waaronder de vraag wordt gesteld.
     */
    public final void setAbonnementId(final Long id) {
        this.abonnementId = id;
    }

}
