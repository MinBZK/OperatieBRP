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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdresregelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratieAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.PersoonMigratieGroep;
import nl.bzk.brp.model.logisch.kern.PersoonMigratieGroepBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonMigratieGroepModel implements PersoonMigratieGroepBasis {

    @Embedded
    @AttributeOverride(name = SoortMigratieAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "SrtMigratie"))
    @JsonProperty
    private SoortMigratieAttribuut soortMigratie;

    @Embedded
    @AssociationOverride(name = RedenWijzigingVerblijfAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "RdnWijzMigratie"))
    @JsonProperty
    private RedenWijzigingVerblijfAttribuut redenWijzigingMigratie;

    @Embedded
    @AssociationOverride(name = AangeverAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "AangMigratie"))
    @JsonProperty
    private AangeverAttribuut aangeverMigratie;

    @Embedded
    @AssociationOverride(name = LandGebiedAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "LandGebiedMigratie"))
    @JsonProperty
    private LandGebiedAttribuut landGebiedMigratie;

    @Embedded
    @AttributeOverride(name = AdresregelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLAdresRegel1Migratie"))
    @JsonProperty
    private AdresregelAttribuut buitenlandsAdresRegel1Migratie;

    @Embedded
    @AttributeOverride(name = AdresregelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLAdresRegel2Migratie"))
    @JsonProperty
    private AdresregelAttribuut buitenlandsAdresRegel2Migratie;

    @Embedded
    @AttributeOverride(name = AdresregelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLAdresRegel3Migratie"))
    @JsonProperty
    private AdresregelAttribuut buitenlandsAdresRegel3Migratie;

    @Embedded
    @AttributeOverride(name = AdresregelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLAdresRegel4Migratie"))
    @JsonProperty
    private AdresregelAttribuut buitenlandsAdresRegel4Migratie;

    @Embedded
    @AttributeOverride(name = AdresregelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLAdresRegel5Migratie"))
    @JsonProperty
    private AdresregelAttribuut buitenlandsAdresRegel5Migratie;

    @Embedded
    @AttributeOverride(name = AdresregelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLAdresRegel6Migratie"))
    @JsonProperty
    private AdresregelAttribuut buitenlandsAdresRegel6Migratie;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonMigratieGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param soortMigratie soortMigratie van Migratie.
     * @param redenWijzigingMigratie redenWijzigingMigratie van Migratie.
     * @param aangeverMigratie aangeverMigratie van Migratie.
     * @param landGebiedMigratie landGebiedMigratie van Migratie.
     * @param buitenlandsAdresRegel1Migratie buitenlandsAdresRegel1Migratie van Migratie.
     * @param buitenlandsAdresRegel2Migratie buitenlandsAdresRegel2Migratie van Migratie.
     * @param buitenlandsAdresRegel3Migratie buitenlandsAdresRegel3Migratie van Migratie.
     * @param buitenlandsAdresRegel4Migratie buitenlandsAdresRegel4Migratie van Migratie.
     * @param buitenlandsAdresRegel5Migratie buitenlandsAdresRegel5Migratie van Migratie.
     * @param buitenlandsAdresRegel6Migratie buitenlandsAdresRegel6Migratie van Migratie.
     */
    public AbstractPersoonMigratieGroepModel(
        final SoortMigratieAttribuut soortMigratie,
        final RedenWijzigingVerblijfAttribuut redenWijzigingMigratie,
        final AangeverAttribuut aangeverMigratie,
        final LandGebiedAttribuut landGebiedMigratie,
        final AdresregelAttribuut buitenlandsAdresRegel1Migratie,
        final AdresregelAttribuut buitenlandsAdresRegel2Migratie,
        final AdresregelAttribuut buitenlandsAdresRegel3Migratie,
        final AdresregelAttribuut buitenlandsAdresRegel4Migratie,
        final AdresregelAttribuut buitenlandsAdresRegel5Migratie,
        final AdresregelAttribuut buitenlandsAdresRegel6Migratie)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.soortMigratie = soortMigratie;
        this.redenWijzigingMigratie = redenWijzigingMigratie;
        this.aangeverMigratie = aangeverMigratie;
        this.landGebiedMigratie = landGebiedMigratie;
        this.buitenlandsAdresRegel1Migratie = buitenlandsAdresRegel1Migratie;
        this.buitenlandsAdresRegel2Migratie = buitenlandsAdresRegel2Migratie;
        this.buitenlandsAdresRegel3Migratie = buitenlandsAdresRegel3Migratie;
        this.buitenlandsAdresRegel4Migratie = buitenlandsAdresRegel4Migratie;
        this.buitenlandsAdresRegel5Migratie = buitenlandsAdresRegel5Migratie;
        this.buitenlandsAdresRegel6Migratie = buitenlandsAdresRegel6Migratie;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonMigratieGroep te kopieren groep.
     */
    public AbstractPersoonMigratieGroepModel(final PersoonMigratieGroep persoonMigratieGroep) {
        this.soortMigratie = persoonMigratieGroep.getSoortMigratie();
        this.redenWijzigingMigratie = persoonMigratieGroep.getRedenWijzigingMigratie();
        this.aangeverMigratie = persoonMigratieGroep.getAangeverMigratie();
        this.landGebiedMigratie = persoonMigratieGroep.getLandGebiedMigratie();
        this.buitenlandsAdresRegel1Migratie = persoonMigratieGroep.getBuitenlandsAdresRegel1Migratie();
        this.buitenlandsAdresRegel2Migratie = persoonMigratieGroep.getBuitenlandsAdresRegel2Migratie();
        this.buitenlandsAdresRegel3Migratie = persoonMigratieGroep.getBuitenlandsAdresRegel3Migratie();
        this.buitenlandsAdresRegel4Migratie = persoonMigratieGroep.getBuitenlandsAdresRegel4Migratie();
        this.buitenlandsAdresRegel5Migratie = persoonMigratieGroep.getBuitenlandsAdresRegel5Migratie();
        this.buitenlandsAdresRegel6Migratie = persoonMigratieGroep.getBuitenlandsAdresRegel6Migratie();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortMigratieAttribuut getSoortMigratie() {
        return soortMigratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenWijzigingVerblijfAttribuut getRedenWijzigingMigratie() {
        return redenWijzigingMigratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AangeverAttribuut getAangeverMigratie() {
        return aangeverMigratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LandGebiedAttribuut getLandGebiedMigratie() {
        return landGebiedMigratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel1Migratie() {
        return buitenlandsAdresRegel1Migratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel2Migratie() {
        return buitenlandsAdresRegel2Migratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel3Migratie() {
        return buitenlandsAdresRegel3Migratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel4Migratie() {
        return buitenlandsAdresRegel4Migratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel5Migratie() {
        return buitenlandsAdresRegel5Migratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel6Migratie() {
        return buitenlandsAdresRegel6Migratie;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (soortMigratie != null) {
            attributen.add(soortMigratie);
        }
        if (redenWijzigingMigratie != null) {
            attributen.add(redenWijzigingMigratie);
        }
        if (aangeverMigratie != null) {
            attributen.add(aangeverMigratie);
        }
        if (landGebiedMigratie != null) {
            attributen.add(landGebiedMigratie);
        }
        if (buitenlandsAdresRegel1Migratie != null) {
            attributen.add(buitenlandsAdresRegel1Migratie);
        }
        if (buitenlandsAdresRegel2Migratie != null) {
            attributen.add(buitenlandsAdresRegel2Migratie);
        }
        if (buitenlandsAdresRegel3Migratie != null) {
            attributen.add(buitenlandsAdresRegel3Migratie);
        }
        if (buitenlandsAdresRegel4Migratie != null) {
            attributen.add(buitenlandsAdresRegel4Migratie);
        }
        if (buitenlandsAdresRegel5Migratie != null) {
            attributen.add(buitenlandsAdresRegel5Migratie);
        }
        if (buitenlandsAdresRegel6Migratie != null) {
            attributen.add(buitenlandsAdresRegel6Migratie);
        }
        return attributen;
    }

}
