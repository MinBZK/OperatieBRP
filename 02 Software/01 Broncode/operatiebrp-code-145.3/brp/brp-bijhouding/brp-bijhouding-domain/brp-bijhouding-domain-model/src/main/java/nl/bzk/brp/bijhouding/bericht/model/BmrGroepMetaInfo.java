/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.brp.bijhouding.bericht.annotation.XmlChildList;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElementInterface;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElementRegister;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElementen;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlTransient;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Deze class bevat de meta informatie van een {@link BmrGroep} class. Deze informatie wordt verkregen door gebruik te
 * maken van reflectie en de annotatie op de class.
 * @see nl.bzk.brp.bijhouding.bericht.annotation.XmlElement
 * @see nl.bzk.brp.bijhouding.bericht.annotation.XmlElementen
 * @see nl.bzk.brp.bijhouding.bericht.annotation.XmlChild
 * @see nl.bzk.brp.bijhouding.bericht.annotation.XmlChildList
 * @see nl.bzk.brp.bijhouding.bericht.annotation.XmlTransient
 */
public final class BmrGroepMetaInfo {

    private static final String ELEMENT_NAMEN_METHOD = "getElementNamen";
    private static final String PARSE_ELEMENT_NAAM_METHOD = "parseElementNaam";
    private static final Map<Class, BmrGroepMetaInfo> META_INFO_MAP = new HashMap<>();

    private final Class<?> elementType;
    private final List<String> mogelijkeElementNamen;
    private final Map<String, BmrFieldMetaInfo> childElementNaamFieldMetaInfoMap;
    private final List<BmrFieldMetaInfo> childElementMetaInfoLijst;

    private BmrGroepMetaInfo(
            final Class<?> elementType,
            final List<String> mogelijkeElementNamen,
            final Map<String, BmrFieldMetaInfo> childElementNaamFieldMetaInfoMap,
            final List<BmrFieldMetaInfo> childElementMetaInfoLijst) {
        this.elementType = elementType;
        this.mogelijkeElementNamen = mogelijkeElementNamen;
        this.childElementNaamFieldMetaInfoMap = childElementNaamFieldMetaInfoMap;
        this.childElementMetaInfoLijst = childElementMetaInfoLijst;
    }

    /**
     * Geef de waarde van elementType.
     * @return elementType
     */
    public Class<?> getElementType() {
        return elementType;
    }

    /**
     * Geeft de verzameling van XML velden.
     * @return xml velden
     */
    public Collection<String> getXmlChildNamen() {
        return childElementNaamFieldMetaInfoMap.keySet();
    }

    /**
     * Geef de waarde van mogelijkeElementNamen.
     * @return mogelijkeElementNamen
     */
    public List<String> getMogelijkeElementNamen() {
        return Collections.unmodifiableList(mogelijkeElementNamen);
    }

    /**
     * Geef de lijst met metainformatie van elke veld van deze groep.
     * @return de meta informatie van de velden van deze groep
     */
    public List<BmrFieldMetaInfo> getChildElementMetaInfoLijst() {
        return Collections.unmodifiableList(childElementMetaInfoLijst);
    }

    /**
     * Bepaald of er meta informatie is voor een child element met de gegeven naam.
     * @param childElementNaam child element naam
     * @return true als hiervoor meta informatie bestaat, anders false
     */
    public boolean hasChildElement(final String childElementNaam) {
        return childElementNaamFieldMetaInfoMap.containsKey(childElementNaam);
    }

    /**
     * Geeft het type dat correspondeert met de lijst van child elementen dat hoort bij deze element naam.
     * @param childElementNaam de child element naam
     * @return het type dat van het element dat in de lijst zit
     */
    public Class<?> getChildListElementType(final String childElementNaam) {
        if (!childElementNaamFieldMetaInfoMap.get(childElementNaam).isList()) {
            throw new IllegalArgumentException("Er is geen lijst veld die gemapped is op elementnaam: " + childElementNaam);
        } else {
            return childElementNaamFieldMetaInfoMap.get(childElementNaam).getListType();
        }
    }

