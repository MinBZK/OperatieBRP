/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.migratie.conversietabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest;
import nl.bzk.algemeenbrp.test.dal.DBUnit.InsertBefore;

import org.junit.Before;
import org.junit.Test;

public class VoorvoegselConversietabelTest extends AbstractDatabaseTest {

    private static final VoorvoegselScheidingstekenPaar BRP_VOORVOEGSEL = new VoorvoegselScheidingstekenPaar(
            new BrpString("Aus 'm", null),
            new BrpCharacter(' ', null));
    private static final Lo3String LO3_VOORVOEGSEL = Lo3String.wrap("Aus 'm");

    @Inject
    private ConversietabelFactory conversietabelFactory;
    private Conversietabel<Lo3String, VoorvoegselScheidingstekenPaar> conversietabel;

    @Before
    public void setup() {
        conversietabel = conversietabelFactory.createVoorvoegselScheidingstekenConversietabel();
    }

    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml"})
    @Test
    public void testConverteerNaarBrp() {
        final VoorvoegselScheidingstekenPaar brpResultaat = conversietabel.converteerNaarBrp(LO3_VOORVOEGSEL);
        assertEquals(BRP_VOORVOEGSEL, brpResultaat);
        assertNull(conversietabel.converteerNaarBrp(null));
    }

    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml"})
    @Test
    public void testConverteerNaarLo3() {
        final Lo3String lo3Resultaat = conversietabel.converteerNaarLo3(BRP_VOORVOEGSEL);
        assertEquals(LO3_VOORVOEGSEL, lo3Resultaat);
        assertNull(conversietabel.converteerNaarLo3(null));
    }
}
