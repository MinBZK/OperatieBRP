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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.synchronisatie.dal.AbstractDatabaseTest;
import nl.bzk.algemeenbrp.test.dal.DBUnit.InsertBefore;

import org.junit.Before;
import org.junit.Test;

public class AdellijkeTitelPredikaatConversietabelTest extends AbstractDatabaseTest {

    private static final AdellijkeTitelPredikaatPaar BRP_ADELLIJKE_TITEL = new AdellijkeTitelPredikaatPaar(
            new BrpCharacter('H', null),
            null,
            BrpGeslachtsaanduidingCode.MAN);
    private static final Lo3AdellijkeTitelPredikaatCode LO3_ADELLIJKE_TITEL = new Lo3AdellijkeTitelPredikaatCode("H");

    @Inject
    private ConversietabelFactory conversietabelFactory;
    private Conversietabel<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar> conversietabel;

    @Before
    public void setup() {
        conversietabel = conversietabelFactory.createAdellijkeTitelPredikaatConversietabel();
    }

    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml"})
    @Test
    public void testConverteerNaarBrp() {
        final AdellijkeTitelPredikaatPaar brpResultaat = conversietabel.converteerNaarBrp(LO3_ADELLIJKE_TITEL);
        assertEquals(BRP_ADELLIJKE_TITEL, brpResultaat);
        assertNull(conversietabel.converteerNaarBrp(null));

    }

    @InsertBefore({"/sql/data/brpStamgegevens-kern.xml", "/sql/data/brpStamgegevens-autaut.xml", "/sql/data/brpStamgegevens-conv.xml"})
    @Test
    public void testConverteerNaarLo3() {
        final Lo3AdellijkeTitelPredikaatCode lo3Resultaat = conversietabel.converteerNaarLo3(BRP_ADELLIJKE_TITEL);
        assertEquals(LO3_ADELLIJKE_TITEL, lo3Resultaat);
        assertNull(conversietabel.converteerNaarLo3(null));
    }
}
