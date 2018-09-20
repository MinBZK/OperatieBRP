/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.migratiebrp.conversie.model.GegevensSet;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de BRP groep 'Relatie'.
 *
 * Deze class is immutable en threadsafe.
 *
 */
public final class BrpRelatieInhoud extends AbstractBrpGroepInhoud {

    @GegevensSet
    @Element(name = "datumAanvang", required = false)
    private final BrpDatum datumAanvang;
    @Element(name = "gemeenteCodeAanvang", required = false)
    private final BrpGemeenteCode gemeenteCodeAanvang;
    @Element(name = "woonplaatsnaamAanvang", required = false)
    private final BrpString woonplaatsnaamAanvang;
    @Element(name = "buitenlandsePlaatsAanvang", required = false)
    private final BrpString buitenlandsePlaatsAanvang;
    @Element(name = "buitenlandseRegioAanvang", required = false)
    private final BrpString buitenlandseRegioAanvang;
    @Element(name = "landOfGebiedCodeAanvang", required = false)
    private final BrpLandOfGebiedCode landOfGebiedCodeAanvang;
    @Element(name = "omschrijvingLocatieAanvang", required = false)
    private final BrpString omschrijvingLocatieAanvang;

    @Element(name = "redenEindeRelatieCode", required = false)
    private final BrpRedenEindeRelatieCode redenEindeRelatieCode;
    @GegevensSet
    @Element(name = "datumEinde", required = false)
    private final BrpDatum datumEinde;
    @Element(name = "gemeenteCodeEinde", required = false)
    private final BrpGemeenteCode gemeenteCodeEinde;
    @Element(name = "woonplaatsnaamEinde", required = false)
    private final BrpString woonplaatsnaamEinde;
    @Element(name = "buitenlandsePlaatsEinde", required = false)
    private final BrpString buitenlandsePlaatsEinde;
    @Element(name = "buitenlandseRegioEinde", required = false)
    private final BrpString buitenlandseRegioEinde;
    @Element(name = "landOfGebiedCodeEinde", required = false)
    private final BrpLandOfGebiedCode landOfGebiedCodeEinde;
    @Element(name = "omschrijvingLocatieEinde", required = false)
    private final BrpString omschrijvingLocatieEinde;

