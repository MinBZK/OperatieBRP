/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.blobber;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaam;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;


/**
 * Map Voornaam entiteit op Blob-records.
 */
final class PersoonVoornaamBlobMapper {

    private final PersoonBlobber blobber;

    /**
     * Constructor.
     *
     * @param blobber persoon blob
     */
    PersoonVoornaamBlobMapper(final PersoonBlobber blobber) {
        this.blobber = blobber;
    }

    /**
     * Map voornamen.
     */
    void map() {
        for (final PersoonVoornaam persoonVoornaam : blobber.getPersoon().getPersoonVoornaamSet()) {
            mapIdentiteitGroep(persoonVoornaam);
            mapStandaardGroep(persoonVoornaam);
        }
    }

    private void mapIdentiteitGroep(final PersoonVoornaam persoonVoornaam) {
        // identiteitsgroep, geen historie
        final BlobRecord record = blobber.maakBlobRecord();
        record.setParentObjectElement(Element.PERSOON);
        record.setParentObjectSleutel(persoonVoornaam.getPersoon().getId());
        record.setObjectSleutel(persoonVoornaam.getId());
        record.setObjectElement(Element.PERSOON_VOORNAAM);
        record.setVoorkomenSleutel(persoonVoornaam.getId() /* dummy */);
        record.setGroepElement(Element.PERSOON_VOORNAAM_IDENTITEIT);
        record.addAttribuut(Element.PERSOON_VOORNAAM_VOLGNUMMER, persoonVoornaam.getVolgnummer());
    }

    private void mapStandaardGroep(final PersoonVoornaam persoonVoornaam) {
        for (final PersoonVoornaamHistorie his : persoonVoornaam.getPersoonVoornaamHistorieSet()) {
            // standaardgroep, F+M
            final BlobRecord record = blobber.maakBlobRecord(his);
            record.setParentObjectElement(Element.PERSOON);
            record.setParentObjectSleutel(persoonVoornaam.getPersoon().getId());
            record.setObjectSleutel(persoonVoornaam.getId());
            record.setObjectElement(Element.PERSOON_VOORNAAM);
            record.setGroepElement(Element.PERSOON_VOORNAAM_STANDAARD);
            record.setVoorkomenSleutel(his.getId());
            record.addAttribuut(Element.PERSOON_VOORNAAM_NAAM, his.getNaam());
        }
    }
}
