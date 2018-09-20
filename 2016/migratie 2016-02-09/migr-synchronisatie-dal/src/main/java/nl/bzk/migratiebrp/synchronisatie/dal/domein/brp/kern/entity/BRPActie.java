/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

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
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.util.common.Kopieer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The persistent class for the actie database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "actie", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"id" }))
@SuppressWarnings("checkstyle:designforextension")
public class BRPActie extends AbstractDeltaEntiteit implements AdministratiefGegeven, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "actie_id_generator", sequenceName = "kern.seq_actie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actie_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "srt", nullable = false)
    private short soortActieId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "admhnd", nullable = false)
    private AdministratieveHandeling administratieveHandeling;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "partij", nullable = false)
    private Partij partij;

    @Column(name = "tsreg", nullable = false)
    private Timestamp datumTijdRegistratie;

    @Column(name = "datontlening")
    private Integer datumOntlening;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "actie", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval = true)
    private final Set<ActieBron> actieBronSet = new LinkedHashSet<>(0);

    // bi-directional one-to-one association to Lo3Herkomst
    @OneToOne(mappedBy = "actie", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    private Lo3Voorkomen lo3Voorkomen;

    /**
     * JPA default constructor.
     */
    protected BRPActie() {
    }

    /**
     * Maak een nieuwe BRP actie.
     *
     * @param soortActie
     *            soort actie
     * @param administratieveHandeling
     *            administratieve handeling
     * @param partij
     *            partij
     * @param datumTijdRegistratie
     *            datum tijd registratie
     */
    public BRPActie(
        final SoortActie soortActie,
        final AdministratieveHandeling administratieveHandeling,
        final Partij partij,
        final Timestamp datumTijdRegistratie)
    {
        setSoortActie(soortActie);
        setAdministratieveHandeling(administratieveHandeling);
        setPartij(partij);
        setDatumTijdRegistratie(datumTijdRegistratie);
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
     * Geef de waarde van soort actie.
     *
     * @return soort actie
     */
    public SoortActie getSoortActie() {
        return SoortActie.parseId(soortActieId);
    }

    /**
     * Zet de waarde van soort actie.
     *
     * @param soortActie
     *            soort actie
     */
    public void setSoortActie(final SoortActie soortActie) {
        ValidationUtils.controleerOpNullWaarden("soortActie mag niet null zijn", soortActie);
        soortActieId = soortActie.getId();
    }

    /**
     * Geef de waarde van administratieve handeling.
     *
     * @return administratieve handeling
     */
    public AdministratieveHandeling getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Zet de waarde van administratieve handeling.
     *
     * @param administratieveHandeling
     *            administratieve handeling
     */
    public void setAdministratieveHandeling(final AdministratieveHandeling administratieveHandeling) {
        ValidationUtils.controleerOpNullWaarden("administratieveHandeling mag niet null zijn", administratieveHandeling);
        this.administratieveHandeling = administratieveHandeling;
    }

    /**
     * Geef de waarde van datum tijd registratie.
     *
     * @return datum tijd registratie
     */
    public Timestamp getDatumTijdRegistratie() {
        return Kopieer.timestamp(datumTijdRegistratie);
    }

    /**
     * Zet de waarde van datum tijd registratie.
     *
     * @param datumTijdRegistratie
     *            datum tijd registratie
     */
    public void setDatumTijdRegistratie(final Timestamp datumTijdRegistratie) {
        ValidationUtils.controleerOpNullWaarden("datumTijdRegistratie mag niet null zijn", datumTijdRegistratie);
        this.datumTijdRegistratie = Kopieer.timestamp(datumTijdRegistratie);
    }

    /**
     * Geef de waarde van datum ontlening.
     *
     * @return datum ontlening
     */
    public Integer getDatumOntlening() {
        return datumOntlening;
    }

    /**
     * Zet de waarde van datum ontlening.
     *
     * @param datumOntlening
     *            datum ontlening
     */
    public void setDatumOntlening(final Integer datumOntlening) {
        this.datumOntlening = datumOntlening;
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
        ValidationUtils.controleerOpNullWaarden("partij mag niet null zijn", partij);
        this.partij = partij;
    }

    /**
     * Geef de waarde van actie bron set.
     *
     * @return actie bron set
     */
    public Set<ActieBron> getActieBronSet() {
        return actieBronSet;
    }

    /**
     * Geef de waarde van document set.
     *
     * @return document set
     */
    public Set<Document> getDocumentSet() {
        final Set<Document> result = new LinkedHashSet<>();
        for (final ActieBron actieBron : actieBronSet) {
            final Document document = actieBron.getDocument();
            if (document != null) {
                result.add(document);
            }
        }
        return result;
    }

    /**
     * Geef de waarde van rechtsgrond set.
     *
     * @return rechtsgrond set
     */
    public Set<Rechtsgrond> getRechtsgrondSet() {
        final Set<Rechtsgrond> result = new LinkedHashSet<>();
        for (final ActieBron actieBron : actieBronSet) {
            final Rechtsgrond rechtsgrond = actieBron.getRechtsgrond();
            if (rechtsgrond != null) {
                result.add(rechtsgrond);
            }
        }
        return result;
    }

    /**
     * Bevat document.
     *
     * @param document
     *            document
     * @return true, if successful
     */
    public boolean bevatDocument(final Document document) {
        return getDocumentSet().contains(document);
    }

    /**
     * Maakt een bidirectionele koppeling tussen het meegegeven document en deze brpActie.
     *
     * @param document
     *            het document dat moet worden toegevoegd aan deze brpActie
     * @param rechtsgrondOmschrijving
     *            De rechtsgrondomschrijving, te zetten in actieBron
     */
    public final void koppelDocumentViaActieBron(final Document document, final String rechtsgrondOmschrijving) {
        if (!bevatDocument(document)) {
            final ActieBron actieBron = new ActieBron(this);
            actieBron.setDocument(document);
            actieBron.setRechtsgrondOmschrijving(rechtsgrondOmschrijving);
            actieBronSet.add(actieBron);

            if (!document.bevatBRPActie(this)) {
                document.addActieBron(actieBron);
            }

        }
    }

    /**
     * Toevoegen van een actie bron.
     *
     * @param actieBron
     *            actie bron
     */
    public void addActieBron(final ActieBron actieBron) {
        actieBron.setActie(this);
        actieBronSet.add(actieBron);
    }

    /**
     * Verwijderen van een actie bron.
     *
     * @param actieBron
     *            actie bron
     */
    public void removeActieBron(final ActieBron actieBron) {
        actieBronSet.remove(actieBron);
    }

    /**
     * Geef de waarde van lo3 voorkomen.
     *
     * @return lo3 voorkomen
     */
    public Lo3Voorkomen getLo3Voorkomen() {
        return lo3Voorkomen;
    }

    /**
     * Zet de waarde van lo3 voorkomen.
     *
     * @param lo3Voorkomen
     *            lo3 voorkomen
     */
    public void setLo3Voorkomen(final Lo3Voorkomen lo3Voorkomen) {
        this.lo3Voorkomen = lo3Voorkomen;
    }

    /**
     * Bepaal of een andere actie inhoudelijk gelijk is.
     *
     * @param andereActie
     *            andere actie
     * @return true, als de andere actie inhoudelijk gelijk is, anders false
     */
    public boolean isInhoudelijkGelijkAan(final BRPActie andereActie) {
        if (this == andereActie) {
            return true;
        }
        if (andereActie == null) {
            return false;
        }
        if (getDatumTijdRegistratie() == null) {
            if (andereActie.getDatumTijdRegistratie() != null) {
                return false;
            }
        } else if (!getDatumTijdRegistratie().equals(andereActie.getDatumTijdRegistratie())) {
            return false;
        }
        if (getDatumOntlening() == null) {
            if (andereActie.getDatumOntlening() != null) {
                return false;
            }
        } else if (!getDatumOntlening().equals(andereActie.getDatumOntlening())) {
            return false;
        }
        if (getPartij() == null) {
            if (andereActie.getPartij() != null) {
                return false;
            }
        } else if (!getPartij().isInhoudelijkGelijkAan(andereActie.getPartij())) {
            return false;
        }
        if (getSoortActie() != andereActie.getSoortActie()) {
            return false;
        }
        if (getDocumentSet().size() != andereActie.getDocumentSet().size()) {
            return false;
        }
        final List<Document> documentList = new ArrayList<>(getDocumentSet());
        final List<Document> andereDocumentList = new ArrayList<>(andereActie.getDocumentSet());

        Collections.sort(documentList, Document.COMPARATOR);
        Collections.sort(andereDocumentList, Document.COMPARATOR);

        for (int index = 0; index < documentList.size(); index++) {
            if (!documentList.get(index).isInhoudelijkGelijkAan(andereDocumentList.get(index))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("id", id)
                                                                          .append("soortActie", getSoortActie())
                                                                          .append("administratieveHandeling", administratieveHandeling)
                                                                          .append("partij", partij)
                                                                          .append("datumTijdRegistratie", datumTijdRegistratie)
                                                                          .append("datumOntlening", datumOntlening)
                                                                          .append("lo3Voorkomen", lo3Voorkomen)
                                                                          .toString();
    }

    /**
     * @return true als deze actie voort komt uit LO3 categorie 07 (Inschrijving) of categorie 13 (Kiesrecht).
     */
    public boolean isCat07Of13Actie() {
        return lo3Voorkomen != null && ("07".equals(lo3Voorkomen.getCategorie()) || isCat13Actie());
    }

    /**
     * @return true als deze actie voort komt uit LO3 categorie 13 (Kiesrecht).
     */
    public boolean isCat13Actie() {
        return lo3Voorkomen != null && "13".equals(lo3Voorkomen.getCategorie());
    }

}
