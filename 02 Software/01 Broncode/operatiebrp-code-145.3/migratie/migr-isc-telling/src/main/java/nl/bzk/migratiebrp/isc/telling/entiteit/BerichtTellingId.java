/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.entiteit;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import nl.bzk.migratiebrp.util.common.Kopieer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Id klasse voor de berichten tellingen database tabel.
 */
public final class BerichtTellingId implements Serializable {

    private static final long serialVersionUID = 343L;

    private Timestamp datum;
    @Column(name = "bericht_type")
    private String berichtType;
    private String kanaal;

    /**
     * Default constructor.
     */
    public BerichtTellingId() {
        // leeg voor testcode
    }

    /**
     * Convenient constructor.
     * @param berichtType Het berichttype
     * @param datum De datum
     * @param kanaal Het kanaal
     */
    public BerichtTellingId(final Timestamp datum, final String berichtType, final String kanaal) {
        this.datum = Kopieer.timestamp(datum);
        this.berichtType = berichtType;
        this.kanaal = kanaal;
    }

    /**
     * Geef de waarde van bericht type.
     * @return bericht type
     */
    public String getBerichtType() {
        return berichtType;
    }

    /**
     * Geef de waarde van datum.
     * @return datum
     */
    public Timestamp getDatum() {
        return Kopieer.timestamp(datum);
    }

    /**
     * Geef de waarde van kanaal.
     * @return kanaal
     */
    public String getKanaal() {
        return kanaal;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datum).append(berichtType).append(kanaal).toHashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BerichtTellingId)) {
            return false;
        }
        final BerichtTellingId castOther = (BerichtTellingId) other;
        return new EqualsBuilder().append(getDatum(), castOther.getDatum())
                .append(getBerichtType(), castOther.getBerichtType())
                .append(getKanaal(), castOther.getKanaal())
                .isEquals();
    }
}
