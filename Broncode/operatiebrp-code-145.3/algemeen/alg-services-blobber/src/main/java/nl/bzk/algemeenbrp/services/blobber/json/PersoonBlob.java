/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.blobber.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * Representatie van de JSON BLOB.
 */
@JsonAutoDetect
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class PersoonBlob {

    /**
     * Lijst met BlobRecords. Dit is een platgeslagen representatie van de persoonsgegevens.
     */
    private BlobRoot persoonsgegevens;

    /**
     * Lijst met BlobRecords. Dit is een platgeslagen representatie van de verantwoording.
     * (AdministratieveHandeling, Actie, Actiebron, Document)
     */
    private List<BlobRoot> verantwoording;

    /**
     * @return de persoonsgegevens.
     */
    public BlobRoot getPersoonsgegevens() {
        return persoonsgegevens;
    }

    /**
     * @param persoonsgegevens de persoonsgegevens
     */
    public void setPersoonsgegevens(final BlobRoot persoonsgegevens) {
        this.persoonsgegevens = persoonsgegevens;
    }

    /**
     * @return de verantwoording
     */
    public List<BlobRoot> getVerantwoording() {
        return verantwoording;
    }

    /**
     * @param verantwoording de verantwoording
     */
    public void setVerantwoording(final List<BlobRoot> verantwoording) {
        this.verantwoording = verantwoording;
    }

}
