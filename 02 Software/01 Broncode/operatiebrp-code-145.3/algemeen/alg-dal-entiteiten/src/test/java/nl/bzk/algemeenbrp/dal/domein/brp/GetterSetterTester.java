/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Entity;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AbstractDelegateBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AbstractDelegateOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AbstractDelegatePersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AbstractDelegatePersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AbstractDelegateRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AutoriteitAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DelegateBetrokkenheidTest;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DelegatePersoonTest;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DelegateRelatieTest;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.OnderzoekHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortActieBrongebruikSleutel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VoorvoegselSleutel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Enumeratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import org.junit.Assert;
import org.reflections.Reflections;

/**
 * Tester voor getter en setter methoden.
 */
public class GetterSetterTester {

    private static final Map<Class<?>, Object> VALUES = new HashMap<>();
    private static final List<Class<?>> EXCLUDES = new ArrayList<>();
    private static final List<Class<?>> DELEGATES = new ArrayList<>();
    private static final List<String> EXCLUDED_FIELDS = new ArrayList<>();

    static {
        VALUES.put(Character.class, 'c');
        VALUES.put(Character.TYPE, 'c');
        VALUES.put(String.class, "42");
        VALUES.put(Number.class, 42);
        VALUES.put(Long.class, 1234L);
        VALUES.put(Long.TYPE, 1234L);
        VALUES.put(Integer.class, 42);
        VALUES.put(Integer.TYPE, 42);
        VALUES.put(Short.class, (short) 2);
        VALUES.put(Short.TYPE, (short) 2);
        VALUES.put(Boolean.class, true);
        VALUES.put(Boolean.TYPE, true);
        VALUES.put(Timestamp.class, new Timestamp(System.currentTimeMillis()));
        VALUES.put(Date.class, new Date(System.currentTimeMillis()));
        VALUES.put(Set.class, Collections.emptySet());
        VALUES.put(List.class, Collections.emptyList());
        VALUES.put(Map.class, Collections.emptyMap());
        VALUES.put(Comparator.class, (Comparator<Object>) (o1, o2) -> 0);
        VALUES.put(AutoriteitAfgifteBuitenlandsPersoonsnummer.class, new AutoriteitAfgifteBuitenlandsPersoonsnummer("0042"));
        final byte[] byteArray = new byte[]{};
        VALUES.put(byteArray.getClass(), byteArray);
        VALUES.put(FormeleHistorie.class, new RelatieHistorie(new Relatie(SoortRelatie.HUWELIJK)));
        VALUES.put(Element.class, Element.PERSOON_NAAMGEBRUIK_ACTIEINHOUD);
        VALUES.put(
                ToegangLeveringsAutorisatie.class,
                new ToegangLeveringsAutorisatie(new PartijRol(new Partij("partij", "000000"), Rol.AFNEMER), new Leveringsautorisatie(Stelsel.GBA, false)));
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonIDHistorie persoonIDHistorie = new PersoonIDHistorie(persoon);
        persoonIDHistorie.setAdministratienummer("1234567890");
        persoon.addPersoonIDHistorie(persoonIDHistorie);
        VALUES.put(Persoon.class, persoon);
        VALUES.put(Predicaat.class, Predicaat.H);
        VALUES.put(VoorvoegselSleutel.class, new VoorvoegselSleutel('\'', "dal"));
        VALUES.put(
                SoortActieBrongebruikSleutel.class,
                new SoortActieBrongebruikSleutel(
                        SoortActie.CONVERSIE_GBA,
                        SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL,
                        new SoortDocument("Test", "test")));
        VALUES.put(Entiteit.class, new Aangever('A', "Aangever", "omschrijving"));
        final Partij partij = new Partij("bla", "000001");
        final Onderzoek onderzoek = new Onderzoek(partij, persoon);
        final OnderzoekHistorie onderzoekHistorie = new OnderzoekHistorie(20150101, StatusOnderzoek.AFGESLOTEN, onderzoek);
        final BRPActie actie = new BRPActie(
                SoortActie.CONVERSIE_GBA,
                new AdministratieveHandeling(
                        partij,
                        SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND,
                        Timestamp.from(Instant.now())), partij, Timestamp.from(Instant.now()));
        onderzoekHistorie.setActieInhoud(
                actie);
        onderzoek.addOnderzoekHistorie(onderzoekHistorie);
        VALUES.put(GegevenInOnderzoek.class, new GegevenInOnderzoek(onderzoek, Element.PERSOON_BUITENLANDSPERSOONSNUMMER_STANDAARD));
        VALUES.put(EnumSet.class, EnumSet.noneOf(Rol.class));
        VALUES.put(BRPActie.class, actie);

        // excludes
        // BMR44
        EXCLUDES.add(Dienst.class);
        EXCLUDES.add(Dienstbundel.class);
        EXCLUDES.add(DienstHistorie.class);
        EXCLUDES.add(DienstbundelHistorie.class);
        EXCLUDES.add(AbstractDelegatePersoon.class);
        EXCLUDES.add(AbstractDelegateRelatie.class);
        EXCLUDES.add(AbstractDelegateBetrokkenheid.class);
        EXCLUDES.add(AbstractDelegateOnderzoek.class);
        EXCLUDES.add(AbstractDelegatePersoonNationaliteit.class);
        EXCLUDES.add(DelegatePersoonTest.class);
        EXCLUDES.add(DelegateRelatieTest.class);
        EXCLUDES.add(DelegateBetrokkenheidTest.class);
        EXCLUDES.add(DelegatePersoonTest.SimpleDelegatePersoon.class);
        // BMR52
        EXCLUDES.add(AdministratieveHandeling.class);
        // Delegates (sla controle field over)
        DELEGATES.add(AbstractDelegatePersoon.class);
        DELEGATES.add(AbstractDelegateRelatie.class);
        DELEGATES.add(AbstractDelegateBetrokkenheid.class);
        DELEGATES.add(AbstractDelegateOnderzoek.class);
        DELEGATES.add(AbstractDelegatePersoonNationaliteit.class);

        // methodes die extra controles uitvoeren mbt de gegeven parameter waardoor deze lastig met defaults te testen zijn
        EXCLUDED_FIELDS.add("code");
        EXCLUDED_FIELDS.add("administratienummer");
        EXCLUDED_FIELDS.add("volgendeAdministratienummer");
        EXCLUDED_FIELDS.add("vorigeAdministratienummer");
        EXCLUDED_FIELDS.add("burgerservicenummer");
        EXCLUDED_FIELDS.add("volgendeBurgerservicenummer");
        EXCLUDED_FIELDS.add("vorigeBurgerservicenummer");
        EXCLUDED_FIELDS.add("anummer");
    }

