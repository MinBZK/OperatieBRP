/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.groep;

import nl.bzk.brp.model.logisch.AbstractIdentificerendeGroep;

/**
 *
 *
 */

public class PersoonInschrijving extends AbstractIdentificerendeGroep {
    private Integer datumInschrijving;
    private Long versienummer;
    private Long vorigPersoonId;
    private Long volgendPersoonId;
    private String vorigBsn;
    private String volgendBsn;
    private String vorigAnr;
    private String volgendAnr;
    /**
     * @return the datumInschrijving
     */
    public Integer getDatumInschrijving() {
        return datumInschrijving;
    }
    /**
     * @param datumInschrijving the datumInschrijving to set
     */
    public void setDatumInschrijving(final Integer datumInschrijving) {
        this.datumInschrijving = datumInschrijving;
    }
    /**
     * @return the versienummer
     */
    public Long getVersienummer() {
        return versienummer;
    }
    /**
     * @param versienummer the versienummer to set
     */
    public void setVersienummer(final Long versienummer) {
        this.versienummer = versienummer;
    }
    /**
     * @return the vorigPersoonId
     */
    public Long getVorigPersoonId() {
        return vorigPersoonId;
    }
    /**
     * @param vorigPersoonId the vorigPersoonId to set
     */
    public void setVorigPersoonId(final Long vorigPersoonId) {
        this.vorigPersoonId = vorigPersoonId;
    }
    /**
     * @return the volgendPersoonId
     */
    public Long getVolgendPersoonId() {
        return volgendPersoonId;
    }
    /**
     * @param volgendPersoonId the volgendPersoonId to set
     */
    public void setVolgendPersoonId(final Long volgendPersoonId) {
        this.volgendPersoonId = volgendPersoonId;
    }
    /**
     * @return the vorigBsn
     */
    public String getVorigBsn() {
        return vorigBsn;
    }
    /**
     * @param vorigBsn the vorigBsn to set
     */
    public void setVorigBsn(final String vorigBsn) {
        this.vorigBsn = vorigBsn;
    }
    /**
     * @return the volgendBsn
     */
    public String getVolgendBsn() {
        return volgendBsn;
    }
    /**
     * @param volgendBsn the volgendBsn to set
     */
    public void setVolgendBsn(final String volgendBsn) {
        this.volgendBsn = volgendBsn;
    }
    /**
     * @return the vorigAnr
     */
    public String getVorigAnr() {
        return vorigAnr;
    }
    /**
     * @param vorigAnr the vorigAnr to set
     */
    public void setVorigAnr(final String vorigAnr) {
        this.vorigAnr = vorigAnr;
    }
    /**
     * @return the volgendAnr
     */
    public String getVolgendAnr() {
        return volgendAnr;
    }
    /**
     * @param volgendAnr the volgendAnr to set
     */
    public void setVolgendAnr(final String volgendAnr) {
        this.volgendAnr = volgendAnr;
    }
}
