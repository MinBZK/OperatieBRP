/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.controle.rapport;

/**
 * BijzondereSituatie bevat de melding en de PL(en) waar de melding bij hoort. Dit kunnnen verwachte en onverwachte
 * meldingen zijn.
 */
public class BijzondereSituatie {

    private String lo3Pl;
    private String brpPl;
    private String foutmelding;

    /**
     * Constructor waarbij de 3 velden direct meegegeven worden.
     * 
     * @param lo3Pl
     *            De lo3 pl.
     * @param brpPl
     *            De brp pl.
     * @param melding
     *            De melding of foutmelding.
     */
    public BijzondereSituatie(final String lo3Pl, final String brpPl, final String melding) {
        this.lo3Pl = lo3Pl;
        this.brpPl = brpPl;
        foutmelding = melding;
    }

    /**
     * @return the lo3Pl
     */
    public final String getLo3Pl() {
        return lo3Pl;
    }

    /**
     * @param lo3Pl
     *            the lo3Pl to set
     */
    public final void setLo3Pl(final String lo3Pl) {
        this.lo3Pl = lo3Pl;
    }

    /**
     * @return the brpPl
     */
    public final String getBrpPl() {
        return brpPl;
    }

    /**
     * @param brpPl
     *            the brpPl to set
     */
    public final void setBrpPl(final String brpPl) {
        this.brpPl = brpPl;
    }

    /**
     * @return the foutmelding
     */
    public final String getFoutmelding() {
        return foutmelding;
    }

    /**
     * @param foutmelding
     *            the foutmelding to set
     */
    public final void setFoutmelding(final String foutmelding) {
        this.foutmelding = foutmelding;
    }
}
