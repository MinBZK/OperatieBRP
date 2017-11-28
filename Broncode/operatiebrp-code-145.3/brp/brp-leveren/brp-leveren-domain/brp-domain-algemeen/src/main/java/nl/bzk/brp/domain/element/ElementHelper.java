/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.element;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.commons.lang3.StringUtils;

/**
 * Helper klasse om elementen te categoriseren en een API aan te bieden om elementen op te vragen.
 */
public final class ElementHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static ElementLocatorImpl locator = new ElementLocatorImpl();

    /**
     * Private constructor.
     */
    private ElementHelper() {
    }

    /**
     * Geeft element op basis van naam.
     * @param naam naam van het element
     * @return het element indien deze bestaat.
     * @throws IllegalStateException als het element niet bestaat
     */
    public static ElementObject getElement(final String naam) {
        final ElementObject element = locator.naamElementMap.get(naam);
        if (element == null) {
            throw new IllegalStateException("Element niet gevonden met naam:" + naam);
        }
        return element;
    }

    /**
     * Geeft {@link AttribuutElement} op basis van naam.
     * @param naam naam van het element
     * @return het element indien deze bestaat.
     * @throws IllegalStateException als het elementtype onjuist is of het element niet bestaat
     */
    public static AttribuutElement getAttribuutElement(final String naam) {
        final ElementObject element = getElement(naam);
        if (element instanceof AttribuutElement) {
            return (AttribuutElement) element;
        } else {
            throw new IllegalStateException(String.format("Het gevonden element met naam %s is geen attribuutelement", naam));
        }
    }

    /**
     * Geeft {@link GroepElement} op basis van naam.
     * @param naam naam van het element
     * @return het element indien deze bestaat.
     * @throws IllegalStateException als het elementtype onjuist is of het element niet bestaat
     */
    public static GroepElement getGroepElement(final String naam) {
        final ElementObject element = getElement(naam);
        if (element instanceof GroepElement) {
            return (GroepElement) element;
        } else {
            throw new IllegalStateException(String.format("Het gevonden element met naam %s is geen groepelement", naam));
        }
    }

    /**
     * Geeft {@link ObjectElement} op basis van naam.
     * @param naam naam van het element
     * @return het element indien deze bestaat.
     * @throws IllegalStateException als het elementtype onjuist is of het element niet bestaat
     */
    public static ObjectElement getObjectElement(final String naam) {
        final ElementObject element = getElement(naam);
        if (element instanceof ObjectElement) {
            return (ObjectElement) element;
        } else {
            throw new IllegalStateException(String.format("Het gevonden element met naam %s is geen objectelement", naam));
        }
    }

    /**
     * Geeft element op basis van id.
     * @param id id van het element
     * @return het element indien deze bestaat.
     * @throws IllegalStateException als het element niet bestaat
     */
    public static ElementObject getElementMetId(final Integer id) {
        final ElementObject element = locator.idElementMap.get(id);
        if (element == null) {
            throw new IllegalStateException("Element niet gevonden met id:" + id);
        }
        return element;
    }

    /**
     * Geeft het AttribuutElement dat hoort bij het gegeven element.
     * @param element een element
     * @return het AttribuutElement indien deze bestaat.
     */
    public static AttribuutElement getAttribuutElement(final Element element) {
        return getAttribuutElement(element.getId());
    }

    /**
     * Geeft het AttribuutElement dat hoort bij het gegeven elementId.
     * @param elementId element elementId
     * @return het AttribuutElement indien deze bestaat.
     */
    public static AttribuutElement getAttribuutElement(final int elementId) {
        final AttribuutElement attribuutElement = locator.idAttribuutMap.get(elementId);
        if (attribuutElement == null) {
            throw new IllegalStateException("AttribuutElement niet gevonden met elementId:" + elementId);
        }
        return attribuutElement;
    }

    /**
     * Geeft het GroepElement dat hoort bij het gegeven element.
     * @param element element
     * @return het GroepElement indien deze bestaat.
     */
    public static GroepElement getGroepElement(final Element element) {
        return getGroepElement(element.getId());
    }

    /**
     * Geeft het GroepElement dat hoort bij het gegeven elementId.
     * @param elementId element elementId
     * @return het GroepElement indien deze bestaat.
     */
    public static GroepElement getGroepElement(final int elementId) {
        final GroepElement groepElement = locator.idGroepMap.get(elementId);
        if (groepElement == null) {
            throw new IllegalStateException("GroepElement niet gevonden met elementId:" + elementId);
        }
        return groepElement;
    }

    /**
     * Geeft het ObjectElement dat hoort bij het gegeven element.
     * @param element element element
     * @return het objectElement indien deze bestaat.
     */
    public static ObjectElement getObjectElement(final Element element) {
        return getObjectElement(element.getId());
    }

    /**
     * Geeft het ObjectElement dat hoort bij het gegeven elementId.
     * @param elementId element elementId
     * @return het objectElement indien deze bestaat.
     */
    public static ObjectElement getObjectElement(final int elementId) {
        final ObjectElement objectElement = locator.idObjectMap.get(elementId);
        if (objectElement == null) {
            throw new IllegalStateException("ObjectElement niet gevonden met elementId:" + elementId);
        }
        return objectElement;
    }

    /**
     * Geeft alle attribuut elementen.
     * @return collectie met alle attributen.
     */
    public static Collection<AttribuutElement> getAttributen() {
        return locator.idAttribuutMap.values();
    }

    /**
     * Geeft alle groep elementen.
     * @return collectie met alle groepen.
     */
    public static Collection<GroepElement> getGroepen() {
        return locator.idGroepMap.values();
    }

    /**
     * Geeft alle object elementen.
     * @return collectie met alle objecten.
     */
    public static Collection<ObjectElement> getObjecten() {
        return locator.idObjectMap.values();
    }

    /**
     * De naamgeving voor objectrelaties worden beschreven middels inverse attributen die aanwezig zijn op de attributen van een gegeven object. <br>
     * Bijvoorbeeld: De naamgeving voor Persoon (Object) naar Adres (Object) wordt beschreven middels het attribuut 'Persoon.Adres.Persoon' wat de
     * inverseassociatiecode 'Adressen' heeft.
     * @return een lijst van alle inverse attributen.
     */
    public static Collection<AttribuutElement> geefAlleInverseAttribuutElementen() {
        return locator.inverseAttribuutMap.values();
    }

    /**
     * Geeft de associatiecode van een gegeven object, of null indien deze niet bestaat.
     * @param objectElement een objectElement
     * @return de associatiecode
     */
    public static String getObjectAssociatiecode(final ObjectElement objectElement) {
        final AttribuutElement attribuutElement = locator.inverseAttribuutMap.get(objectElement);
        return attribuutElement != null ? attribuutElement.getInverseAssociatieIdentCode() : null;
    }

    /**
     * Reset de elementen.
     */
    static synchronized void reset() {
        locator = new ElementLocatorImpl();
    }

    /**
     * Geeft indicatie of een element met de gegeven naam bestaat.
     * @param naam naam van het element
     * @return boolean waarde
     */
    public static boolean bestaatElementMetNaam(final String naam) {
        return locator.naamElementMap.containsKey(naam);
    }

    /**
     * Helper class om Elementen te vinden op id / naam.
     */
    private static final class ElementLocatorImpl implements ElementLocator {
        private Map<Integer, ObjectElement> idObjectMap;
        private Map<Integer, GroepElement> idGroepMap;
        private Map<Integer, AttribuutElement> idAttribuutMap;
        private Map<Integer, ElementObject> idElementMap;
        private Map<String, ElementObject> naamElementMap;
        private Map<Integer, List<AttribuutElement>> groepAttribuutMap;
        private Map<Integer, List<GroepElement>> objectGroepMap;
        private Map<ObjectElement, AttribuutElement> inverseAttribuutMap;
        private Map<Integer, GroepElement> objectSorteerGroepMap;
        private Map<GroepElement, List<AttribuutElement>> sorteerAttributenVoorGroep;


        ElementLocatorImpl() {
            mapRecordOpElement();
            mapObjectNaarGroep();
            mapGroepNaarAttribuut();
            mapInverseAttributen();
            mapSorteerAttributen();

            for (ElementObject elementObject : idElementMap.values()) {
                elementObject.postCreate(this);
            }
            LOGGER.debug("Elementen geinitialiseerd");
        }

        private void mapRecordOpElement() {
            final Map<Integer, ObjectElement> tempIdNaarObject = Maps.newHashMap();
            final Map<Integer, GroepElement> tempIdNaarGroepElement = Maps.newHashMap();
            final Map<Integer, AttribuutElement> tempIdNaarAttribuutElement = Maps.newHashMap();
            final Map<String, ElementObject> tempNaamNaarElement = Maps.newHashMap();
            final Map<Integer, ElementObject> tempIdNaarElement = Maps.newHashMap();

            //zet records om naar elementen
            for (final Element element : Element.values()) {
                switch (element.getSoort()) {
                    case OBJECTTYPE:
                        final ObjectElement objectElement = new ObjectElement(element);
                        tempIdNaarObject.put(objectElement.getId(), objectElement);
                        tempNaamNaarElement.put(element.getNaam(), objectElement);
                        tempIdNaarElement.put(element.getId(), objectElement);
                        break;
                    case GROEP:
                        final GroepElement groepElement = new GroepElement(element);
                        tempIdNaarGroepElement.put(groepElement.getId(), groepElement);
                        tempNaamNaarElement.put(groepElement.getNaam(), groepElement);
                        tempIdNaarElement.put(element.getId(), groepElement);
                        break;
                    case ATTRIBUUT:
                        final AttribuutElement attribuutElement = new AttribuutElement(element);
                        tempIdNaarAttribuutElement.put(attribuutElement.getId(), attribuutElement);
                        tempNaamNaarElement.put(attribuutElement.getNaam(), attribuutElement);
                        tempIdNaarElement.put(element.getId(), attribuutElement);
                        break;
                    default:
                        //noop
                }
            }

            idObjectMap = ImmutableMap.copyOf(tempIdNaarObject);
            idGroepMap = ImmutableMap.copyOf(tempIdNaarGroepElement);
            idAttribuutMap = ImmutableMap.copyOf(tempIdNaarAttribuutElement);
            naamElementMap = ImmutableMap.copyOf(tempNaamNaarElement);
            idElementMap = ImmutableMap.copyOf(tempIdNaarElement);

        }

        private void mapSorteerAttributen() {
            final Map<Integer, GroepElement> objectSorteerGroepTemp = Maps.newHashMap();
            final ArrayListMultimap<GroepElement, AttribuutElement> sorteerElementenVoorGroep = ArrayListMultimap.create();
            for (final AttribuutElement attribuutElement : idAttribuutMap.values()) {
                if (attribuutElement.getElement().getElementWaarde().getSorteervolgorde() != null) {
                    objectSorteerGroepTemp.put(attribuutElement.getObjectType(), idGroepMap.get(attribuutElement.getGroepId()));
                    sorteerElementenVoorGroep.put(idGroepMap.get(attribuutElement.getGroepId()), attribuutElement);
                }
            }
            objectSorteerGroepMap = ImmutableMap.copyOf(objectSorteerGroepTemp);
            final Map<GroepElement, List<AttribuutElement>> gesorteerdeElementenVoorGroepTemp = new HashMap<>();

            for (GroepElement groepElement : sorteerElementenVoorGroep.keySet()) {
                final List<AttribuutElement> sorteerAttributen = sorteerElementenVoorGroep.get(groepElement);
                sorteerAttributen.sort(
                        Comparator.comparing(o -> o.getElement().getElementWaarde().getSorteervolgorde()));
                gesorteerdeElementenVoorGroepTemp.put(groepElement, sorteerAttributen);
            }
            sorteerAttributenVoorGroep = ImmutableMap.copyOf(gesorteerdeElementenVoorGroepTemp);
        }

        private void mapInverseAttributen() {
            final Map<ObjectElement, AttribuutElement> tempMap = Maps.newHashMap();
            for (final AttribuutElement attribuutElement : idAttribuutMap.values()) {
                if (!StringUtils.isEmpty(attribuutElement.getInverseAssociatieIdentCode())) {
                    tempMap.put(idObjectMap.get(attribuutElement.getObjectType()), attribuutElement);
                }
            }
            inverseAttribuutMap = ImmutableMap.copyOf(tempMap);
        }

        private void mapGroepNaarAttribuut() {
            final Map<Integer, List<AttribuutElement>> tempMap = Maps.newHashMap();
            for (final GroepElement groepElement : idGroepMap.values()) {
                final List<AttribuutElement> elementList = new LinkedList<>();
                for (final AttribuutElement attribuutElement : idAttribuutMap.values()) {
                    if (attribuutElement.getGroepId() == groepElement.getId()) {
                        elementList.add(attribuutElement);
                    }
                }
                tempMap.put(groepElement.getId(), Collections.unmodifiableList(elementList));
            }
            groepAttribuutMap = ImmutableMap.copyOf(tempMap);
        }

        private void mapObjectNaarGroep() {
            final Map<Integer, List<GroepElement>> tempMap = Maps.newHashMap();
            for (final ObjectElement objectElement : idObjectMap.values()) {
                final List<GroepElement> elementList = Lists.newLinkedList();
                for (final GroepElement groepElement : idGroepMap.values()) {
                    if (groepElement.getElement().getElementWaarde().getObjecttype().equals(objectElement.getElement().getId())) {
                        elementList.add(groepElement);
                    }
                }
                tempMap.put(objectElement.getId(), ImmutableList.copyOf(elementList));
            }
            objectGroepMap = ImmutableMap.copyOf(tempMap);
        }

        @Override
        public ObjectElement getObject(final int elementId) {
            return idObjectMap.get(elementId);
        }


        /**
         * Geeft de attributen die behoren tot gegeven groep
         * @param elementId elementId van de groep
         * @return lijst van attributen
         */
        @Override
        public List<AttribuutElement> getAttributenInGroep(final int elementId) {
            return groepAttribuutMap.get(elementId);
        }

        @Override
        public List<GroepElement> getGroepenInObject(final int elementId) {
            return objectGroepMap.get(elementId);
        }

        @Override
        public GroepElement getSorteerGroep(final ObjectElement objectElement) {
            return objectSorteerGroepMap.get(objectElement.getId());
        }

        @Override
        public List<AttribuutElement> getSorteerElementen(final ObjectElement objectElement) {
            final GroepElement groepElement = objectSorteerGroepMap.get(objectElement.getId());
            if (groepElement == null) {
                return Collections.emptyList();
            }
            return sorteerAttributenVoorGroep.get(groepElement);
        }

        @Override
        public GroepElement getGroep(final int elementId) {
            return idGroepMap.get(elementId);
        }

        @Override
        public AttribuutElement getAttribuut(final int elementId) {
            return idAttribuutMap.get(elementId);
        }
    }
}
