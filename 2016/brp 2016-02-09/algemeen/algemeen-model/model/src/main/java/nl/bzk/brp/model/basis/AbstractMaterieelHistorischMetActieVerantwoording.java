/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NadereAanduidingVervalAttribuut;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import org.hibernate.annotations.Type;


/**
 *
 */
@Embeddable
@Access(AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractMaterieelHistorischMetActieVerantwoording extends AbstractGerelateerdIdentificeerbaar implements
    MaterieelVerantwoordbaar<ActieModel>, VerantwoordingTbvLeveringMutaties, MaterieelHistorisch, MaterieleHistorie, NadereAanduidingVervalToepasbaar
{

    @Embedded
    private MaterieleHistorieImpl materieleHistorie = new MaterieleHistorieImpl();

    @Transient
    private Verwerkingssoort verwerkingssoort;

    @JsonProperty
    @Transient
    private ActieModel actieInhoud;

    @JsonProperty
    @Transient
    private ActieModel actieVerval;

    @JsonProperty
    @Transient
    private ActieModel actieAanpassingGeldigheid;

    @Embedded
    @AttributeOverride(name = NadereAanduidingVervalAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "nadereaandverval"))
    @JsonProperty
    private NadereAanduidingVervalAttribuut nadereAanduidingVerval;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "indvoorkomentbvlevmuts"))
    @JsonProperty
    @Type(type = "Ja")
    private JaAttribuut indicatieVoorkomenTbvLeveringMutaties;

    @JsonProperty
    @Transient
    private ActieModel actieVervalTbvLeveringMutaties;

    /**
     * Copy constructor.
     *
     * @param kopie de kopie
     */
    protected AbstractMaterieelHistorischMetActieVerantwoording(
        final AbstractMaterieelHistorischMetActieVerantwoording kopie)
    {
        materieleHistorie = new MaterieleHistorieImpl((MaterieleHistorieImpl) kopie.getMaterieleHistorie());
        actieInhoud = kopie.getVerantwoordingInhoud();
        actieVerval = kopie.getVerantwoordingVerval();
        actieAanpassingGeldigheid = kopie.getVerantwoordingAanpassingGeldigheid();
        nadereAanduidingVerval = kopie.getNadereAanduidingVerval();
    }

    /**
     * Default constructor.
     */
    protected AbstractMaterieelHistorischMetActieVerantwoording() {

    }

    @Access(AccessType.PROPERTY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actieinh")
    @Override
    public ActieModel getVerantwoordingInhoud() {
        return actieInhoud;
    }

    @Override
    public void setVerantwoordingInhoud(final ActieModel verantwoodingInhoud) {
        this.actieInhoud = verantwoodingInhoud;
    }

    @Access(AccessType.PROPERTY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actieverval")
    @Override
    public ActieModel getVerantwoordingVerval() {
        return actieVerval;
    }

    @Override
    public void setVerantwoordingVerval(final ActieModel verantwoodingVerval) {
        this.actieVerval = verantwoodingVerval;
    }

    @Access(AccessType.PROPERTY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actieaanpgel")
    @Override
    public ActieModel getVerantwoordingAanpassingGeldigheid() {
        return actieAanpassingGeldigheid;
    }

    @Override
    public void setVerantwoordingAanpassingGeldigheid(final ActieModel actie) {
        actieAanpassingGeldigheid = actie;
    }

    @Override
    @JsonProperty
    public MaterieleHistorieModel getMaterieleHistorie() {
        return materieleHistorie;
    }

    @Override
    public DatumTijdAttribuut getTijdstipRegistratie() {
        return this.materieleHistorie.getTijdstipRegistratie();
    }

    @Override
    public DatumTijdAttribuut getDatumTijdVerval() {
        return this.materieleHistorie.getDatumTijdVerval();
    }

    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return this.materieleHistorie.getDatumAanvangGeldigheid();
    }

    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return this.materieleHistorie.getDatumEindeGeldigheid();
    }

    @Override
    public Verwerkingssoort getVerwerkingssoort() {
        return verwerkingssoort;
    }

    @Override
    public void setVerwerkingssoort(final Verwerkingssoort verwerkingsSoort) {
        this.verwerkingssoort = verwerkingsSoort;
    }

    @Override
    public boolean isMagGeleverdWorden() {
        return false;
    }

    @Override
    public NadereAanduidingVervalAttribuut getNadereAanduidingVerval() {
        return nadereAanduidingVerval;
    }

    @Override
    public void setNadereAanduidingVerval(final NadereAanduidingVervalAttribuut nadereAanduidingVerval) {
        this.nadereAanduidingVerval = nadereAanduidingVerval;
    }

    @Override
	public JaAttribuut getIndicatieVoorkomenTbvLeveringMutaties() {
		return indicatieVoorkomenTbvLeveringMutaties;
	}

    @Override
	public void setIndicatieVoorkomenTbvLeveringMutaties(final JaAttribuut isVoorkomenTbvLeveringMutaties) {
		this.indicatieVoorkomenTbvLeveringMutaties = isVoorkomenTbvLeveringMutaties;
	}

	@Access(AccessType.PROPERTY)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "actievervaltbvlevmuts")
    @Override
	public ActieModel getVerantwoordingVervalTbvLeveringMutaties() {
		return actieVervalTbvLeveringMutaties;
	}

    @Override
	public void setVerantwoordingVervalTbvLeveringMutaties(final ActieModel verantwoordingVervalTbvLeveringMutaties) {
		this.actieVervalTbvLeveringMutaties = verantwoordingVervalTbvLeveringMutaties;
	}

}
