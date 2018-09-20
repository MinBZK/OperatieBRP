/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.basis;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.attribuuttype.DatumTijd;
import nl.bzk.copy.model.attribuuttype.Ontleningstoelichting;
import nl.bzk.copy.model.basis.AbstractDynamischObjectType;
import nl.bzk.copy.model.objecttype.logisch.Verdrag;
import nl.bzk.copy.model.objecttype.logisch.basis.ActieBasis;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.copy.model.objecttype.operationeel.statisch.SoortActie;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * Implementatie voor objecttype actie.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractActieModel extends AbstractDynamischObjectType implements ActieBasis {

    @Id
    @SequenceGenerator(name = "ACTIE", sequenceName = "Kern.seq_Actie")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACTIE")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "partij")
    @NotNull
    @Fetch(FetchMode.JOIN)
    private Partij partij;

    @Transient
    // @ManyToOne
    // @JoinColumn(name = "verdrag")
    private Verdrag verdrag;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "tijdstipontlening"))
    private DatumTijd tijdstipOntlening;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "tijdstipreg"))
    @NotNull
    private DatumTijd tijdstipRegistratie;

    @Column(name = "srt")
    @Enumerated
    @NotNull
    private SoortActie soort;

    @Transient
    // TODO: datumAanvangGeldigheid of de db is verouderd of deze attribuut kan weg.
    private Datum datumAanvangGeldigheid;
    @Transient
    // TODO: datumEindeGeldigheid of de db is verouderd of deze attribuut kan weg.
    private Datum datumEindeGeldigheid;
    @Transient
    // TODO: ontleningstoelichting of de db is verouderd of deze attribuut kan weg.
    private Ontleningstoelichting ontleningstoelichting;

    /**
     * Copy constructor. Om een model object te construeren uit een web object.
     *
     * @param actie Object type dat gekopieerd dient te worden.
     */
    protected AbstractActieModel(final ActieBasis actie) {
        super(actie);
        partij = actie.getPartij();
        // TODO verdrag is nog niet gebouwd.
        // verdrag = actie.getVerdrag();
        tijdstipOntlening = actie.getTijdstipRegistratie();
        tijdstipRegistratie = actie.getTijdstipRegistratie();
        soort = actie.getSoort();
    }

    /**
     * Standaard (lege) constructor.
     */
    protected AbstractActieModel() {
    }

    public Long getId() {
        return id;
    }

    @Override
    public Partij getPartij() {
        return partij;
    }

    @Override
    public Verdrag getVerdrag() {
        return verdrag;
    }

    @Override
    public DatumTijd getTijdstipOntlening() {
        return tijdstipOntlening;
    }

    @Override
    public DatumTijd getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    @Override
    public SoortActie getSoort() {
        return soort;
    }

    @Override
    public Datum getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    @Override
    public Datum getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    @Override
    public Ontleningstoelichting getOntleningstoelichting() {
        return ontleningstoelichting;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setPartij(final Partij partij) {
        this.partij = partij;
    }

    public void setVerdrag(final Verdrag verdrag) {
        this.verdrag = verdrag;
    }

    public void setTijdstipOntlening(final DatumTijd tijdstipOntlening) {
        this.tijdstipOntlening = tijdstipOntlening;
    }

    public void setTijdstipRegistratie(final DatumTijd tijdstipRegistratie) {
        this.tijdstipRegistratie = tijdstipRegistratie;
    }

    public void setSoort(final SoortActie soort) {
        this.soort = soort;
    }

    public void setDatumAanvangGeldigheid(final Datum datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    public void setDatumEindeGeldigheid(final Datum datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    public void setOntleningstoelichting(final Ontleningstoelichting ontleningstoelichting) {
        this.ontleningstoelichting = ontleningstoelichting;
    }
}
