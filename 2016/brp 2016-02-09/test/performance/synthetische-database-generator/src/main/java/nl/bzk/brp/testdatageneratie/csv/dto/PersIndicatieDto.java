/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.csv.dto;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.testdatageneratie.domain.kern.HisPersindicatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Persindicatie;

/**
 * Pers indicatie dto.
 */
public class PersIndicatieDto {

    private Persindicatie persindicatie;

    private List<HisPersindicatie> hispersindicatie = new ArrayList<HisPersindicatie>();

    /**
     * Instantieert Pers indicatie dto.
     *
     * @param persindicatie persindicatie
     */
    public PersIndicatieDto(final Persindicatie persindicatie) {
        this.persindicatie = persindicatie;
    }

    /**
     * Add void.
     *
     * @param his his
     */
    public void add(final HisPersindicatie his) {
        hispersindicatie.add(his);
    }

    public Persindicatie getPersindicatie() {
        return persindicatie;
    }

    public List<HisPersindicatie> getHispersindicatie() {
        return hispersindicatie;
    }

}
