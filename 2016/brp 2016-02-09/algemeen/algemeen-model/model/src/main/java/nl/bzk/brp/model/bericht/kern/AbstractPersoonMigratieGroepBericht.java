/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdresregelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratieAttribuut;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonMigratieGroepBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonMigratieGroepBericht extends AbstractMaterieleHistorieGroepBericht implements Groep, PersoonMigratieGroepBasis,
        MetaIdentificeerbaar
{

    private static final Integer META_ID = 3790;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(10881, 11277, 11278, 3579, 10882, 10883, 10884, 10885, 10886, 10887);
    private SoortMigratieAttribuut soortMigratie;
    private String redenWijzigingMigratieCode;
    private RedenWijzigingVerblijfAttribuut redenWijzigingMigratie;
    private String aangeverMigratieCode;
    private AangeverAttribuut aangeverMigratie;
    private String landGebiedMigratieCode;
    private LandGebiedAttribuut landGebiedMigratie;
    private AdresregelAttribuut buitenlandsAdresRegel1Migratie;
    private AdresregelAttribuut buitenlandsAdresRegel2Migratie;
    private AdresregelAttribuut buitenlandsAdresRegel3Migratie;
    private AdresregelAttribuut buitenlandsAdresRegel4Migratie;
    private AdresregelAttribuut buitenlandsAdresRegel5Migratie;
    private AdresregelAttribuut buitenlandsAdresRegel6Migratie;

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortMigratieAttribuut getSoortMigratie() {
        return soortMigratie;
    }

    /**
     * Retourneert Reden wijziging migratie van Migratie.
     *
     * @return Reden wijziging migratie.
     */
    public String getRedenWijzigingMigratieCode() {
        return redenWijzigingMigratieCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenWijzigingVerblijfAttribuut getRedenWijzigingMigratie() {
        return redenWijzigingMigratie;
    }

    /**
     * Retourneert Aangever migratie van Migratie.
     *
     * @return Aangever migratie.
     */
    public String getAangeverMigratieCode() {
        return aangeverMigratieCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AangeverAttribuut getAangeverMigratie() {
        return aangeverMigratie;
    }

    /**
     * Retourneert Land/gebied migratie van Migratie.
     *
     * @return Land/gebied migratie.
     */
    public String getLandGebiedMigratieCode() {
        return landGebiedMigratieCode;
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
     * Zet Soort migratie van Migratie.
     *
     * @param soortMigratie Soort migratie.
     */
    public void setSoortMigratie(final SoortMigratieAttribuut soortMigratie) {
        this.soortMigratie = soortMigratie;
    }

    /**
     * Zet Reden wijziging migratie van Migratie.
     *
     * @param redenWijzigingMigratieCode Reden wijziging migratie.
     */
    public void setRedenWijzigingMigratieCode(final String redenWijzigingMigratieCode) {
        this.redenWijzigingMigratieCode = redenWijzigingMigratieCode;
    }

    /**
     * Zet Reden wijziging migratie van Migratie.
     *
     * @param redenWijzigingMigratie Reden wijziging migratie.
     */
    public void setRedenWijzigingMigratie(final RedenWijzigingVerblijfAttribuut redenWijzigingMigratie) {
        this.redenWijzigingMigratie = redenWijzigingMigratie;
    }

    /**
     * Zet Aangever migratie van Migratie.
     *
     * @param aangeverMigratieCode Aangever migratie.
     */
    public void setAangeverMigratieCode(final String aangeverMigratieCode) {
        this.aangeverMigratieCode = aangeverMigratieCode;
    }

    /**
     * Zet Aangever migratie van Migratie.
     *
     * @param aangeverMigratie Aangever migratie.
     */
    public void setAangeverMigratie(final AangeverAttribuut aangeverMigratie) {
        this.aangeverMigratie = aangeverMigratie;
    }

    /**
     * Zet Land/gebied migratie van Migratie.
     *
     * @param landGebiedMigratieCode Land/gebied migratie.
     */
    public void setLandGebiedMigratieCode(final String landGebiedMigratieCode) {
        this.landGebiedMigratieCode = landGebiedMigratieCode;
    }

    /**
     * Zet Land/gebied migratie van Migratie.
     *
     * @param landGebiedMigratie Land/gebied migratie.
     */
    public void setLandGebiedMigratie(final LandGebiedAttribuut landGebiedMigratie) {
        this.landGebiedMigratie = landGebiedMigratie;
    }

    /**
     * Zet Buitenlands adres regel 1 migratie van Migratie.
     *
     * @param buitenlandsAdresRegel1Migratie Buitenlands adres regel 1 migratie.
     */
    public void setBuitenlandsAdresRegel1Migratie(final AdresregelAttribuut buitenlandsAdresRegel1Migratie) {
        this.buitenlandsAdresRegel1Migratie = buitenlandsAdresRegel1Migratie;
    }

    /**
     * Zet Buitenlands adres regel 2 migratie van Migratie.
     *
     * @param buitenlandsAdresRegel2Migratie Buitenlands adres regel 2 migratie.
     */
    public void setBuitenlandsAdresRegel2Migratie(final AdresregelAttribuut buitenlandsAdresRegel2Migratie) {
        this.buitenlandsAdresRegel2Migratie = buitenlandsAdresRegel2Migratie;
    }

    /**
     * Zet Buitenlands adres regel 3 migratie van Migratie.
     *
     * @param buitenlandsAdresRegel3Migratie Buitenlands adres regel 3 migratie.
     */
    public void setBuitenlandsAdresRegel3Migratie(final AdresregelAttribuut buitenlandsAdresRegel3Migratie) {
        this.buitenlandsAdresRegel3Migratie = buitenlandsAdresRegel3Migratie;
    }

    /**
     * Zet Buitenlands adres regel 4 migratie van Migratie.
     *
     * @param buitenlandsAdresRegel4Migratie Buitenlands adres regel 4 migratie.
     */
    public void setBuitenlandsAdresRegel4Migratie(final AdresregelAttribuut buitenlandsAdresRegel4Migratie) {
        this.buitenlandsAdresRegel4Migratie = buitenlandsAdresRegel4Migratie;
    }

    /**
     * Zet Buitenlands adres regel 5 migratie van Migratie.
     *
     * @param buitenlandsAdresRegel5Migratie Buitenlands adres regel 5 migratie.
     */
    public void setBuitenlandsAdresRegel5Migratie(final AdresregelAttribuut buitenlandsAdresRegel5Migratie) {
        this.buitenlandsAdresRegel5Migratie = buitenlandsAdresRegel5Migratie;
    }

    /**
     * Zet Buitenlands adres regel 6 migratie van Migratie.
     *
     * @param buitenlandsAdresRegel6Migratie Buitenlands adres regel 6 migratie.
     */
    public void setBuitenlandsAdresRegel6Migratie(final AdresregelAttribuut buitenlandsAdresRegel6Migratie) {
        this.buitenlandsAdresRegel6Migratie = buitenlandsAdresRegel6Migratie;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getMetaId() {
        return META_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean bevatElementMetMetaId(final Integer metaId) {
        return ONDERLIGGENDE_ATTRIBUTEN.contains(metaId);
    }

}
