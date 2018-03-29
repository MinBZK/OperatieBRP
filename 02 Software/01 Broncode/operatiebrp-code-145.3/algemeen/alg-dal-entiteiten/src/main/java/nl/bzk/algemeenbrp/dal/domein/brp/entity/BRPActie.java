/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The persistent class for the actie database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "actie", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class BRPActie extends AbstractEntiteit implements AdministratiefGegeven, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "actie_id_generator", sequenceName = "kern.seq_actie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actie_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "srt", nullable = false)
    private int soortActieId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "admhnd", nullable = false)
    private AdministratieveHandeling administratieveHandeling;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partij", nullable = false)
    private Partij partij;

    @Column(name = "tsreg", nullable = false)
    private Timestamp datumTijdRegistratie;

    @Column(name = "datontlening")
    private Integer datumOntlening;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "actie", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private final Set<ActieBron> actieBronSet = new LinkedHashSet<>(0);

    // bi-directional one-to-one association to Lo3Herkomst
    @OneToOne(mappedBy = "actie", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Lo3Voorkomen lo3Voorkomen;

    /**
     * JPA default constructor.
     */
    protected BRPActie() {
    }

    /**
     * Maak een nieuwe BRP actie.
     * @param soortActie soort actie
     * @param administratieveHandeling administratieve handeling
     * @param partij partij
     * @param datumTijdRegistratie datum tijd registratie
     */
    public BRPActie(final SoortActie soortActie, final AdministratieveHandeling administratieveHandeling, final Partij partij,
                    final Timestamp datumTijdRegistratie) {
        setSoortActie(soortActie);
        setAdministratieveHandeling(administratieveHandeling);
        setPartij(partij);
        setDatumTijdRegistratie(datumTijdRegistratie);
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
     * Zet de waarden voor id van BRPActie.
     * @param id de nieuwe waarde voor id van BRPActie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van soort actie van BRPActie.
     * @return de waarde van soort actie van BRPActie
     */
    public SoortActie getSoortActie() {
        return SoortActie.parseId(soortActieId);
    }

    /**
     * Zet de waarden voor soort actie van BRPActie.
     * @param soortActie de nieuwe waarde voor soort actie van BRPActie
     */
    public void setSoortActie(final SoortActie soortActie) {
        ValidationUtils.controleerOpNullWaarden("soortActie mag niet null zijn", soortActie);
        soortActieId = soortActie.getId();
    }

    /**
     * Geef de waarde van administratieve handeling van BRPActie.
     * @return de waarde van administratieve handeling van BRPActie
     */
    public AdministratieveHandeling getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Zet de waarden voor administratieve handeling van BRPActie.
     * @param administratieveHandeling de nieuwe waarde voor administratieve handeling van BRPActie
     */
    public void setAdministratieveHandeling(final AdministratieveHandeling administratieveHandeling) {
        ValidationUtils.controleerOpNullWaarden("administratieveHandeling mag niet null zijn", administratieveHandeling);
        this.administratieveHandeling = administratieveHandeling;
    }

    /**
     * Geef de waarde van datum tijd registratie van BRPActie.
     * @return de waarde van datum tijd registratie van BRPActie
     */
    public Timestamp getDatumTijdRegistratie() {
        return Entiteit.timestamp(datumTijdRegistratie);
    }

    /**
     * Zet de waarden voor datum tijd registratie van BRPActie.
     * @param datumTijdRegistratie de nieuwe waarde voor datum tijd registratie van BRPActie
     */
    public void setDatumTijdRegistratie(final Timestamp datumTijdRegistratie) {
        ValidationUtils.controleerOpNullWaarden("datumTijdRegistratie mag niet null zijn", datumTijdRegistratie);
        this.datumTijdRegistratie = Entiteit.timestamp(datumTijdRegistratie);
    }

    /**
     * Geef de waarde van datum ontlening van BRPActie.
     * @return de waarde van datum ontlening van BRPActie
     */
    public Integer getDatumOntlening() {
        return datumOntlening;
    }

    /**
     * Zet de waarden voor datum ontlening van BRPActie.
     * @param datumOntlening de nieuwe waarde voor datum ontlening van BRPActie
     */
    public void setDatumOntlening(final Integer datumOntlening) {
        this.datumOntlening = datumOntlening;
    }

    /**
     * Geef de waarde van partij van BRPActie.
     * @return de waarde van partij van BRPActie
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet de waarden voor partij van BRPActie.
     * @param partij de nieuwe waarde voor partij van BRPActie
     */
    public void setPartij(final Partij partij) {
        ValidationUtils.controleerOpNullWaarden("partij mag niet null zijn", partij);
        this.partij = partij;
    }

    /**
     * Geef de waarde van actie bron set van BRPActie.
     * @return de waarde van actie bron set van BRPActie
     */
    public Set<ActieBron> getActieBronSet() {
        return actieBronSet;
    }

    /**
     * Geef de waarde van document set van BRPActie.
     * @return de waarde van document set van BRPActie
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
     * Geef de waarde van rechtsgrond set van BRPActie.
     * @return de waarde van rechtsgrond set van BRPActie
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
     * @param document document
     * @return true, if successful
     */
    public boolean bevatDocument(final Document document) {
        return getDocumentSet().contains(document);
    }

    /**
     * Maakt een bidirectionele koppeling tussen het meegegeven document en deze brpActie.
     * @param document het document dat moet worden toegevoegd aan deze brpActie
     */
    public final void koppelDocumentViaActieBron(final Document document) {
        koppelDocumentViaActieBron(document, null, null);
    }

    /**
     * Maakt een bidirectionele koppeling tussen het meegegeven document en deze brpActie.
     * @param document het document dat moet worden toegevoegd aan deze brpActie
     * @param rechtsgrondOmschrijving De rechtsgrondomschrijving, te zetten in actieBron
     */
    public final void koppelDocumentViaActieBron(final Document document, final String rechtsgrondOmschrijving) {
        koppelDocumentViaActieBron(document, null, rechtsgrondOmschrijving);
    }

    /**
     * Maakt een bidirectionele koppeling tussen het meegegeven document en deze brpActie.
     * @param document het document dat moet worden toegevoegd aan deze brpActie
     * @param rechtsgrond De rechtsgrond, te zetten in actieBron
     */
    public final void koppelDocumentViaActieBron(final Document document, final Rechtsgrond rechtsgrond) {
        koppelDocumentViaActieBron(document, rechtsgrond, null);
    }

    /**
     * Maakt een bidirectionele koppeling tussen het meegegeven document en deze brpActie.
     * @param document het document dat moet worden toegevoegd aan deze brpActie
     * @param rechtsgrond De rechtsgrond, te zetten in actieBron
     * @param rechtsgrondOmschrijving De rechtsgrondomschrijving, te zetten in actieBron
     */
    public void koppelDocumentViaActieBron(final Document document, final Rechtsgrond rechtsgrond, final String rechtsgrondOmschrijving) {
        if (!bevatDocument(document)) {
            final ActieBron actieBron = new ActieBron(this);
            actieBron.setDocument(document);
            actieBron.setRechtsgrond(rechtsgrond);
            actieBron.setRechtsgrondOmschrijving(rechtsgrondOmschrijving);
            actieBronSet.add(actieBron);

            if (document != null && !document.bevatBRPActie(this)) {
                document.addActieBron(actieBron);
            }
        }
    }

    /**
     * Toevoegen van een actie bron.
     * @param actieBron actie bron
     */
    public void addActieBron(final ActieBron actieBron) {
        actieBron.setActie(this);
        actieBronSet.add(actieBron);
    }

    /**
     * Verwijderen van een actie bron.
     * @param actieBron actie bron
     */
    public void removeActieBron(final ActieBron actieBron) {
        actieBronSet.remove(actieBron);
    }

    /**
     * Geef de waarde van lo3 voorkomen van BRPActie.
     * @return de waarde van lo3 voorkomen van BRPActie
     */
    public Lo3Voorkomen getLo3Voorkomen() {
        return lo3Voorkomen;
    }

    /**
     * Zet de waarden voor lo3 voorkomen van BRPActie.
     * @param lo3Voorkomen de nieuwe waarde voor lo3 voorkomen van BRPActie
     */
    public void setLo3Voorkomen(final Lo3Voorkomen lo3Voorkomen) {
        this.lo3Voorkomen = lo3Voorkomen;
    }

    /**
     * Bepaal of een andere actie inhoudelijk gelijk is.
     * @param andereActie andere actie
     * @return true, als de andere actie inhoudelijk gelijk is, anders false
     */
    public boolean isInhoudelijkGelijkAan(final BRPActie andereActie) {
        boolean result;
        if (this == andereActie) {
            result = true;
        } else {
            result = andereActie != null;
            result = result && komenDatumTijdstipRegistratieOvereen(andereActie);
            result = result && komenDatumOntleningOvereen(andereActie);
            result = result && komenPartijOvereen(andereActie);
            result = result && getSoortActie() == andereActie.getSoortActie();
            result = result && getDocumentSet().size() == andereActie.getDocumentSet().size();
            result = result && zijnDocumentenInhoudelijkGelijk(andereActie);
        }
        return result;
    }

    private boolean komenPartijOvereen(final BRPActie andereActie) {
        boolean resultaat;
        if (getPartij() == null) {
            resultaat = andereActie.getPartij() == null;
        } else {
            resultaat = getPartij().isInhoudelijkGelijkAan(andereActie.getPartij());
        }
        return resultaat;
    }

    private boolean komenDatumOntleningOvereen(final BRPActie andereActie) {
        boolean resultaat;
        if (getDatumOntlening() == null) {
            resultaat = andereActie.getDatumOntlening() == null;
        } else {
            resultaat = getDatumOntlening().equals(andereActie.getDatumOntlening());
        }
        return resultaat;
    }

    private boolean komenDatumTijdstipRegistratieOvereen(final BRPActie andereActie) {
        boolean resultaat;
        if (getDatumTijdRegistratie() == null) {
            resultaat = andereActie.getDatumTijdRegistratie() == null;
        } else {
            resultaat = getDatumTijdRegistratie().equals(andereActie.getDatumTijdRegistratie());
        }
        return resultaat;
    }

    private boolean zijnDocumentenInhoudelijkGelijk(final BRPActie andereActie) {
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("id", id).append("soortActie", getSoortActie())
                .append("administratieveHandeling", administratieveHandeling).append("partij", partij).append("datumTijdRegistratie", datumTijdRegistratie)
                .append("datumOntlening", datumOntlening).append("lo3Voorkomen", lo3Voorkomen).toString();
    }

    /**
     * Checks if is cat07 of13 actie.
     * @return true, if is cat07 of13 actie
     */
    public boolean isCat07Of13Actie() {
        return lo3Voorkomen != null && ("07".equals(lo3Voorkomen.getCategorie()) || isCat13Actie());
    }

    /**
     * Checks if is cat13 actie.
     * @return true, if is cat13 actie
     */
    public boolean isCat13Actie() {
        return lo3Voorkomen != null && "13".equals(lo3Voorkomen.getCategorie());
    }

}
