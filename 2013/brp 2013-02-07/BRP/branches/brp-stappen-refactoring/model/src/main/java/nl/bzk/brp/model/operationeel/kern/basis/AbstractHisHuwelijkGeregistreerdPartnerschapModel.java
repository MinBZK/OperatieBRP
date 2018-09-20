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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenBeeindigingRelatie;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieEntiteit;
import nl.bzk.brp.model.operationeel.kern.HuwelijkGeregistreerdPartnerschapModel;
import nl.bzk.brp.model.operationeel.kern.HuwelijkGeregistreerdPartnerschapStandaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;
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
public abstract class AbstractHisHuwelijkGeregistreerdPartnerschapModel extends AbstractFormeleHistorieEntiteit {

    @Id
    @SequenceGenerator(name = "HIS_HUWELIJKGEREGISTREERDPARTNERSCHAP",
                       sequenceName = "Kern.seq_His_HuwelijkGeregistreerdPar")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_HUWELIJKGEREGISTREERDPARTNERSCHAP")
    @JsonProperty
    private Integer                 iD;

    @ManyToOne
    @JoinColumn(name = "Relatie")
    @JsonProperty
    private RelatieModel            relatie;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatAanv"))
    @JsonProperty
    private Datum                   datumAanvang;

    @ManyToOne
    @JoinColumn(name = "GemAanv")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Partij                  gemeenteAanvang;

