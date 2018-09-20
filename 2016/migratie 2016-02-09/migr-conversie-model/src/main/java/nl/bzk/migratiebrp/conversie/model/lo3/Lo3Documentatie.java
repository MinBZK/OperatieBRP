/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3;

import java.util.regex.Pattern;
import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Elementnummer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de LO3 categorieen document, akte en RNI-deelnemer.
 *
 * Deze class is immutable en threadsafe.
 *
 */
public final class Lo3Documentatie {

    /**
     * Geprivilegieerden worden aangeduid door rubriek 04.82.30 beschrjjving document met deze waarde te laten beginnen.
     * Deze tekst is case insensitive en wordt voorafgegaan door 0 of meerdere spaties.
     */
    private static final Pattern GEPRIVILEGIEERDE_PATROON = Pattern.compile("(?i)^[ ]*PROBAS.*$");

    @Element(name = "id")
    private final long id;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_8110)
    @Element(name = "gemeenteAkte", required = false)
    private final Lo3GemeenteCode gemeenteAkte;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_8120)
    @Element(name = "nummerAkte", required = false)
    private final Lo3String nummerAkte;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_8210)
    @Element(name = "gemeenteDocument", required = false)
    private final Lo3GemeenteCode gemeenteDocument;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_8220)
    @Element(name = "datumDocument", required = false)
    private final Lo3Datum datumDocument;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_8230)
    @Element(name = "beschrijvingDocument", required = false)
    private final Lo3String beschrijvingDocument;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_8810)
    @Element(name = "rniDeelnemerCode", required = false)
    private final Lo3RNIDeelnemerCode rniDeelnemerCode;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_8820)
    @Element(name = "omschrijvingVerdrag", required = false)
    private final Lo3String omschrijvingVerdrag;

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
     *            82.30 beschrijving document indicatie of hier voor de conversie een extra document aan toegevoegd moet
     *            worden
     * @param rniDeelnemerCode
     *            88.10 RNI deelnemer code
     * @param omschrijvingVerdrag
     *            88.20 omschrijving verdrag
     */
    public Lo3Documentatie(
        /* Meer dan 7 parameters is in constructors van immutable model klassen getolereerd. */
        @Element(name = "id") final long id,
        @Element(name = "gemeenteAkte", required = false) final Lo3GemeenteCode gemeenteAkte,
        @Element(name = "nummerAkte", required = false) final Lo3String nummerAkte,
        @Element(name = "gemeenteDocument", required = false) final Lo3GemeenteCode gemeenteDocument,
        @Element(name = "datumDocument", required = false) final Lo3Datum datumDocument,
        @Element(name = "beschrijvingDocument", required = false) final Lo3String beschrijvingDocument,
        @Element(name = "rniDeelnemerCode", required = false) final Lo3RNIDeelnemerCode rniDeelnemerCode,
        @Element(name = "omschrijvingVerdrag", required = false) final Lo3String omschrijvingVerdrag)
    {
        this.id = id;
        this.gemeenteAkte = gemeenteAkte;
        this.nummerAkte = nummerAkte;
        this.gemeenteDocument = gemeenteDocument;
        this.datumDocument = datumDocument;
        this.beschrijvingDocument = beschrijvingDocument;
        this.rniDeelnemerCode = rniDeelnemerCode;
        this.omschrijvingVerdrag = omschrijvingVerdrag;
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
     * @param rniDeelnemerCode
     *            88.10 RNI deelnemer code
     * @param omschrijvingVerdrag
     *            88.20 omschrijving verdrag
     *
     * @return een nieuwe Lo3Document, null als alle velden leeg zijn
     */
    public static Lo3Documentatie build(
        final Lo3GemeenteCode gemeenteAkte,
        final Lo3String nummerAkte,
        final Lo3GemeenteCode gemeenteDocument,
        final Lo3Datum datumDocument,
        final Lo3String beschrijvingDocument,
        final Lo3RNIDeelnemerCode rniDeelnemerCode,
        final Lo3String omschrijvingVerdrag)
    {
        if (Validatie.isEenParameterNietNull(
            gemeenteAkte,
            nummerAkte,
            gemeenteDocument,
            datumDocument,
            beschrijvingDocument,
            rniDeelnemerCode,
            omschrijvingVerdrag))
        {
            return new Lo3Documentatie(
                UniqueSequence.next(),
                gemeenteAkte,
                nummerAkte,
                gemeenteDocument,
                datumDocument,
                beschrijvingDocument,
                rniDeelnemerCode,
                omschrijvingVerdrag);
        } else {
            return null;
        }
    }

    /**
     * @return boolean
     */
    public boolean bevatAanduidingGeprivilegieerde() {
        final String beschrijvingDocument1 = Lo3String.unwrap(getBeschrijvingDocument());
        return beschrijvingDocument1 != null && GEPRIVILEGIEERDE_PATROON.matcher(beschrijvingDocument1).matches();
    }

    /**
     * Geef de waarde van id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Geef de waarde van gemeente akte.
     *
     * @return the gemeenteAkte
     */
    public Lo3GemeenteCode getGemeenteAkte() {
        return gemeenteAkte;
    }

    /**
     * Geef de waarde van nummer akte.
     *
     * @return the aktenummer
     */
    public Lo3String getNummerAkte() {
        return nummerAkte;
    }

    /**
     * Geef de waarde van gemeente document.
     *
     * @return the gemeenteDocument
     */
    public Lo3GemeenteCode getGemeenteDocument() {
        return gemeenteDocument;
    }

    /**
     * Geef de waarde van datum document.
     *
     * @return the datumDocument
     */
    public Lo3Datum getDatumDocument() {
        return datumDocument;
    }

    /**
     * Geef de waarde van beschrijving document.
     *
     * @return the beschrijvingDocument
     */
    public Lo3String getBeschrijvingDocument() {
        return beschrijvingDocument;
    }

    /**
     * Geef de waarde van rni deelnemer code.
     *
     * @return the rniDeelnemerCode
     */
    public Lo3RNIDeelnemerCode getRniDeelnemerCode() {
        return rniDeelnemerCode;
    }

    /**
     * Geef de waarde van omschrijving verdrag.
     *
     * @return the omschrijvingVerdrag
     */
    public Lo3String getOmschrijvingVerdrag() {
        return omschrijvingVerdrag;
    }

    /**
     * Heeft deze documentatie akte gegevens?.
     *
     * @return true, als gemeente akte of nummer akte gevuld is; anders false
     */
    @Definitie(Definities.DEF043)
    public boolean isAkte() {
        return gemeenteAkte != null && gemeenteAkte.isInhoudelijkGevuld() || nummerAkte != null && nummerAkte.isInhoudelijkGevuld();
    }

    /**
     * Heeft deze documentatie document gegevens?.
     *
     * @return true, als document akte of datum document of beschrijving document gevuld is; anders false
     */
    @Definitie(Definities.DEF042)
    public boolean isDocument() {
        final boolean gemeenteDocumentGevuld = gemeenteDocument != null && gemeenteDocument.isInhoudelijkGevuld();
        final boolean datumDocumentGevuld = datumDocument != null && datumDocument.isInhoudelijkGevuld();
        final boolean beschrijvingDocumentGevuld = beschrijvingDocument != null && beschrijvingDocument.isInhoudelijkGevuld();
        return gemeenteDocumentGevuld || datumDocumentGevuld || beschrijvingDocumentGevuld;
    }

    /**
     * Heeft deze documentatie RNI-deelnemer gegevens?.
     *
     * @return true, als rniDeelnemerCode of omschrijvingVerdrag gevuld is; anders false
     */
    @Definitie({Definities.DEF081, Definities.DEF082 })
    public boolean isRniDeelnemer() {
        return rniDeelnemerCode != null
               && rniDeelnemerCode.isInhoudelijkGevuld()
               || omschrijvingVerdrag != null
               && omschrijvingVerdrag.isInhoudelijkGevuld();
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
                                  .append(nummerAkte, castOther.nummerAkte)
                                  .append(gemeenteDocument, castOther.gemeenteDocument)
                                  .append(datumDocument, castOther.datumDocument)
                                  .append(beschrijvingDocument, castOther.beschrijvingDocument)
                                  .append(rniDeelnemerCode, castOther.rniDeelnemerCode)
                                  .append(omschrijvingVerdrag, castOther.omschrijvingVerdrag)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(gemeenteAkte)
                                    .append(nummerAkte)
                                    .append(gemeenteDocument)
                                    .append(datumDocument)
                                    .append(beschrijvingDocument)
                                    .append(rniDeelnemerCode)
                                    .append(omschrijvingVerdrag)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("gemeenteAkte", gemeenteAkte)
                                                                          .append("nummerAkte", nummerAkte)
                                                                          .append("gemeenteDocument", gemeenteDocument)
                                                                          .append("datumDocument", datumDocument)
                                                                          .append("beschrijvingDocument", beschrijvingDocument)
                                                                          .append("rniDeelnemerCode", rniDeelnemerCode)
                                                                          .append("omschrijvingVerdrag", omschrijvingVerdrag)
                                                                          .toString();
    }
}
