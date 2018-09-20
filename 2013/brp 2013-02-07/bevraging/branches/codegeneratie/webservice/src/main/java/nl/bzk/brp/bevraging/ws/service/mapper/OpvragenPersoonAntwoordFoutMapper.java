/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.mapper;

import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.ws.service.model.OpvragenPersoonAntwoordFout;


/**
 * Utility class voor het mappen van een {@link BerichtVerwerkingsFout} domein object naar een
 * {@link OpvragenPersoonAntwoordFout} DTO object.
 */
public final class OpvragenPersoonAntwoordFoutMapper {

    /**
     * Private constructor daar het hier om een utility class gaat die niet geinstantieert dient te worden.
     */
    private OpvragenPersoonAntwoordFoutMapper() {
    }

    /**
     * Utility methode voor het mappen van een @link BerichtVerwerkingsFout} domein object naar een
     * {@link OpvragenPersoonAntwoordFout} DTO object.
     *
     * @param foutDO het fout domein object.
     * @return een adres DTO object.
     */
    public static OpvragenPersoonAntwoordFout mapDomeinObjectNaarDTO(final BerichtVerwerkingsFout foutDO) {
        if (foutDO == null) {
            return null;
        }

        OpvragenPersoonAntwoordFout fout = new OpvragenPersoonAntwoordFout();
        fout.setId(foutDO.getCode());
        fout.setBeschrijving(foutDO.getMelding());
        if (foutDO.getZwaarte() != null) {
            fout.setToelichting(foutDO.getZwaarte().name());
        }
        return fout;
    }

}
