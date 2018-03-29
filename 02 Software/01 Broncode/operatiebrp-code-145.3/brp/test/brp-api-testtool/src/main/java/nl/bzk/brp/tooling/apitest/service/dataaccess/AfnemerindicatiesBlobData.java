/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.dataaccess;

import java.util.Arrays;
import nl.bzk.algemeenbrp.services.blobber.json.AfnemerindicatiesBlob;

/**
 * Hulpklasse
 */
class AfnemerindicatiesBlobData {

    private final AfnemerindicatiesBlob afnemerindicatiesBlob;
    private final byte[] data;

    AfnemerindicatiesBlobData(final AfnemerindicatiesBlob afnemerindicatiesBlob, final byte[] data) {
        this.afnemerindicatiesBlob = afnemerindicatiesBlob;
        this.data = Arrays.copyOf(data, data.length);
    }

    AfnemerindicatiesBlob getAfnemerindicatiesBlob() {
        return afnemerindicatiesBlob;
    }

    byte[] getData() {
        return data;
    }
}
