/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;

/**
 * Reden opschorting bijhouding.
 */
public final class Lo3RedenOpschortingBijhoudingCode extends AbstractLo3Element {
    private static final long serialVersionUID = 1L;

    private static final String STANDAARDWAARDE = ".";
    private static final String FOUT = "F";
    private static final String RNI = "R";
    private static final String OVERLEDEN = "O";
    private static final String EMIGRATIE = "E";
    private static final String MINISTERIEEL_BESLUIT = "M";

    /**
     * Constructor.
     * @param waarde code
     */
    public Lo3RedenOpschortingBijhoudingCode(final String waarde) {
        this(waarde, null);
    }

    /**
     * Constructor met onderzoek.
     * @param waarde code
     * @param onderzoek het onderzoek waar deze code onder valt. Mag NULL zijn.
     */
    public Lo3RedenOpschortingBijhoudingCode(@Element(name = "waarde", required = false) final String waarde, @Element(name = "onderzoek",
            required = false) final Lo3Onderzoek onderzoek) {
        super(waarde, onderzoek);
    }

    /**
     * Geeft terug of de code overeenkomt met 'onbekend'.
     * @return True indien onbekend, false in overige gevallen.
     */
    public boolean isOnbekend() {
        return STANDAARDWAARDE.equals(getWaarde());
    }

    /**
     * Geeft terug of de code overeenkomt met 'emigratie'.
     * @return True indien onbekend, false in overige gevallen.
     */
    public boolean isEmigratie() {
        return EMIGRATIE.equals(getWaarde());
    }

    /**
     * Geeft terug of de code overeenkomt met 'emigratie'.
     * @return True indien onbekend, false in overige gevallen.
     */
    public boolean isMinisterieelBesluit() {
        return MINISTERIEEL_BESLUIT.equals(getWaarde());
    }

    /**
     * Geeft terug of de code overeenkomt met 'fout'.
     * @return True indien fout, false in overige gevallen.
     */
    public boolean isFout() {
        return FOUT.equalsIgnoreCase(getWaarde());
    }

    /**
     * Geeft terug of de code overeenkomt met 'RNI'.
     * @return True indien RNI, false in overige gevallen.
     */
    public boolean isRNI() {
        return RNI.equalsIgnoreCase(getWaarde());
    }

    /**
     * Geeft terug of de code overeenkomt met 'Overleden'.
     * @return true indien overleden, false in overige gevallen
     */
    public boolean isOverleden() {
        return OVERLEDEN.equalsIgnoreCase(getWaarde());
    }
}
