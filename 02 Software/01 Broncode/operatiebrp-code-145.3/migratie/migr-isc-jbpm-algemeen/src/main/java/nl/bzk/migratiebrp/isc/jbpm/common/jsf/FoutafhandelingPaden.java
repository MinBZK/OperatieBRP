/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.jsf;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * DTO voor foutafhandeling.
 */
public final class FoutafhandelingPaden implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String PAD_ONBEKEND = "Pad '%s' is onbekend.";

    private final Map<String, Pad> paden = new LinkedHashMap<>();
    private transient Map<String, String> selectItems;

    /**
     * Voeg een foutafhandeling pad toe.
     * @param pad pad naam
     * @param omschrijving pad omschrijving
     * @param pf indicatie gba cyclus eindigen
     * @param vb indicatie verstuur vrijbericht naar BRP
     */
    public void put(final String pad, final String omschrijving, final Boolean pf, final Boolean vb) {
        selectItems = null;
        paden.put(pad, new Pad(omschrijving, Boolean.TRUE.equals(pf), Boolean.TRUE.equals(vb)));
    }

    /**
     * Verwijder foutafhandeling paden.
     * @param teVerwijderenPaden te verwijderen pad namen
     */
    public void removeAll(final List<String> teVerwijderenPaden) {
        selectItems = null;
        for (final String teVerwijderenPad : teVerwijderenPaden) {
            paden.remove(teVerwijderenPad);
        }
    }

    /**
     * Geef de select items voor deze foutpaden.
     * @return select items
     */
    public Map<String, String> getSelectItems() {
        if (selectItems == null) {
            selectItems = new LinkedHashMap<>();
            for (final Map.Entry<String, Pad> entry : paden.entrySet()) {
                if (entry.getValue().getOmschrijving() != null) {
                    selectItems.put(entry.getValue().getOmschrijving(), entry.getKey());
                }
            }
        }
        return selectItems;
    }

    /**
     * Geef de indicatie pf voor een bepaald pad.
     * @param pad pad
     * @return indicatie pf
     */
    public boolean getPf(final String pad) {
        if (!paden.containsKey(pad)) {
            throw new IllegalArgumentException(String.format(PAD_ONBEKEND, pad));
        }
        return paden.get(pad).getPf();
    }

    /**
     * Geef de indicatie vb voor een bepaald pad.
     * @param pad pad
     * @return indicatie vb
     */
    public boolean getVb(final String pad) {
        if (!paden.containsKey(pad)) {
            throw new IllegalArgumentException(String.format(PAD_ONBEKEND, pad));
        }
        return paden.get(pad).getVb();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("paden", paden).toString();
    }


    /**
     * Pad data.
     */
    private static class Pad implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String omschrijving;
        private final boolean pf;
        private final boolean vb;

        /**
         * Constructor.
         * @param omschrijving omschrijving
         * @param pf indicatie gba cyclus beeindigen
         */
        public Pad(final String omschrijving, final boolean pf, final boolean vb) {
            this.omschrijving = omschrijving;
            this.pf = pf;
            this.vb = vb;
        }

        /**
         * Geef de waarde van omschrijving.
         * @return omschrijving
         */
        public String getOmschrijving() {
            return omschrijving;
        }

        /**
         * Geef de waarde van pf.
         * @return pf
         */
        public boolean getPf() {
            return pf;
        }

        /**
         * Geeft aan of er een vrijbericht gestuurd moet worden.
         * @return of er een vrijbericht gestuurd moet worden
         */
        public boolean getVb() {
            return vb;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                    .append("omschrijving", omschrijving)
                    .append("pf", pf)
                    .append("vb", vb)
                    .toString();
        }
    }
}
