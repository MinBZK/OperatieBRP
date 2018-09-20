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
 * 37.10 Aanduiding bezit buitenlands reisdocument.
 */
public final class Lo3AanduidingBezitBuitenlandsReisdocument implements Lo3Element, Serializable {
    private static final long serialVersionUID = 1L;

    @Text
    private final Integer code;

    /**
     * Constructor.
     * 
     * @param code
     *            code
     */
    public Lo3AanduidingBezitBuitenlandsReisdocument(@Text final Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3AanduidingBezitBuitenlandsReisdocument)) {
            return false;
        }
        final Lo3AanduidingBezitBuitenlandsReisdocument castOther = (Lo3AanduidingBezitBuitenlandsReisdocument) other;
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
