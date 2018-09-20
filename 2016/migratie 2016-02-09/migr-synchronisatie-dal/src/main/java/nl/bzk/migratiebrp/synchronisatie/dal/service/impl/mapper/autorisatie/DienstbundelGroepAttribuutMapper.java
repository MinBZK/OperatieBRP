/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.util.HashSet;
import java.util.Set;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelGroepAttribuutInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.DienstbundelGroepAttribuut;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.DienstbundelGroepAttribuutHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;

/**
 * Mapper verantwoordelijk voor het mappen van het BRP model naar een DienstbundelGroepAttribuut entiteit.
 */
public final class DienstbundelGroepAttribuutMapper extends
    AbstractHistorieMapperStrategie<BrpDienstbundelGroepAttribuutInhoud, DienstbundelGroepAttribuutHistorie, DienstbundelGroepAttribuut>
{

    /**
     * Constructor.
     *
     * @param dynamischeStamtabelRepository
     *            dynamische stamtabel repository om oa partijen te bevragen
     * @param brpActieFactory
     *            actie factory
     */
    public DienstbundelGroepAttribuutMapper(final DynamischeStamtabelRepository dynamischeStamtabelRepository, final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory, null);
    }

    @Override
    protected void mapActueleGegevens(final BrpStapel<BrpDienstbundelGroepAttribuutInhoud> brpStapel, final DienstbundelGroepAttribuut entiteit) {
        // Niet nodig voor dit object.
    }

    @Override
    protected void voegHistorieToeAanEntiteit(final DienstbundelGroepAttribuutHistorie historie, final DienstbundelGroepAttribuut entiteit) {
        Set<DienstbundelGroepAttribuutHistorie> historieSet = entiteit.getDienstbundelGroepAttribuutHistorieSet();
        if (historieSet == null) {
            historieSet = new HashSet<>();
            entiteit.setDienstbundelGroepAttribuutHistorieSet(historieSet);
        }
        historieSet.add(historie);
    }

    @Override
    protected void kopieerActueleGroepNaarEntiteit(final DienstbundelGroepAttribuutHistorie historie, final DienstbundelGroepAttribuut entiteit) {
        // Niet nodig voor dit object.
    }

    @Override
    protected DienstbundelGroepAttribuutHistorie mapHistorischeGroep(
        final BrpDienstbundelGroepAttribuutInhoud groepInhoud,
        final DienstbundelGroepAttribuut entiteit)
    {
        return new DienstbundelGroepAttribuutHistorie(entiteit);
    }

}
