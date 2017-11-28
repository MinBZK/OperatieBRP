/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.blobber;

import com.google.common.collect.Lists;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRoot;

/**
 * Map PersoonAfnemerindicatie entiteit op Blob-records.
 */
final class PersoonAfnemerindicatieBlobMapper {
    private static final int DEFAULT_RECORD_LIST_CAPACITY = 2;

    private PersoonAfnemerindicatieBlobMapper() {}

    /**
     * Map PersoonAfnemerindicatie entiteit op Blob-records.
     *
     * @param afnemerindicatie de entiteit
     * @return een BlobRoot object voor de afnemerindicaties, of null indien de afnemerindicatie
     *         geen actueel historie record bevat
     */
    public static BlobRoot map(final PersoonAfnemerindicatie afnemerindicatie) {

        final BlobRoot root = new BlobRoot();
        root.setRecordList(Lists.newArrayListWithCapacity(DEFAULT_RECORD_LIST_CAPACITY));

        final BlobRecord idRecord = new BlobRecord();
        idRecord.setObjectElementId(Element.PERSOON_AFNEMERINDICATIE.getId());
        idRecord.setObjectSleutel(afnemerindicatie.getId());
        idRecord.setVoorkomenSleutel(afnemerindicatie.getId()/* dummy */);
        idRecord.setGroepElement(Element.PERSOON_AFNEMERINDICATIE_IDENTITEIT);
        idRecord.addAttribuut(Element.PERSOON_AFNEMERINDICATIE_PARTIJCODE, afnemerindicatie.getPartij().getCode());
        idRecord.addAttribuut(Element.PERSOON_AFNEMERINDICATIE_LEVERINGSAUTORISATIEIDENTIFICATIE, afnemerindicatie.getLeveringsautorisatie().getId());
        idRecord.addAttribuut(Element.PERSOON_AFNEMERINDICATIE_PERSOON, afnemerindicatie.getPersoon().getId());

        root.getRecordList().add(idRecord);

        for (final PersoonAfnemerindicatieHistorie his : afnemerindicatie.getPersoonAfnemerindicatieHistorieSet()) {
            final BlobRecord record = new BlobRecord();
            vulRecord(afnemerindicatie, his, record);
            root.getRecordList().add(record);
        }
        return root;
    }

    private static void vulRecord(final PersoonAfnemerindicatie afnemerindicatie, final PersoonAfnemerindicatieHistorie his, final BlobRecord record) {
        record.setObjectElementId(Element.PERSOON_AFNEMERINDICATIE.getId());
        record.setObjectSleutel(afnemerindicatie.getId());
        record.setVoorkomenSleutel(his.getId());
        record.setGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD);
        record.addAttribuut(Element.PERSOON_AFNEMERINDICATIE_DATUMAANVANGMATERIELEPERIODE, his.getDatumAanvangMaterielePeriode());
        record.addAttribuut(Element.PERSOON_AFNEMERINDICATIE_DATUMEINDEVOLGEN, his.getDatumEindeVolgen());
        record.addAttribuut(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE, his.getDatumTijdRegistratie());
        record.addAttribuut(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPVERVAL, his.getDatumTijdVerval());

        if (his.getDienstInhoud() != null) {
            record.addAttribuut(Element.PERSOON_AFNEMERINDICATIE_DIENSTINHOUD, his.getDienstInhoud().getId());
        }
        if (his.getDienstVerval() != null) {
            record.addAttribuut(Element.PERSOON_AFNEMERINDICATIE_DIENSTVERVAL, his.getDienstVerval().getId());
        }
    }
}
