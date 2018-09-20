/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import nl.moderniseringgba.migratie.Preconditie;
import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepValidator;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de BRP groep geboorte.
 * 
 * Deze class is immutable en thread safe.
 * 
 */
public final class BrpGeboorteInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "geboortedatum", required = false)
    private final BrpDatum geboortedatum;
    @Element(name = "gemeenteCode", required = false)
    private final BrpGemeenteCode gemeenteCode;
    @Element(name = "plaatsCode", required = false)
    private final BrpPlaatsCode plaatsCode;
    @Element(name = "buitenlandseGeboorteplaats", required = false)
    private final String buitenlandseGeboorteplaats;
    @Element(name = "buitenlandseRegioGeboorte", required = false)
    private final String buitenlandseRegioGeboorte;
    @Element(name = "landCode", required = false)
    private final BrpLandCode landCode;
    @Element(name = "omschrijvingGeboortelocatie", required = false)
    private final String omschrijvingGeboortelocatie;

    /**
     * Maakt een BrpGeboorteInhoud object.
     * 
     * @param geboortedatum
     *            de geboortedatum, mag null zijn
     * @param gemeenteCode
     *            de nederlandse gemeente code
     * @param plaatsCode
     *            de nederlandse plaats code
     * @param buitenlandseGeboorteplaats
     *            de buitenlandse geboorteplaats
     * @param buitenlandseRegioGeboorte
     *            de buitenlandse regio geboorte
     * @param landCode
     *            de land code, mag null zijn
     * @param omschrijvingGeboortelocatie
     *            de omschrijving van de geboortelocatie
     * @throws NullPointerException
     *             als geboortedatum of landCode null is
     */
    public BrpGeboorteInhoud(
            @Element(name = "geboortedatum", required = false) final BrpDatum geboortedatum,
            @Element(name = "gemeenteCode", required = false) final BrpGemeenteCode gemeenteCode,
            @Element(name = "plaatsCode", required = false) final BrpPlaatsCode plaatsCode,
            @Element(name = "buitenlandseGeboorteplaats", required = false) final String buitenlandseGeboorteplaats,
            @Element(name = "buitenlandseRegioGeboorte", required = false) final String buitenlandseRegioGeboorte,
            @Element(name = "landCode", required = false) final BrpLandCode landCode,
            @Element(name = "omschrijvingGeboortelocatie", required = false) final String omschrijvingGeboortelocatie) {
        this.geboortedatum = geboortedatum;
        this.gemeenteCode = gemeenteCode;
        this.plaatsCode = plaatsCode;
        this.buitenlandseGeboorteplaats = buitenlandseGeboorteplaats;
        this.buitenlandseRegioGeboorte = buitenlandseRegioGeboorte;
        this.landCode = landCode;
        this.omschrijvingGeboortelocatie = omschrijvingGeboortelocatie;
    }

    @Override
    @Preconditie({ Precondities.PRE003, Precondities.PRE004 })
    public void valideer() {
        final String groep = "Geboorte";
        BrpGroepValidator.valideerGemeenteInNederland(gemeenteCode, landCode, groep);
        BrpGroepValidator.valideerBuitenlandsePlaatsNietNederland(buitenlandseGeboorteplaats, landCode, groep);
    }

    /**
     * @return the geboortedatum
     */
    public BrpDatum getGeboortedatum() {
        return geboortedatum;
    }

    /**
     * @return the gemeenteCode, of null
     */
    public BrpGemeenteCode getGemeenteCode() {
        return gemeenteCode;
    }

    /**
     * @return the plaatsCode, of null
     */
    public BrpPlaatsCode getPlaatsCode() {
        return plaatsCode;
    }

    /**
     * @return the buitenlandseGeboorteplaats, of null
     */
    public String getBuitenlandseGeboorteplaats() {
        return buitenlandseGeboorteplaats;
    }

    /**
     * @return the buitenlandseRegioGeboorte, of null
     */
    public String getBuitenlandseRegioGeboorte() {
        return buitenlandseRegioGeboorte;
    }

    /**
     * @return the landCode
     */
    public BrpLandCode getLandCode() {
        return landCode;
    }

    /**
     * @return the omschrijvingGeboortelocatie, of null
     */
    public String getOmschrijvingGeboortelocatie() {
        return omschrijvingGeboortelocatie;
    }

    /**
     * @return true als alle attributen null zijn, anders false
     */
    @Override
    public boolean isLeeg() {
        return !ValidationUtils.isEenParameterGevuld(geboortedatum, gemeenteCode, plaatsCode,
                buitenlandseGeboorteplaats, buitenlandseRegioGeboorte, landCode, omschrijvingGeboortelocatie);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpGeboorteInhoud)) {
            return false;
        }
        final BrpGeboorteInhoud castOther = (BrpGeboorteInhoud) other;
        return new EqualsBuilder().append(geboortedatum, castOther.geboortedatum)
                .append(gemeenteCode, castOther.gemeenteCode).append(plaatsCode, castOther.plaatsCode)
                .append(buitenlandseGeboorteplaats, castOther.buitenlandseGeboorteplaats)
                .append(buitenlandseRegioGeboorte, castOther.buitenlandseRegioGeboorte)
                .append(landCode, castOther.landCode)
                .append(omschrijvingGeboortelocatie, castOther.omschrijvingGeboortelocatie).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(geboortedatum).append(gemeenteCode).append(plaatsCode)
                .append(buitenlandseGeboorteplaats).append(buitenlandseRegioGeboorte).append(landCode)
                .append(omschrijvingGeboortelocatie).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("geboortedatum", geboortedatum).append("gemeenteCode", gemeenteCode)
                .append("plaatsCode", plaatsCode).append("buitenlandseGeboorteplaats", buitenlandseGeboorteplaats)
                .append("buitenlandseRegioGeboorte", buitenlandseRegioGeboorte).append("landCode", landCode)
                .append("omschrijvingGeboortelocatie", omschrijvingGeboortelocatie).toString();
    }
}
