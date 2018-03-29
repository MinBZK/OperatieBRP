/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import org.junit.Assert;
import org.junit.Test;

public class DocumentTest {

    private static final Document.DocumentConverter CONVERTER = Document.converter();

    @Test
    public void testDocumentConverter() {
        final MetaObject documentMetaObject = maakDocumentMetaObject(1);

        final Document doc1 = CONVERTER.converteer(documentMetaObject);

        Assert.assertEquals(1, doc1.getiD());
        Assert.assertEquals(documentMetaObject, doc1.getMetaObject());
        Assert.assertEquals(documentMetaObject.getObjectsleutel(), doc1.getObjectSleutel());
        Assert.assertEquals("srtnaam1", doc1.getSoortNaam());
        Assert.assertEquals("aktenr1", doc1.getAktenummer());
        Assert.assertEquals("omschrijving1", doc1.getOmschrijving());
        Assert.assertEquals("000001", doc1.getPartijCode());
    }


    @Test
    public void testEquals() {
        final MetaObject documentMetaObject1 = maakDocumentMetaObject(1);
        final MetaObject documentMetaObject2 = maakDocumentMetaObject(2);
        final MetaObject documentMetaObject3 = maakDocumentMetaObject(1);

        final Document doc1 = CONVERTER.converteer(documentMetaObject1);
        final Document doc2 = CONVERTER.converteer(documentMetaObject2);
        final Document doc3 = CONVERTER.converteer(documentMetaObject3);

        Assert.assertFalse(doc1.equals(doc2));
        Assert.assertFalse(doc1.equals(null));
        Assert.assertFalse(doc1.equals(documentMetaObject1));

        Assert.assertTrue(doc1.equals(doc1));
        Assert.assertTrue(doc1.equals(doc3));
    }

    private MetaObject maakDocumentMetaObject(final int id) {
        //@formatter:off
        return MetaObject.maakBuilder()
                    .metObjectElement(Element.DOCUMENT.getId())
                        .metId(id)
                        .metGroep()
                            .metGroepElement((GroepElement) ElementHelper.getElementMetId(Element.DOCUMENT_IDENTITEIT.getId()))
                                .metRecord()
                                    .metId(id)
                                    .metAttribuut(Element.DOCUMENT_SOORTNAAM.getId(), "srtnaam" + id)
                                    .metAttribuut(Element.DOCUMENT_AKTENUMMER.getId(), "aktenr" + id)
                                    .metAttribuut(Element.DOCUMENT_OMSCHRIJVING.getId(), "omschrijving" + id)
                                    .metAttribuut(Element.DOCUMENT_PARTIJCODE.getId(), String.format("%06d", id))
                                .eindeRecord()
                        .eindeGroep()
                    .eindeObject().build();
        //@formatter:on
    }
}
