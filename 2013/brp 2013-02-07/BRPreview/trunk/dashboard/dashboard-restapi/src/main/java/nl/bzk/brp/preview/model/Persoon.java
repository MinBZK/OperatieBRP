/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;


/**
 * De Class Persoon.
 */
public class Persoon {

    /** De bsn. */
    private Integer bsn;

    /** De datum geboorte. */
    private final Datum datumGeboorte = new Datum();

    /** De gemeente geboorte. */
    private Gemeente gemeenteGeboorte;

    /** De voornamen. */
    private List<String> voornamen;

    /** De geslachtsnaamcomponenten. */
    private List<Geslachtsnaamcomponent> geslachtsnaamcomponenten;

    /** De geslacht. */
    private String geslacht;

    /**
     * Instantieert een nieuwe persoon.
     */
    public Persoon() {
    }

    /**
     * Instantieert een nieuwe persoon.
     *
     * @param bsn de bsn
     * @param achternaam de achternaam
     */
    public Persoon(final Integer bsn, final String achternaam) {
        this(bsn, null, null, achternaam);
    }

    /**
     * Instantieert een nieuwe persoon.
     *
     * @param bsn de bsn
     * @param voornaam de voornaam
     * @param achternaam de achternaam
     */
    public Persoon(final Integer bsn, final String voornaam, final String achternaam) {
        this(bsn, voornaam, null, achternaam);
    }

    /**
     * Instantieert een nieuwe persoon.
     *
     * @param bsn de bsn
     * @param voornaam de voornaam
     * @param tussenvoegsels de tussenvoegsels
     * @param achternaam de achternaam
     */
    public Persoon(final Integer bsn, final String voornaam, final String tussenvoegsels, final String achternaam) {
        this.bsn = bsn;
        if (voornaam != null) {
            voornamen = Arrays.asList(voornaam);
        }
        geslachtsnaamcomponenten =
                Arrays.<Geslachtsnaamcomponent>asList(new Geslachtsnaamcomponent(tussenvoegsels, achternaam));
    }

    /**
     * Haalt een bsn op.
     *
     * @return bsn
     */
    public Integer getBsn() {
        return bsn;
    }

    /**
     * Instellen van bsn.
     *
     * @param bsn de nieuwe bsn
     */
    public void setBsn(final Integer bsn) {
        this.bsn = bsn;
    }

    /**
     * Haalt een datum geboorte op.
     *
     * @return datum geboorte
     */
    public int getDatumGeboorte() {
        return datumGeboorte.getDecimalen();
    }

    /**
     * Haalt een datum geboorte tekst op.
     *
     * @return datum geboorte tekst
     */
    @JsonIgnore
    public String getDatumGeboorteTekst() {
        return datumGeboorte.getTekst();
    }

    /**
     * Instellen van datum geboorte.
     *
     * @param datumGeboorte de nieuwe datum geboorte
     */
    public void setDatumGeboorte(final int datumGeboorte) {
        this.datumGeboorte.setDecimalen(datumGeboorte);
    }

    /**
     * Haalt een gemeente geboorte op.
     *
     * @return gemeente geboorte
     */
    public Gemeente getGemeenteGeboorte() {
        return gemeenteGeboorte;
    }

    /**
     * Instellen van gemeente geboorte.
     *
     * @param gemeenteGeboorte de nieuwe gemeente geboorte
     */
    public void setGemeenteGeboorte(final Gemeente gemeenteGeboorte) {
        this.gemeenteGeboorte = gemeenteGeboorte;
    }

    /**
     * Haalt een naam tekst op.
     *
     * @return naam tekst
     */
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

    /**
     * Haalt een voornamen op.
     *
     * @return voornamen
     */
    public List<String> getVoornamen() {
        return voornamen;
    }

    /**
     * Instellen van voornamen.
     *
     * @param voornamen de nieuwe voornamen
     */
    public void setVoornamen(final List<String> voornamen) {
        this.voornamen = voornamen;
    }

    /**
     * Haalt een geslachtsnaamcomponenten op.
     *
     * @return geslachtsnaamcomponenten
     */
    public List<Geslachtsnaamcomponent> getGeslachtsnaamcomponenten() {
        return geslachtsnaamcomponenten;
    }

    /**
     * Instellen van geslachtsnaamcomponenten.
     *
     * @param geslachtsnaamcomponenten de nieuwe geslachtsnaamcomponenten
     */
    public void setGeslachtsnaamcomponenten(final List<Geslachtsnaamcomponent> geslachtsnaamcomponenten) {
        this.geslachtsnaamcomponenten = geslachtsnaamcomponenten;
    }

    /**
     * Haalt een geslacht op.
     *
     * @return geslacht
     */
    public String getGeslacht() {
        return geslacht;
    }

    /**
     * Instellen van geslacht.
     *
     * @param geslacht de nieuwe geslacht
     */
    public void setGeslacht(final String geslacht) {
        this.geslacht = geslacht;
    }

}
