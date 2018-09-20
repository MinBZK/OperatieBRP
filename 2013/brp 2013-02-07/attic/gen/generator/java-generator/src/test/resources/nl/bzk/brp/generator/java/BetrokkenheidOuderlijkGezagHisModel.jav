package nl.bzk.brp.model.groep.operationeel.historisch;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.groep.operationeel.AbstractBetrokkenheidOuderlijkGezagGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.basis.AbstractBetrokkenheidOuderlijkGezagHisModel;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;


/**
 * User implementatie van {@link AbstractBetrokkenheidOuderlijkGezagHisModel}.
 */
@Entity
@Table(schema = "kern", name = "His_BetrOuderlijkGezag")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class BetrokkenheidOuderlijkGezagHisModel extends
        AbstractBetrokkenheidOuderlijkGezagHisModel
{

    /**
     * Default constructor tbv hibernate.
     */
    @SuppressWarnings("unused")
    private BetrokkenheidOuderlijkGezagHisModel() {
        super();
    }

    /**
     * Constructor.
     *
     * @param groep
     * @param betrokkenheid
     */
    public BetrokkenheidOuderlijkGezagHisModel(
            final AbstractBetrokkenheidOuderlijkGezagGroep groep,
            final BetrokkenheidModel betrokkenheid)
    {
        super(groep, betrokkenheid);
    }

    public BetrokkenheidOuderlijkGezagHisModel kopieer() {
        return new BetrokkenheidOuderlijkGezagHisModel(this, getBetrokkenheid());
    }
}
