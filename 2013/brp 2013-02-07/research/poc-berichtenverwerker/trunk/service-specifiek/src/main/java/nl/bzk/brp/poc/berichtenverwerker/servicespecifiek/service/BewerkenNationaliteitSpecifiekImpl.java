/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.berichtenverwerker.servicespecifiek.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.jws.WebService;

import nl.brp.actie.toevoegennationaliteit.ToevoegenNationaliteit;
import nl.brp.actie.verwijderennationaliteit.VerwijderNationaliteit;
import nl.brp.actie.wijzigennationaliteit.WijzigNationaliteit;
import nl.brp.basis.antwoord.StandaardAntwoord;
import nl.brp.contract.bewerkennationaliteitspecifiek.BewerkenNationaliteitSpecifiekPortType;
import nl.bzk.brp.poc.berichtenverwerker.model.Actie;
import nl.bzk.brp.poc.berichtenverwerker.model.Bron;
import nl.bzk.brp.poc.berichtenverwerker.model.Gem;
import nl.bzk.brp.poc.berichtenverwerker.model.Srtactie;
import nl.bzk.brp.poc.berichtenverwerker.service.PersoonEnNationaliteitService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Implementatie van de POC berichtenverwerker webservice die methodes aanbiedt voor het bewerken
 * van de nationaliteit.
 */
@WebService(targetNamespace = "http://bewerkennationaliteitspecifiek.berichtenverwerker.poc.brp.bzk.nl/",
            portName = "BewerkenNationaliteitSpecifiekImplPort",
            serviceName = "BewerkenNationaliteitSpecifiekImplService",
            endpointInterface = "nl.brp.contract.bewerkennationaliteitspecifiek.BewerkenNationaliteitSpecifiekPortType")
public class BewerkenNationaliteitSpecifiekImpl implements BewerkenNationaliteitSpecifiekPortType {

    @Autowired
    private PersoonEnNationaliteitService persoonEnNationaliteitService;

    /**
     * Voegt een nationaliteit toe aan een persoon. Het {@code bericht} bevat, naast algemene informatie aangaande het
     * bericht, de id van de persoon voor wie de nationaliteit moet worden toegevoegd en de id van de nationaliteit die
     * moet worden toegevoegd aan die persoon.
     *
     * @param bericht het bericht dat is verstuurd met daarin de parameters nodig voor het uitvoeren van het bericht.
     * @return een antwoord bericht met daarin een code die aangeeft of het bericht succesvol is uitgevoerd of niet.
     */
    @Override
    public final StandaardAntwoord toevoegenNationaliteitSpecifiek(final ToevoegenNationaliteit bericht) {
        Actie actie = bouwActie(bericht.getBRPActie());
        BigDecimal bsn = bericht.getPers().getBSN().getValue();
        BigDecimal aanvGel = bericht.getPers().getPersNation().getDatAanvGel().getValue();
        int nationId = bericht.getPers().getPersNation().getNation().getValue().intValue();
        persoonEnNationaliteitService.toevoegenNationaliteit(actie, actie.getBrons(), bsn, nationId, aanvGel);

        StandaardAntwoord response = new StandaardAntwoord();
        response.setActieId(actie.getId());
        response.setAntwoordcode("OK");
        response.setMelding(String.format("Nieuwe Nationaliteit '%s' toegevoegd aan '%s'", nationId, bsn));
        return response;
    }

    @Override
    public final StandaardAntwoord wijzigenNationaliteitSpecifiek(final WijzigNationaliteit bericht) {
        StandaardAntwoord response = new StandaardAntwoord();
        response.setAntwoordcode(String.format("Gewijzigde Nationaliteit: %s", bericht.getPers().getPersNation()
                .getNation().getValue()));
        return response;
    }

