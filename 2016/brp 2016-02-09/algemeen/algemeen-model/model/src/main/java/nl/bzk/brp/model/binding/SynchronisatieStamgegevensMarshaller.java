/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.binding;

import java.util.List;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshaller;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;
import org.jibx.runtime.impl.MarshallingContext;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;


/**
 * Speciale marshaller voor synchronisatie stamgegevens. Dit omdat we met enkel binding configuratie niet uit de voeten kunnen.
 */
public final class SynchronisatieStamgegevensMarshaller implements IMarshaller {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Zie package attribuut in de binding definitie voor synchronisatie stamgegevens.
     */
    private static final String BINDING_PACKAGE = "nl.bzk.brp.model.algemeen.stamgegeven";
    /**
     * Zie name attribuut in de binding, naam van de binding.
     */
    private static final String BINDING_NAAM    = "synchronisatiestamgegevens";

    /**
     * The Binding factory voor de binding.
     */
    private IBindingFactory synchronisatieStamgegevensBindingFactory;

    /**
     * Default constructor.
     */
    public SynchronisatieStamgegevensMarshaller() {
        try {
            this.synchronisatieStamgegevensBindingFactory = BindingDirectory.getFactory(BINDING_NAAM, BINDING_PACKAGE);
        } catch (JiBXException e) {
            LOGGER.error("Kan Binding factory niet vinden, klopt de naam en de package van de binding nog?", e);
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public boolean isExtension(final String mapname) {
        return false;
    }

    @Override
    public void marshal(final Object obj, final IMarshallingContext marshallingContext) throws JiBXException {

        // Meegegeven marshalling context is van de aanroepende factory.
        // Namelijk de binding waar deze mapper in wordt aangeroepen.
        if (obj != null) {
            final List<SynchroniseerbaarStamgegeven> synchronisatieStamgegevens = (List<SynchroniseerbaarStamgegeven>) obj;
            final MarshallingContext aanroependeBindingContext = (MarshallingContext) marshallingContext;
            final MarshallingContext context =
                (MarshallingContext) synchronisatieStamgegevensBindingFactory.createMarshallingContext();
            context.setXmlWriter(aanroependeBindingContext.getXmlWriter());

            if (!synchronisatieStamgegevens.isEmpty()) {
                final String containerTypeName = bepaalContainerMappingTypeName(synchronisatieStamgegevens.get(0));
                context.getMarshaller(containerTypeName).marshal(synchronisatieStamgegevens, context);
            }
        }
    }

    /**
     * Type-name in de binding voor de mapping die hoort bij dit stamgegeven.
     *
     * @param synchronisatieStamgegeven het synchronisatie stamgegeven.
     * @return type-name van de abstract mapping.
     */
    private String bepaalContainerMappingTypeName(final SynchroniseerbaarStamgegeven synchronisatieStamgegeven) {
        return "Container_" + synchronisatieStamgegeven.getClass().getSimpleName() + "_Levering";
    }
}