    /**
     * Maakt een BrpRelatieInhoud object.
     */
    public BrpRelatieInhoud() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    /**
     * Maakt een BrpRelatieInhoud object.
     *
     * Een standaard betrokkenheid object wijkt in zoverre afdat de {@link #isLeeg()} methode altijd false retourneerd
     * en dat alle properties null zijn.
     *
     * @param datumAanvang
     *            de aanvangsdatum van deze relatie, mag null zijn
     * @param gemeenteCodeAanvang
     *            de gemeente waar de relatie is aangegaan, mag null zijn
     * @param woonplaatsnaamAanvang
     *            de woonplaats waar de relatie is aangegaan, mag null zijn
     * @param buitenlandsePlaatsAanvang
     *            de buitenlandse plaats waar de relatie is aangegaan, mag null zijn
     * @param buitenlandseRegioAanvang
     *            de buitenlandse regio waar de relatie is aangegaan, mag null zijn
     * @param landOfGebiedCodeAanvang
     *            de land code waar de relatie is aangegaan, mag null zijn
     * @param omschrijvingLocatieAanvang
     *            de omschrijving van de locatie waar de relatie is aangegaan, mag null zijn
     * @param redenEindeRelatieCode
     *            de reden voor de beëindiging van de relatie, mag null zijn
     * @param datumEinde
     *            de datum van het einde van de relatie, mag null zijn
     * @param gemeenteCodeEinde
     *            de gemeente code waar de relatie is beëindigd, mag null zijn
     * @param woonplaatsnaamEinde
     *            de plaats code waar de relatie is beëindigd, mag null zijn
     * @param buitenlandsePlaatsEinde
     *            de buitenlandse plaats waar de relatie is beëindigd, mag null zijn
     * @param buitenlandseRegioEinde
     *            de buitenlandse regio waar de relatie is beëindigd, mag null zijn
     * @param landOfGebiedCodeEinde
     *            de land code waar de relatie is beëindigd, mag null zijn
     * @param omschrijvingLocatieEinde
     *            de omschrijving van de locatie waar de relatie is beëindigd, mag null zijn
     */
    public BrpRelatieInhoud(
        /* Meer dan 7 parameters is in constructors van immutable model klassen getolereerd. */
        @Element(name = "datumAanvang", required = false) final BrpDatum datumAanvang,
        @Element(name = "gemeenteCodeAanvang", required = false) final BrpGemeenteCode gemeenteCodeAanvang,
        @Element(name = "woonplaatsnaamAanvang", required = false) final BrpString woonplaatsnaamAanvang,
        @Element(name = "buitenlandsePlaatsAanvang", required = false) final BrpString buitenlandsePlaatsAanvang,
        @Element(name = "buitenlandseRegioAanvang", required = false) final BrpString buitenlandseRegioAanvang,
        @Element(name = "landOfGebiedCodeAanvang", required = false) final BrpLandOfGebiedCode landOfGebiedCodeAanvang,
        @Element(name = "omschrijvingLocatieAanvang", required = false) final BrpString omschrijvingLocatieAanvang,
        @Element(name = "redenEindeRelatieCode", required = false) final BrpRedenEindeRelatieCode redenEindeRelatieCode,
        @Element(name = "datumEinde", required = false) final BrpDatum datumEinde,
        @Element(name = "gemeenteCodeEinde", required = false) final BrpGemeenteCode gemeenteCodeEinde,
        @Element(name = "woonplaatsnaamEinde", required = false) final BrpString woonplaatsnaamEinde,
        @Element(name = "buitenlandsePlaatsEinde", required = false) final BrpString buitenlandsePlaatsEinde,
        @Element(name = "buitenlandseRegioEinde", required = false) final BrpString buitenlandseRegioEinde,
        @Element(name = "landOfGebiedCodeEinde", required = false) final BrpLandOfGebiedCode landOfGebiedCodeEinde,
        @Element(name = "omschrijvingLocatieEinde", required = false) final BrpString omschrijvingLocatieEinde)
    {
        this.datumAanvang = datumAanvang;
        this.gemeenteCodeAanvang = gemeenteCodeAanvang;
        this.woonplaatsnaamAanvang = woonplaatsnaamAanvang;
        this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
        this.buitenlandseRegioAanvang = buitenlandseRegioAanvang;
        this.landOfGebiedCodeAanvang = landOfGebiedCodeAanvang;
        this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
        this.redenEindeRelatieCode = redenEindeRelatieCode;
        this.datumEinde = datumEinde;
        this.gemeenteCodeEinde = gemeenteCodeEinde;
        this.woonplaatsnaamEinde = woonplaatsnaamEinde;
        this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
        this.buitenlandseRegioEinde = buitenlandseRegioEinde;
        this.landOfGebiedCodeEinde = landOfGebiedCodeEinde;
        this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
    }

    @Override
    @Preconditie({SoortMeldingCode.PRE003, SoortMeldingCode.PRE004 })
    public void valideer() {
        BrpGroepValidator.valideerGemeenteInNederland(gemeenteCodeAanvang, landOfGebiedCodeAanvang, this);
        BrpGroepValidator.valideerGemeenteInNederland(gemeenteCodeEinde, landOfGebiedCodeEinde, this);
        BrpGroepValidator.valideerBuitenlandsePlaatsNietNederland(buitenlandsePlaatsAanvang, landOfGebiedCodeAanvang, this);
        BrpGroepValidator.valideerBuitenlandsePlaatsNietNederland(buitenlandsePlaatsEinde, landOfGebiedCodeEinde, this);
    }

