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
 * Deze class representeert de inhoud van de BRP Europese verkiezingen.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpEuropeseVerkiezingenInhoud extends AbstractBrpGroepInhoud {
    @Element(name = "deelnameEuropeseVerkiezingen", required = false)
    private final Boolean deelnameEuropeseVerkiezingen;
    @Element(name = "datumAanleidingAanpassingDeelnameEuropeseVerkiezingen", required = false)
    private final BrpDatum datumAanleidingAanpassingDeelnameEuropeseVerkiezingen;
    @Element(name = "datumEindeUitsluitingEuropeesKiesrecht", required = false)
    private final BrpDatum datumEindeUitsluitingEuropeesKiesrecht;

    /**
     * Maakt een BrpEuropeseVerkiezingenInhoud object.
     * 
     * @param deelnameEuropeseVerkiezingen
     *            deelname europese verkiezingen, mag null zijn
     * @param datumAanleidingAanpassingDeelnameEuropeseVerkiezingen
     *            de datum aanleiding aanpassing deelname europese verkiezingen, mag null zijn
     * @param datumEindeUitsluitingEuropeesKiesrecht
     *            de datum einde uitsluiting europees kiesrecht, mag null zijn
     */
    public BrpEuropeseVerkiezingenInhoud(
            @Element(name = "deelnameEuropeseVerkiezingen", required = false) final Boolean deelnameEuropeseVerkiezingen,
            @Element(name = "datumAanleidingAanpassingDeelnameEuropeseVerkiezingen", required = false) final BrpDatum datumAanleidingAanpassingDeelnameEuropeseVerkiezingen,
            @Element(name = "datumEindeUitsluitingEuropeesKiesrecht", required = false) final BrpDatum datumEindeUitsluitingEuropeesKiesrecht) {
        this.deelnameEuropeseVerkiezingen = deelnameEuropeseVerkiezingen;
        this.datumAanleidingAanpassingDeelnameEuropeseVerkiezingen =
                datumAanleidingAanpassingDeelnameEuropeseVerkiezingen;
        this.datumEindeUitsluitingEuropeesKiesrecht = datumEindeUitsluitingEuropeesKiesrecht;
    }

    @Override
    public boolean isLeeg() {
        return deelnameEuropeseVerkiezingen == null && datumAanleidingAanpassingDeelnameEuropeseVerkiezingen == null
                && datumEindeUitsluitingEuropeesKiesrecht == null;
    }

    public BrpDatum getDatumAanleidingAanpassingDeelnameEuropeseVerkiezingen() {
        return datumAanleidingAanpassingDeelnameEuropeseVerkiezingen;
    }

    public BrpDatum getDatumEindeUitsluitingEuropeesKiesrecht() {
        return datumEindeUitsluitingEuropeesKiesrecht;
    }

    public Boolean getDeelnameEuropeseVerkiezingen() {
        return deelnameEuropeseVerkiezingen;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpEuropeseVerkiezingenInhoud)) {
            return false;
        }
        final BrpEuropeseVerkiezingenInhoud castOther = (BrpEuropeseVerkiezingenInhoud) other;
        return new EqualsBuilder()
                .append(deelnameEuropeseVerkiezingen, castOther.deelnameEuropeseVerkiezingen)
                .append(datumAanleidingAanpassingDeelnameEuropeseVerkiezingen,
                        castOther.datumAanleidingAanpassingDeelnameEuropeseVerkiezingen)
                .append(datumEindeUitsluitingEuropeesKiesrecht, castOther.datumEindeUitsluitingEuropeesKiesrecht)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(deelnameEuropeseVerkiezingen)
                .append(datumAanleidingAanpassingDeelnameEuropeseVerkiezingen)
                .append(datumEindeUitsluitingEuropeesKiesrecht).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("deelnameEuropeseVerkiezingen", deelnameEuropeseVerkiezingen)
                .append("datumAanleidingAanpassingDeelnameEuropeseVerkiezingen",
                        datumAanleidingAanpassingDeelnameEuropeseVerkiezingen)
                .append("datumEindeUitsluitingEuropeesKiesrecht", datumEindeUitsluitingEuropeesKiesrecht).toString();
    }
}
