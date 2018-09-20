/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import org.springframework.stereotype.Service;

/**
 * Deze class implementeert de services die geleverd worden voor de conversie van Lo3 naar BRP.
 */
@Service
public interface ConverteerLo3NaarBrpService {

    /**
     * Converteert een Lo3Persoonslijst naar een BrpPersoonslijst. Hiervoor worden de volgende stappen uitgevoerd:
     *
     * <ul>
     * <li>Stap 0: valideer LO3 model
     * <li>Stap 1: conversie inhoud (
     * {@link nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.Lo3InhoudNaarBrpConversieStap})</li>
     * <li>Stap 2: conversie historie (
     * {@link nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.Lo3HistorieConversie})</li>
     * <li>Stap 3: afgeleide gegevens (
     * {@link nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.Lo3BepalenAfgeleideGegevens})</li>
     * </ul>
     *
     * @param lo3Persoonslijst
     *            de LO3 persoonslijst
     * @return een BrpPersoonslijst
     */
    BrpPersoonslijst converteerLo3Persoonslijst(final Lo3Persoonslijst lo3Persoonslijst);

    /**
     * Converteert een Lo3Autorisatie naar een BrpAutorisatie. Hiervoor worden de volgende stappen uitgevoerd:
     *
     * <ul>
     * <li>Stap 1: conversie inhoud ()</li>
     * </ul>
     *
     * @param lo3Autorisatie
     *            De te converteren autorisaties.
     * @return een BrpAutorisatie
     * @throws Lo3SyntaxException
     *             In het geval van een conversie probleem.
     */
    BrpAutorisatie converteerLo3Autorisatie(final Lo3Autorisatie lo3Autorisatie) throws Lo3SyntaxException;

    /**
     * Converteert een Lo3Afnemersindicatie naar een BrpAfnemersindicaties. Hiervoor worden de volgende stappen
     * uitgevoerd:
     *
     * <ul>
     * <li>Stap 1: conversie inhoud ()</li>
     * <li>Stap 2: conversie historie (
     * {@link nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.Lo3HistorieConversie})</li>
     * </ul>
     *
     * @param lo3Afnemersindicaties
     *            De te converteren afnemers indicaties.
     * @return de geconverteerde afnemers indicaties
     */
    BrpAfnemersindicaties converteerLo3Afnemersindicaties(final Lo3Afnemersindicatie lo3Afnemersindicaties);
}
