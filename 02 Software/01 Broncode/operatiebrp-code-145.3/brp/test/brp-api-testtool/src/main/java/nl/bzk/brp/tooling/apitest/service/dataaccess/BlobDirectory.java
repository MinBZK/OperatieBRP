/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.dataaccess;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.test.common.TestclientExceptie;
import org.apache.commons.lang3.StringUtils;

/**
 * Representeert een directory met daarin blob-files.
 */
public final class BlobDirectory {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String DASH = "-";
    private static final Set<String> BIJHOUDING_JAR_NAME_LIST = Sets.newHashSet("VZIG", "VHNL", "AGBL", "EGBL",  "EGNL", "NHNL", "OHBL",
            "OMGP", "WBVP", "AGNL", "NGNL" , "OHNL", "VHBL" );

    private List<Blobfile> blobfileList = Lists.newLinkedList();

    BlobDirectory(final String blobdirPath) throws IOException {
        if (!blobdirPath.contains(":")) {
            //pak van classpath
            blobfileList.add(new Blobfile(Paths.get(blobdirPath)));
            return;
        }

        final Path jarPath = resolveJarPath(blobdirPath);
        final String pathInJar = StringUtils.substringAfter(blobdirPath, ":");
        try (final FileSystem fileSystem = FileSystems.newFileSystem(jarPath, null)) {
            final Path path = fileSystem.getPath("/" + pathInJar);
            Files.list(path).filter(path1 -> path1.toString().endsWith(".json")).forEach(path1 -> {
                try {
                    blobfileList.add(new Blobfile(path1));
                } catch (IOException e) {
                    throw new TestclientExceptie(e);
                }
            });
        }
    }

    void add(final Blobfile blobfile) {
        blobfileList.add(blobfile);
    }

    List<Blobfile> getBlobfileList() {
        return blobfileList;
    }


    Map<Long, PersoonBlobData> listPersoonBlobs() {
        final Map<Long, PersoonBlobData> persoonBlobMap = Maps.newHashMap();
        for (Blobfile blobfile : blobfileList) {
            if (blobfile.getPersoonBlob() != null) {
                persoonBlobMap.put(blobfile.getPersId(), new PersoonBlobData(blobfile.getPersoonBlob(), blobfile.getData()));
            }
        }
        return persoonBlobMap;
    }

    Map<Long, AfnemerindicatiesBlobData> listAfnemerindicatieBlobs() {
        final Map<Long, AfnemerindicatiesBlobData> afnemerindicatiesBlobMap = Maps.newHashMap();
        for (final Blobfile blobFile : blobfileList) {
            if (blobFile.getAfnemerindicatiesBlobData() == null) {
                continue;
            }
            final Long persId = Long.parseLong(blobFile.getFilename().substring(0, blobFile.getFilename().indexOf(DASH)));
            AfnemerindicatiesBlobData afnemerindicatiesBlobData = null;
            if (blobFile.getData() != null && blobFile.getData().length > 0) {
                afnemerindicatiesBlobData = new AfnemerindicatiesBlobData(blobFile.getAfnemerindicatiesBlobData(), blobFile.getData());
            }
            afnemerindicatiesBlobMap.put(persId, afnemerindicatiesBlobData);
        }
        return afnemerindicatiesBlobMap;
    }

    static List<BlobDirectory> map(List<String> blobDirStringList) throws IOException {
        final List<BlobDirectory> blobdirs = Lists.newArrayListWithExpectedSize(blobDirStringList.size());
        for (String blobDirString : blobDirStringList) {
            final BlobDirectory blobdir = new BlobDirectory(blobDirString);
            blobdirs.add(blobdir);

        }
        return blobdirs;
    }

    public static Path resolveJarPath(final String path) {
        final String jarName = resolveJarName(path);
        final URL jarURL = resolveJarURL(path, jarName);
        LOGGER.debug("BlobJar = {}", jarURL);
        try {
            return Paths.get(jarURL.toURI());
        } catch (URISyntaxException e) {
            throw new TestclientExceptie(e);
        }
    }

    private static URL resolveJarURL(final String path, final String patternMatchJar) {
        final List<URL> urls = Lists.newLinkedList();
        final String surefireClasspath = System.getProperty("surefire.test.class.path");
        if (surefireClasspath != null) {
            try (Scanner scanner = new Scanner(surefireClasspath)) {
                scanner.useDelimiter(File.pathSeparator);
                while (scanner.hasNext()) {
                    try {
                        urls.add(new File(scanner.next()).toURI().toURL());
                    } catch (MalformedURLException e) {
                        throw new TestclientExceptie(e);
                    }
                }
            }
        } else {
            final ClassLoader cl = ClassLoader.getSystemClassLoader();
            Collections.addAll(urls, ((URLClassLoader) cl).getURLs());
        }

        return Lists.newArrayList(urls).stream()
                .filter(url -> url.getFile().contains(patternMatchJar))
                .findFirst()
                .orElseThrow(() -> new TestclientExceptie("Blobdirectory niet gevonden:" + path));
    }

    private static String resolveJarName(final String pattern) {
        final String jarName;
        if (pattern.startsWith("benchmarks:")) {
            jarName = "benchmarks";
        } else if (pattern.startsWith("oranje:")) {
            jarName = "blob-oranje";
        } else if (pattern.startsWith("specials:")) {
            jarName = "blob-specials";
        } else if (pattern.startsWith("DOCKER:")) {
            jarName = "blob-bijhouding-DockerBlobs";
        } else {
            final String prefix = pattern.substring(0, pattern.indexOf(':'));
            if (BIJHOUDING_JAR_NAME_LIST.contains(prefix)) {
                jarName = "blob-bijhouding-" + prefix;
            } else {
                //deze jar bevat niet alles, vandaar de uitsplitsing...
                //WAIS,WGIS,VZBG,VZBLWZVB
                jarName = "blob-bijhouding-BIJHOUDING";
            }
        }
        if (jarName == null) {
            throw new TestclientExceptie("Jar kan niet gevonden worden: " + pattern);
        }
        return jarName;
    }
}
