/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;

/**
 * Deze class representeert het LO3 element gemeente code. Dit is een verwijzing naar de LO3 gemeente tabel (Tabel 33).
 *
 * Deze class is immutable en threadsafe.
 */
public final class Lo3GemeenteCode extends AbstractLo3Element {

    /**
     * GemeenteCode ONBEKEND.
     */
    public static final Lo3GemeenteCode ONBEKEND = new Lo3GemeenteCode("0000");

    /**
     * GemeenteCode RNI.
     */
    public static final Lo3GemeenteCode RNI = new Lo3GemeenteCode("1999");

    private static final long serialVersionUID = 1L;

    private static final int LENGTE_NEDERLANDSE_CODE = 4;

    /**
     * Maakt een Lo3GemeenteCode object.
     * @param code de LO3 code die een gemeente in LO3 uniek identificeert, mag niet null zijn
     * @throws IllegalArgumentException als de lengte van de code niet tussen de 1 en 40 karakters is
     * @throws NullPointerException als code null is
     */
    public Lo3GemeenteCode(final String code) {
        this(code, null);
    }

    /**
     * Maakt een Lo3GemeenteCode object met onderzoek.
     * @param waarde de LO3 code die een gemeente in LO3 uniek identificeert, mag niet null zijn
     * @param onderzoek het onderzoek waar deze gemeentecode onder valt. Mag NULL zijn.
     * @throws IllegalArgumentException als de lengte van de code niet tussen de 1 en 40 karakters is
     * @throws NullPointerException als code null is
     */
    public Lo3GemeenteCode(
            @Element(name = "waarde", required = false) final String waarde,
            @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek) {
        super(waarde, onderzoek);
    }

    /**
     * Geef de valide nederlandse gemeente code.
     * @return true als de gemeente code uit vier cijfers bestaat en niet gelijk is aan 0000, anders false
     */
    @Definitie(Definities.DEF001)
    public boolean isValideNederlandseGemeenteCode() {
        boolean result = false;
        final String code = getWaarde();
        if (code != null && code.length() == LENGTE_NEDERLANDSE_CODE) {
            try {
                Integer.parseInt(code);
                result = true;
            } catch (final NumberFormatException nfe) {
                result = false;
            }
        }
        return result;
    }
}
