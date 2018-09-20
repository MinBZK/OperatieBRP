/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.dashboard;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.copy.model.objecttype.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.copy.model.objecttype.logisch.PersoonVoornaam;


/**
 * Persoon.
 */
public class Persoon {

    private Long bsn;

    private final nl.bzk.copy.model.dashboard.Datum datumGeboorte = new nl.bzk.copy.model.dashboard.Datum();

    private nl.bzk.copy.model.dashboard.Gemeente gemeenteGeboorte;

    private List<String> voornamen;

    private List<nl.bzk.copy.model.dashboard.Geslachtsnaamcomponent> geslachtsnaamcomponenten;

    private final String geslacht;

    /**
     * Constructor voor een dashboard persoon op basis van een logisch persoon.
     *
     * @param persoon logisch persoon
     */
    public Persoon(final nl.bzk.copy.model.objecttype.logisch.Persoon persoon) {
        bsn = new Long(persoon.getIdentificatienummers().getBurgerservicenummer().getWaarde());
        datumGeboorte.setDecimalen(persoon.getGeboorte().getDatumGeboorte().getWaarde());
        if (persoon.getGeboorte().getGemeenteGeboorte() != null) {
            gemeenteGeboorte = new nl.bzk.copy.model.dashboard.Gemeente(persoon.getGeboorte().getGemeenteGeboorte());
        }
        voornamen = new ArrayList<String>();
        if (persoon.getPersoonVoornaam() != null) {
            for (PersoonVoornaam voornaam : persoon.getPersoonVoornaam()) {
                voornamen.add(voornaam.getGegevens().getVoornaam().getWaarde());
            }
        }
        geslachtsnaamcomponenten = new ArrayList<nl.bzk.copy.model.dashboard.Geslachtsnaamcomponent>();
        if (persoon.getGeslachtsnaamcomponenten() != null) {
            for (PersoonGeslachtsnaamcomponent geslachtsnaam : persoon.getGeslachtsnaamcomponenten()) {
                geslachtsnaamcomponenten.add(new nl.bzk.copy.model.dashboard.Geslachtsnaamcomponent(geslachtsnaam));
            }
        }

        geslacht = persoon.getGeslachtsaanduiding().getGeslachtsaanduiding().getNaam();
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

    public nl.bzk.copy.model.dashboard.Gemeente getGemeenteGeboorte() {
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

    public List<nl.bzk.copy.model.dashboard.Geslachtsnaamcomponent> getGeslachtsnaamcomponenten() {
        return geslachtsnaamcomponenten;
    }

    public void setGeslachtsnaamcomponenten(final List<Geslachtsnaamcomponent> geslachtsnaamcomponenten) {
        this.geslachtsnaamcomponenten = geslachtsnaamcomponenten;
    }

    public String getGeslacht() {
        return geslacht;
    }

}
