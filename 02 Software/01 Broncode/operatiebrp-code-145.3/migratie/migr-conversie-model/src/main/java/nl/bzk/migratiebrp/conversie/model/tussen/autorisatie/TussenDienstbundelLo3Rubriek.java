/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen.autorisatie;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelLo3RubriekInhoud;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de groep BRP Dienstbundel Lo3Rubriek.
 *
 * Deze class is immutable en threadsafe.
 */
public final class TussenDienstbundelLo3Rubriek extends AbstractBrpGroepInhoud {

    @Element(name = "conversieRubriek", required = false)
    private final String conversieRubriek;
    @Element(name = "actief", required = false)
    private final Boolean actief;
    @Element(name = "dienstbundelLo3RubriekStapel", required = false)
    private final TussenStapel<BrpDienstbundelLo3RubriekInhoud> dienstbundelLo3RubriekStapel;

    /**
     * Maak een nieuw TussenLo3Rubriek object.
     * @param conversieRubriek Id van de bijbehorende conversie LO3 rubriek
     * @param actief actief
     * @param dienstbundelLo3RubriekStapel dienstbundelLo3RubriekStapel
     */
    public TussenDienstbundelLo3Rubriek(
            @Element(name = "conversieRubriek", required = false) final String conversieRubriek,
            @Element(name = "actief", required = false) final Boolean actief,
            @Element(name = "dienstbundelLo3RubriekStapel",
                    required = false) final TussenStapel<BrpDienstbundelLo3RubriekInhoud> dienstbundelLo3RubriekStapel) {
        super();
        this.conversieRubriek = conversieRubriek;
        this.actief = actief;
        this.dienstbundelLo3RubriekStapel = dienstbundelLo3RubriekStapel;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return actief == null;
    }

    /**
     * Geef de waarde van actief van TussenDienstbundelLo3Rubriek.
     * @return de waarde van actief van TussenDienstbundelLo3Rubriek
     */
    public Boolean getActief() {
        return actief;
    }

    /**
     * Geef de waarde van conversie rubriek van TussenDienstbundelLo3Rubriek.
     * @return de waarde van conversie rubriek van TussenDienstbundelLo3Rubriek
     */
    public String getConversieRubriek() {
        return conversieRubriek;
    }

    /**
     * Geef de waarde van dienstbundel lo3 rubriek stapel van TussenDienstbundelLo3Rubriek.
     * @return de waarde van dienstbundel lo3 rubriek stapel van TussenDienstbundelLo3Rubriek
     */
    public TussenStapel<BrpDienstbundelLo3RubriekInhoud> getDienstbundelLo3RubriekStapel() {
        return dienstbundelLo3RubriekStapel;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TussenDienstbundelLo3Rubriek)) {
            return false;
        }
        final TussenDienstbundelLo3Rubriek castOther = (TussenDienstbundelLo3Rubriek) other;
        return new EqualsBuilder().append(conversieRubriek, castOther.conversieRubriek)
                .append(actief, castOther.actief)
                .append(dienstbundelLo3RubriekStapel, castOther.dienstbundelLo3RubriekStapel)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(conversieRubriek).append(actief).append(dienstbundelLo3RubriekStapel).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("conversieRubriekId", conversieRubriek)
                .append("actief", actief.booleanValue())
                .append("dienstbundelLo3RubriekStapel", dienstbundelLo3RubriekStapel)
                .toString();
    }

}