    /**
     * Verwijderd een nationaliteit van een persoon. Het {@code bericht} bevat, naast algemene informatie aangaande het
     * bericht, de id van de persoonsnationaliteit die moet worden verwijderd.
     *
     * @param bericht het bericht dat is verstuurd met daarin de parameters nodig voor het uitvoeren van het bericht.
     * @return een antwoord bericht met daarin een code die aangeeft of het bericht succesvol is uitgevoerd of niet.
     */
    @Override
    public final StandaardAntwoord verwijderenNationaliteitSpecifiek(final VerwijderNationaliteit bericht) {
        Actie actie = bouwActie(bericht.getActie());
        long persoonsNationaliteitId = bericht.getPers().getPersNation().getId().getValue();
        persoonEnNationaliteitService.opheffenNationaliteit(actie, actie.getBrons(), persoonsNationaliteitId);

        StandaardAntwoord response = new StandaardAntwoord();
        response.setActieId(actie.getId());
        response.setAntwoordcode("OK");
        response.setMelding(String.format("Bestaande Nationaliteit '%s' verwijderd van '%s'",
                persoonsNationaliteitId, bericht.getPers().getBSN().getValue().getValue()));
        return response;
    }

    /**
     * Bouwt een nieuwe instantie van de {@link Actie} class op basis van de gegevens in door de web service ontvangen
     * {@link nl.brp.actie.toevoegennationaliteit.Actie Actie} object.
     *
     * @param brpActie het actie object ontvangen door de web service.
     * @return een nieuwe Actie instantie.
     */
    private Actie bouwActie(final nl.brp.actie.toevoegennationaliteit.Actie brpActie) {
        Srtactie soortActie = new Srtactie();
        soortActie.setId(brpActie.getSrt().getValue().intValue());
        Set<Bron> bronnen = new HashSet<Bron>();

        for (nl.brp.actie.toevoegennationaliteit.Bron bron : brpActie.getBron()) {
            Bron newBron = new Bron();
            bronnen.add(newBron);
        }

        Actie actie = new Actie();
        actie.setSrtactie(soortActie);
        actie.setBrons(bronnen);
        if (brpActie.getGem() != null) {
            actie.setGem(new Gem(brpActie.getGem().getValue().getValue().intValue(), null));
        }
        if (brpActie.getTijdstipOntlening() != null) {
            actie.setTijdstipontlening(brpActie.getTijdstipOntlening().getValue().getValue().toGregorianCalendar()
                    .getTime());
        }
        if (brpActie.getTijdstipReg() != null) {
            actie.setTijdstipreg(brpActie.getTijdstipReg().getValue().toGregorianCalendar().getTime());
        }
        return actie;
    }

    /**
     * Bouwt een nieuwe instantie van de {@link Actie} class op basis van de gegevens in door de web service ontvangen
     * {@link nl.brp.actie.verwijderennationaliteit.Actie Actie} object.
     *
     * @param brpActie het actie object ontvangen door de web service.
     * @return een nieuwe Actie instantie.
     */
    private Actie bouwActie(final nl.brp.actie.verwijderennationaliteit.Actie brpActie) {
        Srtactie soortActie = new Srtactie();
        soortActie.setId(brpActie.getSrt().getValue().intValue());
        Set<Bron> bronnen = new HashSet<Bron>();

        for (nl.brp.actie.verwijderennationaliteit.Bron bron : brpActie.getBron()) {
            Bron newBron = new Bron();
            bronnen.add(newBron);
        }

        Actie actie = new Actie();
        actie.setSrtactie(soortActie);
        actie.setBrons(bronnen);
        if (brpActie.getGem() != null) {
            actie.setGem(new Gem(brpActie.getGem().getValue().getValue().intValue(), null));
        }
        if (brpActie.getTijdstipOntlening() != null) {
            actie.setTijdstipontlening(brpActie.getTijdstipOntlening().getValue().getValue().toGregorianCalendar()
                    .getTime());
        }
        if (brpActie.getTijdstipReg() != null) {
            actie.setTijdstipreg(brpActie.getTijdstipReg().getValue().toGregorianCalendar().getTime());
        }
        return actie;
    }

}
