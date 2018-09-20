/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.util.ArrayList;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.GeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderlijkGezagGroepBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderschapGroepBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;


/**
 * Relatie builder om gemakkelijk relaties te kunnen maken voor unit tests.
 */
public final class RelatieBuilder<T extends RelatieBericht> {

    private T relatie;
    private final String prefix = PrefixBuilder.getPrefix();
    private static final String MESSAGE_FOUT_RELATIE_TYPE = "Relatie is geen huwelijk of geregistreerd partnerschap.";

    public RelatieBuilder() {

    }

    @SuppressWarnings("unchecked")
    public RelatieBuilder<T> bouwHuwelijkRelatie() {
        this.relatie = (T) new HuwelijkBericht();
        this.relatie.setCommunicatieID(this.prefix + "id.huwelijk");
        // relatie.setSoort(SoortRelatie.HUWELIJK);
        ((HuwelijkBericht) this.relatie).setStandaard(new RelatieStandaardGroepBericht());
        this.relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        return this;
    }

    @SuppressWarnings("unchecked")
    public RelatieBuilder<T> bouwGeregistreerdPartnerschap() {
        this.relatie = (T) new GeregistreerdPartnerschapBericht();
        this.relatie.setCommunicatieID(this.prefix + "id.regpart");
        // relatie.setSoort(SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        this.relatie.setStandaard(new RelatieStandaardGroepBericht());
        this.relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        return this;
    }

    public RelatieBuilder<T> setDatumAanvang(final Integer datum) {
        if (!(this.relatie instanceof HuwelijkGeregistreerdPartnerschapBericht)) {
            throw new UnsupportedOperationException(MESSAGE_FOUT_RELATIE_TYPE);
        }
        ((HuwelijkGeregistreerdPartnerschapBericht) this.relatie).getStandaard().setDatumAanvang(
                new DatumEvtDeelsOnbekendAttribuut(datum));
        return this;
    }

    public RelatieBuilder<T> setGemeenteAanvang(final Gemeente gemeenteAanvang) {
        if (!(this.relatie instanceof HuwelijkGeregistreerdPartnerschapBericht)) {
            throw new UnsupportedOperationException(MESSAGE_FOUT_RELATIE_TYPE);
        }
        ((HuwelijkGeregistreerdPartnerschapBericht) this.relatie).getStandaard().setGemeenteAanvang(
                new GemeenteAttribuut(gemeenteAanvang));
        return this;
    }

    public RelatieBuilder<T> setGemeenteAanvangCode(final GemeenteCodeAttribuut partijAanvangCode) {
        if (!(this.relatie instanceof HuwelijkGeregistreerdPartnerschapBericht)) {
            throw new UnsupportedOperationException(MESSAGE_FOUT_RELATIE_TYPE);
        }
        ((HuwelijkGeregistreerdPartnerschapBericht) this.relatie).getStandaard().setGemeenteAanvangCode(
                partijAanvangCode.getWaarde().toString());
        return this;
    }

    public RelatieBuilder<T> setLandGebiedAanvang(final LandGebied landAanvang) {
        if (!(this.relatie instanceof HuwelijkGeregistreerdPartnerschapBericht)) {
            throw new UnsupportedOperationException(MESSAGE_FOUT_RELATIE_TYPE);
        }
        ((HuwelijkGeregistreerdPartnerschapBericht) this.relatie).getStandaard().setLandGebiedAanvang(
                new LandGebiedAttribuut(landAanvang));
        return this;
    }

    public RelatieBuilder<T> setLandGebiedAanvangCode(final LandGebiedCodeAttribuut landAanvangCode) {
        if (!(this.relatie instanceof HuwelijkGeregistreerdPartnerschapBericht)) {
            throw new UnsupportedOperationException(MESSAGE_FOUT_RELATIE_TYPE);
        }
        ((HuwelijkGeregistreerdPartnerschapBericht) this.relatie).getStandaard().setLandGebiedAanvangCode(
                landAanvangCode.getWaarde().toString());
        return this;
    }

    public RelatieBuilder<T> setPlaatsnaamAanvang(final NaamEnumeratiewaardeAttribuut plaatsnaamAanvang) {
        if (!(this.relatie instanceof HuwelijkGeregistreerdPartnerschapBericht)) {
            throw new UnsupportedOperationException(MESSAGE_FOUT_RELATIE_TYPE);
        }
        ((HuwelijkGeregistreerdPartnerschapBericht) this.relatie).getStandaard().setWoonplaatsnaamAanvang(
                plaatsnaamAanvang);
        return this;
    }

