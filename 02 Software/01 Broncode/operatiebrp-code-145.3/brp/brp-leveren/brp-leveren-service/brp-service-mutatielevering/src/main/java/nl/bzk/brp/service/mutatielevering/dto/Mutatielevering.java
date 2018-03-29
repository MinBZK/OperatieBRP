/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.dto;

import java.util.Collection;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * Dit object bevat de handeling, de autorisatie en de te leveren personen (subset van bijgehouden personen).
 */
public final class Mutatielevering {

    /**
     * de autorisatie die geleverd wordt
     */
    private final Autorisatiebundel autorisatiebundel;

    /**
     * map met te leveren personen en de daarbij behoren populatie.
     */
    private final Map<Persoonslijst, Populatie> teLeverenPersonenMap;

    /**
     * Constructor.
     * @param autorisatiebundel de autorisatie
     * @param teLeverenPersonenMap de personen met populatie
     */
    public Mutatielevering(final Autorisatiebundel autorisatiebundel,
                           final Map<Persoonslijst, Populatie> teLeverenPersonenMap) {
        this.autorisatiebundel = autorisatiebundel;
        this.teLeverenPersonenMap = teLeverenPersonenMap;
    }

    public Autorisatiebundel getAutorisatiebundel() {
        return autorisatiebundel;
    }

    public Map<Persoonslijst, Populatie> getTeLeverenPersonenMap() {
        return teLeverenPersonenMap;
    }

    public Collection<Persoonslijst> getPersonen() {
        return teLeverenPersonenMap.keySet();
    }

    public Stelsel getStelsel() {
        return autorisatiebundel.getLeveringsautorisatie().getStelsel();
    }

}
