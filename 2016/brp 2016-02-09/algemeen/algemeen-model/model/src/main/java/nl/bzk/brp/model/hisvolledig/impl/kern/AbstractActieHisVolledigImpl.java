/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.ActieHisVolledigBasis;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * HisVolledig klasse voor Actie.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractActieHisVolledigImpl implements HisVolledigImpl, ActieHisVolledigBasis, ElementIdentificeerbaar {

    @Transient
    @JsonProperty
    private Long iD;

    @Embedded
    @AttributeOverride(name = SoortActieAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Srt", updatable = false))
    @JsonProperty
    private SoortActieAttribuut soort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AdmHnd")
    @JsonBackReference
    private AdministratieveHandelingHisVolledigImpl administratieveHandeling;

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "actie", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<ActieBronHisVolledigImpl> bronnen;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractActieHisVolledigImpl() {
        bronnen = new HashSet<ActieBronHisVolledigImpl>();

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
    public AbstractActieHisVolledigImpl(
        final SoortActieAttribuut soort,
        final AdministratieveHandelingHisVolledigImpl administratieveHandeling,
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
     * Retourneert Soort van Actie.
     *
     * @return Soort.
     */
    public SoortActieAttribuut getSoort() {
        return soort;
    }

    /**
     * Retourneert Administratieve handeling van Actie.
     *
     * @return Administratieve handeling.
     */
    public AdministratieveHandelingHisVolledigImpl getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Retourneert Partij van Actie.
     *
     * @return Partij.
     */
    public PartijAttribuut getPartij() {
        return partij;
    }

    /**
     * Retourneert Datum aanvang geldigheid van Actie.
     *
     * @return Datum aanvang geldigheid.
     */
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert Datum einde geldigheid van Actie.
     *
     * @return Datum einde geldigheid.
     */
    public DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Retourneert Tijdstip registratie van Actie.
     *
     * @return Tijdstip registratie.
     */
    public DatumTijdAttribuut getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * Retourneert Datum ontlening van Actie.
     *
     * @return Datum ontlening.
     */
    public DatumEvtDeelsOnbekendAttribuut getDatumOntlening() {
        return datumOntlening;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<ActieBronHisVolledigImpl> getBronnen() {
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
     * Zet lijst van Actie \ Bron.
     *
     * @param bronnen lijst van Actie \ Bron
     */
    public void setBronnen(final Set<ActieBronHisVolledigImpl> bronnen) {
        this.bronnen = bronnen;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.ACTIE;
    }

}
