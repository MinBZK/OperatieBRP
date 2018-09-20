/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonIndicatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortIndicatie;

import org.springframework.stereotype.Component;

/**
 * Map een BRP database Persoon naar een conversiemodel BRP persoonslijst.
 */
// CHECKSTYLE:OFF - Fan-Out complexity - Meerdere specifieke mappers gekoppeld
@Component
public final class BrpPersoonslijstMapper {
    // CHECKSTYLE:ON

    @Inject
    private BrpAanschrijvingMapper brpAanschrijvingMapper;
    @Inject
    private BrpAdresMapper brpAdresMapper;
    @Inject
    private BrpAfgeleidAdministratiefMapper brpAfgeleidAdministratiefMapper;
    @Inject
    private BrpBehandeldAlsNederlanderIndicatieMapper brpBehandeldAlsNederlanderIndicatieMapper;
    @Inject
    private BrpBelemmeringVerstrekkingReisdocumentIndicatieMapper brpBelemmeringVerstrekkingReisdocumentIndicatieMapper;
    @Inject
    private BrpBezitBuitenlandsReisdocumentIndicatieMapper brpBezitBuitenlandsReisdocumentIndicatieMapper;
    @Inject
    private BrpBijhoudingsgemeenteMapper brpBijhoudingsgemeenteMapper;
    @Inject
    private BrpBijhoudingsverantwoordelijkheidMapper brpBijhoudingsverantwoordelijkheidMapper;
    @Inject
    private BrpDerdeHeeftGezagIndicatieMapper brpDerdeHeeftGezagIndicatieMapper;
    @Inject
    private BrpEuropeseVerkiezingenMapper brpEuropeseVerkiezingenMapper;
    @Inject
    private BrpGeboorteMapper brpGeboorteMapper;
    @Inject
    private BrpGeprivilegieerdeIndicatieMapper brpGeprivilegieerdeIndicatieMapper;
    @Inject
    private BrpGeslachtsaanduidingMapper brpGeslachtsaanduidingMapper;
    @Inject
    private BrpGeslachtsnaamcomponentMapper brpGeslachtsnaamcomponentMapper;
    @Inject
    private BrpIdentificatienummersMapper brpIdentificatienummersMapper;
    @Inject
    private BrpImmigratieMapper brpImmigratieMapper;
    @Inject
    private BrpInschrijvingMapper brpInschrijvingMapper;
    @Inject
    private BrpNationaliteitMapper brpNationaliteitMapper;
    @Inject
    private BrpOnderCurateleIndicatieMapper brpOnderCurateleIndicatieMapper;
    @Inject
    private BrpOpschortingMapper brpOpschortingMapper;
    @Inject
    private BrpOverlijdenMapper brpOverlijdenMapper;
    @Inject
    private BrpPersoonskaartMapper brpPersoonskaartMapper;
    @Inject
    private BrpReisdocumentMapper brpReisdocumentMapper;
    @Inject
    private BrpRelatieMapper brpRelatieMapper;
    @Inject
    private BrpSamengesteldeNaamMapper brpSamengesteldeNaamMapper;
    @Inject
    private BrpStatenloosIndicatieMapper brpStatenloosIndicatieMapper;
    @Inject
    private BrpUitsluitingNederlandsKiesrechtMapper brpUitsluitingNederlandsKiesrechtMapper;
    @Inject
    private BrpVastgesteldNietNederlanderIndicatieMapper brpVastgesteldNietNederlanderIndicatieMapper;
    @Inject
    private BrpVerblijfsrechtMapper brpVerblijfsrechtMapper;
    @Inject
    private BrpVerstrekkingsbeperkingMapper brpVerstrekkingsbeperkingMapper;
    @Inject
    private BrpVoornaamMapper brpVoornaamMapper;

    /**
     * Map een database persoon naar het BRP conversiemodel.
     * 
     * @param persoon
     *            database persoon
     * @return BRP conversiemodel persoon
     */
    public BrpPersoonslijst mapNaarMigratie(final Persoon persoon) {
        if (persoon == null) {
            return null;
        }

        final BrpPersoonslijstBuilder plBuilder = new BrpPersoonslijstBuilder();
        mapBasis(persoon, plBuilder);
        mapIndicaties(persoon, plBuilder);
        mapRelaties(persoon, plBuilder);

        return plBuilder.build();
    }

