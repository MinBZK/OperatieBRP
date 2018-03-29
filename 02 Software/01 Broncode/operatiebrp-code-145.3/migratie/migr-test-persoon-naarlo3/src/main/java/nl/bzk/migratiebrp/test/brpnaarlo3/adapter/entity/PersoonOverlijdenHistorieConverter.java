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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonOverlijdenHistorie;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.GemeenteConverter;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.LandOfGebiedConverter;
import org.springframework.stereotype.Component;

/**
 * Overlijden converter.
 */
@Component
public final class PersoonOverlijdenHistorieConverter extends AbstractEntityHistorieConverter<PersoonOverlijdenHistorie> {

    private static final String HEADER_TYPE = "kern.his_persoverlijden";
    private static final String HEADER_DATUM_OVERLIJDEN = "datoverlijden";
    private static final String HEADER_GEMEENTE_OVERLIJDEN = "gemoverlijden";
    private static final String HEADER_WOONPLAATSNAAM_OVERLIJDEN = "wplnaamoverlijden";
    private static final String HEADER_BUITENLANDSE_PLAATS_OVERLIJDEN = "blplaatsoverlijden";
    private static final String HEADER_BUITENLANDSE_REGIO_OVERLIJDEN = "blregiooverlijden";
    private static final String HEADER_OMSCHRIJVING_LOCATIE_OVERLIJDEN = "omslocoverlijden";
    private static final String HEADER_LAND_OF_GEBIED_OVERLIJDEN = "landgebiedoverlijden";

    @Inject
    private GemeenteConverter gemeenteConverter;
    @Inject
    private LandOfGebiedConverter landOfGebiedConverter;

    private String buitenlandsePlaatsOverlijden;
    private String buitenlandseRegioOverlijden;
    private Integer datumOverlijden;
    private String omschrijvingLocatieOverlijden;
    private Timestamp datumTijdRegistratie;
    private Timestamp datumTijdVerval;
    private BRPActie actieVerval;
    private BRPActie actieInhoud;
    private LandOfGebied landOfGebiedOverlijden;
    private Gemeente gemeenteOverlijden;
    private Persoon persoon;
    private String woonplaatsnaamOverlijden;

    /**
     * Default constructor.
     */
    public PersoonOverlijdenHistorieConverter() {
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
            case HEADER_DATUM_OVERLIJDEN:
                datumOverlijden = Integer.valueOf(value);
                break;
            case HEADER_GEMEENTE_OVERLIJDEN:
                gemeenteOverlijden = gemeenteConverter.convert(value);
                break;
            case HEADER_WOONPLAATSNAAM_OVERLIJDEN:
                woonplaatsnaamOverlijden = value;
                break;
            case HEADER_BUITENLANDSE_PLAATS_OVERLIJDEN:
                buitenlandsePlaatsOverlijden = value;
                break;
            case HEADER_BUITENLANDSE_REGIO_OVERLIJDEN:
                buitenlandseRegioOverlijden = value;
                break;
            case HEADER_OMSCHRIJVING_LOCATIE_OVERLIJDEN:
                omschrijvingLocatieOverlijden = value;
                break;
            case HEADER_LAND_OF_GEBIED_OVERLIJDEN:
                landOfGebiedOverlijden = landOfGebiedConverter.convert(value);
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
            default:
                throw new OnbekendeHeaderException(header, getName());
        }
    }

    @Override
    protected void maakHistorieEntity(final ConverterContext context) {
        final PersoonOverlijdenHistorie historie = new PersoonOverlijdenHistorie(persoon, datumOverlijden, landOfGebiedOverlijden);
        historie.setActieInhoud(actieInhoud);
        historie.setActieVerval(actieVerval);
        historie.setBuitenlandsePlaatsOverlijden(buitenlandsePlaatsOverlijden);
        historie.setBuitenlandseRegioOverlijden(buitenlandseRegioOverlijden);
        historie.setDatumTijdRegistratie(datumTijdRegistratie);
        historie.setDatumTijdVerval(datumTijdVerval);
        historie.setGemeente(gemeenteOverlijden);
        historie.setOmschrijvingLocatieOverlijden(omschrijvingLocatieOverlijden);
        historie.setWoonplaatsnaamOverlijden(woonplaatsnaamOverlijden);

        persoon.addPersoonOverlijdenHistorie(historie);
    }

    @Override
    protected void resetConverter() {
        buitenlandsePlaatsOverlijden = null;
        buitenlandseRegioOverlijden = null;
        datumOverlijden = null;
        omschrijvingLocatieOverlijden = null;
        datumTijdRegistratie = null;
        datumTijdVerval = null;
        actieVerval = null;
        actieInhoud = null;
        landOfGebiedOverlijden = null;
        gemeenteOverlijden = null;
        persoon = null;
        woonplaatsnaamOverlijden = null;
    }

    @Override
    protected void vulActueelLaag() {
        final Set<PersoonOverlijdenHistorie> historieSet = persoon.getPersoonOverlijdenHistorieSet();
        vulActueelVanuit(persoon, getActueel(historieSet));
    }
}
