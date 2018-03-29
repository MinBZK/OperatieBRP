/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.autorisatie;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.algemeenbrp.util.xml.annotation.ElementList;
import nl.bzk.migratiebrp.conversie.model.Sortable;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpStelselCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpLeveringsautorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.SortUtil;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert het BRP objecttype LeveringAutorisatie.
 *
 * Deze class is immutable en thread-safe.
 */
public final class BrpLeveringsautorisatie implements Sortable {

    @Element(name = "stelsel")
    private final BrpStelselCode stelsel;
    @Element(name = "indicatieModelautorisatie")
    private final Boolean indicatieModelautorisatie;
    @Element(name = "leveringsautorisatieStapel")
    private final BrpStapel<BrpLeveringsautorisatieInhoud> leveringsautorisatieStapel;
    @ElementList(name = "dienstbundels", entry = "dienstbundel", type = BrpDienstbundel.class)
    private final List<BrpDienstbundel> dienstbundels;

    /**
     * Maak een nieuw BrpLeveringsAutorisatie object.
     * @param stelsel stelsel
     * @param indicatieModelautorisatie de indicatieModelautorisatie
     * @param leveringsautorisatieStapel de stapel van leveringsAutorisatie
     * @param dienstbundels de gekoppelde dienstbundels
     */
    public BrpLeveringsautorisatie(
            @Element(name = "stelsel") final BrpStelselCode stelsel,
            @Element(name = "indicatieModelautorisatie") final Boolean indicatieModelautorisatie,
            @Element(name = "leveringsautorisatieStapel") final BrpStapel<BrpLeveringsautorisatieInhoud> leveringsautorisatieStapel,
            @ElementList(name = "dienstbundels", entry = "dienstbundel", type = BrpDienstbundel.class) final List<BrpDienstbundel> dienstbundels) {
        super();
        this.stelsel = stelsel;
        this.indicatieModelautorisatie = indicatieModelautorisatie;
        this.leveringsautorisatieStapel = leveringsautorisatieStapel;
        this.dienstbundels = dienstbundels;
    }

    /**
     * Geef de waarde van stelsel.
     * @return stelsel
     */
    public BrpStelselCode getStelsel() {
        return stelsel;
    }

    /**
     * Geef de waarde van indicatieModelautorisatie.
     * @return indicatieModelautorisatie
     */
    public Boolean getIndicatieModelautorisatie() {
        return indicatieModelautorisatie;
    }

    /**
     * Geef de waarde van de leveringautorisatieinhoud stapel.
     * @return leveringautorisatieinhoud stapel
     */
    public BrpStapel<BrpLeveringsautorisatieInhoud> getLeveringsautorisatieStapel() {
        return leveringsautorisatieStapel;
    }

    /**
     * Geef de waarde van dienstenbundels.
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

    @Override
    public void sorteer() {
        if (dienstbundels != null) {
            for (final BrpDienstbundel dienstbundel : dienstbundels) {
                dienstbundel.sorteer();
            }
            dienstbundels.sort(new DienstbundelComparator());
        }

        if (leveringsautorisatieStapel != null) {
            leveringsautorisatieStapel.sorteer();
        }
    }

    private static class DienstbundelComparator implements Comparator<BrpDienstbundel>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpDienstbundel o1, final BrpDienstbundel o2) {
            int resultaat;

            // Controle op bestaan stapel.
            if (o1.getDienstbundelStapel() == null) {
                if (o2.getDienstbundelStapel() == null) {
                    resultaat = 0;
                } else {
                    resultaat = 1;
                }
            } else {

                // Stapel bestaat, controleer inhoudelijk.
                final BrpGroep<BrpDienstbundelInhoud> inhoudO1 = o1.getDienstbundelStapel().get(0);
                final BrpGroep<BrpDienstbundelInhoud> inhoudO2 = o2.getDienstbundelStapel().get(0);

                // Controleer of de bovenste van
                if (inhoudO1 == null) {
                    if (inhoudO2 == null) {
                        resultaat = 0;
                    } else {
                        resultaat = 1;
                    }
                } else {
                    int index = 0;
                    resultaat = 0;
                    while (resultaat == 0 && index < o1.getDienstbundelStapel().size()) {
                        resultaat =
                                new DienstbundelInhoudComparator().compare(o1.getDienstbundelStapel().get(index), o2.getDienstbundelStapel().get(index));
                        index++;
                    }
                }
            }
            return resultaat;
        }
    }

    private static class DienstbundelInhoudComparator implements Comparator<BrpGroep<BrpDienstbundelInhoud>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpGroep<BrpDienstbundelInhoud> o1, final BrpGroep<BrpDienstbundelInhoud> o2) {

            int resultaat;

            if (o1 == null) {
                if (o2 == null) {
                    resultaat = 0;
                } else {
                    resultaat = 1;
                }
            } else {
                // Sortering op ingangsdatum aflopend (jongste bovenaan).
                resultaat = SortUtil.vergelijkDatums(o1.getInhoud().getDatumIngang(), o2.getInhoud().getDatumIngang());

                // Sortering op einddatum aflopend indien sortering op ingangsdatum gelijk geeft.
                if (resultaat == 0) {
                    resultaat = SortUtil.vergelijkDatums(o1.getInhoud().getDatumEinde(), o2.getInhoud().getDatumEinde());
                }

                // Sortering op naam indien datums geen effect hebben.
                if (resultaat == 0) {
                    resultaat = o1.getInhoud().getNaam().compareTo(o2.getInhoud().getNaam());
                }
            }

            return resultaat;
        }
    }
}
