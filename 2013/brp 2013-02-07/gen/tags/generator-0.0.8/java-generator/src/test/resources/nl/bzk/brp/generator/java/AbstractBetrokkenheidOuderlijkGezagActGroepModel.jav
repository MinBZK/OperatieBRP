package nl.bzk.brp.model.groep.operationeel.actueel.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.brp.model.basis.Onderzoekbaar;
import nl.bzk.brp.model.groep.logisch.basis.BetrokkenheidOuderlijkGezagGroepBasis;
import nl.bzk.brp.model.groep.operationeel.AbstractBetrokkenheidOuderlijkGezagGroep;


/**
 * Implementatie voor groep "Ouderlijk gezag" van objecttype "Betrokkenheid".
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractBetrokkenheidOuderlijkGezagActGroepModel extends
        AbstractBetrokkenheidOuderlijkGezagGroep implements Onderzoekbaar
{

    @Transient
    private boolean inOnderzoek;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractBetrokkenheidOuderlijkGezagActGroepModel() {
        super();
    }

    /**
     * Copy constructor.
     *
     * @param groep De te kopieren groep
     */
    protected AbstractBetrokkenheidOuderlijkGezagActGroepModel(
            final BetrokkenheidOuderlijkGezagGroepBasis groep)
    {
        super(groep);
    }

    @Override
    public boolean isInOnderzoek() {
        return inOnderzoek;
    }

}
