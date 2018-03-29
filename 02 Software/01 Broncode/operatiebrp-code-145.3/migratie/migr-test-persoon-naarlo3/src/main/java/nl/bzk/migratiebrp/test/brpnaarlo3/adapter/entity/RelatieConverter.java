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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.GemeenteConverter;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.LandOfGebiedConverter;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.RedenBeeindigingRelatieConverter;

import org.springframework.stereotype.Component;

/**
 * Relatie converter.
 */
@Component
public final class RelatieConverter extends AbstractEntityHistorieConverter<RelatieHistorie> {

    private static final String HEADER_TYPE = "kern.relatie";
    private static final String HEADER_DATUM_AANVANG = "dataanv";
    private static final String HEADER_GEMEENTE_AANVANG = "gemaanv";
    private static final String HEADER_WOONPLAATSNAAM_AANVANG = "wplnaamaanv";
    private static final String HEADER_BUITENLANDSE_PLAATS_AANVANG = "blplaatsaanv";
    private static final String HEADER_BUITENLANDSE_REGIO_AANVANG = "blregioaanv";
    private static final String HEADER_OMSCHRIJVING_LOCATIE_AANVANG = "omslocaanv ";
    private static final String HEADER_LAND_OF_GEBIED_AANVANG = "landgebiedaanv";
    private static final String HEADER_REDEN_EINDE = "rdneinde";
    private static final String HEADER_DATUM_EINDE = "dateinde";
    private static final String HEADER_GEMEENTE_EINDE = "gemeinde";
    private static final String HEADER_WOONPLAATSNAAM_EINDE = "wplnaameinde";
    private static final String HEADER_BUITENLANDSE_PLAATS_EINDE = "blplaatseinde";
    private static final String HEADER_BUITENLANDSE_REGIO_EINDE = "blregioeinde";
    private static final String HEADER_OMSCHRIJVING_LOCATIE_EINDE = "omsloceinde";
    private static final String HEADER_LAND_OF_GEBIED_EINDE = "landgebiedeinde";

    @Inject
    private GemeenteConverter gemeenteConverter;
    @Inject
    private LandOfGebiedConverter landOfGebiedConverter;
    @Inject
    private RedenBeeindigingRelatieConverter redenBeeindigingRelatieConverter;

    private Integer id;
    private String buitenlandsePlaatsAanvang;
    private String buitenlandsePlaatsEinde;
    private String buitenlandseRegioAanvang;
    private String buitenlandseRegioEinde;
    private Integer datumAanvang;
    private Integer datumEinde;
    private String omschrijvingLocatieAanvang;
    private String omschrijvingLocatieEinde;
    private LandOfGebied landOfGebiedEinde;
    private LandOfGebied landOfGebiedAanvang;
    private Gemeente gemeenteEinde;
    private Gemeente gemeenteAanvang;
    private String woonplaatsnaamAanvang;
    private String woonplaatsnaamEinde;
    private RedenBeeindigingRelatie redenBeeindigingRelatie;
    private SoortRelatie soortRelatie;
    private Timestamp datumTijdRegistratie;
    private Timestamp datumTijdVerval;
    private BRPActie actieVerval;
    private BRPActie actieInhoud;
    private Relatie relatie;

    /**
     * Default constructor.
     */
    public RelatieConverter() {
        super(HEADER_TYPE);
    }

