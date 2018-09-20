/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.csv.dto;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.testdatageneratie.domain.kern.HisPersnation;
import nl.bzk.brp.testdatageneratie.domain.kern.Persnation;

/**
 * Pers nation dto.
 */
public class PersNationDto {
    private Persnation persnation;
    private List<HisPersnation> hispersnation = new ArrayList<HisPersnation>();

    /**
     * Instantieert Pers nation dto.
     *
     * @param persnation persnation
     */
    public PersNationDto(final Persnation persnation) {
        this.persnation = persnation;
    }

    /**
     * Toevoegen van his pers nation.
     *
     * @param his his pers nation
     */
    public void add(final HisPersnation his) {
        hispersnation.add(his);
    }

    public Persnation getPersnation() {
        return persnation;
    }

    public List<HisPersnation> getHispersnation() {
        return hispersnation;
    }
}
