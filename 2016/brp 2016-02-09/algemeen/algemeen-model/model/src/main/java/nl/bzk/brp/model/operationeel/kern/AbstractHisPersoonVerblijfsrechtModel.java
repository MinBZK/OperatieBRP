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
import javax.persistence.AssociationOverride;
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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingVerblijfsrechtAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonVerblijfsrechtGroep;
import nl.bzk.brp.model.logisch.kern.PersoonVerblijfsrechtGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 3517)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPersoonVerblijfsrechtModel extends AbstractFormeelHistorischMetActieVerantwoording implements
        PersoonVerblijfsrechtGroepBasis, ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONVERBLIJFSRECHT", sequenceName = "Kern.seq_His_PersVerblijfsr")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONVERBLIJFSRECHT")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PersoonHisVolledigImpl.class)
    @JoinColumn(name = "Pers")
    @JsonBackReference
    private PersoonHisVolledig persoon;

    @Embedded
    @AssociationOverride(name = AanduidingVerblijfsrechtAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "AandVerblijfsr"))
    @JsonProperty
    private AanduidingVerblijfsrechtAttribuut aanduidingVerblijfsrecht;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatAanvVerblijfsr"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumAanvangVerblijfsrecht;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatMededelingVerblijfsr"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumMededelingVerblijfsrecht;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatVoorzEindeVerblijfsr"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeVerblijfsrecht;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPersoonVerblijfsrechtModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param persoon persoon van HisPersoonVerblijfsrechtModel
     * @param aanduidingVerblijfsrecht aanduidingVerblijfsrecht van HisPersoonVerblijfsrechtModel
     * @param datumAanvangVerblijfsrecht datumAanvangVerblijfsrecht van HisPersoonVerblijfsrechtModel
     * @param datumMededelingVerblijfsrecht datumMededelingVerblijfsrecht van HisPersoonVerblijfsrechtModel
     * @param datumVoorzienEindeVerblijfsrecht datumVoorzienEindeVerblijfsrecht van HisPersoonVerblijfsrechtModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonVerblijfsrechtModel(
        final PersoonHisVolledig persoon,
        final AanduidingVerblijfsrechtAttribuut aanduidingVerblijfsrecht,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangVerblijfsrecht,
        final DatumEvtDeelsOnbekendAttribuut datumMededelingVerblijfsrecht,
        final DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeVerblijfsrecht,
        final ActieModel actieInhoud)
    {
        this.persoon = persoon;
        this.aanduidingVerblijfsrecht = aanduidingVerblijfsrecht;
        this.datumAanvangVerblijfsrecht = datumAanvangVerblijfsrecht;
        this.datumMededelingVerblijfsrecht = datumMededelingVerblijfsrecht;
        this.datumVoorzienEindeVerblijfsrecht = datumVoorzienEindeVerblijfsrecht;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisPersoonVerblijfsrechtModel(final AbstractHisPersoonVerblijfsrechtModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        aanduidingVerblijfsrecht = kopie.getAanduidingVerblijfsrecht();
        datumAanvangVerblijfsrecht = kopie.getDatumAanvangVerblijfsrecht();
        datumMededelingVerblijfsrecht = kopie.getDatumMededelingVerblijfsrecht();
        datumVoorzienEindeVerblijfsrecht = kopie.getDatumVoorzienEindeVerblijfsrecht();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonVerblijfsrechtModel(
        final PersoonHisVolledig persoonHisVolledig,
        final PersoonVerblijfsrechtGroep groep,
        final ActieModel actieInhoud)
    {
        this.persoon = persoonHisVolledig;
        this.aanduidingVerblijfsrecht = groep.getAanduidingVerblijfsrecht();
        this.datumAanvangVerblijfsrecht = groep.getDatumAanvangVerblijfsrecht();
        this.datumMededelingVerblijfsrecht = groep.getDatumMededelingVerblijfsrecht();
        this.datumVoorzienEindeVerblijfsrecht = groep.getDatumVoorzienEindeVerblijfsrecht();
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Persoon Verblijfsrecht.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Verblijfsrecht.
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
    @AttribuutAccessor(dbObjectId = 9760)
    public AanduidingVerblijfsrechtAttribuut getAanduidingVerblijfsrecht() {
        return aanduidingVerblijfsrecht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 21316)
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangVerblijfsrecht() {
        return datumAanvangVerblijfsrecht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9761)
    public DatumEvtDeelsOnbekendAttribuut getDatumMededelingVerblijfsrecht() {
        return datumMededelingVerblijfsrecht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9762)
    public DatumEvtDeelsOnbekendAttribuut getDatumVoorzienEindeVerblijfsrecht() {
        return datumVoorzienEindeVerblijfsrecht;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (aanduidingVerblijfsrecht != null) {
            attributen.add(aanduidingVerblijfsrecht);
        }
        if (datumAanvangVerblijfsrecht != null) {
            attributen.add(datumAanvangVerblijfsrecht);
        }
        if (datumMededelingVerblijfsrecht != null) {
            attributen.add(datumMededelingVerblijfsrecht);
        }
        if (datumVoorzienEindeVerblijfsrecht != null) {
            attributen.add(datumVoorzienEindeVerblijfsrecht);
        }
        return attributen;
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_VERBLIJFSRECHT;
    }

}
