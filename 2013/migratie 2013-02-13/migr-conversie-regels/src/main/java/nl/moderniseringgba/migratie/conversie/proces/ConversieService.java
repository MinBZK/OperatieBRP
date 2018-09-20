/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;

import org.springframework.stereotype.Service;

/**
 * Deze class implementeert de services die geleverd worden voor de conversie.
 */
@Service
public interface ConversieService {

    /**
     * Converteert een Lo3Persoonslijst naar een BrpPersoonslijst. Hiervoor worden de volgende stappen uitgevoerd:
     * 
     * <ul>
     * <li>Stap 0: valideer LO3 model
     * <li>Stap 1a: extra documentatie (
     * {@link nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.Lo3ToevoegenExtraDocumentatie})</li>
     * <li>Stap 1b: splitsen verbintenissen (
     * {@link nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.Lo3SplitsenVerbintenis})</li>
     * <li>Stap 1c: splitsen ouders (
     * {@link nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.Lo3SplitsenGerelateerdeOuders})</li>
     * <li>Stap 2a: conversie inhoud (
     * {@link nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.Lo3InhoudNaarBrpConversieStap})</li>
     * <li>Stap 2b: verwijder redundantie (
     * {@link nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.Lo3InhoudRedundantie})</li>
     * <li>Stap 3: conversie historie (
     * {@link nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.Lo3HistorieConversie})</li>
     * <li>Stap 4: afgeleide gegevens (
     * {@link nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.Lo3BepalenAfgeleideGegevens})</li>
     * </ul>
     * 
     * @param lo3Persoonslijst
     *            de LO3 persoonslijst
     * @return een BrpPersoonslijst
     * @throws InputValidationException
     *             bij inhoudelijke fouten waardoor de brp persoonlijst niet gemaakt kan worden
     */
    BrpPersoonslijst converteerLo3Persoonslijst(final Lo3Persoonslijst lo3Persoonslijst)
            throws InputValidationException;

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
     * 
     * @param teConverterenPersoonslijst
     *            de te converteren BRP persoonslijst
     * @return een Lo3Persoonslijst
     */
    Lo3Persoonslijst converteerBrpPersoonslijst(final BrpPersoonslijst teConverterenPersoonslijst);
}
