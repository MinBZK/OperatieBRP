/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.blobber;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;

/**
 * Map Nationaliteit entiteit op Blob-records.
 */
final class PersoonNationaliteitBlobMapper {

    private final PersoonBlobber blobber;

    /**
     * Constructor.
     *
     * @param blobber persoon blob
     */
    PersoonNationaliteitBlobMapper(final PersoonBlobber blobber) {
        this.blobber = blobber;
    }

    /**
     * Map nationaliteiten.
     */
    void map() {
        for (final PersoonNationaliteit persoonNationaliteit : blobber.getPersoon().getPersoonNationaliteitSet()) {
            mapIdentiteitGroep(persoonNationaliteit);
            mapStandaardGroep(persoonNationaliteit);
        }
    }

    /**
     * identiteitsgroep, geen historie.
     */
    private void mapIdentiteitGroep(final PersoonNationaliteit persoonNationaliteit) {
        final BlobRecord record = blobber.maakBlobRecord();
        record.setParentObjectElement(Element.PERSOON);
        record.setParentObjectSleutel(persoonNationaliteit.getPersoon().getId());
        record.setObjectSleutel(persoonNationaliteit.getId());
        record.setObjectElement(Element.PERSOON_NATIONALITEIT);
        record.setVoorkomenSleutel(persoonNationaliteit.getId()/* dummy */);
        record.setGroepElement(Element.PERSOON_NATIONALITEIT_IDENTITEIT);
        record.addAttribuut(Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE, persoonNationaliteit.getNationaliteit().getCode());

    }

    /**
     * standaardgroep, F+M
     */
    private void mapStandaardGroep(final PersoonNationaliteit persoonNationaliteit) {
        for (final PersoonNationaliteitHistorie his : persoonNationaliteit.getPersoonNationaliteitHistorieSet()) {
            final BlobRecord record = blobber.maakBlobRecord(his);
            record.setParentObjectElement(Element.PERSOON);
            record.setParentObjectSleutel(persoonNationaliteit.getPersoon().getId());
            record.setObjectSleutel(persoonNationaliteit.getId());
            record.setObjectElement(Element.PERSOON_NATIONALITEIT);
            record.setGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD);
            record.setVoorkomenSleutel(his.getId());
            if (his.getRedenVerkrijgingNLNationaliteit() != null) {
                record.addAttribuut(Element.PERSOON_NATIONALITEIT_REDENVERKRIJGINGCODE, his.getRedenVerkrijgingNLNationaliteit().getCode());
            }
            if (his.getRedenVerliesNLNationaliteit() != null) {
                record.addAttribuut(Element.PERSOON_NATIONALITEIT_REDENVERLIESCODE, his.getRedenVerliesNLNationaliteit().getCode());
            }
            record.addAttribuut(Element.PERSOON_NATIONALITEIT_INDICATIEBIJHOUDINGBEEINDIGD, his.getIndicatieBijhoudingBeeindigd());
            record.addAttribuut(Element.PERSOON_NATIONALITEIT_MIGRATIEREDENOPNAMENATIONALITEIT, his.getMigratieRedenOpnameNationaliteit());
            record.addAttribuut(Element.PERSOON_NATIONALITEIT_MIGRATIEREDENBEEINDIGENNATIONALITEIT, his.getMigratieRedenBeeindigenNationaliteit());
            record.addAttribuut(Element.PERSOON_NATIONALITEIT_MIGRATIEDATUMEINDEBIJHOUDING, his.getMigratieDatumEindeBijhouding());
        }
    }
}
