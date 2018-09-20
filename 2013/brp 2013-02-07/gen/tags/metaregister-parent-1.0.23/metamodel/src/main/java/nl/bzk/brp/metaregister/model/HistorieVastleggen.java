/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.model;

public enum HistorieVastleggen {

    GEEN('G'), FORMEEL('F'), BEIDE('B'), BESTAANSPERIODE('P');

    private final char code;

    private HistorieVastleggen(final char code) {
        this.code = code;
    }

    public char getCode() {
        return code;
    }

}
