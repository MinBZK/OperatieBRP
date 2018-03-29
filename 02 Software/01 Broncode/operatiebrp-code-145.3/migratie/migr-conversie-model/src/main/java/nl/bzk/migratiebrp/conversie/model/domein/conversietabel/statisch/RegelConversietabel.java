/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import com.google.common.collect.ImmutableMap;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3Foutcode;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de BRP regels en LO3 foutmeldingen.
 */
public final class RegelConversietabel implements Conversietabel<Character, String> {

    private static final ImmutableMap<String, Lo3Foutcode> CONVERSIE_TABEL = ImmutableMap.<String, Lo3Foutcode>builder()
            // UCS LV.1.AL (Autorisatie levering)
            .put(Regel.R1257.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R1258.getCode(), Lo3Foutcode.X)
            .put(Regel.R1260.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R1261.getCode(), Lo3Foutcode.X)
            .put(Regel.R1262.getCode(), Lo3Foutcode.X)
            .put(Regel.R1263.getCode(), Lo3Foutcode.X)
            .put(Regel.R1264.getCode(), Lo3Foutcode.X)
            .put(Regel.R2052.getCode(), Lo3Foutcode.X)
            .put(Regel.R2053.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R2054.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R2055.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R2056.getCode(), Lo3Foutcode.X)
            .put(Regel.R2120.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R2121.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R2122.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R2130.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R2239.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R2242.getCode(), Lo3Foutcode.X)
            .put(Regel.R2243.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R2244.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R2245.getCode(), Lo3Foutcode.X)
            .put(Regel.R2258.getCode(), Lo3Foutcode.GEEN)

            // UCS LV.1.CP (Controleer persoonsselectie)
            .put(Regel.R1339.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R1401.getCode(), Lo3Foutcode.I)
            .put(Regel.R1403.getCode(), Lo3Foutcode.G)
            .put(Regel.R2193.getCode(), Lo3Foutcode.GEEN)

            .put(Regel.R1538.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R1539.getCode(), Lo3Foutcode.GEEN)

            // UCS SA.1.PA (Plaats afnemerindicatie)
            .put(Regel.R2061.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R1402.getCode(), Lo3Foutcode.I)
            .put(Regel.R1405.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R1406.getCode(), Lo3Foutcode.GEEN)

            // UCS BV.0.ZP (Zoek persoon)
            .put(Regel.R1274.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R2265.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R2266.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R2267.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R2281.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R2284.getCode(), Lo3Foutcode.F)
            .put(Regel.R2285.getCode(), Lo3Foutcode.F)
            .put(Regel.R2288.getCode(), Lo3Foutcode.U)
            .put(Regel.R2289.getCode(), Lo3Foutcode.U)
            .put(Regel.R2290.getCode(), Lo3Foutcode.X)
            .put(Regel.R2295.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R2297.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R2308.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R2311.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R2340.getCode(), Lo3Foutcode.F)
            .put(Regel.R2389.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R2343.getCode(), Lo3Foutcode.X)
            .put(Regel.R2405.getCode(), Lo3Foutcode.X)
            .put(Regel.R2541.getCode(), Lo3Foutcode.GEEN)
            .put(Regel.R2542.getCode(), Lo3Foutcode.GEEN)

            // UCS BV.0.ZA (Zoek persoon op adres)
            .put(Regel.R1640.getCode(), Lo3Foutcode.Z)
            .put(Regel.R2373.getCode(), Lo3Foutcode.P)
            .put(Regel.R2374.getCode(), Lo3Foutcode.P)
            .put(Regel.R2375.getCode(), Lo3Foutcode.P)
            .put(Regel.R2392.getCode(), Lo3Foutcode.U)
            .put(Regel.R2240.getCode(), Lo3Foutcode.P)

            // Code
            .put("BRBV0006", Lo3Foutcode.G)

            // UC309
            .put("NTB0001", Lo3Foutcode.N)
            .put("NTB0002", Lo3Foutcode.B)
            .put("NTB0003", Lo3Foutcode.B)
            .put("NTB0004", Lo3Foutcode.O)
            .put("NTB0005", Lo3Foutcode.N)

            .build();

    @Override
    public String converteerNaarBrp(final Character input) {
        throw new IllegalArgumentException("Vertaling van LO3 naar BRP niet ondersteund");
    }

    @Override
    public boolean valideerLo3(final Character input) {
        return false;
    }

    @Override
    public Character converteerNaarLo3(final String input) {
        return CONVERSIE_TABEL.getOrDefault(input, Lo3Foutcode.GEEN).code();
    }

    @Override
    public boolean valideerBrp(final String input) {
        return CONVERSIE_TABEL.containsKey(input);
    }
}
