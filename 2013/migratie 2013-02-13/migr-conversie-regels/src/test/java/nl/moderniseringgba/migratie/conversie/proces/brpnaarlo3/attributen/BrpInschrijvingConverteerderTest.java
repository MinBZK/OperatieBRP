/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen;

import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.act;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.afgeleid;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.beperking;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.groep;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.his;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.inschrijving;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.opschorting;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.persoonskaart;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.stapel;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper.lo3Inschrijving;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.AbstractComponentTest;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAfgeleidAdministratiefInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOpschortingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpPersoonskaartInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVerstrekkingsbeperkingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;

import org.junit.Assert;
import org.junit.Test;

public class BrpInschrijvingConverteerderTest extends AbstractComponentTest {

    @Inject
    private BrpInschrijvingConverteerder subject;

    @Test
    @SuppressWarnings("unchecked")
    public void test() {
        // Input
        //@formatter:off
        // groep(inhoud, historie, inhoud, verval, geldig)
        // his(aanvang, einde, registratie, verval)
        // act(id, registratie)

        final BrpStapel<BrpOpschortingInhoud> opschortingStapel = stapel(
                groep(opschorting(19920101, "O"), his(19940101, null, 19940102, 20000102), act(2, 19940102), act(5, 20000102), null),
                groep(opschorting(19920101, "O"), his(19940101, 20000101, 20000102, null), act(2, 19940102), null, act(5, 20000102)));

        final BrpStapel<BrpInschrijvingInhoud> inschrijvingStapel = stapel(
                groep(inschrijving(null, null, 19940101, 42), his(19940101), act(9, 19940102)));

        final BrpStapel<BrpPersoonskaartInhoud> persoonskaartStapel = stapel(
                groep(persoonskaart("0599", false), his(19940101), act(1, 19940102)));

        final BrpStapel<BrpVerstrekkingsbeperkingInhoud> beperkingStapel = stapel(
                groep(beperking(true), his(19940101), act(4, 19940102)));

        final BrpStapel<BrpAfgeleidAdministratiefInhoud> afgeleidAdministratiefStapel = stapel(
                groep(afgeleid(19961231), his(19940101), act(3, 19940102)));
        //@formatter:on

        // Execute
        final Lo3Stapel<Lo3InschrijvingInhoud> result =
                subject.converteer(opschortingStapel, inschrijvingStapel, persoonskaartStapel, beperkingStapel,
                        afgeleidAdministratiefStapel);

        // Expectation
        //@formatter:off
        // cat(inhoud, historie, documentatie)
        // his(ingangsdatumGeldigheid)
        // akt(id)
        final Lo3InschrijvingInhoud expected = lo3Inschrijving(null,  null, null,  19940101, "0599", 7, 42,19961231000000000L ,false);

        // Check
        Assert.assertNotNull("Stapel is null", result);
        Assert.assertFalse("Stapel is leeg", result.isEmpty());
        Assert.assertNotNull("Categorie is null", result.get(0));

        System.out.println("expected: " + expected);
        System.out.println("actual:   " + result.get(0).getInhoud());

        Assert.assertEquals("Inhoud niet gelijk", expected, result.get(0).getInhoud());
    }
}
