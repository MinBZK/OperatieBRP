package nl.bzk.brp.model.groep.operationeel.actueel;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.groep.logisch.PersoonAdresStandaardGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonAdresStandaardGroepBasis;
import nl.bzk.brp.model.groep.operationeel.actueel.basis.AbstractPersoonAdresStandaardActGroepModel;


/**
 *
 */
@Embeddable
@SuppressWarnings("serial")
public class PersoonAdresStandaardGroepModel extends
        AbstractPersoonAdresStandaardActGroepModel implements
        PersoonAdresStandaardGroep
{

    /**
     * Default constructor tbv hibernate.
     */
    @SuppressWarnings("unused")
    private PersoonAdresStandaardGroepModel() {
        super();
    }

    /**
     * Copy constructor.
     *
     * @param groep De te kopieren groep
     */
    public PersoonAdresStandaardGroepModel(
            final PersoonAdresStandaardGroepBasis groep)
    {
        super(groep);
    }
}
