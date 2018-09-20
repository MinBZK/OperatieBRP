/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.FoutmeldingUtil;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Aangever;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.LandOfGebied;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Nationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenBeeindigingRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenVerliesNLNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenWijzigingVerblijf;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortDocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPartij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Verblijfsrecht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.Lo3Rubriek;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.springframework.dao.InvalidDataAccessApiUsageException;

/* Class Fan-Out complexity is hier hoog maar deze class is niet onnodig complex */
/**
 * Een adapter voor het bevragen van stamtabellen voor het mappen van het migratie model op de entiteiten uit het
 * operationele model van de BRP.
 *
 */
final class DynamischeStamtabelRepositoryAdapter implements StamtabelMapping {

    private static final Logger LOG = LoggerFactory.getLogger();
    private final DynamischeStamtabelRepository dynamischeStamtabelRepository;

    /**
     * Maakt een DynamischeStamtabelRepositoryAdapter object.
     *
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt.
     */
    public DynamischeStamtabelRepositoryAdapter(final DynamischeStamtabelRepository dynamischeStamtabelRepository) {
        this.dynamischeStamtabelRepository = dynamischeStamtabelRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenVerliesNLNationaliteit findRedenVerliesNLNationaliteitByCode(final BrpRedenVerliesNederlandschapCode redenVerliesNederlandschapCode) {
        if (!Validatie.isAttribuutGevuld(redenVerliesNederlandschapCode)) {
            return null;
        }
        return dynamischeStamtabelRepository.getRedenVerliesNLNationaliteitByCode(redenVerliesNederlandschapCode.getWaarde());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenVerkrijgingNLNationaliteit findRedenVerkrijgingNLNationaliteitByCode(
        final BrpRedenVerkrijgingNederlandschapCode redenVerkrijgingNederlandschapCode)
    {
        if (!Validatie.isAttribuutGevuld(redenVerkrijgingNederlandschapCode)) {
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
        if (!Validatie.isAttribuutGevuld(gemeenteCode)) {
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
        if (Validatie.isAttribuutGevuld(partijCode)) {
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
        if (!Validatie.isAttribuutGevuld(landOfGebiedCode)) {
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
        if (!Validatie.isAttribuutGevuld(verblijfsrechtCode)) {
            return null;
        }
        return dynamischeStamtabelRepository.getVerblijfsrechtByCode(verblijfsrechtCode.getWaarde());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Aangever findAangeverByCode(final BrpAangeverCode aangeverCode) {
        if (!Validatie.isAttribuutGevuld(aangeverCode)) {
            return null;
        }
        return dynamischeStamtabelRepository.getAangeverByCode(aangeverCode.getWaarde());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenWijzigingVerblijf findRedenWijzigingVerblijfByCode(final BrpRedenWijzigingVerblijfCode redenWijzigingVerblijfCode) {
        if (!Validatie.isAttribuutGevuld(redenWijzigingVerblijfCode)) {
            return null;
        }
        return dynamischeStamtabelRepository.getRedenWijzigingVerblijf(redenWijzigingVerblijfCode.getWaarde());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Nationaliteit findNationaliteitByCode(final BrpNationaliteitCode brpNationaliteitCode) {
        if (!Validatie.isAttribuutGevuld(brpNationaliteitCode)) {
            return null;
        }
        return dynamischeStamtabelRepository.getNationaliteitByNationaliteitcode(brpNationaliteitCode.getWaarde());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AanduidingInhoudingOfVermissingReisdocument findAanduidingInhoudingOfVermissingReisdocumentByCode(
        final BrpAanduidingInhoudingOfVermissingReisdocumentCode brpAanduidingInhoudingOfVermissingReisdocumentCode)
    {
        if (!Validatie.isAttribuutGevuld(brpAanduidingInhoudingOfVermissingReisdocumentCode)) {
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
        if (!Validatie.isAttribuutGevuld(brpSoortNederlandsReisdocumentCode)) {
            return null;
        }
        return dynamischeStamtabelRepository.getSoortNederlandsReisdocumentByCode(brpSoortNederlandsReisdocumentCode.getWaarde());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortDocument findSoortDocumentByCode(final BrpSoortDocumentCode soortDocumentCode) {
        if (!Validatie.isAttribuutGevuld(soortDocumentCode)) {
            return null;
        }
        return dynamischeStamtabelRepository.getSoortDocumentByNaam(soortDocumentCode.getWaarde());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenBeeindigingRelatie findRedenBeeindigingRelatieByCode(final BrpRedenEindeRelatieCode redenEinde) {
        if (!Validatie.isAttribuutGevuld(redenEinde)) {
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
}
