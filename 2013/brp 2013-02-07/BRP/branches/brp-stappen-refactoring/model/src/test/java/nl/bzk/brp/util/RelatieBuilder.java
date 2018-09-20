/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.util.ArrayList;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Landcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Woonplaatscode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.GeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderlijkGezagGroepBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderschapGroepBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;


/** Relatie builder om gemakkelijk relaties te kunnen maken voor unit tests. */
public final class RelatieBuilder<T extends RelatieBericht> {

    private T relatie;

    public RelatieBuilder() {

    }

    public RelatieBuilder<T> bouwHuwlijkRelatie() {
        relatie = (T) new HuwelijkBericht();
        //relatie.setSoort(SoortRelatie.HUWELIJK);
        ((HuwelijkBericht) relatie).setStandaard(new HuwelijkGeregistreerdPartnerschapStandaardGroepBericht());
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        return this;
    }

    public RelatieBuilder<T> bouwGeregistreerdPartnerschap() {
        relatie = (T) new GeregistreerdPartnerschapBericht();
        //relatie.setSoort(SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        ((GeregistreerdPartnerschapBericht) relatie)
                .setStandaard(new HuwelijkGeregistreerdPartnerschapStandaardGroepBericht());
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        return this;
    }

    public RelatieBuilder<T> setDatumAanvang(final Integer datum) {
        if (!(relatie instanceof HuwelijkGeregistreerdPartnerschapBericht)) {
            throw new UnsupportedOperationException("Relatie is geen huwelijk of geregistreerd partnerschap.");
        }
        ((HuwelijkGeregistreerdPartnerschapBericht) relatie).getStandaard().setDatumAanvang(new Datum(datum));
        return this;
    }

    public RelatieBuilder<T> setGemeenteAanvang(final Partij partijAanvang) {
        if (!(relatie instanceof HuwelijkGeregistreerdPartnerschapBericht)) {
            throw new UnsupportedOperationException("Relatie is geen huwelijk of geregistreerd partnerschap.");
        }
        ((HuwelijkGeregistreerdPartnerschapBericht) relatie).getStandaard().setGemeenteAanvang(partijAanvang);
        return this;
    }
    public RelatieBuilder<T> setGemeenteAanvangCode(final GemeenteCode partijAanvangCode) {
        if (!(relatie instanceof HuwelijkGeregistreerdPartnerschapBericht)) {
            throw new UnsupportedOperationException("Relatie is geen huwelijk of geregistreerd partnerschap.");
        }
        ((HuwelijkGeregistreerdPartnerschapBericht) relatie).getStandaard().setGemeenteAanvangCode(partijAanvangCode.toString());
        return this;
    }

    public RelatieBuilder<T> setLandAanvang(final Land landAanvang) {
        if (!(relatie instanceof HuwelijkGeregistreerdPartnerschapBericht)) {
            throw new UnsupportedOperationException("Relatie is geen huwelijk of geregistreerd partnerschap.");
        }
        ((HuwelijkGeregistreerdPartnerschapBericht) relatie).getStandaard().setLandAanvang(landAanvang);
        return this;
    }
    public RelatieBuilder<T> setLandAanvangCode(final Landcode landAanvangCode) {
        if (!(relatie instanceof HuwelijkGeregistreerdPartnerschapBericht)) {
            throw new UnsupportedOperationException("Relatie is geen huwelijk of geregistreerd partnerschap.");
        }
        ((HuwelijkGeregistreerdPartnerschapBericht) relatie).getStandaard().setLandAanvangCode(landAanvangCode.toString());
        return this;
    }

    public RelatieBuilder<T> setPlaatsAanvang(final Plaats plaatsAanvang) {
        if (!(relatie instanceof HuwelijkGeregistreerdPartnerschapBericht)) {
            throw new UnsupportedOperationException("Relatie is geen huwelijk of geregistreerd partnerschap.");
        }
        ((HuwelijkGeregistreerdPartnerschapBericht) relatie).getStandaard().setWoonplaatsAanvang(plaatsAanvang);
        return this;
    }

    public RelatieBuilder<T> setPlaatsAanvangCode(final Woonplaatscode plaatsAanvangCode) {
        if (!(relatie instanceof HuwelijkGeregistreerdPartnerschapBericht)) {
            throw new UnsupportedOperationException("Relatie is geen huwelijk of geregistreerd partnerschap.");
        }
        ((HuwelijkGeregistreerdPartnerschapBericht) relatie).getStandaard().setWoonplaatsAanvangCode(plaatsAanvangCode.toString());
        return this;
    }

