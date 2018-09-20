/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import java.util.ArrayList;
import java.util.List;


public class AdrescorrectieBerichtRequest extends AbstractBerichtRequest {

    private Persoon     persoon;

    private List<Adres> adressen = new ArrayList<Adres>();

    @Override
    public OndersteundeBijhoudingsTypes getSoortBijhouding() {
        return OndersteundeBijhoudingsTypes.ADRESCORRECTIE;
    }

    @Override
    public String creeerBerichtTekst() {
        StringBuilder resultaat = new StringBuilder("Adrescorrectie voor persoon ");
        resultaat.append(persoon.getNaamTekst()).append(".");
        for (Adres adres : adressen) {
            resultaat.append(adres.getTekstMetPeriode());
        }
        return resultaat.toString();
    }

    @Override
    public List<Integer> creeerBsnLijst() {
        List<Integer> resultaat = new ArrayList<Integer>();
        resultaat.add(persoon.getBsn());
        return resultaat;
    }

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    public List<Adres> getAdressen() {
        return adressen;
    }

    public void setAdressen(final List<Adres> adressen) {
        this.adressen = adressen;
    }

}
