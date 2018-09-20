/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.functies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.EvaluatieFoutCode;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.ExpressieUtil;
import nl.bzk.brp.expressietaal.expressies.FoutExpressie;
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.Signatuur;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.SignatuurOptie;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.SimpeleSignatuur;
import nl.bzk.brp.expressietaal.expressies.literals.AttribuutcodeExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.BrpObjectExpressie;
import nl.bzk.brp.expressietaal.symbols.BmrSymbolTable;
import nl.bzk.brp.expressietaal.symbols.DefaultSolver;
import nl.bzk.brp.expressietaal.symbols.ExpressieAttribuut;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * Representeert de functie GEWIJZIGD(oud, nieuw, attribuutcode). De functie geeft WAAR terug als het attribuut met code
 * attribuutcode gewijzigd is, waarbij oude verwijst naar de oude situatie en nieuw naar de nieuwe. Als beide
 * attributen onbekend (NULL) zijn, worden ze als 'niet gewijzigd' beschouwd.
 */
public final class FunctieGEWIJZIGD implements Functieberekening {

    private static final Signatuur SIGNATUUR =
            new SignatuurOptie(
                    new SimpeleSignatuur(ExpressieType.ONBEKEND_TYPE, ExpressieType.ONBEKEND_TYPE),
                    new SimpeleSignatuur(ExpressieType.ONBEKEND_TYPE, ExpressieType.ONBEKEND_TYPE, ExpressieType.ATTRIBUUTCODE),
                    new SimpeleSignatuur(ExpressieType.ONBEKEND_TYPE, ExpressieType.ONBEKEND_TYPE, ExpressieType.ATTRIBUUTCODE,
                            ExpressieType.ATTRIBUUTCODE)
            );
    private static final int TWEE_ARGUMENTEN = 2;
    private static final int DRIE_ARGUMENTEN = 3;
    private static final int VIER_ARGUMENTEN = 4;

    @Override
    public List<Expressie> vulDefaultArgumentenIn(final List<Expressie> argumenten) {
        return argumenten;
    }

    @Override
    public Signatuur getSignatuur() {
        return SIGNATUUR;
    }

    @Override
    public ExpressieType getType(final List<Expressie> argumenten, final Context context) {
        return ExpressieType.BOOLEAN;
    }

    @Override
    public ExpressieType getTypeVanElementen(final List<Expressie> argumenten, final Context context) {
        return ExpressieType.ONBEKEND_TYPE;
    }

    @Override
    public boolean evalueerArgumenten() {
        // De argumenten van GEWIJZIGD moeten niet van tevoren geevalueerd worden, omdat de verwijzing naar het
        // attribuut niet verloren moet gaan en er een speciale (afwijkende) rol voor NULL is.
        return false;
    }

