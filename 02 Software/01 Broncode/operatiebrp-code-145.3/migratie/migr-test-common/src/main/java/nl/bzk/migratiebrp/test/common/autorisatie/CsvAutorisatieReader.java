/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.autorisatie;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.SimpleParser;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.test.common.util.AutorisatieCsvConstants;
import org.mozilla.universalchardet.UniversalDetector;

/**
 * Autorisatie reader obv CSV bestand (structuur agentschap BPR).
 */
public final class CsvAutorisatieReader implements AutorisatieReader {

    private static final int AANTAL_VELDEN_IN_LO_39 = 29;
    private static final int AANTAL_VELDEN_IN_LO_38 = 35;

    private static final int DETECTION_BUFFER = 4096;

    private static final char QUOTED_START = '"';
    private static final String QUOTED_END = "\",";
    private static final String SEPARATOR = ",";

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public List<Lo3Autorisatie> read(final InputStream inputstream) throws IOException {
        final BufferedInputStream input = new BufferedInputStream(inputstream);
        if (!input.markSupported()) {
            throw new IllegalArgumentException("BufferedInputStream does not support mark?!");
        }
        input.mark(DETECTION_BUFFER);

        // Detect encoding
        final byte[] detectionBuffer = new byte[DETECTION_BUFFER];
        final int length = input.read(detectionBuffer, 0, DETECTION_BUFFER);
        final String encoding = detectEncoding(detectionBuffer, length);
        input.reset();

        LOG.debug("File encoding detected: " + encoding);

        // Read CSV to map (key=afnemer, value=regels)
        final BufferedReader reader = new BufferedReader(new InputStreamReader(input, Charset.forName(encoding)));
        return read(reader);
    }

    private List<Lo3Autorisatie> read(final BufferedReader reader) throws IOException {
        final Map<String, List<Lo3AutorisatieInhoud>> collector = new TreeMap<>();

        // Eerste regel negeren i.v.m. headers.
        reader.readLine();

        String line = reader.readLine();
        int index = 0;
        while (line != null && !"".equals(line)) {
            final Lo3AutorisatieInhoud autorisatie = parseCsv(line, ++index);
            if (autorisatie != null) {
                final String afnemersindicatie = autorisatie.getAfnemersindicatie();
                if (!collector.containsKey(afnemersindicatie)) {
                    collector.put(afnemersindicatie, new ArrayList<>());
                }
                collector.get(afnemersindicatie).add(autorisatie);
            }

            line = reader.readLine();
        }

        for (final List<Lo3AutorisatieInhoud> inhoudLijst : collector.values()) {
            inhoudLijst.sort(new AutorisatieInhoudDatumIngangDescendingComparator());
        }

        // Transform map to result
        final List<Lo3Autorisatie> result = new ArrayList<>();
        for (final List<Lo3AutorisatieInhoud> value : collector.values()) {

            final List<Lo3Categorie<Lo3AutorisatieInhoud>> categorieen = new ArrayList<>();
            for (int i = 0; i < value.size(); i++) {

                final Lo3Herkomst herkomst = new Lo3Herkomst(i == 0 ? Lo3CategorieEnum.CATEGORIE_35 : Lo3CategorieEnum.CATEGORIE_85, 0, i);

                final Lo3AutorisatieInhoud lo3Autorisatie = value.get(i);
                final Lo3Datum datumIngang = lo3Autorisatie.getDatumIngang();

                final Lo3Historie historie = new Lo3Historie(null, datumIngang, datumIngang);
                categorieen.add(new Lo3Categorie<>(lo3Autorisatie, null, historie, herkomst));
            }

            result.add(new Lo3Autorisatie(new Lo3Stapel<>(categorieen)));
        }

        return result;
    }

    private Lo3AutorisatieInhoud parseCsv(final String line, final int index) {
        final String[] values = splitCsvLine(line);

        if (values.length != AANTAL_VELDEN_IN_LO_38 && values.length != AANTAL_VELDEN_IN_LO_39) {
            LOG.error("Regel {} bevat niet de verwachte hoeveelheid waarden, maar {}", index, values.length);
            return null;
        }

        return readInhoud(values);
    }

