/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import nl.moderniseringgba.migratie.Preconditie;
import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepValidator;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPredikaatCode;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

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
    @Element(name = "predikaatCode", required = false)
    private final BrpPredikaatCode predikaatCode;
    @Element(name = "voornamen", required = false)
    private final String voornamen;
    @Element(name = "voorvoegsel", required = false)
    private final String voorvoegsel;
    @Element(name = "scheidingsteken", required = false)
    private final Character scheidingsteken;
    @Element(name = "adellijkeTitelCode", required = false)
    private final BrpAdellijkeTitelCode adellijkeTitelCode;
    @Element(name = "geslachtsnaam", required = false)
    private final String geslachtsnaam;
    @Element(name = "indicatieNamenreeks", required = true)
    private final boolean indicatieNamenreeks;
    @Element(name = "indicatieAfgeleid", required = true)
    private final boolean indicatieAfgeleid;

    /**
     * Maak een BrpSamengesteldeNaamInhoud object.
     * 
     * @param predikaatCode
     *            predikaat
     * @param voornamen
     *            voornamen
     * @param voorvoegsel
     *            voorvoegsel
     * @param scheidingsteken
     *            scheidingteken
     * @param adellijkeTitelCode
     *            adellijke titel
     * @param geslachtsnaam
     *            geslachtsnaam
     * @param indicatieNamenreeks
     *            indicatie namenreeks
     * @param indicatieAfgeleid
     *            indicatie afgeleid
     */
    // CHECKSTYLE:OFF - Meer dan 7 parameters is in constructors van immutable model klassen getolereerd.
    public BrpSamengesteldeNaamInhoud(
            @Element(name = "predikaatCode", required = false) final BrpPredikaatCode predikaatCode,
            @Element(name = "voornamen", required = false) final String voornamen,
            @Element(name = "voorvoegsel", required = false) final String voorvoegsel,
            @Element(name = "scheidingsteken", required = false) final Character scheidingsteken,
            @Element(name = "adellijkeTitelCode", required = false) final BrpAdellijkeTitelCode adellijkeTitelCode,
            @Element(name = "geslachtsnaam", required = false) final String geslachtsnaam,
            @Element(name = "indicatieNamenreeks", required = true) final boolean indicatieNamenreeks,
            @Element(name = "indicatieAfgeleid", required = true) final boolean indicatieAfgeleid) {
        // CHECKSTYLE:ON
        this.predikaatCode = predikaatCode;
        this.voornamen = voornamen;
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
        this.adellijkeTitelCode = adellijkeTitelCode;
        this.geslachtsnaam = geslachtsnaam;
        this.indicatieNamenreeks = indicatieNamenreeks;
        this.indicatieAfgeleid = indicatieAfgeleid;
    }

    @Override
    @Preconditie(Precondities.PRE022)
    public void valideer() {
        BrpGroepValidator.valideerVoorvoegselScheidingsteken(voorvoegsel, scheidingsteken, "Samengestelde naam");
    }

    @Override
    public boolean isLeeg() {
        return !ValidationUtils.isEenParameterGevuld(predikaatCode, voornamen, voorvoegsel, adellijkeTitelCode,
                geslachtsnaam);
    }

    /**
     * @return the predikaatCode
     */
    public BrpPredikaatCode getPredikaatCode() {
        return predikaatCode;
    }

    /**
     * @return the voornamen
     */
    public String getVoornamen() {
        return voornamen;
    }

    /**
     * @return the voorvoegsel
     */
    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * @return the scheidingsteken
     */
    public Character getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * @return the adellijkeTitelCode
     */
    public BrpAdellijkeTitelCode getAdellijkeTitelCode() {
        return adellijkeTitelCode;
    }

    /**
     * @return the geslachtsnaam
     */
    public String getGeslachtsnaam() {
        return geslachtsnaam;
    }

    /**
     * @return the indicatieNamenreeks
     */
    public boolean getIndicatieNamenreeks() {
        return indicatieNamenreeks;
    }

    /**
     * @return the indicatieAfgeleid
     */
    public boolean getIndicatieAfgeleid() {
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
        return new EqualsBuilder().append(predikaatCode, castOther.predikaatCode)
                .append(voornamen, castOther.voornamen).append(voorvoegsel, castOther.voorvoegsel)
                .append(scheidingsteken, castOther.scheidingsteken)
                .append(adellijkeTitelCode, castOther.adellijkeTitelCode)
                .append(geslachtsnaam, castOther.geslachtsnaam)
                .append(indicatieNamenreeks, castOther.indicatieNamenreeks)
                .append(indicatieAfgeleid, castOther.indicatieAfgeleid).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(predikaatCode).append(voornamen).append(voorvoegsel)
                .append(scheidingsteken).append(adellijkeTitelCode).append(geslachtsnaam).append(indicatieNamenreeks)
                .append(indicatieAfgeleid).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("predikaatCode", predikaatCode)
                .append("voornamen", voornamen).append("voorvoegsel", voorvoegsel)
                .append("scheidingsteken", scheidingsteken).append("adellijkeTitelCode", adellijkeTitelCode)
                .append("geslachtsnaam", geslachtsnaam).append("indicatieNamenreeks", indicatieNamenreeks)
                .append("indicatieAfgeleid", indicatieAfgeleid).toString();
    }

}
