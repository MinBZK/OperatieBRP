/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.bericht;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.BerichtBedrijfsRegel;
import nl.bzk.brp.business.dto.bijhouding.CorrectieAdresBericht;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.groep.logisch.PersoonIdentificatienummersGroep;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortMelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementatie van bedrijfsregel BRBY0532: De adrescorrecties in 1 bericht moeten betrekking hebben op dezelfde
 * persoon.
 * <p/>
 * De adrescorrecties in 1 bericht moeten betrekking hebben op dezelfde persoon.
 * <p/>
 * Doel: Bewaken dat bericht enkel wordt gebruikt voor de bijbehorende gebeurtenis.
 *
 * @brp.bedrijfsregel BRBY0532
 */
public class BRBY0532 implements BerichtBedrijfsRegel<CorrectieAdresBericht> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BerichtBedrijfsRegel.class);

    @Override
    public String getCode() {
        return "BRBY0532";
    }

    /**
     * Bedrijfsregel controleert of de acties in het bericht allen over dezelfde personen gaat.
     *
     * @param bericht het bericht waarvoor de bedrijfsregel moet worden uitgevoerd.
     * @return een lijst met eventuele meldingen indien het bericht niet voldoet aan de bedrijfsregel.
     */
    @Override
    public List<Melding> executeer(final CorrectieAdresBericht bericht) {
        boolean anderPersoonGevonden = false;

        if (bericht != null && bericht.getBrpActies() != null && bericht.getBrpActies().size() > 1) {
            Actie hoofdActie = bepaalHoofdActie(bericht.getBrpActies());
            Persoon hoofdPersoon = bepaalPersoonUitActie(hoofdActie);

            if (hoofdPersoon != null) {
                for (Actie actie : bericht.getBrpActies()) {
                    Persoon persoon = bepaalPersoonUitActie(actie);
                    if (persoon != null && !personenZijnGelijk(hoofdPersoon, persoon)) {
                        anderPersoonGevonden = true;
                        break;
                    }
                }
            } else {
                LOGGER.warn(String.format("Kan (hoofd)persoon uit (hoofd)actie niet bepalen. Bedrijfsregel %s zal "
                    + "daarom een melding geven.", getCode()));
                anderPersoonGevonden = true;
            }
        }

        List<Melding> meldingen;
        if (anderPersoonGevonden) {
            meldingen =
                Arrays.asList(new Melding(SoortMelding.FOUT, MeldingCode.BRBY0532, bericht, null));
        } else {
            meldingen = Collections.EMPTY_LIST;
        }
        return meldingen;
    }

    /**
     * Bepaald uit de lijst van acties de hoofdactie. In principe is dit altijd de eerste actie.
     *
     * @param acties de lijst van acties uit een bericht.
     * @return de hoofd actie.
     */
    private Actie bepaalHoofdActie(final List<Actie> acties) {
        return acties.get(0);
    }

    /**
     * Bepaalt de persoon die in een actie bijgehouden wordt.
     *
     * @param actie de actie uit het bericht.
     * @return de persoon die in de actie wordt bijgehouden.
     */
    private Persoon bepaalPersoonUitActie(final Actie actie) {
        Persoon persoon;

        if (actie == null) {
            persoon = null;
            LOGGER.warn("Actie was null en kon daardoor geen bij te houden persoon bepalen.");
        } else if (actie.getRootObjecten() != null && !actie.getRootObjecten().isEmpty()) {
            RootObject hoofdRootObject = actie.getRootObjecten().get(0);
            if (hoofdRootObject instanceof Persoon) {
                persoon = (Persoon) hoofdRootObject;
            } else {
                persoon = null;
                LOGGER.error(String.format("Onverwacht rootobject verwacht. Verwachte persoon, maar kreeg een "
                    + "instantie van %s. Bedrijfsregel daarom niet verder uitgevoerd en geen meldingen toegevoegd.",
                    hoofdRootObject.getClass()));
            }
        } else {
            persoon = null;
            LOGGER.error("Geen rootobjecten gevonden in actie. Bedrijfsregel daarom niet verder uitgevoerd en geen "
                + "meldingen toegevoegd.");
        }
        return persoon;
    }

    /**
     * Controleert of de twee opgegeven personen dezelfde personen zijn (op functioneel vlak), dus of hun
     * identificerende gegevens naar dezelfde persoon wijzen.
     *
     * @param persoon1 de eerste persoon die met de tweede vergeleken moet worden.
     * @param persoon2 de tweede persoon die met de eerste vergeleken moet worden.
     * @return <code>true</code> alleen indien de twee personen (functioneel gezien) dezelfde personen zijn; anders
     *         <code>false</code>.
     */
    private boolean personenZijnGelijk(final Persoon persoon1, final Persoon persoon2) {
        boolean gelijk;

        PersoonIdentificatienummersGroep idnrs1 = persoon1.getIdentificatienummers();
        PersoonIdentificatienummersGroep idnrs2 = persoon2.getIdentificatienummers();

        if (idnrs1 != null && idnrs2 != null && idnrs1.getBurgerservicenummer() != null
            && idnrs2.getBurgerservicenummer() != null)
        {
            gelijk = idnrs1.getBurgerservicenummer().equals(idnrs2.getBurgerservicenummer());
        } else {
            LOGGER.warn(String.format("Kan personen uit acties niet vergelijken vanwege ontbrekende identificerende "
                + "gegevens. Bedrijfsregel %s zal daarom een melding geven.", getCode()));
            gelijk = false;
        }
        return gelijk;
    }

}