    private Lo3AutorisatieInhoud readInhoud(final String[] values) {
        final Lo3AutorisatieInhoud result = new Lo3AutorisatieInhoud();

        // "95.10 Afnemersindicatie"
        result.setAfnemersindicatie(values[AutorisatieCsvConstants.KOLOM_AFNEMERSINDICATIE]);
        // "95.20 Afnemernaam"
        result.setAfnemernaam(values[AutorisatieCsvConstants.KOLOM_AFNEMERNAAM]);
        // "99.98 Datum ingang"
        result.setDatumIngang(SimpleParser.parseLo3Datum(values[AutorisatieCsvConstants.KOLOM_DATUM_INGANG]));
        // "99.99 Datum einde"
        result.setDatumEinde(SimpleParser.parseLo3Datum(values[AutorisatieCsvConstants.KOLOM_DATUM_EINDE]));
        // "95.12 Indicatie geheimhouding"
        result.setIndicatieGeheimhouding(SimpleParser.parseInteger(values[AutorisatieCsvConstants.KOLOM_INDICATIE_GEHEIMHOUDING]));
        // "95.13 Verstrekkingsbeperking"
        result.setVerstrekkingsbeperking(SimpleParser.parseInteger(values[AutorisatieCsvConstants.KOLOM_VERSTREKKINGSBEPERKING]));

        int offset;
        if (values.length == AANTAL_VELDEN_IN_LO_38) {
            // "95.30 Straatnaam"
            // "95.31 Huisnummer"
            // "95.32 Huisletter"
            // "95.33 Huisnummertoevoeging"
            // "95.35 Postcode"
            // "95.36 Gemeente"
            offset = 0;
        } else {
            offset = AANTAL_VELDEN_IN_LO_38 - AANTAL_VELDEN_IN_LO_39;
        }
        // "95.40 Rubrieknummer spontaan"
        result.setRubrieknummerSpontaan(values[AutorisatieCsvConstants.KOLOM_RUBRIEKNUMMER_SPONTAAN - offset]);
        // "95.41 Voorwaarderegel spontaan"
        result.setVoorwaarderegelSpontaan(values[AutorisatieCsvConstants.KOLOM_VOORWAARDEREGEL_SPONTAAN - offset]);
        // "95.42 Sleutelrubriek"
        result.setSleutelrubriek(values[AutorisatieCsvConstants.KOLOM_SLEUTEL_RUBRIEK - offset]);
        // "95.43 Conditionele verstrekking"
        result.setConditioneleVerstrekking(SimpleParser.parseInteger(values[AutorisatieCsvConstants.KOLOM_CONDITIONELE_VERSTREKKING - offset]));
        // "95.44 Medium spontaan"
        result.setMediumSpontaan(values[AutorisatieCsvConstants.KOLOM_MEDIUM_SPONTAAN - offset]);
        // "95.50 Rubrieknummer selectie"
        result.setRubrieknummerSelectie(values[AutorisatieCsvConstants.KOLOM_RUBRIEKNUMMER_SELECTIE - offset]);
        // "95.51 Voorwaarderegel selectie"
        result.setVoorwaarderegelSelectie(values[AutorisatieCsvConstants.KOLOM_VOORWAARDEREGEL_SELECTIE - offset]);
        // "95.52 Selectiesoort"
        result.setSelectiesoort(SimpleParser.parseInteger(values[AutorisatieCsvConstants.KOLOM_SELECTIESOORT - offset]));
        // "95.53 Berichtaanduiding"
        result.setBerichtaanduiding(SimpleParser.parseInteger(values[AutorisatieCsvConstants.KOLOM_BERICHTAANDUIDING - offset]));
        // "95.54 Eerste selectiedatum"
        result.setEersteSelectiedatum(SimpleParser.parseLo3Datum(values[AutorisatieCsvConstants.KOLOM_EERSTE_SELECTIEDATUM - offset]));
        // "95.55 Selectieperiode"
        result.setSelectieperiode(SimpleParser.parseInteger(values[AutorisatieCsvConstants.KOLOM_SELECTIEPERIODE - offset]));
        // "95.56 Medium selectie"
        result.setMediumSelectie(values[AutorisatieCsvConstants.KOLOM_MEDIUM_SELECTIE - offset]);
        // "95.60 Rubrieknummer ad hoc"
        result.setRubrieknummerAdHoc(values[AutorisatieCsvConstants.KOLOM_RUBRIEKNUMMER_AD_HOC - offset]);
        // "95.61 Voorwaarderegel ad hoc"
        result.setVoorwaarderegelAdHoc(values[AutorisatieCsvConstants.KOLOM_VOORWAARDEREGEL_AD_HOC - offset]);
        // "95.62 Plaatsingsbevoegdheid persoonslijst"
        result.setPlaatsingsbevoegdheidPersoonslijst(
                SimpleParser.parseInteger(values[AutorisatieCsvConstants.KOLOM_PLAATSINGSBEVOEGDHEID_PERSOONSLIJST - offset]));
        // "95.63 Afnemersverstrekking"
        result.setAfnemersverstrekking(values[AutorisatieCsvConstants.KOLOM_AFNEMERSVERSTREKKING - offset]);
        // "95.66 Adresvraag bevoegdheid"
        result.setAdresvraagBevoegdheid(SimpleParser.parseInteger(values[AutorisatieCsvConstants.KOLOM_ADRESVRAAG_BEVOEGDHEID - offset]));
        // "95.67 Medium ad hoc"
        result.setMediumAdHoc(values[AutorisatieCsvConstants.KOLOM_MEDIUM_AD_HOC - offset]);
        // "95.70 Rubrieknummer adresgeoriënteerd"
        result.setRubrieknummerAdresgeorienteerd(values[AutorisatieCsvConstants.KOLOM_RUBRIEKNUMMER_ADRESGEORIENTEERD - offset]);
        // "95.71 Voorwaarderegel adresgeoriënteerd"
        result.setVoorwaarderegelAdresgeorienteerd(values[AutorisatieCsvConstants.KOLOM_VOORWAARDEREGEL_ADRESGEORIENTEERD - offset]);
        // "95.73 Medium adresgeoriënteerd"
        result.setMediumAdresgeorienteerd(values[AutorisatieCsvConstants.KOLOM_MEDIUM_ADRESGEORIENTEERD - offset]);

        return result;
    }

