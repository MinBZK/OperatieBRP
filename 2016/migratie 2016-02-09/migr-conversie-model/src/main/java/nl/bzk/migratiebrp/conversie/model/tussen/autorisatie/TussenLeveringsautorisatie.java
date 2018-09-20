/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen.autorisatie;

import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpStelselCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpLeveringsautorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 * Migratie representatie voor een leveringsautorisatie (BRP inhoud, LO3 historie).
 *
 * Deze class is immutable en thread-safe.
 */
public final class TussenLeveringsautorisatie {

    @Element(name = "stelsel", required = false)
    private final BrpStelselCode stelsel;
    @Element(name = "indicatieModelautorisatie", required = false)
    private final Boolean indicatieModelautorisatie;
    @Element(name = "leveringsautorisatieStapel", required = false)
    private final TussenStapel<BrpLeveringsautorisatieInhoud> leveringsautorisatieStapel;
    @ElementList(name = "dienstBundels", entry = "dienstBundel", type = TussenDienstbundel.class, required = false)
    private final List<TussenDienstbundel> dienstBundels;

    /**
     * Maak een nieuw TussenLeveringsAutorisatie object.
     *
     * @param stelsel
     *            stelsel
     * @param indicatieModelautorisatie
     *            de indicatieModelautorisatie
     * @param leveringsautorisatieStapel
     *            de stapel van leveringsAutorisatie
     * @param dienstBundels
     *            de gekoppelde dienstbundels
     */
    public TussenLeveringsautorisatie(@Element(name = "stelsel", required = false) final BrpStelselCode stelsel, @Element(
        name = "indicatieModelautorisatie", required = false) final Boolean indicatieModelautorisatie, @Element(name = "leveringsautorisatieStapel",
        required = false) final TussenStapel<BrpLeveringsautorisatieInhoud> leveringsautorisatieStapel, @ElementList(name = "dienstBundels",
        entry = "dienstBundel", type = TussenDienstbundel.class, required = false) final List<TussenDienstbundel> dienstBundels)
    {
        super();
        this.stelsel = stelsel;
        this.indicatieModelautorisatie = indicatieModelautorisatie;
        this.leveringsautorisatieStapel = leveringsautorisatieStapel;
        this.dienstBundels = dienstBundels;
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
    public TussenStapel<BrpLeveringsautorisatieInhoud> getLeveringsautorisatieStapel() {
        return leveringsautorisatieStapel;
    }

    /**
     * Geef de waarde van dienstenbundels.
     *
     * @return diensten
     */
    public List<TussenDienstbundel> getDienstBundels() {
        return dienstBundels;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TussenLeveringsautorisatie)) {
            return false;
        }
        final TussenLeveringsautorisatie castOther = (TussenLeveringsautorisatie) other;
        return new EqualsBuilder().append(stelsel, castOther.stelsel)
                .append(indicatieModelautorisatie, castOther.indicatieModelautorisatie)
                .append(leveringsautorisatieStapel, castOther.leveringsautorisatieStapel)
                .append(dienstBundels, castOther.dienstBundels)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(stelsel)
                                    .append(indicatieModelautorisatie)
                                    .append(leveringsautorisatieStapel)
                                    .append(dienstBundels)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("stelsel", stelsel)
                                                                          .append("indicatieModelautorisatie", indicatieModelautorisatie)
                                                                          .append("leveringsAutorisatieStapel", leveringsautorisatieStapel)
                                                                          .append("dienstBundels", dienstBundels)
                                                                          .toString();
    }
}
