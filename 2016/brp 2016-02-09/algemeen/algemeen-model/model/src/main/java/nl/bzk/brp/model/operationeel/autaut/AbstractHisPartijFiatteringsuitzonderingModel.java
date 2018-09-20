/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.autaut;

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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.autaut.PartijFiatteringsuitzonderingHisVolledig;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PartijFiatteringsuitzonderingHisVolledigImpl;
import nl.bzk.brp.model.logisch.autaut.PartijFiatteringsuitzonderingStandaardGroep;
import nl.bzk.brp.model.logisch.autaut.PartijFiatteringsuitzonderingStandaardGroepBasis;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPartijFiatteringsuitzonderingModel extends AbstractFormeelHistorischMetActieVerantwoording implements
        PartijFiatteringsuitzonderingStandaardGroepBasis, ModelIdentificeerbaar<Integer>
{

    @Id
    @SequenceGenerator(name = "HIS_PARTIJFIATTERINGSUITZONDERING", sequenceName = "AutAut.seq_His_PartijFiatuitz")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PARTIJFIATTERINGSUITZONDERING")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PartijFiatteringsuitzonderingHisVolledigImpl.class)
    @JoinColumn(name = "PartijFiatuitz")
    @JsonBackReference
    private PartijFiatteringsuitzonderingHisVolledig partijFiatteringsuitzondering;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngang"))
    @JsonProperty
    private DatumAttribuut datumIngang;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    @JsonProperty
    private DatumAttribuut datumEinde;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "PartijBijhvoorstel"))
    @JsonProperty
    private PartijAttribuut partijBijhoudingsvoorstel;

    @Embedded
    @AssociationOverride(name = SoortDocumentAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "SrtDoc"))
    @JsonProperty
    private SoortDocumentAttribuut soortDocument;

    @Embedded
    @AttributeOverride(name = SoortAdministratieveHandelingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "SrtAdmHnd"))
    @JsonProperty
    private SoortAdministratieveHandelingAttribuut soortAdministratieveHandeling;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndBlok"))
    @JsonProperty
    private JaAttribuut indicatieGeblokkeerd;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPartijFiatteringsuitzonderingModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param partijFiatteringsuitzondering partijFiatteringsuitzondering van HisPartijFiatteringsuitzonderingModel
     * @param datumIngang datumIngang van HisPartijFiatteringsuitzonderingModel
     * @param datumEinde datumEinde van HisPartijFiatteringsuitzonderingModel
     * @param partijBijhoudingsvoorstel partijBijhoudingsvoorstel van HisPartijFiatteringsuitzonderingModel
     * @param soortDocument soortDocument van HisPartijFiatteringsuitzonderingModel
     * @param soortAdministratieveHandeling soortAdministratieveHandeling van HisPartijFiatteringsuitzonderingModel
     * @param indicatieGeblokkeerd indicatieGeblokkeerd van HisPartijFiatteringsuitzonderingModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisPartijFiatteringsuitzonderingModel(
        final PartijFiatteringsuitzonderingHisVolledig partijFiatteringsuitzondering,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final PartijAttribuut partijBijhoudingsvoorstel,
        final SoortDocumentAttribuut soortDocument,
        final SoortAdministratieveHandelingAttribuut soortAdministratieveHandeling,
        final JaAttribuut indicatieGeblokkeerd,
        final ActieModel actieInhoud)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.partijFiatteringsuitzondering = partijFiatteringsuitzondering;
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.partijBijhoudingsvoorstel = partijBijhoudingsvoorstel;
        this.soortDocument = soortDocument;
        this.soortAdministratieveHandeling = soortAdministratieveHandeling;
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisPartijFiatteringsuitzonderingModel(final AbstractHisPartijFiatteringsuitzonderingModel kopie) {
        super(kopie);
        partijFiatteringsuitzondering = kopie.getPartijFiatteringsuitzondering();
        datumIngang = kopie.getDatumIngang();
        datumEinde = kopie.getDatumEinde();
        partijBijhoudingsvoorstel = kopie.getPartijBijhoudingsvoorstel();
        soortDocument = kopie.getSoortDocument();
        soortAdministratieveHandeling = kopie.getSoortAdministratieveHandeling();
        indicatieGeblokkeerd = kopie.getIndicatieGeblokkeerd();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param partijFiatteringsuitzonderingHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisPartijFiatteringsuitzonderingModel(
        final PartijFiatteringsuitzonderingHisVolledig partijFiatteringsuitzonderingHisVolledig,
        final PartijFiatteringsuitzonderingStandaardGroep groep,
        final ActieModel actieInhoud)
    {
        this.partijFiatteringsuitzondering = partijFiatteringsuitzonderingHisVolledig;
        this.datumIngang = groep.getDatumIngang();
        this.datumEinde = groep.getDatumEinde();
        this.partijBijhoudingsvoorstel = groep.getPartijBijhoudingsvoorstel();
        this.soortDocument = groep.getSoortDocument();
        this.soortAdministratieveHandeling = groep.getSoortAdministratieveHandeling();
        this.indicatieGeblokkeerd = groep.getIndicatieGeblokkeerd();
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Partij \ Fiatteringsuitzondering.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Partij \ Fiatteringsuitzondering van His Partij \ Fiatteringsuitzondering.
     *
     * @return Partij \ Fiatteringsuitzondering.
     */
    public PartijFiatteringsuitzonderingHisVolledig getPartijFiatteringsuitzondering() {
        return partijFiatteringsuitzondering;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 21512)
    public DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 21513)
    public DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 21514)
    public PartijAttribuut getPartijBijhoudingsvoorstel() {
        return partijBijhoudingsvoorstel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 21515)
    public SoortDocumentAttribuut getSoortDocument() {
        return soortDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 21516)
    public SoortAdministratieveHandelingAttribuut getSoortAdministratieveHandeling() {
        return soortAdministratieveHandeling;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 21517)
    public JaAttribuut getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (datumIngang != null) {
            attributen.add(datumIngang);
        }
        if (datumEinde != null) {
            attributen.add(datumEinde);
        }
        if (partijBijhoudingsvoorstel != null) {
            attributen.add(partijBijhoudingsvoorstel);
        }
        if (soortDocument != null) {
            attributen.add(soortDocument);
        }
        if (soortAdministratieveHandeling != null) {
            attributen.add(soortAdministratieveHandeling);
        }
        if (indicatieGeblokkeerd != null) {
            attributen.add(indicatieGeblokkeerd);
        }
        return attributen;
    }

}
