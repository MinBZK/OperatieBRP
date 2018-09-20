package nl.bzk.brp.model.objecttype.operationeel.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonAdresStandaardGroepModel;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonAdresBasis;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.StatusHistorie;


/**
 * Implementatie voor objecttype Persoon adres.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonAdresModel extends AbstractDynamischObjectType implements PersoonAdresBasis {

    @Id
    @SequenceGenerator(name = "PERSOONADRES", sequenceName = "Kern.seq_PersAdres")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSOONADRES")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel persoon;

    @Embedded
    @NotNull
    private PersoonAdresStandaardGroepModel gegevens;

    @Column(name = "PersAdresStatusHis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie standaardStatusHis = StatusHistorie.X;

    /**
     * Copy constructor.
     *
     * @param adres het adres
     * @param pers de persoon
     */
    protected AbstractPersoonAdresModel(final PersoonAdresBasis persoonAdres, final PersoonModel persoon) {
        super(persoonAdres);
        if (persoonAdres.getStandaardGroep() != null) {
            standaardGroep = new PersoonAdresStandaardGroepModel(persoonAdres.getStandaardGroep());
            standaardStatusHis = StatusHistorie.A;
        }
        this.persoon = persoon;
    }

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonAdresModel() {
    }

    /**
     * Vervang een lijst van groepen met nieuwe data.
     *
     * @param groepen de lijst
     */
    public void vervangGroepen(final Groep ... groepen) {
        for (Groep groep: groepen) {
            if (groep == null) {
                continue;
	        } else if (groep instanceof PersoonAdresStandaardGroepBasis) {
	            standaard =
	                new PersoonAdresStandaardGroepModel(
	                    (PersoonAdresStandaardGroepBasis) groep);
	            standaardStatusHis = StatusHistorie.A;
            }
        }
    }

    public Long getId() {
        return id;
    }

    @Override
    public PersoonModel getPersoon() {
        return persoon;
    }

    @Override
    public PersoonAdresStandaardGroepModel getGegevens() {
        return gegevens;
    }

    protected void vervang(final AbstractPersoonAdresModel nieuwAdres) {
        gegevens = new PersoonAdresStandaardGroepModel(nieuwAdres.getGegevens());
    }

    public StatusHistorie getStatusHistorie() {
        return standaardStatusHis;
    }

}
