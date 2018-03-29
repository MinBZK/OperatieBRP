/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.generiek;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.AbstractZoekPersoonVerzoek;

/**
 * ZoekPersoonVerzoek specifiek voor GBA.
 */
public class ZoekPersoonGbaVerzoek extends AbstractZoekPersoonVerzoek implements ZoekPersoonGeneriekGbaVerzoek {

    private List<String> zoekRubrieken = new ArrayList<>();
    private List<String> gevraagdeRubrieken = new ArrayList<>();

    @Override
    public List<String> getZoekRubrieken() {
        return zoekRubrieken;
    }

    /**
     * Zet de zoekrubrieken.
     * @param zoekRubrieken de zoekrubrieken
     */
    void setZoekRubrieken(final List<String> zoekRubrieken) {
        this.zoekRubrieken = new ArrayList<>(zoekRubrieken);
    }

    @Override
    public List<String> getGevraagdeRubrieken() {
        return gevraagdeRubrieken;
    }

    /**
     * Zet de gevraagde rubrieken.
     * @param gevraagdeRubrieken de gevraagde rubrieken
     */
    void setGevraagdeRubrieken(final List<String> gevraagdeRubrieken) {
        this.gevraagdeRubrieken = new ArrayList<>(gevraagdeRubrieken);
    }
}
