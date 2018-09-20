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
 * Initiele vulling afnemersindicatieregel entity.
 */
@Embeddable
@SuppressWarnings("checkstyle:designforextension")
public class InitVullingAfnemersindicatieRegelPk implements Serializable {
    /**
     * default serial.
     */
    private static final long serialVersionUID = -3985377812361935329L;

    @Column(name = "pl_id")
    private long plId;

    @Column(name = "stapel_nr")
    private short stapelNr;

    @Column(name = "volg_nr")
    private short volgNr;

    /**
     * Default JPA constructor.
     */
    public InitVullingAfnemersindicatieRegelPk() {
    }

    /**
     * Maakt een InitVullingAfnemersindicatieRegelPk object.
     *
     * @param plId
     *            de PL id
     * @param stapelNr
     *            het stapelnummer
     * @param volgNr
     *            het volgnummer
     */
    public InitVullingAfnemersindicatieRegelPk(final long plId, final short stapelNr, final short volgNr) {
        setPlId(plId);
        setStapelNr(stapelNr);
        setVolgNr(volgNr);
    }

    /**
     * Geef de waarde van pl id.
     *
     * @return the plId
     */
    public Long getPlId() {
        return plId;
    }

    /**
     * Zet de waarde van pl id.
     *
     * @param plId
     *            the plId to set
     */
    public void setPlId(final Long plId) {
        this.plId = plId;
    }

    /**
     * Geef de waarde van stapel nr.
     *
     * @return the stapelNr
     */
    public short getStapelNr() {
        return stapelNr;
    }

    /**
     * Zet de waarde van stapel nr.
     *
     * @param stapelNr
     *            the stapelNr to set
     */
    public void setStapelNr(final short stapelNr) {
        this.stapelNr = stapelNr;
    }

    /**
     * Geef de waarde van volg nr.
     *
     * @return the volgNr
     */
    public short getVolgNr() {
        return volgNr;
    }

    /**
     * Zet de waarde van volg nr.
     *
     * @param volgNr
     *            the volgNr to set
     */
    public void setVolgNr(final short volgNr) {
        this.volgNr = volgNr;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InitVullingAfnemersindicatieRegelPk)) {
            return false;
        }
        final InitVullingAfnemersindicatieRegelPk castOther = (InitVullingAfnemersindicatieRegelPk) other;
        return new EqualsBuilder().append(getPlId(), castOther.getPlId())
                                  .append(getStapelNr(), castOther.getStapelNr())
                                  .append(getVolgNr(), castOther.getVolgNr())
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getPlId()).append(getStapelNr()).append(getVolgNr()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("plId", getPlId())
                                                                          .append("stapelNr", getStapelNr())
                                                                          .append("volgNr", getVolgNr())
                                                                          .toString();
    }
}
