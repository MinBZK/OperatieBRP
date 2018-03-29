/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.fileaccess;

import com.google.common.collect.Lists;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;
import nl.bzk.brp.domain.internbericht.selectie.SelectieFragmentSchrijfBericht;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;
import nl.bzk.brp.service.selectie.algemeen.ConfiguratieService;
import nl.bzk.brp.service.selectie.schrijver.SelectieFileService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

/**
 * SelectieFileServiceImpl.
 */
@Bedrijfsregel(Regel.R2557)
@Service
public final class SelectieFileServiceImpl implements SelectieFileService {

    /**
     * fragment postfix.
     */
    static final String FRAGMENT_POSTFIX = ".fragment";
    /**
     * part file postfix.
     */
    static final String PART_POSTFIX = ".part";
    /**
     * part folder.
     */
    static final String PART_FOLDER = "part";
    /**
     * fragment folder.
     */
    static final String FRAGMENT_FOLDER = "fragment";

    private static final byte[] NEW_LINE = System.getProperty("line.separator").getBytes(StandardCharsets.UTF_8);

    @Inject
    private ConfiguratieService configuratieService;

    private SelectieFileServiceImpl() {
    }

    @Override
    public void schoonResultaatDirectory(final MaakSelectieResultaatTaak maakSelectieResultaatTaak) {
        final Path resultaatPath = getSelectietaakResultaatPath(maakSelectieResultaatTaak.getSelectietaakId(),
                maakSelectieResultaatTaak.getDatumUitvoer());
        try {
            if (maakSelectieResultaatTaak.isOngeldig() && resultaatPath.toFile().exists()) {
                Files.walk(resultaatPath)
                        .map(Path::toFile)
                        .forEach(File::delete);
            } else {
                final Path path = Paths.get(resultaatPath.toString(), FRAGMENT_FOLDER);
                verwijderBestandenInFolder(path, FRAGMENT_POSTFIX);
                Files.deleteIfExists(Paths.get(resultaatPath.toString(), PART_FOLDER));
                Files.deleteIfExists(path);
            }
        } catch (IOException e) {
            throw new BrpServiceRuntimeException(e);
        }
    }

    @Override
    public List<Path> geefFragmentFiles(final MaakSelectieResultaatTaak maakSelectieResultaatTaak) throws IOException {
        final Path path = Paths.get(getSelectietaakResultaatPath(maakSelectieResultaatTaak.getSelectietaakId(),
                maakSelectieResultaatTaak.getDatumUitvoer()).toString(), FRAGMENT_FOLDER);
        if (!path.toFile().exists()) {
            return Lists.newArrayList();
        }
        return Files.list(path)
                .filter(p -> p.getFileName()
                        .toString().endsWith(FRAGMENT_POSTFIX))
                .collect(Collectors.toList());
    }


    @Override
    public Stream<String> leesFragmentRegels(final Path path) throws IOException {
        return Files.lines(path);
    }

    @Override
    public Path initSchrijfOpslag(final SelectieFragmentSchrijfBericht berichtSchrijfTaak) {
        final Path path = getSelectietaakResultaatPath(berichtSchrijfTaak.getSelectietaakId(), berichtSchrijfTaak.getSelectietaakDatumUitvoer());
        try {
            if (!path.toFile().exists()) {
                final Path berichtResultaatFolder = getBerichtResultaatFolder();
                if (!berichtResultaatFolder.toFile().exists()) {
                    Files.createDirectory(berichtResultaatFolder);
                }
                Files.createDirectory(path);
                Files.createDirectory(Paths.get(path.toString(), FRAGMENT_FOLDER));
                Files.createDirectory(Paths.get(path.toString(), PART_FOLDER));
                Files.createDirectory(Paths.get(path.toString(), geefProtocolleringBestand().getParent().toString()));
            }
        } catch (IOException e) {
            throw new BrpServiceRuntimeException(e);
        }
        return path;
    }

    @Override
    public Path getBerichtResultaatFolder() {
        return Paths.get(configuratieService.getBerichtResultaatFolder());
    }

    @Override
    public void schrijfDeelFragment(List<byte[]> berichtenEncoded, SelectieFragmentSchrijfBericht bericht) throws IOException {
        final Path partFilePath = Paths.get(getSelectietaakResultaatPath(bericht.getSelectietaakId(),
                bericht.getSelectietaakDatumUitvoer()).toString(),
                PART_FOLDER, UUID.randomUUID() + PART_POSTFIX);
        try (OutputStream out = Files.newOutputStream(partFilePath, StandardOpenOption.CREATE)) {
            for (byte[] bytes : berichtenEncoded) {
                out.write(bytes);
                out.write(NEW_LINE);
            }
        }
    }

