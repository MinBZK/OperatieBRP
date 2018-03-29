/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de BRP Groep Nationaliteit.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpNationaliteitInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "nationaliteitCode")
    private final BrpNationaliteitCode nationaliteitCode;
    @Element(name = "redenVerkrijgingNederlandschapCode")
    private final BrpRedenVerkrijgingNederlandschapCode redenVerkrijgingNederlandschapCode;
    @Element(name = "redenVerliesNederlandschapCode")
    private final BrpRedenVerliesNederlandschapCode redenVerliesNederlandschapCode;
    @Element(name = "eindeBijhouding")
    private final BrpBoolean eindeBijhouding;
    @Element(name = "migratieDatum")
    private final BrpDatum migratieDatum;
    @Element(name = "migratieRedenOpnameNationaliteit")
    private final BrpString migratieRedenOpnameNationaliteit;
    @Element(name = "migratieRedenBeeindigingNationaliteit")
    private final BrpString migratieRedenBeeindigingNationaliteit;

    /**
     * Maakt een BrpNationaliteitInhoud object.
     * @param nationaliteitCode code die een BRP nationaliteit identificeert, mag null zijn
     * @param redenVerkrijgingNederlandschapCode code die een BRP reden verkrijging nederlandschap identificeert, mag null zijn
     * @param redenVerliesNederlandschapCode code de een reden verlies nederlanschap identificeert, mag null zijn
     * @param eindeBijhouding true als de nationaliteit niet meer bijgehouden wordt, anders null
     * @param migratieDatum de inhoud van veld 85.10 als de nationaliteit niet meer bijgehouden wordt, anders null
     * @param migratieRedenOpnameNationaliteit de inhoud van veld 63.10 als de code niet in het BRP voorkomt, anders null
     * @param migratieRedenBeeindigingNationaliteit de inhoud van veld 64.10 als de code niet in het BRP voorkomt, anders null
     */
    public BrpNationaliteitInhoud(
            @Element(name = "nationaliteitCode") final BrpNationaliteitCode nationaliteitCode,
            @Element(name = "redenVerkrijgingNederlandschapCode") final BrpRedenVerkrijgingNederlandschapCode redenVerkrijgingNederlandschapCode,
            @Element(name = "redenVerliesNederlandschapCode") final BrpRedenVerliesNederlandschapCode redenVerliesNederlandschapCode,
            @Element(name = "eindeBijhouding") final BrpBoolean eindeBijhouding,
            @Element(name = "migratieDatum") final BrpDatum migratieDatum,
            @Element(name = "migratieRedenOpnameNationaliteit") final BrpString migratieRedenOpnameNationaliteit,
            @Element(name = "migratieRedenBeeindigingNationaliteit") final BrpString migratieRedenBeeindigingNationaliteit) {
        this.nationaliteitCode = nationaliteitCode;
        this.redenVerkrijgingNederlandschapCode = redenVerkrijgingNederlandschapCode;
        this.redenVerliesNederlandschapCode = redenVerliesNederlandschapCode;
        this.eindeBijhouding = eindeBijhouding;
        this.migratieDatum = migratieDatum;
        this.migratieRedenOpnameNationaliteit = migratieRedenOpnameNationaliteit;
        this.migratieRedenBeeindigingNationaliteit = migratieRedenBeeindigingNationaliteit;
    }

    private BrpNationaliteitInhoud(final Builder builder) {
        nationaliteitCode = builder.nationaliteitCode;
        redenVerkrijgingNederlandschapCode = builder.redenVerkrijgingNederlandschapCode;
        redenVerliesNederlandschapCode = builder.redenVerliesNederlandschapCode;
        eindeBijhouding = builder.eindeBijhouding;
        migratieDatum = builder.migratieDatum;
        migratieRedenOpnameNationaliteit = builder.migratieRedenOpnameNationaliteit;
        migratieRedenBeeindigingNationaliteit = builder.migratieRedenBeeindigingNationaliteit;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        // groep is leeg als nationaliteitCode en redenVerkrijging leeg zijn.
        // redenVerlies is dan wel gevuld
        return !BrpValidatie.isEenParameterGevuld(nationaliteitCode, redenVerkrijgingNederlandschapCode);
    }

    /**
     * Geef de waarde van nationaliteit code van BrpNationaliteitInhoud.
     * @return de waarde van nationaliteit code van BrpNationaliteitInhoud
     */
    public BrpNationaliteitCode getNationaliteitCode() {
        return nationaliteitCode;
    }

    /**
     * Geef de waarde van reden verkrijging nederlandschap code van BrpNationaliteitInhoud.
     * @return de waarde van reden verkrijging nederlandschap code van BrpNationaliteitInhoud
     */
    public BrpRedenVerkrijgingNederlandschapCode getRedenVerkrijgingNederlandschapCode() {
        return redenVerkrijgingNederlandschapCode;
    }

    /**
     * Geef de waarde van reden verlies nederlandschap code van BrpNationaliteitInhoud.
     * @return de waarde van reden verlies nederlandschap code van BrpNationaliteitInhoud
     */
    public BrpRedenVerliesNederlandschapCode getRedenVerliesNederlandschapCode() {
        return redenVerliesNederlandschapCode;
    }

    /**
     * Geef de waarde van migratie datum van BrpNationaliteitInhoud.
     * @return de waarde van migratie datum van BrpNationaliteitInhoud
     */
    public BrpDatum getMigratieDatum() {
        return migratieDatum;
    }

    /**
     * Geef de waarde van einde bijhouding van BrpNationaliteitInhoud.
     * @return de waarde van einde bijhouding van BrpNationaliteitInhoud
     */

    public BrpBoolean getEindeBijhouding() {
        return eindeBijhouding;
    }

    /**
     * Geef de waarde van einde bijhouding van BrpNationaliteitInhoud.
     * @return de waarde van einde bijhouding van BrpNationaliteitInhoud
     */
    public boolean isEindeBijhouding() {
        return BrpBoolean.isTrue(eindeBijhouding);
    }

    /**
     * Geef de waarde van migratie reden beeindiging nationaliteit van BrpNationaliteitInhoud.
     * @return de waarde van migratie reden beeindiging nationaliteit van BrpNationaliteitInhoud
     */
    public BrpString getMigratieRedenBeeindigingNationaliteit() {
        return migratieRedenBeeindigingNationaliteit;
    }

    /**
     * Geef de waarde van migratie reden opname nationaliteit van BrpNationaliteitInhoud.
     * @return de waarde van migratie reden opname nationaliteit van BrpNationaliteitInhoud
     */
    public BrpString getMigratieRedenOpnameNationaliteit() {
        return migratieRedenOpnameNationaliteit;
    }

    /**
     * Vergelijkt de inhoud van deze BrpNationaliteitInhoud met het meegegeven object.
     *
     * Deze logica wijkt in het geval van reden verlies NL nationaliteit af van het standaard equals gedrag. Wanneer
     * BrpNationaliteitInhoud X en Y met elkaar worden vergeleken en X.redenVerliesNederlandschapCode of
     * Y.redenVerliesNederlandschapCode is null, dan wordt deze verder niet meegenomen in de equals vergelijking.
     * @param other het object waarme vergeleken moet worden
     * @return true als het andere object gelijk is aan deze BRP Nationaliteit inhoud, anders false
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpNationaliteitInhoud)) {
            return false;
        }
        final BrpNationaliteitInhoud castOther = (BrpNationaliteitInhoud) other;
        boolean result =
                new EqualsBuilder().append(nationaliteitCode, castOther.nationaliteitCode)
                        .append(redenVerkrijgingNederlandschapCode, castOther.redenVerkrijgingNederlandschapCode)
                        .append(eindeBijhouding, castOther.eindeBijhouding)
                        .append(migratieDatum, castOther.migratieDatum)
                        .append(migratieRedenOpnameNationaliteit, castOther.migratieRedenOpnameNationaliteit)
                        .append(migratieRedenBeeindigingNationaliteit, castOther.migratieRedenBeeindigingNationaliteit)
                        .isEquals();
        /* Uitzondering op standaard gedrag! */
        if (redenVerliesNederlandschapCode != null
                && castOther.redenVerliesNederlandschapCode != null
                && !redenVerliesNederlandschapCode.equals(castOther.redenVerliesNederlandschapCode)) {
            result = false;
        }
        return result;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(nationaliteitCode)
                .append(redenVerkrijgingNederlandschapCode)
                .append(eindeBijhouding)
                .append(migratieDatum)
                .append(migratieRedenOpnameNationaliteit)
                .append(migratieRedenBeeindigingNationaliteit)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("nationaliteitCode", nationaliteitCode)
                .append("redenVerkrijgingNederlandschapCode", redenVerkrijgingNederlandschapCode)
                .append("redenVerliesNederlandschapCode", redenVerliesNederlandschapCode)
                .append("eindeBijhouding", eindeBijhouding)
                .append("migratieDatum", migratieDatum)
                .append("migratieRedenOpnameNationaliteit", migratieRedenOpnameNationaliteit)
                .append(
                        "migratieRedenBeeindigingNationaliteit",
                        migratieRedenBeeindigingNationaliteit)
                .toString();
    }

    /**
     * Builder object voor BrpNationatliteitInhoud.
     */
    public static final class Builder {
        private final BrpNationaliteitCode nationaliteitCode;
        private BrpRedenVerkrijgingNederlandschapCode redenVerkrijgingNederlandschapCode;
        private BrpRedenVerliesNederlandschapCode redenVerliesNederlandschapCode;
        private BrpString migratieRedenOpnameNationaliteit;
        private BrpString migratieRedenBeeindigingNationaliteit;
        private BrpBoolean eindeBijhouding;
        private BrpDatum migratieDatum;

        /**
         * Constructor voor de builder van {@link BrpNationaliteitInhoud} met de verplichte velden.
         * @param nationaliteitCode de nationaliteit code
         */
        public Builder(final BrpNationaliteitCode nationaliteitCode) {
            this.nationaliteitCode = nationaliteitCode;
        }

        /**
         * Constructor voor de builder van {@link BrpNationaliteitInhoud} op basis van een ander
         * {@link BrpNationaliteitInhoud}.
         * @param inhoud brp Nationaliteit inhoud
         */
        public Builder(final BrpNationaliteitInhoud inhoud) {
            this.nationaliteitCode = inhoud.getNationaliteitCode();
            this.redenVerkrijgingNederlandschapCode = inhoud.getRedenVerkrijgingNederlandschapCode();
            this.redenVerliesNederlandschapCode = inhoud.getRedenVerliesNederlandschapCode();
            this.migratieRedenOpnameNationaliteit = inhoud.getMigratieRedenOpnameNationaliteit();
            this.migratieRedenBeeindigingNationaliteit = inhoud.getMigratieRedenBeeindigingNationaliteit();
        }

        /**
         * Zet de reden verkrijging Nederlandschap code.
         * @param param de {@link BrpRedenVerkrijgingNederlandschapCode}
         * @return de {@link Builder} zodat method chaining mogelijk is
         */
        public Builder redenVerkrijgingNederlandschapCode(final BrpRedenVerkrijgingNederlandschapCode param) {
            this.redenVerkrijgingNederlandschapCode = param;
            return this;
        }

        /**
         * Zet de reden verlies Nederlandschap code.
         * @param param de {@link BrpRedenVerliesNederlandschapCode}
         * @return de {@link Builder} zodat method chaining mogelijk is
         */
        public Builder redenVerliesNederlandschapCode(final BrpRedenVerliesNederlandschapCode param) {
            this.redenVerliesNederlandschapCode = param;
            return this;
        }

        /**
         * Zet de migratie reden opname nationaliteit.
         * @param param een {@link BrpString}
         * @return de {@link Builder} zodat method chaining mogelijk is
         */
        public Builder migratieRedenOpnameNationaliteit(final BrpString param) {
            this.migratieRedenOpnameNationaliteit = param;
            return this;
        }

        /**
         * Zet de migratie reden beeindiging nationaliteit.
         * @param param een {@link BrpString}
         * @return de {@link Builder} zodat method chaining mogelijk is
         */
        public Builder migratieRedenBeeindigingNationaliteit(final BrpString param) {
            this.migratieRedenBeeindigingNationaliteit = param;
            return this;
        }

        /**
         * Zet de einde bijhouding.
         * @param param een {@link BrpBoolean}
         * @return de {@link Builder} zodat method chaining mogelijk is
         */
        public Builder eindeBijhouding(final BrpBoolean param) {
            this.eindeBijhouding = param;
            return this;
        }

        /**
         * Zet de migratie datum.
         * @param param een {@link BrpDatum}
         * @return de {@link Builder} zodat method chaining mogelijk is
         */
        public Builder migratieDatum(final BrpDatum param) {
            this.migratieDatum = param;
            return this;
        }

        /**
         * Bouwt een nieuw {@link BrpNationaliteitInhoud}.
         * @return Geeft de {@link BrpNationaliteitInhoud} terug
         */
        public BrpNationaliteitInhoud build() {
            return new BrpNationaliteitInhoud(this);
        }
    }
}
