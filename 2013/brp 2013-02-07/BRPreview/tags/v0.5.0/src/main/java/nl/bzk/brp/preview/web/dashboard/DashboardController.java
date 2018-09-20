/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.web.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * De Class DashboardController.
 */
@Controller
public class DashboardController {

    /**
     * Homepage for the dashboard.
     *
     * @param model de model
     * @return de string
     */
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String home(final Model model) {
        model.addAttribute("welcome", "Welcome message!");
        return "dashboard";
    }
}
