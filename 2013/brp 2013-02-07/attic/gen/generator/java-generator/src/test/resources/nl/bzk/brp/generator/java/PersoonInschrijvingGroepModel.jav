package nl.bzk.brp.model.groep.operationeel.actueel;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.groep.logisch.PersoonInschrijvingGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonInschrijvingGroepBasis;
import nl.bzk.brp.model.groep.operationeel.actueel.basis.AbstractPersoonInschrijvingActGroepModel;


/**
 *
 */
@Embeddable
@SuppressWarnings("serial")
public class PersoonInschrijvingGroepModel extends
        AbstractPersoonInschrijvingActGroepModel implements
        PersoonInschrijvingGroep
{

    /**
     * Default constructor tbv hibernate.
     */
    @SuppressWarnings("unused")
    private PersoonInschrijvingGroepModel() {
        super();
    }

    /**
     * Copy constructor.
     *
     * @param groep De te kopieren groep
     */
    public PersoonInschrijvingGroepModel(
            final PersoonInschrijvingGroepBasis groep)
    {
        super(groep);
    }
}
