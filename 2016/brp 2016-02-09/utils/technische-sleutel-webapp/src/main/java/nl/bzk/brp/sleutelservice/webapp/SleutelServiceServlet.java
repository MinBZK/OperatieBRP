/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.sleutelservice.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.bzk.brp.webservice.business.service.ObjectSleutelService;
import nl.bzk.brp.webservice.business.service.ObjectSleutelServiceImpl;

public class SleutelServiceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ObjectSleutelService sleutelService = new ObjectSleutelServiceImpl();

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        String persoonIdString = request.getParameter("persoonId");
        String partijCodeString = request.getParameter("partijCode");
        request.setAttribute("persoonId", persoonIdString);
        request.setAttribute("partijCode", partijCodeString);
        try {
            int persoonId = Integer.parseInt(persoonIdString);
            int partijCode = Integer.parseInt(partijCodeString);
            request.setAttribute("sleutel", sleutelService.genereerObjectSleutelString(persoonId, partijCode));
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Persoon id en partij code moeten numeriek zijn.");
        }

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

}
