/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import com.sun.facelets.tag.AbstractTagLibrary;
import nl.bzk.migratiebrp.isc.console.mig4jsf.filter.BerichtenFilterHandler;
import nl.bzk.migratiebrp.isc.console.mig4jsf.filter.ProcessInstancesFilterHandler;
import nl.bzk.migratiebrp.isc.console.mig4jsf.jmx.BepaalEsbNaamHandler;
import nl.bzk.migratiebrp.isc.console.mig4jsf.jmx.JmxAttributeHandler;
import nl.bzk.migratiebrp.isc.console.mig4jsf.jmx.JmxServerHandler;
import nl.bzk.migratiebrp.isc.console.mig4jsf.pager.PagerHandler;

/**
 * Migratie tag library.
 */
public class MigratieLibrary extends AbstractTagLibrary {

    /**
     * Constructor.
     */
    public MigratieLibrary() {
        super("http://www.bzk.nl/migratiebrp");

        // Actions
        addTagHandler("loadBericht", LoadBerichtHandler.class);
        addTagHandler("listBerichtenForProcessInstance", ListBerichtenForProcessInstanceHandler.class);
        addTagHandler("listBerichten", ListBerichtenHandler.class);
        addTagHandler("prettyPrintBericht", PrettyPrintBerichtHandler.class);
        addTagHandler("berichtenFilter", BerichtenFilterHandler.class);

        addTagHandler("listJobs", ListJobsHandler.class);
        addTagHandler("listGerelateerdeGegevensForProcessInstance", ListGerelateerdeGegevensForProcessInstanceHandler.class);

        addTagHandler("listTasks", ListTasksHandler.class);
        addTagHandler("listTasksForActor", ListTasksForActorHandler.class);
        addTagHandler("unassignTask", UnassignTaskHandler.class);

        addTagHandler("listTransitionsForProcessInstance", ListTransitionsForProcessInstanceHandler.class);

        addTagHandler("listProcessInstances", ListProcessInstancesHandler.class);
        addTagHandler("listProcessInstancesForProcessInstance", ListProcessInstancesForProcessInstanceHandler.class);
        addTagHandler("processInstancesFilter", ProcessInstancesFilterHandler.class);

        addTagHandler("reportSelectedChoice", ReportSelectedChoiceHandler.class);

        addTagHandler("resumeProcessInstance", ResumeProcessInstanceHandler.class);
        addTagHandler("suspendProcessInstance", SuspendProcessInstanceHandler.class);

        addTagHandler("synchronisatievraag", SynchronisatievraagHandler.class);
        addTagHandler("startTaak", StartTaakHandler.class);

        addTagHandler("pager", PagerHandler.class);

        addTagHandler("jmxServer", JmxServerHandler.class);
        addTagHandler("jmxAttribute", JmxAttributeHandler.class);
        addTagHandler("bepaalEsbNaam", BepaalEsbNaamHandler.class);

        addTagHandler("verwerkTaak", VerwerkTaakHandler.class);
        addTagHandler("propertyLoader", PropertyLoaderHandler.class);

        addTagHandler("loadRootProcessInstanceForTask", LoadRootProcessInstanceForTaskHandler.class);
    }
}
