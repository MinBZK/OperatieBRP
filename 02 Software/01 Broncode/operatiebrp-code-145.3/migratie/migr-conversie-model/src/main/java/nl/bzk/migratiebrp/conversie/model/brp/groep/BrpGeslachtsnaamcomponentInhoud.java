/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de BRP geslachtsnaamcomponent inhoud.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpGeslachtsnaamcomponentInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "voorvoegsel", required = false)
    private final BrpString voorvoegsel;
    @Element(name = "scheidingsteken", required = false)
    private final BrpCharacter scheidingsteken;
    @Element(name = "stam", required = false)
    private final BrpString stam;
    @Element(name = "predicaatCode", required = false)
    private final BrpPredicaatCode predicaatCode;
    @Element(name = "adellijkeTitelCode", required = false)
    private final BrpAdellijkeTitelCode adellijkeTitelCode;
    @Element(name = "volgnummer", required = false)
    private final BrpInteger volgnummer;

    /**
     * Maakt een BrpGeslachtsnaamcomponentInhoud object.
     * @param voorvoegsel het voorvoegsel, mag null zijn
     * @param scheidingsteken het scheidingsteken, mag null zijn
     * @param stam de stam (geslachtsnaam), mag niet null zijn en de lengte minimaal 1 karakter
     * @param predicaatCode de predikaat code, mag null zijn
     * @param adellijkeTitelCode de adellijke titel code, mag null zijn
     * @param volgnummer het volgnummer
     * @throws IllegalArgumentException als de lengte van stam kleiner is dan 1
     * @throws NullPointerException als stam null is
     */
    public BrpGeslachtsnaamcomponentInhoud(
            @Element(name = "voorvoegsel", required = false) final BrpString voorvoegsel,
            @Element(name = "scheidingsteken", required = false) final BrpCharacter scheidingsteken,
            @Element(name = "stam", required = false) final BrpString stam,
            @Element(name = "predicaatCode", required = false) final BrpPredicaatCode predicaatCode,
            @Element(name = "adellijkeTitelCode", required = false) final BrpAdellijkeTitelCode adellijkeTitelCode,
            @Element(name = "volgnummer", required = false) final BrpInteger volgnummer) {
        final String stamAsString = BrpString.unwrap(stam);
        if (stamAsString == null) {
            throw new NullPointerException("stam mag niet null zijn");
        }
        if (stamAsString.length() < 1) {
            throw new IllegalArgumentException("de lengte van stam moet groter of gelijk aan 1 zijn");
        }
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
        this.stam = stam;
        this.predicaatCode = predicaatCode;
        this.adellijkeTitelCode = adellijkeTitelCode;
        this.volgnummer = volgnummer;
    }

    @Override
    @Preconditie(SoortMeldingCode.PRE022)
    public void valideer() {
        BrpGroepValidator.valideerVoorvoegselScheidingsteken(BrpString.unwrap(voorvoegsel), BrpCharacter.unwrap(scheidingsteken), this);
    }

    /**
     * Geef de waarde van voorvoegsel van BrpGeslachtsnaamcomponentInhoud.
     * @return de waarde van voorvoegsel van BrpGeslachtsnaamcomponentInhoud
     */
    public BrpString getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Geef de waarde van scheidingsteken van BrpGeslachtsnaamcomponentInhoud.
     * @return de waarde van scheidingsteken van BrpGeslachtsnaamcomponentInhoud
     */
    public BrpCharacter getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Geef de waarde van stam van BrpGeslachtsnaamcomponentInhoud.
     * @return de waarde van stam van BrpGeslachtsnaamcomponentInhoud
     */
    public BrpString getStam() {
        return stam;
    }

    /**
     * Geef de waarde van predicaat code van BrpGeslachtsnaamcomponentInhoud.
     * @return de waarde van predicaat code van BrpGeslachtsnaamcomponentInhoud
     */
    public BrpPredicaatCode getPredicaatCode() {
        return predicaatCode;
    }

    /**
     * Geef de waarde van adellijke titel code van BrpGeslachtsnaamcomponentInhoud.
     * @return de waarde van adellijke titel code van BrpGeslachtsnaamcomponentInhoud
     */
    public BrpAdellijkeTitelCode getAdellijkeTitelCode() {
        return adellijkeTitelCode;
    }

    /**
     * Geef de waarde van volgnummer van BrpGeslachtsnaamcomponentInhoud.
     * @return de waarde van volgnummer van BrpGeslachtsnaamcomponentInhoud
     */
    public BrpInteger getVolgnummer() {
        return volgnummer;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        // stam is verplicht dus zal deze inhoud nooit leeg zijn
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
                .append(scheidingsteken, castOther.scheidingsteken)
                .append(stam, castOther.stam)
                .append(predicaatCode, castOther.predicaatCode)
                .append(adellijkeTitelCode, castOther.adellijkeTitelCode)
                .append(volgnummer, castOther.volgnummer)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(voorvoegsel)
                .append(scheidingsteken)
                .append(stam)
                .append(predicaatCode)
                .append(adellijkeTitelCode)
                .append(volgnummer)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("voorvoegsel", voorvoegsel)
                .append("scheidingsteken", scheidingsteken)
                .append("stam", stam)
                .append("predicaatCode", predicaatCode)
                .append("adellijkeTitelCode", adellijkeTitelCode)
                .append("volgnummer", volgnummer)
                .toString();
    }
}
