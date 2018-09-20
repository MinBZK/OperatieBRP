/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.functies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.DefaultKeywordMapping;
import nl.bzk.brp.expressietaal.EvaluatieFoutCode;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.Keyword;
import nl.bzk.brp.expressietaal.expressies.AbstractNonLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.FoutExpressie;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.Signatuur;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Expressie voor functieaanroepen.
 */
public final class FunctieExpressie extends AbstractNonLiteralExpressie {

    private static final Dictionary<Keyword, Functieberekening> KEYWORD_MAPPING;

    private final Keyword           keyword;
    private final List<Expressie>   argumenten;
    private final Functieberekening berekening;


    static {
        KEYWORD_MAPPING = new Hashtable<>();
        KEYWORD_MAPPING.put(Keyword.AANTAL, new FunctieAANTAL());
        KEYWORD_MAPPING.put(Keyword.ALLE, new FunctieALLE());
        KEYWORD_MAPPING.put(Keyword.ALS, new FunctieALS());
        KEYWORD_MAPPING.put(Keyword.DAG, new FunctieDAG());
        KEYWORD_MAPPING.put(Keyword.DATUM, new FunctieDATUM());
        KEYWORD_MAPPING.put(Keyword.AANTAL_DAGEN, new FunctieAANTALDAGEN());
        KEYWORD_MAPPING.put(Keyword.LAATSTE_DAG, new FunctieLAATSTEDAG());
        KEYWORD_MAPPING.put(Keyword.ER_IS, new FunctieERIS());
        KEYWORD_MAPPING.put(Keyword.FILTER, new FunctieFILTER());
        KEYWORD_MAPPING.put(Keyword.GERELATEERDE_BETROKKENHEDEN, new FunctieGERELATEERDEBETROKKENHEDEN());
        KEYWORD_MAPPING.put(Keyword.HUWELIJKEN, new FunctieHUWELIJKEN());
        KEYWORD_MAPPING.put(Keyword.IS_NULL, new FunctieISNULL());
        KEYWORD_MAPPING.put(Keyword.IS_OPGESCHORT, new FunctieISOPGESCHORT());
        KEYWORD_MAPPING.put(Keyword.JAAR, new FunctieJAAR());
        KEYWORD_MAPPING.put(Keyword.MAAND, new FunctieMAAND());
        KEYWORD_MAPPING.put(Keyword.MAP, new FunctieMAP());
        KEYWORD_MAPPING.put(Keyword.VANDAAG, new FunctieVANDAAG());
        KEYWORD_MAPPING.put(Keyword.PARTNERSCHAPPEN, new FunctiePARTNERSCHAPPEN());
        KEYWORD_MAPPING.put(Keyword.FAMILIERECHTELIJKE_BETREKKINGEN, new FunctieFAMILIERECHTELIJKEBETREKKINGEN());
        KEYWORD_MAPPING.put(Keyword.PLATTE_LIJST, new FunctiePLATTELIJST());
        KEYWORD_MAPPING.put(Keyword.RMAP, new FunctieRMAP());
        KEYWORD_MAPPING.put(Keyword.VIEW, new FunctieVIEW());
        KEYWORD_MAPPING.put(Keyword.GEWIJZIGD, new FunctieGEWIJZIGD());
        KEYWORD_MAPPING.put(Keyword.OUDERS, new FunctieOUDERS());
        KEYWORD_MAPPING.put(Keyword.KINDEREN, new FunctieKINDEREN());
        KEYWORD_MAPPING.put(Keyword.INSTEMMERS, new FunctieINSTEMMERS());
        KEYWORD_MAPPING.put(Keyword.ERKENNERS, new FunctieERKENNERS());
        KEYWORD_MAPPING.put(Keyword.PARTNERS, new FunctiePARTNERS());
        KEYWORD_MAPPING.put(Keyword.NAAMSKEUZEPARTNERS, new FunctieNAAMSKEUZEPARTNER());
        KEYWORD_MAPPING.put(Keyword.NAAMGEVERS, new FunctieNAAMGEVERS());
        KEYWORD_MAPPING.put(Keyword.HUWELIJKSPARTNERS, new FunctieHUWELIJKSPARTNERS());
        KEYWORD_MAPPING.put(Keyword.GEREGISTREERD_PARTNERS, new FunctieGEREGISTREERDPARTNERS());
        KEYWORD_MAPPING.put(Keyword.BETROKKENHEDEN, new FunctieBETROKKENHEDEN());
        KEYWORD_MAPPING.put(Keyword.ONDERZOEKEN, new FunctieONDERZOEKEN());
        KEYWORD_MAPPING.put(Keyword.PERSOONONDERZOEKEN, new FunctiePERSOONONDERZOEKEN());
    }

