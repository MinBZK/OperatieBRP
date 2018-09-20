/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale.services;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.bijhouding.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.bijhouding.business.service.BijhoudingsBerichtVerwerker;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.gba.centrale.berichten.AntwoordBerichtFactory;
import nl.bzk.brp.gba.centrale.scope.RequestScope;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingAntwoordBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.service.ObjectSleutelService;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.JmsException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Verwerk toevallige gebeurtenis vanuit GBA.
 */
public final class ToevalligeGebeurtenisService implements GbaService, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private RequestScope                requestScope;
    @Inject
    private AntwoordBerichtFactory      antwoordBerichtFactory;
    @Inject
    private ObjectSleutelService        objectSleutelService;
    @Inject
    private BijhoudingsBerichtVerwerker bijhoudingsBerichtVerwerker;
    @Inject
    private ReferentieDataRepository    referentieDataRepository;

    private IBindingFactory bijhoudingBindingFactory;
    private IBindingFactory antwoordBindingFactory;

    @Override
    public void afterPropertiesSet() throws JiBXException {
        bijhoudingBindingFactory = BindingDirectory.getFactory(BijhoudingsBericht.class);
        antwoordBindingFactory = BindingDirectory.getFactory(BijhoudingAntwoordBericht.class);
    }

    @Override
    @Transactional(transactionManager = "lezenSchrijvenTransactionManager")
    public String verwerk(final String bericht, final String berichtReferentie) {

        // Deserialize naar Object
        final BijhoudingsBericht notificatie;
        try {
            final IUnmarshallingContext unmarshallingContext = bijhoudingBindingFactory.createUnmarshallingContext();
            notificatie = (BijhoudingsBericht) unmarshallingContext.unmarshalDocument(new StringReader(bericht));
        } catch (final JiBXException e) {
            throw new JmsException("Kan bericht niet unmarshallen naar BijhoudingsBericht.", e) {
                private static final long serialVersionUID = 1L;
            };
        }
        LOGGER.info("BijhoudingsBericht: {}", notificatie);

        // Request scope
        requestScope.reset();

        final BijhoudingAntwoordBericht antwoord;
        try {
            // Verwerk
            antwoord = verwerkNotificatie(notificatie);

        } finally {
            requestScope.reset();
        }
        LOGGER.info("AntwoordBericht: {}", antwoord);

        // Serialize antwoord naar XML
        final StringWriter antwoordWriter;
        try {
            final IMarshallingContext marshallingContext = antwoordBindingFactory.createMarshallingContext();
            antwoordWriter = new StringWriter();
            marshallingContext.marshalDocument(antwoord, null, null, antwoordWriter);
        } catch (final JiBXException e) {
            throw new JmsException("Kan bericht niet marshallen van AntwoordBericht.", e) {

                private static final long serialVersionUID = 1L;

            };
        }
        return antwoordWriter.toString();
    }

    private BijhoudingAntwoordBericht verwerkNotificatie(final BijhoudingsBericht notificatie) {
        LOGGER.debug("Bijhouding bericht: {}", notificatie);

        // Vervang objectsleutels
        final Integer partijCode = Integer.valueOf(notificatie.getStuurgegevens().getZendendePartijCode());
        vervangObjectsleutels(notificatie, partijCode);

        // Context
        // TODO: Inkomend bericht ID wordt gebruikt voor een uniek transactie ID
        final BerichtenIds berichtenIds = new BerichtenIds(-1L, null);
        final Partij partij = referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(partijCode));
        final BijhoudingBerichtContext context =
                new BijhoudingBerichtContext(
                    berichtenIds,
                    partij,
                    notificatie.getStuurgegevens().getReferentienummer().getWaarde(),
                    notificatie.getCommunicatieIdMap());
        LOGGER.debug("Bijhouding context: {}", context);

        // Uitvoeren bijhouding
        final BijhoudingResultaat bijhoudingResultaat = bijhoudingsBerichtVerwerker.verwerkBericht(notificatie, context);

        LOGGER.debug("Bijhouding resultaat: {}", bijhoudingResultaat);
        if (bijhoudingResultaat.getMeldingen() != null && !bijhoudingResultaat.getMeldingen().isEmpty()) {
            for (final Melding melding : bijhoudingResultaat.getMeldingen()) {
                LOGGER.debug("Melding: {} - {}", melding.getSoort(), melding.getMeldingTekst());
            }
        }

        LOGGER.info("Administratieve handeling: {}", bijhoudingResultaat.getAdministratieveHandeling());

        // Maak antwoord bericht
        return (BijhoudingAntwoordBericht) antwoordBerichtFactory.bouwAntwoordBericht(notificatie, bijhoudingResultaat);
    }

    private void vervangObjectsleutels(final BijhoudingsBericht bericht, final Integer partijCode) {
        LOGGER.info("vervang objectsleutels");
        final AdministratieveHandelingBericht administratieveHandeling = bericht.getStandaard().getAdministratieveHandeling();
        final Collection<ActieBericht> acties = administratieveHandeling.getActies();
        LOGGER.debug("Aantal acties in administratieve handeling: " + acties.size());
        for (final ActieBericht actie : acties) {
            final BerichtRootObject root = actie.getRootObject();
            LOGGER.debug("Type bericht in actie " + root.getClass().getName());
            if (root instanceof HuwelijkBericht) {
                final HuwelijkBericht huwelijk = (HuwelijkBericht) root;
                final List<BetrokkenheidBericht> betrokkenheden = huwelijk.getBetrokkenheden();
                for (final BetrokkenheidBericht betrokkenheidBericht : betrokkenheden) {
                    werkPersoonBijMetCorrecteObjectSleutel(partijCode, betrokkenheidBericht.getPersoon());
                }
            } else if (root instanceof PersoonBericht) {
                final PersoonBericht persoon = (PersoonBericht) root;
                werkPersoonBijMetCorrecteObjectSleutel(partijCode, persoon);
            } else {
                throw new IllegalStateException("Bericht wordt niet ondersteund: " + root.getClass().getName());
            }
        }
    }

    private void werkPersoonBijMetCorrecteObjectSleutel(final Integer partijCode, final PersoonBericht persoon) {
        if ("PersoonTechnischId".equals(persoon.getObjecttype())) {
            final Integer technischId = Integer.valueOf(persoon.getObjectSleutel());
            final String objectSleutel = objectSleutelService.genereerObjectSleutelString(technischId, partijCode);
            persoon.setObjectSleutel(objectSleutel);
            LOGGER.debug("Technische ID {} vervangen door object sleutel {}", technischId, objectSleutel);
            persoon.setObjecttype("Persoon");
        }
    }

}
