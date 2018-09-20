/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de BRP groep Verblijfsrecht.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpVerblijfsrechtInhoud extends AbstractBrpGroepInhoud {
    @Element(name = "verblijfsrechtCode", required = false)
    private final BrpVerblijfsrechtCode verblijfsrechtCode;
    @Element(name = "aanvangVerblijfsrecht", required = false)
    private final BrpDatum aanvangVerblijfsrecht;
    @Element(name = "voorzienEindeVerblijfsrecht", required = false)
    private final BrpDatum voorzienEindeVerblijfsrecht;
    @Element(name = "aanvangAaneensluitendVerblijfsrecht", required = false)
    private final BrpDatum aanvangAaneensluitendVerblijfsrecht;

    /**
     * Maakt een BrpVerblijfsrechtInhoud object.
     * 
     * @param verblijfsrechtCode
     *            de unieke verblijfsrecht code
     * @param aanvangVerblijfsrecht
     *            de datum aanvang verblijfsrecht
     * @param voorzienEindeVerblijfsrecht
     *            de datum voorzien einde verblijfsrecht
     * @param aanvangAaneensluitendVerblijfsrecht
     *            de datum aanvang aaneensluitend verblijfsrecht
     */
    public BrpVerblijfsrechtInhoud(
            @Element(name = "verblijfsrechtCode", required = false) final BrpVerblijfsrechtCode verblijfsrechtCode,
            @Element(name = "aanvangVerblijfsrecht", required = false) final BrpDatum aanvangVerblijfsrecht,
            @Element(name = "voorzienEindeVerblijfsrecht", required = false) final BrpDatum voorzienEindeVerblijfsrecht,
            @Element(name = "aanvangAaneensluitendVerblijfsrecht", required = false) final BrpDatum aanvangAaneensluitendVerblijfsrecht) {
        this.verblijfsrechtCode = verblijfsrechtCode;
        this.aanvangVerblijfsrecht = aanvangVerblijfsrecht;
        this.voorzienEindeVerblijfsrecht = voorzienEindeVerblijfsrecht;
        this.aanvangAaneensluitendVerblijfsrecht = aanvangAaneensluitendVerblijfsrecht;
    }

    /**
     * @return the verblijfsrechtCode
     */
    public BrpVerblijfsrechtCode getVerblijfsrechtCode() {
        return verblijfsrechtCode;
    }

    /**
     * @return the aanvangVerblijfsrecht
     */
    public BrpDatum getAanvangVerblijfsrecht() {
        return aanvangVerblijfsrecht;
    }

    /**
     * @return the voorzienEindeVerblijfsrecht, of null
     */
    public BrpDatum getVoorzienEindeVerblijfsrecht() {
        return voorzienEindeVerblijfsrecht;
    }

    /**
     * @return the aanvangAaneensluitendVerblijfsrecht, of null
     */
    public BrpDatum getAanvangAaneensluitendVerblijfsrecht() {
        return aanvangAaneensluitendVerblijfsrecht;
    }

    /**
     * @return false, verblijfsrechtCode en aanvangVerblijfsrecht zijn verplicht, deze inhoud kan dus nooit leeg zijn
     */
    @Override
    public boolean isLeeg() {
        return !ValidationUtils.isEenParameterGevuld(verblijfsrechtCode, aanvangVerblijfsrecht,
                voorzienEindeVerblijfsrecht, aanvangAaneensluitendVerblijfsrecht);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpVerblijfsrechtInhoud)) {
            return false;
        }
        final BrpVerblijfsrechtInhoud castOther = (BrpVerblijfsrechtInhoud) other;
        return new EqualsBuilder().append(verblijfsrechtCode, castOther.verblijfsrechtCode)
                .append(aanvangVerblijfsrecht, castOther.aanvangVerblijfsrecht)
                .append(voorzienEindeVerblijfsrecht, castOther.voorzienEindeVerblijfsrecht)
                .append(aanvangAaneensluitendVerblijfsrecht, castOther.aanvangAaneensluitendVerblijfsrecht)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(verblijfsrechtCode).append(aanvangVerblijfsrecht)
                .append(voorzienEindeVerblijfsrecht).append(aanvangAaneensluitendVerblijfsrecht).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("verblijfsrechtCode", verblijfsrechtCode)
                .append("aanvangVerblijfsrecht", aanvangVerblijfsrecht)
                .append("voorzienEindeVerblijfsrecht", voorzienEindeVerblijfsrecht)
                .append("aanvangAaneensluitendVerblijfsrecht", aanvangAaneensluitendVerblijfsrecht).toString();
    }
}
