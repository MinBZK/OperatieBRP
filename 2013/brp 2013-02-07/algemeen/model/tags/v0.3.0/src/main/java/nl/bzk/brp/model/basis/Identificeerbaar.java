/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;
/**
 * .
 *
 */
public interface Identificeerbaar {
    /**
     * CommunicatieId is een stuff attribuut waar je een groep of object kan identificeren.
     * @param communicatieId de te zetten id.
     */
    void setCommunicatieID(String communicatieId);

    /**
     * .
     * @return de communicatieID.
     */
    String getCommunicatieID();


    /**
     * Entiteit type is een stuff attribuut waarmee die verteld om welk soort (Logische Ontwerp) type het gaat.
     * @return de type.
     */
    String getEntiteitType();

    /**
     * RefentieId is een stuff attribuut waarmee je naar andere groepen/ objecten kan referen.
     * @return de id.
     */
    String getReferentieID();

    /**
     * RefentieId is een stuff attribuut waarmee je naar andere groepen/ objecten kan referen.
     * @param referentieID de te zetten id.
     */
    void setReferentieID(String referentieID);
}
