/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieLogger;
import nl.bzk.brp.domain.expressie.ExpressieRuntimeException;
import nl.bzk.brp.domain.expressie.ExpressieTaalConstanten;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.element.ElementExpressie;
import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.ElementnaamLiteral;
import nl.bzk.brp.domain.expressie.MetaObjectLiteral;
import nl.bzk.brp.domain.expressie.operator.EAINOperator;
import nl.bzk.brp.domain.expressie.operator.TypeCollectieoperator;
import nl.bzk.brp.domain.expressie.signatuur.SignatuurOptie;
import nl.bzk.brp.domain.expressie.signatuur.SimpeleSignatuur;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.springframework.stereotype.Component;

/**
 * Representeert de functie GEWIJZIGD(oud, nieuw, attribuutcode). De functie geeft WAAR terug als het attribuut met code attribuutcode gewijzigd is,
 * waarbij oude verwijst naar de oude situatie en nieuw naar de nieuwe. Als beide attributen onbekend (NULL) zijn, worden ze als 'niet gewijzigd'
 * beschouwd.
 */
@Component
@FunctieKeyword("GEWIJZIGD")
final class GewijzigdFunctie extends AbstractFunctie {
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final int TWEE_ARGUMENTEN = 2;

    /**
     * Constructor voor de functie.
     */
    GewijzigdFunctie() {
        super(new SignatuurOptie(
                new SimpeleSignatuur(ExpressieType.ONBEKEND_TYPE, ExpressieType.ONBEKEND_TYPE),
                new SimpeleSignatuur(ExpressieType.ONBEKEND_TYPE, ExpressieType.ONBEKEND_TYPE, ExpressieType.ELEMENT)
        ));
    }

    @Override
    public ExpressieType getType(final List<Expressie> argumenten, final Context context) {
        return ExpressieType.BOOLEAN;
    }

    @Override
    public Expressie evalueer(final List<Expressie> ongeevalueerdeArgumenten, final Context context) {
        final Expressie resultaat;
        if (ongeevalueerdeArgumenten.size() == TWEE_ARGUMENTEN) {
            resultaat = verwerkMetTweeArgumenten(ongeevalueerdeArgumenten, context);
        } else {
            resultaat = verwerkMetDrieArgumenten(ongeevalueerdeArgumenten, context);
        }
        return resultaat;
    }

    private Expressie verwerkMetTweeArgumenten(final List<Expressie> argumenten, final Context context) {
        return maakResultaatExpressie(argumenten.get(0), argumenten.get(1), context);
    }

    private Expressie verwerkMetDrieArgumenten(final List<Expressie> argumenten, final Context context) {
        final Expressie resultaat;
        final Persoonslijst oud = context.getProperty(ExpressieTaalConstanten.CONTEXT_PERSOON_OUD);
        final Persoonslijst nieuw = context.getProperty(ExpressieTaalConstanten.CONTEXT_PERSOON_NIEUW);
        final ElementnaamLiteral attribuutCode = (ElementnaamLiteral) argumenten.get(2);
        final LijstExpressie lijstExpressieWaardenOud = oud == null ? new LijstExpressie() : getLijstExpressie(oud, attribuutCode);
        final LijstExpressie lijstExpressieWaardenNieuw = getLijstExpressie(nieuw, attribuutCode);
        resultaat = maakResultaatExpressie(lijstExpressieWaardenOud, lijstExpressieWaardenNieuw, context);
        return resultaat;
    }

    private LijstExpressie getLijstExpressie(final Persoonslijst persoonslijst,
                                             final ElementnaamLiteral attribuutCode) {
        final Context context = new Context();
        context.definieer(ExpressieTaalConstanten.CONTEXT_PERSOON, new MetaObjectLiteral(persoonslijst.getMetaObject(), ExpressieType.BRP_METAOBJECT));
        context.addProperty(ExpressieTaalConstanten.CONTEXT_PROPERTY_PERSOONSLIJST, persoonslijst);

        final Expressie expressie = new ElementExpressie(ExpressieTaalConstanten.CONTEXT_PERSOON, attribuutCode.alsString());
        final Expressie evalueer = expressie.evalueer(context);
        if (!(evalueer instanceof LijstExpressie)) {
            throw new ExpressieRuntimeException("Evaluatie moet als resultaat een LijstExpressie opleveren.");
        }
        return (LijstExpressie) evalueer;
    }

    /**
     * Geeft een expressie die aangeeft of twee waarden onderling verschillen. Als beiden NULL zijn, worden ze als niet verschillend beschouwd; als één van
     * beide NULL is, zijn ze wel verschillend. Als beide niet NULL zijn, wordt de standaard vergelijkingsoperator gebruikt.
     *
     * @param waarde1 De oude waarde.
     * @param waarde2 De nieuwe waarde.
     * @param context Context waarbinnen evaluatie plaatsvindt.
     * @return Resultaat van de vergelijking.
     */
    private static Expressie maakResultaatExpressie(final Expressie waarde1, final Expressie waarde2,
                                                    final Context context) {
        //bepaal het resultaat op het oud-beeld
        final Context contextOud = new Context(context);
        final Persoonslijst plOud = context.getProperty(ExpressieTaalConstanten.CONTEXT_PERSOON_OUD);
        final Expressie resultaatOud;
        if (plOud != null) {
            contextOud.definieer(ExpressieTaalConstanten.CONTEXT_PERSOON_OUD,
                    new MetaObjectLiteral(plOud.getMetaObject(), ExpressieType.BRP_METAOBJECT));
            contextOud.addProperty(ExpressieTaalConstanten.CONTEXT_PROPERTY_PERSOONSLIJST, plOud);
            resultaatOud = LijstExpressie.ensureList(waarde1.evalueer(contextOud));
        } else {
            resultaatOud = new LijstExpressie();
        }

        //bepaal het resultaat op het nieuw-beeld
        final Context contextNieuw = new Context(context);
        final Persoonslijst plNieuw = context.getProperty(ExpressieTaalConstanten.CONTEXT_PERSOON_NIEUW);
        contextNieuw.definieer(ExpressieTaalConstanten.CONTEXT_PERSOON_NIEUW,
                new MetaObjectLiteral(plNieuw.getMetaObject(), ExpressieType.BRP_METAOBJECT));
        contextNieuw.addProperty(ExpressieTaalConstanten.CONTEXT_PROPERTY_PERSOONSLIJST, plNieuw);
        final Expressie resultaatNieuw = LijstExpressie.ensureList(waarde2.evalueer(contextNieuw));

        //controleer gelijkheid
        final Expressie linksInRechts = new EAINOperator(resultaatOud, resultaatNieuw, TypeCollectieoperator.EN).evalueer(context);
        final Expressie rechtsInLinks = new EAINOperator(resultaatNieuw, resultaatOud, TypeCollectieoperator.EN).evalueer(context);
        final BooleanLiteral gewijzigd = BooleanLiteral.valueOf(!(linksInRechts.alsBoolean() && rechtsInLinks.alsBoolean()));

        if (ExpressieLogger.IS_DEBUG_ENABLED) {
            LOGGER.debug("resultaat oud: " + resultaatOud);
            LOGGER.debug("resultaat nieuw: " + resultaatNieuw);
            LOGGER.debug("resultaat gewijzigd: " + gewijzigd);
        }
        return gewijzigd;
    }

    @Override
    public boolean evalueerArgumenten() {
        // De argumenten van GEWIJZIGD moeten niet van tevoren geevalueerd worden, omdat de verwijzing naar het
        // attribuut niet verloren moet gaan en er een speciale (afwijkende) rol voor NULL is.
        return false;
    }
}
