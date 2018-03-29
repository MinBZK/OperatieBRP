/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.brpnaarlo3;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpGroepInhoud;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Groep inhoud voor datum familierechtelijke betrekking.
 */
public final class BrpFamilierechtelijkeBetrekkingInhoud extends AbstractBrpGroepInhoud {
    private final BrpDatum datumFamilierechtelijkeBetrekking;

    /**
     * Maakt een BrpFamilierechtelijkeBetrekkingInhoud object.
     * @param datumFamilierechtelijkeBetrekking datum familierechtelijke betrekking
     */
    public BrpFamilierechtelijkeBetrekkingInhoud(final BrpDatum datumFamilierechtelijkeBetrekking) {
        this.datumFamilierechtelijkeBetrekking = datumFamilierechtelijkeBetrekking;
    }

    @Override
    public boolean isLeeg() {
        return !BrpValidatie.isAttribuutGevuld(datumFamilierechtelijkeBetrekking);
    }

    /**
     * Geef de waarde van datum familierechtelijke betrekking.
     * @return datum familierechtelijke betrekking
     */
    public BrpDatum getDatumFamilierechtelijkeBetrekking() {
        return datumFamilierechtelijkeBetrekking;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpFamilierechtelijkeBetrekkingInhoud)) {
            return false;
        }
        final BrpFamilierechtelijkeBetrekkingInhoud castOther = (BrpFamilierechtelijkeBetrekkingInhoud) other;
        return new EqualsBuilder().append(datumFamilierechtelijkeBetrekking, castOther.datumFamilierechtelijkeBetrekking).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datumFamilierechtelijkeBetrekking).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("datumFamilierechtelijkeBetrekking", datumFamilierechtelijkeBetrekking)
                .toString();
    }
}
