/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.attribuuttype.Aktenummer;
import nl.bzk.brp.model.attribuuttype.DocumentIdentificatie;
import nl.bzk.brp.model.attribuuttype.DocumentOmschrijving;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.DocumentStandaardGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * .
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractDocumentStandaardGroep extends AbstractGroep implements DocumentStandaardGroepBasis {

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Ident"))
    private DocumentIdentificatie identificatie;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Aktenr"))
    private Aktenummer            aktenummer;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
    private DocumentOmschrijving  omschrijving;

    @ManyToOne
    @JoinColumn(name = "Partij")
    @Fetch(FetchMode.JOIN)
    private Partij                partij;


    /**
     * .
     */
    protected AbstractDocumentStandaardGroep() {
        super();
    }

    /**
     * .
     * @param that .
     */
    protected AbstractDocumentStandaardGroep(final DocumentStandaardGroepBasis that) {
        super();
        if (null != that) {
            identificatie = that.getIdentificatie();
            aktenummer = that.getAktenummer();
            omschrijving  = that.getOmschrijving();
            partij = that.getPartij();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentIdentificatie getIdentificatie() {
        return identificatie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Aktenummer getAktenummer() {
        return aktenummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentOmschrijving getOmschrijving() {
        return omschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet Identificatie van Standaard.
     *
     * @param identificatie Identificatie.
     */
    public void setIdentificatie(final DocumentIdentificatie identificatie) {
        this.identificatie = identificatie;
    }

    /**
     * Zet Aktenummer van Standaard.
     *
     * @param aktenummer Aktenummer.
     */
    public void setAktenummer(final Aktenummer aktenummer) {
        this.aktenummer = aktenummer;
    }

    /**
     * Zet Omschrijving van Standaard.
     *
     * @param omschrijving Omschrijving.
     */
    public void setOmschrijving(final DocumentOmschrijving omschrijving) {
        this.omschrijving = omschrijving;
    }

    /**
     * Zet Partij van Standaard.
     *
     * @param partij Partij.
     */
    public void setPartij(final Partij partij) {
        this.partij = partij;
    }
}
