/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.expressie.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.ConverteerNaarExpressieServiceImpl;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.TekstHelper;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels.DatumVoorwaardeRegel;
import nl.bzk.migratiebrp.test.common.autorisatie.CsvAutorisatieReader;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * Maak testgevallen obv productie tabel 35.
 */
public final class MaakProductieTestgevallen {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Verwerk autorisaties.
     *
     * @param autorisaties
     *            autorisaties
     * @param outputDirectory
     *            output directory
     */
    public void verwerk(final List<Lo3Autorisatie> autorisaties, final File outputDirectory) {
        final Map<String, String> volledigActueelAdhoc = new TreeMap<>();
        final Map<String, String> volledigHistorischAdhoc = new TreeMap<>();
        final Map<String, String> volledigActueelSpontaan = new TreeMap<>();
        final Map<String, String> volledigHistorischSpontaan = new TreeMap<>();
        final Map<String, String> volledigActueelSelectie = new TreeMap<>();
        final Map<String, String> volledigHistorischSelectie = new TreeMap<>();

        final Map<EnkelvoudigId, String> enkelvoudigActueel = new TreeMap<>();
        final Map<EnkelvoudigId, String> enkelvoudigHistorisch = new TreeMap<>();

        for (final Lo3Autorisatie autorisatie : autorisaties) {
            for (final Lo3Categorie<Lo3AutorisatieInhoud> inhoud : autorisatie.getAutorisatieStapel()) {
                final boolean isActueel = inhoud.getInhoud().getDatumEinde() == null;
                verwerkVoorwaarde(
                    autorisatie.getAfnemersindicatie(),
                    isActueel,
                    // inhoud.getInhoud().getVersieNr(),
                    inhoud.getInhoud().getVoorwaarderegelAdHoc(),
                    volledigActueelAdhoc,
                    volledigHistorischAdhoc,
                    enkelvoudigActueel,
                    enkelvoudigHistorisch);
                verwerkVoorwaarde(
                    autorisatie.getAfnemersindicatie(),
                    isActueel,
                    // inhoud.getInhoud().getVersieNr(),
                    inhoud.getInhoud().getVoorwaarderegelSelectie(),
                    volledigActueelSelectie,
                    volledigHistorischSelectie,
                    enkelvoudigActueel,
                    enkelvoudigHistorisch);
                verwerkVoorwaarde(
                    autorisatie.getAfnemersindicatie(),
                    isActueel,
                    // inhoud.getInhoud().getVersieNr(),
                    inhoud.getInhoud().getVoorwaarderegelSpontaan(),
                    volledigActueelSpontaan,
                    volledigHistorischSpontaan,
                    enkelvoudigActueel,
                    enkelvoudigHistorisch);
            }
        }

        outputVolledig(volledigActueelAdhoc, new File(outputDirectory, "volledig actueel - adhoc"), "adhoc - ");
        outputVolledig(volledigHistorischAdhoc, new File(outputDirectory, "volledig historisch - adhoc"), "adhoc - ");
        outputVolledig(volledigActueelSelectie, new File(outputDirectory, "volledig actueel - selectie"), "selectie - ");
        outputVolledig(volledigHistorischSelectie, new File(outputDirectory, "volledig historisch - selectie"), "selectie - ");
        outputVolledig(volledigActueelSpontaan, new File(outputDirectory, "volledig actueel - spontaan"), "spontaan - ");
        outputVolledig(volledigHistorischSpontaan, new File(outputDirectory, "volledig historisch - spontaan"), "spontaan - ");
        outputEnkelvoudig(enkelvoudigActueel, new File(outputDirectory, "enkelvoudig actueel"), "enkelvoudig - ");
        outputEnkelvoudig(enkelvoudigHistorisch, new File(outputDirectory, "enkelvoudig historisch"), "enkelvoudig - ");
    }

    private void outputVolledig(final Map<String, String> voorwaarden, final File directory, final String prefix) {
        try {
            FileUtils.forceMkdir(directory);
        } catch (final IOException e) {
            throw new IllegalArgumentException("Kan directory niet aanmaken", e);
        }

        for (final Map.Entry<String, String> entry : voorwaarden.entrySet()) {
            try (FileOutputStream fos = new FileOutputStream(new File(directory, prefix + entry.getKey() + ".txt"))) {
                IOUtils.write(entry.getValue(), fos);
            } catch (final IOException e) {
                LOG.warn("Onverwachte fout bij schrijven bestand.", e);
            }
        }
    }

