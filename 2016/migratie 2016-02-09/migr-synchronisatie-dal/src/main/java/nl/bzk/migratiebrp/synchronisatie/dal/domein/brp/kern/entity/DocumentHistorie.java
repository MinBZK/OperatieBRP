/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the his_doc database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_doc", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"doc", "tsreg" }))
@SuppressWarnings("checkstyle:designforextension")
public class DocumentHistorie extends AbstractFormeleHistorie implements AdministratiefGegeven, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String DOCUMENT_MAG_NIET_NULL_ZIJN = "document mag niet null zijn";
    private static final String PARTIJ_MAG_NIET_NULL_ZIJN = "partij mag niet null zijn";

    @Id
    @SequenceGenerator(name = "his_doc_id_generator", sequenceName = "kern.seq_his_doc", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_doc_id_generator")
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "doc", nullable = false)
    private Document document;

    @Column(name = "aktenr", length = 7)
    private String aktenummer;

    @Column(name = "ident", length = 20)
    private String identificatie;

    @Column(name = "oms", length = 40)
    private String omschrijving;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "partij", nullable = false)
    private Partij partij;

    /**
     * JPA default constructor.
     */
    protected DocumentHistorie() {
    }

    /**
     * Maakt een DocumentHistorie object.
     *
     * @param document
     *            het document
     * @param partij
     *            de partij
     */
    public DocumentHistorie(final Document document, final Partij partij) {
        ValidationUtils.controleerOpNullWaarden(DOCUMENT_MAG_NIET_NULL_ZIJN, document);
        this.document = document;
        ValidationUtils.controleerOpNullWaarden(PARTIJ_MAG_NIET_NULL_ZIJN, partij);
        this.partij = partij;
    }

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van aktenummer.
     *
     * @return aktenummer
     */
    public String getAktenummer() {
        return aktenummer;
    }

    /**
     * Zet de waarde van aktenummer.
     *
     * @param aktenummer
     *            aktenummer
     */
    public void setAktenummer(final String aktenummer) {
        Validatie.controleerOpLegeWaarden("aktenummer mag geen lege string zijn", aktenummer);
        this.aktenummer = aktenummer;
    }

    /**
     * Geef de waarde van identificatie.
     *
     * @return identificatie
     */
    public String getIdentificatie() {
        return identificatie;
    }

    /**
     * Zet de waarde van identificatie.
     *
     * @param identificatie
     *            identificatie
     */
    public void setIdentificatie(final String identificatie) {
        Validatie.controleerOpLegeWaarden("identificatie mag geen lege string zijn", identificatie);
        this.identificatie = identificatie;
    }

    /**
     * Geef de waarde van omschrijving.
     *
     * @return omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Zet de waarde van omschrijving.
     *
     * @param omschrijving
     *            omschrijving
     */
    public void setOmschrijving(final String omschrijving) {
        Validatie.controleerOpLegeWaarden("omschrijving mag geen lege string zijn", omschrijving);
        this.omschrijving = omschrijving;
    }

    /**
     * Geef de waarde van document.
     *
     * @return document
     */
    public Document getDocument() {
        return document;
    }

    /**
     * Zet de waarde van document.
     *
     * @param document
     *            document
     */
    public void setDocument(final Document document) {
        ValidationUtils.controleerOpNullWaarden(DOCUMENT_MAG_NIET_NULL_ZIJN, document);
        this.document = document;
    }

    /**
     * Geef de waarde van partij.
     *
     * @return partij
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet de waarde van partij.
     *
     * @param partij
     *            partij
     */
    public void setPartij(final Partij partij) {
        ValidationUtils.controleerOpNullWaarden(PARTIJ_MAG_NIET_NULL_ZIJN, partij);
        this.partij = partij;
    }

    /**
     * Bepaal of een ander document historie inhoudelijk gelijk is.
     *
     * @param andereDocumentHistorie
     *            andere document historie
     * @return true, als de andere document historie inhoudelijk gelijk is, anders false
     */
    public boolean isInhoudelijkGelijkAan(final DocumentHistorie andereDocumentHistorie) {
        if (this == andereDocumentHistorie) {
            return true;
        }
        if (andereDocumentHistorie == null) {
            return false;
        }
        if (getAktenummer() == null) {
            if (andereDocumentHistorie.getAktenummer() != null) {
                return false;
            }
        } else if (!getAktenummer().equals(andereDocumentHistorie.getAktenummer())) {
            return false;
        }
        if (getDatumTijdRegistratie() == null) {
            if (andereDocumentHistorie.getDatumTijdRegistratie() != null) {
                return false;
            }
        } else if (!getDatumTijdRegistratie().equals(andereDocumentHistorie.getDatumTijdRegistratie())) {
            return false;
        }
        if (getDatumTijdVerval() == null) {
            if (andereDocumentHistorie.getDatumTijdVerval() != null) {
                return false;
            }
        } else if (!getDatumTijdVerval().equals(andereDocumentHistorie.getDatumTijdVerval())) {
            return false;
        }
        if (getIdentificatie() == null) {
            if (andereDocumentHistorie.getIdentificatie() != null) {
                return false;
            }
        } else if (!getIdentificatie().equals(andereDocumentHistorie.getIdentificatie())) {
            return false;
        }
        if (getOmschrijving() == null) {
            if (andereDocumentHistorie.getOmschrijving() != null) {
                return false;
            }
        } else if (!getOmschrijving().equals(andereDocumentHistorie.getOmschrijving())) {
            return false;
        }
        if (getPartij() == null) {
            if (andereDocumentHistorie.getPartij() != null) {
                return false;
            }
        } else if (!getPartij().isInhoudelijkGelijkAan(andereDocumentHistorie.getPartij())) {
            return false;
        }
        return true;
    }
}
