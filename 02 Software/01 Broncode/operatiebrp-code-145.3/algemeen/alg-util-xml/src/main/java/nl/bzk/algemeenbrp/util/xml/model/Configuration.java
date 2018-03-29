/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.xml.annotation.Attribute;
import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.algemeenbrp.util.xml.annotation.ElementList;
import nl.bzk.algemeenbrp.util.xml.annotation.ElementMap;
import nl.bzk.algemeenbrp.util.xml.annotation.Ignore;
import nl.bzk.algemeenbrp.util.xml.annotation.Text;
import nl.bzk.algemeenbrp.util.xml.exception.ConfigurationException;
import nl.bzk.algemeenbrp.util.xml.util.ReflectionUtils;
import nl.bzk.algemeenbrp.util.xml.util.Utils;

/**
 * Configuratie.
 */
public final class Configuration {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final Set<Class<?>> ANNOTATIONS =
            new HashSet<>(Arrays.asList(Attribute.class, Element.class, ElementList.class, ElementMap.class, Text.class, Ignore.class));

    private final Map<Class<?>, XmlObject<?>> elementen = new HashMap<>();
    private final Map<Class<?>, Root<?>> roots = new HashMap<>();

    /**
     * Geef het model voor de gegeven klasse.
     * @param clazz klasse
     * @param <T> type van de klasse
     * @return model
     * @throws ConfigurationException bij configuratie fouten
     */
    public <T> Root<T> getModelFor(final Class<T> clazz) throws ConfigurationException {
        if (!roots.containsKey(clazz)) {
            roots.put(clazz, buildRootFor(clazz));
        }
        return (Root<T>) roots.get(clazz);
    }

    private <T> Root<T> buildRootFor(final Class<T> clazz) throws ConfigurationException {
        final XmlObject<T> element = getXmlObjectFor(clazz);

        final nl.bzk.algemeenbrp.util.xml.annotation.Root rootAnnotation = clazz.getAnnotation(nl.bzk.algemeenbrp.util.xml.annotation.Root.class);
        return new Root<>(element, Utils.name(rootAnnotation == null ? null : rootAnnotation.name(), clazz));
    }

    /**
     * Geef de configuratie voor een gegeven klasse.
     * @param clazz klasse
     * @param <T> type van de klasse
     * @return configuratie
     * @throws ConfigurationException bij configuratie fouten
     */
    public <T> XmlObject<T> getXmlObjectFor(final Class<T> clazz) throws ConfigurationException {
        if (!elementen.containsKey(clazz)) {
            elementen.put(clazz, buildXmlObjectFor(clazz));
        }
        return (XmlObject<T>) elementen.get(clazz);
    }

    /**
     * Reset alle configuratie.
     */
    public void reset() {
        elementen.clear();
    }

    /**
     * Registreer een configuratie voor een gegeven klasse.
     * @param clazz klasse
     * @param xmlObject configuratie
     * @param <T> type van de klasse
     */
    public <T> void registerXmlObjectFor(final Class<T> clazz, final XmlObject<T> xmlObject) {
        elementen.put(clazz, xmlObject);
    }

    private static <T> XmlObject<T> buildXmlObjectFor(final Class<T> clazz) throws ConfigurationException {
        LOGGER.debug("buildElementFor({})", clazz);
        final Map<String, MemberData> memberData = collectMemberData(clazz);

        if (memberData.isEmpty()) {
            return new SimpleObject<>(clazz);
        } else {
            // Composite
            final Constructor<T> constructor = determineConstructor(clazz, memberData);
            final java.util.List<Child<?>> children = buildChildren(memberData);
            return new CompositeObject<>(clazz, constructor, children);
        }
    }

    private static <T> Constructor<T> determineConstructor(final Class<T> clazz, final Map<String, MemberData> memberData) {
        Constructor<T> constructor = null;
        for (final MemberData member : memberData.values()) {
            if (member.constructor != null) {
                constructor = (Constructor<T>) member.constructor;
            }
        }

        if (constructor == null) {
            constructor = getDefaultConstructor(clazz);
        }

        return constructor;
    }

    private static <T> Constructor<T> getDefaultConstructor(final Class<T> clazz) {
        for (final Constructor<T> constructor : (Constructor<T>[]) clazz.getDeclaredConstructors()) {
            if (constructor.getParameterTypes().length == 0) {
                return constructor;
            }
        }

        return null;
    }

