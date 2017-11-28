/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;

/**
 * De klasse bouwt een nieuwe MetaObject structuur op basis van een gegeven MetaObject. Deze klasse biedt optioneel de mogelijkheid een filter mee te geven voor
 * objecten, groepen, record en attributen.
 */
public final class MetaObjectFilter {

    private final MetaObject brpObject;
    private Predicate<MetaObject> objectFilter = p -> true;
    private Predicate<MetaGroep> groepFilter = p -> true;
    private Predicate<MetaRecord> recordFilter = p -> true;
    private Predicate<MetaAttribuut> attribuutFilter = p -> true;

    /**
     * Constructor.
     * @param brpObject het te filteren object.
     */
    public MetaObjectFilter(final MetaObject brpObject) {
        this.brpObject = brpObject;
    }

    /**
     * Voeg een {@link MetaObject} filter toe.
     * @param predicate een {@link Predicate}
     * @return dit {@link MetaObjectFilter}
     */
    public MetaObjectFilter addObjectFilter(final Predicate<MetaObject> predicate) {
        this.objectFilter = this.objectFilter.and(predicate);
        return this;
    }

    /**
     * Voeg een {@link MetaGroep} filter toe.
     * @param predicate een {@link Predicate}
     * @return dit {@link MetaObjectFilter}
     */
    public MetaObjectFilter addGroepFilter(final Predicate<MetaGroep> predicate) {
        this.groepFilter = this.groepFilter.and(predicate);
        return this;
    }

    /**
     * Voeg een {@link MetaRecord} filter toe.
     * @param predicate een {@link Predicate}
     * @return dit {@link MetaObjectFilter}
     */
    public MetaObjectFilter addRecordFilter(final Predicate<MetaRecord> predicate) {
        this.recordFilter = this.recordFilter.and(predicate);
        return this;
    }

    /**
     * Voeg een {@link MetaAttribuut} filter toe.
     * @param predicate een {@link Predicate}
     * @return dit {@link MetaObjectFilter}
     */
    public MetaObjectFilter addAttribuutFilter(final Predicate<MetaAttribuut> predicate) {
        this.attribuutFilter = this.attribuutFilter.and(predicate);
        return this;
    }

    /**
     * Maakt een nieuw object van het input object en past filtering toe.
     * @return het nieuwe MetaObject
     */
    public MetaObject filter() {
        final MetaObject.Builder builder = bouwObject(this.brpObject);
        if (builder == null) {
            return null;
        }
        return builder.build();
    }

    private MetaObject.Builder bouwObject(final MetaObject metaObject) {
        final MetaObject.Builder objectBuilder = new MetaObject.Builder();
        objectBuilder.metObjectElement(metaObject.getObjectElement());
        objectBuilder.metId(metaObject.getObjectsleutel());

        final List<MetaObject.Builder> kindObjectBuilders = metaObject.getObjecten().stream().filter(objectFilter)
                .map(this::bouwObject).filter(Objects::nonNull).collect(Collectors.toList());
        objectBuilder.metObjecten(kindObjectBuilders);

        final List<MetaGroep.Builder> groepen =
                metaObject.getGroepen().stream().filter(groepFilter)
                        .map(this::bouwGroep).filter(Objects::nonNull).collect(Collectors.toList());
        if (!groepen.isEmpty()) {
            objectBuilder.metGroepen(groepen);
            return objectBuilder;
        }
        return null;
    }

    private MetaGroep.Builder bouwGroep(final MetaGroep metaGroep) {
        final MetaGroep.Builder groepBuilder = new MetaGroep.Builder();
        groepBuilder.metGroepElement(metaGroep.getGroepElement());

        final List<MetaRecord.Builder> records =
                metaGroep.getRecords().stream().filter(recordFilter)
                        .map(r -> bouwRecord(r, groepBuilder)).filter(Objects::nonNull).collect(Collectors.toList());
        if (!records.isEmpty()) {
            groepBuilder.metRecords(records);
            return groepBuilder;
        }
        return null;
    }

    private MetaRecord.Builder bouwRecord(final MetaRecord record, final MetaGroep.Builder groepBuilder) {
        final MetaRecord.Builder metaRecordBuilder = groepBuilder.metRecord();
        metaRecordBuilder.metId(record.getVoorkomensleutel());

        record.getAttributen().values().stream().filter(attribuutFilter)
                .forEach(a -> metaRecordBuilder.metAttribuut(a.getAttribuutElement(), a.getWaarde()));

        return metaRecordBuilder;
    }
}
