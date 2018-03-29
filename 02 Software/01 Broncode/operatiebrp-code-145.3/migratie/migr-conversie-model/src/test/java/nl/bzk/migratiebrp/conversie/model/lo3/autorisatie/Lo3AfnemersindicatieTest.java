/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.autorisatie;

import java.util.Collections;
import org.junit.Assert;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import org.junit.Test;

public class Lo3AfnemersindicatieTest {

    @Test
    public void test() {
        final Lo3Afnemersindicatie subject = maakAfnemersindicatie("12345678", "123456", 19920101);
        final Lo3Afnemersindicatie equals = maakAfnemersindicatie("12345678", "123456", 19920101);
        final Lo3Afnemersindicatie different = maakAfnemersindicatie("98765432", "654321", 19920101);

        Assert.assertEquals("12345678", subject.getANummer());
        Assert.assertEquals(1, subject.getAfnemersindicatieStapels().size());
        Assert.assertEquals(subject, subject);
        Assert.assertEquals(subject, equals);
        Assert.assertFalse(subject.equals(new Object()));
        Assert.assertFalse(subject.equals(different));
        Assert.assertEquals(subject.hashCode(), equals.hashCode());
        Assert.assertEquals(subject.toString(), equals.toString());
    }

    private Lo3Afnemersindicatie maakAfnemersindicatie(final String aNummer, final String afnemersindicatie, final int datumingang) {
        final Lo3AfnemersindicatieInhoud inhoud = new Lo3AfnemersindicatieInhoud(afnemersindicatie);
        final Lo3Historie historie = new Lo3Historie(null, null, new Lo3Datum(datumingang));

        final Lo3Categorie<Lo3AfnemersindicatieInhoud> categorie = new Lo3Categorie<>(inhoud, null, historie, null);

        final Lo3Stapel<Lo3AfnemersindicatieInhoud> stapel = new Lo3Stapel<>(Collections.singletonList(categorie));

        return new Lo3Afnemersindicatie(aNummer, Collections.singletonList(stapel));
    }
}
