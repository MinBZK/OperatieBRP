/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

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
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the doc database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "doc", schema = "kern")
@SuppressWarnings("checkstyle:designforextension")
public class Document extends AbstractDeltaEntiteit implements AdministratiefGegeven, Serializable {
    /**
     * Sorteert de lijst van documenten o.b.v. de hashcode van aktenummer, identificatie, omschrijving en soort
     * document.
     */
    public static final Comparator<Document> COMPARATOR = new Comparator<Document>() {

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
                    prime * result
                     + (document.soortDocument == null
                        || document.soortDocument.getOmschrijving() == null ? 0 : document.soortDocument.getOmschrijving().hashCode());
            return result;
        }
    };
    private static final long serialVersionUID = 1L;
    private static final String SOORT_DOCUMENT_MAG_NIET_NULL_ZIJN = "soortDocument mag niet null zijn";

    @Id
    @SequenceGenerator(name = "doc_id_generator", sequenceName = "kern.seq_doc", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_id_generator")
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "srt", nullable = false)
    private SoortDocument soortDocument;

    @Column(name = "ident", length = 20)
    private String identificatie;

    @Column(name = "aktenr", length = 7)
    private String aktenummer;

    @Column(name = "oms", length = 80)
    private String omschrijving;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "partij")
    private Partij partij;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "document", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<ActieBron> actieBronSet = new LinkedHashSet<>(0);

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "document", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<DocumentHistorie> documentHistorieSet = new LinkedHashSet<>(0);

    /**
     * JPA default constructor.
     */
    protected Document() {
    }

    /**
     * Maakt een Document object.
     *
     * @param soortDocument
     *            soort document
     */
    public Document(final SoortDocument soortDocument) {
        ValidationUtils.controleerOpNullWaarden(SOORT_DOCUMENT_MAG_NIET_NULL_ZIJN, soortDocument);
        this.soortDocument = soortDocument;
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
     * Geef de waarde van soort document.
     *
     * @return soort document
     */
    public SoortDocument getSoortDocument() {
        return soortDocument;
    }

    /**
     * Zet de waarde van soort document.
     *
     * @param soortDocument
     *            soort document
     */
    public void setSoortDocument(final SoortDocument soortDocument) {
        ValidationUtils.controleerOpNullWaarden(SOORT_DOCUMENT_MAG_NIET_NULL_ZIJN, soortDocument);
        this.soortDocument = soortDocument;
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
        this.partij = partij;
    }

    /**
     * Geef de waarde van BRP actie set.
     *
     * @return BRP actie set
     */
    public Set<BRPActie> getBRPActieSet() {
        final Set<BRPActie> result = new LinkedHashSet<>();
        for (final ActieBron actieBron : actieBronSet) {
            result.add(actieBron.getActie());
        }
        return result;
    }

    /**
     * Bevat brp actie.
     *
     * @param actie
     *            actie
     * @return true, if successful
     */
    public boolean bevatBRPActie(final BRPActie actie) {
        return getBRPActieSet().contains(actie);
    }

    /**
     * Wordt aangeroepen door BRPActie.addDocument om relatie wederkerig te registreren zonder dubbele ActieBronnen te
     * maken.
     *
     * @param actieBron
     *            actie bron
     */
    public final void addActieBron(final ActieBron actieBron) {
        if (actieBron.getDocument() == this && !bevatBRPActie(actieBron.getActie())) {
            actieBronSet.add(actieBron);
        }
    }

    /**
     * Geef de waarde van document historie set.
     *
     * @return document historie set
     */
    public Set<DocumentHistorie> getDocumentHistorieSet() {
        return documentHistorieSet;
    }

    /**
     * Toevoegen van een document historie.
     *
     * @param documentHistorie
     *            document historie
     */
    public void addDocumentHistorie(final DocumentHistorie documentHistorie) {
        documentHistorie.setDocument(this);
        documentHistorieSet.add(documentHistorie);
    }

    /**
     * Bepaal of een ander document inhoudelijk gelijk is.
     *
     * @param anderDocument
     *            ander document
     * @return true, als het andere document inhoudelijk gelijk is, anders false
     */
    public boolean isInhoudelijkGelijkAan(final Document anderDocument) {
        if (this == anderDocument) {
            return true;
        }
        if (anderDocument == null) {
            return false;
        }
        if (getAktenummer() == null) {
            if (anderDocument.getAktenummer() != null) {
                return false;
            }
        } else if (!getAktenummer().equals(anderDocument.getAktenummer())) {
            return false;
        }
        if (getIdentificatie() == null) {
            if (anderDocument.getIdentificatie() != null) {
                return false;
            }
        } else if (!getIdentificatie().equals(anderDocument.getIdentificatie())) {
            return false;
        }
        if (getOmschrijving() == null) {
            if (anderDocument.getOmschrijving() != null) {
                return false;
            }
        } else if (!getOmschrijving().equals(anderDocument.getOmschrijving())) {
            return false;
        }
        if (getPartij() == null) {
            if (anderDocument.getPartij() != null) {
                return false;
            }
        } else if (!getPartij().isInhoudelijkGelijkAan(anderDocument.getPartij())) {
            return false;
        }
        if (getSoortDocument() == null) {
            if (anderDocument.getSoortDocument() != null) {
                return false;
            }
        } else if (!getSoortDocument().isInhoudelijkGelijkAan(anderDocument.getSoortDocument())) {
            return false;
        }
        if (getDocumentHistorieSet().size() != anderDocument.getDocumentHistorieSet().size()) {
            return false;
        }
        final List<DocumentHistorie> documentHistorieList = new ArrayList<>(getDocumentHistorieSet());
        final List<DocumentHistorie> anderDocumentHistorieList = new ArrayList<>(anderDocument.getDocumentHistorieSet());

        Collections.sort(documentHistorieList, FormeleHistorie.COMPARATOR);
        Collections.sort(anderDocumentHistorieList, FormeleHistorie.COMPARATOR);

        for (int index = 0; index < documentHistorieList.size(); index++) {
            if (!documentHistorieList.get(index).isInhoudelijkGelijkAan(anderDocumentHistorieList.get(index))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verwijderen van een document historie.
     *
     * @param documentHistorie
     *            document historie
     */
    public void removeDocumentHistorie(final DocumentHistorie documentHistorie) {
        documentHistorieSet.remove(documentHistorie);
    }
}
