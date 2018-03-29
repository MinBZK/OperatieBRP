/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.domain.bevraging;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import org.springframework.util.Assert;

/**
 * Adresvraag (geconverteerd vanuit Xq01).
 */
@JsonInclude(Include.NON_NULL)
public final class Adresvraag extends Basisvraag {

    @JsonProperty
    private SoortIdentificatie soortIdentificatie;

    /**
     * Geef soort identificatie.
     * @return soort identificatie
     */
    public SoortIdentificatie getSoortIdentificatie() {
        return soortIdentificatie;
    }

    /**
     * Zet soort identificatie.
     * @param soortIdentificatie soort identificatie
     */
    public void setSoortIdentificatie(final SoortIdentificatie soortIdentificatie) {
        this.soortIdentificatie = soortIdentificatie;
    }

    /**
     * Maak van een adresvraag op persoonsidentificatie een adresvraag op adresidentificatie om de medebewoners op te
     * halen op basis van de meegegeven BAG sleutel.
     * @param bagSleutel BAG sleutel aan de hand waarvan de medebewoners worden opgezocht
     * @return een adresvraag op adresidentificatie om de medebewoners op te halen
     */
    public Adresvraag toMedebewonersAdresvraag(final String bagSleutel) {
        Assert.isTrue(
                this.soortIdentificatie == SoortIdentificatie.PERSOON,
                "Kan alleen worden aangeroepen op een adresvraag op basis van persoonsidentificatie.");

        ZoekCriterium bagCriterium = new ZoekCriterium(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getNaam());
        bagCriterium.setWaarde(bagSleutel);
        Adresvraag vraag = new Adresvraag();
        vraag.setGevraagdeRubrieken(this.getGevraagdeRubrieken());
        vraag.setZoekCriteria(Collections.singletonList(bagCriterium));
        vraag.setSoortDienst(this.getSoortDienst());
        vraag.setSoortIdentificatie(SoortIdentificatie.ADRES);
        return vraag;
    }

    /**
     * Soort identificatie.
     */
    public enum SoortIdentificatie {
        /**
         * Persoonsidentificatie.
         */
        PERSOON,

        /**
         * Adresidentificatie.
         */
        ADRES
    }
}