    /**
     * Geeft het type terug voor de gegeven child element naam.
     * @param childElementNaam child element naam
     * @return het type
     */
    public Class<?> getChildElementType(final String childElementNaam) {
        final Class<?> result = childElementNaamFieldMetaInfoMap.get(childElementNaam).getTypeVoorElementNaam(childElementNaam);
        if (result == null) {
            throw new IllegalArgumentException("Er is geen veld die gemapped is op elementnaam: " + childElementNaam);
        }
        return result;
    }

    /**
     * Maakt een nieuw object op basis van de gegeven parameters. Om succesvol een nieuw object te kunnen maken dient er
     * een constructor te zijn die de volgende parameters bevat:
     * <ol>
     * <li><code>Map<String, String>: attributen</code></li>
     * <li>
     * <code>(ALLEEN BIJ @XmlElementen) enum: soort, die een static methode heeft met de volgende signature: parseElementNaam(String elementNaam)</code>
     * </li>
     * <li><code>een parameters voor elk niet-transient veld (zie @XmlTransient) in declaratie volgorde</code></li>
     * </ol>
     * @param elementNaam de naam van het element, wordt alleen gebruikt voor types die geannoteerd zijn met {@link XmlElementen}
     * @param attributen de lijst met attributen
     * @param childElementen de lijst met child elementen
     * @return een nieuw instantie van het type van deze meta informatie
     */
    public Object maakObject(final String elementNaam, final Map<String, String> attributen, final Map<String, Object> childElementen) {
        final Object soort = bepaalSoort(elementNaam);
        final Map.Entry<Class[], Object[]> parametersMapEntry = bepaalParameters(attributen, childElementen, soort);
        try {
            return zoekPassendeConstructor(parametersMapEntry.getKey()).newInstance(parametersMapEntry.getValue());
        } catch (
                InstantiationException
                        | IllegalAccessException
                        | InvocationTargetException e) {
            throw new IllegalArgumentException(String.format("Er kan geen instantie van type '%s' worden gemaakt.", elementType), e);
        }
    }

    private Map.Entry<Class[], Object[]> bepaalParameters(
            final Map<String, String> attributen,
            final Map<String, Object> childElementen,
            final Object soort) {
        final List<Class> parameterTypes = new ArrayList<>();
        final List<Object> parameterValues = new ArrayList<>();

        parameterTypes.add(Map.class);
        parameterValues.add(attributen);
        if (soort != null) {
            parameterTypes.add(soort.getClass());
            parameterValues.add(soort);
        }
        for (final BmrFieldMetaInfo fieldMetaInfo : childElementMetaInfoLijst) {
            String elementNaam = fieldMetaInfo.getMogelijkeElementNamen().iterator().next();
            Object parameterWaarde = null;
            for (final String mogelijkeElementNaam : fieldMetaInfo.getMogelijkeElementNamen()) {
                if (childElementen.containsKey(mogelijkeElementNaam)) {
                    elementNaam = mogelijkeElementNaam;
                    parameterWaarde = childElementen.get(mogelijkeElementNaam);
                    break;
                }
            }
            parameterTypes.add(fieldMetaInfo.getTypeVoorElementNaam(elementNaam));
            parameterValues.add(parameterWaarde);
        }
        return Collections.singletonMap(
                parameterTypes.toArray(new Class[parameterTypes.size()]),
                parameterValues.toArray(new Object[parameterValues.size()])).entrySet().iterator().next();
    }

    private Object bepaalSoort(final String elementNaam) {
        final XmlElementen xmlElementen = elementType.getAnnotation(XmlElementen.class);
        if (xmlElementen == null) {
            return null;
        }
        try {
            return xmlElementen.enumType().getDeclaredMethod(PARSE_ELEMENT_NAAM_METHOD, String.class).invoke(null, elementNaam);
        } catch (
                IllegalAccessException
                        | InvocationTargetException
                        | NoSuchMethodException e) {
            throw new IllegalArgumentException(
                    String.format("Het soort kan niet bepaald worden voor elementNaam '%s' in enum '%s'", elementNaam, xmlElementen.enumType()),
                    e);
        }
    }

