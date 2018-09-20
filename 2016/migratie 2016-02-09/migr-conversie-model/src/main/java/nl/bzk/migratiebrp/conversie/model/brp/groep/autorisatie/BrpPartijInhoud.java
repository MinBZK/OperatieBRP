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
 * Deze class representeert de inhoud van de groep BRP Partij.
 * 
 * Deze class is immutable en threadsafe.
 */
public final class BrpPartijInhoud extends AbstractBrpGroepInhoud {
    @Element(name = "datumIngang", required = false)
    private final BrpDatum datumIngang;
    @Element(name = "datumEinde", required = false)
    private final BrpDatum datumEinde;
    @Element(name = "indicatieVerstrekkingsbeperking", required = false)
    private final Boolean indicatieVerstrekkingsbeperking;
    @Element(name = "isGevuld", required = false)
    private final boolean isGevuld;

    /**
     * Maak een nieuw BrpPartijInhoud object.
     * 
     * @param datumIngang
     *            daum ingang
     * @param datumEinde
     *            datum einde
     * @param indicatieVerstrekkingsbeperking
     *            indicatieVerstrekkingsbeperking
     * @param isGevuld
     *            is gevuld
     */
    public BrpPartijInhoud(
        @Element(name = "datumIngang", required = false) final BrpDatum datumIngang,
        @Element(name = "datumEinde", required = false) final BrpDatum datumEinde,
        @Element(name = "indicatieVerstrekkingsbeperking", required = false) final Boolean indicatieVerstrekkingsbeperking,
        @Element(name = "isGevuld", required = false) final boolean isGevuld)
    {
        super();
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.indicatieVerstrekkingsbeperking = indicatieVerstrekkingsbeperking;
        this.isGevuld = isGevuld;
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
     * Geef de waarde van indicatie verstrekkingsbeperking.
     *
     * @return indicatie verstrekkingsbeperking
     */
    public Boolean getIndicatieVerstrekkingsbeperking() {
        return indicatieVerstrekkingsbeperking;
    }

    /**
     * Geef de gevuld.
     *
     * @return gevuld
     */
    public boolean isGevuld() {
        return isGevuld;
    }

    /**
     * Geef de leeg.
     *
     * @return false
     */
    @Override
    public boolean isLeeg() {
        return !isGevuld;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpPartijInhoud)) {
            return false;
        }
        final BrpPartijInhoud castOther = (BrpPartijInhoud) other;
        return new EqualsBuilder().append(datumIngang, castOther.datumIngang)
                                  .append(datumEinde, castOther.datumEinde)
                                  .append(indicatieVerstrekkingsbeperking, castOther.indicatieVerstrekkingsbeperking)
                                  .append(isGevuld, castOther.isGevuld)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datumIngang)
                                    .append(datumEinde)
                                    .append(indicatieVerstrekkingsbeperking)
                                    .append(isGevuld)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("datumIngang", datumIngang)
                                                                          .append("datumEinde", datumEinde)
                                                                          .append(
                                                                              "indicatieVerstrekkingsbeperking",
                                                                              indicatieVerstrekkingsbeperking)
                                                                          .append("isGevuld", isGevuld)
                                                                          .toString();
    }
}
