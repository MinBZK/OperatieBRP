/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.exception;

import java.util.Arrays;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;

/**
 * Exception om aan te geven dat de verwerking van een synchronisatie niet correct kan worden afgerond.
 */
public final class SynchronisatieVerwerkerException extends Exception {
    private static final long serialVersionUID = 1L;

    private final StatusType status;
    private final transient List<BrpPersoonslijst> kandidaten;

    /**
     * Constructor.
     * @param status status
     */
    public SynchronisatieVerwerkerException(final StatusType status) {
        this.status = status;
        kandidaten = null;
    }

    /**
     * Constructor.
     * @param status status
     * @param cause oorzaak
     */
    public SynchronisatieVerwerkerException(final StatusType status, final Throwable cause) {
        super(cause);
        this.status = status;
        kandidaten = null;
    }

    /**
     * Constructor.
     * @param status status
     * @param kandidaten kandidaten
     */
    public SynchronisatieVerwerkerException(final StatusType status, final List<BrpPersoonslijst> kandidaten) {
        this.status = status;
        this.kandidaten = kandidaten;
    }

    /**
     * Constructor.
     * @param status status
     * @param kandidaat kandidaat
     */
    public SynchronisatieVerwerkerException(final StatusType status, final BrpPersoonslijst kandidaat) {
        this.status = status;
        kandidaten = Arrays.asList(kandidaat);
    }

    /**
     * Geef de waarde van status.
     * @return status
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Geef de waarde van kandidaten.
     * @return kandidaten
     */
    public List<BrpPersoonslijst> getKandidaten() {
        return kandidaten;
    }

}
