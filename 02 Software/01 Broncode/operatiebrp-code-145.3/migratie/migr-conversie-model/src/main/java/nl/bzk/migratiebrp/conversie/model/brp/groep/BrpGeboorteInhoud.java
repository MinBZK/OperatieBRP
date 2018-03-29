/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de BRP groep geboorte.
 *
 * Deze class is immutable en thread safe.
 */
public final class BrpGeboorteInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "geboortedatum", required = false)
    private final BrpDatum geboortedatum;
    @Element(name = "gemeenteCode", required = false)
    private final BrpGemeenteCode gemeenteCode;
    @Element(name = "woonplaatsnaamGeboorte", required = false)
    private final BrpString woonplaatsnaamGeboorte;
    @Element(name = "buitenlandsePlaatsGeboorte", required = false)
    private final BrpString buitenlandsePlaatsGeboorte;
    @Element(name = "buitenlandseRegioGeboorte", required = false)
    private final BrpString buitenlandseRegioGeboorte;
    @Element(name = "landOfGebiedCode", required = false)
    private final BrpLandOfGebiedCode landOfGebiedCode;
    @Element(name = "omschrijvingGeboortelocatie", required = false)
    private final BrpString omschrijvingGeboortelocatie;

    /**
     * Maakt een BrpGeboorteInhoud object.
     * @param geboortedatum de geboortedatum, mag null zijn
     * @param gemeenteCode de nederlandse gemeente code
     * @param woonplaatsnaamGeboorte de nederlandse plaats code
     * @param buitenlandsePlaatsGeboorte de buitenlandse geboorteplaats
     * @param buitenlandseRegioGeboorte de buitenlandse regio geboorte
     * @param landOfGebiedCode de land code, mag null zijn
     * @param omschrijvingGeboortelocatie de omschrijving van de geboortelocatie
     * @throws NullPointerException als geboortedatum of landOfGebiedCode null is
     */
    public BrpGeboorteInhoud(
            @Element(name = "geboortedatum", required = false) final BrpDatum geboortedatum,
            @Element(name = "gemeenteCode", required = false) final BrpGemeenteCode gemeenteCode,
            @Element(name = "woonplaatsnaamGeboorte", required = false) final BrpString woonplaatsnaamGeboorte,
            @Element(name = "buitenlandsePlaatsGeboorte", required = false) final BrpString buitenlandsePlaatsGeboorte,
            @Element(name = "buitenlandseRegioGeboorte", required = false) final BrpString buitenlandseRegioGeboorte,
            @Element(name = "landOfGebiedCode", required = false) final BrpLandOfGebiedCode landOfGebiedCode,
            @Element(name = "omschrijvingGeboortelocatie", required = false) final BrpString omschrijvingGeboortelocatie) {
        this.geboortedatum = geboortedatum;
        this.gemeenteCode = gemeenteCode;
        this.woonplaatsnaamGeboorte = woonplaatsnaamGeboorte;
        this.buitenlandsePlaatsGeboorte = buitenlandsePlaatsGeboorte;
        this.buitenlandseRegioGeboorte = buitenlandseRegioGeboorte;
        this.landOfGebiedCode = landOfGebiedCode;
        this.omschrijvingGeboortelocatie = omschrijvingGeboortelocatie;
    }

    @Override
    @Preconditie({SoortMeldingCode.PRE003, SoortMeldingCode.PRE004})
    public void valideer() {
        BrpGroepValidator.valideerGemeenteInNederland(gemeenteCode, landOfGebiedCode, this);
        BrpGroepValidator.valideerBuitenlandsePlaatsNietNederland(buitenlandsePlaatsGeboorte, landOfGebiedCode, this);
    }

    /**
     * Geef de waarde van geboortedatum van BrpGeboorteInhoud.
     * @return de waarde van geboortedatum van BrpGeboorteInhoud
     */
    public BrpDatum getGeboortedatum() {
        return geboortedatum;
    }

    /**
     * Geef de waarde van gemeente code van BrpGeboorteInhoud.
     * @return de waarde van gemeente code van BrpGeboorteInhoud
     */
    public BrpGemeenteCode getGemeenteCode() {
        return gemeenteCode;
    }

    /**
     * Geef de waarde van woonplaatsnaam geboorte van BrpGeboorteInhoud.
     * @return de waarde van woonplaatsnaam geboorte van BrpGeboorteInhoud
     */
    public BrpString getWoonplaatsnaamGeboorte() {
        return woonplaatsnaamGeboorte;
    }

    /**
     * Geef de waarde van buitenlandse plaats geboorte van BrpGeboorteInhoud.
     * @return de waarde van buitenlandse plaats geboorte van BrpGeboorteInhoud
     */
    public BrpString getBuitenlandsePlaatsGeboorte() {
        return buitenlandsePlaatsGeboorte;
    }

    /**
     * Geef de waarde van buitenlandse regio geboorte van BrpGeboorteInhoud.
     * @return de waarde van buitenlandse regio geboorte van BrpGeboorteInhoud
     */
    public BrpString getBuitenlandseRegioGeboorte() {
        return buitenlandseRegioGeboorte;
    }

    /**
     * Geef de waarde van land of gebied code van BrpGeboorteInhoud.
     * @return de waarde van land of gebied code van BrpGeboorteInhoud
     */
    public BrpLandOfGebiedCode getLandOfGebiedCode() {
        return landOfGebiedCode;
    }

    /**
     * Geef de waarde van omschrijving geboortelocatie van BrpGeboorteInhoud.
     * @return de waarde van omschrijving geboortelocatie van BrpGeboorteInhoud
     */
    public BrpString getOmschrijvingGeboortelocatie() {
        return omschrijvingGeboortelocatie;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !BrpValidatie.isEenParameterGevuld(
                geboortedatum,
                gemeenteCode,
                woonplaatsnaamGeboorte,
                buitenlandsePlaatsGeboorte,
                buitenlandseRegioGeboorte,
                landOfGebiedCode,
                omschrijvingGeboortelocatie);
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
                .append(gemeenteCode, castOther.gemeenteCode)
                .append(woonplaatsnaamGeboorte, castOther.woonplaatsnaamGeboorte)
                .append(buitenlandsePlaatsGeboorte, castOther.buitenlandsePlaatsGeboorte)
                .append(buitenlandseRegioGeboorte, castOther.buitenlandseRegioGeboorte)
                .append(landOfGebiedCode, castOther.landOfGebiedCode)
                .append(omschrijvingGeboortelocatie, castOther.omschrijvingGeboortelocatie)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(geboortedatum)
                .append(gemeenteCode)
                .append(woonplaatsnaamGeboorte)
                .append(buitenlandsePlaatsGeboorte)
                .append(buitenlandseRegioGeboorte)
                .append(landOfGebiedCode)
                .append(omschrijvingGeboortelocatie)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("geboortedatum", geboortedatum)
                .append("gemeenteCode", gemeenteCode)
                .append("woonplaatsnaamGeboorte", woonplaatsnaamGeboorte)
                .append("buitenlandsePlaatsGeboorte", buitenlandsePlaatsGeboorte)
                .append("buitenlandseRegioGeboorte", buitenlandseRegioGeboorte)
                .append("landOfGebiedCode", landOfGebiedCode)
                .append("omschrijvingGeboortelocatie", omschrijvingGeboortelocatie)
                .toString();
    }
}
