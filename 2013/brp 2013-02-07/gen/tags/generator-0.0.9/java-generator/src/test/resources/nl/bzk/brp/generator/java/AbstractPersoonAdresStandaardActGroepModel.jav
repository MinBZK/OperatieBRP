package nl.bzk.brp.model.groep.operationeel.actueel.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.brp.model.basis.Onderzoekbaar;
import nl.bzk.brp.model.groep.logisch.basis.PersoonAdresStandaardGroepBasis;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonAdresStandaardGroep;


/**
 * Implementatie voor groep "Standaard" van objecttype "Persoon \ Adres".
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonAdresStandaardActGroepModel extends
        AbstractPersoonAdresStandaardGroep implements Onderzoekbaar
{

    @Transient
    private boolean inOnderzoek;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonAdresStandaardActGroepModel() {
        super();
    }

    /**
     * Copy constructor.
     *
     * @param groep De te kopieren groep
     */
    protected AbstractPersoonAdresStandaardActGroepModel(
            final PersoonAdresStandaardGroepBasis groep)
    {
        super(groep);
    }

    @Override
    public boolean isInOnderzoek() {
        return inOnderzoek;
    }

}
