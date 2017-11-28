/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.ElementObject;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.MetaAttribuutLiteral;
import nl.bzk.brp.domain.expressie.ActieLiteral;
import nl.bzk.brp.domain.expressie.ElementnaamLiteral;
import nl.bzk.brp.domain.expressie.NullLiteral;
import nl.bzk.brp.domain.expressie.parser.exception.ExpressieParseException;
import nl.bzk.brp.domain.expressie.signatuur.SimpeleSignatuur;
import nl.bzk.brp.domain.expressie.validator.OperandValidator;
import nl.bzk.brp.domain.expressie.validator.Validator;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.ModelIndex;

/**
 * Abstracte functie tbv van verantwoordingexpressies.
 */
abstract class AbstractVerantwoordingFunctie extends AbstractFunctie {

    private static final int TWEE = 2;
    private final OperandValidator arg1ParsetimeValidator = new OperandValidator(
            (Validator) expressie ->
                    expressie instanceof FunctieExpressie
                            || expressie.getType(null) == ExpressieType.LIJST ? null : "Argument 1 moet een LIJST opleveren"
    );

    private final OperandValidator arg2ParsetimeValidator = new OperandValidator(
            (Validator) expressie -> expressie instanceof ElementnaamLiteral ? null : "Argument 2 moet een [elementexpressie] zijn",
            expressie -> {
                final ElementObject element = ElementHelper.getElement(expressie.alsString());
                if (!isGeldigVerantwoordingElement(element)) {
                    return "Argument 2 is geen geldige verantwoordingsexpressie";
                }
                return null;
            }
    );

    /**
     * Constructor voor de functie.
     */
    AbstractVerantwoordingFunctie() {
        super(new SimpeleSignatuur(ExpressieType.LIJST, ExpressieType.ELEMENT));
    }

    @Override
    public Expressie evalueer(final List<Expressie> argumenten, final Context context) {

        final Expressie expressieNaEvaluatie = argumenten.get(0).evalueer(context);
        if (expressieNaEvaluatie == NullLiteral.INSTANCE) {
            return new LijstExpressie();
        }
        final LijstExpressie argument0 = LijstExpressie.ensureList(expressieNaEvaluatie);

        final List<Expressie> resultaat = Lists.newArrayList();
        for (Expressie expressie1 : argument0) {
            final ActieLiteral actieLiteral = (ActieLiteral) expressie1;
            final Actie actie = actieLiteral.getActie();
            final MetaObject metaObject = bepaalMetaObject(actie);
            //let op ModelIndex is niet gecached
            final ModelIndex modelIndex = new ModelIndex(metaObject);
            final AttribuutElement element = ElementHelper.getAttribuutElement(argumenten.get(1).alsString());
            final Set<MetaAttribuut> attribuutSet = modelIndex.geefAttributen(element);
            for (MetaAttribuut attribuut : attribuutSet) {
                final Expressie expressie = MetaAttribuutLiteral.maakAttribuutWaardeLiteral(attribuut);
                resultaat.add(expressie);
            }
        }
        return new LijstExpressie(resultaat);
    }

    @Override
    public List<Expressie> init(final List<Expressie> argumenten) {
        if (argumenten.size() != TWEE) {
            throw new ExpressieParseException("ACTIE Functie vereist 2 argumenten");
        }
        //parse-time validatie
        arg1ParsetimeValidator.valideer(argumenten.get(0), () -> "Ongeldig 1e argument");
        arg2ParsetimeValidator.valideer(argumenten.get(1), () -> "Ongeldig 2e argument");
        return argumenten;
    }

    @Override
    public ExpressieType getType(final List<Expressie> argumenten, final Context context) {
        return ExpressieType.LIJST;
    }

    @Override
    public boolean evalueerArgumenten() {
        return false;
    }

    /**
     * Hook om te bepalen welk {@link MetaObject} gebruikt voor voor de gegeven {@link Actie}.
     *
     * @param actie een Actie object
     * @return een {@link MetaObject}
     */
    protected abstract MetaObject bepaalMetaObject(final Actie actie);

    /**
     * Hook om te bepalen of het element in de verantwoordingsexpressie geldig is.
     *
     * @param objectElement een Actie object
     * @return een {@link MetaObject}
     */
    protected abstract boolean isGeldigVerantwoordingElement(final ElementObject objectElement);

}
