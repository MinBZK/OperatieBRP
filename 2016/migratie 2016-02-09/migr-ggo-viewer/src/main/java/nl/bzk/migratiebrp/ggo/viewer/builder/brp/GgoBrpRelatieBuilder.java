/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBetrokkenheid;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpIstVoorkomen;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpRelatie;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoStapel;
import nl.bzk.migratiebrp.ggo.viewer.util.ViewerDateUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Betrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BetrokkenheidOuderHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BetrokkenheidOuderlijkGezagHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.HistorieUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.MaterieleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeboorteHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIDHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RelatieHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortBetrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Stapel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.StapelVoorkomen;

import org.springframework.stereotype.Component;

/**
 * De builder die de BrpPersoonslijst omzet naar het viewer model.
 */
@Component("newGgoBrpRelatieBuilder")
public class GgoBrpRelatieBuilder {
    private static final String CATEGORIE_PREFIX = "CATEGORIE_";
    private static final int HIST = 50;

    @Inject
    private GgoBrpGegevensgroepenBuilder ggoBrpGegevensgroepenBuilder;
    @Inject
    private GgoBrpActieBuilder ggoBrpActieBuilder;
    @Inject
    private GgoBrpOnderzoekBuilder ggoBrpOnderzoekBuilder;
    @Inject
    private GgoBrpValueConvert brpValueConvert;
    @Inject
    private GgoBrpIdentificatienummersMapper identificatienummersMapper;
    @Inject
    private GgoBrpGeslachtsaanduidingMapper geslachtsaanduidingMapper;
    @Inject
    private GgoBrpSamengesteldeNaamMapper samengesteldeNaamMapper;
    @Inject
    private GgoBrpGeboorteMapper geboorteMapper;

    /**
     * Maak de relaties van een specifieke soort.
     *
     * @param soort
     *            Selecteer welke soort relatie te maken
     * @param rol
     *            De soort betrokkenheid
     * @param brpPersoon
     *            brpPersoon
     * @param ggoBrpPersoonslijst
     *            de viewer-persoonslijst
     * @param ahs
     *            Map met administratieve handelingen tbv de losse weergaven van AH's.
     */
    public final void buildRelaties(
        final SoortRelatie soort,
        final SoortBetrokkenheid rol,
        final Persoon brpPersoon,
        final List<GgoStapel> ggoBrpPersoonslijst,
        final Map<AdministratieveHandeling, Set<String>> ahs)
    {
        final Long aNummer = brpPersoon.getAdministratienummer();
        if (brpPersoon.getRelaties() != null) {
            int brpStapelNr = 0;
            for (final Relatie relatie : brpPersoon.getRelaties()) {
                if (rol.equals(getRolVan(brpPersoon, relatie)) && soort.equals(relatie.getSoortRelatie())) {
                    buildRelatie(rol, aNummer, brpStapelNr, brpPersoon, relatie, ggoBrpPersoonslijst, ahs);
                }

                brpStapelNr++;
            }
        }
    }

    private SoortBetrokkenheid getRolVan(final Persoon persoon, final Relatie relatie) {
        for (final Betrokkenheid betrokkenheid : relatie.getBetrokkenheidSet()) {
            if (persoon == betrokkenheid.getPersoon()) {
                return betrokkenheid.getSoortBetrokkenheid();
            }
        }

        return null;
    }

