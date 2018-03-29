/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de BRP groep Buitenlands persoonsnummer. Deze class is immutable en
 * threadsafe.
 */
public final class BrpBuitenlandsPersoonsnummerInhoud extends AbstractBrpGroepInhoud {
    @Element(name = "nummer")
    private final BrpString nummer;
    @Element(name = "autoriteitVanAfgifte")
    private final BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer autoriteitVanAfgifte;

    /**
     * Maakt een {@link BrpBuitenlandsPersoonsnummerInhoud} aan.
     * @param nummer het nummer
     * @param autoriteitVanAfgifte de autoriteit van afgifte
     */
    public BrpBuitenlandsPersoonsnummerInhoud(
            @Element(name = "nummer") final BrpString nummer,
            @Element(name = "autoriteitVanAfgifte") final BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer autoriteitVanAfgifte) {
        this.nummer = nummer;
        this.autoriteitVanAfgifte = autoriteitVanAfgifte;
    }

    @Override
    public boolean isLeeg() {
        return !BrpValidatie.isEenParameterGevuld(nummer, autoriteitVanAfgifte);
    }

    /**
     * Geeft het buitenlands persoonsnummer terug.
     * @return het nummer
     */
    public BrpString getNummer() {
        return nummer;
    }

    /**
     * Geeft de autorisatie van afgifte terug.
     * @return de autorisatie van afgifte
     */
    public BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer getAutoriteitVanAfgifte() {
        return autoriteitVanAfgifte;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpBuitenlandsPersoonsnummerInhoud)) {
            return false;
        }
        final BrpBuitenlandsPersoonsnummerInhoud castOther = (BrpBuitenlandsPersoonsnummerInhoud) other;
        return new EqualsBuilder().append(nummer, castOther.nummer).append(autoriteitVanAfgifte, castOther.autoriteitVanAfgifte).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(nummer).append(autoriteitVanAfgifte).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("nummer", nummer)
                .append("autoriteitVanAfgifte", autoriteitVanAfgifte)
                .toString();
    }
}
