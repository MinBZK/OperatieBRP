/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale.berichten;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;

/**
 * Deze class definieert de interface voor het GBA Afnemersindicatie bericht naar de BRP.
 *
 * Moet inhoudelijk overeenkomen met
 * <code>nl.bzk.migratiebrp.synchronisatie.runtime.bericht.brp.AfnemerindicatieOnderhoudOpdracht</code> uit het
 * Migratievoorzieningen model.
 */
@JsonInclude(Include.NON_NULL)
public final class AfnemerindicatieOnderhoudOpdracht {

    @JsonProperty
    private EffectAfnemerindicaties effectAfnemerindicatie;

    @JsonProperty
    private Integer toegangLeveringsautorisatieId;

    @JsonProperty
    private Integer dienstId;

    @JsonProperty
    private Integer persoonId;

    @JsonProperty
    private String referentienummer;

    /**
     * Geef het effect op de afnemerindicatie.
     *
     * @return het effet op de afnemerindicatie
     */
    public EffectAfnemerindicaties getEffectAfnemerindicatie() {
        return effectAfnemerindicatie;
    }

    /**
     * Zet het effect op de afnemerindicatie.
     *
     * @param effectAfnemerindicatie
     *            Het te zetten effect
     */
    public void setEffectAfnemerindicatie(final EffectAfnemerindicaties effectAfnemerindicatie) {
        this.effectAfnemerindicatie = effectAfnemerindicatie;
    }

    /**
     * Geef het persoon ID.
     *
     * @return het persoon ID
     */
    public Integer getPersoonId() {
        return persoonId;
    }

    /**
     * Zet het persoonId.
     *
     * @param persoonId
     *            Het te zetten persoonId
     */
    public void setPersoonId(final Integer persoonId) {
        this.persoonId = persoonId;
    }

    /**
     * Geef de leveringsautorisatie id.
     *
     * @return de leveringsautorisatie id
     */
    public Integer getToegangLeveringsautorisatieId() {
        return toegangLeveringsautorisatieId;
    }

    /**
     * Zet de toegang leveringsautorisatie id.
     *
     * @param toegangLeveringsautorisatieId
     *            De te zetten toegang leveringsautorisatie id
     */
    public void setToegangLeveringsautorisatieId(final Integer toegangLeveringsautorisatieId) {
        this.toegangLeveringsautorisatieId = toegangLeveringsautorisatieId;
    }

    /**
     * Geef het referentienummer.
     *
     * @return het referentienummer
     */
    public String getReferentienummer() {
        return referentienummer;
    }

    /**
     * Zet het referentienummer.
     *
     * @param referentienummer
     *            Het te zetten referentienummer
     */
    public void setReferentienummer(final String referentienummer) {
        this.referentienummer = referentienummer;
    }

    /**
     * Geef het dienst ID.
     *
     * @return het dienst ID
     */
    public Integer getDienstId() {
        return dienstId;
    }

    /**
     * Zet het dienst ID.
     *
     * @param dienstId
     *            Het te zetten dienst ID
     */
    public void setDienstId(final Integer dienstId) {
        this.dienstId = dienstId;
    }
}
