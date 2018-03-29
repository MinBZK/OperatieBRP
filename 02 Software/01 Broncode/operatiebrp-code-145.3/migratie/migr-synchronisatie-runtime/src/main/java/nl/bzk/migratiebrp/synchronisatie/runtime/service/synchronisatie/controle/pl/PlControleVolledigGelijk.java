/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.synchronisatie.dal.service.SyncParameters;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces.DeltaProces;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces.SamengesteldDeltaProces;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.PersoonMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapperImpl;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.springframework.stereotype.Component;

/**
 * Controle dat de inhoud van de gevonden persoonslijst overeenkomt met de inhoud van de aangeboden persoonslijst.
 */
@Component(value = "plControleVolledigGelijk")
public final class PlControleVolledigGelijk implements PlControle {

    private final SyncParameters syncParameters;
    private final DynamischeStamtabelRepository dynamischeStamtabelRepository;

    /**
     * Constructor.
     * @param syncParameters parameters voor de synchronisatie server
     * @param dynamischeStamtabelRepository dynamische stamtabel repository
     */
    public PlControleVolledigGelijk(final SyncParameters syncParameters, final DynamischeStamtabelRepository dynamischeStamtabelRepository) {
        this.syncParameters = syncParameters;
        this.dynamischeStamtabelRepository = dynamischeStamtabelRepository;
    }

    @Override
    public boolean controleer(final VerwerkingsContext context, final BrpPersoonslijst dbPersoonslijst) {
        final ControleLogging logging = new ControleLogging(ControleMelding.PL_CONTROLE_VOLLEDIG_GELIJK);

        final Persoon nieuwPersoon = mapPersoonEnOnderzoek(context.getBrpPersoonslijst(), context.getLoggingBericht());
        final Persoon bestaandPersoon = mapPersoonEnOnderzoek(dbPersoonslijst, context.getLoggingBericht());

        final DeltaBepalingContext deltaContext = new DeltaBepalingContext(nieuwPersoon, bestaandPersoon, context.getLoggingBericht(), false);
        final DeltaProces deltaProces = SamengesteldDeltaProces.newInstanceMetAlleProcessen();
        deltaProces.bepaalVerschillen(deltaContext);
        final boolean result = !deltaContext.heeftPersoonWijzigingen();

        if (!result) {
            logging.addMelding("Verschillen door delta bepaling gedetecteerd (zie bovenstaande log).");
        }

        logging.logResultaat(result);
        return result;
    }

    private Persoon mapPersoonEnOnderzoek(final BrpPersoonslijst brpPersoonslijst, final Lo3Bericht lo3Bericht) {
        // Map persoon
        final BrpPartijCode partijCode = brpPersoonslijst.getBijhoudingStapel().getActueel().getInhoud().getBijhoudingspartijCode();
        final Partij partij = dynamischeStamtabelRepository.getPartijByCode(partijCode.getWaarde());

        final Persoon kluizenaar = new Persoon(SoortPersoon.INGESCHREVENE);
        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(kluizenaar, partij);

        new PersoonMapper(dynamischeStamtabelRepository, syncParameters, onderzoekMapper).mapVanMigratie(brpPersoonslijst, kluizenaar, lo3Bericht);

        return kluizenaar;
    }
}
