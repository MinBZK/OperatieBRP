/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
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

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Aktenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentIdentificatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentOmschrijving;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieEntiteit;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import nl.bzk.brp.model.operationeel.kern.DocumentStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
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
public abstract class AbstractHisDocumentModel extends AbstractFormeleHistorieEntiteit {

    @Id
    @SequenceGenerator(name = "HIS_DOCUMENT", sequenceName = "Kern.seq_His_Doc")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_DOCUMENT")
    @JsonProperty
    private Integer               iD;

    @ManyToOne
    @JoinColumn(name = "Doc")
    private DocumentModel         document;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Ident"))
    @JsonProperty
    private DocumentIdentificatie identificatie;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Aktenr"))
    @JsonProperty
    private Aktenummer            aktenummer;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
    @JsonProperty
    private DocumentOmschrijving  omschrijving;

    @ManyToOne
    @JoinColumn(name = "Partij")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Partij                partij;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisDocumentModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param documentModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisDocumentModel(final DocumentModel documentModel, final DocumentStandaardGroepModel groep) {
        this.document = documentModel;
        this.identificatie = groep.getIdentificatie();
        this.aktenummer = groep.getAktenummer();
        this.omschrijving = groep.getOmschrijving();
        this.partij = groep.getPartij();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisDocumentModel(final AbstractHisDocumentModel kopie) {
        super(kopie);
        document = kopie.getDocument();
        identificatie = kopie.getIdentificatie();
        aktenummer = kopie.getAktenummer();
        omschrijving = kopie.getOmschrijving();
        partij = kopie.getPartij();

    }

    /**
     * Retourneert ID van His Document.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Document van His Document.
     *
     * @return Document.
     */
    public DocumentModel getDocument() {
        return document;
    }

    /**
     * Retourneert Identificatie van His Document.
     *
     * @return Identificatie.
     */
    public DocumentIdentificatie getIdentificatie() {
        return identificatie;
    }

    /**
     * Retourneert Aktenummer van His Document.
     *
     * @return Aktenummer.
     */
    public Aktenummer getAktenummer() {
        return aktenummer;
    }

    /**
     * Retourneert Omschrijving van His Document.
     *
     * @return Omschrijving.
     */
    public DocumentOmschrijving getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert Partij van His Document.
     *
     * @return Partij.
     */
    public Partij getPartij() {
        return partij;
    }

}
