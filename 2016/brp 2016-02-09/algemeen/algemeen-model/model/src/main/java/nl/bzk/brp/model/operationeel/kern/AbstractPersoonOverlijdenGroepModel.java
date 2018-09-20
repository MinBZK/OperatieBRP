/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegioAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.PersoonOverlijdenGroep;
import nl.bzk.brp.model.logisch.kern.PersoonOverlijdenGroepBasis;

/**
 * 1. De vorm van historie is conform bij geboorte: ondanks dat er een materieel tijdsaspect is (datum overlijden) is er
 * géén sprake van een 'materieel historiepatroon'. Immers, het is absurd om te stellen dat iemand vorig jaar overleden
 * is in plaats X en dit jaar in plaats Y.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonOverlijdenGroepModel implements PersoonOverlijdenGroepBasis {

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatOverlijden"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumOverlijden;

    @Embedded
    @AssociationOverride(name = GemeenteAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "GemOverlijden"))
    @JsonProperty
    private GemeenteAttribuut gemeenteOverlijden;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "WplnaamOverlijden"))
    @JsonProperty
    private NaamEnumeratiewaardeAttribuut woonplaatsnaamOverlijden;

    @Embedded
    @AttributeOverride(name = BuitenlandsePlaatsAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLPlaatsOverlijden"))
    @JsonProperty
    private BuitenlandsePlaatsAttribuut buitenlandsePlaatsOverlijden;

    @Embedded
    @AttributeOverride(name = BuitenlandseRegioAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLRegioOverlijden"))
    @JsonProperty
    private BuitenlandseRegioAttribuut buitenlandseRegioOverlijden;

    @Embedded
    @AttributeOverride(name = LocatieomschrijvingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "OmsLocOverlijden"))
    @JsonProperty
    private LocatieomschrijvingAttribuut omschrijvingLocatieOverlijden;

    @Embedded
    @AssociationOverride(name = LandGebiedAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "LandGebiedOverlijden"))
    @JsonProperty
    private LandGebiedAttribuut landGebiedOverlijden;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonOverlijdenGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param datumOverlijden datumOverlijden van Overlijden.
     * @param gemeenteOverlijden gemeenteOverlijden van Overlijden.
     * @param woonplaatsnaamOverlijden woonplaatsnaamOverlijden van Overlijden.
     * @param buitenlandsePlaatsOverlijden buitenlandsePlaatsOverlijden van Overlijden.
     * @param buitenlandseRegioOverlijden buitenlandseRegioOverlijden van Overlijden.
     * @param omschrijvingLocatieOverlijden omschrijvingLocatieOverlijden van Overlijden.
     * @param landGebiedOverlijden landGebiedOverlijden van Overlijden.
     */
    public AbstractPersoonOverlijdenGroepModel(
        final DatumEvtDeelsOnbekendAttribuut datumOverlijden,
        final GemeenteAttribuut gemeenteOverlijden,
        final NaamEnumeratiewaardeAttribuut woonplaatsnaamOverlijden,
        final BuitenlandsePlaatsAttribuut buitenlandsePlaatsOverlijden,
        final BuitenlandseRegioAttribuut buitenlandseRegioOverlijden,
        final LocatieomschrijvingAttribuut omschrijvingLocatieOverlijden,
        final LandGebiedAttribuut landGebiedOverlijden)
    {
        this.datumOverlijden = datumOverlijden;
        this.gemeenteOverlijden = gemeenteOverlijden;
        this.woonplaatsnaamOverlijden = woonplaatsnaamOverlijden;
        this.buitenlandsePlaatsOverlijden = buitenlandsePlaatsOverlijden;
        this.buitenlandseRegioOverlijden = buitenlandseRegioOverlijden;
        this.omschrijvingLocatieOverlijden = omschrijvingLocatieOverlijden;
        this.landGebiedOverlijden = landGebiedOverlijden;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonOverlijdenGroep te kopieren groep.
     */
    public AbstractPersoonOverlijdenGroepModel(final PersoonOverlijdenGroep persoonOverlijdenGroep) {
        this.datumOverlijden = persoonOverlijdenGroep.getDatumOverlijden();
        this.gemeenteOverlijden = persoonOverlijdenGroep.getGemeenteOverlijden();
        this.woonplaatsnaamOverlijden = persoonOverlijdenGroep.getWoonplaatsnaamOverlijden();
        this.buitenlandsePlaatsOverlijden = persoonOverlijdenGroep.getBuitenlandsePlaatsOverlijden();
        this.buitenlandseRegioOverlijden = persoonOverlijdenGroep.getBuitenlandseRegioOverlijden();
        this.omschrijvingLocatieOverlijden = persoonOverlijdenGroep.getOmschrijvingLocatieOverlijden();
        this.landGebiedOverlijden = persoonOverlijdenGroep.getLandGebiedOverlijden();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumOverlijden() {
        return datumOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GemeenteAttribuut getGemeenteOverlijden() {
        return gemeenteOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NaamEnumeratiewaardeAttribuut getWoonplaatsnaamOverlijden() {
        return woonplaatsnaamOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandsePlaatsAttribuut getBuitenlandsePlaatsOverlijden() {
        return buitenlandsePlaatsOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandseRegioAttribuut getBuitenlandseRegioOverlijden() {
        return buitenlandseRegioOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocatieomschrijvingAttribuut getOmschrijvingLocatieOverlijden() {
        return omschrijvingLocatieOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LandGebiedAttribuut getLandGebiedOverlijden() {
        return landGebiedOverlijden;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (datumOverlijden != null) {
            attributen.add(datumOverlijden);
        }
        if (gemeenteOverlijden != null) {
            attributen.add(gemeenteOverlijden);
        }
        if (woonplaatsnaamOverlijden != null) {
            attributen.add(woonplaatsnaamOverlijden);
        }
        if (buitenlandsePlaatsOverlijden != null) {
            attributen.add(buitenlandsePlaatsOverlijden);
        }
        if (buitenlandseRegioOverlijden != null) {
            attributen.add(buitenlandseRegioOverlijden);
        }
        if (omschrijvingLocatieOverlijden != null) {
            attributen.add(omschrijvingLocatieOverlijden);
        }
        if (landGebiedOverlijden != null) {
            attributen.add(landGebiedOverlijden);
        }
        return attributen;
    }

}
