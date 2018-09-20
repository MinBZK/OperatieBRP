/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaats;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegio;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieOmschrijving;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenBeeindigingRelatie;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschapStandaardGroep;
import nl.bzk.brp.model.logisch.kern.basis.HuwelijkGeregistreerdPartnerschapStandaardGroepBasis;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * Gegevens over de aanvang en einde van een Relatie
 *
 * 1. Niet van toepassing op de familierechtelijke betrekking. Zie ook overkoepelend memo over Relatie & Betrokkenheid.
 * Het lijkt erop dat de attributen waarmee de 'plaats' (woonplaats, gemeente, land etc etc) wordt aangeduid, alleen van
 * belang is voor huwelijk en geregistreerd partnerschap. Opnemen van de velden voor andere relaties is alleen reden
 * voor verwarring. We kiezen er daarom voor om 'plaats' velden alleen te vullen voor huwelijk en geregistreerd
 * partnerschap.
 * 2. Vorm van historie: alleen formeel. Motivatie: alle (materi�le) tijdsaspecten zijn uitgemodelleerd (met datum
 * aanvang en datum einde), waardoor dus geen (extra) materi�le historie van toepassing is. Verder 'herleeft' een
 * Huwelijk niet, en wordt het ene Huwelijk niet door een ander Huwelijk be�indigd. Met andere woorden: twee personen
 * die eerst met elkaar Huwen, vervolgens scheiden, en vervolgens weer Huwen, hebben TWEE (verschillende) exemplaren van
 * Relatie: het eerste Huwelijk, en het tweede.
 * Door deze zienswijze (die volgt uit de definitie van Relatie) is er DUS geen sprake van materi�le historie, en
 * volstaat dus de formele historie.
 * RvdP 17 jan 2012.
 *
 *
 *
 */
@MappedSuperclass
public abstract class AbstractHuwelijkGeregistreerdPartnerschapStandaardGroepModel implements
        HuwelijkGeregistreerdPartnerschapStandaardGroepBasis
{

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
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractHuwelijkGeregistreerdPartnerschapStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     * CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param datumAanvang datumAanvang van Standaard.
     * @param gemeenteAanvang gemeenteAanvang van Standaard.
     * @param woonplaatsAanvang woonplaatsAanvang van Standaard.
     * @param buitenlandsePlaatsAanvang buitenlandsePlaatsAanvang van Standaard.
     * @param buitenlandseRegioAanvang buitenlandseRegioAanvang van Standaard.
     * @param omschrijvingLocatieAanvang omschrijvingLocatieAanvang van Standaard.
     * @param landAanvang landAanvang van Standaard.
     * @param redenEinde redenEinde van Standaard.
     * @param datumEinde datumEinde van Standaard.
     * @param gemeenteEinde gemeenteEinde van Standaard.
     * @param woonplaatsEinde woonplaatsEinde van Standaard.
     * @param buitenlandsePlaatsEinde buitenlandsePlaatsEinde van Standaard.
     * @param buitenlandseRegioEinde buitenlandseRegioEinde van Standaard.
     * @param omschrijvingLocatieEinde omschrijvingLocatieEinde van Standaard.
     * @param landEinde landEinde van Standaard.
     */
    public AbstractHuwelijkGeregistreerdPartnerschapStandaardGroepModel(final Datum datumAanvang,
            final Partij gemeenteAanvang, final Plaats woonplaatsAanvang,
            final BuitenlandsePlaats buitenlandsePlaatsAanvang, final BuitenlandseRegio buitenlandseRegioAanvang,
            final LocatieOmschrijving omschrijvingLocatieAanvang, final Land landAanvang,
            final RedenBeeindigingRelatie redenEinde, final Datum datumEinde, final Partij gemeenteEinde,
            final Plaats woonplaatsEinde, final BuitenlandsePlaats buitenlandsePlaatsEinde,
            final BuitenlandseRegio buitenlandseRegioEinde, final LocatieOmschrijving omschrijvingLocatieEinde,
            final Land landEinde)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.datumAanvang = datumAanvang;
        this.gemeenteAanvang = gemeenteAanvang;
        this.woonplaatsAanvang = woonplaatsAanvang;
        this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
        this.buitenlandseRegioAanvang = buitenlandseRegioAanvang;
        this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
        this.landAanvang = landAanvang;
        this.redenEinde = redenEinde;
        this.datumEinde = datumEinde;
        this.gemeenteEinde = gemeenteEinde;
        this.woonplaatsEinde = woonplaatsEinde;
        this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
        this.buitenlandseRegioEinde = buitenlandseRegioEinde;
        this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
        this.landEinde = landEinde;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param huwelijkGeregistreerdPartnerschapStandaardGroep te kopieren groep.
     */
    public AbstractHuwelijkGeregistreerdPartnerschapStandaardGroepModel(
            final HuwelijkGeregistreerdPartnerschapStandaardGroep huwelijkGeregistreerdPartnerschapStandaardGroep)
    {
        this.datumAanvang = huwelijkGeregistreerdPartnerschapStandaardGroep.getDatumAanvang();
        this.gemeenteAanvang = huwelijkGeregistreerdPartnerschapStandaardGroep.getGemeenteAanvang();
        this.woonplaatsAanvang = huwelijkGeregistreerdPartnerschapStandaardGroep.getWoonplaatsAanvang();
        this.buitenlandsePlaatsAanvang = huwelijkGeregistreerdPartnerschapStandaardGroep.getBuitenlandsePlaatsAanvang();
        this.buitenlandseRegioAanvang = huwelijkGeregistreerdPartnerschapStandaardGroep.getBuitenlandseRegioAanvang();
        this.omschrijvingLocatieAanvang =
            huwelijkGeregistreerdPartnerschapStandaardGroep.getOmschrijvingLocatieAanvang();
        this.landAanvang = huwelijkGeregistreerdPartnerschapStandaardGroep.getLandAanvang();
        this.redenEinde = huwelijkGeregistreerdPartnerschapStandaardGroep.getRedenEinde();
        this.datumEinde = huwelijkGeregistreerdPartnerschapStandaardGroep.getDatumEinde();
        this.gemeenteEinde = huwelijkGeregistreerdPartnerschapStandaardGroep.getGemeenteEinde();
        this.woonplaatsEinde = huwelijkGeregistreerdPartnerschapStandaardGroep.getWoonplaatsEinde();
        this.buitenlandsePlaatsEinde = huwelijkGeregistreerdPartnerschapStandaardGroep.getBuitenlandsePlaatsEinde();
        this.buitenlandseRegioEinde = huwelijkGeregistreerdPartnerschapStandaardGroep.getBuitenlandseRegioEinde();
        this.omschrijvingLocatieEinde = huwelijkGeregistreerdPartnerschapStandaardGroep.getOmschrijvingLocatieEinde();
        this.landEinde = huwelijkGeregistreerdPartnerschapStandaardGroep.getLandEinde();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij getGemeenteAanvang() {
        return gemeenteAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Plaats getWoonplaatsAanvang() {
        return woonplaatsAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandsePlaats getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandseRegio getBuitenlandseRegioAanvang() {
        return buitenlandseRegioAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocatieOmschrijving getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Land getLandAanvang() {
        return landAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenBeeindigingRelatie getRedenEinde() {
        return redenEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumEinde() {
        return datumEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij getGemeenteEinde() {
        return gemeenteEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Plaats getWoonplaatsEinde() {
        return woonplaatsEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandsePlaats getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandseRegio getBuitenlandseRegioEinde() {
        return buitenlandseRegioEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocatieOmschrijving getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Land getLandEinde() {
        return landEinde;
    }

}
