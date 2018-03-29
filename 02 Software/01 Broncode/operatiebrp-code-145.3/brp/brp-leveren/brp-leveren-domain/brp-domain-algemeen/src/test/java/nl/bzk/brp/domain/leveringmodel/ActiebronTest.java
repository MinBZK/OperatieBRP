/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ActiebronTest {
    private static final Long ACTIE_ID = 1L;
    private static final Integer ACTIEBRON_ID = 2;
    private static final Integer DOCUMENT_ID = 2;
    private static final String ACTIEBRON_RECHTSGROND = "Rechtsgrond";
    private static final String ACTIEBRON_RECHTSGRONDOMSCHRIJVING = "Rechtsgrondomschrijving";

    private Actie actie;
    private Actiebron actiebron;
    private Document document;
    private MetaObject actiebronMetaObject;

    @Before
    public void voorTest() {
        final MetaObject.Builder actieBronMetaobjectBuilder =
                TestVerantwoording.maakActiebronBuilder(ACTIEBRON_ID, ACTIEBRON_RECHTSGROND, ACTIEBRON_RECHTSGRONDOMSCHRIJVING);
        final MetaObject.Builder documentMetaObjectBuilder = TestVerantwoording.maakDocumentBuilder(DOCUMENT_ID);
        document = Document.converter().converteer(documentMetaObjectBuilder.build());
        actiebronMetaObject = actieBronMetaobjectBuilder.metObject(documentMetaObjectBuilder).build();
        actie = TestVerantwoording.maakActie(ACTIE_ID);
        actiebron = Actiebron.converter().converteer(actiebronMetaObject, actie);
    }

    @Test
    public void test() {
        assertEquals(Long.valueOf(ACTIEBRON_ID), actiebron.getId());
        assertEquals(actie, actiebron.getActie());
        assertEquals(document, actiebron.getDocument());
        assertEquals(ACTIEBRON_RECHTSGROND, actiebron.getRechtsgrond());
        assertEquals(ACTIEBRON_RECHTSGRONDOMSCHRIJVING, actiebron.getRechtsgrondomschrijving());
        assertEquals(actiebronMetaObject, actiebron.getMetaObject());
    }

    @Test
    public void testEquals(){
        final Actiebron actiebron2 = Actiebron.converter().converteer(
                        TestVerantwoording.maakActiebronBuilder(ACTIEBRON_ID, "", "").build(), actie);
        assertTrue(actiebron.equals(actiebron));
        //gelijk id, dus gelijk
        assertTrue(actiebron.equals(actiebron2));
        assertFalse(actiebron.equals(null));
        assertFalse(actiebron.equals(actie));
    }
}
