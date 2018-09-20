/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.reader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import nl.bzk.migratiebrp.test.common.writer.AbstractIOFactory;
import org.springframework.stereotype.Component;

/**
 * Reader factory.
 */
@Component
public final class ReaderFactory extends AbstractIOFactory {
    private static final String INPUT_EXTENSIE_LO3_EXCEL = "xls";
    private static final String INPUT_EXTENSIE_LO3_LG01 = "lg01";
    private static final String INPUT_EXTENSIE_LO3_PL = "txt";
    private static final String INPUT_EXTENSIE_SQL = "sql";

    private static final Map<String, Class<? extends Reader>> READERS = new HashMap<>();
    private static final Class<? extends Reader> DEFAULT_READER = TextReader.class;

    /**
     * Default constructor. Maakt een {@link ReaderFactory} met alle standaard alle readers (xls, lg01, txt en sql)
     * enabled.
     */
    public ReaderFactory() {
        this(new Properties());
    }

    /**
     * Constructor. Maakt een {@link ReaderFactory} waarmee readers uitgezet kan worden.
     * 
     * @param configuratie
     *            een {@link Properties} object waarin geconfigureerd staat welke reader aan dan wel uit gezet moet
     *            worden (bv xls=false is {@link ExcelReader} uit gezet).
     */
    public ReaderFactory(final Properties configuratie) {
        if (!isDisabled(configuratie, INPUT_EXTENSIE_CSV)) {
            READERS.put(INPUT_EXTENSIE_CSV, CsvReader.class);
        }

        if (!isDisabled(configuratie, INPUT_EXTENSIE_LO3_EXCEL)) {
            READERS.put(INPUT_EXTENSIE_LO3_EXCEL, ExcelReader.class);
        }

        if (!isDisabled(configuratie, INPUT_EXTENSIE_LO3_LG01)) {
            READERS.put(INPUT_EXTENSIE_LO3_LG01, Lg01Reader.class);
        }

        if (!isDisabled(configuratie, INPUT_EXTENSIE_SQL)) {
            READERS.put(INPUT_EXTENSIE_SQL, SqlReader.class);
        }
    }

    private static boolean isDisabled(final Properties configuratie, final String extensie) {
        return "false".equalsIgnoreCase(configuratie.getProperty(extensie));
    }

    /**
     * Geef reader voor het bestand.
     * 
     * @param file
     *            bestand
     * @return reader
     */
    public Reader getReader(final File file) {
        final Matcher matcher = PATTERN_EXTENSIE.matcher(file.getName().toLowerCase());
        final String extensie;
        if (matcher.find()) {
            extensie = matcher.group(1);
        } else {
            extensie = null;
        }

        final Class<? extends Reader> readerClazz = READERS.containsKey(extensie) ? READERS.get(extensie) : DEFAULT_READER;
        LOG.info("Using reader (file: {}, extensie {}): {} ", new Object[] {file.getName(), extensie, readerClazz.getName() });

        final Reader reader;
        try {
            reader = readerClazz.newInstance();
        } catch (
            InstantiationException
            | IllegalAccessException e)
        {
            throw new RuntimeException("Kan reader niet instantieren.", e);
        }
        return reader;
    }

    @Override
    public boolean accept(final File file) {
        boolean result = false;
        final Matcher matcher = PATTERN_EXTENSIE.matcher(file.getName().toLowerCase());
        if (matcher.find()) {
            final String extensie = matcher.group(1);
            switch (extensie) {
                case INPUT_EXTENSIE_LO3_EXCEL:
                case INPUT_EXTENSIE_LO3_LG01:
                case INPUT_EXTENSIE_LO3_PL:
                case INPUT_EXTENSIE_SQL:
                case INPUT_EXTENSIE_CSV:
                    result = true;
                    break;
                default:
                    LOG.error(String.format("Bestand heeft een niet ondersteunde extensie (%s).", extensie));
                    result = false;
            }
        }
        return result;
    }
}
