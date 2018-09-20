/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.writer.formatter;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.generatoren.java.util.JavaGeneratorConstante;
import org.apache.commons.digester.Digester;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;


/**
 * Formatteert java bron code volgens de BRP eclipse formatter profile die geldt voor het project BRP.
 */
public class BronCodeFormatterImpl implements BronCodeFormatter {

    private static final Logger LOGGER = LoggerFactory.getLogger(BronCodeFormatterImpl.class);

    private static final String FORMATTER_OPTIES_BESTAND = "brp_eclipse_formatter_profile.xml";
    private static final String JAVA_VERSION             = JavaCore.VERSION_1_7;

    private CodeFormatter codeFormatter;

    /**
     * Constructor. Initialiseert de Eclipse formatter die ge-wrapped wordt door deze code formatter.
     */
    public BronCodeFormatterImpl() {
        initialiseerEclipseFormatter();
    }

    /**
     * Initialiseert de Eclipse formatter inclusief de format opties.
     */
    private void initialiseerEclipseFormatter() {
        final Map<String, String> opties = leesFormatterOptiesIn();
        codeFormatter = ToolFactory.createCodeFormatter(opties);
    }

    /**
     * Deze functie leest de formatter opties in uit een xml configuratie bestand.
     *
     * @return De formatter opties opgeslagen in een map.
     */
    private Map<String, String> leesFormatterOptiesIn() {
        Map<String, String> opties = new HashMap<>();
        opties.put(JavaCore.COMPILER_SOURCE, JAVA_VERSION);
        opties.put(JavaCore.COMPILER_COMPLIANCE, JAVA_VERSION);
        opties.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JAVA_VERSION);

        InputStream configBestand = null;
        try {
            configBestand = this.getClass().getClassLoader().getResourceAsStream(FORMATTER_OPTIES_BESTAND);
            Map<String, String> config = leesConfigFile(configBestand);
            opties.putAll(config);
        } catch (IOException e) {
            LOGGER.error("Fout tijdens inlezen configuratie bestand voor code formatter.", e);
        } catch (SAXException e) {
            LOGGER.error("Fout tijdens parsen configuratie bestand voor code formatter.", e);
        } finally {
            if (configBestand != null) {
                try {
                    configBestand.close();
                } catch (IOException e) {
                    LOGGER.error("Fout bij sluiten config bestand.", e);
                }
            }
        }

        return opties;
    }

    /**
     * Leest een Eclipse formatter configuratie bestand in.
     *
     * @param configFile Inputstream naar het configuratie bestand.
     * @return De Eclipse formatter opties opgeslagen in een map.
     * @throws IOException  Indien er iets fout gaat in file IO.
     * @throws SAXException Indien het bestand niet ge-parsed kan worden door digester.
     */
    private Map<String, String> leesConfigFile(final InputStream configFile) throws IOException, SAXException {
        Digester digester = new Digester();
        digester.addRuleSet(new RuleSet());

        Object result = digester.parse(configFile);
        if (result == null) {
            LOGGER.error("Geen profiles gevonden in config file.");
            throw new IllegalStateException("Geen profiles gevonden in config file.");
        }

        FormatterProfielen profiles = (FormatterProfielen) result;
        List<Map<String, String>> list = profiles.getProfiles();
        if (list.size() == 0) {
            LOGGER.error("Config file bevat geen profiles.");
            throw new IllegalStateException("Geen profiles gevonden in config file.");
        }

        return list.get(0);
    }

    @Override
    public String formatteerCode(final String code) {
        TextEdit te = codeFormatter.format(
                CodeFormatter.K_COMPILATION_UNIT + CodeFormatter.F_INCLUDE_COMMENTS,
                code, 0, code.length(), 0, JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde());

        if (te == null) {
            String message = "Broncode kon niet geparsed worden:\n\n" + code + "\n\n";
            System.out.println(message);
            throw new IllegalArgumentException(message);
        }

        IDocument doc = new Document(code);
        try {
            te.apply(doc);

            //Helaas worden trailing spaces achter lege javadoc comments niet verwijderd door de formatter.
            //Dit heeft wellicht te maken met de versie van de formatter die we gebruiken.
            //Nu lossen we het op met een FindReplaceDocumentAdapter.
            final FindReplaceDocumentAdapter findReplaceDocumentAdapter = new FindReplaceDocumentAdapter(doc);
            while (findReplaceDocumentAdapter
                    .find(0, " \\* $", true, false, false, true) != null)
            {
                findReplaceDocumentAdapter.replace(" *", false);
            }
        } catch (BadLocationException e) {
            LOGGER.error("Fout bij toepassen code reformattering.", e);
        }
        return doc.get();
    }
}
