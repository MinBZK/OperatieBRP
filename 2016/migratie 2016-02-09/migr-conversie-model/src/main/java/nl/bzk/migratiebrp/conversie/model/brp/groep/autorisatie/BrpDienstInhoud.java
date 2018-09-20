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
 * Deze class representeert de inhoud van de groep BRP Dienst.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpDienstInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "datumIngang", required = false)
    private final BrpDatum datumIngang;
    @Element(name = "datumEinde", required = false)
    private final BrpDatum datumEinde;
    @Element(name = "geblokkeerd", required = false)
    private final Boolean geblokkeerd;

    /**
     * Maak een BrpDienstInhoud object.
     *
     * @param datumIngang
     *            datum ingang
     * @param datumEinde
     *            datum einde
     * @param geblokkeerd
     *            geblokkeerd
     */
    public BrpDienstInhoud(
        @Element(name = "datumIngang", required = false) final BrpDatum datumIngang,
        @Element(name = "datumEinde", required = false) final BrpDatum datumEinde,
        @Element(name = "geblokkeerd", required = false) final Boolean geblokkeerd)
    {
        super();
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.geblokkeerd = geblokkeerd;
    }

    /**
     * Geef de waarde van datum ingang.
     *
     * @return datum ingang
     */
    public BrpDatum getDatumIngang() {
        return datumIngang;
    }

    /**
     * Geef de waarde van datum einde.
     *
     * @return datum einde
     */
    public BrpDatum getDatumEinde() {
        return datumEinde;
    }

    /**
     * Geef de waarde van geblokkeerd.
     *
     * @return geblokkeerd
     */
    public Boolean getGeblokkeerd() {
        return geblokkeerd;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpDienstInhoud)) {
            return false;
        }
        final BrpDienstInhoud castOther = (BrpDienstInhoud) other;
        return new EqualsBuilder().append(datumIngang, castOther.datumIngang)
                                  .append(datumEinde, castOther.datumEinde)
                                  .append(geblokkeerd, castOther.geblokkeerd)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datumIngang).append(datumEinde).append(geblokkeerd).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("datumIngang", datumIngang)
                                                                          .append("datumEinde", datumEinde)
                                                                          .append("geblokkeerd", geblokkeerd)
                                                                          .toString();
    }

    @Override
    public boolean isLeeg() {
        return datumIngang == null;
    }
}
