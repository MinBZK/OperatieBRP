/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service.impl;

import javax.inject.Inject;

import nl.bzk.migratiebrp.ggo.viewer.service.StamtabelService;
import nl.bzk.migratiebrp.ggo.viewer.util.VerwerkerUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FunctieAdres;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Geslachtsaanduiding;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.LandOfGebied;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Naamgebruik;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Nationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenBeeindigingRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenVerliesNLNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Verblijfsrecht;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Implementatie van StamtabelService.
 *
 */
@Component
public class StamtabelServiceImpl implements StamtabelService {

    /**
     * Het formaat voor de log messages.
     */
    protected static final String LOG_MSG_FORMAT = "%s '%s' niet gevonden";
    /**
     * code (omschrijving).
     */
    protected static final String STRING_FORMAT = "%s (%s)";
    /**
     * code (omschrijving mannelijk / omschrijving vrouwelijk).
     */
    protected static final String STRING_FORMAT_GESL = "%s (%s / %s)";
    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getFunctieAdres(final String functieAdresCode) {
        String result;
        try {
            final FunctieAdres functieAdres = FunctieAdres.parseCode(functieAdresCode);
            result = String.format(STRING_FORMAT, functieAdres.getCode(), functieAdres.getNaam());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "FunctieAdres", functieAdresCode);
            LOG.warn(logMsg);
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
                final Gemeente gemeente = getDynamischeStamtabelRepository().getGemeenteByGemeentecode(StamtabelServiceImpl.parse(gemeenteCode));
                final String gemeenteCodePadded = VerwerkerUtil.zeroPad(String.valueOf(gemeente.getCode()), gemeenteCodeSize);
                result = String.format(STRING_FORMAT, gemeenteCodePadded, gemeente.getNaam());
            } catch (final IllegalArgumentException iae) {
                final String logMsg = String.format(LOG_MSG_FORMAT, "GemeenteCode", gemeenteCode);
                LOG.warn(logMsg);
                result = VerwerkerUtil.zeroPad(gemeenteCode, gemeenteCodeSize);
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
            LOG.warn(logMsg);
            result = geslachtsaanduidingCode;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getLandOfGebied(final String landCode) {
        final int landCodeSize = 4;
        String result;
        try {
            final LandOfGebied landOfGebied = getDynamischeStamtabelRepository().getLandOfGebiedByCode(StamtabelServiceImpl.parse(landCode));
            final String landCodePadded = VerwerkerUtil.zeroPad(String.valueOf(landOfGebied.getCode()), landCodeSize);
            result = String.format(STRING_FORMAT, landCodePadded, landOfGebied.getNaam());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "LandCode", landCode);
            LOG.warn(logMsg);
            result = VerwerkerUtil.zeroPad(landCode, landCodeSize);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getNationaliteit(final String nationaliteitCode) {
        final int nationaliteitCodeSize = 4;
        String result;
        try {
            final Nationaliteit nationaliteit =
                    getDynamischeStamtabelRepository().getNationaliteitByNationaliteitcode(StamtabelServiceImpl.parse(nationaliteitCode));
            final String nationaliteitCodePadded = VerwerkerUtil.zeroPad(Short.toString(nationaliteit.getCode()), nationaliteitCodeSize);
            result = String.format(STRING_FORMAT, nationaliteitCodePadded, nationaliteit.getNaam());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "Nationaliteit", nationaliteitCode);
            LOG.warn(logMsg);
            result = VerwerkerUtil.zeroPad(nationaliteitCode, nationaliteitCodeSize);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getRedenVerkrijgingNederlandschap(final String redenVerkrijgingNederlandschapCode) {
        final int redenNLSize = 3;
        String result;
        try {
            final RedenVerkrijgingNLNationaliteit redenVerkrijgingNl =
                    getDynamischeStamtabelRepository().getRedenVerkrijgingNLNationaliteitByCode(
                        StamtabelServiceImpl.parse(redenVerkrijgingNederlandschapCode));
            final String redenVerkrijgingNlCodePadded = VerwerkerUtil.zeroPad(String.valueOf(redenVerkrijgingNl.getCode()), redenNLSize);
            result = String.format(STRING_FORMAT, redenVerkrijgingNlCodePadded, redenVerkrijgingNl.getOmschrijving());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "RedenVerkrijgingNederlandschapCode", redenVerkrijgingNederlandschapCode);
            LOG.warn(logMsg);
            result = VerwerkerUtil.zeroPad(redenVerkrijgingNederlandschapCode, redenNLSize);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getRedenVerliesNederlandschap(final String redenVerliesNederlandschapCode) {
        final int redenNLSize = 3;
        String result;
        try {
            final RedenVerliesNLNationaliteit redenVerliesNl =
                    getDynamischeStamtabelRepository().getRedenVerliesNLNationaliteitByCode(StamtabelServiceImpl.parse(redenVerliesNederlandschapCode));
            final String redenVerliesNlCodePadded = VerwerkerUtil.zeroPad(String.valueOf(redenVerliesNl.getCode()), redenNLSize);
            result = String.format(STRING_FORMAT, redenVerliesNlCodePadded, redenVerliesNl.getOmschrijving());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "RedenVerliesNederlandschapCode", redenVerliesNederlandschapCode);
            LOG.warn(logMsg);
            result = VerwerkerUtil.zeroPad(redenVerliesNederlandschapCode, redenNLSize);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getVerblijfstitel(final String verblijfstitelCode) {
        final int verblijfstitelCodeSize = 2;
        String result;
        try {
            final Verblijfsrecht verblijfsrecht =
                    getDynamischeStamtabelRepository().getVerblijfsrechtByCode(StamtabelServiceImpl.parse(verblijfstitelCode));
            result = String.format(STRING_FORMAT, verblijfsrecht.getCode(), verblijfsrecht.getOmschrijving());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "Verblijfsrecht", verblijfstitelCode);
            LOG.warn(logMsg);
            result = VerwerkerUtil.zeroPad(verblijfstitelCode, verblijfstitelCodeSize);
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
            LOG.warn(logMsg);
            result = naamgebruikCode;
        }
        return result;
    }

    /**
     * Parsed de String code naar een Short code.
     *
     * @param code
     *            String
     * @return code Short
     */
    private static Short parse(final String code) {
        Short result = null;
        try {
            result = Short.valueOf(code);
        } catch (final NumberFormatException nfe) {
            LOG.warn("Kan code '" + code + "' niet omzetten naar een getal (Short)", nfe);
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
            LOG.warn(logMsg);
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
            LOG.warn(logMsg);
            result = brpRedenEindeRelatieCode;
        }
        return result;
    }

    /**
     * Geef de waarde van dynamische stamtabel repository.
     *
     * @return the dynamischeStamtabelRepository
     */
    protected final DynamischeStamtabelRepository getDynamischeStamtabelRepository() {
        return dynamischeStamtabelRepository;
    }

}
