/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpProtocolleringsniveauCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AangeverRedenWijzigingVerblijfPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.AbstractConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch.VerstrekkingsbeperkingConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.ConversietabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.StamtabelRepository;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * De implementatie van de ConversietabelFactory die gebruik maakt van JPA repositories om conversietabellen uit de
 * database in te lezen.
 */
@Component
public final class DalConversietabelFactory extends AbstractConversietabelFactory implements ApplicationListener<ContextRefreshedEvent> {

    private static final String CACHE_LOCATION = "conversietabellen";
    private static final String CACHE_KEY = "#root.methodName";
    private static final String CACHE_MANAGER = "cacheManager";
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private ConversietabelRepository conversietabelRepository;

    @Inject
    private StamtabelRepository stamtabelRepository;

    /**
     * event Listner welke na het laden van de Context het lezen van de caches triggerd.
     * 
     * @param contextRefreshedEvent
     *            event
     */
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        loadCaches();
    }

    /**
     * Alle laden van alle Spring caches.
     */
    @SuppressWarnings("checkstyle:illegalcatch")
    @Transactional(value = "syncDalTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public void loadCaches() {
        try {
            LOGGER.info("Caches worden geladen");
            createAdellijkeTitelPredikaatConversietabel();
            createRedenEindeRelatieConversietabel();
            createVerblijfsrechtConversietabel();
            createAanduidingInhoudingVermissingReisdocumentConversietabel();
            createSoortReisdocumentConversietabel();
            createAangeverRedenWijzigingVerblijfConversietabel();
            createRedenBeeindigingNationaliteitConversietabel();
            createRedenOpnameNationaliteitConversietabel();
            createRedenOpschortingBijhoudingConversietabel();
            createRNIDeelnemerConversietabel();
            createVoorvoegselScheidingstekenConversietabel();
            createPartijConversietabel();
            createGemeenteConversietabel();
            createLandConversietabel();
            createNationaliteitConversietabel();
            createWoonplaatsnaamConversietabel();
            createSoortRegisterSoortDocumentConversietabel();
            createLo3RubriekConversietabel();
            createVerstrekkingsbeperkingConversietabel();
            LOGGER.info("Klaar met laden Caches");
        } catch (Exception e) {
            LOGGER.info("Caches konden niet geladen worden!");
        }
    }

    @Cacheable(cacheNames = CACHE_LOCATION, key = CACHE_KEY, cacheManager = CACHE_MANAGER)
    @Override
    public Conversietabel<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar> createAdellijkeTitelPredikaatConversietabel() {
        return new AdellijkeTitelPredikaatConversietabel(conversietabelRepository.findAllAdellijkeTitelPredikaat());
    }

    @Cacheable(cacheNames = CACHE_LOCATION, key = CACHE_KEY, cacheManager = CACHE_MANAGER)
    @Override
    public Conversietabel<Lo3RedenOntbindingHuwelijkOfGpCode, BrpRedenEindeRelatieCode> createRedenEindeRelatieConversietabel() {
        return new RedenOntbindingHuwelijkPartnerschapConversietabel(conversietabelRepository.findAllRedenOntbindingHuwelijkPartnerschap());
    }

    @Cacheable(cacheNames = CACHE_LOCATION, key = CACHE_KEY, cacheManager = CACHE_MANAGER)
    @Override
    public Conversietabel<Lo3AanduidingVerblijfstitelCode, BrpVerblijfsrechtCode> createVerblijfsrechtConversietabel() {
        return new VerblijfsrechtConversietabel(stamtabelRepository.findAllVerblijfsrecht());
    }

    @Cacheable(cacheNames = CACHE_LOCATION, key = CACHE_KEY, cacheManager = CACHE_MANAGER)
    @Override
    public Conversietabel<Lo3RedenNederlandschapCode, BrpRedenVerkrijgingNederlandschapCode> createRedenOpnameNationaliteitConversietabel() {
        return new RedenOpnameNationaliteitConversieTabel(stamtabelRepository.findAllRedenOpnameNationaliteit());
    }

    @Cacheable(cacheNames = CACHE_LOCATION, key = CACHE_KEY, cacheManager = CACHE_MANAGER)
    @Override
    public Conversietabel<Lo3RedenNederlandschapCode, BrpRedenVerliesNederlandschapCode> createRedenBeeindigingNationaliteitConversietabel() {
        return new RedenBeeindigingNationaliteitConversietabel(stamtabelRepository.findAllRedenBeeindigingNationaliteit());
    }

    @Cacheable(cacheNames = CACHE_LOCATION, key = CACHE_KEY, cacheManager = CACHE_MANAGER)
    @Override
    public Conversietabel<Lo3AangifteAdreshouding, AangeverRedenWijzigingVerblijfPaar> createAangeverRedenWijzigingVerblijfConversietabel() {
        return new AangeverRedenWijzigingVerblijfConversietabel(conversietabelRepository.findAllAangifteAdreshouding());
    }

    @Cacheable(cacheNames = CACHE_LOCATION, key = CACHE_KEY, cacheManager = CACHE_MANAGER)
    @Override
    public Conversietabel<Lo3SoortNederlandsReisdocument, BrpSoortNederlandsReisdocumentCode> createSoortReisdocumentConversietabel() {
        return new SoortNlReisdocumentConversietabel(conversietabelRepository.findAllSoortNlReisdocument());
    }

    @Cacheable(cacheNames = CACHE_LOCATION, key = CACHE_KEY, cacheManager = CACHE_MANAGER)
    @Override
    public Conversietabel<Lo3AanduidingInhoudingVermissingNederlandsReisdocument, BrpAanduidingInhoudingOfVermissingReisdocumentCode> createAanduidingInhoudingVermissingReisdocumentConversietabel() {
        return new AanduidingInhoudingVermissingReisdocumentConversietabel(conversietabelRepository.findAllAanduidingInhoudingVermissingReisdocument());
    }

    @Cacheable(cacheNames = CACHE_LOCATION, key = CACHE_KEY, cacheManager = CACHE_MANAGER)
    @Override
    public Conversietabel<Lo3RedenOpschortingBijhoudingCode, BrpNadereBijhoudingsaardCode> createRedenOpschortingBijhoudingConversietabel() {
        return new RedenOpschortingConversietabel(conversietabelRepository.findAllRedenOpschorting());
    }

    @Cacheable(cacheNames = CACHE_LOCATION, key = CACHE_KEY, cacheManager = CACHE_MANAGER)
    @Override
    public Conversietabel<Lo3RNIDeelnemerCode, BrpPartijCode> createRNIDeelnemerConversietabel() {
        return new RNIDeelnemerConversietabel(conversietabelRepository.findAllRNIDeelnemer());
    }

    @Cacheable(cacheNames = CACHE_LOCATION, key = CACHE_KEY, cacheManager = CACHE_MANAGER)
    @Override
    public Conversietabel<Lo3String, VoorvoegselScheidingstekenPaar> createVoorvoegselScheidingstekenConversietabel() {
        return new VoorvoegselConversietabel(conversietabelRepository.findAllVoorvoegselConversie());
    }

    @Cacheable(cacheNames = CACHE_LOCATION, key = CACHE_KEY, cacheManager = CACHE_MANAGER)
    @Override
    public Conversietabel<Lo3GemeenteCode, BrpPartijCode> createPartijConversietabel() {
        return new PartijConversietabel(stamtabelRepository.findAllGemeentes());
    }

    @Cacheable(cacheNames = CACHE_LOCATION, key = CACHE_KEY, cacheManager = CACHE_MANAGER)
    @Override
    public Conversietabel<Lo3GemeenteCode, BrpGemeenteCode> createGemeenteConversietabel() {
        return new GemeenteConversietabel(stamtabelRepository.findAllGemeenteCodes());
    }

    @Cacheable(cacheNames = CACHE_LOCATION, key = CACHE_KEY, cacheManager = CACHE_MANAGER)
    @Override
    public Conversietabel<Lo3LandCode, BrpLandOfGebiedCode> createLandConversietabel() {
        return new LandConversietabel(stamtabelRepository.findAllLandOfGebiedCodes());
    }

    @Cacheable(cacheNames = CACHE_LOCATION, key = CACHE_KEY, cacheManager = CACHE_MANAGER)
    @Override
    public Conversietabel<Lo3NationaliteitCode, BrpNationaliteitCode> createNationaliteitConversietabel() {
        return new NationaliteitConversietabel(stamtabelRepository.findAllNationaliteitCodes());
    }

    @Cacheable(cacheNames = CACHE_LOCATION, key = CACHE_KEY, cacheManager = CACHE_MANAGER)
    @Override
    public Conversietabel<String, String> createWoonplaatsnaamConversietabel() {
        return new WoonplaatsnaamConversietabel(stamtabelRepository.findAllPlaatsnamen());
    }

    @Cacheable(cacheNames = CACHE_LOCATION, key = CACHE_KEY, cacheManager = CACHE_MANAGER)
    @Override
    public Conversietabel<Character, BrpSoortDocumentCode> createSoortRegisterSoortDocumentConversietabel() {
        return new SoortRegisterSoortDocumentConversietabel(
            stamtabelRepository.findSoortDocumentConversie(SoortRegisterSoortDocumentConversietabel.CONVERSIE_SOORT_DOCUMENT));
    }

    @Cacheable(cacheNames = CACHE_LOCATION, key = CACHE_KEY, cacheManager = CACHE_MANAGER)
    @Override
    public Conversietabel<String, String> createLo3RubriekConversietabel() {
        return new Lo3RubriekConversietabel(conversietabelRepository.findAllLo3Rubrieken());
    }

    @Cacheable(cacheNames = CACHE_LOCATION, key = CACHE_KEY, cacheManager = CACHE_MANAGER)
    @Override
    public Conversietabel<Integer, BrpProtocolleringsniveauCode> createVerstrekkingsbeperkingConversietabel() {
        return new VerstrekkingsbeperkingConversietabel();
    }

}
