/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.autorisatie;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.service.algemeen.request.OIN;
import org.springframework.util.Assert;

/**
 * Parameter object voor de LeveringsautorisatieValidatieService.
 */
public final class AutorisatieParams {
    private String zendendePartijCode;
    private int leveringautorisatieId;
    private OIN oin;
    private SoortDienst soortDienst;
    private Integer dienstId;
    private Rol rol;
    private boolean brpKoppelvlakVerzoek;

    /**
     * @return maakt een builder voor het parameter object.
     */
    public static Builder maakBuilder() {
        return new Builder();
    }

    /**
     * @return code van de zendende partij
     */
    public String getZendendePartijCode() {
        return zendendePartijCode;
    }

    /**
     * @return id van de leveringsautorisatie
     */
    public int getLeveringautorisatieId() {
        return leveringautorisatieId;
    }

    /**
     * @return oin
     */
    public OIN getOin() {
        return oin;
    }

    /**
     * @return soort dient van het verzoek
     */
    public SoortDienst getSoortDienst() {
        return soortDienst;
    }

    /**
     * @return id van de dienst
     */
    public Integer getDienstId() {
        return dienstId;
    }

    /**
     * @return de rol van de partijrol
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * @return is verzoek via koppelvlak
     */
    public boolean isBrpKoppelvlakVerzoek() {
        return brpKoppelvlakVerzoek;
    }

    /**
     * Builder class voor AutorisatieParams.
     */
    public static final class Builder {

        private String zendendePartijCode;
        private int leveringautorisatieId;
        private OIN oin;
        private SoortDienst soortDienst;
        private Integer dienstId;
        private Rol rol;
        private boolean verzoekViaBrpKoppelvlak;

        private Builder() {

        }

        /**
         * Zet de code van de zendende partij.
         * @param code code van de zendende partij.
         * @return deze builder
         */
        public Builder metZendendePartijCode(final String code) {
            this.zendendePartijCode = code;
            return this;
        }

        /**
         * Zet het id van de leveringautorisatie.
         * @param id id van de leveringautorisatie
         * @return deze builder
         */
        public Builder metLeveringautorisatieId(final int id) {
            this.leveringautorisatieId = id;
            return this;
        }

        /**
         * Zet de OIN (optioneel).
         * @param oin een OIN
         * @return deze builder
         */
        public Builder metOIN(final OIN oin) {
            this.oin = oin;
            return this;
        }

        /**
         * Zet de SoortDienst.
         * @param soort een SoortDienst
         * @return deze builder
         */
        public Builder metSoortDienst(final SoortDienst soort) {
            this.soortDienst = soort;
            return this;
        }

        /**
         * Zet het id van de dienst (optioneel).
         * @param id van een dienst
         * @return deze builder
         */
        public Builder metDienstId(final Integer id) {
            this.dienstId = id;
            return this;
        }

        /**
         * Zet route van verzoek.
         * @param verzoekViaKoppelvlak (wel/niet of via koppelvlak)
         * @return deze builder
         */
        public Builder metVerzoekViaKoppelvlak(final boolean verzoekViaKoppelvlak) {
            this.verzoekViaBrpKoppelvlak = verzoekViaKoppelvlak;
            return this;
        }

        /**
         * Zet de rol (optioneel).
         * @param rolNaam de rol
         * @return deze builder
         */
        public Builder metRol(final String rolNaam) {
            if (rolNaam != null) {
                final Rol[] values = Rol.values();
                for (Rol value : values) {
                    if (value.getNaam().equals(rolNaam)) {
                        this.rol = value;
                        break;
                    }
                }
            }
            return this;
        }

        /**
         * Build het object.
         * @return het AutorisatieParams object.
         */
        public AutorisatieParams build() {

            Assert.notNull(leveringautorisatieId, "LeveringsautorisatieId is verplicht");
            Assert.notNull(zendendePartijCode, "Zendende partijCode is verplicht");
            Assert.notNull(soortDienst, "SoortDienst is verplicht");

            final AutorisatieParams params = new AutorisatieParams();
            params.leveringautorisatieId = leveringautorisatieId;
            params.zendendePartijCode = zendendePartijCode;
            params.oin = oin == null ? new OIN(null, null) : oin;
            params.soortDienst = soortDienst;
            params.dienstId = dienstId;
            params.rol = rol;
            params.brpKoppelvlakVerzoek = verzoekViaBrpKoppelvlak;
            return params;
        }
    }
}
