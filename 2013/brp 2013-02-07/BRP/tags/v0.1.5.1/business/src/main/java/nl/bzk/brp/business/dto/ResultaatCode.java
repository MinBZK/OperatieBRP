/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto;

/**
 * Enumeratie voor de verschillende berichtresultaat codes.
 */
public enum ResultaatCode {

    /** Resultaatcode GOED geeft aan dat het bericht geheel correct, zonder fouten, is afgehandeld. */
    GOED,
    /** Resultaatcode FOUT geeft aan dat het bericht niet correct is afgehandeld en dat er fouten zijn opgetreden. */
    FOUT;

    public String getNaam() {
        return this.name();
    }

}
