/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de BRP Groep Nationaliteit.
 * 
 * Deze class is immutable en threadsafe.
 * 
 * 
 * 
 */
public final class BrpNationaliteitInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "nationaliteitCode", required = false)
    private final BrpNationaliteitCode nationaliteitCode;
    @Element(name = "redenVerkrijgingNederlandschapCode", required = false)
    private final BrpRedenVerkrijgingNederlandschapCode redenVerkrijgingNederlandschapCode;
    @Element(name = "redenVerliesNederlandschapCode", required = false)
    private final BrpRedenVerliesNederlandschapCode redenVerliesNederlandschapCode;
    @Element(name = "eindeBijhouding", required = false)
    private final BrpBoolean eindeBijhouding;
    @Element(name = "migratieDatum", required = false)
    private BrpDatum migratieDatum;
    @Element(name = "migratieRedenOpnameNationaliteit", required = false)
    private BrpString migratieRedenOpnameNationaliteit;
    @Element(name = "migratieRedenBeeindigingNationaliteit", required = false)
    private BrpString migratieRedenBeeindigingNationaliteit;

    /**
     * Maakt een BrpNationaliteitInhoud object.
     * 
     * @param nationaliteitCode
     *            code die een BRP nationaliteit identificeert, mag null zijn
     * @param redenVerkrijgingNederlandschapCode
     *            code die een BRP reden verkrijging nederlandschap identificeert, mag null zijn
     * @param redenVerliesNederlandschapCode
     *            code de een reden verlies nederlanschap identificeert, mag null zijn
     * @param eindeBijhouding
     *            true als de nationaliteit niet meer bijgehouden wordt, anders null
     * @param migratieDatum
     *            de inhoud van veld 85.10 als de nationaliteit niet meer bijgehouden wordt, anders null
     * @param migratieRedenOpnameNationaliteit
     *            de inhoud van veld 63.10 als de code niet in het BRP voorkomt, anders null
     * @param migratieRedenBeeindigingNationaliteit
     *            de inhoud van veld 64.10 als de code niet in het BRP voorkomt, anders null
     */
    public BrpNationaliteitInhoud(
        @Element(name = "nationaliteitCode", required = false) final BrpNationaliteitCode nationaliteitCode,
        @Element(name = "redenVerkrijgingNederlandschapCode", required = false) final BrpRedenVerkrijgingNederlandschapCode redenVerkrijgingNederlandschapCode,
        @Element(name = "redenVerliesNederlandschapCode", required = false) final BrpRedenVerliesNederlandschapCode redenVerliesNederlandschapCode,
        @Element(name = "eindeBijhouding", required = false) final BrpBoolean eindeBijhouding,
        @Element(name = "migratieDatum", required = false) final BrpDatum migratieDatum,
        @Element(name = "migratieRedenOpnameNationaliteit", required = false) final BrpString migratieRedenOpnameNationaliteit,
        @Element(name = "migratieRedenBeeindigingNationaliteit", required = false) final BrpString migratieRedenBeeindigingNationaliteit)
    {
        this.nationaliteitCode = nationaliteitCode;
        this.redenVerkrijgingNederlandschapCode = redenVerkrijgingNederlandschapCode;
        this.redenVerliesNederlandschapCode = redenVerliesNederlandschapCode;
        this.eindeBijhouding = eindeBijhouding;
        this.migratieDatum = migratieDatum;
        this.migratieRedenOpnameNationaliteit = migratieRedenOpnameNationaliteit;
        this.migratieRedenBeeindigingNationaliteit = migratieRedenBeeindigingNationaliteit;
    }

    private BrpNationaliteitInhoud(final Builder builder) {
        this.nationaliteitCode = builder.inhoud.getNationaliteitCode();
        this.redenVerkrijgingNederlandschapCode = builder.inhoud.getRedenVerkrijgingNederlandschapCode();
        this.redenVerliesNederlandschapCode = builder.inhoud.getRedenVerliesNederlandschapCode();
        this.eindeBijhouding = builder.eindeBijhouding;
        this.migratieDatum = builder.migrDatum;
        this.migratieRedenOpnameNationaliteit = builder.inhoud.getMigratieRedenOpnameNationaliteit();
        this.migratieRedenBeeindigingNationaliteit = builder.inhoud.getMigratieRedenBeeindigingNationaliteit();
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
        return !Validatie.isEenParameterGevuld(nationaliteitCode, redenVerkrijgingNederlandschapCode);
    }

    /**
     * Geef de waarde van nationaliteit code.
     *
     * @return nationaliteit code
     */
    public BrpNationaliteitCode getNationaliteitCode() {
        return nationaliteitCode;
    }

    /**
     * Geef de waarde van reden verkrijging nederlandschap code.
     *
     * @return reden verkrijging nederlandschap code
     */
    public BrpRedenVerkrijgingNederlandschapCode getRedenVerkrijgingNederlandschapCode() {
        return redenVerkrijgingNederlandschapCode;
    }

    /**
     * Geef de waarde van reden verlies nederlandschap code.
     *
     * @return reden verlies nederlandschap code
     */
    public BrpRedenVerliesNederlandschapCode getRedenVerliesNederlandschapCode() {
        return redenVerliesNederlandschapCode;
    }

    /**
     * @return geeft de migratiedatum terug
     */
    public BrpDatum getMigratieDatum() {
        return migratieDatum;
    }

    /**
     * @return Geeft de einde bijhouding terug.
     */

    public BrpBoolean getEindeBijhouding() {
        return eindeBijhouding;
    }

    /**
     * @return true als de nationaliteit een einde bijhouding heeft.
     */
    public boolean isEindeBijhouding() {
        return BrpBoolean.isTrue(eindeBijhouding);
    }

    /**
     * @return de reden beeindiging nationaliteit welke niet in BRP voorkomt, maar wel nodig is voor migratie/conversie.
     */
    public BrpString getMigratieRedenBeeindigingNationaliteit() {
        return migratieRedenBeeindigingNationaliteit;
    }

    /**
     * @return de reden opname nationaliteit welke niet in BRP voorkomt, maar wel nodig is voor migratie/conversie.
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
     * 
     * @param other
     *            het object waarme vergeleken moet worden
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
            && !redenVerliesNederlandschapCode.equals(castOther.redenVerliesNederlandschapCode))
        {
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
     * Builder object voor BrpMigratieInhoud.
     */
    public static final class Builder {
        private final BrpNationaliteitInhoud inhoud;
        private final BrpBoolean eindeBijhouding;
        private final BrpDatum migrDatum;

        /**
         * Default constructor met alle verplichte velden.
         *
         * @param brpNationaliteitInhoud
         *            brp Nationaliteit inhoud
         * @param eindeBijhouding
         *            einde bijhouding
         * @param migratieDatum
         *            migratie datum
         */
        public Builder(final BrpNationaliteitInhoud brpNationaliteitInhoud, final BrpBoolean eindeBijhouding, final BrpDatum migratieDatum) {
            this.inhoud = brpNationaliteitInhoud;
            this.eindeBijhouding = eindeBijhouding;
            this.migrDatum = migratieDatum;
        }

        /**
         *
         * @return Geeft de {@link BrpMigratieInhoud} terug
         */
        public BrpNationaliteitInhoud build() {
            return new BrpNationaliteitInhoud(this);
        }
    }
}
