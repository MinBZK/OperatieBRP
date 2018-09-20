/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.VerantwoordingsEntiteit;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.ActieBasis;

/**
 * Kleinste eenheid van gegevensbewerking in de BRP.
 *
 * Het bijhouden van de BRP geschiedt door het verwerken van administratieve handelingen. Deze administratieve
 * handelingen vallen uiteen in één of meer 'eenheden' van gegevensbewerkingen.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractActieModel extends AbstractDynamischObject implements VerantwoordingsEntiteit, ActieBasis, ModelIdentificeerbaar<Long> {

    @Transient
    @JsonProperty
    private Long iD;

    @Embedded
    @AttributeOverride(name = SoortActieAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Srt", updatable = false))
    @JsonProperty
    private SoortActieAttribuut soort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AdmHnd")
    // PdJ: Handmatige wijziging: TEAMBRP-1621
    @JsonProperty
    private AdministratieveHandelingModel administratieveHandeling;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Partij"))
    @JsonProperty
    private PartijAttribuut partij;

    @Transient
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;

    @Transient
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

    @Embedded
    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "TsReg"))
    @JsonProperty
    private DatumTijdAttribuut tijdstipRegistratie;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatOntlening"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumOntlening;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Actie")
    @JsonProperty
    private Set<ActieBronModel> bronnen;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractActieModel() {
        bronnen = new HashSet<ActieBronModel>();

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Actie.
     * @param administratieveHandeling administratieveHandeling van Actie.
     * @param partij partij van Actie.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Actie.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Actie.
     * @param tijdstipRegistratie tijdstipRegistratie van Actie.
     * @param datumOntlening datumOntlening van Actie.
     */
    public AbstractActieModel(
        final SoortActieAttribuut soort,
        final AdministratieveHandelingModel administratieveHandeling,
        final PartijAttribuut partij,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid,
        final DatumTijdAttribuut tijdstipRegistratie,
        final DatumEvtDeelsOnbekendAttribuut datumOntlening)
    {
        this();
        this.soort = soort;
        this.administratieveHandeling = administratieveHandeling;
        this.partij = partij;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;
        this.tijdstipRegistratie = tijdstipRegistratie;
        this.datumOntlening = datumOntlening;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param actie Te kopieren object type.
     * @param administratieveHandeling Bijbehorende Administratieve handeling.
     */
    public AbstractActieModel(final Actie actie, final AdministratieveHandelingModel administratieveHandeling) {
        this();
        this.soort = actie.getSoort();
        this.administratieveHandeling = administratieveHandeling;
        this.partij = actie.getPartij();
        this.datumAanvangGeldigheid = actie.getDatumAanvangGeldigheid();
        this.datumEindeGeldigheid = actie.getDatumEindeGeldigheid();
        this.tijdstipRegistratie = actie.getTijdstipRegistratie();
        this.datumOntlening = actie.getDatumOntlening();

    }

    /**
     * Retourneert ID van Actie.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "ACTIE", sequenceName = "Kern.seq_Actie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ACTIE")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortActieAttribuut getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdministratieveHandelingModel getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijAttribuut getPartij() {
        return partij;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumTijdAttribuut getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumOntlening() {
        return datumOntlening;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<ActieBronModel> getBronnen() {
        return bronnen;
    }

    /**
     * Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. We maken de functie private voor de
     * zekerheid.
     *
     * @param id Id
     */
    private void setID(final Long id) {
        this.iD = id;
    }

    /**
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        return groepen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van het object.
     */
    public List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (soort != null) {
            attributen.add(soort);
        }
        if (partij != null) {
            attributen.add(partij);
        }
        if (datumAanvangGeldigheid != null) {
            attributen.add(datumAanvangGeldigheid);
        }
        if (datumEindeGeldigheid != null) {
            attributen.add(datumEindeGeldigheid);
        }
        if (tijdstipRegistratie != null) {
            attributen.add(tijdstipRegistratie);
        }
        if (datumOntlening != null) {
            attributen.add(datumOntlening);
        }
        return attributen;
    }

}
