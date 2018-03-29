/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen.autorisatie;

import java.util.List;
import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.algemeenbrp.util.xml.annotation.ElementList;
import nl.bzk.algemeenbrp.util.xml.annotation.Root;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Migratie representatie voor een autorisatie (BRP inhoud, LO3 historie).
 */
@Root
public final class TussenAutorisatie {

    @Element(name = "partijCode", required = false)
    private final BrpPartijCode partij;
    @Element(name = "indicatieVerstrekkingsbeperkingMogelijk", required = false)
    private final BrpBoolean indicatieVerstrekkingsbeperkingMogelijk;
    @ElementList(name = "leveringsAutorisaties", entry = "leveringsautorisatie", type = TussenLeveringsautorisatie.class, required = false)
    private final List<TussenLeveringsautorisatie> leveringsautorisaties;

    /**
     * Maak een nieuw TussenAutorisatie object.
     * @param partij de partij van de autorisatie
     * @param indicatieVerstrekkingsbeperkingMogelijk indicatie verstrekkingsbeperking mogelijk
     * @param leveringsautorisaties de leveringsautorisaties van de autorisatie
     */
    public TussenAutorisatie(
            @Element(name = "partijCode", required = false) final BrpPartijCode partij,
            @Element(name = "indicatieVerstrekkingsbeperkingMogelijk", required = false) final BrpBoolean indicatieVerstrekkingsbeperkingMogelijk,
            @ElementList(name = "leveringsAutorisaties", entry = "leveringsautorisatie", type = TussenLeveringsautorisatie.class,
                    required = false) final List<TussenLeveringsautorisatie> leveringsautorisaties) {
        super();
        this.partij = partij;
        this.indicatieVerstrekkingsbeperkingMogelijk = indicatieVerstrekkingsbeperkingMogelijk;
        this.leveringsautorisaties = leveringsautorisaties;
    }

    /**
     * Geef de waarde van partij van TussenAutorisatie.
     * @return de waarde van partij van TussenAutorisatie
     */
    public BrpPartijCode getPartij() {
        return partij;
    }

    /**
     * Geef de waarde van indicatie verstrekkingsbeperking mogelijk van TussenAutorisatie.
     * @return de waarde van indicatie verstrekkingsbeperking mogelijk van TussenAutorisatie
     */
    public BrpBoolean getIndicatieVerstrekkingsbeperkingMogelijk() {
        return indicatieVerstrekkingsbeperkingMogelijk;
    }

    /**
     * Geef de waarde van leveringsautorisaties van TussenAutorisatie.
     * @return de waarde van leveringsautorisaties van TussenAutorisatie
     */
    public List<TussenLeveringsautorisatie> getLeveringsautorisaties() {
        return leveringsautorisaties;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TussenAutorisatie)) {
            return false;
        }
        final TussenAutorisatie castOther = (TussenAutorisatie) other;
        return new EqualsBuilder().append(partij, castOther.partij)
                .append(indicatieVerstrekkingsbeperkingMogelijk, castOther.indicatieVerstrekkingsbeperkingMogelijk)
                .append(leveringsautorisaties, castOther.leveringsautorisaties)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(partij).append(leveringsautorisaties).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("partij", partij)
                .append(
                        "indicatieVerstrekkingsbeperkingMogelijk",
                        indicatieVerstrekkingsbeperkingMogelijk)
                .append("leveringsautorisaties", leveringsautorisaties)
                .toString();
    }
}
