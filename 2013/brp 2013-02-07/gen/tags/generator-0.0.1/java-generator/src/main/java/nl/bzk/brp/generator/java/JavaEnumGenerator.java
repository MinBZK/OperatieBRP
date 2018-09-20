/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 *
 */
package nl.bzk.brp.generator.java;

import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.generator.GenerationPackageNames;
import nl.bzk.brp.generator.java.domein.Constructor;
import nl.bzk.brp.generator.java.domein.EnumValue;
import nl.bzk.brp.generator.java.domein.Enumeration;
import nl.bzk.brp.generator.java.domein.Field;
import nl.bzk.brp.generator.java.domein.GenerationReport;
import nl.bzk.brp.generator.java.domein.Identifier;
import nl.bzk.brp.metaregister.dataaccess.AttribuutDao;
import nl.bzk.brp.metaregister.dataaccess.ObjectTypeDao;
import nl.bzk.brp.metaregister.dataaccess.TekstDao;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * TODO: refactor naar nieuwe structuur en verbeter afhandeling van attributen e.d (wordt nu te vaak over geitereerd, kan beter)
 *
 */
@Component
@Transactional
public class JavaEnumGenerator extends AbstractJavaGenerator<ObjectType> {

    private static final String PARAMETER_NULL_VALUE                        = StringUtils.EMPTY;
    private static final String TEMPLATE_GROUP_NAME                         = "enumeraties";
    private static final String TEMPLATE_ENUMERATIE                         = "enumeratie";

    private static final String HEEFT_MATERIELE_HISTORIE                    = "HeeftMaterieleHistorie";

    private static final String INDICATIE_MATERIELE_HISTORIE_VAN_TOEPASSING = "IndicatieMaterieleHistorieVanToepassing";

    private static final String ENUM_WAARDE_ID                              = "RelatiefId";

    private static final String DUMMY_ENUM_VALUE                            = "DUMMY";

    private static final String DEFAULT_ATTRIBUTE_TYPE                      = "String";

    private static final Logger logger                                      = LoggerFactory
                                                                                    .getLogger(JavaEnumGenerator.class);

    @Autowired(required = true)
    private ObjectTypeDao       objectTypeDao;

    @Autowired(required = true)
    private TekstDao            tekstDao;

    @Autowired(required = true)
    private AttribuutDao        attribuutDao;

    public JavaEnumGenerator() {
        setTemplateGroupName(TEMPLATE_GROUP_NAME);
        setBasePackage(GenerationPackageNames.OBJECTTYPE_OPERATIONEEL_STATISCH_PACKAGE);
    }

    /**
     *
     * @param destinationPackage
     */
    public GenerationReport genereer() {
        GenerationReport report = new GenerationReport();
        report.setObjectType("Enumeraties");

        List<ObjectType> enumeraties = objectTypeDao.getEnumeratieTypes();

        for (ObjectType enumeratie : enumeraties) {
            Writer writer = creeerWriter(enumeratie.getIdentCode());
            genereerElement(writer, report, enumeratie);
        }
        return report;

    }

    @Override
    public void genereerElement(final Writer writer, final GenerationReport report, final ObjectType enumeratie) {
        logger.debug("Genereer enum voor " + enumeratie.getNaam() + ", code " + enumeratie.getIdentCode());

        Set<Tuple> tuples = enumeratie.getTuples();
        Collection<Attribuut> attributen = attribuutDao.getAttributen(enumeratie);
        String enumeratieNaam = enumeratie.getIdentCode() != null ? enumeratie.getIdentCode() : enumeratie.getNaam();
        Enumeration enumGenerationObject = new Enumeration(enumeratieNaam, tekstDao.getJavaDocForObject(enumeratie));
        enumGenerationObject.setPackageName(basePackageName.getPackage());
        Constructor constructor = new Constructor(enumeratie.getIdentCode());
        EnumValue dummy = new EnumValue(DUMMY_ENUM_VALUE, DUMMY_ENUM_VALUE);

        for (Attribuut attribuut : attributen) {
            if(attribuut.getIdentCode().equalsIgnoreCase("id"))
                continue;
            // Voeg veld toe voor enum
            Field field = new Field(DEFAULT_ATTRIBUTE_TYPE, attribuut.getIdentCode());
            field.setJavaDoc(tekstDao.getJavaDocForObject(attribuut));

            enumGenerationObject.addAttribuut(field);
            // Maak een getter voor het veld
            nl.bzk.brp.generator.java.domein.Method accessor = new nl.bzk.brp.generator.java.domein.Method(DEFAULT_ATTRIBUTE_TYPE, "get"
                    + new Identifier(attribuut.getIdentCode()));
            accessor.setBody("return " + new Identifier(attribuut.getIdentCode()).getLowerCamel());
            accessor.setJavaDoc(field.getJavaDoc());
            enumGenerationObject.addAccessor(accessor);
            // Voeg het attribuut toe aan de constructor
            constructor.addParameter(attribuut.getIdentCode());
            // Voeg het attribuut toe als initialisatie met een lege waarde aan de Dummy waarde (om de Enum ordinal
            // 0 te vullen)
            dummy.addParameter(PARAMETER_NULL_VALUE);
        }

        enumGenerationObject.addConstructor(constructor);
        enumGenerationObject.addValue(dummy);
        enumGenerationObject.addValues(initializeEnumValues(tuples, attributen));

        String sourceCode = genereerEnum(enumGenerationObject);
        logger.debug(sourceCode);
        int result = write(sourceCode, writer);
        if (result > 0) {
            report.addSucccess(enumeratie.getNaam(), sourceCode);
        } else {
            report.addFailure(enumeratie.getNaam(), "Fout bij het wegschrijven, resultaat=" + result);
        }
    }

