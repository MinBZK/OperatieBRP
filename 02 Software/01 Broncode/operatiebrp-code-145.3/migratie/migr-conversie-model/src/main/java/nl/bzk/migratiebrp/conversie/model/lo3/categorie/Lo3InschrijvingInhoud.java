/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.categorie;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Elementnummer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van LO3 categorie Inschrijving.
 *
 * Deze class is immutable en threadsafe.
 */
public final class Lo3InschrijvingInhoud implements Lo3CategorieInhoud {

    /* De default waarde de gebruikt is toen groep 80 werd toegevoegd aan bestaande PL-en */
    private static final Lo3Integer DEFAULT_VERSIENUMMER = new Lo3Integer("0001", null);
    /* De default waarde de gebruikt is toen groep 80 werd toegevoegd aan bestaande PL-en */
    private static final Lo3Datumtijdstempel DEFAULT_DATUMTIJDSTEMPEL = new Lo3Datumtijdstempel(20_070_401_000_000_000L);

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_6620)
    @Element(name = "datumIngangBlokkering", required = false)
    private final Lo3Datum datumIngangBlokkering;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_6710)
    @Element(name = "datumOpschortingBijhouding", required = false)
    private final Lo3Datum datumOpschortingBijhouding;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_6720)
    @Element(name = "redenOpschortingBijhoudingCode", required = false)
    private final Lo3RedenOpschortingBijhoudingCode redenOpschortingBijhoudingCode;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_6810)
    @Element(name = "datumEersteInschrijving", required = false)
    private final Lo3Datum datumEersteInschrijving;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_6910)
    @Element(name = "gemeentePKCode", required = false)
    private final Lo3GemeenteCode gemeentePKCode;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_7010)
    @Element(name = "indicatieGeheimCode", required = false)
    private final Lo3IndicatieGeheimCode indicatieGeheimCode;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_7110)
    @Element(name = "datumVerificatie", required = false)
    private final Lo3Datum datumVerificatie;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_7120)
    @Element(name = "omschrijvingVerificatie", required = false)
    private final Lo3String omschrijvingVerificatie;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_8010)
    @Element(name = "versienummer", required = false)
    private final Lo3Integer versienummer;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_8020)
    @Element(name = "datumtijdstempel", required = false)
    private final Lo3Datumtijdstempel datumtijdstempel;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_8710)
    @Element(name = "indicatiePKVolledigGeconverteerdCode", required = false)
    private final Lo3IndicatiePKVolledigGeconverteerdCode indicatiePKVolledigGeconverteerdCode;

    /**
     * Default constructor (alles null).
     */
    public Lo3InschrijvingInhoud() {
        this(null, null, null, null, null, null, null, null, null, null, null);
    }

    /**
     * Maakt een Lo3InschrijvingInhoud object.
     * @param datumIngangBlokkering 66.20 datum ingang blokkering
     * @param datumOpschortingBijhouding 67.10 datum opschorting bijhouding
     * @param redenOpschortingBijhoudingCode 67.20 reden opschorting bijhouding
     * @param datumEersteInschrijving 68.10 de datum vanaf wanneer een persoon in de GBA is ingeschreven
     * @param gemeentePKCode 69.10 gemeente waar de PK fysiek is opgeslagen
     * @param indicatieGeheimCode 70.10 indicatie geheim
     * @param datumVerificatie 71.10 datum verificatie
     * @param omschrijvingVerificatie 71.20 omschrijving verificatie
     * @param versienummer 80.10 een nummer waarmee de versie van een PL aangegeven wordt, moet een getal tussen de 0 en 9999 zijn
     * @param datumtijdstempel 80.20 tijdsstempel laatste wijziging
     * @param indicatiePKVolledigGeconverteerdCode 87.10 indicatie PK volledig geconverteerd
     */
    public Lo3InschrijvingInhoud(
        /* Meer dan 7 parameters is in constructors van immutable model klassen getolereerd. */
        @Element(name = "datumIngangBlokkering", required = false) final Lo3Datum datumIngangBlokkering,
        @Element(name = "datumOpschortingBijhouding", required = false) final Lo3Datum datumOpschortingBijhouding,
        @Element(name = "redenOpschortingBijhoudingCode", required = false) final Lo3RedenOpschortingBijhoudingCode redenOpschortingBijhoudingCode,
        @Element(name = "datumEersteInschrijving", required = false) final Lo3Datum datumEersteInschrijving,
        @Element(name = "gemeentePKCode", required = false) final Lo3GemeenteCode gemeentePKCode,
        @Element(name = "indicatieGeheimCode", required = false) final Lo3IndicatieGeheimCode indicatieGeheimCode,
        @Element(name = "datumVerificatie", required = false) final Lo3Datum datumVerificatie,
        @Element(name = "omschrijvingVerificatie", required = false) final Lo3String omschrijvingVerificatie,
        @Element(name = "versienummer", required = false) final Lo3Integer versienummer,
        @Element(name = "datumtijdstempel", required = false) final Lo3Datumtijdstempel datumtijdstempel,
        @Element(name = "indicatiePKVolledigGeconverteerdCode",
                required = false) final Lo3IndicatiePKVolledigGeconverteerdCode indicatiePKVolledigGeconverteerdCode) {
        super();
        this.datumIngangBlokkering = datumIngangBlokkering;
        this.datumOpschortingBijhouding = datumOpschortingBijhouding;
        this.redenOpschortingBijhoudingCode = redenOpschortingBijhoudingCode;
        this.datumEersteInschrijving = datumEersteInschrijving;
        this.gemeentePKCode = gemeentePKCode;
        this.indicatieGeheimCode = indicatieGeheimCode;
        this.datumVerificatie = datumVerificatie;
        this.omschrijvingVerificatie = omschrijvingVerificatie;
        this.versienummer = versienummer;
        this.datumtijdstempel = datumtijdstempel;
        this.indicatiePKVolledigGeconverteerdCode = indicatiePKVolledigGeconverteerdCode;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.lo3.Lo3CategorieInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !Lo3Validatie.isEenParameterGevuld(
                datumIngangBlokkering,
                datumOpschortingBijhouding,
                redenOpschortingBijhoudingCode,
                datumEersteInschrijving,
                gemeentePKCode,
                indicatieGeheimCode,
                datumVerificatie,
                omschrijvingVerificatie,
                versienummer,
                datumtijdstempel,
                indicatiePKVolledigGeconverteerdCode);
    }

    /**
     * Geef de waarde van datum ingang blokkering van Lo3InschrijvingInhoud.
     * @return de waarde van datum ingang blokkering van Lo3InschrijvingInhoud
     */
    public Lo3Datum getDatumIngangBlokkering() {
        return datumIngangBlokkering;
    }

    /**
     * Geef de waarde van datum opschorting bijhouding van Lo3InschrijvingInhoud.
     * @return de waarde van datum opschorting bijhouding van Lo3InschrijvingInhoud
     */
    public Lo3Datum getDatumOpschortingBijhouding() {
        return datumOpschortingBijhouding;
    }

    /**
     * Geef de waarde van reden opschorting bijhouding code van Lo3InschrijvingInhoud.
     * @return de waarde van reden opschorting bijhouding code van Lo3InschrijvingInhoud
     */
    public Lo3RedenOpschortingBijhoudingCode getRedenOpschortingBijhoudingCode() {
        return redenOpschortingBijhoudingCode;
    }

    /**
     * Geef de waarde van datum eerste inschrijving van Lo3InschrijvingInhoud.
     * @return de waarde van datum eerste inschrijving van Lo3InschrijvingInhoud
     */
    public Lo3Datum getDatumEersteInschrijving() {
        return datumEersteInschrijving;
    }

    /**
     * Geef de waarde van gemeente pk code van Lo3InschrijvingInhoud.
     * @return de waarde van gemeente pk code van Lo3InschrijvingInhoud
     */
    public Lo3GemeenteCode getGemeentePKCode() {
        return gemeentePKCode;
    }

    /**
     * Geef de waarde van indicatie geheim code van Lo3InschrijvingInhoud.
     * @return de waarde van indicatie geheim code van Lo3InschrijvingInhoud
     */
    public Lo3IndicatieGeheimCode getIndicatieGeheimCode() {
        return indicatieGeheimCode;
    }

    /**
     * Geef de waarde van datum verificatie van Lo3InschrijvingInhoud.
     * @return de waarde van datum verificatie van Lo3InschrijvingInhoud
     */
    public Lo3Datum getDatumVerificatie() {
        return datumVerificatie;
    }

    /**
     * Geef de waarde van omschrijving verificatie van Lo3InschrijvingInhoud.
     * @return de waarde van omschrijving verificatie van Lo3InschrijvingInhoud
     */
    public Lo3String getOmschrijvingVerificatie() {
        return omschrijvingVerificatie;
    }

    /**
     * Geef de waarde van versienummer van Lo3InschrijvingInhoud.
     * @return de waarde van versienummer van Lo3InschrijvingInhoud
     */
    public Lo3Integer getVersienummer() {
        return versienummer;
    }

    /**
     * Geef de waarde van datumtijdstempel van Lo3InschrijvingInhoud.
     * @return de waarde van datumtijdstempel van Lo3InschrijvingInhoud
     */
    public Lo3Datumtijdstempel getDatumtijdstempel() {
        return datumtijdstempel;
    }

    /**
     * Geef de waarde van indicatie pk volledig geconverteerd code van Lo3InschrijvingInhoud.
     * @return de waarde van indicatie pk volledig geconverteerd code van Lo3InschrijvingInhoud
     */
    public Lo3IndicatiePKVolledigGeconverteerdCode getIndicatiePKVolledigGeconverteerdCode() {
        return indicatiePKVolledigGeconverteerdCode;
    }

    /**
     * Geef de waarde van groep80 leeg van Lo3InschrijvingInhoud.
     * @return de waarde van groep80 leeg van Lo3InschrijvingInhoud
     */
    public boolean isGroep80Leeg() {
        return versienummer == null && datumtijdstempel == null;
    }

    /**
     * Geef de waarde van groep71 leeg van Lo3InschrijvingInhoud.
     * @return de waarde van groep71 leeg van Lo3InschrijvingInhoud
     */
    public boolean isGroep71Leeg() {
        return datumVerificatie == null && omschrijvingVerificatie == null;
    }

    /**
     * Geef de waarde van opgeschort van Lo3InschrijvingInhoud.
     * @return de waarde van opgeschort van Lo3InschrijvingInhoud
     */
    public boolean isOpgeschort() {
        return datumOpschortingBijhouding != null || redenOpschortingBijhoudingCode != null;
    }

    /**
     * Maakt een kopie van deze Lo3InschrijvingInhoud en vult groep 80 met default waarden. Als groep 80 wel gevuld is
     * wordt een {@link IllegalStateException} gegooid.
     * @return een kopie met groep 80 gevuld
     * @throws IllegalStateException als groep 80 gevuld is
     * @see #isGroep80Leeg()
     */
    public Lo3InschrijvingInhoud maakKopieMetDefaultGroep80() {
        if (!isGroep80Leeg()) {
            throw new IllegalStateException("Groep 80 is niet leeg.");
        }
        return new Lo3InschrijvingInhoud(
                datumIngangBlokkering,
                datumOpschortingBijhouding,
                redenOpschortingBijhoudingCode,
                datumEersteInschrijving,
                gemeentePKCode,
                indicatieGeheimCode,
                datumVerificatie,
                omschrijvingVerificatie,
                DEFAULT_VERSIENUMMER,
                DEFAULT_DATUMTIJDSTEMPEL,
                indicatiePKVolledigGeconverteerdCode);
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
                .append(datumVerificatie, castOther.datumVerificatie)
                .append(omschrijvingVerificatie, castOther.omschrijvingVerificatie)
                .append(versienummer, castOther.versienummer)
                .append(datumtijdstempel, castOther.datumtijdstempel)
                .append(indicatiePKVolledigGeconverteerdCode, castOther.indicatiePKVolledigGeconverteerdCode)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datumIngangBlokkering)
                .append(datumOpschortingBijhouding)
                .append(redenOpschortingBijhoudingCode)
                .append(datumEersteInschrijving)
                .append(gemeentePKCode)
                .append(indicatieGeheimCode)
                .append(datumVerificatie)
                .append(omschrijvingVerificatie)
                .append(versienummer)
                .append(datumtijdstempel)
                .append(indicatiePKVolledigGeconverteerdCode)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("datumIngangBlokkering", datumIngangBlokkering)
                .append("datumOpschortingBijhouding", datumOpschortingBijhouding)
                .append("redenOpschortingBijhoudingCode", redenOpschortingBijhoudingCode)
                .append("datumEersteInschrijving", datumEersteInschrijving)
                .append("gemeentePK", gemeentePKCode)
                .append("indicatieGeheimCode", indicatieGeheimCode)
                .append("datumVerificatie", datumVerificatie)
                .append("omschrijvingVerificatie", omschrijvingVerificatie)
                .append("versienummer", versienummer)
                .append("datumtijdstempel", datumtijdstempel)
                .append(
                        "indicatiePKVolledigGeconverteerdCode",
                        indicatiePKVolledigGeconverteerdCode)
                .toString();
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
        private Lo3Datum datumVerificatie;
        private Lo3String omschrijvingVerificatie;
        private Lo3Integer versienummer;
        private Lo3Datumtijdstempel datumtijdstempel;
        private Lo3IndicatiePKVolledigGeconverteerdCode indicatiePKVolledigGeconverteerdCode;

        /**
         * Maak een lege builder.
         */
        public Builder() {
            //lege builder
        }

        /**
         * Maak een builder initieel gevuld met de gegeven inhoud.
         * @param inhoud inhoud
         */
        public Builder(final Lo3InschrijvingInhoud inhoud) {
            datumIngangBlokkering = inhoud.datumIngangBlokkering;
            datumOpschortingBijhouding = inhoud.datumOpschortingBijhouding;
            redenOpschortingBijhoudingCode = inhoud.redenOpschortingBijhoudingCode;
            datumEersteInschrijving = inhoud.datumEersteInschrijving;
            gemeentePKCode = inhoud.gemeentePKCode;
            indicatieGeheimCode = inhoud.indicatieGeheimCode;
            datumVerificatie = inhoud.datumVerificatie;
            omschrijvingVerificatie = inhoud.omschrijvingVerificatie;
            versienummer = inhoud.versienummer;
            datumtijdstempel = inhoud.datumtijdstempel;
            indicatiePKVolledigGeconverteerdCode = inhoud.indicatiePKVolledigGeconverteerdCode;
        }

        /**
         * Build.
         * @return inhoud
         */
        public Lo3InschrijvingInhoud build() {
            return new Lo3InschrijvingInhoud(
                    datumIngangBlokkering,
                    datumOpschortingBijhouding,
                    redenOpschortingBijhoudingCode,
                    datumEersteInschrijving,
                    gemeentePKCode,
                    indicatieGeheimCode,
                    datumVerificatie,
                    omschrijvingVerificatie,
                    versienummer,
                    datumtijdstempel,
                    indicatiePKVolledigGeconverteerdCode);
        }

        /**
         * Zet de waarden voor datum ingang blokkering van Lo3InschrijvingInhoud.
         * @param datumIngangBlokkering de nieuwe waarde voor datum ingang blokkering van Lo3InschrijvingInhoud
         */
        public void setDatumIngangBlokkering(final Lo3Datum datumIngangBlokkering) {
            this.datumIngangBlokkering = datumIngangBlokkering;
        }

        /**
         * Zet de waarden voor datum opschorting bijhouding van Lo3InschrijvingInhoud.
         * @param datumOpschortingBijhouding de nieuwe waarde voor datum opschorting bijhouding van Lo3InschrijvingInhoud
         */
        public void setDatumOpschortingBijhouding(final Lo3Datum datumOpschortingBijhouding) {
            this.datumOpschortingBijhouding = datumOpschortingBijhouding;
        }

        /**
         * Zet de waarden voor reden opschorting bijhouding code van Lo3InschrijvingInhoud.
         * @param redenOpschortingBijhoudingCode de nieuwe waarde voor reden opschorting bijhouding code van Lo3InschrijvingInhoud
         */
        public void setRedenOpschortingBijhoudingCode(final Lo3RedenOpschortingBijhoudingCode redenOpschortingBijhoudingCode) {
            this.redenOpschortingBijhoudingCode = redenOpschortingBijhoudingCode;
        }

        /**
         * Zet de waarden voor datum eerste inschrijving van Lo3InschrijvingInhoud.
         * @param datumEersteInschrijving de nieuwe waarde voor datum eerste inschrijving van Lo3InschrijvingInhoud
         */
        public void setDatumEersteInschrijving(final Lo3Datum datumEersteInschrijving) {
            this.datumEersteInschrijving = datumEersteInschrijving;
        }

        /**
         * Zet de waarden voor gemeente pk code van Lo3InschrijvingInhoud.
         * @param gemeentePKCode de nieuwe waarde voor gemeente pk code van Lo3InschrijvingInhoud
         */
        public void setGemeentePKCode(final Lo3GemeenteCode gemeentePKCode) {
            this.gemeentePKCode = gemeentePKCode;
        }

        /**
         * Zet de waarden voor indicatie geheim code van Lo3InschrijvingInhoud.
         * @param indicatieGeheimCode de nieuwe waarde voor indicatie geheim code van Lo3InschrijvingInhoud
         */
        public void setIndicatieGeheimCode(final Lo3IndicatieGeheimCode indicatieGeheimCode) {
            this.indicatieGeheimCode = indicatieGeheimCode;
        }

        /**
         * Zet de waarden voor datum verificatie van Lo3InschrijvingInhoud.
         * @param datumVerificatie de nieuwe waarde voor datum verificatie van Lo3InschrijvingInhoud
         */
        public void setDatumVerificatie(final Lo3Datum datumVerificatie) {
            this.datumVerificatie = datumVerificatie;
        }

        /**
         * Zet de waarden voor omschrijving verificatie van Lo3InschrijvingInhoud.
         * @param omschrijvingVerificatie de nieuwe waarde voor omschrijving verificatie van Lo3InschrijvingInhoud
         */
        public void setOmschrijvingVerificatie(final Lo3String omschrijvingVerificatie) {
            this.omschrijvingVerificatie = omschrijvingVerificatie;
        }

        /**
         * Zet de waarden voor versienummer van Lo3InschrijvingInhoud.
         * @param versienummer de nieuwe waarde voor versienummer van Lo3InschrijvingInhoud
         */
        public void setVersienummer(final Lo3Integer versienummer) {
            this.versienummer = versienummer;
        }

        /**
         * Zet de waarden voor datumtijdstempel van Lo3InschrijvingInhoud.
         * @param datumtijdstempel de nieuwe waarde voor datumtijdstempel van Lo3InschrijvingInhoud
         */
        public void setDatumtijdstempel(final Lo3Datumtijdstempel datumtijdstempel) {
            this.datumtijdstempel = datumtijdstempel;
        }

        /**
         * Zet de waarden voor indicatie pk volledig geconverteerd code van Lo3InschrijvingInhoud.
         * @param indicatiePKVolledigGeconverteerdCode de nieuwe waarde voor indicatie pk volledig geconverteerd code van Lo3InschrijvingInhoud
         */
        public void setIndicatiePKVolledigGeconverteerdCode(final Lo3IndicatiePKVolledigGeconverteerdCode indicatiePKVolledigGeconverteerdCode) {
            this.indicatiePKVolledigGeconverteerdCode = indicatiePKVolledigGeconverteerdCode;
        }
    }
}
