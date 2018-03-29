/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.repositories.jpa;

import javax.annotation.Resource;
import nl.bzk.algemeenbrp.dal.domein.brp.alaag.ALaagAfleidingsUtil;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AutoriteitAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Plaats;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Rechtsgrond;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortActieBrongebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortActieBrongebruikSleutel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortPartij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Voorvoegsel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VoorvoegselSleutel;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.dal.repositories.StamtabelRepository;
import org.springframework.stereotype.Component;

/**
 * De JPA implementatie van {@link nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository}.
 */
@Component
public final class DynamischeStamtabelRepositoryImpl implements DynamischeStamtabelRepository {

    @Resource
    private StamtabelRepository<Aangever, Short, Character> aangeverRepository;
    @Resource
    private StamtabelRepository<LandOfGebied, Integer, String> landOfGebiedRepository;
    @Resource
    private StamtabelRepository<Nationaliteit, Integer, String> nationaliteitRepository;
    @Resource
    private StamtabelRepository<Gemeente, Short, String> gemeenteRepository;
    @Resource
    private StamtabelRepository<Gemeente, Short, String> gemeentePartijCodeRepository;
    @Resource
    private StamtabelRepository<Gemeente, Short, Partij> gemeentePartijRepository;
    @Resource
    private StamtabelRepository<Partij, Short, String> partijRepository;
    @Resource
    private StamtabelRepository<Partij, Short, String> partijNaamRepository;
    @Resource
    private StamtabelRepository<RedenBeeindigingRelatie, Short, Character> redenBeeindigingRelatieRepository;
    @Resource
    private StamtabelRepository<RedenVerkrijgingNLNationaliteit, Short, String> redenVerkrijgingNLNationaliteitRepository;
    @Resource
    private StamtabelRepository<RedenVerliesNLNationaliteit, Short, String> redenVerliesNLNationaliteitRepository;
    @Resource
    private StamtabelRepository<AanduidingInhoudingOfVermissingReisdocument, Short, Character> aanduidingInhoudingOfVermissingReisdocumentRepository;
    @Resource
    private StamtabelRepository<RedenWijzigingVerblijf, Short, Character> redenWijzigingVerblijfRepository;
    @Resource
    private StamtabelRepository<SoortDocument, Short, String> soortDocumentRepository;
    @Resource
    private StamtabelRepository<SoortNederlandsReisdocument, Short, String> soortNederlandsReisdocumentRepository;
    @Resource
    private StamtabelRepository<Verblijfsrecht, Short, String> verblijfsrechtRepository;
    @Resource
    private StamtabelRepository<SoortPartij, Short, String> soortPartijRepository;
    @Resource
    private StamtabelRepository<Plaats, Short, String> plaatsRepository;
    @Resource
    private StamtabelRepository<Voorvoegsel, Short, VoorvoegselSleutel> voorvoegselRepository;
    @Resource
    private StamtabelRepository<SoortActieBrongebruik, Short, SoortActieBrongebruikSleutel> soortActieBrongebruikRepository;
    @Resource
    private StamtabelRepository<Lo3Rubriek, Integer, String> lo3RubriekRepository;
    @Resource
    private StamtabelRepository<AutoriteitAfgifteBuitenlandsPersoonsnummer, Integer, String> autoriteitAfgifteBuitenlandsPersoonsnummerStamtabelRepository;
    @Resource
    private StamtabelRepository<Rechtsgrond, Short, String> rechtsgrondStamtabelRepository;

    @Override
    public Aangever getAangeverByCode(final char code) {
        return aangeverRepository.findByKey(code);
    }

    @Override
    public LandOfGebied getLandOfGebiedByCode(final String landcode) {
        return landOfGebiedRepository.findByKey(landcode);
    }

    @Override
    public Nationaliteit getNationaliteitByNationaliteitcode(final String nationaliteitCode) {
        return nationaliteitRepository.findByKey(nationaliteitCode);
    }

