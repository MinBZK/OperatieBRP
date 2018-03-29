/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.validatie;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * Valideert de inhoud van een .story file.
 */
public final class StoryFileValidator implements Validator<URL> {
    private static final int MAX_AANTAL_REGELS = 1000;
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final Validator<MetaTag> metaTagValidator = new MetaTagValidator();

    @Override
    public boolean valideer(final URL fileLocation) {
        if (fileLocation == null) {
            return false;
        }

        try (Stream<String> stream = Files.lines(Paths.get(fileLocation.toURI()))) {
            boolean result = true;
            final List<String> regels = stream.collect(Collectors.toList());

            if (regels.size() > MAX_AANTAL_REGELS) {
                LOGGER.error("De story file heeft meer dan 1000 regels");
                result = false;
            }

            boolean hasStatusMetaTag = false;
            boolean hasNarrative = false;
            for (final String regel : regels) {
                if (regel.startsWith("@")) {
                    final MetaTag metaTag = new MetaTag(regel);
                    final boolean isValide = metaTagValidator.valideer(metaTag);
                    if (!isValide) {
                        LOGGER.error(String.format("Meta tag %s is niet ondersteund of bevat verkeerde waarde", metaTag.getNaam()));
                        result = false;
                    }
                    if (metaTag.isStatusTag()) {
                        hasStatusMetaTag = true;
                    }
                } else {
                    if (regel.startsWith("Narrative:")) {
                        hasNarrative = true;
                    }
                }
            }
            if (!hasNarrative) {
                LOGGER.error("Er is geen Narrative in de story");
                result = false;

            }
            if (!hasStatusMetaTag) {
                LOGGER.error("Er is geen @status Meta informatie");
                result = false;
            }
            return result;
        } catch (IOException | URISyntaxException e) {
            final String foutmelding = "Fout tijdens valideren story";
            LOGGER.error(foutmelding, e);
            throw new IllegalStateException(foutmelding, e);
        }
    }
}
