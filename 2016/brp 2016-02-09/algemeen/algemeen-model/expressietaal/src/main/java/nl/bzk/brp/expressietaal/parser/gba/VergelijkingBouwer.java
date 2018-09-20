/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.gba;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieTaalConstanten;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.Keyword;
import nl.bzk.brp.expressietaal.expressies.AttribuutExpressie;
import nl.bzk.brp.expressietaal.expressies.VariabeleExpressie;
import nl.bzk.brp.expressietaal.expressies.functies.FunctieExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.GetalLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.expressietaal.expressies.literals.PeriodeLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.BevatOperatorExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.LogischeNietExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.MinusOperatorExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.PlusOperatorExpressie;
import nl.bzk.brp.expressietaal.symbols.ExpressieAttribuut;

/**
 * Utility class voor het omzetten van GBA-vergelijkingen naar BRP-vergelijkingen.
 */
public final class VergelijkingBouwer {

    /**
     * Default naam voor de lokale variabele in een ER_IS- of ALLE-functie.
     */
    public static final String DEFAULT_LOKALE_VARIABELE = "v";

    private static final String OPSCHORTING_RUBRIEK = "07.67.10";

    /**
     * Constructor. Private voor utility class.
     */
    private VergelijkingBouwer() {
    }

    /**
     * Maakt een vergelijking (gelijkheid, ongelijkheid) in de BRP-expressietaal gebaseerd op een rubriek, een operator
     * en een lijst van waarden waarmee vergeleken wordt.
     *
     * @param rubriek  De rubriek die vergeleken wordt.
     * @param txtOp    De vergelijkingsoperator.
     * @param waarden  De waarden waarmee de rubriek vergeleken wordt.
     * @param logOpVgl De GBA-operator waarmee de elementen zijn gegroepeerd (OFVGL of ENVGL).
     * @return Vergelijking in BRP-expressietaal, of NULL bij fout.
     */
    public static Expressie maakVergelijking(final Expressie rubriek, final TxtOp txtOp, final Expressie waarden,
                                             final LogOpVgl logOpVgl)
    {
        Expressie result = NullValue.getInstance();

        if (!rubriek.isNull(null)) {
            ExpressieAttribuut attr = null;

            if (rubriek instanceof AttribuutExpressie) {
                attr = ((AttribuutExpressie) rubriek).getExpressieAttribuut();
            }

            if (attr == null || attr.getParent() == null) {
                if (waarden.aantalElementen() == 1) {
                    result = GbaOperatoren.maakOperatorExpressie(rubriek, txtOp, waarden.getElement(0));
                } else {
                    result = new BevatOperatorExpressie(rubriek, waarden, attr.getType() == ExpressieType.STRING);
                }
            } else if (logOpVgl == LogOpVgl.OFVGL) {
                result = maakFunctieERIS(attr, txtOp, waarden);
            } else {
                result = maakFunctieALLE(attr, txtOp, waarden);
            }

            if (waarden.aantalElementen() > 1 && txtOp == TxtOp.OGAA) {
                result = new LogischeNietExpressie(result);
            }
        }

        return result;
    }

    /**
     * Maakt een vergelijking (kleiner, groter) in de BRP-expressietaal gebaseerd op een rubriek, een operator en een
     * waarde waarmee vergeleken wordt. Indien data vergeleken worden, is het mogelijk een periode mee te geven.
     *
     * @param rubriek De rubriek die vergeleken wordt.
     * @param relOp   De vergelijkingsoperator.
     * @param waarden De waarde waarmee de rubriek vergeleken wordt.
     * @return Vergelijking in BRP-expressietaal, of NULL bij fout.
     */
    public static Expressie maakVergelijking(final Expressie rubriek, final RelOp relOp, final Expressie waarden) {
        Expressie result;
        if (!rubriek.isNull(null)) {
            ExpressieAttribuut attr = null;

            if (rubriek instanceof AttribuutExpressie) {
                attr = ((AttribuutExpressie) rubriek).getExpressieAttribuut();
            }

            if (attr == null || attr.getParent() == null) {
                result = GbaOperatoren.maakOperatorExpressie(rubriek, relOp, waarden.getElement(0));
            } else if (relOp == RelOp.GD1 || relOp == RelOp.KD1 || relOp == RelOp.GDOG1 || relOp == RelOp.KDOG1) {
                result = maakFunctieERIS(attr, relOp, waarden);
            } else {
                result = maakFunctieALLE(attr, relOp, waarden);
            }
        } else {
            result = NullValue.getInstance();
        }
        return result;
    }

