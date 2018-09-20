/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.bedrijfsregels;

import java.util.List;

import nl.bzk.brp.pocmotor.model.RootObject;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortBetrokkenheid;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Betrokkenheid;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Persoon;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Relatie;

/**
 * Bedrijfsregel die controleert of een van beide ouders van de kind/nieuwgeborene in Nederland is geboren en deze zelf
 * niet de Nederlandse nationaliteit bezit.
 */
public class OpaOmaArtikel implements BedrijfsRegel<Relatie> {

    /**
     * {@inheritDoc}
     */
    @Override
    public BedrijfsRegelFout executeer(final Relatie nieuweSituatie, final List<RootObject> huidigeSituatie) {
        BedrijfsRegelFout fout = null;
        
        for (Betrokkenheid betrokkenheid : nieuweSituatie.getBetrokkenheden()) {
            if (betrokkenheid.getIdentiteit().getRol() == SoortBetrokkenheid.OUDER) {
                Persoon ouder = getOuderUitHuidigeSituatie(betrokkenheid.getIdentiteit().getBetrokkene(), huidigeSituatie);
                if (ouder != null && ouder.getGeboorte().getLandGeboorte().getIdentiteit().getNaam().getWaarde().equals("Nederland") &&
                        !ouder.getNationaliteiten().iterator().next().getIdentiteit().getNationaliteit().getIdentiteit().getNaam().getWaarde().equals("Nederlandse"))
                {
                    fout = new BedrijfsRegelFout("OPA_OMA_ARTIKEL", BedrijfsRegelFoutErnst.WAARSCHUWING,
                            "Wel geboren in Nederland, maar niet de Nederlandse nationaliteit.");
                    break;
                }
            }
        }
        return fout;
    }

    /**
     * Haalt de juiste ouder, behorende bij de opgegeven betrokkene, op uit de lijst van {@link RootObject RootObjecten}
     * die de huidige situatie beschrijven. Hierbij is de opgegeven betrokkene een persoon uit het bericht en geeft
     * deze methode dus de huidige toestand van deze persoon terug zoals op het huidige moment bekend is binnen het 
     * systeem.
     * 
     * @param betrokkene de persoon, zoals in het bericht en dus de nieuwe situatie voorkomt, maar waarvoor de
     *     huidige toestand moet worden geretourneerd. 
     * @param huidigeRootObjects de lijst van {@link RootObject RootObjecten} die de huidige toestand bevatten van aan
     *     het bericht gerelateerde objecten.
     * @return de huidige situatie van de opgevraagde betrokkene.
     */
    private Persoon getOuderUitHuidigeSituatie(final Persoon betrokkene, final List<RootObject> huidigeRootObjects) {
        // TODO: Deze methode zal generieker moeten worden getrokken: het ophalen van de juiste huidige stand op basis van en RootObject uit het bericht.
        if (betrokkene.getIdentificatienummers() != null && betrokkene.getIdentificatienummers().getBurgerservicenummer() != null) {
            for (RootObject rootObject : huidigeRootObjects) {
                if (rootObject instanceof Persoon && betrokkene.getIdentificatienummers().getBurgerservicenummer().getWaarde()
                                                               .equals(((Persoon) rootObject).getIdentificatienummers().getBurgerservicenummer().getWaarde())) {
                    return (Persoon) rootObject;
                }
            }
        }

        return null;
    }

}
