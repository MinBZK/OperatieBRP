/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

public class VerhuisBerichtRequest extends BerichtRequest {

    private Persoon persoon;

    private Adres   oudAdres;

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    public Adres getOudAdres() {
        return oudAdres;
    }

    public void setOudAdres(final Adres oudAdres) {
        this.oudAdres = oudAdres;
    }

}
