/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.IOException;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3BerichtTest;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import org.junit.Test;

public class Wa01BerichtTest extends AbstractLo3BerichtTest {

    @Test
    public void testEmpty() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Wa01Bericht bericht = new Wa01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        testFormatAndParseBericht(bericht);
    }

    @Test
    public void test() throws ClassNotFoundException, BerichtInhoudException, IOException {
        final Wa01Bericht bericht = new Wa01Bericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setNieuwANummer(1235467890L);
        bericht.setDatumGeldigheid(new Lo3Datum(20000101));
        bericht.setOudANummer(9876543210L);
        bericht.setVoornamen("Piet");
        bericht.setAdellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("RI"));
        bericht.setVoorvoegselGeslachtsnaam("van");
        bericht.setGeslachtsnaam("Janssen");
        bericht.setGeboortedatum(new Lo3Datum(19770101));
        bericht.setGeboorteGemeenteCode(new Lo3GemeenteCode("0517"));
        bericht.setGeboorteLandCode(new Lo3LandCode("6030"));

        testFormatAndParseBericht(bericht);
    }
}