    public RelatieBuilder<T> bouwFamilieRechtelijkeBetrekkingRelatie() {
        this.relatie = (T) new FamilierechtelijkeBetrekkingBericht();
        this.relatie.setCommunicatieID(this.prefix + "id.famrecht");
        // relatie.setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        this.relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        return this;
    }

    public RelatieBuilder<T> voegPartnerToe(final PersoonBericht partner) {
        // handel ook null correct af.
        if (partner != null) {
            checkRelatieIsNietNull();
            if (SoortRelatie.FAMILIERECHTELIJKE_BETREKKING == this.relatie.getSoort().getWaarde()) {
                throw new IllegalArgumentException(
                        "Kan geen partner betrokkenheid toevoegen aan familierechtelijke betrekking");
            }
            final PartnerBericht betr = new PartnerBericht();
            this.relatie.setCommunicatieID(partner.getCommunicatieID() + "." + this.prefix);
            // betr.setRol(SoortBetrokkenheid.PARTNER);
            betr.setPersoon(partner);
            betr.setRelatie(this.relatie);
            this.relatie.getBetrokkenheden().add(betr);
        }
        return this;
    }

    public RelatieBuilder<T> voegKindToe(final PersoonBericht kind) {
        // handel ook null correct af.
        if (kind != null) {
            checkRelatieIsNietNull();
            if (!(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING == this.relatie.getSoort().getWaarde())) {
                throw new IllegalArgumentException(
                        "Kind betrokkenheid kan alleen toegevoegd worden aan een familierechtelijke betrekking.");
            }
            final KindBericht betr = new KindBericht();
            this.relatie.setCommunicatieID(this.prefix + "id.kind");
            // betr.setRol(SoortBetrokkenheid.KIND);
            betr.setPersoon(kind);
            betr.setRelatie(this.relatie);
            this.relatie.getBetrokkenheden().add(betr);
        }
        return this;
    }

    public RelatieBuilder<T> voegOuderToe(final PersoonBericht ouder) {
        // handel ook null correct af.
        if (ouder != null) {
            checkRelatieIsNietNull();
            if (!(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING == this.relatie.getSoort().getWaarde())) {
                throw new IllegalArgumentException(
                        "Ouder betrokkenheid kan alleen toegevoegd worden aan een familierechtelijke betrekking.");
            }
            final OuderBericht betr = new OuderBericht();
            this.relatie.setCommunicatieID(ouder.getCommunicatieID() + "." + this.prefix);
            // betr.setRol(SoortBetrokkenheid.OUDER);
            betr.setPersoon(ouder);
            betr.setRelatie(this.relatie);
            betr.setOuderlijkGezag(new OuderOuderlijkGezagGroepBericht());
            betr.setOuderschap(new OuderOuderschapGroepBericht());
            this.relatie.getBetrokkenheden().add(betr);
        }
        return this;
    }

    private void checkRelatieIsNietNull() {
        if (this.relatie == null) {
            throw new IllegalStateException("Relatie is nog niet geinstantieerd, roep eerst bouwXXXRelatie() aan.");
        }
    }

    public T getRelatie() {
        return this.relatie;
    }

    private static final Integer BSN_KIND   = 105889313;
    private static final Integer BSN_VADER  = 105889465;
    private static final Integer BSN_MOEDER = 105889556;

    public static FamilierechtelijkeBetrekkingBericht bouwSimpleFamilie() {
        FamilierechtelijkeBetrekkingBericht relatie = new FamilierechtelijkeBetrekkingBericht();
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        relatie.getBetrokkenheden().add(
                maakBetrokkenheid(SoortBetrokkenheid.KIND, relatie, new BurgerservicenummerAttribuut(BSN_KIND), "kind",
                        "familie"));
        relatie.getBetrokkenheden().add(
                maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new BurgerservicenummerAttribuut(BSN_VADER),
                        "vader", "familie"));
        relatie.getBetrokkenheden().add(
                maakBetrokkenheid(SoortBetrokkenheid.OUDER, relatie, new BurgerservicenummerAttribuut(BSN_MOEDER),
                        "moeder", "familie"));

        return relatie;
    }

    public static BetrokkenheidBericht maakBetrokkenheid(final SoortBetrokkenheid soort, final RelatieBericht relatie,
            final BurgerservicenummerAttribuut bsn, final String voornaam, final String achternaam)
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
        PersoonBericht persoon =
                PersoonBuilder
                        .bouwPersoon(SoortPersoon.INGESCHREVENE, bsn.getWaarde(), null, null, null, null, voornaam,
                                null, achternaam);
        betr.setPersoon(persoon);
        return betr;
    }

}