    private void outputEnkelvoudig(final Map<EnkelvoudigId, String> voorwaarden, final File directory, final String prefix) {
        try {
            FileUtils.forceMkdir(directory);
        } catch (final IOException e) {
            throw new IllegalArgumentException("Kan directory niet aanmaken", e);
        }

        for (final Map.Entry<EnkelvoudigId, String> entry : voorwaarden.entrySet()) {
            try (FileOutputStream fos = new FileOutputStream(new File(directory, prefix + entry.getKey() + ".txt"))) {
                IOUtils.write(entry.getValue(), fos);
            } catch (final IOException e) {
                LOG.warn("Onverwachte fout bij schrijven bestand.", e);
            }
        }
    }

    private void verwerkVoorwaarde(
        final Integer afnemersindicatie,
        final boolean isActueel,
        final String ruweVoorwaarderegel,
        final Map<String, String> volledigActueel,
        final Map<String, String> volledigHistorisch,
        final Map<EnkelvoudigId, String> enkelvoudigeActueel,
        final Map<EnkelvoudigId, String> enkelvoudigHistorisch)
    {
        if (ruweVoorwaarderegel == null || "".equals(ruweVoorwaarderegel)) {
            return;
        }

        final String voorwaarderegel = ConverteerNaarExpressieServiceImpl.normaliseerSpaties(ruweVoorwaarderegel);

        final StringBuilder id = new StringBuilder();
        id.append(afnemersindicatie);
        // if (!isActueel) {
        // id.append("-v").append(versienummer);
        // }

        checkVerwachteNOK(voorwaarderegel, id);

        if (isActueel) {
            volledigActueel.put(id.toString(), voorwaarderegel);
        } else {
            volledigHistorisch.put(id.toString(), voorwaarderegel);
        }

        final String[] enkelvoudigeDelen = voorwaarderegel.split("(OFVWD|ENVWD)");
        for (final String enkelvoudigDeel : enkelvoudigeDelen) {
            final TekstHelper tekstHelper = new TekstHelper(enkelvoudigDeel);
            tekstHelper.setVeiligeRegel(tekstHelper.getVeiligeRegel().replaceAll("\\(", "").replaceAll("\\)", ""));
            final String voorwaardeRegel = tekstHelper.getGbaVoorwaardeRegel().trim();
            final EnkelvoudigId voorwaardeId = new EnkelvoudigId(voorwaardeRegel);
            if (isActueel) {
                if (!enkelvoudigeActueel.containsKey(voorwaardeId)) {
                    enkelvoudigeActueel.put(voorwaardeId, voorwaardeRegel);
                }
            } else {
                if (!enkelvoudigHistorisch.containsKey(voorwaardeId)) {
                    enkelvoudigHistorisch.put(voorwaardeId, voorwaardeRegel);
                }
            }
        }
    }

    /**
     * Bepaal of dit een verwachte NOK is.
     *
     * @param voorwaarderegel
     *            voorwaarde regel
     * @param result
     *            hieraan wordt NOK toegevoegd
     */
    public static void checkVerwachteNOK(final String voorwaarderegel, final StringBuilder result) {
        if (voorwaarderegel.contains("19.89.20")) {
            result.append(" SELECTIEDATUM_NOK");
        }
        if (Pattern.compile("[5|6]\\d{1}\\.\\d{2}\\.\\d{2}").matcher(voorwaarderegel).find()) {
            result.append(" HISTORIE_NOK");
        }
        if (voorwaarderegel.contains(".81.20")) {
            result.append(" AKTENUMMER_NOK");
        }
        if (voorwaarderegel.contains(".83.10")) {
            result.append(" ONDERZOEK_NOK");
        }
    }

