/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.autconv.lo3naarbrp;

import com.google.common.collect.Lists;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatieHistorie;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Converteer GBA afnemerindicaties naar BRP afnemerindicaties.
 */
@Component
final class AfnemerindicatieConversie {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    static final String NULL_VALUE = "\\N";

    private static final int LOG_TRESHOLD = 25_000;

    private final File persafnemerindicatieOudFile;
    private final File persafnemerindicatieNieuwFile;
    private final File hisPersafnemerindicatieOudFile;
    private final File hisPersafnemerindicatieNieuwFile;

    @Inject
    private PartijConversie partijConversie;
    @Inject
    private DienstConversie dienstConversie;
    @Inject
    private LeveringsautorisatieConversie leveringsautorisatieConversie;
    @Inject
    private AfnemerindicatieDatabaseInteractieStrategy afnemerindicatieDatabaseInteractieStrategy;

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager entityManager;

    private AfnemerindicatieConversie() {

        final File afnemerindicatieworkdir = new File(System.getProperty("workdir", "afnemerindicatieworkdir"));
        if (!afnemerindicatieworkdir.exists()) {
            Assert.isTrue(afnemerindicatieworkdir.mkdirs());
        }
        persafnemerindicatieOudFile = new File(afnemerindicatieworkdir, "persafnemerindicatie_oud.txt");
        persafnemerindicatieNieuwFile = new File(afnemerindicatieworkdir, "persafnemerindicatie_nieuw.txt");
        hisPersafnemerindicatieOudFile = new File(afnemerindicatieworkdir, "his_persafnemerindicatie_oud.txt");
        hisPersafnemerindicatieNieuwFile = new File(afnemerindicatieworkdir, "his_persafnemerindicatie_nieuw.txt");

        Lists.newArrayList(persafnemerindicatieOudFile, persafnemerindicatieNieuwFile,
                hisPersafnemerindicatieOudFile, hisPersafnemerindicatieNieuwFile).forEach(file -> {
            try {
                if (file.exists()) {
                    Assert.isTrue(file.delete(), "Kan bestand niet verwijderen: " + file.getAbsolutePath());
                }
                Assert.isTrue(file.createNewFile());
                Assert.isTrue(file.setWritable(true, false), "file niet writeable: " + file);

                LOGGER.info("File gemaakt: {}", file.getAbsolutePath());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    void converteerAfnemerindicaties() throws IOException {
        LOGGER.info("Converteer afnemerdicaties");
        final long maxAfnemerindicatieId = bepaalMaxId(PersoonAfnemerindicatie.class).longValue();
        converteerPersafnemerindicatieTabel(maxAfnemerindicatieId);
        converteerHisPersafnemerindicatieTabel(maxAfnemerindicatieId);
        LOGGER.info("Genereren afnemerindicaties klaar!");
    }

    private long converteerPersafnemerindicatieTabel(final long maxAfnemerindicatieId) throws IOException {
        afnemerindicatieDatabaseInteractieStrategy.dumpAfnemerindicatieTabelNaarFile(persafnemerindicatieOudFile);
        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(persafnemerindicatieNieuwFile))) {
            try (InputStream is = new BufferedInputStream(new FileInputStream(persafnemerindicatieOudFile))) {
                final LineIterator it = IOUtils.lineIterator(is, StandardCharsets.UTF_8);
                converteerAfnemerindicatiebestand(maxAfnemerindicatieId, os, it);
            }
        }
        afnemerindicatieDatabaseInteractieStrategy.vulAfnemerindicatieTabel(persafnemerindicatieNieuwFile);
        return maxAfnemerindicatieId;
    }

    private void converteerAfnemerindicatiebestand(final long maxAfnemerindicatieId,
                                                   final OutputStream os, final LineIterator it) throws IOException {
        long voortgang = 0;
        final Map<Short, Short> partijConversieMap = partijConversie.getPartijConversieMap();
        final Map<Integer, Integer> leveringsautorisatieConversieMap = leveringsautorisatieConversie.getLeveringsautorisatieConversieMap();
        while (it.hasNext()) {
            final String line = it.nextLine();
            final String[] splitLine = StringUtils.split(line, ",");
            final long origId = Long.parseLong(splitLine[0]);
            final long id = maxAfnemerindicatieId + origId;
            final String pers = splitLine[1];
            final String afnemer = String.valueOf(partijConversieMap.get(Short.parseShort(splitLine[2])));
            final String levsautorisatie = String.valueOf(leveringsautorisatieConversieMap.get(Integer.parseInt(splitLine[3])));
            final String dataanvmaterieleperiode = StringUtils.defaultString(StringUtils.trimToNull(splitLine[4]), NULL_VALUE);
            final String dateindevolgen = StringUtils.defaultString(StringUtils.trimToNull(splitLine[5]), NULL_VALUE);
            final String indag = splitLine[6];
            final String newLine = String.format("%s,%s,%s,%s,%s,%s,%s%n", id, pers, afnemer, levsautorisatie,
                    dataanvmaterieleperiode, dateindevolgen, indag);
            IOUtils.write(newLine, os, StandardCharsets.UTF_8);

            voortgang++;
            if (voortgang % LOG_TRESHOLD == 0) {
                LOGGER.info("Voortgang persafnemerindicatie {}", voortgang);
            }
        }
    }

    private void converteerHisPersafnemerindicatieTabel(final long maxAfnemerindicatieId)
            throws IOException {
        afnemerindicatieDatabaseInteractieStrategy.dumpHisAfnemerindicatieTabel(hisPersafnemerindicatieOudFile);
        final long maxHisAfnemerindicatieId = bepaalMaxId(PersoonAfnemerindicatieHistorie.class).longValue();
        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(hisPersafnemerindicatieNieuwFile))) {
            try (InputStream is = new BufferedInputStream(new FileInputStream(hisPersafnemerindicatieOudFile))) {
                final LineIterator it = IOUtils.lineIterator(is, StandardCharsets.UTF_8);
                converteerHisAfnemerindicatiebestand(maxAfnemerindicatieId, maxHisAfnemerindicatieId, os, it);
            }
        }
        afnemerindicatieDatabaseInteractieStrategy.vulHisAfnemerindicatieTabel(hisPersafnemerindicatieNieuwFile);
    }

