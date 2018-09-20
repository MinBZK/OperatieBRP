/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.EvaluatieFoutCode;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Representeert fouten in expressies en foute expressies.
 */
public final class FoutExpressie implements Expressie, Foutmelding {

    private EvaluatieFoutCode foutCode;
    private String            informatie;

    /**
     * Constructor.
     *
     * @param aFoutCode   Code van de fout die is gevonden.
     * @param aInformatie Aanvullende informatie over de fout die is gevonden.
     */
    public FoutExpressie(final EvaluatieFoutCode aFoutCode, final String aInformatie)
    {
        this.foutCode = aFoutCode;
        this.informatie = aInformatie;
    }

    /**
     * Constructor.
     *
     * @param aFoutCode Code van de fout die is gevonden.
     */
    public FoutExpressie(final EvaluatieFoutCode aFoutCode) {
        this(aFoutCode, "");
    }

    public EvaluatieFoutCode getFoutCode() {
        return foutCode;
    }

    public String getInformatie() {
        return informatie;
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.FOUT;
    }

    @Override
    public Expressie evalueer(final Context context) {
        return this;
    }

    @Override
    public int getPrioriteit() {
        return 0;
    }

    @Override
    public boolean isFout() {
        return true;
    }

    @Override
    public boolean isConstanteWaarde() {
        return true;
    }

    @Override
    public boolean isConstanteWaarde(final ExpressieType expressieType) {
        return expressieType == ExpressieType.FOUT;
    }

    @Override
    public boolean isConstanteWaarde(final ExpressieType expressieType, final Context context) {
        return expressieType == ExpressieType.FOUT;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isNull(final Context context) {
        return false;
    }

    @Override
    public boolean isVariabele() {
        return false;
    }

    @Override
    public boolean isLijstExpressie() {
        return false;
    }

    @Override
    public ExpressieType bepaalTypeVanElementen(final Context context) {
        return ExpressieType.ONBEKEND_TYPE;
    }

    @Override
    public int aantalElementen() {
        return 1;
    }

    @Override
    public Iterable<Expressie> getElementen() {
        final List<Expressie> elementen = new ArrayList<>();
        elementen.add(this);
        return Collections.unmodifiableList(elementen);
    }

    @Override
    public Expressie getElement(final int index) {
        final int indexEersteElement = 0;
        Expressie element = NullValue.getInstance();
        if (index == indexEersteElement) {
            element = this;
        }
        return element;
    }

    @Override
    public boolean alsBoolean() {
        return false;
    }

    @Override
    public int alsInteger() {
        return 0;
    }

    @Override
    public long alsLong() {
        return 0L;
    }

    @Override
    public String alsString() {
        return this.toString();
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
        return false;
    }

    @Override
    public Expressie optimaliseer(final Context context) {
        return this;
    }

    @Override
    public String toString() {
        return "FOUT:" + getFoutCode().getFoutmelding() + ":" + getInformatie();
    }
}
