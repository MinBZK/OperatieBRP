/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikGeslachtsnaamstam;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de BRP groep Naamgebruik.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpNaamgebruikInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "naamgebruikCode", required = false)
    private final BrpNaamgebruikCode naamgebruikCode;
    @Element(name = "indicatieAfgeleid", required = false)
    private final BrpBoolean indicatieAfgeleid;
    @Element(name = "predicaatCode", required = false)
    private final BrpPredicaatCode predicaatCode;
    @Element(name = "adellijkeTitelCode", required = false)
    private final BrpAdellijkeTitelCode adellijkeTitelCode;
    @Element(name = "voornamen", required = false)
    private final BrpString voornamen;
    @Element(name = "voorvoegsel", required = false)
    private final BrpString voorvoegsel;
    @Element(name = "scheidingsteken", required = false)
    private final BrpCharacter scheidingsteken;
    @Element(name = "geslachtsnaamstam", required = false)
    private final BrpNaamgebruikGeslachtsnaamstam geslachtsnaamstam;

    /**
     * Maakt een BrpNaamgebruikInhoud object.
     * @param naamgebruikCode wijze gebruik geslachtsnaam
     * @param indicatieAfgeleid indicatie afgeleid
     * @param predicaatCode predicaat
     * @param adellijkeTitelCode adellijke titel
     * @param voornamen voornamen
     * @param voorvoegsel voorvoegsel
     * @param scheidingsteken scheidingsteken
     * @param geslachtsnaamstam geslachtsnaamstam
     */
    public BrpNaamgebruikInhoud(
        /* Meer dan 7 parameters is in constructors van immutable model klassen getolereerd. */
        @Element(name = "naamgebruikCode", required = false) final BrpNaamgebruikCode naamgebruikCode,
        @Element(name = "indicatieAfgeleid", required = false) final BrpBoolean indicatieAfgeleid,
        @Element(name = "predicaatCode", required = false) final BrpPredicaatCode predicaatCode,
        @Element(name = "adellijkeTitelCode", required = false) final BrpAdellijkeTitelCode adellijkeTitelCode,
        @Element(name = "voornamen", required = false) final BrpString voornamen,
        @Element(name = "voorvoegsel", required = false) final BrpString voorvoegsel,
        @Element(name = "scheidingsteken", required = false) final BrpCharacter scheidingsteken,
        @Element(name = "geslachtsnaamstam", required = false) final BrpNaamgebruikGeslachtsnaamstam geslachtsnaamstam) {
        this.naamgebruikCode = naamgebruikCode;
        this.indicatieAfgeleid = indicatieAfgeleid;
        this.predicaatCode = predicaatCode;
        this.adellijkeTitelCode = adellijkeTitelCode;
        this.voornamen = voornamen;
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
        this.geslachtsnaamstam = geslachtsnaamstam;
    }

    private BrpNaamgebruikInhoud(final BrpNaamgebruikInhoud.Builder builder) {
        naamgebruikCode = builder.naamgebruikCode;
        indicatieAfgeleid = builder.indicatieAfgeleid;
        predicaatCode = builder.predicaatCode;
        adellijkeTitelCode = builder.adellijkeTitelCode;
        voornamen = builder.voornamen;
        voorvoegsel = builder.voorvoegsel;
        scheidingsteken = builder.scheidingsteken;
        geslachtsnaamstam = builder.geslachtsnaamstam;
    }

    @Override
    @Preconditie(SoortMeldingCode.PRE022)
    public void valideer() {
        BrpGroepValidator.valideerVoorvoegselScheidingsteken(BrpString.unwrap(voorvoegsel), BrpCharacter.unwrap(scheidingsteken), this);
    }

    /**
     * Geef de waarde van naamgebruik code van BrpNaamgebruikInhoud.
     * @return de waarde van naamgebruik code van BrpNaamgebruikInhoud
     */

    public BrpNaamgebruikCode getNaamgebruikCode() {
        return naamgebruikCode;
    }

    /**
     * Geef de waarde van indicatie afgeleid van BrpNaamgebruikInhoud.
     * @return de waarde van indicatie afgeleid van BrpNaamgebruikInhoud
     */
    public BrpBoolean getIndicatieAfgeleid() {
        return indicatieAfgeleid;
    }

    /**
     * Geef de waarde van predicaat code van BrpNaamgebruikInhoud.
     * @return de waarde van predicaat code van BrpNaamgebruikInhoud
     */
    public BrpPredicaatCode getPredicaatCode() {
        return predicaatCode;
    }

    /**
     * Geef de waarde van adellijke titel code van BrpNaamgebruikInhoud.
     * @return de waarde van adellijke titel code van BrpNaamgebruikInhoud
     */
    public BrpAdellijkeTitelCode getAdellijkeTitelCode() {
        return adellijkeTitelCode;
    }

    /**
     * Geef de waarde van voornamen van BrpNaamgebruikInhoud.
     * @return de waarde van voornamen van BrpNaamgebruikInhoud
     */
    public BrpString getVoornamen() {
        return voornamen;
    }

    /**
     * Geef de waarde van voorvoegsel van BrpNaamgebruikInhoud.
     * @return de waarde van voorvoegsel van BrpNaamgebruikInhoud
     */
    public BrpString getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Geef de waarde van scheidingsteken van BrpNaamgebruikInhoud.
     * @return de waarde van scheidingsteken van BrpNaamgebruikInhoud
     */
    public BrpCharacter getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Geef de waarde van geslachtsnaamstam van BrpNaamgebruikInhoud.
     * @return de waarde van geslachtsnaamstam van BrpNaamgebruikInhoud
     */
    public BrpNaamgebruikGeslachtsnaamstam getGeslachtsnaamstam() {
        return geslachtsnaamstam;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return false;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpNaamgebruikInhoud)) {
            return false;
        }
        final BrpNaamgebruikInhoud castOther = (BrpNaamgebruikInhoud) other;
        return new EqualsBuilder().append(naamgebruikCode, castOther.naamgebruikCode)
                .append(indicatieAfgeleid, castOther.indicatieAfgeleid)
                .append(predicaatCode, castOther.predicaatCode)
                .append(voornamen, castOther.voornamen)
                .append(voorvoegsel, castOther.voorvoegsel)
                .append(scheidingsteken, castOther.scheidingsteken)
                .append(geslachtsnaamstam, castOther.geslachtsnaamstam)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(naamgebruikCode)
                .append(indicatieAfgeleid)
                .append(predicaatCode)
                .append(voornamen)
                .append(voorvoegsel)
                .append(scheidingsteken)
                .append(geslachtsnaamstam)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("naamgebruikCode", naamgebruikCode)
                .append("indicatieAfgeleid", indicatieAfgeleid)
                .append("predicaatCode", predicaatCode)
                .append("voornamen", voornamen)
                .append("voorvoegsel", voorvoegsel)
                .append("scheidingsteken", scheidingsteken)
                .append("geslachtsnaam", geslachtsnaamstam)
                .toString();
    }

    /**
     * Builder voor een {@link BrpNaamgebruikInhoud}.
     */
    public static final class Builder {
        private final BrpNaamgebruikCode naamgebruikCode;
        private final BrpBoolean indicatieAfgeleid;
        private final BrpNaamgebruikGeslachtsnaamstam geslachtsnaamstam;
        private BrpPredicaatCode predicaatCode;
        private BrpAdellijkeTitelCode adellijkeTitelCode;
        private BrpString voornamen;
        private BrpString voorvoegsel;
        private BrpCharacter scheidingsteken;

        /**
         * Constructor voor de builder met verplichte velden voor deze inhoud.
         * @param naamgebruikCode de naamgebruik code
         * @param indicatieAfgeleid indicatie of het naamgebruik is afgeleid
         * @param geslachtsnaamstam de geslachtsnaam
         */
        public Builder(final BrpNaamgebruikCode naamgebruikCode, final BrpBoolean indicatieAfgeleid, final BrpNaamgebruikGeslachtsnaamstam geslachtsnaamstam) {
            this.naamgebruikCode = naamgebruikCode;
            this.indicatieAfgeleid = indicatieAfgeleid;
            this.geslachtsnaamstam = geslachtsnaamstam;
        }

        /**
         * Zet de predicaat code.
         * @param param een {@link BrpPredicaatCode}
         * @return deze builder instantie
         */
        public Builder predicaatCode(final BrpPredicaatCode param) {
            predicaatCode = param;
            return this;
        }

        /**
         * Zet de adellijke titel code.
         * @param param een {@link BrpAdellijkeTitelCode}
         * @return deze builder instantie
         */
        public Builder adellijkeTitelCode(final BrpAdellijkeTitelCode param) {
            adellijkeTitelCode = param;
            return this;
        }

        /**
         * Zet de voornamen.
         * @param param een {@link BrpString}
         * @return deze builder instantie
         */
        public Builder voornamen(final BrpString param) {
            voornamen = param;
            return this;
        }

        /**
         * Zet het voorvoegsel.
         * @param param een {@link BrpString}
         * @return deze builder instantie
         */
        public Builder voorvoegsel(final BrpString param) {
            voorvoegsel = param;
            return this;
        }

        /**
         * Zet het scheidingsteken.
         * @param param een {@link BrpCharacter}
         * @return deze builder instantie
         */
        public Builder scheidingsteken(final BrpCharacter param) {
            scheidingsteken = param;
            return this;
        }

        /**
         * Bouwt een instantie van {@link BrpNaamgebruikInhoud} op basis van deze builder. Als de verplichte velden niet
         * gevuld zijn, dan wordt er null terug gegeven.
         * @return een gevulde {@link BrpNaamgebruikInhoud}
         */
        public BrpNaamgebruikInhoud build() {
            final boolean isIndicatieAfgeleidGevuld = indicatieAfgeleid != null && indicatieAfgeleid.isInhoudelijkGevuld();
            final boolean isGeslachtsnaamGevuld = geslachtsnaamstam != null && geslachtsnaamstam.isInhoudelijkGevuld();
            final boolean isNaamgebruikGevuld = naamgebruikCode != null && naamgebruikCode.isInhoudelijkGevuld();
            if (isIndicatieAfgeleidGevuld && isGeslachtsnaamGevuld && isNaamgebruikGevuld) {
                return new BrpNaamgebruikInhoud(this);
            }
            return null;
        }
    }
}
