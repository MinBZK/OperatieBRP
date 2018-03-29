/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.schrijver.gba;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * AM Bestand writer.
 */
public class SelectieResultaatGbaBerichtWriter {

    private static final String DATETIME_FORMAT = "YYMMddHHmm'Z'";
    private static final String VERSION = "02.50";
    private static final String VOLUME_SEQUNECE_NUMBER = "001";
    private static final int RECORD_SIZE = 512;
    private static final int MAX_SPD_SIZE = 24_000;
    private static final int MAX_RECORD_SIZE = ((int) Math.ceil((double) MAX_SPD_SIZE / RECORD_SIZE)) * RECORD_SIZE;
    private static final String LENGTH_FORMAT = "00000";
    private static final Character PADDING = '*';
    private static final String TERMINATION_RECORD = "00000";
    private static final int LENGTE_MAILBOXNUMMER = 7;
    private static final int LENGTE_MESSAGE_ID = 12;
    private static final int LENGTE_SPD_ENVELOPE = 24;
    private static final int LENGTE_SPD_HEADER = 45;
    private static final long LENGTE_BODY_PREFIX = 3L;

    private final String originator;
    private final String recipient;
    private OutputStream out;

    /**
     * Default constructor.
     * @param path het pad waar het bestand wordt weggeschreven
     * @param originator de verzendende instantie
     * @param recipient de ontvangende instantie
     * @throws IOException in het geval er iets misgaat bij het wegschrijven van het bestand
     */
    public SelectieResultaatGbaBerichtWriter(final Path path, final String originator, final String recipient) throws IOException {
        checkLength(originator, LENGTE_MAILBOXNUMMER);
        checkLength(recipient, LENGTE_MAILBOXNUMMER);

        this.originator = originator;
        this.recipient = recipient;

        final OpenOption[] openOptions = {StandardOpenOption.CREATE, StandardOpenOption.APPEND};
        out = new BufferedOutputStream(Files.newOutputStream(path, openOptions));

        schrijfStuurRecord();
    }

    private void schrijfStuurRecord() throws IOException {
        final Record stuurRecord = new Record();
        stuurRecord.addBytes(originator);
        stuurRecord.addBytes(DateTimeFormatter.ofPattern(DATETIME_FORMAT).format(LocalDateTime.now()));
        stuurRecord.addBytes(VERSION);
        stuurRecord.addBytes(VOLUME_SEQUNECE_NUMBER);
        stuurRecord.addBytes(new DecimalFormat(LENGTH_FORMAT).format(RECORD_SIZE));
        stuurRecord.addBytes(PADDING.toString());
        stuurRecord.write(out);
    }

    /**
     * Schrijft het bericht voor een persoon weg.
     * @param messageId het id van het bericht
     * @param data de inhoud van het bericht
     * @throws IOException in het geval er iets misgaat bij het wegschrijven van het bestand
     */
    public void schrijfDataRecord(String messageId, String data) throws IOException {
        checkLength(messageId, LENGTE_MESSAGE_ID);

        final Record dataRecord = new Record();

        // sPd envelope (24 characters)
        dataRecord.addBytes(new DecimalFormat(LENGTH_FORMAT).format(LENGTE_SPD_ENVELOPE));
        dataRecord.addBytes("120");
        dataRecord.addBytes(originator);
        dataRecord.addBytes("2");
        dataRecord.addBytes("0");
        dataRecord.addBytes("           ");
        dataRecord.addBytes("0");

        // sPd header (45 characters)
        dataRecord.addBytes(new DecimalFormat(LENGTH_FORMAT).format(LENGTE_SPD_HEADER));
        dataRecord.addBytes("150");
        dataRecord.addBytes(messageId);
        dataRecord.addBytes("            ");
        dataRecord.addBytes(originator);
        dataRecord.addBytes("001");
        dataRecord.addBytes(recipient);
        dataRecord.addBytes("0");

        // sPd body
        dataRecord.addBytes(new DecimalFormat(LENGTH_FORMAT).format(LENGTE_BODY_PREFIX + data.length()));
        dataRecord.addBytes("180");
        dataRecord.addBytes(data);

        // write
        dataRecord.write(out);
    }


    /**
     * Schrijft het afsluitrecord in het bestand.
     * @throws IOException in het geval er iets misgaat bij het wegschrijven van het bestand
     */
    public void schrijfAfsluitRecord() throws IOException {
        final Record afsluitRecord = new Record();
        afsluitRecord.addBytes(TERMINATION_RECORD);
        afsluitRecord.write(out);
        out.close();
    }


    private void checkLength(final String value, final int length) {
        if (value.length() != length) {
            throw new IllegalArgumentException("Waarde heeft niet de juiste lengte");
        }
    }

    /**
     * Record
     */
    private static final class Record {
        private final byte[] data = new byte[MAX_RECORD_SIZE];
        private int index;

        /**
         * default constructor
         */
        public Record() {
            for (int i = 0; i < data.length; i++) {
                data[i] = '*';
            }
        }

        /**
         * plaatst @bytes in Record
         * @param bytes de te plaatsen bytes
         */
        public void addBytes(final byte[] bytes) {
            System.arraycopy(bytes, 0, data, index, bytes.length);
            index += bytes.length;
        }

        /**
         * plaatst de bytes van String value in Record
         * @param value de te plaatsen waarde
         */
        public void addBytes(final String value) {
            addBytes(value.getBytes(Charset.defaultCharset()));
        }

        /**
         * schrijft de inhoud van dit record naar @output
         * @param output de schrijven outputstream
         */
        public void write(final OutputStream output) throws IOException {
            int blocks = index / RECORD_SIZE;
            final int remainder = index % RECORD_SIZE;
            if (remainder > 0) {
                blocks++;
            }

            final int size = blocks * RECORD_SIZE;

            output.write(data, 0, size);
        }
    }
}
