/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import nl.bzk.migratiebrp.isc.console.mig4jsf.util.ValidationUtil;
import nl.bzk.migratiebrp.isc.jbpm.command.Command;
import nl.bzk.migratiebrp.isc.jbpm.command.client.CommandClient;
import nl.bzk.migratiebrp.isc.jbpm.command.exception.CommandException;
import nl.bzk.migratiebrp.isc.jbpm.command.impl.JbpmSynchronisatievraagCommand;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringService;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringServiceFactory;
import org.apache.commons.io.IOUtils;
import org.jbpm.jsf.JbpmJsfContext;

/**
 * Synchronisatievraag actionlistener.
 */
public final class SynchronisatievraagActionListener extends AbstractActionListener {

    private static final Pattern GEMEENTE_PATTERN = Pattern.compile("^[0-9]{4}$");
    private static final String OUTCOME_SINGLE = "single";
    private static final String OUTCOME_MULTI = "multiple";
    private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";

    private static final String MESSAGE_OK = "Synchronisatievraag is gestart.";

    private final ValueExpression gemeenteExpression;
    private final ValueExpression aNummerExpression;
    private final ValueExpression bulkBestandExpression;
    private final ValueExpression targetExpression;

    /**
     * Constructor.
     *
     * @param gemeenteExpression
     *            gemeente expression
     * @param aNummerExpression
     *            a-nummer expression
     * @param bulkBestandExpression
     *            bulk bestand expression
     * @param targetExpression
     *            target expression
     */
    public SynchronisatievraagActionListener(
        final ValueExpression gemeenteExpression,
        final ValueExpression aNummerExpression,
        final ValueExpression bulkBestandExpression,
        final ValueExpression targetExpression)
    {
        super("synchronisatievraag");
        this.gemeenteExpression = gemeenteExpression;
        this.aNummerExpression = aNummerExpression;
        this.bulkBestandExpression = bulkBestandExpression;
        this.targetExpression = targetExpression;
    }

    @Override
    public void verwerkAction(final JbpmJsfContext context, final ActionEvent event) throws IOException, CommandException {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();

        final String gemeente = (String) gemeenteExpression.getValue(elContext);
        final String aNummer = (String) aNummerExpression.getValue(elContext);
        final Object bulkBestand = bulkBestandExpression.getValue(elContext);

        valideerGemeente(context, gemeente);
        valideerANummer(context, aNummer);
        valideerCombinatie(context, gemeente, aNummer, bulkBestand);

        final Command<?> command;
        if (OUTCOME_SINGLE.equals(context.getOutcome())) {
            command = maakSingleCommand(gemeente, aNummer);
        } else if (OUTCOME_MULTI.equals(context.getOutcome())) {
            command = maakBulkCommand(bulkBestand);
        } else {
            // Bij validatie fouten
            command = null;
        }

        if (command != null) {
            final SpringService springService =
                    (SpringService) context.getJbpmContext().getServiceFactory(SpringServiceFactory.SERVICE_NAME).openService();
            final CommandClient commandClient = springService.getBean(CommandClient.class);
            final Object resultaat = commandClient.executeCommand(command);
            targetExpression.setValue(elContext, resultaat);
            context.addSuccessMessage(MESSAGE_OK);
            context.selectOutcome("success");
        }
    }

    private Command<?> maakSingleCommand(final String gemeente, final String aNummer) {
        final StringBuilder uc811 = new StringBuilder(XML_HEADER + "<uc811 xmlns=\"http://www.moderniseringgba.nl/ISC/0001\"><gemeenteCode>");
        uc811.append(gemeente);
        uc811.append("</gemeenteCode><aNummer>");
        uc811.append(aNummer);
        uc811.append("</aNummer></uc811>");

        return new JbpmSynchronisatievraagCommand("uc811", uc811.toString(), gemeente, "Uc811");

    }

    private Command<?> maakBulkCommand(final Object bulkBestand) throws IOException {
        final StringBuilder uc812 = new StringBuilder(XML_HEADER + "<uc812 xmlns=\"http://www.moderniseringgba.nl/ISC/0001\"><bulkSynchronisatievraag>");

        uc812.append(leesBulkBestand(bulkBestand));
        uc812.append("</bulkSynchronisatievraag></uc812>");

        return new JbpmSynchronisatievraagCommand("uc812", uc812.toString(), null, "Uc812");
    }

    private String leesBulkBestand(final Object bulkBestand) throws IOException {
        if (bulkBestand instanceof InputStream) {
            return IOUtils.toString((InputStream) bulkBestand);
        } else if (bulkBestand instanceof byte[]) {
            return new String((byte[]) bulkBestand, "UTF-8");
        } else {
            throw new IllegalArgumentException("Obekend soort bulkbestand." + (bulkBestand == null ? "" : " Class=" + bulkBestand.getClass().getName()));
        }
    }

    /* ************************************************************************************************************* */
    /* *** VALIDATIE *********************************************************************************************** */
    /* ************************************************************************************************************* */

    private void valideerGemeente(final JbpmJsfContext context, final String gemeente) {
        if (gemeente != null && !"".equals(gemeente) && !GEMEENTE_PATTERN.matcher(gemeente).matches()) {
            context.setError("Gemeente moet een geldige gemeentecode bevatten.");
        }
    }

    private void valideerANummer(final JbpmJsfContext context, final String aNummer) {
        if (aNummer != null && !"".equals(aNummer) && !ValidationUtil.valideerANummer(aNummer)) {
            context.setError("A-nummer moet een geldig a-nummer bevatten.");
        }
    }

    private void valideerCombinatie(final JbpmJsfContext context, final String gemeente, final String aNummer, final Object bulkBestand) {
        final boolean heeftGemeente = gemeente != null && !"".equals(gemeente);
        final boolean heeftAnummer = aNummer != null && !"".equals(aNummer);
        final boolean single = heeftGemeente || heeftAnummer;
        final boolean multiple = bulkBestand != null;

        boolean error = false;

        if (single) {
            if (heeftGemeente != heeftAnummer) {
                context.setError("Gemeente en a-nummer moeten beide gevuld zijn.");
            }
            if (multiple) {
                error = true;
            } else {
                if (!context.isError()) {
                    context.selectOutcome(OUTCOME_SINGLE);
                }
            }
        } else {
            if (multiple) {
                if (!context.isError()) {
                    context.selectOutcome(OUTCOME_MULTI);
                }
            } else {
                error = true;
            }
        }

        if (error) {
            context.setError("Er moet of een gemeente en a-nummer worden gevuld" + " of een bulk bestand worden opgegeven.");
        }
    }

}
