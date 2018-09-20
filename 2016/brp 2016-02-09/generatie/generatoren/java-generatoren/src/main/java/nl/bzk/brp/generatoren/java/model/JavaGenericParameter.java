/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Generic parameter voor een java-type.
 */
public class JavaGenericParameter implements Cloneable {

    private static final int HASHCODE_GETAL1 = 97;
    private static final int HASHCODE_GETAL2 = 73;

    private JavaType javaType;
    private boolean genericParameterSubtypesToegestaan;

    /**
     * Maakt een nieuwe generic parameter.
     *
     * @param javaType type van de generic parameter.
     * @param genericParameterSubtypesToegestaan
     *                 TRUE als subtypes van de parameter zijn toegestaan. Dus of de 'upper bounded' wildcard
     *                 gebruikt dient te worden voor de genericParameter (
     *                 <code><? extends genericParameter></code> of <code><genericParameter></code>).
     */
    public JavaGenericParameter(final JavaType javaType, final boolean genericParameterSubtypesToegestaan) {
        this.javaType = javaType;
        this.genericParameterSubtypesToegestaan = genericParameterSubtypesToegestaan;
    }

    /**
     * Maakt een nieuwe generic parameter.
     *
     * @param javaType type van de generic parameter.
     */
    public JavaGenericParameter(final JavaType javaType) {
        this(javaType, false);
    }

    public JavaType getJavaType() {
        return javaType;
    }

    /**
     * Geeft aan of er ook subtypes voor de generic parameters zijn toegestaan of niet. Dus of de 'upper bounded'
     * wildcard gebruikt dient te worden voor de genericParameter. Het geeft dus aan of het
     * <code><? extends genericParameter></code> moet zijn of <code><genericParameter></code>.
     *
     * @return of er ook subtypes voor de generic parameters zijn toegestaan of niet.
     */
    public boolean isGenericParameterSubtypesToegestaan() {
        return genericParameterSubtypesToegestaan;
    }

    /**
     * Haal de gebruikte types voor de generic parameter op.
     *
     * @return de gebruikte types.
     */
    public List<JavaType> getGebruikteTypes() {
        return javaType.getGebruikteTypes();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        final JavaGenericParameter clone = (JavaGenericParameter) super.clone();
        clone.javaType = (JavaType) this.javaType.clone();
        clone.genericParameterSubtypesToegestaan = this.genericParameterSubtypesToegestaan;
        return clone;
    }

    @Override
    public String toString() {
        String toString = "";
        if (isGenericParameterSubtypesToegestaan()) {
            toString += "? extends ";
        }
        toString += javaType.toString();
        return toString;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASHCODE_GETAL1, HASHCODE_GETAL2)
                .append(javaType).append(genericParameterSubtypesToegestaan).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JavaGenericParameter other = (JavaGenericParameter) obj;
        return new EqualsBuilder()
                .append(this.javaType, other.javaType)
                .append(this.genericParameterSubtypesToegestaan, other.genericParameterSubtypesToegestaan)
                .isEquals();
    }
}
