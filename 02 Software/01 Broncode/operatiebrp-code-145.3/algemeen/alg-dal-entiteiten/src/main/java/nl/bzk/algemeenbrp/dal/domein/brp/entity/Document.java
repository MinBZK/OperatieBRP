/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedHashSet;
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
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * The persistent class for the doc database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "doc", schema = "kern")
public class Document extends AbstractEntiteit implements AdministratiefGegeven, Serializable {
    /**
     * Sorteert de lijst van documenten o.b.v. de hashcode van aktenummer, identificatie,
     * omschrijving en soort document.
     */
    static final Comparator<Document> COMPARATOR = new Comparator<Document>() {

        @Override
        public int compare(final Document document1, final Document document2) {
            return berekenHash(document1) - berekenHash(document2);
        }

        private int berekenHash(final Document document) {
            final int prime = 31;
            int result = 1;
            result = prime * result + (document.aktenummer == null ? 0 : document.aktenummer.hashCode());
            result = prime * result + (document.omschrijving == null ? 0 : document.omschrijving.hashCode());
            result = prime * result + (document.soortDocument == null || document.soortDocument.getOmschrijving() == null ? 0
                    : document.soortDocument.getOmschrijving().hashCode());
            return result;
        }
    };
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "doc_id_generator", sequenceName = "kern.seq_doc", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_id_generator")
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "srt", nullable = false)
    private SoortDocument soortDocument;

    @Column(name = "aktenr", length = 7)
    private String aktenummer;

    @Column(name = "oms", length = 80)
    private String omschrijving;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partij", nullable = false)
    private Partij partij;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "document", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private final Set<ActieBron> actieBronSet = new LinkedHashSet<>(0);

    /**
     * JPA default constructor.
     */
    protected Document() {}

    /**
     * Maakt een Document object.
     *
     * @param soortDocument soort document
     * @param partij de partij
     */
    public Document(final SoortDocument soortDocument, final Partij partij) {
        setSoortDocument(soortDocument);
        setPartij(partij);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van Document.
     *
     * @param id de nieuwe waarde voor id van Document
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van soort document van Document.
     *
     * @return de waarde van soort document van Document
     */
    public SoortDocument getSoortDocument() {
        return soortDocument;
    }

    /**
     * Zet de waarden voor soort document van Document.
     *
     * @param soortDocument de nieuwe waarde voor soort document van Document
     */
    public void setSoortDocument(final SoortDocument soortDocument) {
        ValidationUtils.controleerOpNullWaarden("soortDocument mag niet null zijn", soortDocument);
        this.soortDocument = soortDocument;
    }

    /**
     * Geef de waarde van aktenummer van Document.
     *
     * @return de waarde van aktenummer van Document
     */
    public String getAktenummer() {
        return aktenummer;
    }

    /**
     * Zet de waarden voor aktenummer van Document.
     *
     * @param aktenummer de nieuwe waarde voor aktenummer van Document
     */
    public void setAktenummer(final String aktenummer) {
        ValidationUtils.controleerOpLegeWaarden("aktenummer mag geen lege string zijn", aktenummer);
        this.aktenummer = aktenummer;
    }

    /**
     * Geef de waarde van omschrijving van Document.
     *
     * @return de waarde van omschrijving van Document
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Zet de waarden voor omschrijving van Document.
     *
     * @param omschrijving de nieuwe waarde voor omschrijving van Document
     */
    public void setOmschrijving(final String omschrijving) {
        ValidationUtils.controleerOpLegeWaarden("omschrijving mag geen lege string zijn", omschrijving);
        this.omschrijving = omschrijving;
    }

    /**
     * Geef de waarde van partij van Document.
     *
     * @return de waarde van partij van Document
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet de waarden voor partij van Document.
     *
     * @param partij de nieuwe waarde voor partij van Document
     */
    public void setPartij(final Partij partij) {
        this.partij = partij;
    }

    /**
     * Geef de waarde van BRP actie set van Document.
     *
     * @return de waarde van BRP actie set van Document
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
     * @param actie actie
     * @return true, if successful
     */
    boolean bevatBRPActie(final BRPActie actie) {
        return getBRPActieSet().contains(actie);
    }

    /**
     * Wordt aangeroepen door BRPActie.addDocument om relatie wederkerig te registreren zonder
     * dubbele ActieBronnen te maken.
     *
     * @param actieBron actie bron
     */
    public final void addActieBron(final ActieBron actieBron) {
        if (actieBron.getDocument() == this && !bevatBRPActie(actieBron.getActie())) {
            actieBronSet.add(actieBron);
        }
    }

    /**
     * Bepaal of een ander document inhoudelijk gelijk is.
     *
     * @param anderDocument ander document
     * @return true, als het andere document inhoudelijk gelijk is, anders false
     */
    public boolean isInhoudelijkGelijkAan(final Document anderDocument) {
        if (anderDocument == null) {
            return false;
        }

        final boolean isInhoudelijkGelijk = new EqualsBuilder().append(aktenummer, anderDocument.aktenummer).append(omschrijving, anderDocument.omschrijving)
                .append(partij, anderDocument.partij).isEquals();

        final boolean soortDocumentGelijk = soortDocument == null && anderDocument.soortDocument == null
                || soortDocument != null && soortDocument.isInhoudelijkGelijkAan(anderDocument.soortDocument);
        return isInhoudelijkGelijk && soortDocumentGelijk;
    }
}
