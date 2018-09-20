/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

/**
 * De Class HuwelijkBericht, het bericht voor de administratieve handeling Huwelijk.
 */
public class HuwelijkBericht extends AdministratieveHandelingBericht {

    /** De datum aanvang. */
    private final Datum datumAanvang;

    /** De plaats. */
    private Plaats plaats;

    /** De persoon1. */
    private final Persoon persoon1;

    /** De persoon2. */
    private final Persoon persoon2;

    /**
     * Instantieert een huwelijk bericht op basis van een huwelijk.
     * @param huwelijk Het huwelijk om het bericht op te baseren.
     */
    public HuwelijkBericht(final Huwelijk huwelijk) {
        super(huwelijk);
        datumAanvang = huwelijk.getDatumAanvang();
        persoon1 = huwelijk.getPersoon1();
        persoon2 = huwelijk.getPersoon2();
        plaats = huwelijk.getPlaats();
    }

    /**
     * Haalt een plaats op.
     *
     * @return plaats
     */
    public Plaats getPlaats() {
        return plaats;
    }

    /**
     * Haalt een plaats tekst op.
     *
     * @return plaats tekst
     */
    public String getPlaatsTekst() {
        String tekst = "";
        if (!hasPlaats()) {
            tekst = " te " + plaats.getNaam();
        }
        return tekst;
    }

    /**
     * Checks for plaats.
     *
     * @return true, als succesvol
     */
    private boolean hasPlaats() {
        return plaats == null || !StringUtils.hasText(plaats.getNaam());
    }

    /**
     * Instellen van plaats.
     *
     * @param plaats de nieuwe plaats
     */
    public void setPlaats(final Plaats plaats) {
        this.plaats = plaats;
    }

    /**
     * Haalt een persoon1 op.
     *
     * @return persoon1
     */
    public Persoon getPersoon1() {
        return persoon1;
    }

    /**
     * Haalt een persoon2 op.
     *
     * @return persoon2
     */
    public Persoon getPersoon2() {
        return persoon2;
    }

    /**
     * Haalt een datum aanvang tekst op.
     *
     * @return datum aanvang tekst
     */
    public String getDatumAanvangTekst() {
        return datumAanvang.getTekst();
    }

    /**
     * Creeert de tekst voor dit bericht.
     * @return De tekst voor dit bericht.
     */
    @Override
    public String creeerBerichtTekst() {
        String format = "Huwelijk op %s%s tussen %s en %s.";
        return String.format(format, getDatumAanvangTekst(), getPlaatsTekst(), persoon1.getNaamTekst(),
                persoon2.getNaamTekst());
    }

    /**
     * Creeert de lijst van BSN nummers die door deze handeling geraakt worden.
     * @return De lijst met BSN nummers.
     */
    @Override
    public List<Integer> creeerBsnLijst() {
        List<Integer> resultaat = new ArrayList<Integer>();
        resultaat.add(persoon1.getBsn());
        resultaat.add(persoon2.getBsn());
        return resultaat;
    }

    /**
     * Geeft de soort bijhouding voor dit type handeling.
     * @return De OndersteundeBijhoudingsTypes voor deze administratieve handeling.
     */
    @Override
    public OndersteundeBijhoudingsTypes getSoortBijhouding() {
        return OndersteundeBijhoudingsTypes.HUWELIJK;
    }

}