    /**
     * Maakt een expressie voor de functie ALLE toegepast op een attribuut met meerdere elementen (zoals adres) met
     * een gegeven vergelijking.
     *
     * @param attr         Attribuut.
     * @param vergelijking Vergelijking die gecontroleerd moet worden.
     * @return Expressie voor functie ALLE.
     */
    private static Expressie maakFunctieALLE(final ExpressieAttribuut attr, final Expressie vergelijking) {
        Expressie result;
        final List<Expressie> argumenten = new ArrayList<Expressie>();
        argumenten.add(new AttribuutExpressie(ExpressieTaalConstanten.DEFAULT_OBJECT, attr.getParent()));
        argumenten.add(new VariabeleExpressie(DEFAULT_LOKALE_VARIABELE));
        argumenten.add(vergelijking);
        result = FunctieExpressie.maakFunctieaanroep(Keyword.ALLE, argumenten);
        return result;
    }

    /**
     * Maakt een expressie voor de functie ER_IS toegepast op een attribuut met meerdere elementen (zoals adres) met
     * een gegeven vergelijking.
     *
     * @param attr         Attribuut.
     * @param vergelijking Vergelijking die gecontroleerd moet worden.
     * @return Expressie voor functie ER_IS.
     */
    public static Expressie maakFunctieERIS(final ExpressieAttribuut attr, final Expressie vergelijking) {
        Expressie result;
        final List<Expressie> argumenten = new ArrayList<Expressie>();
        argumenten.add(new AttribuutExpressie(ExpressieTaalConstanten.DEFAULT_OBJECT, attr.getParent()));
        argumenten.add(new VariabeleExpressie(DEFAULT_LOKALE_VARIABELE));
        argumenten.add(vergelijking);
        result = FunctieExpressie.maakFunctieaanroep(Keyword.ER_IS, argumenten);
        return result;
    }

    /**
     * Maakt een expressie voor de functie ALLE toegepast op een attribuut met meerdere elementen (zoals adres) met
     * een gegeven vergelijkingsoperator (txtOp) en een expressie met waarden.
     *
     * @param attr    Attribuut.
     * @param txtOp   Vergelijkingsoperator.
     * @param waarden De waarden waarop gecontroleerd moet worden.
     * @return Expressie voor functie ALLE.
     */
    private static Expressie maakFunctieALLE(final ExpressieAttribuut attr, final TxtOp txtOp,
                                             final Expressie waarden)
    {
        Expressie vergelijking;
        if (waarden.aantalElementen() == 1) {
            vergelijking = GbaOperatoren.maakOperatorExpressie(
                new AttribuutExpressie(DEFAULT_LOKALE_VARIABELE, attr), txtOp, waarden.getElement(0));
        } else {
            vergelijking = new BevatOperatorExpressie(
                new AttribuutExpressie(DEFAULT_LOKALE_VARIABELE, attr), waarden,
                attr.getType() == ExpressieType.STRING);
        }
        return maakFunctieALLE(attr, vergelijking);
    }

    /**
     * Maakt een expressie voor de functie ER_IS toegepast op een attribuut met meerdere elementen (zoals adres) met
     * een gegeven vergelijkingsoperator (txtOp) en een expressie met waarden.
     *
     * @param attr    Attribuut.
     * @param txtOp   Vergelijkingsoperator.
     * @param waarden De waarden waarop gecontroleerd moet worden.
     * @return Expressie voor functie ER_IS.
     */
    private static Expressie maakFunctieERIS(final ExpressieAttribuut attr, final TxtOp txtOp,
                                             final Expressie waarden)
    {
        Expressie vergelijking;
        if (waarden.aantalElementen() == 1) {
            vergelijking = GbaOperatoren.maakOperatorExpressie(
                new AttribuutExpressie(DEFAULT_LOKALE_VARIABELE, attr), txtOp, waarden.getElement(0));
        } else {
            vergelijking = new BevatOperatorExpressie(
                new AttribuutExpressie(DEFAULT_LOKALE_VARIABELE, attr), waarden,
                attr.getType() == ExpressieType.STRING);
        }
        return maakFunctieERIS(attr, vergelijking);
    }

    /**
     * Maakt een expressie voor de functie ALLE toegepast op een attribuut met meerdere elementen (zoals adres) met
     * een gegeven vergelijkingsoperator (relOp) en een expressie met waarden.
     *
     * @param attr    Attribuut.
     * @param relOp   Vergelijkingsoperator.
     * @param waarden De waarden waarop gecontroleerd moet worden.
     * @return Expressie voor functie ALLE.
     */
    private static Expressie maakFunctieALLE(final ExpressieAttribuut attr, final RelOp relOp,
                                             final Expressie waarden)
    {
        final Expressie vergelijking = GbaOperatoren.maakOperatorExpressie(
            new AttribuutExpressie(DEFAULT_LOKALE_VARIABELE, attr), relOp, waarden.getElement(0)
        );
        return maakFunctieALLE(attr, vergelijking);
    }

