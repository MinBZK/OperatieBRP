/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the his_doc database table.
 * 
 */
@Entity
@Table(name = "his_doc", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class DocumentHistorie implements Serializable, FormeleHistorie {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "HIS_DOC_ID_GENERATOR", sequenceName = "KERN.SEQ_HIS_DOC", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HIS_DOC_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Column(name = "aktenr", length = 7)
    private String aktenummer;

    @Column(name = "ident", length = 20)
    private String identificatie;

    @Column(name = "oms", length = 80)
    private String omschrijving;

    @Column(name = "tsreg")
    private Timestamp datumTijdRegistratie;

    @Column(name = "tsverval")
    private Timestamp datumTijdVerval;

    // bi-directional many-to-one association to BRPActie
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "actieverval")
    private BRPActie actieVerval;

    // bi-directional many-to-one association to BRPActie
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "actieinh")
    private BRPActie actieInhoud;

    // bi-directional many-to-one association to Document
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "doc")
    private Document document;

    // bi-directional many-to-one association to Partij
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "partij", nullable = false)
    private Partij partij;

    public DocumentHistorie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getAktenummer() {
        return aktenummer;
    }

    public void setAktenummer(final String aktenummer) {
        this.aktenummer = aktenummer;
    }

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(final String identificatie) {
        this.identificatie = identificatie;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

    @Override
    public Timestamp getDatumTijdRegistratie() {
        return datumTijdRegistratie;
    }

    @Override
    public void setDatumTijdRegistratie(final Timestamp datumTijdRegistratie) {
        this.datumTijdRegistratie = datumTijdRegistratie;
    }

    @Override
    public Timestamp getDatumTijdVerval() {
        return datumTijdVerval;
    }

    @Override
    public void setDatumTijdVerval(final Timestamp datumTijdVerval) {
        this.datumTijdVerval = datumTijdVerval;
    }

    @Override
    public BRPActie getActieVerval() {
        return actieVerval;
    }

    @Override
    public void setActieVerval(final BRPActie actieVerval) {
        this.actieVerval = actieVerval;
    }

    @Override
    public BRPActie getActieInhoud() {
        return actieInhoud;
    }

    @Override
    public void setActieInhoud(final BRPActie actieInhoud) {
        this.actieInhoud = actieInhoud;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(final Document document) {
        this.document = document;
    }

    public Partij getPartij() {
        return partij;
    }

    public void setPartij(final Partij partij) {
        this.partij = partij;
    }

    /**
     * @param andereDocumentHistorie
     * @return
     */
    public boolean isInhoudelijkGelijkAan(final DocumentHistorie andereDocumentHistorie) {
        if (this == andereDocumentHistorie) {
            return true;
        }
        if (andereDocumentHistorie == null) {
            return false;
        }
        if (aktenummer == null) {
            if (andereDocumentHistorie.aktenummer != null) {
                return false;
            }
        } else if (!aktenummer.equals(andereDocumentHistorie.aktenummer)) {
            return false;
        }
        if (datumTijdRegistratie == null) {
            if (andereDocumentHistorie.datumTijdRegistratie != null) {
                return false;
            }
        } else if (!datumTijdRegistratie.equals(andereDocumentHistorie.datumTijdRegistratie)) {
            return false;
        }
        if (datumTijdVerval == null) {
            if (andereDocumentHistorie.datumTijdVerval != null) {
                return false;
            }
        } else if (!datumTijdVerval.equals(andereDocumentHistorie.datumTijdVerval)) {
            return false;
        }
        if (identificatie == null) {
            if (andereDocumentHistorie.identificatie != null) {
                return false;
            }
        } else if (!identificatie.equals(andereDocumentHistorie.identificatie)) {
            return false;
        }
        if (omschrijving == null) {
            if (andereDocumentHistorie.omschrijving != null) {
                return false;
            }
        } else if (!omschrijving.equals(andereDocumentHistorie.omschrijving)) {
            return false;
        }
        if (partij == null) {
            if (andereDocumentHistorie.partij != null) {
                return false;
            }
        } else if (!partij.isInhoudelijkGelijkAan(andereDocumentHistorie.partij)) {
            return false;
        }
        return true;
    }

}
