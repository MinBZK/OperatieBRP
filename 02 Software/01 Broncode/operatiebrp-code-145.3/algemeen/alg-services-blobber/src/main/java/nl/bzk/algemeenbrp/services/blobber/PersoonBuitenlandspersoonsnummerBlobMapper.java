/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.blobber;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBuitenlandsPersoonsnummer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBuitenlandsPersoonsnummerHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;

/**
 * Mapt het object Persoon / Buitenlandspersoonsnummer naar de BLOB.
 */
final class PersoonBuitenlandspersoonsnummerBlobMapper {

    private final PersoonBlobber blobber;

    /**
     * Constructor.
     *
     * @param blobber persoon blob
     */
    PersoonBuitenlandspersoonsnummerBlobMapper(final PersoonBlobber blobber) {
        this.blobber = blobber;
    }

    /**
     * Map buitenlands persoonsnummer.
     */
    void map() {
        for (final PersoonBuitenlandsPersoonsnummer persoonBuitenlandsPersoonsnummer : blobber.getPersoon().getPersoonBuitenlandsPersoonsnummerSet()) {
            mapIdentiteitGroep(persoonBuitenlandsPersoonsnummer);
            mapStandaardGroep(persoonBuitenlandsPersoonsnummer);
        }
    }

    private void mapIdentiteitGroep(final PersoonBuitenlandsPersoonsnummer persoonBuitenlandsPersoonsnummer) {
        // identiteitsgroep, geen historie
        final BlobRecord record = blobber.maakBlobRecord();
        record.setParentObjectElement(Element.PERSOON);
        record.setParentObjectSleutel(persoonBuitenlandsPersoonsnummer.getPersoon().getId());
        record.setObjectSleutel(persoonBuitenlandsPersoonsnummer.getId());
        record.setObjectElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER);
        record.setVoorkomenSleutel(persoonBuitenlandsPersoonsnummer.getId()/* dummy */);
        record.setGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_IDENTITEIT);
        record.addAttribuut(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_NUMMER, persoonBuitenlandsPersoonsnummer.getNummer());
        record.addAttribuut(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_AUTORITEITVANAFGIFTECODE,
                persoonBuitenlandsPersoonsnummer.getAutoriteitAfgifteBuitenlandsPersoonsnummer().getCode());
    }

    private void mapStandaardGroep(final PersoonBuitenlandsPersoonsnummer persoonBuitenlandsPersoonsnummer) {
        for (final PersoonBuitenlandsPersoonsnummerHistorie his : persoonBuitenlandsPersoonsnummer.getPersoonBuitenlandsPersoonsnummerHistorieSet()) {
            // standaardgroep, F
            final BlobRecord record = blobber.maakBlobRecord(his);
            record.setParentObjectElement(Element.PERSOON);
            record.setParentObjectSleutel(persoonBuitenlandsPersoonsnummer.getPersoon().getId());
            record.setObjectSleutel(persoonBuitenlandsPersoonsnummer.getId());
            record.setObjectElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER);
            record.setGroepElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_STANDAARD);
            record.setVoorkomenSleutel(his.getId());
        }
    }
}
