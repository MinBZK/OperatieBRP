/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.blobber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import nl.bzk.algemeenbrp.services.blobber.json.AfnemerindicatiesBlob;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRoot;
import nl.bzk.algemeenbrp.services.blobber.json.PersoonBlob;

/**
 * Util klasse voor het maken van een Blob.
 */
public final class Blobber {

    /**
     * Threadsafe ObjectMapper.
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private Blobber() {

    }

    /**
     * Maakt een Blob van de Persoon entiteit.
     *
     * @param persoon de persoon die geBLOBt moet worden
     * @return de blob
     */
    public static PersoonBlob maakBlob(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("Persoon kan niet null zijn", persoon);
        final PersoonBlobber persoonBlobber = new PersoonBlobber(persoon);
        final PersoonBlob persoonBlob = new PersoonBlob();
        persoonBlob.setPersoonsgegevens(new BlobRoot());
        persoonBlob.getPersoonsgegevens().setRecordList(Lists.newArrayList(persoonBlobber.getPersRecords()));
        persoonBlob.setVerantwoording(Lists.newLinkedList());
        final VerantwoordingBlobber verantwoordingBlobber = new VerantwoordingBlobber(persoonBlobber.getActieMap().values());
        persoonBlob.setVerantwoording(verantwoordingBlobber.getBlobRootList());
        return persoonBlob;
    }

    /**
     * Maakt een Blob van de PersoonAfnemerindicatie entiteiten.
     *
     * @param afnemerindicaties de persoonafnemerindicaties
     * @return de blob
     */
    public static AfnemerindicatiesBlob maakBlob(final Collection<PersoonAfnemerindicatie> afnemerindicaties) {
        final AfnemerindicatiesBlob afnemerindicatiesBlob = new AfnemerindicatiesBlob();
        afnemerindicatiesBlob.setAfnemerindicaties(Lists.newLinkedList());
        for (final PersoonAfnemerindicatie afnemerindicatie : afnemerindicaties) {
            final BlobRoot root = PersoonAfnemerindicatieBlobMapper.map(afnemerindicatie);
            afnemerindicatiesBlob.getAfnemerindicaties().add(root);
        }
        return afnemerindicatiesBlob;
    }

    /**
     * Generieke methode voor het serialiseren van een object naar Json (bytes).
     *
     * @param blobObject de persoon
     * @return een blob bytes
     * @throws BlobException als het blobben mislukt
     */
    public static byte[] toJsonBytes(final Object blobObject) throws BlobException {
        try {
            return OBJECT_MAPPER.writeValueAsBytes(blobObject);
        } catch (final JsonProcessingException e) {
            throw new BlobException("Fout bij maken Blob", e);
        }
    }

    /**
     * Maakt een Blob object representatie obv de gegeven string.
     *
     * @param blobData blob data
     * @return een PersoonBlob object
     * @throws BlobException als het deserialiseren mislukt
     */
    public static PersoonBlob deserializeNaarPersoonBlob(final byte[] blobData) throws BlobException {
        return deserialiseer(blobData, PersoonBlob.class);
    }

    /**
     * Maakt een Blob object representatie obv de gegeven string.
     *
     * @param blobData blob data
     * @return een PersoonBlob object
     * @throws BlobException als het deserialiseren mislukt
     */
    public static AfnemerindicatiesBlob deserializeNaarAfnemerindicatiesBlob(final byte[] blobData) throws BlobException {
        return deserialiseer(blobData, AfnemerindicatiesBlob.class);
    }

    private static <T> T deserialiseer(final byte[] blobData, final Class<T> clazz) throws BlobException {
        try {
            return OBJECT_MAPPER.readValue(blobData, clazz);
        } catch (final IOException e) {
            throw new BlobException("Fout bij het deserialiseren", e);
        }
    }
}
