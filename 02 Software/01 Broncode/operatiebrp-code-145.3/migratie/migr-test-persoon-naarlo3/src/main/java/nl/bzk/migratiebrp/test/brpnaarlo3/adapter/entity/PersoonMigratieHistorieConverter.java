/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import java.sql.Timestamp;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonMigratieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.LandOfGebiedConverter;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.SoortMigratieConverter;

import org.springframework.stereotype.Component;

/**
 * Immigratie converter.
 */
@Component
public final class PersoonMigratieHistorieConverter extends AbstractEntityHistorieConverter<PersoonMigratieHistorie> {
    private static final String HEADER_TYPE = "kern.his_persmigratie";
    private static final String HEADER_SOORT = "srtmigratie";
    private static final String HEADER_LAND_OF_GEBIED = "landgebiedmigratie";
    private static final String HEADER_BUITENLANDS_ADRES_REGEL_1 = "bladresregel1migratie";
    private static final String HEADER_BUITENLANDS_ADRES_REGEL_2 = "bladresregel2migratie";
    private static final String HEADER_BUITENLANDS_ADRES_REGEL_3 = "bladresregel3migratie";
    private static final String HEADER_BUITENLANDS_ADRES_REGEL_4 = "bladresregel4migratie";
    private static final String HEADER_BUITENLANDS_ADRES_REGEL_5 = "bladresregel5migratie";
    private static final String HEADER_BUITENLANDS_ADRES_REGEL_6 = "bladresregel6migratie";

    @Inject
    private LandOfGebiedConverter landOfGebiedConverter;
    @Inject
    private SoortMigratieConverter soortMigratieConverter;

    private Integer datumAanvangGeldigheid;
    private Integer datumEindeGeldigheid;
    private Timestamp datumTijdRegistratie;
    private Timestamp datumTijdVerval;
    private BRPActie actieAanpassingGeldigheid;
    private BRPActie actieVerval;
    private BRPActie actieInhoud;
    private LandOfGebied landOfGebied;
    private SoortMigratie soortMigratie;
    private String buitenlandsAdresRegel1;
    private String buitenlandsAdresRegel2;
    private String buitenlandsAdresRegel3;
    private String buitenlandsAdresRegel4;
    private String buitenlandsAdresRegel5;
    private String buitenlandsAdresRegel6;
    private Persoon persoon;

    /**
     * Default constructor.
     */
    public PersoonMigratieHistorieConverter() {
        super(HEADER_TYPE);
    }

    @Override
    protected void convertInhoudelijk(final ConverterContext context, final String header, final String value) {
        switch (header) {
            case HEADER_TYPE:
                break;
            case HEADER_PERSOON:
                persoon = context.getPersoon(Integer.parseInt(value));
                break;
            case HEADER_LAND_OF_GEBIED:
                landOfGebied = landOfGebiedConverter.convert(value);
                break;
            case HEADER_SOORT:
                soortMigratie = soortMigratieConverter.convert(value);
                break;
            case HEADER_BUITENLANDS_ADRES_REGEL_1:
                buitenlandsAdresRegel1 = value;
                break;
            case HEADER_BUITENLANDS_ADRES_REGEL_2:
                buitenlandsAdresRegel2 = value;
                break;
            case HEADER_BUITENLANDS_ADRES_REGEL_3:
                buitenlandsAdresRegel3 = value;
                break;
            case HEADER_BUITENLANDS_ADRES_REGEL_4:
                buitenlandsAdresRegel4 = value;
                break;
            case HEADER_BUITENLANDS_ADRES_REGEL_5:
                buitenlandsAdresRegel5 = value;
                break;
            case HEADER_BUITENLANDS_ADRES_REGEL_6:
                buitenlandsAdresRegel6 = value;
                break;
            case HEADER_DATUM_AANVANG_GELDIGHEID:
                datumAanvangGeldigheid = Integer.valueOf(value);
                break;
            case HEADER_DATUM_EINDE_GELDIGHEID:
                datumEindeGeldigheid = Integer.valueOf(value);
                break;
            case HEADER_TIJDSTIP_REGISTRATIE:
                datumTijdRegistratie = maakTimestamp(value);
                break;
            case HEADER_TIJDSTIP_VERVAL:
                datumTijdVerval = maakTimestamp(value);
                break;
            case HEADER_ACTIE_INHOUD:
                actieInhoud = context.getActie(Integer.parseInt(value));
                break;
            case HEADER_ACTIE_VERVAL:
                actieVerval = context.getActie(Integer.parseInt(value));
                break;
            case HEADER_ACTIE_AANPASSING_GELDIGHEID:
                actieAanpassingGeldigheid = context.getActie(Integer.parseInt(value));
                break;
            default:
                throw new OnbekendeHeaderException(header, getName());
        }
    }

    @Override
    protected void maakHistorieEntity(final ConverterContext context) {
        final PersoonMigratieHistorie historie = new PersoonMigratieHistorie(persoon, soortMigratie);
        historie.setActieAanpassingGeldigheid(actieAanpassingGeldigheid);
        historie.setActieInhoud(actieInhoud);
        historie.setActieVerval(actieVerval);
        historie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        historie.setDatumEindeGeldigheid(datumEindeGeldigheid);
        historie.setDatumTijdRegistratie(datumTijdRegistratie);
        historie.setDatumTijdVerval(datumTijdVerval);
        historie.setSoortMigratie(soortMigratie);
        historie.setLandOfGebied(landOfGebied);
        historie.setBuitenlandsAdresRegel1(buitenlandsAdresRegel1);
        historie.setBuitenlandsAdresRegel2(buitenlandsAdresRegel2);
        historie.setBuitenlandsAdresRegel3(buitenlandsAdresRegel3);
        historie.setBuitenlandsAdresRegel4(buitenlandsAdresRegel4);
        historie.setBuitenlandsAdresRegel5(buitenlandsAdresRegel5);
        historie.setBuitenlandsAdresRegel6(buitenlandsAdresRegel6);

        persoon.addPersoonMigratieHistorie(historie);
    }

    @Override
    protected void resetConverter() {
        datumAanvangGeldigheid = null;
        datumEindeGeldigheid = null;
        datumTijdRegistratie = null;
        datumTijdVerval = null;
        actieAanpassingGeldigheid = null;
        actieVerval = null;
        actieInhoud = null;
        landOfGebied = null;
        soortMigratie = null;
        buitenlandsAdresRegel1 = null;
        buitenlandsAdresRegel2 = null;
        buitenlandsAdresRegel3 = null;
        buitenlandsAdresRegel4 = null;
        buitenlandsAdresRegel5 = null;
        buitenlandsAdresRegel6 = null;
        persoon = null;
    }

    @Override
    protected void vulActueelLaag() {
        final Set<PersoonMigratieHistorie> historieSet = persoon.getPersoonMigratieHistorieSet();
        vulActueelVanuit(persoon, getActueel(historieSet));
    }
}