    /**
     * Maak de relaties.
     *
     * @param aNummer
     *            Het aNummer.
     * @param brpStapelNr
     *            Het indexnummer van de brp stapel.
     * @param relatie
     *            De brp relatie.
     * @param ggoBrpPersoonslijst
     *            de GGO brp persoonslijst.
     */
    private void buildRelatie(
        final SoortBetrokkenheid rol,
        final Long aNummer,
        final int brpStapelNr,
        final Persoon persoon,
        final Relatie relatie,
        final List<GgoStapel> ggoBrpPersoonslijst,
        final Map<AdministratieveHandeling, Set<String>> ahs)
    {
        final GgoBrpRelatie ggoBrpRelatie = new GgoBrpRelatie();

        // betrokkenheden, alleen actuele info
        ggoBrpRelatie.setBetrokkenheden(createBetrokkenheden(rol, persoon, relatie, brpStapelNr, aNummer, ahs));

        // relatie inhoud
        createRelatieInhoud(persoon, relatie, ggoBrpRelatie, brpStapelNr, aNummer, ahs);

        // IST relatie
        ggoBrpRelatie.setIstInhoud(createIstInhoud(relatie, brpStapelNr, aNummer, ahs));

        // maak zelf een key
        ggoBrpRelatie.setaNummer(aNummer);
        final String label = relatie.getSoortRelatie().getCode();
        ggoBrpRelatie.setLabel(label);
        ggoBrpRelatie.setBrpStapelNr(brpStapelNr);

        // Toevoegen als stapel. Dit heeft hier geen functionele betekenis,
        // maar is slechts omdat de persoonslijst een List<GgoStapel> is.
        final GgoStapel stapel = new GgoStapel(label);
        stapel.addVoorkomen(ggoBrpRelatie);
        ggoBrpPersoonslijst.add(stapel);
    }

    private List<GgoStapel> createIstInhoud(
        final Relatie relatie,
        final int brpStapelNr,
        final Long aNummer,
        final Map<AdministratieveHandeling, Set<String>> ahs)
    {
        final List<GgoStapel> result = new ArrayList<>();
        for (final Stapel stapel : relatie.getStapels()) {
            addIstInhoud(stapel, Lo3CategorieEnum.valueOf(CATEGORIE_PREFIX + stapel.getCategorie()), brpStapelNr, aNummer, result, ahs);
        }

        return result;
    }

    private void addIstInhoud(
        final Stapel brpStapel,
        final Lo3CategorieEnum categorieEnum,
        final int brpStapelNr,
        final Long aNummer,
        final List<GgoStapel> istInhoud,
        final Map<AdministratieveHandeling, Set<String>> ahs)

    {
        if (brpStapel != null) {
            final GgoStapel stapel = new GgoStapel(categorieEnum.getLabel());

            for (final StapelVoorkomen istRelatieInhoud : brpStapel.getStapelvoorkomens()) {
                final GgoBrpVoorkomen voorkomen = createGgoIstBrpVoorkomen(istRelatieInhoud, aNummer, categorieEnum, brpStapelNr);
                voorkomen.setInhoud(new LinkedHashMap<String, String>());

                if (isFamilierechtelijkeBetrekking(brpStapel)) {
                    brpValueConvert.bepaalVervallenEnDatumGeldigheid(voorkomen, istRelatieInhoud);
                    ggoBrpGegevensgroepenBuilder.addIstRelatie(voorkomen, istRelatieInhoud);

                    stapel.addVoorkomen(voorkomen);
                } else if (isGezagsverhouding(brpStapel)) {
                    brpValueConvert.bepaalVervallenEnDatumGeldigheid(voorkomen, istRelatieInhoud);
                    ggoBrpGegevensgroepenBuilder.addIstGezagsVerhouding(voorkomen, istRelatieInhoud);
                    stapel.addVoorkomen(voorkomen);
                } else if (isHuwelijkOfGp(brpStapel)) {
                    brpValueConvert.bepaalVervallenEnDatumGeldigheid(voorkomen, istRelatieInhoud);
                    ggoBrpGegevensgroepenBuilder.addIstRelatie(voorkomen, istRelatieInhoud);
                    ggoBrpGegevensgroepenBuilder.addIstHuwelijkOfGp(voorkomen, istRelatieInhoud);
                    stapel.addVoorkomen(voorkomen);
                }

                if (ahs.get(istRelatieInhoud.getAdministratieveHandeling()) == null) {
                    ahs.put(istRelatieInhoud.getAdministratieveHandeling(), new LinkedHashSet<String>());
                }

                ahs.get(istRelatieInhoud.getAdministratieveHandeling()).add(
                    String.format(
                        "Gearchiveerde %02d %s - %s",
                        voorkomen.getCategorieLabelNr(),
                        voorkomen.getLabel(),
                        ViewerDateUtil.formatDatum(istRelatieInhoud.getRubriek8610DatumVanOpneming())));
            }

            istInhoud.add(stapel);
        }
    }

