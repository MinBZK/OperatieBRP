/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.schrijver;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.SequenceInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieService;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;


/**
 * Implementatie van {@link SteekproefService}.
 */
@Component
@Bedrijfsregel(Regel.R2613)
@Bedrijfsregel(Regel.R2614)
final class SteekproefServiceImpl implements SteekproefService {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final int MAX_PERSONEN_IN_STEEKPROEF = 50;

    @Inject
    private SelectieFileService selectieFileService;

    @Inject
    private LeveringsautorisatieService leveringsautorisatieService;

    private SteekproefServiceImpl() {

    }

    @Override
    public void maakSteekproefBestand(final MaakSelectieResultaatTaak taak) throws SelectieResultaatVerwerkException {
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie =
                leveringsautorisatieService.geefToegangLeveringsAutorisatie(taak.getToegangLeveringsAutorisatieId());
        final Dienst dienst = AutAutUtil.zoekDienst(toegangLeveringsAutorisatie.getLeveringsautorisatie(), taak.getDienstId());
        if (dienst.getIndicatieSelectieresultaatControleren() != Boolean.TRUE) {
            LOG.debug("Geen steekproefbestand voor dienstId:" + dienst.getId());
            return;
        }

        LOG.debug("Maak steekproefbestand voor dienstId:" + dienst.getId());
        try {
            final List<Path> paths = selectieFileService.geefFragmentFiles(taak);
            final int totaalAantalRegels = paths.stream().mapToInt(SteekproefServiceImpl::bepaalLinesPerFile).sum();
            final List<Integer> regelnummers = bepaalRegelnummers(totaalAantalRegels);
            try (final LineNumberReader br = new LineNumberReader(new InputStreamReader(maakLogischBestand(paths), StandardCharsets.UTF_8))) {
                final Set<Integer> uniekeRegelnummers = Sets.newTreeSet(regelnummers);
                final Map<Integer, String> regelsInSteekproefMap = uniekeRegelnummers.stream().
                        collect(Collectors.toMap(o -> o, o -> leesRegel(br, o)));
                final List<String> regelsInSteekproef = regelnummers.stream().map(regelsInSteekproefMap::get).collect(Collectors.toList());
                selectieFileService.schrijfSteekproefBestand(taak, regelsInSteekproef);
            }

        } catch (IOException e) {
            throw new SelectieResultaatVerwerkException(e);
        }
    }

    @Override
    public int maxPersonenInSteekproef() {
        return MAX_PERSONEN_IN_STEEKPROEF;
    }

    private String leesRegel(LineNumberReader br, int regelnummer) {
        try {
            String regel = null;
            while (br.getLineNumber() <= regelnummer) {
                regel = br.readLine();
            }
            return regel;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private List<Integer> bepaalRegelnummers(final int totaalAantalRegels) {
        final IntStream regelsInSteekproef;
        if (totaalAantalRegels <= maxPersonenInSteekproef()) {
            //statische steekproef
            regelsInSteekproef = IntStream.range(0, totaalAantalRegels);
        } else {
            //random steekproef, dubbele personen is geen probleem
            final Random random = new Random();
            regelsInSteekproef = IntStream.generate(() -> random.nextInt(totaalAantalRegels)).limit(maxPersonenInSteekproef()).sorted();
        }
        return regelsInSteekproef.boxed().collect(Collectors.toList());
    }

    private static SequenceInputStream maakLogischBestand(final List<Path> paths) {
        final List<InputStream> inputStreamList = Lists.newArrayList();
        for (Path path : paths) {
            try {
                final FileInputStream fileInputStream = new FileInputStream(path.toFile());
                inputStreamList.add(fileInputStream);
            } catch (FileNotFoundException e) {
                inputStreamList.forEach(IOUtils::closeQuietly);
                throw new IllegalStateException(e);

            }
        }
        return new SequenceInputStream(Collections.enumeration(inputStreamList));
    }

    private static int bepaalLinesPerFile(Path path) {
        try (LineNumberReader lnr = new LineNumberReader(new InputStreamReader(
                new FileInputStream(path.toFile()), StandardCharsets.UTF_8))) {
            lnr.skip(Long.MAX_VALUE);
            return lnr.getLineNumber();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
