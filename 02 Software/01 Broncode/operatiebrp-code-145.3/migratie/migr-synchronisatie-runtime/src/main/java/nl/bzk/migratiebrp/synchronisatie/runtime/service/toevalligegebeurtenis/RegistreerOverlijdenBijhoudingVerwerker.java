/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis;

import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingRegistreerOverlijdenMigVrz;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenis;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils.BerichtIdentificatieMaker;

/**
 * verwerkt een registreer overlijden bijhouding.
 */
@FunctionalInterface
public interface RegistreerOverlijdenBijhoudingVerwerker {

    /**
     * Vult inhoud van overlijden toevallige gebeurtenis.
     * @param idMaker id maker voor identiteit velden
     * @param opdracht brp bericht welke gevuld moet worden
     * @param verzoek orginele brp toevallige gebeurtenis bericht
     * @param rootPersoon Persoon om wie het draait
     */
    void maakBrpOpdrachtInhoud(
            final BerichtIdentificatieMaker idMaker,
            final BijhoudingRegistreerOverlijdenMigVrz opdracht,
            final BrpToevalligeGebeurtenis verzoek,
            final BrpPersoonslijst rootPersoon);
}
