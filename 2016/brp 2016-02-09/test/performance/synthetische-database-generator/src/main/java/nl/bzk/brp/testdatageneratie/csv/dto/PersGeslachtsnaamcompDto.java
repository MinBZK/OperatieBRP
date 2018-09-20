/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.csv.dto;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.testdatageneratie.domain.kern.HisPersgeslnaamcomp;
import nl.bzk.brp.testdatageneratie.domain.kern.Persgeslnaamcomp;

/**
 * Pers geslachtsnaamcomp dto.
 */
public class PersGeslachtsnaamcompDto {

    private Persgeslnaamcomp persgeslnaamcomp;
    private List<HisPersgeslnaamcomp> hispersgeslnaamcomp = new ArrayList<HisPersgeslnaamcomp>();

    /**
     * Instantieert Pers geslachtsnaamcomp dto.
     *
     * @param persgeslnaamcomp persgeslnaamcomp
     */
    public PersGeslachtsnaamcompDto(final Persgeslnaamcomp persgeslnaamcomp) {
        this.persgeslnaamcomp = persgeslnaamcomp;
    }

    /**
     * Toevoegen his pers geslachtsnaam component.
     *
     * @param his his pers geslachtsnaam component
     */
    public void add(final HisPersgeslnaamcomp his) {
        hispersgeslnaamcomp.add(his);
    }

    public Persgeslnaamcomp getPersgeslnaamcomp() {
        return persgeslnaamcomp;
    }

    public List<HisPersgeslnaamcomp> getHispersgeslnaamcomp() {
        return hispersgeslnaamcomp;
    }
}
