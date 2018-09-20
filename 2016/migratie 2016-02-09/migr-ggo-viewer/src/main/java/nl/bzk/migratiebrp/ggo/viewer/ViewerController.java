/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer;

import com.fasterxml.jackson.core.io.JsonStringEncoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoPersoonslijstGroep;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * De view-Controller voor de GGO-Viewer.
 */
@Controller
public class ViewerController {
    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String QQFILE = "qqfile";

    private static final String USERNAME_VARIABLE = "username";

    @Inject
    private DemoMode demoMode;
    @Inject
    private JsonFormatter jsonFormatter;
    @Inject
    private FileUploadVerwerker fileUploadVerwerker;
    @Inject
    private ZoekAnummerVerwerker zoekAnummerVerwerker;

    /**
     * Return de indexpagina.
     *
     * @return De indexpagina.
     */
    @RequestMapping(value = "/", produces = "text/html; charset=UTF-8")
    public final ModelAndView getIndex() {
        final Map<String, Object> model = demoMode.getDemoModel();
        model.put(USERNAME_VARIABLE, SecurityUtils.getSubject().getPrincipal());
        if (demoMode.isDemoMode()) {
            return new ModelAndView("viewerDemo", model);
        } else {
            return new ModelAndView("viewer", model);
        }
    }

    /**
     * Zoekt in de Database naar pl en conversie gegevens op basis van Anummer.
     *
     * @param aNummer
     *            Long
     * @return String met de persoonslijst.
     */
    @RequestMapping(value = "/zoek", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public final ResponseEntity<String> zoekOpAnummer(@RequestParam(value = "aNummer") final long aNummer) {
        final FoutMelder foutMelder = new FoutMelder();
        final List<GgoPersoonslijstGroep> persoonslijstGroepen = zoekAnummerVerwerker.zoekOpAnummer(aNummer, foutMelder);
        final ViewerResponse response = new ViewerResponse(persoonslijstGroepen, foutMelder.getFoutRegels());

        return jsonFormatter.format(response);
    }

    /**
     * Behandelt inkomend request, zijnde een upload.
     *
     * @param filename
     *            De naam van het bestand.
     * @param file
     *            De file in een byte array.
     * @return String met de persoonslijst.
     */
    @RequestMapping(value = "/file/upload", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public final ResponseEntity<String> uploadRequest(@RequestParam(QQFILE) final String filename, @RequestBody final byte[] file) {
        return verwerkFileUpload(filename, file);
    }

    /**
     * Behandelt een upload request met alleen een file naam. Dit bestand moet op de server staan.
     *
     * @param filename
     *            De file naam.
     * @return Json string met de PL.
     * @throws IOException
     *             als de file niet gelezen kan worden
     */
    @RequestMapping(value = "/file/uploadFileName", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public final ResponseEntity<String> uploadRequestFileName(@RequestParam(QQFILE) final String filename) throws IOException {
        return verwerkFileUpload(filename, demoMode.getDemoUploadFile(FilenameUtils.separatorsToUnix(filename)));
    }

    /**
     * Behandelt inkomend request, zijnde een upload, vanaf een IE.
     *
     * @param file
     *            De file in een MultipartFile.
     * @return String met de persoonslijst.
     */
    @RequestMapping(value = "/file/uploadMSIE", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
    @ResponseBody
    public final ResponseEntity<String> uploadRequestMSIE(@RequestParam(QQFILE) final MultipartFile file) {
        try {
            return verwerkFileUpload(file.getOriginalFilename(), file.getBytes());
        } catch (final IOException e) {
            // kon file niet inlezen; produceer foutresultaat door verder te gaan met lege file
            return verwerkFileUpload(file.getOriginalFilename(), new byte[] {});
        }
    }

    /**
     * Behandelt een download request voor een van de demo files.
     *
     * @param filename
     *            De file naam.
     * @return De file.
     * @throws IOException
     *             als de file niet gelezen kan worden
     */
    @RequestMapping(value = "/file/downloadFileName", method = RequestMethod.GET)
    @ResponseBody
    public final ResponseEntity<byte[]> downloadRequestFileName(@RequestParam(QQFILE) final String filename) throws IOException {

        final HttpHeaders headers = new HttpHeaders();

        if (filename.endsWith(".xls")) {
            headers.set("Content-Type", "application/vnd.ms-excel");
        } else {
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        }

        headers.set("Content-Disposition", "attachment;filename=\"" + FilenameUtils.getName(filename) + "\"");
        final byte[] result = demoMode.getDemoUploadFile(FilenameUtils.separatorsToUnix(filename));

        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }

    /**
     * Laad de help pagina.
     *
     * @return Laad help pagina.
     */
    @RequestMapping(value = "/help", produces = "text/html; charset=UTF-8")
    public final ModelAndView help() {
        final Map<String, Object> model = demoMode.getDemoModel();
        model.put(USERNAME_VARIABLE, SecurityUtils.getSubject().getPrincipal());
        return new ModelAndView("help", model);
    }

    /**
     * Ensure that exceptions are returned as JSON 500 errors.
     *
     * @param e
     *            The thrown exception
     * @return The thrown exception as a JSON encoded 500 error string.
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<String> verwerkException(final Exception e) {
        LOG.error("Exception verstuurd als response:", e);
        return new ResponseEntity<>(
            "{ \"error\" : \"" + new String(new JsonStringEncoder().encodeAsUTF8(e.toString()), StandardCharsets.UTF_8) + "\"}",
            HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Verwerkt de upload van een file.
     *
     * @param filename
     *            bestandsnaam
     * @param file
     *            bestand inhoud
     * @return String met de persoonslijst.
     */
    private ResponseEntity<String> verwerkFileUpload(final String filename, final byte[] file) {
        final FoutMelder foutMelder = new FoutMelder();
        final List<GgoPersoonslijstGroep> persoonslijstGroepen = fileUploadVerwerker.verwerkFileUpload(filename, file, foutMelder);

        return jsonFormatter.format(new ViewerResponse(persoonslijstGroepen, foutMelder.getFoutRegels()));
    }

}
