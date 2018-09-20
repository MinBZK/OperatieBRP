/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.lang.reflect.InvocationTargetException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVeld;


/**
 * Conditioneel veld afhankelijk van de waarde in een andere veld.
 */
public class ConditioneelVeldValidator implements ConstraintValidator<ConditioneelVeld, Object> {

    private String                         naamVerplichtVeld;
    private String                         naamAfhankelijkVeld;
    private String                         bevatWaarde;
    private ConditioneelVeld.ConditieRegel conditioneelRegel;

    @Override
    public void initialize(final ConditioneelVeld constraintAnnotation) {
        this.naamVerplichtVeld = constraintAnnotation.danVoldoetRegelInInhoudVanVeld();
        this.naamAfhankelijkVeld = constraintAnnotation.wanneerInhoudVanVeld();
        this.bevatWaarde = constraintAnnotation.isGelijkAan();
        this.conditioneelRegel = constraintAnnotation.aanConditieRegel();
    }

    @Override
    /**
     * Synchroon (default, zie boven):
     * - Als veld 1 de waarde 'x' heeft dan MAG NIET veld 2 null zijn.
     * - Als veld 1 NIET de waarde 'x' (incl. null) heeft, dan MOET veld 2 null zijn.
     *
     * Exclusief:
     * - Als veld 1 de waarde 'x' heeft dan MOET veld 2 null zijn.
     * - Als veld 1 NIET de waarde 'x' (incl. null) heeft, dan MAG NIET veld 2 null zijn.
     *
     * Verder kunnen we deze constraint ook uitbouwen met 'wildcard' als waarde.
     *
     * Dus wildcard in combinatie met synchroon:
     * - Als veld 1 wildcard heeft (dus niet null is), dan MAG veld 2 NIET null zijn.
     * - Als veld 1 null is , dan MOET veld 2 null zijn.
     *
     * Dus wildcard in combinatie met exclusief:
     * - Als veld 1 wildcard heeft (dus niet null is), dan MOET veld 2 null zijn.
     * - Als veld 1 null is , dan MAG NIET veld 2 null zijn.
     *
     * Synchroon_Dont_Care
     * - Als veld 1 de waarde 'x' heeft dan MAG veld 2 NIET null zijn.
     * - Als veld 1 filter = wildcard en is null, dan mag veld 2 alles zijn.
     * - Als veld 1 NIET de waarde 'x', dan mag veld 2 alles zijn.
     *
     * Exclusief_Dont_Care
     * - Als veld 1 de waarde 'x' heeft dan MOET veld 2 null zijn.
     * - Als veld 1 filter = wildcard en is null, dan mag veld 2 alles zijn.
     * - Als veld 1 NIET de waarde 'x', dan mag veld 2 alles zijn.
     *
     */
    public boolean isValid(final Object waarde, final ConstraintValidatorContext context)
    {
        final boolean resultaat;

        final Object verplichtVeld;
        final Object afhankelijkVeld;
        try {
            verplichtVeld = ValidatorUtil.haalWaardeOp(waarde, this.naamVerplichtVeld);
            afhankelijkVeld = ValidatorUtil.haalWaardeOp(waarde, this.naamAfhankelijkVeld);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new IllegalArgumentException(ex);
        }

        if (voldoetAanVoorwaarde(afhankelijkVeld)) {
            if (this.conditioneelRegel == ConditioneelVeld.ConditieRegel.SYNCHROON) {
                // verplicht veld MAG NIET null zijn.
                resultaat = !verplichtVeldIsNull(verplichtVeld);
            } else if (this.conditioneelRegel == ConditioneelVeld.ConditieRegel.EXCLUSIEF) {
                // exclusief: verplicht veld MOET null zijn.
                resultaat = verplichtVeldIsNull(verplichtVeld);
            } else if (this.conditioneelRegel == ConditioneelVeld.ConditieRegel.SYNCHROON_IF_NULL_DONT_CARE
                && afhankelijkVeld != null)
            {
                // als we hier komen, dan voldet hij aan de conditie (wildcard + null of EEN waarde).
                // als de mode is 'dont'care' test alleen als de runtime waarde niet null is.
                resultaat = !verplichtVeldIsNull(verplichtVeld);
            } else if (this.conditioneelRegel == ConditioneelVeld.ConditieRegel.EXCLUSIEF_IF_NULL_DONT_CARE
                && afhankelijkVeld != null)
            {
                // als we hier komen, dan voldet hij aan de conditie (wildcard + null of EEN waarde).
                // als de mode is 'dont'care' test alleen als de runtime waarde niet null is.
                resultaat = verplichtVeldIsNull(verplichtVeld);
            } else {
                // dit is als * dontcare EN runtime conditieveld is null ==> altijd valide.
                resultaat = true;
            }
        } else {
            // Voldoet niet aan de voorwaarde.
            if (this.conditioneelRegel == ConditioneelVeld.ConditieRegel.SYNCHROON) {
                // Als de voorwaarde was een wildcard en voldoet er niet aan, dan is de waarde blijkbaar null
                // als de voorwaarde was single waarde, dan
                // ==> verplicht veld moet ook null zijn.
                resultaat = verplichtVeldIsNull(verplichtVeld);
            } else if (this.conditioneelRegel == ConditioneelVeld.ConditieRegel.EXCLUSIEF) {
                // Als de voorwaarde was een wildcard en voldoet er niet aan, dan is de waarde blijkbaar null
                // ==> verplicht veld MAG NIET null zijn.
                resultaat = !verplichtVeldIsNull(verplichtVeld);
            } else {
                // voldoet niet, dont care => valide
                resultaat = true;
            }
        }

        return resultaat;
    }

    /**
     * Test of de conditionele veld voldoet aan een criteria. Als de criteria is wildcard, dan voldoet ELK NIET NULL waarde aan de conditie. Als de
     * criteria is een vast waarde, dan voldoet het alleen als de waarde hieraan gelijk is.
     *
     * @param conditioneelVeld conditioneel veld
     * @return true als het aan voorwaarden voldoet
     */
    private boolean voldoetAanVoorwaarde(final Object conditioneelVeld) {
        boolean voldoet;
        if (null == conditioneelVeld) {
            voldoet = false;
        } else if (this.bevatWaarde.equals(ConditioneelVeld.WILDCARD_WAARDE_CODE)) {
            voldoet = true;
        } else {
            if (bevatWaarde.startsWith(ConditioneelVeld.OPERATOR_NOT)) {
                voldoet = !this.bevatWaarde.equals(ConditioneelVeld.OPERATOR_NOT + conditioneelVeld.toString());
            } else {
                voldoet = this.bevatWaarde.equals(conditioneelVeld.toString());
            }
        }
        return voldoet;
    }

    /**
     * Controleert of verplicht veld null is.
     *
     * @param verplichtVeld het verplichte veld
     * @return true als verplichyt veld null is
     */
    private boolean verplichtVeldIsNull(final Object verplichtVeld) {
        return verplichtVeld == null;
    }
}
