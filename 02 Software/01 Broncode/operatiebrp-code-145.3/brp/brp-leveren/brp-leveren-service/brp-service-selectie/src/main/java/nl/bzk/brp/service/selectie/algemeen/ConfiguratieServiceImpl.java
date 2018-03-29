/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.algemeen;

import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 * ConfiguratieServiceImpl.
 */
@Service
public final class ConfiguratieServiceImpl implements ConfiguratieService {

    @Inject
    private Configuratie configuratie;

    private ConfiguratieServiceImpl() {
    }

    @Override
    public int getPoolSizeBlobBatchProducer() {
        return configuratie.getPoolSizeBlobBatchProducer();
    }

    @Override
    public int getBatchSizeBatchProducer() {
        return configuratie.getBatchSizeBatchProducer();
    }

    @Override
    public int getBlobsPerSelectieTaak() {
        return configuratie.getBlobsPerSelectieTaak();
    }

    @Override
    public int getMaxSelectieTaak() {
        return configuratie.getMaxSelectieTaak();
    }

    @Override
    public int getVerwerkerPoolSize() {
        return configuratie.getVerwerkerPoolSize();
    }

    @Override
    public String getBerichtResultaatFolder() {
        return configuratie.getBerichtResultaatFolder();
    }

    @Override
    public String getSelectiebestandFolder() {
        return configuratie.getSelectiebestandFolder();
    }

    @Override
    public int getConcatPartsCount() {
        return configuratie.getConcatPartsCount();
    }

    @Override
    public int getMaxSelectieSchrijfTaak() {
        return configuratie.getMaxSelectieSchrijfTaak();
    }

    @Override
    public int getAutorisatiesPerSelectieTaak() {
        return configuratie.getAutorisatiesPerSelectieTaak();
    }

    @Override
    public int getSchrijverPoolSize() {
        return configuratie.getSchrijverPoolSize();
    }

    @Override
    public long getMaximaleWachttijdFragmentVerwerkerMin() {
        return configuratie.getMaximaleWachtTijdFragmentVerwerkerMin();
    }

    @Override
    public long getMaximaleWachttijdMaakPersoonsBeeldMin() {
        return configuratie.getWachttijdMaakPersoonsBeeldMin();
    }

    @Override
    public long getMaximaleWachttijdPersoonslijstFragmentMin() {
        return configuratie.getMaximaleWachttijdPersoonslijstFragmentMin();
    }

    @Override
    public long getMaximaleLooptijdSelectierun() {
        return configuratie.getMaximaleLooptijdSelectierun();
    }

    @Override
    public long getMaximaleWachttijdWachttaak() {
        return configuratie.getMaximaleWachttijdWachttaak();
    }
}
