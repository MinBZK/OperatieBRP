/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import java.util.ArrayList;
import java.util.List;

/**
 * De Class CorrectieAdresBericht.
 */
public class CorrectieAdresBericht extends AdministratieveHandelingBericht {

    /** De persoon. */
    private Persoon persoon;

    /** De adressen. */
    private List<Adres> adressen = new ArrayList<Adres>();

    /**
     * Instantieert een correctie adres bericht op basis van een correctie adres.
     * @param correctieAdres Het correctieadres om dit bericht op te baseren.
     */
    public CorrectieAdresBericht(final CorrectieAdres correctieAdres) {
        super(correctieAdres);
        persoon = correctieAdres.getPersoon();
        adressen = correctieAdres.getAdressen();
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
     * Instellen van persoon.
     *
     * @param persoon de nieuwe persoon
     */
    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    /**
     * Haalt een adressen op.
     *
     * @return adressen
     */
    public List<Adres> getAdressen() {
        return adressen;
    }

    /**
     * Instellen van adressen.
     *
     * @param adressen de nieuwe adressen
     */
    public void setAdressen(final List<Adres> adressen) {
        this.adressen = adressen;
    }

    /**
     * Creeert de tekst voor dit bericht.
     * @return De tekst voor dit bericht.
     */
    @Override
    public String creeerBerichtTekst() {
        StringBuilder resultaat = new StringBuilder("Adrescorrectie voor persoon ");
        resultaat.append(persoon.getNaamTekst()).append(".");
        for (Adres adres : adressen) {
            resultaat.append(adres.getTekstMetPeriode());
        }
        return resultaat.toString();
    }

    /**
     * Creeert de lijst van BSN nummers die door deze handeling geraakt worden.
     * @return De lijst met BSN nummers.
     */
    @Override
    public List<Integer> creeerBsnLijst() {
        List<Integer> resultaat = new ArrayList<Integer>();
        resultaat.add(persoon.getBsn());
        return resultaat;
    }

    /**
     * Geeft de soort bijhouding voor dit type handeling.
     * @return De OndersteundeBijhoudingsTypes voor deze administratieve handeling.
     */
    @Override
    public OndersteundeBijhoudingsTypes getSoortBijhouding() {
        return OndersteundeBijhoudingsTypes.ADRESCORRECTIE;
    }

}
