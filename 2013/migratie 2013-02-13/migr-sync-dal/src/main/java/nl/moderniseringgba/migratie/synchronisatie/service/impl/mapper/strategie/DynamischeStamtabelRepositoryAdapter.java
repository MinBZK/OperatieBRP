/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie;

import java.math.BigDecimal;

import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAangeverAdreshoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenWijzigingAdresCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifte;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentRedenOntbreken;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentSoort;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.groep.FoutmeldingUtil;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.AangeverAdreshouding;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.AutoriteitVanAfgifteReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Land;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Nationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Partij;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Plaats;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenBeeindigingRelatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenVerkrijgingNLNationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenVerliesNLNationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenVervallenReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenWijzigingAdres;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortDocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortNederlandsReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Verblijfsrecht;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;

import org.springframework.dao.InvalidDataAccessApiUsageException;

/**
 * Een adapter voor het bevragen van stamtabellen voor het mappen van het migratie model op de entiteiten uit het
 * operationele model van de BRP.
 * 
 */
// CHECKSTYLE:OFF Class Fan-Out complexity is hier hoog maar deze class is niet onnodig complex
final class DynamischeStamtabelRepositoryAdapter implements StamtabelMapping {
    // CHECKSTYLE:ON

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
    public RedenVerliesNLNationaliteit findRedenVerliesNLNationaliteitByCode(
            final BrpRedenVerliesNederlandschapCode redenVerliesNederlandschapCode) {
        if (redenVerliesNederlandschapCode == null) {
            return null;
        }
        // TODO property code toevoegen aan RedenVerliesNLNationaliteit entiteit
        return dynamischeStamtabelRepository.findRedenVerliesNLNationaliteitByNaam(redenVerliesNederlandschapCode
                .getNaam());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenVerkrijgingNLNationaliteit findRedenVerkrijgingNLNationaliteitByCode(
            final BrpRedenVerkrijgingNederlandschapCode redenVerkrijgingNederlandschapCode) {
        if (redenVerkrijgingNederlandschapCode == null) {
            return null;
        }
        // TODO property code toevoegen aan RedenVerkrijgingNLNationaliteit entiteit
        return dynamischeStamtabelRepository
                .findRedenVerkrijgingNLNationaliteitByNaam(redenVerkrijgingNederlandschapCode.getNaam());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij findPartijByGemeentecode(final BrpGemeenteCode bijhoudingsgemeenteCode) {
        return findPartijByGemeentecode(bijhoudingsgemeenteCode, Precondities.PRE002);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij findPartijByGemeentecode(
            final BrpGemeenteCode bijhoudingsgemeenteCode,
            final Precondities preconditie) {
        if (bijhoudingsgemeenteCode == null) {
            return null;
        }
        try {
            return dynamischeStamtabelRepository.findPartijByGemeentecode(bijhoudingsgemeenteCode.getCode());
        } catch (final InvalidDataAccessApiUsageException idae) {
            throw FoutmeldingUtil.getValidatieExceptie(
                    String.format("De gemeentecode '%s' komt niet voor in de gemeentetabel.",
                            bijhoudingsgemeenteCode.getCode()), preconditie);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij findPartijByPartijcode(final BrpPartijCode partijCode) {
        Partij result = null;
        if (partijCode != null) {
            if (partijCode.getNaam() != null) {
                result = dynamischeStamtabelRepository.findPartijByNaam(partijCode.getNaam());
            }
            if (partijCode.getGemeenteCode() != null) {
                result =
                        dynamischeStamtabelRepository.findPartijByGemeentecode(new BigDecimal(partijCode
                                .getGemeenteCode()));
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Plaats findPlaatsByCode(final BrpPlaatsCode plaatsCode) {
        if (plaatsCode == null || plaatsCode.bevatStandaardWaarde()) {
            return null;
        }
        try {
            return dynamischeStamtabelRepository.findPlaatsByNaam(plaatsCode.getNaam());
        } catch (final InvalidDataAccessApiUsageException iae) {
            throw FoutmeldingUtil.getValidatieExceptie(
                    String.format("PlaatsCode '%s' komt niet voor in de BRP Plaats tabel.", plaatsCode.getNaam()),
                    Precondities.PRE057);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Land findLandByLandcode(final BrpLandCode landCode) {
        return findLandByLandcode(landCode, Precondities.PRE001);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Land findLandByLandcode(final BrpLandCode landCode, final Precondities preconditie) {
        if (landCode == null) {
            return null;
        }
        try {
            return dynamischeStamtabelRepository.findLandByLandcode(new BigDecimal(landCode.getCode()));
        } catch (final InvalidDataAccessApiUsageException idae) {
            throw FoutmeldingUtil.getValidatieExceptie(
                    String.format("De landcode '%s' komt niet voor in de landtabel.", landCode.getCode()),
                    preconditie);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Verblijfsrecht findVerblijfsrechtByCode(final BrpVerblijfsrechtCode verblijfsrechtCode) {
        if (verblijfsrechtCode == null) {
            return null;
        }
        // TODO code property toevoegen aan Verblijfsrecht entiteit
        return dynamischeStamtabelRepository.findVerblijfsrechtByOmschrijving(verblijfsrechtCode.getOmschrijving());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AangeverAdreshouding findAangeverAdreshouding(final BrpAangeverAdreshoudingCode aangeverAdreshoudingCode) {
        if (aangeverAdreshoudingCode == null) {
            return null;
        }
        return dynamischeStamtabelRepository.findAangeverAdreshoudingByCode(aangeverAdreshoudingCode.getCode());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenWijzigingAdres findRedenWijzigingAdres(final BrpRedenWijzigingAdresCode redenWijzigingAdresCode) {
        if (redenWijzigingAdresCode == null) {
            return null;
        }
        return dynamischeStamtabelRepository.findRedenWijzigingAdres(redenWijzigingAdresCode.getCode());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Nationaliteit findNationaliteitByNationaliteitcode(final BrpNationaliteitCode brpNationaliteitCode) {
        if (brpNationaliteitCode == null) {
            return null;
        }
        return dynamischeStamtabelRepository.findNationaliteitByNationaliteitcode(new BigDecimal(brpNationaliteitCode
                .getCode()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AutoriteitVanAfgifteReisdocument findAutoriteitVanAfgifteReisdocumentByCode(
            final BrpReisdocumentAutoriteitVanAfgifte brpReisdocumentAutoriteitVanAfgifte) {
        if (brpReisdocumentAutoriteitVanAfgifte == null) {
            return null;
        }
        return dynamischeStamtabelRepository
                .findAutoriteitVanAfgifteReisdocumentByCode(brpReisdocumentAutoriteitVanAfgifte.getCode());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenVervallenReisdocument findRedenVervallenReisdocumentByCode(
            final BrpReisdocumentRedenOntbreken brpReisdocumentRedenOntbreken) {
        if (brpReisdocumentRedenOntbreken == null) {
            return null;
        }
        return dynamischeStamtabelRepository.findRedenVervallenReisdocumentByCode(brpReisdocumentRedenOntbreken
                .getCode());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortNederlandsReisdocument findSoortNederlandsReisdocumentByCode(
            final BrpReisdocumentSoort brpReisdocumentSoort) {
        if (brpReisdocumentSoort == null) {
            return null;
        }
        return dynamischeStamtabelRepository.findSoortNederlandsReisdocumentByCode(brpReisdocumentSoort.getCode());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortDocument findSoortDocumentByCode(final BrpSoortDocumentCode soortDocumentCode) {
        if (soortDocumentCode == null) {
            return null;
        }
        return dynamischeStamtabelRepository.findSoortDocumentByOmschrijving(soortDocumentCode.getCode());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenBeeindigingRelatie findRedenBeeindigingRelatieByCode(final BrpRedenEindeRelatieCode redenEinde) {
        if (redenEinde == null) {
            return null;
        }
        return dynamischeStamtabelRepository.findRedenBeeindigingRelatieByCode(redenEinde.getCode());
    }
}
