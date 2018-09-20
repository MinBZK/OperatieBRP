/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.register;

import java.io.Serializable;
import java.util.Date;
import nl.bzk.migratiebrp.util.common.Kopieer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Gemeente uit het gemeente register (immutable, thread-safe).
 */
public final class Gemeente implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String gemeenteCode;
    private final String partijCode;
    private final Date datumOvergangNaarBrp;

    /**
     * Constructor.
     * 
     * @param gemeenteCode
     *            gemeente code
     * @param partijCode
     *            partij code
     * @param datumOvergangNaarBrp
     *            datum overgang naar BRP
     */
    public Gemeente(final String gemeenteCode, final String partijCode, final Date datumOvergangNaarBrp) {
        super();
        this.gemeenteCode = gemeenteCode;
        this.partijCode = partijCode;
        this.datumOvergangNaarBrp = Kopieer.utilDate(datumOvergangNaarBrp);
    }

    /**
     * Geef de waarde van gemeente code.
     *
     * @return gemeente code
     */
    public String getGemeenteCode() {
        return gemeenteCode;
    }

    /**
     * Geef de waarde van partij code.
     *
     * @return partij code
     */
    public String getPartijCode() {
        return partijCode;
    }

    /**
     * Geef de waarde van datum overgang naar brp.
     *
     * @return datum overgang naar brp
     */
    public Date getDatumOvergangNaarBrp() {
        return Kopieer.utilDate(datumOvergangNaarBrp);
    }

    /**
     * Geef het stelsel waarop de gemeente zich *OP DIT MOMENT (new Date())* bevindt.
     * 
     * @return Stelsel.GBA of Stelsel.BRP
     */
    public Stelsel getStelsel() {
        if (datumOvergangNaarBrp == null || datumOvergangNaarBrp.compareTo(new Date()) > 0) {
            return Stelsel.GBA;
        } else {
            return Stelsel.BRP;
        }
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Gemeente)) {
            return false;
        }
        final Gemeente castOther = (Gemeente) other;
        return new EqualsBuilder().append(gemeenteCode, castOther.gemeenteCode)
                                  .append(partijCode, castOther.partijCode)
                                  .append(datumOvergangNaarBrp, castOther.datumOvergangNaarBrp)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(gemeenteCode).append(partijCode).append(datumOvergangNaarBrp).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("gemeenteCode", gemeenteCode)
                                                                          .append("partijCode", partijCode)
                                                                          .append("datumOvergangNaarBrp", datumOvergangNaarBrp)
                                                                          .toString();
    }

}
