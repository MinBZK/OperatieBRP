/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschap;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschapStandaardGroep;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractHuwelijkGeregistreerdPartnerschapModel;


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
@Access(value = AccessType.FIELD)
public abstract class HuwelijkGeregistreerdPartnerschapModel extends AbstractHuwelijkGeregistreerdPartnerschapModel
        implements HuwelijkGeregistreerdPartnerschap
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected HuwelijkGeregistreerdPartnerschapModel() {
        super();
    }

    /**
     * Constructor met de soort relatie.
     *
     * @param soortRelatie de soort relatie
     */
    protected HuwelijkGeregistreerdPartnerschapModel(final SoortRelatie soortRelatie) {
        super(soortRelatie);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param huwelijkGeregistreerdPartnerschap
     *         Te kopieren object type.
     */
    public HuwelijkGeregistreerdPartnerschapModel(
            final HuwelijkGeregistreerdPartnerschap huwelijkGeregistreerdPartnerschap)
    {
        super(huwelijkGeregistreerdPartnerschap);
    }

    @Transient
    @Override
    public boolean isPartnerschapVoltrokkenInNederland() {
        boolean retval = false;
        if (getStandaard().getLandAanvang() != null) {
            retval = BrpConstanten.NL_LAND_CODE.equals(getStandaard().getLandAanvang().getCode());
        }
        return retval;
    }

    /**
     * Retourneert partner betrokkenheden in deze relatie.
     * @return Partner betrokkenheden.
     */
    @Transient
    public PartnerModel getPartnerBetrokkenheid() {
        for (BetrokkenheidModel betrokkenheid : getBetrokkenheden()) {
            if (betrokkenheid instanceof PartnerModel) {
                return (PartnerModel) betrokkenheid;
            }
        }
        return null;
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     * @param betr Betrokkenheid.
     */
    protected void voegPartnerBetrokkenheidToe(final PartnerModel betr) { }

    /**
     * Vervang groepen van een persoon met nieuwe groepen.
     *
     * @param groepen een lijst met de nieuwe groepen
     */
    public void vervangGroepen(final Groep... groepen) {
        if (groepen != null) {
            for (Groep groep : groepen) {

                if (groep instanceof HuwelijkGeregistreerdPartnerschapStandaardGroep) {
                    setStandaard(
                            new HuwelijkGeregistreerdPartnerschapStandaardGroepModel(
                                    (HuwelijkGeregistreerdPartnerschapStandaardGroep) groep));
                    setHuwelijkGeregistreerdPartnerschapStatusHis(StatusHistorie.A);
                } else {
                    // adres heeft alleen maar 1 groep. Dus eigenlijk hoeven we
                    // geen list mee te geven.
                    throw new IllegalArgumentException("Groep van type "
                            + groep.getClass().getName()
                            + " wordt hier niet ondersteund");
                }
            }
        }
    }

    /**
     * Kopie methode van interface naar model.
     *
     * @param hgp het hgp object
     * @return in instantie van het model
     */
    public static HuwelijkGeregistreerdPartnerschapModel kopieerNaarModel(
            final HuwelijkGeregistreerdPartnerschap hgp)
    {
        if (hgp.getSoort() == SoortRelatie.HUWELIJK) {
            return new HuwelijkModel();
        } else if (hgp.getSoort() == SoortRelatie.GEREGISTREERD_PARTNERSCHAP) {
            return new GeregistreerdPartnerschapModel();
        } else {
            throw new IllegalStateException("Ongeldige relatie soort: " + hgp.getSoort());
        }
    }

}
