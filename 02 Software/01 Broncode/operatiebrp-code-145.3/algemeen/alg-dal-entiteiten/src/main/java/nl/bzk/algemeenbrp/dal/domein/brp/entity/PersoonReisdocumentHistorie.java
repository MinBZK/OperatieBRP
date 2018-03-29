/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

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
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the his_persreisdoc database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persreisdoc", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"persreisdoc", "tsreg"}))
public class PersoonReisdocumentHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persreisdoc_id_generator", sequenceName = "kern.seq_his_persreisdoc", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persreisdoc_id_generator")
    @Column(nullable = false)
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "persreisdoc", nullable = false)
    private PersoonReisdocument persoonReisdocument;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aandinhingvermissing")
    private AanduidingInhoudingOfVermissingReisdocument aanduidingInhoudingOfVermissingReisdocument;

    /**
     * JPA default constructor.
     */
    protected PersoonReisdocumentHistorie() {}

    /**
     * Maak een nieuwe persoon reisdocument historie.
     *
     * @param datumIngangDocument datum ingang document
     * @param datumUitgifte datum uitgifte
     * @param datumEindeDocument datum einde document
     * @param nummer nummer
     * @param autoriteitVanAfgifte autoriteit van afgifte
     * @param persoonReisdocument persoon reisdocument
     */
    public PersoonReisdocumentHistorie(final int datumIngangDocument, final int datumUitgifte, final int datumEindeDocument, final String nummer,
            final String autoriteitVanAfgifte, final PersoonReisdocument persoonReisdocument) {
        setDatumIngangDocument(datumIngangDocument);
        setDatumUitgifte(datumUitgifte);
        setDatumEindeDocument(datumEindeDocument);
        setNummer(nummer);
        setAutoriteitVanAfgifte(autoriteitVanAfgifte);
        setPersoonReisdocument(persoonReisdocument);
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
     * Zet de waarden voor id van PersoonReisdocumentHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonReisdocumentHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datum inhouding of vermissing van PersoonReisdocumentHistorie.
     *
     * @return de waarde van datum inhouding of vermissing van PersoonReisdocumentHistorie
     */
    public Integer getDatumInhoudingOfVermissing() {
        return datumInhoudingOfVermissing;
    }

    /**
     * Zet de waarden voor datum inhouding of vermissing van PersoonReisdocumentHistorie.
     *
     * @param datumInhoudingOfVermissing de nieuwe waarde voor datum inhouding of vermissing van
     *        PersoonReisdocumentHistorie
     */
    public void setDatumInhoudingOfVermissing(final Integer datumInhoudingOfVermissing) {
        this.datumInhoudingOfVermissing = datumInhoudingOfVermissing;
    }

    /**
     * Geef de waarde van datum ingang document van PersoonReisdocumentHistorie.
     *
     * @return de waarde van datum ingang document van PersoonReisdocumentHistorie
     */
    public int getDatumIngangDocument() {
        return datumIngangDocument;
    }

    /**
     * Zet de waarden voor datum ingang document van PersoonReisdocumentHistorie.
     *
     * @param datumIngangDocument de nieuwe waarde voor datum ingang document van
     *        PersoonReisdocumentHistorie
     */
    public void setDatumIngangDocument(final int datumIngangDocument) {
        this.datumIngangDocument = datumIngangDocument;
    }

    /**
     * Geef de waarde van datum uitgifte van PersoonReisdocumentHistorie.
     *
     * @return de waarde van datum uitgifte van PersoonReisdocumentHistorie
     */
    public int getDatumUitgifte() {
        return datumUitgifte;
    }

    /**
     * Zet de waarden voor datum uitgifte van PersoonReisdocumentHistorie.
     *
     * @param datumUitgifte de nieuwe waarde voor datum uitgifte van PersoonReisdocumentHistorie
     */
    public void setDatumUitgifte(final int datumUitgifte) {
        this.datumUitgifte = datumUitgifte;
    }

    /**
     * Geef de waarde van datum einde document van PersoonReisdocumentHistorie.
     *
     * @return de waarde van datum einde document van PersoonReisdocumentHistorie
     */
    public int getDatumEindeDocument() {
        return datumEindeDocument;
    }

    /**
     * Zet de waarden voor datum einde document van PersoonReisdocumentHistorie.
     *
     * @param datumEindeDocument de nieuwe waarde voor datum einde document van
     *        PersoonReisdocumentHistorie
     */
    public void setDatumEindeDocument(final int datumEindeDocument) {
        this.datumEindeDocument = datumEindeDocument;
    }

    /**
     * Geef de waarde van nummer van PersoonReisdocumentHistorie.
     *
     * @return de waarde van nummer van PersoonReisdocumentHistorie
     */
    public String getNummer() {
        return nummer;
    }

    /**
     * Zet de waarden voor nummer van PersoonReisdocumentHistorie.
     *
     * @param nummer de nieuwe waarde voor nummer van PersoonReisdocumentHistorie
     */
    public void setNummer(final String nummer) {
        ValidationUtils.controleerOpNullWaarden("nummer mag niet null zijn", nummer);
        ValidationUtils.controleerOpLegeWaarden("nummer mag geen lege string zijn", nummer);
        this.nummer = nummer;
    }

    /**
     * Geef de waarde van autoriteit van afgifte van PersoonReisdocumentHistorie.
     *
     * @return de waarde van autoriteit van afgifte van PersoonReisdocumentHistorie
     */
    public String getAutoriteitVanAfgifte() {
        return autoriteitVanAfgifte;
    }

    /**
     * Zet de waarden voor autoriteit van afgifte van PersoonReisdocumentHistorie.
     *
     * @param autoriteitVanAfgifte de nieuwe waarde voor autoriteit van afgifte van
     *        PersoonReisdocumentHistorie
     */
    public void setAutoriteitVanAfgifte(final String autoriteitVanAfgifte) {
        ValidationUtils.controleerOpNullWaarden("autoriteitVanAfgifte mag niet null zijn", autoriteitVanAfgifte);
        ValidationUtils.controleerOpLegeWaarden("autoriteitVanAfgifte mag geen lege string zijn", autoriteitVanAfgifte);
        this.autoriteitVanAfgifte = autoriteitVanAfgifte;
    }

    /**
     * Geef de waarde van persoon reisdocument van PersoonReisdocumentHistorie.
     *
     * @return de waarde van persoon reisdocument van PersoonReisdocumentHistorie
     */
    public PersoonReisdocument getPersoonReisdocument() {
        return persoonReisdocument;
    }

    /**
     * Zet de waarden voor persoon reisdocument van PersoonReisdocumentHistorie.
     *
     * @param persoonReisdocument de nieuwe waarde voor persoon reisdocument van
     *        PersoonReisdocumentHistorie
     */
    public void setPersoonReisdocument(final PersoonReisdocument persoonReisdocument) {
        ValidationUtils.controleerOpNullWaarden("persoonReisdocument mag niet null zijn", persoonReisdocument);
        this.persoonReisdocument = persoonReisdocument;
    }

    /**
     * Geef de waarde van aanduiding inhouding of vermissing reisdocument van
     * PersoonReisdocumentHistorie.
     *
     * @return de waarde van aanduiding inhouding of vermissing reisdocument van
     *         PersoonReisdocumentHistorie
     */
    public AanduidingInhoudingOfVermissingReisdocument getAanduidingInhoudingOfVermissingReisdocument() {
        return aanduidingInhoudingOfVermissingReisdocument;
    }

    /**
     * Zet de waarden voor aanduiding inhouding of vermissing reisdocument van
     * PersoonReisdocumentHistorie.
     *
     * @param aanduidingInhoudingOfVermissingReisdocument de nieuwe waarde voor aanduiding inhouding
     *        of vermissing reisdocument van PersoonReisdocumentHistorie
     */
    public void setAanduidingInhoudingOfVermissingReisdocument(final AanduidingInhoudingOfVermissingReisdocument aanduidingInhoudingOfVermissingReisdocument) {
        this.aanduidingInhoudingOfVermissingReisdocument = aanduidingInhoudingOfVermissingReisdocument;
    }
}
