package nl.bzk.brp.model.groep.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.BetrokkenheidOuderlijkGezagGroepBasis;
import org.hibernate.annotations.Type;


/**
 * Implementatie voor groep "Ouderlijk gezag" van objecttype "Betrokkenheid".
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractBetrokkenheidOuderlijkGezagGroep extends AbstractGroep implements
        BetrokkenheidOuderlijkGezagGroepBasis
{

    @Column(name = "IndOuderHeeftGezag")
    @Type(type = "JaNee")
    private JaNee indicatieOuderHeeftGezag;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractBetrokkenheidOuderlijkGezagGroep() {
        super();
    }

    /**
     * Copy constructor.
     *
     * @param groep De te kopieren groep
     */
    protected AbstractBetrokkenheidOuderlijkGezagGroep(
            final BetrokkenheidOuderlijkGezagGroepBasis groep)
    {
        indicatieOuderHeeftGezag = groep.getIndicatieOuderHeeftGezag();
    }

    @Override
    public JaNee getIndicatieOuderHeeftGezag() {
        return indicatieOuderHeeftGezag;
    }
}