    private void converteerHisAfnemerindicatiebestand(final long maxAfnemerindicatieId, final long maxHisAfnemerindicatieId,
                                                      final OutputStream os, final LineIterator it)
            throws IOException {
        long voortgang = 0;
        final Map<Integer, Integer> dienstConversieMap = dienstConversie.getDienstConversieMap();
        while (it.hasNext()) {
            final String line = it.nextLine();
            final String[] splitLine = StringUtils.split(line, ",");
            final long origId = Long.parseLong(splitLine[0]);
            final long id = maxHisAfnemerindicatieId + origId;
            final long persafnemerindicatieOud = Long.parseLong(splitLine[1]);
            final long persafnemerindicatie = persafnemerindicatieOud + maxAfnemerindicatieId;
            final String treg = splitLine[2];
            final String tsverval = splitLine[3];
            final String dienstinhWaarde =
                    NULL_VALUE.equals(splitLine[4])
                            ? NULL_VALUE
                            : dienstConversieMap.get(Integer.parseInt(splitLine[4])).toString();
            final String dienstvervalWaarde =
                    NULL_VALUE.equals(splitLine[5])
                            ? NULL_VALUE
                            : dienstConversieMap.get(Integer.parseInt(splitLine[5])).toString();
            final String dataanvmaterieleperiodeWaarde = splitLine[6];
            final String dateindevolgenWaarde = splitLine[7];
            final String newLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s%n", id, persafnemerindicatie, treg, tsverval,
                    dienstinhWaarde, dienstvervalWaarde, dataanvmaterieleperiodeWaarde, dateindevolgenWaarde);
            IOUtils.write(newLine, os, StandardCharsets.UTF_8);

            voortgang++;
            if (voortgang % LOG_TRESHOLD == 0) {
                LOGGER.info("Voortgang persafnemerindicatie {}", voortgang);
            }
        }
    }

    private Number bepaalMaxId(Class<?> tabelClass) {
        final Table tableAnnotation = tabelClass.getAnnotation(Table.class);
        final Number singleResult = (Number) entityManager.createNativeQuery(String.format("select max(id) from %s.%s",
                tableAnnotation.schema(), tableAnnotation.name())).getSingleResult();
        return singleResult == null ? 0 : singleResult;
    }

}