    private boolean isFamilierechtelijkeBetrekking(final Stapel brpStapel) {
        final String categorie = brpStapel.getCategorie();
        return "02".equals(categorie) || "03".equals(categorie) || "09".equals(categorie);
    }

    private boolean isGezagsverhouding(final Stapel brpStapel) {
        final String categorie = brpStapel.getCategorie();
        return "11".equals(categorie);
    }

    private boolean isHuwelijkOfGp(final Stapel brpStapel) {
        final String categorie = brpStapel.getCategorie();
        return "05".equals(categorie);
    }

    /**
     * Maak een GgoBrpVoorkomen van het type IST.
     *
     * @param istInhoud
     *            BrpGroep<? extends BrpGroepInhoud>.
     * @param aNummer
     *            Het anummer van de persoon.
     * @param categorieEnum
     *            Het label van de IST categorie
     * @param brpStapelNr
     *            Het index nummer van de brp stapel.
     * @return GgoCategorieKey met de waarden.
     */
    public final GgoBrpIstVoorkomen createGgoIstBrpVoorkomen(
        final StapelVoorkomen istInhoud,
        final Long aNummer,
        final Lo3CategorieEnum categorieEnum,
        final int brpStapelNr)
    {
        final GgoBrpIstVoorkomen voorkomen = new GgoBrpIstVoorkomen();
        voorkomen.setInhoud(new LinkedHashMap<String, String>());

        voorkomen.setBrpStapelNr(brpStapelNr);
        voorkomen.setaNummer(aNummer);
        voorkomen.setLabel(categorieEnum.getLabel());

        if (istInhoud != null) {
            voorkomen.setAdministratieveHandeling(ggoBrpActieBuilder.createAdministratieveHandeling(istInhoud.getAdministratieveHandeling()));

            voorkomen.setCategorieNr(Integer.parseInt(istInhoud.getStapel().getCategorie()) % HIST + (istInhoud.getVolgnummer() == 0 ? 0 : HIST));
            voorkomen.setStapelNr(istInhoud.getStapel().getVolgnummer());
            voorkomen.setVoorkomenNr(istInhoud.getVolgnummer());

            voorkomen.setCategorieLabelNr(voorkomen.getCategorieNr());

        }

        return voorkomen;
    }

    private void createRelatieInhoud(
        final Persoon persoon,
        final Relatie relatie,
        final GgoBrpRelatie ggoBrpRelatie,
        final int brpStapelNr,
        final Long aNummer,
        final Map<AdministratieveHandeling, Set<String>> ahs)
    {

        final GgoBrpGroepEnum brpGroepEnum = GgoBrpGroepEnum.RELATIE_INHOUD;

        GgoStapel stapel = null;
        // relatie inhoud
        if (relatie.getRelatieHistorieSet() != null && !relatie.getRelatieHistorieSet().isEmpty()) {
            stapel = new GgoStapel(brpGroepEnum.getLabel());

            for (final RelatieHistorie relatieInhoud : relatie.getRelatieHistorieSet()) {
                final GgoBrpVoorkomen voorkomen = brpValueConvert.createGgoBrpVoorkomen(relatieInhoud, aNummer, brpGroepEnum, brpStapelNr);

                ggoBrpGegevensgroepenBuilder.addGroepRelatie(voorkomen, relatieInhoud, brpGroepEnum);
                ggoBrpGegevensgroepenBuilder.addHistorie(voorkomen, relatieInhoud, brpGroepEnum);
                ggoBrpActieBuilder.createActies(voorkomen, relatieInhoud, brpGroepEnum, aNummer, ahs);
                ggoBrpOnderzoekBuilder.createOnderzoeken(voorkomen, persoon, relatieInhoud, aNummer);

                // if (voorkomen.getOnderzoeken() != null) {
                // ggoBrpRelatie.getOnderzoeken().addAll(voorkomen.getOnderzoeken());
                // }

                stapel.addVoorkomen(voorkomen);
            }
        }

        ggoBrpRelatie.setRelatieInhoud(stapel);
    }

