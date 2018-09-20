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

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.preview.model.AbstractBerichtRequest#creeerDetailTekstVoorGevolg()
     */
    @Override
    public String creeerBerichtTekst() {
        String geboorteDatum = nieuwgeborene.getDatumGeboorteTekst();
        String gemeenteGeboorte = getGemeenteGeboorte();
        String naamOuder1 = ouder1 == null ? "?" : ouder1.getNaamTekst();
        String naamOuder2 = ouder2 == null ? "?" : ouder2.getNaamTekst();
        String template = "%s geboren op %s te %s. BSN: %d. Ouders: %s en %s.";

        String resultaat =
            String.format(template, nieuwgeborene.getNaamTekst(), geboorteDatum, gemeenteGeboorte, nieuwgeborene.getBsn(), naamOuder1, naamOuder2);

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

    /*
     * (non-Javadoc)
     *
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

}
