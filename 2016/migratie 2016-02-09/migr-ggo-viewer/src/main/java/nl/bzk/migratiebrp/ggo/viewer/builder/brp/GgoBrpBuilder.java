/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoAdministratieveHandeling;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoStapel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAdres;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIndicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonVerificatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonVoornaam;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortBetrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortIndicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortRelatie;
import org.springframework.stereotype.Component;

/**
 * De builder die de BrpPersoonslijst omzet naar het viewer model.
 */
@Component
public class GgoBrpBuilder {
    @Inject
    private GgoBrpRelatieBuilder ggoBrpRelatieBuilder;
    @Inject
    private GgoBrpActieBuilder ggoBrpActieBuilder;

    @Inject
    @Resource(name = "brpMappers")
    private Map<GgoBrpGroepEnum, AbstractGgoBrpMapper> brpMappers;

    /**
     * Build de persoonslijst en geef een viewer model terug.
     *
     * @param brpPersoonslijst
     *            BrpPersoonslijst
     * @param persoon
     *            Persoon
     * @return Map met de categorie.
     */
    public final List<GgoStapel> build(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        if (brpPersoonslijst == null || persoon == null) {
            return null;
        }
        final List<GgoStapel> ggoBrpPersoonslijst = new ArrayList<>();
        buildPersoon(ggoBrpPersoonslijst, brpPersoonslijst, persoon);
        return ggoBrpPersoonslijst;
    }

    private void buildPersoon(final List<GgoStapel> ggoBrpPersoonslijst, final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        final Map<AdministratieveHandeling, Set<String>> ahs = new LinkedHashMap<>();

        final Long aNummer = brpPersoonslijst.getActueelAdministratienummer();

        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonIDHistorieSet(), aNummer, GgoBrpGroepEnum.IDENTIFICATIENUMMERS, ahs);

        createPersoonVoornaamStapels(ggoBrpPersoonslijst, persoon, aNummer, ahs);

        createGeslachtsnaamcomponentStapels(ggoBrpPersoonslijst, persoon, aNummer, ahs);

