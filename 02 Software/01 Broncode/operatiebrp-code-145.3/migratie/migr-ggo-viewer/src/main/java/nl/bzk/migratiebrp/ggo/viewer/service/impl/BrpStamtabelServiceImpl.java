/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service.impl;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.ggo.viewer.service.BrpStamtabelService;
import org.springframework.stereotype.Component;

/**
 * Implementatie van BrpStamtabelService.
 */
@Component
public class BrpStamtabelServiceImpl extends StamtabelServiceImpl implements BrpStamtabelService {
    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Constructor.
     * @param dynamischeStamtabelRepository de repository voor de dynamische stamtabel
     */
    public BrpStamtabelServiceImpl(final DynamischeStamtabelRepository dynamischeStamtabelRepository) {
        super(dynamischeStamtabelRepository);
    }

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
            LOG.warn(logMsg, iae);
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
            LOG.warn(logMsg, iae);
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
            LOG.warn(logMsg, iae);
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
            LOG.warn(logMsg, iae);
            result = brpNadereBijhoudingsaard;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getPartij(final String brpPartijCode) {
        String result;
        try {
            final Partij partij = getDynamischeStamtabelRepository().getPartijByCode(brpPartijCode);
            final String partijCode = partij.getCode();
            result = String.format(STRING_FORMAT, partijCode, partij.getNaam());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "PartijCode", brpPartijCode);
            LOG.warn(logMsg, iae);
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
            LOG.warn(logMsg, iae);
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
            LOG.warn(logMsg, iae);
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
            LOG.warn(logMsg, iae);
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
            LOG.warn(logMsg, iae);
            result = String.valueOf(brpRedenWijzigingVerblijfCode);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getGemeenteByPartij(final String gemeentePartijCode) {
        String result;
        try {
            final Gemeente gemeente =
                    getDynamischeStamtabelRepository().getGemeenteByPartij(getDynamischeStamtabelRepository().getPartijByCode(gemeentePartijCode));
            final String gemeenteCodePadded = gemeente.getCode();
            result = String.format(STRING_FORMAT, gemeenteCodePadded, gemeente.getNaam());
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "GemeentePartijCode", gemeentePartijCode);
            LOG.warn(logMsg, iae);
            result = gemeentePartijCode;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getGemeenteCodeByPartij(final String gemeentePartijCode) {
        String result;
        try {
            final Gemeente gemeente =
                    getDynamischeStamtabelRepository().getGemeenteByPartij(getDynamischeStamtabelRepository().getPartijByCode(gemeentePartijCode));
            result = gemeente.getCode();
        } catch (final IllegalArgumentException iae) {
            final String logMsg = String.format(LOG_MSG_FORMAT, "Gemeente-PartijCode", gemeentePartijCode);
            LOG.warn(logMsg, iae);
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
            LOG.warn(logMsg, iae);
            result = brpSoortMigratieCode;
        }
        return result;
    }
}
