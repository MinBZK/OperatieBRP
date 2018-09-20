/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
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

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ontleningstoelichting;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandeling;
import nl.bzk.brp.model.logisch.kern.basis.AdministratieveHandelingBasis;
import nl.bzk.brp.model.operationeel.ber.AdministratieveHandelingBijgehoudenPersoonModel;
import nl.bzk.brp.model.operationeel.ber.AdministratieveHandelingDocumentModel;
import nl.bzk.brp.model.operationeel.ber.AdministratieveHandelingGedeblokkeerdeMeldingModel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;


/**
 * Een door het bijhoudingsorgaan ge�nitieerde activiteit in de BRP, waarmee persoonsgegevens worden bijgehouden.
 *
 * De binnen de BRP geadministreerde persoonsgegevens worden bijgehouden doordat wijzigingen worden doorgevoerd vanuit
 * de gemeentelijke of ministeri�le verantwoordelijkheid. Het initiatief gegevens te wijzigen komt vanuit het
 * betreffende bijhoudingsorgaan; deze stuurt daartoe een bericht aan de BRP die de daadwerkelijke bijhouding doet
 * plaatsvinden. Voor de verwerking binnen de BRP wordt dit bericht uiteen gerafeld in ��n of meer Acties. Het geheel
 * aan acties wordt de administratieve handeling genoemd; dit is in de BRP de weerslag van wat in termen van de
 * burgerzakenmodule 'de zaak' zal zijn geweest. Qua niveau staat het op hetzelfde niveau als het bericht; het verschil
 * bestaat eruit dat het bericht het vehikel is waarmee de administratieve handeling wordt bewerkstelligt.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractAdministratieveHandelingModel extends AbstractDynamischObjectType implements
        AdministratieveHandelingBasis
{

    @Id
    @SequenceGenerator(name = "ADMINISTRATIEVEHANDELING", sequenceName = "Kern.seq_AdmHnd")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ADMINISTRATIEVEHANDELING")
    @JsonProperty
    private Long                                                    iD;

    @Enumerated
    @Column(name = "Srt", updatable = false)
    @JsonProperty
    private SoortAdministratieveHandeling                           soort;

    @ManyToOne
    @JoinColumn(name = "Partij")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Partij                                                  partij;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "TsOntlening"))
    @JsonProperty
    private DatumTijd                                               tijdstipOntlening;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "ToelichtingOntlening"))
    @JsonProperty
    private Ontleningstoelichting                                   toelichtingOntlening;

    @Transient
    @JsonProperty
    private DatumTijd                                               tijdstipRegistratie;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "AdmHnd")
    @JsonProperty
    private Set<AdministratieveHandelingGedeblokkeerdeMeldingModel> gedeblokkeerdeMeldingen;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "AdmHnd")
    @JsonProperty
    private Set<AdministratieveHandelingBijgehoudenPersoonModel>    bijgehoudenPersonen;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "AdmHnd")
    @JsonProperty
    @Sort(type = SortType.NATURAL)
    private Set<ActieModel>                                         acties;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "AdmHnd")
    @JsonProperty
    private Set<AdministratieveHandelingDocumentModel>              bijgehoudenDocumenten;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractAdministratieveHandelingModel() {
        gedeblokkeerdeMeldingen = new HashSet<AdministratieveHandelingGedeblokkeerdeMeldingModel>();
        bijgehoudenPersonen = new HashSet<AdministratieveHandelingBijgehoudenPersoonModel>();
        acties = new TreeSet<ActieModel>();
        bijgehoudenDocumenten = new HashSet<AdministratieveHandelingDocumentModel>();

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Administratieve handeling.
     * @param partij partij van Administratieve handeling.
     * @param tijdstipOntlening tijdstipOntlening van Administratieve handeling.
     * @param toelichtingOntlening toelichtingOntlening van Administratieve handeling.
     * @param tijdstipRegistratie tijdstipRegistratie van Administratieve handeling.
     */
    public AbstractAdministratieveHandelingModel(final SoortAdministratieveHandeling soort, final Partij partij,
            final DatumTijd tijdstipOntlening, final Ontleningstoelichting toelichtingOntlening,
            final DatumTijd tijdstipRegistratie)
    {
        this();
        this.soort = soort;
        this.partij = partij;
        this.tijdstipOntlening = tijdstipOntlening;
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
        this.tijdstipOntlening = administratieveHandeling.getTijdstipOntlening();
        this.toelichtingOntlening = administratieveHandeling.getToelichtingOntlening();
        this.tijdstipRegistratie = administratieveHandeling.getTijdstipRegistratie();

    }

    /**
     * Retourneert ID van Administratieve handeling.
     *
     * @return ID.
     */
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Soort van Administratieve handeling.
     *
     * @return Soort.
     */
    public SoortAdministratieveHandeling getSoort() {
        return soort;
    }

    /**
     * Retourneert Partij van Administratieve handeling.
     *
     * @return Partij.
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Retourneert Tijdstip ontlening van Administratieve handeling.
     *
     * @return Tijdstip ontlening.
     */
    public DatumTijd getTijdstipOntlening() {
        return tijdstipOntlening;
    }

    /**
     * Retourneert Toelichting ontlening van Administratieve handeling.
     *
     * @return Toelichting ontlening.
     */
    public Ontleningstoelichting getToelichtingOntlening() {
        return toelichtingOntlening;
    }

    /**
     * Retourneert Tijdstip registratie van Administratieve handeling.
     *
     * @return Tijdstip registratie.
     */
    public DatumTijd getTijdstipRegistratie() {
        return tijdstipRegistratie;
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
    public Set<AdministratieveHandelingBijgehoudenPersoonModel> getBijgehoudenPersonen() {
        return bijgehoudenPersonen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<ActieModel> getActies() {
        return acties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<AdministratieveHandelingDocumentModel> getBijgehoudenDocumenten() {
        return bijgehoudenDocumenten;
    }

}
