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
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.SortUtil;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert het BRP objecttype Dienstbundel.
 *
 * Deze class is immutable en thread-safe.
 */
public final class BrpDienstbundel implements Sortable {

    @ElementList(name = "diensten", entry = "dienst", type = BrpDienst.class)
    private final List<BrpDienst> diensten;
    @ElementList(name = "lo3rubrieken", entry = "lo3Rubriek", type = BrpDienstbundelLo3Rubriek.class)
    private final List<BrpDienstbundelLo3Rubriek> lo3Rubrieken;
    @Element(name = "dienstbundelStapel")
    private final BrpStapel<BrpDienstbundelInhoud> dienstbundelStapel;

    /**
     * Maak een nieuw BrpDienstBundel object.
     * @param diensten de diensten
     * @param lo3Rubrieken de lo3Rubrieken
     * @param dienstbundelStapel de dienstbundel stapels
     */
    public BrpDienstbundel(
            @ElementList(name = "diensten", entry = "dienst", type = BrpDienst.class) final List<BrpDienst> diensten,
            @ElementList(name = "lo3rubrieken", entry = "lo3Rubriek", type = BrpDienstbundelLo3Rubriek.class) final List<BrpDienstbundelLo3Rubriek>
                    lo3Rubrieken, @Element(name = "dienstbundelStapel") final BrpStapel<BrpDienstbundelInhoud> dienstbundelStapel) {
        super();
        this.diensten = diensten;
        this.lo3Rubrieken = lo3Rubrieken;
        this.dienstbundelStapel = dienstbundelStapel;
    }

    /**
     * Geef de waarde van diensten.
     * @return diensten
     */
    public List<BrpDienst> getDiensten() {
        return diensten;
    }

    /**
     * Geef de waarde van lo3rubrieken.
     * @return lo3rubrieken
     */
    public List<BrpDienstbundelLo3Rubriek> getLo3Rubrieken() {
        return lo3Rubrieken;
    }

    /**
     * Geef de waarde van dienstbundel stapel.
     * @return dienstbundel stapel
     */
    public BrpStapel<BrpDienstbundelInhoud> getDienstbundelStapel() {
        return dienstbundelStapel;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpDienstbundel)) {
            return false;
        }
        final BrpDienstbundel castOther = (BrpDienstbundel) other;
        return new EqualsBuilder().append(diensten, castOther.diensten)
                .append(lo3Rubrieken, castOther.lo3Rubrieken)
                .append(dienstbundelStapel, castOther.dienstbundelStapel)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(diensten).append(lo3Rubrieken).append(dienstbundelStapel).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("diensten", diensten)
                .append("lo3Rubrieken", lo3Rubrieken)
                .append("dienstBundelStapel", dienstbundelStapel)
                .toString();
    }

    @Override
    public void sorteer() {
        if (diensten != null) {
            for (final BrpDienst dienst : diensten) {
                dienst.sorteer();
            }
            diensten.sort(new DienstComparator());
        }

        if (lo3Rubrieken != null) {
            lo3Rubrieken.sort((o1, o2) -> {

                final int resultaat;

                if (o1.getConversieRubriek() == null) {
                    if (o2.getConversieRubriek() == null) {
                        resultaat = 0;
                    } else {
                        resultaat = 1;
                    }
                } else {
                    resultaat = o1.getConversieRubriek().compareTo(o2.getConversieRubriek());
                }

                return resultaat;
            });
        }

        dienstbundelStapel.sorteer();

    }

    private static class DienstComparator implements Comparator<BrpDienst>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpDienst o1, final BrpDienst o2) {
            int resultaat;

            // Controle op bestaan stapel.
            if (o1.getDienstStapel() == null) {
                if (o2.getDienstStapel() == null) {
                    resultaat = 0;
                } else {
                    resultaat = 1;
                }
            } else {
                // Stapel bestaat, controleer inhoudelijk. Sorteer de stapel eerst.
                final BrpGroep<BrpDienstInhoud> inhoudO1 = o1.getDienstStapel().get(0);
                final BrpGroep<BrpDienstInhoud> inhoudO2 = o2.getDienstStapel().get(0);

                // Controleer of de bovenste van
                if (inhoudO1 == null) {
                    if (inhoudO2 == null) {
                        resultaat = 0;
                    } else {
                        resultaat = 1;
                    }
                } else {
                    // Sortering op dienstcode.
                    resultaat = o1.getSoortDienstCode().compareTo(o2.getSoortDienstCode());

                    int index = 0;
                    while (resultaat == 0 && index < o1.getDienstStapel().size()) {
                        // Sortering op
                        resultaat = new DienstInhoudComparator().compare(o1.getDienstStapel().get(index), o2.getDienstStapel().get(index));
                        index++;
                    }

                }
            }
            return resultaat;
        }
    }

    private static class DienstInhoudComparator implements Comparator<BrpGroep<BrpDienstInhoud>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpGroep<BrpDienstInhoud> o1, final BrpGroep<BrpDienstInhoud> o2) {
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
            }
            return resultaat;
        }
    }
}
