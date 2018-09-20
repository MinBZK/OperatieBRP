/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;


public class Persoon {

    private Integer                      bsn;

    private final Datum                  datumGeboorte = new Datum();

    private Gemeente                     gemeenteGeboorte;

    private List<String>                 voornamen;

    private List<Geslachtsnaamcomponent> geslachtsnaamcomponenten;

    private String                       geslacht;

    protected Persoon() {
    }

    public Persoon(final Integer bsn, final String achternaam) {
        this(bsn, null, null, achternaam);
    }

    public Persoon(final Integer bsn, final String voornaam, final String achternaam) {
        this(bsn, voornaam, null, achternaam);
    }

    public Persoon(final Integer bsn, final String voornaam, final String tussenvoegsels, final String achternaam) {
        this.bsn = bsn;
        if (voornaam != null) {
            voornamen = Arrays.asList(voornaam);
        }
        geslachtsnaamcomponenten =
                Arrays.<Geslachtsnaamcomponent> asList(new Geslachtsnaamcomponent(tussenvoegsels, achternaam));
    }

    public Integer getBsn() {
        return bsn;
    }

    public void setBsn(final Integer bsn) {
        this.bsn = bsn;
    }

    public int getDatumGeboorte() {
        return datumGeboorte.getDecimalen();
    }

    @JsonIgnore
    public String getDatumGeboorteTekst() {
        return datumGeboorte.getTekst();
    }

    public void setDatumGeboorte(final int datumGeboorte) {
        this.datumGeboorte.setDecimalen(datumGeboorte);
    }

    public Gemeente getGemeenteGeboorte() {
        return gemeenteGeboorte;
    }

    public void setGemeenteGeboorte(final Gemeente gemeenteGeboorte) {
        this.gemeenteGeboorte = gemeenteGeboorte;
    }

    @JsonIgnore
    public String getNaamTekst() {
        String resultaat;
        String voornaam = getVoornaam();
        String geslachtsnaam = getGeslachtsnaam();
        if (voornaam != null && geslachtsnaam != null) {
            resultaat = voornaam + " " + geslachtsnaam;
        } else if (voornaam != null && geslachtsnaam == null) {
            resultaat = voornaam;
        } else if (voornaam == null && geslachtsnaam != null) {
            resultaat = geslachtsnaam;
        } else {
            resultaat = "naam onbekend";
        }
        return resultaat;
    }

    /**
     * Geef de geslachtsnaam.
     * 
     * @return de geslachtsnaam
     */
    private String getGeslachtsnaam() {
        String resultaat;
        if (getGeslachtsnaamcomponenten() == null || getGeslachtsnaamcomponenten().isEmpty()) {
            resultaat = null;
        } else {
            resultaat = getGeslachtsnaamcomponenten().get(0).getVolledigeNaam();
        }
        return resultaat;
    }

    /**
     * Geef de voornaam.
     * 
     * @return de voornaam
     */
    private String getVoornaam() {
        String resultaat;
        if (getVoornamen() == null || getVoornamen().isEmpty()) {
            resultaat = null;
        } else {
            resultaat = getVoornamen().get(0);
        }
        return resultaat;
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

    public String getGeslacht() {
        return geslacht;
    }

    public void setGeslacht(String geslacht) {
        this.geslacht = geslacht;
    }

}
