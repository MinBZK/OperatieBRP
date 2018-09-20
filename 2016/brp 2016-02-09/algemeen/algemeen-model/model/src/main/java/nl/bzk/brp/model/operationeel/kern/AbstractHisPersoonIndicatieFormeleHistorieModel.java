/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.brp.model.basis.Attribuut;
import org.hibernate.annotations.Type;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.FormeleHistorie;
import nl.bzk.brp.model.basis.FormeleHistorieImpl;
import nl.bzk.brp.model.basis.FormeleHistorieModel;
import nl.bzk.brp.model.basis.VerantwoordingTbvLeveringMutaties;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieHisVolledigImpl;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatieStandaardGroep;


@MappedSuperclass
public abstract class AbstractHisPersoonIndicatieFormeleHistorieModel extends HisPersoonIndicatieModel implements
    FormeelHistorisch, FormeleHistorie, FormeelVerantwoordbaar<ActieModel>, VerantwoordingTbvLeveringMutaties
{

    @Embedded
    private FormeleHistorieImpl formeleHistorie = new FormeleHistorieImpl();

    @Transient
    @JsonProperty
    private ActieModel actieInhoud;

    @Transient
    @JsonProperty
    private ActieModel actieVerval;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "indvoorkomentbvlevmuts"))
    @JsonProperty
    @Type(type = "Ja")
    private JaAttribuut indicatieVoorkomenTbvLeveringMutaties;

    @JsonProperty
    @Transient
    private ActieModel actieVervalTbvLeveringMutaties;

    /**
     * Default constructor t.b.v. JPA
     */
    protected AbstractHisPersoonIndicatieFormeleHistorieModel() {
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonIndicatieHisVolledig instantie van A-laag klasse.
     * @param groep                       groep
     * @param historie                    historie
     * @param actieInhoud                 Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonIndicatieFormeleHistorieModel(
        final PersoonIndicatieHisVolledigImpl<?> persoonIndicatieHisVolledig,
        final PersoonIndicatieStandaardGroep groep, final FormeleHistorie historie, final ActieModel actieInhoud)
    {
        super(persoonIndicatieHisVolledig, groep);
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisPersoonIndicatieFormeleHistorieModel(
        final AbstractHisPersoonIndicatieFormeleHistorieModel kopie)
    {
        super(kopie);
        formeleHistorie = new FormeleHistorieImpl((FormeleHistorieImpl) kopie.getFormeleHistorie());

    }

    /**
     * @param persoonIndicatie persoonIndicatie van HisPersoonIndicatieModel
     * @param waarde           waarde van HisPersoonIndicatieModel
     * @param actieInhoud      Actie inhoud; de actie die leidt tot dit nieuwe record.
     * @param historie         De groep uit een bericht
     */
    public AbstractHisPersoonIndicatieFormeleHistorieModel(final PersoonIndicatieHisVolledigImpl<?> persoonIndicatie,
        final JaAttribuut waarde, final ActieModel actieInhoud, final FormeleHistorie historie)
    {
        super(persoonIndicatie, waarde);
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    @Override
    @JsonProperty
    public FormeleHistorieModel getFormeleHistorie() {
        return formeleHistorie;
    }

    @Override
    public DatumTijdAttribuut getTijdstipRegistratie() {
        return this.formeleHistorie.getTijdstipRegistratie();
    }

    @Override
    public DatumTijdAttribuut getDatumTijdVerval() {
        return this.formeleHistorie.getDatumTijdVerval();
    }

    @Access(AccessType.PROPERTY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actieinh")
    @Override
    public ActieModel getVerantwoordingInhoud() {
        return actieInhoud;
    }

    @Override
    public void setVerantwoordingInhoud(final ActieModel verantwoordingInhoud) {
        this.actieInhoud = verantwoordingInhoud;
    }

    @Access(AccessType.PROPERTY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actieverval")
    @Override
    public ActieModel getVerantwoordingVerval() {
        return actieVerval;
    }

    @Override
    public void setVerantwoordingVerval(final ActieModel verantwoordingVerval) {
        this.actieVerval = verantwoordingVerval;
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

    @Override
    public List<Attribuut> getAttributen() {
        return new ArrayList<>();
    }
}
