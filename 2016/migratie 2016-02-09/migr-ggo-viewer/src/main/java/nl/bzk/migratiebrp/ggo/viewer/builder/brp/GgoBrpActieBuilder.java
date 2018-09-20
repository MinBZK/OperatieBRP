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
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpActie;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoStapel;
import nl.bzk.migratiebrp.ggo.viewer.util.ViewerDateUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.ActieBron;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DocumentHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.MaterieleHistorie;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * De builder die de BrpPersoonslijst omzet naar het viewer model.
 */

@Component
public final class GgoBrpActieBuilder {
    @Inject
    private GgoBrpGegevensgroepenBuilder ggoBrpGegevensgroepenBuilder;
    @Inject
    private GgoBrpValueConvert ggoBrpValueConvert;

    /**
     * Maak acties bij de brp groep.
     *
     * @param voorkomen
     *            Het aan te vullen GGO BRP voorkomen.
     * @param brpGroep
     *            De brp groep inhoud.
     * @param brpGroepEnum
     *            De naam van de groep waarbij de acties horen.
     * @param aNummer
     *            Het anummer.
     * @param ahs
     *            Map met administratieve handelingen tbv de losse weergaven van AH's.
     */
    public void createActies(
        final GgoBrpVoorkomen voorkomen,
        final FormeleHistorie brpGroep,
        final GgoBrpGroepEnum brpGroepEnum,
        final Long aNummer,
        final Map<AdministratieveHandeling, Set<String>> ahs)
    {
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
        final Long aNummer,
        final Map<AdministratieveHandeling, Set<String>> ahs)
    {
        GgoBrpActie ggoBrpActie = null;
        if (brpActie != null) {
            ggoBrpActie = new GgoBrpActie();
            final Map<String, String> actieMap = new LinkedHashMap<>();
            addActie(actieMap, brpActie, brpGroepEnum);

            ggoBrpActie.setInhoud(actieMap);
            ggoBrpActie.setAdministratieveHandeling(createAdministratieveHandeling(brpActie.getAdministratieveHandeling()));
            ggoBrpActie.setActieBronnen(createActieBronnen(brpActie));
            ggoBrpActie.setDocumenten(createDocumenten(brpActie, aNummer, brpGroepEnum));

            if (ahs.get(brpActie.getAdministratieveHandeling()) == null) {
                ahs.put(brpActie.getAdministratieveHandeling(), new LinkedHashSet<String>());
            }

            ahs.get(brpActie.getAdministratieveHandeling()).add(
                brpGroepEnum.getLabel() + " - " + ViewerDateUtil.formatDatumTijdUtc(brpGroep.getDatumTijdRegistratie()));
        }
        return ggoBrpActie;
    }

    /**
     * Voeg BrpActie elementen toe aan het voorkomen.
     *
     * @param actieMap
     *            De inhoud vande actie die gevuld moet worden.
     * @param brpActie
     *            Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param brpGroepEnum
     *            De naam van de groep waartoe de actie behoort.
     */
    protected void addActie(final Map<String, String> actieMap, final BRPActie brpActie, final GgoBrpGroepEnum brpGroepEnum) {
        if (brpActie != null) {
            ggoBrpValueConvert.verwerkElement(actieMap, GgoBrpGroepEnum.ACTIE_INHOUD, GgoBrpElementEnum.ID, brpActie.getId());
            ggoBrpValueConvert.verwerkElement(actieMap, GgoBrpGroepEnum.ACTIE_INHOUD, GgoBrpElementEnum.SOORT_ACTIE, brpActie.getSoortActie());
            ggoBrpValueConvert.verwerkElement(actieMap, GgoBrpGroepEnum.ACTIE_INHOUD, GgoBrpElementEnum.PARTIJ, brpActie.getPartij());
            ggoBrpValueConvert.verwerkElement(
                actieMap,
                GgoBrpGroepEnum.ACTIE_INHOUD,
                GgoBrpElementEnum.TIJDSTIP_REGISTRATIE,
                brpActie.getDatumTijdRegistratie());
            ggoBrpValueConvert.verwerkElement(actieMap, GgoBrpGroepEnum.ACTIE_INHOUD, GgoBrpElementEnum.DATUM_ONTLENING, brpActie.getDatumOntlening());
        }
    }

