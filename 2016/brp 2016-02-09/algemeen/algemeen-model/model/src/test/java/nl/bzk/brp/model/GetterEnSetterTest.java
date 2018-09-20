/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.operationeel.ber.BerichtParametersGroepModel;
import org.junit.Assert;
import org.junit.Test;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;


@SuppressWarnings("rawtypes")
public class GetterEnSetterTest {

    private static final Logger LOGGER       = LoggerFactory.getLogger();
    private static final String ROOT_PACKAGE = "nl.bzk.brp.model";

    private static final Map<Class, Object> VALUES = new HashMap<>();

    static {
        VALUES.put(Boolean.class, Boolean.TRUE);
        VALUES.put(Integer.class, 6);
        VALUES.put(Long.class, 5L);
        VALUES.put(String.class, "Test");
        VALUES.put(BigInteger.class, BigInteger.TEN);
        VALUES.put(Calendar.class, Calendar.getInstance());
        VALUES.put(Set.class, new HashSet());
        VALUES.put(List.class, new ArrayList());

        VALUES.put(BigDecimal.class, BigDecimal.ONE);
        VALUES.put(Short.class, (short) 4);
        VALUES.put(Byte.class, new Byte("42"));
        VALUES.put(byte[].class, new byte[2]);
        VALUES.put(Date.class, new Date());
    }

    /**
     * Test alle getters van entity classes op het ophalen van een standaard waarde voor het betreffende veld en <code>null</code>. Hiervoor worden alle
     * entities binnen het opgegeven root package opgehaald en wordt gekeken voor welke entity velden er ook getter methodes bestaan. Voor deze getter
     * methodes wordt eerst het veld op een standaard waarde gezet, waarna de betreffende getter methode wordt aangeroepen en gekeken wordt of dezelfde
     * waarde dan wordt geretourneerd. Daarna wordt hetzelfde gedaan, maar dan wordt het veld op <code> null</code> gezet en gekeken of de getter ook
     * <code>null</code> retourneerd.
     */
    @Test
    public void testEntityGetters()
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, URISyntaxException, IOException,
        ClassNotFoundException
    {
        final List<Class> classes = getClassesInPackage(ROOT_PACKAGE);
        final List<Class> entities = filterClassesOpEntities(classes);

        testModelGetters(entities);
    }

    /**
     * Test alle getters van logisch model classes op het ophalen van een standaard waarde voor het betreffende veld en <code>null</code>. Hiervoor worden
     * alle classes binnen het logisch model package opgehaald en wordt gekeken voor welke velden er ook getter methodes bestaan. Voor deze getter methodes
     * wordt eerst het veld op een standaard waarde gezet, waarna de betreffende getter methode wordt aangeroepen en gekeken wordt of dezelfde waarde dan
     * wordt geretourneerd. Daarna wordt hetzelfde gedaan, maar dan wordt het veld op <code> null</code> gezet en gekeken of de getter ook
     * <code>null</code> retourneerd.
     */
    @Test
    public void testLogischModelGetters()
        throws URISyntaxException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException,
        IllegalAccessException
    {
        final List<Class> interfaces = getClassesInPackage(ROOT_PACKAGE + ".logisch");
        final List<Class> alleKlasses = getClassesInPackage(ROOT_PACKAGE);

        final List<Class> implementatiesVanInterfaces = getImplementatiesVanInterfaces(interfaces, alleKlasses);

        testModelGetters(implementatiesVanInterfaces);
    }