    @Override
    protected void maakHistorieEntity(final ConverterContext context) {
        relatie = new Relatie(soortRelatie);
        if (datumAanvang != null && landOfGebiedAanvang != null) {
            final RelatieHistorie historie = new RelatieHistorie(relatie);
            historie.setBuitenlandsePlaatsAanvang(buitenlandsePlaatsAanvang);
            historie.setBuitenlandsePlaatsEinde(buitenlandsePlaatsEinde);
            historie.setBuitenlandseRegioAanvang(buitenlandseRegioAanvang);
            historie.setBuitenlandseRegioEinde(buitenlandseRegioEinde);
            historie.setDatumAanvang(datumAanvang);
            historie.setDatumEinde(datumEinde);
            historie.setGemeenteAanvang(gemeenteAanvang);
            historie.setGemeenteEinde(gemeenteEinde);
            historie.setLandOfGebiedAanvang(landOfGebiedAanvang);
            historie.setLandOfGebiedEinde(landOfGebiedEinde);
            historie.setOmschrijvingLocatieAanvang(omschrijvingLocatieAanvang);
            historie.setOmschrijvingLocatieEinde(omschrijvingLocatieEinde);
            historie.setRedenBeeindigingRelatie(redenBeeindigingRelatie);
            historie.setWoonplaatsnaamAanvang(woonplaatsnaamAanvang);
            historie.setWoonplaatsnaamEinde(woonplaatsnaamEinde);
            historie.setActieInhoud(actieInhoud);
            historie.setActieVerval(actieVerval);
            historie.setDatumTijdRegistratie(datumTijdRegistratie);
            historie.setDatumTijdVerval(datumTijdVerval);

            relatie.addRelatieHistorie(historie);
        }
        context.storeRelatie(id, relatie);
    }

    @Override
    protected void resetConverter() {
        id = null;
        buitenlandsePlaatsAanvang = null;
        buitenlandsePlaatsEinde = null;
        buitenlandseRegioAanvang = null;
        buitenlandseRegioEinde = null;
        datumAanvang = null;
        datumEinde = null;
        omschrijvingLocatieAanvang = null;
        omschrijvingLocatieEinde = null;
        landOfGebiedEinde = null;
        landOfGebiedAanvang = null;
        gemeenteEinde = null;
        gemeenteAanvang = null;
        woonplaatsnaamAanvang = null;
        woonplaatsnaamEinde = null;
        redenBeeindigingRelatie = null;
        soortRelatie = null;
    }

    @Override
    public void convertInhoudelijk(final ConverterContext context, final String header, final String value) {
        switch (header) {
            case HEADER_TYPE:
                id = Integer.valueOf(value);
                break;
            case HEADER_SOORT:
                soortRelatie = SoortRelatie.parseId(Integer.valueOf(value));
                break;
            case HEADER_DATUM_AANVANG:
                datumAanvang = Integer.valueOf(value);
                break;
            case HEADER_GEMEENTE_AANVANG:
                gemeenteAanvang = gemeenteConverter.convert(value);
                break;
            case HEADER_WOONPLAATSNAAM_AANVANG:
                woonplaatsnaamAanvang = value;
                break;
            case HEADER_BUITENLANDSE_PLAATS_AANVANG:
                buitenlandsePlaatsAanvang = value;
                break;
            case HEADER_BUITENLANDSE_REGIO_AANVANG:
                buitenlandseRegioAanvang = value;
                break;
            case HEADER_OMSCHRIJVING_LOCATIE_AANVANG:
                omschrijvingLocatieAanvang = value;
                break;
            case HEADER_LAND_OF_GEBIED_AANVANG:
                landOfGebiedAanvang = landOfGebiedConverter.convert(value);
                break;
            case HEADER_REDEN_EINDE:
                redenBeeindigingRelatie = redenBeeindigingRelatieConverter.convert(value);
                break;
            case HEADER_DATUM_EINDE:
                datumEinde = Integer.valueOf(value);
                break;
            case HEADER_GEMEENTE_EINDE:
                gemeenteEinde = gemeenteConverter.convert(value);
                break;
            case HEADER_WOONPLAATSNAAM_EINDE:
                woonplaatsnaamEinde = value;
                break;
            case HEADER_BUITENLANDSE_PLAATS_EINDE:
                buitenlandsePlaatsEinde = value;
                break;
            case HEADER_BUITENLANDSE_REGIO_EINDE:
                buitenlandseRegioEinde = value;
                break;
            case HEADER_OMSCHRIJVING_LOCATIE_EINDE:
                omschrijvingLocatieEinde = value;
                break;
            case HEADER_LAND_OF_GEBIED_EINDE:
                landOfGebiedEinde = landOfGebiedConverter.convert(value);
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
    protected void vulActueelLaag() {
        final Set<RelatieHistorie> historieSet = relatie.getRelatieHistorieSet();
        if (!historieSet.isEmpty()) {
            vulActueelVanuit(relatie, getActueel(historieSet));
        }
    }
}
