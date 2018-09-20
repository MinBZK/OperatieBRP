/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.impl.gen;

import java.math.BigInteger;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.OntleningsToelichting;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.objecttype.interfaces.gen.ActieBasis;
import nl.bzk.brp.model.objecttype.interfaces.usr.Verdrag;
import nl.bzk.brp.model.objecttype.statisch.Partij;
import nl.bzk.brp.model.objecttype.statisch.SoortActie;

/**
 * Implementatie voor objecttype actie.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractActieMdl extends AbstractDynamischObjectType implements ActieBasis {

    @Id
    @SequenceGenerator(name = "ACTIE", sequenceName = "Kern.seq_Actie")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACTIE")
    private BigInteger id;

    @ManyToOne
    @JoinColumn(name = "partij")
    @NotNull
    private Partij partij;

    @Transient
//    @ManyToOne
//    @JoinColumn(name = "verdrag")
    private Verdrag verdrag;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "tsontlening"))
    private DatumTijd tijdstipOntlening;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "tsreg"))
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
    // TODO: ontleningsToelichting of de db is verouderd of deze attribuut kan weg.
    private OntleningsToelichting ontleningsToelichting;

    public BigInteger getId() {
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
    public OntleningsToelichting getOntleningsToelichting() {
        return ontleningsToelichting;
    }
}
