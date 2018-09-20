/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.validatie.validator.ConditioneelVeldValidator;


/**
 * Conditioneel Synchroon veld is een validatie regel waarbij een de verplichting van een ander veld afhangt van de waarde van deze veld.
 * <p/>
 * bijvoorbeeld: - Als veld 1 de waarde 'x' heeft dan MAG NIET veld 2 null zijn. - Als veld 1 NIET de waarde 'x' heeft, dan MOET veld 2 null zijn.
 * <p/>
 * De eerste deel kunnen we implemneteren met conditioneelVerplichtVeld (en verplichtNull = true), maar de tweede deel kunnen we niet omdat de 'waarde'
 * conditie is 'anders dan x'.
 * <p/>
 * <p/>
 * <p/>
 * Om de tegenovergestelde regel te implementeren (exclusief), kunnen we de parameter mode gebruiken.
 * <p/>
 * Synchroon (default, zie boven): - Als veld 1 de waarde 'x' heeft dan MAG NIET veld 2 null zijn. - Als veld 1 NIET de waarde 'x' (incl. null) heeft, dan
 * MOET veld 2 null zijn.
 * <p/>
 * Exclusief: - Als veld 1 de waarde 'x' heeft dan MOET veld 2 null zijn. - Als veld 1 NIET de waarde 'x' (incl. null) heeft, dan MAG NIET veld 2 null
 * zijn.
 * <p/>
 * Verder kunnen we deze constraint ook uitbouwen met 'wildcard' als waarde.
 * <p/>
 * Dus wildcard in combinatie met synchroon: - Als veld 1 wildcard heeft (dus niet null is), dan MAG NIET veld 2 null zijn. - Als veld 1 null is , dan MOET
 * veld 2 null zijn.
 * <p/>
 * Dus wildcard in combinatie met exclusief: - Als veld 1 wildcard heeft (dus niet null is), dan MOET veld 2 null zijn. - Als veld 1 null is , dan MAG NIET
 * veld 2 null zijn.
 */
@Documented
@Constraint(validatedBy = ConditioneelVeldValidator.class)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ConditioneelVeld {

    /**
     * Constante.
     */
    public static final String NAAMGEBRUIK_ALGORITMISCH_AFGELEID_BOOLEAN_JA  = "true";

    /**
     * Constante.
     */
    public static final String NAAMGEBRUIK_ALGORITMISCH_AFGELEID_BOOLEAN_NEE = "false";

    /**
     * Constante.
     */
    public static final String SAMENGESTELDENAAM_INDICATIE_NAMENREEKS_BOOLEAN_JA  = "true";

    /**
     * Constante.
     */
    public static final String SAMENGESTELDENAAM_INDICATIE_NAMENREEKS_BOOLEAN_NEE = "false";

    /**
     * Operator te gebruiken bij veldje is gelijkAan. Hiermee kan aangegeven dat het gelijk is aan !conditie.
     * <p/>
     * Bijvoorbeeld isGelijkAan = ConditioneelVeld.OPERATOR_NOT + LandGebiedCodeAttribuut.NL_LAND_CODE_STRING
     */
    String OPERATOR_NOT = "!";


    /**
     * Een lijst van ondersteunde conditie regels.
     */
    enum ConditieRegel {
        /**
         * als de waarde van een voldoet aan de conditie, dan MAG de ander NIET leeg zijn. als de waarde = wildcard, en runtime is null, dan MOET de ander
         * LEEG zijn. als de waarde van een NIET voldoet aan de conditie, dan MOET de ander leeg zijn.
         */
        SYNCHROON,

        /**
         * als de waarde van een voldoet aan de conditie, dan MOET de ander leeg zijn. als de waarde = wildcard, en runtime is null, dan MAG de ander NIET
         * leeg zijn. als de waarde van een NIET voldoet aan de conditie, dan MAG de ander NIET leeg zijn.
         */
        EXCLUSIEF,

        /**
         * als de waarde van een voldoet aan de conditie, dan MAG de ander NIET leeg zijn. als de waarde = wildcard, en runtime is null, dan de ander
         * DONT_CARE. als de waarde van een NIET voldoet aan de conditie, dan de ander DONT_CARE.
         */
        SYNCHROON_IF_NULL_DONT_CARE,

        /**
         * als de waarde van een voldoet aan de conditie, dan MOET de ander leeg zijn. als de waarde = wildcard, en runtime is null, dan de ander
         * DONT_CARE. als de waarde van een NIET voldoet aan de conditie, dan de ander DONT_CARE.
         */
        EXCLUSIEF_IF_NULL_DONT_CARE
    }


    /**
     * deze is is nodig om in de constraint validator aan te geven dat om een wildcard of NOT NULL waarden gaat.
     */
    String WILDCARD_WAARDE_CODE = "___*___";

    /**
     * De veld naam waar de conditioneel verplichte veld van afhankelijk is.
     */
    String wanneerInhoudVanVeld();

    /**
     * De waarde waaraan moet voldoen om de veld gespecificeerd in danVoldoetRegelInInhoudVanVeld veplicht te maken.
     */
    String isGelijkAan();

    /**
     * Veld dat verplicht moet zijn afhankelijk van de waarde (bevatWaarde) in de veld gespecificeerd in wanneerInhoudVanVeld.
     */
    String danVoldoetRegelInInhoudVanVeld();

    /**
     * Welk conditie regel is van toepassing.
     */
    ConditieRegel aanConditieRegel();

    /**
     * Standaard validatie bericht.
     */
    String message() default "";

    /**
     * Standaard validatie groep.
     */
    Class<?>[] groups() default { };

    /**
     * Verplicht veld voor de validatie framework.
     */
    Class<? extends Payload>[] payload() default { };

    /**
     * De bedrijfsregel code.
     */
    Regel code();

    /**
     * Standaard regel effect BR (blocker).
     */
    SoortMelding soortMelding() default SoortMelding.FOUT;

    /**
     * Database meta id waarop de constraint van toepassing is.
     */
    DatabaseObjectKern dbObject();
}
