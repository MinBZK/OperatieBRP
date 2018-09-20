/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
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
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingHisVolledigBasis;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingLeveringGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * HisVolledig klasse voor Administratieve handeling.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractAdministratieveHandelingHisVolledigImpl implements HisVolledigImpl, AdministratieveHandelingHisVolledigBasis,
        ElementIdentificeerbaar
{

    @Transient
    @JsonProperty
    private Long iD;

    @Embedded
    @AttributeOverride(name = SoortAdministratieveHandelingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Srt", updatable = false))
    @JsonProperty
    private SoortAdministratieveHandelingAttribuut soort;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Partij"))
    @JsonProperty
    private PartijAttribuut partij;

    @Embedded
    @AttributeOverride(name = OntleningstoelichtingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "ToelichtingOntlening"))
    @JsonProperty
    private OntleningstoelichtingAttribuut toelichtingOntlening;

    @Embedded
    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "TsReg"))
    @JsonProperty
    private DatumTijdAttribuut tijdstipRegistratie;

    @Embedded
    private AdministratieveHandelingLeveringGroepModel levering;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "administratieveHandeling", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImpl> gedeblokkeerdeMeldingen;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "administratieveHandeling", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<ActieHisVolledigImpl> acties;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractAdministratieveHandelingHisVolledigImpl() {
        gedeblokkeerdeMeldingen = new HashSet<AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImpl>();
        acties = new HashSet<ActieHisVolledigImpl>();

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Administratieve handeling.
     * @param partij partij van Administratieve handeling.
     * @param toelichtingOntlening toelichtingOntlening van Administratieve handeling.
     * @param tijdstipRegistratie tijdstipRegistratie van Administratieve handeling.
     */
    public AbstractAdministratieveHandelingHisVolledigImpl(
        final SoortAdministratieveHandelingAttribuut soort,
        final PartijAttribuut partij,
        final OntleningstoelichtingAttribuut toelichtingOntlening,
        final DatumTijdAttribuut tijdstipRegistratie)
    {
        this();
        this.soort = soort;
        this.partij = partij;
        this.toelichtingOntlening = toelichtingOntlening;
        this.tijdstipRegistratie = tijdstipRegistratie;

    }

    /**
     * Retourneert ID van Administratieve handeling.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "ADMINISTRATIEVEHANDELING", sequenceName = "Kern.seq_AdmHnd")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ADMINISTRATIEVEHANDELING")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Soort van Administratieve handeling.
     *
     * @return Soort.
     */
    public SoortAdministratieveHandelingAttribuut getSoort() {
        return soort;
    }

    /**
     * Retourneert Partij van Administratieve handeling.
     *
     * @return Partij.
     */
    public PartijAttribuut getPartij() {
        return partij;
    }

    /**
     * Retourneert Toelichting ontlening van Administratieve handeling.
     *
     * @return Toelichting ontlening.
     */
    public OntleningstoelichtingAttribuut getToelichtingOntlening() {
        return toelichtingOntlening;
    }

    /**
     * Retourneert Tijdstip registratie van Administratieve handeling.
     *
     * @return Tijdstip registratie.
     */
    public DatumTijdAttribuut getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImpl> getGedeblokkeerdeMeldingen() {
        return gedeblokkeerdeMeldingen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<ActieHisVolledigImpl> getActies() {
        return acties;
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
     * Zet Levering van Administratieve handeling.
     *
     * @param levering Levering.
     */
    public void setLevering(final AdministratieveHandelingLeveringGroepModel levering) {
        this.levering = levering;
    }

    /**
     * Zet lijst van Administratieve handeling \ Gedeblokkeerde melding.
     *
     * @param gedeblokkeerdeMeldingen lijst van Administratieve handeling \ Gedeblokkeerde melding
     */
    public void setGedeblokkeerdeMeldingen(final Set<AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImpl> gedeblokkeerdeMeldingen) {
        this.gedeblokkeerdeMeldingen = gedeblokkeerdeMeldingen;
    }

    /**
     * Zet lijst van Actie.
     *
     * @param acties lijst van Actie
     */
    public void setActies(final Set<ActieHisVolledigImpl> acties) {
        this.acties = acties;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.ADMINISTRATIEVEHANDELING;
    }

}
