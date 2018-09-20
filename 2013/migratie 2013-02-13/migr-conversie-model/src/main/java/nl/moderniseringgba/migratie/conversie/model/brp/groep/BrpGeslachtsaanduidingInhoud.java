/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de BRP inhoud van de groep geslachtsaanduiding.
 * 
 * Deze class is immutable en threadsafe.
 * 
 * 
 * 
 * 
 */
public final class BrpGeslachtsaanduidingInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "geslachtsaanduiding", required = false)
    private final BrpGeslachtsaanduidingCode geslachtsaanduiding;

    /**
     * Maakt een BrpGeslachtsaanduidingInhoud object.
     * 
     * @param geslachtsaanduiding
     *            de BRP geslachtsaanduiding, mag null zijn
     * @throws NullPointerException
     *             als geslachtsaanduiding null is
     */
    public BrpGeslachtsaanduidingInhoud(
            @Element(name = "geslachtsaanduiding", required = false) final BrpGeslachtsaanduidingCode geslachtsaanduiding) {
        this.geslachtsaanduiding = geslachtsaanduiding;
    }

    public BrpGeslachtsaanduidingCode getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }

    @Override
    public boolean isLeeg() {
        return geslachtsaanduiding == null;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpGeslachtsaanduidingInhoud)) {
            return false;
        }
        final BrpGeslachtsaanduidingInhoud castOther = (BrpGeslachtsaanduidingInhoud) other;
        return new EqualsBuilder().append(geslachtsaanduiding, castOther.geslachtsaanduiding).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(geslachtsaanduiding).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("geslachtsaanduiding", geslachtsaanduiding).toString();
    }
}
