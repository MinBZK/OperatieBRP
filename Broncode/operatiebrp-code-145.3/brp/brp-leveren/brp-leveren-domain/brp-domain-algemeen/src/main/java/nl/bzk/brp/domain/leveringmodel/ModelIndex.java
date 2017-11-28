/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import java.util.Collection;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.element.ObjectElement;

/**
 * Klasse voor het optimaliseren van het zoeken van objecten en groepen in de persoon structuur.
 */
public final class ModelIndex {

    private final MetaObject metaObject;
    private final SetMultimap<ObjectElement, MetaObject> objectenMap;
    private final SetMultimap<GroepElement, MetaGroep> groepenMap;
    private final SetMultimap<AttribuutElement, MetaAttribuut> attributenMap;

    /**
     * Constructor.
     * @param metaObject het meta object
     */
    public ModelIndex(final MetaObject metaObject) {
        this.metaObject = metaObject;
        final Indexer indexer = new Indexer();
        indexer.visit(metaObject);
        objectenMap = Multimaps.unmodifiableSetMultimap(indexer.objectenMap);
        groepenMap = Multimaps.unmodifiableSetMultimap(indexer.groepenMap);
        attributenMap = Multimaps.unmodifiableSetMultimap(indexer.attributenMap);
    }

    /**
     * @return het metaobject waar de index voor gemaakt is
     */
    public MetaObject getMetaObject() {
        return metaObject;
    }

    /**
     * @param element het type groep
     * @return een verzameling groepen van het gegeven element
     */
    public Set<MetaGroep> geefGroepenVanElement(final GroepElement element) {
        return groepenMap.get(element);
    }

    /**
     * @return alle groepen
     */
    public Collection<MetaGroep> geefGroepen() {
        return groepenMap.values();
    }

    /**
     * @return alle objecten
     */
    public Collection<MetaObject> geefObjecten() {
        return objectenMap.values();
    }

    /**
     * @return alle attribuut elementen
     */
    public Set<AttribuutElement> geefAttribuutElementen() {
        return attributenMap.keySet();
    }

    /**
     * Geef een lijst van attributen van het gegeven type.
     * @param element het attribuut element
     * @return een verzameling attributen van het gegeven element
     */
    public Set<MetaAttribuut> geefAttributen(final Element element) {
        return geefAttributen(ElementHelper.getAttribuutElement(element));
    }

    /**
     * Geef een lijst van attributen van het gegeven type.
     * @param element het attribuut element
     * @return een verzameling attributen van het gegeven element
     */
    public Set<MetaAttribuut> geefAttributen(final AttribuutElement element) {
        return attributenMap.get(element);
    }

    /**
     * @param element een object element
     * @return een verzameling objecten van het gegeven object element.
     */
    public Set<MetaObject> geefObjecten(final Element element) {
        return geefObjecten(ElementHelper.getObjectElement(element));
    }

    /**
     * @param element een object element
     * @return een verzameling objecten van het gegeven object element.
     */
    public Set<MetaObject> geefObjecten(final ObjectElement element) {
        return objectenMap.get(element);
    }

    /**
     * Model visitor om het model te indexeren
     */
    private static final class Indexer extends ParentFirstModelVisitor {

        private final SetMultimap<ObjectElement, MetaObject> objectenMap = LinkedHashMultimap.create();
        private final SetMultimap<GroepElement, MetaGroep> groepenMap = LinkedHashMultimap.create();
        private final SetMultimap<AttribuutElement, MetaAttribuut> attributenMap = LinkedHashMultimap.create();

        @Override
        public void doVisit(final MetaObject metaObject) {
            objectenMap.put(metaObject.getObjectElement(), metaObject);
        }

        @Override
        public void doVisit(final MetaGroep metaGroep) {
            groepenMap.put(metaGroep.getGroepElement(), metaGroep);
        }

        @Override
        public void doVisit(final MetaAttribuut attribuut) {
            attributenMap.put(attribuut.getAttribuutElement(), attribuut);
        }
    }
}
