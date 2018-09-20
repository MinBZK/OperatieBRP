/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.util.jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Deze class vertelt Jackson wat het mag negeren van de bijbehorende originele class, BrpHistorie. In dit geval om te
 * voorkomen dat er redundante informatie over de lijn wordt gestuurd.
 */
public abstract class BrpHistorieJsonMixin {
    /**
     * Geeft aan of de historie vervallen is ja of nee. Json gaat deze methode negeren.
     * 
     * @return boolean of de historie vervallen is.
     */
    @JsonIgnore
    public abstract boolean isVervallen();
}
