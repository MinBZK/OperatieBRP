/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.util.HashSet;
import java.util.Set;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelGroepInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.DienstbundelGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.DienstbundelGroepHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;

/**
 * Mapper verantwoordelijk voor het mappen van het BRP model naar een DienstbundelGroep entiteit.
 */
// @formatter:off
public final class DienstbundelGroepMapper extends
    AbstractHistorieMapperStrategie<BrpDienstbundelGroepInhoud, DienstbundelGroepHistorie, DienstbundelGroep>
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
    public DienstbundelGroepMapper(final DynamischeStamtabelRepository dynamischeStamtabelRepository, final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory, null);
    }

    @Override
    protected void mapActueleGegevens(final BrpStapel<BrpDienstbundelGroepInhoud> brpStapel, final DienstbundelGroep entiteit) {
        // Niet nodig voor dit object.
    }

    @Override
    protected void voegHistorieToeAanEntiteit(final DienstbundelGroepHistorie historie, final DienstbundelGroep entiteit) {
        Set<DienstbundelGroepHistorie> historieSet = entiteit.getDienstbundelGroepHistorieSet();
        if (historieSet == null) {
            historieSet = new HashSet<>();
            entiteit.setDienstbundelGroepHistorieSet(historieSet);
        }
        historieSet.add(historie);
    }

    @Override
    protected void kopieerActueleGroepNaarEntiteit(final DienstbundelGroepHistorie historie, final DienstbundelGroep entiteit) {
        if (historie != null) {
            entiteit.setIndicatieFormeleHistorie(historie.getIndicatieFormeleHistorie());
            entiteit.setIndicatieMaterieleHistorie(historie.getIndicatieMaterieleHistorie());
            entiteit.setIndicatieVerantwoording(historie.getIndicatieMaterieleHistorie());
        }
    }

    @Override
    protected DienstbundelGroepHistorie mapHistorischeGroep(final BrpDienstbundelGroepInhoud groepInhoud, final DienstbundelGroep entiteit) {
        final DienstbundelGroepHistorie result = new DienstbundelGroepHistorie(entiteit);
        result.setIndicatieFormeleHistorie(groepInhoud.getIndicatieFormeleHistorie());
        result.setIndicatieMaterieleHistorie(groepInhoud.getIndicatieMaterieleHistorie());
        result.setIndicatieVerantwoording(groepInhoud.getIndicatieVerantwoording());
        return result;
    }

}
