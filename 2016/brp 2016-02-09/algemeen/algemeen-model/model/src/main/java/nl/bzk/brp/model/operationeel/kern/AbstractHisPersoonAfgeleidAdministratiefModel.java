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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SorteervolgordeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonAfgeleidAdministratiefGroep;
import nl.bzk.brp.model.logisch.kern.PersoonAfgeleidAdministratiefGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 3909)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPersoonAfgeleidAdministratiefModel extends AbstractFormeelHistorischMetActieVerantwoording implements
        PersoonAfgeleidAdministratiefGroepBasis, ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONAFGELEIDADMINISTRATIEF", sequenceName = "Kern.seq_His_PersAfgeleidAdministrati")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONAFGELEIDADMINISTRATIEF")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PersoonHisVolledigImpl.class)
    @JoinColumn(name = "Pers")
    @JsonBackReference
    private PersoonHisVolledig persoon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AdmHnd")
    @JsonProperty
    private AdministratieveHandelingModel administratieveHandeling;

    @Embedded
    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "TsLaatsteWijz"))
    @JsonProperty
    private DatumTijdAttribuut tijdstipLaatsteWijziging;

    @Embedded
    @AttributeOverride(name = SorteervolgordeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Sorteervolgorde"))
    @JsonProperty
    private SorteervolgordeAttribuut sorteervolgorde;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndOnverwBijhvoorstelNietIng"))
    @JsonProperty
    private JaNeeAttribuut indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig;

    @Embedded
    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "TsLaatsteWijzGBASystematiek"))
    @JsonProperty
    private DatumTijdAttribuut tijdstipLaatsteWijzigingGBASystematiek;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPersoonAfgeleidAdministratiefModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param persoon persoon van HisPersoonAfgeleidAdministratiefModel
     * @param administratieveHandeling administratieveHandeling van HisPersoonAfgeleidAdministratiefModel
     * @param tijdstipLaatsteWijziging tijdstipLaatsteWijziging van HisPersoonAfgeleidAdministratiefModel
     * @param sorteervolgorde sorteervolgorde van HisPersoonAfgeleidAdministratiefModel
     * @param indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig
     *            indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig van HisPersoonAfgeleidAdministratiefModel
     * @param tijdstipLaatsteWijzigingGBASystematiek tijdstipLaatsteWijzigingGBASystematiek van
     *            HisPersoonAfgeleidAdministratiefModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonAfgeleidAdministratiefModel(
        final PersoonHisVolledig persoon,
        final AdministratieveHandelingModel administratieveHandeling,
        final DatumTijdAttribuut tijdstipLaatsteWijziging,
        final SorteervolgordeAttribuut sorteervolgorde,
        final JaNeeAttribuut indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig,
        final DatumTijdAttribuut tijdstipLaatsteWijzigingGBASystematiek,
        final ActieModel actieInhoud)
    {
        this.persoon = persoon;
        this.administratieveHandeling = administratieveHandeling;
        this.tijdstipLaatsteWijziging = tijdstipLaatsteWijziging;
        this.sorteervolgorde = sorteervolgorde;
        this.indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig = indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig;
        this.tijdstipLaatsteWijzigingGBASystematiek = tijdstipLaatsteWijzigingGBASystematiek;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisPersoonAfgeleidAdministratiefModel(final AbstractHisPersoonAfgeleidAdministratiefModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        administratieveHandeling = kopie.getAdministratieveHandeling();
        tijdstipLaatsteWijziging = kopie.getTijdstipLaatsteWijziging();
        sorteervolgorde = kopie.getSorteervolgorde();
        indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig = kopie.getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig();
        tijdstipLaatsteWijzigingGBASystematiek = kopie.getTijdstipLaatsteWijzigingGBASystematiek();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonAfgeleidAdministratiefModel(
        final PersoonHisVolledig persoonHisVolledig,
        final PersoonAfgeleidAdministratiefGroep groep,
        final ActieModel actieInhoud)
    {
        this.persoon = persoonHisVolledig;
        if (groep.getAdministratieveHandeling() != null) {
            this.administratieveHandeling = new AdministratieveHandelingModel(groep.getAdministratieveHandeling());
        }
        this.tijdstipLaatsteWijziging = groep.getTijdstipLaatsteWijziging();
        this.sorteervolgorde = groep.getSorteervolgorde();
        this.indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig = groep.getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig();
        this.tijdstipLaatsteWijzigingGBASystematiek = groep.getTijdstipLaatsteWijzigingGBASystematiek();
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Persoon Afgeleid administratief.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Afgeleid administratief.
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
    @AttribuutAccessor(dbObjectId = 10153)
    public AdministratieveHandelingModel getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10154)
    public DatumTijdAttribuut getTijdstipLaatsteWijziging() {
        return tijdstipLaatsteWijziging;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10406)
    public SorteervolgordeAttribuut getSorteervolgorde() {
        return sorteervolgorde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10931)
    public JaNeeAttribuut getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig() {
        return indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10932)
    public DatumTijdAttribuut getTijdstipLaatsteWijzigingGBASystematiek() {
        return tijdstipLaatsteWijzigingGBASystematiek;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (tijdstipLaatsteWijziging != null) {
            attributen.add(tijdstipLaatsteWijziging);
        }
        if (sorteervolgorde != null) {
            attributen.add(sorteervolgorde);
        }
        if (indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig != null) {
            attributen.add(indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig);
        }
        if (tijdstipLaatsteWijzigingGBASystematiek != null) {
            attributen.add(tijdstipLaatsteWijzigingGBASystematiek);
        }
        return attributen;
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_AFGELEIDADMINISTRATIEF;
    }

}
