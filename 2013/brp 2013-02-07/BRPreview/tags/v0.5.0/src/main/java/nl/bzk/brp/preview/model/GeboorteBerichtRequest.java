/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import java.util.ArrayList;
import java.util.List;


/**
 * De representatie van het geboortebericht vanuit de BRP naar het dashboard.
 */
public class GeboorteBerichtRequest extends AbstractBerichtRequest {

    /** De nieuwgeborene. */
    private Persoon nieuwgeborene;

    /** De adres nieuwgeborene. */
    private Adres   adresNieuwgeborene;

    /** De ouder1. */
    private Persoon ouder1;

    /** De ouder2. */
    private Persoon ouder2;

    /**
     * Geef de nieuwgeborene.
     *
     * @return de nieuwgeborene
     */
    public Persoon getNieuwgeborene() {
        return nieuwgeborene;
    }

    /**
     * Zet de nieuwgeborene.
     *
     * @param persoon de new nieuwgeborene
     */
    public void setNieuwgeborene(final Persoon persoon) {
        nieuwgeborene = persoon;
    }

    /**
     * Geef het adres nieuwgeborene.
     *
     * @return de adres nieuwgeborene
     */
    public Adres getAdresNieuwgeborene() {
        return adresNieuwgeborene;
    }

    /**
     * Zet het adres nieuwgeborene.
     *
     * @param adresNieuwgeborene de new adres nieuwgeborene
     */
    public void setAdresNieuwgeborene(final Adres adresNieuwgeborene) {
        this.adresNieuwgeborene = adresNieuwgeborene;
    }

    /**
     * Geef de ouder1.
     *
     * @return de ouder1
     */
    public Persoon getOuder1() {
        return ouder1;
    }

    /**
     * Zet de ouder1.
     *
     * @param ouder1 de new ouder1
     */
    public void setOuder1(final Persoon ouder1) {
        this.ouder1 = ouder1;
    }

    /**
     * Geef de ouder2.
     *
     * @return de ouder2
     */
    public Persoon getOuder2() {
        return ouder2;
    }

    /**
     * Zet de ouder2.
     *
     * @param ouder2 de new ouder2
     */
    public void setOuder2(final Persoon ouder2) {
        this.ouder2 = ouder2;
    }

    @Override
    public void valideer() {
        super.valideer();
        if (nieuwgeborene == null) {
            throw new IllegalStateException("nieuwgeborene ontbreekt");
        }
        if (nieuwgeborene.getBsn() == null) {
            throw new IllegalStateException("bsn nieuwgeborene ontbreekt");
        }
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.preview.model.AbstractBerichtRequest#creeerBerichtTekst()
     */
    @Override
    public String creeerBerichtTekst() {
        String verzendendePartij = getKenmerken().getVerzendendePartij().getNaam();
        String voorGemeenteTekst = creeerVoorGemeenteTekst();
        String naam = getNaam();
        String prevalidatieTekst = creeerPrevalidatieTekst(getKenmerken().isPrevalidatie());
        String meldingenGeneriek = creeerGeneriekeTekstVoorMeldingen(getMeldingen());
        String template = "%s%s:%s inschrijving nieuwgeborene %s.%s";

        return String
                .format(template, verzendendePartij, voorGemeenteTekst, prevalidatieTekst, naam, meldingenGeneriek);
    }

    /**
     * Geef de naam.
     * TODO: moet dit niet vanuit de BRP al de juiste naam samenstelling zijn?
     * @return de naam
     */
    private String getNaam() {
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
        if (getNieuwgeborene().getGeslachtsnaamcomponenten() == null
            || getNieuwgeborene().getGeslachtsnaamcomponenten().isEmpty())
        {
            resultaat = null;
        } else {
            resultaat = getNieuwgeborene().getGeslachtsnaamcomponenten().get(0).getVolledigeNaam();
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
        if (getNieuwgeborene().getVoornamen() == null || getNieuwgeborene().getVoornamen().isEmpty()) {
            resultaat = null;
        } else {
            resultaat = getNieuwgeborene().getVoornamen().get(0);
        }
        return resultaat;
    }

    /**
     * Creeer voor gemeente tekst.
     *
     * @return de string
     */
    private String creeerVoorGemeenteTekst() {
        String tekst;
        if (adresNieuwgeborene == null
            || getKenmerken().getVerzendendePartij().equals(adresNieuwgeborene.getGemeente()))
        {
            tekst = "";
        } else {
            tekst = String.format(" (voor %s)", adresNieuwgeborene.getGemeente().getNaam());
        }
        return tekst;
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.preview.model.AbstractBerichtRequest#creeerDetailTekstVoorGevolg()
     */
    @Override
    protected String creeerDetailTekstVoorGevolg() {
        String geboorteDatum = nieuwgeborene.getDatumGeboorteTekst();
        String gemeenteGeboorte = getGemeenteGeboorte();
        String voornaamOuder1 = ouder1 == null ? "?" : ouder1.getVoornamen().get(0);
        String geslachtsnaamOuder1 =
            ouder1 == null ? "" : ouder1.getGeslachtsnaamcomponenten().get(0).getVolledigeNaam();
        String voornaamOuder2 = ouder2 == null ? "?" : ouder2.getVoornamen().get(0);
        String geslachtsnaamOuder2 =
            ouder2 == null ? "" : ouder2.getGeslachtsnaamcomponenten().get(0).getVolledigeNaam();
        String template = "Geboren op %s te %s. BSN: %d. Ouders: %s %s en %s %s.";

        String resultaat =
            String.format(template, geboorteDatum, gemeenteGeboorte, nieuwgeborene.getBsn(), voornaamOuder1,
                    geslachtsnaamOuder1, voornaamOuder2, geslachtsnaamOuder2);

        return resultaat;
    }

    /**
     * Geef de gemeente geboorte.
     *
     * @return de gemeente geboorte
     */
    private String getGemeenteGeboorte() {
        String resultaat;
        if (getNieuwgeborene().getGemeenteGeboorte() == null
            || getNieuwgeborene().getGemeenteGeboorte().getNaam() == null)
        {
            resultaat = "?";
        } else {
            resultaat = nieuwgeborene.getGemeenteGeboorte().getNaam();
        }
        return resultaat;
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.preview.model.AbstractBerichtRequest#getSoortBijhouding()
     */
    @Override
    public OndersteundeBijhoudingsTypes getSoortBijhouding() {
        return OndersteundeBijhoudingsTypes.GEBOORTE;
    }

    /**
     * Creeer bsn lijst.
     *
     * @return de list
     * @see nl.bzk.brp.preview.model.AbstractBerichtRequest#creeerBsnLijst()
     */
    @Override
    public final List<Integer> creeerBsnLijst() {
        List<Integer> resultaat = new ArrayList<Integer>();
        resultaat.add(nieuwgeborene.getBsn());
        if (ouder1 != null && ouder1.getBsn() != null) {
            resultaat.add(ouder1.getBsn());
        }
        if (ouder2 != null && ouder2.getBsn() != null) {
            resultaat.add(ouder2.getBsn());
        }
        return resultaat;
    }

    @Override
    public void verrijk() {
        if (nieuwgeborene.getGemeenteGeboorte() != null) {
            nieuwgeborene.getGemeenteGeboorte().verrijk();
        }
    }

}
