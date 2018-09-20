/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.vergelijk;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.bzk.migratiebrp.test.common.vergelijk.vergelijking.VergelijkingContext;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceConstants;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.XMLUnit;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Util om 'ongesorteerde' xml te vergelijken.
 *
 * <code>&lt;bla>&lt;x>1&lt;/x>&lt;y>2&lt;/y>&lt;/bla></code> en
 * <code>&lt;bla>&lt;y>2&lt;/y>&lt;x>1&lt;/x>&lt;/bla></code> zullen gelijk zijn.
 *
 */
public final class VergelijkXml {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final Pattern ACTIE_PATTERN = Pattern.compile("<actie(Inhoud|Geldigheid|Verval)\\sid=\"(.*?)\">.*?</actie\\1>", Pattern.DOTALL);

    private VergelijkXml() {
        // Niet instantieerbaar
    }

    /**
     * Vergelijk ongesorteerd XML en houdt rekening met <actie id=""> en <actie ref=""> zoals in de BRP persoonslijst
     * wordt gegenereerd door simple xml.
     *
     * @param verwacht
     *            verwachte xml
     * @param actueel
     *            actuele xml
     * @return true, als de xml vergelijkbaar is, anders false
     */
    public static boolean vergelijkXmlMetActies(final String verwacht, final String actueel) {
        // Verwachte acties
        final Map<String, String> verwachteActies = new HashMap<>();
        final String verwachtParsed = verwerkActies(verwacht, verwachteActies);
        // Actuele acties
        final Map<String, String> actueleActies = new HashMap<>();
        final String actueelParsed = verwerkActies(actueel, actueleActies);

        final VergelijkingContext vergelijkingContext = new VergelijkingContext();

        boolean resultaat = true;

        // Vergelijk
        if (!vergelijkXml(vergelijkingContext, verwachtParsed, actueelParsed)) {
            resultaat = false;
        }

        // Vergelijk acties
        if (resultaat) {
            for (final Map.Entry<String, String> entry : verwachteActies.entrySet()) {
                final String key = vergelijkingContext.getConstantVariable(entry.getKey());

                if (!actueleActies.containsKey(key)) {
                    resultaat = false;
                    break;
                }

                if (!vergelijkXml(vergelijkingContext, entry.getValue(), actueleActies.get(key))) {
                    resultaat = false;
                    break;
                }
            }
        }

        return resultaat;
    }

    /**
     * Haal <actie id=""> eruit en vervang door <actie ref=""/>, en voeg deze toe (op key van id) aan de map.
     *
     * @param verwacht
     *            verwachte string (met acties met id)
     * @param acties
     *            actie map
     * @return verwachte verwachte string
     */
    private static String verwerkActies(final String verwacht, final Map<String, String> acties) {
        final StringBuilder result = new StringBuilder();
        final Matcher matcher = ACTIE_PATTERN.matcher(verwacht);

        int index = 0;

        while (matcher.find()) {
            final int groupStartIndex = matcher.start();
            final int groupEndIndex = matcher.end();

            if (groupStartIndex != index) {
                result.append(verwacht.substring(index, groupStartIndex));
            }

            // Handle group
            final String actie = matcher.group();
            final String typeActie = matcher.group(1);
            final String id = matcher.group(2);

            acties.put(id, actie);

            final String actieRef = "<actie" + typeActie + " ref=\"" + id + "\"/>";
            result.append(actieRef);

            index = groupEndIndex;
        }

        if (index != verwacht.length()) {
            result.append(verwacht.substring(index));
        }

        return result.toString();

    }

    /**
     * Vergelijk 'ongesorteerde' xml.
     *
     * @param verwacht
     *            verwachte xml
     * @param actueel
     *            actuele xml
     * @return true, als de xml vergelijkbaar is, anders false
     */
    public static boolean vergelijkXml(final String verwacht, final String actueel) {
        return vergelijkXml(new VergelijkingContext(), verwacht, actueel);
    }