    private List<GgoBetrokkenheid> createBetrokkenheden(
        final SoortBetrokkenheid rol,
        final Persoon persoon,
        final Relatie relatie,
        final int brpStapelNr,
        final Long aNummer,
        final Map<AdministratieveHandeling, Set<String>> ahs)
    {
        // Betrokkenheid heeft een extra unieke key nodig om een betrokkenheid uniek te maken in de Map.
        // Bijvoorbeeld OuderlijkGezag heeft voor betrokkenheid Ouder1 als Ouder2 dezelfde
        // herkomst,label,brpStapelNr.
        // brpStapelNr uitbreiden met betrokkenheid index
        int brpStapelBetrokkenheidNr = Integer.parseInt(brpStapelNr + "0");

        // betrokkenheden, alleen actuele info
        List<GgoBetrokkenheid> betrokkenheden = null;
        if (relatie.getBetrokkenheidSet() != null) {
            betrokkenheden = new ArrayList<>();
            for (final Betrokkenheid betrokkenheid : relatie.getBetrokkenheidSet()) {
                if (betrokkenheid.getPersoon() == persoon
                    || SoortBetrokkenheid.OUDER.equals(rol)
                    && SoortBetrokkenheid.OUDER.equals(betrokkenheid.getSoortBetrokkenheid()))
                {
                    continue;
                }

                final GgoBetrokkenheid ggoBetrokkenheid = new GgoBetrokkenheid();

                final GgoStapel gerelateerdeStapel = new GgoStapel(GgoBrpGroepEnum.GERELATEERDE.getLabel());
                gerelateerdeStapel.addVoorkomen(maakGerelateerde(persoon, brpStapelBetrokkenheidNr, ggoBetrokkenheid, betrokkenheid, aNummer));
                ggoBetrokkenheid.addStapel(gerelateerdeStapel);

                createRelatieOuder(persoon, brpStapelBetrokkenheidNr, ggoBetrokkenheid, betrokkenheid, aNummer, ahs);
                createRelatieOuderlijkGezag(persoon, brpStapelBetrokkenheidNr, ggoBetrokkenheid, betrokkenheid, aNummer, ahs);

                ggoBetrokkenheid.setaNummer(aNummer);
                ggoBetrokkenheid.setBrpStapelNr(brpStapelBetrokkenheidNr);
                ggoBetrokkenheid.setLabel(bepaalRolLabel(betrokkenheid.getSoortBetrokkenheid(), relatie));

                final Betrokkenheid ikke = vindMezelf(persoon, relatie.getBetrokkenheidSet());

                if (SoortBetrokkenheid.KIND.equals(betrokkenheid.getSoortBetrokkenheid())) {
                    createRelatieOuder(persoon, brpStapelBetrokkenheidNr, ggoBetrokkenheid, ikke, aNummer, ahs);
                    createRelatieOuderlijkGezag(persoon, brpStapelBetrokkenheidNr, ggoBetrokkenheid, ikke, aNummer, ahs);
                }

                betrokkenheden.add(ggoBetrokkenheid);
                brpStapelBetrokkenheidNr++;
            }
        }
        return betrokkenheden;
    }

    private Betrokkenheid vindMezelf(final Persoon persoon, final Set<Betrokkenheid> betrokkenheden) {
        for (final Betrokkenheid betrokkenheid : betrokkenheden) {
            if (persoon.equals(betrokkenheid.getPersoon())) {
                return betrokkenheid;
            }
        }

        return null;
    }

