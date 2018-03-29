/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AutoriteitAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortPartij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.FoutmeldingUtil;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.springframework.dao.InvalidDataAccessApiUsageException;

/* Class Fan-Out complexity is hier hoog maar deze class is niet onnodig complex */

/**
 * Een adapter voor het bevragen van stamtabellen voor het mappen van het migratie model op de entiteiten uit het
 * operationele model van de BRP.
 */
final class DynamischeStamtabelRepositoryAdapter implements StamtabelMapping {

    private static final Logger LOG = LoggerFactory.getLogger();
    private final DynamischeStamtabelRepository dynamischeStamtabelRepository;

    /**
     * Maakt een DynamischeStamtabelRepositoryAdapter object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt.
     */
    DynamischeStamtabelRepositoryAdapter(final DynamischeStamtabelRepository dynamischeStamtabelRepository) {
        this.dynamischeStamtabelRepository = dynamischeStamtabelRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenVerliesNLNationaliteit findRedenVerliesNLNationaliteitByCode(final BrpRedenVerliesNederlandschapCode redenVerliesNederlandschapCode) {
        if (!BrpValidatie.isAttribuutGevuld(redenVerliesNederlandschapCode)) {
            return null;
        }
        return dynamischeStamtabelRepository.getRedenVerliesNLNationaliteitByCode(redenVerliesNederlandschapCode.getWaarde());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenVerkrijgingNLNationaliteit findRedenVerkrijgingNLNationaliteitByCode(
            final BrpRedenVerkrijgingNederlandschapCode redenVerkrijgingNederlandschapCode) {
        if (!BrpValidatie.isAttribuutGevuld(redenVerkrijgingNederlandschapCode)) {
            return null;
        }
        return dynamischeStamtabelRepository.getRedenVerkrijgingNLNationaliteitByCode(redenVerkrijgingNederlandschapCode.getWaarde());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gemeente findGemeenteByCode(final BrpGemeenteCode gemeenteCode) {
        return findGemeenteByCode(gemeenteCode, SoortMeldingCode.PRE002);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gemeente findGemeenteByCode(final BrpGemeenteCode gemeenteCode, final SoortMeldingCode soortMeldingCode) {
        if (!BrpValidatie.isAttribuutGevuld(gemeenteCode)) {
            return null;
        }
        try {
            return dynamischeStamtabelRepository.getGemeenteByGemeentecode(gemeenteCode.getWaarde());
        } catch (final InvalidDataAccessApiUsageException idae) {
            LOG.error("Validatie Exceptie gemeente code", idae);
            throw FoutmeldingUtil.getValidatieExceptie(
                    String.format("De gemeentecode '%s' komt niet voor in de gemeentetabel.", gemeenteCode.getWaarde()),
                    soortMeldingCode,
                    idae);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij findPartijByCode(final BrpPartijCode partijCode) {
        Partij result = null;
        if (BrpValidatie.isAttribuutGevuld(partijCode)) {
            result = dynamischeStamtabelRepository.getPartijByCode(partijCode.getWaarde());
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LandOfGebied findLandOfGebiedByCode(final BrpLandOfGebiedCode landOfGebiedCode) {
        return findLandOfGebiedByCode(landOfGebiedCode, SoortMeldingCode.PRE001);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LandOfGebied findLandOfGebiedByCode(final BrpLandOfGebiedCode landOfGebiedCode, final SoortMeldingCode soortMeldingCode) {
        if (!BrpValidatie.isAttribuutGevuld(landOfGebiedCode)) {
            return null;
        }
        try {
            return dynamischeStamtabelRepository.getLandOfGebiedByCode(landOfGebiedCode.getWaarde());
        } catch (final InvalidDataAccessApiUsageException idae) {
            LOG.error("Validatie Exceptie landcode code", idae);
            throw FoutmeldingUtil.getValidatieExceptie(
                    String.format("De landcode '%s' komt niet voor in de landtabel.", landOfGebiedCode.getWaarde()),
                    soortMeldingCode,
                    idae);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Verblijfsrecht findVerblijfsrechtByCode(final BrpVerblijfsrechtCode verblijfsrechtCode) {
        if (!BrpValidatie.isAttribuutGevuld(verblijfsrechtCode)) {
            return null;
        }
        return dynamischeStamtabelRepository.getVerblijfsrechtByCode(verblijfsrechtCode.getWaarde());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Aangever findAangeverByCode(final BrpAangeverCode aangeverCode) {
        if (!BrpValidatie.isAttribuutGevuld(aangeverCode)) {
            return null;
        }
        return dynamischeStamtabelRepository.getAangeverByCode(aangeverCode.getWaarde());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenWijzigingVerblijf findRedenWijzigingVerblijfByCode(final BrpRedenWijzigingVerblijfCode redenWijzigingVerblijfCode) {
        if (!BrpValidatie.isAttribuutGevuld(redenWijzigingVerblijfCode)) {
            return null;
        }
        return dynamischeStamtabelRepository.getRedenWijzigingVerblijf(redenWijzigingVerblijfCode.getWaarde());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Nationaliteit findNationaliteitByCode(final BrpNationaliteitCode brpNationaliteitCode) {
        if (!BrpValidatie.isAttribuutGevuld(brpNationaliteitCode)) {
            return null;
        }
        return dynamischeStamtabelRepository.getNationaliteitByNationaliteitcode(brpNationaliteitCode.getWaarde());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AanduidingInhoudingOfVermissingReisdocument findAanduidingInhoudingOfVermissingReisdocumentByCode(
            final BrpAanduidingInhoudingOfVermissingReisdocumentCode brpAanduidingInhoudingOfVermissingReisdocumentCode) {
        if (!BrpValidatie.isAttribuutGevuld(brpAanduidingInhoudingOfVermissingReisdocumentCode)) {
            return null;
        }
        final Character code = brpAanduidingInhoudingOfVermissingReisdocumentCode.getWaarde();
        return dynamischeStamtabelRepository.getAanduidingInhoudingOfVermissingReisdocumentByCode(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortNederlandsReisdocument findSoortNederlandsReisdocumentByCode(final BrpSoortNederlandsReisdocumentCode brpSoortNederlandsReisdocumentCode) {
        if (!BrpValidatie.isAttribuutGevuld(brpSoortNederlandsReisdocumentCode)) {
            return null;
        }
        return dynamischeStamtabelRepository.getSoortNederlandsReisdocumentByCode(brpSoortNederlandsReisdocumentCode.getWaarde());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortDocument findSoortDocumentByCode(final BrpSoortDocumentCode soortDocumentCode) {
        if (!BrpValidatie.isAttribuutGevuld(soortDocumentCode)) {
            return null;
        }
        return dynamischeStamtabelRepository.getSoortDocumentByNaam(soortDocumentCode.getWaarde());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenBeeindigingRelatie findRedenBeeindigingRelatieByCode(final BrpRedenEindeRelatieCode redenEinde) {
        if (!BrpValidatie.isAttribuutGevuld(redenEinde)) {
            return null;
        }
        return dynamischeStamtabelRepository.getRedenBeeindigingRelatieByCode(redenEinde.getWaarde());
    }

    @Override
    public Lo3Rubriek findLo3RubriekByNaam(final String lo3Rubriek) {
        if (lo3Rubriek == null) {
            return null;
        }
        return dynamischeStamtabelRepository.getLo3RubriekByNaam(lo3Rubriek);
    }

    @Override
    public SoortPartij findSoortPartijByNaam(final BrpSoortPartijCode soortPartijCode) {
        if (soortPartijCode == null) {
            return null;
        }
        return dynamischeStamtabelRepository.getSoortPartijByNaam(soortPartijCode.getSoortPartij());
    }

    @Override
    public AutoriteitAfgifteBuitenlandsPersoonsnummer findAutoriteitVanAfgifteBuitenlandsPersoonsnummer(
            final BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer autoriteitVanAfgifteBuitenlandsPersoonsnummer) {
        if (autoriteitVanAfgifteBuitenlandsPersoonsnummer == null) {
            return null;
        }
        return dynamischeStamtabelRepository.getAutorisatieVanAfgifteBuitenlandsPersoonsnummer(autoriteitVanAfgifteBuitenlandsPersoonsnummer.getWaarde());

    }
}
