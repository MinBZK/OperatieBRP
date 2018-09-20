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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatieAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.RelatieStandaardGroep;
import nl.bzk.brp.model.logisch.kern.RelatieStandaardGroepBasis;

/**
 * Gegevens over de aanvang en einde van een Relatie
 *
 * 1. Niet van toepassing op de familierechtelijke betrekking. Zie ook overkoepelend memo over Relatie & Betrokkenheid.
 * Het lijkt erop dat de attributen waarmee de 'plaats' (woonplaats, gemeente, land etc etc) wordt aangeduid, alleen van
 * belang is voor huwelijk en geregistreerd partnerschap. Opnemen van de velden voor andere relaties is alleen reden
 * voor verwarring. We kiezen er daarom voor om 'plaats' velden alleen te vullen voor huwelijk en geregistreerd
 * partnerschap. 2. Vorm van historie: alleen formeel. Motivatie: alle (materiële) tijdsaspecten zijn uitgemodelleerd
 * (met datum aanvang en datum einde), waardoor dus geen (extra) materiële historie van toepassing is. Verder 'herleeft'
 * een Huwelijk niet, en wordt het ene Huwelijk niet door een ander Huwelijk beëindigd. Met andere woorden: twee
 * personen die eerst met elkaar Huwen, vervolgens scheiden, en vervolgens weer Huwen, hebben TWEE (verschillende)
 * exemplaren van Relatie: het eerste Huwelijk, en het tweede. Door deze zienswijze (die volgt uit de definitie van
 * Relatie) is er DUS geen sprake van materiële historie, en volstaat dus de formele historie.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractRelatieStandaardGroepModel implements RelatieStandaardGroepBasis {

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatAanv"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumAanvang;

    @Embedded
    @AssociationOverride(name = GemeenteAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "GemAanv"))
    @JsonProperty
    private GemeenteAttribuut gemeenteAanvang;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "WplnaamAanv"))
    @JsonProperty
    private NaamEnumeratiewaardeAttribuut woonplaatsnaamAanvang;

    @Embedded
    @AttributeOverride(name = BuitenlandsePlaatsAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLPlaatsAanv"))
    @JsonProperty
    private BuitenlandsePlaatsAttribuut buitenlandsePlaatsAanvang;

    @Embedded
    @AttributeOverride(name = BuitenlandseRegioAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLRegioAanv"))
    @JsonProperty
    private BuitenlandseRegioAttribuut buitenlandseRegioAanvang;

    @Embedded
    @AttributeOverride(name = LocatieomschrijvingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "OmsLocAanv"))
    @JsonProperty
    private LocatieomschrijvingAttribuut omschrijvingLocatieAanvang;

    @Embedded
    @AssociationOverride(name = LandGebiedAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "LandGebiedAanv"))
    @JsonProperty
    private LandGebiedAttribuut landGebiedAanvang;

    @Embedded
    @AssociationOverride(name = RedenEindeRelatieAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "RdnEinde"))
    @JsonProperty
    private RedenEindeRelatieAttribuut redenEinde;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumEinde;

    @Embedded
    @AssociationOverride(name = GemeenteAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "GemEinde"))
    @JsonProperty
    private GemeenteAttribuut gemeenteEinde;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "WplnaamEinde"))
    @JsonProperty
    private NaamEnumeratiewaardeAttribuut woonplaatsnaamEinde;

    @Embedded
    @AttributeOverride(name = BuitenlandsePlaatsAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLPlaatsEinde"))
    @JsonProperty
    private BuitenlandsePlaatsAttribuut buitenlandsePlaatsEinde;

    @Embedded
    @AttributeOverride(name = BuitenlandseRegioAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLRegioEinde"))
    @JsonProperty
    private BuitenlandseRegioAttribuut buitenlandseRegioEinde;

    @Embedded
    @AttributeOverride(name = LocatieomschrijvingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "OmsLocEinde"))
    @JsonProperty
    private LocatieomschrijvingAttribuut omschrijvingLocatieEinde;

    @Embedded
    @AssociationOverride(name = LandGebiedAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "LandGebiedEinde"))
    @JsonProperty
    private LandGebiedAttribuut landGebiedEinde;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractRelatieStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param datumAanvang datumAanvang van Standaard.
     * @param gemeenteAanvang gemeenteAanvang van Standaard.
     * @param woonplaatsnaamAanvang woonplaatsnaamAanvang van Standaard.
     * @param buitenlandsePlaatsAanvang buitenlandsePlaatsAanvang van Standaard.
     * @param buitenlandseRegioAanvang buitenlandseRegioAanvang van Standaard.
     * @param omschrijvingLocatieAanvang omschrijvingLocatieAanvang van Standaard.
     * @param landGebiedAanvang landGebiedAanvang van Standaard.
     * @param redenEinde redenEinde van Standaard.
     * @param datumEinde datumEinde van Standaard.
     * @param gemeenteEinde gemeenteEinde van Standaard.
     * @param woonplaatsnaamEinde woonplaatsnaamEinde van Standaard.
     * @param buitenlandsePlaatsEinde buitenlandsePlaatsEinde van Standaard.
     * @param buitenlandseRegioEinde buitenlandseRegioEinde van Standaard.
     * @param omschrijvingLocatieEinde omschrijvingLocatieEinde van Standaard.
     * @param landGebiedEinde landGebiedEinde van Standaard.
     */
    public AbstractRelatieStandaardGroepModel(
        final DatumEvtDeelsOnbekendAttribuut datumAanvang,
        final GemeenteAttribuut gemeenteAanvang,
        final NaamEnumeratiewaardeAttribuut woonplaatsnaamAanvang,
        final BuitenlandsePlaatsAttribuut buitenlandsePlaatsAanvang,
        final BuitenlandseRegioAttribuut buitenlandseRegioAanvang,
        final LocatieomschrijvingAttribuut omschrijvingLocatieAanvang,
        final LandGebiedAttribuut landGebiedAanvang,
        final RedenEindeRelatieAttribuut redenEinde,
        final DatumEvtDeelsOnbekendAttribuut datumEinde,
        final GemeenteAttribuut gemeenteEinde,
        final NaamEnumeratiewaardeAttribuut woonplaatsnaamEinde,
        final BuitenlandsePlaatsAttribuut buitenlandsePlaatsEinde,
        final BuitenlandseRegioAttribuut buitenlandseRegioEinde,
        final LocatieomschrijvingAttribuut omschrijvingLocatieEinde,
        final LandGebiedAttribuut landGebiedEinde)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.datumAanvang = datumAanvang;
        this.gemeenteAanvang = gemeenteAanvang;
        this.woonplaatsnaamAanvang = woonplaatsnaamAanvang;
        this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
        this.buitenlandseRegioAanvang = buitenlandseRegioAanvang;
        this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
        this.landGebiedAanvang = landGebiedAanvang;
        this.redenEinde = redenEinde;
        this.datumEinde = datumEinde;
        this.gemeenteEinde = gemeenteEinde;
        this.woonplaatsnaamEinde = woonplaatsnaamEinde;
        this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
        this.buitenlandseRegioEinde = buitenlandseRegioEinde;
        this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
        this.landGebiedEinde = landGebiedEinde;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param relatieStandaardGroep te kopieren groep.
     */
    public AbstractRelatieStandaardGroepModel(final RelatieStandaardGroep relatieStandaardGroep) {
        this.datumAanvang = relatieStandaardGroep.getDatumAanvang();
        this.gemeenteAanvang = relatieStandaardGroep.getGemeenteAanvang();
        this.woonplaatsnaamAanvang = relatieStandaardGroep.getWoonplaatsnaamAanvang();
        this.buitenlandsePlaatsAanvang = relatieStandaardGroep.getBuitenlandsePlaatsAanvang();
        this.buitenlandseRegioAanvang = relatieStandaardGroep.getBuitenlandseRegioAanvang();
        this.omschrijvingLocatieAanvang = relatieStandaardGroep.getOmschrijvingLocatieAanvang();
        this.landGebiedAanvang = relatieStandaardGroep.getLandGebiedAanvang();
        this.redenEinde = relatieStandaardGroep.getRedenEinde();
        this.datumEinde = relatieStandaardGroep.getDatumEinde();
        this.gemeenteEinde = relatieStandaardGroep.getGemeenteEinde();
        this.woonplaatsnaamEinde = relatieStandaardGroep.getWoonplaatsnaamEinde();
        this.buitenlandsePlaatsEinde = relatieStandaardGroep.getBuitenlandsePlaatsEinde();
        this.buitenlandseRegioEinde = relatieStandaardGroep.getBuitenlandseRegioEinde();
        this.omschrijvingLocatieEinde = relatieStandaardGroep.getOmschrijvingLocatieEinde();
        this.landGebiedEinde = relatieStandaardGroep.getLandGebiedEinde();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GemeenteAttribuut getGemeenteAanvang() {
        return gemeenteAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NaamEnumeratiewaardeAttribuut getWoonplaatsnaamAanvang() {
        return woonplaatsnaamAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandsePlaatsAttribuut getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandseRegioAttribuut getBuitenlandseRegioAanvang() {
        return buitenlandseRegioAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocatieomschrijvingAttribuut getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LandGebiedAttribuut getLandGebiedAanvang() {
        return landGebiedAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenEindeRelatieAttribuut getRedenEinde() {
        return redenEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GemeenteAttribuut getGemeenteEinde() {
        return gemeenteEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NaamEnumeratiewaardeAttribuut getWoonplaatsnaamEinde() {
        return woonplaatsnaamEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandsePlaatsAttribuut getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuitenlandseRegioAttribuut getBuitenlandseRegioEinde() {
        return buitenlandseRegioEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocatieomschrijvingAttribuut getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LandGebiedAttribuut getLandGebiedEinde() {
        return landGebiedEinde;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (datumAanvang != null) {
            attributen.add(datumAanvang);
        }
        if (gemeenteAanvang != null) {
            attributen.add(gemeenteAanvang);
        }
        if (woonplaatsnaamAanvang != null) {
            attributen.add(woonplaatsnaamAanvang);
        }
        if (buitenlandsePlaatsAanvang != null) {
            attributen.add(buitenlandsePlaatsAanvang);
        }
        if (buitenlandseRegioAanvang != null) {
            attributen.add(buitenlandseRegioAanvang);
        }
        if (omschrijvingLocatieAanvang != null) {
            attributen.add(omschrijvingLocatieAanvang);
        }
        if (landGebiedAanvang != null) {
            attributen.add(landGebiedAanvang);
        }
        if (redenEinde != null) {
            attributen.add(redenEinde);
        }
        if (datumEinde != null) {
            attributen.add(datumEinde);
        }
        if (gemeenteEinde != null) {
            attributen.add(gemeenteEinde);
        }
        if (woonplaatsnaamEinde != null) {
            attributen.add(woonplaatsnaamEinde);
        }
        if (buitenlandsePlaatsEinde != null) {
            attributen.add(buitenlandsePlaatsEinde);
        }
        if (buitenlandseRegioEinde != null) {
            attributen.add(buitenlandseRegioEinde);
        }
        if (omschrijvingLocatieEinde != null) {
            attributen.add(omschrijvingLocatieEinde);
        }
        if (landGebiedEinde != null) {
            attributen.add(landGebiedEinde);
        }
        return attributen;
    }

}
