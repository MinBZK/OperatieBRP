/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import javax.inject.Inject;

import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.AangeverAdreshoudingRepository;
import nl.bzk.brp.dataaccess.repository.AdellijkeTitelRepository;
import nl.bzk.brp.dataaccess.repository.LandRepository;
import nl.bzk.brp.dataaccess.repository.NationaliteitRepository;
import nl.bzk.brp.dataaccess.repository.PartijRepository;
import nl.bzk.brp.dataaccess.repository.PlaatsRepository;
import nl.bzk.brp.dataaccess.repository.PredikaatRepository;
import nl.bzk.brp.dataaccess.repository.RedenVerkrijgingNLNationaliteitRepository;
import nl.bzk.brp.dataaccess.repository.RedenVerliesNLNationaliteitRepository;
import nl.bzk.brp.dataaccess.repository.RedenWijzigingAdresRepository;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.attribuuttype.AangeverAdreshoudingCode;
import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.Nationaliteitcode;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.attribuuttype.PredikaatCode;
import nl.bzk.brp.model.attribuuttype.RedenVerkrijgingCode;
import nl.bzk.brp.model.attribuuttype.RedenVerliesNaam;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AangeverAdreshouding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Nationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Predikaat;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenWijzigingAdres;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Repository voor referentieData en standaard implementatie van de
 * {@link nl.bzk.brp.dataaccess.repository.ReferentieDataRepository} class.
 */
@Component
class ReferentieDataRepositoryImpl implements ReferentieDataRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReferentieDataRepositoryImpl.class);

    @Inject
    private PlaatsRepository plaatsRepository;

    @Inject
    private PartijRepository partijRepository;

    @Inject
    private LandRepository landRepository;

    @Inject
    private NationaliteitRepository nationaliteitRepository;

    @Inject
    private RedenWijzigingAdresRepository redenWijzigingAdresRepository;

    @Inject
    private AangeverAdreshoudingRepository aangeverAdreshoudingRepository;

    @Inject
    private AdellijkeTitelRepository adellijkeTitelRepository;

    @Inject
    private PredikaatRepository predikaatRepository;

    @Inject
    private RedenVerkrijgingNLNationaliteitRepository redenVerkrijgingNLNationaliteitRepository;

    @Inject
    private RedenVerliesNLNationaliteitRepository redenVerliesNLNationaliteitRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Plaats vindWoonplaatsOpCode(final PlaatsCode code) {
        final Plaats result = plaatsRepository.vindWoonplaatsOpCode(code);

        if (result == null) {
            LOGGER.info("Onbekende woonplaatscode '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PLAATSCODE,
                                                  code, null);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij vindGemeenteOpCode(final Gemeentecode code) {
        final Partij result = partijRepository.vindGemeenteOpCode(code);

        if (result == null) {
            LOGGER.info("Onbekende gemeentecode '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.GEMEENTECODE,
                                                  code, null);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Land vindLandOpCode(final Landcode code) {
        final Land result = landRepository.vindLandOpCode(code);

        if (result == null) {
            LOGGER.info("Onbekende landcode '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.LANDCODE,
                                                  code, null);
        }
        return result;
    }

    @Override
    public Nationaliteit vindNationaliteitOpCode(final Nationaliteitcode code) {
        final Nationaliteit result = nationaliteitRepository.vindNationaliteitOpCode(code);

        if(result == null) {
            LOGGER.info("Onbekende nationaliteit code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.NATIONALITITEITCODE,
                                                  code, null);
        }
        return result;
    }

    @Override
    public RedenWijzigingAdres vindRedenWijzingAdresOpCode(final RedenWijzigingAdresCode code) {
        final RedenWijzigingAdres result = redenWijzigingAdresRepository.vindRedenWijzingAdresOpCode(code);

        if(result == null) {
            LOGGER.info("Onbekende RedenWijzigingAdres code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.REDENWIJZINGADRES,
                                                  code, null);
        }
        return result;
    }

    @Override
    public AangeverAdreshouding vindAangeverAdreshoudingOpCode(final AangeverAdreshoudingCode code) {
        final AangeverAdreshouding result = aangeverAdreshoudingRepository.vindAangeverAdreshoudingOpCode(code);

        if(result == null) {
            LOGGER.info("Onbekende AangeverAdreshouding code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.AAANGEVERADRESHOUDING,
                                                  code, null);
        }
        return result;
    }

    @Override
    public AdellijkeTitel vindAdellijkeTitelOpCode(final AdellijkeTitelCode code) {
        final AdellijkeTitel result = adellijkeTitelRepository.vindAdellijkeTitelOpCode(code);

        if(result == null) {
            LOGGER.info("Onbekende AdellijkeTitel code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.ADELLIJKETITEL,
                                                  code, null);
        }
        return result;
    }

    @Override
    public Predikaat vindPredikaatOpCode(final PredikaatCode code) {
        final Predikaat result = predikaatRepository.findOneByCode(code);

        if(result == null) {
            LOGGER.info("Onbekende Predikaat code, '{}' niet gevonden.", code.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PREDIKAAT,
                                                  code, null);
        }
        return result;
    }

    @Override
    public RedenVerkrijgingNLNationaliteit vindRedenVerkregenNlNationaliteitOpCode(
            final RedenVerkrijgingCode redenVerkrijgingCode)
    {
        final RedenVerkrijgingNLNationaliteit result = redenVerkrijgingNLNationaliteitRepository.findOneByCode(redenVerkrijgingCode);

        if(result == null) {
            LOGGER.info("Onbekende Reden verkrijgen NL nationaliteit naam, '{}' niet gevonden.",
                        redenVerkrijgingCode.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.REDENVERKRIJGENNLNATION,
                                                  redenVerkrijgingCode, null);
        }
        return result;
    }

    @Override
    public RedenVerliesNLNationaliteit vindRedenVerliesNLNationaliteitOpNaam(final RedenVerliesNaam naam) {
        final RedenVerliesNLNationaliteit result = redenVerliesNLNationaliteitRepository.findOneByNaam(naam);

        if(result == null) {
            LOGGER.info("Onbekende Reden verlies NL nationaliteit naam, '{}' niet gevonden.", naam.getWaarde());
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.REDENVERLIESNLNATION,
                                                  naam, null);
        }
        return result;
    }
}
