/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.bericht;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.business.bedrijfsregels.BerichtBedrijfsRegel;
import nl.bzk.brp.business.dto.bijhouding.HuwelijkEnGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortMelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementatie van bedrijfsregel BRBY0411: De wijziging van wijze gebruik naam (aka. aanschrijving) moet van personen
 * die in het eerdere actie ook de partnerschap samengaat.
 * <p/>
 * BRBY04011:  Naamgebruik of wijziging naam voor H/P partner
 * <p/>
 * Doel: Consistentie gegevens in het bericht. BSN (of A-nummer) van persoon in identificatienummers moet gelijk zijn
 * aan BSN van een van beoogde partners in de groep "betrokkenheid.
 *
 *
 * LET OP !!!, we gaan vanuit dat hier allemaal bsn identificeerbare personen gaan !!!
 *
 * @brp.bedrijfsregel BRBY04011
 */
public class BRBY04011 implements BerichtBedrijfsRegel<HuwelijkEnGeregistreerdPartnerschapBericht> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BerichtBedrijfsRegel.class);

    @Override
    public String getCode() {
        return "BRBY04011";
    }

    /**
     * return welk foutmelding gegenereerd moet worden.
     * @return de foutmelding.
     */
    protected MeldingCode getFoutMeldingCode() {
        return MeldingCode.BRBY04011;
    }

    /**
     * Bedrijfsregel controleert of de acties in het bericht allen over dezelfde personen gaat.
     *
     * @param bericht het bericht waarvoor de bedrijfsregel moet worden uitgevoerd.
     * @return een lijst met eventuele meldingen indien het bericht niet voldoet aan de bedrijfsregel.
     */
    @Override
    public List<Melding> executeer(final HuwelijkEnGeregistreerdPartnerschapBericht bericht) {
        List<Melding> meldingen = new ArrayList<Melding>();

        if (bericht != null && bericht.getBrpActies() != null && bericht.getBrpActies().size() > 1) {
            List<Persoon> hoofdPersonen = bepaalHoofdPersonen(bericht.getBrpActies());

            if (CollectionUtils.isNotEmpty(hoofdPersonen)) {
                // test nu of alle gevonden personen in de volgende acties hieraan voldoet.
                for (int i = 1; i < bericht.getBrpActies().size(); i++) {
                    List<Persoon> bijPersonen = bepaalPersonenUitSubActie(bericht.getBrpActies().get(i));
                    checkPersonenInHoofdpersonen(hoofdPersonen, bijPersonen, meldingen);
                }
            } else {
                LOGGER.warn(String.format("Kan (hoofd)personen uit (hoofd)actie niet bepalen. Bedrijfsregel %s "
                    + "werkt dus niet.", getCode()));
            }
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
     * haal een lijst van de hoofpersonen uit het bericht.
     * @param acties de lijst van actie in het hoofdbericht.
     * @return de lijst van personen
     */
    protected List<Persoon> bepaalHoofdPersonen(final List<Actie> acties) {
        return bepaalPersonenUitActie(bepaalHoofdActie(acties));
    }

    /**
     * Haal de lijst van personen uit de sub actie.
     * @param actie de subactie
     * @return de lijst van personen
     */
    protected List<Persoon> bepaalPersonenUitSubActie(final Actie actie) {
        return bepaalPersonenUitActie(actie);
    }

    /**
     * Test of alle peronen in de lijst van bijpersonen voorkomt in de lijst van de hoofdpersonen.
     * @param hoofdPersonen de lijst van personen uit de hoofdactie
     * @param bijPersonen de lijst van personen uit deze subactie
     * @param meldingen wordt aan de lijst een melding toegevoegd als er iets fout gaat.
     * @return true als alle personen hier in zitten, false anders.
     */
    protected boolean checkPersonenInHoofdpersonen(final List<Persoon> hoofdPersonen, final List<Persoon> bijPersonen,
            final List<Melding> meldingen)
    {
        if (CollectionUtils.isNotEmpty(bijPersonen)) {
            for (Persoon pers : bijPersonen) {
                if (!persoonInLijst(hoofdPersonen, pers)) {
                    // foutmelding.
                    meldingen.add(new Melding(SoortMelding.FOUT, getFoutMeldingCode(),
                        (Identificeerbaar) pers.getIdentificatienummers(), "burgerservicenummer"));
                }
            }
        }
        return true;
    }
    /**
     * Stel samen de lijst van alle personen die gerefereerd wordt binnen een actie. Deze methode gaat vanuit dat de
     * actie een Relatie-georienteerde actie gaat, waarin meerdere personen benoemd zijn.
     * @param actie de actie
     * @return de gevonden personen.
     */
    protected List<Persoon> bepaalPersonenUitActie(final Actie actie) {
        List<Persoon> personen = new ArrayList<Persoon>();
        if (CollectionUtils.isNotEmpty(actie.getRootObjecten())) {
            RootObject hoofdRootObject = actie.getRootObjecten().get(0);
            if (hoofdRootObject instanceof Persoon) {
                personen.add((Persoon) hoofdRootObject);
            } else if (hoofdRootObject instanceof Relatie) {
                Relatie relatie = (Relatie) hoofdRootObject;
                if (relatie.getSoort() == SoortRelatie.HUWELIJK
                    || relatie.getSoort() == SoortRelatie.GEREGISTREERD_PARTNERSCHAP)
                {
                    // deze heeft 2 partners.
                    Set<? extends Betrokkenheid> betrokkenheden = relatie.getPartnerBetrokkenheden();
                    for (Betrokkenheid betr : betrokkenheden) {
                        if (null != betr.getBetrokkene() && !personen.contains(betr.getBetrokkene())) {
                            personen.add(betr.getBetrokkene());
                        }
                    }
                }
            }
        } else {
            LOGGER.error("Geen rootobjecten gevonden in actie. Bedrijfsregel {} daarom niet verder uitgevoerd en geen "
                + "meldingen toegevoegd.", getCode());
        }
        return personen;
    }

    /**
     * .
     * @param bsn1 .
     * @param bsn2 .
     * @return .
     */
    private boolean personenZijnGelijk(final Burgerservicenummer bsn1, final Burgerservicenummer bsn2) {
        return bsn1.equals(bsn2);
    }

    /***
     * .
     * @param persoon .
     * @return .
     */
    private boolean persoonIsCompleet(final Persoon persoon) {
        if (null == persoon || null == persoon.getIdentificatienummers()
                || null == persoon.getIdentificatienummers().getBurgerservicenummer())
        {
            LOGGER.warn("Kan personen uit acties niet vergelijken vanwege ontbrekende identificerende gegevens."
                + " Bedrijfdsregel {}.", getCode());
            return false;
        }
        return true;
    }

    /**
     * .
     * @param personen .
     * @param teTestenPersoon .
     * @return .
     */
    private boolean persoonInLijst(final List<Persoon> personen, final Persoon teTestenPersoon) {
       // check of de persoon compleet is.
        if (persoonIsCompleet(teTestenPersoon)) {
            Burgerservicenummer bsn = teTestenPersoon.getIdentificatienummers().getBurgerservicenummer();
            for (Persoon pers : personen) {
                if (personenZijnGelijk(pers.getIdentificatienummers().getBurgerservicenummer(), bsn)) {
                    return true;
                }
            }
        }
        // else {}
        return false;
    }
}
