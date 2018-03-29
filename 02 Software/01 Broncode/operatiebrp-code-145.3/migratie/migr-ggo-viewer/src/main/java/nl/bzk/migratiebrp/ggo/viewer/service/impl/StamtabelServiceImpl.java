/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service.impl;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.ggo.viewer.service.StamtabelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Implementatie van StamtabelService.
 */
@Component
public class StamtabelServiceImpl implements StamtabelService {

    /**
     * Het formaat voor de log messages.
     */
    static final String LOG_MSG_FORMAT = "%s '%s' niet gevonden";
    /**
     * code (omschrijving).
     */
    static final String STRING_FORMAT = "%s (%s)";
    /**
     * code (omschrijving mannelijk / omschrijving vrouwelijk).
     */
    static final String STRING_FORMAT_GESL = "%s (%s / %s)";
    private static final Logger LOG = LoggerFactory.getLogger();

    private final DynamischeStamtabelRepository dynamischeStamtabelRepository;

    /**
     * Constructor.
     * @param dynamischeStamtabelRepository de repository voor de dynamische stamtabel
     */
    @Inject
    public StamtabelServiceImpl(final DynamischeStamtabelRepository dynamischeStamtabelRepository) {
        this.dynamischeStamtabelRepository = dynamischeStamtabelRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getFunctieAdres(final String functieAdresCode) {
        String result;
        try {
            final SoortAdres functieAdres = SoortAdres.parseCode(functieAdresCode);
            result = String.format(STRING_FORMAT, functieAdres.getCode(), functieAdres.getNaam());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "FunctieAdres", functieAdresCode);
            LOG.warn(logMsg, iae);
            result = functieAdresCode;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getGemeente(final String gemeenteCode) {
        final int gemeenteCodeSize = 4;
        String result;

        if (!StringUtils.isNumeric(gemeenteCode.substring(0, 1)) || gemeenteCode.length() != gemeenteCodeSize) {
            result = gemeenteCode;
        } else {
            try {
                final Gemeente gemeente = getDynamischeStamtabelRepository().getGemeenteByGemeentecode(gemeenteCode);
                result = String.format(STRING_FORMAT, gemeente.getCode(), gemeente.getNaam());
            } catch (final IllegalArgumentException iae) {
                final String logMsg = String.format(LOG_MSG_FORMAT, "GemeenteCode", gemeenteCode);
                LOG.warn(logMsg, iae);
                result = gemeenteCode;
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getGeslachtsaanduiding(final String geslachtsaanduidingCode) {
        String result;
        try {
            final Geslachtsaanduiding geslachtsaanduiding = Geslachtsaanduiding.parseCode(geslachtsaanduidingCode);
            result = String.format(STRING_FORMAT, geslachtsaanduiding.getCode(), geslachtsaanduiding.getNaam());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "Geslachtsaanduiding", geslachtsaanduidingCode);
            LOG.warn(logMsg, iae);
            result = geslachtsaanduidingCode;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getLandOfGebied(final String landCode) {
        String result;
        try {
            final LandOfGebied landOfGebied = getDynamischeStamtabelRepository().getLandOfGebiedByCode(landCode);
            result = String.format(STRING_FORMAT, landOfGebied.getCode(), landOfGebied.getNaam());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "LandCode", landCode);
            LOG.warn(logMsg, iae);
            result = landCode;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getNationaliteit(final String nationaliteitCode) {
        String result;
        try {
            final Nationaliteit nationaliteit =
                    getDynamischeStamtabelRepository().getNationaliteitByNationaliteitcode(nationaliteitCode);
            result = String.format(STRING_FORMAT, nationaliteit.getCode(), nationaliteit.getNaam());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "Nationaliteit", nationaliteitCode);
            LOG.warn(logMsg, iae);
            result = nationaliteitCode;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getRedenVerkrijgingNederlandschap(final String redenVerkrijgingNederlandschapCode) {
        String result;
        final RedenVerkrijgingNLNationaliteit redenVerkrijgingNl =
                getDynamischeStamtabelRepository().getRedenVerkrijgingNLNationaliteitByCode(redenVerkrijgingNederlandschapCode);
        if (redenVerkrijgingNl != null) {
            result = String.format(STRING_FORMAT, redenVerkrijgingNl.getCode(), redenVerkrijgingNl.getOmschrijving());
        } else {
            final String logMsg = String.format(LOG_MSG_FORMAT, "RedenVerkrijgingNederlandschapCode", redenVerkrijgingNederlandschapCode);
            LOG.warn(logMsg);
            result = redenVerkrijgingNederlandschapCode;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getRedenVerliesNederlandschap(final String redenVerliesNederlandschapCode) {
        String result;
        final RedenVerliesNLNationaliteit redenVerliesNl =
                getDynamischeStamtabelRepository().getRedenVerliesNLNationaliteitByCode(redenVerliesNederlandschapCode);
        if (redenVerliesNl == null) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "RedenVerliesNederlandschapCode", redenVerliesNederlandschapCode);
            LOG.warn(logMsg);
            result = redenVerliesNederlandschapCode;
        } else {
            result = String.format(STRING_FORMAT, redenVerliesNl.getCode(), redenVerliesNl.getOmschrijving());

        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getVerblijfstitel(final String verblijfstitelCode) {
        String result;
        try {
            final Verblijfsrecht verblijfsrecht =
                    getDynamischeStamtabelRepository().getVerblijfsrechtByCode(verblijfstitelCode);
            result = String.format(STRING_FORMAT, verblijfsrecht.getCode(), verblijfsrecht.getOmschrijving());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "Verblijfsrecht", verblijfstitelCode);
            LOG.warn(logMsg, iae);
            result = verblijfstitelCode;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getNaamgebruik(final String naamgebruikCode) {
        String result;
        try {
            final Naamgebruik naamgebruik = Naamgebruik.parseCode(naamgebruikCode);
            result = String.format(STRING_FORMAT, naamgebruik.getCode(), naamgebruik.getNaam());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "naamgebruik", naamgebruikCode);
            LOG.warn(logMsg, iae);
            result = naamgebruikCode;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getAutoriteitVanAfgifteReisdocument(final String reisdocumentAutoriteitVanAfgifte) {
        return reisdocumentAutoriteitVanAfgifte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getSoortNlReisdocument(final String brpReisdocumentSoort) {
        String result;
        try {
            final SoortNederlandsReisdocument soortNlReisdocument =
                    getDynamischeStamtabelRepository().getSoortNederlandsReisdocumentByCode(brpReisdocumentSoort);
            result = String.format(STRING_FORMAT, soortNlReisdocument.getCode(), soortNlReisdocument.getOmschrijving());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "SoortNlReisdocument", brpReisdocumentSoort);
            LOG.warn(logMsg, iae);
            result = brpReisdocumentSoort;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getRedenEindeRelatie(final String brpRedenEindeRelatieCode) {
        String result;
        try {
            final RedenBeeindigingRelatie redenBeeindigingRelatie =
                    getDynamischeStamtabelRepository().getRedenBeeindigingRelatieByCode(brpRedenEindeRelatieCode.charAt(0));
            result = String.format(STRING_FORMAT, redenBeeindigingRelatie.getCode(), redenBeeindigingRelatie.getOmschrijving());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "RedenEindeRelatie", brpRedenEindeRelatieCode);
            LOG.warn(logMsg, iae);
            result = brpRedenEindeRelatieCode;
        }
        return result;
    }

    /**
     * Geef de waarde van dynamische stamtabel repository.
     * @return the dynamischeStamtabelRepository
     */
    final DynamischeStamtabelRepository getDynamischeStamtabelRepository() {
        return dynamischeStamtabelRepository;
    }

}
