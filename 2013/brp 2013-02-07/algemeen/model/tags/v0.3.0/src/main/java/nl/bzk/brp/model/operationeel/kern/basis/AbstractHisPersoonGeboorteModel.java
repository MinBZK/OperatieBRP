/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaats;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegio;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieOmschrijving;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieEntiteit;
import nl.bzk.brp.model.logisch.kern.basis.PersoonGeboorteGroepBasis;
import nl.bzk.brp.model.operationeel.kern.PersoonGeboorteGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisPersoonGeboorteModel extends AbstractFormeleHistorieEntiteit implements
        PersoonGeboorteGroepBasis
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONGEBOORTE", sequenceName = "Kern.seq_His_PersGeboorte")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONGEBOORTE")
    @JsonProperty
    private Integer             iD;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel        persoon;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatGeboorte"))
    @JsonProperty
    private Datum               datumGeboorte;

    @ManyToOne
    @JoinColumn(name = "GemGeboorte")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Partij              gemeenteGeboorte;

    @ManyToOne
    @JoinColumn(name = "WplGeboorte")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Plaats              woonplaatsGeboorte;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLPlaatsGeboorte"))
    @JsonProperty
    private BuitenlandsePlaats  buitenlandsePlaatsGeboorte;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLRegioGeboorte"))
    @JsonProperty
    private BuitenlandseRegio   buitenlandseRegioGeboorte;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "OmsLocGeboorte"))
    @JsonProperty
    private LocatieOmschrijving omschrijvingLocatieGeboorte;

    @ManyToOne
    @JoinColumn(name = "LandGeboorte")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Land                landGeboorte;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPersoonGeboorteModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisPersoonGeboorteModel(final PersoonModel persoonModel, final PersoonGeboorteGroepModel groep) {
        this.persoon = persoonModel;
        this.datumGeboorte = groep.getDatumGeboorte();
        this.gemeenteGeboorte = groep.getGemeenteGeboorte();
        this.woonplaatsGeboorte = groep.getWoonplaatsGeboorte();
        this.buitenlandsePlaatsGeboorte = groep.getBuitenlandsePlaatsGeboorte();
        this.buitenlandseRegioGeboorte = groep.getBuitenlandseRegioGeboorte();
        this.omschrijvingLocatieGeboorte = groep.getOmschrijvingLocatieGeboorte();
        this.landGeboorte = groep.getLandGeboorte();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisPersoonGeboorteModel(final AbstractHisPersoonGeboorteModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        datumGeboorte = kopie.getDatumGeboorte();
        gemeenteGeboorte = kopie.getGemeenteGeboorte();
        woonplaatsGeboorte = kopie.getWoonplaatsGeboorte();
        buitenlandsePlaatsGeboorte = kopie.getBuitenlandsePlaatsGeboorte();
        buitenlandseRegioGeboorte = kopie.getBuitenlandseRegioGeboorte();
        omschrijvingLocatieGeboorte = kopie.getOmschrijvingLocatieGeboorte();
        landGeboorte = kopie.getLandGeboorte();

    }

    /**
     * Retourneert ID van His Persoon Geboorte.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Geboorte.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Datum geboorte van His Persoon Geboorte.
     *
     * @return Datum geboorte.
     */
    public Datum getDatumGeboorte() {
        return datumGeboorte;
    }

    /**
     * Retourneert Gemeente geboorte van His Persoon Geboorte.
     *
     * @return Gemeente geboorte.
     */
    public Partij getGemeenteGeboorte() {
        return gemeenteGeboorte;
    }

    /**
     * Retourneert Woonplaats geboorte van His Persoon Geboorte.
     *
     * @return Woonplaats geboorte.
     */
    public Plaats getWoonplaatsGeboorte() {
        return woonplaatsGeboorte;
    }

    /**
     * Retourneert Buitenlandse plaats geboorte van His Persoon Geboorte.
     *
     * @return Buitenlandse plaats geboorte.
     */
    public BuitenlandsePlaats getBuitenlandsePlaatsGeboorte() {
        return buitenlandsePlaatsGeboorte;
    }

    /**
     * Retourneert Buitenlandse regio geboorte van His Persoon Geboorte.
     *
     * @return Buitenlandse regio geboorte.
     */
    public BuitenlandseRegio getBuitenlandseRegioGeboorte() {
        return buitenlandseRegioGeboorte;
    }

    /**
     * Retourneert Omschrijving locatie geboorte van His Persoon Geboorte.
     *
     * @return Omschrijving locatie geboorte.
     */
    public LocatieOmschrijving getOmschrijvingLocatieGeboorte() {
        return omschrijvingLocatieGeboorte;
    }

    /**
     * Retourneert Land geboorte van His Persoon Geboorte.
     *
     * @return Land geboorte.
     */
    public Land getLandGeboorte() {
        return landGeboorte;
    }

}
