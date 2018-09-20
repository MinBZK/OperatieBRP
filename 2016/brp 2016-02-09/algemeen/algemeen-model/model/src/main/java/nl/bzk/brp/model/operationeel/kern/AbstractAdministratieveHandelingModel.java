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
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandeling;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandelingBasis;

/**
 * Een door het bijhoudingsorgaan geïnitieerde activiteit in de BRP, waarmee persoonsgegevens worden bijgehouden.
 *
 * De binnen de BRP geadministreerde persoonsgegevens worden bijgehouden doordat wijzigingen worden doorgevoerd vanuit
 * de gemeentelijke of ministeriële verantwoordelijkheid. Het initiatief gegevens te wijzigen komt vanuit het
 * betreffende bijhoudingsorgaan; deze stuurt daartoe een bericht aan de BRP die de daadwerkelijke bijhouding doet
 * plaatsvinden. Voor de verwerking binnen de BRP wordt dit bericht uiteen gerafeld in één of meer Acties. Het geheel
 * aan acties wordt de administratieve handeling genoemd; dit is in de BRP de weerslag van wat in termen van de
 * burgerzakenmodule 'de zaak' zal zijn geweest. Qua niveau staat het op hetzelfde niveau als het bericht; het verschil
 * bestaat eruit dat het bericht het vehikel is waarmee de administratieve handeling wordt bewerkstelligd.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractAdministratieveHandelingModel extends AbstractDynamischObject implements AdministratieveHandelingBasis,
        ModelIdentificeerbaar<Long>
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
    @JsonProperty
    private AdministratieveHandelingLeveringGroepModel levering;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "AdmHnd")
    @JsonProperty
    private Set<AdministratieveHandelingGedeblokkeerdeMeldingModel> gedeblokkeerdeMeldingen;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "AdmHnd")
    @JsonProperty
    private Set<ActieModel> acties;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractAdministratieveHandelingModel() {
        gedeblokkeerdeMeldingen = new HashSet<AdministratieveHandelingGedeblokkeerdeMeldingModel>();
        acties = new HashSet<ActieModel>();

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Administratieve handeling.
     * @param partij partij van Administratieve handeling.
     * @param toelichtingOntlening toelichtingOntlening van Administratieve handeling.
     * @param tijdstipRegistratie tijdstipRegistratie van Administratieve handeling.
     */
    public AbstractAdministratieveHandelingModel(
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
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param administratieveHandeling Te kopieren object type.
     */
    public AbstractAdministratieveHandelingModel(final AdministratieveHandeling administratieveHandeling) {
        this();
        this.soort = administratieveHandeling.getSoort();
        this.partij = administratieveHandeling.getPartij();
        this.toelichtingOntlening = administratieveHandeling.getToelichtingOntlening();
        this.tijdstipRegistratie = administratieveHandeling.getTijdstipRegistratie();
        if (administratieveHandeling.getLevering() != null) {
            this.levering = new AdministratieveHandelingLeveringGroepModel(administratieveHandeling.getLevering());
        }

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
     * {@inheritDoc}
     */
    @Override
    public SoortAdministratieveHandelingAttribuut getSoort() {
        return soort;
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
    public OntleningstoelichtingAttribuut getToelichtingOntlening() {
        return toelichtingOntlening;
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
    public AdministratieveHandelingLeveringGroepModel getLevering() {
        return levering;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<AdministratieveHandelingGedeblokkeerdeMeldingModel> getGedeblokkeerdeMeldingen() {
        return gedeblokkeerdeMeldingen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<ActieModel> getActies() {
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
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        if (levering != null) {
            groepen.add(levering);
        }
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
        if (toelichtingOntlening != null) {
            attributen.add(toelichtingOntlening);
        }
        if (tijdstipRegistratie != null) {
            attributen.add(tijdstipRegistratie);
        }
        return attributen;
    }

}
