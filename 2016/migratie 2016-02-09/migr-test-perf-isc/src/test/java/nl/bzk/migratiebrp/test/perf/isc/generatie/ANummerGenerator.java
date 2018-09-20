/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.isc.generatie;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ANummerGenerator {

    private static final Random RANDOM = new Random();
    private static final Set<Long> ANUMMERS = new HashSet<>();

    public Long genereer() {
        while (true) {
            final byte[] basis = genereerBasis();
            // System.out.println("Basis: " + basis);
            final Byte eindcijfer = geefEindCijfer(basis);
            // System.out.println("Eindcijfer: " + eindcijfer);
            if (eindcijfer != null) {
                final Long anummer = maakAnummer(basis, eindcijfer);

                if (!ANUMMERS.contains(anummer)) {
                    ANUMMERS.add(anummer);
                    // System.out.println("Anummer: " + anummer);
                    return anummer;
                }
            }
        }
    }

    private Long maakAnummer(final byte[] basis, final Byte eindcijfer) {
        final StringBuilder result = new StringBuilder(10);
        result.append(basis[0]);
        result.append(basis[1]);
        result.append(basis[2]);
        result.append(basis[3]);
        result.append(basis[4]);
        result.append(basis[5]);
        result.append(basis[6]);
        result.append(basis[7]);
        result.append(basis[8]);
        result.append(eindcijfer);

        return Long.valueOf(result.toString());
    }

    private Byte geefEindCijfer(final byte[] basis) {
        final Set<Byte> mogelijkeEindCijfers = geefMogelijkeEindCijfersObvNietOpvolgendeCijfers(basis);
        mogelijkeEindCijfers.retainAll(maakMogelijkeEindCijfersObvElfproef(basis, mogelijkeEindCijfers));
        mogelijkeEindCijfers.retainAll(maakMogelijkeEindCijfersObvMachtTweeElfproef(basis, mogelijkeEindCijfers));

        return mogelijkeEindCijfers.isEmpty() ? null : mogelijkeEindCijfers.iterator().next();
    }

    private Set<Byte> geefMogelijkeEindCijfersObvNietOpvolgendeCijfers(final byte[] basis) {
        final Set<Byte> result = new HashSet<>();
        for (byte b = 0; b < 9; b++) {
            result.add(b < basis[8] ? b : (byte) (b + 1));
        }
        return result;
    }

    private Set<Byte> maakMogelijkeEindCijfersObvElfproef(final byte[] basis, final Set<Byte> mogelijkeEindCijfers) {
        if (mogelijkeEindCijfers.isEmpty()) {
            return Collections.emptySet();
        }
        final int basisSum = basis[0] + basis[1] + basis[2] + basis[3] + basis[4] + basis[5] + basis[6] + basis[7] + basis[8];

        final Set<Byte> result = new HashSet<>();
        for (final Byte mogelijk : mogelijkeEindCijfers) {
            final int compleetSum = basisSum + mogelijk;
            final int rest = compleetSum % 11;

            if (rest == 5 || rest == 0) {
                result.add(mogelijk);
            }
        }
        return result;
    }

    private Collection<?> maakMogelijkeEindCijfersObvMachtTweeElfproef(final byte[] basis, final Set<Byte> mogelijkeEindCijfers) {
        if (mogelijkeEindCijfers.isEmpty()) {
            return Collections.emptySet();
        }
        final int basisSum =
                1
                        * basis[0]
                        + 2
                        * basis[1]
                        + 4
                        * basis[2]
                        + 8
                        * basis[3]
                        + 16
                        * basis[4]
                        + 32
                        * basis[5]
                        + 64
                        * basis[6]
                        + 128
                        * basis[7]
                        + 256
                        * basis[8];

        final Set<Byte> result = new HashSet<>();
        for (final Byte mogelijk : mogelijkeEindCijfers) {
            final int compleetSum = basisSum + 512 * mogelijk;
            final int rest = compleetSum % 11;

            if (rest == 0) {
                result.add(mogelijk);
            }
        }
        return result;
    }

    /**
     * Genereerd de eerste negen getallen (random).
     * 
     * @return
     */
    byte[] genereerBasis() {
        final byte[] result = new byte[9];
        result[0] = genereerEersteGetal((byte) 0);
        result[1] = genereerEersteGetal(result[0]);
        result[2] = genereerEersteGetal(result[1]);
        result[3] = genereerEersteGetal(result[2]);
        result[4] = genereerEersteGetal(result[3]);
        result[5] = genereerEersteGetal(result[4]);
        result[6] = genereerEersteGetal(result[5]);
        result[7] = genereerEersteGetal(result[6]);
        result[8] = genereerEersteGetal(result[7]);

        return result;
    }

    byte genereerEersteGetal(final byte nietToegestaan) {
        final byte result = (byte) RANDOM.nextInt(9);
        return result < nietToegestaan ? result : (byte) (result + 1);
    }
}
