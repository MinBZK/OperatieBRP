/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.web;

import nl.bzk.brp.model.data.kern.HisOnderzoek;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/hisonderzoeks")
@Controller
@RooWebScaffold(path = "hisonderzoeks", formBackingObject = HisOnderzoek.class)
public class HisOnderzoekController {
}