    private void mapBasis(final Persoon persoon, final BrpPersoonslijstBuilder plBuilder) {
        plBuilder.aanschrijvingStapel(brpAanschrijvingMapper.map(persoon.getPersoonAanschrijvingHistorieSet()));
        plBuilder.adresStapel(brpAdresMapper.map(persoon.getPersoonAdresSet()));
        plBuilder.afgeleidAdministratiefStapel(brpAfgeleidAdministratiefMapper.map(persoon));
        plBuilder.bijhoudingsgemeenteStapel(brpBijhoudingsgemeenteMapper.map(persoon
                .getPersoonBijhoudingsgemeenteHistorieSet()));
        plBuilder.bijhoudingsverantwoordelijkheidStapel(brpBijhoudingsverantwoordelijkheidMapper.map(persoon
                .getPersoonBijhoudingsverantwoordelijkheidHistorieSet()));
        plBuilder.europeseVerkiezingenStapel(brpEuropeseVerkiezingenMapper.map(persoon
                .getPersoonEUVerkiezingenHistorieSet()));
        plBuilder.geboorteStapel(brpGeboorteMapper.map(persoon.getPersoonGeboorteHistorieSet()));
        plBuilder.geslachtsaanduidingStapel(brpGeslachtsaanduidingMapper.map(persoon
                .getPersoonGeslachtsaanduidingHistorieSet()));
        plBuilder.geslachtsnaamcomponentStapels(brpGeslachtsnaamcomponentMapper.map(persoon
                .getPersoonGeslachtsnaamcomponentSet()));
        plBuilder.identificatienummersStapel(brpIdentificatienummersMapper.map(persoon.getPersoonIDHistorieSet()));
        plBuilder.immigratieStapel(brpImmigratieMapper.map(persoon.getPersoonImmigratieHistorieSet()));
        plBuilder.inschrijvingStapel(brpInschrijvingMapper.map(persoon.getPersoonInschrijvingHistorieSet()));
        plBuilder.nationaliteitStapels(brpNationaliteitMapper.map(persoon.getPersoonNationaliteitSet()));
        plBuilder.opschortingStapel(brpOpschortingMapper.map(persoon.getPersoonOpschortingHistorieSet()));
        plBuilder.overlijdenStapel(brpOverlijdenMapper.map(persoon.getPersoonOverlijdenHistorieSet()));
        plBuilder.persoonskaartStapel(brpPersoonskaartMapper.map(persoon.getPersoonPersoonskaartHistorieSet()));
        plBuilder.reisdocumentStapels(brpReisdocumentMapper.map(persoon.getPersoonReisdocumentSet()));
        plBuilder.samengesteldeNaamStapel(brpSamengesteldeNaamMapper.map(persoon
                .getPersoonSamengesteldeNaamHistorieSet()));
        plBuilder.uitsluitingNederlandsKiesrechtStapel(brpUitsluitingNederlandsKiesrechtMapper.map(persoon
                .getPersoonUitsluitingNLKiesrechtHistorieSet()));
        plBuilder.verblijfsrechtStapel(brpVerblijfsrechtMapper.map(persoon.getPersoonVerblijfsrechtHistorieSet()));
        plBuilder.voornaamStapels(brpVoornaamMapper.map(persoon.getPersoonVoornaamSet()));
    }

    private void mapIndicaties(final Persoon persoon, final BrpPersoonslijstBuilder plBuilder) {
        // Indicaties
        for (final PersoonIndicatie persoonIndicatie : persoon.getPersoonIndicatieSet()) {
            final SoortIndicatie soortIndicatie = persoonIndicatie.getSoortIndicatie();
            switch (soortIndicatie) {
                case DERDE_HEEFT_GEZAG:
                    plBuilder.derdeHeeftGezagIndicatieStapel(brpDerdeHeeftGezagIndicatieMapper.map(persoonIndicatie
                            .getPersoonIndicatieHistorieSet()));
                    break;
                case ONDER_CURATELE:
                    plBuilder.onderCurateleIndicatieStapel(brpOnderCurateleIndicatieMapper.map(persoonIndicatie
                            .getPersoonIndicatieHistorieSet()));
                    break;
                case VERSTREKKINGSBEPERKING:
                    plBuilder.verstrekkingsbeperkingStapel(brpVerstrekkingsbeperkingMapper.map(persoonIndicatie
                            .getPersoonIndicatieHistorieSet()));
                    break;
                case GEPRIVILEGIEERDE:
                    plBuilder.geprivilegieerdeIndicatieStapel(brpGeprivilegieerdeIndicatieMapper.map(persoonIndicatie
                            .getPersoonIndicatieHistorieSet()));
                    break;
                case VASTGESTELD_NIET_NEDERLANDER:
                    plBuilder.vastgesteldNietNederlanderIndicatieStapel(brpVastgesteldNietNederlanderIndicatieMapper
                            .map(persoonIndicatie.getPersoonIndicatieHistorieSet()));
                    break;
                case BEHANDELD_ALS_NEDERLANDER:
                    plBuilder.behandeldAlsNederlanderIndicatieStapel(brpBehandeldAlsNederlanderIndicatieMapper
                            .map(persoonIndicatie.getPersoonIndicatieHistorieSet()));
                    break;
                case BELEMMERING_VERSTREKKING_REISDOCUMENT:
                    plBuilder
                            .belemmeringVerstrekkingReisdocumentIndicatieStapel(brpBelemmeringVerstrekkingReisdocumentIndicatieMapper
                                    .map(persoonIndicatie.getPersoonIndicatieHistorieSet()));
                    break;
                case BEZIT_BUITENLANDS_REISDOCUMENT:
                    plBuilder
                            .bezitBuitenlandsReisdocumentIndicatieStapel(brpBezitBuitenlandsReisdocumentIndicatieMapper
                                    .map(persoonIndicatie.getPersoonIndicatieHistorieSet()));
                    break;
                case STATENLOOS:
                    plBuilder.statenloosIndicatieStapel(brpStatenloosIndicatieMapper.map(persoonIndicatie
                            .getPersoonIndicatieHistorieSet()));
                    break;
                default:
                    throw new IllegalArgumentException("Onbekende soort indicatie: " + soortIndicatie);
            }
        }
    }

    private void mapRelaties(final Persoon persoon, final BrpPersoonslijstBuilder plBuilder) {
        // Relaties
        plBuilder.relaties(brpRelatieMapper.map(persoon.getBetrokkenheidSet(),
                persoon.getMultiRealiteitRegelGeldigVoorPersoonSet()));

    }
}
