/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.logisch.kern.basis.HuwelijkGeregistreerdPartnerschapBasis;


/**
 * De op een gegeven moment tussen twee personen aangegane wettelijk geregelde samenlevingsvorm.
 *
 * Naast het huwelijk beschouwt de Wet BRP ook het geregistreerd partnerschap als te administreren algemene gegevens
 * over de burgerlijke staat. Twee personen die trouwen of een geregistreerd partnerschap aangaan, deze weer laten
 * be�indigen, en vervolgens weer trouwen of een geregistreerd partnerschap aangaan, kennen hierdoor een TWEEDE
 * Huwelijk/Geregistreerd partnerschap. Van een Huwelijk/Geregistreerd partnerschap worden gegevens over de
 * sluiting/over het aangaan dan wel over een eventuele ontbinding/be�indiging vastgelegd.
 *
 * De ietwat gekunstelde definities van Relatie enerzijds, en Huwelijk/Geregistreerd partnerschap en Huwelijk en
 * Geregistreerd partnerschap anderzijds, komen voort uit de volgende overwegingen:
 * - uit definitie en toelichting moet ��nduidig blijken wanneer er sprake is van ��n of van meerdere exemplaren. In
 * geval van Huwelijk en Geregistreerd partnerschap is een hertrouwen van twee dezelfde personen een NIEUW Huwelijk (het
 * opnieuw aangaan van Geregistreerd partnerschap is ��k een tweede exemplaar, en een omzetting van Huwelijk in
 * Geregistreerd partnerschap of vice versa betekent OOK een tweede exemplaar van de Relatie).
 * - De naam Huwelijk en Geregistreerd partnerschap zijn ambigu: ze kunnen slaan op hetzij de 'gebeurtenis rondom het
 * aangaan van', OF op de 'huwelijkse staat' c.q. het HEBBEN van een bepaalde samenlevingsvorm.
 * - Het geheel aan definitie en toelichting van de objecttypen Relatie, het subtype Huwelijk/Geregistreerd
 * partnerschap, en de 'sub-subtype' Huwelijk en Geregistreerd partnerschap moeten gezamenlijk alle uitsluitsel geven
 * over of we het over '��n' of 'meer' exemplaren hebben.
 * RvdP 26 oktober 2012.
 *
 *
 *
 */
public abstract class AbstractHuwelijkGeregistreerdPartnerschapBericht extends RelatieBericht implements
        HuwelijkGeregistreerdPartnerschapBasis
{

    private HuwelijkGeregistreerdPartnerschapStandaardGroepBericht standaard;

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     * @param soort de waarde van het discriminator attribuut
     */
    public AbstractHuwelijkGeregistreerdPartnerschapBericht(final SoortRelatie soort) {
        super(soort);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HuwelijkGeregistreerdPartnerschapStandaardGroepBericht getStandaard() {
        return standaard;
    }

    /**
     * Zet Standaard van Huwelijk / Geregistreerd partnerschap.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final HuwelijkGeregistreerdPartnerschapStandaardGroepBericht standaard) {
        this.standaard = standaard;
    }

}
