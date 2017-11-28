/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.schrijver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.berichtmodel.SelectieResultaatBericht;
import nl.bzk.brp.service.selectie.schrijver.SelectieFileService;
import nl.bzk.brp.service.selectie.schrijver.SelectieResultaatSchrijfInfo;
import nl.bzk.brp.service.selectie.schrijver.SelectieResultaatVerwerkException;
import nl.bzk.brp.service.selectie.schrijver.SelectieResultaatWriterFactory;
import org.springframework.stereotype.Component;

/**
 * SelectieResultaatWriterFactoryImpl.
 */
@Component
public final class SelectieResultaatWriterFactoryImpl implements SelectieResultaatWriterFactory {

    @Inject
    private SelectieFileService selectieFileService;

    private SelectieResultaatWriterFactoryImpl() {
    }

    @Override
    public PersoonBestandWriter persoonWriterBrp(final SelectieResultaatSchrijfInfo info, final SelectieResultaatBericht bericht)
            throws SelectieResultaatVerwerkException {
        final Path filePath = getPath(info, bericht);
        return new SelectieResultaatBerichtWriter.BrpPersoonWriter(filePath, bericht);
    }

    @Override
    public PersoonBestandWriter persoonWriterGba(final SelectieResultaatSchrijfInfo info, final SelectieResultaatBericht bericht)
            throws SelectieResultaatVerwerkException {
        final Path filePath = getPath(info, bericht);
        return new SelectieResultaatBerichtWriter.GbaPersoonWriter(filePath, bericht);
    }

    @Override
    public TotalenBestandWriter totalenWriterBrp(final SelectieResultaatSchrijfInfo info, final SelectieResultaatBericht bericht)
            throws SelectieResultaatVerwerkException {
        final Path filePath = getPath(info, bericht);
        try {
            //in het geval er geen personen zijn bestaat de directory niet
            Files.createDirectories(filePath.getParent());
        } catch (IOException e) {
            throw new SelectieResultaatVerwerkException(e);
        }
        return new SelectieResultaatBerichtWriter.BrpTotalenWriter(filePath, bericht);
    }

    @Override
    public TotalenBestandWriter totalenWriterGba(final SelectieResultaatSchrijfInfo info, final SelectieResultaatBericht bericht)
            throws SelectieResultaatVerwerkException {
        final Path filePath = getPath(info, bericht);
        try {
            //in het geval er geen personen zijn bestaat de directory niet
            Files.createDirectories(filePath.getParent());
        } catch (IOException e) {
            throw new SelectieResultaatVerwerkException(e);
        }
        return new SelectieResultaatBerichtWriter.GbaTotalenWriter(filePath, bericht);
    }

    @Bedrijfsregel(Regel.R2557)
    private Path getPath(final SelectieResultaatSchrijfInfo info, final SelectieResultaatBericht bericht) {
        final Path folderPath =
                selectieFileService.getSelectietaakResultaatPath(bericht.getSelectieKenmerken().
                        getSelectietaakId(), bericht.getSelectieKenmerken().getDatumUitvoer());
        final String bestandsnaam = String.format("%d_%s.xml", info.getBerichtId(), bericht.getSelectieKenmerken().getSoortSelectieresultaatSet());
        return Paths.get(folderPath.toString(), bestandsnaam);
    }
}
