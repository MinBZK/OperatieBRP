/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.ActieBron;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Document;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpActie;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoStapel;
import nl.bzk.migratiebrp.ggo.viewer.util.ViewerDateUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

/**
 * De builder die de BrpPersoonslijst omzet naar het viewer model.
 */

@Component
final class GgoBrpActieBuilder {
    private final GgoBrpGegevensgroepenBuilder gegevensgroepenBuilder;
    private final GgoBrpValueConvert valueConvert;

    /**
     * Constructor.
     * @param gegevensgroepenBuilder een {@link GgoBrpGegevensgroepenBuilder} instantie
     * @param valueConvert een {@link GgoBrpValueConvert} instantie
     */
    @Inject
    public GgoBrpActieBuilder(final GgoBrpGegevensgroepenBuilder gegevensgroepenBuilder, final GgoBrpValueConvert valueConvert) {
        this.gegevensgroepenBuilder = gegevensgroepenBuilder;
        this.valueConvert = valueConvert;
    }

    /**
     * Maak acties bij de brp groep.
     * @param voorkomen Het aan te vullen GGO BRP voorkomen.
     * @param brpGroep De brp groep inhoud.
     * @param brpGroepEnum De naam van de groep waarbij de acties horen.
     * @param aNummer Het anummer.
     * @param ahs Map met administratieve handelingen tbv de losse weergaven van AH's.
     */
    void createActies(
            final GgoBrpVoorkomen voorkomen,
            final FormeleHistorie brpGroep,
            final GgoBrpGroepEnum brpGroepEnum,
            final String aNummer,
            final Map<AdministratieveHandeling, Set<String>> ahs) {
        voorkomen.setActieInhoud(createActie(brpGroep, brpGroep.getActieInhoud(), brpGroepEnum, aNummer, ahs));
        voorkomen.setActieVerval(createActie(brpGroep, brpGroep.getActieVerval(), brpGroepEnum, aNummer, ahs));

        if (brpGroep instanceof MaterieleHistorie) {
            voorkomen.setActieGeldigheid(createActie(brpGroep, ((MaterieleHistorie) brpGroep).getActieAanpassingGeldigheid(), brpGroepEnum, aNummer, ahs));
        }
    }

    private GgoBrpActie createActie(
            final FormeleHistorie brpGroep,
            final BRPActie brpActie,
            final GgoBrpGroepEnum brpGroepEnum,
            final String aNummer,
            final Map<AdministratieveHandeling, Set<String>> ahs) {
        GgoBrpActie ggoBrpActie = null;
        if (brpActie != null) {
            ggoBrpActie = new GgoBrpActie();
            final Map<String, String> actieMap = new LinkedHashMap<>();
            addActie(actieMap, brpActie);

            ggoBrpActie.setInhoud(actieMap);
            final AdministratieveHandeling administratieveHandeling = brpActie.getAdministratieveHandeling();
            ggoBrpActie.setAdministratieveHandeling(createAdministratieveHandeling(administratieveHandeling));
            ggoBrpActie.setActieBronnen(createActieBronnen(brpActie));
            ggoBrpActie.setDocumenten(createDocumenten(brpActie, aNummer));

            ahs.computeIfAbsent(administratieveHandeling, k -> new LinkedHashSet<>());

            ahs.get(administratieveHandeling).add(brpGroepEnum.getLabel() + " - " + ViewerDateUtil.formatDatumTijdUtc(brpGroep.getDatumTijdRegistratie()));
        }
        return ggoBrpActie;
    }

    /**
     * Voeg BrpActie elementen toe aan het voorkomen.
     * @param actieMap De inhoud vande actie die gevuld moet worden.
     * @param brpActie Element die eventueel toegevoegd moet worden aan voorkomen.
     */
    void addActie(final Map<String, String> actieMap, final BRPActie brpActie) {
        if (brpActie != null) {
            valueConvert.verwerkElement(actieMap, GgoBrpElementEnum.ID, brpActie.getId());
            valueConvert.verwerkElement(actieMap, GgoBrpElementEnum.SOORT_ACTIE, brpActie.getSoortActie());
            valueConvert.verwerkElement(actieMap, GgoBrpElementEnum.PARTIJ, brpActie.getPartij());
            valueConvert.verwerkElement(
                    actieMap,
                    GgoBrpElementEnum.TIJDSTIP_REGISTRATIE,
                    brpActie.getDatumTijdRegistratie());
            valueConvert.verwerkElement(actieMap, GgoBrpElementEnum.DATUM_ONTLENING, brpActie.getDatumOntlening());
        }
    }

    /**
     * Maak admin handeling inhoud.
     * @param ah De Administratieve handeling
     * @return De inhoud
     */
    Map<String, String> createAdministratieveHandeling(final AdministratieveHandeling ah) {
        final Map<String, String> ahMap = new LinkedHashMap<>();

        valueConvert.verwerkElement(ahMap, GgoBrpElementEnum.ID, ah.getId());
        valueConvert.verwerkElement(ahMap, GgoBrpElementEnum.SOORT_ADMINISTRATIEVE_HANDELING, ah.getSoort());
        valueConvert.verwerkElement(ahMap, GgoBrpElementEnum.PARTIJ, ah.getPartij());
        valueConvert.verwerkElement(
                ahMap,
                GgoBrpElementEnum.TOELICHTING_ONTLENING,
                ah.getToelichtingOntlening());
        valueConvert.verwerkElement(
                ahMap,
                GgoBrpElementEnum.TIJDSTIP_REGISTRATIE,
                ah.getDatumTijdRegistratie());
        valueConvert.verwerkElement(ahMap, GgoBrpElementEnum.TIJDSTIP_LEVERING, ah.getDatumTijdLevering());

        return ahMap;
    }

    private List<Map<String, String>> createActieBronnen(final BRPActie brpActie) {
        if (brpActie == null) {
            return Collections.emptyList();
        }

        final List<Map<String, String>> actieBronnen = new ArrayList<>();
        if (brpActie.getActieBronSet() != null) {
            for (final ActieBron actieBron : brpActie.getActieBronSet()) {
                if (!StringUtils.isEmpty(actieBron.getRechtsgrondOmschrijving())) {
                    final Map<String, String> actieBronMap = new LinkedHashMap<>();
                    actieBronMap.put(GgoBrpElementEnum.RECHTSGRONDOMSCHRIJVING.getLabel(), actieBron.getRechtsgrondOmschrijving());
                    actieBronnen.add(actieBronMap);
                }
            }
        }
        return actieBronnen;
    }

    private List<GgoStapel> createDocumenten(final BRPActie brpActie, final String aNummer) {
        if (brpActie == null) {
            return Collections.emptyList();
        }

        final List<GgoStapel> actieDocumenten = new ArrayList<>();
        if (brpActie.getActieBronSet() != null) {
            for (final ActieBron actieBron : brpActie.getActieBronSet()) {
                final Document document = actieBron.getDocument();
                if (document != null) {
                    final GgoStapel stapel = new GgoStapel(GgoBrpGroepEnum.DOCUMENT.getLabel());
                    final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();
                    voorkomen.setInhoud(new LinkedHashMap<>());
                    voorkomen.setaNummer(aNummer);
                    voorkomen.setLabel(GgoBrpGroepEnum.DOCUMENT.getLabel());
                    gegevensgroepenBuilder.addGroepDocument(voorkomen.getInhoud(), document);

                    stapel.addVoorkomen(voorkomen);
                    actieDocumenten.add(stapel);
                }
            }
        }
        return actieDocumenten;
    }
}
