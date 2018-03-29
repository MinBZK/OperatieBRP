/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.domain.afnemerindicatie;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EffectAfnemerindicaties;

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
    private String partijCode;

    @JsonProperty
    private String bsn;

    @JsonProperty
    private String referentienummer;

    /**
     * Geef het effect op de afnemerindicatie.
     * @return het effet op de afnemerindicatie
     */
    public EffectAfnemerindicaties getEffectAfnemerindicatie() {
        return effectAfnemerindicatie;
    }

    /**
     * Zet het effect op de afnemerindicatie.
     * @param effectAfnemerindicatie Het te zetten effect
     */
    public void setEffectAfnemerindicatie(final EffectAfnemerindicaties effectAfnemerindicatie) {
        this.effectAfnemerindicatie = effectAfnemerindicatie;
    }

    /**
     * Geef het bsn.
     * @return het bsn
     */
    public String getBsn() {
        return bsn;
    }

    /**
     * Zet het bsn.
     * @param bsn Het te bsn
     */
    public void setBsn(final String bsn) {
        this.bsn = bsn;
    }

    /**
     * Geef de partijcode.
     * @return de partijcode
     */
    public String getPartijCode() {
        return partijCode;
    }

    /**
     * Zet de partij code.
     * @param partijCode De te zetten partij code
     */
    public void setPartijCode(final String partijCode) {
        this.partijCode = partijCode;
    }

    /**
     * Geef het referentienummer.
     * @return het referentienummer
     */
    public String getReferentienummer() {
        return referentienummer;
    }

    /**
     * Zet het referentienummer.
     * @param referentienummer Het te zetten referentienummer
     */
    public void setReferentienummer(final String referentienummer) {
        this.referentienummer = referentienummer;
    }
}
