/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

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
import nl.bzk.algemeenbrp.dal.domein.brp.annotation.IndicatieActueelEnGeldig;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * The persistent class for the persreisdoc database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "persreisdoc", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class PersoonReisdocument extends AbstractEntiteit implements SubRootEntiteit, Serializable {
    /**
     * Label om het veld voor het technischID uit te lezen.
     */
    public static final String TECHNISCH_ID = "technischId";

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "persreisdoc_id_generator", sequenceName = "kern.seq_persreisdoc", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persreisdoc_id_generator")
    @Column(nullable = false)
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

    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;

    // bi-directional many-to-one association to PersoonReisdocumentHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonReisdocument", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonReisdocumentHistorie> persoonReisdocumentHistorieSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aandinhingvermissing")
    private AanduidingInhoudingOfVermissingReisdocument aanduidingInhoudingOfVermissingReisdocument;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "srt", nullable = false)
    private SoortNederlandsReisdocument soortNederlandsReisdocument;

    /**
     * JPA default constructor.
     */
    protected PersoonReisdocument() {}

    /**
     * Maak een nieuwe persoon reisdocument.
     *
     * @param persoon persoon
     * @param soortNederlandsReisdocument soort nederlands reisdocument
     */
    public PersoonReisdocument(final Persoon persoon, final SoortNederlandsReisdocument soortNederlandsReisdocument) {
        setPersoon(persoon);
        setSoortNederlandsReisdocument(soortNederlandsReisdocument);
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
     * Zet de waarden voor id van PersoonReisdocument.
     *
     * @param id de nieuwe waarde voor id van PersoonReisdocument
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datum inhouding of vermissing van PersoonReisdocument.
     *
     * @return de waarde van datum inhouding of vermissing van PersoonReisdocument
     */
    public Integer getDatumInhoudingOfVermissing() {
        return datumInhoudingOfVermissing;
    }

    /**
     * Zet de waarden voor datum inhouding of vermissing van PersoonReisdocument.
     *
     * @param datumInhoudingOfVermissing de nieuwe waarde voor datum inhouding of vermissing van
     *        PersoonReisdocument
     */
    public void setDatumInhoudingOfVermissing(final Integer datumInhoudingOfVermissing) {
        this.datumInhoudingOfVermissing = datumInhoudingOfVermissing;
    }

    /**
     * Geef de waarde van datum ingang document van PersoonReisdocument.
     *
     * @return de waarde van datum ingang document van PersoonReisdocument
     */
    public Integer getDatumIngangDocument() {
        return datumIngangDocument;
    }

    /**
     * Zet de waarden voor datum ingang document van PersoonReisdocument.
     *
     * @param datumIngangDocument de nieuwe waarde voor datum ingang document van
     *        PersoonReisdocument
     */
    public void setDatumIngangDocument(final Integer datumIngangDocument) {
        this.datumIngangDocument = datumIngangDocument;
    }

    /**
     * Geef de waarde van datum uitgifte van PersoonReisdocument.
     *
     * @return de waarde van datum uitgifte van PersoonReisdocument
     */
    public Integer getDatumUitgifte() {
        return datumUitgifte;
    }

    /**
     * Zet de waarden voor datum uitgifte van PersoonReisdocument.
     *
     * @param datumUitgifte de nieuwe waarde voor datum uitgifte van PersoonReisdocument
     */
    public void setDatumUitgifte(final Integer datumUitgifte) {
        this.datumUitgifte = datumUitgifte;
    }

    /**
     * Geef de waarde van datum einde document van PersoonReisdocument.
     *
     * @return de waarde van datum einde document van PersoonReisdocument
     */
    public Integer getDatumEindeDocument() {
        return datumEindeDocument;
    }

    /**
     * Zet de waarden voor datum einde document van PersoonReisdocument.
     *
     * @param datumEindeDocument de nieuwe waarde voor datum einde document van PersoonReisdocument
     */
    public void setDatumEindeDocument(final Integer datumEindeDocument) {
        this.datumEindeDocument = datumEindeDocument;
    }

    /**
     * Geef de waarde van nummer van PersoonReisdocument.
     *
     * @return de waarde van nummer van PersoonReisdocument
     */
    public String getNummer() {
        return nummer;
    }

    /**
     * Zet de waarden voor nummer van PersoonReisdocument.
     *
     * @param nummer de nieuwe waarde voor nummer van PersoonReisdocument
     */
    public void setNummer(final String nummer) {
        ValidationUtils.controleerOpLegeWaarden("nummer mag geen lege string zijn", nummer);
        this.nummer = nummer;
    }

    /**
     * Geef de waarde van isActueelEnGeldig.
     *
     * @return isActueelEnGeldig
     */
    public boolean isActueelEnGeldig() {
        return isActueelEnGeldig;
    }

    /**
     * Zet de waarde van isActueelEnGeldig.
     *
     * @param actueelEnGeldig isActueelEnGeldig
     */
    public void setActueelEnGeldig(final boolean actueelEnGeldig) {
        isActueelEnGeldig = actueelEnGeldig;
    }

    /**
     * Geef de waarde van persoon reisdocument historie set van PersoonReisdocument.
     *
     * @return de waarde van persoon reisdocument historie set van PersoonReisdocument
     */
    public Set<PersoonReisdocumentHistorie> getPersoonReisdocumentHistorieSet() {
        return persoonReisdocumentHistorieSet;
    }

    /**
     * Toevoegen van een persoon reisdocument historie set.
     *
     * @param persoonReisdocumentHistorie persoon reisdocument historie
     */
    public void addPersoonReisdocumentHistorieSet(final PersoonReisdocumentHistorie persoonReisdocumentHistorie) {
        persoonReisdocumentHistorie.setPersoonReisdocument(this);
        persoonReisdocumentHistorieSet.add(persoonReisdocumentHistorie);
    }

    /**
     * Geef de waarde van autoriteit van afgifte van PersoonReisdocument.
     *
     * @return de waarde van autoriteit van afgifte van PersoonReisdocument
     */
    public String getAutoriteitVanAfgifte() {
        return autoriteitVanAfgifte;
    }

    /**
     * Zet de waarden voor autoriteit van afgifte van PersoonReisdocument.
     *
     * @param autoriteitVanAfgifte de nieuwe waarde voor autoriteit van afgifte van
     *        PersoonReisdocument
     */
    public void setAutoriteitVanAfgifte(final String autoriteitVanAfgifte) {
        ValidationUtils.controleerOpLegeWaarden("autoriteitVanAfgifte mag geen lege string zijn", autoriteitVanAfgifte);
        this.autoriteitVanAfgifte = autoriteitVanAfgifte;
    }

    /**
     * Geef de waarde van persoon van PersoonReisdocument.
     *
     * @return de waarde van persoon van PersoonReisdocument
     */
    @Override
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van PersoonReisdocument.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonReisdocument
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van aanduiding inhouding of vermissing reisdocument van PersoonReisdocument.
     *
     * @return de waarde van aanduiding inhouding of vermissing reisdocument van PersoonReisdocument
     */
    public AanduidingInhoudingOfVermissingReisdocument getAanduidingInhoudingOfVermissingReisdocument() {
        return aanduidingInhoudingOfVermissingReisdocument;
    }

    /**
     * Zet de waarden voor aanduiding inhouding of vermissing reisdocument van PersoonReisdocument.
     *
     * @param aanduidingInhoudingOfVermissingReisdocument de nieuwe waarde voor aanduiding inhouding
     *        of vermissing reisdocument van PersoonReisdocument
     */
    public void setAanduidingInhoudingOfVermissingReisdocument(final AanduidingInhoudingOfVermissingReisdocument aanduidingInhoudingOfVermissingReisdocument) {
        this.aanduidingInhoudingOfVermissingReisdocument = aanduidingInhoudingOfVermissingReisdocument;
    }

    /**
     * Geef de waarde van soort nederlands reisdocument van PersoonReisdocument.
     *
     * @return de waarde van soort nederlands reisdocument van PersoonReisdocument
     */
    public SoortNederlandsReisdocument getSoortNederlandsReisdocument() {
        return soortNederlandsReisdocument;
    }

    /**
     * Zet de waarden voor soort nederlands reisdocument van PersoonReisdocument.
     *
     * @param soortNederlandsReisdocument de nieuwe waarde voor soort nederlands reisdocument van
     *        PersoonReisdocument
     */
    public void setSoortNederlandsReisdocument(final SoortNederlandsReisdocument soortNederlandsReisdocument) {
        ValidationUtils.controleerOpNullWaarden("soortNederlandsReisdocument mag niet null zijn", soortNederlandsReisdocument);
        this.soortNederlandsReisdocument = soortNederlandsReisdocument;
    }

    @Override
    public Map<String, Collection<FormeleHistorie>> verzamelHistorie() {
        final Map<String, Collection<FormeleHistorie>> result = new HashMap<>();
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
     * </UL>
     * .
     *
     * @param anderReisdocument het document waarmee vergeleken wordt.
     * @return true als de beschreven velden overeenkomen.
     */
    public final boolean isMatchMet(final PersoonReisdocument anderReisdocument) {
        final PersoonReisdocumentHistorie historie = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getPersoonReisdocumentHistorieSet());
        final PersoonReisdocumentHistorie anderReisdocumenthistorie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(anderReisdocument.getPersoonReisdocumentHistorieSet());

        final EqualsBuilder equalsBuilder = new EqualsBuilder();
        equalsBuilder.append(historie.getNummer(), anderReisdocumenthistorie.getNummer())
                .append(historie.getAutoriteitVanAfgifte(), anderReisdocumenthistorie.getAutoriteitVanAfgifte())
                .append(historie.getDatumEindeDocument(), anderReisdocumenthistorie.getDatumEindeDocument())
                .append(historie.getDatumUitgifte(), anderReisdocumenthistorie.getDatumUitgifte());
        return soortNederlandsReisdocument.isInhoudelijkGelijkAan(anderReisdocument.soortNederlandsReisdocument) && equalsBuilder.isEquals();
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
     * </UL>
     * .
     *
     * @param anderReisdocument het document waarmee vergeleken wordt.
     * @return true als de beschreven velden overeenkomen.
     */
    public final boolean isVolledigeMatchMet(final PersoonReisdocument anderReisdocument) {
        final PersoonReisdocumentHistorie historie = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getPersoonReisdocumentHistorieSet());
        final PersoonReisdocumentHistorie anderReisdocumenthistorie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(anderReisdocument.getPersoonReisdocumentHistorieSet());

        final EqualsBuilder equalsBuilder = new EqualsBuilder();
        equalsBuilder.append(historie.getDatumInhoudingOfVermissing(), anderReisdocumenthistorie.getDatumInhoudingOfVermissing())
                .append(historie.getAanduidingInhoudingOfVermissingReisdocument(), anderReisdocumenthistorie.getAanduidingInhoudingOfVermissingReisdocument())
                .append(historie.getDatumIngangDocument(), anderReisdocumenthistorie.getDatumIngangDocument());
        return isMatchMet(anderReisdocument) && equalsBuilder.isEquals();
    }

    /**
     * @return true als deze groep alleen M-rijen bevat, anders false
     */
    public boolean bevatAlleenMrijen() {
        boolean result = true;
        for (final FormeleHistorie historie : getPersoonReisdocumentHistorieSet()) {
            if (historie.getIndicatieVoorkomenTbvLeveringMutaties() == null) {
                result = false;
                break;
            }
        }
        return result;
    }
}
