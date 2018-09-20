/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the doc database table.
 * 
 */
@Entity
@Table(name = "doc", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class Document implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Sorteert de lijst van documenten o.b.v. de hashcode van aktenummer, identificatie, omschrijving en soort
     * document.
     */
    public final static Comparator<Document> COMPARATOR = new Comparator<Document>() {

        @Override
        public int compare(final Document document1, final Document document2) {
            return berekenHash(document1) - berekenHash(document2);
        }

        private int berekenHash(final Document document) {
            final int prime = 31;
            int result = 1;
            result = prime * result + (document.aktenummer == null ? 0 : document.aktenummer.hashCode());
            result = prime * result + (document.identificatie == null ? 0 : document.identificatie.hashCode());
            result = prime * result + (document.omschrijving == null ? 0 : document.omschrijving.hashCode());
            result =
                    prime
                            * result
                            + (document.soortDocument == null || document.soortDocument.getOmschrijving() == null ? 0
                                    : document.soortDocument.getOmschrijving().hashCode());
            return result;
        }
    };

    @Id
    @SequenceGenerator(name = "DOC_ID_GENERATOR", sequenceName = "KERN.SEQ_DOC", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOC_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Column(name = "aktenr", length = 7)
    private String aktenummer;

    @Enumerated(EnumType.STRING)
    @Column(name = "docstatushis", nullable = false, length = 1)
    private HistorieStatus documentStatusHistorie = HistorieStatus.X;

    @Column(name = "ident", length = 20)
    private String identificatie;

    @Column(name = "oms", length = 80)
    private String omschrijving;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "documentSet",
            cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<BRPActie> brpActieSet = new LinkedHashSet<BRPActie>(0);

    // bi-directional many-to-one association to Partij
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "partij")
    private Partij partij;

    // bi-directional many-to-one association to SoortDocument
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "srt", nullable = false)
    private SoortDocument soortDocument;

    // bi-directional many-to-one association to DocumentHistorie
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "document", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private final Set<DocumentHistorie> documentHistorieSet = new LinkedHashSet<DocumentHistorie>(0);

    /**
     * 
     */
    public Document() {
        brpActieSet = new LinkedHashSet<BRPActie>();
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

    public HistorieStatus getDocumentStatusHistorie() {
        return documentStatusHistorie;
    }

    public void setDocumentStatusHistorie(final HistorieStatus documentStatusHistorie) {
        this.documentStatusHistorie = documentStatusHistorie;
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

    public Set<BRPActie> getBRPActieSet() {
        return brpActieSet;
    }

    /**
     * @param brpActie
     */
    public void addBRPActie(final BRPActie brpActie) {
        if (!brpActieSet.contains(brpActie)) {
            brpActieSet.add(brpActie);
        }
        if (!brpActie.getDocumentSet().contains(this)) {
            brpActie.getDocumentSet().add(this);
        }
    }

    public Partij getPartij() {
        return partij;
    }

    public void setPartij(final Partij partij) {
        this.partij = partij;
    }

    public SoortDocument getSoortDocument() {
        return soortDocument;
    }

    public void setSoortDocument(final SoortDocument soortDocument) {
        this.soortDocument = soortDocument;
    }

    public Set<DocumentHistorie> getDocumentHistorieSet() {
        return documentHistorieSet;
    }

    /**
     * @param documentHistorie
     */
    public void addDocumentHistorie(final DocumentHistorie documentHistorie) {
        documentHistorie.setDocument(this);
        documentHistorieSet.add(documentHistorie);
    }

    /**
     * @param anderDocument
     * @return
     */
    public boolean isInhoudelijkGelijkAan(final Document anderDocument) {
        if (this == anderDocument) {
            return true;
        }
        if (anderDocument == null) {
            return false;
        }
        if (aktenummer == null) {
            if (anderDocument.aktenummer != null) {
                return false;
            }
        } else if (!aktenummer.equals(anderDocument.aktenummer)) {
            return false;
        }
        if (identificatie == null) {
            if (anderDocument.identificatie != null) {
                return false;
            }
        } else if (!identificatie.equals(anderDocument.identificatie)) {
            return false;
        }
        if (omschrijving == null) {
            if (anderDocument.omschrijving != null) {
                return false;
            }
        } else if (!omschrijving.equals(anderDocument.omschrijving)) {
            return false;
        }
        if (partij == null) {
            if (anderDocument.partij != null) {
                return false;
            }
        } else if (!partij.isInhoudelijkGelijkAan(anderDocument.partij)) {
            return false;
        }
        if (soortDocument == null) {
            if (anderDocument.soortDocument != null) {
                return false;
            }
        } else if (!soortDocument.isInhoudelijkGelijkAan(anderDocument.soortDocument)) {
            return false;
        }
        if (documentHistorieSet.size() != anderDocument.documentHistorieSet.size()) {
            return false;
        }
        final List<DocumentHistorie> documentHistorieList = new ArrayList<DocumentHistorie>(documentHistorieSet);
        final List<DocumentHistorie> anderDocumentHistorieList =
                new ArrayList<DocumentHistorie>(anderDocument.documentHistorieSet);

        Collections.sort(documentHistorieList, FormeleHistorie.COMPARATOR);
        Collections.sort(anderDocumentHistorieList, FormeleHistorie.COMPARATOR);

        for (int index = 0; index < documentHistorieList.size(); index++) {
            if (!documentHistorieList.get(index).isInhoudelijkGelijkAan(anderDocumentHistorieList.get(index))) {
                return false;
            }
        }
        return true;
    }
}
