/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.model.domein.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Initi&euml;le vulling afnemersindicatierstapel entity.
 */
@Embeddable
public class InitVullingAfnemersindicatieStapelPk implements Serializable {
    /**
     * default serial.
     */
    private static final long serialVersionUID = -3985377812361935329L;

    @Column(name = "pl_id")
    private long plId;

    @Column(name = "stapel_nr")
    private short stapelNr;

    /**
     * Default JPA constructor.
     */
    public InitVullingAfnemersindicatieStapelPk() {
        // No operation, default JPA constructor.
    }

    /**
     * Maakt een InitVullingAfnemersindicatieRegelPk object.
     * @param plId de PL id
     * @param stapelNr het stapelnummer
     */
    public InitVullingAfnemersindicatieStapelPk(final long plId, final short stapelNr) {
        setPlId(plId);
        setStapelNr(stapelNr);
    }

    /**
     * Geef de waarde van pl id.
     * @return the plId
     */
    public Long getPlId() {
        return plId;
    }

    /**
     * Zet de waarde van pl id.
     * @param plId the plId to set
     */
    public void setPlId(final Long plId) {
        this.plId = plId;
    }

    /**
     * Geef de waarde van stapel nr.
     * @return the stapelNr
     */
    public short getStapelNr() {
        return stapelNr;
    }

    /**
     * Zet de waarde van stapel nr.
     * @param stapelNr the stapelNr to set
     */
    public void setStapelNr(final short stapelNr) {
        this.stapelNr = stapelNr;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || (this.getClass() != other.getClass())) {
            return false;
        }
        final InitVullingAfnemersindicatieStapelPk castOther = (InitVullingAfnemersindicatieStapelPk) other;
        return new EqualsBuilder().append(getPlId(), castOther.getPlId()).append(getStapelNr(), castOther.getStapelNr()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getPlId()).append(getStapelNr()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("plId", getPlId())
                .append("stapelNr", getStapelNr()).toString();
    }
}
