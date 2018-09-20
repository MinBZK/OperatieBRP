/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.dashboard;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.model.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.PersoonVoornaam;


/**
 * Persoon.
 */
public class Persoon {

    private Long                         bsn;

    private final Datum                  datumGeboorte = new Datum();

    private Gemeente                     gemeenteGeboorte;

    private List<String>                 voornamen;

    private List<Geslachtsnaamcomponent> geslachtsnaamcomponenten;

    /**
     * Constructor voor een dashboard persoon op basis van een logisch persoon.
     * @param persoon logisch persoon
     */
    public Persoon(final nl.bzk.brp.model.logisch.Persoon persoon) {
        bsn = Long.parseLong(persoon.getIdentificatienummers().getBurgerservicenummer());
        datumGeboorte.setDecimalen(persoon.getGeboorte().getDatumGeboorte());
        if (persoon.getGeboorte().getGemeenteGeboorte() != null) {
            gemeenteGeboorte = new Gemeente(persoon.getGeboorte().getGemeenteGeboorte());
        }
        voornamen = new ArrayList<String>();
        if (persoon.getPersoonVoornamen() != null) {
            for (PersoonVoornaam voornaam : persoon.getPersoonVoornamen()) {
                voornamen.add(voornaam.getNaam());
            }
        }
        geslachtsnaamcomponenten = new ArrayList<Geslachtsnaamcomponent>();
        if (persoon.getGeslachtsnaamcomponenten() != null) {
            for (PersoonGeslachtsnaamcomponent geslachtsnaam : persoon.getGeslachtsnaamcomponenten()) {
                geslachtsnaamcomponenten.add(new Geslachtsnaamcomponent(geslachtsnaam));
            }
        }
    }

    public Long getBsn() {
        return bsn;
    }

    public void setBsn(final Long bsn) {
        this.bsn = bsn;
    }

    public int getDatumGeboorte() {
        return datumGeboorte.getDecimalen();
    }

    /**
     * @param datumGeboorte datum waarin nulwaardes zijn toegestaan
     */
    public void setDatumGeboorte(final int datumGeboorte) {
        this.datumGeboorte.setDecimalen(datumGeboorte);
    }

    public Gemeente getGemeenteGeboorte() {
        return gemeenteGeboorte;
    }

    public void setGemeenteGeboorte(final Gemeente gemeenteGeboorte) {
        this.gemeenteGeboorte = gemeenteGeboorte;
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

}
