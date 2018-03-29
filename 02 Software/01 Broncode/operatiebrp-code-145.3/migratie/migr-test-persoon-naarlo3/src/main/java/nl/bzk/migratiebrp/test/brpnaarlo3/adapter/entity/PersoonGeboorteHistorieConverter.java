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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.GemeenteConverter;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.LandOfGebiedConverter;
import org.springframework.stereotype.Component;

/**
 * Geboorte converter.
 */
@Component
public final class PersoonGeboorteHistorieConverter extends AbstractEntityHistorieConverter<PersoonGeboorteHistorie> {

    private static final String HEADER_TYPE = "kern.his_persgeboorte";
    private static final String HEADER_DATUM_GEBOORTE = "datgeboorte";
    private static final String HEADER_GEMEENTE_GEBOORTE = "gemgeboorte";
    private static final String HEADER_WOONPLAATSNAAM_GEBOORTE = "wplnaamgeboorte";
    private static final String HEADER_BUITENLANDSE_PLAATS_GEBOORTE = "blplaatsgeboorte";
    private static final String HEADER_BUITENLANDSE_REGIO_GEBOORTE = "blregiogeboorte";
    private static final String HEADER_OMSCHRIJVING_LOCATIE_GEBOORTE = "omslocgeboorte";
    private static final String HEADER_LAND_OF_GEBIED_GEBOORTE = "landgebiedgeboorte";

    @Inject
    private GemeenteConverter gemeenteConverter;
    @Inject
    private LandOfGebiedConverter landOfGebiedConverter;

    private String buitenlandsePlaatsGeboorte;
    private String buitenlandseRegioGeboorte;
    private Integer datumGeboorte;
    private String omschrijvingGeboortelocatie;
    private Timestamp datumTijdRegistratie;
    private Timestamp datumTijdVerval;
    private BRPActie actieVerval;
    private BRPActie actieInhoud;
    private LandOfGebied landOfGebiedGeboorte;
    private Gemeente gemeenteGeboorte;
    private Persoon persoon;
    private String woonplaatsnaamGeboorte;

    /**
     * Default constructor.
     */
    public PersoonGeboorteHistorieConverter() {
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
            case HEADER_DATUM_GEBOORTE:
                datumGeboorte = Integer.valueOf(value);
                break;
            case HEADER_GEMEENTE_GEBOORTE:
                gemeenteGeboorte = gemeenteConverter.convert(value);
                break;
            case HEADER_WOONPLAATSNAAM_GEBOORTE:
                woonplaatsnaamGeboorte = value;
                break;
            case HEADER_BUITENLANDSE_PLAATS_GEBOORTE:
                buitenlandsePlaatsGeboorte = value;
                break;
            case HEADER_BUITENLANDSE_REGIO_GEBOORTE:
                buitenlandseRegioGeboorte = value;
                break;
            case HEADER_OMSCHRIJVING_LOCATIE_GEBOORTE:
                omschrijvingGeboortelocatie = value;
                break;
            case HEADER_LAND_OF_GEBIED_GEBOORTE:
                landOfGebiedGeboorte = landOfGebiedConverter.convert(value);
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
        final PersoonGeboorteHistorie persoonGeboorteHistorie = new PersoonGeboorteHistorie(persoon, datumGeboorte, landOfGebiedGeboorte);
        persoonGeboorteHistorie.setActieInhoud(actieInhoud);
        persoonGeboorteHistorie.setActieVerval(actieVerval);
        persoonGeboorteHistorie.setBuitenlandsePlaatsGeboorte(buitenlandsePlaatsGeboorte);
        persoonGeboorteHistorie.setBuitenlandseRegioGeboorte(buitenlandseRegioGeboorte);
        persoonGeboorteHistorie.setDatumTijdRegistratie(datumTijdRegistratie);
        persoonGeboorteHistorie.setDatumTijdVerval(datumTijdVerval);
        persoonGeboorteHistorie.setGemeente(gemeenteGeboorte);
        persoonGeboorteHistorie.setOmschrijvingGeboortelocatie(omschrijvingGeboortelocatie);
        persoonGeboorteHistorie.setWoonplaatsnaamGeboorte(woonplaatsnaamGeboorte);

        persoon.addPersoonGeboorteHistorie(persoonGeboorteHistorie);
    }

    @Override
    protected void resetConverter() {
        buitenlandsePlaatsGeboorte = null;
        buitenlandseRegioGeboorte = null;
        datumGeboorte = null;
        omschrijvingGeboortelocatie = null;
        datumTijdRegistratie = null;
        datumTijdVerval = null;
        actieVerval = null;
        actieInhoud = null;
        landOfGebiedGeboorte = null;
        gemeenteGeboorte = null;
        persoon = null;
        woonplaatsnaamGeboorte = null;
    }

    @Override
    protected void vulActueelLaag() {
        final Set<PersoonGeboorteHistorie> historieSet = persoon.getPersoonGeboorteHistorieSet();
        vulActueelVanuit(persoon, getActueel(historieSet));
    }
}
