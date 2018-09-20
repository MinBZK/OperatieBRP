/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3;

import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.proces.UniqueSequence;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de LO3 categorieen document en akte.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class Lo3Documentatie {

    @Element(name = "id")
    private final long id;

    @Element(name = "gemeenteAkte", required = false)
    private final Lo3GemeenteCode gemeenteAkte;
    @Element(name = "nummerAkte", required = false)
    private final String nummerAkte;

    @Element(name = "gemeenteDocument", required = false)
    private final Lo3GemeenteCode gemeenteDocument;
    @Element(name = "datumDocument", required = false)
    private final Lo3Datum datumDocument;
    @Element(name = "beschrijvingDocument", required = false)
    private final String beschrijvingDocument;

    @Element(name = "extraDocument", required = false)
    private final ExtraDocument extraDocument;
    @Element(name = "extraDocumentInformatie", required = false)
    private final Integer extraDocumentInformatie;

    /**
     * Maakt een Lo3Document object.
     * 
     * @param id
     *            technisch id
     * @param gemeenteAkte
     *            81.10 gemeente akte
     * @param nummerAkte
     *            81.20 nummer akte
     * @param gemeenteDocument
     *            82.10 gemeente document
     * @param datumDocument
     *            82.20 datum document
     * @param beschrijvingDocument
     *            83.30 beschrijving document
     * @param extraDocument
     *            indicatie of hier voor de conversie een extra document aan toegevoegd moet worden
     * @param extraDocumentInformatie
     *            de extra informatie bij een extra document
     */
    // CHECKSTYLE:OFF - Meer dan 7 parameters is in constructors van immutable model klassen getolereerd.
    public Lo3Documentatie(
    // CHECKSTYLE:ON
            @Element(name = "id") final long id,
            @Element(name = "gemeenteAkte", required = false) final Lo3GemeenteCode gemeenteAkte,
            @Element(name = "nummerAkte", required = false) final String nummerAkte,
            @Element(name = "gemeenteDocument", required = false) final Lo3GemeenteCode gemeenteDocument,
            @Element(name = "datumDocument", required = false) final Lo3Datum datumDocument,
            @Element(name = "beschrijvingDocument", required = false) final String beschrijvingDocument,
            @Element(name = "extraDocument", required = false) final ExtraDocument extraDocument,
            @Element(name = "extraDocumentInformatie", required = false) final Integer extraDocumentInformatie) {
        this.id = id;
        this.gemeenteAkte = gemeenteAkte;
        this.nummerAkte = nummerAkte;
        this.gemeenteDocument = gemeenteDocument;
        this.datumDocument = datumDocument;
        this.beschrijvingDocument = beschrijvingDocument;
        this.extraDocument = extraDocument;
        this.extraDocumentInformatie = extraDocumentInformatie;
    }

    /**
     * Maak een Lo3Document, indien nodig.
     * 
     * @param gemeenteAkte
     *            81.10 gemeente akte
     * @param nummerAkte
     *            81.20 nummer akte
     * @param gemeenteDocument
     *            82.10 gemeente document
     * @param datumDocument
     *            82.20 datum document
     * @param beschrijvingDocument
     *            83.30 beschrijving document
     * 
     * @return een nieuwe Lo3Document, null als alle velden leeg zijn
     */
    public static Lo3Documentatie build(
            final Lo3GemeenteCode gemeenteAkte,
            final String nummerAkte,
            final Lo3GemeenteCode gemeenteDocument,
            final Lo3Datum datumDocument,
            final String beschrijvingDocument) {
        if (ValidationUtils.isEenParameterGevuld(gemeenteAkte, nummerAkte, gemeenteDocument, datumDocument,
                beschrijvingDocument)) {
            return new Lo3Documentatie(UniqueSequence.next(), gemeenteAkte, nummerAkte, gemeenteDocument,
                    datumDocument, beschrijvingDocument, null, null);
        } else {
            return null;
        }
    }

    // /**
    // * Valideer de documentatie.
    // *
    // * <pre>
    // * Er mag niet zowel een akte als een document voorkomen.
    // * </pre>
    // */
    // @Preconditie(Precondities.PRE020)
    // public void valideer() {
    // if (isDocument() && isAkte()) {
    // FoutmeldingUtil
    // .gooiValidatieExceptie("Akte en Document mogen niet beiden voorkomen", Precondities.PRE020);
    // }
    // }

    /**
     * Voeg voor de conversie een extra document toe aan deze documentatie.
     * 
     * @param extraDocument
     *            type extra document
     * @param extraDocumentInformatie
     *            informatie bij het extra document
     * @return nieuwe documentatie
     */
    public Lo3Documentatie extraDocumentToevoegen(
            final ExtraDocument extraDocument,
            final Integer extraDocumentInformatie) {
        return new Lo3Documentatie(this.id, this.gemeenteAkte, this.nummerAkte, this.gemeenteDocument,
                this.datumDocument, this.beschrijvingDocument, extraDocument, extraDocumentInformatie);
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the gemeenteAkte
     */
    public Lo3GemeenteCode getGemeenteAkte() {
        return gemeenteAkte;
    }

    /**
     * @return the aktenummer
     */
    public String getNummerAkte() {
        return nummerAkte;
    }

    /**
     * @return the gemeenteDocument
     */
    public Lo3GemeenteCode getGemeenteDocument() {
        return gemeenteDocument;
    }

    /**
     * @return the datumDocument
     */
    public Lo3Datum getDatumDocument() {
        return datumDocument;
    }

    /**
     * @return the beschrijvingDocument
     */
    public String getBeschrijvingDocument() {
        return beschrijvingDocument;
    }

    /**
     * Heeft deze documentatie akte gegevens?
     * 
     * @return true, als gemeente akte of nummer akte gevuld is; anders false
     */
    public boolean isAkte() {
        return gemeenteAkte != null || nummerAkte != null;
    }

    /**
     * Heeft deze documentatie document gegevens?
     * 
     * @return true, als document akte of datum document of beschrijving document gevuld is; anders false
     */
    public boolean isDocument() {
        return gemeenteDocument != null || datumDocument != null || beschrijvingDocument != null;
    }

    /**
     * @return the extraDocument
     */
    public ExtraDocument getExtraDocument() {
        return extraDocument;
    }

    /**
     * @return the extraDocumentInformatie
     */
    public Integer getExtraDocumentInformatie() {
        return extraDocumentInformatie;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3Documentatie)) {
            return false;
        }
        final Lo3Documentatie castOther = (Lo3Documentatie) other;
        return new EqualsBuilder().append(gemeenteAkte, castOther.gemeenteAkte)
                .append(nummerAkte, castOther.nummerAkte).append(gemeenteDocument, castOther.gemeenteDocument)
                .append(datumDocument, castOther.datumDocument)
                .append(beschrijvingDocument, castOther.beschrijvingDocument).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(gemeenteAkte).append(nummerAkte).append(gemeenteDocument)
                .append(datumDocument).append(beschrijvingDocument).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("gemeenteAkte", gemeenteAkte)
                .append("nummerAkte", nummerAkte).append("gemeenteDocument", gemeenteDocument)
                .append("datumDocument", datumDocument).append("beschrijvingDocument", beschrijvingDocument)
                .toString();
    }

    /**
     * Indicatie dat en type van een extra document wat tbv de conversie toegevoegd moet worden aan acties die worden
     * gemaakt op basis van deze documentatie.
     */
    public static enum ExtraDocument {
        /** Extra document voor de indicatie van een huwelijks stapel. */
        HUWELIJK,
        /** Extra document voor de indicatie van de ouder (ouder1 of ouder2). */
        OUDER;
    }

}
