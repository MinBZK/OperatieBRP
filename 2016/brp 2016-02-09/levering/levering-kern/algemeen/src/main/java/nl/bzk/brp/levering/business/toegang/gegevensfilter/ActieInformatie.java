/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.gegevensfilter;

import java.util.Set;

/**
 * Data holder utility object.
 */
public final class ActieInformatie {

    private Set<Long>  actiesVanuitGroepen;
    private Set<Long>  documentenVanuitGroepen;
    private Set<Short> rechtsGrondenVanuitGroepen;

    /**
     * @param actiesVanuitGroepen        acties
     * @param documentenVanuitGroepen    docuemten
     * @param rechtsGrondenVanuitGroepen rechtsgronden
     */
    public ActieInformatie(final Set<Long> actiesVanuitGroepen, final Set<Long> documentenVanuitGroepen, final Set<Short> rechtsGrondenVanuitGroepen) {
        this.actiesVanuitGroepen = actiesVanuitGroepen;
        this.documentenVanuitGroepen = documentenVanuitGroepen;
        this.rechtsGrondenVanuitGroepen = rechtsGrondenVanuitGroepen;
    }

    public Set<Long> getActiesVanuitGroepen() {
        return actiesVanuitGroepen;
    }

    public Set<Long> getDocumentenVanuitGroepen() {
        return documentenVanuitGroepen;
    }

    public Set<Short> getRechtsGrondenVanuitGroepen() {
        return rechtsGrondenVanuitGroepen;
    }

}
