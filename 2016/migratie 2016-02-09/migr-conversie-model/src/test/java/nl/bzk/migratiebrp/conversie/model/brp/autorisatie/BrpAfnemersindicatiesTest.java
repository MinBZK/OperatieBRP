/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.autorisatie;

import java.util.Collections;
import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpAfnemersindicatieInhoud;
import org.junit.Test;

public class BrpAfnemersindicatiesTest {

    @Test
    public void test() {
        final BrpAfnemersindicaties subject = maakAfnemersindicatie(123456789L, 123456, 199201011200L);
        final BrpAfnemersindicaties equals = maakAfnemersindicatie(123456789L, 123456, 199201011200L);
        final BrpAfnemersindicaties different = maakAfnemersindicatie(987654321L, 654321, 199201011200L);

        Assert.assertEquals(subject, equals);
        Assert.assertFalse(subject.equals(new Object()));
        Assert.assertFalse(subject.equals(different));
        Assert.assertEquals(subject.hashCode(), equals.hashCode());
        Assert.assertEquals(subject.toString(), equals.toString());
    }

    private BrpAfnemersindicaties maakAfnemersindicatie(final long administratienummer, final int partijCode, final long datumTijdRegistratie) {
        final BrpAfnemersindicatieInhoud inhoud = new BrpAfnemersindicatieInhoud(null, null, false);
        final BrpHistorie historie = new BrpHistorie(null, null, BrpDatumTijd.fromDatumTijd(datumTijdRegistratie, null), null, null);
        final BrpActie actieInhoud =
                new BrpActie(
                    1L,
                    BrpSoortActieCode.CONVERSIE_GBA,
                    new BrpPartijCode(199902),
                    BrpDatumTijd.fromDatumTijd(datumTijdRegistratie, null),
                    null,
                    null,
                    1,
                    null);

        final BrpGroep<BrpAfnemersindicatieInhoud> categorie = new BrpGroep<>(inhoud, historie, actieInhoud, null, null);

        final BrpStapel<BrpAfnemersindicatieInhoud> stapel = new BrpStapel<>(Collections.singletonList(categorie));

        return new BrpAfnemersindicaties(
            administratienummer,
            Collections.singletonList(new BrpAfnemersindicatie(new BrpPartijCode(partijCode), stapel, null)));
    }
}
