/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ist;

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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.ist.StapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractStapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepModel implements
        StapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepBasis
{

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatAanv"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumAanvang;

    @Embedded
    @AssociationOverride(name = GemeenteAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "GemAanv"))
    @JsonProperty
    private GemeenteAttribuut gemeenteAanvang;

    @Embedded
    @AttributeOverride(name = BuitenlandsePlaatsAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLPlaatsAanv"))
    @JsonProperty
    private BuitenlandsePlaatsAttribuut buitenlandsePlaatsAanvang;

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
    @AttributeOverride(name = BuitenlandsePlaatsAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLPlaatsEinde"))
    @JsonProperty
    private BuitenlandsePlaatsAttribuut buitenlandsePlaatsEinde;

    @Embedded
    @AttributeOverride(name = LocatieomschrijvingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "OmsLocEinde"))
    @JsonProperty
    private LocatieomschrijvingAttribuut omschrijvingLocatieEinde;

    @Embedded
    @AssociationOverride(name = LandGebiedAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "LandGebiedEinde"))
    @JsonProperty
    private LandGebiedAttribuut landGebiedEinde;

    @Embedded
    @AttributeOverride(name = SoortRelatieAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "SrtRelatie"))
    @JsonProperty
    private SoortRelatieAttribuut soortRelatie;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractStapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param datumAanvang datumAanvang van Categorie Huwelijk/Geregistreerd partnerschap.
     * @param gemeenteAanvang gemeenteAanvang van Categorie Huwelijk/Geregistreerd partnerschap.
     * @param buitenlandsePlaatsAanvang buitenlandsePlaatsAanvang van Categorie Huwelijk/Geregistreerd partnerschap.
     * @param omschrijvingLocatieAanvang omschrijvingLocatieAanvang van Categorie Huwelijk/Geregistreerd partnerschap.
     * @param landGebiedAanvang landGebiedAanvang van Categorie Huwelijk/Geregistreerd partnerschap.
     * @param redenEinde redenEinde van Categorie Huwelijk/Geregistreerd partnerschap.
     * @param datumEinde datumEinde van Categorie Huwelijk/Geregistreerd partnerschap.
     * @param gemeenteEinde gemeenteEinde van Categorie Huwelijk/Geregistreerd partnerschap.
     * @param buitenlandsePlaatsEinde buitenlandsePlaatsEinde van Categorie Huwelijk/Geregistreerd partnerschap.
     * @param omschrijvingLocatieEinde omschrijvingLocatieEinde van Categorie Huwelijk/Geregistreerd partnerschap.
     * @param landGebiedEinde landGebiedEinde van Categorie Huwelijk/Geregistreerd partnerschap.
     * @param soortRelatie soortRelatie van Categorie Huwelijk/Geregistreerd partnerschap.
     */
    public AbstractStapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepModel(
        final DatumEvtDeelsOnbekendAttribuut datumAanvang,
        final GemeenteAttribuut gemeenteAanvang,
        final BuitenlandsePlaatsAttribuut buitenlandsePlaatsAanvang,
        final LocatieomschrijvingAttribuut omschrijvingLocatieAanvang,
        final LandGebiedAttribuut landGebiedAanvang,
        final RedenEindeRelatieAttribuut redenEinde,
        final DatumEvtDeelsOnbekendAttribuut datumEinde,
        final GemeenteAttribuut gemeenteEinde,
        final BuitenlandsePlaatsAttribuut buitenlandsePlaatsEinde,
        final LocatieomschrijvingAttribuut omschrijvingLocatieEinde,
        final LandGebiedAttribuut landGebiedEinde,
        final SoortRelatieAttribuut soortRelatie)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.datumAanvang = datumAanvang;
        this.gemeenteAanvang = gemeenteAanvang;
        this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
        this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
        this.landGebiedAanvang = landGebiedAanvang;
        this.redenEinde = redenEinde;
        this.datumEinde = datumEinde;
        this.gemeenteEinde = gemeenteEinde;
        this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
        this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
        this.landGebiedEinde = landGebiedEinde;
        this.soortRelatie = soortRelatie;

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
    public BuitenlandsePlaatsAttribuut getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
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
    public BuitenlandsePlaatsAttribuut getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
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
     * {@inheritDoc}
     */
    @Override
    public SoortRelatieAttribuut getSoortRelatie() {
        return soortRelatie;
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
        if (buitenlandsePlaatsAanvang != null) {
            attributen.add(buitenlandsePlaatsAanvang);
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
        if (buitenlandsePlaatsEinde != null) {
            attributen.add(buitenlandsePlaatsEinde);
        }
        if (omschrijvingLocatieEinde != null) {
            attributen.add(omschrijvingLocatieEinde);
        }
        if (landGebiedEinde != null) {
            attributen.add(landGebiedEinde);
        }
        if (soortRelatie != null) {
            attributen.add(soortRelatie);
        }
        return attributen;
    }

}
