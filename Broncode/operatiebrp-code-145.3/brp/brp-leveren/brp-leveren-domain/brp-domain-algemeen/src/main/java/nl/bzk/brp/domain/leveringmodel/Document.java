/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import java.util.Objects;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import org.springframework.util.Assert;

/**
 * Domein object voor documenten in de BRP.
 */
public final class Document {

    private long iD;
    private long objectSleutel;
    private int soortDocument;
    private String soortNaam;
    private String aktenummer;
    private String omschrijving;
    private String partijCode;
    private MetaObject metaObject;

    /**
     * @return het technisch id
     */
    public long getiD() {
        return iD;
    }

    /**
     * @return de objectsleutel
     */
    public long getObjectSleutel() {
        return objectSleutel;
    }

    /**
     * @return document soort
     */
    public int getSoort() {
        return soortDocument;
    }

    /**
     * @return document soort naam
     */
    public String getSoortNaam() {
        return soortNaam;
    }

    /**
     * @return document aktenummer
     */
    public String getAktenummer() {
        return aktenummer;
    }

    /**
     * @return document omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * @return document partijcode
     */
    public String getPartijCode() {
        return partijCode;
    }

    /**
     * @return het metaobject van het document
     */
    public MetaObject getMetaObject() {
        return metaObject;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Document document = (Document) o;
        return Objects.equals(iD, document.iD);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iD);
    }

    /**
     * @return een document MetaObject converter
     */
    public static DocumentConverter converter() {
        return DocumentConverter.INSTANCE;
    }

    /**
     * Converter voor MetaObject naar Document.
     */
    static final class DocumentConverter {

        private static final DocumentConverter INSTANCE = new DocumentConverter();

        private static final GroepElement IDENTITEIT = ElementHelper.getGroepElement(Element.DOCUMENT_IDENTITEIT.getId());

        private DocumentConverter() {
        }

        /**
         * Converteer het Document metaobject.
         * @param metaObject het Document metaobject
         * @return de handeling
         */
        Document converteer(final MetaObject metaObject) {
            final Document document = new Document();
            document.metaObject = metaObject;
            document.objectSleutel = metaObject.getObjectsleutel();

            final MetaGroep identiteit = metaObject.getGroep(IDENTITEIT);
            Assert.notNull(identiteit, "Document conversiefout : identiteitgroep is null.");
            final MetaRecord idRecord = identiteit.getRecords().iterator().next();
            final MetaAttribuut soortNaam = idRecord.getAttribuut(Element.DOCUMENT_SOORTNAAM);
            document.soortNaam = soortNaam.getWaarde();

            //aktenummer
            final MetaAttribuut aktenrAttr = idRecord.getAttribuut(Element.DOCUMENT_AKTENUMMER);
            if (aktenrAttr != null) {
                document.aktenummer = aktenrAttr.getWaarde();
            }
            //omschrijving
            final MetaAttribuut omschrijvingAttr = idRecord.getAttribuut(Element.DOCUMENT_OMSCHRIJVING);
            if (omschrijvingAttr != null) {
                document.omschrijving = omschrijvingAttr.getWaarde();
            }
            //partijcode
            final MetaAttribuut partijCodeAttr = idRecord.getAttribuut(Element.DOCUMENT_PARTIJCODE);
            if (partijCodeAttr != null) {
                document.partijCode = partijCodeAttr.getWaarde();
            }
            document.iD = idRecord.getVoorkomensleutel();
            return document;
        }
    }
}
