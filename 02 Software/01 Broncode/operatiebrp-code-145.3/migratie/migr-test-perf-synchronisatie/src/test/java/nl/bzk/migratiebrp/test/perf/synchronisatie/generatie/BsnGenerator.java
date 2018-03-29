/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.synchronisatie.generatie;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BsnGenerator {
    private static final Random RANDOM = new Random();

    public String genereer() {
        while (true) {
            final byte[] basis = genereerBasis();
            final Byte eindcijfer = geefEindCijfer(basis);
            if (eindcijfer != null) {
                return maakBsn(basis, eindcijfer);
            }
        }
    }

    private String maakBsn(final byte[] basis, final Byte eindcijfer) {
        final StringBuilder result = new StringBuilder(9);
        result.append(basis[0]);
        result.append(basis[1]);
        result.append(basis[2]);
        result.append(basis[3]);
        result.append(basis[4]);
        result.append(basis[5]);
        result.append(basis[6]);
        result.append(basis[7]);
        result.append(eindcijfer);

        return result.toString();
    }

    private Byte geefEindCijfer(final byte[] basis) {
        final Set<Byte> mogelijkeEindCijfers = geefMogelijkeEindCijfersObvElfproef(basis);

        return mogelijkeEindCijfers.isEmpty() ? null : mogelijkeEindCijfers.iterator().next();
    }

    private Set<Byte> geefMogelijkeEindCijfersObvElfproef(final byte[] basis) {
        // (9*s0)+(8*s1)+(7*s2)+...+(2*s7)-(1*s8)
        final int basisSum =
                9 * basis[0] + 8 * basis[1] + 7 * basis[2] + 6 * basis[3] + 5 * basis[4] + 4 * basis[5] + 3 * basis[6] + 2 * basis[7];

        final Set<Byte> result = new HashSet<>();
        for (byte mogelijk = 0; mogelijk < 10; mogelijk++) {
            final int compleetSum = basisSum - mogelijk;
            final int rest = compleetSum % 11;

            if (rest == 0) {
                result.add(mogelijk);
            }
        }
        return result;
    }

    /**
     * Genereerd de eerste acht getallen (random).
     */
    byte[] genereerBasis() {
        final byte[] result = new byte[8];
        for (int i = 0; i < 8; i++) {
            result[i] = (byte) RANDOM.nextInt(10);
        }

        return result;
    }
}
