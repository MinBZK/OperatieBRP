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
import nl.bzk.algemeenbrp.util.xml.annotation.Root;
import nl.bzk.migratiebrp.conversie.model.Sortable;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpLeveringsautorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.SortUtil;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de migratie specifieke kijk op een BRP Autorisatie.
 */
@Root
public final class BrpAutorisatie implements Sortable {

    @Element(name = "partij", required = false)
    private final BrpPartijCode partij;
    @Element(name = "indicatieVerstrekkingsbeperkingMogelijk", required = false)
    private final BrpBoolean indicatieVerstrekkingsbeperkingMogelijk;
    @ElementList(name = "leveringsautorisatieLijst", entry = "leveringsautorisatie", type = BrpLeveringsautorisatie.class, required = false)
    private final List<BrpLeveringsautorisatie> leveringsautorisatieLijst;

    /**
     * Maak een nieuw BrpAutorisatie object.
     * @param partij de partij van de autorisatie
     * @param indicatieVerstrekkingsbeperkingMogelijk indicatie verstrekkingsbeperking mogelijk
     * @param leveringsautorisatieLijst de leveringautorisatieLijst van de autorisatie
     */
    public BrpAutorisatie(
            @Element(name = "partij", required = false) final BrpPartijCode partij,
            @Element(name = "indicatieVerstrekkingsbeperkingMogelijk", required = false) final BrpBoolean indicatieVerstrekkingsbeperkingMogelijk,
            @ElementList(name = "leveringsautorisatieLijst", entry = "leveringsautorisatie", type = BrpLeveringsautorisatie.class,
                    required = false) final List<BrpLeveringsautorisatie> leveringsautorisatieLijst) {
        super();
        this.partij = partij;
        this.indicatieVerstrekkingsbeperkingMogelijk = indicatieVerstrekkingsbeperkingMogelijk;
        this.leveringsautorisatieLijst = leveringsautorisatieLijst;
    }

    /**
     * Geef de waarde van partij.
     * @return partij
     */
    public BrpPartijCode getPartij() {
        return partij;
    }

    /**
     * Geef de waarde van indicatie verstrekkingsbeperking mogelijk.
     * @return partij
     */
    public BrpBoolean getIndicatieVerstrekkingsbeperkingMogelijk() {
        return indicatieVerstrekkingsbeperkingMogelijk;
    }

    /**
     * Geef de waarde van autorisatie lijst.
     * @return autorisatie lijst
     */
    public List<BrpLeveringsautorisatie> getLeveringsAutorisatieLijst() {
        return leveringsautorisatieLijst;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpAutorisatie)) {
            return false;
        }
        final BrpAutorisatie castOther = (BrpAutorisatie) other;
        return new EqualsBuilder().append(partij, castOther.partij)
                .append(indicatieVerstrekkingsbeperkingMogelijk, castOther.indicatieVerstrekkingsbeperkingMogelijk)
                .append(leveringsautorisatieLijst, castOther.leveringsautorisatieLijst)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(partij).append(leveringsautorisatieLijst).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("partij", partij)
                .append(
                        "indicatieVerstrekkingsbeperkingMogelijk",
                        indicatieVerstrekkingsbeperkingMogelijk)
                .append("leveringsautorisatieLijst", leveringsautorisatieLijst)
                .toString();
    }

    @Override
    public void sorteer() {
        if (leveringsautorisatieLijst != null) {
            for (final BrpLeveringsautorisatie leveringsautorisatie : leveringsautorisatieLijst) {
                leveringsautorisatie.sorteer();
            }

            leveringsautorisatieLijst.sort(new LeveringsautorisatieComparator());
        }
    }

    private static class LeveringsautorisatieComparator implements Comparator<BrpLeveringsautorisatie>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpLeveringsautorisatie o1, final BrpLeveringsautorisatie o2) {
            int resultaat;

            // Controle op bestaan stapel.
            if (o1.getLeveringsautorisatieStapel() == null) {
                if (o2.getLeveringsautorisatieStapel() == null) {
                    resultaat = 0;
                } else {
                    resultaat = 1;
                }
            } else if(o2.getLeveringsautorisatieStapel() == null){
                resultaat = -1;
            } else {
                // Stapel bestaat, controleer inhoudelijk. Stapel is al gesorteerd bij het sorteren van de levering autorisatie.
                final BrpGroep<BrpLeveringsautorisatieInhoud> inhoudO1 = o1.getLeveringsautorisatieStapel().get(0);
                final BrpGroep<BrpLeveringsautorisatieInhoud> inhoudO2 = o2.getLeveringsautorisatieStapel().get(0);

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
                    while (resultaat == 0 && index < o1.getLeveringsautorisatieStapel().size()) {
                        resultaat =
                                new LeveringsautorisatieInhoudComparator().compare(
                                        o1.getLeveringsautorisatieStapel().get(index),
                                        o2.getLeveringsautorisatieStapel().get(index));
                        index++;
                    }
                }
            }
            return resultaat;
        }
    }

    /**
     * Volgorde voor leveringsautorisatie inhoud.
     */
    private static class LeveringsautorisatieInhoudComparator implements Comparator<BrpGroep<BrpLeveringsautorisatieInhoud>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpGroep<BrpLeveringsautorisatieInhoud> o1, final BrpGroep<BrpLeveringsautorisatieInhoud> o2) {

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
