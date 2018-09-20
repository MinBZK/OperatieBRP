/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;

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
    @Element(name = "woonplaatsnaamOverlijden", required = false)
    private final BrpString woonplaatsnaamOverlijden;
    @Element(name = "buitenlandsePlaats", required = false)
    private final BrpString buitenlandsePlaats;
    @Element(name = "buitenlandseRegio", required = false)
    private final BrpString buitenlandseRegio;
    @Element(name = "landOfGebiedCode", required = false)
    private final BrpLandOfGebiedCode landOfGebiedCode;
    @Element(name = "omschrijvingLocatie", required = false)
    private final BrpString omschrijvingLocatie;

    /**
     * Maakt een nieuw BrpOverlijdenInhoud object.
     * 
     * @param datum
     *            de datum van overlijden, mag niet null zijn
     * @param gemeenteCode
     *            de gemeente van overlijden, mag null zijn
     * @param woonplaatsnaamOverlijden
     *            de plaats van overlijden, mag null zijn
     * @param buitenlandsePlaats
     *            de buitenlandse plaats van overlijden, mag null zijn
     * @param buitenlandseRegio
     *            de buitenlandse regio van overlijden, mag null zijn
     * @param landOfGebiedCode
     *            het land van overlijden, mag niet null zijn
     * @param omschrijvingLocatie
     *            de omschrijving van de locatie van overlijden, mag null zijn
     * @throws NullPointerException
     *             als datum of landOfGebiedCode null is
     */
    public BrpOverlijdenInhoud(
        @Element(name = "datum", required = false) final BrpDatum datum,
        @Element(name = "gemeenteCode", required = false) final BrpGemeenteCode gemeenteCode,
        @Element(name = "woonplaatsnaamOverlijden", required = false) final BrpString woonplaatsnaamOverlijden,
        @Element(name = "buitenlandsePlaats", required = false) final BrpString buitenlandsePlaats,
        @Element(name = "buitenlandseRegio", required = false) final BrpString buitenlandseRegio,
        @Element(name = "landOfGebiedCode", required = false) final BrpLandOfGebiedCode landOfGebiedCode,
        @Element(name = "omschrijvingLocatie", required = false) final BrpString omschrijvingLocatie)
    {
        if (Validatie.isEenParameterGevuld(
            datum,
            gemeenteCode,
            woonplaatsnaamOverlijden,
            buitenlandsePlaats,
            buitenlandseRegio,
            landOfGebiedCode,
            omschrijvingLocatie))
        {
            if (datum == null) {
                throw new NullPointerException("als één van de attributen van BRP Overlijden is gevuld dan mag de datum niet null zijn");
            }
            if (landOfGebiedCode == null) {
                throw new NullPointerException("als één van de attributen van BRP Overlijden is gevuld dan mag de landOfGebiedCode niet null zijn");
            }
        }
        this.datum = datum;
        this.gemeenteCode = gemeenteCode;
        this.woonplaatsnaamOverlijden = woonplaatsnaamOverlijden;
        this.buitenlandsePlaats = buitenlandsePlaats;
        this.buitenlandseRegio = buitenlandseRegio;
        this.landOfGebiedCode = landOfGebiedCode;
        this.omschrijvingLocatie = omschrijvingLocatie;
    }

    @Override
    @Preconditie({SoortMeldingCode.PRE003, SoortMeldingCode.PRE004 })
    public void valideer() {
        BrpGroepValidator.valideerGemeenteInNederland(gemeenteCode, landOfGebiedCode, this);
        BrpGroepValidator.valideerBuitenlandsePlaatsNietNederland(buitenlandsePlaats, landOfGebiedCode, this);
    }

    /**
     * Geef de waarde van datum.
     *
     * @return the datum, of null
     */
    public BrpDatum getDatum() {
        return datum;
    }

    /**
     * Geef de waarde van gemeente code.
     *
     * @return the gemeenteCode, of null
     */
    public BrpGemeenteCode getGemeenteCode() {
        return gemeenteCode;
    }

    /**
     * Geef de waarde van woonplaatsnaam overlijden.
     *
     * @return the woonplaatsnaamOverlijden, of null
     */
    public BrpString getWoonplaatsnaamOverlijden() {
        return woonplaatsnaamOverlijden;
    }

    /**
     * Geef de waarde van buitenlandse plaats.
     *
     * @return the buitenlandsePlaats, of null
     */
    public BrpString getBuitenlandsePlaats() {
        return buitenlandsePlaats;
    }

    /**
     * Geef de waarde van buitenlandse regio.
     *
     * @return the buitenlandseRegio, of null
     */
    public BrpString getBuitenlandseRegio() {
        return buitenlandseRegio;
    }

    /**
     * Geef de waarde van land of gebied code.
     *
     * @return the landOfGebiedCode, of null
     */
    public BrpLandOfGebiedCode getLandOfGebiedCode() {
        return landOfGebiedCode;
    }

    /**
     * Geef de waarde van omschrijving locatie.
     *
     * @return the omschrijvingLocatie, of null
     */
    public BrpString getOmschrijvingLocatie() {
        return omschrijvingLocatie;
    }

    /**
     * Geef de leeg.
     *
     * @return true als deze groep als leeg moet worden beschouwd, anders false
     */
    @Override
    public boolean isLeeg() {
        return !Validatie.isEenParameterGevuld(datum, landOfGebiedCode);
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
        return new EqualsBuilder().append(datum, castOther.datum)
                                  .append(gemeenteCode, castOther.gemeenteCode)
                                  .append(woonplaatsnaamOverlijden, castOther.woonplaatsnaamOverlijden)
                                  .append(buitenlandsePlaats, castOther.buitenlandsePlaats)
                                  .append(buitenlandseRegio, castOther.buitenlandseRegio)
                                  .append(landOfGebiedCode, castOther.landOfGebiedCode)
                                  .append(omschrijvingLocatie, castOther.omschrijvingLocatie)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datum)
                                    .append(gemeenteCode)
                                    .append(woonplaatsnaamOverlijden)
                                    .append(buitenlandsePlaats)
                                    .append(buitenlandseRegio)
                                    .append(landOfGebiedCode)
                                    .append(omschrijvingLocatie)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("datum", datum)
                                                                          .append("gemeenteCode", gemeenteCode)
                                                                          .append("woonplaatsnaamOverlijden", woonplaatsnaamOverlijden)
                                                                          .append("buitenlandsePlaats", buitenlandsePlaats)
                                                                          .append("buitenlandseRegio", buitenlandseRegio)
                                                                          .append("landOfGebiedCode", landOfGebiedCode)
                                                                          .append("omschrijvingLocatie", omschrijvingLocatie)
                                                                          .toString();
    }
}
