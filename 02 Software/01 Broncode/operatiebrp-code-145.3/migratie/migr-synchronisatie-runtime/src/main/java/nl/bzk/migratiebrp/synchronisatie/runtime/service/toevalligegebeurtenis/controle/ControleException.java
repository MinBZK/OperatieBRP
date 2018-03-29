/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle;

import nl.bzk.migratiebrp.bericht.model.sync.generated.FoutredenType;

/**
 * Controle exception indien een controle faalt.
 */
public class ControleException extends Exception {

    private static final long serialVersionUID = 1L;
    private final FoutredenType foutreden;
    private final String bijhoudingsgemeente;

    /**
     * Maak nieuwe controle exception zonder bijhoudingsgemeente.
     * @param foutreden de foutreden
     */
    public ControleException(final FoutredenType foutreden) {
        this.foutreden = foutreden;
        bijhoudingsgemeente = null;
    }

    /**
     * Maak nieuwe controle exception.
     * @param foutreden de foutreden
     * @param bijhoudingsgemeente de bijhoudingsgemeente bij een verhuizing/blokkering.
     */
    public ControleException(final FoutredenType foutreden, final String bijhoudingsgemeente) {
        this.foutreden = foutreden;
        this.bijhoudingsgemeente = bijhoudingsgemeente;
    }

    /**
     * Geef de controle waardoor de fout is onstaan.
     * @return controle
     */
    public final FoutredenType getFoutreden() {
        return foutreden;
    }

    /**
     * Geef de bijhoudingsgemeente voor verhuizing/blokkering.
     * @return bijhoudingsgemeente
     */
    public final String getBijhoudingsgemeente() {
        return bijhoudingsgemeente;
    }
}
