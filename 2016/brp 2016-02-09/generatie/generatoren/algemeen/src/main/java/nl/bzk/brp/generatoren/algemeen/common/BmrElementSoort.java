/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Enumeratie van de element soorten die het BMR kent.
 *
 * Dit is niet een enumeratie die behoort tot het meta model, maar een enumeratie die gebruikt wordt in
 * de generator logica.
 */
public enum BmrElementSoort {

    /**
     * ObjectType.
     */
    OBJECTTYPE("OT"),
    /**
     * Groep.
     */
    GROEP("G"),
    /**
     * Attribuuttype.
     */
    ATTRIBUUTTYPE("AT"),
    /**
     * Attribuut.
     */
    ATTRIBUUT("A"),
    /**
     * Tuple. *
     */
    TUPLE("T", "BS", "AC", "H", "CD"),;

    private final List<String> codes;


    private static final Map<String, BmrElementSoort> bmrElementSoortBijCode = new HashMap<>();

    static {
        registreerBmrElementSoortBijCode();
    }

    private static void registreerBmrElementSoortBijCode() {
        for (BmrElementSoort bmrElementSoort : BmrElementSoort.values()) {
            for (String code : bmrElementSoort.codes) {
                BmrElementSoort bestaandeWaarde = bmrElementSoortBijCode.put(code, bmrElementSoort);
                if (bestaandeWaarde != null) {
                    throw new IllegalStateException("Configuratiefout: dubbele mapping van soort " + code);
                }
            }
        }
    }

    /**
     * Constructor die de codes van de element soort enumeratie zet.
     *
     * @param codes de codes uit de elementtabel die horen bij deze BmrElementSoort horen.
     */
    private BmrElementSoort(final String... codes) {
        this.codes = Arrays.asList(codes);
    }

    /**
     * Retourneert de element soort op basis van de opgegeven code.
     *
     * @param code de code van het gezochte element soort.
     * @return het element soort behorende bij de code.
     */
    public static BmrElementSoort getBmrElementSoortBijCode(final String code) {
        return bmrElementSoortBijCode.get(code);
    }

    /**
     * Zoekt uit of deze BmrElementSoort hoort bij de gegeven code.
     *
     * @param code code waartegen we controleren.
     * @return true indien code en BmrElementSoort bij elkaar horen, anders false
     */
    public boolean hoortBijCode(final String code) {
        return this == getBmrElementSoortBijCode(code);
    }

    /**
     * Geeft de code die bij deze BmrElementSoort hoort.
     *
     * @return de bij deze BmrElementSoort horende code, indien het er precies één is
     * @throws java.lang.IllegalStateException indien er niet precies één code bij de BmrElementSoort hoort.
     */
    public String getUniekeCode() {
        if (codes.size() != 1) {
            throw new IllegalStateException("geen unieke code");
        }
        return codes.get(0);
    }
}
