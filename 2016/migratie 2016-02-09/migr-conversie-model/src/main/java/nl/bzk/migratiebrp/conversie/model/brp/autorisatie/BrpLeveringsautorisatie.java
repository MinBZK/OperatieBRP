/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.autorisatie;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpStelselCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpLeveringsautorisatieInhoud;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 * Deze class representeert het BRP objecttype LeveringAutorisatie.
 *
 * Deze class is immutable en thread-safe.
 */
public final class BrpLeveringsautorisatie {

    @Element(name = "stelsel", required = false)
    private final BrpStelselCode stelsel;
    @Element(name = "indicatieModelautorisatie", required = false)
    private final Boolean indicatieModelautorisatie;
    @Element(name = "leveringsautorisatieStapel", required = false)
    private final BrpStapel<BrpLeveringsautorisatieInhoud> leveringsautorisatieStapel;
    @ElementList(name = "dienstbundels", entry = "dienstbundel", type = BrpDienstbundel.class, required = false)
    private final List<BrpDienstbundel> dienstbundels;

    /**
     * Maak een nieuw BrpLeveringsAutorisatie object.
     *
     * @param stelsel
     *            stelsel
     * @param indicatieModelautorisatie
     *            de indicatieModelautorisatie
     * @param leveringsautorisatieStapel
     *            de stapel van leveringsAutorisatie
     * @param dienstbundels
     *            de gekoppelde dienstbundels
     */
    public BrpLeveringsautorisatie(
        @Element(name = "stelsel", required = false) final BrpStelselCode stelsel,
        @Element(name = "indicatieModelautorisatie", required = false) final Boolean indicatieModelautorisatie,
        @Element(name = "leveringsautorisatieStapel", required = false) final BrpStapel<BrpLeveringsautorisatieInhoud> leveringsautorisatieStapel,
        @ElementList(name = "dienstbundels", entry = "dienstbundel", type = BrpDienstbundel.class,
                required = false) final List<BrpDienstbundel> dienstbundels)
    {
        super();
        this.stelsel = stelsel;
        this.indicatieModelautorisatie = indicatieModelautorisatie;
        this.leveringsautorisatieStapel = leveringsautorisatieStapel;
        this.dienstbundels = dienstbundels;
    }

    /**
     * Geef de waarde van stelsel.
     *
     * @return stelsel
     */
    public BrpStelselCode getStelsel() {
        return stelsel;
    }

    /**
     * Geef de waarde van indicatieModelautorisatie.
     *
     * @return indicatieModelautorisatie
     */
    public Boolean getIndicatieModelautorisatie() {
        return indicatieModelautorisatie;
    }

    /**
     * Geef de waarde van de leveringautorisatieinhoud stapel.
     *
     * @return leveringautorisatieinhoud stapel
     */
    public BrpStapel<BrpLeveringsautorisatieInhoud> getLeveringsautorisatieStapel() {
        return leveringsautorisatieStapel;
    }

    /**
     * Geef de waarde van dienstenbundels.
     *
     * @return diensten
     */
    public List<BrpDienstbundel> getDienstbundels() {
        return dienstbundels;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpLeveringsautorisatie)) {
            return false;
        }
        final BrpLeveringsautorisatie castOther = (BrpLeveringsautorisatie) other;
        return new EqualsBuilder().append(stelsel, castOther.stelsel)
                                  .append(indicatieModelautorisatie, castOther.indicatieModelautorisatie)
                                  .append(leveringsautorisatieStapel, castOther.leveringsautorisatieStapel)
                                  .append(dienstbundels, castOther.dienstbundels)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(stelsel)
                                    .append(indicatieModelautorisatie)
                                    .append(leveringsautorisatieStapel)
                                    .append(dienstbundels)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("stelsel", stelsel)
                                                                          .append("indicatieModelautorisatie", indicatieModelautorisatie)
                                                                          .append("leveringsAutorisatieStapel", leveringsautorisatieStapel)
                                                                          .append("dienstBundels", dienstbundels)
                                                                          .toString();
    }
}