    /**
     * Test alle getters van de opgegeven classes op het ophalen van een standaard waarde voor het betreffende veld en <code>null</code>. Hiervoor wordt
     * gekeken voor welke velden er ook getter methodes bestaan. Voor deze getter methodes wordt eerst het veld op een standaard waarde gezet, waarna de
     * betreffende getter methode wordt aangeroepen en gekeken wordt of dezelfde waarde dan wordt geretourneerd. Daarna wordt hetzelfde gedaan, maar dan
     * wordt het veld op <code>null</code> gezet en gekeken of de getter ook <code>null</code> retourneert.
     */
    private void testModelGetters(final List<Class> classes)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        for (final Class clazz : classes) {
            final Object obj;
            final Constructor[] cons = clazz.getConstructors();
            if ((cons != null && cons.length > 0) && (!Modifier.isAbstract(clazz.getModifiers()))) {
                // abstract classes doesn't have constructors, skip these.
                try {
                    obj = creeerEntityInstantie(clazz);
                } catch (final InstantiationException e) {
                    LOGGER.error("Failed to create instance ofTAbstr " + clazz.getName() + " error " + e.getMessage());
                    throw e;
                }
                final List<Field> inheritedFields = getInheritedFields(clazz);
                final Field[] entityVelden = new Field[inheritedFields.size()];
                inheritedFields.toArray(entityVelden);

                for (final Field entityVeld : entityVelden) {
                    final Method getterMethode = getGetterMethodeVoorVeld(entityVeld);

                    if (getterMethode != null && isTestbareGetterMethodeVoorVeld(getterMethode, entityVeld)) {
                        entityVeld.setAccessible(true);

                        // Test retourneren van een standaard waarde
                        final Object waarde = getStandaardWaardeVoorVeld(entityVeld);
                        entityVeld.set(obj, waarde);
                        if (waarde instanceof Set || waarde instanceof List || waarde instanceof Date) {
                            // Voor Collecties checken we alleen op equal, daar deze regelmatig niet het veld
                            // retourneren, maar een onaanpasbare (unmodifiable) versie van het veld. Voor Dates doen we
                            // dit ook, daar daar de setter en getter vaak met clones werken.
                            assertEquals(waarde, getterMethode.invoke(obj));
                        } else if (clazz.equals(BerichtParametersGroepModel.class) && getterMethode.getName().equals("getPeilmomentMaterieelSelectie")) {
                            //overslaan, gooit een exceptie.
                        } else if (getterMethode.getName().equals("getWaarde")) {
                            // Voor getWaarde moeten we bepalen wat voor type de waarde heeft. Aangezien de boven gezette
                            // waarde voor dit type niet klopt doen we het hier nogmaals.
                            final Type genericSuperclass = bepaalGenericSuperClass(clazz);
                            if (genericSuperclass != null) {
                                final Type typeVanWaarde = ((ParameterizedTypeImpl) genericSuperclass).getActualTypeArguments()[0];

                                //Bepaal en zet een nieuwe waarde
                                final Object waardeVoorWaardeVeld;
                                if (((Class) typeVanWaarde).isEnum()) {
                                    waardeVoorWaardeVeld = ((Class) typeVanWaarde).getEnumConstants()[0];
                                } else if (VALUES.containsKey(typeVanWaarde)) {
                                    waardeVoorWaardeVeld = VALUES.get(typeVanWaarde);
                                } else {
                                    waardeVoorWaardeVeld = creeerEntityInstantie((Class) typeVanWaarde);
                                }
                                entityVeld.set(obj, waardeVoorWaardeVeld);
                                Assert.assertSame(waardeVoorWaardeVeld, getterMethode.invoke(obj));
                            } else {
                                Assert.assertSame(waarde, getterMethode.invoke(obj));
                            }
                        } else {
                            if (Modifier.isPublic(getterMethode.getModifiers())) {
                                Assert.assertSame(waarde, getterMethode.invoke(obj));
                            }
                        }

                        // Test retourneren van null waarde
                        entityVeld.set(obj, null);
                        if (entityVeld.getName().equals("objectSleutel")) {
                            //Objectsleutels retourneren soms X als ze null zijn
                            final Object objectSleutelWaarde = getterMethode.invoke(obj);
                            assertTrue(objectSleutelWaarde == null || objectSleutelWaarde.equals("X"));
                        } else if (clazz.equals(BerichtParametersGroepModel.class) && getterMethode.getName().equals("getPeilmomentMaterieelSelectie")) {
                            //overslaan, gooit een exceptie.
                        } else {
                            if (Modifier.isPublic(getterMethode.getModifiers())) {
                                Assert.assertNull(getterMethode.invoke(obj));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Bepaalt de generic superclass, dit doen we om te bepalen wat voor type waarde attributen hebben.
     *
     * @param clazz De klasse.
     * @return Het type van de waarde.
     */
    private Type bepaalGenericSuperClass(final Class clazz) {
        final Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof Class) {
            return bepaalGenericSuperClass((Class) genericSuperclass);
        }
        return genericSuperclass;
    }

    /**
     * Bepaalt alle velden, inclusief de velden in eventuele superklasses.
     *
     * @param type De klasse.
     * @return De velden.
     */
    private List<Field> getInheritedFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }

    /**
     * Test alle getters van entity classes op het ophalen van een standaard waarde voor het betreffende veld en <code>null</code>. Hiervoor worden alle
     * entities binnen het opgegeven root package opgehaald en wordt gekeken voor welke entity velden er ook getter methodes bestaan. Voor deze getter
     * methodes wordt eerst het veld op een standaard waarde gezet, waarna de betreffende getter methode wordt aangeroepen en gekeken wordt of dezelfde
     * waarde dan wordt geretourneerd. Daarna wordt hetzelfde gedaan, maar dan wordt het veld op <code> null</code> gezet en gekeken of de getter ook
     * <code>null</code> retourneerd.
     */
    @Test
    public void testEnumGetters()
        throws URISyntaxException, IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        final List<Class> classes = getClassesInPackage(ROOT_PACKAGE);
        final List<Class> enums = filterClassesOpEnums(classes);

        for (final Class clazz : enums) {
            final Object obj = clazz.getEnumConstants()[0];
            final Field[] enumVelden = clazz.getDeclaredFields();
            for (final Field enumVeld : enumVelden) {
                final Method getterMethode = getGetterMethodeVoorVeld(enumVeld);
                if (getterMethode != null && isTestbareGetterMethodeVoorVeld(getterMethode, enumVeld)) {
                    enumVeld.setAccessible(true);
                    Assert.assertSame(enumVeld.get(obj), getterMethode.invoke(obj));
                }
            }
        }
    }

    @Test
    public void testGettersAlleTestbareKlassen()
        throws URISyntaxException, IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException,
        InstantiationException
    {
        final List<Class> classes = getClassesInPackage(ROOT_PACKAGE);
        final List<Class> teCheckenKlassen = geefTestbareKlassen(classes);

        testModelGetters(teCheckenKlassen);
    }

    @Test
    public void testSettersAlleTestbareKlassen()
        throws URISyntaxException, IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException,
        InstantiationException
    {
        final List<Class> classes = getClassesInPackage(ROOT_PACKAGE);
        final List<Class> teCheckenKlassen = geefTestbareKlassen(classes);

        testModelSetters(teCheckenKlassen);
    }

    private List<Class> geefTestbareKlassen(final List<Class> classes) {
        final List<Class> teCheckenKlassen = new ArrayList<>();
        for (final Class clazz : classes) {
            //Geen testklassen, geen builders, geen views en geen predikaten (geen standaard-getters).
            //Daarnaast een drietal uitsluitingen omdat die geen null-waardes in de constructor aan kunnen.
            if (AdministratieveHandelingSynchronisatie.class.equals(clazz)
                || FormeleHistorieSetImpl.class.equals(clazz)
                || MaterieleHistorieSetImpl.class.equals(clazz)
                || clazz.getName().toLowerCase().contains("test")
                || clazz.getName().toLowerCase().contains("builder")
                || clazz.getName().toLowerCase().contains("view")
                || clazz.getName().toLowerCase().contains("predikaat"))
            {
                continue;
            }

            for (Constructor constructor : clazz.getDeclaredConstructors()) {
                teCheckenKlassen.add(clazz);
                break;
            }
        }
        return teCheckenKlassen;
    }

    /**
     * Test alle setters van logisch model classes op het zetten van een veld met een standaard waarde en <code>null</code>. Hiervoor worden alle model
     * classes binnen het logisch model package opgehaald en wordt gekeken voor welke class velden er ook setter methodes bestaan. Voor een standaard
     * instantie van de class waar setter methodes voor bestaan wordt dan de setter methode aangeroepen met een standaard waarde, waarna het betreffende
     * veld wordt bekeken of deze nu dezelfde waarde heeft als de waarde die is gezet. Daarna wordt hetzelfde gedaan, maar dan wordt de setter methode
     * aangeroepen met <code>null</code> en wordt gekeken of het veld daarna ook <code>null</code> is.
     */
    @Test
    public void testLogischModelSetters()
        throws URISyntaxException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException,
        IllegalAccessException
    {
        final List<Class> interfaces = getClassesInPackage(ROOT_PACKAGE + ".logisch");
        final List<Class> alleKlasses = getClassesInPackage(ROOT_PACKAGE);

        final List<Class> implementatiesVanInterfaces = getImplementatiesVanInterfaces(interfaces, alleKlasses);

        testModelSetters(implementatiesVanInterfaces);
    }

    /**
     * Test alle setters van entity classes op het zetten van een veld met een standaard waarde en <code>null</code>. Hiervoor worden alle entities binnen
     * het opgegeven root package opgehaald en wordt gekeken voor welke entity velden er ook setter methodes bestaan. Voor een standaard instantie van de
     * entity waar setter methodes voor bestaan wordt dan de setter methode aangeroepen met een standaard waarde, waarna het betreffende veld wordt bekeken
     * of deze nu dezelfde waarde heeft als de waarde die is gezet. Daarna wordt hetzelfde gedaan, maar dan wordt de setter methode aangeroepen met
     * <code>null</code> en wordt gekeken of het veld daarna ook <code>null</code> is.
     */
    @Test
    public void testEntitySetters()
        throws URISyntaxException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException,
        IllegalAccessException
    {
        final List<Class> classes = getClassesInPackage(ROOT_PACKAGE);
        final List<Class> entities = filterClassesOpEntities(classes);

        testModelSetters(entities);
    }

    /**
     * Test alle setters van de opgegeven classes op het zetten van een veld met een standaard waarde en <code>null</code>. Hiervoor wordt gekeken voor
     * welke velden er ook setter methodes bestaan. Voor een standaard instantie van de class waar setter methodes voor bestaan wordt dan de setter methode
     * aangeroepen met een standaard waarde, waarna het betreffende veld wordt bekeken of deze nu dezelfde waarde heeft als de waarde die is gezet. Daarna
     * wordt hetzelfde gedaan, maar dan wordt de setter methode aangeroepen met <code>null</code> en wordt gekeken of het veld daarna ook <code>null</code>
     * is.
     */
    private void testModelSetters(final List<Class> classes)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        for (final Class clazz : classes) {
            final Object obj;
            final Constructor[] cons = clazz.getConstructors();
            if ((cons != null && cons.length > 0) && (!Modifier.isAbstract(clazz.getModifiers()))) {
                // abstract classes doesn't have constructors, skip these.
                try {
                    obj = creeerEntityInstantie(clazz);
                } catch (final InstantiationException e) {
                    LOGGER.error("Failed to create instance ofTAbstr " + clazz.getName() + " error " + e.getMessage());
                    throw e;
                }

                final Field[] entityVelden = clazz.getDeclaredFields();

                for (final Field entityVeld : entityVelden) {
                    final String setterMethodeNaam = bouwSetterMethodeNaamVoorVeld(entityVeld);
                    if (heeftClassOpgegevenMethode(clazz, setterMethodeNaam, entityVeld.getType())) {
                        final Method setterMethode = clazz.getDeclaredMethod(setterMethodeNaam, entityVeld.getType());
                        if (isTestbareSetterMethodeVoorVeld(setterMethode, entityVeld)) {
                            entityVeld.setAccessible(true);
                            setterMethode.setAccessible(true);

                            // Test zetten van een standaard waarde
                            final Object waarde = getStandaardWaardeVoorVeld(entityVeld);
                            setterMethode.invoke(obj, waarde);

                            if (waarde instanceof Date) {
                                // Voor Dates controleren we middels equals daar hiervoor vaak clones gebruikt worden in
                                // de setter methodes.
                                assertEquals(waarde, entityVeld.get(obj));
                            } else {
                                Assert.assertSame(waarde, entityVeld.get(obj));
                            }

                            // Test zetten van null
                            setterMethode.invoke(obj, (Object) null);
                            Assert.assertNull(entityVeld.get(obj));
                        }
                    }
                }
            }
        }
    }

    /**
     * Retourneert de getter methode voor het opgegeven veld conform de standaard JavaBean specificatie. Dit is altijd een methode zonder parameters,
     * waarbij de naam van het veld gelijk is aan de naam van de methode, alleen dan geprefixed met 'get' of 'is' (indien het een boolean veld is). Indien
     * een dergelijke methode niet bestaat binnen de opgegeven class, wordt <code>null</code> geretourneerd.
     *
     * @param veld het veld waarvoor de getter methode wordt gezocht.
     * @return de bij het veld horende getter methode, of <code>null</code> indien er geen getter methode is.
     * @throws NoSuchMethodException indien er een fout optreedt.
     */
    private Method getGetterMethodeVoorVeld(final Field veld) throws NoSuchMethodException {
        final Class clazz = veld.getDeclaringClass();
        Method result = null;
        String getterMethodeNaam = bouwGetterMethodeNaamVoorVeld(veld);
        if (heeftClassOpgegevenMethode(clazz, getterMethodeNaam, null)) {
            result = clazz.getDeclaredMethod(getterMethodeNaam);
        } else if (veld.getType().equals(Boolean.class)) {
            getterMethodeNaam = bouwBooleanGetterMethodeNaamVoorVeld(veld);
            if (heeftClassOpgegevenMethode(clazz, getterMethodeNaam, null)) {
                result = clazz.getDeclaredMethod(getterMethodeNaam);
            }
        }
        return result;
    }

    /**
     * Retourneert een standaard waarde voor het opgegeven veld. Hiervoor wordt met name gekeken naar het type van het veld. Indien dit een enum is, wordt
     * de eerste enum constante geretourneerd, indien het een in de constante {@link #VALUES} map bekende class is, wordt de binnen de {@link #VALUES} map
     * bekende waarde van die class geretourneerd en als het een type is die een standaard lege constructor ondersteund, dan wordt die gebruikt om de
     * standaard waarde te genereren.
     *
     * @param veld het veld waarvoor een standaard waarde wordt gezocht.
     * @return de standaard waarde voor het opgegeven veld.
     */
    @SuppressWarnings("unchecked")
    private Object getStandaardWaardeVoorVeld(final Field veld)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        Object result = null;
        if (veld.getType().isEnum()) {
            result = ((Class<? extends Enum>) veld.getType()).getEnumConstants()[0];
        } else if (VALUES.containsKey(veld.getType())) {
            result = VALUES.get(veld.getType());
        } else if (heeftVeldTypeLegeConstructor(veld.getType()) && !Modifier
            .isAbstract(veld.getType().getModifiers()))
        {
            final Constructor constructor = veld.getType().getDeclaredConstructor();
            constructor.setAccessible(true);
            result = constructor.newInstance();
        }
        return result;
    }

    /**
     * Controleert of de opgegeven getter methode ook een correct te testen methode is voor het opgegeven veld. Dit is alleen indien de methode hetzelfde
     * type retourneert als het veld is en indien het of een in de {@link #VALUES} ondersteund type is, het een enum is of het type een lege (zonder
     * parameters) constructor heeft.
     *
     * @param getterMethode de getter methode die gecontroleerd dient te worden.
     * @param veld          het veld dat middels de getter methode ondersteund wordt.
     * @return een boolean die aangeeft of de getter methode een goed testbare methode is of niet.
     */
    private boolean isTestbareGetterMethodeVoorVeld(final Method getterMethode, final Field veld) {
        boolean result = false;

        if (getterMethode.getReturnType().equals(veld.getType())) {
            result = VALUES.containsKey(veld.getType()) && (veld.getType().equals(getterMethode.getReturnType()))
                || veld.getType().isEnum()
                || heeftVeldTypeLegeConstructor(veld.getType());
        }
        return result;
    }

    /**
     * Controleert of de opgegeven setter methode ook een correct te testen methode is voor het opgegeven veld. Dit is alleen indien de methode een enkele
     * parameter ondersteund en deze van hetzelfde type is als het veld zelf en indien het of een in de {@link #VALUES} ondersteund type is, het veld een
     * enum is of het type een lege (zonder parameters) constructor heeft.
     *
     * @param setterMethode de setter methode die gecontroleerd dient te worden.
     * @param veld          het veld dat middels de setter methode ondersteund wordt.
     * @return een boolean die aangeeft of de setter methode een goed testbare methode is of niet.
     */
    private boolean isTestbareSetterMethodeVoorVeld(final Method setterMethode, final Field veld) {
        boolean result = false;

        final Class paramClazz = setterMethode.getParameterTypes()[0];

        if (paramClazz.equals(veld.getType())) {
            result = VALUES.containsKey(veld.getType())
                || veld.getType().isEnum()
                || heeftVeldTypeLegeConstructor(veld.getType());
        }
        return result;
    }

    /**
     * Geeft aan of het opgegeven type over een lege (zonder parameters) constructor heeft of niet.
     *
     * @param type het type dat gecontroleerd dient te worden.
     * @return of het opgegeven type over een lege (zonder parameters) constructor heeft.
     */
    private boolean heeftVeldTypeLegeConstructor(final Class<?> type) {
        for (final Constructor<?> constructor : type.getDeclaredConstructors()) {
            if (constructor.getParameterTypes().length == 0) {
                return true;
            }
        }
        return false;
    }

    private String bouwGetterMethodeNaamVoorVeld(final Field veld) {
        return String.format("get%s%s", veld.getName().substring(0, 1).toUpperCase(), veld.getName().substring(1));
    }

    private String bouwBooleanGetterMethodeNaamVoorVeld(final Field veld) {
        return String.format("is%s%s", veld.getName().substring(0, 1).toUpperCase(), veld.getName().substring(1));
    }

    private String bouwSetterMethodeNaamVoorVeld(final Field veld) {
        return String.format("set%s%s", veld.getName().substring(0, 1).toUpperCase(), veld.getName().substring(1));
    }

    /**
     * Creeert een nieuwe instantie voor de (entity) class. Het doet dit door de verwachte parameterloze constructor op te halen voor de opgegeven class en
     * deze aan te roepen. Daar JPA entities verplicht zijn een parameterloze constructor te hebben, zal dit geen probleem zijn, ook al is deze niet
     * public.
     *
     * @param clazz de class waarvoor een instantie moet worden gecreeerd.
     * @param <T>   het type van de class.
     * @return een nieuwe instantie van de class.
     */
    private <T> T creeerEntityInstantie(final Class<T> clazz)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        for (Constructor constructor : clazz.getDeclaredConstructors()) {
            if (constructor.getParameterTypes().length == 0) {
                constructor.setAccessible(true);
                return (T) constructor.newInstance();
            }
        }
        //Geen default constructor gevonden, probeer met null-waardes te instantieren
        for (Constructor constructor : clazz.getDeclaredConstructors()) {
            final List<Object> constructorArgs = new ArrayList<>();
            for (Class parameterType : constructor.getParameterTypes()) {
                constructorArgs.add(null);
            }
            constructor.setAccessible(true);
            return (T) constructor.newInstance(constructorArgs.toArray());
        }

        return null;
    }

