/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschap;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschapBasis;

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
@MappedSuperclass
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
public abstract class AbstractHuwelijkGeregistreerdPartnerschapModel extends RelatieModel implements HuwelijkGeregistreerdPartnerschapBasis {

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHuwelijkGeregistreerdPartnerschapModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Huwelijk/Geregistreerd partnerschap.
     */
    public AbstractHuwelijkGeregistreerdPartnerschapModel(final SoortRelatieAttribuut soort) {
        super(soort);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param huwelijkGeregistreerdPartnerschap Te kopieren object type.
     */
    public AbstractHuwelijkGeregistreerdPartnerschapModel(final HuwelijkGeregistreerdPartnerschap huwelijkGeregistreerdPartnerschap) {
        super(huwelijkGeregistreerdPartnerschap);

    }

    /**
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        return groepen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van het object.
     */
    public List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        return attributen;
    }

}
