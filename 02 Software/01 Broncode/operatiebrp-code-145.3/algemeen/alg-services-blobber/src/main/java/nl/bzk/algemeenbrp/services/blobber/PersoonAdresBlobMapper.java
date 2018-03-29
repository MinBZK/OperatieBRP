/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.blobber;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;


/**
 * Map Adres entiteit op Blob-records.
 */
final class PersoonAdresBlobMapper {

    private final PersoonBlobber blobber;

    /**
     * Constructor.
     *
     * @param blobber persoon blob
     */
    PersoonAdresBlobMapper(final PersoonBlobber blobber) {
        this.blobber = blobber;
    }

    /**
     * Map adressen.
     */
    void map() {
        for (final PersoonAdres persoonAdres : blobber.getPersoon().getPersoonAdresSet()) {
            mapIdentiteitGroep(persoonAdres);
            mapStandaardGroep(persoonAdres);
        }

        blobber.getPersoon().getPersoonAdresSet().forEach(this::mapStandaardGroep);
    }

    /**
     * identiteitsgroep, geen historie.
     */
    private void mapIdentiteitGroep(final PersoonAdres persoonAdres) {
        final BlobRecord record = blobber.maakBlobRecord();
        record.setParentObjectElement(Element.PERSOON);
        record.setParentObjectSleutel(persoonAdres.getPersoon().getId());
        record.setObjectSleutel(persoonAdres.getId());
        record.setObjectElement(Element.PERSOON_ADRES);
        record.setVoorkomenSleutel(persoonAdres.getId()/* dummy */);
        record.setGroepElement(Element.PERSOON_ADRES_IDENTITEIT);
    }

    private void mapStandaardGroep(final PersoonAdres persoonAdres) {
        for (final PersoonAdresHistorie his : persoonAdres.getPersoonAdresHistorieSet()) {
            final BlobRecord record = blobber.maakBlobRecord(his);
            record.setParentObjectElement(Element.PERSOON);
            record.setParentObjectSleutel(persoonAdres.getPersoon().getId());
            record.setObjectSleutel(persoonAdres.getId());
            record.setObjectElement(Element.PERSOON_ADRES);
            record.setGroepElement(Element.PERSOON_ADRES_STANDAARD);
            record.setVoorkomenSleutel(his.getId());
            if (his.getAangeverAdreshouding() != null) {
                record.addAttribuut(Element.PERSOON_ADRES_AANGEVERADRESHOUDINGCODE, String.valueOf(his.getAangeverAdreshouding().getCode()));
            }
            record.addAttribuut(Element.PERSOON_ADRES_AFGEKORTENAAMOPENBARERUIMTE, his.getAfgekorteNaamOpenbareRuimte());
            record.addAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1, his.getBuitenlandsAdresRegel1());
            record.addAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2, his.getBuitenlandsAdresRegel2());
            record.addAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3, his.getBuitenlandsAdresRegel3());
            record.addAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL4, his.getBuitenlandsAdresRegel4());
            record.addAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL5, his.getBuitenlandsAdresRegel5());
            record.addAttribuut(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL6, his.getBuitenlandsAdresRegel6());
            record.addAttribuut(Element.PERSOON_ADRES_DATUMAANVANGADRESHOUDING, his.getDatumAanvangAdreshouding());
            if (his.getGemeente() != null) {
                record.addAttribuut(Element.PERSOON_ADRES_GEMEENTECODE, his.getGemeente().getCode());
            }
            record.addAttribuut(Element.PERSOON_ADRES_GEMEENTEDEEL, his.getGemeentedeel());
            if (his.getHuisletter() != null) {
                record.addAttribuut(Element.PERSOON_ADRES_HUISLETTER, his.getHuisletter().toString());
            }
            record.addAttribuut(Element.PERSOON_ADRES_HUISNUMMER, his.getHuisnummer());
            record.addAttribuut(Element.PERSOON_ADRES_HUISNUMMERTOEVOEGING, his.getHuisnummertoevoeging());
            record.addAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODEADRESSEERBAAROBJECT, his.getIdentificatiecodeAdresseerbaarObject());
            record.addAttribuut(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING, his.getIdentificatiecodeNummeraanduiding());
            record.addAttribuut(Element.PERSOON_ADRES_INDICATIEPERSOONAANGETROFFENOPADRES, his.getIndicatiePersoonAangetroffenOpAdres());
            if (his.getLandOfGebied() != null) {
                record.addAttribuut(Element.PERSOON_ADRES_LANDGEBIEDCODE, his.getLandOfGebied().getCode());
            }
            record.addAttribuut(Element.PERSOON_ADRES_LOCATIEOMSCHRIJVING, his.getLocatieOmschrijving());
            record.addAttribuut(Element.PERSOON_ADRES_LOCATIETENOPZICHTEVANADRES, his.getLocatietovAdres());
            record.addAttribuut(Element.PERSOON_ADRES_NAAMOPENBARERUIMTE, his.getNaamOpenbareRuimte());
            record.addAttribuut(Element.PERSOON_ADRES_POSTCODE, his.getPostcode());
            record.addAttribuut(Element.PERSOON_ADRES_WOONPLAATSNAAM, his.getWoonplaatsnaam());
            if (his.getRedenWijziging() != null) {
                record.addAttribuut(Element.PERSOON_ADRES_REDENWIJZIGINGCODE, String.valueOf(his.getRedenWijziging().getCode()));
            }
            if (his.getSoortAdres() != null) {
                record.addAttribuut(Element.PERSOON_ADRES_SOORTCODE, his.getSoortAdres().getCode());
            }
        }
    }
}
