/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonDeelnameEUVerkiezingenGroep;
import nl.bzk.brp.model.logisch.kern.PersoonDeelnameEUVerkiezingenGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 3901)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPersoonDeelnameEUVerkiezingenModel extends AbstractFormeelHistorischMetActieVerantwoording implements
        PersoonDeelnameEUVerkiezingenGroepBasis, ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONDEELNAMEEUVERKIEZINGEN", sequenceName = "Kern.seq_His_PersDeelnEUVerkiezingen")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONDEELNAMEEUVERKIEZINGEN")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PersoonHisVolledigImpl.class)
    @JoinColumn(name = "Pers")
    @JsonBackReference
    private PersoonHisVolledig persoon;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndDeelnEUVerkiezingen"))
    @JsonProperty
    private JaNeeAttribuut indicatieDeelnameEUVerkiezingen;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatAanlAanpDeelnEUVerkiezing"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumAanleidingAanpassingDeelnameEUVerkiezingen;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatVoorzEindeUitslEUVerkiezi"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeUitsluitingEUVerkiezingen;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPersoonDeelnameEUVerkiezingenModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param persoon persoon van HisPersoonDeelnameEUVerkiezingenModel
     * @param indicatieDeelnameEUVerkiezingen indicatieDeelnameEUVerkiezingen van HisPersoonDeelnameEUVerkiezingenModel
     * @param datumAanleidingAanpassingDeelnameEUVerkiezingen datumAanleidingAanpassingDeelnameEUVerkiezingen van
     *            HisPersoonDeelnameEUVerkiezingenModel
     * @param datumVoorzienEindeUitsluitingEUVerkiezingen datumVoorzienEindeUitsluitingEUVerkiezingen van
     *            HisPersoonDeelnameEUVerkiezingenModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonDeelnameEUVerkiezingenModel(
        final PersoonHisVolledig persoon,
        final JaNeeAttribuut indicatieDeelnameEUVerkiezingen,
        final DatumEvtDeelsOnbekendAttribuut datumAanleidingAanpassingDeelnameEUVerkiezingen,
        final DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeUitsluitingEUVerkiezingen,
        final ActieModel actieInhoud)
    {
        this.persoon = persoon;
        this.indicatieDeelnameEUVerkiezingen = indicatieDeelnameEUVerkiezingen;
        this.datumAanleidingAanpassingDeelnameEUVerkiezingen = datumAanleidingAanpassingDeelnameEUVerkiezingen;
        this.datumVoorzienEindeUitsluitingEUVerkiezingen = datumVoorzienEindeUitsluitingEUVerkiezingen;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisPersoonDeelnameEUVerkiezingenModel(final AbstractHisPersoonDeelnameEUVerkiezingenModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        indicatieDeelnameEUVerkiezingen = kopie.getIndicatieDeelnameEUVerkiezingen();
        datumAanleidingAanpassingDeelnameEUVerkiezingen = kopie.getDatumAanleidingAanpassingDeelnameEUVerkiezingen();
        datumVoorzienEindeUitsluitingEUVerkiezingen = kopie.getDatumVoorzienEindeUitsluitingEUVerkiezingen();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonDeelnameEUVerkiezingenModel(
        final PersoonHisVolledig persoonHisVolledig,
        final PersoonDeelnameEUVerkiezingenGroep groep,
        final ActieModel actieInhoud)
    {
        this.persoon = persoonHisVolledig;
        this.indicatieDeelnameEUVerkiezingen = groep.getIndicatieDeelnameEUVerkiezingen();
        this.datumAanleidingAanpassingDeelnameEUVerkiezingen = groep.getDatumAanleidingAanpassingDeelnameEUVerkiezingen();
        this.datumVoorzienEindeUitsluitingEUVerkiezingen = groep.getDatumVoorzienEindeUitsluitingEUVerkiezingen();
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Persoon Deelname EU verkiezingen.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Deelname EU verkiezingen.
     *
     * @return Persoon.
     */
    public PersoonHisVolledig getPersoon() {
        return persoon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9766)
    public JaNeeAttribuut getIndicatieDeelnameEUVerkiezingen() {
        return indicatieDeelnameEUVerkiezingen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9767)
    public DatumEvtDeelsOnbekendAttribuut getDatumAanleidingAanpassingDeelnameEUVerkiezingen() {
        return datumAanleidingAanpassingDeelnameEUVerkiezingen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9768)
    public DatumEvtDeelsOnbekendAttribuut getDatumVoorzienEindeUitsluitingEUVerkiezingen() {
        return datumVoorzienEindeUitsluitingEUVerkiezingen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (indicatieDeelnameEUVerkiezingen != null) {
            attributen.add(indicatieDeelnameEUVerkiezingen);
        }
        if (datumAanleidingAanpassingDeelnameEUVerkiezingen != null) {
            attributen.add(datumAanleidingAanpassingDeelnameEUVerkiezingen);
        }
        if (datumVoorzienEindeUitsluitingEUVerkiezingen != null) {
            attributen.add(datumVoorzienEindeUitsluitingEUVerkiezingen);
        }
        return attributen;
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_DEELNAMEEUVERKIEZINGEN;
    }

}
