/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen;

import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.act;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.doc;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.europees;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.groep;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.his;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.stapel;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.uitsluiting;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper.lo3Doc;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper.lo3Kiesrecht;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.AbstractComponentTest;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpEuropeseVerkiezingenInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpUitsluitingNederlandsKiesrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;

import org.junit.Assert;
import org.junit.Test;

public class BrpKiesrechtConverteerderTest extends AbstractComponentTest {

    @Inject
    private BrpKiesrechtConverteerder subject;

    @Test
    @SuppressWarnings("unchecked")
    public void test() {
        // Input
        //@formatter:off
        // groep(inhoud, historie, inhoud, verval, geldig)
        // his(aanvang, einde, registratie, verval)
        // act(id, registratie)
        final BrpStapel<BrpEuropeseVerkiezingenInhoud> europeesStapel = stapel(
                groep(europees(true, 20040101, 20100101), his(20040101), act(7, 20040102, doc("Euro-Doc", "0599"))));
            
        final BrpStapel<BrpUitsluitingNederlandsKiesrechtInhoud> uitsluitingStapel = stapel(
                groep(uitsluiting(true, 20090101), his(20090101), act(3, 20090102)));
        //@formatter:on

        // Execute

        final Lo3Stapel<Lo3KiesrechtInhoud> result = subject.converteer(europeesStapel, uitsluitingStapel);

        // Expectation
        //@formatter:off
        // cat(inhoud, historie, documentatie)
        // his(ingangsdatumGeldigheid)
        // akt(id)
        final Lo3KiesrechtInhoud expected = lo3Kiesrecht(true, 20040101, 20100101, true, 20090101);
        final Lo3Documentatie expectedDoc = lo3Doc(7L, "0599", 0, "Euro-Doc");
        
        // Check
        Assert.assertNotNull("Stapel is null", result);
        Assert.assertFalse("Stapel is leeg", result.isEmpty());
        Assert.assertNotNull("Categorie is null", result.get(0));
        
        System.out.println("expected: " + expected);
        System.out.println("actual:   " + result.get(0).getInhoud());
        
        Assert.assertEquals("Inhoud niet gelijk", expected, result.get(0).getInhoud());
        Assert.assertEquals("Document niet gelijk", expectedDoc, result.get(0).getDocumentatie());
    }
}
