/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import java.util.ArrayList;
import java.util.List;

/**
 * De Class VerhuizingBericht.
 */
public class VerhuizingBericht extends AdministratieveHandelingBericht {

    /**
     * De persoon.
     */
    private final Persoon persoon;

    /**
     * De oud adres.
     */
    private final Adres oudAdres;

    /**
     * De nieuw adres.
     */
    private final Adres nieuwAdres;

    /**
     * Instantieert een verhuizing bericht voor een verhuizing.
     * @param verhuizing De verhuizing voor dit verhuizing bericht.
     */
    public VerhuizingBericht(final Verhuizing verhuizing) {
        super(verhuizing);
        persoon = verhuizing.getPersoon();
        oudAdres = verhuizing.getOudAdres();
        nieuwAdres = verhuizing.getNieuwAdres();
    }

    /**
     * Haalt een persoon op.
     *
     * @return persoon
     */
    public Persoon getPersoon() {
        return persoon;
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
     * Geef de nieuw adres.
     *
     * @return de nieuw adres
     */
    public Adres getNieuwAdres() {
        return nieuwAdres;
    }

    /**
     * Creeert de tekst voor dit bericht.
     * @return De tekst voor dit bericht.
     */
    @Override
    public String creeerBerichtTekst() {
        String verhuisDatum = nieuwAdres.getDatumAanvangTekst();
        String format = "Verhuizing van %s per %s naar %s.";
        return String.format(format, getPersoon().getNaamTekst(), verhuisDatum, nieuwAdres.getTekst());
    }

    /**
     * Creeert de lijst van BSN nummers die door deze handeling geraakt worden.
     * @return De lijst met BSN nummers.
     */
    @Override
    public OndersteundeBijhoudingsTypes getSoortBijhouding() {
        return OndersteundeBijhoudingsTypes.VERHUIZING;
    }

    /**
     * Geeft de soort bijhouding voor dit type handeling.
     * @return De OndersteundeBijhoudingsTypes voor deze administratieve handeling.
     */
    @Override
    public List<Integer> creeerBsnLijst() {
        List<Integer> resultaat = new ArrayList<Integer>();
        resultaat.add(persoon.getBsn());
        return resultaat;
    }

}