    /**
     * Geef de waarde van datum aanvang.
     *
     * @return the datumAanvang of null
     */
    public BrpDatum getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * Geef de waarde van gemeente code aanvang.
     *
     * @return the gemeenteCodeAanvang of null
     */
    public BrpGemeenteCode getGemeenteCodeAanvang() {
        return gemeenteCodeAanvang;
    }

    /**
     * Geef de waarde van woonplaatsnaam aanvang.
     *
     * @return the woonplaatsnaamAanvang of null
     */
    public BrpString getWoonplaatsnaamAanvang() {
        return woonplaatsnaamAanvang;
    }

    /**
     * Geef de waarde van buitenlandse plaats aanvang.
     *
     * @return the buitenlandsePlaatsAanvang of null
     */
    public BrpString getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    /**
     * Geef de waarde van buitenlandse regio aanvang.
     *
     * @return the buitenlandseRegioAanvang of null
     */
    public BrpString getBuitenlandseRegioAanvang() {
        return buitenlandseRegioAanvang;
    }

    /**
     * Geef de waarde van land of gebied code aanvang.
     *
     * @return the landOfGebiedCodeAanvang of null
     */
    public BrpLandOfGebiedCode getLandOfGebiedCodeAanvang() {
        return landOfGebiedCodeAanvang;
    }

    /**
     * Geef de waarde van omschrijving locatie aanvang.
     *
     * @return the omschrijvingLocatieAanvang of null
     */
    public BrpString getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    /**
     * Geef de waarde van reden einde relatie code.
     *
     * @return the redenEindeRelatieCode of null
     */
    public BrpRedenEindeRelatieCode getRedenEindeRelatieCode() {
        return redenEindeRelatieCode;
    }

    /**
     * Geef de waarde van datum einde.
     *
     * @return the datumEinde of null
     */
    public BrpDatum getDatumEinde() {
        return datumEinde;
    }

    /**
     * Geef de waarde van gemeente code einde.
     *
     * @return the gemeenteCodeEinde of null
     */
    public BrpGemeenteCode getGemeenteCodeEinde() {
        return gemeenteCodeEinde;
    }

    /**
     * Geef de waarde van woonplaatsnaam einde.
     *
     * @return the woonplaatsnaamEinde of null
     */
    public BrpString getWoonplaatsnaamEinde() {
        return woonplaatsnaamEinde;
    }

    /**
     * Geef de waarde van buitenlandse plaats einde.
     *
     * @return the buitenlandsePlaatsEinde of null
     */
    public BrpString getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    /**
     * Geef de waarde van buitenlandse regio einde.
     *
     * @return the buitenlandseRegioEinde of null
     */
    public BrpString getBuitenlandseRegioEinde() {
        return buitenlandseRegioEinde;
    }

    /**
     * Geef de waarde van land of gebied code einde.
     *
     * @return the landOfGebiedCodeEinde of null
     */
    public BrpLandOfGebiedCode getLandOfGebiedCodeEinde() {
        return landOfGebiedCodeEinde;
    }

    /**
     * Geef de waarde van omschrijving locatie einde.
     *
     * @return the omschrijvingLocatieEinde of null
     */
    public BrpString getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    /**
     * Geef de einde gevuld.
     *
     * @return true als een van de elementen van groep 07 (Ontbinding huwelijk/geregistreerd partnerschap) is gevuld,
     *         false als deze allemaal null zijn
     */
    public boolean isEindeGevuld() {
        return redenEindeRelatieCode != null
               || datumEinde != null
               || gemeenteCodeEinde != null
               || woonplaatsnaamEinde != null
               || buitenlandsePlaatsEinde != null
               || buitenlandseRegioEinde != null
               || landOfGebiedCodeEinde != null
               || omschrijvingLocatieEinde != null;
    }