    /**
     * Bouwt een nieuwe lijst op van classes, gebaseerd op de opgegeven lijst van classes, maar waar dan alleen nog classes inzitten die een entity zijn
     * (welke geannoteerd zijn met de annotatie {@link Entity}).
     *
     * @param classes de lijst van classes waaruit de lijst van entities wordt opgebouwd.
     * @return een lijst van entity classes.
     */
    private List<Class> filterClassesOpEntities(final List<Class> classes) {
        final List<Class> entities = new ArrayList<>();
        for (final Class clazz : classes) {
            if (clazz.isAnnotationPresent(Entity.class)) {
                entities.add(clazz);
            } else if (clazz.isAnnotationPresent(Embeddable.class)) {
                entities.add(clazz);
            }
        }

        return entities;
    }

    /**
     * Bouwt een nieuwe lijst op van classes, gebaseerd op de opgegeven lijst van classes, maar waar dan alleen nog classes inzitten die een enum zijn.
     *
     * @param classes de lijst van classes waaruit de lijst van entities wordt opgebouwd.
     * @return een lijst van enum classes.
     */
    private List<Class> filterClassesOpEnums(final List<Class> classes) {
        final List<Class> enums = new ArrayList<>();
        for (final Class clazz : classes) {
            if (clazz.isEnum()) {
                enums.add(clazz);
            }
        }
        return enums;
    }

