/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
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
 * Controle dat de inhoud van de gevonden persoonslijst overeenkomt met de inhoud van de aangeboden persoonslijst, met
 * uitzondering van de adressen en synchroniciteit.
 */
@Component(value = "plControleVolledigGelijkMuvAdressenDatumtijdstempelEnVersienummer")
public final class PlControleVolledigGelijkMuvAdressenDatumtijdstempelEnVersienummer implements PlControle {

    private static final BrpLong DUMMY_VERSIENUMMER = new BrpLong(0L);
    private static final BrpDatumTijd DUMMY_TIJDSTEMPEL = new BrpDatumTijd(new Date(0));
    private static final BrpActie DUMMY_ACTIE =
            new BrpActie(-1L, BrpSoortActieCode.CONVERSIE_GBA, BrpPartijCode.MIGRATIEVOORZIENING, DUMMY_TIJDSTEMPEL, null, null, 0, null);
    private static final BrpHistorie DUMMY_HISTORIE = new BrpHistorie(DUMMY_TIJDSTEMPEL, null, null);

    private final SyncParameters syncParameters;
    private final DynamischeStamtabelRepository dynamischeStamtabelRepository;

    /**
     * Constructor.
     * @param syncParameters parameters voor de synchronisatie server
     * @param dynamischeStamtabelRepository dynamische stamtabel repository
     */
    @Inject
    public PlControleVolledigGelijkMuvAdressenDatumtijdstempelEnVersienummer(final SyncParameters syncParameters,
                                                                             final DynamischeStamtabelRepository dynamischeStamtabelRepository) {
        this.syncParameters = syncParameters;
        this.dynamischeStamtabelRepository = dynamischeStamtabelRepository;
    }


    @Override
    public boolean controleer(final VerwerkingsContext context, final BrpPersoonslijst dbPersoonslijst) {
        final BrpPersoonslijst brpPersoonslijst = context.getBrpPersoonslijst();
        final ControleLogging logging = new ControleLogging(ControleMelding.PL_CONTROLE_VOLLEDIG_GELIJK_MUV_ADRESSEN_EN_SYNCH);

        final Persoon nieuwPersoon = mapPersoonEnOnderzoek(brpPersoonslijst, context.getLoggingBericht());
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

        new PersoonMapper(dynamischeStamtabelRepository, syncParameters, onderzoekMapper)
                .mapVanMigratie(sluitAdressenEnSynchroniciteitUit(brpPersoonslijst), kluizenaar, lo3Bericht);

        return kluizenaar;
    }


    private BrpPersoonslijst sluitAdressenEnSynchroniciteitUit(final BrpPersoonslijst brpPersoonslijst) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder(brpPersoonslijst);
        builder.adresStapel(null);
        builder.bijhoudingStapel(null);
        builder.persoonskaartStapel(null);
        builder.inschrijvingStapel(sluitSynchroniciteitUit(brpPersoonslijst.getInschrijvingStapel()));

        return builder.build();
    }

    private BrpStapel<BrpInschrijvingInhoud> sluitSynchroniciteitUit(final BrpStapel<BrpInschrijvingInhoud> inschrijvingStapel) {
        final List<BrpGroep<BrpInschrijvingInhoud>> groepen = new ArrayList<>();

        for (final BrpGroep<BrpInschrijvingInhoud> groep : inschrijvingStapel) {
            groepen.add(sluitSynchroniciteitUit(groep));
        }

        return new BrpStapel<>(groepen);
    }

    private BrpGroep<BrpInschrijvingInhoud> sluitSynchroniciteitUit(final BrpGroep<BrpInschrijvingInhoud> groep) {
        return new BrpGroep<>(sluitSynchroniciteitUit(groep.getInhoud()), DUMMY_HISTORIE, DUMMY_ACTIE, groep.getActieVerval(), groep.getActieGeldigheid());
    }

    private BrpInschrijvingInhoud sluitSynchroniciteitUit(final BrpInschrijvingInhoud inhoud) {
        return new BrpInschrijvingInhoud(inhoud.getDatumInschrijving(), DUMMY_VERSIENUMMER, DUMMY_TIJDSTEMPEL);
    }

}
