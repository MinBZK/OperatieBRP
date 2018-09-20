/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de BRP groep Naamgebruik.
 *
 * Deze class is immutable en threadsafe.
 *
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
    private final BrpString geslachtsnaamstam;

    /**
     * Maakt een BrpNaamgebruikInhoud object.
     *
     * @param naamgebruikCode
     *            wijze gebruik geslachtsnaam
     * @param indicatieAfgeleid
     *            indicatie afgeleid
     * @param predicaatCode
     *            predicaat
     * @param adellijkeTitelCode
     *            adellijke titel
     * @param voornamen
     *            voornamen
     * @param voorvoegsel
     *            voorvoegsel
     * @param scheidingsteken
     *            scheidingsteken
     * @param geslachtsnaamstam
     *            geslachtsnaamstam
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
        @Element(name = "geslachtsnaamstam", required = false) final BrpString geslachtsnaamstam)
    {
        this.naamgebruikCode = naamgebruikCode;
        this.indicatieAfgeleid = indicatieAfgeleid;
        this.predicaatCode = predicaatCode;
        this.adellijkeTitelCode = adellijkeTitelCode;
        this.voornamen = voornamen;
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
        this.geslachtsnaamstam = geslachtsnaamstam;
    }

    @Override
    @Preconditie(SoortMeldingCode.PRE022)
    public void valideer() {
        BrpGroepValidator.valideerVoorvoegselScheidingsteken(BrpString.unwrap(voorvoegsel), BrpCharacter.unwrap(scheidingsteken), this);
    }

    /**
     * Geef de waarde van naamgebruik code.
     *
     * @return the naamgebruikCode, of null
     */

    public BrpNaamgebruikCode getNaamgebruikCode() {
        return naamgebruikCode;
    }

    /**
     * Geef de waarde van indicatie afgeleid.
     *
     * @return the indicatieAfgeleid
     */
    public BrpBoolean getIndicatieAfgeleid() {
        return indicatieAfgeleid;
    }

    /**
     * Geef de waarde van predicaat code.
     *
     * @return the predikaatCode
     */
    public BrpPredicaatCode getPredicaatCode() {
        return predicaatCode;
    }

    /**
     * Geef de waarde van adellijke titel code.
     *
     * @return the adellijkeTitelCode
     */
    public BrpAdellijkeTitelCode getAdellijkeTitelCode() {
        return adellijkeTitelCode;
    }

    /**
     * Geef de waarde van voornamen.
     *
     * @return the voornamen
     */
    public BrpString getVoornamen() {
        return voornamen;
    }

    /**
     * Geef de waarde van voorvoegsel.
     *
     * @return the voorvoegsel
     */
    public BrpString getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Geef de waarde van scheidingsteken.
     *
     * @return the scheidingsteken
     */
    public BrpCharacter getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Geef de waarde van geslachtsnaamstam.
     *
     * @return the geslachtsnaamstam
     */
    public BrpString getGeslachtsnaamstam() {
        return geslachtsnaamstam;
    }

    /**
     * Geef de leeg.
     *
     * @return false
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

}
