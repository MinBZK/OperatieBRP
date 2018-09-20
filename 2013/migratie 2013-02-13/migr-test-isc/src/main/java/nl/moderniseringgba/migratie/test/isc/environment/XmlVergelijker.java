/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.isc.environment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.moderniseringgba.migratie.test.isc.TestException;
import nl.moderniseringgba.migratie.test.vergelijk.Vergelijk;
import nl.moderniseringgba.migratie.test.vergelijk.VergelijkingContext;

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
public final class XmlVergelijker {

    private static final Pattern ACTIE_PATTERN = Pattern.compile(
            "<actie(Inhoud|Geldigheid|Verval)\\sid=\"(.*?)\">.*?</actie\\1>", Pattern.DOTALL);

    private XmlVergelijker() {
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
     * @throws TestException
     *             bij fouten
     */
    // CHECKSTYLE:OFF - Retun count: makkelijk om hier de loop uit te springen
    public static boolean vergelijkXmlMetActies(final String verwacht, final String actueel) throws TestException {
        // CHECKSTYLE:ON

        // Verwachte acties
        final Map<String, String> verwachteActies = new HashMap<String, String>();
        final String verwachtParsed = verwerkActies(verwacht, verwachteActies);
        // Actuele acties
        final Map<String, String> actueleActies = new HashMap<String, String>();
        final String actueelParsed = verwerkActies(actueel, actueleActies);

        final VergelijkingContext vergelijkingContext = new VergelijkingContext();

        // Vergelijk
        if (!vergelijkXml(vergelijkingContext, verwachtParsed, actueelParsed)) {
            return false;
        }

        // Vergelijk acties
        for (final Map.Entry<String, String> entry : verwachteActies.entrySet()) {
            final String key = vergelijkingContext.getConstantVariable(entry.getKey());

            if (!actueleActies.containsKey(key)) {
                return false;
            }

            if (!vergelijkXml(vergelijkingContext, entry.getValue(), actueleActies.get(key))) {
                return false;
            }
        }

        return true;
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
     * @throws TestException
     *             bij fouten
     */
    public static boolean vergelijkXml(final String verwacht, final String actueel) throws TestException {
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
     * @throws TestException
     *             bij fouten
     */
    public static boolean vergelijkXml(
            final VergelijkingContext vergelijkingContext,
            final String verwacht,
            final String actueel) throws TestException {
        XMLUnit.setIgnoreWhitespace(true);
        final DifferenceListener myDifferenceListener = new MyDifferenceListener(vergelijkingContext);
        Diff myDiff;
        try {
            myDiff = new Diff(verwacht, actueel);
        } catch (final SAXException e) {
            throw new TestException("Inhoud niet gelijk (SAXException)", e);
        } catch (final IOException e) {
            throw new TestException("Inhoud niet gelijk (IOException)", e);
        }
        myDiff.overrideDifferenceListener(myDifferenceListener);

        return myDiff.similar();
    }

    /**
     * Difference listener die inhoudelijke ongelijkheden (attribuut waarde en text waarde) ook nog controleert via de
     * Vergelijk.vergelijkInhoud methode (om {decimal} en {messageId} te verwerken).
     */
    private static final class MyDifferenceListener implements DifferenceListener {
        private final VergelijkingContext vergelijkingContext;

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
                    System.out.println("Difference: " + difference);
                    System.out.println("ControlNode: " + difference.getControlNodeDetail());
                    System.out.println("TestNode: " + difference.getTestNodeDetail());
                    System.out.println("Element waarde inhoudelijk verschillend:\n" + controlText + "\n" + testText);
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
                    System.out.println("Difference: " + difference);
                    System.out.println("ControlNode: " + difference.getControlNodeDetail());
                    System.out.println("TestNode: " + difference.getTestNodeDetail());
                    System.out
                            .println("Attribuut waarde inhoudelijk verschillend:\n" + controlText + "\n" + testText);
                    result = RETURN_ACCEPT_DIFFERENCE;
                }
            } else {
                if (!difference.isRecoverable()) {
                    System.out.println("Difference: " + difference);
                    System.out.println("Id: " + difference.getId());
                    System.out.println("Description: " + difference.getDescription());
                    System.out.println("Recoverable: " + difference.isRecoverable());
                    System.out.println("ControlNode: " + difference.getControlNodeDetail());
                    System.out.println("TestNode: " + difference.getTestNodeDetail());
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
