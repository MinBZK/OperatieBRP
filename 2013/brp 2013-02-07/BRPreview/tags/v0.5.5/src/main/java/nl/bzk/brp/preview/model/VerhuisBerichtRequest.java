/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import nl.bzk.brp.model.data.kern.Persadres;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 * De representatie van het verhuisbericht zoals dat via de BRP naar het dashboard wordt gestuurd.
 */
public class VerhuisBerichtRequest extends AbstractBerichtRequest {

    /** De persoon. */
    private Persoon persoon;

    /** De oud adres. */
    private Adres   oudAdres;

    /** De nieuw adres. */
    private Adres   nieuwAdres;

    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de persoon.
     *
     * @param persoon de new persoon
     */
    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    /**
     * Geef de oud adres.
     *
     * @return de oud adres
     */
    public Adres getOudAdres() {
        return oudAdres;
    }

    /**
     * Zet de oud adres.
     *
     * @param oudAdres de new oud adres
     */
    public void setOudAdres(final Adres oudAdres) {
        this.oudAdres = oudAdres;
    }

    /**
     * Geef de nieuw adres.
     *
     * @return de nieuw adres
     */
    public Adres getNieuwAdres() {
        return nieuwAdres;
    }

    /**
     * Zet de nieuw adres.
     *
     * @param nieuwAdres de new nieuw adres
     */
    public void setNieuwAdres(final Adres nieuwAdres) {
        this.nieuwAdres = nieuwAdres;
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.preview.model.AbstractBerichtRequest#valideer()
     */
    @Override
    public void valideer() {
        super.valideer();

        if (getPersoon() == null) {
            throw new IllegalStateException("persoon ontbreekt");
        }
        if (getPersoon().getBsn() == null) {
            throw new IllegalStateException("bsn ontbreekt");
        }
        if (getPersoon().getVoornamen() == null) {
            throw new IllegalStateException("voornamen ontbreken");
        }
        if (getPersoon().getGeslachtsnaamcomponenten() == null) {
            throw new IllegalStateException("geslachtsnaamcomponenten ontbreken");
        }

        valideerVerhuisAdressen();
    }

    /**
     * Valideer of het oude en nieuwe adres voldoende aanwezig zijn om door te gaan.
     */
    private void valideerVerhuisAdressen() {
        if (getOudAdres() == null) {
            throw new IllegalStateException("oud adres ontbreekt");
        }
        if (getNieuwAdres() == null) {
            throw new IllegalStateException("nieuw adres ontbreekt");
        }
        if (getNieuwAdres().getGemeente() == null) {
            throw new IllegalStateException("gemeente van nieuw adres ontbreekt");
        }
        if (getNieuwAdres().getStraat() == null) {
            throw new IllegalStateException("straat van nieuw adres ontbreekt");
        }
        if (getNieuwAdres().getHuisnummer() == null) {
            throw new IllegalStateException("huisnummer van nieuw adres ontbreekt");
        }
        if (getNieuwAdres().getPlaats() == null) {
            throw new IllegalStateException("plaats van nieuw adres ontbreekt");
        }
        if (getOudAdres().getGemeente() == null) {
            throw new IllegalStateException("gemeente van oud adres ontbreekt");
        }
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.preview.model.AbstractBerichtRequest#creeerBerichtTekst()
     */
    @Override
    public String creeerBerichtTekst() {
        String verzendendePartij = getKenmerken().getVerzendendePartij().getNaam();
        Gemeente gemeenteOud = getOudAdres().getGemeente();
        Gemeente gemeenteNieuw = getNieuwAdres().getGemeente();
        String voornaam = getPersoon().getVoornamen().get(0);
        String geslachtsnaam = getPersoon().getGeslachtsnaamcomponenten().get(0).getVolledigeNaam();
        String oudeGemeenteTekst = creeerOudeGemeenteTekst(gemeenteOud, gemeenteNieuw);
        String prevalidatieTekst = creeerPrevalidatieTekst(getKenmerken().isPrevalidatie());
        String meldingenGeneriek = creeerGeneriekeTekstVoorMeldingen(getMeldingen());
        String berichtTemplate = "%s:%s %s van %s %s.%s";

        return String.format(berichtTemplate, verzendendePartij, prevalidatieTekst, oudeGemeenteTekst, voornaam,
                geslachtsnaam, meldingenGeneriek);
    }

    /**
     * Creeer oude gemeente tekst.
     *
     * @param gemeenteOud de gemeente oud
     * @param gemeenteNieuw de gemeente nieuw
     * @return de string
     */
    private String creeerOudeGemeenteTekst(final Gemeente gemeenteOud, final Gemeente gemeenteNieuw) {
        String resultaat;
        if (gemeenteOud.equals(gemeenteNieuw)) {
            resultaat = "verhuizing binnen gemeente";
        } else {
            resultaat = String.format("verhuizing vanuit %s", gemeenteOud.getNaam());
        }
        return resultaat;
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.preview.model.AbstractBerichtRequest#creeerDetailTekstVoorGevolg()
     */
    @Override
    protected String creeerDetailTekstVoorGevolg() {
        return getNieuwAdres().getAdresVolledig();
    }

    @Override
    public OndersteundeBijhoudingsTypes getSoortBijhouding() {
        return OndersteundeBijhoudingsTypes.VERHUIZING;
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.preview.model.AbstractBerichtRequest#creeerBsnLijst()
     */
    @Override
    public List<Integer> creeerBsnLijst() {
        List<Integer> resultaat = new ArrayList<Integer>();
        resultaat.add(persoon.getBsn());
        return resultaat;
    }

    @Override
    //TODO: Wat doet dit in een model class???
    public void verrijk() {
        try {
        String sql = "from Persadres pa where pa.pers.bsn = :bsn";
        Query query = Persadres.entityManager().createQuery(sql);
        query.setParameter("bsn", persoon.getBsn().toString());
        Persadres adres = (Persadres) query.getSingleResult();
        oudAdres = new Adres(adres);
        } catch(EmptyResultDataAccessException ex) {
            System.out.println("Adres niet gevonden voor BSN " + persoon.getBsn() + ex.getMessage());
        }
    }

}
