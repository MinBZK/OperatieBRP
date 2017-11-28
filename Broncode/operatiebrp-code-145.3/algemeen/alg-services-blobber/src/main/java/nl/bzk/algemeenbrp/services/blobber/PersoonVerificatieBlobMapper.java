/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.blobber;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerificatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerificatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;

/**
 * Map Verificatie op Blob-records.
 */
final class PersoonVerificatieBlobMapper {
    private final PersoonBlobber blobber;

    /**
     * Constructor.
     *
     * @param blobber de blob
     */
    PersoonVerificatieBlobMapper(final PersoonBlobber blobber) {
        this.blobber = blobber;
    }

    /**
     * Map verificaties.
     */
    void map() {
        blobber.getPersoon().getPersoonVerificatieSet().forEach(this::map);
    }

    private void map(final PersoonVerificatie verificatie) {
        mapIdentiteitGroep(verificatie);
        mapStandaardGroep(verificatie);
    }

    private void mapIdentiteitGroep(final PersoonVerificatie verificatie) {
        // identiteitsgroep, geen historie
        final BlobRecord record = blobber.maakBlobRecord();
        record.setParentObjectElement(Element.PERSOON);
        record.setParentObjectSleutel(verificatie.getPersoon().getId());
        record.setObjectSleutel(verificatie.getId());
        record.setObjectElement(Element.PERSOON_VERIFICATIE);
        record.setVoorkomenSleutel(verificatie.getId() /* dummy */);
        record.setGroepElement(Element.PERSOON_VERIFICATIE_IDENTITEIT);
        if (verificatie.getPartij() != null) {
            record.addAttribuut(Element.PERSOON_VERIFICATIE_PARTIJCODE, verificatie.getPartij().getCode());
        }
        record.addAttribuut(Element.PERSOON_VERIFICATIE_SOORT, verificatie.getSoortVerificatie());
        record.addAttribuut(Element.PERSOON_VERIFICATIE_PERSOON, verificatie.getPersoon().getId());
    }

    private void mapStandaardGroep(final PersoonVerificatie verificatie) {
        for (final PersoonVerificatieHistorie his : verificatie.getPersoonVerificatieHistorieSet()) {
            // standaardgroep, F+M
            final BlobRecord record = blobber.maakBlobRecord(his);
            record.setParentObjectElement(Element.PERSOON);
            record.setParentObjectSleutel(verificatie.getPersoon().getId());
            record.setObjectSleutel(verificatie.getId());
            record.setObjectElement(Element.PERSOON_VERIFICATIE);
            record.setGroepElement(Element.PERSOON_VERIFICATIE_STANDAARD);
            record.setVoorkomenSleutel(his.getId());
            record.addAttribuut(Element.PERSOON_VERIFICATIE_DATUM, his.getDatum());
        }
    }
}
