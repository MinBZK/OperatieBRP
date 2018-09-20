/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

import nl.bzk.brp.expressietaal.symbols.Attributes;
import nl.bzk.brp.expressietaal.symbols.Characters;

/**
 * Representeert een attribuutexpressie.
 */
public class IndexedAttribuutExpressie extends AbstractNonLiteralExpressie {

    private final String object;
    private final Attributes indexedAttribuut;
    private final Expressie index;
    private final Attributes subattribuut;

    /**
     * Constructor. Creëert een geïndiceerde attribuutverwijzing voor een gegeven object.
     *
     * @param object           De naam van het object.
     * @param indexedAttribuut Het attribuutpad van het benoemde geïndiceerde attribuut.
     * @param index            De index.
     * @param subattribuut     Het (sub)attribuut van het geïndiceerde attribuut waarnaar verwezen wordt.
     */
    public IndexedAttribuutExpressie(final String object, final Attributes indexedAttribuut, final Expressie index,
                                     final Attributes subattribuut)
    {
        this.object = object.toUpperCase();
        this.indexedAttribuut = indexedAttribuut;
        this.index = index;
        this.subattribuut = subattribuut;
    }

    @Override
    public final boolean isVariabele() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isAttribuut() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isLijstExpressie() {
        return false;
    }

    @Override
    public final ExpressieType getTypeElementen() {
        return ExpressieType.UNKNOWN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String alsLeesbareString() {
        return String.format("%s.%s%c%s%c%s",
                object, indexedAttribuut.getSyntax(), Characters.INDEX_START, index.alsLeesbareString(),
                Characters.INDEX_EIND, subattribuut.getSyntax());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String alsFormeleString() {
        return String.format("$[%s<%s>.%s%c%s%c%s]",
                object, indexedAttribuut.getObjectType().getNaam(), indexedAttribuut.getSyntax(),
                Characters.INDEX_START, index.alsFormeleString(),
                Characters.INDEX_EIND, subattribuut.getSyntax());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ExpressieType getType() {
        return subattribuut.getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie optimaliseer() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final EvaluatieResultaat evalueer(final Context context) {
        EvaluatieResultaat result;
        if (context != null) {
            EvaluatieResultaat eIndex = index.evalueer(context);

            if (eIndex.succes()) {
                Expressie indexExpressie = eIndex.getExpressie();
                if (indexExpressie.isConstanteWaarde() && indexExpressie.getType() == ExpressieType.NUMBER) {
                    int indexValue = ((NumberLiteralExpressie) indexExpressie).getValue();
                    EvaluatieResultaat attributeValue = context.solve(object, subattribuut, indexValue);
                    if (attributeValue.succes()) {
                        result = attributeValue;
                    } else if (attributeValue.getFout().getFoutCode() == EvaluatieFoutCode.INDEX_BUITEN_BEREIK) {
                        result = attributeValue;
                    } else {
                        result = new EvaluatieResultaat(EvaluatieFoutCode.ATTRIBUUT_NIET_GEVONDEN,
                                this.alsLeesbareString());
                    }
                } else {
                    result = new EvaluatieResultaat(EvaluatieFoutCode.INCORRECTE_EXPRESSIE,
                            indexExpressie.alsLeesbareString());
                }
            } else {
                result = eIndex;
            }
        } else {
            result = new EvaluatieResultaat(EvaluatieFoutCode.ATTRIBUUT_NIET_GEVONDEN, this.alsLeesbareString());
        }
        return result;
    }

    @Override
    public final boolean includes(final Attributes attribuut) {
        return (indexedAttribuut.equals(attribuut)) || (subattribuut.equals(attribuut));
    }
}
