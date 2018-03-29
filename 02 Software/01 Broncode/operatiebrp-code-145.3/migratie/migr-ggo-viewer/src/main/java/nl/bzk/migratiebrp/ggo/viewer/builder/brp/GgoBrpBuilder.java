/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBuitenlandsPersoonsnummer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerificatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaam;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoAdministratieveHandeling;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoStapel;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

/**
 * De builder die de BrpPersoonslijst omzet naar het viewer model.
 */
@Component
public class GgoBrpBuilder {
    private final GgoBrpRelatieBuilder ggoBrpRelatieBuilder;
    private final GgoBrpActieBuilder ggoBrpActieBuilder;

    private final Map<GgoBrpGroepEnum, AbstractGgoBrpMapper> brpMappers = new EnumMap<>(GgoBrpGroepEnum.class);

    @Inject
    public GgoBrpBuilder(final GgoBrpGegevensgroepenBuilder gegevensgroepenBuilder, final GgoBrpRelatieBuilder relatieBuilder,
                         final GgoBrpActieBuilder actieBuilder, final GgoBrpOnderzoekBuilder onderzoekBuilder, final GgoBrpValueConvert valueConverter) {
        this.ggoBrpRelatieBuilder = relatieBuilder;
        this.ggoBrpActieBuilder = actieBuilder;
        vulMapperMap(gegevensgroepenBuilder, actieBuilder, onderzoekBuilder, valueConverter);
    }

    private void vulMapperMap(final GgoBrpGegevensgroepenBuilder gegevensgroepenBuilder, final GgoBrpActieBuilder actieBuilder,
                              final GgoBrpOnderzoekBuilder onderzoekBuilder, final GgoBrpValueConvert valueConvert) {
        brpMappers.put(GgoBrpGroepEnum.PERSOON_AFGELEID_ADMINISTRATIEF, new GgoBrpAfgeleidAdministratiefMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.IDENTIFICATIENUMMERS, new GgoBrpIdentificatienummersMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.VOORNAAM, new GgoBrpVoornaamMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.GESLACHTSNAAMCOMPONENT, new GgoBrpGeslachtsnaamcomponentMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.SAMENGESTELDE_NAAM, new GgoBrpSamengesteldeNaamMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.GEBOORTE, new GgoBrpGeboorteMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.GESLACHTSAANDUIDING, new GgoBrpGeslachtsaanduidingMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.NAAMGEBRUIK, new GgoBrpNaamgebruikMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.NUMMERVERWIJZING, new GgoBrpNummerverwijzingMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.NATIONALITEIT, new GgoBrpNationaliteitMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.STAATLOOS_IND, new GgoBrpIndicatieMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.VASTGESTELD_NIET_NEDERLANDER_IND, new GgoBrpIndicatieMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.BEHANDELD_ALS_NEDERLANDER_IND, new GgoBrpIndicatieMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE, new GgoBrpIndicatieMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.OVERLIJDEN, new GgoBrpOverlijdenMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.VERSTREKKINGSBEPERKING, new GgoBrpIndicatieMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.INSCHRIJVING, new GgoBrpInschrijvingMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.PERSOONSKAART, new GgoBrpPersoonskaartMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.VERIFICATIE, new GgoBrpVerificatieMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.BIJHOUDING, new GgoBrpBijhoudingMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.ADRES, new GgoBrpAdresMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.MIGRATIE, new GgoBrpMigratieMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.VERBLIJFSRECHT, new GgoBrpVerblijfsrechtMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.ONDER_CURATELE_IND, new GgoBrpIndicatieMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.DERDE_HEEFT_GEZAG_IND, new GgoBrpIndicatieMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.REISDOCUMENT, new GgoBrpReisdocumentMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.SIGNALERING_MBT_VERSTREKKEN_REISDOCUMENT, new GgoBrpIndicatieMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.DEELNAME_EU_VERKIEZINGEN, new GgoBrpDeelnameEuVerkiezingenMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.UITSLUITING_KIESRECHT, new GgoBrpUitsluitingKiesrechtMapper(gegevensgroepenBuilder,
                actieBuilder, onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.BUITENLANDS_PERSOONSNUMMER, new GgoBrpBuitenlandsPersoonsnummerMapper(gegevensgroepenBuilder, actieBuilder,
                onderzoekBuilder, valueConvert));
        brpMappers.put(GgoBrpGroepEnum.ONVERWERKT_DOCUMENT_AANWEZIG_IND,
                new GgoBrpIndicatieMapper(gegevensgroepenBuilder, actieBuilder, onderzoekBuilder, valueConvert));
    }

    /**
     * Build de persoonslijst en geef een viewer model terug.
     * @param brpPersoonslijst BrpPersoonslijst
     * @param persoon Persoon
     * @return Map met de categorie.
     */
    public final List<GgoStapel> build(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        if (brpPersoonslijst == null || persoon == null) {
            return Collections.emptyList();
        }
        final List<GgoStapel> ggoBrpPersoonslijst = new ArrayList<>();
        buildPersoon(ggoBrpPersoonslijst, brpPersoonslijst, persoon);
        return ggoBrpPersoonslijst;
    }

    private void buildPersoon(final List<GgoStapel> ggoBrpPersoonslijst, final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        final Map<AdministratieveHandeling, Set<String>> ahs = new LinkedHashMap<>();

        final String aNummer = brpPersoonslijst.getActueelAdministratienummer();

        buildGegevensUitCategorie01(ggoBrpPersoonslijst, persoon, ahs, aNummer);
        buildGegevensUitCategorie02En03(ggoBrpPersoonslijst, persoon, ahs, aNummer);
        buildGegevensUitCategorie04(ggoBrpPersoonslijst, persoon, ahs, aNummer);
        buildGegevensUitCategorie05(ggoBrpPersoonslijst, persoon, ahs);
        buildGegevensUitCategorie06(ggoBrpPersoonslijst, persoon, ahs, aNummer);
        buildGegevensUitCategorie07(ggoBrpPersoonslijst, persoon, ahs, aNummer);
        buildGegevensUitCategorie08(ggoBrpPersoonslijst, persoon, ahs, aNummer);
        buildGegevensUitCategorie09(ggoBrpPersoonslijst, persoon, ahs);
        buildGegevensUitCategorie10(ggoBrpPersoonslijst, persoon, ahs, aNummer);
        buildGegevensUitCategorie11(ggoBrpPersoonslijst, persoon, ahs, aNummer);
        buildGegevensUitCategorie12(ggoBrpPersoonslijst, persoon, ahs, aNummer);
        buildGegevensUitCategorie13(ggoBrpPersoonslijst, persoon, ahs, aNummer);

        maakAdministratieveHandelingen(ggoBrpPersoonslijst, ahs);
    }

    private void buildGegevensUitCategorie06(final List<GgoStapel> ggoBrpPersoonslijst, final Persoon persoon,
                                             final Map<AdministratieveHandeling, Set<String>> ahs, final String aNummer) {
        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonOverlijdenHistorieSet(), aNummer, GgoBrpGroepEnum.OVERLIJDEN, ahs);
    }

