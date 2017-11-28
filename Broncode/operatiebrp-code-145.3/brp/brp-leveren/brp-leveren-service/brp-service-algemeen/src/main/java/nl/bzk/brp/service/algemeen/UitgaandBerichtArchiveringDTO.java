/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import java.time.ZonedDateTime;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;

/**
 * Archivering DTO voor uitgaande synchrone berichten.
 */
public final class UitgaandBerichtArchiveringDTO {
    private ZonedDateTime datumTijdVerzending;
    private SoortBericht soortBericht;
    private String referentienummerAttribuut;
    private String crossReferentienummerAttribuut;
    private Set<Long> teArchiverenPersonen;
    private SoortMelding hoogsteMeldingsNiveau;
    private String bericht;
    private VerwerkingsResultaat verwerkingsresultaat;

    private ToegangLeveringsAutorisatie toegangLeveringsautorisatie;

    /**
     * Gets bericht.
     * @return the bericht
     */
    public String getBericht() {
        return bericht;
    }

    /**
     * Gets verwerkingsresultaat.
     * @return the verwerkingsresultaat
     */
    public VerwerkingsResultaat getVerwerkingsresultaat() {
        return verwerkingsresultaat;
    }

    /**
     * Gets hoogste meldings niveau.
     * @return the hoogste meldings niveau
     */
    public SoortMelding getHoogsteMeldingsNiveau() {
        return hoogsteMeldingsNiveau;
    }

    /**
     * Gets datum tijd verzending.
     * @return the datum tijd verzending
     */
    public ZonedDateTime getDatumTijdVerzending() {
        return datumTijdVerzending;
    }

    /**
     * Gets soort bericht.
     * @return the soort bericht
     */
    public SoortBericht getSoortBericht() {
        return soortBericht;
    }

    /**
     * Gets referentienummer attribuut.
     * @return the referentienummer attribuut
     */
    public String getReferentienummerAttribuut() {
        return referentienummerAttribuut;
    }

    /**
     * Gets cross referentienummer attribuut.
     * @return the cross referentienummer attribuut
     */
    public String getCrossReferentienummerAttribuut() {
        return crossReferentienummerAttribuut;
    }

    /**
     * Gets te archiveren personen.
     * @return the te archiveren personen
     */
    public Set<Long> getTeArchiverenPersonen() {
        return teArchiverenPersonen;
    }

    /**
     * Gets toegang leveringsautorisatie.
     * @return the toegang leveringsautorisatie
     */
    public ToegangLeveringsAutorisatie getToegangLeveringsautorisatie() {
        return toegangLeveringsautorisatie;
    }

    /**
     * Sets toegang leveringsautorisatie.
     * @param toegangLeveringsautorisatie the toegang leveringsautorisatie
     */
    public void setToegangLeveringsautorisatie(final ToegangLeveringsAutorisatie toegangLeveringsautorisatie) {
        this.toegangLeveringsautorisatie = toegangLeveringsautorisatie;
    }

    /**
     * Sets datum tijd verzending.
     * @param datumTijdVerzending the datum tijd verzending
     */
    public void setDatumTijdVerzending(final ZonedDateTime datumTijdVerzending) {
        this.datumTijdVerzending = datumTijdVerzending;
    }

    /**
     * Sets soort bericht.
     * @param soortBericht the soort bericht
     */
    public void setSoortBericht(final SoortBericht soortBericht) {
        this.soortBericht = soortBericht;
    }

    /**
     * Sets referentienummer attribuut.
     * @param referentienummerAttribuut the referentienummer attribuut
     */
    public void setReferentienummerAttribuut(final String referentienummerAttribuut) {
        this.referentienummerAttribuut = referentienummerAttribuut;
    }

    /**
     * Sets cross referentienummer attribuut.
     * @param crossReferentienummerAttribuut the cross referentienummer attribuut
     */
    public void setCrossReferentienummerAttribuut(final String crossReferentienummerAttribuut) {
        this.crossReferentienummerAttribuut = crossReferentienummerAttribuut;
    }

    /**
     * Sets te archiveren personen.
     * @param teArchiverenPersonen the te archiveren personen
     */
    public void setTeArchiverenPersonen(final Set<Long> teArchiverenPersonen) {
        this.teArchiverenPersonen = teArchiverenPersonen;
    }

    /**
     * Sets hoogste meldings niveau.
     * @param hoogsteMeldingsNiveau the hoogste meldings niveau
     */
    public void setHoogsteMeldingsNiveau(final SoortMelding hoogsteMeldingsNiveau) {
        this.hoogsteMeldingsNiveau = hoogsteMeldingsNiveau;
    }

    /**
     * Sets bericht.
     * @param bericht the bericht
     */
    public void setBericht(final String bericht) {
        this.bericht = bericht;
    }

    /**
     * Sets verwerkingsresultaat.
     * @param verwerkingsresultaat the verwerkingsresultaat
     */
    public void setVerwerkingsresultaat(final VerwerkingsResultaat verwerkingsresultaat) {
        this.verwerkingsresultaat = verwerkingsresultaat;
    }
}
