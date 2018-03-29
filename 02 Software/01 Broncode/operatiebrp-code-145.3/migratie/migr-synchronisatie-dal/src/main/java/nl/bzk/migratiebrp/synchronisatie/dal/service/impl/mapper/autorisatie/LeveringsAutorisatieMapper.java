/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LeveringsautorisatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpLeveringsautorisatieInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;

/**
 * Mapper verantwoordelijk voor het mappen van het BRP model naar een Leveringsautorisatie entiteit.
 */
public final class LeveringsAutorisatieMapper
        extends AbstractHistorieMapperStrategie<BrpLeveringsautorisatieInhoud, LeveringsautorisatieHistorie, Leveringsautorisatie> {

    private static final int LENGTE_AFNEMER_CODE = 6;

    private final String autorisatieNaam;

    /**
     * Constructor.
     * @param dynamischeStamtabelRepository dynamische stamtabel repository om oa partijen te bevragen
     * @param brpActieFactory actie factory
     * @param autorisatieNaam naam voor in de autorisatie
     */
    public LeveringsAutorisatieMapper(final DynamischeStamtabelRepository dynamischeStamtabelRepository, final BRPActieFactory brpActieFactory,
                                      final String autorisatieNaam) {
        super(dynamischeStamtabelRepository, brpActieFactory, null);
        this.autorisatieNaam = autorisatieNaam;
    }

    @Override
    protected void voegHistorieToeAanEntiteit(final LeveringsautorisatieHistorie historie, final Leveringsautorisatie entiteit) {
        Set<LeveringsautorisatieHistorie> historieSet = entiteit.getLeveringsautorisatieHistorieSet();
        if (historieSet == null) {
            historieSet = new HashSet<>();
            entiteit.setLeveringsautorisatieHistorieSet(historieSet);
        }
        historieSet.add(historie);
    }

    @Override
    protected LeveringsautorisatieHistorie mapHistorischeGroep(final BrpLeveringsautorisatieInhoud groepInhoud, final Leveringsautorisatie entiteit) {
        final LeveringsautorisatieHistorie result =
                new LeveringsautorisatieHistorie(
                        entiteit,
                        new Timestamp(System.currentTimeMillis()),
                        bepaalAutorisatieNaam(groepInhoud.getNaam()),
                        Protocolleringsniveau.parseCode(groepInhoud.getProtocolleringsniveau().getCode()),
                        groepInhoud.getIndicatieAliasSoortAdministratieHandelingLeveren().booleanValue(),
                        MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumIngang()));

        result.setDatumEinde(MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumEinde()));
        result.setPopulatiebeperking(groepInhoud.getPopulatiebeperking());
        result.setIndicatieGeblokkeerd(groepInhoud.getIndicatieGeblokkeerd());
        result.setToelichting(groepInhoud.getToelichting());

        return result;
    }

    private String bepaalAutorisatieNaam(final String afnemerCode) {
        return afnemerCode.length() > LENGTE_AFNEMER_CODE ? afnemerCode.substring(0, LENGTE_AFNEMER_CODE) + autorisatieNaam + afnemerCode
                .substring(LENGTE_AFNEMER_CODE) : afnemerCode + autorisatieNaam;
    }

}
