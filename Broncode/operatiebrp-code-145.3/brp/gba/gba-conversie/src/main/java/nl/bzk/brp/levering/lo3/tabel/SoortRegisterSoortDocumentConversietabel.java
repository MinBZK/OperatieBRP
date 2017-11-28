/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;

/**
 * Conversietabel voor soortdocument.
 */
public final class SoortRegisterSoortDocumentConversietabel implements Conversietabel<Character, BrpSoortDocumentCode> {

    @Override
    public BrpSoortDocumentCode converteerNaarBrp(final Character input) {
        return null;
    }

    @Override
    public boolean valideerLo3(final Character input) {
        return true;
    }

    @Override
    public Character converteerNaarLo3(final BrpSoortDocumentCode input) {
        return null;
    }

    @Override
    public boolean valideerBrp(final BrpSoortDocumentCode input) {
        return true;
    }
}
