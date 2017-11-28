/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.build.timestamp.mojo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.InstantiationStrategy;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * timestamp mojo om de tijden van bestanden te fixeren op 1 tijd.
 * Te gebruiken om verschillende builds van ongewijzigde code een
 * jar/war file te laten leveren met een zelfde hash, zodat bijvoorbeeld
 * docker geen wijziging detecteert als de jar ongewijzigd is maar op
 * een ander tijdstip is gebouwd.
 */
@Mojo(name = "timestamp", instantiationStrategy = InstantiationStrategy.PER_LOOKUP,
        threadSafe = true)
public class TimestampMojo extends AbstractMojo {

    private static final String META_INF_PATH = "META-INF/";
    private static final int BUFFER_SIZE = 2048;
    private static final int COMPRESSED = 8;

    /**
     * tijd waarop de directories en bestanden moeten worden gezet voordat ze gepackaged worden.
     * Formaat yyyyMMddHHmm
     * Standaard waarde is 201701011200
     */
    @Parameter(property = "time", defaultValue = "201701011200")
    private String time;

    /**
     * artifact waarvan de inhoud moet worden gereset
     */
    @Parameter(property = "artifact", required = true)
    private String artifact;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (artifact.endsWith("jar") || artifact.endsWith("war")) {
            getLog().debug("Setting fixed timestamp for " + artifact);
            try {
                final Path tmpFolderPath = createTmpFolderPath();
                if (tmpFolderPath.toFile().exists()) {
                    deleteTmpFolder(tmpFolderPath);
                }
                final Path tmpFolder = Files.createDirectory(tmpFolderPath);
                backupArtifact();
                Set<String> keys = pakArchiefBestandUit(tmpFolder);
                pakArchiefBestandOpnieuwIn(tmpFolder, keys);
                Files.deleteIfExists(FileSystems.getDefault().getPath(artifact + ".bak"));
                deleteTmpFolder(tmpFolder);
            } catch (IOException e) {
                throw new MojoExecutionException("Cannot create temp folder", e);
            }
        } else {
            getLog().debug("Artifact is not a jar or war file");
        }
    }

    private Path createTmpFolderPath() {
        return FileSystems.getDefault().getPath(artifact.substring(0, artifact.lastIndexOf(File.separator)) + "/tmp");
    }

    private void deleteTmpFolder(final Path tmpFolder) throws IOException {
        Files.walkFileTree(tmpFolder, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void backupArtifact() throws MojoExecutionException {
        final Path orgineelPath = FileSystems.getDefault().getPath(artifact);
        final Path backupPath = FileSystems.getDefault().getPath(artifact + ".bak");
        try {
            Files.move(orgineelPath, backupPath);
        } catch (IOException e) {
            throw new MojoExecutionException("Cannot move artifact", e);
        }
    }

    private Set<String> pakArchiefBestandUit(final Path tmpFolder) {
        getLog().debug("Unpacking artifact");
        Set<String> keys = new TreeSet<>();
        try (final JarInputStream archiefStream = new JarInputStream(new FileInputStream(artifact + ".bak"))) {

            final List<String> bestanden = new ArrayList<>();
            JarEntry archiefItem = archiefStream.getNextJarEntry();
            while (archiefItem != null) {
                final File archiefBestand = new File(tmpFolder.toFile(), archiefItem.getName());
                if (archiefItem.isDirectory()) {
                    archiefBestand.mkdirs();
                } else {
                    pakBestandUit(archiefStream, archiefBestand);
                }
                bestanden.add(archiefItem.getName());
                archiefStream.closeEntry();
                archiefItem = archiefStream.getNextJarEntry();
            }

            pakManifestUit(tmpFolder, archiefStream, bestanden);
            keys.addAll(bestanden);
        } catch (IOException | WrappedException e) {
            getLog().debug("Artifact cannot be fixed", e);
        }
        return keys;
    }

    private void pakBestandUit(final JarInputStream archiefStream, final File archiefBestand) throws IOException {
        final File parentBestand =
                new File(archiefBestand.getAbsolutePath().substring(0, archiefBestand.getAbsolutePath().lastIndexOf(File.separator)));
        parentBestand.mkdirs();
        if (archiefBestand.createNewFile()) {
            try (final FileOutputStream fos = new FileOutputStream(archiefBestand);
                 final BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                int len;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((len = archiefStream.read(buffer)) > 0) {
                    bos.write(buffer, 0, len);
                }
                bos.flush();
            }
        }
    }

    private void pakManifestUit(final Path tmpFolder, final JarInputStream archiefStream, final List<String> bestanden) throws IOException {
        final File manifestBestand = new File(tmpFolder.toFile(), JarFile.MANIFEST_NAME);
        final File manifestFolder = new File(manifestBestand.getAbsolutePath().substring(0, manifestBestand.getAbsolutePath().lastIndexOf(File.separator)));
        manifestFolder.mkdirs();
        if (manifestBestand.createNewFile()) {
            try (final FileOutputStream fos = new FileOutputStream(manifestBestand);
                 final BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                archiefStream.getManifest().write(bos);
            }
            bestanden.add(META_INF_PATH);
            bestanden.add(JarFile.MANIFEST_NAME);
        }
    }

    private void pakArchiefBestandOpnieuwIn(final Path tmpFolder, final Set<String> keys) throws MojoExecutionException {
        final FileTime bestandstijd = maakFileTime(time);
        try (final JarOutputStream nieuwArchiefStream = new JarOutputStream(new FileOutputStream(artifact))) {
            verwerkManifest(tmpFolder, bestandstijd, nieuwArchiefStream);
            keys.forEach(key -> {
                if (!META_INF_PATH.equals(key) && !JarFile.MANIFEST_NAME.equals(key)) {
                    verwerkNieuweJarEntry(tmpFolder, bestandstijd, nieuwArchiefStream, key);
                }
            });
        } catch (IOException | WrappedException e) {
            getLog().debug("Artifact cannot be fixed", e);
        }
    }

    private void verwerkManifest(final Path tmpFolder, final FileTime bestandstijd, final JarOutputStream nieuwArchiefStream) throws MojoExecutionException {
        final File archiefBestand = new File(tmpFolder.toFile(), JarFile.MANIFEST_NAME);
        getLog().debug("Processing manifest");
        if (archiefBestand.exists()) {
            try (final FileInputStream fis = new FileInputStream(archiefBestand)) {
                Manifest manifest = new Manifest();
                if (archiefBestand.exists()) {
                    manifest.read(fis);
                }
                ZipEntry manifestFolder = new ZipEntry(META_INF_PATH);
                fixeerDatumTijd(manifestFolder, bestandstijd);
                nieuwArchiefStream.putNextEntry(manifestFolder);
                nieuwArchiefStream.closeEntry();
                ZipEntry manifestBestand = new ZipEntry(JarFile.MANIFEST_NAME);
                fixeerDatumTijd(manifestBestand, bestandstijd);
                nieuwArchiefStream.putNextEntry(manifestBestand);
                manifest.write(nieuwArchiefStream);
                nieuwArchiefStream.closeEntry();
                getLog().debug("manifest processed");
            } catch (IOException e) {
                throw new MojoExecutionException("Cannot write manifest file", e);
            }
        }
    }

    private void verwerkNieuweJarEntry(final Path tmpFolder, final FileTime bestandstijd, final JarOutputStream nieuwArchiefStream, final String key) {
        try {
            getLog().debug("Creating jar entry for: " + key);
            final File archiefBestand = new File(tmpFolder.toFile(), key);
            JarEntry newEntry;
            newEntry = new JarEntry(key);
            fixeerDatumTijd(newEntry, bestandstijd);
            newEntry.setCompressedSize(COMPRESSED);
            nieuwArchiefStream.putNextEntry(newEntry);
            if (archiefBestand.isFile()) {
                pakBestandIn(nieuwArchiefStream, archiefBestand);
            }
            nieuwArchiefStream.closeEntry();
        } catch (IOException e) {
            throw new WrappedException(e.getMessage(), e);
        }
    }

    private void pakBestandIn(final JarOutputStream nieuwArchiefStream, final File archiefBestand) throws IOException {
        try (final FileInputStream fis = new FileInputStream(archiefBestand);
             final BufferedInputStream bis = new BufferedInputStream(fis)) {
            int len;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((len = bis.read(buffer)) > 0) {
                nieuwArchiefStream.write(buffer, 0, len);
            }
        }
    }

    private FileTime maakFileTime(final String time) throws MojoExecutionException {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        try {
            return FileTime.from(LocalDateTime.from(formatter.parse(time)).toInstant(ZoneOffset.UTC));
        } catch (DateTimeParseException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    private void fixeerDatumTijd(final ZipEntry entry, final FileTime time) {
        entry.setCreationTime(time);
        entry.setLastAccessTime(time);
        entry.setLastModifiedTime(time);
    }

    /**
     * Zet de tijd.
     * @param time de tijd
     */
    public void setTime(final String time) {
        this.time = time;
    }

    /**
     * Zet de directories die gereset moeten worden
     */
    public void setArtifact(final String artifact) {
        this.artifact = artifact;
    }

    private static class WrappedException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        WrappedException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }


}
