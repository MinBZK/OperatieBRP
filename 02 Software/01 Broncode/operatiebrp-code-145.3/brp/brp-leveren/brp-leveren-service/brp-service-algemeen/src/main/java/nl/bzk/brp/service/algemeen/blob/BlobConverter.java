/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.blob;

import com.google.common.collect.Lists;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRoot;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;

/**
 * Object om Meta-objecten te vertalen naar BLOB-objecten.
 */
final class BlobConverter {

    private BlobConverter() {
    }

    /**
     * Converteer naar json blob formaat.
     * @param metaObject metaObject
     * @return het Blob object
     */
    public static BlobRoot converteer(final MetaObject metaObject) {
        final SlaPlatVisitor slaPlatVisitor = new SlaPlatVisitor();
        slaPlatVisitor.visit(metaObject);

        final BlobRoot root = new BlobRoot();
        root.setRecordList(slaPlatVisitor.recordsJson);
        return root;
    }

    /**
     * Visitor om BlobRecords te maken.
     */
    private static class SlaPlatVisitor extends ParentFirstModelVisitor {
        private final List<BlobRecord> recordsJson = Lists.newLinkedList();

        @Override
        public void doVisit(final MetaRecord record) {
            final MetaObject object = record.getParentGroep().getParentObject();
            final BlobRecord blobRecord = new BlobRecord();
            blobRecord.setGroepElementId(record.getParentGroep().getGroepElement().getId());
            blobRecord.setObjectElementId(object.getObjectElement().getId());
            blobRecord.setVoorkomenSleutel(record.getVoorkomensleutel());
            blobRecord.setObjectSleutel(object.getObjectsleutel());

            if (object.getParentObject() != null) {
                blobRecord.setParentObjectElementId(object.getParentObject().getObjectElement().getId());
                blobRecord.setParentObjectSleutel(object.getParentObject().getObjectsleutel());
            }
            if (record.getNadereAanduidingVerval() != null) {
                blobRecord.setNadereAanduidingVerval(record.getNadereAanduidingVerval());
            }
            blobRecord.setIndicatieTbvLeveringMutaties(record.isIndicatieTbvLeveringMutaties());
            converteerActie(record, blobRecord);
            converteerDatum(record, blobRecord);
            converteerAttributen(record, blobRecord);
            recordsJson.add(blobRecord);
        }

        private void converteerDatum(final MetaRecord record, final BlobRecord blobRecord) {
            if (record.getDatumAanvangGeldigheid() != null) {
                blobRecord.setDatumAanvangGeldigheid(record.getDatumAanvangGeldigheid());
            }
            if (record.getDatumEindeGeldigheid() != null) {
                blobRecord.setDatumEindeGeldigheid(record.getDatumEindeGeldigheid());
            }
        }

        private void converteerActie(final MetaRecord record, final BlobRecord blobRecord) {
            if (record.getActieInhoud() != null) {
                blobRecord.setActieInhoud(record.getActieInhoud().getId());
            }
            if (record.getActieVerval() != null) {
                blobRecord.setActieVerval(record.getActieVerval().getId());
            }
            if (record.getActieAanpassingGeldigheid() != null) {
                blobRecord.setActieAanpassingGeldigheid(record.getActieAanpassingGeldigheid().getId());
            }
            if (record.getActieVervalTbvLeveringMutaties() != null) {
                blobRecord.setActieVervalTbvLeveringMutaties(record.getActieVervalTbvLeveringMutaties().getId());
            }
        }

        private void converteerAttributen(final MetaRecord record, final BlobRecord blobRecord) {
            for (final Map.Entry<AttribuutElement, MetaAttribuut> entry : record.getAttributen().entrySet()) {
                final AttribuutElement element = entry.getKey();
                final Object huidigeWaarde = entry.getValue().getWaarde();
                final Object value;

                if (huidigeWaarde instanceof Date) {
                    value = ((Date) huidigeWaarde).getTime();
                } else if (huidigeWaarde instanceof ZonedDateTime) {
                    value = ((ZonedDateTime) huidigeWaarde).toInstant().toEpochMilli();
                } else {
                    value = huidigeWaarde;
                }

                blobRecord.addAttribuut(element.getId(), value);
            }
        }
    }
}
