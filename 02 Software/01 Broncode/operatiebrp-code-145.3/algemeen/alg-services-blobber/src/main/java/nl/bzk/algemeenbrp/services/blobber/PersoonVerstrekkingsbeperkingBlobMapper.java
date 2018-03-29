/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.blobber;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerstrekkingsbeperking;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerstrekkingsbeperkingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;


/**
 * Map Verstrekkingsbeperking entiteit op Blob-records.
 */
final class PersoonVerstrekkingsbeperkingBlobMapper {

    private final PersoonBlobber blobber;

    /**
     * Constructor.
     *
     * @param blobber persoon blob
     */
    PersoonVerstrekkingsbeperkingBlobMapper(final PersoonBlobber blobber) {
        this.blobber = blobber;
    }

    /**
     * Map verstrekkingsbeperkingen.
     */
    void map() {
        for (final PersoonVerstrekkingsbeperking persoonVerstrekkingsbeperking : blobber.getPersoon().getPersoonVerstrekkingsbeperkingSet()) {
            mapVerstrekkingsbeperking(persoonVerstrekkingsbeperking);
        }
    }

    private void mapVerstrekkingsbeperking(final PersoonVerstrekkingsbeperking persoonVerstrekkingsbeperking) {
        for (final PersoonVerstrekkingsbeperkingHistorie his : persoonVerstrekkingsbeperking.getPersoonVerstrekkingsbeperkingHistorieSet()) {
            final BlobRecord record = blobber.maakBlobRecord(his);
            record.setParentObjectElement(Element.PERSOON);
            record.setParentObjectSleutel(persoonVerstrekkingsbeperking.getPersoon().getId());
            record.setObjectSleutel(persoonVerstrekkingsbeperking.getId());
            record.setObjectElement(Element.PERSOON_VERSTREKKINGSBEPERKING);
            record.setGroepElement(Element.PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT);
            record.setVoorkomenSleutel(his.getId()/* dummy */);
            final PersoonVerstrekkingsbeperking verstrekkingsbeperking = his.getPersoonVerstrekkingsbeperking();
            record.addAttribuut(Element.PERSOON_VERSTREKKINGSBEPERKING_OMSCHRIJVINGDERDE, verstrekkingsbeperking.getOmschrijvingDerde());
            if (verstrekkingsbeperking.getPartij() != null) {
                record.addAttribuut(Element.PERSOON_VERSTREKKINGSBEPERKING_PARTIJCODE, verstrekkingsbeperking.getPartij().getCode());
            }
            if (verstrekkingsbeperking.getGemeenteVerordening() != null) {
                record.addAttribuut(Element.PERSOON_VERSTREKKINGSBEPERKING_GEMEENTEVERORDENINGPARTIJCODE,
                        verstrekkingsbeperking.getGemeenteVerordening().getCode());
            }
        }
    }
}
