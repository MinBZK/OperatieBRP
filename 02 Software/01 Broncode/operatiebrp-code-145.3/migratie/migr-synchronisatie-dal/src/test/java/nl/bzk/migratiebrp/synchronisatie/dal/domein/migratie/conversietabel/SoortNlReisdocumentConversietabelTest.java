/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest;
import nl.bzk.algemeenbrp.test.dal.DBUnit.InsertBefore;

import org.junit.Before;
import org.junit.Test;

public class SoortNlReisdocumentConversietabelTest extends AbstractDatabaseTest {

    private static final BrpSoortNederlandsReisdocumentCode BRP_REISDOCUMENT_SOORT = new BrpSoortNederlandsReisdocumentCode("IA");
    private static final Lo3SoortNederlandsReisdocument LO3_SOORT_NEDERLANDS_REISDOCUMENT = new Lo3SoortNederlandsReisdocument("IA");

    @Inject
    private ConversietabelFactory conversietabelFactory;
    private Conversietabel<Lo3SoortNederlandsReisdocument, BrpSoortNederlandsReisdocumentCode> conversietabel;

    @Before
    public void setup() {
        conversietabel = conversietabelFactory.createSoortReisdocumentConversietabel();
    }

    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml"})
    @Test
    public void testNullConversie() {
        assertNull(conversietabel.converteerNaarBrp(null));
        assertNull(conversietabel.converteerNaarLo3(null));
    }

    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml"})
    @Test(expected = IllegalArgumentException.class)
    public void testOntbrekendeConversieNaarBrp() {
        conversietabel.converteerNaarBrp(new Lo3SoortNederlandsReisdocument("ONBEKEND"));
    }

    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml"})
    @Test(expected = IllegalArgumentException.class)
    public void testOntbrekendeConversieNaarLo3() {
        conversietabel.converteerNaarLo3(new BrpSoortNederlandsReisdocumentCode("ONBEKEND"));
    }

    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml"})
    @Test
    public void testConverteerNaarBrp() {
        final BrpSoortNederlandsReisdocumentCode verwachtResultaat = BRP_REISDOCUMENT_SOORT;
        final BrpSoortNederlandsReisdocumentCode conversieResultaat = conversietabel.converteerNaarBrp(LO3_SOORT_NEDERLANDS_REISDOCUMENT);
        assertEquals(verwachtResultaat, conversieResultaat);
    }

    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml"})
    @Test
    public void testConverteerNaarLo3() {
        final Lo3SoortNederlandsReisdocument verwachtResultaat = LO3_SOORT_NEDERLANDS_REISDOCUMENT;
        final Lo3SoortNederlandsReisdocument conversieResultaat = conversietabel.converteerNaarLo3(BRP_REISDOCUMENT_SOORT);
        assertEquals(verwachtResultaat, conversieResultaat);
    }

}
