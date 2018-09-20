/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;


import nl.bzk.brp.model.bijhouding.ActualiseerAfstammingAntwoordBericht;
import nl.bzk.brp.model.bijhouding.ActualiseerAfstammingBericht;
import nl.bzk.brp.model.bijhouding.CorrigeerAdresAntwoordBericht;
import nl.bzk.brp.model.bijhouding.CorrigeerAdresBericht;
import nl.bzk.brp.model.bijhouding.RegistreerAdoptieAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerAdoptieBericht;
import nl.bzk.brp.model.bijhouding.RegistreerErkenningAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerErkenningBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGBAGeboorteAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGBAGeboorteBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGBAHuwelijkGeregistreerdPartnerschapAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGBAHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGeboorteAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGeboorteBericht;
import nl.bzk.brp.model.bijhouding.RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bijhouding.RegistreerKiesrechtAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerKiesrechtBericht;
import nl.bzk.brp.model.bijhouding.RegistreerMededelingVerzoekAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerMededelingVerzoekBericht;
import nl.bzk.brp.model.bijhouding.RegistreerNaamGeslachtAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerNaamGeslachtBericht;
import nl.bzk.brp.model.bijhouding.RegistreerNationaliteitAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerNationaliteitBericht;
import nl.bzk.brp.model.bijhouding.RegistreerOverlijdenAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerOverlijdenBericht;
import nl.bzk.brp.model.bijhouding.RegistreerReisdocumentAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerReisdocumentBericht;
import nl.bzk.brp.model.bijhouding.RegistreerVerhuizingAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerVerhuizingBericht;


/** De Service Endpoint Interface voor bijhoudingen. */
public interface BijhoudingService {

    /**
     * Dit is de webservice methode voor het verwerken van een verhuizings bericht.
     *
     * @param bericht Het bijhoudings bericht wat ontvangen is.
     * @return Het resultaat van de bijhouding.
     */
    RegistreerVerhuizingAntwoordBericht registreerVerhuizing(RegistreerVerhuizingBericht bericht);

    /**
     * Dit is de webservice methode voor het verwerken van een verhuizings bericht.
     *
     * @param bericht Het bijhoudings bericht wat ontvangen is.
     * @return Het resultaat van de bijhouding.
     */
    CorrigeerAdresAntwoordBericht corrigeerAdres(CorrigeerAdresBericht bericht);

    /**
     * Dit is de webservice methode voor het verwerken van een inschrijving geboorte bericht.
     *
     * @param bericht Het bijhoudings bericht wat ontvangen is.
     * @return Het resultaat van de bijhouding.
     */
    RegistreerGeboorteAntwoordBericht registreerGeboorte(RegistreerGeboorteBericht bericht);

    /**
     * Dit is de webservice methode voor het verwerken van een inschrijving geboorte bericht.
     *
     * @param bericht Het bijhoudings bericht wat ontvangen is.
     * @return Het resultaat van de bijhouding.
     */
    RegistreerGBAGeboorteAntwoordBericht registreerGBAGeboorte(RegistreerGBAGeboorteBericht bericht);

    /**
     * Dit is de webservice methode voor het verwerken van een registratie adoptie bericht.
     *
     * @param bericht Het bijhoudings bericht wat ontvangen is.
     * @return Het resultaat van de bijhouding.
     */
    RegistreerAdoptieAntwoordBericht registreerAdoptie(RegistreerAdoptieBericht bericht);

    /**
     * Dit is de webservice methode voor het verwerken van een actualiseren van afstamming bericht.
     *
     * @param bericht Het bijhoudings bericht wat ontvangen is.
     * @return Het resultaat van de bijhouding.
     */
    ActualiseerAfstammingAntwoordBericht actualiseerAfstamming(ActualiseerAfstammingBericht bericht);

    /**
     * Dit is de webservice methode voor het verwerken van huwelijk en geregistreerd partnerschap.
     *
     * @param bericht het bijhoudings bericht wat ontvangen is.
     * @return Het resultaat van de bijhouding
     */
    RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht registreerHuwelijkPartnerschap(
            RegistreerHuwelijkGeregistreerdPartnerschapBericht bericht);

    /**
     * Dit is de webservice methode voor het verwerken van huwelijk en geregistreerd partnerschap.
     *
     * @param bericht het bijhoudings bericht wat ontvangen is.
     * @return Het resultaat van de bijhouding
     */
    RegistreerGBAHuwelijkGeregistreerdPartnerschapAntwoordBericht registreerGBAHuwelijkGeregistreerdPartnerschap(
        RegistreerGBAHuwelijkGeregistreerdPartnerschapBericht bericht);

    /**
     * Dit is de webservice methode voor het verwerken van overlijden.
     *
     * @param bericht het bijhoudings bericht wat ontvangen is.
     * @return Het resultaat van de bijhouding
     */
    RegistreerOverlijdenAntwoordBericht registreerOverlijden(RegistreerOverlijdenBericht bericht);

    /**
     * Dit is de webservice methode voor het verwerken van erkenningen.
     * @param bericht het bijhoudings bericht wat ontvangen is.
     * @return Het resultaat van de bijhouding.
     */
    RegistreerErkenningAntwoordBericht registreerErkenning(RegistreerErkenningBericht bericht);

    /**
     * Dit is de webservice methode voor het verwerken van naam en geslacht.
     * @param bericht het bijhoudingsbericht dat ontvangen is
     * @return Het resultaat van de bijhouding
     */
    RegistreerNaamGeslachtAntwoordBericht registreerNaamGeslacht(RegistreerNaamGeslachtBericht bericht);

    /**
     * Dit is de webservice methode voor het registreren van nationaliteiten.
     * @param bericht het bijhoudingsbericht dat ontvangen is
     * @return Het resultaat van de bijhouding
     */
    RegistreerNationaliteitAntwoordBericht registreerNationaliteit(RegistreerNationaliteitBericht bericht);

    /**
     * Dit is de webservice methode voor het registreren van reisdocumenten.
     * @param bericht het bijhoudingsbericht dat ontvangen is
     * @return Het resultaat van de bijhouding
     */
    RegistreerReisdocumentAntwoordBericht registreerReisdocument(RegistreerReisdocumentBericht bericht);

    /**
     * Dit is de webservice methode voor het registreren van document / verzoek / mededeling.
     * @param bericht het bijhoudingsbericht dat ontvangen is
     * @return Het resultaat van de bijhouding
     */
    RegistreerMededelingVerzoekAntwoordBericht registreerMededelingVerzoek(
            RegistreerMededelingVerzoekBericht bericht);

    /**
     * Dit is de webservice methode voor het registreren van kiesrecht.
     * @param bericht het bijhoudingsbericht dat ontvangen is
     * @return Het resultaat van de bijhouding
     */
    RegistreerKiesrechtAntwoordBericht registreerKiesrecht(RegistreerKiesrechtBericht bericht);

}
