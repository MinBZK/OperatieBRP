/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschap;
import nl.bzk.brp.model.logisch.kern.basis.HuwelijkGeregistreerdPartnerschapBasis;
import nl.bzk.brp.model.operationeel.kern.HuwelijkGeregistreerdPartnerschapStandaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;
import org.hibernate.annotations.Type;


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
@MappedSuperclass
public abstract class AbstractHuwelijkGeregistreerdPartnerschapModel extends RelatieModel implements
        HuwelijkGeregistreerdPartnerschapBasis
{

    @Embedded
    @JsonProperty
    private HuwelijkGeregistreerdPartnerschapStandaardGroepModel standaard;

    @Type(type = "StatusHistorie")
    @Column(name = "HuwelijkGeregistreerdPartner")
    @JsonProperty
    private StatusHistorie                                       huwelijkGeregistreerdPartnerschapStatusHis;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractHuwelijkGeregistreerdPartnerschapModel() {
        this.huwelijkGeregistreerdPartnerschapStatusHis = StatusHistorie.X;

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Huwelijk / Geregistreerd partnerschap.
     */
    public AbstractHuwelijkGeregistreerdPartnerschapModel(final SoortRelatie soort) {
        super(soort);
        this.huwelijkGeregistreerdPartnerschapStatusHis = StatusHistorie.X;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param huwelijkGeregistreerdPartnerschap Te kopieren object type.
     */
    public AbstractHuwelijkGeregistreerdPartnerschapModel(
            final HuwelijkGeregistreerdPartnerschap huwelijkGeregistreerdPartnerschap)
    {
        super(huwelijkGeregistreerdPartnerschap);
        if (huwelijkGeregistreerdPartnerschap.getStandaard() != null) {
            this.standaard =
                new HuwelijkGeregistreerdPartnerschapStandaardGroepModel(
                        huwelijkGeregistreerdPartnerschap.getStandaard());
        }
        this.huwelijkGeregistreerdPartnerschapStatusHis = StatusHistorie.X;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HuwelijkGeregistreerdPartnerschapStandaardGroepModel getStandaard() {
        return standaard;
    }

    /**
     * Retourneert Huwelijk / Geregistreerd partnerschap StatusHis van Huwelijk / Geregistreerd partnerschap.
     *
     * @return Huwelijk / Geregistreerd partnerschap StatusHis.
     */
    public StatusHistorie getHuwelijkGeregistreerdPartnerschapStatusHis() {
        return huwelijkGeregistreerdPartnerschapStatusHis;
    }

    /**
     * Zet Standaard van Huwelijk / Geregistreerd partnerschap. Zet tevens het bijbehorende status his veld op 'A' als
     * het
     * argument niet null is.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final HuwelijkGeregistreerdPartnerschapStandaardGroepModel standaard) {
        this.standaard = standaard;
        if (standaard != null) {
            huwelijkGeregistreerdPartnerschapStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Huwelijk / Geregistreerd partnerschap StatusHis van Huwelijk / Geregistreerd partnerschap.
     *
     * @param huwelijkGeregistreerdPartnerschapStatusHis Huwelijk / Geregistreerd partnerschap StatusHis.
     */
    public void setHuwelijkGeregistreerdPartnerschapStatusHis(
            final StatusHistorie huwelijkGeregistreerdPartnerschapStatusHis)
    {
        this.huwelijkGeregistreerdPartnerschapStatusHis = huwelijkGeregistreerdPartnerschapStatusHis;
    }

}
