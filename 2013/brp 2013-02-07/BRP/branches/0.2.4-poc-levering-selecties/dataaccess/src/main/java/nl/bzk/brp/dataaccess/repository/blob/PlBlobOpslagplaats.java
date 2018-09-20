/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.blob;

import java.io.IOException;

import nl.bzk.brp.dataaccess.repository.jpa.serialization.BlobSerializer;
import nl.bzk.brp.model.objecttype.pojo.PersoonHisModel;
import org.springframework.stereotype.Repository;

@Repository
public interface PlBlobOpslagplaats {

    /**
     * Haal een persoonHisModel op. Als er nog geen blob in de DB is, wordt deze gemaakt en weggeschreven.
     *
     * @param id Identifier van de blob
     * @return hisModel van een persoon
     * @throws IOException
     * @throws ClassNotFoundException
     */
    PersoonHisModel leesPlBlob(final int id) throws IOException, ClassNotFoundException;

    /**
     * Schrijf een hisModel (als Blob) weg.
     *
     * @param persoonHisModel
     * @throws IOException
     */
    void schrijfPlBlob(final PersoonHisModel persoonHisModel) throws IOException;

    /**
     * Override de serializer om te gebruiken.
     *
     * @param serializer
     */
    void setBlobSerializer(BlobSerializer serializer);
}
