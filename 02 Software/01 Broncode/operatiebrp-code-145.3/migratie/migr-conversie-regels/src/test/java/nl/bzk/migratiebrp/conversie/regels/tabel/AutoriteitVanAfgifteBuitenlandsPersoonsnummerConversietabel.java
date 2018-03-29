/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.tabel;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractAutoriteitVanAfgifteBuitenlandsPersoonsnummerConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * Test implementatie van autoriteit van afgifte buitenlands persoonsnummer conversietabel.
 */
public class AutoriteitVanAfgifteBuitenlandsPersoonsnummerConversietabel extends AbstractAutoriteitVanAfgifteBuitenlandsPersoonsnummerConversietabel
        implements Conversietabel<Lo3NationaliteitCode, BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer> {

    /**
     * Test waarde voor een niet geldige waarde (niet wijzigen, tests zijn hiervan afhankelijk).
     */
    public static final Lo3NationaliteitCode LO3_NIET_VALIDE_UITZONDERING = new Lo3NationaliteitCode("0029");

    @Override
    public boolean valideerLo3(final Lo3NationaliteitCode input) {
        return !Lo3Validatie.isElementGevuld(input) || !LO3_NIET_VALIDE_UITZONDERING.equalsWaarde(input);
    }

    @Override
    public boolean valideerBrp(final BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer input) {
        return true;
    }
}
