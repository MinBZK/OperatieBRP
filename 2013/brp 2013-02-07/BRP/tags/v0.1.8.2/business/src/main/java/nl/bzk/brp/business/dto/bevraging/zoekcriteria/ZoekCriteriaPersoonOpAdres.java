/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging.zoekcriteria;


/**
 *
 *
 */
public class ZoekCriteriaPersoonOpAdres extends ZoekCriteriaBsn {
    private String gemeentecode;
    private String naamOpenbareRuimte;
    private Integer huisnummer;
    private String huisletter;
    private String huisnummertoevoeging;
    private String postcode;

    public String getGemeentecode() {
        return gemeentecode;
    }

    public void setGemeentecode(final String gemeentecode) {
        this.gemeentecode = gemeentecode;
    }

    public String getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    public void setNaamOpenbareRuimte(final String naamOpenbareRuimte) {
        this.naamOpenbareRuimte = naamOpenbareRuimte;
    }

    public Integer getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(final Integer huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getHuisletter() {
        return huisletter;
    }

    public void setHuisletter(final String huisletter) {
        this.huisletter = huisletter;
    }

    public String getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    public void setHuisnummertoevoeging(final String huisnummertoevoeging) {
        this.huisnummertoevoeging = huisnummertoevoeging;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(final String postcode) {
        this.postcode = postcode;
    }

}
