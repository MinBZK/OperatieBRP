/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.element;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieLogger;
import nl.bzk.brp.domain.expressie.ExpressieRuntimeException;
import nl.bzk.brp.domain.expressie.ExpressieTaalConstanten;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.Literal;
import nl.bzk.brp.domain.expressie.MetaAttribuutLiteral;
import nl.bzk.brp.domain.expressie.MetaGroepLiteral;
import nl.bzk.brp.domain.expressie.MetaObjectLiteral;
import nl.bzk.brp.domain.expressie.MetaRecordLiteral;
import nl.bzk.brp.domain.expressie.NonLiteralExpressie;
import nl.bzk.brp.domain.expressie.NullLiteral;
import nl.bzk.brp.domain.expressie.Prioriteit;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * Een elementexpressie wijst een {@link nl.bzk.algemeenbrp.dal.domein.brp.enums.Element element} aan in een
 * {@link Persoonslijst persoonslijst}. Het resultaat van een {@link ElementExpressie} is altijd een {@link LijstExpressie lijst}.
 * Als het element niet gevonden wordt is de lijst leeg.
 * <p>
 * Bepaal welke solvers deze expressie kunnen uitvoeren
 * Potentieel zijn dit er meerdere omdat naamgeving niet uniek hoeft te zijn.
 * De prioriteit lost dit probleem op, bijvoorbeeld:
 * attribuut "Persoon.Naamgebruik.Code" heeft als identexpressie "naamgebruik"
 * groep "Persoon.Naamgebruik" heeft als identexpressie "naamgebruik"
 * x.naamgebruik resulteert in bovenstaande attribuut en groep solver.
 * de prioriteit voor het resolven van de groep is hoger.
 */
public final class ElementExpressie implements NonLiteralExpressie {


    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final String variabeleNaam;
    private final String expressieWaarde;
    private final List<Resolver> resolverList;

    /**
     * Constructor.
     * @param variabeleNaam de variabele waarom de expressieWaarde uitgevoerd wordt
     * @param expressieWaarde de expressieWaarde
     */
    public ElementExpressie(final String variabeleNaam, final String expressieWaarde) {
        this.variabeleNaam = variabeleNaam;
        this.expressieWaarde = expressieWaarde;

        if (ExpressieLogger.IS_DEBUG_ENABLED) {
            LOGGER.debug("ParentIdentifier = {}, expressieString = {}", variabeleNaam, expressieWaarde);
        }
        resolverList = ResolverMap.getSolver(expressieWaarde);
        if (resolverList.isEmpty()) {
            throw new ExpressieRuntimeException("Ongeldige elementexpressie: " + expressieWaarde);
        }
        Collections.sort(resolverList, Resolver.prioriteitComparator());
    }

    @Override
    public Expressie evalueer(final Context context) {
        final Object contextwaarde = resolveContextwaarde(context);
        final List<Resolver> solversVoorContextObject = resolverList.stream().filter(resolver1 -> resolver1.matchContext(contextwaarde))
                .collect(Collectors.toList());
        if (solversVoorContextObject.isEmpty()) {
            throw new ExpressieRuntimeException("Expressie kan niet uitgevoerd worden op het gegeven contextobject");
        }
        final Resolver resolver = Iterables.get(solversVoorContextObject, 0);
        final Persoonslijst persoonslijst = context.getProperty(ExpressieTaalConstanten.CONTEXT_PROPERTY_PERSOONSLIJST);
        final Predicate<MetaAttribuut> attribuutFilter = resolver.postFilter()
                ? bepaalAttribuutFilter(context, persoonslijst)
                : metaAttribuut -> true;
        final Collection solve = resolver.apply(persoonslijst.getModelIndex(), contextwaarde);
        final LijstExpressie resultaatLijst = maakResultaatLijst(attribuutFilter, solve, context);
        LOGGER.debug("resultaat = {}", resultaatLijst);
        return resultaatLijst;
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.LIJST;
    }

    @Override
    public Prioriteit getPrioriteit() {
        return Prioriteit.PRIORITEIT_LITERAL;
    }

    @Override
    public boolean isConstanteWaarde() {
        return false;
    }

    @Override
    public String toString() {
        final String string;
        if (expressieWaarde == null) {
            string = NullLiteral.INSTANCE.toString();
        } else {
            if (ExpressieTaalConstanten.CONTEXT_PERSOON.equals(variabeleNaam)) {
                string = String.format("%s", expressieWaarde);
            } else {
                string = String.format("%s.%s", variabeleNaam, expressieWaarde);
            }
        }
        return string;
    }

    private Predicate<MetaAttribuut> bepaalAttribuutFilter(final Context context, final Persoonslijst persoonslijst) {
        Expressie actueel = context.zoekWaarde(ExpressieTaalConstanten.TOON_ACTUELE_GEGEVENS);
        Predicate<MetaAttribuut> attribuutPredicate = metaAttribuut -> true;
        if (actueel != BooleanLiteral.ONWAAR) {
            attribuutPredicate = metaAttribuut -> persoonslijst.isActueel(metaAttribuut.getParentRecord());
        }
        return attribuutPredicate;
    }

    private Object resolveContextwaarde(final Context context) {
        final Object contextObject = context.zoekWaarde(variabeleNaam);
        final Object contextWaarde;
        if (contextObject instanceof MetaObjectLiteral) {
            contextWaarde = ((MetaObjectLiteral) contextObject).getMetaObject();
        } else if (contextObject instanceof MetaGroepLiteral) {
            contextWaarde = ((MetaGroepLiteral) contextObject).getMetaGroep();
        } else if (contextObject instanceof MetaRecordLiteral) {
            contextWaarde = ((MetaRecordLiteral) contextObject).getMetaRecord();
        } else {
            throw new ExpressieRuntimeException("Contextwaarde is van een onverwacht type");
        }
        return contextWaarde;
    }

    private LijstExpressie maakResultaatLijst(final Predicate<MetaAttribuut> attribuutFilter,
                                              final Collection solve, final Context context) {
        final List<Expressie> resultaatWaarden = Lists.newLinkedList();
        for (final Object metaModel : solve) {
            final Literal literal = maakResultaatLiteral(attribuutFilter, context, metaModel);
            if (literal != null) {
                resultaatWaarden.add(literal);
            }
        }
        return new LijstExpressie(resultaatWaarden);
    }

    private Literal maakResultaatLiteral(final Predicate<MetaAttribuut> attribuutFilter, final Context context,
                                         final Object metaModel) {

        Literal resultaat = null;
        if (metaModel instanceof MetaAttribuut) {
            final MetaAttribuut metaAttribuut = (MetaAttribuut) metaModel;
            if (attribuutFilter.test(metaAttribuut)) {
                if (context.zoekWaarde(ExpressieTaalConstanten.ATTRIBUUTALSWAARDE) == BooleanLiteral.ONWAAR) {
                    resultaat = new MetaAttribuutLiteral(metaAttribuut);
                } else {
                    resultaat = MetaAttribuutLiteral.maakAttribuutWaardeLiteral(metaAttribuut);
                }
            }
        } else if (metaModel instanceof MetaGroep) {
            resultaat = new MetaGroepLiteral((MetaGroep) metaModel);
        } else if (metaModel instanceof MetaObject) {
            resultaat = new MetaObjectLiteral((MetaObject) metaModel, ExpressieType.BRP_METAOBJECT);
        } else if (metaModel == null) {
            resultaat = NullLiteral.INSTANCE;
        } else {
            throw new IllegalStateException();
        }
        return resultaat;
    }

}
