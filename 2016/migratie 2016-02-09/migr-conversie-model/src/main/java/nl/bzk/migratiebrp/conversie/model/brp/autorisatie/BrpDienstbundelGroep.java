/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.autorisatie;

import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelGroepInhoud;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 * Deze class representeert het BRP objecttype DienstbundelGroep.
 *
 * Deze class is immutable en thread-safe.
 */
public final class BrpDienstbundelGroep {

    @Element(name = "groep", required = false)
    private final Integer groepId;
    @ElementList(name = "dienstbundelGroepAttributen", entry = "dienstbundelGroepAttribuut", type = BrpDienstbundelGroepAttribuut.class, required = false)
    private final List<BrpDienstbundelGroepAttribuut> dienstbundelGroepAttributen;
    @Element(name = "dienstbundelGroepStapel", required = false)
    private final BrpStapel<BrpDienstbundelGroepInhoud> dienstbundelGroepStapel;

    /**
     * Maak een nieuw BrpDienstBundelGroep object.
     *
     * @param groepId
     *            groepId
     * @param dienstbundelGroepAttributen
     *            de dienstbundelGroepAttributen
     * @param dienstbundelGroepStapel
     *            de dienstBundelGroep stapels
     */
    public BrpDienstbundelGroep(
        @Element(name = "groep", required = false) final Integer groepId,
        @ElementList(name = "dienstbundelGroepAttributen", entry = "dienstbundelGroepAttribuut", type = BrpDienstbundelGroepAttribuut.class,
                required = false) final List<BrpDienstbundelGroepAttribuut> dienstbundelGroepAttributen, @Element(name = "dienstbundelGroepStapel",
                required = false) final BrpStapel<BrpDienstbundelGroepInhoud> dienstbundelGroepStapel)
    {
        super();
        this.groepId = groepId;
        this.dienstbundelGroepAttributen = dienstbundelGroepAttributen;
        this.dienstbundelGroepStapel = dienstbundelGroepStapel;
    }

    /**
     * Geef de waarde van groepId.
     *
     * @return groepId
     */
    public Integer getGroepId() {
        return groepId;
    }

    /**
     * Geef de waarde van dienstbundelGroepAttributen.
     *
     * @return dienstbundelGroepAttributen
     */
    public List<BrpDienstbundelGroepAttribuut> getDienstbundelGroepAttributen() {
        return dienstbundelGroepAttributen;
    }

    /**
     * Geef de waarde van dienstbundel groep stapel.
     *
     * @return dienstbundel stapel
     */
    public BrpStapel<BrpDienstbundelGroepInhoud> getDienstbundelGroepStapel() {
        return dienstbundelGroepStapel;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpDienstbundelGroep)) {
            return false;
        }
        final BrpDienstbundelGroep castOther = (BrpDienstbundelGroep) other;
        return new EqualsBuilder().append(groepId, castOther.groepId)
                .append(dienstbundelGroepAttributen, castOther.dienstbundelGroepAttributen)
                .append(dienstbundelGroepStapel, castOther.dienstbundelGroepStapel)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(groepId).append(dienstbundelGroepAttributen).append(dienstbundelGroepStapel).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("groepId", groepId)
                .append("dienstbundelGroepAttributen", dienstbundelGroepAttributen)
                .append("dienstBundelGroepStapel", dienstbundelGroepStapel)
                .toString();
    }

}
