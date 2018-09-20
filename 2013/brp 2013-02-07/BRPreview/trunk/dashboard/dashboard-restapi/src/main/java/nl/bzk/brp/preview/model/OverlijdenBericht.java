/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import java.util.Arrays;
import java.util.List;

/**
 * De Class OverlijdenBericht.
 */
public class OverlijdenBericht extends AdministratieveHandelingBericht {

    /** De persoon. */
    private final Persoon persoon;

    /** De gemeente overlijden. */
    private final Gemeente gemeenteOverlijden;

    /** De datum overlijden. */
    private final Datum datumOverlijden;

    /**
     * Instantieert een nieuw overlijden bericht gebaseerd op een overlijden.
     * @param overlijden Het overlijden waarvoor een bericht gemaakt moet worden.
     */
    public OverlijdenBericht(final Overlijden overlijden) {
        super(overlijden);
        persoon = overlijden.getPersoon();
        gemeenteOverlijden = overlijden.getGemeenteOverlijden();
        datumOverlijden = overlijden.getDatumOverlijden();
    }

    /**
     * Haalt een gemeente overlijden op.
     *
     * @return gemeente overlijden
     */
    private String getGemeenteOverlijden() {
        final String resultaat;
        if (gemeenteOverlijden == null
                || gemeenteOverlijden.getNaam() == null)
        {
            resultaat = "?";
        } else {
            resultaat = gemeenteOverlijden.getNaam();
        }
        return resultaat;
    }

    /**
     * Creeert de tekst voor dit bericht.
     * @return De tekst voor dit bericht.
     */
    @Override
    public String creeerBerichtTekst() {
        final String template = "%s overleden op %s te %s. BSN: %d.";

        return String.format(template, persoon.getNaamTekst(), datumOverlijden.getTekst(),
                getGemeenteOverlijden(), persoon.getBsn());
    }

    /**
     * Creeert de lijst van BSN nummers die door deze handeling geraakt worden.
     * @return De lijst met BSN nummers.
     */
    @Override
    public final List<Integer> creeerBsnLijst() {
        return Arrays.asList(new Integer[]{persoon.getBsn()});
    }

    /**
     * Geeft de soort bijhouding voor dit type handeling.
     * @return De OndersteundeBijhoudingsTypes voor deze administratieve handeling.
     */
    @Override
    public OndersteundeBijhoudingsTypes getSoortBijhouding() {
        return OndersteundeBijhoudingsTypes.OVERLIJDEN;
    }

}
