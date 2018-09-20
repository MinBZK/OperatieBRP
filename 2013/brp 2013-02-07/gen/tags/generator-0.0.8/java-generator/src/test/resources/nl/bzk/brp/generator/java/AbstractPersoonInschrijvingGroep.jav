package nl.bzk.brp.model.groep.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Versienummer;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonInschrijvingGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;


/**
 * Implementatie voor groep "Inschrijving" van objecttype "Persoon".
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonInschrijvingGroep extends AbstractGroep implements
        PersoonInschrijvingGroepBasis
{

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatInschr"))
    private Datum datumInschrijving;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Versienr"))
    private Versienummer versienummer;

    @ManyToOne
    @JoinColumn(name = "VorigePers")
    private PersoonModel vorigePersoon;

    @ManyToOne
    @JoinColumn(name = "VolgendePers")
    private PersoonModel volgendePersoon;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonInschrijvingGroep() {
        super();
    }

    /**
     * Copy constructor.
     *
     * @param groep De te kopieren groep
     */
    protected AbstractPersoonInschrijvingGroep(
            final PersoonInschrijvingGroepBasis groep)
    {
        datumInschrijving = groep.getDatumInschrijving();
        versienummer = groep.getVersienummer();
        vorigePersoon = groep.getVorigePersoon();
        volgendePersoon = groep.getVolgendePersoon();
    }

    @Override
    public Datum getDatumInschrijving() {
        return datumInschrijving;
    }

    @Override
    public Versienummer getVersienummer() {
        return versienummer;
    }

    @Override
    public PersoonModel getVorigePersoon() {
        return vorigePersoon;
    }

    @Override
    public PersoonModel getVolgendePersoon() {
        return volgendePersoon;
    }
}
