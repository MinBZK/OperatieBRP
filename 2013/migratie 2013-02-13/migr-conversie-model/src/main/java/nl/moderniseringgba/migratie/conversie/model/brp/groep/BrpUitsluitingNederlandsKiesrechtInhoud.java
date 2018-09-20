/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de BRP Uitsluiting Nederlands Kiesrecht.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpUitsluitingNederlandsKiesrechtInhoud extends AbstractBrpGroepInhoud {
    @Element(name = "indicatieUitsluitingNederlandsKiesrecht", required = false)
    private final Boolean indicatieUitsluitingNederlandsKiesrecht;
    @Element(name = "datumEindeUitsluitingNederlandsKiesrecht", required = false)
    private final BrpDatum datumEindeUitsluitingNederlandsKiesrecht;

    /**
     * Maakt een nieuw BrpUitsluitingNederlandsKiesrechtInhoud object.
     * 
     * @param indicatieUitsluitingNederlandsKiesrecht
     *            de indicatie 'Uitsluiting Nederlands Kiesrecht', mag null zijn
     * @param datumEindeUitsluitingNederlandsKiesrecht
     *            de datum einde uitsluiting nederlands kiesrecht, mag null zijn
     */
    public BrpUitsluitingNederlandsKiesrechtInhoud(
            @Element(name = "indicatieUitsluitingNederlandsKiesrecht", required = false) final Boolean indicatieUitsluitingNederlandsKiesrecht,
            @Element(name = "datumEindeUitsluitingNederlandsKiesrecht", required = false) final BrpDatum datumEindeUitsluitingNederlandsKiesrecht) {
        this.indicatieUitsluitingNederlandsKiesrecht = indicatieUitsluitingNederlandsKiesrecht;
        this.datumEindeUitsluitingNederlandsKiesrecht = datumEindeUitsluitingNederlandsKiesrecht;
    }

    @Override
    public boolean isLeeg() {
        return indicatieUitsluitingNederlandsKiesrecht == null && datumEindeUitsluitingNederlandsKiesrecht == null;
    }

    public BrpDatum getDatumEindeUitsluitingNederlandsKiesrecht() {
        return datumEindeUitsluitingNederlandsKiesrecht;
    }

    public Boolean getIndicatieUitsluitingNederlandsKiesrecht() {
        return indicatieUitsluitingNederlandsKiesrecht;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpUitsluitingNederlandsKiesrechtInhoud)) {
            return false;
        }
        final BrpUitsluitingNederlandsKiesrechtInhoud castOther = (BrpUitsluitingNederlandsKiesrechtInhoud) other;
        return new EqualsBuilder()
                .append(indicatieUitsluitingNederlandsKiesrecht, castOther.indicatieUitsluitingNederlandsKiesrecht)
                .append(datumEindeUitsluitingNederlandsKiesrecht, castOther.datumEindeUitsluitingNederlandsKiesrecht)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(indicatieUitsluitingNederlandsKiesrecht)
                .append(datumEindeUitsluitingNederlandsKiesrecht).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("indicatieUitsluitingNederlandsKiesrecht", indicatieUitsluitingNederlandsKiesrecht)
                .append("datumEindeUitsluitingNederlandsKiesrecht", datumEindeUitsluitingNederlandsKiesrecht)
                .toString();
    }
}
