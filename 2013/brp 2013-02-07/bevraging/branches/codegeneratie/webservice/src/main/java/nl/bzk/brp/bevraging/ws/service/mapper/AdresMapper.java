/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.mapper;

import java.math.BigDecimal;

import nl.bzk.brp.bevraging.ws.basis.Adres;
import nl.bzk.brp.domein.kern.PersoonAdres;


/**
 * Utility class voor het mappen van een {@link PersoonAdres} domein object naar een {@link Adres} DTO object.
 */
public final class AdresMapper {

    /**
     * Private constructor daar het hier om een utility class gaat die niet geinstantieert dient te worden.
     */
    private AdresMapper() {
    }

    /**
     * Utility methode voor het mappen van een @link PersoonAdres} domein object naar een {@link Adres} DTO object.
     *
     * @param adresDO het adres domein object.
     * @return een adres DTO object.
     */
    public static Adres mapDomeinObjectNaarDTO(final PersoonAdres adresDO) {
        if (adresDO == null) {
            return null;
        }

        Adres adres = new Adres();
        if (adresDO.getSoort() != null) {
            adres.setSoortAdresCode(adresDO.getSoort().getCode());
            adres.setSoortAdresNaam(adresDO.getSoort().getNaam());
        }
        adres.setAfgekorteNaamOpenbareRuimte(adresDO.getAfgekorteNaamOpenbareRuimte());
        adres.setGemeenteDeel(adresDO.getGemeentedeel());
        adres.setPostcode(adresDO.getPostcode());
        adres.setLocatieTOVAdres(adresDO.getLocatietovAdres());
        if (adresDO.getHuisnummer() != null) {
            adres.setHuisnummer(new BigDecimal(adresDO.getHuisnummer()));
        }
        adres.setHuisnummerToevoeging(adresDO.getHuisnummertoevoeging());
        adres.setHuisletter(adresDO.getHuisletter());
        return adres;
    }

}