    /**
     * Constructor.
     *
     * @param aFunctie    Keyword van de functie.
     * @param aArgumenten Argumenten voor de aanroep.
     */
    private FunctieExpressie(final Keyword aFunctie, final List<Expressie> aArgumenten) {
        super();
        keyword = aFunctie;
        berekening = KEYWORD_MAPPING.get(aFunctie);
        argumenten = berekening.vulDefaultArgumentenIn(aArgumenten);
    }

    public Keyword getKeyword() {
        return keyword;
    }

    /**
     * Maakt een functieaanroep voor een functie (bepaald door een Keyword) en een aantal argumenten.
     *
     * @param aKeyword    Keyword van de functie.
     * @param aArgumenten Argumenten voor de aanroep.
     * @return Expressie met functieaanroep of een foutexpressie indien een fout is gevonden.
     */
    public static Expressie maakFunctieaanroep(final Keyword aKeyword, final List<Expressie> aArgumenten) {
        final Expressie result;
        final Functieberekening berekening = KEYWORD_MAPPING.get(aKeyword);
        if (berekening == null) {
            result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE,
                "Onbekende functie: " + aKeyword.toString());
        } else if (berekening.getSignatuur().matchArgumenten(aArgumenten, null)) {
            result = new FunctieExpressie(aKeyword, aArgumenten);
        } else {
            result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE,
                "Fout in argumenten");
        }
        return result;
    }

    /**
     * Maakt een functieaanroep voor een functie (bepaald door een Keyword) en een aantal argumenten.
     *
     * @param aFunctie    Keyword van de functie.
     * @param aArgumenten Argumenten voor de aanroep.
     * @return Expressie met functieaanroep of een foutexpressie indien een fout is gevonden.
     */
    public static Expressie maakFunctieaanroep(final Keyword aFunctie, final Expressie... aArgumenten) {
        final List<Expressie> argumenten = new ArrayList<>();
        Collections.addAll(argumenten, aArgumenten);
        return maakFunctieaanroep(aFunctie, argumenten);
    }

    public Signatuur getSignatuur() {
        return berekening.getSignatuur();
    }

    @Override
    public ExpressieType getType(final Context context) {
        return berekening.getType(argumenten, context);
    }

    @Override
    public Expressie evalueer(final Context context) {
        Expressie result = null;
        List<Expressie> argumentenVoorEvaluatie = null;

        /* Controleer eerst of de argumenten voldoen aan de signatuur. Bereken daarna eventueel de argumenten.
           En pas de functie toe.
         */
        if (!berekening.getSignatuur().matchArgumenten(argumenten, context)) {
            result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        } else if (berekening.evalueerArgumenten()) {
            /* De argumenten van de functieaanroep moeten/mogen eerst geëvalueerd worden, voordat de functie
               wordt toegepast. Als een van de argumenten NULL (onbekend) of fout is, dan wordt de functie niet
               toegepast en is het resultaat respectievelijk NULL of de gevonden fout.
             */
            argumentenVoorEvaluatie = new LinkedList<>();
            for (final Expressie argument : argumenten) {
                final Expressie argResult = argument.evalueer(context);
                if (argResult.isNull(context) || !argResult.isConstanteWaarde()) {
                    result = NullValue.getInstance();
                    break;
                } else if (!argResult.isFout()) {
                    argumentenVoorEvaluatie.add(argResult);
                } else {
                    result = argResult;
                    break;
                }
            }
        } else {
            argumentenVoorEvaluatie = argumenten;
        }

        if (result == null) {
            result = berekening.pasToe(argumentenVoorEvaluatie, context);
        }

        return result;
    }

    @Override
    public int getPrioriteit() {
        //De prioriteit van een functie is gelijk aan die van literals.
        return PRIORITEIT_LITERAL;
    }

    @Override
    public boolean isVariabele() {
        return false;
    }

    @Override
    public ExpressieType bepaalTypeVanElementen(final Context context) {
        final ExpressieType type;
        if (getType(context) != ExpressieType.LIJST) {
            type = getType(context);
        } else {
            type = berekening.getTypeVanElementen(argumenten, context);
        }
        return type;
    }

    @Override
    public int aantalElementen() {
        return 1;
    }

    @Override
    public Attribuut getAttribuut() {
        return null;
    }

    @Override
    public Groep getGroep() {
        return null;
    }

    @Override
    public boolean bevatOngebondenVariabele(final String id) {
        boolean gevonden = false;
        for (final Expressie argument : argumenten) {
            if (argument.bevatOngebondenVariabele(id)) {
                gevonden = true;
                break;
            }
        }
        return gevonden;
    }

    @Override
    public Expressie optimaliseer(final Context context) {
        Expressie geoptimaliseerdeExpressie;
        final List<Expressie> geoptimaliseerdeArgumenten = optimaliseerArgumenten(context);
        boolean heeftOnberekendeArgumenten = false;
        boolean heeftNullArgument = false;
        for (final Expressie exp : geoptimaliseerdeArgumenten) {
            if (exp.isNull(context)) {
                heeftNullArgument = true;
            }
            if (!exp.isConstanteWaarde() && !exp.isNull(context)) {
                heeftOnberekendeArgumenten = true;
                break;
            }
        }

        if (heeftOnberekendeArgumenten || !berekening.berekenBijOptimalisatie()) {
            geoptimaliseerdeExpressie = berekening.optimaliseer(geoptimaliseerdeArgumenten, context);
            if (geoptimaliseerdeExpressie == null) {
                geoptimaliseerdeExpressie = new FunctieExpressie(keyword, geoptimaliseerdeArgumenten);
            }
        } else if (heeftNullArgument && berekening.evalueerArgumenten()) {
            geoptimaliseerdeExpressie = NullValue.getInstance();
        } else {
            geoptimaliseerdeExpressie = berekening.pasToe(geoptimaliseerdeArgumenten, context);
        }

        return geoptimaliseerdeExpressie;
    }

    @Override
    public String stringRepresentatie() {
        final StringBuilder result = new StringBuilder();
        result.append(DefaultKeywordMapping.getSyntax(keyword));
        result.append('(');
        boolean eersteArgument = true;
        for (final Expressie argument : argumenten) {
            if (!eersteArgument) {
                result.append(", ");
            }
            eersteArgument = false;
            result.append(argument.toString());
        }
        result.append(')');
        return result.toString();
    }

    /**
     * Maakt een lijst van geoptimaliseerde argumenten.
     *
     * @param context De bekende symbolische namen (identifiers) afgebeeld op hun waarde.
     * @return Lijst van geoptimaliseerde argumenten.
     */
    protected List<Expressie> optimaliseerArgumenten(final Context context) {
        final List<Expressie> result = new ArrayList<>();
        for (final Expressie e : argumenten) {
            result.add(e.optimaliseer(context));
        }
        return result;
    }
}
