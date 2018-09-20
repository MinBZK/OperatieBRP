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
 * Deze class representeert de inhoud van de groep BRP ToegangLeveringsAutorisatie.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpToegangLeveringsautorisatieInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "afleverpunt", required = false)
    private final String afleverpunt;
    @Element(name = "datumIngang", required = false)
    private final BrpDatum datumIngang;
    @Element(name = "datumEinde", required = false)
    private final BrpDatum datumEinde;
    @Element(name = "indicatieGeblokkeerd", required = false)
    private final Boolean indicatieGeblokkeerd;
    @Element(name = "naderePopulatiebeperking", required = false)
    private final String naderePopulatiebeperking;

    /**
     * Maak een nieuw BrpLeveringsAutorisatieInhoud object.
     *
     * @param afleverpunt
     *            afleverpunt
     * @param datumIngang
     *            datumIngang
     * @param datumEinde
     *            datumEinde
     * @param indicatieGeblokkeerd
     *            indicatieGeblokkeerd
     * @param naderePopulatiebeperking
     *            naderePopulatiebeperking
     */
    public BrpToegangLeveringsautorisatieInhoud(@Element(name = "afleverpunt", required = false) final String afleverpunt, @Element(name = "datumIngang",
            required = false) final BrpDatum datumIngang, @Element(name = "datumEinde", required = false) final BrpDatum datumEinde, @Element(
            name = "indicatieGeblokkeerd", required = false) final Boolean indicatieGeblokkeerd, @Element(name = "naderePopulatiebeperking",
            required = false) final String naderePopulatiebeperking)
    {
        super();
        this.afleverpunt = afleverpunt;
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
        this.naderePopulatiebeperking = naderePopulatiebeperking;
    }

    /**
     * Geef de waarde van afleverpunt.
     *
     * @return afleverpunt.
     */
    public String getAfleverpunt() {
        return afleverpunt;
    }

    /**
     * Geef de waarde van datumIngang.
     *
     * @return datumIngang.
     */
    public BrpDatum getDatumIngang() {
        return datumIngang;
    }

    /**
     * Geef de waarde van datumEinde.
     *
     * @return datumEinde.
     */
    public BrpDatum getDatumEinde() {
        return datumEinde;
    }

    /**
     * Geef de waarde van indicatieGeblokkeerd.
     *
     * @return indicatieGeblokkeerd.
     */
    public Boolean getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Geef de waarde van naderePopulatiebeperking.
     *
     * @return naderePopulatiebeperking.
     */
    public String getNaderePopulatiebeperking() {
        return naderePopulatiebeperking;
    }

    /**
     * Geef de leeg.
     *
     * @return false
     */
    @Override
    public boolean isLeeg() {
        return datumIngang == null;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpToegangLeveringsautorisatieInhoud)) {
            return false;
        }
        final BrpToegangLeveringsautorisatieInhoud castOther = (BrpToegangLeveringsautorisatieInhoud) other;
        return new EqualsBuilder().append(afleverpunt, castOther.afleverpunt)
                                  .append(datumIngang, castOther.datumIngang)
                                  .append(datumEinde, castOther.datumEinde)
                                  .append(indicatieGeblokkeerd, castOther.indicatieGeblokkeerd)
                                  .append(naderePopulatiebeperking, castOther.naderePopulatiebeperking)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(afleverpunt)
                                    .append(datumIngang)
                                    .append(datumEinde)
                                    .append(indicatieGeblokkeerd)
                                    .append(naderePopulatiebeperking)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("afleverpunt", afleverpunt)
                .append("datumIngang", datumIngang)
                .append("datumEinde", datumEinde)
                .append("indicatieGeblokkeerd", indicatieGeblokkeerd)
                .append("naderePopulatiebeperking", naderePopulatiebeperking)
                .toString();
    }
}
