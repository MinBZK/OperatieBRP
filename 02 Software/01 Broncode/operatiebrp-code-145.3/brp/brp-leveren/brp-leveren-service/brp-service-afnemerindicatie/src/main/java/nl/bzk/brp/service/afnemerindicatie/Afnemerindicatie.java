/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.afnemerindicatie;

/**
 * Afnemerindicatie.
 */
public final class Afnemerindicatie {

    private String bsn;
    private String partijCode;
    private String datumAanvangMaterielePeriode;
    private String datumEindeVolgen;

    /**
     * Gets bsn.
     * @return the bsn
     */
    public String getBsn() {
        return bsn;
    }

    /**
     * Sets bsn.
     * @param bsn the bsn
     */
    public void setBsn(final String bsn) {
        this.bsn = bsn;
    }

    /**
     * Gets partij code.
     * @return the partij code
     */
    public String getPartijCode() {
        return partijCode;
    }

    /**
     * Sets partij code.
     * @param partijCode the partij code
     */
    public void setPartijCode(final String partijCode) {
        this.partijCode = partijCode;
    }

    /**
     * Sets datum aanvang materiele periode.
     * @param datumAanvangMaterielePeriode the datum aanvang materiele periode
     */
    public void setDatumAanvangMaterielePeriode(final String datumAanvangMaterielePeriode) {
        this.datumAanvangMaterielePeriode = datumAanvangMaterielePeriode;
    }

    /**
     * Sets datum einde volgen.
     * @param datumEindeVolgen the datum einde volgen
     */
    public void setDatumEindeVolgen(final String datumEindeVolgen) {
        this.datumEindeVolgen = datumEindeVolgen;
    }

    /**
     * Gets datum aanvang materiele periode.
     * @return the datum aanvang materiele periode
     */
    public String getDatumAanvangMaterielePeriode() {
        return datumAanvangMaterielePeriode;
    }

    /**
     * Gets datum einde volgen.
     * @return the datum einde volgen
     */
    public String getDatumEindeVolgen() {
        return datumEindeVolgen;
    }
}
