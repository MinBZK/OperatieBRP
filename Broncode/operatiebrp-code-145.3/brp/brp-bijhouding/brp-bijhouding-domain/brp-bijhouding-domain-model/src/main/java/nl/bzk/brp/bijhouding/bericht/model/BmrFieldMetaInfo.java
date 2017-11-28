/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.brp.bijhouding.bericht.annotation.XmlChild;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlChildList;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Bevat de meta informatie van een veld en is onderdeel van de meta informatie van een BMR groep. De meta informatie
 * wordt op de volgende manier bepaald:
 * <ul>
 * <li>is het veld een BmrAttribuut dan bepaald het veld naam of de
 * {@link nl.bzk.brp.bijhouding.bericht.annotation.XmlChild} annotatie de elementnaam en het veld type hoort hier dan
 * bij</li>
 * <li>is het veld een BmrGroep dan bepaald de {@link nl.bzk.brp.bijhouding.bericht.annotation.XmlElement},
 * {@link nl.bzk.brp.bijhouding.bericht.annotation.XmlElementen} of
 * {@link nl.bzk.brp.bijhouding.bericht.annotation.XmlElementInterface} de mogelijke elementnamen en bijbehorende type
 * voor dit veld</li>
 * </ul>
 */
public final class BmrFieldMetaInfo {

    private static final String GET = "get";

    private final Class<?> groepClass;
    private final Field field;
    private final Method getterMethode;
    private final Map<String, Class<?>> elementNaamTypeMap;
    private final int volgorde;

    private BmrFieldMetaInfo(
            final Class<?> groepClass,
            final Field field,
            final Method getterMethode,
            final Map<String, Class<?>> elementNaamTypeMap,
            final int volgorde) {
        this.groepClass = groepClass;
        this.field = field;
        this.getterMethode = getterMethode;
        this.elementNaamTypeMap = elementNaamTypeMap;
        this.volgorde = volgorde;
    }

    /**
     * Geef de verzameling van mogelijk elementnamen voor dit veld.
     *
     * @return mogelijke elementnamen
     */
    public Collection<String> getMogelijkeElementNamen() {
        return Collections.unmodifiableCollection(elementNaamTypeMap.keySet());
    }

    /**
     * Geef het type dat hoort bij dit veld wanneer de gegeven elementnaam gebruikt wordt.
     *
     * @param elementNaam een van de mogeljke elementnamen voor dit veld
     * @return het veld type
     */
    public Class<?> getTypeVoorElementNaam(final String elementNaam) {
        return elementNaamTypeMap.get(elementNaam);
    }

    /**
     * Bepaald of het veld een een lijst type is.
     *
     * @return true als het type van field {@link List} is, anders false
     */
    public boolean isList() {
        return List.class.isAssignableFrom(field.getType());
    }

    /**
     * Geef de waarde van volgorde.
     *
     * @return volgorde
     */
    public int getVolgorde() {
        return volgorde;
    }

    /**
     * Als dit meta informatie van een lijst veld betreft dan wordt het type van de inhoud van deze lijst geretourneerd.
     * Anders wordt een fout gegeven.
     *
     * @return het type van de inhoud van de lijst
     * @throws IllegalArgumentException als dit geen {@link List} betreft of als de {@link XmlChildList} annotatie ontbreekt.
     */
    public Class<?> getListType() {
        if (!isList()) {
            throw new IllegalArgumentException(String.format(
                    "Het type van de inhoud van een lijst kan niet worden bepaald omdat veld '%s' in class '%s' geen lijst is.",
                    field.getName(),
                    groepClass));
        }
        final XmlChildList xmlChildList = field.getAnnotation(XmlChildList.class);
        if (xmlChildList == null) {
            throw new IllegalArgumentException(String.format(
                    "De @XmlChildList annotatie ontbreekt op veld '%s' in class '%s'",
                    field.getName(),
                    groepClass));
        }
        return xmlChildList.listElementType();
    }

    /**
     * Roept de getter methode aan voor het gegeven field op het gegeven object.
     *
     * @param element het element
     * @return het resultaat van de aanroep
     */
    public Object invokeGetterMethod(final Object element) {
        try {
            return getterMethode.invoke(element);
        } catch (
                InvocationTargetException
                        | IllegalAccessException
                        | IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format(
                    "Het child element kan niet worden geschreven omdat de getter methode fout gaat voor methode '%s' in element '%s'",
                    getterMethode.getName(),
                    element.getClass()), e);
        }
    }

    /**
     * Maakt een nieuw BmrFieldMetaInfo object voor het gegeven veld.
     *
     * @param field het veld waarvan de meta informatie moet worden gemaakt
     * @return BrmFieldMetaInfo
     */
    public static BmrFieldMetaInfo getInstance(final Field field) {
        final Map<String, Class<?>> elementNaamTypeMap = new LinkedHashMap<>();
        final XmlChild xmlChildAnnotatie = field.getAnnotation(XmlChild.class);
        final String xmlChildNaam = xmlChildAnnotatie != null ? xmlChildAnnotatie.naam() : "";
        final int xmlChildVolgorde = xmlChildAnnotatie != null ? xmlChildAnnotatie.volgorde() : 0;
        initialiseerElementNaamTypeMapping(elementNaamTypeMap, field, xmlChildNaam);
        return new BmrFieldMetaInfo(field.getDeclaringClass(), field, findGetterMethode(field), elementNaamTypeMap, xmlChildVolgorde);
    }

    private static void initialiseerElementNaamTypeMapping(final Map<String, Class<?>> elementNaamTypeMap, final Field field, final String xmlChildNaam) {
        if (BmrAttribuut.class.isAssignableFrom(field.getType())) {
            final String elementNaam;
            if (!"".equals(xmlChildNaam)) {
                elementNaam = xmlChildNaam;
            } else {
                elementNaam = field.getName();
            }
            elementNaamTypeMap.put(elementNaam, field.getType());
        } else if (BmrGroep.class.isAssignableFrom(field.getType())) {
            final Map<String, BmrGroepMetaInfo> childGroepMetaInfoMap = BmrGroepMetaInfo.getInstanceMap(field.getType());
            for (final Map.Entry<String, BmrGroepMetaInfo> elementEntry : childGroepMetaInfoMap.entrySet()) {
                elementNaamTypeMap.put(elementEntry.getKey(), elementEntry.getValue().getElementType());
            }
        } else if (List.class.isAssignableFrom(field.getType())) {
            final XmlChildList xmlChildListAnnotatie = field.getAnnotation(XmlChildList.class);
            final String elementNaam;
            if (xmlChildListAnnotatie != null && !"".equals(xmlChildListAnnotatie.naam())) {
                elementNaam = xmlChildListAnnotatie.naam();
            } else {
                elementNaam = field.getName();
            }
            elementNaamTypeMap.put(elementNaam, field.getType());
        }
    }

    private static Method findGetterMethode(final Field field) {
        try {
            return field.getDeclaringClass().getDeclaredMethod(getter(field.getName()));
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(String.format(
                    "Er kan geen getter methode gevonden worden voor veld '%s' in class '%s'",
                    field.getName(),
                    field.getDeclaringClass()), e);
        }
    }

    private static String getter(final String veldNaam) {
        final String eersteLetter = veldNaam.substring(0, 1);
        final String rest = veldNaam.substring(1);
        return GET + eersteLetter.toUpperCase() + rest;
    }
}
