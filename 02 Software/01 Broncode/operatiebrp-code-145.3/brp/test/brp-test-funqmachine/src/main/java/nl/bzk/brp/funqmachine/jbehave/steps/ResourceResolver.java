/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.steps;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import nl.bzk.brp.funqmachine.jbehave.context.BevraagbaarContextView;

/**
 * een class die de opgevraagde resource (bestand) kan vinden.
 */
public class ResourceResolver {

    private final BevraagbaarContextView context;

    /**
     * Constructor.
     * @param contextView de context view
     */
    public ResourceResolver(final BevraagbaarContextView contextView) {
        this.context = contextView;
    }

    /**
     * De methode om een bestand van het systeem te lezen.
     * @param fileNaam de naam van het bestand
     * @return het {@link Path} naar het gevraagde bestand
     * @throws ResourceException als er iets tijdens het resolven fout is gegaan.
     */
    Path resolve(String fileNaam) throws ResourceException {
        return resolve(fileNaam, false);
    }

    /**
     * De methode om een bestand (evt een expected) van het systeem te lezen.
     * @param fileNaam de naam van het bestand
     * @param isExpected true als het een expected is
     * @return het {@link Path} naar het gevraagde bestand
     * @throws ResourceException als er iets tijdens het resolven fout is gegaan.
     */
    Path resolve(String fileNaam, boolean isExpected) throws ResourceException {
        String baseDir = "";

        final String storyLocation = context.getStory();
        if (storyLocation.contains("/")) {
            baseDir = "/" + storyLocation.substring(0, storyLocation.lastIndexOf('/'));
        }

        if (isExpected) {
            baseDir = baseDir + "/expected";
        }

        try {
            final Path basePath = Paths.get(getClass().getResource(baseDir).toURI());
            final ResourceVisitor fileVisitor = new ResourceVisitor(fileNaam);
            final EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
            Files.walkFileTree(basePath, options, 1, fileVisitor);
            final Path foundFile = fileVisitor.foundFile;
            if (foundFile == null) {
                throw new ResourceException(String.format("Bestand %s niet gevonden", fileNaam));
            }
            return fileVisitor.foundFile;
        } catch (URISyntaxException | IOException e) {
            throw new ResourceException(e);
        }
    }

    private static final class ResourceVisitor extends SimpleFileVisitor<Path> {

        private final PathMatcher matcher;
        private Path foundFile;

        private ResourceVisitor(final String matchingFileName) {
            matcher = FileSystems.getDefault().getPathMatcher("glob:" + matchingFileName);

        }

        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
            final Path name = file.getFileName();
            if (name != null && matcher.matches(name)) {
                foundFile = file;
                return FileVisitResult.TERMINATE;
            }
            return FileVisitResult.CONTINUE;
        }
    }
}
