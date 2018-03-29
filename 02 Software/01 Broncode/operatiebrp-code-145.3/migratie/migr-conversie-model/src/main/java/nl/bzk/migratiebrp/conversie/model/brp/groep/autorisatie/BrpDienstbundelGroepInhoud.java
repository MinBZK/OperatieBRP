/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpGroepInhoud;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de groep BRP DienstBundelGroepInhoud.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpDienstbundelGroepInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "indformelehistorie", required = false)
    private final Boolean indicatieFormeleHistorie;
    @Element(name = "indmaterielehistorie", required = false)
    private final Boolean indicatieMaterieleHistorie;
    @Element(name = "indverantwoording", required = false)
    private final Boolean indicatieVerantwoording;

    /**
     * Maak een BrpDienstBundelGroepInhoud object.
     * @param indicatieFormeleHistorie indicatieFormeleHistorie
     * @param indicatieMaterieleHistorie indicatieMaterieleHistorie
     * @param indicatieVerantwoording indicatieVerantwoording
     */
    public BrpDienstbundelGroepInhoud(
            @Element(name = "indformelehistorie", required = false) final Boolean indicatieFormeleHistorie,
            @Element(name = "indmaterielehistorie", required = false) final Boolean indicatieMaterieleHistorie,
            @Element(name = "indverantwoording", required = false) final Boolean indicatieVerantwoording) {
        super();
        this.indicatieFormeleHistorie = indicatieFormeleHistorie;
        this.indicatieMaterieleHistorie = indicatieMaterieleHistorie;
        this.indicatieVerantwoording = indicatieVerantwoording;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return indicatieFormeleHistorie == null && indicatieMaterieleHistorie == null && indicatieVerantwoording == null;
    }

    /**
     * Geef de waarde van indicatie formele historie van BrpDienstbundelGroepInhoud.
     * @return de waarde van indicatie formele historie van BrpDienstbundelGroepInhoud
     */
    public Boolean getIndicatieFormeleHistorie() {
        return indicatieFormeleHistorie;
    }

    /**
     * Geef de waarde van indicatie materiele historie van BrpDienstbundelGroepInhoud.
     * @return de waarde van indicatie materiele historie van BrpDienstbundelGroepInhoud
     */
    public Boolean getIndicatieMaterieleHistorie() {
        return indicatieMaterieleHistorie;
    }

    /**
     * Geef de waarde van indicatie verantwoording van BrpDienstbundelGroepInhoud.
     * @return de waarde van indicatie verantwoording van BrpDienstbundelGroepInhoud
     */
    public Boolean getIndicatieVerantwoording() {
        return indicatieVerantwoording;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpDienstbundelGroepInhoud)) {
            return false;
        }
        final BrpDienstbundelGroepInhoud castOther = (BrpDienstbundelGroepInhoud) other;
        return new EqualsBuilder().append(indicatieFormeleHistorie, castOther.indicatieFormeleHistorie)
                .append(indicatieMaterieleHistorie, castOther.indicatieMaterieleHistorie)
                .append(indicatieVerantwoording, castOther.indicatieVerantwoording)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(indicatieFormeleHistorie).append(indicatieMaterieleHistorie).append(indicatieVerantwoording).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("indicatieFormeleHistorie", indicatieFormeleHistorie)
                .append("indicatieMaterieleHistorie", indicatieMaterieleHistorie)
                .append("indicatieVerantwoording", indicatieVerantwoording)
                .toString();
    }
}