    private GgoBrpVoorkomen maakGerelateerde(
        final Persoon persoon,
        final int brpStapelNr,
        final GgoBetrokkenheid ggoBetrokkenheid,
        final Betrokkenheid betrokkenheid,
        final Long aNummer)
    {
        final Map<String, String> gerelateerdeInhoud = new LinkedHashMap<>();

        MaterieleHistorie materieleHistorie = null;
        FormeleHistorie formeleHistorie = null;

        final Persoon betrokkenheidPersoon = betrokkenheid.getPersoon();

        if (betrokkenheidPersoon != null) {
            final PersoonIDHistorie ids = HistorieUtil.getActueelHistorieVoorkomen(betrokkenheidPersoon.getPersoonIDHistorieSet());
            if (ids != null) {
                materieleHistorie = ids;
                identificatienummersMapper.verwerkInhoud(gerelateerdeInhoud, ids, GgoBrpGroepEnum.IDENTIFICATIENUMMERS);
                ggoBrpOnderzoekBuilder.createOnderzoeken(ggoBetrokkenheid, persoon, ids, aNummer);
            }

            final PersoonSamengesteldeNaamHistorie namen =
                    HistorieUtil.getActueelHistorieVoorkomen(betrokkenheidPersoon.getPersoonSamengesteldeNaamHistorieSet());
            if (namen != null) {
                materieleHistorie = namen;
                final Map<String, String> samengesteldeNaamMap = new LinkedHashMap<>();
                samengesteldeNaamMapper.verwerkInhoud(samengesteldeNaamMap, namen, GgoBrpGroepEnum.SAMENGESTELDE_NAAM);

                // niet tonen in relatie
                samengesteldeNaamMap.remove(GgoBrpElementEnum.INDICATIE_AFGELEID.getLabel());
                samengesteldeNaamMap.remove(GgoBrpElementEnum.INDICATIE_NAMENREEKS.getLabel());

                gerelateerdeInhoud.putAll(samengesteldeNaamMap);
                ggoBrpOnderzoekBuilder.createOnderzoeken(ggoBetrokkenheid, persoon, namen, aNummer);
            }

            final PersoonGeboorteHistorie geboorte = HistorieUtil.getActueelHistorieVoorkomen(betrokkenheidPersoon.getPersoonGeboorteHistorieSet());
            if (geboorte != null) {
                formeleHistorie = geboorte;
                geboorteMapper.verwerkInhoud(gerelateerdeInhoud, geboorte, GgoBrpGroepEnum.GEBOORTE);
                ggoBrpOnderzoekBuilder.createOnderzoeken(ggoBetrokkenheid, persoon, geboorte, aNummer);
            }

            final PersoonGeslachtsaanduidingHistorie geslacht =
                    HistorieUtil.getActueelHistorieVoorkomen(betrokkenheidPersoon.getPersoonGeslachtsaanduidingHistorieSet());
            if (geslacht != null) {
                materieleHistorie = geslacht;
                geslachtsaanduidingMapper.verwerkInhoud(gerelateerdeInhoud, geslacht, GgoBrpGroepEnum.GESLACHTSAANDUIDING);
                ggoBrpOnderzoekBuilder.createOnderzoeken(ggoBetrokkenheid, persoon, geslacht, aNummer);
            }
        }
        return maakGerelateerdeVoorkomen(brpStapelNr, aNummer, gerelateerdeInhoud, materieleHistorie, formeleHistorie);
    }

    private GgoBrpVoorkomen maakGerelateerdeVoorkomen(
        final int brpStapelNr,
        final Long aNummer,
        final Map<String, String> gerelateerdeInhoud,
        final MaterieleHistorie materieleHistorie,
        final FormeleHistorie formeleHistorie)
    {
        final FormeleHistorie groep = materieleHistorie != null ? materieleHistorie : formeleHistorie;
        if (groep != null) {
            final GgoBrpVoorkomen gerelateerdeVoorkomen = brpValueConvert.createGgoBrpVoorkomen(groep, aNummer, GgoBrpGroepEnum.GERELATEERDE, brpStapelNr);
            gerelateerdeVoorkomen.setInhoud(gerelateerdeInhoud);
            ggoBrpGegevensgroepenBuilder.addHistorie(gerelateerdeVoorkomen, groep, GgoBrpGroepEnum.GERELATEERDE);
            return gerelateerdeVoorkomen;
        } else {
            return null;
        }
    }

