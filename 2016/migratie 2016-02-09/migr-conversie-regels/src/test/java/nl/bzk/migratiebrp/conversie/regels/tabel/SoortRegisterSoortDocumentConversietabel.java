/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.tabel;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;

public class SoortRegisterSoortDocumentConversietabel implements Conversietabel<Character, BrpSoortDocumentCode> {

    private static final Set<Character> ONJUISTE_WAARDES = new HashSet<>();
    static {
        ONJUISTE_WAARDES.add('4');
        ONJUISTE_WAARDES.add('6');
    }

    @Override
    public BrpSoortDocumentCode converteerNaarBrp(final Character input) {
        return input == null ? null : new BrpSoortDocumentCode(input.toString());
    }

    @Override
    public boolean valideerLo3(final Character input) {
        return !ONJUISTE_WAARDES.contains(input);
    }

    @Override
    public Character converteerNaarLo3(final BrpSoortDocumentCode input) {
        return input == null ? null : input.getWaarde().charAt(0);
    }

    @Override
    public boolean valideerBrp(final BrpSoortDocumentCode input) {
        return true;
    }
}