    @Override
    public Expressie pasToe(final List<Expressie> ongeevalueerdeArgumenten, final Context context) {
    	final List<Expressie> argumenten = new ArrayList<>(ongeevalueerdeArgumenten.size());
    	for(Expressie ongeevalueerdArgument : ongeevalueerdeArgumenten) {
    		argumenten.add(ongeevalueerdArgument.evalueer(context));
    	}

        final Expressie resultaat;
        final String foutMelding = valideerParameters(argumenten, context);
        if (foutMelding != null) {
            resultaat = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE, foutMelding);
        } else {
            // Signatuur zorgt ervoor dat de functie enkel met twee, drie of vier argumenten aangeroepen kan worden.
            if (argumenten.size() == TWEE_ARGUMENTEN) {
                resultaat = verwerkMetTweeArgumenten(argumenten, context);
            } else if (argumenten.size() == DRIE_ARGUMENTEN) {
                resultaat = verwerkMetDrieArgumenten(argumenten, context);
            } else {
                resultaat = verwerkMetVierArgumenten(argumenten, context);
            }
        }
        return resultaat;
    }

    /**
     * Verwerk met twee argumenten.
     *
     * @param argumenten de argumenten
     * @param context de context
     * @return de expressie
     */
    private Expressie verwerkMetTweeArgumenten(final List<Expressie> argumenten, final Context context) {
        return ExpressieUtil.waardenVerschillend(argumenten.get(0), argumenten.get(1), context);
    }

    /**
     * Verwerk met default argumenten.
     *
     * @param argumenten de argumenten
     * @param context de context
     * @return de expressie
     */
    private Expressie verwerkMetDrieArgumenten(final List<Expressie> argumenten, final Context context) {
        final Expressie resultaat;
        if (isFunctieResultaatVergelijking(argumenten)) {
            resultaat = vergelijkFunctieResultaten(argumenten, context);
        } else {
            final BrpObjectExpressie oudObject = (BrpObjectExpressie) argumenten.get(0);
            final BrpObjectExpressie nieuwObject = (BrpObjectExpressie) argumenten.get(1);

            final AttribuutcodeExpressie attribuutCode = (AttribuutcodeExpressie) argumenten.get(2);
            final ExpressieAttribuut attribuut = bepaalExpressieAttribuutVoorAttribuut(attribuutCode, oudObject, context);

            if (attribuut == null) {
                resultaat = bouwFoutExpressieOnbekendAttribuut(attribuutCode, oudObject.getType(context));
            } else {
                final Expressie oudeWaarde = DefaultSolver.getInstance().bepaalWaarde(oudObject.getBrpObject(), attribuut);
                final Expressie nieuweWaarde = DefaultSolver.getInstance().bepaalWaarde(nieuwObject.getBrpObject(), attribuut);

                resultaat = ExpressieUtil.waardenVerschillend(oudeWaarde, nieuweWaarde, context);
            }
        }
        return resultaat;
    }

    /**
     * Verwerk met vier argumenten, het derde argument is dan niet de directe verwijzing naar een attribuut, maar naar een lijst met groepen zoals
     * adressen. Het vierde argument wijst dan naar een attribuut binnen de lijst, zoals postcode.
     *
     * @param argumenten de argumenten
     * @param context de context
     * @return de expressie
     */
    private Expressie verwerkMetVierArgumenten(final List<Expressie> argumenten, final Context context) {
        final BrpObjectExpressie oudObject = (BrpObjectExpressie) argumenten.get(0);

        final AttribuutcodeExpressie lijstAttribuutCode = (AttribuutcodeExpressie) argumenten.get(2);
        final ExpressieAttribuut lijstAttribuut = bepaalExpressieAttribuutVoorAttribuut(lijstAttribuutCode, oudObject, context);

        final Expressie resultaat;
        if (lijstAttribuut == null) {
            resultaat = bouwFoutExpressieOnbekendAttribuut(lijstAttribuutCode, oudObject.getType(context));
        } else {
            resultaat = verwerkMetVierdeArgument(argumenten, context, lijstAttribuut);
        }
        return resultaat;
    }

    /**
     * Verwerk met vierde argument, de lijst expressie is bepaald en nu kan de expressie worden verwerkt tot het definitieve resultaat door het attribuut
     * binnen de lijst te vergelijken.
     *
     * @param argumenten de argumenten
     * @param context de context
     * @param lijstAttribuut het lijst attribuut
     * @return de expressie met het resultaat
     */
    private Expressie verwerkMetVierdeArgument(final List<Expressie> argumenten, final Context context, final ExpressieAttribuut lijstAttribuut) {
        final Expressie resultaat;
        final BrpObjectExpressie oudObject = (BrpObjectExpressie) argumenten.get(0);
        final BrpObjectExpressie nieuwObject = (BrpObjectExpressie) argumenten.get(1);

        final AttribuutcodeExpressie attribuutCode = (AttribuutcodeExpressie) argumenten.get(3);
        final ExpressieAttribuut attribuut = bepaalExpressieAttribuutVoorAttribuut(attribuutCode, lijstAttribuut);

        if (attribuut != null) {
            final List<Expressie> lijstWaardenOud = bouwLijstWaardes(lijstAttribuut, oudObject, attribuut);
            final List<Expressie> lijstWaardenNieuw = bouwLijstWaardes(lijstAttribuut, nieuwObject, attribuut);
            resultaat = ExpressieUtil.waardenVerschillend(new LijstExpressie(lijstWaardenOud), new LijstExpressie(lijstWaardenNieuw), context);
        } else {
            resultaat = bouwFoutExpressieOnbekendAttribuut(attribuutCode, lijstAttribuut.getType());
        }
        return resultaat;
    }

    /**
     * Bouw lijst waardes.
     *
     * @param lijstAttribuut the lijst attribuut
     * @param oudObject the oud object
     * @param attribuut the attribuut
     * @return the list
     */
    private List<Expressie> bouwLijstWaardes(final ExpressieAttribuut lijstAttribuut, final BrpObjectExpressie oudObject,
                                             final ExpressieAttribuut attribuut)
    {
        final Expressie lijstObjecten1 = DefaultSolver.getInstance().bepaalWaarde(oudObject.getBrpObject(), lijstAttribuut);
        return bouwLijstWaardes(attribuut, lijstObjecten1);
    }

    private List<Expressie> bouwLijstWaardes(final ExpressieAttribuut attribuut, final Expressie lijstObjecten1) {
        final List<Expressie> lijstWaarden = new ArrayList<>();
        for (final Expressie objExp : lijstObjecten1.getElementen()) {
            final BrpObject obj = ((BrpObjectExpressie) objExp).getBrpObject();
            final Expressie waarde = DefaultSolver.getInstance().bepaalWaarde(obj, attribuut);
            lijstWaarden.add(waarde);
        }
        return lijstWaarden;
    }

    /**
     * Controleer de parameters.
     *
     * @param argumenten de argumenten
     * @param context de context
     * @return de foutmelding of null als er geen fout is opgetreden
     */
    private String valideerParameters(final List<Expressie> argumenten, final Context context) {
        final Expressie argument1 = argumenten.get(0);
        final Expressie argument2 = argumenten.get(1);

        String foutmelding = null;
        if (argument1 instanceof BrpObjectExpressie && argument2 instanceof BrpObjectExpressie) {
            final BrpObjectExpressie oudObject = (BrpObjectExpressie) argument1;
            final BrpObjectExpressie nieuwObject = (BrpObjectExpressie) argument2;

            if (oudObject.getType(context) != nieuwObject.getType(context)) {
                foutmelding = "GEWIJZIGD verwacht twee BRP-objecten van hetzelfde type.";
            }
        } else if (argument1 instanceof LijstExpressie && argument2 instanceof LijstExpressie) {
            if (argumenten.size() == VIER_ARGUMENTEN) {
                foutmelding = "GEWIJZIGD kan niet met vier argumenten omgaan, als de eerste twee argumenten al lijsten opleveren.";
            }
            if (argumenten.size() == TWEE_ARGUMENTEN) {
                foutmelding = "GEWIJZIGD kan niet met twee argumenten omgaan, als de eerste twee argumenten al lijsten opleveren.";
            }
        } else {
        	if(!argument1.isNull() && !argument2.isNull()) {
                if (argument1.getType(context) != argument2.getType(context)) {
                    foutmelding = "GEWIJZIGD verwacht twee dezelfde objecten als argument.";
                }
        	}

        }
        return foutmelding;
    }

    /**
     * Vergelijk de functie resultaten.
     *
     * @param argumenten de argumenten
     * @param context de context
     * @return de expressie
     */
    private Expressie vergelijkFunctieResultaten(final List<Expressie> argumenten, final Context context) {
        final LijstExpressie oudObject = (LijstExpressie) argumenten.get(0);
        final LijstExpressie nieuwObject = (LijstExpressie) argumenten.get(1);

        final AttribuutcodeExpressie attribuutCode = (AttribuutcodeExpressie) argumenten.get(2);

        final List<Expressie> oudExpressieWaardes = bepaalAttribuutWaardesPerLijstElement(context, oudObject, attribuutCode);
        final List<Expressie> nieuweExpressieWaardes = bepaalAttribuutWaardesPerLijstElement(context, nieuwObject, attribuutCode);

        return ExpressieUtil.waardenVerschillend(new LijstExpressie(oudExpressieWaardes), new LijstExpressie(nieuweExpressieWaardes), context);
    }

    private List<Expressie> bepaalAttribuutWaardesPerLijstElement(final Context context, final LijstExpressie lijstExpressie, final AttribuutcodeExpressie attribuutCode) {
        List<Expressie> expressieWaardes = Collections.emptyList();
        if (lijstExpressie.aantalElementen() > 0) {
            final Expressie objExp = lijstExpressie.getElement(0);
            final ExpressieAttribuut attribuut = bepaalExpressieAttribuutVoorAttribuut(attribuutCode, (BrpObjectExpressie) objExp, context);
            expressieWaardes = bouwLijstWaardes(attribuut, lijstExpressie);
        }
        return expressieWaardes;
    }

    /**
     * Is functie resultaat vergelijking.
     *
     * @param argumenten de argumenten
     * @return the boolean
     */
    private boolean isFunctieResultaatVergelijking(final List<Expressie> argumenten) {
        return argumenten.get(0) instanceof LijstExpressie && argumenten.get(1) instanceof LijstExpressie;
    }

    /**
     * Retourneert een fout expressie voor een onbekend attribuut.
     *
     * @param attribuutCode het attribuut dat fout/ongeldig is.
     * @param objectType    het object type waarbinnen het attribuut zich bevindt.
     * @return de fout expressie.
     */
    private FoutExpressie bouwFoutExpressieOnbekendAttribuut(final AttribuutcodeExpressie attribuutCode,
                                                             final ExpressieType objectType)
    {
        return new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE, String.format("Onbekend attribuut %s bij type %s",
                attribuutCode.alsString(), objectType.getNaam()));
    }

    /**
     * Retourneert de {@link nl.bzk.brp.expressietaal.symbols.ExpressieAttribuut} voor het opgegeven attribuut in
     * meegegeven context en voor opgegeven object.
     *
     * @param attribuut het attribuut waarvoor de expressie gezocht wordt.
     * @param object    het object waarbinnen het attribuut zich bevindt.
     * @param context   de context waarbinnen het object en het attribuut zich bevinden.
     * @return een expressie wijzende naar het attribuut.
     */
    private ExpressieAttribuut bepaalExpressieAttribuutVoorAttribuut(final AttribuutcodeExpressie attribuut,
                                                                     final BrpObjectExpressie object, final Context context)
    {
        return BmrSymbolTable.getInstance().zoekSymbool(attribuut.alsString(), object.getType(context));
    }

    /**
     * Retourneert de {@link nl.bzk.brp.expressietaal.symbols.ExpressieAttribuut} voor het opgegeven attribuut in
     * meegegeven context en voor opgegeven object.
     *
     * @param attribuut het attribuut waarvoor de expressie gezocht wordt.
     * @param object    het object waarbinnen het attribuut zich bevindt.
     * @return een expressie wijzende naar het attribuut.
     */
    private ExpressieAttribuut bepaalExpressieAttribuutVoorAttribuut(final AttribuutcodeExpressie attribuut, final ExpressieAttribuut object) {
        return BmrSymbolTable.getInstance().zoekSymbool(attribuut.alsString(), object.getType());
    }

    @Override
    public boolean berekenBijOptimalisatie() {
        return false;
    }

    @Override
    public Expressie optimaliseer(final List<Expressie> argumenten, final Context context) {
        return null;
    }
}