    /**
     * Geef de leeg.
     *
     * @return true als alle velden null zijn, anders false
     */
    @Override
    public boolean isLeeg() {
        return !Validatie.isEenParameterGevuld(
            datumAanvang,
            gemeenteCodeAanvang,
            woonplaatsnaamAanvang,
            buitenlandsePlaatsAanvang,
            buitenlandseRegioAanvang,
            landOfGebiedCodeAanvang,
            omschrijvingLocatieAanvang,
            redenEindeRelatieCode,
            datumEinde,
            gemeenteCodeEinde,
            woonplaatsnaamEinde,
            buitenlandsePlaatsEinde,
            buitenlandseRegioEinde,
            landOfGebiedCodeEinde,
            omschrijvingLocatieEinde);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpRelatieInhoud)) {
            return false;
        }
        final BrpRelatieInhoud castOther = (BrpRelatieInhoud) other;
        return new EqualsBuilder().append(datumAanvang, castOther.datumAanvang)
                                  .append(gemeenteCodeAanvang, castOther.gemeenteCodeAanvang)
                                  .append(woonplaatsnaamAanvang, castOther.woonplaatsnaamAanvang)
                                  .append(buitenlandsePlaatsAanvang, castOther.buitenlandsePlaatsAanvang)
                                  .append(buitenlandseRegioAanvang, castOther.buitenlandseRegioAanvang)
                                  .append(landOfGebiedCodeAanvang, castOther.landOfGebiedCodeAanvang)
                                  .append(omschrijvingLocatieAanvang, castOther.omschrijvingLocatieAanvang)
                                  .append(redenEindeRelatieCode, castOther.redenEindeRelatieCode)
                                  .append(datumEinde, castOther.datumEinde)
                                  .append(gemeenteCodeEinde, castOther.gemeenteCodeEinde)
                                  .append(woonplaatsnaamEinde, castOther.woonplaatsnaamEinde)
                                  .append(buitenlandsePlaatsEinde, castOther.buitenlandsePlaatsEinde)
                                  .append(buitenlandseRegioEinde, castOther.buitenlandseRegioEinde)
                                  .append(landOfGebiedCodeEinde, castOther.landOfGebiedCodeEinde)
                                  .append(omschrijvingLocatieEinde, castOther.omschrijvingLocatieEinde)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datumAanvang)
                                    .append(gemeenteCodeAanvang)
                                    .append(woonplaatsnaamAanvang)
                                    .append(buitenlandsePlaatsAanvang)
                                    .append(buitenlandseRegioAanvang)
                                    .append(landOfGebiedCodeAanvang)
                                    .append(omschrijvingLocatieAanvang)
                                    .append(redenEindeRelatieCode)
                                    .append(datumEinde)
                                    .append(gemeenteCodeEinde)
                                    .append(woonplaatsnaamEinde)
                                    .append(buitenlandsePlaatsEinde)
                                    .append(buitenlandseRegioEinde)
                                    .append(landOfGebiedCodeEinde)
                                    .append(omschrijvingLocatieEinde)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("datumAanvang", datumAanvang)
                                                                          .append("gemeenteCodeAanvang", gemeenteCodeAanvang)
                                                                          .append("woonplaatsnaamAanvang", woonplaatsnaamAanvang)
                                                                          .append("buitenlandsePlaatsAanvang", buitenlandsePlaatsAanvang)
                                                                          .append("buitenlandseRegioAanvang", buitenlandseRegioAanvang)
                                                                          .append("landOfGebiedCodeAanvang", landOfGebiedCodeAanvang)
                                                                          .append("omschrijvingLocatieAanvang", omschrijvingLocatieAanvang)
                                                                          .append("redenEindeRelatieCode", redenEindeRelatieCode)
                                                                          .append("datumEinde", datumEinde)
                                                                          .append("gemeenteCodeEinde", gemeenteCodeEinde)
                                                                          .append("woonplaatsnaamEinde", woonplaatsnaamEinde)
                                                                          .append("buitenlandsePlaatsEinde", buitenlandsePlaatsEinde)
                                                                          .append("buitenlandseRegioEinde", buitenlandseRegioEinde)
                                                                          .append("landOfGebiedCodeEinde", landOfGebiedCodeEinde)
                                                                          .append("omschrijvingLocatieEinde", omschrijvingLocatieEinde)
                                                                          .toString();
    }
}
