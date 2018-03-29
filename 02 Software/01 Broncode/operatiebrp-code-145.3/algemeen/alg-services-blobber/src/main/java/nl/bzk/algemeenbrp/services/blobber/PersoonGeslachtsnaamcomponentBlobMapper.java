/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.blobber;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;


/**
 * Map geslachtsnaam entiteit op Blob-records.
 */
final class PersoonGeslachtsnaamcomponentBlobMapper {

    private final PersoonBlobber blobber;

    /**
     * Constructor.
     *
     * @param blobber persoon blob
     */
    PersoonGeslachtsnaamcomponentBlobMapper(final PersoonBlobber blobber) {
        this.blobber = blobber;
    }

    /**
     * Map geslachtsnaamcomponenten.
     */
    void map() {
        for (final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent : blobber.getPersoon().getPersoonGeslachtsnaamcomponentSet()) {
            mapIdentiteitGroep(persoonGeslachtsnaamcomponent);
            mapStandaardGroep(persoonGeslachtsnaamcomponent);
        }
    }

    private void mapIdentiteitGroep(final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent) {
        // identiteitsgroep, geen historie
        final BlobRecord record = blobber.maakBlobRecord();
        record.setParentObjectElement(Element.PERSOON);
        record.setParentObjectSleutel(persoonGeslachtsnaamcomponent.getPersoon().getId());
        record.setObjectSleutel(persoonGeslachtsnaamcomponent.getId());
        record.setVoorkomenSleutel(persoonGeslachtsnaamcomponent.getId()/* dummy */);
        record.setObjectElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT);
        record.setGroepElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_IDENTITEIT);
        record.addAttribuut(Element.PERSOON_GESLACHTSNAAMCOMPONENT_VOLGNUMMER, persoonGeslachtsnaamcomponent.getVolgnummer());
    }

    private void mapStandaardGroep(final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent) {
        for (final PersoonGeslachtsnaamcomponentHistorie his : persoonGeslachtsnaamcomponent.getPersoonGeslachtsnaamcomponentHistorieSet()) {
            // standaardgroep, F+M
            final BlobRecord record = blobber.maakBlobRecord(his);
            record.setParentObjectElement(Element.PERSOON);
            record.setParentObjectSleutel(persoonGeslachtsnaamcomponent.getPersoon().getId());
            record.setObjectSleutel(persoonGeslachtsnaamcomponent.getId());
            record.setObjectElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT);
            record.setGroepElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD);
            record.setVoorkomenSleutel(his.getId());
            record.addAttribuut(Element.PERSOON_GESLACHTSNAAMCOMPONENT_PREDICAATCODE, his.getPredicaat() == null ? null : his.getPredicaat().getCode());
            record.addAttribuut(Element.PERSOON_GESLACHTSNAAMCOMPONENT_ADELLIJKETITELCODE,
                    his.getAdellijkeTitel() == null ? null : his.getAdellijkeTitel().getCode());
            record.addAttribuut(Element.PERSOON_GESLACHTSNAAMCOMPONENT_VOORVOEGSEL, his.getVoorvoegsel());

            if (his.getScheidingsteken() != null) {
                record.addAttribuut(Element.PERSOON_GESLACHTSNAAMCOMPONENT_SCHEIDINGSTEKEN, his.getScheidingsteken().toString());
            }
            record.addAttribuut(Element.PERSOON_GESLACHTSNAAMCOMPONENT_STAM, his.getStam());
        }
    }
}