    private String[] splitCsvLine(final String line) {
        final List<String> values = new ArrayList<>();

        int index = 0;
        while (index < line.length()) {
            final boolean startsWithQuote = line.charAt(index) == QUOTED_START;

            if (startsWithQuote) {
                int end = line.indexOf(QUOTED_END, index);
                if (end == -1) {
                    end = line.length() - 1;
                }
                final String value = line.substring(index + 1, end);
                if (!"".equals(value)) {
                    values.add(value);
                } else {
                    values.add(null);
                }
                index = end + 2;
            } else {
                int end = line.indexOf(SEPARATOR, index);
                if (end == -1) {
                    end = line.length() - 1;
                }
                final String value = line.substring(index, end);
                if (!"".equals(value)) {
                    values.add(value);
                } else {
                    values.add(null);
                }
                index = end + 1;
            }
        }

        if (line.endsWith(SEPARATOR)) {
            values.add(null);
        }

        return values.toArray(new String[values.size()]);
    }

    private String detectEncoding(final byte[] data, final int length) {
        final UniversalDetector detector = new UniversalDetector(null);
        detector.handleData(data, 0, length);
        detector.dataEnd();
        final String encoding = detector.getDetectedCharset();
        detector.reset();

        return encoding == null ? "UTF-8" : encoding;
    }

    /**
     * Aanname: hoeft niet te sorteren over afnemers heen; alleen binnen een afnemer voor de versies.
     */
    private static class AutorisatieInhoudDatumIngangDescendingComparator implements Comparator<Lo3AutorisatieInhoud>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final Lo3AutorisatieInhoud o1, final Lo3AutorisatieInhoud o2) {

            final Lo3Datum o1DatumIngang = SimpleParser.parseLo3Datum(o1.getDatumIngang() != null ? o1.getDatumIngang().getWaarde() : null);
            final Lo3Datum o2DatumIngang = SimpleParser.parseLo3Datum(o2.getDatumIngang() != null ? o2.getDatumIngang().getWaarde() : null);
            final Lo3Datum o1DatumEinde = SimpleParser.parseLo3Datum(o1.getDatumEinde() != null ? o1.getDatumEinde().getWaarde() : null);
            final Lo3Datum o2DatumEinde = SimpleParser.parseLo3Datum(o2.getDatumEinde() != null ? o2.getDatumEinde().getWaarde() : null);

            // Initieel vergelijken op 99.99 Einddatum.
            int resultaat = new DatumComparator().compare(o1DatumEinde, o2DatumEinde);

            // Indien vergelijken op 99.99 Einddatum gelijk resultaat geeft, extra vergelijken op 99.98 Ingangsdatum.
            if (resultaat == 0) {
                resultaat = new DatumComparator().compare(o1DatumIngang, o2DatumIngang);
            }

            return resultaat;
        }
    }

    /**
     * Vergelijker voor Lo3Datums die kan omgaan met null waarden.
     */
    private static class DatumComparator implements Comparator<Lo3Datum>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final Lo3Datum o1, final Lo3Datum o2) {

            final int resultaat;

            if (o1 == null) {
                if (o2 == null) {
                    resultaat = 0;
                } else {
                    resultaat = -1;
                }
            } else if (o2 == null) {
                resultaat = 1;
            } else {
                resultaat = o2.compareTo(o1);
            }

            return resultaat;
        }

    }
}
