/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

import java.lang.reflect.Field;
import java.util.Collection;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaEntiteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Bewaart informatie over bij elkaar horende stapels in de bestaande, opgeslagen persoonslijst en de nieuwe
 * (kluizenaar) persoonslijst.
 */
public final class DeltaStapelMatch {
    private final Collection<FormeleHistorie> opgeslagenRijen;
    private final Collection<FormeleHistorie> nieuweRijen;
    private final DeltaEntiteit eigenaarDeltaEntiteit;
    private final EntiteitSleutel eigenaarSleutel;
    private final Field eigenaarVeld;

    /**
     * Maak een DeltaStapelmatch instantie.
     * 
     * @param opgeslagenRijen
     *            de verzameling rijen in de opgeslagen persoonslijst
     * @param nieuweRijen
     *            de verzameling rijen in de nieuwe persoonslijst
     * @param eigenaarDeltaEntiteit
     *            de (opgeslagen) eigenaar entiteit van de rijen
     * @param eigenaarSleutel
     *            de sleutel van de eigenaar
     * @param eigenaarVeld
     *            het veld van de eigenaar entiteit waarin de verzameling is opgeslagen
     */
    public DeltaStapelMatch(
        final Collection<FormeleHistorie> opgeslagenRijen,
        final Collection<FormeleHistorie> nieuweRijen,
        final DeltaEntiteit eigenaarDeltaEntiteit,
        final EntiteitSleutel eigenaarSleutel,
        final Field eigenaarVeld)
    {
        this.opgeslagenRijen = opgeslagenRijen;
        this.nieuweRijen = nieuweRijen;
        this.eigenaarDeltaEntiteit = eigenaarDeltaEntiteit;
        this.eigenaarSleutel = eigenaarSleutel;
        this.eigenaarVeld = eigenaarVeld;
    }

    /**
     * @return de verzameling rijen in de opgeslagen persoonslijst
     */
    public Collection<FormeleHistorie> getOpgeslagenRijen() {
        return opgeslagenRijen;
    }

    /**
     * @return de verzameling rijen in de nieuwe persoonslijst
     */
    public Collection<FormeleHistorie> getNieuweRijen() {
        return nieuweRijen;
    }

    /**
     * @return de eigenaar entiteit van de stapels
     */
    public DeltaEntiteit getEigenaarDeltaEntiteit() {
        return eigenaarDeltaEntiteit;
    }

    /**
     * @return de sleutel van de eigenaar
     */
    public EntiteitSleutel getEigenaarSleutel() {
        return eigenaarSleutel;
    }

    /**
     * @return het veld van de eigenaar entiteit waarin de verzameling is opgeslagen
     */
    public Field getEigenaarVeld() {
        return eigenaarVeld;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("aantal opgeslagenRijen", opgeslagenRijen.size())
                                                                          .append("aantal nieuweRijen", nieuweRijen.size())
                                                                          .append("eigenaarEntiteit", eigenaarDeltaEntiteit)
                                                                          .append("eigenaarSleutel", eigenaarSleutel)
                                                                          .append("eigenaarVeld", eigenaarVeld)
                                                                          .toString();
    }
}
