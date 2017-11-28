/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker.persoonsbeelden;

import java.util.List;
import nl.bzk.brp.domain.internbericht.selectie.SelectiePersoonBericht;

/**
 * MaakPersoonslijstBatchTaak.
 */
final class MaakPersoonslijstBatchOpdracht {

    private List<SelectiePersoonBericht> caches;
    private boolean stop;

    /**
     * @return caches
     */
    List<SelectiePersoonBericht> getCaches() {
        return caches;
    }

    /**
     * @param caches caches
     */
    public void setCaches(List<SelectiePersoonBericht> caches) {
        this.caches = caches;
    }

    /**
     * @return stop
     */
    public boolean isStop() {
        return stop;
    }

    /**
     * @param stop stop
     */
    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