    private void buildGegevensUitCategorie10(final List<GgoStapel> ggoBrpPersoonslijst, final Persoon persoon,
                                             final Map<AdministratieveHandeling, Set<String>> ahs, final String aNummer) {
        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonVerblijfsrechtHistorieSet(), aNummer, GgoBrpGroepEnum.VERBLIJFSRECHT, ahs);
    }

    private void buildGegevensUitCategorie09(final List<GgoStapel> ggoBrpPersoonslijst, final Persoon persoon,
                                             final Map<AdministratieveHandeling, Set<String>> ahs) {
        // Ik in de rol van ouder (tonen van kinderen)
        ggoBrpRelatieBuilder.buildRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, SoortBetrokkenheid.OUDER, persoon, ggoBrpPersoonslijst, ahs);
    }

    private void buildGegevensUitCategorie02En03(final List<GgoStapel> ggoBrpPersoonslijst, final Persoon persoon,
                                                 final Map<AdministratieveHandeling, Set<String>> ahs, final String aNummer) {
        // Ik in de rol van kind (tonen van ouders)
        ggoBrpRelatieBuilder.buildRelaties(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, SoortBetrokkenheid.KIND, persoon, ggoBrpPersoonslijst, ahs);
        createIndicatieStapels(ggoBrpPersoonslijst, persoon, SoortIndicatie.ONDER_CURATELE, aNummer, GgoBrpGroepEnum.ONDER_CURATELE_IND, ahs);
    }

    private void buildGegevensUitCategorie13(final List<GgoStapel> ggoBrpPersoonslijst, final Persoon persoon,
                                             final Map<AdministratieveHandeling, Set<String>> ahs, final String aNummer) {
        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonDeelnameEuVerkiezingenHistorieSet(), aNummer,
                GgoBrpGroepEnum.DEELNAME_EU_VERKIEZINGEN, ahs);
        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonUitsluitingKiesrechtHistorieSet(), aNummer,
                GgoBrpGroepEnum.UITSLUITING_KIESRECHT, ahs);
    }

    private void buildGegevensUitCategorie12(final List<GgoStapel> ggoBrpPersoonslijst, final Persoon persoon,
                                             final Map<AdministratieveHandeling, Set<String>> ahs, final String aNummer) {
        createReisdocumentStapels(ggoBrpPersoonslijst, persoon, aNummer, ahs);
        createIndicatieStapels(ggoBrpPersoonslijst, persoon, SoortIndicatie.SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT, aNummer,
                GgoBrpGroepEnum.SIGNALERING_MBT_VERSTREKKEN_REISDOCUMENT, ahs);
    }

    private void buildGegevensUitCategorie11(final List<GgoStapel> ggoBrpPersoonslijst, final Persoon persoon,
                                             final Map<AdministratieveHandeling, Set<String>> ahs, final String aNummer) {
        createIndicatieStapels(ggoBrpPersoonslijst, persoon, SoortIndicatie.DERDE_HEEFT_GEZAG, aNummer, GgoBrpGroepEnum.DERDE_HEEFT_GEZAG_IND, ahs);
    }

    private void buildGegevensUitCategorie08(final List<GgoStapel> ggoBrpPersoonslijst, final Persoon persoon,
                                             final Map<AdministratieveHandeling, Set<String>> ahs, final String aNummer) {
        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonBijhoudingHistorieSet(), aNummer, GgoBrpGroepEnum.BIJHOUDING, ahs);
        createAdresStapels(ggoBrpPersoonslijst, persoon, aNummer, ahs);
        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonMigratieHistorieSet(), aNummer, GgoBrpGroepEnum.MIGRATIE, ahs);
        createIndicatieStapels(ggoBrpPersoonslijst, persoon, SoortIndicatie.ONVERWERKT_DOCUMENT_AANWEZIG, aNummer,
                GgoBrpGroepEnum.ONVERWERKT_DOCUMENT_AANWEZIG_IND, ahs);
    }

    private void buildGegevensUitCategorie07(final List<GgoStapel> ggoBrpPersoonslijst, final Persoon persoon,
                                             final Map<AdministratieveHandeling, Set<String>> ahs, final String aNummer) {
        createIndicatieStapels(ggoBrpPersoonslijst, persoon, SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING, aNummer, GgoBrpGroepEnum.VERSTREKKINGSBEPERKING,
                ahs);
        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonInschrijvingHistorieSet(), aNummer, GgoBrpGroepEnum.INSCHRIJVING, ahs);
        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonPersoonskaartHistorieSet(), aNummer, GgoBrpGroepEnum.PERSOONSKAART, ahs);
        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonAfgeleidAdministratiefHistorieSet(), aNummer,
                GgoBrpGroepEnum.PERSOON_AFGELEID_ADMINISTRATIEF, ahs);
        createVerificatieStapels(ggoBrpPersoonslijst, persoon, aNummer, ahs);
    }

    private void buildGegevensUitCategorie05(final List<GgoStapel> ggoBrpPersoonslijst, final Persoon persoon,
                                             final Map<AdministratieveHandeling, Set<String>> ahs) {
        ggoBrpRelatieBuilder.buildRelaties(SoortRelatie.HUWELIJK, SoortBetrokkenheid.PARTNER, persoon, ggoBrpPersoonslijst, ahs);
        ggoBrpRelatieBuilder.buildRelaties(SoortRelatie.GEREGISTREERD_PARTNERSCHAP, SoortBetrokkenheid.PARTNER, persoon, ggoBrpPersoonslijst, ahs);
    }

    private void buildGegevensUitCategorie04(final List<GgoStapel> ggoBrpPersoonslijst, final Persoon persoon,
                                             final Map<AdministratieveHandeling, Set<String>> ahs, final String aNummer) {
        createNationaliteitStapels(ggoBrpPersoonslijst, persoon, aNummer, ahs);
        createIndicatieStapels(ggoBrpPersoonslijst, persoon, SoortIndicatie.STAATLOOS, aNummer, GgoBrpGroepEnum.STAATLOOS_IND, ahs);
        createIndicatieStapels(ggoBrpPersoonslijst, persoon, SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER, aNummer,
                GgoBrpGroepEnum.VASTGESTELD_NIET_NEDERLANDER_IND, ahs);
        createIndicatieStapels(ggoBrpPersoonslijst, persoon, SoortIndicatie.BEHANDELD_ALS_NEDERLANDER, aNummer, GgoBrpGroepEnum.BEHANDELD_ALS_NEDERLANDER_IND,
                ahs);
        createIndicatieStapels(ggoBrpPersoonslijst, persoon, SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE, aNummer,
                GgoBrpGroepEnum.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE, ahs);
        createBuitenlandsPersoonsnummerStapels(ggoBrpPersoonslijst, persoon, aNummer, ahs);
    }

    private void buildGegevensUitCategorie01(final List<GgoStapel> ggoBrpPersoonslijst, final Persoon persoon,
                                             final Map<AdministratieveHandeling, Set<String>> ahs, final String aNummer) {
        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonIDHistorieSet(), aNummer, GgoBrpGroepEnum.IDENTIFICATIENUMMERS, ahs);
        createPersoonVoornaamStapels(ggoBrpPersoonslijst, persoon, aNummer, ahs);
        createGeslachtsnaamcomponentStapels(ggoBrpPersoonslijst, persoon, aNummer, ahs);
        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonSamengesteldeNaamHistorieSet(), aNummer, GgoBrpGroepEnum.SAMENGESTELDE_NAAM,
                ahs);
        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonGeboorteHistorieSet(), aNummer, GgoBrpGroepEnum.GEBOORTE, ahs);
        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonGeslachtsaanduidingHistorieSet(), aNummer,
                GgoBrpGroepEnum.GESLACHTSAANDUIDING, ahs);
        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonNaamgebruikHistorieSet(), aNummer, GgoBrpGroepEnum.NAAMGEBRUIK, ahs);
        createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, persoon.getPersoonNummerverwijzingHistorieSet(), aNummer, GgoBrpGroepEnum.NUMMERVERWIJZING, ahs);
    }

    private void createEnkelvoudigeStapel(
            final List<GgoStapel> ggoBrpPersoonslijst,
            final Persoon persoon,
            final Set<? extends FormeleHistorie> brpStapel,
            final String aNummer,
            final GgoBrpGroepEnum brpGroepEnum,
            final Map<AdministratieveHandeling, Set<String>> ahs) {
        if (brpStapel != null && !brpStapel.isEmpty()) {
            brpMappers.get(brpGroepEnum).createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, brpStapel, aNummer, brpGroepEnum, ahs);
        }
    }

    private void createIndicatieStapels(
            final List<GgoStapel> ggoBrpPersoonslijst,
            final Persoon persoon,
            final SoortIndicatie soortIndicatie,
            final String aNummer,
            final GgoBrpGroepEnum brpGroepEnum,
            final Map<AdministratieveHandeling, Set<String>> ahs) {
        for (final PersoonIndicatie indicatie : persoon.getPersoonIndicatieSet()) {
            if (soortIndicatie.equals(indicatie.getSoortIndicatie())) {
                createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, indicatie.getPersoonIndicatieHistorieSet(), aNummer, brpGroepEnum, ahs);
            }
        }
    }

    private void createPersoonVoornaamStapels(
            final List<GgoStapel> ggoBrpPersoonslijst,
            final Persoon persoon,
            final String aNummer,
            final Map<AdministratieveHandeling, Set<String>> ahs) {
        for (final PersoonVoornaam stapel : persoon.getPersoonVoornaamSet()) {
            createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, stapel.getPersoonVoornaamHistorieSet(), aNummer, GgoBrpGroepEnum.VOORNAAM, ahs);
        }
    }

    private void createGeslachtsnaamcomponentStapels(
            final List<GgoStapel> ggoBrpPersoonslijst,
            final Persoon persoon,
            final String aNummer,
            final Map<AdministratieveHandeling, Set<String>> ahs) {
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
            final String aNummer,
            final Map<AdministratieveHandeling, Set<String>> ahs) {
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

    private void createBuitenlandsPersoonsnummerStapels(final List<GgoStapel> ggoBrpPersoonslijst, final Persoon persoon, final String aNummer,
                                                        final Map<AdministratieveHandeling, Set<String>> ahs) {
        for (final PersoonBuitenlandsPersoonsnummer stapel : persoon.getPersoonBuitenlandsPersoonsnummerSet()) {
            createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, stapel.getPersoonBuitenlandsPersoonsnummerHistorieSet(), aNummer,
                    GgoBrpGroepEnum.BUITENLANDS_PERSOONSNUMMER, ahs);
        }
    }

    private void createVerificatieStapels(
            final List<GgoStapel> ggoBrpPersoonslijst,
            final Persoon persoon,
            final String aNummer,
            final Map<AdministratieveHandeling, Set<String>> ahs) {
        for (final PersoonVerificatie stapel : persoon.getPersoonVerificatieSet()) {
            createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, stapel.getPersoonVerificatieHistorieSet(), aNummer, GgoBrpGroepEnum.VERIFICATIE, ahs);
        }
    }

    private void createAdresStapels(
            final List<GgoStapel> ggoBrpPersoonslijst,
            final Persoon persoon,
            final String aNummer,
            final Map<AdministratieveHandeling, Set<String>> ahs) {
        for (final PersoonAdres stapel : persoon.getPersoonAdresSet()) {
            createEnkelvoudigeStapel(ggoBrpPersoonslijst, persoon, stapel.getPersoonAdresHistorieSet(), aNummer, GgoBrpGroepEnum.ADRES, ahs);
        }
    }

    private void createReisdocumentStapels(
            final List<GgoStapel> ggoBrpPersoonslijst,
            final Persoon persoon,
            final String aNummer,
            final Map<AdministratieveHandeling, Set<String>> ahs) {
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
