package nl.bzk.brp.model.groep.operationeel.actueel;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.groep.logisch.BetrokkenheidOuderlijkGezagGroep;
import nl.bzk.brp.model.groep.logisch.basis.BetrokkenheidOuderlijkGezagGroepBasis;
import nl.bzk.brp.model.groep.operationeel.actueel.basis.AbstractBetrokkenheidOuderlijkGezagActGroepModel;


/**
 *
 */
@Embeddable
@SuppressWarnings("serial")
public class BetrokkenheidOuderlijkGezagGroepModel extends
        AbstractBetrokkenheidOuderlijkGezagActGroepModel implements
        BetrokkenheidOuderlijkGezagGroep
{

    /**
     * Default constructor tbv hibernate.
     */
    @SuppressWarnings("unused")
    private BetrokkenheidOuderlijkGezagGroepModel() {
        super();
    }

    /**
     * Copy constructor.
     *
     * @param groep De te kopieren groep
     */
    public BetrokkenheidOuderlijkGezagGroepModel(
            final BetrokkenheidOuderlijkGezagGroepBasis groep)
    {
        super(groep);
    }
}
