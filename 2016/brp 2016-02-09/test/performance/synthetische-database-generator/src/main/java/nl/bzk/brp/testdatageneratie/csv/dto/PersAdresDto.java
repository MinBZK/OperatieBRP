/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.csv.dto;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.testdatageneratie.domain.kern.HisPersadres;
import nl.bzk.brp.testdatageneratie.domain.kern.Persadres;
/**
 * Houder voor een PersAdres objec en zijn historie.
 * Het adresseerbaarobject, identificatienummer, huisnummertoevoeging wordt elke keer overschreven.
 * Het huisnummer wordt ook elke keer overschreven, maar is gebasserd adh. originele huisnr.
 *
 */
public class PersAdresDto {

    private Persadres persadres;
    private Integer origineleHuisnr;
    private List<HisPersadres> hispersadres = new ArrayList<HisPersadres>();

    /**
     * Instantieert Pers adres dto.
     *
     * @param persadres persadres
     */
    public PersAdresDto(final Persadres persadres) {
        this.persadres = persadres;
        origineleHuisnr = persadres.getHuisnr();
    }

    /**
     * Toevoegen van his pers adres.
     *
     * @param his his pers adres
     */
    public void add(final HisPersadres his) {
        hispersadres.add(his);
    }

    public List<HisPersadres> getHispersadres() {
        return hispersadres;
    }

    public Persadres getPersadres() {
        return persadres;
    }

    public Integer getOrigineleHuisnr() {
        return origineleHuisnr;
    }
}