    /**
     * Test entities in package.
     * @param packageName package
     */
    public void testEntities(final String packageName) throws ReflectiveOperationException {
        final Reflections reflections = new Reflections(packageName);
        final Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Entity.class);

        if (classes.isEmpty()) {
            throw new IllegalStateException("Geen entiteiten gevonden");
        }

        for (final Class<?> clazz : classes) {
            if (EXCLUDES.contains(clazz)) {
                continue;
            }
            final Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            for (final Constructor<?> constructor : constructors) {
                constructor.setAccessible(true);

                final Object[] arguments = new Object[constructor.getParameterTypes().length];
                for (int i = 0; i < constructor.getParameterTypes().length; i++) {
                    arguments[i] = generateValue(constructor.getParameterTypes()[i], constructor);
                }

                try {
                    final Object object = constructor.newInstance(arguments);
                    test(object);
                    testKopieConstructor(object);
                } catch (InvocationTargetException ita) {
                    if (ita.getCause() instanceof IllegalArgumentException) {
                        // negeer extra validaties in constructor
                    } else {
                        throw ita;
                    }
                }
            }
        }
    }

    private void testKopieConstructor(final Object object) throws ReflectiveOperationException {
        if (MaterieleHistorie.class.isAssignableFrom(object.getClass())) {
            final Constructor<?> kopieConstructor = object.getClass().getDeclaredConstructor(object.getClass());
            kopieConstructor.setAccessible(true);
            Object kopie = kopieConstructor.newInstance(object);
            for (final String property : new TreeSet<>(bepaalPropertiesObvFields(object))) {
                final Field field = findField(object.getClass(), property);
                if (field != null && !Modifier.isStatic(field.getModifiers())) {
                    field.setAccessible(true);
                    if ("id".equals(field.getName())) {
                        assertNotNull(field.get(object));
                        assertNull(field.get(kopie));
                    } else {
                        final String message = String.format("het field %s is niet gelijk na het kopieren voor type: %s", field.getName(), object.getClass());
                        if (Short.TYPE.equals(field.getType())) {
                            assertTrue(message, field.getShort(object) == field.getShort(kopie));
                        } else if (Integer.TYPE.equals(field.getType())) {
                            assertTrue(message, field.getInt(object) == field.getInt(kopie));
                        } else if (Long.TYPE.equals(field.getType())) {
                            assertTrue(message, field.getLong(object) == field.getLong(kopie));
                        } else if (Boolean.TYPE.equals(field.getType())) {
                            assertTrue(message, field.getBoolean(object) == field.getBoolean(kopie));
                        } else if (Float.TYPE.equals(field.getType())) {
                            assertTrue(message, field.getFloat(object) == field.getFloat(kopie));
                        } else if (Double.TYPE.equals(field.getType())) {
                            assertTrue(message, field.getDouble(object) == field.getDouble(kopie));
                        } else if (Byte.TYPE.equals(field.getType())) {
                            assertTrue(message, field.getByte(object) == field.getByte(kopie));
                        } else {
                            final Object bronObject = field.get(object);
                            final Object doelObject = field.get(kopie);
                            if (bronObject instanceof java.util.Date || bronObject instanceof Map) {
                                assertTrue(message, bronObject.equals(doelObject));
                            } else {
                                assertTrue(message, bronObject == doelObject);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Test het object (op alle mogelijke manieren {@link #bepaalPropertiesObvFields(Object)}).
     * @param objectToTest object om te testen
     * @throws ReflectiveOperationException bij fouten
     */
    public void test(final Object objectToTest) throws ReflectiveOperationException {
        for (final String property : new TreeSet<>(bepaalPropertiesObvFields(objectToTest))) {
            if (property.endsWith("Id")) {
                testEnumField(property, objectToTest);
            } else {
                test(property, objectToTest);
            }
        }
    }

    /**
     * Bepaal properties obv fields in klassen.
     * @param objectToTest objcet om te testen
     * @return lisjt van properties
     */
    private Set<String> bepaalPropertiesObvFields(final Object objectToTest) {
        final Set<String> result = new TreeSet<>();

        Class<?> clazz = objectToTest.getClass();
        while (clazz != null) {
            for (final Field field : clazz.getDeclaredFields()) {
                if (!field.isSynthetic()) {
                    result.add(field.getName());
                }
            }

            clazz = clazz.getSuperclass();
        }

        return result;
    }

    private void testEnumField(final String property, final Object objectToTest)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        final Field field = findField(objectToTest.getClass(), property);
        final String partialMethodName = Character.toUpperCase(property.charAt(0)) + property.substring(1, property.indexOf("Id"));
        final Method setter = findMethod(objectToTest.getClass(), "set" + partialMethodName, 1);
        final Method getter = findMethod(objectToTest.getClass(), "get" + partialMethodName, 0);

        if (field != null) {
            final boolean isPrimitiveType = field.getType().isPrimitive();
            field.setAccessible(true);
            checkEnumEquals(null, field.get(objectToTest), isPrimitiveType);

            Object valueToSet = null;
            if (setter != null) {
                valueToSet = generateValue(setter.getParameterTypes()[0], setter);
                setter.invoke(objectToTest, valueToSet);

                final Object result = field.get(objectToTest);
                if (!isDelegateClass(objectToTest)) {
                    checkEnumEquals(valueToSet, result, isPrimitiveType);
                }
            }

            if (getter != null) {
                final Object value = field.get(objectToTest);
                final Object result = getter.invoke(objectToTest);
                if (!isDelegateClass(objectToTest)) {
                    checkEnumEquals(value, result, isPrimitiveType);
                } else if (setter != null) {
                    checkEnumEquals(valueToSet, result, isPrimitiveType);
                }
            }
        }
    }

    /**
     * Test gebaseerd op property naam (setProperty, getProperty/isProperty, property).
     * @param property property naam
     * @param objectToTest object om te testen
     * @throws ReflectiveOperationException bij fouten
     */
    private void test(final String property, final Object objectToTest) throws ReflectiveOperationException {
        final Field field = findField(objectToTest.getClass(), property);
        final Method setter = findMethod(objectToTest.getClass(), "set" + Character.toUpperCase(property.charAt(0)) + property.substring(1), 1);
        Method getter = findMethod(objectToTest.getClass(), "get" + Character.toUpperCase(property.charAt(0)) + property.substring(1), 0);
        if (getter == null) {
            getter = findMethod(objectToTest.getClass(), "is" + Character.toUpperCase(property.charAt(0)) + property.substring(1), 0);
        }

        final String meldingTekst =
                String.format("Waardes uit %s#%s zijn niet gelijk%n", objectToTest.getClass().getSimpleName(), field == null ? "" : field.getName());

        if (field != null) {
            field.setAccessible(true);
            Object valueToSet = generateValue(field.getType(), field);
            if (setter != null && field.getType().isAssignableFrom(setter.getParameterTypes()[0]) && !EXCLUDED_FIELDS.contains(field.getName())) {
                setter.setAccessible(true);

                setter.invoke(objectToTest, valueToSet);

                final Object result = field.get(objectToTest);
                if (!isDelegateClass(objectToTest)) {
                    checkEquals(meldingTekst, valueToSet, result);
                }
            }
            if (getter != null && field.getType().isAssignableFrom(getter.getReturnType()) && !EXCLUDED_FIELDS.contains(field.getName())) {
                getter.setAccessible(true);
                final Object value = field.get(objectToTest);

                final Object result = getter.invoke(objectToTest);

                if (!isDelegateClass(objectToTest)) {
                    checkEquals(meldingTekst, value, result);
                } else if (setter != null && field.getType().isAssignableFrom(setter.getParameterTypes()[0])) {
                    checkEquals(meldingTekst, valueToSet, result);
                    if (field.getType().isPrimitive()) {
                        checkEquals("field moet null zijn want delegate moet gebruikt worden.", value, null);
                    }
                }
            }
        }

        if (setter != null && getter != null && getter.getReturnType().isAssignableFrom(setter.getParameterTypes()[0]) && !EXCLUDED_FIELDS
                .contains(field.getName())) {
            setter.setAccessible(true);
            final Object value = generateValue(setter.getParameterTypes()[0], setter);
            setter.invoke(objectToTest, value);

            getter.setAccessible(true);
            final Object result = getter.invoke(objectToTest);
            checkEquals(meldingTekst, value, result);
        }
    }

    private boolean isDelegateClass(final Object objectToTest) {
        for (Class<?> delegateClass : DELEGATES) {
            if (delegateClass.isAssignableFrom(objectToTest.getClass())) {
                return true;
            }
        }
        return false;
    }

    private void checkEnumEquals(final Object value, final Object result, final boolean isPrimitiveType) {
        if (value == null) {
            assertTrue(result == null || isPrimitiveType);
        } else if (value.getClass().isEnum() && result.getClass().isEnum()) {
            assertEquals(((Enumeratie) value).getId(), ((Enumeratie) result).getId());
        } else if (value.getClass().isEnum()) {
            assertEquals(((Enumeratie) value).getId(), result);
        } else if (result.getClass().isEnum()) {
            assertEquals(value, ((Enumeratie) result).getId());
        }
    }

    private void checkEquals(final String meldingTekst, final Object value, final Object result) {
        if (value == null) {
            assertNull(meldingTekst, result);
        } else if (value.getClass().isArray()) {
            if (value.getClass().getComponentType().equals(Byte.TYPE)) {
                Assert.assertArrayEquals(meldingTekst, (byte[]) value, (byte[]) result);
            } else {
                Assert.assertArrayEquals(meldingTekst, (Object[]) value, (Object[]) result);
            }
        } else {
            assertEquals(meldingTekst, value, result);
        }
    }

    private Field findField(final Class<?> clazz, final String property) {
        try {
            return clazz.getDeclaredField(property);
        } catch (final NoSuchFieldException e) {
            final Class<?> superClazz = clazz.getSuperclass();
            return superClazz == null ? null : findField(superClazz, property);
        }
    }

    private Method findMethod(final Class<?> clazz, final String name, final int numberOfArguments) {
        for (final Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(name) && method.getParameterTypes().length == numberOfArguments) {
                return method;
            }
        }

        final Class<?> superClazz = clazz.getSuperclass();
        return superClazz == null ? null : findMethod(superClazz, name, numberOfArguments);
    }

    private Object generateValue(final Class<?> type, final AccessibleObject accessible) {
        if (VALUES.containsKey(type)) {
            return VALUES.get(type);
        } else if (type.isEnum()) {
            final Object[] values = type.getEnumConstants();
            return values[0];
        } else {
            try {
                final Constructor<?> constructor = type.getDeclaredConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
            } catch (final ReflectiveOperationException e) {
            }

            throw new IllegalArgumentException("Kan geen waarde genereren voor type " + type.getName() + " voor " + accessible);
        }
    }
}
