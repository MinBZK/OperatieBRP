/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree.functies;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.expressietaal.parser.ParserUtils;
import nl.bzk.brp.expressietaal.parser.syntaxtree.Expressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.ExpressieType;
import nl.bzk.brp.expressietaal.parser.syntaxtree.VariableExpressie;
import nl.bzk.brp.expressietaal.symbols.Keywords;

/**
 * Factory voor functieaanroepen in de expressies.
 */
public abstract class FunctieFactory {

    /**
     * Constructor. Private omdat het een factory class is. *
     */
    private FunctieFactory() {
    }

    /**
     * Creëert een functieaanroep op basis van een keyword en lijst van argumenten.
     *
     * @param keyword    Keyword van de functie.
     * @param argumenten Lijst met argumenten.
     * @return Expressie voor de functieaanroep of NULL indien een fout is gevonden.
     */
    public static Expressie creeerFunctieaanroep(final Keywords keyword, final List<Expressie> argumenten) {
        if (argumenten == null) {
            return null;
        }
        Expressie result = null;

        switch (keyword) {
            case GEDEFINIEERD:
                if (checkAttribuutArgument(argumenten)) {
                    result = new FunctieGEDEFINIEERD(argumenten);
                }
                break;
            case IN_ONDERZOEK:
                if (checkAttribuutArgument(argumenten)) {
                    result = new FunctieONDERZOEK(argumenten);
                }
                break;
            case NU:
                if (argumenten.size() == 0) {
                    result = new FunctieNU();
                }
                break;
            case JAAR:
                if (checkArgument(argumenten, ExpressieType.DATE)) {
                    result = new FunctieJAAR(argumenten);
                }
                break;
            case MAAND:
                if (checkArgument(argumenten, ExpressieType.DATE)) {
                    result = new FunctieMAAND(argumenten);
                }
                break;
            case DAG:
                if (checkArgument(argumenten, ExpressieType.DATE)) {
                    result = new FunctieDAG(argumenten);
                }
                break;
            case AANTAL:
                if (checkArgument(argumenten, ExpressieType.INDEXED) || checkArgument(argumenten, ExpressieType.LIST)) {
                    result = new FunctieAANTAL(argumenten);
                }
                break;
            case ALLE:
                break;
            case ER_IS:
                break;
            case KINDEREN:
                if (argumenten.size() == 0) {
                    List<Expressie> persoonArgument = new ArrayList<Expressie>();
                    persoonArgument.add(new VariableExpressie(ParserUtils.DEFAULT_OBJECT, ExpressieType.PERSOON));
                    result = new FunctieKINDEREN(persoonArgument);
                } else if (checkArgument(argumenten, ExpressieType.PERSOON)
                        || checkArgument(argumenten, ExpressieType.UNKNOWN))
                {
                    result = new FunctieKINDEREN(argumenten);
                }
                break;
            case OUDERS:
                if (argumenten.size() == 0) {
                    List<Expressie> persoonArgument = new ArrayList<Expressie>();
                    persoonArgument.add(new VariableExpressie(ParserUtils.DEFAULT_OBJECT, ExpressieType.PERSOON));
                    result = new FunctieOUDERS(persoonArgument);
                } else if (checkArgument(argumenten, ExpressieType.PERSOON)
                        || checkArgument(argumenten, ExpressieType.UNKNOWN))
                {
                    result = new FunctieOUDERS(argumenten);
                }
                break;
            case PARTNERS:
                if (argumenten.size() == 0) {
                    List<Expressie> persoonArgument = new ArrayList<Expressie>();
                    persoonArgument.add(new VariableExpressie(ParserUtils.DEFAULT_OBJECT, ExpressieType.PERSOON));
                    result = new FunctiePARTNERS(persoonArgument);
                } else if (checkArgument(argumenten, ExpressieType.PERSOON)
                        || checkArgument(argumenten, ExpressieType.UNKNOWN))
                {
                    result = new FunctiePARTNERS(argumenten);
                }
                break;
            case HUWELIJKEN:
                List<Expressie> volledigeArgumenten = new ArrayList<Expressie>();
                if (argumenten.size() == 0) {
                    volledigeArgumenten.add(new VariableExpressie(ParserUtils.DEFAULT_OBJECT, ExpressieType.PERSOON));
                    volledigeArgumenten.add(new FunctieNU());
                } else if (argumenten.size() == 1) {
                    if (checkArgument(argumenten, ExpressieType.PERSOON)
                            || checkArgument(argumenten, ExpressieType.UNKNOWN))
                    {
                        volledigeArgumenten.add(argumenten.get(0));
                        volledigeArgumenten.add(new FunctieNU());
                    } else if (checkArgument(argumenten, ExpressieType.DATE)) {
                        volledigeArgumenten.add(new VariableExpressie(ParserUtils.DEFAULT_OBJECT,
                                ExpressieType.PERSOON));
                        volledigeArgumenten.add(argumenten.get(0));
                    }
                } else if (argumenten.size() == 2 && checkArgumenten(argumenten, ExpressieType.PERSOON,
                        ExpressieType.DATE))
                {
                    volledigeArgumenten = argumenten;
                }
                result = new FunctieHUWELIJKEN(volledigeArgumenten);
                break;
            default:
                result = null;
        }

        return result;
    }

    /**
     * Controleert of de lijst met argumenten bestaat uit 1 argument van een bepaald type.
     *
     * @param argumenten Lijst met argumenten.
     * @param type       Verwachte type.
     * @return TRUE als er 1 argument is van het verwachte type.
     */
    private static boolean checkArgument(final List<Expressie> argumenten, final ExpressieType type) {
        return !(argumenten == null || argumenten.size() != 1) && (argumenten.get(0).getType() == type);
    }

    /**
     * Controleert of de lijst met argumenten bestaat uit twee argumenten van een bepaald type.
     *
     * @param argumenten Lijst met argumenten.
     * @param type1      Eerste verwachte type.
     * @param type2      Tweede verwachte type.
     * @return TRUE als er 1 argument is van het verwachte type.
     */
    private static boolean checkArgumenten(final List<Expressie> argumenten, final ExpressieType type1,
                                           final ExpressieType type2)
    {
        return !(argumenten == null || argumenten.size() != 2) && (argumenten.get(0).getType() == type1)
                && (argumenten.get(1).getType() == type2);
    }

    /**
     * Controleert of de lijst met argumenten bestaat uit 1 argument dat verwijst naar een attribuut.
     *
     * @param argumenten Lijst met argumenten.
     * @return TRUE als er 1 argument is dat verwijst naar een attribuut.
     */
    private static boolean checkAttribuutArgument(final List<Expressie> argumenten) {
        return !(argumenten == null || argumenten.size() != 1) && argumenten.get(0).isAttribuut();
    }
}
