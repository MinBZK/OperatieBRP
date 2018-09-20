/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.misc;

/**
 * Totalen dto.
 */
public class TotalenDto {
    private long totaalDuurGoed;
    private long totaalDuurFout;
    private int aantalGoed;
    private int aantalFout;

    /**
     * Verkrijgt totaal duur goed.
     *
     * @return totaal duur goed
     */
    public long getTotaalDuurGoed() {
        return totaalDuurGoed;
    }

    /**
     * Verkrijgt totaal duur fout.
     *
     * @return totaal duur fout
     */
    public long getTotaalDuurFout() {
        return totaalDuurFout;
    }

    /**
     * Verkrijgt aantal goed.
     *
     * @return aantal goed
     */
    public int getAantalGoed() {
        return aantalGoed;
    }

    /**
     * Verkrijgt aantal fout.
     *
     * @return aantal fout
     */
    public int getAantalFout() {
        return aantalFout;
    }

    /**
     * Zet totaal duur goed.
     *
     * @param totaalDuurGoed totaal duur goed
     */
    public void setTotaalDuurGoed(final long totaalDuurGoed) {
        this.totaalDuurGoed = totaalDuurGoed;
    }

    /**
     * Zet totaal duur fout.
     *
     * @param totaalDuurFout totaal duur fout
     */
    public void setTotaalDuurFout(final long totaalDuurFout) {
        this.totaalDuurFout = totaalDuurFout;
    }

    /**
     * Zet aantal goed.
     *
     * @param aantalGoed aantal goed
     */
    public void setAantalGoed(final int aantalGoed) {
        this.aantalGoed = aantalGoed;
    }

    /**
     * Zet aantal fout.
     *
     * @param aantalFout aantal fout
     */
    public void setAantalFout(final int aantalFout) {
        this.aantalFout = aantalFout;
    }
}
