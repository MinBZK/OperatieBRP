/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
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
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * The persistent class for the persreisdoc database table.
 * 
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "persreisdoc", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"id" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonReisdocument extends AbstractDeltaEntiteit implements ALaagHistorieVerzameling, Serializable {
    /**
     * Label tbv delta voor uniciteit {@link EntiteitSleutel}.
     */
    public static final String TECHNISCH_ID = "technischId";

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "persreisdoc_id_generator", sequenceName = "kern.seq_persreisdoc", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persreisdoc_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "datinhingvermissing")
    private Integer datumInhoudingOfVermissing;

    @Column(name = "datingangdoc")
    private Integer datumIngangDocument;

    @Column(name = "datuitgifte")
    private Integer datumUitgifte;

    @Column(name = "dateindedoc")
    private Integer datumEindeDocument;

    @Column(name = "autvanafgifte", length = 6)
    private String autoriteitVanAfgifte;

    @Column(name = "nr", length = 9)
    private String nummer;

    // bi-directional many-to-one association to PersoonReisdocumentHistorie
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonReisdocument", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonReisdocumentHistorie> persoonReisdocumentHistorieSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "aandinhingvermissing")
    private AanduidingInhoudingOfVermissingReisdocument aanduidingInhoudingOfVermissingReisdocument;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "srt", nullable = false)
    private SoortNederlandsReisdocument soortNederlandsReisdocument;

    /**
     * JPA default constructor.
     */
    protected PersoonReisdocument() {
    }

    /**
     * Maak een nieuwe persoon reisdocument.
     *
     * @param persoon
     *            persoon
     * @param soortNederlandsReisdocument
     *            soort nederlands reisdocument
     */
    public PersoonReisdocument(final Persoon persoon, final SoortNederlandsReisdocument soortNederlandsReisdocument) {
        setPersoon(persoon);
        setSoortNederlandsReisdocument(soortNederlandsReisdocument);
    }

    /**
     * Geef de Id waarde voor de entiteit. Intern is de Id een Long voor integratie met GegevenInOnderzoek. De waarde
     * wordt geconverteerd naar een Integer.
     *
     * @return de Id waarde.
     */
    public Integer getId() {
        return convertLongNaarInteger(id);
    }

    /**
     * Zet de Id waarde voor de entiteit. Intern wordt de Id waarde geconverteert naar een Long voor integratie met
     * GegevenInOnderzoek.
     *
     * @param id
     *            de Id waarde.
     */
    public void setId(final Integer id) {
        this.id = convertIntegerNaarLong(id);
    }

    /**
     * Geef de waarde van datum inhouding of vermissing.
     *
     * @return datum inhouding of vermissing
     */
    public Integer getDatumInhoudingOfVermissing() {
        return datumInhoudingOfVermissing;
    }

    /**
     * Zet de waarde van datum inhouding of vermissing.
     *
     * @param datumInhoudingOfVermissing
     *            datum inhouding of vermissing
     */
    public void setDatumInhoudingOfVermissing(final Integer datumInhoudingOfVermissing) {
        this.datumInhoudingOfVermissing = datumInhoudingOfVermissing;
    }

    /**
     * Geef de waarde van datum ingang document.
     *
     * @return datum ingang document
     */
    public Integer getDatumIngangDocument() {
        return datumIngangDocument;
    }

    /**
     * Zet de waarde van datum ingang document.
     *
     * @param datumIngangDocument
     *            datum ingang document
     */
    public void setDatumIngangDocument(final Integer datumIngangDocument) {
        this.datumIngangDocument = datumIngangDocument;
    }

    /**
     * Geef de waarde van datum uitgifte.
     *
     * @return datum uitgifte
     */
    public Integer getDatumUitgifte() {
        return datumUitgifte;
    }

    /**
     * Zet de waarde van datum uitgifte.
     *
     * @param datumUitgifte
     *            datum uitgifte
     */
    public void setDatumUitgifte(final Integer datumUitgifte) {
        this.datumUitgifte = datumUitgifte;
    }

    /**
     * Geef de waarde van datum einde document.
     *
     * @return datum einde document
     */
    public Integer getDatumEindeDocument() {
        return datumEindeDocument;
    }

    /**
     * Zet de waarde van datum einde document.
     *
     * @param datumEindeDocument
     *            datum einde document
     */
    public void setDatumEindeDocument(final Integer datumEindeDocument) {
        this.datumEindeDocument = datumEindeDocument;
    }

    /**
     * Geef de waarde van nummer.
     *
     * @return nummer
     */
    public String getNummer() {
        return nummer;
    }

    /**
     * Zet de waarde van nummer.
     *
     * @param nummer
     *            nummer
     */
    public void setNummer(final String nummer) {
        Validatie.controleerOpLegeWaarden("nummer mag geen lege string zijn", nummer);
        this.nummer = nummer;
    }

    /**
     * Geef de waarde van persoon reisdocument historie set.
     *
     * @return persoon reisdocument historie set
     */
    public Set<PersoonReisdocumentHistorie> getPersoonReisdocumentHistorieSet() {
        return persoonReisdocumentHistorieSet;
    }

    /**
     * Toevoegen van een persoon reisdocument historie set.
     *
     * @param persoonReisdocumentHistorie
     *            persoon reisdocument historie
     */
    public void addPersoonReisdocumentHistorieSet(final PersoonReisdocumentHistorie persoonReisdocumentHistorie) {
        persoonReisdocumentHistorie.setPersoonReisdocument(this);
        persoonReisdocumentHistorieSet.add(persoonReisdocumentHistorie);
    }

    /**
     * Geef de waarde van autoriteit van afgifte.
     *
     * @return autoriteit van afgifte
     */
    public String getAutoriteitVanAfgifte() {
        return autoriteitVanAfgifte;
    }

    /**
     * Zet de waarde van autoriteit van afgifte.
     *
     * @param autoriteitVanAfgifte
     *            autoriteit van afgifte
     */
    public void setAutoriteitVanAfgifte(final String autoriteitVanAfgifte) {
        Validatie.controleerOpLegeWaarden("autoriteitVanAfgifte mag geen lege string zijn", autoriteitVanAfgifte);
        this.autoriteitVanAfgifte = autoriteitVanAfgifte;
    }

    /**
     * Geef de waarde van persoon.
     *
     * @return persoon
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarde van persoon.
     *
     * @param persoon
     *            persoon
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van aanduiding inhouding of vermissing reisdocument.
     *
     * @return aanduiding inhouding of vermissing reisdocument
     */
    public AanduidingInhoudingOfVermissingReisdocument getAanduidingInhoudingOfVermissingReisdocument() {
        return aanduidingInhoudingOfVermissingReisdocument;
    }

    /**
     * Zet de waarde van aanduiding inhouding of vermissing reisdocument.
     *
     * @param aanduidingInhoudingOfVermissingReisdocument
     *            aanduiding inhouding of vermissing reisdocument
     */
    public void setAanduidingInhoudingOfVermissingReisdocument(
        final AanduidingInhoudingOfVermissingReisdocument aanduidingInhoudingOfVermissingReisdocument)
    {
        this.aanduidingInhoudingOfVermissingReisdocument = aanduidingInhoudingOfVermissingReisdocument;
    }

    /**
     * Geef de waarde van soort nederlands reisdocument.
     *
     * @return soort nederlands reisdocument
     */
    public SoortNederlandsReisdocument getSoortNederlandsReisdocument() {
        return soortNederlandsReisdocument;
    }

    /**
     * Zet de waarde van soort nederlands reisdocument.
     *
     * @param soortNederlandsReisdocument
     *            soort nederlands reisdocument
     */
    public void setSoortNederlandsReisdocument(final SoortNederlandsReisdocument soortNederlandsReisdocument) {
        ValidationUtils.controleerOpNullWaarden("soortNederlandsReisdocument mag niet null zijn", soortNederlandsReisdocument);
        this.soortNederlandsReisdocument = soortNederlandsReisdocument;
    }

    @Override
    public Map<String, Collection<? extends FormeleHistorie>> verzamelHistorie() {
        final Map<String, Collection<? extends FormeleHistorie>> result = new HashMap<>();
        result.put("persoonReisdocumentHistorieSet", Collections.unmodifiableSet(persoonReisdocumentHistorieSet));
        return result;
    }

    /**
     * Is een match als de volgende velden overeenkomen:
     * <UL>
     * <LI>soort</LI>
     * <LI>nummer</LI>
     * <LI>autoriteit van afgifte</LI>
     * <LI>datum einde document</LI>
     * <LI>datum uitgifte overeenkomen</LI>
     * </UL>.
     * 
     * @param nieuwReisdocument
     *            het document waarmee vergeleken wordt.
     * @return true als de beschreven velden overeenkomen.
     */
    public final boolean isMatchMet(final PersoonReisdocument nieuwReisdocument) {
        final EqualsBuilder equalsBuilder = new EqualsBuilder();
        equalsBuilder.append(soortNederlandsReisdocument, nieuwReisdocument.soortNederlandsReisdocument)
                     .append(nummer, nieuwReisdocument.nummer)
                     .append(autoriteitVanAfgifte, nieuwReisdocument.autoriteitVanAfgifte)
                     .append(datumEindeDocument, nieuwReisdocument.datumEindeDocument)
                     .append(datumUitgifte, nieuwReisdocument.datumUitgifte);
        return equalsBuilder.isEquals();
    }

    /**
     * Is een match als de volgende velden overeenkomen:
     * <UL>
     * <LI>soort</LI>
     * <LI>nummer</LI>
     * <LI>autoriteit van afgifte</LI>
     * <LI>datum einde document</LI>
     * <LI>datum uitgifte overeenkomen</LI>
     * <LI>datum ingang document</LI>
     * <LI>datum inhouding of vermissing</LI>
     * <LI>aanduiding inhouding of vermissing</LI>
     * </UL>.
     *
     * @param nieuwReisdocument
     *            het document waarmee vergeleken wordt.
     * @return true als de beschreven velden overeenkomen.
     */
    public final boolean isVolledigeMatchMet(final PersoonReisdocument nieuwReisdocument) {
        final EqualsBuilder equalsBuilder = new EqualsBuilder();
        equalsBuilder.append(datumInhoudingOfVermissing, nieuwReisdocument.datumInhoudingOfVermissing)
                     .append(aanduidingInhoudingOfVermissingReisdocument, nieuwReisdocument.aanduidingInhoudingOfVermissingReisdocument)
                     .append(datumIngangDocument, nieuwReisdocument.datumIngangDocument);
        return isMatchMet(nieuwReisdocument) && equalsBuilder.isEquals();
    }
}
