/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.autorisatie;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelGroepAttribuutInhoud;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert het BRP objecttype DienstbundelGroepAttribuut.
 *
 * Deze class is immutable en thread-safe.
 */
public final class BrpDienstbundelGroepAttribuut {

    @Element(name = "attr", required = false)
    private final Integer attribuutId;
    @Element(name = "dienstbundelGroepAttribuutStapel", required = false)
    private final BrpStapel<BrpDienstbundelGroepAttribuutInhoud> dienstbundelGroepAttribuutStapel;

    /**
     * Maak een nieuw DienstbundelGroepAttribuut object.
     *
     * @param attribuutId
     *            het attribuutId
     *
     * @param dienstbundelGroepAttribuutStapel
     *            de dienstbundelgroepattribuut stapels
     */
    public BrpDienstbundelGroepAttribuut(
        @Element(name = "attr", required = false) final Integer attribuutId,
        @Element(name = "dienstbundelGroepAttribuutStapel", required = false) final BrpStapel<BrpDienstbundelGroepAttribuutInhoud> dienstbundelGroepAttribuutStapel)
    {
        super();
        this.attribuutId = attribuutId;
        this.dienstbundelGroepAttribuutStapel = dienstbundelGroepAttribuutStapel;
    }

    /**
     * Geef de waarde van attribuutId.
     *
     * @return attribuutId
     */
    public Integer getAttribuutId() {
        return attribuutId;
    }

    /**
     * Geef de waarde van de stapel van dienstBundelGroepAttributen.
     *
     * @return de stapel van dienstBundelGroepAttributen
     */
    public BrpStapel<BrpDienstbundelGroepAttribuutInhoud> getDienstbundelGroepAttribuutStapel() {
        return dienstbundelGroepAttribuutStapel;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpDienstbundelGroepAttribuut)) {
            return false;
        }
        final BrpDienstbundelGroepAttribuut castOther = (BrpDienstbundelGroepAttribuut) other;
        return new EqualsBuilder().append(attribuutId, castOther.attribuutId)
                                  .append(dienstbundelGroepAttribuutStapel, castOther.dienstbundelGroepAttribuutStapel)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(attribuutId).append(dienstbundelGroepAttribuutStapel).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("attribuutId", attribuutId)
                                                                          .append("dienstBundelGroepAttribuutStapel", dienstbundelGroepAttribuutStapel)
                                                                          .toString();
    }

}