    /**
     * Runner.
     */
    public static final class Runner {
        /**
         * Main.
         *
         * @param args
         *            arguments (unused)
         */
        public static void main(final String[] args) {
            final CsvAutorisatieReader autorisatieReader = new CsvAutorisatieReader();
            try (InputStream is = MaakProductieTestgevallen.class.getResourceAsStream("/Tabel35 Autorisatietabel.csv")) {
                final List<Lo3Autorisatie> autorisaties = autorisatieReader.read(is);
                LOG.info("{} autorisaties ingelezen", autorisaties.size());

                final File outputDirectory = new File("./target/generated/productie");
                FileUtils.deleteDirectory(outputDirectory);
                LOG.info("Bestanden worden weggeschreven in {}", outputDirectory.getCanonicalPath());

                new MaakProductieTestgevallen().verwerk(autorisaties, outputDirectory);

            } catch (final IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    /**
     * Id.
     */
    public static final class EnkelvoudigId implements Comparable<EnkelvoudigId> {

        private final String id;

        /**
         * Constructor.
         *
         * @param voorwaardeRegel
         *            voorwaarde regel
         */
        public EnkelvoudigId(final String voorwaardeRegel) {
            id = bepaalId(voorwaardeRegel);
        }

        private String bepaalId(final String voorwaardeRegel) {
            final StringBuilder result = new StringBuilder();

            final String[] delen = voorwaardeRegel.split(" ", 3);

            final String hoofdRubriek = bepaalRubriek(delen);
            result.append(hoofdRubriek);
            result.append(' ').append(bepaalOperator(delen));
            if (delen.length == 3) {
                final String[] waardeDelen = delen[2].split("(OFVGL|ENVGL)");
                final String waardeDeel = waardeDelen[0].trim();

                final String rubriek = getRubriek(waardeDeel);
                if (rubriek != null) {
                    result.append(' ').append(rubriek);
                } else {
                    result.append(" VASTEWAARDE");
                    if (delen[2].contains("*") || delen[2].contains("?")) {
                        result.append(" WILDCARD");
                    }

                    if (isDatumRubriek(hoofdRubriek) && delen[2].endsWith("00")) {
                        final Pattern onbekendPattern = Pattern.compile("^.*?((00)*)$");
                        final Matcher onbekendMatcher = onbekendPattern.matcher(delen[2]);
                        if (onbekendMatcher.matches()) {
                            result.append(" ONBEKEND").append(onbekendMatcher.group(1).length());
                        }
                    }
                }

                final String matop = bepaalMatop(waardeDeel);
                if (matop != null) {
                    result.append(" PERIODE").append(matop.length());
                }

                if (delen[2].contains("OFVGL")) {
                    result.append(" OFVGL");
                }
                if (delen[2].contains("ENVGL")) {
                    result.append(" ENVGL");
                }
            }

            checkVerwachteNOK(result.toString(), result);

            return result.toString();
        }

        private boolean isDatumRubriek(final String hoofdRubriek) {
            return new DatumVoorwaardeRegel().filter(hoofdRubriek);
        }

        private String bepaalMatop(final String waardeDeel) {
            final Matcher matcher = Pattern.compile("^.*[-+]\\s*(\\d*)$").matcher(waardeDeel);

            if (matcher.matches()) {
                return matcher.group(1);
            } else {
                return null;
            }
        }

        private String getRubriek(final String waardeDeel) {
            final Matcher matcher = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{2}").matcher(waardeDeel);
            if (matcher.find()) {
                return matcher.group(0);
            } else {
                return null;
            }
        }

        private String bepaalRubriek(final String[] delen) {
            if ("KV".equalsIgnoreCase(delen[0]) || "KNV".equalsIgnoreCase(delen[0])) {
                return delen[1];
            } else {
                return delen[0];
            }
        }

        private String bepaalOperator(final String[] delen) {
            if ("KV".equalsIgnoreCase(delen[0]) || "KNV".equalsIgnoreCase(delen[0])) {
                return delen[0];
            } else {
                return delen[1];
            }
        }

        @Override
        public int compareTo(final EnkelvoudigId that) {
            return id.compareTo(that.id);
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (id == null ? 0 : id.hashCode());
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final EnkelvoudigId other = (EnkelvoudigId) obj;
            if (id == null) {
                if (other.id != null) {
                    return false;
                }
            } else if (!id.equals(other.id)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return id;
        }

    }

}
