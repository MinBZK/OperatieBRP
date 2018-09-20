/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpGroepInhoud;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de groep BRP Dienst Selectie.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpDienstSelectieInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "eersteSelectieDatum", required = false)
    private final BrpDatum eersteSelectieDatum;
    @Element(name = "selectiePeriodeInMaanden", required = false)
    private final Short selectiePeriodeInMaanden;

    /**
     * Maak een BrpDienstSelectieInhoud object.
     *
     * @param eersteSelectieDatum
     *            eerste selectie datum
     * @param selectiePeriodeInMaanden
     *            selectie periode in maanden
     */
    public BrpDienstSelectieInhoud(@Element(name = "eersteSelectieDatum", required = false) final BrpDatum eersteSelectieDatum, @Element(
            name = "selectiePeriodeInMaanden", required = false) final Short selectiePeriodeInMaanden)
    {
        super();
        this.eersteSelectieDatum = eersteSelectieDatum;
        this.selectiePeriodeInMaanden = selectiePeriodeInMaanden;
    }

    /**
     * Geef de waarde van eerste selectie datum.
     *
     * @return eerste selectie datum
     */
    public BrpDatum getEersteSelectieDatum() {
        return eersteSelectieDatum;
    }

    /**
     * Geef de waarde van selectie periode in maanden.
     *
     * @return selectie periode in maanden
     */
    public Short getSelectiePeriodeInMaanden() {
        return selectiePeriodeInMaanden;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpDienstSelectieInhoud)) {
            return false;
        }
        final BrpDienstSelectieInhoud castOther = (BrpDienstSelectieInhoud) other;
        return new EqualsBuilder().append(eersteSelectieDatum, castOther.eersteSelectieDatum)
                                  .append(selectiePeriodeInMaanden, castOther.selectiePeriodeInMaanden)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(eersteSelectieDatum).append(selectiePeriodeInMaanden).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("eersteSelectieDatum", eersteSelectieDatum)
                                                                          .append("selectiePeriodeInMaanden", selectiePeriodeInMaanden)
                                                                          .toString();
    }

    @Override
    public boolean isLeeg() {
        return selectiePeriodeInMaanden == null;
    }
}
