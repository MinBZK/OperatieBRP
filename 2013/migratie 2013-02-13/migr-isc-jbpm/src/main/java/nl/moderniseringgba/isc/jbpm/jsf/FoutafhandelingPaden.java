/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.jsf;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * DTO voor foutafhandeling.
 */
public final class FoutafhandelingPaden implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String PAD_ONBEKEND = "Pad '%s' is onbekend.";

    private final Map<String, Pad> paden = new LinkedHashMap<String, Pad>();
    private transient Map<String, String> selectItems;

    /**
     * Voeg een foutafhandeling pad toe.
     * 
     * @param pad
     *            pad naam
     * @param omschrijving
     *            pad omschrijving
     * @param pf
     *            indicatie gba cyclus eindigen
     * @param deblokkeren
     *            indicatie deblokkeren
     * @param antwoord
     *            indicatie brp antwoord
     */
    public void put(
            final String pad,
            final String omschrijving,
            final Boolean pf,
            final Boolean deblokkeren,
            final Boolean antwoord) {
        selectItems = null;
        paden.put(
                pad,
                new Pad(omschrijving, Boolean.TRUE.equals(pf), Boolean.TRUE.equals(deblokkeren), Boolean.TRUE
                        .equals(antwoord)));
    }

    /**
     * Geef de select items voor deze foutpaden.
     * 
     * @return select items
     */
    public Map<String, String> getSelectItems() {
        if (selectItems == null) {
            selectItems = new LinkedHashMap<String, String>();
            for (final Map.Entry<String, Pad> entry : paden.entrySet()) {
                selectItems.put(entry.getValue().getOmschrijving(), entry.getKey());
            }
        }
        return selectItems;
    }

    /**
     * Geef de indicatie pf voor een bepaald pad.
     * 
     * @param pad
     *            pad
     * @return indicatie pf
     */
    public boolean getPf(final String pad) {
        if (!paden.containsKey(pad)) {
            throw new IllegalArgumentException(String.format(PAD_ONBEKEND, pad));
        }
        return paden.get(pad).getPf();
    }

    /**
     * Geef de indicatie deblokkeren voor een bepaald pad.
     * 
     * @param pad
     *            pad
     * @return indicatie deblokkeren
     */
    public boolean getDeblokkeren(final String pad) {
        if (!paden.containsKey(pad)) {
            throw new IllegalArgumentException(String.format(PAD_ONBEKEND, pad));
        }
        return paden.get(pad).getDeblokkeren();
    }

    /**
     * Geef de indicatie antwoord voor een bepaald pad.
     * 
     * @param pad
     *            pad
     * @return indicatie antwoord
     */
    public boolean getAntwoord(final String pad) {
        if (!paden.containsKey(pad)) {
            throw new IllegalArgumentException(String.format(PAD_ONBEKEND, pad));
        }
        return paden.get(pad).getAntwoord();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("paden", paden).toString();
    }

    /**
     * Pad data.
     */
    private static class Pad implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String omschrijving;
        private final boolean pf;
        private final boolean deblokkeren;
        private final boolean antwoord;

        /**
         * Constructor.
         * 
         * @param omschrijving
         *            omschrijving
         * @param pf01
         *            indicatie gba cyclus beeindigen
         * @param deblok
         *            indicatie deblokkeren
         * @param antwoord
         *            indicatie antwoord
         */
        public Pad(final String omschrijving, final boolean pf, final boolean deblok, final boolean antwoord) {
            this.omschrijving = omschrijving;
            this.pf = pf;
            this.deblokkeren = deblok;
            this.antwoord = antwoord;
        }

        public String getOmschrijving() {
            return omschrijving;
        }

        public boolean getPf() {
            return pf;
        }

        public boolean getDeblokkeren() {
            return deblokkeren;
        }

        public boolean getAntwoord() {
            return antwoord;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                    .append("omschrijving", omschrijving).append("pf", pf).append("deblokkeren", deblokkeren)
                    .append("antwoord", antwoord).toString();
        }

    }

}
