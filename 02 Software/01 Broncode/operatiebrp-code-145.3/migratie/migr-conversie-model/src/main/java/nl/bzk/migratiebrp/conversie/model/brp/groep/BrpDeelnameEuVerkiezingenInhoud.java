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
 * Deze class representeert de inhoud van de BRP Groep Deelname EU verkiezingen.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpDeelnameEuVerkiezingenInhoud extends AbstractBrpGroepInhoud {
    @Element(name = "indicatieDeelnameEuVerkiezingen", required = false)
    private final BrpBoolean indicatieDeelnameEuVerkiezingen;
    @Element(name = "datumAanleidingAanpassingDeelnameEuVerkiezingen", required = false)
    private final BrpDatum datumAanleidingAanpassingDeelnameEuVerkiezingen;
    @Element(name = "datumVoorzienEindeUitsluitingEuVerkiezingen", required = false)
    private final BrpDatum datumVoorzienEindeUitsluitingEuVerkiezingen;

    /**
     * Maakt een BrpDeelnameEuVerkiezingenInhoud object.
     * @param indicatieDeelnameEuVerkiezingen deelname Europese verkiezingen, mag null zijn
     * @param datumAanleidingAanpassingDeelnameEuVerkiezingen de datum aanleiding aanpassing deelname Europese verkiezingen, mag null zijn
     * @param datumVoorzienEindeUitsluitingEuVerkiezingen de datum voorzien einde uitsluiting Europees verkiezingen, mag null zijn
     */
    public BrpDeelnameEuVerkiezingenInhoud(
            @Element(name = "indicatieDeelnameEuVerkiezingen", required = false) final BrpBoolean indicatieDeelnameEuVerkiezingen,
            @Element(name = "datumAanleidingAanpassingDeelnameEuVerkiezingen",
                    required = false) final BrpDatum datumAanleidingAanpassingDeelnameEuVerkiezingen,
            @Element(name = "datumVoorzienEindeUitsluitingEuVerkiezingen", required = false) final BrpDatum datumVoorzienEindeUitsluitingEuVerkiezingen) {
        this.indicatieDeelnameEuVerkiezingen = indicatieDeelnameEuVerkiezingen;
        this.datumAanleidingAanpassingDeelnameEuVerkiezingen = datumAanleidingAanpassingDeelnameEuVerkiezingen;
        this.datumVoorzienEindeUitsluitingEuVerkiezingen = datumVoorzienEindeUitsluitingEuVerkiezingen;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !BrpValidatie.isEenParameterGevuld(
                indicatieDeelnameEuVerkiezingen,
                datumAanleidingAanpassingDeelnameEuVerkiezingen,
                datumVoorzienEindeUitsluitingEuVerkiezingen);
    }

    /**
     * Geef de waarde van datum aanleiding aanpassing deelname eu verkiezingen van BrpDeelnameEuVerkiezingenInhoud.
     * @return de waarde van datum aanleiding aanpassing deelname eu verkiezingen van BrpDeelnameEuVerkiezingenInhoud
     */
    public BrpDatum getDatumAanleidingAanpassingDeelnameEuVerkiezingen() {
        return datumAanleidingAanpassingDeelnameEuVerkiezingen;
    }

    /**
     * Geef de waarde van datum voorzien einde uitsluiting eu verkiezingen van BrpDeelnameEuVerkiezingenInhoud.
     * @return de waarde van datum voorzien einde uitsluiting eu verkiezingen van BrpDeelnameEuVerkiezingenInhoud
     */
    public BrpDatum getDatumVoorzienEindeUitsluitingEuVerkiezingen() {
        return datumVoorzienEindeUitsluitingEuVerkiezingen;
    }

    /**
     * Geef de waarde van indicatie deelname eu verkiezingen van BrpDeelnameEuVerkiezingenInhoud.
     * @return de waarde van indicatie deelname eu verkiezingen van BrpDeelnameEuVerkiezingenInhoud
     */
    public BrpBoolean getIndicatieDeelnameEuVerkiezingen() {
        return indicatieDeelnameEuVerkiezingen;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpDeelnameEuVerkiezingenInhoud)) {
            return false;
        }
        final BrpDeelnameEuVerkiezingenInhoud castOther = (BrpDeelnameEuVerkiezingenInhoud) other;
        return new EqualsBuilder().append(indicatieDeelnameEuVerkiezingen, castOther.indicatieDeelnameEuVerkiezingen)
                .append(datumAanleidingAanpassingDeelnameEuVerkiezingen, castOther.datumAanleidingAanpassingDeelnameEuVerkiezingen)
                .append(datumVoorzienEindeUitsluitingEuVerkiezingen, castOther.datumVoorzienEindeUitsluitingEuVerkiezingen)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(indicatieDeelnameEuVerkiezingen)
                .append(datumAanleidingAanpassingDeelnameEuVerkiezingen)
                .append(datumVoorzienEindeUitsluitingEuVerkiezingen)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("indicatieDeelnameEuVerkiezingen", indicatieDeelnameEuVerkiezingen)
                .append(
                        "datumAanleidingAanpassingDeelnameEuVerkiezingen",
                        datumAanleidingAanpassingDeelnameEuVerkiezingen)
                .append(
                        "datumVoorzienEindeUitsluitingEuVerkiezingen",
                        datumVoorzienEindeUitsluitingEuVerkiezingen)
                .toString();
    }
}
