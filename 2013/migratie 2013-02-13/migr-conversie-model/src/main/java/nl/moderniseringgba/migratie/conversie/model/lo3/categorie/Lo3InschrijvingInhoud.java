/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.categorie;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van LO3 categorie Inschrijving.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class Lo3InschrijvingInhoud implements Lo3CategorieInhoud {

    /* De default waarde de gebruikt is toen groep 80 werd toegevoegd aan bestaande PL-en */
    private static final Integer DEFAULT_VERSIENUMMER = Integer.valueOf(1);
    /* De default waarde de gebruikt is toen groep 80 werd toegevoegd aan bestaande PL-en */
    private static final Lo3Datumtijdstempel DEFAULT_DATUMTIJDSTEMPEL = new Lo3Datumtijdstempel(20070401000000000L);

    // 66 Blokkering
    @Element(name = "datumIngangBlokkering", required = false)
    private final Lo3Datum datumIngangBlokkering;

    // 67 Opschorting
    @Element(name = "datumOpschortingBijhouding", required = false)
    private final Lo3Datum datumOpschortingBijhouding;
    @Element(name = "redenOpschortingBijhoudingCode", required = false)
    private final Lo3RedenOpschortingBijhoudingCode redenOpschortingBijhoudingCode;

    // 68 Opname
    @Element(name = "datumEersteInschrijving", required = false)
    private final Lo3Datum datumEersteInschrijving;

    // 69 Gemeente PK
    @Element(name = "gemeentePKCode", required = false)
    private final Lo3GemeenteCode gemeentePKCode;

    // 70 Geheim
    @Element(name = "indicatieGeheimCode", required = false)
    private final Lo3IndicatieGeheimCode indicatieGeheimCode;

    // 80 Synchroniciteit
    @Element(name = "versienummer", required = false)
    private final Integer versienummer;
    @Element(name = "datumtijdstempel", required = false)
    private final Lo3Datumtijdstempel datumtijdstempel;

    // 87 PK-Conversie
    @Element(name = "indicatiePKVolledigGeconverteerdCode", required = false)
    private final Lo3IndicatiePKVolledigGeconverteerdCode indicatiePKVolledigGeconverteerdCode;

    /**
     * Default constructor (alles null).
     */
    public Lo3InschrijvingInhoud() {
        this(null, null, null, null, null, null, null, null, null);
    }

    /**
     * Maakt een Lo3InschrijvingInhoud object.
     * 
     * @param datumIngangBlokkering
     *            66.20 datum ingang blokkering
     * @param datumOpschortingBijhouding
     *            67.10 datum opschorting bijhouding
     * @param redenOpschortingBijhoudingCode
     *            67.20 reden opschorting bijhouding
     * @param datumEersteInschrijving
     *            68.10 de datum vanaf wanneer een persoon in de GBA is ingeschreven
     * @param gemeentePKCode
     *            69.10 gemeente waar de PK fysiek is opgeslagen
     * @param indicatieGeheimCode
     *            70.10 indicatie geheim
     * @param versienummer
     *            80.10 een nummer waarmee de versie van een PL aangegeven wordt, moet een getal tussen de 0 en 9999
     *            zijn
     * @param datumtijdstempel
     *            80.20 tijdsstempel laatste wijziging
     * @param indicatiePKVolledigGeconverteerdCode
     *            87.10 indicatie PK volledig geconverteerd
     * @throws IllegalArgumentException
     *             als niet aan inhoudelijke voorwaarden is voldaan
     *             {@link Lo3CategorieValidator#valideerCategorie07Inschrijving}
     * @throws NullPointerException
     *             als verplichte velden niet aanwezig zijn
     *             {@link Lo3CategorieValidator#valideerCategorie07Inschrijving}
     */
    // CHECKSTYLE:OFF - Meer dan 7 parameters is in constructors van immutable model klassen getolereerd.
    public Lo3InschrijvingInhoud(
    // CHECKSTYLE:ON
            @Element(name = "datumIngangBlokkering", required = false) final Lo3Datum datumIngangBlokkering,
            @Element(name = "datumOpschortingBijhouding", required = false) final Lo3Datum datumOpschortingBijhouding,
            @Element(name = "redenOpschortingBijhoudingCode", required = false) final Lo3RedenOpschortingBijhoudingCode redenOpschortingBijhoudingCode,
            @Element(name = "datumEersteInschrijving", required = false) final Lo3Datum datumEersteInschrijving,
            @Element(name = "gemeentePKCode", required = false) final Lo3GemeenteCode gemeentePKCode,
            @Element(name = "indicatieGeheimCode", required = false) final Lo3IndicatieGeheimCode indicatieGeheimCode,
            @Element(name = "versienummer", required = false) final Integer versienummer,
            @Element(name = "datumtijdstempel", required = false) final Lo3Datumtijdstempel datumtijdstempel,
            @Element(name = "indicatiePKVolledigGeconverteerdCode", required = false) final Lo3IndicatiePKVolledigGeconverteerdCode indicatiePKVolledigGeconverteerdCode) {
        super();
        this.datumIngangBlokkering = datumIngangBlokkering;
        this.datumOpschortingBijhouding = datumOpschortingBijhouding;
        this.redenOpschortingBijhoudingCode = redenOpschortingBijhoudingCode;
        this.datumEersteInschrijving = datumEersteInschrijving;
        this.gemeentePKCode = gemeentePKCode;
        this.indicatieGeheimCode = indicatieGeheimCode;
        this.versienummer = versienummer;
        this.datumtijdstempel = datumtijdstempel;
        this.indicatiePKVolledigGeconverteerdCode = indicatiePKVolledigGeconverteerdCode;
    }

    @Override
    public boolean isLeeg() {
        return !ValidationUtils.isEenParameterGevuld(datumIngangBlokkering, datumOpschortingBijhouding,
                redenOpschortingBijhoudingCode, datumEersteInschrijving, gemeentePKCode, indicatieGeheimCode,
                versienummer, datumtijdstempel, indicatiePKVolledigGeconverteerdCode);
    }

    /**
     * @return the datumIngangBlokkering
     */
    public Lo3Datum getDatumIngangBlokkering() {
        return datumIngangBlokkering;
    }

    /**
     * @return the datumOpschortingBijhouding
     */
    public Lo3Datum getDatumOpschortingBijhouding() {
        return datumOpschortingBijhouding;
    }

    /**
     * @return the redenOpschortingBijhoudingCode
     */
    public Lo3RedenOpschortingBijhoudingCode getRedenOpschortingBijhoudingCode() {
        return redenOpschortingBijhoudingCode;
    }

    /**
     * @return the datumEersteInschrijving
     */
    public Lo3Datum getDatumEersteInschrijving() {
        return datumEersteInschrijving;
    }

    /**
     * @return the gemeentePKCode
     */
    public Lo3GemeenteCode getGemeentePKCode() {
        return gemeentePKCode;
    }

    /**
     * @return the indicatieGeheimCode
     */
    public Lo3IndicatieGeheimCode getIndicatieGeheimCode() {
        return indicatieGeheimCode;
    }

    /**
     * @return the versienummer
     */
    public Integer getVersienummer() {
        return versienummer;
    }

    /**
     * @return the datumtijdstempel
     */
    public Lo3Datumtijdstempel getDatumtijdstempel() {
        return datumtijdstempel;
    }

    /**
     * @return the indicatiePKVolledigGeconverteerdCode
     */
    public Lo3IndicatiePKVolledigGeconverteerdCode getIndicatiePKVolledigGeconverteerdCode() {
        return indicatiePKVolledigGeconverteerdCode;
    }

    /**
     * @return true als versienummer en datumtijdstempel leeg zijn
     */
    public boolean isGroep80Leeg() {
        return versienummer == null && datumtijdstempel == null;
    }

    /**
     * Maakt een kopie van deze Lo3InschrijvingInhoud en vult groep 80 met default waarden. Als groep 80 wel gevuld is
     * wordt een {@link IllegalStateException} gegooid.
     * 
     * @return een kopie met groep 80 gevuld
     * @throws IllegalStateException
     *             als groep 80 gevuld is
     * @see #isGroep80Leeg()
     */
    public Lo3InschrijvingInhoud maakKopieMetDefaultGroep80() {
        if (!isGroep80Leeg()) {
            throw new IllegalStateException("Groep 80 is niet leeg.");
        }
        return new Lo3InschrijvingInhoud(datumIngangBlokkering, datumOpschortingBijhouding,
                redenOpschortingBijhoudingCode, datumEersteInschrijving, gemeentePKCode, indicatieGeheimCode,
                DEFAULT_VERSIENUMMER, DEFAULT_DATUMTIJDSTEMPEL, indicatiePKVolledigGeconverteerdCode);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3InschrijvingInhoud)) {
            return false;
        }
        final Lo3InschrijvingInhoud castOther = (Lo3InschrijvingInhoud) other;
        return new EqualsBuilder().append(datumIngangBlokkering, castOther.datumIngangBlokkering)
                .append(datumOpschortingBijhouding, castOther.datumOpschortingBijhouding)
                .append(redenOpschortingBijhoudingCode, castOther.redenOpschortingBijhoudingCode)
                .append(datumEersteInschrijving, castOther.datumEersteInschrijving)
                .append(gemeentePKCode, castOther.gemeentePKCode)
                .append(indicatieGeheimCode, castOther.indicatieGeheimCode)
                .append(versienummer, castOther.versienummer).append(datumtijdstempel, castOther.datumtijdstempel)
                .append(indicatiePKVolledigGeconverteerdCode, castOther.indicatiePKVolledigGeconverteerdCode)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datumIngangBlokkering).append(datumOpschortingBijhouding)
                .append(redenOpschortingBijhoudingCode).append(datumEersteInschrijving).append(gemeentePKCode)
                .append(indicatieGeheimCode).append(versienummer).append(datumtijdstempel)
                .append(indicatiePKVolledigGeconverteerdCode).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("datumIngangBlokkering", datumIngangBlokkering)
                .append("datumOpschortingBijhouding", datumOpschortingBijhouding)
                .append("redenOpschortingBijhoudingCode", redenOpschortingBijhoudingCode)
                .append("datumEersteInschrijving", datumEersteInschrijving).append("gemeentePK", gemeentePKCode)
                .append("indicatieGeheimCode", indicatieGeheimCode).append("versienummer", versienummer)
                .append("datumtijdstempel", datumtijdstempel)
                .append("indicatiePKVolledigGeconverteerdCode", indicatiePKVolledigGeconverteerdCode).toString();
    }

    /**
     * Builder.
     */
    public static final class Builder {
        private Lo3Datum datumIngangBlokkering;
        private Lo3Datum datumOpschortingBijhouding;
        private Lo3RedenOpschortingBijhoudingCode redenOpschortingBijhoudingCode;
        private Lo3Datum datumEersteInschrijving;
        private Lo3GemeenteCode gemeentePKCode;
        private Lo3IndicatieGeheimCode indicatieGeheimCode;
        private Integer versienummer;
        private Lo3Datumtijdstempel datumtijdstempel;
        private Lo3IndicatiePKVolledigGeconverteerdCode indicatiePKVolledigGeconverteerdCode;

        /**
         * Maak een lege builder.
         */
        public Builder() {

        }

        /**
         * Maak een builder initieel gevuld met de gegeven inhoud.
         * 
         * @param inhoud
         *            inhoud
         */
        public Builder(final Lo3InschrijvingInhoud inhoud) {
            datumIngangBlokkering = inhoud.datumIngangBlokkering;
            datumOpschortingBijhouding = inhoud.datumOpschortingBijhouding;
            redenOpschortingBijhoudingCode = inhoud.redenOpschortingBijhoudingCode;
            datumEersteInschrijving = inhoud.datumEersteInschrijving;
            gemeentePKCode = inhoud.gemeentePKCode;
            indicatieGeheimCode = inhoud.indicatieGeheimCode;
            versienummer = inhoud.versienummer;
            datumtijdstempel = inhoud.datumtijdstempel;
            indicatiePKVolledigGeconverteerdCode = inhoud.indicatiePKVolledigGeconverteerdCode;
        }

        /**
         * Build.
         * 
         * @return inhoud
         */
        public Lo3InschrijvingInhoud build() {
            return new Lo3InschrijvingInhoud(datumIngangBlokkering, datumOpschortingBijhouding,
                    redenOpschortingBijhoudingCode, datumEersteInschrijving, gemeentePKCode, indicatieGeheimCode,
                    versienummer, datumtijdstempel, indicatiePKVolledigGeconverteerdCode);
        }

        /**
         * @param datumIngangBlokkering
         *            the datumIngangBlokkering to set
         */
        public void setDatumIngangBlokkering(final Lo3Datum datumIngangBlokkering) {
            this.datumIngangBlokkering = datumIngangBlokkering;
        }

        /**
         * @param datumOpschortingBijhouding
         *            the datumOpschortingBijhouding to set
         */
        public void setDatumOpschortingBijhouding(final Lo3Datum datumOpschortingBijhouding) {
            this.datumOpschortingBijhouding = datumOpschortingBijhouding;
        }

        /**
         * @param redenOpschortingBijhoudingCode
         *            the redenOpschortingBijhoudingCode to set
         */
        public void setRedenOpschortingBijhoudingCode(
                final Lo3RedenOpschortingBijhoudingCode redenOpschortingBijhoudingCode) {
            this.redenOpschortingBijhoudingCode = redenOpschortingBijhoudingCode;
        }

        /**
         * @param datumEersteInschrijving
         *            the datumEersteInschrijving to set
         */
        public void setDatumEersteInschrijving(final Lo3Datum datumEersteInschrijving) {
            this.datumEersteInschrijving = datumEersteInschrijving;
        }

        /**
         * @param gemeentePKCode
         *            the gemeentePKCode to set
         */
        public void setGemeentePKCode(final Lo3GemeenteCode gemeentePKCode) {
            this.gemeentePKCode = gemeentePKCode;
        }

        /**
         * @param indicatieGeheimCode
         *            the indicatieGeheimCode to set
         */
        public void setIndicatieGeheimCode(final Lo3IndicatieGeheimCode indicatieGeheimCode) {
            this.indicatieGeheimCode = indicatieGeheimCode;
        }

        /**
         * @param versienummer
         *            the versienummer to set
         */
        public void setVersienummer(final Integer versienummer) {
            this.versienummer = versienummer;
        }

        /**
         * @param datumtijdstempel
         *            the datumtijdstempel to set
         */
        public void setDatumtijdstempel(final Lo3Datumtijdstempel datumtijdstempel) {
            this.datumtijdstempel = datumtijdstempel;
        }

        /**
         * @param indicatiePKVolledigGeconverteerdCode
         *            the indicatiePKVolledigGeconverteerdCode to set
         */
        public void setIndicatiePKVolledigGeconverteerdCode(
                final Lo3IndicatiePKVolledigGeconverteerdCode indicatiePKVolledigGeconverteerdCode) {
            this.indicatiePKVolledigGeconverteerdCode = indicatiePKVolledigGeconverteerdCode;
        }

    }
}
