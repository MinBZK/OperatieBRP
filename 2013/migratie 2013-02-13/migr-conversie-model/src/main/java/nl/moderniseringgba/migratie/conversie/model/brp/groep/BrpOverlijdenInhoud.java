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
 * Deze class representeert de inhoud van de BRP groep Overlijden.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpOverlijdenInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "datum", required = false)
    private final BrpDatum datum;
    @Element(name = "gemeenteCode", required = false)
    private final BrpGemeenteCode gemeenteCode;
    @Element(name = "plaatsCode", required = false)
    private final BrpPlaatsCode plaatsCode;
    @Element(name = "buitenlandsePlaats", required = false)
    private final String buitenlandsePlaats;
    @Element(name = "buitenlandseRegio", required = false)
    private final String buitenlandseRegio;
    @Element(name = "landCode", required = false)
    private final BrpLandCode landCode;
    @Element(name = "omschrijvingLocatie", required = false)
    private final String omschrijvingLocatie;

    /**
     * Maakt een nieuw BrpOverlijdenInhoud object.
     * 
     * @param datum
     *            de datum van overlijden, mag niet null zijn
     * @param gemeenteCode
     *            de gemeente van overlijden, mag null zijn
     * @param plaatsCode
     *            de plaats van overlijden, mag null zijn
     * @param buitenlandsePlaats
     *            de buitenlandse plaats van overlijden, mag null zijn
     * @param buitenlandseRegio
     *            de buitenlandse regio van overlijden, mag null zijn
     * @param landCode
     *            het land van overlijden, mag niet null zijn
     * @param omschrijvingLocatie
     *            de omschrijving van de locatie van overlijden, mag null zijn
     * @throws NullPointerException
     *             als datum of landCode null is
     */
    public BrpOverlijdenInhoud(
            @Element(name = "datum", required = false) final BrpDatum datum,
            @Element(name = "gemeenteCode", required = false) final BrpGemeenteCode gemeenteCode,
            @Element(name = "plaatsCode", required = false) final BrpPlaatsCode plaatsCode,
            @Element(name = "buitenlandsePlaats", required = false) final String buitenlandsePlaats,
            @Element(name = "buitenlandseRegio", required = false) final String buitenlandseRegio,
            @Element(name = "landCode", required = false) final BrpLandCode landCode,
            @Element(name = "omschrijvingLocatie", required = false) final String omschrijvingLocatie) {
        if (ValidationUtils.isEenParameterGevuld(datum, gemeenteCode, plaatsCode, buitenlandsePlaats,
                buitenlandseRegio, landCode, omschrijvingLocatie)) {
            if (datum == null) {
                throw new NullPointerException(
                        "als één van de attributen van BRP Overlijden is gevuld dan mag de datum niet null zijn");
            }
            if (landCode == null) {
                throw new NullPointerException(
                        "als één van de attributen van BRP Overlijden is gevuld dan mag de landCode niet null zijn");
            }
        }
        this.datum = datum;
        this.gemeenteCode = gemeenteCode;
        this.plaatsCode = plaatsCode;
        this.buitenlandsePlaats = buitenlandsePlaats;
        this.buitenlandseRegio = buitenlandseRegio;
        this.landCode = landCode;
        this.omschrijvingLocatie = omschrijvingLocatie;
    }

    @Override
    @Preconditie({ Precondities.PRE003, Precondities.PRE004 })
    public void valideer() {
        final String groep = "Overlijden";
        BrpGroepValidator.valideerGemeenteInNederland(gemeenteCode, landCode, groep);
        BrpGroepValidator.valideerBuitenlandsePlaatsNietNederland(buitenlandsePlaats, landCode, groep);
    }

    /**
     * @return the datum, of null
     */
    public BrpDatum getDatum() {
        return datum;
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
     * @return the buitenlandsePlaats, of null
     */
    public String getBuitenlandsePlaats() {
        return buitenlandsePlaats;
    }

    /**
     * @return the buitenlandseRegio, of null
     */
    public String getBuitenlandseRegio() {
        return buitenlandseRegio;
    }

    /**
     * @return the landCode, of null
     */
    public BrpLandCode getLandCode() {
        return landCode;
    }

    /**
     * @return the omschrijvingLocatie, of null
     */
    public String getOmschrijvingLocatie() {
        return omschrijvingLocatie;
    }

    /**
     * @return true als deze groep als leeg moet worden beschouwd, anders false
     */
    @Override
    public boolean isLeeg() {
        return datum == null && landCode == null;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpOverlijdenInhoud)) {
            return false;
        }
        final BrpOverlijdenInhoud castOther = (BrpOverlijdenInhoud) other;
        return new EqualsBuilder().append(datum, castOther.datum).append(gemeenteCode, castOther.gemeenteCode)
                .append(plaatsCode, castOther.plaatsCode).append(buitenlandsePlaats, castOther.buitenlandsePlaats)
                .append(buitenlandseRegio, castOther.buitenlandseRegio).append(landCode, castOther.landCode)
                .append(omschrijvingLocatie, castOther.omschrijvingLocatie).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datum).append(gemeenteCode).append(plaatsCode).append(buitenlandsePlaats)
                .append(buitenlandseRegio).append(landCode).append(omschrijvingLocatie).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("datum", datum).append("gemeenteCode", gemeenteCode).append("plaatsCode", plaatsCode)
                .append("buitenlandsePlaats", buitenlandsePlaats).append("buitenlandseRegio", buitenlandseRegio)
                .append("landCode", landCode).append("omschrijvingLocatie", omschrijvingLocatie).toString();
    }
}
