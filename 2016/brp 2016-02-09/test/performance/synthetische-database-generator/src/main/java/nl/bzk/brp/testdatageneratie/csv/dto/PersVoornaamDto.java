/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.csv.dto;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.testdatageneratie.domain.kern.HisPersvoornaam;
import nl.bzk.brp.testdatageneratie.domain.kern.Persvoornaam;

/**
 * Pers voornaam dto.
 */
public class PersVoornaamDto {

    private Persvoornaam persvoornaam;
    private List<HisPersvoornaam> hispersvoornaam = new ArrayList<HisPersvoornaam>();

    /**
     * Instantieert Pers voornaam dto.
     *
     * @param persvoornaam persvoornaam
     */
    public PersVoornaamDto(final Persvoornaam persvoornaam) {
        this.persvoornaam = persvoornaam;
    }

    /**
     * Toevoegen his record voor pers voornaam.
     *
     * @param his his pers voornaam.
     */
    public void add(final HisPersvoornaam his) {
        hispersvoornaam.add(his);
    }

    public List<HisPersvoornaam> getHispersvoornaam() {
        return hispersvoornaam;
    }

    public Persvoornaam getPersvoornaam() {
        return persvoornaam;
    }
}