    private Constructor<?> zoekPassendeConstructor(final Class[] parameterTypes) {
        Constructor<?> result = null;
        for (final Constructor<?> mogelijkeConstructor : elementType.getConstructors()) {
            if (isPassendeConstructor(mogelijkeConstructor, parameterTypes)) {
                result = mogelijkeConstructor;
                break;
            }
        }
        if (result == null) {
            throw new IllegalArgumentException(
                    String.format(
                            "Er is geen passende constructor gevonden voor type '%s'. Er moet een constructor zijn met de volgende parameters: %s",
                            elementType,
                            Arrays.asList(parameterTypes)));
        } else {
            return result;
        }
    }

    private static boolean isPassendeConstructor(final Constructor<?> constructor, final Class[] parameterTypes) {
        if (constructor.getParameterCount() == parameterTypes.length) {
            for (int index = 0; index < constructor.getParameterCount(); index++) {
                if (!constructor.getParameterTypes()[index].isAssignableFrom(parameterTypes[index])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Deze factory methode leest de class meta informatie of retourneerd het resultaat van een eerdere lees actie. Deze
     * methode wordt voornamelijk door writers ({@link nl.bzk.brp.bijhouding.bericht.writer.Writer}) gebruikt omdat die
     * beschikken over het specifieke type van het object dat wordt geschreven.
     * @param clazz de class waarvan de meta informatie moet worden gelezen
     * @return de meta informatie
     */
    public static BmrGroepMetaInfo getInstance(final Class clazz) {
        ValidatieHelper.controleerOpNullWaarde(clazz, "clazz");
        if (!META_INFO_MAP.containsKey(clazz)) {
            META_INFO_MAP.put(clazz, maakBmrGroepMetaInfo(clazz));
        }
        return META_INFO_MAP.get(clazz);
    }

    /**
     * Deze factory methode leest de class meta informatie of retourneerd het resultaat van een eerdere lees actie als
     * map van mogelijke element namen op het daarbij horende type. Deze methode kan ook omgaan met typen die
     * geannoteerd zijn met {@link nl.bzk.brp.bijhouding.bericht.annotation.XmlElementInterface}. Deze methode wordt
     * voornamelijk gebruikt door parsers ({@link nl.bzk.brp.bijhouding.bericht.parser.Parser}) omdat deze niet altijd
     * beschikken over het specifieke type maar soms een interface type en dan het element naam gebruiken om het
     * specifieke type te bepalen.
     * @param clazz de class waarvan de meta informatie moet worden gelezen
     * @return de meta informatie
     */
    public static Map<String, BmrGroepMetaInfo> getInstanceMap(final Class<?> clazz) {
        final XmlElementInterface xmlElementInterface = clazz.getAnnotation(XmlElementInterface.class);
        if (xmlElementInterface == null) {
            return converteerNaarMap(BmrGroepMetaInfo.getInstance(clazz));
        } else {
            try {
                final Map<String, BmrGroepMetaInfo> result = new LinkedHashMap<>();
                final XmlElementRegister xmlElementRegister = xmlElementInterface.value().newInstance();
                for (final Class<?> implementatieClass : xmlElementRegister.getImplementaties()) {
                    result.putAll(converteerNaarMap(BmrGroepMetaInfo.getInstance(implementatieClass)));
                }
                return result;
            } catch (
                    InstantiationException
                            | IllegalAccessException e) {
                throw new IllegalArgumentException("Er kan geen XmlElementRegister instantie gemaakt worden voor type: " + xmlElementInterface.value(), e);
            }
        }

    }

    /**
     * Bepaald de XML element naam die hoort bij het gegeven element object.
     * @param element het element
     * @return de element naam
     */
    public static String bepaalElementNaam(final Object element) {
        if (element == null) {
            return null;
        }

        final XmlElement xmlElementAnnotatie = element.getClass().getAnnotation(XmlElement.class);
        final XmlElementen xmlElementenAnnotatie = element.getClass().getAnnotation(XmlElementen.class);
        if (xmlElementAnnotatie != null) {
            return xmlElementAnnotatie.value();
        } else if (xmlElementenAnnotatie != null) {
            try {
                final Method soortMethode = element.getClass().getDeclaredMethod(xmlElementenAnnotatie.methode());
                final Object soortEnum = soortMethode.invoke(element);
                final Method elementNaamMethode = soortEnum.getClass().getDeclaredMethod(XmlElementen.ELEMENT_NAAM_METHOD);
                return (String) elementNaamMethode.invoke(soortEnum);
            } catch (
                    NoSuchMethodException
                            | InvocationTargetException
                            | IllegalAccessException e) {
                throw new IllegalArgumentException(
                        "De element naam kan niet bepaald worden voor een @XmlElementen object van het type: " + element.getClass(), e);
            }
        } else {
            throw new IllegalArgumentException(
                    "De element naam kan niet bepaald worden omdat de @XmlElement of @XmlElementen annotatie ontbreekt op type: " + element.getClass());
        }
    }

    private static BmrGroepMetaInfo maakBmrGroepMetaInfo(final Class clazz) {
        final List<Field> fields = getXmlFields(clazz);
        final Map<String, BmrFieldMetaInfo> childElementNaamFieldMetaInfoMap = new LinkedHashMap<>();
        final List<BmrFieldMetaInfo> childElementMetaInfoLijst = new ArrayList<>();
        for (final Field field : fields) {
            final BmrFieldMetaInfo fieldMetaInfo = BmrFieldMetaInfo.getInstance(field);
            childElementMetaInfoLijst.add(fieldMetaInfo);
            for (final String childElementNaam : fieldMetaInfo.getMogelijkeElementNamen()) {
                childElementNaamFieldMetaInfoMap.put(childElementNaam, fieldMetaInfo);
            }
        }
        final Comparator<BmrFieldMetaInfo> xmlVeldVolgorde = Comparator.comparingInt(BmrFieldMetaInfo::getVolgorde);
        return new BmrGroepMetaInfo(
                clazz,
                bepaalMogelijkeElementNamen(clazz),
                childElementNaamFieldMetaInfoMap,
                childElementMetaInfoLijst.stream().sorted(xmlVeldVolgorde).collect(Collectors.toList()));
    }

    private static List<String> bepaalMogelijkeElementNamen(final Class<?> clazz) {
        final XmlElement xmlElementAnnotatie = clazz.getAnnotation(XmlElement.class);
        final XmlElementen xmlElementenAnnotatie = clazz.getAnnotation(XmlElementen.class);
        if (xmlElementAnnotatie != null) {
            return Collections.singletonList(xmlElementAnnotatie.value());
        } else if (xmlElementenAnnotatie != null) {
            try {
                final Method soortMethode = xmlElementenAnnotatie.enumType().getDeclaredMethod(ELEMENT_NAMEN_METHOD);
                return createStringList((List) soortMethode.invoke(null));
            } catch (
                    NoSuchMethodException
                            | InvocationTargetException
                            | IllegalAccessException e) {
                throw new IllegalArgumentException(
                        "De mogelijke elementnamen kunnen niet bepaald worden voor een @XmlElementen object van het type: " + clazz, e);
            }
        } else {
            throw new IllegalArgumentException(
                    "De mogelijke elementnamen kunnen niet bepaald worden omdat de @XmlElement of @XmlElementen annotatie ontbreekt op type: " + clazz);
        }
    }

    private static List<String> createStringList(final List list) {
        final List<String> result = new ArrayList<>();
        for (final Object item : list) {
            result.add((String) item);
        }
        return result;
    }

    private static List<Field> getXmlFields(final Class<?> clazz) {
        final List<Field> result = new ArrayList<>();
        if (clazz.getSuperclass() != null) {
            result.addAll(getXmlFields(clazz.getSuperclass()));
        }
        for (final Field field : clazz.getDeclaredFields()) {
            if (isXmlField(field)) {
                result.add(field);
            }
        }
        return result;
    }

    private static boolean isXmlField(final Field field) {
        return field.getAnnotation(XmlTransient.class) == null
                && (Element.class.isAssignableFrom(field.getType())
                || List.class.isAssignableFrom(field.getType()) && field.getAnnotation(XmlChildList.class) != null);
    }

    private static Map<String, BmrGroepMetaInfo> converteerNaarMap(final BmrGroepMetaInfo bmrGroepMetaInfo) {
        final Map<String, BmrGroepMetaInfo> result = new LinkedHashMap<>();
        for (final String mogelijkeElementNaam : bmrGroepMetaInfo.getMogelijkeElementNamen()) {
            result.put(mogelijkeElementNaam, bmrGroepMetaInfo);
        }
        return result;
    }
}
