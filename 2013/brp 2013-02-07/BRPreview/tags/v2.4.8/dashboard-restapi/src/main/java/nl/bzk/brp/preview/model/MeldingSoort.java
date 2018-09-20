/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

public enum MeldingSoort {

    INFORMATIE("Informatie"),
    WAARSCHUWING("Waarschuwing"),
    OVERRULEBAAR("Overrulebaar"),
    FOUT("Fout"),
    GENEGEERDE_FOUT("Overruled");

    private String naam;

    private MeldingSoort(final String naam) {
        this.naam = naam;
    }

    public String getNaam() {
        return naam;
    }

}