    /**
     * Maakt een expressie voor de functie ER_IS toegepast op een attribuut met meerdere elementen (zoals adres) met
     * een gegeven vergelijkingsoperator (relOp) en een expressie met waarden.
     *
     * @param attr    Attribuut.
     * @param relOp   Vergelijkingsoperator.
     * @param waarden De waarden waarop gecontroleerd moet worden.
     * @return Expressie voor functie ER_IS.
     */
    private static Expressie maakFunctieERIS(final ExpressieAttribuut attr, final RelOp relOp,
                                             final Expressie waarden)
    {
        final Expressie vergelijking = GbaOperatoren.maakOperatorExpressie(
            new AttribuutExpressie(DEFAULT_LOKALE_VARIABELE, attr), relOp, waarden.getElement(0)
        );
        return maakFunctieERIS(attr, vergelijking);
    }

    /**
     * Maakt een BRP-expressie die controleert op het al dan niet voorkomen van een attribuut dat overeenkomt
     * met een GBA-rubriek.
     *
     * @param rubriek       De rubriek die wel of niet moet voorkomen.
     * @param moetVoorkomen TRUE als de rubriek moet voorkomen, FALSE als de rubriek niet moet voorkomen.
     * @return BRP-expressie.
     */
    public static Expressie maakControleOpVoorkomen(final String rubriek, final boolean moetVoorkomen) {
        Expressie result;

        if (rubriek.equals(OPSCHORTING_RUBRIEK)) {
            // Speciaal geval voor controle op (niet) voorkomen rubriek "Datum opschorting bijhouding".
            result = FunctieExpressie.maakFunctieaanroep(Keyword.IS_OPGESCHORT);
            if (!moetVoorkomen) {
                result = new LogischeNietExpressie(result);
            }
        } else {
            final ExpressieAttribuut attr = GbaRubrieken.getAttribute(rubriek);
            final Expressie argument = new AttribuutExpressie(ExpressieTaalConstanten.DEFAULT_OBJECT, attr);
            result = FunctieExpressie.maakFunctieaanroep(Keyword.IS_NULL, argument);
            if (moetVoorkomen) {
                result = new LogischeNietExpressie(result);
            }
        }
        return result;
    }

    /**
     * Bepaalt de door een voorwaarderegeloperator geimpliceerde lijstoperator. De GBA-grammatica staat ook
     * nietszeggende combinaties toe, maar die worden in de praktijk (logischerwijs) niet gebruikt.
     *
     * @param txtOp Vergelijkingsoperator.
     * @return Geimpliceerde lijstoperator (LogOpVgl).
     */
    public static LogOpVgl bepaalDefaultLogOpVgl(final TxtOp txtOp) {
        LogOpVgl logOpVgl;
        if (txtOp == TxtOp.GA1 || txtOp == TxtOp.GAA || txtOp == TxtOp.OGA1) {
            logOpVgl = LogOpVgl.OFVGL;
        } else {
            logOpVgl = LogOpVgl.ENVGL;
        }
        return logOpVgl;
    }

    /**
     * Maakt een berekening van een datum en een periode.
     *
     * @param datum    Datum waarmee gerekend wordt.
     * @param periode  De periode die bij de datum opgeteld of van de datum afgetrokken moet worden.
     * @param optellen TRUE als de periode opgeteld moet worden; anders FALSE.
     * @return Expressie met de datumberekening.
     */
    public static Expressie maakDatumBerekening(final Expressie datum, final PeriodeLiteralExpressie periode,
                                                final boolean optellen)
    {
        Expressie result;
        if (periode.isLeeg()) {
            result = datum;
        } else if (optellen) {
            result = new PlusOperatorExpressie(datum, periode);
        } else {
            result = new MinusOperatorExpressie(datum, periode);
        }
        return result;
    }

    /**
     * Maakt een expressie voor een berekening met de huidige datum.
     *
     * @param periode         De periode die bij de huidige datum opgeteld of van de huidige datum afgetrokken moet
     *                        worden.
     * @param optellen        TRUE als de periode opgeteld moet worden; anders FALSE.
     * @param uitsluitendJaar TRUE als uitsluitend het jaartal van belang is; anders FALSE.
     * @return Expressie met datumberekening.
     */
    public static Expressie maakDatumNuExpressie(final PeriodeLiteralExpressie periode, final boolean optellen,
                                                 final boolean uitsluitendJaar)
    {
        Expressie datumExpressie;
        if (!periode.isLeeg()) {
            if (periode.getDag() == 0 && periode.getMaand() == 0) {
                int jaarverschil;
                if (optellen) {
                    jaarverschil = periode.getJaar();
                } else {
                    jaarverschil = -periode.getJaar();
                }
                datumExpressie = FunctieExpressie.maakFunctieaanroep(Keyword.VANDAAG,
                    new GetalLiteralExpressie(jaarverschil));
                if (uitsluitendJaar) {
                    datumExpressie = FunctieExpressie.maakFunctieaanroep(Keyword.JAAR, datumExpressie);
                }
            } else {
                datumExpressie =
                    maakDatumBerekening(FunctieExpressie.maakFunctieaanroep(Keyword.VANDAAG), periode, optellen);
            }
        } else {
            datumExpressie = FunctieExpressie.maakFunctieaanroep(Keyword.VANDAAG);
        }
        return datumExpressie;
    }
}
