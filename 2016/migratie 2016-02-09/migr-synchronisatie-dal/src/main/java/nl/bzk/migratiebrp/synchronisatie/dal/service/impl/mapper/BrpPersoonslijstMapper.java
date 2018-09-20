/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpIndicatieGroepInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIndicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIndicatieHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortIndicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map een BRP database Persoon naar een conversiemodel BRP persoonslijst.
 */
@Component
public final class BrpPersoonslijstMapper {

    @Inject
    private BrpNaamgebruikMapper brpNaamgebruikMapper;
    @Inject
    private BrpAdresMapper brpAdresMapper;
    @Inject
    private BrpPersoonAfgeleidAdministratiefMapper brpPersoonAfgeleidAdministratiefMapper;
    @Inject
    private BrpBehandeldAlsNederlanderIndicatieMapper brpBehandeldAlsNederlanderIndicatieMapper;
    @Inject
    private BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper brpSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper;
    @Inject
    private BrpBijhoudingMapper brpBijhoudingMapper;
    @Inject
    private BrpDerdeHeeftGezagIndicatieMapper brpDerdeHeeftGezagIndicatieMapper;
    @Inject
    private BrpDeelnameEuVerkiezingenMapper brpDeelnameEuVerkiezingenMapper;
    @Inject
    private BrpGeboorteMapper brpGeboorteMapper;
    @Inject
    private BrpGeslachtsaanduidingMapper brpGeslachtsaanduidingMapper;
    @Inject
    private BrpGeslachtsnaamcomponentMapper brpGeslachtsnaamcomponentMapper;
    @Inject
    private BrpIdentificatienummersMapper brpIdentificatienummersMapper;
    @Inject
    private BrpMigratieMapper brpMigratieMapper;
    @Inject
    private BrpInschrijvingMapper brpInschrijvingMapper;
    @Inject
    private BrpNationaliteitMapper brpNationaliteitMapper;
    @Inject
    private BrpNummerverwijzingMapper brpNummerverwijzingMapper;
    @Inject
    private BrpOnderCurateleIndicatieMapper brpOnderCurateleIndicatieMapper;
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
    private BrpStaatloosIndicatieMapper brpStaatloosIndicatieMapper;
    @Inject
    private BrpUitsluitingKiesrechtMapper brpUitsluitingKiesrechtMapper;
    @Inject
    private BrpVastgesteldNietNederlanderIndicatieMapper brpVastgesteldNietNederlanderIndicatieMapper;
    @Inject
    private BrpVerblijfsrechtMapper brpVerblijfsrechtMapper;
    @Inject
    private BrpVerificatieMapper brpVerificatieMapper;
    @Inject
    private BrpBijzondereVerblijfsrechtelijkePositieIndicatieMapper brpBijzondereVerblijfsrechtelijkePositieMapper;
    @Inject
    private BrpVerstrekkingsbeperkingIndicatieMapper brpVerstrekkingsbeperkingIndicatieMapper;
    @Inject
    private BrpVoornaamMapper brpVoornaamMapper;
    @Inject
    private BrpIstOuder1Mapper brpIstOuder1Mapper;
    @Inject
    private BrpIstOuder2Mapper brpIstOuder2Mapper;
    @Inject
    private BrpIstHuwelijkOfGpMapper brpIstHuwelijkOfGpMapper;
    @Inject
    private BrpIstKindMapper brpIstKindMapper;
    @Inject
    private BrpIstGezagsverhoudingMapper brpIstGezagsverhoudingMapper;

    /**
     * Map een database persoon naar het BRP conversiemodel.
     *
     * @param persoon
     *            database persoon
     * @param brpOnderzoekMapper
     *            De mapper voor onderzoeken
     * @return BRP conversiemodel persoon
     */
    public BrpPersoonslijst mapNaarMigratie(final Persoon persoon, final BrpOnderzoekMapper brpOnderzoekMapper) {
        if (persoon == null) {
            return null;
        }

        final BrpPersoonslijstBuilder plBuilder = new BrpPersoonslijstBuilder();
        plBuilder.persoonId(persoon.getId());
        mapBasis(persoon, plBuilder, brpOnderzoekMapper);
        mapIndicaties(persoon, plBuilder, brpOnderzoekMapper);
        mapRelaties(persoon, plBuilder, brpOnderzoekMapper);

        return plBuilder.build();
    }

