/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.dataaccess;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.services.blobber.Blobber;
import nl.bzk.algemeenbrp.services.blobber.json.AfnemerindicatiesBlob;
import nl.bzk.algemeenbrp.services.blobber.json.PersoonBlob;
import nl.bzk.brp.test.common.TestclientExceptie;

/**
 * Representeert een blob-file.
 */
final class Blobfile {

    private static final String DASH = "-";
    private String filename;
    private byte[] data;

    private PersoonBlob persoonBlob;
    private AfnemerindicatiesBlob afnemerindicatiesBlobData;
    private long persId;

    Blobfile(final Path path1) throws IOException {
        this.filename = path1.getFileName().toString();
        this.data = Files.readAllBytes(path1);

        if (this.filename.endsWith("-persoon.blob.json")) {
            try {
                persoonBlob = Blobber.deserializeNaarPersoonBlob(data);
            } catch (BlobException e) {
                throw new TestclientExceptie(e);
            }
        } else if (this.filename.endsWith("-afnemerindicatie.blob.json")) {
            try {
                if (data != null && data.length > 0) {
                    this.afnemerindicatiesBlobData = Blobber.deserializeNaarAfnemerindicatiesBlob(data);
                }
            } catch (BlobException e) {
                throw new TestclientExceptie(e);
            }
        } else {
            throw new IllegalArgumentException(path1.toString());
        }
        this.persId = Long.parseLong(filename.substring(0, filename.indexOf(DASH)));
    }

    String getFilename() {
        return filename;
    }

    byte[] getData() {
        return data;
    }

    PersoonBlob getPersoonBlob() {
        return persoonBlob;
    }

    AfnemerindicatiesBlob getAfnemerindicatiesBlobData() {
        return afnemerindicatiesBlobData;
    }

    long getPersId() {
        return persId;
    }
}
