/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen.autorisatie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Collections;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpAfnemersindicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import org.junit.Test;

public class TussenAfnemersindicatiesTest {

    @Test
    public void test() {
        final TussenAfnemersindicaties subject = maakAfnemersindicatie("123456789", "123456");
        final TussenAfnemersindicaties equals = maakAfnemersindicatie("123456789", "123456");
        final TussenAfnemersindicaties different = maakAfnemersindicatie("987654321", "654321");

        assertEquals("123456789", subject.getAdministratienummer());
        assertEquals(1, subject.getAfnemersindicaties().size());
        assertEquals(subject, subject);
        assertEquals(subject, equals);
        assertFalse(subject.equals(new Object()));
        assertFalse(subject.equals(different));
        assertEquals(subject.hashCode(), equals.hashCode());
        assertEquals(subject.toString(), equals.toString());
    }

    private TussenAfnemersindicaties maakAfnemersindicatie(final String administratienummer, final String partijCode) {
        final BrpAfnemersindicatieInhoud inhoud = new BrpAfnemersindicatieInhoud(null, null, false);
        final Lo3Historie historie = new Lo3Historie(null, null, new Lo3Datum(19920101));

        final TussenGroep<BrpAfnemersindicatieInhoud> categorie = new TussenGroep<>(inhoud, historie, null, null);

        final TussenStapel<BrpAfnemersindicatieInhoud> stapel = new TussenStapel<>(Collections.singletonList(categorie));

        return new TussenAfnemersindicaties(administratienummer, Collections.singletonList(new TussenAfnemersindicatie(new BrpPartijCode(partijCode), stapel)));
    }
}