    @ManyToOne
    @JoinColumn(name = "WplAanv")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Plaats                  woonplaatsAanvang;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLPlaatsAanv"))
    @JsonProperty
    private BuitenlandsePlaats      buitenlandsePlaatsAanvang;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLRegioAanv"))
    @JsonProperty
    private BuitenlandseRegio       buitenlandseRegioAanvang;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "OmsLocAanv"))
    @JsonProperty
    private LocatieOmschrijving     omschrijvingLocatieAanvang;

    @ManyToOne
    @JoinColumn(name = "LandAanv")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Land                    landAanvang;

    @ManyToOne
    @JoinColumn(name = "RdnEinde")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private RedenBeeindigingRelatie redenEinde;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatEinde"))
    @JsonProperty
    private Datum                   datumEinde;

    @ManyToOne
    @JoinColumn(name = "GemEinde")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Partij                  gemeenteEinde;

    @ManyToOne
    @JoinColumn(name = "WplEinde")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Plaats                  woonplaatsEinde;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLPlaatsEinde"))
    @JsonProperty
    private BuitenlandsePlaats      buitenlandsePlaatsEinde;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLRegioEinde"))
    @JsonProperty
    private BuitenlandseRegio       buitenlandseRegioEinde;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "OmsLocEinde"))
    @JsonProperty
    private LocatieOmschrijving     omschrijvingLocatieEinde;

    @ManyToOne
    @JoinColumn(name = "LandEinde")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Land                    landEinde;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisHuwelijkGeregistreerdPartnerschapModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param huwelijkGeregistreerdPartnerschapModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisHuwelijkGeregistreerdPartnerschapModel(
            final HuwelijkGeregistreerdPartnerschapModel huwelijkGeregistreerdPartnerschapModel,
            final HuwelijkGeregistreerdPartnerschapStandaardGroepModel groep)
    {
        this.relatie = huwelijkGeregistreerdPartnerschapModel;
        this.datumAanvang = groep.getDatumAanvang();
        this.gemeenteAanvang = groep.getGemeenteAanvang();
        this.woonplaatsAanvang = groep.getWoonplaatsAanvang();
        this.buitenlandsePlaatsAanvang = groep.getBuitenlandsePlaatsAanvang();
        this.buitenlandseRegioAanvang = groep.getBuitenlandseRegioAanvang();
        this.omschrijvingLocatieAanvang = groep.getOmschrijvingLocatieAanvang();
        this.landAanvang = groep.getLandAanvang();
        this.redenEinde = groep.getRedenEinde();
        this.datumEinde = groep.getDatumEinde();
        this.gemeenteEinde = groep.getGemeenteEinde();
        this.woonplaatsEinde = groep.getWoonplaatsEinde();
        this.buitenlandsePlaatsEinde = groep.getBuitenlandsePlaatsEinde();
        this.buitenlandseRegioEinde = groep.getBuitenlandseRegioEinde();
        this.omschrijvingLocatieEinde = groep.getOmschrijvingLocatieEinde();
        this.landEinde = groep.getLandEinde();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisHuwelijkGeregistreerdPartnerschapModel(
            final AbstractHisHuwelijkGeregistreerdPartnerschapModel kopie)
    {
        super(kopie);
        relatie = kopie.getRelatie();
        datumAanvang = kopie.getDatumAanvang();
        gemeenteAanvang = kopie.getGemeenteAanvang();
        woonplaatsAanvang = kopie.getWoonplaatsAanvang();
        buitenlandsePlaatsAanvang = kopie.getBuitenlandsePlaatsAanvang();
        buitenlandseRegioAanvang = kopie.getBuitenlandseRegioAanvang();
        omschrijvingLocatieAanvang = kopie.getOmschrijvingLocatieAanvang();
        landAanvang = kopie.getLandAanvang();
        redenEinde = kopie.getRedenEinde();
        datumEinde = kopie.getDatumEinde();
        gemeenteEinde = kopie.getGemeenteEinde();
        woonplaatsEinde = kopie.getWoonplaatsEinde();
        buitenlandsePlaatsEinde = kopie.getBuitenlandsePlaatsEinde();
        buitenlandseRegioEinde = kopie.getBuitenlandseRegioEinde();
        omschrijvingLocatieEinde = kopie.getOmschrijvingLocatieEinde();
        landEinde = kopie.getLandEinde();

    }

    /**
     * Retourneert ID van His Huwelijk / Geregistreerd partnerschap.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Relatie van His Huwelijk / Geregistreerd partnerschap.
     *
     * @return Relatie.
     */
    public RelatieModel getRelatie() {
        return relatie;
    }

    /**
     * Retourneert Datum aanvang van His Huwelijk / Geregistreerd partnerschap.
     *
     * @return Datum aanvang.
     */
    public Datum getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * Retourneert Gemeente aanvang van His Huwelijk / Geregistreerd partnerschap.
     *
     * @return Gemeente aanvang.
     */
    public Partij getGemeenteAanvang() {
        return gemeenteAanvang;
    }

    /**
     * Retourneert Woonplaats aanvang van His Huwelijk / Geregistreerd partnerschap.
     *
     * @return Woonplaats aanvang.
     */
    public Plaats getWoonplaatsAanvang() {
        return woonplaatsAanvang;
    }

    /**
     * Retourneert Buitenlandse plaats aanvang van His Huwelijk / Geregistreerd partnerschap.
     *
     * @return Buitenlandse plaats aanvang.
     */
    public BuitenlandsePlaats getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    /**
     * Retourneert Buitenlandse regio aanvang van His Huwelijk / Geregistreerd partnerschap.
     *
     * @return Buitenlandse regio aanvang.
     */
    public BuitenlandseRegio getBuitenlandseRegioAanvang() {
        return buitenlandseRegioAanvang;
    }

    /**
     * Retourneert Omschrijving locatie aanvang van His Huwelijk / Geregistreerd partnerschap.
     *
     * @return Omschrijving locatie aanvang.
     */
    public LocatieOmschrijving getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    /**
     * Retourneert Land aanvang van His Huwelijk / Geregistreerd partnerschap.
     *
     * @return Land aanvang.
     */
    public Land getLandAanvang() {
        return landAanvang;
    }

    /**
     * Retourneert Reden einde van His Huwelijk / Geregistreerd partnerschap.
     *
     * @return Reden einde.
     */
    public RedenBeeindigingRelatie getRedenEinde() {
        return redenEinde;
    }

    /**
     * Retourneert Datum einde van His Huwelijk / Geregistreerd partnerschap.
     *
     * @return Datum einde.
     */
    public Datum getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Gemeente einde van His Huwelijk / Geregistreerd partnerschap.
     *
     * @return Gemeente einde.
     */
    public Partij getGemeenteEinde() {
        return gemeenteEinde;
    }

    /**
     * Retourneert Woonplaats einde van His Huwelijk / Geregistreerd partnerschap.
     *
     * @return Woonplaats einde.
     */
    public Plaats getWoonplaatsEinde() {
        return woonplaatsEinde;
    }

    /**
     * Retourneert Buitenlandse plaats einde van His Huwelijk / Geregistreerd partnerschap.
     *
     * @return Buitenlandse plaats einde.
     */
    public BuitenlandsePlaats getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    /**
     * Retourneert Buitenlandse regio einde van His Huwelijk / Geregistreerd partnerschap.
     *
     * @return Buitenlandse regio einde.
     */
    public BuitenlandseRegio getBuitenlandseRegioEinde() {
        return buitenlandseRegioEinde;
    }

    /**
     * Retourneert Omschrijving locatie einde van His Huwelijk / Geregistreerd partnerschap.
     *
     * @return Omschrijving locatie einde.
     */
    public LocatieOmschrijving getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    /**
     * Retourneert Land einde van His Huwelijk / Geregistreerd partnerschap.
     *
     * @return Land einde.
     */
    public Land getLandEinde() {
        return landEinde;
    }

}
