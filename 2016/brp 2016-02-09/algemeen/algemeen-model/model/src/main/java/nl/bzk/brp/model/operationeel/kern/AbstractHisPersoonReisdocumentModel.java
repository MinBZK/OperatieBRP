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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AutoriteitVanAfgifteReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReisdocumentNummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonReisdocumentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonReisdocumentHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonReisdocumentStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonReisdocumentStandaardGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 3577)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPersoonReisdocumentModel extends AbstractFormeelHistorischMetActieVerantwoording implements
        PersoonReisdocumentStandaardGroepBasis, ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONREISDOCUMENT", sequenceName = "Kern.seq_His_PersReisdoc")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONREISDOCUMENT")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PersoonReisdocumentHisVolledigImpl.class)
    @JoinColumn(name = "PersReisdoc")
    @JsonBackReference
    private PersoonReisdocumentHisVolledig persoonReisdocument;

    @Embedded
    @AttributeOverride(name = ReisdocumentNummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Nr"))
    @JsonProperty
    private ReisdocumentNummerAttribuut nummer;

    @Embedded
    @AttributeOverride(name = AutoriteitVanAfgifteReisdocumentCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "AutVanAfgifte"))
    @JsonProperty
    private AutoriteitVanAfgifteReisdocumentCodeAttribuut autoriteitVanAfgifte;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngangDoc"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumIngangDocument;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEindeDoc"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumEindeDocument;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatUitgifte"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumUitgifte;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatInhingVermissing"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumInhoudingVermissing;

    @Embedded
    @AssociationOverride(name = AanduidingInhoudingVermissingReisdocumentAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(
            name = "AandInhingVermissing"))
    @JsonProperty
    private AanduidingInhoudingVermissingReisdocumentAttribuut aanduidingInhoudingVermissing;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPersoonReisdocumentModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param persoonReisdocument persoonReisdocument van HisPersoonReisdocumentModel
     * @param nummer nummer van HisPersoonReisdocumentModel
     * @param autoriteitVanAfgifte autoriteitVanAfgifte van HisPersoonReisdocumentModel
     * @param datumIngangDocument datumIngangDocument van HisPersoonReisdocumentModel
     * @param datumEindeDocument datumEindeDocument van HisPersoonReisdocumentModel
     * @param datumUitgifte datumUitgifte van HisPersoonReisdocumentModel
     * @param datumInhoudingVermissing datumInhoudingVermissing van HisPersoonReisdocumentModel
     * @param aanduidingInhoudingVermissing aanduidingInhoudingVermissing van HisPersoonReisdocumentModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonReisdocumentModel(
        final PersoonReisdocumentHisVolledig persoonReisdocument,
        final ReisdocumentNummerAttribuut nummer,
        final AutoriteitVanAfgifteReisdocumentCodeAttribuut autoriteitVanAfgifte,
        final DatumEvtDeelsOnbekendAttribuut datumIngangDocument,
        final DatumEvtDeelsOnbekendAttribuut datumEindeDocument,
        final DatumEvtDeelsOnbekendAttribuut datumUitgifte,
        final DatumEvtDeelsOnbekendAttribuut datumInhoudingVermissing,
        final AanduidingInhoudingVermissingReisdocumentAttribuut aanduidingInhoudingVermissing,
        final ActieModel actieInhoud)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.persoonReisdocument = persoonReisdocument;
        this.nummer = nummer;
        this.autoriteitVanAfgifte = autoriteitVanAfgifte;
        this.datumIngangDocument = datumIngangDocument;
        this.datumEindeDocument = datumEindeDocument;
        this.datumUitgifte = datumUitgifte;
        this.datumInhoudingVermissing = datumInhoudingVermissing;
        this.aanduidingInhoudingVermissing = aanduidingInhoudingVermissing;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisPersoonReisdocumentModel(final AbstractHisPersoonReisdocumentModel kopie) {
        super(kopie);
        persoonReisdocument = kopie.getPersoonReisdocument();
        nummer = kopie.getNummer();
        autoriteitVanAfgifte = kopie.getAutoriteitVanAfgifte();
        datumIngangDocument = kopie.getDatumIngangDocument();
        datumEindeDocument = kopie.getDatumEindeDocument();
        datumUitgifte = kopie.getDatumUitgifte();
        datumInhoudingVermissing = kopie.getDatumInhoudingVermissing();
        aanduidingInhoudingVermissing = kopie.getAanduidingInhoudingVermissing();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonReisdocumentHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonReisdocumentModel(
        final PersoonReisdocumentHisVolledig persoonReisdocumentHisVolledig,
        final PersoonReisdocumentStandaardGroep groep,
        final ActieModel actieInhoud)
    {
        this.persoonReisdocument = persoonReisdocumentHisVolledig;
        this.nummer = groep.getNummer();
        this.autoriteitVanAfgifte = groep.getAutoriteitVanAfgifte();
        this.datumIngangDocument = groep.getDatumIngangDocument();
        this.datumEindeDocument = groep.getDatumEindeDocument();
        this.datumUitgifte = groep.getDatumUitgifte();
        this.datumInhoudingVermissing = groep.getDatumInhoudingVermissing();
        this.aanduidingInhoudingVermissing = groep.getAanduidingInhoudingVermissing();
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Persoon \ Reisdocument.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon \ Reisdocument van His Persoon \ Reisdocument.
     *
     * @return Persoon \ Reisdocument.
     */
    public PersoonReisdocumentHisVolledig getPersoonReisdocument() {
        return persoonReisdocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9805)
    public ReisdocumentNummerAttribuut getNummer() {
        return nummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9807)
    public AutoriteitVanAfgifteReisdocumentCodeAttribuut getAutoriteitVanAfgifte() {
        return autoriteitVanAfgifte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9808)
    public DatumEvtDeelsOnbekendAttribuut getDatumIngangDocument() {
        return datumIngangDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9810)
    public DatumEvtDeelsOnbekendAttribuut getDatumEindeDocument() {
        return datumEindeDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9809)
    public DatumEvtDeelsOnbekendAttribuut getDatumUitgifte() {
        return datumUitgifte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9811)
    public DatumEvtDeelsOnbekendAttribuut getDatumInhoudingVermissing() {
        return datumInhoudingVermissing;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9812)
    public AanduidingInhoudingVermissingReisdocumentAttribuut getAanduidingInhoudingVermissing() {
        return aanduidingInhoudingVermissing;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (nummer != null) {
            attributen.add(nummer);
        }
        if (autoriteitVanAfgifte != null) {
            attributen.add(autoriteitVanAfgifte);
        }
        if (datumIngangDocument != null) {
            attributen.add(datumIngangDocument);
        }
        if (datumEindeDocument != null) {
            attributen.add(datumEindeDocument);
        }
        if (datumUitgifte != null) {
            attributen.add(datumUitgifte);
        }
        if (datumInhoudingVermissing != null) {
            attributen.add(datumInhoudingVermissing);
        }
        if (aanduidingInhoudingVermissing != null) {
            attributen.add(aanduidingInhoudingVermissing);
        }
        return attributen;
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_REISDOCUMENT_STANDAARD;
    }

}
