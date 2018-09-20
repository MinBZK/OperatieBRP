/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut;
import nl.bzk.brp.model.validatie.constraint.CustomSpEL;
import org.apache.commons.lang.StringUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;


/**
 * Validator die gebruikt maakt van Spring Expression Language (http://static.springsource.org/spring/docs/3.0.x/reference/expressions.html)
 * <p/>
 * Aan de validatie expressie is de isBlank methode toegevoegd en enkele variabelen voor constanten.
 * <p/>
 * Voorbeeld gebruik:
 * <p/>
 * wanneerVeldVoldoetAanRegel = "land?.waarde?.code?.waarde == #LandGebiedCodeAttribuut.NL_LAND_CODE_SHORT and "
 * + "redenWijziging?.waarde?.code?.waarde ==
 * #RedenWijzigingVerblijfCodeAttribuut.PERSOON_REDEN_WIJZIGING_ADRES_CODE_STRING", danMoetVeldVoldoenAanRegel
 * = "!#isBlank(afgekorteNaamOpenbareRuimte?.waarde)", code =
 * Regel.BRAL2027, message = "BRAL2027"
 * <p/>
 * Wat hier staat is, afgekorteNaamOpenbareRuimte mag niet leeg zijn als land == NL is && redenWijzing == PERSOON_REDEN_WIJZIGING_ADRES_CODE_STRING
 */
public class CustomSpELValidator implements ConstraintValidator<CustomSpEL, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String IS_BLANK = "isBlank";

    private String wanneerVeldVoldoetAanRegel;
    private String danMoetVeldVoldoenAanRegel;

    @Override
    public void initialize(final CustomSpEL constraintAnnotation) {
        wanneerVeldVoldoetAanRegel = constraintAnnotation.wanneerVeldVoldoetAanRegel();
        danMoetVeldVoldoenAanRegel = constraintAnnotation.danMoetVeldVoldoenAanRegel();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        final ExpressionParser parser = new SpelExpressionParser();
        final StandardEvaluationContext evalContext = maakEvaluatieContext(value);

        Expression exp = parser.parseExpression(wanneerVeldVoldoetAanRegel);
        final boolean wanneerVeldVoldoetResult = exp.getValue(evalContext, Boolean.class);

        if (wanneerVeldVoldoetResult) {
            exp = parser.parseExpression(danMoetVeldVoldoenAanRegel);
            return exp.getValue(evalContext, Boolean.class);
        }

        return true;
    }

    /**
     * Registratie van custom methods of variabelen.
     *
     * @param teEvaluerenObject het te evalueren object
     * @return standard evaluatie context
     */
    private StandardEvaluationContext maakEvaluatieContext(final Object teEvaluerenObject) {
        final StandardEvaluationContext evalContext = new StandardEvaluationContext(teEvaluerenObject);
        try {
            evalContext.registerFunction(IS_BLANK, StringUtils.class.getDeclaredMethod(IS_BLANK, new Class[]{ String.class }));
        } catch (final NoSuchMethodException e) {
            LOGGER.error(e.getMessage());
            // Dit zou niet kunnen gebeuren.
            throw new IllegalArgumentException(e);
        }

        evalContext.setVariable("LandGebiedCodeAttribuut", LandGebiedCodeAttribuut.class);
        evalContext.setVariable("RedenWijzigingVerblijfCodeAttribuut", RedenWijzigingVerblijfCodeAttribuut.class);

        return evalContext;
    }
}
