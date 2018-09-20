/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.jsf.mig;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

public class ProcessInstanceUtil {

    public static ProcessInstance getRootProcessInstance(final ProcessInstance processInstance) {
        ProcessInstance result = processInstance;
        while (result.getSuperProcessToken() != null) {
            Token superToken = result.getSuperProcessToken();
            while (superToken.getParent() != null) {
                superToken = superToken.getParent();
            }
            result = superToken.getProcessInstance();
        }
        return result;
    }

    public static List<ProcessInstance> getProcessInstances(
            final JbpmContext context,
            final ProcessInstance processInstance) {
        final List<ProcessInstance> result = new ArrayList<ProcessInstance>();
        result.add(processInstance);

        @SuppressWarnings("unchecked")
        final List<Token> tokens = processInstance.findAllTokens();

        for (final Token token : tokens) {
            final List<Long> subProcessInstanceIds = findSubProcessInstanceIds(context, token.getId());
            for (final Long subProcessInstanceId : subProcessInstanceIds) {
                final ProcessInstance subProcessInstance = context.getProcessInstance(subProcessInstanceId);

                result.addAll(getProcessInstances(context, subProcessInstance));
            }
        }

        return result;
    }

    /**
     * Find process instance id's that have the given token (id) and super process token.
     * 
     * @param token
     *            token
     * @return list of process instance id's (empty list if nothing found)
     */
    private static List<Long> findSubProcessInstanceIds(final JbpmContext context, final Long tokenId) {
        try {
            final PreparedStatement statement =
                    context.getConnection().prepareStatement(
                            "select id_ from jbpm_processinstance where superprocesstoken_ = ?");
            statement.setLong(1, tokenId);

            final List<Long> result = new ArrayList<Long>();

            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getLong(1));
            }

            statement.close();

            return result;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
