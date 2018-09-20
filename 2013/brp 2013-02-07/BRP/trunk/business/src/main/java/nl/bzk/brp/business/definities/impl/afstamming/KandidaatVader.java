/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.definities.impl.afstamming;

import java.util.List;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;

/**
 * Deze klasse bevat definities voor kandidaat vader.
 *
 * @brp.bedrijfsregel BRPUC50110
 */
public interface KandidaatVader {

    /**
     * Haal een lijst van personen uit de database die de kandiddaat vader zou kunnen zijn.
     * @param moeder de moeder van het kind
     * @param geboorteDatumKind de geboorte datum van het (op te bepalen relatie op peil datum)
     * @return de lijst van personen.
     */
    List<PersoonModel> bepaalKandidatenVader(final PersoonModel moeder, final Datum geboorteDatumKind);
}