    @Override
    public void concatLaatsteDeelFragmenten(final MaakSelectieResultaatTaak maakSelectieResultaatTaak) throws IOException {
        final Path baseFolder = getSelectietaakResultaatPath(maakSelectieResultaatTaak.getSelectietaakId(), maakSelectieResultaatTaak.getDatumUitvoer());
        final Path partFolderPath = Paths.get(baseFolder.toString(), PART_FOLDER);
        if (partFolderPath.toFile().exists()) {
            final List<Path> paths = Files.list(partFolderPath)
                    .filter(p -> p.getFileName().toString().endsWith(PART_POSTFIX))
                    .collect(Collectors.toList());
            concat(baseFolder, paths);
        }
    }

    @Override
    public void concatDeelFragmenten(SelectieFragmentSchrijfBericht selectieFragmentSchrijfBericht) throws IOException {
        final Path baseFolder = getSelectietaakResultaatPath(selectieFragmentSchrijfBericht.getSelectietaakId(),
                selectieFragmentSchrijfBericht.getSelectietaakDatumUitvoer());
        final Path partFolderPath = Paths.get(baseFolder.toString(), PART_FOLDER);
        if (!partFolderPath.toFile().exists()) {
            return;
        }

        final List<Path> paths = Files.list(partFolderPath)
                .filter(p -> p.getFileName().toString().endsWith(PART_POSTFIX))
                .collect(Collectors.toList());
        if (paths.size() >= configuratieService.getConcatPartsCount()) {
            concat(baseFolder, paths);
        }
    }

    @Override
    public void schijfProtocolleringPersonen(final SelectieFragmentSchrijfBericht bericht, List<String> regels) throws IOException {
        final Path protocolleringBestand = getSelectietaakResultaatPath(bericht.getSelectietaakId(), bericht.getSelectietaakDatumUitvoer())
                .resolve(geefProtocolleringBestand());
        try (BufferedWriter bw = Files.newBufferedWriter(protocolleringBestand, StandardCharsets.UTF_8)) {
            for (String regel : regels) {
                bw.write(regel);
                bw.newLine();
            }
        }
    }

    @Override
    public void schrijfSteekproefBestand(final MaakSelectieResultaatTaak taak, final List<String> regelsInSteekproef) throws IOException {
        final Path baseFolder = getSelectietaakResultaatPath(taak.getSelectietaakId(), taak.getDatumUitvoer());
        final String steekproefBestand = String.format("/steekproef_%s_%s_%s.txt",
                taak.getDatumUitvoer(), taak.getDienstId(), taak.getSelectietaakId());
        final Path outFile = Paths.get(baseFolder.toString(), steekproefBestand);
        final List<String> decodedList = regelsInSteekproef.stream()
                .map(s -> Base64.getDecoder().decode(s))
                .map(s -> new String(s, StandardCharsets.UTF_8)).
                        collect(Collectors.toList());
        FileUtils.writeLines(outFile.toFile(), StandardCharsets.UTF_8.name(), decodedList);
    }


    private void verwijderBestandenInFolder(final Path path, final String postfix) throws IOException {
        if (path.toFile().exists()) {
            final List<Path> paths = Files.list(path)
                    .filter(p -> p.getFileName()
                            .toString().endsWith(postfix))
                    .collect(Collectors.toList());
            for (Path resultPath : paths) {
                Files.delete(resultPath);
            }
        }
    }

    private void concat(final Path baseFolder, List<Path> paths) throws IOException {
        final Path outFile = Paths.get(baseFolder.toString(), FRAGMENT_FOLDER, UUID.randomUUID() + FRAGMENT_POSTFIX);
        try (FileChannel out = FileChannel.open(outFile, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
            for (Path part : paths) {
                transferPart(out, part);
            }
        }
        for (Path path : paths) {
            Files.delete(path);
        }
    }

    private void transferPart(FileChannel out, Path part) throws IOException {
        try (FileChannel in = FileChannel.open(part, StandardOpenOption.READ)) {
            long position = 0;
            long count = in.size();
            while (position < count) {
                position += in.transferTo(position, count - position, out);
            }
        }
    }
}
