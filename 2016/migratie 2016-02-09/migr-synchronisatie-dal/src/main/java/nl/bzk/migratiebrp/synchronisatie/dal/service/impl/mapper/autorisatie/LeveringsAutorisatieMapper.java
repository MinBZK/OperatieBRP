/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpLeveringsautorisatieInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Leveringsautorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.LeveringsautorisatieHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Protocolleringsniveau;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;

/**
 * Mapper verantwoordelijk voor het mappen van het BRP model naar een Leveringsautorisatie entiteit.
 */
// @formatter:off
public final class LeveringsAutorisatieMapper extends
    AbstractHistorieMapperStrategie<BrpLeveringsautorisatieInhoud, LeveringsautorisatieHistorie, Leveringsautorisatie>
{
    // @formatter:on

    /**
     * Constructor.
     *
     * @param dynamischeStamtabelRepository
     *            dynamische stamtabel repository om oa partijen te bevragen
     * @param brpActieFactory
     *            actie factory
     */
    public LeveringsAutorisatieMapper(final DynamischeStamtabelRepository dynamischeStamtabelRepository, final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory, null);
    }

    @Override
    protected void mapActueleGegevens(final BrpStapel<BrpLeveringsautorisatieInhoud> brpStapel, final Leveringsautorisatie entiteit) {
        // Niet nodig voor dit object.
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
    protected void kopieerActueleGroepNaarEntiteit(final LeveringsautorisatieHistorie historie, final Leveringsautorisatie entiteit) {
        if (historie != null) {
            entiteit.setDatumEinde(historie.getDatumEinde());
            entiteit.setDatumIngang(historie.getDatumIngang());
            entiteit.setIndicatieAliasSoortAdministratieveHandelingLeveren(historie.getIndicatieAliasSoortAdministratieveHandelingLeveren());
            entiteit.setIndicatieGeblokkeerd(historie.getIndicatieGeblokkeerd());
            entiteit.setIndicatiePopulatiebeperkingVolledigGeconverteerd(historie.getIndicatiePopulatiebeperkingVolledigGeconverteerd());
            entiteit.setNaam(historie.getNaam());
            entiteit.setPopulatiebeperking(historie.getPopulatiebeperking());
            entiteit.setProtocolleringsniveau(historie.getProtocolleringsniveau());
            entiteit.setToelichting(historie.getToelichting());
        }
    }

    @Override
    protected LeveringsautorisatieHistorie mapHistorischeGroep(final BrpLeveringsautorisatieInhoud groepInhoud, final Leveringsautorisatie entiteit) {
        final LeveringsautorisatieHistorie result =
                new LeveringsautorisatieHistorie(
                    entiteit,
                    new Timestamp(System.currentTimeMillis()),
                    groepInhoud.getNaam(),
                    Protocolleringsniveau.parseCode(groepInhoud.getProtocolleringsniveau().getCode()),
                    groepInhoud.getIndicatieAliasSoortAdministratieHandelingLeveren().booleanValue(),
                    MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumIngang()));

        result.setDatumEinde(MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumEinde()));
        result.setPopulatiebeperking(groepInhoud.getPopulatiebeperking());
        result.setIndicatieGeblokkeerd(groepInhoud.getIndicatieGeblokkeerd());
        result.setIndicatiePopulatiebeperkingVolledigGeconverteerd(groepInhoud.getIndicatiePopulatiebeperkingVolledigGeconverteerd());
        result.setToelichting(groepInhoud.getToelichting());

        return result;
    }

}
