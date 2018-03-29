/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenis;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.ControleException;

/**
 * Interface voor het inhoudelijk controleren van toevallige gebeurtenis verzoeken.
 */
public interface ToevalligeGebeurtenisControle {

    /**
     * Controleert op basis van een lo3 persoonslijst of de gegevens uit het toevallige gebeurtenis verzoek gelijk zijn.
     * Hierbij worden de gegevens uit het verzoek gecontroleerd tegen de actuele gegevens op de PL in BRP.
     * @param brpPersoon De persoon waar vanuit de bijhouding plaatsvindt en waarop de gegevens gevalideerd worden.
     * @param brpToevalligeGebeurtenis Het binnenkomende verzoek.
     * @throws nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.ControleException indien controle niet slaagt.
     */
    void controleer(BrpPersoonslijst brpPersoon, BrpToevalligeGebeurtenis brpToevalligeGebeurtenis) throws ControleException;

}
