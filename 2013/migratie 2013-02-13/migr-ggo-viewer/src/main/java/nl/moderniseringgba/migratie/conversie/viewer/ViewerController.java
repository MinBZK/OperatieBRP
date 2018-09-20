/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.viewer.log.FoutMelder;
import nl.moderniseringgba.migratie.conversie.viewer.log.FoutRegel;
import nl.moderniseringgba.migratie.conversie.viewer.service.BcmService;
import nl.moderniseringgba.migratie.conversie.viewer.service.DbService;
import nl.moderniseringgba.migratie.conversie.viewer.service.LeesService;
import nl.moderniseringgba.migratie.conversie.viewer.service.PermissionService;
import nl.moderniseringgba.migratie.conversie.viewer.service.ViewerService;
import nl.moderniseringgba.migratie.conversie.viewer.vergelijk.StapelVergelijking;
import nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.BerichtLog;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * (Leuke naam wel.) De Controller voor de Viewer.
 */
@Controller
public class ViewerController {
    private static final String ANR_MSG = "Zoeken op a-nummer";

    @Inject
    private LeesService leesService;
    @Inject
    private DbService dbService;
    @Inject
    private ViewerService viewerService;
    @Inject
    private BcmService bcmService;
    @Inject
    private PermissionService permissionService;
    @Inject
    private DemoMode demoMode;

    private final JsonFormatter jsonFormatter = new JsonFormatter();

    /**
     * Return de indexpagina.
     * 
     * @return De indexpagina.
     */
    @RequestMapping("/")
    public final ModelAndView getIndex() {
        return new ModelAndView("viewer", demoMode.getDemoModel());
    }

