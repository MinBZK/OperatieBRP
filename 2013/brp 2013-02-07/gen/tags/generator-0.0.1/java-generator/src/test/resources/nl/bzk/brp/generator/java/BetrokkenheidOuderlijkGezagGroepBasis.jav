package nl.bzk.brp.model.groep.logisch.basis;

import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.basis.Groep;


/**
 * Interface voor groep "Ouderlijk gezag" van objecttype "Betrokkenheid".
 */
public interface BetrokkenheidOuderlijkGezagGroepBasis extends Groep {

    /**
     * Indicator die aangeeft dat de betrokken ouder het gezag heeft over het kind.
     *
     * @return Ja \ Nee
     */
    JaNee getIndicatieOuderHeeftGezag();

}
