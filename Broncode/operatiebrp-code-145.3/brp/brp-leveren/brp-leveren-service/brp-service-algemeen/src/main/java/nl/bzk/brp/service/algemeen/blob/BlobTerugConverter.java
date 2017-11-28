/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.blob;

import com.google.common.base.Predicates;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRoot;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import org.springframework.util.Assert;

/**
 * Object om BLOB-objecten te vertalen naar Meta-objecten.
 */
final class BlobTerugConverter {

    private final Map<BlobRecord, MetaRecord.Builder> recordMap = Maps.newHashMap();
    private final Map<String, MetaGroep.Builder> groepMap = Maps.newHashMap();
    private final Map<String, MetaObject.Builder> objectMap = Maps.newHashMap();
    private final Map<MetaObject.Builder, String> objectParentMap = Maps.newHashMap();

    /**
     * Constructor.
     * @param root de Blob data
     * @param recordMapper een record mapper.
     */
    BlobTerugConverter(final BlobRoot root, final BiConsumer<MetaRecord.Builder, BlobRecord> recordMapper) {
        final List<BlobRecord> recordList = root.getRecordList();
        mapRecords(recordList, recordMapper);
        //maakt de objecten hierarchie.
        for (final Map.Entry<MetaObject.Builder, String> entry : objectParentMap.entrySet()) {
            final MetaObject.Builder parentObject = objectMap.get(entry.getValue());
            Assert.notNull(parentObject, "Parent mapping in Blob incorrect: " + entry.getValue());
            parentObject.metObject(entry.getKey());
        }
    }

    /**
     * Maakt een key waarmee MetaObject.Builder lookups gedaan kunnen worden.
     * @param objectElementId elementId van het object
     * @param objectSleutel objectsleutel van het object
     * @return een String key
     */
    String maakObjectBuilderKey(final Integer objectElementId, final Long objectSleutel) {
        return String.format("object.element.id=%d-object.id=%d", objectElementId, objectSleutel);
    }

    /**
     * Geeft de een MetaObject.Builder object dat hoort bij de gegeven key.
     * @param key de key om de builder mee op te zoeken
     * @return de builder
     */
    MetaObject.Builder getObjectBuilder(final String key) {
        return objectMap.get(key);
    }

    /**
     * @return alle root MetaObject builders.
     */
    Collection<MetaObject.Builder> geefRootMetaObjectBuilders() {
        final Set<MetaObject.Builder> objectMetParent = Sets.newHashSet(objectParentMap.keySet());
        return Maps.filterValues(objectMap, Predicates.not(Predicates.in(objectMetParent))).values();
    }

    /**
     * Maakt MetaRecord.Builder objecten voor alle BlobRecords.
     */
    private void mapRecords(final List<BlobRecord> recordList, final BiConsumer<MetaRecord.Builder, BlobRecord> recordMapper) {
        for (final BlobRecord blobRecord : recordList) {
            final MetaGroep.Builder groepBuilder = maakGroepBuilder(blobRecord);
            final MetaRecord.Builder recordBuilder = groepBuilder.metRecord();
            recordMap.put(blobRecord, recordBuilder);
            recordMapper.accept(recordBuilder, blobRecord);
        }
    }

    /**
     * Maakt een MetaGroep.Builder object voor het BlobRecord indien deze niet al bestaat.
     */
    private MetaGroep.Builder maakGroepBuilder(final BlobRecord record) {
        final String key = String.format("groep.element.id=%d-object.id=%d", record.getGroepElementId(), record.getObjectSleutel());
        MetaGroep.Builder builder = groepMap.get(key);
        if (builder == null) {
            builder = new MetaGroep.Builder();
            builder.metGroepElement(record.getGroepElementId());
            groepMap.put(key, builder);

            final MetaObject.Builder objectBuilder = getObjectBuilder(record.getObjectElementId(), record.getObjectSleutel());
            objectBuilder.metGroep(builder);

            if (record.getParentObjectSleutel() != null) {
                objectParentMap.put(objectBuilder, maakObjectBuilderKey(record.getParentObjectElementId(), record.getParentObjectSleutel()));
            }
        }
        return builder;
    }

    /**
     * Maakt een MetaObject.Builder object voor het BlobRecord indien deze niet al bestaat.
     */
    private MetaObject.Builder getObjectBuilder(final int elementId, final Long objectSleutel) {
        final String key = maakObjectBuilderKey(elementId, objectSleutel);
        MetaObject.Builder builder = objectMap.get(key);
        if (builder == null) {
            builder = MetaObject.maakBuilder();
            builder.metId(objectSleutel);
            builder.metObjectElement(elementId);
            objectMap.put(key, builder);
        }
        return builder;
    }
}
