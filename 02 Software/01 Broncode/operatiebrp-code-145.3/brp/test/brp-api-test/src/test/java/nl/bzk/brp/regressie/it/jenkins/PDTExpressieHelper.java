/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.regressie.it.jenkins;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.parser.ExpressieParser;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

/**
 */
final class PDTExpressieHelper {

    static final PDTExpressieHelper INSTANCE = new PDTExpressieHelper();

    final Set<String> dienstExpressiesDieNietParsen = Sets.newHashSet();
    final List<Expressie> parsedDienstExpressiesList = Lists.newLinkedList();

    final Set<String> dienstbundelExpressiesDieNietParsen = Sets.newHashSet();
    final List<Expressie> parsedDienstbundelExpressies = Lists.newLinkedList();

    private PDTExpressieHelper() {
        try {
            init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void init() throws IOException {
        try (final InputStream inputStream = new ClassPathResource("/pdtexpressie/dienst-attcrit.txt").getInputStream()) {
            final Set<String> expressies = IOUtils.readLines(inputStream, StandardCharsets.UTF_8).stream().collect(Collectors.toSet());
            for (String dienstExpressie : expressies) {
                try {
                    parsedDienstExpressiesList.add(ExpressieParser.parse(dienstExpressie));
                } catch (ExpressieException e) {
                    dienstExpressiesDieNietParsen.add(dienstExpressie);
                }
            }
        }
        try (final InputStream inputStream = new ClassPathResource("/pdtexpressie/dienstbundel-naderepopbep.txt").getInputStream()) {
            final Set<String> expressies = IOUtils.readLines(inputStream, StandardCharsets.UTF_8).stream().collect(Collectors.toSet());
            for (String dienstbundelExpressie : expressies) {
                try {
                    parsedDienstbundelExpressies.add(ExpressieParser.parse(dienstbundelExpressie));
                } catch (ExpressieException e) {
                    dienstExpressiesDieNietParsen.add(dienstbundelExpressie);
                }
            }
        }
    }


}
