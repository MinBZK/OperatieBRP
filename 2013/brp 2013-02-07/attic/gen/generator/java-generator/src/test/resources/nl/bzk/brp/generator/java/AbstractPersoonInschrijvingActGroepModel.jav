package nl.bzk.brp.model.groep.operationeel.actueel.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.brp.model.basis.Onderzoekbaar;
import nl.bzk.brp.model.groep.logisch.basis.PersoonInschrijvingGroepBasis;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonInschrijvingGroep;


/**
 * Implementatie voor groep "Inschrijving" van objecttype "Persoon".
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonInschrijvingActGroepModel extends
        AbstractPersoonInschrijvingGroep implements Onderzoekbaar
{

    @Transient
    private boolean inOnderzoek;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonInschrijvingActGroepModel() {
        super();
    }

    /**
     * Copy constructor.
     *
     * @param groep De te kopieren groep
     */
    protected AbstractPersoonInschrijvingActGroepModel(
            final PersoonInschrijvingGroepBasis groep)
    {
        super(groep);
    }

    @Override
    public boolean isInOnderzoek() {
        return inOnderzoek;
    }

}
