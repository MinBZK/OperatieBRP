/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service.impl;

import nl.bzk.migratiebrp.ggo.viewer.service.BrpStamtabelService;
import nl.bzk.migratiebrp.ggo.viewer.util.VerwerkerUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Aangever;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdellijkeTitel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Bijhoudingsaard;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.NadereBijhoudingsaard;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Predicaat;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenWijzigingVerblijf;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortMigratie;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Implementatie van BrpStamtabelService.
 */
@Component
public class BrpStamtabelServiceImpl extends StamtabelServiceImpl implements BrpStamtabelService {
    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getAangever(final Character brpAangeverCode) {
        String result;
        try {
            final Aangever aangever = getDynamischeStamtabelRepository().getAangeverByCode(brpAangeverCode);
            result = String.format(STRING_FORMAT, aangever.getCode(), aangever.getNaam());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "Aangever", brpAangeverCode);
            LOG.warn(logMsg);
            result = String.valueOf(brpAangeverCode);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getAdellijkeTitel(final String brpAdellijkeTitelCode) {
        String result;
        try {
            final AdellijkeTitel adelijkeTitel = AdellijkeTitel.parseCode(brpAdellijkeTitelCode);
            result = String.format(STRING_FORMAT_GESL, adelijkeTitel.getCode(), adelijkeTitel.getNaamMannelijk(), adelijkeTitel.getNaamVrouwelijk());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "AdellijkeTitel", brpAdellijkeTitelCode);
            LOG.warn(logMsg);
            result = brpAdellijkeTitelCode;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getBijhoudingsaard(final String brpBijhoudingsaardCode) {
        String result;
        try {
            final Bijhoudingsaard bijhoudingsaard = Bijhoudingsaard.parseCode(brpBijhoudingsaardCode);
            result = String.format(STRING_FORMAT, bijhoudingsaard.getCode(), bijhoudingsaard.getNaam());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "Bijhoudingsaard", brpBijhoudingsaardCode);
            LOG.warn(logMsg);
            result = brpBijhoudingsaardCode;
        }
        return result;
    }

    @Override
    public final String getNadereBijhoudingsaard(final String brpNadereBijhoudingsaard) {
        String result;
        try {
            final NadereBijhoudingsaard nadereBijhoudingsaard = NadereBijhoudingsaard.parseCode(brpNadereBijhoudingsaard);
            result = String.format(STRING_FORMAT, nadereBijhoudingsaard.getCode(), nadereBijhoudingsaard.getNaam());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "Nadere Bijhoudingsaard", brpNadereBijhoudingsaard);
            LOG.warn(logMsg);
            result = brpNadereBijhoudingsaard;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getPartij(final int brpPartijCode) {
        final int partijCodeSize = 4;
        String result;
        try {
            final Partij partij = getDynamischeStamtabelRepository().getPartijByCode(brpPartijCode);
            final String partijCode = VerwerkerUtil.zeroPad(Integer.toString(partij.getCode()), partijCodeSize);
            result = String.format(STRING_FORMAT, partijCode, partij.getNaam());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "PartijCode", brpPartijCode);
            LOG.warn(logMsg);
            result = String.valueOf(brpPartijCode);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getPredicaat(final String brpPredicaatCode) {
        String result;
        try {
            final Predicaat predikaat = Predicaat.parseCode(brpPredicaatCode);
            result = String.format(STRING_FORMAT_GESL, predikaat.getCode(), predikaat.getNaamMannelijk(), predikaat.getNaamVrouwelijk());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "Predicaat", brpPredicaatCode);
            LOG.warn(logMsg);
            result = brpPredicaatCode;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getRedenOpschorting(final String brpRedenOpschortingBijhoudingCode) {
        String result;
        try {
            final NadereBijhoudingsaard redenOpschorting = NadereBijhoudingsaard.parseCode(brpRedenOpschortingBijhoudingCode);
            result = String.format(STRING_FORMAT, redenOpschorting.getCode(), redenOpschorting.getNaam());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "RedenOpschorting", brpRedenOpschortingBijhoudingCode);
            LOG.warn(logMsg);
            result = brpRedenOpschortingBijhoudingCode;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getRedenOntbrekenReisdocument(final String brpReisdocumentRedenOntbreken) {
        String result;
        try {
            final AanduidingInhoudingOfVermissingReisdocument aanduidingInhoudingOfVermissingReisdocument =
                    getDynamischeStamtabelRepository().getAanduidingInhoudingOfVermissingReisdocumentByCode(brpReisdocumentRedenOntbreken.charAt(0));
            result =
                    String.format(
                        STRING_FORMAT,
                        aanduidingInhoudingOfVermissingReisdocument.getCode(),
                        aanduidingInhoudingOfVermissingReisdocument.getNaam());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "ReisdocumentRedenOntbreken", brpReisdocumentRedenOntbreken);
            LOG.warn(logMsg);
            result = brpReisdocumentRedenOntbreken;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getRedenWijzigingVerblijf(final Character brpRedenWijzigingVerblijfCode) {
        String result;
        try {
            final RedenWijzigingVerblijf redenWijzigingVerblijf =
                    getDynamischeStamtabelRepository().getRedenWijzigingVerblijf(brpRedenWijzigingVerblijfCode);
            result = String.format(STRING_FORMAT, redenWijzigingVerblijf.getCode(), redenWijzigingVerblijf.getNaam());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "RedenWijzigingVerblijf", brpRedenWijzigingVerblijfCode);
            LOG.warn(logMsg);
            result = String.valueOf(brpRedenWijzigingVerblijfCode);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getGemeenteByPartij(final int gemeentePartijCode) {
        final int gemeenteCodeSize = 4;
        String result;
        try {
            final Gemeente gemeente =
                    getDynamischeStamtabelRepository().getGemeenteByPartij(getDynamischeStamtabelRepository().getPartijByCode(gemeentePartijCode));
            final String gemeenteCodePadded = VerwerkerUtil.zeroPad(Integer.toString(gemeente.getCode()), gemeenteCodeSize);
            result = String.format(STRING_FORMAT, gemeenteCodePadded, gemeente.getNaam());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "GemeentePartijCode", gemeentePartijCode);
            LOG.warn(logMsg);
            result = VerwerkerUtil.zeroPad(String.valueOf(gemeentePartijCode), gemeenteCodeSize);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getGemeenteCodeByPartij(final int gemeentePartijCode) {
        final int gemeenteCodeSize = 4;
        String result;
        try {
            final Gemeente gemeente =
                    getDynamischeStamtabelRepository().getGemeenteByPartij(getDynamischeStamtabelRepository().getPartijByCode(gemeentePartijCode));
            result = VerwerkerUtil.zeroPad(Integer.toString(gemeente.getCode()), gemeenteCodeSize);
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "Gemeente-PartijCode", gemeentePartijCode);
            LOG.warn(logMsg);
            result = null;
        }
        return result;
    }

    @Override
    public final String getSoortMigratieCode(final String brpSoortMigratieCode) {
        String result;
        try {
            final SoortMigratie soortMigratie = SoortMigratie.parseCode(brpSoortMigratieCode);
            result = String.format(STRING_FORMAT, soortMigratie.getCode(), soortMigratie.getNaam());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "Soort migratie", brpSoortMigratieCode);
            LOG.warn(logMsg);
            result = brpSoortMigratieCode;
        }
        return result;
    }
}
