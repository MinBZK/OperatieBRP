/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.blob;

import nl.bzk.algemeenbrp.services.blobber.json.AfnemerindicatiesBlob;
import nl.bzk.algemeenbrp.services.blobber.json.PersoonBlob;

/**
 * Persoon value holder.
 */
public final class PersoonData {

    private final PersoonBlob persoonBlob;
    private final AfnemerindicatiesBlob afnemerindicatiesBlob;
    private final Long lockversieAfnemerindicatie;

    /**
     * @param persoonBlob persoonBlob
     * @param afnemerindicatiesBlob afnemerindicatiesBlob
     * @param lockversieAfnemerindicatie lockversieAfnemerindicatie
     */
    public PersoonData(final PersoonBlob persoonBlob, final AfnemerindicatiesBlob afnemerindicatiesBlob, final Long lockversieAfnemerindicatie) {
        this.persoonBlob = persoonBlob;
        this.afnemerindicatiesBlob = afnemerindicatiesBlob;
        this.lockversieAfnemerindicatie = lockversieAfnemerindicatie;
    }

    public PersoonBlob getPersoonBlob() {
        return persoonBlob;
    }

    public AfnemerindicatiesBlob getAfnemerindicatiesBlob() {
        return afnemerindicatiesBlob;
    }

    public Long getLockversieAfnemerindicatie() {
        return lockversieAfnemerindicatie;
    }

}
