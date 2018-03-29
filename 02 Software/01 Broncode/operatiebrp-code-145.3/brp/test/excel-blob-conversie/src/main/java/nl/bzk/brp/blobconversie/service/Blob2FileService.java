/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobconversie.service;

import java.io.File;
import java.io.IOException;
import nl.bzk.algemeenbrp.services.blobber.BlobException;

/**
 * Service voor het creeren van blobs en het wegschrijven van blob-bestanden.
 */
public interface Blob2FileService {

    void maakFileBlobs(File resource, final boolean isGbaInitieleVulling) throws IOException, BlobException;

    void leegDb();
}