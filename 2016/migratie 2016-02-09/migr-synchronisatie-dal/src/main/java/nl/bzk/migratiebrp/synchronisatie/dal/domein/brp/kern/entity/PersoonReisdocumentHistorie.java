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
 * The persistent class for the his_persreisdoc database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persreisdoc", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"persreisdoc", "tsreg" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonReisdocumentHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persreisdoc_id_generator", sequenceName = "kern.seq_his_persreisdoc", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persreisdoc_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "datinhingvermissing")
    private Integer datumInhoudingOfVermissing;

    @Column(name = "datingangdoc", nullable = false)
    private int datumIngangDocument;

    @Column(name = "datuitgifte", nullable = false)
    private int datumUitgifte;

    @Column(name = "dateindedoc", nullable = false)
    private int datumEindeDocument;

    @Column(name = "nr", nullable = false, length = 9)
    private String nummer;

    @Column(name = "autvanafgifte", length = 6, nullable = false)
    private String autoriteitVanAfgifte;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "persreisdoc", nullable = false)
    private PersoonReisdocument persoonReisdocument;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "aandinhingvermissing")
    private AanduidingInhoudingOfVermissingReisdocument aanduidingInhoudingOfVermissingReisdocument;

    /**
     * JPA default constructor.
     */
    protected PersoonReisdocumentHistorie() {
    }

    /**
     * Maak een nieuwe persoon reisdocument historie.
     *
     * @param datumIngangDocument
     *            datum ingang document
     * @param datumUitgifte
     *            datum uitgifte
     * @param datumEindeDocument
     *            datum einde document
     * @param nummer
     *            nummer
     * @param autoriteitVanAfgifte
     *            autoriteit van afgifte
     * @param persoonReisdocument
     *            persoon reisdocument
     */
    public PersoonReisdocumentHistorie(
        final int datumIngangDocument,
        final int datumUitgifte,
        final int datumEindeDocument,
        final String nummer,
        final String autoriteitVanAfgifte,
        final PersoonReisdocument persoonReisdocument)
    {
        setDatumIngangDocument(datumIngangDocument);
        setDatumUitgifte(datumUitgifte);
        setDatumEindeDocument(datumEindeDocument);
        setNummer(nummer);
        setAutoriteitVanAfgifte(autoriteitVanAfgifte);
        setPersoonReisdocument(persoonReisdocument);
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
    public int getDatumIngangDocument() {
        return datumIngangDocument;
    }

    /**
     * Zet de waarde van datum ingang document.
     *
     * @param datumIngangDocument
     *            datum ingang document
     */
    public void setDatumIngangDocument(final int datumIngangDocument) {
        this.datumIngangDocument = datumIngangDocument;
    }

    /**
     * Geef de waarde van datum uitgifte.
     *
     * @return datum uitgifte
     */
    public int getDatumUitgifte() {
        return datumUitgifte;
    }

    /**
     * Zet de waarde van datum uitgifte.
     *
     * @param datumUitgifte
     *            datum uitgifte
     */
    public void setDatumUitgifte(final int datumUitgifte) {
        this.datumUitgifte = datumUitgifte;
    }

    /**
     * Geef de waarde van datum einde document.
     *
     * @return datum einde document
     */
    public int getDatumEindeDocument() {
        return datumEindeDocument;
    }

    /**
     * Zet de waarde van datum einde document.
     *
     * @param datumEindeDocument
     *            datum einde document
     */
    public void setDatumEindeDocument(final int datumEindeDocument) {
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
        ValidationUtils.controleerOpNullWaarden("nummer mag niet null zijn", nummer);
        Validatie.controleerOpLegeWaarden("nummer mag geen lege string zijn", nummer);
        this.nummer = nummer;
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
        ValidationUtils.controleerOpNullWaarden("autoriteitVanAfgifte mag niet null zijn", autoriteitVanAfgifte);
        Validatie.controleerOpLegeWaarden("autoriteitVanAfgifte mag geen lege string zijn", autoriteitVanAfgifte);
        this.autoriteitVanAfgifte = autoriteitVanAfgifte;
    }

    /**
     * Geef de waarde van persoon reisdocument.
     *
     * @return persoon reisdocument
     */
    public PersoonReisdocument getPersoonReisdocument() {
        return persoonReisdocument;
    }

    /**
     * Zet de waarde van persoon reisdocument.
     *
     * @param persoonReisdocument
     *            persoon reisdocument
     */
    public void setPersoonReisdocument(final PersoonReisdocument persoonReisdocument) {
        ValidationUtils.controleerOpNullWaarden("persoonReisdocument mag niet null zijn", persoonReisdocument);
        this.persoonReisdocument = persoonReisdocument;
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
}
