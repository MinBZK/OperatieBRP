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
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpWijzeGebruikGeslachtsnaamCode;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de BRP groep Aanschrijving.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpAanschrijvingInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "wijzeGebruikGeslachtsnaamCode", required = false)
    private final BrpWijzeGebruikGeslachtsnaamCode wijzeGebruikGeslachtsnaamCode;
    @Element(name = "indicatieAanschrijvenMetTitels", required = false)
    private final Boolean indicatieAanschrijvenMetTitels;
    @Element(name = "indicatieAfgeleid", required = false)
    private final Boolean indicatieAfgeleid;
    @Element(name = "predikaatCode", required = false)
    private final BrpPredikaatCode predikaatCode;
    @Element(name = "adellijkeTitelCode", required = false)
    private final BrpAdellijkeTitelCode adellijkeTitelCode;
    @Element(name = "voornamen", required = false)
    private final String voornamen;
    @Element(name = "voorvoegsel", required = false)
    private final String voorvoegsel;
    @Element(name = "scheidingsteken", required = false)
    private final Character scheidingsteken;
    @Element(name = "geslachtsnaam", required = false)
    private final String geslachtsnaam;

    /**
     * Maakt een BrpAanschrijvingInhoud object.
     * 
     * @param wijzeGebruikGeslachtsnaamCode
     *            wijze gebruik geslachtsnaam
     * @param indicatieAanschrijvenMetTitels
     *            indicatie aanschrijven met adellijke titels en predikaten
     * @param indicatieAfgeleid
     *            indicatie algoritmisch afgeleid
     * @param predikaatCode
     *            predikaat
     * @param adellijkeTitelCode
     *            adellijke titel
     * @param voornamen
     *            voornamen
     * @param voorvoegsel
     *            voorvoegsel
     * @param scheidingsteken
     *            scheidingsteken
     * @param geslachtsnaam
     *            geslachtsnaam
     */
    // CHECKSTYLE:OFF - Meer dan 7 parameters is in constructors van immutable model klassen getolereerd.
    public BrpAanschrijvingInhoud(
            @Element(name = "wijzeGebruikGeslachtsnaamCode", required = false) final BrpWijzeGebruikGeslachtsnaamCode wijzeGebruikGeslachtsnaamCode,
            @Element(name = "indicatieAanschrijvenMetTitels", required = false) final Boolean indicatieAanschrijvenMetTitels,
            @Element(name = "indicatieAfgeleid", required = false) final Boolean indicatieAfgeleid,
            @Element(name = "predikaatCode", required = false) final BrpPredikaatCode predikaatCode,
            @Element(name = "adellijkeTitelCode", required = false) final BrpAdellijkeTitelCode adellijkeTitelCode,
            @Element(name = "voornamen", required = false) final String voornamen,
            @Element(name = "voorvoegsel", required = false) final String voorvoegsel,
            @Element(name = "scheidingsteken", required = false) final Character scheidingsteken,
            @Element(name = "geslachtsnaam", required = false) final String geslachtsnaam) {
        // CHECKSTYLE:ON
        this.wijzeGebruikGeslachtsnaamCode = wijzeGebruikGeslachtsnaamCode;
        this.indicatieAanschrijvenMetTitels = indicatieAanschrijvenMetTitels;
        this.indicatieAfgeleid = indicatieAfgeleid;
        this.predikaatCode = predikaatCode;
        this.adellijkeTitelCode = adellijkeTitelCode;
        this.voornamen = voornamen;
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
        this.geslachtsnaam = geslachtsnaam;
    }

    @Override
    @Preconditie(Precondities.PRE022)
    public void valideer() {
        BrpGroepValidator.valideerVoorvoegselScheidingsteken(voorvoegsel, scheidingsteken, "Aanschrijving");
    }

    /**
     * @return the wijzeGebruikGeslachtsnaamCode, of null
     */

    public BrpWijzeGebruikGeslachtsnaamCode getWijzeGebruikGeslachtsnaamCode() {
        return wijzeGebruikGeslachtsnaamCode;
    }

    /**
     * @return the indicatieAanschrijvenMetTitels
     */
    public Boolean getIndicatieAanschrijvenMetTitels() {
        return indicatieAanschrijvenMetTitels;
    }

    /**
     * @return the indicatieAfgeleid
     */
    public Boolean getIndicatieAfgeleid() {
        return indicatieAfgeleid;
    }

    /**
     * @return the predikaatCode
     */
    public BrpPredikaatCode getPredikaatCode() {
        return predikaatCode;
    }

    /**
     * @return the adellijkeTitelCode
     */
    public BrpAdellijkeTitelCode getAdellijkeTitelCode() {
        return adellijkeTitelCode;
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
     * @return the geslachtsnaam
     */
    public String getGeslachtsnaam() {
        return geslachtsnaam;
    }

    /**
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
        if (!(other instanceof BrpAanschrijvingInhoud)) {
            return false;
        }
        final BrpAanschrijvingInhoud castOther = (BrpAanschrijvingInhoud) other;
        return new EqualsBuilder().append(wijzeGebruikGeslachtsnaamCode, castOther.wijzeGebruikGeslachtsnaamCode)
                .append(indicatieAanschrijvenMetTitels, castOther.indicatieAanschrijvenMetTitels)
                .append(indicatieAfgeleid, castOther.indicatieAfgeleid)
                .append(predikaatCode, castOther.predikaatCode).append(voornamen, castOther.voornamen)
                .append(voorvoegsel, castOther.voorvoegsel).append(scheidingsteken, castOther.scheidingsteken)
                .append(geslachtsnaam, castOther.geslachtsnaam).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(wijzeGebruikGeslachtsnaamCode).append(indicatieAanschrijvenMetTitels)
                .append(indicatieAfgeleid).append(predikaatCode).append(voornamen).append(voorvoegsel)
                .append(scheidingsteken).append(geslachtsnaam).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("wijzeGebruikGeslachtsnaamCode", wijzeGebruikGeslachtsnaamCode)
                .append("indicatieAanschrijvenMetTitels", indicatieAanschrijvenMetTitels)
                .append("indicatieAfgeleid", indicatieAfgeleid).append("predikaatCode", predikaatCode)
                .append("voornamen", voornamen).append("voorvoegsel", voorvoegsel)
                .append("scheidingsteken", scheidingsteken).append("geslachtsnaam", geslachtsnaam).toString();
    }

}
