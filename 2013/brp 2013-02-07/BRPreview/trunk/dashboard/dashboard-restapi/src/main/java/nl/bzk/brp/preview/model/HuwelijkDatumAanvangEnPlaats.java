/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

/**
 * Dit is een wrapper klasse om de aanvangdatum en plaats voor een huwelijk in 1 query op te kunnen halen
 * van de database.
 */
public class HuwelijkDatumAanvangEnPlaats {

    private Datum datumAanvang;

    private Plaats plaats;

    public Datum getDatumAanvang() {
        return datumAanvang;
    }

    public void setDatumAanvang(final Datum datumAanvang) {
        this.datumAanvang = datumAanvang;
    }

    public Plaats getPlaats() {
        return plaats;
    }

    public void setPlaats(final Plaats plaats) {
        this.plaats = plaats;
    }

}
