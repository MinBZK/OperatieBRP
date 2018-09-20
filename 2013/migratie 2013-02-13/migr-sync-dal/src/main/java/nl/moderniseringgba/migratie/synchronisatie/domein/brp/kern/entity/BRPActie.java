/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.Lo3Herkomst;

/**
 * The persistent class for the actie database table.
 * 
 */
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
/**
 *
 */
@Entity
@Table(name = "actie", schema = "kern")
public class BRPActie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "ACTIE_ID_GENERATOR", sequenceName = "KERN.SEQ_ACTIE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACTIE_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Column(name = "tijdstipontlening")
    private Timestamp datumTijdOntlening;

    @Column(name = "tijdstipreg", nullable = false)
    private Timestamp datumTijdRegistratie;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "partij", nullable = false)
    private Partij partij;

    @Column(name = "srt", nullable = false)
    private Integer soortActieId;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "verdrag")
    private Verdrag verdrag;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "bron", schema = "kern", joinColumns = @JoinColumn(name = "actie"),
            inverseJoinColumns = @JoinColumn(name = "doc"))
    private final Set<Document> documentSet = new LinkedHashSet<Document>();

    // bi-directional one-to-one association to Lo3Herkomst
    @OneToOne(mappedBy = "brpActie", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Lo3Herkomst lo3Herkomst;

    public BRPActie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Timestamp getDatumTijdOntlening() {
        return datumTijdOntlening;
    }

    public void setDatumTijdOntlening(final Timestamp datumTijdOntlening) {
        this.datumTijdOntlening = datumTijdOntlening;
    }

    public Timestamp getDatumTijdRegistratie() {
        return datumTijdRegistratie;
    }

    public void setDatumTijdRegistratie(final Timestamp datumTijdRegistratie) {
        this.datumTijdRegistratie = datumTijdRegistratie;
    }

    public Partij getPartij() {
        return partij;
    }

    public void setPartij(final Partij partij) {
        this.partij = partij;
    }

    /**
     * @return
     */
    public SoortActie getSoortActie() {
        return SoortActie.parseId(soortActieId);

    }

    /**
     * @param soortActie
     */
    public void setSoortActie(final SoortActie soortActie) {
        if (soortActie == null) {
            soortActieId = null;
        } else {
            soortActieId = soortActie.getId();
        }
    }

    public Verdrag getVerdrag() {
        return verdrag;
    }

    public void setVerdrag(final Verdrag verdrag) {
        this.verdrag = verdrag;
    }

    public Set<Document> getDocumentSet() {
        return documentSet;
    }

    /**
     * @param document
     */
    public void addDocument(final Document document) {
        if (!documentSet.contains(document)) {
            documentSet.add(document);
        }
        if (!document.getBRPActieSet().contains(this)) {
            document.getBRPActieSet().add(this);
        }
    }

    public Lo3Herkomst getLo3Herkomst() {
        return lo3Herkomst;
    }

    public void setLo3Herkomst(final Lo3Herkomst lo3Herkomst) {
        this.lo3Herkomst = lo3Herkomst;
        if (lo3Herkomst != null && !this.equals(lo3Herkomst.getBrpActie())) {
            lo3Herkomst.setBrpActie(this);
        }
    }

    /**
     * @param andereActie
     * @return
     */
    public boolean isInhoudelijkGelijkAan(final BRPActie andereActie) {
        if (this == andereActie) {
            return true;
        }
        if (andereActie == null) {
            return false;
        }
        if (datumTijdOntlening == null) {
            if (andereActie.datumTijdOntlening != null) {
                return false;
            }
        } else if (!datumTijdOntlening.equals(andereActie.datumTijdOntlening)) {
            return false;
        }
        if (datumTijdRegistratie == null) {
            if (andereActie.datumTijdRegistratie != null) {
                return false;
            }
        } else if (!datumTijdRegistratie.equals(andereActie.datumTijdRegistratie)) {
            return false;
        }
        if (partij == null) {
            if (andereActie.partij != null) {
                return false;
            }
        } else if (!partij.isInhoudelijkGelijkAan(andereActie.partij)) {
            return false;
        }
        if (soortActieId == null) {
            if (andereActie.soortActieId != null) {
                return false;
            }
        } else if (!soortActieId.equals(andereActie.soortActieId)) {
            return false;
        }
        if (verdrag == null) {
            if (andereActie.verdrag != null) {
                return false;
            }
        } else if (!verdrag.isInhoudelijkGelijkAan(andereActie.verdrag)) {
            return false;
        }
        if (documentSet.size() != andereActie.documentSet.size()) {
            return false;
        }
        final List<Document> documentList = new ArrayList<Document>(documentSet);
        final List<Document> andereDocumentList = new ArrayList<Document>(andereActie.documentSet);

        Collections.sort(documentList, Document.COMPARATOR);
        Collections.sort(andereDocumentList, Document.COMPARATOR);

        for (int index = 0; index < documentList.size(); index++) {
            if (!documentList.get(index).isInhoudelijkGelijkAan(andereDocumentList.get(index))) {
                return false;
            }
        }
        return true;
    }
}
