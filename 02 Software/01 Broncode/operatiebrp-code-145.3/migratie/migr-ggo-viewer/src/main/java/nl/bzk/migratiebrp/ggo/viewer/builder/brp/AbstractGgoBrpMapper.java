/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoStapel;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Callback die we gebruiken om niet 20x dezelfde method te hoeven schrijven. De functies createMeervoudige- en
 * -EnkelvoudigeStapels worden ook in deze class gezet om type safety redenen. Het resultaat is dat je deze class
 * instantieert, de callback implementeert en vervolgens een van de twee bovengenoemde functies aanroept om dit te
 * activeren.
 * @param <T> Een implementatie van BrpGroepInhoud
 */
public abstract class AbstractGgoBrpMapper<T extends FormeleHistorie> {

    private final GgoBrpGegevensgroepenBuilder ggoBrpGegevensgroepenBuilder;
    private final GgoBrpActieBuilder ggoBrpActieBuilder;
    private final GgoBrpOnderzoekBuilder ggoBrpOnderzoekBuilder;
    private final GgoBrpValueConvert ggoBrpValueConvert;

    protected AbstractGgoBrpMapper(final GgoBrpGegevensgroepenBuilder ggoBrpGegevensgroepenBuilder, final GgoBrpActieBuilder ggoBrpActieBuilder,
                                   final GgoBrpOnderzoekBuilder ggoBrpOnderzoekBuilder, final GgoBrpValueConvert ggoBrpValueConvert) {
        this.ggoBrpGegevensgroepenBuilder = ggoBrpGegevensgroepenBuilder;
        this.ggoBrpActieBuilder = ggoBrpActieBuilder;
        this.ggoBrpOnderzoekBuilder = ggoBrpOnderzoekBuilder;
        this.ggoBrpValueConvert = ggoBrpValueConvert;
    }

    /**
     * Geef de waarde van ggo brp gegevensgroepen builder.
     * @return ggo brp gegevensgroepen builder
     */
    protected final GgoBrpGegevensgroepenBuilder getGgoBrpGegevensgroepenBuilder() {
        return ggoBrpGegevensgroepenBuilder;
    }

    /**
     * Geef de waarde van ggo brp value convert.
     * @return ggo brp value convert
     */
    protected final GgoBrpValueConvert getGgoBrpValueConvert() {
        return ggoBrpValueConvert;
    }

    /**
     * Bouwt een GGO Stapel op op basis van een Brp Stapel.
     * @param ggoBrpPersoonslijst De GGO BRP persoonslijst waarin de stapel terecht komt.
     * @param persoon De entitymodel persoon
     * @param brpStapel De BRP stapel die wordt ingevuld.
     * @param aNummer Het aNummer van de persoonslijst.
     * @param labelEnum Het label van de stapel.
     * @param ahs Map met administratieve handelingen tbv de losse weergaven van AH's.
     */
    public final void createEnkelvoudigeStapel(
            final List<GgoStapel> ggoBrpPersoonslijst,
            final Persoon persoon,
            final Set<T> brpStapel,
            final String aNummer,
            final GgoBrpGroepEnum labelEnum,
            final Map<AdministratieveHandeling, Set<String>> ahs) {
        createStapel(ggoBrpPersoonslijst, persoon, brpStapel, aNummer, labelEnum, 0, ahs);
    }

    private void createStapel(
            final List<GgoStapel> ggoBrpPersoonslijst,
            final Persoon persoon,
            final Set<T> brpStapel,
            final String aNummer,
            final GgoBrpGroepEnum brpGroepEnum,
            final int brpStapelNr,
            final Map<AdministratieveHandeling, Set<String>> ahs) {
        if (brpStapel != null) {
            final GgoStapel stapel = new GgoStapel(brpGroepEnum.getLabel());
            stapel.setOmschrijving(bepaalStapelOmschrijving(brpStapel));

            for (final T groep : brpStapel) {
                if (!groep.isVoorkomenTbvLeveringMutaties()) {
                    final GgoBrpVoorkomen voorkomen = ggoBrpValueConvert.createGgoBrpVoorkomen(groep, aNummer, brpGroepEnum, brpStapelNr);
                    verwerkInhoud(voorkomen, groep, brpGroepEnum);
                    ggoBrpGegevensgroepenBuilder.addHistorie(voorkomen, groep);
                    ggoBrpActieBuilder.createActies(voorkomen, groep, brpGroepEnum, aNummer, ahs);
                    ggoBrpOnderzoekBuilder.createOnderzoeken(voorkomen, persoon, groep);

                    stapel.addVoorkomen(voorkomen);
                }
            }

            ggoBrpPersoonslijst.add(stapel);
        }
    }

    /**
     * Bepaalt de omschrijving van de Stapel. Bij sommige Stapels zoals Reisdocument is een omschrijving vereist
     * bijvoorbeeld om het soort reisdocument te tonen in de label van de stapel.
     * @param brpStapel De stapel.
     * @return omschrijving
     */
    
    protected String bepaalStapelOmschrijving(final Set<T> brpStapel) {
        return null;
    }

    /**
     * De door subclasses te implementeren callback.
     * @param voorkomen Het GGO BRP voorkomen dat wordt bijgewekt
     * @param brpInhoud De inhoud van de bron-BRP-groep.
     * @param brpGroepEnum Het label van de groep.
     */
    public abstract void verwerkInhoud(final GgoBrpVoorkomen voorkomen, final T brpInhoud, final GgoBrpGroepEnum brpGroepEnum);
}
