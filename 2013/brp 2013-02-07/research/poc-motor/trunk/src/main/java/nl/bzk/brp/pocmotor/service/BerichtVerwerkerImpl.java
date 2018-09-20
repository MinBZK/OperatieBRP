/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.pocmotor.acties.ActieFactory;
import nl.bzk.brp.pocmotor.acties.ActieUitvoerder;
import nl.bzk.brp.pocmotor.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.pocmotor.bedrijfsregels.BedrijfsRegelFout;
import nl.bzk.brp.pocmotor.bedrijfsregels.BedrijfsRegelFoutErnst;
import nl.bzk.brp.pocmotor.dal.logisch.PersoonLGMRepository;
import nl.bzk.brp.pocmotor.model.Bericht;
import nl.bzk.brp.pocmotor.model.RootObject;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortBetrokkenheid;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.BRPActie;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Betrokkenheid;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Persoon;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Relatie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Standaard implementatie van de BerichtVerwerker.
 */
@Service
public class BerichtVerwerkerImpl implements BerichtVerwerker {

    @Inject
    private BedrijfsRegelManager bedrijfsRegelManager;
    @Inject
    private ActieFactory         actieFactory;
    @Inject
    private PersoonLGMRepository persoonLGMRepository;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<BedrijfsRegelFout> verwerkBericht(final Bericht bericht) {
        List<BedrijfsRegelFout> fouten = new ArrayList<BedrijfsRegelFout>();

        for (BRPActie actie : bericht.getBijhoudingen()) {
            List<RootObject> huidigeSituatie = haalHuidigeSituatieOp(actie.getNieuweSituatie());
            List<BedrijfsRegelFout> actieFouten = controleerBerichtTegenBedrijfsRegels(actie, huidigeSituatie);
            fouten.addAll(actieFouten);
            if (!zijnErBlokkerendeFoutenOpgetreden(actieFouten)) {
                voerActieUit(actie, huidigeSituatie);
            }
        }
        return fouten;
    }

    /**
     * Haalt de huidige situatie op (uit de database) van de relevante root objecten. Afhankelijk van het type van
     * het root object van de nieuwe situatie en of deze reeds bestaat, zullen de juiste objecten worden opgehaald en
     * geretourneerd in een lijst.
     * <p>
     * Indien de nieuwe situatie een persoon betreft, zal de huidige situatie van deze persoon, mits aanwezig, worden
     * opgehaald en geretourneerd. Indien de nieuwe situatie een relatie betreft, zullen de personen die betrokken zijn
     * bij de relatie, voor zover reeds bekend, worden opgehaald en in de lijst worden geretourneerd.
     * </p>
     * @param nieuweSituatie de nieuwe situatie van een rootobject welke is opgegeven in het bericht.
     * @return een lijst van voor het bericht en de nieuwe situatie relevante root objecten en dan wel de huidige
     *     (binnen het systeem bekende) situatie van die rootobjecten.
     */
    private List<RootObject> haalHuidigeSituatieOp(final RootObject nieuweSituatie) {
        List<RootObject> huidigeSituatie = new ArrayList<RootObject>();

        if (nieuweSituatie instanceof Persoon) {
            // Indien we te maken hebben met een persoon, dan voor huidige situatie ook die persoon ophalen.
            Persoon nieuwPersoon = (Persoon) nieuweSituatie;
            if (nieuwPersoon.getIdentificatienummers().getBurgerservicenummer() != null) {
                huidigeSituatie.add(persoonLGMRepository.findByIdentificatienummersBurgerservicenummer(nieuwPersoon.getIdentificatienummers().getBurgerservicenummer()));
            }
        } else if (nieuweSituatie instanceof Relatie) {
            // Indien we te maken hebben met een relatie, dan voor huidige situatie de personen uit de relatie ophalen.
            Relatie nieuweRelatie = (Relatie) nieuweSituatie;
            for (Betrokkenheid betrokkenheid : nieuweRelatie.getBetrokkenheden()) {
                if (betrokkenheid.getIdentiteit().getBetrokkene() != null && betrokkenheid.getIdentiteit().getBetrokkene().getIdentificatienummers() != null
                        && betrokkenheid.getIdentiteit().getBetrokkene().getIdentificatienummers().getBurgerservicenummer() != null
                        && betrokkenheid.getIdentiteit().getRol() != SoortBetrokkenheid.KIND) {
                    huidigeSituatie.add(persoonLGMRepository
                            .findByIdentificatienummersBurgerservicenummer(betrokkenheid.getIdentiteit().getBetrokkene().getIdentificatienummers().getBurgerservicenummer()));
                }
            }
        }
        return huidigeSituatie;
    }

    /**
     * Valideert een actie tegen de bedrijfsregels en retourneert een lijst van opgetreden fouten. Hiervoor wordt eerst
     * de nieuwe situatie in de actie bepaald en dan de relevante nieuwe situatie en deze wordt dan aan alle voor de
     * actie relevante bedrijfsregels meegegeven ter validatie.
     * @param actie De actie in het bericht dat moet worden gecontroleerd.
     * @param huidigeSituatie De huidige situatie in de BRP.
     * @return een lijst van opgetreden fouten en waarschuwingen.
     */
    private List<BedrijfsRegelFout> controleerBerichtTegenBedrijfsRegels(final BRPActie actie,
                                                                         final List<RootObject> huidigeSituatie) {
        List<BedrijfsRegelFout> fouten = new ArrayList<BedrijfsRegelFout>();

        for (BedrijfsRegel regel : bedrijfsRegelManager.getLijstVanBedrijfsRegels(actie)) {
            BedrijfsRegelFout fout = regel.executeer(actie.getNieuweSituatie(), huidigeSituatie);
            if (fout != null) {
                fouten.add(fout);
            }
        }
        return fouten;
    }

    /**
     * Methode die controleert of er blokkerende fouten zijn opgetreden door de lijst van opgetreden fouten te doorlopen
     * en te kijken of hier echt fouten in zitten of niet.
     * @param fouten de lijst van opgetreden fouten.
     * @return een boolean die aangeeft of er blokkerende fouten zijn opgetreden of niet.
     */
    private boolean zijnErBlokkerendeFoutenOpgetreden(final List<BedrijfsRegelFout> fouten) {
        boolean resultaat = false;
        for (BedrijfsRegelFout fout : fouten) {
            if (fout.getErnst() == BedrijfsRegelFoutErnst.ZACHTE_FOUT || fout.getErnst() ==
                    BedrijfsRegelFoutErnst.HARDE_FOUT)
            {
                resultaat = true;
                break;
            }
        }
        return resultaat;
    }

    /**
     * Executeert de actie.
     * @param actie de actie die moet worden doorgevoerd.
     * @param huidigeSituatie De huidige situatie zoals in de BRP.
     */
    private void voerActieUit(final BRPActie actie,
                              final List<RootObject> huidigeSituatie) {
        ActieUitvoerder uitvoerder = actieFactory.getActieUitvoerder(actie);
        if (uitvoerder != null) {
            uitvoerder.voerUit(actie, huidigeSituatie);
        }
    }

}
