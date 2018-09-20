/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPartijFiatteringsuitzondering extends AbstractFormeelHistorischMetActieVerantwoording implements
        ModelIdentificeerbaar<Integer>
{

    @Id
    @SequenceGenerator(name = "HIS_PARTIJFIATTERINGSUITZONDERING", sequenceName = "AutAut.seq_His_PartijFiatuitz")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PARTIJFIATTERINGSUITZONDERING")
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "PartijFiatuitz")
    private PartijFiatteringsuitzondering partijFiatteringsuitzondering;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngang"))
    @JsonProperty
    private DatumAttribuut datumIngang;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    @JsonProperty
    private DatumAttribuut datumEinde;

    @ManyToOne
    @JoinColumn(name = "PartijBijhvoorstel")
    @JsonProperty
    private Partij partijBijhoudingsvoorstel;

    @ManyToOne
    @JoinColumn(name = "SrtDoc")
    @JsonProperty
    private SoortDocument soortDocument;

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
    protected AbstractHisPartijFiatteringsuitzondering() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param partijFiatteringsuitzondering partijFiatteringsuitzondering van HisPartijFiatteringsuitzondering
     * @param datumIngang datumIngang van HisPartijFiatteringsuitzondering
     * @param datumEinde datumEinde van HisPartijFiatteringsuitzondering
     * @param partijBijhoudingsvoorstel partijBijhoudingsvoorstel van HisPartijFiatteringsuitzondering
     * @param soortDocument soortDocument van HisPartijFiatteringsuitzondering
     * @param soortAdministratieveHandeling soortAdministratieveHandeling van HisPartijFiatteringsuitzondering
     * @param indicatieGeblokkeerd indicatieGeblokkeerd van HisPartijFiatteringsuitzondering
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisPartijFiatteringsuitzondering(
        final PartijFiatteringsuitzondering partijFiatteringsuitzondering,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final Partij partijBijhoudingsvoorstel,
        final SoortDocument soortDocument,
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
    public AbstractHisPartijFiatteringsuitzondering(final AbstractHisPartijFiatteringsuitzondering kopie) {
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
    public PartijFiatteringsuitzondering getPartijFiatteringsuitzondering() {
        return partijFiatteringsuitzondering;
    }

    /**
     * Retourneert Datum ingang van His Partij \ Fiatteringsuitzondering.
     *
     * @return Datum ingang.
     */
    public DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van His Partij \ Fiatteringsuitzondering.
     *
     * @return Datum einde.
     */
    public DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Partij bijhoudingsvoorstel van His Partij \ Fiatteringsuitzondering.
     *
     * @return Partij bijhoudingsvoorstel.
     */
    public Partij getPartijBijhoudingsvoorstel() {
        return partijBijhoudingsvoorstel;
    }

    /**
     * Retourneert Soort document van His Partij \ Fiatteringsuitzondering.
     *
     * @return Soort document.
     */
    public SoortDocument getSoortDocument() {
        return soortDocument;
    }

    /**
     * Retourneert Soort administratieve handeling van His Partij \ Fiatteringsuitzondering.
     *
     * @return Soort administratieve handeling.
     */
    public SoortAdministratieveHandelingAttribuut getSoortAdministratieveHandeling() {
        return soortAdministratieveHandeling;
    }

    /**
     * Retourneert Geblokkeerd? van His Partij \ Fiatteringsuitzondering.
     *
     * @return Geblokkeerd?.
     */
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
// handmatige wijziging: dit moet gefixed worden in de generator...
//        if (partijBijhoudingsvoorstel != null) {
//            attributen.add(partijBijhoudingsvoorstel);
//        }
//        if (soortDocument != null) {
//            attributen.add(soortDocument);
//        }
        if (soortAdministratieveHandeling != null) {
            attributen.add(soortAdministratieveHandeling);
        }
        if (indicatieGeblokkeerd != null) {
            attributen.add(indicatieGeblokkeerd);
        }
        return attributen;
    }

}
