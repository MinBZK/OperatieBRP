/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import java.util.List;


public class Persoon {

    private List<String>                 voornamen;

    private List<Geslachtsnaamcomponent> geslachtsnaamcomponenten;

    private Adres                        adres;

    public Persoon() {
    }

    public List<String> getVoornamen() {
        return voornamen;
    }

    public void setVoornamen(final List<String> voornamen) {
        this.voornamen = voornamen;
    }

    public List<Geslachtsnaamcomponent> getGeslachtsnaamcomponenten() {
        return geslachtsnaamcomponenten;
    }

    public void setGeslachtsnaamcomponenten(final List<Geslachtsnaamcomponent> geslachtsnaamcomponenten) {
        this.geslachtsnaamcomponenten = geslachtsnaamcomponenten;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(final Adres adres) {
        this.adres = adres;
    }

}
