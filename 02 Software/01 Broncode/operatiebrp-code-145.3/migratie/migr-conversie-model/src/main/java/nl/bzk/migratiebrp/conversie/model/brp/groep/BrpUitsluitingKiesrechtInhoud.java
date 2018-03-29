/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de BRP Uitsluiting Kiesrecht.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpUitsluitingKiesrechtInhoud extends AbstractBrpGroepInhoud {
    @Element(name = "indicatieUitsluitingKiesrecht", required = false)
    private final BrpBoolean indicatieUitsluitingKiesrecht;
    @Element(name = "datumVoorzienEindeUitsluitingKiesrecht", required = false)
    private final BrpDatum datumVoorzienEindeUitsluitingKiesrecht;

    /**
     * Maakt een nieuw BrpUitsluitingKiesrechtInhoud object.
     * @param indicatieUitsluitingKiesrecht de indicatie 'Uitsluiting Kiesrecht', mag null zijn
     * @param datumVoorzienEindeUitsluitingKiesrecht de voorziene datum einde uitsluiting kiesrecht, mag null zijn
     */
    public BrpUitsluitingKiesrechtInhoud(
            @Element(name = "indicatieUitsluitingKiesrecht", required = false) final BrpBoolean indicatieUitsluitingKiesrecht,
            @Element(name = "datumVoorzienEindeUitsluitingKiesrecht", required = false) final BrpDatum datumVoorzienEindeUitsluitingKiesrecht) {
        this.indicatieUitsluitingKiesrecht = indicatieUitsluitingKiesrecht;
        this.datumVoorzienEindeUitsluitingKiesrecht = datumVoorzienEindeUitsluitingKiesrecht;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !BrpValidatie.isEenParameterGevuld(indicatieUitsluitingKiesrecht, datumVoorzienEindeUitsluitingKiesrecht);
    }

    /**
     * Geef de waarde van datum voorzien einde uitsluiting kiesrecht van BrpUitsluitingKiesrechtInhoud.
     * @return de waarde van datum voorzien einde uitsluiting kiesrecht van BrpUitsluitingKiesrechtInhoud
     */
    public BrpDatum getDatumVoorzienEindeUitsluitingKiesrecht() {
        return datumVoorzienEindeUitsluitingKiesrecht;
    }

    /**
     * Geef de waarde van indicatie uitsluiting kiesrecht van BrpUitsluitingKiesrechtInhoud.
     * @return de waarde van indicatie uitsluiting kiesrecht van BrpUitsluitingKiesrechtInhoud
     */
    public BrpBoolean getIndicatieUitsluitingKiesrecht() {
        return indicatieUitsluitingKiesrecht;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpUitsluitingKiesrechtInhoud)) {
            return false;
        }
        final BrpUitsluitingKiesrechtInhoud castOther = (BrpUitsluitingKiesrechtInhoud) other;
        return new EqualsBuilder().append(indicatieUitsluitingKiesrecht, castOther.indicatieUitsluitingKiesrecht)
                .append(datumVoorzienEindeUitsluitingKiesrecht, castOther.datumVoorzienEindeUitsluitingKiesrecht)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(indicatieUitsluitingKiesrecht).append(datumVoorzienEindeUitsluitingKiesrecht).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("indicatieUitsluitingKiesrecht", indicatieUitsluitingKiesrecht)
                .append(
                        "datumVoorzienEindeUitsluitingKiesrecht",
                        datumVoorzienEindeUitsluitingKiesrecht)
                .toString();
    }
}
