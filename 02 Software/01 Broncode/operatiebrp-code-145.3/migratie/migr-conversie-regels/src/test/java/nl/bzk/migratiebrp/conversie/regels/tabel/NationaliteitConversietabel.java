/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.tabel;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractNationaliteitConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;

public class NationaliteitConversietabel extends AbstractNationaliteitConversietabel implements
        Conversietabel<Lo3NationaliteitCode, BrpNationaliteitCode> {

    /**
     * Test waarde voor een niet geldige waarde (niet wijzigen, tests zijn hiervan afhankelijk).
     */
    public static final Lo3NationaliteitCode LO3_NIET_VALIDE_UITZONDERING = new Lo3NationaliteitCode("8888");

    public static final Lo3NationaliteitCode LO3_STAATLOOS = new Lo3NationaliteitCode(Lo3NationaliteitCode.NATIONALITEIT_CODE_STAATLOOS);
    public static final BrpNationaliteitCode BRP_STAATLOOS = new BrpNationaliteitCode(Lo3NationaliteitCode.NATIONALITEIT_CODE_STAATLOOS);

    @Override
    public boolean valideerLo3(final Lo3NationaliteitCode input) {
        return !LO3_NIET_VALIDE_UITZONDERING.equals(input) && !LO3_STAATLOOS.equals(input);
    }

    @Override
    public boolean valideerBrp(final BrpNationaliteitCode input) {
        return !BRP_STAATLOOS.equals(input);
    }

}