    private void mapBasis(final Persoon persoon, final BrpPersoonslijstBuilder plBuilder, final BrpOnderzoekMapper brpOnderzoekMapper) {
        plBuilder.naamgebruikStapel(brpNaamgebruikMapper.map(persoon.getPersoonNaamgebruikHistorieSet(), brpOnderzoekMapper));
        plBuilder.adresStapel(brpAdresMapper.map(persoon.getPersoonAdresSet(), brpOnderzoekMapper));
        plBuilder.persoonAfgeleidAdministratiefStapel(
            brpPersoonAfgeleidAdministratiefMapper.map(persoon.getPersoonAfgeleidAdministratiefHistorieSet(), brpOnderzoekMapper));
        plBuilder.bijhoudingStapel(brpBijhoudingMapper.map(persoon.getPersoonBijhoudingHistorieSet(), brpOnderzoekMapper));
        plBuilder.deelnameEuVerkiezingenStapel(
            brpDeelnameEuVerkiezingenMapper.map(persoon.getPersoonDeelnameEuVerkiezingenHistorieSet(), brpOnderzoekMapper));
        plBuilder.geboorteStapel(brpGeboorteMapper.map(persoon.getPersoonGeboorteHistorieSet(), brpOnderzoekMapper));
        plBuilder.geslachtsaanduidingStapel(brpGeslachtsaanduidingMapper.map(persoon.getPersoonGeslachtsaanduidingHistorieSet(), brpOnderzoekMapper));
        plBuilder.geslachtsnaamcomponentStapels(brpGeslachtsnaamcomponentMapper.map(persoon.getPersoonGeslachtsnaamcomponentSet(), brpOnderzoekMapper));
        plBuilder.identificatienummersStapel(brpIdentificatienummersMapper.map(persoon.getPersoonIDHistorieSet(), brpOnderzoekMapper));
        plBuilder.migratieStapel(brpMigratieMapper.map(persoon.getPersoonMigratieHistorieSet(), brpOnderzoekMapper));
        plBuilder.inschrijvingStapel(brpInschrijvingMapper.map(persoon.getPersoonInschrijvingHistorieSet(), brpOnderzoekMapper));
        plBuilder.nationaliteitStapels(brpNationaliteitMapper.map(persoon.getPersoonNationaliteitSet(), brpOnderzoekMapper));
        plBuilder.nummerverwijzingStapel(brpNummerverwijzingMapper.map(persoon.getPersoonNummerverwijzingHistorieSet(), brpOnderzoekMapper));
        plBuilder.overlijdenStapel(brpOverlijdenMapper.map(persoon.getPersoonOverlijdenHistorieSet(), brpOnderzoekMapper));
        plBuilder.persoonskaartStapel(brpPersoonskaartMapper.map(persoon.getPersoonPersoonskaartHistorieSet(), brpOnderzoekMapper));
        plBuilder.reisdocumentStapels(brpReisdocumentMapper.map(persoon.getPersoonReisdocumentSet(), brpOnderzoekMapper));
        plBuilder.samengesteldeNaamStapel(brpSamengesteldeNaamMapper.map(persoon.getPersoonSamengesteldeNaamHistorieSet(), brpOnderzoekMapper));
        plBuilder.uitsluitingKiesrechtStapel(brpUitsluitingKiesrechtMapper.map(persoon.getPersoonUitsluitingKiesrechtHistorieSet(), brpOnderzoekMapper));
        plBuilder.verblijfsrechtStapel(brpVerblijfsrechtMapper.map(persoon.getPersoonVerblijfsrechtHistorieSet(), brpOnderzoekMapper));
        plBuilder.voornaamStapels(brpVoornaamMapper.map(persoon.getPersoonVoornaamSet(), brpOnderzoekMapper));
        plBuilder.verificatieStapels(brpVerificatieMapper.map(persoon.getPersoonVerificatieSet(), brpOnderzoekMapper));

        // ist stapels
        plBuilder.istOuder1Stapel(brpIstOuder1Mapper.map(persoon.getStapels()));
        plBuilder.istOuder2Stapel(brpIstOuder2Mapper.map(persoon.getStapels()));
        plBuilder.istHuwelijkOfGpStapels(brpIstHuwelijkOfGpMapper.map(persoon.getStapels()));
        plBuilder.istKindStapels(brpIstKindMapper.map(persoon.getStapels()));
        plBuilder.istGezagsverhoudingStapel(brpIstGezagsverhoudingMapper.map(persoon.getStapels()));
    }

