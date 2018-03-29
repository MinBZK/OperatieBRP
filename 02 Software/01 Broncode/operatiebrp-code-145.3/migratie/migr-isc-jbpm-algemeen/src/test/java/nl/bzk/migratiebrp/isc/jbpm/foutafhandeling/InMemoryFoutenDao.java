/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.foutafhandeling;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryFoutenDao implements FoutenDao {

    private final AtomicLong sequence = new AtomicLong();
    private final Map<Long, Fout> store = new HashMap<>();

    @Override
    public long registreerFout(
            final String foutcode,
            final String foutmelding,
            final String proces,
            final long processId,
            final String bronPartijCode,
            final String doelPartijCode) {
        final Long id = sequence.incrementAndGet();
        final Fout fout = new Fout(foutcode, foutmelding, null, proces, processId, bronPartijCode, doelPartijCode);
        store.put(id, fout);
        return id;
    }

    @Override
    public void voegResolutieToe(final long id, final String resolutie) {
        final Fout fout = store.get(id);
        fout.setResolutie(resolutie);
        store.put(id, fout);
    }

    @Override
    public String haalFoutcodeOp(final long foutId) {
        final Fout fout = store.get(foutId);

        if (fout == null) {
            return null;
        }

        return fout.getFoutcode();
    }

    protected class Fout {

        private final String foutcode;
        private final String foutmelding;
        private String resolutie;
        private final String proces;
        private final long processId;
        private final String bronPartijCode;
        private final String doelPartijCode;

        protected Fout(
                final String foutcode,
                final String foutmelding,
                final String resolutie,
                final String proces,
                final long processId,
                final String bronPartijCode,
                final String doelPartijCode) {
            this.foutcode = foutcode;
            this.foutmelding = foutmelding;
            this.resolutie = resolutie;
            this.proces = proces;
            this.processId = processId;
            this.bronPartijCode = bronPartijCode;
            this.doelPartijCode = doelPartijCode;
        }

        /**
         * Geef de waarde van proces.
         * @return proces
         */
        public String getProces() {
            return proces;
        }

        /**
         * Geef de waarde van foutcode.
         * @return foutcode
         */
        public String getFoutcode() {
            return foutcode;
        }

        /**
         * Geef de waarde van foutmelding.
         * @return foutmelding
         */
        public String getFoutmelding() {
            return foutmelding;
        }

        /**
         * Geef de waarde van resolutie.
         * @return resolutie
         */
        public String getResolutie() {
            return resolutie;
        }

        /**
         * Zet de waarde van resolutie.
         * @param resolutie resolutie
         */
        public void setResolutie(final String resolutie) {
            this.resolutie = resolutie;
        }

        /**
         * Geef de waarde van process id.
         * @return process id
         */
        public long getProcessId() {
            return processId;
        }

        /**
         * Geef de waarde van bron gemeente.
         * @return bron gemeente
         */
        public String getBronPartijCode() {
            return bronPartijCode;
        }

        /**
         * Geef de waarde van doel gemeente.
         * @return doel gemeente
         */
        public String getDoelPartijCode() {
            return doelPartijCode;
        }

    }
}
