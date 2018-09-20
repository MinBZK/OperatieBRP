/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import nl.bzk.brp.toegangsbewaking.parser.OperatorType;
import nl.bzk.brp.toegangsbewaking.parser.ParserException;
import nl.bzk.brp.toegangsbewaking.parser.tokenizer.AbstractTokenizer;
import nl.bzk.brp.toegangsbewaking.parser.tokenizer.DefaultTokenizer;
import nl.bzk.brp.toegangsbewaking.parser.tokenizer.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Basis implementatie voor alle {@link Grammar} classes. Deze basis implementatie regelt de initialisatie op moment
 * van instantiatie en bevat standaard implementaties van de benodigde methodes. Subclasses dienen alleen de
 * initialisatie methodes te implementeren, waarbij deze class dan reeds in de rest voorziet.
 */
public abstract class AbstractGrammar implements Grammar {

    private static final Logger                   LOGGER                = LoggerFactory
                                                                                .getLogger(AbstractGrammar.class);

    private final SortedSet<String>               identifiers           = new TreeSet<String>(
                                                                                String.CASE_INSENSITIVE_ORDER);
    private final SortedMap<String, OperatorType> operatorIdentifiers   = new TreeMap<String, OperatorType>(
                                                                                String.CASE_INSENSITIVE_ORDER);
    private final SortedMap<String, OperatorType> operatorSymbolen      = new TreeMap<String, OperatorType>();
    private final SortedMap<String, Integer>      functies              = new TreeMap<String, Integer>(
                                                                                String.CASE_INSENSITIVE_ORDER);
    private final SortedMap<String, List<Token>>  identifiersVervanging = new TreeMap<String, List<Token>>(
                                                                                String.CASE_INSENSITIVE_ORDER);

    /**
     * Standaard constructor welke de verschillende onderdelen van de grammar initialiseert door het aanroepen
     * van de betreffende initialisatie methodes. Deze initialisatie methodes dienen door concrete subclasses
     * van deze class te worden geimplementeerd zodat de juiste grammar wordt opgebouwd.
     */
    public AbstractGrammar() {
        // N.B. Er wordt niet gecontroleerd of een identifier per ongeluk zowel als
        // identifier als als operator of functie voorkomt. Dat zou bij automatische
        // vulling van de lijsten vanuit bijvoorbeeld een repository natuurlijk
        // best kunnen en zou dan weldegelijk tot een foutmelding moeten leiden.

        initIdentifiers();
        initOperatorIdentifiers();
        initOperatorSymbolen();
        initFuncties();
        initVervangingen();
    }

    /**
     * Voegt de opgegeven {@code identifier} toe als identifier.
     *
     * @param identifier de identifier die moet worden toegevoegd.
     */
    protected void voegToeIdentifier(final String identifier) {
        identifiers.add(identifier);
    }

    /**
     * Voegt de {@code identifier} toe als operator-identifier van het opgegeven {@code type}.
     *
     * @param identifier de identifier die moet worden toegevoegd als operator-identifier.
     * @param type het operator type wat de identifier representeert.
     */
    protected void voegToeOperatorIdentifier(final String identifier, final OperatorType type) {
        operatorIdentifiers.put(identifier, type);
    }

    /**
     * Voegt het {@code symbool} toe als operator-symbool van het opgegeven {@code type}.
     *
     * @param symbool het symbool dat moet worden toegevoegd als operator-symbool.
     * @param type het operator type wat het symbool representeert.
     */
    protected void voegToeOperatorSymbool(final String symbool, final OperatorType type) {
        operatorSymbolen.put(symbool, type);
    }

    /**
     * Voegt de {@code functie} toe als functie met het opgegeven {@code aantalArgumenten}.
     *
     * @param functie de functie die moet worden toegevoegd.
     * @param aantalArgumenten het aantal argumenten dat de betreffende functie vereist.
     */
    protected void voegToeFunctie(final String functie, final int aantalArgumenten) {
        functies.put(functie, aantalArgumenten);
    }

    /**
     * Voegt de {@code identifier} toe aan de lijst van 'te vervangen identifiers', waarbij de meegeleverde
     * {@code vervanging} wordt omgezet in tokens en als vervanging wordt toegevoegd voor de betreffende
     * identifier.
     *
     * @param identifier de identifier die moet worden toegevoegd als 'te vervangen' identifier.
     * @param vervanging een string die als vervanging dient van de opgegeven identifier.
     */
    protected void voegToeIdentifierVervanging(final String identifier, final String vervanging) {
        List<String> lines = new ArrayList<String>();
        List<Token> tokens = new ArrayList<Token>();
        AbstractTokenizer tokenizer = new DefaultTokenizer(lines);

        try {
            // Tokenize de vervanging
            lines.add(vervanging);
            tokenizer.execute();
            tokens.addAll(tokenizer.getTokens());
            tokenizer.getTokens().clear();

            // Sla resultaat op
            identifiersVervanging.put(identifier, tokens);
        } catch (ParserException e) {
            LOGGER.error(String.format(
                    "fout bij het tokenizen van een vervanging indentifier op lijn %1$s, karakter %2$s: %3$s",
                    e.getLijnNr(), e.getKarakterNr(), e.getMessage()), e);
        }
    }

    /**
     * Initialiseert de identifiers door alle voor deze {@code Grammar} geldende identifiers toe te voegen.
     */
    protected abstract void initIdentifiers();

    /**
     * Initialiseert de operator-identifiers door alle voor deze {@code Grammar} geldende operator-identifiers toe te
     * voegen.
     */
    protected abstract void initOperatorIdentifiers();

    /**
     * Initialiseert de operator-symbolen door alle voor deze {@code Grammar} geldende operator-symbolen toe te voegen.
     */
    protected abstract void initOperatorSymbolen();

    /**
     * Initialiseert de functies door alle voor deze {@code Grammar} geldende functies toe te voegen.
     */
    protected abstract void initFuncties();

    /**
     * Initialiseert de vervangingen door alle voor deze {@code Grammar} geldende vervangingen van identifiers toe te
     * voegen.
     */
    protected abstract void initVervangingen();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isIdentifier(final String identifier) {
        return identifiers.contains(identifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOperatorIdentifier(final String identifier) {
        return operatorIdentifiers.containsKey(identifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OperatorType getOperatorTypeVoorOperatorIdentifier(final String identifier) {
        return operatorIdentifiers.get(identifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOperatorSymbool(final String symbool) {
        return operatorSymbolen.containsKey(symbool);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OperatorType getOperatorTypeVoorOperatorSymbool(final String symbool) {
        return operatorSymbolen.get(symbool);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFunctie(final String functie) {
        return functies.containsKey(functie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAantalArgumentenVoorFunctie(final String functie) {
        return functies.get(functie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTeVervangenIdentifier(final String identifier) {
        return identifiersVervanging.containsKey(identifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Token> getVervangingVoorIdentifier(final String identifier) {
        return identifiersVervanging.get(identifier);
    }

}