    private void mapIndicaties(final Persoon persoon, final BrpPersoonslijstBuilder plBuilder, final BrpOnderzoekMapper brpOnderzoekMapper) {
        // Indicaties
        for (final PersoonIndicatie persoonIndicatie : persoon.getPersoonIndicatieSet()) {
            final SoortIndicatie soortIndicatie = persoonIndicatie.getSoortIndicatie();
            switch (soortIndicatie) {
                case DERDE_HEEFT_GEZAG:
                    plBuilder.derdeHeeftGezagIndicatieStapel(mapIndicatie(brpOnderzoekMapper, persoonIndicatie, brpDerdeHeeftGezagIndicatieMapper));
                    break;
                case ONDER_CURATELE:
                    plBuilder.onderCurateleIndicatieStapel(mapIndicatie(brpOnderzoekMapper, persoonIndicatie, brpOnderCurateleIndicatieMapper));
                    break;
                case VOLLEDIGE_VERSTREKKINGSBEPERKING:
                    plBuilder.verstrekkingsbeperkingIndicatieStapel(
                        mapIndicatie(brpOnderzoekMapper, persoonIndicatie, brpVerstrekkingsbeperkingIndicatieMapper));
                    break;
                case VASTGESTELD_NIET_NEDERLANDER:
                    plBuilder.vastgesteldNietNederlanderIndicatieStapel(
                        mapIndicatie(brpOnderzoekMapper, persoonIndicatie, brpVastgesteldNietNederlanderIndicatieMapper));
                    break;
                case BEHANDELD_ALS_NEDERLANDER:
                    plBuilder.behandeldAlsNederlanderIndicatieStapel(
                        mapIndicatie(brpOnderzoekMapper, persoonIndicatie, brpBehandeldAlsNederlanderIndicatieMapper));
                    break;
                case SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT:
                    final BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper mapper = brpSignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper;
                    plBuilder.signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel(mapIndicatie(brpOnderzoekMapper, persoonIndicatie, mapper));
                    break;
                case STAATLOOS:
                    plBuilder.staatloosIndicatieStapel(mapIndicatie(brpOnderzoekMapper, persoonIndicatie, brpStaatloosIndicatieMapper));
                    break;
                case BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE:
                    plBuilder.bijzondereVerblijfsrechtelijkePositieIndicatieStapel(
                        mapIndicatie(brpOnderzoekMapper, persoonIndicatie, brpBijzondereVerblijfsrechtelijkePositieMapper));
                    break;
                default:
                    throw new IllegalArgumentException("Onbekende soort indicatie: " + soortIndicatie);
            }
        }
    }

    private <B extends AbstractBrpIndicatieGroepInhoud> BrpStapel<B> mapIndicatie(
        final BrpOnderzoekMapper brpOnderzoekMapper,
        final PersoonIndicatie persoonIndicatie,
        final AbstractBrpMapper<PersoonIndicatieHistorie, B> indicatieMapper)
    {
        final BrpStapel<B> indicatieInhoudBrpStapel;
        indicatieInhoudBrpStapel = indicatieMapper.map(persoonIndicatie.getPersoonIndicatieHistorieSet(), brpOnderzoekMapper);
        return indicatieInhoudBrpStapel;
    }

    private void mapRelaties(final Persoon persoon, final BrpPersoonslijstBuilder plBuilder, final BrpOnderzoekMapper brpOnderzoekMapper) {
        // Relaties
        plBuilder.relaties(brpRelatieMapper.map(persoon.getBetrokkenheidSet(), brpOnderzoekMapper));
    }
}