    public RelatieBuilder<T> bouwFamilieRechtelijkeBetrekkingRelatie() {
        relatie = (T) new FamilierechtelijkeBetrekkingBericht();
        //relatie.setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        return this;
    }

    public RelatieBuilder<T> voegPartnerToe(final PersoonBericht partner) {
        checkRelatieIsNietNull();
        if (SoortRelatie.FAMILIERECHTELIJKE_BETREKKING == relatie.getSoort()) {
            throw new IllegalArgumentException(
                    "Kan geen partner betrokkenheid toevoegen aan familierechtelijke betrekking");
        }
        final PartnerBericht betr = new PartnerBericht();
        //betr.setRol(SoortBetrokkenheid.PARTNER);
        betr.setPersoon(partner);
        betr.setRelatie(relatie);
        relatie.getBetrokkenheden().add(betr);
        return this;
    }

    public RelatieBuilder<T> voegKindToe(final PersoonBericht kind) {
        checkRelatieIsNietNull();
        if (!(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING == relatie.getSoort())) {
            throw new IllegalArgumentException(
                    "Kind betrokkenheid kan alleen toegevoegd worden aan een familierechtelijke betrekking.");
        }
        final KindBericht betr = new KindBericht();
        //betr.setRol(SoortBetrokkenheid.KIND);
        betr.setPersoon(kind);
        betr.setRelatie(relatie);
        relatie.getBetrokkenheden().add(betr);
        return this;
    }

    public RelatieBuilder<T> voegOuderToe(final PersoonBericht ouder) {
        checkRelatieIsNietNull();
        if (!(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING == relatie.getSoort())) {
            throw new IllegalArgumentException(
                    "Ouder betrokkenheid kan alleen toegevoegd worden aan een familierechtelijke betrekking.");
        }
        final OuderBericht betr = new OuderBericht();
        //betr.setRol(SoortBetrokkenheid.OUDER);
        betr.setPersoon(ouder);
        betr.setRelatie(relatie);
        betr.setOuderlijkGezag(new OuderOuderlijkGezagGroepBericht());
        betr.setOuderschap(new OuderOuderschapGroepBericht());
        relatie.getBetrokkenheden().add(betr);
        return this;
    }

    private void checkRelatieIsNietNull() {
        if (relatie == null) {
            throw new IllegalStateException("Relatie is nog niet geinstantieerd, roep eerst bouwXXXRelatie() aan.");
        }
    }

    public T getRelatie() {
        return relatie;
    }


    private static final String BSN_KIND = "105889313";
    private static final String BSN_VADER = "105889465";
    private static final String BSN_MOEDER = "105889556";

    public static FamilierechtelijkeBetrekkingBericht bouwSimpleFamilie() {
        FamilierechtelijkeBetrekkingBericht relatie = new FamilierechtelijkeBetrekkingBericht();
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        relatie.getBetrokkenheden().add(maakBetrokkenheid(
               SoortBetrokkenheid.KIND, relatie, new Burgerservicenummer(BSN_KIND), "kind", "familie"));
        relatie.getBetrokkenheden().add(maakBetrokkenheid(
               SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer(BSN_VADER), "vader", "familie"));
        relatie.getBetrokkenheden().add(maakBetrokkenheid(
               SoortBetrokkenheid.OUDER, relatie, new Burgerservicenummer(BSN_MOEDER), "moeder", "familie"));

        return relatie;
    }

    public static BetrokkenheidBericht maakBetrokkenheid(final SoortBetrokkenheid soort, final RelatieBericht relatie,
        final Burgerservicenummer bsn, final String voornaam, final String achternaam)
    {
        BetrokkenheidBericht betr;

        if (soort == SoortBetrokkenheid.KIND) {
            betr = new KindBericht();
        } else if (soort == SoortBetrokkenheid.OUDER) {
            betr = new OuderBericht();
        } else {
            throw new IllegalArgumentException("Alleen Ouder of Kind");
        }

        betr.setRelatie(relatie);
        String bsnAlsString = null;
        if (bsn != null) {
            bsnAlsString = bsn.toString();
        }
        PersoonBericht persoon = PersoonBuilder.bouwPersoon(bsnAlsString, null, null, null, voornaam, null, achternaam);
        betr.setPersoon(persoon);
        return betr;
    }

}