    /**
     * Vergelijk 'ongesorteerde' xml.
     *
     * @param vergelijkingContext
     *            vergelijking context
     * @param verwacht
     *            verwachte xml
     * @param actueel
     *            actuele xml
     * @return true, als de xml vergelijkbaar is, anders false
     */
    public static boolean vergelijkXml(final VergelijkingContext vergelijkingContext, final String verwacht, final String actueel) {
        XMLUnit.setIgnoreWhitespace(true);
        final DifferenceListener myDifferenceListener = new MyDifferenceListener(vergelijkingContext);
        final Diff myDiff;
        try {
            myDiff = new Diff(verwacht, actueel);
        } catch (final
            SAXException
            | IOException e)
        {
            LOG.info("{} tijdens XML vergelijking {}", e.getClass().getSimpleName(), e);
            return false;
        }
        myDiff.overrideDifferenceListener(myDifferenceListener);

        return myDiff.similar();
    }

    /**
     * Difference listener die inhoudelijke ongelijkheden (attribuut waarde en text waarde) ook nog controleert via de
     * Vergelijk.vergelijkInhoud methode (om {decimal} en {messageId} te verwerken).
     */
    private static final class MyDifferenceListener implements DifferenceListener {
        private static final String NEWLINE = "\n";
        private static final String LOG_TEST_NODE = "TestNode: ";
        private static final String LOG_CONTROL_NODE = "ControlNode: ";
        private static final String LOG_DIFFERENCE = "Difference: ";
        private final VergelijkingContext vergelijkingContext;

        /**
         * Default constructor.
         * 
         * @param vergelijkingContext
         *            De context voor het vergelijken.
         */
        public MyDifferenceListener(final VergelijkingContext vergelijkingContext) {
            this.vergelijkingContext = vergelijkingContext;
        }

        @Override
        public int differenceFound(final Difference difference) {
            final int result;

            if (DifferenceConstants.TEXT_VALUE_ID == difference.getId()) {
                final String controlText = difference.getControlNodeDetail().getValue().trim();
                final String testText = difference.getTestNodeDetail().getValue().trim();

                if (Vergelijk.vergelijk(vergelijkingContext, controlText, testText)) {
                    // Vergelijking met gebruik van {decimal}, etc is gelijk
                    result = RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
                } else {
                    // Echt ongelijk

                    LOG.info(LOG_DIFFERENCE + difference);
                    LOG.info(LOG_CONTROL_NODE + difference.getControlNodeDetail());
                    LOG.info(LOG_TEST_NODE + difference.getTestNodeDetail());
                    LOG.info("Element waarde inhoudelijk verschillend:\n" + controlText + NEWLINE + testText);
                    result = RETURN_ACCEPT_DIFFERENCE;
                }
            } else if (DifferenceConstants.ATTR_VALUE_ID == difference.getId()) {
                final String controlText = difference.getControlNodeDetail().getValue().trim();
                final String testText = difference.getTestNodeDetail().getValue().trim();

                if (Vergelijk.vergelijk(vergelijkingContext, controlText, testText)) {
                    // Vergelijking met gebruik van {decimal}, etc is gelijk
                    result = RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
                } else {
                    // Echt ongelijk
                    LOG.info(LOG_DIFFERENCE + difference);
                    LOG.info(LOG_CONTROL_NODE + difference.getControlNodeDetail());
                    LOG.info(LOG_TEST_NODE + difference.getTestNodeDetail());
                    LOG.info("Attribuut waarde inhoudelijk verschillend:\n" + controlText + NEWLINE + testText);
                    result = RETURN_ACCEPT_DIFFERENCE;
                }
            } else {
                if (!difference.isRecoverable()) {
                    LOG.info(LOG_DIFFERENCE + difference);
                    LOG.info("Id: " + difference.getId());
                    LOG.info("Description: " + difference.getDescription());
                    LOG.info("Recoverable: " + difference.isRecoverable());
                    LOG.info(LOG_CONTROL_NODE + difference.getControlNodeDetail());
                    LOG.info(LOG_TEST_NODE + difference.getTestNodeDetail());
                }

                result = RETURN_ACCEPT_DIFFERENCE;
            }

            return result;
        }

        @Override
        public void skippedComparison(final Node node1, final Node node2) {
            // Ignore
        }
    }

}
