/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

/**
 * Interface voor Bericht objecten die een communicatie id map bijhouden. Dit is een map met als key een communicatieId uit het xml bericht
 * en de value is de Java Pojo die door JiBX is geunmarshalled.
 * De communicatieId map houden we bij op het object BerichtBericht, want dit object is met JiBX gemapped op het root element van een xml bericht.
 */
public interface CommunicatieIdMapBevattend {

    /**
     * Zet de communicatie id map. Deze map zal verder gebruikt worden tijdens het unmarshallen van een xml bericht.
     * @param communicatieIdMap de te zetten communicatie id map.
     */
    void setCommunicatieIdMap(CommunicatieIdMap communicatieIdMap);

    /**
     * Geeft de communicatie id map terug.
     * @return de communicatie id map.
     */
    CommunicatieIdMap getCommunicatieIdMap();
}
