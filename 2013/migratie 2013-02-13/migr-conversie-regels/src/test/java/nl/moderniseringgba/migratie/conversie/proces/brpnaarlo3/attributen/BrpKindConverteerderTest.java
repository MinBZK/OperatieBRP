/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen;

import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.act;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.doc;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.groep;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.his;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper.stapel;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper.lo3Doc;

import java.math.BigDecimal;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.AbstractComponentTest;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class BrpKindConverteerderTest extends AbstractComponentTest {

    @Inject
    private BrpKindConverteerder subject;

    @Test
    public void testIdentificatieNummers() throws Exception {
        final Lo3Stapel<Lo3KindInhoud> result =
                subject.converteer(stapel(groep(new BrpIdentificatienummersInhoud(1L, 1L), his(20040101),
                        act(7, 20040102, doc("Id-Doc", "0599")))));
        final Lo3KindInhoud expected = new Lo3KindInhoud(1L, 1L, null, null, null, null, null, null, null, true);
        final Lo3Documentatie expectedDoc = lo3Doc(7L, "0599", 0, "Id-Doc");

        // Check
        Assert.assertNotNull("Stapel is null", result);
        Assert.assertFalse("Stapel is leeg", result.isEmpty());
        Assert.assertNotNull("Categorie is null", result.get(0));

        System.out.println("expected: " + expected);
        System.out.println("actual:   " + result.get(0).getInhoud());

        Assert.assertEquals("Inhoud niet gelijk", expected, result.get(0).getInhoud());
        Assert.assertEquals("Document niet gelijk", expectedDoc, result.get(0).getDocumentatie());
    }

    @Test
    public void testGeboorte() throws Exception {
        final Lo3Stapel<Lo3KindInhoud> result =
                subject.converteer(stapel(groep(
                        new BrpGeboorteInhoud(new BrpDatum(20020102), new BrpGemeenteCode(new BigDecimal(599)),
                                new BrpPlaatsCode("TestPlaats"), null, null, new BrpLandCode(Integer.valueOf(6030)),
                                "locatie"), his(20040101), act(7, 20040102, doc("Geboorte-Doc", "0599")))));
        final Lo3KindInhoud expected =
                new Lo3KindInhoud(null, null, null, null, null, null, new Lo3Datum(20020102), new Lo3GemeenteCode(
                        "0599"), new Lo3LandCode("6030"), true);
        final Lo3Documentatie expectedDoc = lo3Doc(7L, "0599", 0, "Geboorte-Doc");

        // Check
        Assert.assertNotNull("Stapel is null", result);
        Assert.assertFalse("Stapel is leeg", result.isEmpty());
        Assert.assertNotNull("Categorie is null", result.get(0));

        System.out.println("expected: " + expected);
        System.out.println("actual:   " + result.get(0).getInhoud());

        Assert.assertEquals("Inhoud niet gelijk", expected, result.get(0).getInhoud());
        Assert.assertEquals("Document niet gelijk", expectedDoc, result.get(0).getDocumentatie());
    }

    @Test
    public void testSamengesteldeNaam() throws Exception {
        final Lo3Stapel<Lo3KindInhoud> result =
                subject.converteer(stapel(groep(new BrpSamengesteldeNaamInhoud(null, "Tester", null, null, null,
                        "Testing", false, false), his(20040101), act(7, 20040102, doc("SNaam-Doc", "0599")))));
        final Lo3KindInhoud expected =
                new Lo3KindInhoud(null, null, "Tester", null, null, "Testing", null, null, null, true);
        final Lo3Documentatie expectedDoc = lo3Doc(7L, "0599", 0, "SNaam-Doc");

        // Check
        Assert.assertNotNull("Stapel is null", result);
        Assert.assertFalse("Stapel is leeg", result.isEmpty());
        Assert.assertNotNull("Categorie is null", result.get(0));

        System.out.println("expected: " + expected);
        System.out.println("actual:   " + result.get(0).getInhoud());

        Assert.assertEquals("Inhoud niet gelijk", expected, result.get(0).getInhoud());
        Assert.assertEquals("Document niet gelijk", expectedDoc, result.get(0).getDocumentatie());
    }

    @Test
    public void testOuder() throws Exception {
        final Lo3Stapel<Lo3KindInhoud> result =
                subject.converteer(stapel(groep(new BrpOuderInhoud(false, new BrpDatum(20020102)), his(20040101),
                        act(7, 20040103, doc("Ouder-Doc", "0599")))));
        final Lo3KindInhoud expected = new Lo3KindInhoud(null, null, null, null, null, null, null, null, null, true);
        final Lo3Documentatie expectedDoc = lo3Doc(7L, "0599", 0, "Ouder-Doc");

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
