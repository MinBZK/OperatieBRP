/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

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

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.basis.ActieBasis;
import nl.bzk.brp.model.operationeel.kern.ActieBronModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * Eenheid van gegevensbewerking in de BRP.
 *
 * Het bijhouden van de BRP geschiedt door het uitwerken van gegevensbewerkingen op de inhoud van de BRP, c.q. het doen
 * van bijhoudingsacties. De kleinste eenheid van gegevensbewerking is de "BRP actie".
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractActieModel extends AbstractDynamischObjectType implements ActieBasis {

    @Id
    @SequenceGenerator(name = "ACTIE", sequenceName = "Kern.seq_Actie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ACTIE")
    @JsonProperty
    private Long                          iD;

    @Enumerated
    @Column(name = "Srt", updatable = false)
    @JsonProperty
    private SoortActie                    soort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AdmHnd")
    private AdministratieveHandelingModel administratieveHandeling;

    @ManyToOne
    @JoinColumn(name = "Partij")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Partij                        partij;

    @Transient
    @JsonProperty
    private Datum                         datumAanvangGeldigheid;

    @Transient
    @JsonProperty
    private Datum                         datumEindeGeldigheid;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "TsReg"))
    @JsonProperty
    private DatumTijd                     tijdstipRegistratie;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Actie")
    @JsonProperty
    private Set<ActieBronModel>           bronnen;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
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
     */
    public AbstractActieModel(final SoortActie soort, final AdministratieveHandelingModel administratieveHandeling,
            final Partij partij, final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid,
            final DatumTijd tijdstipRegistratie)
    {
        this();
        this.soort = soort;
        this.administratieveHandeling = administratieveHandeling;
        this.partij = partij;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;
        this.tijdstipRegistratie = tijdstipRegistratie;

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

    }

    /**
     * Retourneert ID van Actie.
     *
     * @return ID.
     */
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Soort van Actie.
     *
     * @return Soort.
     */
    public SoortActie getSoort() {
        return soort;
    }

    /**
     * Retourneert Administratieve handeling van Actie.
     *
     * @return Administratieve handeling.
     */
    public AdministratieveHandelingModel getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Retourneert Partij van Actie.
     *
     * @return Partij.
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Retourneert Datum aanvang geldigheid van Actie.
     *
     * @return Datum aanvang geldigheid.
     */
    public Datum getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert Datum einde geldigheid van Actie.
     *
     * @return Datum einde geldigheid.
     */
    public Datum getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Retourneert Tijdstip registratie van Actie.
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
    public Set<ActieBronModel> getBronnen() {
        return bronnen;
    }

}
