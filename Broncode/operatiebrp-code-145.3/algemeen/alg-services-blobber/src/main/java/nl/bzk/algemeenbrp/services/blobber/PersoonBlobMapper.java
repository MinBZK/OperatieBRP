/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.blobber;

/**
 * Map entiteiten corresponderend met Persoon-groepen en gerelateerde objecten op Blob-records.
 */
final class PersoonBlobMapper {

    private final PersoonBlobber blobber;

    /**
     * Constructor.
     *
     * @param blobber persoon blob
     */
    PersoonBlobMapper(final PersoonBlobber blobber) {
        this.blobber = blobber;
    }

    /**
     * Map Persoon-groepen.
     */
    void map() {

        // map groepen binnen persoon
        new PersoonGroepenBlobMapper(blobber).map();

        // map objecten
        new PersoonAdresBlobMapper(blobber).map();
        new BetrokkenheidBlobMapper(blobber).map();
        new PersoonBuitenlandspersoonsnummerBlobMapper(blobber).map();
        new PersoonGeslachtsnaamcomponentBlobMapper(blobber).map();
        new PersoonIndicatieBlobMapper(blobber).map();
        new PersoonNationaliteitBlobMapper(blobber).map();
        new PersoonReisdocumentBlobMapper(blobber).map();
        new PersoonVerstrekkingsbeperkingBlobMapper(blobber).map();
        new PersoonVoornaamBlobMapper(blobber).map();
        new PersoonOnderzoekBlobMapper(blobber).map();
        new PersoonVerificatieBlobMapper(blobber).map();
    }
}
