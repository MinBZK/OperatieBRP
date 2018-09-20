/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.util.HashSet;
import java.util.Set;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelLo3RubriekInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.DienstbundelLo3Rubriek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.DienstbundelLo3RubriekHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;

/**
 * Mapper verantwoordelijk voor het mappen van het BRP model naar een DienstbundelLo3Rubriek entiteit.
 */
public final class DienstbundelLo3RubriekMapper extends
    AbstractHistorieMapperStrategie<BrpDienstbundelLo3RubriekInhoud, DienstbundelLo3RubriekHistorie, DienstbundelLo3Rubriek>
{

    /**
     * Constructor.
     *
     * @param dynamischeStamtabelRepository
     *            dynamische stamtabel repository om oa partijen te bevragen
     * @param brpActieFactory
     *            actie factory
     */
    public DienstbundelLo3RubriekMapper(final DynamischeStamtabelRepository dynamischeStamtabelRepository, final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory, null);
    }

    @Override
    protected void mapActueleGegevens(final BrpStapel<BrpDienstbundelLo3RubriekInhoud> brpStapel, final DienstbundelLo3Rubriek entiteit) {
        // Niet nodig voor dit object.
    }

    @Override
    protected void voegHistorieToeAanEntiteit(final DienstbundelLo3RubriekHistorie historie, final DienstbundelLo3Rubriek entiteit) {
        Set<DienstbundelLo3RubriekHistorie> historieSet = entiteit.getDienstbundelLo3RubriekHistorieSet();
        if (historieSet == null) {
            historieSet = new HashSet<>();
            entiteit.setDienstbundelLo3RubriekHistorieSet(historieSet);
        }
        historieSet.add(historie);
    }

    @Override
    protected void kopieerActueleGroepNaarEntiteit(final DienstbundelLo3RubriekHistorie historie, final DienstbundelLo3Rubriek entiteit) {
        // Niet nodig voor dit object.
    }

    @Override
    protected DienstbundelLo3RubriekHistorie mapHistorischeGroep(final BrpDienstbundelLo3RubriekInhoud groepInhoud, final DienstbundelLo3Rubriek entiteit)
    {
        return new DienstbundelLo3RubriekHistorie(entiteit);
    }

}
