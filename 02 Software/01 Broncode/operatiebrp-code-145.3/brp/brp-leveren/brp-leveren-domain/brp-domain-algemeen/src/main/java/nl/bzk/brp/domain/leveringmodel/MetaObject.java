/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.element.ObjectElement;

/**
 * Realisatie van een BMR object in het persoonsmodel.
 */
public final class MetaObject extends MetaModel {

    /**
     * Het type object
     */
    private ObjectElement objectElement;

    /**
     * Het parent object
     */
    private MetaObject parentObject;

    /**
     * Set met geassocieerde objecten
     */
    private Set<MetaObject> objecten;
    private Multimap<ObjectElement, MetaObject> elementObjectMap;

    /**
     * De groepen die het object bevat Bijv. Identiteit, Standaard
     */
    private Set<MetaGroep> groepen;
    private Map<GroepElement, MetaGroep> elementGroepMap;

    /**
     * Technische sleutel
     */
    private long objectsleutel;

    /**
     * Constructor voor een MetaObject.
     */
    private MetaObject() {
    }

    public ObjectElement getObjectElement() {
        return objectElement;
    }

    @Override
    public MetaModel getParent() {
        return parentObject;
    }

    /**
     * @return het optionele parent object
     */
    public MetaObject getParentObject() {
        return parentObject;
    }

    /**
     * @return de technische sleutel van het object
     */
    public long getObjectsleutel() {
        return objectsleutel;
    }

    /**
     * Geef de onderliggende objecten.
     * @return set van BrpObject objecten
     */
    public Set<MetaObject> getObjecten() {
        return objecten;
    }

    /**
     * Geef de onderliggende groepen.
     * @return set van BrpGroep objecten
     */
    public Set<MetaGroep> getGroepen() {
        return groepen;
    }

    /**
     * @param groepElement een groep element
     * @return een onderliggende groep
     */
    public MetaGroep getGroep(final Element groepElement) {
        return elementGroepMap.get(ElementHelper.getGroepElement(groepElement));
    }

    /**
     * @param groepElement een groep element
     * @return een onderliggende groep
     */
    public MetaGroep getGroep(final GroepElement groepElement) {
        return elementGroepMap.get(groepElement);
    }

    /**
     * @param element een object element
     * @return een onderliggende object
     */
    public Collection<MetaObject> getObjecten(final ObjectElement element) {
        return elementObjectMap.get(element);
    }

    /**
     * Voert een deep-equals operatie uit waarbij gekeken wordt of de metaobjecten inhoudelijk gelijk zijn. Als performance overweging is ervoor gekozen
     * dat de equals operaties werken op basis van Object.equals. Dit heeft tot gevolg dat equals false oplevert voor inhoudelijk gelijke objecten. Om dit
     * te ondervangen kan een deep equals uitgevoerd worden waarbij een afdruk (platgeslagen text representatie) wordt vergeleken.
     * @param metaObject andere object
     * @return indicatie of de objecten in houdelijk gelijk zijn.
     */
    public boolean deepEquals(final MetaObject metaObject) {
        return ModelAfdruk.maakAfdruk(this).equals(ModelAfdruk.maakAfdruk(metaObject));
    }

    @Override
    public void accept(final ModelVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return getObjectElement().toString();
    }

    /**
     * Maakt een builder object om een MetaObject op te bouwen.
     * @return een builder
     */
    public static Builder maakBuilder() {
        return new Builder();
    }

    /**
     * Mutable parameter object om een MetaObject te construeren.
     */
    public static final class Builder {

        private Builder parentBuilder;

        /**
         * Het type object
         */
        private ObjectElement objectElement;

        /**
         * Geassocieerde objecten, gegroepeerd op type Bijv Persoon heeft meerdere adressen
         */
        private final List<Builder> objectBuilderList = Lists.newLinkedList();

        /**
         * De groepen die het object bevat Bijv. Identiteit, Standaard
         */
        private final List<MetaGroep.Builder> groepBuilderList = Lists.newLinkedList();

        /**
         * Technische sleutel
         */
        private long objectsleutel;

        /**
         * Default constructor voor params.
         * @param parentBuilder de parent object builder
         */
        public Builder(final Builder parentBuilder) {
            this.parentBuilder = parentBuilder;
        }

