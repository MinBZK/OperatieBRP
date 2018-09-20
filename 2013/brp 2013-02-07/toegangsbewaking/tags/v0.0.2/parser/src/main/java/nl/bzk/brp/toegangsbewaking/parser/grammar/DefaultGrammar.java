/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser.grammar;

import static nl.bzk.brp.toegangsbewaking.parser.OperatorType.BEVAT;
import static nl.bzk.brp.toegangsbewaking.parser.OperatorType.EINDE;
import static nl.bzk.brp.toegangsbewaking.parser.OperatorType.EN;
import static nl.bzk.brp.toegangsbewaking.parser.OperatorType.GELIJK;
import static nl.bzk.brp.toegangsbewaking.parser.OperatorType.GELIJK_CS;
import static nl.bzk.brp.toegangsbewaking.parser.OperatorType.GROTER;
import static nl.bzk.brp.toegangsbewaking.parser.OperatorType.GROTER_GELIJK;
import static nl.bzk.brp.toegangsbewaking.parser.OperatorType.KLEINER;
import static nl.bzk.brp.toegangsbewaking.parser.OperatorType.KLEINER_GELIJK;
import static nl.bzk.brp.toegangsbewaking.parser.OperatorType.LEEG;
import static nl.bzk.brp.toegangsbewaking.parser.OperatorType.NIET;
import static nl.bzk.brp.toegangsbewaking.parser.OperatorType.OF;
import static nl.bzk.brp.toegangsbewaking.parser.OperatorType.START;


public class DefaultGrammar extends AbstractGrammar {

    public DefaultGrammar() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initIdentifiers() {
        // Voeg identifiers toe (identifier)
        voegToeIdentifier("Persoon");
        voegToeIdentifier("Persoon.Geslachtsnaam");
        voegToeIdentifier("Persoon.GeslachtsAanduiding.Code");
        voegToeIdentifier("Persoon.GeslachtsAanduiding.Naam");
        voegToeIdentifier("Persoon.datumGeboorte");
        voegToeIdentifier("Persoon.Adressen[0].Woonplaats.Naam");
        voegToeIdentifier("Persoon.Adressen[0].Gemeente.ID");
        voegToeIdentifier("Persoon.burgerservicenummer");
//        voegToeIdentifier("Persoon.indgegevensinonderzoek");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initOperatorIdentifiers() {
        // Voeg identifiers van de operatoren toe (identifier, type operator)
        voegToeOperatorIdentifier("niet", NIET);
        voegToeOperatorIdentifier("en", EN);
        voegToeOperatorIdentifier("of", OF);
        voegToeOperatorIdentifier("gelijk", GELIJK);
        voegToeOperatorIdentifier("gelijk_cs", GELIJK_CS);
        voegToeOperatorIdentifier("kleiner", KLEINER);
        voegToeOperatorIdentifier("groter", GROTER);
        voegToeOperatorIdentifier("kleiner_gelijk", KLEINER_GELIJK);
        voegToeOperatorIdentifier("groter_gelijk", GROTER_GELIJK);
        voegToeOperatorIdentifier("bevat", BEVAT);
        voegToeOperatorIdentifier("start", START);
        voegToeOperatorIdentifier("einde", EINDE);
        voegToeOperatorIdentifier("leeg", LEEG);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initOperatorSymbolen() {
        // Voeg symbolen van de operatoren toe (symbool, type operator)
        voegToeOperatorSymbool("!", NIET);
        voegToeOperatorSymbool("&", EN);
        voegToeOperatorSymbool("|", OF);
        voegToeOperatorSymbool("=", GELIJK);
        voegToeOperatorSymbool("==", GELIJK_CS);
        voegToeOperatorSymbool("<", KLEINER);
        voegToeOperatorSymbool(">", GROTER);
        voegToeOperatorSymbool("<=", KLEINER_GELIJK);
        voegToeOperatorSymbool(">=", GROTER_GELIJK);
        voegToeOperatorSymbool("%", BEVAT);
        voegToeOperatorSymbool("%-", START);
        voegToeOperatorSymbool("-%", EINDE);
        voegToeOperatorSymbool("#", LEEG);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initFuncties() {
        // Voeg functies toe (identifier, aantal argumenten)
        voegToeFunctie("Jaar", 1);
        voegToeFunctie("Min", 2);
        voegToeFunctie("Vandaag", 0);
        voegToeFunctie("Provincie", 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initVervangingen() {
        // Voeg vervangingen toe (identifier, vervangende tekst)
        // Geen nestingen toegestaan !!! Vervanging is een one-pass routine.
        voegToeIdentifierVervanging("Geslacht", "Persoon.geslachtsAanduiding.Code");
//        voegToeIdentifierVervanging("InOnderzoek", "Persoon.indgegevensinonderzoek = 'J'");
        voegToeIdentifierVervanging("Woonplaats", "Persoon.Adressen[0].Woonplaats.Naam");
        voegToeIdentifierVervanging("Leeftijd", "Jaar(Min(Vandaag, Persoon.datumGeboorte))");
        voegToeIdentifierVervanging("Provincie", "Provincie(Persoon.Adressen[0].Gemeente.ID)");
    }

}
