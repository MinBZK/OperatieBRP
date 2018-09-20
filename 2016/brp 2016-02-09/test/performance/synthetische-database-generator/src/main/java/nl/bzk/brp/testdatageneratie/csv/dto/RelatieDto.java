/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.csv.dto;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.testdatageneratie.domain.kern.His;
import nl.bzk.brp.testdatageneratie.domain.kern.HisErkenningongeborenvrucht;
import nl.bzk.brp.testdatageneratie.domain.kern.HisHuwelijkgeregistreerdpar;
import nl.bzk.brp.testdatageneratie.domain.kern.HisNaamskeuzeongeborenvruch;
import nl.bzk.brp.testdatageneratie.domain.kern.Relatie;

/**
 * Relatie dto.
 */
public class RelatieDto {

    private Relatie relatie;
    private List<HisErkenningongeborenvrucht> hisErkenningongeborenvrucht;
    private List<HisHuwelijkgeregistreerdpar> hisHuwelijkgeregistreerdpar;
    private List<HisNaamskeuzeongeborenvruch> hisNaamskeuzeongeborenvruch;

    private List<BetrDto> betr;

    /**
     * Instantieert Relatie dto.
     *
     * @param relatie relatie
     */
    public RelatieDto(final Relatie relatie) {
        this.relatie = relatie;
        hisErkenningongeborenvrucht = new ArrayList<HisErkenningongeborenvrucht>();
        hisHuwelijkgeregistreerdpar = new ArrayList<HisHuwelijkgeregistreerdpar>();
        hisNaamskeuzeongeborenvruch = new ArrayList<HisNaamskeuzeongeborenvruch>();
        betr = new ArrayList<BetrDto>();
    }

    /**
     * Toevoegen van betrokkenheid dto.
     *
     * @param betrDto betrokkenheid dto
     */
    public void add(final BetrDto betrDto) {
        betr.add(betrDto);
    }

    /**
     * Toevoegen his record.
     *
     * @param his his record
     */
    public void add(final His his) {
        if (his instanceof HisHuwelijkgeregistreerdpar) {
            hisHuwelijkgeregistreerdpar.add((HisHuwelijkgeregistreerdpar) his);
        } else if (his instanceof HisErkenningongeborenvrucht) {
            hisErkenningongeborenvrucht.add((HisErkenningongeborenvrucht) his);
        } else if (his instanceof HisNaamskeuzeongeborenvruch) {
            hisNaamskeuzeongeborenvruch.add((HisNaamskeuzeongeborenvruch) his);
        } else {
            throw new RuntimeException("Kan geen object vinden met type " + his.getClass().getSimpleName());
        }
    }

    public Relatie getRelatie() {
        return relatie;
    }

    public List<HisErkenningongeborenvrucht> getHisErkenningongeborenvrucht() {
        return hisErkenningongeborenvrucht;
    }

    public List<HisHuwelijkgeregistreerdpar> getHisHuwelijkgeregistreerdpar() {
        return hisHuwelijkgeregistreerdpar;
    }

    public List<HisNaamskeuzeongeborenvruch> getHisNaamskeuzeongeborenvruch() {
        return hisNaamskeuzeongeborenvruch;
    }

    public List<BetrDto> getBetr() {
        return betr;
    }
}
