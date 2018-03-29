/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import java.time.ZonedDateTime;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;

/**
 * Archivering DTO voor ingaande synchrone berichten.
 */
public final class IngaandBerichtArchiveringDTO {

    private String referentienummer;
    private String systeemNaam;
    private SoortBericht soortBericht;
    private String bericht;
    private Integer rol;
    private ZonedDateTime datumTijdOntvangst;
    private ZonedDateTime datumTijdVerzending;
    private Set<Long> teArchiverenPersonen;
    private Integer dienstId;
    private Integer leveringsAutorisatieId;
    private Short zendendePartijId;

    /**
     * Gets referentienummer.
     * @return the referentienummer
     */
    public String getReferentienummer() {
        return referentienummer;
    }

    /**
     * Gets systeem naam.
     * @return the systeem naam
     */
    public String getSysteemNaam() {
        return systeemNaam;
    }

    /**
     * Gets soort bericht.
     * @return the soort bericht
     */
    public SoortBericht getSoortBericht() {
        return soortBericht;
    }

    /**
     * Gets datum tijd ontvangst.
     * @return the datum tijd ontvangst
     */
    public ZonedDateTime getDatumTijdOntvangst() {
        return datumTijdOntvangst;
    }

    /**
     * Gets te archiveren personen.
     * @return the te archiveren personen
     */
    public Set<Long> getTeArchiverenPersonen() {
        return teArchiverenPersonen;
    }

    /**
     * Sets te archiveren personen.
     * @param teArchiverenPersonen the te archiveren personen
     */
    public void setTeArchiverenPersonen(final Set<Long> teArchiverenPersonen) {
        this.teArchiverenPersonen = teArchiverenPersonen;
    }

    /**
     * Gets bericht.
     * @return the bericht
     */
    public String getBericht() {
        return bericht;
    }

    /**
     * Sets datum tijd ontvangst.
     * @param datumTijdOntvangst the datum tijd ontvangst
     */
    public void setDatumTijdOntvangst(final ZonedDateTime datumTijdOntvangst) {
        this.datumTijdOntvangst = datumTijdOntvangst;
    }

    /**
     * Gets datum tijd verzending.
     * @return the datum tijd verzending
     */
    public ZonedDateTime getDatumTijdVerzending() {
        return datumTijdVerzending;
    }

    /**
     * Sets datum tijd verzending.
     * @param datumTijdVerzending the datum tijd verzending
     */
    public void setDatumTijdVerzending(final ZonedDateTime datumTijdVerzending) {
        this.datumTijdVerzending = datumTijdVerzending;
    }

    /**
     * Gets rol.
     * @return the rol
     */
    public Integer getRol() {
        return rol;
    }

    /**
     * Sets referentienummer.
     * @param referentienummer the referentienummer
     */
    public void setReferentienummer(final String referentienummer) {
        this.referentienummer = referentienummer;
    }

    /**
     * Sets systeem naam.
     * @param systeemNaam the systeem naam
     */
    public void setSysteemNaam(final String systeemNaam) {
        this.systeemNaam = systeemNaam;
    }

    /**
     * Sets soort bericht.
     * @param soortBericht the soort bericht
     */
    public void setSoortBericht(final SoortBericht soortBericht) {
        this.soortBericht = soortBericht;
    }

    /**
     * Sets bericht.
     * @param bericht the bericht
     */
    public void setBericht(final String bericht) {
        this.bericht = bericht;
    }

    /**
     * Sets rol.
     * @param rol the rol
     */
    public void setRol(final Integer rol) {
        this.rol = rol;
    }

    /**
     * Sets dienst id.
     * @param dienstId the dienst id
     */
    public void setDienstId(Integer dienstId) {
        this.dienstId = dienstId;
    }

    /**
     * Gets dienst id.
     * @return the dienst id
     */
    public Integer getDienstId() {
        return dienstId;
    }

    /**
     * Sets leverings autorisatie id.
     * @param leveringsAutorisatieId the leverings autorisatie id
     */
    public void setLeveringsAutorisatieId(Integer leveringsAutorisatieId) {
        this.leveringsAutorisatieId = leveringsAutorisatieId;
    }

    /**
     * Gets leverings autorisatie id.
     * @return the leverings autorisatie id
     */
    public Integer getLeveringsAutorisatieId() {
        return leveringsAutorisatieId;
    }

    /**
     * Sets zendende partij id.
     * @param zendendePartijId the zendende partij id
     */
    public void setZendendePartijId(Short zendendePartijId) {
        this.zendendePartijId = zendendePartijId;
    }

    /**
     * Gets zendende partij id.
     * @return the zendende partij id
     */
    public Short getZendendePartijId() {
        return zendendePartijId;
    }

}
