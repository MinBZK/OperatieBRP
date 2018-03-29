/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.register;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Partij uit het partijregister.
 */
public class Partij implements Serializable {

    static final long serialVersionUID = 1L;

    private static final List<Rol> BIJHOUDERSROLLEN =
            Arrays.asList(Rol.BIJHOUDINGSORGAAN_MINISTER, Rol.BIJHOUDINGSORGAAN_COLLEGE);
    private static final String RNI_GEMEENTE_CODE = "1999";

    private final String partijCode;
    private final String gemeenteCode;
    private final Date datumOvergangNaarBrp;
    private final List<Rol> actuelePartijRollen;

    /**
     * Partij uit het partijregister.
     * @param partijCode code van partij
     * @param gemeenteCode code van de gemeente (indien de partij een gemeente is)
     * @param datumOvergangNaarBrp datum waarom partij overgaat naar brp
     * @param actuelePartijRollen actuele rollen die een partij heeft
     */
    public Partij(final String partijCode, final String gemeenteCode, final Date datumOvergangNaarBrp, final List<Rol> actuelePartijRollen) {
        this.partijCode = partijCode;
        this.gemeenteCode = gemeenteCode;
        this.datumOvergangNaarBrp = datumOvergangNaarBrp;
        this.actuelePartijRollen = actuelePartijRollen;
    }

    /**
     * Geef partijcode
     * @return partijcode
     */
    public String getPartijCode() {
        return partijCode;
    }

    /**
     * Geef gemeentecode
     * @return gemeentecode
     */
    public String getGemeenteCode() {
        if (isRNI()) {
            return RNI_GEMEENTE_CODE;
        } else {
            return gemeenteCode;
        }
    }

    /**
     * Geef datumovergang naar BRP
     * @return datumovergang naar BRP
     */
    public Date getDatumOvergangNaarBrp() {
        return datumOvergangNaarBrp;
    }

    /**
     * Geef actuele partij rollen
     * @return de actuele partij rollen
     */
    public List<Rol> getActuelePartijRollen() {
        return actuelePartijRollen;
    }

    /**
     * Geef het stelsel waarop de partij zich *OP DIT MOMENT (new Date())* bevindt.
     * @return Stelsel.GBA of Stelsel.BRP
     */
    public Stelsel getStelsel() {
        if (datumOvergangNaarBrp == null || datumOvergangNaarBrp.compareTo(new Date()) > 0) {
            return Stelsel.GBA;
        } else {
            return Stelsel.BRP;
        }
    }

    /**
     * Indicatie of partij afnemer is.
     * @return is partij afnemer
     */
    public boolean isAfnemer() {
        return actuelePartijRollen.contains(Rol.AFNEMER);
    }

    /**
     * Indicatie of partij bijhouder is.
     * @return is partij bijhouder.
     */
    public boolean isBijhouder() {
        return !Collections.disjoint(actuelePartijRollen, BIJHOUDERSROLLEN);
    }

    /**
     * Indicatie of partij de centrale voorziening is
     * @return is partij centrale voorziening
     */
    public boolean isCentraleVoorziening() {
        return "199902".equals(partijCode);
    }

    /**
     * Indicatie of partij de RNI is
     * @return is partij RNI
     */
    public boolean isRNI() {
        return "199901".equals(partijCode);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("partijCode", partijCode)
                .append("gemeenteCode", gemeenteCode)
                .append("stelsel", getStelsel())
                .append("datumOvergangNaarBrp", datumOvergangNaarBrp)
                .append("isAfnemer", isAfnemer())
                .append("isBijhouder", isBijhouder())
                .append("isCentraleVoorziening", isCentraleVoorziening())
                .append("rollen", actuelePartijRollen)
                .build();
    }
}
