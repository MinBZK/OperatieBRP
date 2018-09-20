/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.amlezer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Schrijft een AM-bestand.
 */
public final class AMSchrijver {

    private static final int ORIGINATOR_SIZE = 7;
    private static final String DATE_TIME_FORMAT = "yyMMddHHmm'Z'";

    private final File file;
    private OutputStream output;

    /**
     * Constructor.
     * 
     * @param file
     *            output bestand
     */
    public AMSchrijver(final File file) {
        this.file = file;
    }

    /**
     * Schrijf het start record.
     * 
     * @param originator
     *            voor wie het AM-bestand bedoelt is.
     * @throws IOException
     *             bij schrijf fouten
     */
    public void schrijfStartRecord(final String originator) throws IOException {
        file.getParentFile().mkdirs();
        output = new FileOutputStream(file);

        if (!(originator.getBytes().length == ORIGINATOR_SIZE)) {
            throw new IllegalArgumentException("Originator moet 7 lang zijn.");
        }

        final Record startRecord = new Record();
        startRecord.addBytes(originator);
        startRecord.addBytes(new SimpleDateFormat(DATE_TIME_FORMAT).format(new Date()));
        startRecord.addBytes("02.40");
        startRecord.addBytes("001");
        startRecord.addBytes("00512");
        startRecord.addBytes("*");
        startRecord.write(output);
    }

    /**
     * Schrijf een data record (moet een PutMessage bericht zijn in Spd-formaat).
     * 
     * @param bericht
     *            bericht
     * @throws IOException
     *             bij schrijf fouten
     */
    public void schrijfDataRecord(final String bericht) throws IOException {
        final Record dataRecord = new Record();
        dataRecord.addBytes(bericht);
        dataRecord.write(output);
    }

    /**
     * Schrijf het eind record.
     * 
     * @throws IOException
     *             bij schrijf fouten
     */
    public void schrijfEndRecord() throws IOException {
        final Record endRecord = new Record();
        endRecord.addBytes("00000");
        endRecord.write(output);

        output.close();
    }

    /**
     * Geeft een te schrijven record weer.
     */
    private static final class Record {
        private static final int BLOCK_SIZE = 512;
        private static final int MAX_RECORD_SIZE = 40 * BLOCK_SIZE;

        private final byte[] record = new byte[MAX_RECORD_SIZE];
        private int index;

        /**
         * default constructor
         */
        public Record() {
            for (int i = 0; i < record.length; i++) {
                record[i] = '*';
            }
        }

        /**
         * plaatst @bytes in Record
         * 
         * @param bytes
         */
        public void addBytes(final byte[] bytes) {
            System.arraycopy(bytes, 0, record, index, bytes.length);
            index += bytes.length;
        }

        /**
         * plaatst de bytes van String value in Record
         * 
         * @param value
         */
        public void addBytes(final String value) {
            addBytes(value.getBytes());
        }

        /**
         * schrijft de inhoud van dit record naar @output
         * 
         * @param output
         * @throws IOException
         */
        public void write(final OutputStream output) throws IOException {
            int blocks = index / BLOCK_SIZE;
            final int remainder = index % BLOCK_SIZE;
            if (remainder > 0) {
                blocks++;
            }

            final int size = blocks * BLOCK_SIZE;

            output.write(record, 0, size);
        }
    }
}