    /**
     * Zoekt in de Database naar pl en conversie gegevens op basis van Anummer.
     * 
     * @param aNummer
     *            Long
     * @return String met de persoonslijst.
     */
    @RequestMapping("/zoek")
    @ResponseBody
    public final ResponseEntity<String> zoekOpAnummer(@RequestParam(value = "aNummer") final long aNummer) {
        final FoutMelder foutMelder = new FoutMelder();

        ViewerResponse response = null;

        // zoek BrpPersoonslijst uit BRP
        BrpPersoonslijst brpPersoonslijst = dbService.zoekBrpPersoonsLijst(aNummer);

        if (brpPersoonslijst != null) {
            // Sorteren van de BrpPersoonslijst op basis van geldigheid. Anders komt het slecht overeen met de LO3
            // weergave
            brpPersoonslijst = BrpStapelSorter.sorteerPersoonslijst(brpPersoonslijst, foutMelder);

            // Check role and permission
            if (!permissionService.hasPermissionOnPl(brpPersoonslijst)) {
                brpPersoonslijst = null;
            }
        }

        final List<PersoonslijstGroep> persoonslijstGroepen = new ArrayList<PersoonslijstGroep>();

        if (brpPersoonslijst != null) {
            // zoek BerichtLog op
            final BerichtLog berichtLog = dbService.zoekBerichtLog(aNummer);

            if (berichtLog != null) {
                // Zoek Lo3Persoonslijst op
                final Lo3Persoonslijst lo3Persoonslijst = dbService.haalLo3PersoonslijstUitBerichtLog(berichtLog);

                // Zoek logging op
                final List<LogRegel> logRegels = dbService.haalLogRegelsUitBerichtLog(berichtLog);

                List<FoutRegel> bcmSignaleringen = null;

                if (lo3Persoonslijst != null) {
                    // Controleer door BCM
                    bcmSignaleringen = bcmService.controleerDoorBCM(lo3Persoonslijst, foutMelder);
                }

                // Zoek terugconversie op. HOE?? in GBAV?
                final Lo3Persoonslijst teruggeconverteerd =
                        viewerService.converteerTerug(brpPersoonslijst, foutMelder);

                // Bepaal verschillen
                final List<StapelVergelijking> matches =
                        viewerService.vergelijkLo3OrigineelMetTerugconversie(lo3Persoonslijst, teruggeconverteerd,
                                foutMelder);

                // Resultaat
                persoonslijstGroepen.add(new PersoonslijstGroep(lo3Persoonslijst, brpPersoonslijst,
                        teruggeconverteerd, matches, logRegels, bcmSignaleringen));
            } else {
                foutMelder.log(LogSeverity.WARNING, ANR_MSG, "BerichtLog niet gevonden");
            }
        } else {
            foutMelder.log(LogSeverity.WARNING, ANR_MSG, "Persoonslijst niet gevonden of geen permissie");
        }

        response = new ViewerResponse(persoonslijstGroepen, foutMelder.getFoutRegels());

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
    @RequestMapping(value = "/file/upload")
    @ResponseBody
    public final ResponseEntity<String> uploadRequest(
            @RequestParam("qqfile") final String filename,
            @RequestBody final byte[] file) {
        return verwerkFileUpload(filename, file);
    }

    /**
     * Behandelt een upload request met alleen een file naam. Dit bestand moet op de server staan.
     * 
     * @param filename
     *            De file naam.
     * @return Json string met de PL.
     */
    @RequestMapping(value = "/file/uploadFileName")
    @ResponseBody
    public final ResponseEntity<String> uploadRequestFileName(@RequestParam("qqfile") final String filename) {
        return verwerkFileUpload(filename, demoMode.getDemoUploadFile(filename));
    }

    /**
     * Behandelt inkomend request, zijnde een upload, vanaf een IE.
     * 
     * @param file
     *            De file in een MultipartFile.
     * @return String met de persoonslijst.
     */
    @RequestMapping(value = "/file/uploadMSIE", method = RequestMethod.POST, produces = "text/plain")
    @ResponseBody
    public final ResponseEntity<String> uploadRequestMSIE(@RequestParam("qqfile") final MultipartFile file) {
        try {
            return verwerkFileUpload(file.getOriginalFilename(), file.getBytes());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Verwerkt de upload van een file.
     * 
     * @param filename
     * @param file
     * @return String met de persoonslijst.
     */
    private ResponseEntity<String> verwerkFileUpload(final String filename, final byte[] file) {
        final FoutMelder foutMelder = new FoutMelder();

        final List<Lo3Persoonslijst> pls = leesService.leesLo3Persoonslijst(filename, file, foutMelder);

        final List<PersoonslijstGroep> persoonslijstGroepen = new ArrayList<PersoonslijstGroep>(pls.size());

        for (final Lo3Persoonslijst lo3Persoonslijst : pls) {
            if (lo3Persoonslijst != null) {
                final List<LogRegel> logRegels = viewerService.verwerkPrecondities(lo3Persoonslijst, foutMelder);
                BrpPersoonslijst brpPersoonslijst = viewerService.converteerNaarBrp(lo3Persoonslijst, foutMelder);

                // Sorteren van de BrpPersoonslijst op basis van geldigheid. Anders komt het slecht overeen met de LO3
                // weergave
                brpPersoonslijst = BrpStapelSorter.sorteerPersoonslijst(brpPersoonslijst, foutMelder);

                final Lo3Persoonslijst teruggeconverteerd =
                        viewerService.converteerTerug(brpPersoonslijst, foutMelder);
                final List<FoutRegel> bcmSignaleringen = bcmService.controleerDoorBCM(lo3Persoonslijst, foutMelder);
                final List<StapelVergelijking> matches =
                        viewerService.vergelijkLo3OrigineelMetTerugconversie(lo3Persoonslijst, teruggeconverteerd,
                                foutMelder);

                persoonslijstGroepen.add(new PersoonslijstGroep(lo3Persoonslijst, brpPersoonslijst,
                        teruggeconverteerd, matches, logRegels, bcmSignaleringen));
            }
        }

        return jsonFormatter.format(new ViewerResponse(persoonslijstGroepen, foutMelder.getFoutRegels()));
    }
}
