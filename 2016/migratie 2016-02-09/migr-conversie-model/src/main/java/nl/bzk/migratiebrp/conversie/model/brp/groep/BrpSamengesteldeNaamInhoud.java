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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de BRP samengestelde naam inhoud.
 *
 * Deze class is immutable en threadsafe.
 *
 */
public final class BrpSamengesteldeNaamInhoud extends AbstractBrpGroepInhoud {
    @Element(name = "predicaatCode", required = false)
    private final BrpPredicaatCode predicaatCode;
    @Element(name = "voornamen", required = false)
    private final BrpString voornamen;
    @Element(name = "voorvoegsel", required = false)
    private final BrpString voorvoegsel;
    @Element(name = "scheidingsteken", required = false)
    private final BrpCharacter scheidingsteken;
    @Element(name = "adellijkeTitelCode", required = false)
    private final BrpAdellijkeTitelCode adellijkeTitelCode;
    @Element(name = "geslachtsnaamstam", required = false)
    private final BrpString geslachtsnaamstam;
    @Element(name = "indicatieNamenreeks", required = true)
    private final BrpBoolean indicatieNamenreeks;
    @Element(name = "indicatieAfgeleid", required = true)
    private final BrpBoolean indicatieAfgeleid;

    /**
     * Maak een BrpSamengesteldeNaamInhoud object.
     *
     * @param predicaatCode
     *            predicaat
     * @param voornamen
     *            voornamen
     * @param voorvoegsel
     *            voorvoegsel
     * @param scheidingsteken
     *            scheidingteken
     * @param adellijkeTitelCode
     *            adellijke titel
     * @param geslachtsnaamstam
     *            geslachtsnaamstam
     * @param indicatieNamenreeks
     *            indicatie namenreeks
     * @param indicatieAfgeleid
     *            indicatie afgeleid
     */
    public BrpSamengesteldeNaamInhoud(
        /* Meer dan 7 parameters is in constructors van immutable model klassen getolereerd. */
        @Element(name = "predicaatCode", required = false) final BrpPredicaatCode predicaatCode,
        @Element(name = "voornamen", required = false) final BrpString voornamen,
        @Element(name = "voorvoegsel", required = false) final BrpString voorvoegsel,
        @Element(name = "scheidingsteken", required = false) final BrpCharacter scheidingsteken,
        @Element(name = "adellijkeTitelCode", required = false) final BrpAdellijkeTitelCode adellijkeTitelCode,
        @Element(name = "geslachtsnaamstam", required = false) final BrpString geslachtsnaamstam,
        @Element(name = "indicatieNamenreeks", required = true) final BrpBoolean indicatieNamenreeks,
        @Element(name = "indicatieAfgeleid", required = true) final BrpBoolean indicatieAfgeleid)
    {
        this.predicaatCode = predicaatCode;
        this.voornamen = voornamen;
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
        this.adellijkeTitelCode = adellijkeTitelCode;
        this.geslachtsnaamstam = geslachtsnaamstam;
        this.indicatieNamenreeks = indicatieNamenreeks;
        this.indicatieAfgeleid = indicatieAfgeleid;
    }

    @Override
    @Preconditie(SoortMeldingCode.PRE022)
    public void valideer() {
        BrpGroepValidator.valideerVoorvoegselScheidingsteken(BrpString.unwrap(voorvoegsel), BrpCharacter.unwrap(scheidingsteken), this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !Validatie.isEenParameterGevuld(predicaatCode, voornamen, voorvoegsel, adellijkeTitelCode, geslachtsnaamstam);
    }

    /**
     * Geef de waarde van predicaat code.
     *
     * @return the predicaatCode
     */
    public BrpPredicaatCode getPredicaatCode() {
        return predicaatCode;
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
     * Geef de waarde van adellijke titel code.
     *
     * @return the adellijkeTitelCode
     */
    public BrpAdellijkeTitelCode getAdellijkeTitelCode() {
        return adellijkeTitelCode;
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
     * Geef de waarde van indicatie namenreeks.
     *
     * @return the indicatieNamenreeks
     */
    public BrpBoolean getIndicatieNamenreeks() {
        return indicatieNamenreeks;
    }

    /**
     * Geef de waarde van indicatie afgeleid.
     *
     * @return the indicatieAfgeleid
     */
    public BrpBoolean getIndicatieAfgeleid() {
        return indicatieAfgeleid;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpSamengesteldeNaamInhoud)) {
            return false;
        }
        final BrpSamengesteldeNaamInhoud castOther = (BrpSamengesteldeNaamInhoud) other;
        return new EqualsBuilder().append(predicaatCode, castOther.predicaatCode)
                                  .append(voornamen, castOther.voornamen)
                                  .append(voorvoegsel, castOther.voorvoegsel)
                                  .append(scheidingsteken, castOther.scheidingsteken)
                                  .append(adellijkeTitelCode, castOther.adellijkeTitelCode)
                                  .append(geslachtsnaamstam, castOther.geslachtsnaamstam)
                                  .append(indicatieNamenreeks, castOther.indicatieNamenreeks)
                                  .append(indicatieAfgeleid, castOther.indicatieAfgeleid)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(predicaatCode)
                                    .append(voornamen)
                                    .append(voorvoegsel)
                                    .append(scheidingsteken)
                                    .append(adellijkeTitelCode)
                                    .append(geslachtsnaamstam)
                                    .append(indicatieNamenreeks)
                                    .append(indicatieAfgeleid)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("predicaatCode", predicaatCode)
                                                                          .append("voornamen", voornamen)
                                                                          .append("voorvoegsel", voorvoegsel)
                                                                          .append("scheidingsteken", scheidingsteken)
                                                                          .append("adellijkeTitelCode", adellijkeTitelCode)
                                                                          .append("geslachtsnaamstam", geslachtsnaamstam)
                                                                          .append("indicatieNamenreeks", indicatieNamenreeks)
                                                                          .append("indicatieAfgeleid", indicatieAfgeleid)
                                                                          .toString();
    }

}
