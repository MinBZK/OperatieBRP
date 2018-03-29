/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.validator;

import com.google.common.collect.Lists;
import java.util.function.Supplier;
import nl.bzk.brp.domain.expressie.ExpressieRuntimeException;
import nl.bzk.brp.domain.expressie.GetalLiteral;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.NullLiteral;
import nl.bzk.brp.domain.expressie.element.ElementExpressie;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OperandValidatorTest {

    private final GetalLiteral getalLiteral = new GetalLiteral(13L);
    private final LijstExpressie lijstExpressie = new LijstExpressie(Lists.newArrayList(getalLiteral, getalLiteral));
    private final LijstExpressie lijstExpressieNietConstant = new LijstExpressie(Lists.newArrayList(new ElementExpressie("persoon", "Persoon.Indicatie.OnderCuratele"), getalLiteral));
    private final LijstExpressie lijstExpressieNietHomogeneCompositie = new LijstExpressie(Lists.newArrayList(lijstExpressie, getalLiteral));
    private final LijstExpressie lijstExpressieNonNullCompositie = new LijstExpressie(Lists.newArrayList(NullLiteral.INSTANCE, getalLiteral));
    private final Supplier supplier = () -> "Ongeldig";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void valideerIsLIteral() throws Exception {
        final OperandValidator validator = new OperandValidator(OperandValidator.IS_LITERAL);
        validator.valideer(getalLiteral, supplier);
    }

    @Test
    public void valideerIsLIteral_Fout() throws Exception {
        expectedException.expect(ExpressieRuntimeException.class);
        expectedException.expectMessage("Ongeldig : Operand moet een literal zijn");

        final OperandValidator validator = new OperandValidator(OperandValidator.IS_LITERAL);
        validator.valideer(lijstExpressie, supplier);
    }

    @Test
    public void valideerIsNietNull() {
        final OperandValidator validator = new OperandValidator(OperandValidator.IS_NIET_NULL);
        validator.valideer(lijstExpressie, supplier);
    }

    @Test
    public void valideerIsNietNull_Fout() {
        expectedException.expect(ExpressieRuntimeException.class);
        expectedException.expectMessage("Ongeldig : Operand mag niet NULL zijn");

        final OperandValidator validator = new OperandValidator(OperandValidator.IS_NIET_NULL);
        validator.valideer(NullLiteral.INSTANCE, supplier);
    }

    @Test
    public void valideerIsCompositie() {
        final OperandValidator validator = new OperandValidator(OperandValidator.IS_COMPOSITIE);
        validator.valideer(lijstExpressie, supplier);
    }

    @Test
    public void valideerIsCompositie_Fout() throws Exception {
        expectedException.expect(ExpressieRuntimeException.class);
        expectedException.expectMessage("Ongeldig : Operand moet een LijstExpressie zijn");

        final OperandValidator validator = new OperandValidator(OperandValidator.IS_COMPOSITIE);
        validator.valideer(getalLiteral, supplier);
    }

    @Test
    public void valideerIsHomogeneCompositie() throws Exception {
        final OperandValidator validator = new OperandValidator(OperandValidator.IS_HOMOGENE_COMPOSITIE);
        validator.valideer(lijstExpressie, supplier);
    }

    @Test
    public void valideerIsNietLegeCompositie() throws Exception {
        expectedException.expect(ExpressieRuntimeException.class);
        expectedException.expectMessage("Ongeldig : LijstExpressie moet homogeen zijn");

        final OperandValidator validator = new OperandValidator(OperandValidator.IS_HOMOGENE_COMPOSITIE);
        validator.valideer(lijstExpressieNietHomogeneCompositie, supplier);
    }

    @Test
    public void valideerIsNonNullCompositie() throws Exception {
        final OperandValidator validator = new OperandValidator(OperandValidator.IS_NON_NULL_COMPOSITIE);
        validator.valideer(lijstExpressie, supplier);
    }

    @Test
    public void valideerIsNonNullCompositie_Fout() throws Exception {
        expectedException.expect(ExpressieRuntimeException.class);
        expectedException.expectMessage("Ongeldig : LijstExpressie mag geen null bevatten");

        final OperandValidator validator = new OperandValidator(OperandValidator.IS_NON_NULL_COMPOSITIE);
        validator.valideer(lijstExpressieNonNullCompositie, supplier);
    }

    @Test
    public void valideerIsConstant() throws Exception {
        final OperandValidator validator = new OperandValidator(OperandValidator.IS_CONSTANT);
        validator.valideer(lijstExpressie, supplier);
    }

    @Test
    public void valideerIsConstant_Fout() throws Exception {
        expectedException.expect(ExpressieRuntimeException.class);
        expectedException.expectMessage("Ongeldig : Operand moet constant zijn");

        final OperandValidator validator = new OperandValidator(OperandValidator.IS_CONSTANT);
        validator.valideer(lijstExpressieNietConstant, supplier);
    }

}