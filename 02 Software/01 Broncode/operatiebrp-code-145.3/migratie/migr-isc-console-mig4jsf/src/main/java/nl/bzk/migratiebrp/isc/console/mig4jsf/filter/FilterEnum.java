/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.filter;

/**
 * Constanten voor het filter package.
 */
public enum FilterEnum {
    KANAAL("kanaal"),
    RICHTING("richting"),
    BRON("bron"),
    DOEL("doel"),
    TYPE("type"),
    BERICHT_ID("berichtId"),
    PROCESS_INSTANCE_ID("processInstanceId"),
    CORRELATIE_ID("correlatieId");

    private String naam;

    FilterEnum(final String naam) {
        this.naam = naam;
    }

    @Override
    public String toString() {
        return naam;
    }
}
