/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.jmx;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;
import nl.bzk.migratiebrp.util.common.operatie.ExceptionWrapper;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal;
import nl.bzk.migratiebrp.util.common.operatie.HerhaalException;
import nl.bzk.migratiebrp.util.common.operatie.StopHerhalingExceptionWrapper;

/**
 * Abstracte JMX implementatie.
 */
public abstract class AbstractJmxKanaal extends AbstractKanaal {

    private static final String EMPTY_STRING_IDENTIFIER_JMX = "emptyString";
    private static final String ERROR_ONGELDIGE_NAAM = "Ongeldige objectnaam '%s'.";
    private static final String ERROR_GEEN_OBJECT_INFO = "Kon object informatie voor '%s' niet ophalen.";
    private static final String ERROR_OPERATIE_OF_ATTRIBUUT_NIET_GEVONDEN = "Operatie of attribuut '%s' niet gevonden in object '%s'.";
    private static final String ERROR_OPERATIE_UITVOEREN = "Kon operatie '%s' niet uitvoeren op object '%s'.";
    private static final String ERROR_ATTRIBUUT_UITVOEREN = "Kon attribuut '%s' niet ophalen op object '%s'.";
    private static final String ERROR_ONVERWACHT_OPERATIE_RESULTAAT = "Operatie '%s' op object '%s' resulteerde in  '%s', maar '%s' was verwacht.";
    private static final String ERROR_ONVERWACHT_ATTRIBUUT_RESULTAAT = "Attribuut '%s' op object '%s' resulteerde in  '%s', maar '%s' was verwacht.";

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Geef de MBeanServer connectie.
     * @return MBeanServer connectie
     */
    protected abstract MBeanServerConnection getConnection();

    @Override
    public final void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
        final String[] parts = bericht.getInhoud().replaceAll("\r", "").split("\n");
        if (parts.length < 2) {
            throw new KanaalException(
                    "JMX kanaal verwacht een bericht met meerdere regels; de eerste regel bevat de object naam; "
                            + "de tweede regel de operatie of attribuut waarde; de volgende regels bevatten parameters (voor een operatie); "
                            + "eventueel volgt een verwacht resultaat");
        }

        // "brp.levering.mutaties:name=AbonnementService"
        final String objectName = parts[0].trim();
        // "herlaadAbonnementen"
        final String operationOrAttributeName = parts[1].trim();

        final ObjectName name;
        try {
            name = new ObjectName(objectName);
        } catch (final MalformedObjectNameException e) {
            throw new KanaalException(String.format(ERROR_ONGELDIGE_NAAM, objectName), e);
        }

        final MBeanServerConnection connection = getConnection();
        if (connection == null) {
            throw new KanaalException("Geen MBeamServiceConnection beschikbaar");
        }

        final MBeanInfo info;
        try {
            info = connection.getMBeanInfo(name);
        } catch (final
        InstanceNotFoundException
                | IntrospectionException
                | ReflectionException
                | IOException e) {
            if ("true".equalsIgnoreCase(bericht.getTestBericht().getTestBerichtProperty("jmx.ignore.instancenotfound"))) {
                return;
            }

            throw new KanaalException(String.format(ERROR_GEEN_OBJECT_INFO, objectName), e);
        }

