/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.definities.impl.afstamming;

import java.util.List;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;


/**
 * Deze klasse bevat definities voor kandidaat vader.
 *
 * @brp.bedrijfsregel BRPUC50110
 */
public interface KandidaatVader {

    /**
     * Bepaalt een kandidaat vader.
     *
     * @param moeder de Moeder
     * @param geboorteDatumKind geboorte datum van een kind
     * @return lijst van kandidaten vader
     */
    List<PersoonModel> bepaalKandidatenVader(final PersoonModel moeder, final Datum geboorteDatumKind);
}
