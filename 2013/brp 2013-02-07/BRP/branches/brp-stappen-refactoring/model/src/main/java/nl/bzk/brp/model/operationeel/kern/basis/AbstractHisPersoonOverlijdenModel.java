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
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonOverlijdenGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisPersoonOverlijdenModel extends AbstractFormeleHistorieEntiteit {

    @Id
    @SequenceGenerator(name = "HIS_PERSOONOVERLIJDEN", sequenceName = "Kern.seq_His_PersOverlijden")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONOVERLIJDEN")
    @JsonProperty
    private Integer             iD;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel        persoon;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatOverlijden"))
    @JsonProperty
    private Datum               datumOverlijden;

    @ManyToOne
    @JoinColumn(name = "GemOverlijden")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Partij              gemeenteOverlijden;

    @ManyToOne
    @JoinColumn(name = "WplOverlijden")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Plaats              woonplaatsOverlijden;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLPlaatsOverlijden"))
    @JsonProperty
    private BuitenlandsePlaats  buitenlandsePlaatsOverlijden;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLRegioOverlijden"))
    @JsonProperty
    private BuitenlandseRegio   buitenlandseRegioOverlijden;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "OmsLocOverlijden"))
    @JsonProperty
    private LocatieOmschrijving omschrijvingLocatieOverlijden;

    @ManyToOne
    @JoinColumn(name = "LandOverlijden")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Land                landOverlijden;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPersoonOverlijdenModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisPersoonOverlijdenModel(final PersoonModel persoonModel, final PersoonOverlijdenGroepModel groep) {
        this.persoon = persoonModel;
        this.datumOverlijden = groep.getDatumOverlijden();
        this.gemeenteOverlijden = groep.getGemeenteOverlijden();
        this.woonplaatsOverlijden = groep.getWoonplaatsOverlijden();
        this.buitenlandsePlaatsOverlijden = groep.getBuitenlandsePlaatsOverlijden();
        this.buitenlandseRegioOverlijden = groep.getBuitenlandseRegioOverlijden();
        this.omschrijvingLocatieOverlijden = groep.getOmschrijvingLocatieOverlijden();
        this.landOverlijden = groep.getLandOverlijden();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisPersoonOverlijdenModel(final AbstractHisPersoonOverlijdenModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        datumOverlijden = kopie.getDatumOverlijden();
        gemeenteOverlijden = kopie.getGemeenteOverlijden();
        woonplaatsOverlijden = kopie.getWoonplaatsOverlijden();
        buitenlandsePlaatsOverlijden = kopie.getBuitenlandsePlaatsOverlijden();
        buitenlandseRegioOverlijden = kopie.getBuitenlandseRegioOverlijden();
        omschrijvingLocatieOverlijden = kopie.getOmschrijvingLocatieOverlijden();
        landOverlijden = kopie.getLandOverlijden();

    }

    /**
     * Retourneert ID van His Persoon Overlijden.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Overlijden.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Datum overlijden van His Persoon Overlijden.
     *
     * @return Datum overlijden.
     */
    public Datum getDatumOverlijden() {
        return datumOverlijden;
    }

    /**
     * Retourneert Gemeente overlijden van His Persoon Overlijden.
     *
     * @return Gemeente overlijden.
     */
    public Partij getGemeenteOverlijden() {
        return gemeenteOverlijden;
    }

    /**
     * Retourneert Woonplaats overlijden van His Persoon Overlijden.
     *
     * @return Woonplaats overlijden.
     */
    public Plaats getWoonplaatsOverlijden() {
        return woonplaatsOverlijden;
    }

    /**
     * Retourneert Buitenlandse plaats overlijden van His Persoon Overlijden.
     *
     * @return Buitenlandse plaats overlijden.
     */
    public BuitenlandsePlaats getBuitenlandsePlaatsOverlijden() {
        return buitenlandsePlaatsOverlijden;
    }

    /**
     * Retourneert Buitenlandse regio overlijden van His Persoon Overlijden.
     *
     * @return Buitenlandse regio overlijden.
     */
    public BuitenlandseRegio getBuitenlandseRegioOverlijden() {
        return buitenlandseRegioOverlijden;
    }

    /**
     * Retourneert Omschrijving locatie overlijden van His Persoon Overlijden.
     *
     * @return Omschrijving locatie overlijden.
     */
    public LocatieOmschrijving getOmschrijvingLocatieOverlijden() {
        return omschrijvingLocatieOverlijden;
    }

    /**
     * Retourneert Land overlijden van His Persoon Overlijden.
     *
     * @return Land overlijden.
     */
    public Land getLandOverlijden() {
        return landOverlijden;
    }

}