        /**
         * Voor filter builders, uitgesteld toevoegen via metBuilder.
         */
        public Builder() {
            //no comment
        }

        /**
         * Zet het id van het object.
         * @param metObjectsleutel het id van het object
         * @return de builder
         */
        public Builder metId(final Number metObjectsleutel) {
            objectsleutel = metObjectsleutel.longValue();
            return this;
        }

        /**
         * Zet het element van het object.
         * @param id van het object element
         * @return de builder
         */
        public Builder metObjectElement(final int id) {
            return metObjectElement(ElementHelper.<ObjectElement>getObjectElement(id));
        }

        /**
         * Zet het element van het object.
         * @param element van het object element
         * @return de builder
         */
        public Builder metObjectElement(final Element element) {
            return metObjectElement(ElementHelper.<ObjectElement>getObjectElement(element));
        }

        /**
         * Zet het element van het object.
         * @param metObjectElement het object element
         * @return de builder
         */
        public Builder metObjectElement(final ObjectElement metObjectElement) {
            objectElement = metObjectElement;
            return this;
        }

        /**
         * Voegt een groep toe.
         * @return de builder
         */
        public MetaGroep.Builder metGroep() {
            final MetaGroep.Builder groepBuilder = new MetaGroep.Builder(this);
            groepBuilderList.add(groepBuilder);
            return groepBuilder;
        }

        /**
         * Voegt een groep toe.
         * @param builder de groep builder
         * @return de builder
         */
        public Builder metGroep(final MetaGroep.Builder builder) {
            groepBuilderList.add(builder);
            return this;
        }

        /**
         * @param builderList een lijst van groep builders
         * @return deze builder
         */
        public Builder metGroepen(final List<MetaGroep.Builder> builderList) {
            groepBuilderList.addAll(builderList);
            return this;
        }

        /**
         * Voegt een object toe.
         * @return de builder
         */
        public Builder metObject() {
            final Builder objectBuilder = new Builder(this);
            objectBuilderList.add(objectBuilder);
            return objectBuilder;
        }

        /**
         * Voegt een object toe.
         * @param objectBuilder de object builder
         * @return de builder
         */
        public Builder metObject(final Builder objectBuilder) {
            objectBuilderList.add(objectBuilder);
            return this;
        }

        /**
         * @param builderList een lijst van object builders
         * @return deze builder
         */
        public Builder metObjecten(final Collection<Builder> builderList) {
            objectBuilderList.addAll(builderList);
            return this;
        }

        /**
         * @return de parent builder, als aanwezig, anders deze builder
         */
        public MetaObject.Builder eindeObject() {
            return parentBuilder != null ? parentBuilder : this;
        }

        /**
         * Bouwt het MetaObject.
         * @return het MetaObject
         */
        public MetaObject build() {
            return build(null);
        }

        /**
         * Bouwt het MetaObject.
         * @param parentObject het parent meta object
         * @return het MetaObject
         */
        MetaObject build(final MetaObject parentObject) {
            final MetaObject gebouwdObject = new MetaObject();
            gebouwdObject.parentObject = parentObject;
            gebouwdObject.objectsleutel = objectsleutel;
            gebouwdObject.objectElement = objectElement;

            final Multimap<ObjectElement, MetaObject> tempObjectenMap = HashMultimap.create();
            for (final Builder builder : objectBuilderList) {
                final MetaObject object = builder.build(gebouwdObject);
                tempObjectenMap.put(object.getObjectElement(), object);
            }
            gebouwdObject.elementObjectMap = ImmutableMultimap.copyOf(tempObjectenMap);
            gebouwdObject.objecten = ImmutableSet.copyOf(tempObjectenMap.values());

            final Map<GroepElement, MetaGroep> tempGroepenMap = Maps.newHashMap();
            for (final MetaGroep.Builder groepBuilder : groepBuilderList) {
                final MetaGroep groep = groepBuilder.build(gebouwdObject);
                tempGroepenMap.put(groep.getGroepElement(), groep);
            }
            gebouwdObject.elementGroepMap = ImmutableMap.copyOf(tempGroepenMap);
            gebouwdObject.groepen = ImmutableSet.copyOf(tempGroepenMap.values());
            return gebouwdObject;
        }
    }
}