    private static java.util.List<Child<?>> buildChildren(final Map<String, MemberData> memberData) throws ConfigurationException {
        final java.util.List<Child<?>> children = new ArrayList<>();
        for (final Map.Entry<String, MemberData> member : memberData.entrySet()) {
            if (isAttributeMember(member.getValue())) {
                children.add(
                        buildAttribute(
                                member.getValue().clazz,
                                member.getKey(),
                                member.getValue().field,
                                member.getValue().getter,
                                member.getValue().setter,
                                member.getValue().constructorIndex));
            }
            if (isElementMember(member.getValue())) {
                children.add(
                        buildElementChild(
                                member.getValue().clazz,
                                member.getKey(),
                                member.getValue().field,
                                member.getValue().getter,
                                member.getValue().setter,
                                member.getValue().constructorIndex));
            }
            if (isElementListMember(member.getValue())) {
                final Class<? extends Collection<?>> memberClazz = (Class<? extends Collection<?>>) member.getValue().clazz;
                children.add(
                        buildListChild(
                                memberClazz,
                                member.getKey(),
                                member.getValue().field,
                                member.getValue().getter,
                                member.getValue().setter,
                                member.getValue().constructorIndex));
            }
            if (isElementMapMember(member.getValue())) {
                final Class<? extends Map<?, ?>> memberClazz = (Class<? extends Map<?, ?>>) member.getValue().clazz;
                children.add(
                        buildMapChild(
                                memberClazz,
                                member.getKey(),
                                member.getValue().field,
                                member.getValue().getter,
                                member.getValue().setter,
                                member.getValue().constructorIndex));
            }
            if (isTextMember(member.getValue())) {
                children.add(
                        buildTextChild(
                                member.getValue().clazz,
                                member.getKey(),
                                member.getValue().field,
                                member.getValue().getter,
                                member.getValue().setter,
                                member.getValue().constructorIndex));
            }
        }

        // Debug
        Collections.sort(children, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        return children;
    }

    private static <T> AttributeChild<T> buildAttribute(
            final Class<T> clazz,
            final String name,
            final Field field,
            final Method getter,
            final Method setter,
            final Integer constructorIndex) {
        final Accessor<T> accessor = new Accessor<>(clazz, field, getter, setter, constructorIndex);
        return new AttributeChild<>(accessor, name);

    }

    private static <T> ElementChild<T> buildElementChild(
            final Class<T> clazz,
            final String name,
            final Field field,
            final Method getter,
            final Method setter,
            final Integer constructorIndex) {
        final Accessor<T> accessor = new Accessor<>(clazz, field, getter, setter, constructorIndex);
        return new ElementChild<>(accessor, Utils.name(name, clazz));
    }

    private static <T extends Collection> ListChild<T> buildListChild(
            final Class<T> clazz,
            final String name,
            final Field field,
            final Method getter,
            final Method setter,
            final Integer constructorIndex) {
        final Accessor<T> accessor = new Accessor<>(clazz, field, getter, setter, constructorIndex);

        ElementList listAnnotation =
                field == null ? null : field.getAnnotation(ElementList.class);
        if (listAnnotation == null) {
            listAnnotation = getter.getAnnotation(ElementList.class);
        }

        return new ListChild<>(accessor, name, listAnnotation.inline(), listAnnotation.type(), Utils.name(listAnnotation.entry(), listAnnotation.type()));
    }

    private static <T extends Map<?, ?>> MapChild<T> buildMapChild(
            final Class<T> clazz,
            final String name,
            final Field field,
            final Method getter,
            final Method setter,
            final Integer constructorIndex)  {
        final Accessor<T> accessor = new Accessor<>(clazz, field, getter, setter, constructorIndex);

        ElementMap mapAnnotation =
                field == null ? null : field.getAnnotation(ElementMap.class);
        if (mapAnnotation == null) {
            mapAnnotation = getter.getAnnotation(ElementMap.class);
        }

        return new MapChild<>(
                accessor,
                name,
                mapAnnotation.inline(),
                Utils.name(mapAnnotation.entry(), Map.class),
                Utils.name(mapAnnotation.key(), mapAnnotation.keyType()),
                mapAnnotation.keyType(),
                Utils.name(mapAnnotation.value(), mapAnnotation.valueType()),
                mapAnnotation.valueType(),
                mapAnnotation.attribute());
    }

    private static <T> Child<T> buildTextChild(
            final Class<T> clazz,
            final String name,
            final Field field,
            final Method getter,
            final Method setter,
            final Integer constructorIndex) {
        final Accessor<T> accessor = new Accessor<>(clazz, field, getter, setter, constructorIndex);
        return new TextChild<>(accessor, Utils.name(name, clazz), clazz);
    }

    private static Map<String, MemberData> collectMemberData(final Class<?> clazz) {
        final Map<String, MemberData> memberData = new LinkedHashMap<>();

        collectMemberDataForFields(clazz, memberData);
        collectMemberDataForGetters(clazz, memberData);
        collectMemberDataForSetters(clazz, memberData);
        collectMemberDataForConstructor(clazz, memberData);

        return memberData;
    }

    private static void collectMemberDataForConstructor(final Class<?> clazz, final Map<String, MemberData> memberData) {
        Constructor<?> annotatedConstructor = null;
        for (final Constructor<?> constuctor : clazz.getDeclaredConstructors()) {
            if (isFullyAnnotated(constuctor)
                    && (annotatedConstructor == null || annotatedConstructor.getParameterTypes().length < constuctor.getParameterTypes().length)) {
                annotatedConstructor = constuctor;
            }
        }

        if (annotatedConstructor != null) {
            final Parameter[] parameters = annotatedConstructor.getParameters();
            for (int parameterIndex = 0; parameterIndex < parameters.length; parameterIndex++) {
                final Parameter parameter = parameters[parameterIndex];
                final String name = getNameFromMemberAnnotation(parameter);
                LOGGER.debug("constructor member '{}' -> ", name, parameter);
                getMemberData(memberData, name).constructor = annotatedConstructor;
                getMemberData(memberData, name).constructorIndex = parameterIndex;
            }
        }
    }

    private static void collectMemberDataForSetters(final Class<?> clazz, final Map<String, MemberData> memberData) {
        for (final Method method : ReflectionUtils.getAllSetters(clazz)) {
            final String name = getNameFromMemberAnnotation(method);
            LOGGER.debug("setter member '{}' -> ", name, method);
            if (name != null) {
                getMemberData(memberData, name).clazz = method.getParameters()[0].getType();
                getMemberData(memberData, name).setter = method;
            }
        }
    }

    private static void collectMemberDataForGetters(final Class<?> clazz, final Map<String, MemberData> memberData) {
        for (final Method method : ReflectionUtils.getAllGetters(clazz)) {
            final String name = getNameFromMemberAnnotation(method);
            LOGGER.debug("getter member '{}' -> ", name, method);
            if (name != null) {
                getMemberData(memberData, name).clazz = method.getReturnType();
                getMemberData(memberData, name).getter = method;
            }
        }
    }

    private static void collectMemberDataForFields(final Class<?> clazz, final Map<String, MemberData> memberData) {
        for (final Field field : ReflectionUtils.getAllFields(clazz)) {
            final String name = getNameFromMemberAnnotation(field);
            LOGGER.debug("field member '{}' -> ", name, field);
            if (name != null) {
                getMemberData(memberData, name).clazz = field.getType();
                getMemberData(memberData, name).field = field;
            }
        }
    }

    private static boolean isFullyAnnotated(final Constructor<?> constuctor) {
        return Arrays.stream(constuctor.getParameterAnnotations()).allMatch(Configuration::hasAnnotation);
    }

    private static boolean hasAnnotation(final Annotation[] annotations) {
        return Arrays.stream(annotations).anyMatch(Configuration::isInAnnotationSet);
    }

    private static boolean isInAnnotationSet(final Annotation annotation) {
        return ANNOTATIONS.stream().anyMatch(e -> e.isAssignableFrom(annotation.getClass()));
    }

    private static String getNameFromMemberAnnotation(final AnnotatedElement field) {
        final String result;
        if (field.isAnnotationPresent(Attribute.class)) {
            result = Utils.name(field.getAnnotation(Attribute.class).name(), field);
        } else if (field.isAnnotationPresent(Element.class)) {
            result = Utils.name(field.getAnnotation(Element.class).name(), field);
        } else if (field.isAnnotationPresent(ElementList.class)) {
            result = Utils.name(field.getAnnotation(ElementList.class).name(), field);
        } else if (field.isAnnotationPresent(ElementMap.class)) {
            result = Utils.name(field.getAnnotation(ElementMap.class).name(), field);
        } else if (field.isAnnotationPresent(Text.class)) {
            result = "**text**";
        } else {
            result = null;
        }
        return result;
    }

    /* *********************************************** */
    /* *********************************************** */
    /* *********************************************** */
    /* *********************************************** */
    /* *********************************************** */

    private static MemberData getMemberData(final Map<String, MemberData> memberData, final String name) {
        if (!memberData.containsKey(name)) {
            memberData.put(name, new MemberData());
        }
        return memberData.get(name);
    }

    private static boolean isAttributeMember(final MemberData value) {
        return value.field != null && value.field.isAnnotationPresent(Attribute.class)
                || value.getter != null && value.getter.isAnnotationPresent(Attribute.class);
    }

    private static boolean isElementMember(final MemberData value) {
        return value.field != null && value.field.isAnnotationPresent(Element.class)
                || value.getter != null && value.getter.isAnnotationPresent(Element.class);
    }

    private static boolean isElementListMember(final MemberData value) {
        return value.field != null && value.field.isAnnotationPresent(ElementList.class)
                || value.getter != null && value.getter.isAnnotationPresent(ElementList.class);
    }

    private static boolean isElementMapMember(final MemberData value) {
        return value.field != null && value.field.isAnnotationPresent(ElementMap.class)
                || value.getter != null && value.getter.isAnnotationPresent(ElementMap.class);
    }

    private static boolean isTextMember(final MemberData value) {
        return value.field != null && value.field.isAnnotationPresent(Text.class)
                || value.getter != null && value.getter.isAnnotationPresent(Text.class);
    }

    /**
     * Member data.
     */
    private static final class MemberData {
        private Class<?> clazz;
        private Field field;
        private Method getter;
        private Method setter;
        private Constructor<?> constructor;
        private Integer constructorIndex;
    }

}
