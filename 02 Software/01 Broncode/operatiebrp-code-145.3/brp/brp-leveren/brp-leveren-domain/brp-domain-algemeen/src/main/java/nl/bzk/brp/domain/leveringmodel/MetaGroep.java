/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;

/**
 * Realisatie van een BMR groep in het persoonsmodel.
 */
public final class MetaGroep extends MetaModel {
    /**
     * Het parent object dat de groep bevat
     */
    private MetaObject parentObject;

    /**
     * Type groep
     */
    private GroepElement groepElement;

    /**
     * Aantal historische voorkomens in de groep
     */
    private Set<MetaRecord> records;

    /**
     * Constructor een BrpGroep.
     */
    private MetaGroep() {
    }

    @Override
    public MetaModel getParent() {
        return parentObject;
    }

    public MetaObject getParentObject() {
        return parentObject;
    }

    public GroepElement getGroepElement() {
        return groepElement;
    }

    /**
     * @return de records binnen deze groep
     */
    public Set<MetaRecord> getRecords() {
        return records;
    }

    @Override
    public void accept(final ModelVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return groepElement.toString();
    }

    /**
     * Mutable parameter object om een MetaGroep te construeren.
     */
    public static final class Builder {

        /**
         * Aantal historische voorkomens in de groep
         */
        private final List<MetaRecord.Builder> recordBuilderList = Lists.newLinkedList();

        /**
         * De object builder
         */
        private final MetaObject.Builder objectBuilder;

        /**
         * De gebouwde groep
         */
        private final MetaGroep groep = new MetaGroep();

        /**
         * Default constructor.
         * @param objectBuilder de parent object builder
         */
        public Builder(final MetaObject.Builder objectBuilder) {
            this.objectBuilder = objectBuilder;
        }

        /**
         * voor bouw in filters, uitgesteld toevoegen via lijst.
         */
        public Builder() {
            objectBuilder = null;
        }

        /**
         * Zet het groep element.
         * @param id id van groep element
         * @return de builder
         */
        public Builder metGroepElement(final int id) {
            return metGroepElement(ElementHelper.<GroepElement>getGroepElement(id));
        }

        /**
         * Zet het groep element.
         * @param element element van groep element
         * @return de builder
         */
        public Builder metGroepElement(final Element element) {
            return metGroepElement(ElementHelper.getGroepElement(element));
        }

        /**
         * Zet het groep element.
         * @param groepElement een groep element
         * @return de builder
         */
        public Builder metGroepElement(final GroepElement groepElement) {
            groep.groepElement = groepElement;
            return this;
        }

        /**
         * Voegt een record toe.
         * @return de record builder
         */
        public MetaRecord.Builder metRecord() {
            final MetaRecord.Builder recordBuilder = new MetaRecord.Builder(this);
            recordBuilderList.add(recordBuilder);
            return recordBuilder;
        }

        /**
         * @param builderList lijst van record builders
         * @return deze builder
         */
        public Builder metRecords(final List<MetaRecord.Builder> builderList) {
            recordBuilderList.addAll(builderList);
            return this;
        }

        /**
         * @param builder een recordbuilder
         * @return deze builder
         */
        public Builder metRecord(final MetaRecord.Builder builder) {
            recordBuilderList.add(builder);
            return this;
        }

        GroepElement getGroepElement() {
            return groep.groepElement;
        }

        /**
         * Markeert einde van de groep.
         * @return de object builder
         */
        public MetaObject.Builder eindeGroep() {
            return objectBuilder;
        }

        /**
         * Bouwt de groep.
         * @param gebouwdObject het parent object
         * @return de groep
         */
        public MetaGroep build(final MetaObject gebouwdObject) {
            groep.parentObject = gebouwdObject;
            final Map<Long, MetaRecord> tempRecords = Maps.newHashMap();
            for (final MetaRecord.Builder builder : recordBuilderList) {
                final MetaRecord record = builder.build(groep);
                tempRecords.put(record.getVoorkomensleutel(), record);
            }
            groep.records = ImmutableSet.copyOf(tempRecords.values());
            return groep;
        }
    }
}