    @Override
    public Gemeente getGemeenteByGemeentecode(final String gemeentecode) {
        return gemeenteRepository.findByKey(gemeentecode);
    }

    @Override
    public Gemeente getGemeenteByPartijcode(final String partijcode) {
        return gemeentePartijCodeRepository.findByKey(partijcode);
    }

    @Override
    public Gemeente getGemeenteByPartij(Partij partij) {
        return gemeentePartijRepository.findByKey(partij);
    }

    @Override
    public Partij findPartijByNaam(String naam) {
        return partijNaamRepository.findByKey(naam);
    }

    @Override
    public Partij getPartijByCode(final String code) {
        return partijRepository.findByKey(code);
    }

    @Override
    public RedenBeeindigingRelatie getRedenBeeindigingRelatieByCode(final char code) {
        return redenBeeindigingRelatieRepository.findByKey(code);
    }

    @Override
    public RedenVerkrijgingNLNationaliteit getRedenVerkrijgingNLNationaliteitByCode(final String code) {
        return redenVerkrijgingNLNationaliteitRepository.findByKey(code);
    }

    @Override
    public RedenVerliesNLNationaliteit getRedenVerliesNLNationaliteitByCode(final String code) {
        return redenVerliesNLNationaliteitRepository.findByKey(code);
    }

    @Override
    public AanduidingInhoudingOfVermissingReisdocument getAanduidingInhoudingOfVermissingReisdocumentByCode(final char code) {
        return aanduidingInhoudingOfVermissingReisdocumentRepository.findByKey(code);
    }

    @Override
    public RedenWijzigingVerblijf getRedenWijzigingVerblijf(final char code) {
        return redenWijzigingVerblijfRepository.findByKey(code);
    }

    @Override
    public SoortDocument getSoortDocumentByNaam(final String naam) {
        return soortDocumentRepository.findByKey(naam);
    }

    @Override
    public SoortNederlandsReisdocument getSoortNederlandsReisdocumentByCode(final String code) {
        return soortNederlandsReisdocumentRepository.findByKey(code);
    }

    @Override
    public Verblijfsrecht getVerblijfsrechtByCode(final String code) {
        return verblijfsrechtRepository.findByKey(code);
    }

    @Override
    public Lo3Rubriek getLo3RubriekByNaam(String lo3Rubriek) {
        return lo3RubriekRepository.findByKey(lo3Rubriek);
    }

    @Override
    public SoortPartij getSoortPartijByNaam(final String naam) {
        return soortPartijRepository.findByKey(naam);
    }

    @Override
    public AutoriteitAfgifteBuitenlandsPersoonsnummer getAutorisatieVanAfgifteBuitenlandsPersoonsnummer(String waarde) {
        return autoriteitAfgifteBuitenlandsPersoonsnummerStamtabelRepository.findByKey(waarde);
    }

    @Override
    public Plaats getPlaatsByPlaatsNaam(final String plaatsnaam) {
        return plaatsRepository.findByKey(plaatsnaam);
    }

    @Override
    public Voorvoegsel getVoorvoegselByVoorvoegselSleutel(final VoorvoegselSleutel voorvoegselSleutel) {
        return voorvoegselRepository.findByKey(voorvoegselSleutel);
    }

    @Override
    public SoortActieBrongebruik getSoortActieBrongebruikBySoortActieBrongebruikSleutel(final SoortActieBrongebruikSleutel soortActieBrongebruikSleutel) {
        return soortActieBrongebruikRepository.findByKey(soortActieBrongebruikSleutel);
    }

    @Override
    public Rechtsgrond getRechtsgrondByCode(final String code) {
        return rechtsgrondStamtabelRepository.findByKey(code);
    }

    @Override
    public Partij savePartij(Partij partij) {
        ALaagAfleidingsUtil.vulALaag(partij);
        partijRepository.merge(partij);
        partijRepository.clear();
        return partijRepository.findByKey(partij.getCode());
    }
}
