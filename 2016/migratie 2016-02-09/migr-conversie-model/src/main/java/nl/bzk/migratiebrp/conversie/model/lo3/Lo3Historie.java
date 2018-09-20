/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3;

import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Elementnummer;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de LO3 categorie historie.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
@Preconditie(SoortMeldingCode.PRE030)
public final class Lo3Historie {

    /**
     * Null object voor de categorieen die geen historie bezitten.
     */
    public static final Lo3Historie NULL_HISTORIE = new Lo3Historie(null, Lo3Datum.NULL_DATUM, Lo3Datum.NULL_DATUM);

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_8410)
    @Element(name = "indicatieOnjuist", required = false)
    private final Lo3IndicatieOnjuist indicatieOnjuist;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_8510)
    @Element(name = "ingangsdatumGeldigheid", required = false)
    private final Lo3Datum ingangsdatumGeldigheid;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_8610)
    @Element(name = "datumVanOpneming", required = false)
    private final Lo3Datum datumVanOpneming;

    /**
     * Maakt een Lo3Historie object.
     * 
     * @param indicatieOnjuist
     *            de indicatie voor onjuist of null
     * @param ingangsdatumGeldigheid
     *            de ingangsdatum geldigheid, mag niet null zijn
     * @param datumVanOpneming
     *            de datum van opneming, mag niet null zijn
     */
    public Lo3Historie(
        @Element(name = "indicatieOnjuist", required = false) final Lo3IndicatieOnjuist indicatieOnjuist,
        @Element(name = "ingangsdatumGeldigheid", required = false) final Lo3Datum ingangsdatumGeldigheid,
        @Element(name = "datumVanOpneming", required = false) final Lo3Datum datumVanOpneming)
    {
        this.indicatieOnjuist = indicatieOnjuist;
        this.ingangsdatumGeldigheid = ingangsdatumGeldigheid;
        this.datumVanOpneming = datumVanOpneming;
    }

    /**
     * Maak een Lo3Historie.
     * 
     * @param indicatieOnjuist
     *            de indicatie voor onjuist of null (84.10)
     * @param ingangsdatumGeldigheid
     *            de ingangsdatum geldigheid, mag niet null zijn (85.10)
     * @param datumVanOpneming
     *            de datum van opneming, mag niet null zijn (86.10)
     * 
     * @return een nieuwe Lo3Historie. als alle velden leeg zijn, wordt er een historie met 0 als ingangsdatum
     *         geldigheid en datum van opnemening terug gegeven
     */
    public static Lo3Historie build(final Lo3IndicatieOnjuist indicatieOnjuist, final Lo3Datum ingangsdatumGeldigheid, final Lo3Datum datumVanOpneming) {
        if (Validatie.isEenParameterGevuld(indicatieOnjuist, ingangsdatumGeldigheid, datumVanOpneming)) {
            return new Lo3Historie(indicatieOnjuist, ingangsdatumGeldigheid, datumVanOpneming);
        } else {
            return NULL_HISTORIE;
        }
    }

    /**
     * Geef de waarde van indicatie onjuist.
     *
     * @return de indicatie onjuist of null
     */
    public Lo3IndicatieOnjuist getIndicatieOnjuist() {
        return indicatieOnjuist;
    }

    /**
     * Geef de waarde van ingangsdatum geldigheid.
     *
     * @return de ingangsdatums geldigheid (kan niet null zijn).
     */
    public Lo3Datum getIngangsdatumGeldigheid() {
        return ingangsdatumGeldigheid;
    }

    /**
     * Geef de waarde van datum van opneming.
     *
     * @return de datum van opneming (kan niet null zijn).
     */
    public Lo3Datum getDatumVanOpneming() {
        return datumVanOpneming;
    }

    /**
     * Geef de null historie.
     *
     * @return true als deze historie een NULL historie is.
     * @see Lo3Historie#NULL_HISTORIE
     */
    public boolean isNullHistorie() {
        return this.equals(NULL_HISTORIE);
    }

    /**
     * Geef de onjuist.
     *
     * @return true als deze historie als onjuist moet worden beschouwd, anders false
     */
    public boolean isOnjuist() {
        return Validatie.isElementGevuld(indicatieOnjuist);
    }

    /**
     * @return een nieuw Lo3Historie object dat een kopie is van dit Lo3Historie object maar zonder indicatieOnjuist.
     */
    public Lo3Historie verwijderIndicatieOnjuist() {
        return new Lo3Historie(null, ingangsdatumGeldigheid, datumVanOpneming);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3Historie)) {
            return false;
        }
        final Lo3Historie castOther = (Lo3Historie) other;
        return new EqualsBuilder().append(indicatieOnjuist, castOther.indicatieOnjuist)
                                  .append(ingangsdatumGeldigheid, castOther.ingangsdatumGeldigheid)
                                  .append(datumVanOpneming, castOther.datumVanOpneming)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(indicatieOnjuist).append(ingangsdatumGeldigheid).append(datumVanOpneming).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("indicatieOnjuist", indicatieOnjuist)
                                                                          .append("ingangsdatumGeldigheid", ingangsdatumGeldigheid)
                                                                          .append("datumVanOpneming", datumVanOpneming)
                                                                          .toString();
    }
}