        try {
            // Zoek operatie
            final MBeanOperationInfo operation = findOperationInfo(info, operationOrAttributeName, parts);
            if (operation != null) {
                bericht.getTestBericht().maakHerhaling().herhaal(new InvokeOperation(connection, name, operation, parts));
            } else {
                // Zoek attribuut
                final MBeanAttributeInfo attribute = findAttributeInfo(info, operationOrAttributeName);
                if (attribute != null) {
                    final Herhaal herhaal = bericht.getTestBericht().maakHerhaling();
                    herhaal.herhaal(new GetAttribute(connection, name, attribute, parts));
                } else {
                    throw new KanaalException(String.format(ERROR_OPERATIE_OF_ATTRIBUUT_NIET_GEVONDEN, operationOrAttributeName, objectName));
                }
            }
        } catch (final HerhaalException e) {
            final List<Exception> exceptions = e.getPogingExcepties();
            if (exceptions.isEmpty()) {
                throw new KanaalException("Fout bij uitvoeren (geen pogingen)", e);
            } else {
                throw new KanaalException("Fout bij uitvoeren", exceptions.get(exceptions.size() - 1));
            }
        }
    }

    private MBeanOperationInfo findOperationInfo(final MBeanInfo info, final String operationName, final String[] parts) {
        for (final MBeanOperationInfo operationInfo : info.getOperations()) {
            LOGGER.info("Operatie: {}", operationInfo.getName());
            if (operationInfo.getName().equals(operationName)) {
                LOGGER.info("Operatie met correcte naam gevonden: {}", operationName);

                // Bepalen of het aantal parameters/return waarde klopt
                int verwachtAantal;
                LOGGER.info("Return type: {}", operationInfo.getReturnType());
                if ("void".equals(operationInfo.getReturnType())) {
                    verwachtAantal = 0;
                } else {
                    verwachtAantal = 1;
                }
                verwachtAantal = +operationInfo.getSignature().length;
                LOGGER.info("Operatie verwacht {} parts", verwachtAantal);
                LOGGER.info("Test heeft {}", new Object[]{parts});

                // Parts heeft ook ObjectName en OperationName
                if (verwachtAantal + 2 == parts.length) {
                    return operationInfo;
                }
            }
        }
        return null;
    }

    private MBeanAttributeInfo findAttributeInfo(final MBeanInfo info, final String attributeName) {
        for (final MBeanAttributeInfo attributeInfo : info.getAttributes()) {
            if (attributeInfo.getName().equals(attributeName)) {
                return attributeInfo;
            }
        }
        return null;
    }

    private static String get(final String[] parts, final int index) {
        if (index >= parts.length) {
            return null;
        } else {
            String result;
            if (EMPTY_STRING_IDENTIFIER_JMX.equals(parts[index])) {
                result = "";
            } else {
                result = parts[index];
            }
            return result;
        }
    }

    private static Object castTo(final String value, final String type) throws KanaalException {
        final Object resultaat;

        if (value == null) {
            resultaat = null;
        } else {
            try {
                final Class<?> clazz;
                if ("int".equals(type)) {
                    clazz = Integer.class;
                } else {
                    clazz = Class.forName(type);
                }

                if (clazz.equals(String.class)) {
                    resultaat = value;
                } else if ("".equals(value)) {
                    resultaat = null;
                } else {
                    // Assume clazz has constructor with String
                    final Constructor<?> constructor = clazz.getConstructor(String.class);
                    resultaat = constructor.newInstance(value);
                }
            } catch (final ReflectiveOperationException e) {
                throw new KanaalException("Cannot cast '" + value + "' to a '" + type + "'.", e);
            }
        }

        return resultaat;
    }

    /**
     * Get Attribute.
     */
    public static final class GetAttribute implements Runnable {
        private final MBeanServerConnection connection;
        private final ObjectName name;
        private final MBeanAttributeInfo attribute;
        private final String expectedResult;

        /**
         * Constructor.
         * @param connection connectie
         * @param name object name
         * @param attribute attribute
         * @param parts parts
         */
        public GetAttribute(final MBeanServerConnection connection, final ObjectName name, final MBeanAttributeInfo attribute, final String[] parts) {
            this.connection = connection;
            this.name = name;
            this.attribute = attribute;

            expectedResult = get(parts, 2);
        }

        @Override
        public void run() {
            Object result;
            try {
                result = connection.getAttribute(name, attribute.getName());
            } catch (final
            AttributeNotFoundException
                    | InstanceNotFoundException
                    | MBeanException
                    | ReflectionException
                    | IOException e) {
                throw new StopHerhalingExceptionWrapper(new KanaalException(String.format(ERROR_ATTRIBUUT_UITVOEREN, attribute.getName(), name), e));
            }

            if (expectedResult != null) {
                if (result == null || !expectedResult.equals(result.toString())) {
                    throw new ExceptionWrapper(
                            new KanaalException(String.format(ERROR_ONVERWACHT_ATTRIBUUT_RESULTAAT, attribute.getName(), name, result, expectedResult)));
                }
            }
        }
    }

    /**
     * Invoke operation.
     */
    public static final class InvokeOperation implements Runnable {
        private final MBeanServerConnection connection;
        private final ObjectName name;
        private final MBeanOperationInfo operation;
        private final List<Object> parameters;
        private final List<String> signature;
        private final String expectedResult;

        /**
         * Constructor.
         * @param connection connectie
         * @param name object name
         * @param operation operation
         * @param parts parts
         * @throws KanaalException bij parameter fouten
         */
        public InvokeOperation(final MBeanServerConnection connection, final ObjectName name, final MBeanOperationInfo operation, final String[] parts)
                throws KanaalException {
            this.connection = connection;
            this.name = name;
            this.operation = operation;

            parameters = new ArrayList<>();
            signature = new ArrayList<>();

            final MBeanParameterInfo[] parameterInfos = operation.getSignature();
            for (int parameterIndex = 0; parameterIndex < parameterInfos.length; parameterIndex++) {
                final MBeanParameterInfo parameterInfo = parameterInfos[parameterIndex];
                final String parameterValue = get(parts, parameterIndex + 2);

                parameters.add(castTo(parameterValue, parameterInfo.getType()));
                signature.add(parameterInfo.getType());
            }

            expectedResult = get(parts, 2 + parameters.size());
        }

        @Override
        public void run() {
            Object result;
            try {
                result =
                        connection.invoke(
                                name,
                                operation.getName(),
                                parameters.toArray(new Object[parameters.size()]),
                                signature.toArray(new String[signature.size()]));
            } catch (final
            InstanceNotFoundException
                    | MBeanException
                    | ReflectionException
                    | IOException e) {
                throw new StopHerhalingExceptionWrapper(new KanaalException(String.format(ERROR_OPERATIE_UITVOEREN, operation.getName(), name), e));
            }

            if (expectedResult != null) {
                if (result == null || !expectedResult.equals(result.toString())) {
                    throw new ExceptionWrapper(
                            new KanaalException(String.format(ERROR_ONVERWACHT_OPERATIE_RESULTAAT, operation.getName(), name, result, expectedResult)));
                }
            }
        }
    }
}