    /**
     * Initialiseer de waardes van de enumeratie. Gebruik de naam of indien de naam leeg is de code als waarde en vul de
     * initialisatie parameters aan de hand van het tuple en de attributen zoals die voor deze enumeratie gedefineerd
     * zijn.
     *
     * @param tuples the tuples
     * @param attributen the attributen
     * @return the sets the
     */
    private Set<EnumValue> initializeEnumValues(final Set<Tuple> tuples, final Collection<Attribuut> attributen) {
        Set<EnumValue> values = new LinkedHashSet<EnumValue>();
        for (Tuple tuple : tuples) {
            EnumValue waarde = null;
            if(tuple.getNaam().equalsIgnoreCase("id")) {
                continue; //skip the id
            }
            if (StringUtils.isBlank(tuple.getNaam())) {
                waarde = new EnumValue(tuple.getCode());
            } else if (tuple.getNaam().equalsIgnoreCase("?")) {
                waarde = new EnumValue(tuple.getCode());
            } else {
                waarde = new EnumValue(tuple.getNaam());
            }

            waarde.setCode(tuple.getCode());
            waarde.setParameters(getEnumValueInitializationParameters(attributen, tuple));
            values.add(waarde);
        }
        return values;
    }

    /**
     * Haal voor een enum waarde de initializatie parameters op om de constructor juist aan te roepen.
     *
     * @param attributen de attributen
     * @param tuple de tuple
     * @return the enum value initializatie parameters
     */
    private List<String>
            getEnumValueInitializationParameters(final Collection<Attribuut> attributen, final Tuple tuple)
    {
        List<String> parameters = new ArrayList<String>();
        for (Attribuut attribuut : attributen) {
            String parameterWaarde = null;
            if(attribuut.getIdentCode().equalsIgnoreCase("id"))
                continue;
            parameterWaarde = getParameterInitializationValue(attribuut, tuple);
            parameters.add(parameterWaarde);
            logger.debug("Add parameter for attribuut:" + attribuut.getIdentCode() + "," + attribuut.getNaam());
        }

        return parameters;
    }

    /**
     * Achterhaal de intializatie waarde voor een parameter van een enumeratie waarde. Gebruik de naam van het attribuut
     * om via reflectie de get methode aan te roepen op het tuple. Let op dat er hierbij een mapping plaatsvind voor de
     * methode naam voor speciale kolommen die niet gelijk zijn aan de attribuutnamen.
     *
     * @param attribuut het attribuut waarvoor we de initializatie waarde ophalen
     * @param tuple het tuple waarin de initializatie waarde zit
     * @return the parameter initialization value
     */
    private String getParameterInitializationValue(final Attribuut attribuut, final Tuple tuple) {
        String value = null;
        try {
            String methodName = attribuut.getIdentCode();
            if (methodName.equalsIgnoreCase(ID)) {
                methodName = ENUM_WAARDE_ID;
            } else if (methodName.equalsIgnoreCase(INDICATIE_MATERIELE_HISTORIE_VAN_TOEPASSING)) {
                methodName = HEEFT_MATERIELE_HISTORIE;
            }

            Method getter = Tuple.class.getDeclaredMethod("get" + methodName, (Class<?>[]) null);
            if (getter != null) {
                Object obj = getter.invoke(tuple, new Object[] {});
                if (obj == null) {
                    value = PARAMETER_NULL_VALUE;
                } else {
                    // Escaping van accenten
                    value = GeneratorUtils.normalise(obj.toString());
                }
            } else {
                logger.warn("FOUTJE, geen getter gevonden voor " + methodName);
            }
        } catch (SecurityException e) {
            logger.error("Could not access getter with name " + "get" + attribuut.getIdentCode(), e);
        } catch (NoSuchMethodException e) {
            logger.error("Oeps, no method found with name " + "get" + attribuut.getIdentCode(), e);
        } catch (IllegalArgumentException e) {
            logger.error("Oeps, illegal arguments for method with name " + "get" + attribuut.getIdentCode(), e);
        } catch (IllegalAccessException e) {
            logger.error("Oeps, no access to method with name " + "get" + attribuut.getIdentCode(), e);
        } catch (InvocationTargetException e) {
            logger.error("Oeps, cannot invoke method with name " + "get" + attribuut.getIdentCode(), e);
        }
        return value;
    }

    /**
     * Genereer de enum door de elementen van het samengestelde object aan de template door te spelen.
     *
     * @param enumGenerationObject the enum generation object
     * @return the string
     */
    private String genereerEnum(final Enumeration enumGenerationObject) {

        contentTemplate = group.getInstanceOf(TEMPLATE_ENUMERATIE);
        if (contentTemplate == null) {
            throw new IllegalStateException("Template voor enumeraties niet gevonden");
        }
        contentTemplate.add("enumObject", enumGenerationObject);

        return contentTemplate.render(MAX_LINE_WIDTH);
    }

}