    /**
     * Maak admin handeling inhoud.
     *
     * @param ah
     *            De Administratieve handeling
     * @return De inhoud
     */
    public Map<String, String> createAdministratieveHandeling(final AdministratieveHandeling ah) {
        final Map<String, String> ahMap = new LinkedHashMap<>();

        ggoBrpValueConvert.verwerkElement(ahMap, GgoBrpGroepEnum.ADMINISTRATIEVE_HANDELING, GgoBrpElementEnum.ID, ah.getId());
        ggoBrpValueConvert.verwerkElement(
            ahMap,
            GgoBrpGroepEnum.ADMINISTRATIEVE_HANDELING,
            GgoBrpElementEnum.SOORT_ADMINISTRATIEVE_HANDELING,
            ah.getSoort());
        ggoBrpValueConvert.verwerkElement(ahMap, GgoBrpGroepEnum.ADMINISTRATIEVE_HANDELING, GgoBrpElementEnum.PARTIJ, ah.getPartij());
        ggoBrpValueConvert.verwerkElement(
            ahMap,
            GgoBrpGroepEnum.ADMINISTRATIEVE_HANDELING,
            GgoBrpElementEnum.TOELICHTING_ONTLENING,
            ah.getToelichtingOntlening());
        ggoBrpValueConvert.verwerkElement(
            ahMap,
            GgoBrpGroepEnum.ADMINISTRATIEVE_HANDELING,
            GgoBrpElementEnum.TIJDSTIP_REGISTRATIE,
            ah.getDatumTijdRegistratie());
        ggoBrpValueConvert.verwerkElement(ahMap, GgoBrpGroepEnum.ADMINISTRATIEVE_HANDELING, GgoBrpElementEnum.TIJDSTIP_LEVERING, ah.getDatumTijdLevering());

        return ahMap;
    }

    private List<Map<String, String>> createActieBronnen(final BRPActie brpActie) {

        List<Map<String, String>> actieBronnen = null;
        if (brpActie != null) {
            actieBronnen = new ArrayList<>();
            if (brpActie.getActieBronSet() != null) {
                for (final ActieBron actieBron : brpActie.getActieBronSet()) {
                    if (!StringUtils.isEmpty(actieBron.getRechtsgrondOmschrijving())) {
                        final Map<String, String> actieBronMap = new LinkedHashMap<>();
                        actieBronMap.put(GgoBrpElementEnum.RECHTSGRONDOMSCHRIJVING.getLabel(), actieBron.getRechtsgrondOmschrijving());
                        actieBronnen.add(actieBronMap);
                    }
                }
            }
        }
        return actieBronnen;
    }

    private List<GgoStapel> createDocumenten(final BRPActie brpActie, final Long aNummer, final GgoBrpGroepEnum gerelateerdeGroep) {
        List<GgoStapel> actieDocumenten = null;
        if (brpActie != null) {
            actieDocumenten = new ArrayList<>();
            if (brpActie.getActieBronSet() != null) {
                int brpStapelNr = 0;
                for (final ActieBron actieBron : brpActie.getActieBronSet()) {
                    if (actieBron.getDocument() != null) {
                        final GgoStapel stapel = new GgoStapel(GgoBrpGroepEnum.DOCUMENT.getLabel());
                        for (final DocumentHistorie brpDocumentInhoud : actieBron.getDocument().getDocumentHistorieSet()) {
                            final GgoBrpVoorkomen voorkomen =
                                    ggoBrpValueConvert.createGgoBrpVoorkomen(brpDocumentInhoud, aNummer, GgoBrpGroepEnum.DOCUMENT, brpStapelNr);
                            ggoBrpGegevensgroepenBuilder.addGroepDocument(voorkomen.getInhoud(), brpDocumentInhoud, gerelateerdeGroep);
                            ggoBrpGegevensgroepenBuilder.addHistorie(voorkomen, brpDocumentInhoud, gerelateerdeGroep);

                            stapel.addVoorkomen(voorkomen);
                        }
                        actieDocumenten.add(stapel);
                        brpStapelNr++;
                    }
                }
            }
        }
        return actieDocumenten;
    }
}
