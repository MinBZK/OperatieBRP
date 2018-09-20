/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.bzk.brp.beheer.webapp.BeheerConstants;
import nl.bzk.brp.logging.Data;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * Filter welke alle requests logt.
 */
public class LogFilter extends OncePerRequestFilter {

    private static final String HEADER_AUTORIZATION        = "Authorization";
    private static final int    BEGIN_INDEX_GEBRUIKERSNAAM = 5;
    private static final Logger LOGGER                     = LoggerFactory.getLogger();
    private static final String JA                         = "Ja";
    private static final String NEE                        = "Nee";
    private static final String METHOD_OPTIONS             = "OPTIONS";

    @Override
    protected final void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain filterChain) throws ServletException, IOException
    {
        LOGGER.info(FunctioneleMelding.BEHEER_VERZOEK_VERWERKEN, Data.asMap(BeheerConstants.BEHEER_ACTIE, request.getMethod(),
                BeheerConstants.BEHEER_GEIDENTIFICEERD,
                getGebruikerUitHeader(request.getHeader(HEADER_AUTORIZATION)) == null ? NEE : JA,
                    BeheerConstants.BEHEER_IDENTIFICATIE,
                getGebruikerUitHeader(request.getHeader(HEADER_AUTORIZATION)) == null ? ""
                    : getGebruikerUitHeader(request.getHeader(HEADER_AUTORIZATION)), BeheerConstants.BEHEER_IP_ADRES,
                        request.getRemoteHost(), BeheerConstants.BEHEER_URL, request.getRequestURL().toString()));

        // OPTIONS requests worden niet gelogd.
        if (!METHOD_OPTIONS.equals(request.getMethod())) {
            logVoorVerwerking(request);
        }

        try {
            filterChain.doFilter(request, response);
        } catch (IOException | ServletException exception) {
            LOGGER.info(FunctioneleMelding.BEHEER_ONVERWACHTE_FOUT, Data.asMap(BeheerConstants.BEHEER_EXCEPTIE,
                    exception.getMessage(), getGebruikerUitHeader(request.getHeader(HEADER_AUTORIZATION)) == null ? NEE
                        : JA, BeheerConstants.BEHEER_IDENTIFICATIE,
                        getGebruikerUitHeader(request.getHeader(HEADER_AUTORIZATION)) == null ? ""
                            : getGebruikerUitHeader(request.getHeader(HEADER_AUTORIZATION)),
                            BeheerConstants.BEHEER_STACKTRACE, ExceptionUtils.getFullStackTrace(exception)));

            // Rethrow
            throw exception;
        }

        if (!METHOD_OPTIONS.equals(request.getMethod())) {
            logNaVerwerking(response);
        }

    }

    private void logVoorVerwerking(final HttpServletRequest request) {
        LOGGER.info(FunctioneleMelding.BEHEER_VERZOEK_ONTVANGEN, Data.asMap(
                BeheerConstants.BEHEER_MEEGESTUURDE_GEGEVENS, converteerParameterEnumeratieNaarString(request)));
    }

    private void logNaVerwerking(final HttpServletResponse response) {
        LOGGER.info(FunctioneleMelding.BEHEER_VERZOEK_VERWERKT, Data.asMap(BeheerConstants.BEHEER_RESULTAAT, HttpStatus
                .valueOf(response.getStatus()).getReasonPhrase(), BeheerConstants.BEHEER_GEAUTORISEERD, response
                .getStatus() == HttpServletResponse.SC_UNAUTHORIZED ? NEE : JA));
    }

    /**
     * Converteert een enumeratie van parameters naar een string zodat deze eenvoudiger gelogd kan worden.
     *
     * @param request Het request dat de parameters bevat.
     * @return
     */
    private String converteerParameterEnumeratieNaarString(final HttpServletRequest request) {

        final Enumeration<String> parameterEnumeratie = request.getParameterNames();

        final StringBuilder meegestuurdeGegevens = new StringBuilder("");

        while (parameterEnumeratie.hasMoreElements()) {
            final String parameter = parameterEnumeratie.nextElement();
            meegestuurdeGegevens.append(parameter);
            meegestuurdeGegevens.append("=");
            meegestuurdeGegevens.append(request.getParameter(parameter));
            if (parameterEnumeratie.hasMoreElements()) {
                meegestuurdeGegevens.append("\n\t");
            }
        }

        return meegestuurdeGegevens.toString();
    }

    private String getGebruikerUitHeader(final String autorisatieString) {

        // Sla de eerste vijf tekens over, deze zijn altijd BASIC.
        if (autorisatieString == null || !autorisatieString.toUpperCase().startsWith("BASIC")) {
            return null;
        }

        String gebruikersnaam;

        try {
            final String decodedString =
                    new String(Base64.decodeBase64(autorisatieString.substring(BEGIN_INDEX_GEBRUIKERSNAAM)), "UTF-8");
            final String[] gebruikerWachtwoordcombi = decodedString.split(":");
            gebruikersnaam = gebruikerWachtwoordcombi[0];
        } catch (final UnsupportedEncodingException exception) {
            LOGGER.info(FunctioneleMelding.BEHEER_ONVERWACHTE_FOUT, exception.getMessage());
            gebruikersnaam = null;
        }

        return gebruikersnaam;
    }
}