        createEnkelvoudigeStapel(
            ggoBrpPersoonslijst,
            persoon,
            persoon.getPersoonSamengesteldeNaamHistorieSet(),
            aNummer,
            GgoBrpGroepEnum.SAMENGESTELDE_NAAM,
            ahs);

        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonGeboorteHistorieSet(), aNummer, GgoBrpGroepEnum.GEBOORTE, ahs);

        createEnkelvoudigeStapel(
            ggoBrpPersoonslijst,
            persoon,
            persoon.getPersoonGeslachtsaanduidingHistorieSet(),
            aNummer,
            GgoBrpGroepEnum.GESLACHTSAANDUIDING,
            ahs);

        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonNaamgebruikHistorieSet(), aNummer, GgoBrpGroepEnum.NAAMGEBRUIK, ahs);

        createEnkelvoudigeStapel(
            ggoBrpPersoonslijst,
            persoon,
            persoon.getPersoonNummerverwijzingHistorieSet(),
            aNummer,
            GgoBrpGroepEnum.NUMMERVERWIJZING,
            ahs);

        ggoBrpRelatieBuilder.buildRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, SoortBetrokkenheid.KIND, persoon, ggoBrpPersoonslijst, ahs);

        createNationaliteitStapels(ggoBrpPersoonslijst, persoon, aNummer, ahs);

        createIndicatieStapels(ggoBrpPersoonslijst, persoon, SoortIndicatie.STAATLOOS, aNummer, GgoBrpGroepEnum.STAATLOOS_IND, ahs);

        createIndicatieStapels(
            ggoBrpPersoonslijst,
            persoon,
            SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER,
            aNummer,
            GgoBrpGroepEnum.VASTGESTELD_NIET_NEDERLANDER_IND,
            ahs);

        createIndicatieStapels(
            ggoBrpPersoonslijst,
            persoon,
            SoortIndicatie.BEHANDELD_ALS_NEDERLANDER,
            aNummer,
            GgoBrpGroepEnum.BEHANDELD_ALS_NEDERLANDER_IND,
            ahs);

        createIndicatieStapels(
            ggoBrpPersoonslijst,
            persoon,
            SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE,
            aNummer,
            GgoBrpGroepEnum.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE,
            ahs);

        ggoBrpRelatieBuilder.buildRelaties(SoortRelatie.HUWELIJK, SoortBetrokkenheid.PARTNER, persoon, ggoBrpPersoonslijst, ahs);
        ggoBrpRelatieBuilder.buildRelaties(SoortRelatie.GEREGISTREERD_PARTNERSCHAP, SoortBetrokkenheid.PARTNER, persoon, ggoBrpPersoonslijst, ahs);

        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonOverlijdenHistorieSet(), aNummer, GgoBrpGroepEnum.OVERLIJDEN, ahs);

        createIndicatieStapels(
            ggoBrpPersoonslijst,
            persoon,
            SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING,
            aNummer,
            GgoBrpGroepEnum.VERSTREKKINGSBEPERKING,
            ahs);

        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonInschrijvingHistorieSet(), aNummer, GgoBrpGroepEnum.INSCHRIJVING, ahs);

        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonPersoonskaartHistorieSet(), aNummer, GgoBrpGroepEnum.PERSOONSKAART, ahs);

        createEnkelvoudigeStapel(
            ggoBrpPersoonslijst,
            persoon,
            persoon.getPersoonAfgeleidAdministratiefHistorieSet(),
            aNummer,
            GgoBrpGroepEnum.PERSOON_AFGELEID_ADMINISTRATIEF,
            ahs);

        createVerificatieStapels(ggoBrpPersoonslijst, persoon, aNummer, ahs);

        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonBijhoudingHistorieSet(), aNummer, GgoBrpGroepEnum.BIJHOUDING, ahs);

        createAdresStapels(ggoBrpPersoonslijst, persoon, aNummer, ahs);

        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonMigratieHistorieSet(), aNummer, GgoBrpGroepEnum.MIGRATIE, ahs);

        ggoBrpRelatieBuilder.buildRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, SoortBetrokkenheid.OUDER, persoon, ggoBrpPersoonslijst, ahs);

        createEnkelvoudigeStapel(
            ggoBrpPersoonslijst,
            persoon,
            persoon.getPersoonVerblijfsrechtHistorieSet(),
            aNummer,
            GgoBrpGroepEnum.VERBLIJFSRECHT,
            ahs);

        createIndicatieStapels(ggoBrpPersoonslijst, persoon, SoortIndicatie.ONDER_CURATELE, aNummer, GgoBrpGroepEnum.ONDER_CURATELE_IND, ahs);

        createIndicatieStapels(ggoBrpPersoonslijst, persoon, SoortIndicatie.DERDE_HEEFT_GEZAG, aNummer, GgoBrpGroepEnum.DERDE_HEEFT_GEZAG_IND, ahs);

        createReisdocumentStapels(ggoBrpPersoonslijst, persoon, aNummer, ahs);

        createIndicatieStapels(
            ggoBrpPersoonslijst,
            persoon,
            SoortIndicatie.SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT,
            aNummer,
            GgoBrpGroepEnum.SIGNALERING_MBT_VERSTREKKEN_REISDOCUMENT,
            ahs);

        createEnkelvoudigeStapel(
            ggoBrpPersoonslijst,
            persoon,
            persoon.getPersoonDeelnameEuVerkiezingenHistorieSet(),
            aNummer,
            GgoBrpGroepEnum.DEELNAME_EU_VERKIEZINGEN,
            ahs);

        createEnkelvoudigeStapel(
            ggoBrpPersoonslijst,
            persoon,
            persoon.getPersoonUitsluitingKiesrechtHistorieSet(),
            aNummer,
            GgoBrpGroepEnum.UITSLUITING_KIESRECHT,
            ahs);

        maakAdministratieveHandelingen(ggoBrpPersoonslijst, ahs);
    }

    private void createEnkelvoudigeStapel(
        final List<GgoStapel> ggoBrpPersoonslijst,
        final Persoon persoon,
        final Set<? extends FormeleHistorie> brpStapel,
        final Long aNummer,
        final GgoBrpGroepEnum brpGroepEnum,
        final Map<AdministratieveHandeling, Set<String>> ahs)
    {
        if (brpStapel != null && !brpStapel.isEmpty()) {
            brpMappers.get(brpGroepEnum).createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, brpStapel, aNummer, brpGroepEnum, ahs);
        }
    }

    private void createIndicatieStapels(
        final List<GgoStapel> ggoBrpPersoonslijst,
        final Persoon persoon,
        final SoortIndicatie soortIndicatie,
        final Long aNummer,
        final GgoBrpGroepEnum brpGroepEnum,
        final Map<AdministratieveHandeling, Set<String>> ahs)
    {
        for (final PersoonIndicatie indicatie : persoon.getPersoonIndicatieSet()) {
            if (soortIndicatie.equals(indicatie.getSoortIndicatie())) {
                createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, indicatie.getPersoonIndicatieHistorieSet(), aNummer, brpGroepEnum, ahs);
            }
        }
    }

    private void createPersoonVoornaamStapels(
        final List<GgoStapel> ggoBrpPersoonslijst,
        final Persoon persoon,
        final Long aNummer,
        final Map<AdministratieveHandeling, Set<String>> ahs)
    {
        for (final PersoonVoornaam stapel : persoon.getPersoonVoornaamSet()) {
            createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, stapel.getPersoonVoornaamHistorieSet(), aNummer, GgoBrpGroepEnum.VOORNAAM, ahs);
        }
    }

    private void createGeslachtsnaamcomponentStapels(
        final List<GgoStapel> ggoBrpPersoonslijst,
        final Persoon persoon,
        final Long aNummer,
        final Map<AdministratieveHandeling, Set<String>> ahs)
    {
        for (final PersoonGeslachtsnaamcomponent stapel : persoon.getPersoonGeslachtsnaamcomponentSet()) {
            createEnkelvoudigeStapel(
                ggoBrpPersoonslijst,
                persoon,
                stapel.getPersoonGeslachtsnaamcomponentHistorieSet(),
                aNummer,
                GgoBrpGroepEnum.GESLACHTSNAAMCOMPONENT,
                ahs);
        }
    }

    private void createNationaliteitStapels(
        final List<GgoStapel> ggoBrpPersoonslijst,
        final Persoon persoon,
        final Long aNummer,
        final Map<AdministratieveHandeling, Set<String>> ahs)
    {
        for (final PersoonNationaliteit stapel : persoon.getPersoonNationaliteitSet()) {
            createEnkelvoudigeStapel(
                ggoBrpPersoonslijst,
                persoon,
                stapel.getPersoonNationaliteitHistorieSet(),
                aNummer,
                GgoBrpGroepEnum.NATIONALITEIT,
                ahs);
        }
    }

    private void createVerificatieStapels(
        final List<GgoStapel> ggoBrpPersoonslijst,
        final Persoon persoon,
        final Long aNummer,
        final Map<AdministratieveHandeling, Set<String>> ahs)
    {
        for (final PersoonVerificatie stapel : persoon.getPersoonVerificatieSet()) {
            createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, stapel.getPersoonVerificatieHistorieSet(), aNummer, GgoBrpGroepEnum.VERIFICATIE, ahs);
        }
    }

    private void createAdresStapels(
        final List<GgoStapel> ggoBrpPersoonslijst,
        final Persoon persoon,
        final Long aNummer,
        final Map<AdministratieveHandeling, Set<String>> ahs)
    {
        for (final PersoonAdres stapel : persoon.getPersoonAdresSet()) {
            createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, stapel.getPersoonAdresHistorieSet(), aNummer, GgoBrpGroepEnum.ADRES, ahs);
        }
    }

    private void createReisdocumentStapels(
        final List<GgoStapel> ggoBrpPersoonslijst,
        final Persoon persoon,
        final Long aNummer,
        final Map<AdministratieveHandeling, Set<String>> ahs)
    {
        for (final PersoonReisdocument stapel : persoon.getPersoonReisdocumentSet()) {
            createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, stapel.getPersoonReisdocumentHistorieSet(), aNummer, GgoBrpGroepEnum.REISDOCUMENT, ahs);
        }
    }

    private void maakAdministratieveHandelingen(final List<GgoStapel> ggoBrpPersoonslijst, final Map<AdministratieveHandeling, Set<String>> ahs) {
        if (ahs.size() > 0) {
            final GgoStapel ahStapel = new GgoStapel(GgoBrpGroepEnum.ADMINISTRATIEVE_HANDELING.getLabel());

            for (final Map.Entry<AdministratieveHandeling, Set<String>> entry : ahs.entrySet()) {
                final GgoAdministratieveHandeling voorkomen = new GgoAdministratieveHandeling();
                voorkomen.setLabel(GgoBrpGroepEnum.ADMINISTRATIEVE_HANDELING.getLabel());
                voorkomen.setInhoud(ggoBrpActieBuilder.createAdministratieveHandeling(entry.getKey()));
                voorkomen.setBetrokkenVoorkomens(entry.getValue());
                ahStapel.addVoorkomen(voorkomen);
            }

            ggoBrpPersoonslijst.add(ahStapel);
        }
    }
}
