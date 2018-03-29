/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

import java.util.Collections;
import java.util.Set;
import nl.bzk.brp.domain.element.AttribuutElement;

/**
 * SelectieLijst.
 */
public final class SelectieLijst {

    public static final SelectieLijst GEEN_LIJST = new SelectieLijst(-1, null, Collections.emptySet());

    private final Integer dienstId;
    private final Set<String> waarden;
    private final AttribuutElement waardeType;

    /**
     * Instantiates a new Selectie lijst.
     * @param dienstId the dienst id
     * @param waardeType the waarde type
     * @param waarden the waarden
     */
    public SelectieLijst(final Integer dienstId, final AttribuutElement waardeType, final Set<String> waarden) {
        this.dienstId = dienstId;
        this.waarden = waarden;
        this.waardeType = waardeType;
    }

    /**
     * Gets dienst id.
     * @return the dienst id
     */
    public Integer getDienstId() {
        return dienstId;
    }

    /**
     * Gets waarden.
     * @return the waarden
     */
    public Set<String> getWaarden() {
        return waarden;
    }

    /**
     * Gets waarde type.
     * @return the waarde type
     */
    public AttribuutElement getWaardeType() {
        return waardeType;
    }

    public boolean isLeeg() {
        return this == GEEN_LIJST;
    }
}
