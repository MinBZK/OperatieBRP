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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de BRP geslachtsnaamcomponent inhoud.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpGeslachtsnaamcomponentInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "voorvoegsel", required = false)
    private final String voorvoegsel;
    @Element(name = "scheidingsteken", required = false)
    private final Character scheidingsteken;
    @Element(name = "naam", required = false)
    private final String naam;
    @Element(name = "predikaatCode", required = false)
    private final BrpPredikaatCode predikaatCode;
    @Element(name = "adellijkeTitelCode", required = false)
    private final BrpAdellijkeTitelCode adellijkeTitelCode;
    @Element(name = "volgnummer", required = false)
    private final int volgnummer;

    /**
     * Maakt een BrpGeslachtsnaamcomponentInhoud object.
     * 
     * @param voorvoegsel
     *            het voorvoegsel, mag null zijn
     * @param scheidingsteken
     *            het scheidingsteken, mag null zijn
     * @param naam
     *            de naam (geslachtsnaam), mag niet null zijn en de lengte minimaal 1 karakter
     * @param predikaatCode
     *            de predikaat code, mag null zijn
     * @param adellijkeTitelCode
     *            de adellijke titel code, mag null zijn
     * @param volgnummer
     *            het volgnummer
     * @throws IllegalArgumentException
     *             als de lengte van naam kleiner is dan 1
     * @throws NullPointerException
     *             als naam null is
     */
    public BrpGeslachtsnaamcomponentInhoud(
            @Element(name = "voorvoegsel", required = false) final String voorvoegsel,
            @Element(name = "scheidingsteken", required = false) final Character scheidingsteken,
            @Element(name = "naam", required = false) final String naam,
            @Element(name = "predikaatCode", required = false) final BrpPredikaatCode predikaatCode,
            @Element(name = "adellijkeTitelCode", required = false) final BrpAdellijkeTitelCode adellijkeTitelCode,
            @Element(name = "volgnummer", required = false) final int volgnummer) {
        if (naam == null) {
            throw new NullPointerException("naam mag niet null zijn");
        }
        if (naam.length() < 1) {
            throw new IllegalArgumentException("de lengte van naam moet groter of gelijk aan 1 zijn");
        }
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
        this.naam = naam;
        this.predikaatCode = predikaatCode;
        this.adellijkeTitelCode = adellijkeTitelCode;
        this.volgnummer = volgnummer;
    }

    @Override
    @Preconditie(Precondities.PRE022)
    public void valideer() {
        BrpGroepValidator.valideerVoorvoegselScheidingsteken(voorvoegsel, scheidingsteken, "Geslachtsnaamcomponent");
    }

    /**
     * @return the voorvoegsel, of null
     */
    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * @return the scheidingsteken, of null
     */
    public Character getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * @return the naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * @return the predikaatCode, of null
     */
    public BrpPredikaatCode getPredikaatCode() {
        return predikaatCode;
    }

    /**
     * @return the adellijkeTitelCode, of null
     */
    public BrpAdellijkeTitelCode getAdellijkeTitelCode() {
        return adellijkeTitelCode;
    }

    /**
     * @return the volgnummer
     */
    public int getVolgnummer() {
        return volgnummer;
    }

    @Override
    public boolean isLeeg() {
        // naam is verplicht dus zal deze inhoud nooit leeg zijn
        return false;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpGeslachtsnaamcomponentInhoud)) {
            return false;
        }
        final BrpGeslachtsnaamcomponentInhoud castOther = (BrpGeslachtsnaamcomponentInhoud) other;
        return new EqualsBuilder().append(voorvoegsel, castOther.voorvoegsel)
                .append(scheidingsteken, castOther.scheidingsteken).append(naam, castOther.naam)
                .append(predikaatCode, castOther.predikaatCode)
                .append(adellijkeTitelCode, castOther.adellijkeTitelCode).append(volgnummer, castOther.volgnummer)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(voorvoegsel).append(scheidingsteken).append(naam).append(predikaatCode)
                .append(adellijkeTitelCode).append(volgnummer).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("voorvoegsel", voorvoegsel).append("scheidingsteken", scheidingsteken).append("naam", naam)
                .append("predikaatCode", predikaatCode).append("adellijkeTitelCode", adellijkeTitelCode)
                .append("volgnummer", volgnummer).toString();
    }
}
