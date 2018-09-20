/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import nl.moderniseringgba.migratie.GegevensSet;
import nl.moderniseringgba.migratie.Preconditie;
import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepValidator;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

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
    @Element(name = "plaatsCodeAanvang", required = false)
    private final BrpPlaatsCode plaatsCodeAanvang;
    @Element(name = "buitenlandsePlaatsAanvang", required = false)
    private final String buitenlandsePlaatsAanvang;
    @Element(name = "buitenlandseRegioAanvang", required = false)
    private final String buitenlandseRegioAanvang;
    @Element(name = "landCodeAanvang", required = false)
    private final BrpLandCode landCodeAanvang;
    @Element(name = "omschrijvingLocatieAanvang", required = false)
    private final String omschrijvingLocatieAanvang;

    @Element(name = "redenEinde", required = false)
    private final BrpRedenEindeRelatieCode redenEinde;
    @GegevensSet
    @Element(name = "datumEinde", required = false)
    private final BrpDatum datumEinde;
    @Element(name = "gemeenteCodeEinde", required = false)
    private final BrpGemeenteCode gemeenteCodeEinde;
    @Element(name = "plaatsCodeEinde", required = false)
    private final BrpPlaatsCode plaatsCodeEinde;
    @Element(name = "buitenlandsePlaatsEinde", required = false)
    private final String buitenlandsePlaatsEinde;
    @Element(name = "buitenlandseRegioEinde", required = false)
    private final String buitenlandseRegioEinde;
    @Element(name = "landCodeEinde", required = false)
    private final BrpLandCode landCodeEinde;
    @Element(name = "omschrijvingLocatieEinde", required = false)
    private final String omschrijvingLocatieEinde;

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
     * @param plaatsCodeAanvang
     *            de woonplaats waar de relatie is aangegaan, mag null zijn
     * @param buitenlandsePlaatsAanvang
     *            de buitenlandse plaats waar de relatie is aangegaan, mag null zijn
     * @param buitenlandseRegioAanvang
     *            de buitenlandse regio waar de relatie is aangegaan, mag null zijn
     * @param landCodeAanvang
     *            de land code waar de relatie is aangegaan, mag null zijn
     * @param omschrijvingLocatieAanvang
     *            de omschrijving van de locatie waar de relatie is aangegaan, mag null zijn
     * @param redenEinde
     *            de reden voor de beëindiging van de relatie, mag null zijn
     * @param datumEinde
     *            de datum van het einde van de relatie, mag null zijn
     * @param gemeenteCodeEinde
     *            de gemeente code waar de relatie is beëindigd, mag null zijn
     * @param plaatsCodeEinde
     *            de plaats code waar de relatie is beëindigd, mag null zijn
     * @param buitenlandsePlaatsEinde
     *            de buitenlandse plaats waar de relatie is beëindigd, mag null zijn
     * @param buitenlandseRegioEinde
     *            de buitenlandse regio waar de relatie is beëindigd, mag null zijn
     * @param landCodeEinde
     *            de land code waar de relatie is beëindigd, mag null zijn
     * @param omschrijvingLocatieEinde
     *            de omschrijving van de locatie waar de relatie is beëindigd, mag null zijn
     */
    // CHECKSTYLE:OFF - Meer dan 7 parameters is in constructors van immutable model klassen getolereerd.
    public BrpRelatieInhoud(
            @Element(name = "datumAanvang", required = false) final BrpDatum datumAanvang,
            @Element(name = "gemeenteCodeAanvang", required = false) final BrpGemeenteCode gemeenteCodeAanvang,
            @Element(name = "plaatsCodeAanvang", required = false) final BrpPlaatsCode plaatsCodeAanvang,
            @Element(name = "buitenlandsePlaatsAanvang", required = false) final String buitenlandsePlaatsAanvang,
            @Element(name = "buitenlandseRegioAanvang", required = false) final String buitenlandseRegioAanvang,
            @Element(name = "landCodeAanvang", required = false) final BrpLandCode landCodeAanvang,
            @Element(name = "omschrijvingLocatieAanvang", required = false) final String omschrijvingLocatieAanvang,
            @Element(name = "redenEinde", required = false) final BrpRedenEindeRelatieCode redenEinde,
            @Element(name = "datumEinde", required = false) final BrpDatum datumEinde,
            @Element(name = "gemeenteCodeEinde", required = false) final BrpGemeenteCode gemeenteCodeEinde,
            @Element(name = "plaatsCodeEinde", required = false) final BrpPlaatsCode plaatsCodeEinde,
            @Element(name = "buitenlandsePlaatsEinde", required = false) final String buitenlandsePlaatsEinde,
            @Element(name = "buitenlandseRegioEinde", required = false) final String buitenlandseRegioEinde,
            @Element(name = "landCodeEinde", required = false) final BrpLandCode landCodeEinde,
            @Element(name = "omschrijvingLocatieEinde", required = false) final String omschrijvingLocatieEinde) {
        // CHECKSTYLE:ON
        this.datumAanvang = datumAanvang;
        this.gemeenteCodeAanvang = gemeenteCodeAanvang;
        this.plaatsCodeAanvang = plaatsCodeAanvang;
        this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
        this.buitenlandseRegioAanvang = buitenlandseRegioAanvang;
        this.landCodeAanvang = landCodeAanvang;
        this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
        this.redenEinde = redenEinde;
        this.datumEinde = datumEinde;
        this.gemeenteCodeEinde = gemeenteCodeEinde;
        this.plaatsCodeEinde = plaatsCodeEinde;
        this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
        this.buitenlandseRegioEinde = buitenlandseRegioEinde;
        this.landCodeEinde = landCodeEinde;
        this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
    }

    @Override
    @Preconditie({ Precondities.PRE003, Precondities.PRE004 })
    public void valideer() {
        final String groep = "Relatie";
        BrpGroepValidator.valideerGemeenteInNederland(gemeenteCodeAanvang, landCodeAanvang, groep);
        BrpGroepValidator.valideerGemeenteInNederland(gemeenteCodeEinde, landCodeEinde, groep);
        BrpGroepValidator.valideerBuitenlandsePlaatsNietNederland(buitenlandsePlaatsAanvang, landCodeAanvang, groep);
        BrpGroepValidator.valideerBuitenlandsePlaatsNietNederland(buitenlandsePlaatsEinde, landCodeEinde, groep);
    }

    /**
     * @return the datumAanvang of null
     */
    public BrpDatum getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * @return the gemeenteCodeAanvang of null
     */
    public BrpGemeenteCode getGemeenteCodeAanvang() {
        return gemeenteCodeAanvang;
    }

    /**
     * @return the plaatsCodeAanvang of null
     */
    public BrpPlaatsCode getPlaatsCodeAanvang() {
        return plaatsCodeAanvang;
    }

    /**
     * @return the buitenlandsePlaatsAanvang of null
     */
    public String getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    /**
     * @return the buitenlandseRegioAanvang of null
     */
    public String getBuitenlandseRegioAanvang() {
        return buitenlandseRegioAanvang;
    }

    /**
     * @return the landCodeAanvang of null
     */
    public BrpLandCode getLandCodeAanvang() {
        return landCodeAanvang;
    }

    /**
     * @return the omschrijvingLocatieAanvang of null
     */
    public String getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    /**
     * @return the redenEinde of null
     */
    public BrpRedenEindeRelatieCode getRedenEinde() {
        return redenEinde;
    }

    /**
     * @return the datumEinde of null
     */
    public BrpDatum getDatumEinde() {
        return datumEinde;
    }

    /**
     * @return the gemeenteCodeEinde of null
     */
    public BrpGemeenteCode getGemeenteCodeEinde() {
        return gemeenteCodeEinde;
    }

    /**
     * @return the plaatsCodeEinde of null
     */
    public BrpPlaatsCode getPlaatsCodeEinde() {
        return plaatsCodeEinde;
    }

    /**
     * @return the buitenlandsePlaatsEinde of null
     */
    public String getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    /**
     * @return the buitenlandseRegioEinde of null
     */
    public String getBuitenlandseRegioEinde() {
        return buitenlandseRegioEinde;
    }

    /**
     * @return the landCodeEinde of null
     */
    public BrpLandCode getLandCodeEinde() {
        return landCodeEinde;
    }

    /**
     * @return the omschrijvingLocatieEinde of null
     */
    public String getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    /**
     * @return true als alle velden null zijn, anders false
     */
    @Override
    public boolean isLeeg() {
        return !ValidationUtils.isEenParameterGevuld(datumAanvang, gemeenteCodeAanvang, plaatsCodeAanvang,
                buitenlandsePlaatsAanvang, buitenlandseRegioAanvang, landCodeAanvang, omschrijvingLocatieAanvang,
                redenEinde, datumEinde, gemeenteCodeEinde, plaatsCodeEinde, buitenlandsePlaatsEinde,
                buitenlandseRegioEinde, landCodeEinde, omschrijvingLocatieEinde);
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
                .append(plaatsCodeAanvang, castOther.plaatsCodeAanvang)
                .append(buitenlandsePlaatsAanvang, castOther.buitenlandsePlaatsAanvang)
                .append(buitenlandseRegioAanvang, castOther.buitenlandseRegioAanvang)
                .append(landCodeAanvang, castOther.landCodeAanvang)
                .append(omschrijvingLocatieAanvang, castOther.omschrijvingLocatieAanvang)
                .append(redenEinde, castOther.redenEinde).append(datumEinde, castOther.datumEinde)
                .append(gemeenteCodeEinde, castOther.gemeenteCodeEinde)
                .append(plaatsCodeEinde, castOther.plaatsCodeEinde)
                .append(buitenlandsePlaatsEinde, castOther.buitenlandsePlaatsEinde)
                .append(buitenlandseRegioEinde, castOther.buitenlandseRegioEinde)
                .append(landCodeEinde, castOther.landCodeEinde)
                .append(omschrijvingLocatieEinde, castOther.omschrijvingLocatieEinde).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datumAanvang).append(gemeenteCodeAanvang).append(plaatsCodeAanvang)
                .append(buitenlandsePlaatsAanvang).append(buitenlandseRegioAanvang).append(landCodeAanvang)
                .append(omschrijvingLocatieAanvang).append(redenEinde).append(datumEinde).append(gemeenteCodeEinde)
                .append(plaatsCodeEinde).append(buitenlandsePlaatsEinde).append(buitenlandseRegioEinde)
                .append(landCodeEinde).append(omschrijvingLocatieEinde).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("datumAanvang", datumAanvang).append("gemeenteCodeAanvang", gemeenteCodeAanvang)
                .append("plaatsCodeAanvang", plaatsCodeAanvang)
                .append("buitenlandsePlaatsAanvang", buitenlandsePlaatsAanvang)
                .append("buitenlandseRegioAanvang", buitenlandseRegioAanvang)
                .append("landCodeAanvang", landCodeAanvang)
                .append("omschrijvingLocatieAanvang", omschrijvingLocatieAanvang).append("redenEinde", redenEinde)
                .append("datumEinde", datumEinde).append("gemeenteCodeEinde", gemeenteCodeEinde)
                .append("plaatsCodeEinde", plaatsCodeEinde)
                .append("buitenlandsePlaatsEinde", buitenlandsePlaatsEinde)
                .append("buitenlandseRegioEinde", buitenlandseRegioEinde).append("landCodeEinde", landCodeEinde)
                .append("omschrijvingLocatieEinde", omschrijvingLocatieEinde).toString();
    }
}
