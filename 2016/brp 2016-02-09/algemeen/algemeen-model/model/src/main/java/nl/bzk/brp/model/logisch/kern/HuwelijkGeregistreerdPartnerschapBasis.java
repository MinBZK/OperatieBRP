/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * De op een gegeven moment tussen twee personen aangegane wettelijk geregelde samenlevingsvorm.
 *
 * Naast het huwelijk beschouwt de Wet BRP ook het geregistreerd partnerschap als te administreren algemene gegevens
 * over de burgerlijke staat. Twee personen die trouwen of een geregistreerd partnerschap aangaan, deze weer laten
 * beëindigen, en vervolgens weer trouwen of een geregistreerd partnerschap aangaan, kennen hierdoor een TWEEDE
 * Huwelijk/Geregistreerd partnerschap. Van een Huwelijk/Geregistreerd partnerschap worden gegevens over de
 * sluiting/over het aangaan dan wel over een eventuele ontbinding/beëindiging vastgelegd.
 *
 * De ietwat gekunstelde definities van Relatie enerzijds, en Huwelijk/Geregistreerd partnerschap en Huwelijk en
 * Geregistreerd partnerschap anderzijds, komen voort uit de volgende overwegingen: - uit definitie en toelichting moet
 * éénduidig blijken wanneer er sprake is van één of van meerdere exemplaren. In geval van Huwelijk en Geregistreerd
 * partnerschap is een hertrouwen van twee dezelfde personen een NIEUW Huwelijk (het opnieuw aangaan van Geregistreerd
 * partnerschap is óók een tweede exemplaar, en een omzetting van Huwelijk in Geregistreerd partnerschap of vice versa
 * betekent OOK een tweede exemplaar van de Relatie). - De naam Huwelijk en Geregistreerd partnerschap zijn ambigu: ze
 * kunnen slaan op hetzij de 'gebeurtenis rondom het aangaan van', OF op de 'huwelijkse staat' c.q. het HEBBEN van een
 * bepaalde samenlevingsvorm. - Het geheel aan definitie en toelichting van de objecttypen Relatie, het subtype
 * Huwelijk/Geregistreerd partnerschap, en de 'sub-subtype' Huwelijk en Geregistreerd partnerschap moeten gezamenlijk
 * alle uitsluitsel geven over of we het over 'één' of 'meer' exemplaren hebben.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface HuwelijkGeregistreerdPartnerschapBasis extends Relatie, BrpObject {

}
