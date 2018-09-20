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
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Dienstbundel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.DienstbundelHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;

/**
 * Mapper verantwoordelijk voor het mappen van het BRP model naar een Dienstbundel entiteit.
 */
// @formatter:off
public final class DienstbundelMapper extends
    AbstractHistorieMapperStrategie<BrpDienstbundelInhoud, DienstbundelHistorie, Dienstbundel>
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
    public DienstbundelMapper(final DynamischeStamtabelRepository dynamischeStamtabelRepository, final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory, null);
    }

    @Override
    protected void mapActueleGegevens(final BrpStapel<BrpDienstbundelInhoud> brpStapel, final Dienstbundel entiteit) {
        // Niet nodig voor dit object.
    }

    @Override
    protected void voegHistorieToeAanEntiteit(final DienstbundelHistorie historie, final Dienstbundel entiteit) {
        Set<DienstbundelHistorie> historieSet = entiteit.getDienstbundelHistorieSet();
        if (historieSet == null) {
            historieSet = new HashSet<>();
            entiteit.setDienstbundelHistorieSet(historieSet);
        }
        historieSet.add(historie);
    }

    @Override
    protected void kopieerActueleGroepNaarEntiteit(final DienstbundelHistorie historie, final Dienstbundel entiteit) {
        if (historie != null) {
            entiteit.setDatumIngang(historie.getDatumIngang());
            entiteit.setDatumEinde(historie.getDatumEinde());
            entiteit.setIndicatieGeblokkeerd(historie.getIndicatieGeblokkeerd());
            entiteit.setIndicatieNaderePopulatiebeperkingVolledigGeconverteerd(historie.getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd());
            entiteit.setNaam(historie.getNaam());
            entiteit.setNaderePopulatiebeperking(historie.getNaderePopulatiebeperking());
            entiteit.setToelichting(historie.getToelichting());
        }
    }

    @Override
    protected DienstbundelHistorie mapHistorischeGroep(final BrpDienstbundelInhoud groepInhoud, final Dienstbundel entiteit) {
        final DienstbundelHistorie result =
                new DienstbundelHistorie(
                    entiteit,
                    new Timestamp(System.currentTimeMillis()),
                    groepInhoud.getNaam(),
                    MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumIngang()));
        result.setDatumEinde(MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumEinde()));
        result.setIndicatieGeblokkeerd(groepInhoud.getGeblokkeerd());
        result.setIndicatieNaderePopulatiebeperkingVolledigGeconverteerd(groepInhoud.getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd());
        result.setNaderePopulatiebeperking(groepInhoud.getNaderePopulatiebeperking());
        result.setToelichting(groepInhoud.getToelichting());
        return result;
    }
}
