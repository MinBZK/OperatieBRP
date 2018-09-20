/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.attribuut;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpAttribuut;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Text;

/**
 * Deze class representeert een unieke verwijzing naar de BRP stamtabel 'Reden beëindiging relatie'. Dit is een
 * dynamische stamtabel en dus geen enum maar een class.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpRedenEindeRelatieCode implements BrpAttribuut {

    /**
     * Codering die aangeeft dat de reden van beeinding van een relatie een omzetting van het type van die relatie is.
     */
    public static final BrpRedenEindeRelatieCode OMZETTING = new BrpRedenEindeRelatieCode("Z");

    @Text
    private final String code;

    /**
     * Maakt een BrpRedenEindeRelatieCode.
     * 
     * @param code
     *            de unieke verwijzing naar een rij uit de stamtabel
     * @throws NullPointerException
     *             als code null is
     */
    public BrpRedenEindeRelatieCode(@Text final String code) {
        if (code == null) {
            throw new NullPointerException("code mag niet null zijn");
        }
        this.code = code;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpRedenEindeRelatieCode)) {
            return false;
        }
        final BrpRedenEindeRelatieCode castOther = (BrpRedenEindeRelatieCode) other;
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
