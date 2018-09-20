/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.element;

import java.io.Serializable;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Element;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Text;

/**
 * 61.10 Aanduiding naamgebruik.
 * 
 */
public final class Lo3AanduidingNaamgebruikCode implements Lo3Element, Serializable {
    private static final long serialVersionUID = 1L;

    @Text
    private final String code;

    /**
     * Constructor.
     * 
     * @param code
     *            code
     */
    public Lo3AanduidingNaamgebruikCode(@Text final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3AanduidingNaamgebruikCode)) {
            return false;
        }
        final Lo3AanduidingNaamgebruikCode castOther = (Lo3AanduidingNaamgebruikCode) other;
        return new EqualsBuilder().append(code, castOther.code).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(code).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("code", code).toString();
    }
}