    private void createRelatieOuderlijkGezag(
        final Persoon persoon,
        final int brpStapelNr,
        final GgoBetrokkenheid ggoBetrokkenheid,
        final Betrokkenheid betrokkenheid,
        final Long aNummer,
        final Map<AdministratieveHandeling, Set<String>> ahs)
    {
        final BetrokkenheidOuderlijkGezagHistorie ouderlijkGezagInhoudGroep =
                HistorieUtil.getActueelHistorieVoorkomen(betrokkenheid.getBetrokkenheidOuderlijkGezagHistorieSet());

        if (ouderlijkGezagInhoudGroep != null) {
            final GgoBrpGroepEnum brpGroepEnum = GgoBrpGroepEnum.OUDERLIJK_GEZAG;

            final GgoBrpVoorkomen voorkomen = brpValueConvert.createGgoBrpVoorkomen(ouderlijkGezagInhoudGroep, aNummer, brpGroepEnum, brpStapelNr);

            ggoBrpGegevensgroepenBuilder.addGroepOuderlijkGezag(voorkomen, ouderlijkGezagInhoudGroep, brpGroepEnum);
            ggoBrpGegevensgroepenBuilder.addHistorie(voorkomen, ouderlijkGezagInhoudGroep, brpGroepEnum);
            ggoBrpActieBuilder.createActies(voorkomen, ouderlijkGezagInhoudGroep, brpGroepEnum, aNummer, ahs);
            ggoBrpOnderzoekBuilder.createOnderzoeken(voorkomen, persoon, ouderlijkGezagInhoudGroep, aNummer);

            final GgoStapel stapel = new GgoStapel(brpGroepEnum.getLabel());
            stapel.addVoorkomen(voorkomen);
            ggoBetrokkenheid.addStapel(stapel);
        }
    }

    private void createRelatieOuder(
        final Persoon persoon,
        final int brpStapelNr,
        final GgoBetrokkenheid ggoBetrokkenheid,
        final Betrokkenheid betrokkenheid,
        final Long aNummer,
        final Map<AdministratieveHandeling, Set<String>> ahs)
    {
        final BetrokkenheidOuderHistorie ouderInhoudGroep = HistorieUtil.getActueelHistorieVoorkomen(betrokkenheid.getBetrokkenheidOuderHistorieSet());

        if (ouderInhoudGroep != null) {
            final GgoBrpGroepEnum brpGroepEnum = GgoBrpGroepEnum.OUDER;

            final GgoBrpVoorkomen voorkomen = brpValueConvert.createGgoBrpVoorkomen(ouderInhoudGroep, aNummer, brpGroepEnum, brpStapelNr);

            ggoBrpGegevensgroepenBuilder.addGroepOuder(voorkomen, ouderInhoudGroep, brpGroepEnum);
            ggoBrpGegevensgroepenBuilder.addHistorie(voorkomen, ouderInhoudGroep, brpGroepEnum);
            ggoBrpActieBuilder.createActies(voorkomen, ouderInhoudGroep, brpGroepEnum, aNummer, ahs);
            ggoBrpOnderzoekBuilder.createOnderzoeken(voorkomen, persoon, ouderInhoudGroep, aNummer);

            final GgoStapel stapel = new GgoStapel(GgoBrpGroepEnum.OUDER.getLabel());
            stapel.addVoorkomen(voorkomen);
            ggoBetrokkenheid.addStapel(stapel);
        }
    }

    private String bepaalRolLabel(final SoortBetrokkenheid rol, final Relatie relatie) {
        final String label;
        if (rol != null) {
            if (SoortBetrokkenheid.PARTNER.equals(rol)) {
                if (SoortRelatie.HUWELIJK.equals(relatie.getSoortRelatie())) {
                    label = "Huwelijk";
                } else {
                    label = "Geregistreerd partnerschap";
                }
            } else {
                label = rol.getNaam();
            }
        } else {
            // betrokkenheid voor kind soms niet ingevuld?
            label = "Kind";
        }

        return label;
    }
}
