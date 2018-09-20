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
import nl.bzk.brp.model.logisch.kern.PersoonOverlijdenGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonOverlijdenGroepBasis;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * 1. De vorm van historie is conform bij geboorte: ondanks dat er een materieel tijdsaspect is (datum overlijden) is er
 * g��n sprake van een 'materieel historiepatroon'. Immers, het is absurd om te stellen dat iemand vorig jaar overleden
 * is in plaats X en dit jaar in plaats Y ;-0
 * RvdP 9 jan 2012.
 *
 *
 *
 */
@MappedSuperclass
public abstract class AbstractPersoonOverlijdenGroepModel implements PersoonOverlijdenGroepBasis {

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
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonOverlijdenGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param datumOverlijden datumOverlijden van Overlijden.
     * @param gemeenteOverlijden gemeenteOverlijden van Overlijden.
     * @param woonplaatsOverlijden woonplaatsOverlijden van Overlijden.
     * @param buitenlandsePlaatsOverlijden buitenlandsePlaatsOverlijden van Overlijden.
     * @param buitenlandseRegioOverlijden buitenlandseRegioOverlijden van Overlijden.
     * @param omschrijvingLocatieOverlijden omschrijvingLocatieOverlijden van Overlijden.
     * @param landOverlijden landOverlijden van Overlijden.
     */
    public AbstractPersoonOverlijdenGroepModel(final Datum datumOverlijden, final Partij gemeenteOverlijden,
            final Plaats woonplaatsOverlijden, final BuitenlandsePlaats buitenlandsePlaatsOverlijden,
            final BuitenlandseRegio buitenlandseRegioOverlijden,
            final LocatieOmschrijving omschrijvingLocatieOverlijden, final Land landOverlijden)
    {
        this.datumOverlijden = datumOverlijden;
        this.gemeenteOverlijden = gemeenteOverlijden;
        this.woonplaatsOverlijden = woonplaatsOverlijden;
        this.buitenlandsePlaatsOverlijden = buitenlandsePlaatsOverlijden;
        this.buitenlandseRegioOverlijden = buitenlandseRegioOverlijden;
        this.omschrijvingLocatieOverlijden = omschrijvingLocatieOverlijden;
        this.landOverlijden = landOverlijden;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonOverlijdenGroep te kopieren groep.
     */
    public AbstractPersoonOverlijdenGroepModel(final PersoonOverlijdenGroep persoonOverlijdenGroep) {
        this.datumOverlijden = persoonOverlijdenGroep.getDatumOverlijden();
        this.gemeenteOverlijden = persoonOverlijdenGroep.getGemeenteOverlijden();
        this.woonplaatsOverlijden = persoonOverlijdenGroep.getWoonplaatsOverlijden();
        this.buitenlandsePlaatsOverlijden = persoonOverlijdenGroep.getBuitenlandsePlaatsOverlijden();
        this.buitenlandseRegioOverlijden = persoonOverlijdenGroep.getBuitenlandseRegioOverlijden();
        this.omschrijvingLocatieOverlijden = persoonOverlijdenGroep.getOmschrijvingLocatieOverlijden();
        this.landOverlijden = persoonOverlijdenGroep.getLandOverlijden();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumOverlijden() {
        return datumOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij getGemeenteOverlijden() {
        return gemeenteOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Plaats getWoonplaatsOverlijden() {
        return woonplaatsOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandsePlaats getBuitenlandsePlaatsOverlijden() {
        return buitenlandsePlaatsOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandseRegio getBuitenlandseRegioOverlijden() {
        return buitenlandseRegioOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocatieOmschrijving getOmschrijvingLocatieOverlijden() {
        return omschrijvingLocatieOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Land getLandOverlijden() {
        return landOverlijden;
    }

}
