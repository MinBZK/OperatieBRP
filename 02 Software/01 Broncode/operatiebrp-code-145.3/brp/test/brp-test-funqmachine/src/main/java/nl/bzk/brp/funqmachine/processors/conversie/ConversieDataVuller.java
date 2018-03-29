/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.processors.conversie;

import java.nio.file.Path;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface voor het vullen van de database met geconverteerde persoonslijsten.
 */
public interface ConversieDataVuller {

    /**
     * Converteert een LO3 persoonslijst als initiele vulling naar BRP.
     * @param persoonFile excelsheet met daarin de LO3 persoonslijst
     * @throws ConversieException treedt op als er tijdens conversie iets mis gaat
     */
    @Transactional
    void converteerInitieleVullingPL(Path persoonFile) throws ConversieException;


    /**
     * Lees de BRP Persoonslijst uit de database en map deze terug naar het conversie model. Het conversie model wordt gebruikt omdat dit model de
     * mogelijheid biedt om de inhoud naar een bestand weg te schrijven. Hiermee kan dit bestand als bv een expected result dienen.
     * @param anummer het anummer van de persoon wiens persoonslijst opgevraagd wordt uit de database
     * @return de gevraagde persoonslijst of null
     */
    BrpPersoonslijst vraagPersoonOpUitDatabase(String anummer);
}
