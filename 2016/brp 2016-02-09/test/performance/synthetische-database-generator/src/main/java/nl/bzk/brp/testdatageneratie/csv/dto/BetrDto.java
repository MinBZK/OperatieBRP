/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.csv.dto;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.testdatageneratie.domain.kern.Betr;
import nl.bzk.brp.testdatageneratie.domain.kern.His;
import nl.bzk.brp.testdatageneratie.domain.kern.HisOuderouderlijkgezag;
import nl.bzk.brp.testdatageneratie.domain.kern.HisOuderouderschap;

/**
 * Is een houder voor een Betr object en lijsten van ouderschap en ouderlijk gezag historie records.
 * Daarnaast moeten we originele logischer persoonID onthouden.
 *
 */
public class BetrDto {

    private Betr betr;
    private List<HisOuderouderschap> hisOuderouderschap;
    private List<HisOuderouderlijkgezag> hisOuderouderlijkgezag;
    private Integer logischePersId;

    /**
     * Instantieert Betr dto.
     *
     * @param betr betr
     */
    public BetrDto(final Betr betr) {
        this.betr = betr;
        // maak een copie, omdat het betr dadelijk continue wordt overschreven.
        logischePersId = betr.getPers();
        hisOuderouderlijkgezag = new ArrayList<HisOuderouderlijkgezag>();
        hisOuderouderschap = new ArrayList<HisOuderouderschap>();
    }

    /**
     * 'Interface' om een historisch record aan de lijst toe te voegen.
     *
     * @param his his record
     */
    public void add(final His his) {
        if (his instanceof HisOuderouderschap) {
            hisOuderouderschap.add((HisOuderouderschap) his);
        } else if (his instanceof HisOuderouderlijkgezag) {
            hisOuderouderlijkgezag.add((HisOuderouderlijkgezag) his);
        } else {
            throw new RuntimeException("Kan geen object vinden met type " + his.getClass().getSimpleName());
        }
    }

    public Betr getBetr() {
        return betr;
    }

    public List<HisOuderouderschap> getHisOuderouderschap() {
        return hisOuderouderschap;
    }

    public List<HisOuderouderlijkgezag> getHisOuderouderlijkgezag() {
        return hisOuderouderlijkgezag;
    }

    public Integer getLogischePersId() {
        return logischePersId;
    }
}