    /**
     * Controleert of de opgegeven class een methode (private, protected, package of public) heeft die voldoet aan de opgegeven methode naam en een
     * parameter heeft van het eventueel opgegeven parametertype.
     *
     * @param clazz       de class die moet worden gecontroleerd.
     * @param methodeNaam de naam van de methode waarop wordt gezocht.
     * @param veldType    parametertype.
     * @return een boolean die aangeeft of de methode bestaat in de class of niet.
     */
    private boolean heeftClassOpgegevenMethode(final Class clazz, final String methodeNaam,
        final Class veldType)
    {
        boolean result = false;
        final Method[] methods = clazz.getDeclaredMethods();
        for (final Method method : methods) {
            if (method.getName().equals(methodeNaam)) {
                if (veldType != null) {
                    if (method.getParameterTypes().length == 1 && method.getParameterTypes()[0].equals(veldType)) {
                        result = true;
                        break;
                    }
                } else {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Bepaalt de implementates van een interface binnen een meegegeven lijst van klasses.
     *
     * @param interfaces              de interfaces
     * @param mogelijkeImplementaties de mogelijke implementaties
     * @return de implementaties van interfaces
     */
    private static List<Class> getImplementatiesVanInterfaces(final List<Class> interfaces,
        final List<Class> mogelijkeImplementaties)
    {
        final List<Class> implementaties = new ArrayList<>();

        for (final Class eenInterface : interfaces) {
            for (final Class eenImplementatie : mogelijkeImplementaties) {
                if (!eenImplementatie.isInterface()
                    && !wordtGenegeerd(eenImplementatie)
                    && eenInterface.isAssignableFrom(eenImplementatie))
                {
                    implementaties.add(eenImplementatie);
                }
            }
        }
        return implementaties;
    }

    /**
     * Klasses die genegeerd worden, deze hebben geen.
     *
     * @param eenImplementatie implementatie van klasse
     * @return true als implementatie
     */
    private static boolean wordtGenegeerd(final Class eenImplementatie) {
        final List<Class> teNegerenKlasses = new ArrayList<>();
        teNegerenKlasses.add(PersoonIndicatieBericht.class);

        return eenImplementatie.getSimpleName().endsWith("View")
            || teNegerenKlasses.contains(eenImplementatie);
    }

    /**
     * Haalt voor de opgegeven <code>packageNaam</code> de folders op, op basis van de classloader, die behoren tot het opgegeven package (dit zijn naar
     * alle waarschijnlijkheid de package in src/main/java en src/test/java). Daarna wordt een lijst opgebouwd voor de classes die zijn te vinden in deze
     * folders en hun onderliggende folders, waarna deze lijst van classes wordt geretourneerd.
     *
     * @param packageNaam De naam van het package waarvoor de classes moeten worden geretourneerd.
     * @return Een lijst van classes welke worden gevonden in de opgegeven package en diens subpackages.
     * @throws ClassNotFoundException indien een class niet gevonden kan worden.
     * @throws IOException            indien de package niet gevonden kan worden.
     * @throws URISyntaxException     als de URI syntactisch niet juist is, wat onwaarschijnlijk is, omdat die opgehaald wordt.
     */
    private static List<Class> getClassesInPackage(final String packageNaam)
        throws ClassNotFoundException, IOException, URISyntaxException
    {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final Enumeration<URL> resources = classLoader.getResources(packageNaam.replace('.', '/'));

        // Maak een lijst van alle folders/resources die overeen komen met het gezochte package. Dit zal de package
        // specifieke folder zijn in /src/main/java en de folder in /src/test/java.
        final List<File> bestanden = new ArrayList<>();
        while (resources.hasMoreElements()) {
            final URI resource = resources.nextElement().toURI();
            bestanden.add(new File(resource.getPath()));
        }

        // Maak een lijst van Class bestanden die zitten in de lijst van gevonden folders behorende bij de package.
        final List<Class> classes = new ArrayList<>();
        for (final File folder : bestanden) {
            classes.addAll(vindClassesInPackageFolderEnSubpackages(folder, packageNaam));
        }
        return classes;
    }

    /**
     * Haalt alle classes op uit de opgegeven folder en diens subfolder en retourneert deze.
     *
     * @param folder      de folder die classes bevat welke geretourneerd dienen te worden.
     * @param packageNaam de naam van het package waartoe de classes in de folder behoren.
     * @return de in de folder en diens subfolder gevonden classes.
     * @throws ClassNotFoundException indien een class niet gevonden kan worden.
     */
    private static List<Class> vindClassesInPackageFolderEnSubpackages(final File folder,
        final String packageNaam) throws ClassNotFoundException
    {
        final List<Class> classes = new ArrayList<>();

        if (!folder.exists()) {
            LOGGER.error("Folder bestaat niet: {}", folder.getAbsolutePath());
            return classes;
        }

        final File[] bestanden = folder.listFiles();
        if (bestanden != null) {
            for (final File bestand : bestanden) {
                if (bestand.isDirectory()) {
                    classes.addAll(vindClassesInPackageFolderEnSubpackages(bestand,
                        bouwPackageNaamVoorFolder(bestand, packageNaam)));
                } else if (bestand.getName().endsWith(".class")) {
                    classes.add(Class.forName(bouwClassNaamVoorBestand(bestand, packageNaam)));
                }
            }
        }
        return classes;
    }

    /**
     * Bouwt de package naam op voor een folder.
     *
     * @param folder            de folder waarvoor de package naam wordt opgebouwd.
     * @param parentPackageNaam de naam van het (parent) package waarin dit package zich bevindt.
     * @return de package naam voor de folder.
     */
    private static String bouwPackageNaamVoorFolder(final File folder, final String parentPackageNaam) {
        return String.format("%s.%s", parentPackageNaam, folder.getName());
    }

    /**
     * Retourneert de volledige naam van de java class file.
     *
     * @param bestand     het java class bestand waarvoor de naam moet worden gegenereerd.
     * @param packageNaam de naam van het package waarin de java class file zich bevindt.
     * @return de volledige naam van de java class file.
     */
    private static String bouwClassNaamVoorBestand(final File bestand, final String packageNaam) {
        return String.format("%s.%s", packageNaam, bestand.getName().substring(0, bestand.getName().length() - 6));
    }
}
