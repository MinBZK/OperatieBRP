/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

public class Plaats {

    public static final Plaats DUMMY = new Plaats("plaats onbekend");

    private String code;

    private String naam;

    public Plaats() {
    }

    public Plaats(final String naam) {
        this.naam = naam;
    }

    public Plaats(final nl.bzk.brp.model.data.kern.Plaats wpl) {
        code = wpl.getWplcode();
        naam = wpl.getNaam();
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(final String naam) {
        this.naam = naam;
    }

}
