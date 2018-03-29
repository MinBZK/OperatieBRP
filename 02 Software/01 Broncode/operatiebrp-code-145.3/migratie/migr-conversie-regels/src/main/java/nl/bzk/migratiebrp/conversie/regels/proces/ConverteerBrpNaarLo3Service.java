/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import org.springframework.stereotype.Service;

/**
 * Deze class implementeert de services die geleverd worden voor de terugconversie, van BRP naar Lo3.
 */
@Service
public interface ConverteerBrpNaarLo3Service {
    /**
     * Converteert een BrpPersoonslijst naar een Lo3Persoonslijst. Hiervoor worden de volgende stappen uitgevoerd:
     *
     * <ul>
     * <li>stap 1: bepalen gegevens in gegevens set</li>
     * <li>stap 2: bepalen materiele historie</li>
     * <li>stap 3: converteer inhoud & historie</li>
     * <li>stap 4: ouders samenvoegen</li>
     * <li>Stap 5: Opschonen relaties</li>
     * <li>stap 6: juridisch geen ouder toevoegen</li>
     * <li>stap 7: adellijke titel / predikaat bijwerken voor geslacht</li>
     * <li>Stap 8: Opschorten in geval van emigratie</li>
     * <li>Stap 9: Sorteren</li>
     * </ul>
     * @param teConverterenPersoonslijst de te converteren BRP persoonslijst
     * @return een Lo3Persoonslijst
     */
    Lo3Persoonslijst converteerBrpPersoonslijst(final BrpPersoonslijst teConverterenPersoonslijst);
}
