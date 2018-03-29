/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.service;

import java.util.GregorianCalendar;
import java.util.UUID;
import javax.inject.Inject;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceException;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.brp.brp0200.DatumTijd;
import nl.bzk.brp.brp0200.GroepBerichtParametersStuurVrijBericht;
import nl.bzk.brp.brp0200.GroepBerichtStuurgegevens;
import nl.bzk.brp.brp0200.GroepBerichtVrijBericht;
import nl.bzk.brp.brp0200.NaamEnumeratiewaarde;
import nl.bzk.brp.brp0200.ObjectFactory;
import nl.bzk.brp.brp0200.PartijCode;
import nl.bzk.brp.brp0200.Referentienummer;
import nl.bzk.brp.brp0200.SysteemNaam;
import nl.bzk.brp.brp0200.VrijBerichtData;
import nl.bzk.brp.brp0200.VrijBerichtStuurVrijBericht;
import nl.bzk.brp.brp0200.VrijBerichtStuurVrijBerichtResultaat;
import nl.bzk.brp.vrijbericht.vrijbericht.service.VrbStuurVrijBericht;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Implementatie van de vrij bericht web service client.
 */
@Service
final class VrijBerichtClientServiceImpl implements VrijBerichtClientService {

    private static final int PARTIJ_CODE_LENGTE = 6;
    private final ObjectFactory objectFactory = new ObjectFactory();

    @Inject
    private VrbStuurVrijBericht service;

    private VrijBerichtClientServiceImpl() {
    }

    @Override
    public void verstuurVrijBericht(final VrijBericht vrijBericht, final String ontvangendePartijCode) throws VrijBerichtClientException {
        try {
            // Maak verzoek
            final VrijBerichtStuurVrijBericht verzoek = new VrijBerichtStuurVrijBericht();
            final GroepBerichtVrijBericht groepBerichtVrijBericht = new GroepBerichtVrijBericht();
            final PartijCode zendendePartij = new PartijCode();
            groepBerichtVrijBericht.setCommunicatieID(UUID.randomUUID().toString());
            zendendePartij.setValue(Partij.PARTIJ_CODE_BRP);
            // Vul verzoek
            vulStuurgegevens(vrijBericht, verzoek, zendendePartij);
            vulParameters(ontvangendePartijCode, verzoek, zendendePartij);
            vulInhoud(vrijBericht, groepBerichtVrijBericht);
            // Verstuur
            verzoek.setVrijBericht(objectFactory.createObjecttypeBerichtVrijBericht(groepBerichtVrijBericht));
            final VrijBerichtStuurVrijBerichtResultaat resultaat = service.stuurVrijBericht(verzoek);
            // Verwerk resultaat
            final String resultaatVerwerking = resultaat.getResultaat().getValue().getVerwerking().getValue().getValue().value();
            if (!VerwerkingsResultaat.GESLAAGD.getNaam().equals(resultaatVerwerking)) {
                throw new VrijBerichtClientException(String.format("Vrij bericht service retourneerde verwerkingsresultaat %s.", resultaat));
            }
        } catch (WebServiceException | DatatypeConfigurationException e) {
            throw new VrijBerichtClientException(e.getMessage(), e);
        }
    }

    private void vulInhoud(final VrijBericht vrijBericht, final GroepBerichtVrijBericht groepBerichtVrijBericht) {
        final VrijBerichtData vrijBerichtData = new VrijBerichtData();
        vrijBerichtData.setValue(vrijBericht.getData());
        final NaamEnumeratiewaarde soortNaam = new NaamEnumeratiewaarde();
        soortNaam.setValue(vrijBericht.getSoortVrijBericht().getNaam());
        groepBerichtVrijBericht.setInhoud(objectFactory.createGroepBerichtVrijBerichtInhoud(vrijBerichtData));
        groepBerichtVrijBericht.setSoortNaam(objectFactory.createGroepBerichtVrijBerichtSoortNaam(soortNaam));
    }

    private void vulParameters(final String ontvangendePartijCode, final VrijBerichtStuurVrijBericht verzoek, final PartijCode zendendePartij) {
        final GroepBerichtParametersStuurVrijBericht parameters = new GroepBerichtParametersStuurVrijBericht();
        parameters.setCommunicatieID(UUID.randomUUID().toString());
        parameters.setZenderVrijBericht(objectFactory.createGroepBerichtParametersZenderVrijBericht(zendendePartij));
        final PartijCode ontvangerVrijBericht = new PartijCode();
        ontvangerVrijBericht.setValue(StringUtils.leftPad(ontvangendePartijCode, PARTIJ_CODE_LENGTE, "0"));
        parameters.setOntvangerVrijBericht(objectFactory.createGroepBerichtParametersOntvangerVrijBericht(ontvangerVrijBericht));
        verzoek.setParameters(objectFactory.createObjecttypeBerichtParameters(parameters));
    }

    private void vulStuurgegevens(final VrijBericht vrijBericht, final VrijBerichtStuurVrijBericht verzoek, final PartijCode zendendePartij)
            throws DatatypeConfigurationException {
        final GroepBerichtStuurgegevens stuurgegevens = objectFactory.createGroepBerichtStuurgegevens();
        stuurgegevens.setCommunicatieID(UUID.randomUUID().toString());
        final SysteemNaam zendendeSysteem = new SysteemNaam();
        final Referentienummer referentieNummer = new Referentienummer();
        final DatumTijd tijdstipVerzending = new DatumTijd();
        zendendeSysteem.setValue("BRP");
        referentieNummer.setValue(UUID.randomUUID().toString());
        final GregorianCalendar c = new GregorianCalendar();
        final XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        c.setTime(vrijBericht.getTijdstipRegistratie());
        tijdstipVerzending.setValue(xmlDate);
        stuurgegevens.setZendendePartij(objectFactory.createGroepBerichtStuurgegevensZendendePartij(zendendePartij));
        stuurgegevens.setZendendeSysteem(objectFactory.createGroepBerichtStuurgegevensZendendeSysteem(zendendeSysteem));
        stuurgegevens.setReferentienummer(objectFactory.createGroepBerichtStuurgegevensReferentienummer(referentieNummer));
        stuurgegevens.setTijdstipVerzending(objectFactory.createGroepBerichtStuurgegevensTijdstipVerzending(tijdstipVerzending));
        verzoek.setStuurgegevens(objectFactory.createObjecttypeBerichtStuurgegevens(stuurgegevens));
    }
}
