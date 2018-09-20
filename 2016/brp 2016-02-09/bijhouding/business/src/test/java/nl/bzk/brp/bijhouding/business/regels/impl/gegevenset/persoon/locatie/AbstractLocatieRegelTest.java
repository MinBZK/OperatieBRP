/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie;

import java.util.ArrayList;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegioAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestGemeenteBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestLandGebiedBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPlaatsBuilder;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.GeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;

/**
 * Util superklasse voor enkele veel voorkomende zaken in locatie tests.
 */
public abstract class AbstractLocatieRegelTest {

    //TODO: betere algehele coverage mbt verschillende groepen / personen / relaties.

    protected PersoonBericht getPersoonGeboorteNederland(final Gemeente gemeente, final NaamEnumeratiewaardeAttribuut woonplaats) {
        return getPersoonGeboorte(getNederland(), gemeente, woonplaats, null, null, null);
    }

    protected PersoonBericht getPersoonGeboorteBuitenland(final BuitenlandsePlaatsAttribuut buitenlandsePlaats,
                                                          final BuitenlandseRegioAttribuut buitenlandseRegio,
                                                          final LocatieomschrijvingAttribuut locatieOmschrijving)
    {
        return getPersoonGeboorte(getBuitenland(), null, null,
                buitenlandsePlaats, buitenlandseRegio, locatieOmschrijving);
    }

    protected PersoonBericht getPersoonGeboorte(final LandGebied land, final Gemeente gemeente,
                                                final NaamEnumeratiewaardeAttribuut woonplaats,
                                                final BuitenlandsePlaatsAttribuut buitenlandsePlaats,
                                                final BuitenlandseRegioAttribuut buitenlandseRegio,
                                                final LocatieomschrijvingAttribuut locatieOmschrijving)
    {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();
        if (land != null) {
            geboorte.setLandGebiedGeboorte(new LandGebiedAttribuut(land));
        }
        if (gemeente != null) {
            geboorte.setGemeenteGeboorte(new GemeenteAttribuut(gemeente));
        }
        if (woonplaats != null) {
            geboorte.setWoonplaatsnaamGeboorte(woonplaats);
        }
        if (buitenlandsePlaats != null) {
            geboorte.setBuitenlandsePlaatsGeboorte(buitenlandsePlaats);
        }
        if (buitenlandseRegio != null) {
            geboorte.setBuitenlandseRegioGeboorte(buitenlandseRegio);
        }
        if (locatieOmschrijving != null) {
            geboorte.setOmschrijvingLocatieGeboorte(locatieOmschrijving);
        }
        if (geboorte != null) {
            persoonBericht.setGeboorte(geboorte);
        }
        return persoonBericht;
    }

    protected GeregistreerdPartnerschapBericht getGeregistreerdPartnerschap(final DatumEvtDeelsOnbekendAttribuut datumAanvang,
                                                                            final LandGebied landAanvang,
                                                                            final BuitenlandsePlaatsAttribuut buitenlandsePlaatsAanvang,
                                                                            final LandGebied landEinde,
                                                                            final BuitenlandsePlaatsAttribuut buitenlandsePlaatsEinde,
                                                                            final DatumEvtDeelsOnbekendAttribuut datumEinde,
                                                                            final PersoonBericht persoon)
    {
        final GeregistreerdPartnerschapBericht regPartner = new GeregistreerdPartnerschapBericht();
        regPartner.setStandaard(new RelatieStandaardGroepBericht());
        regPartner.getStandaard().setDatumAanvang(datumAanvang);
        regPartner.getStandaard().setBuitenlandsePlaatsAanvang(buitenlandsePlaatsAanvang);
        regPartner.getStandaard().setLandGebiedAanvang(new LandGebiedAttribuut(landAanvang));
        regPartner.getStandaard().setBuitenlandsePlaatsEinde(buitenlandsePlaatsEinde);
        regPartner.getStandaard().setLandGebiedEinde(new LandGebiedAttribuut(landEinde));
        regPartner.getStandaard().setDatumEinde(datumEinde);

        final PartnerBericht partner = new PartnerBericht();
        partner.setPersoon(persoon);

        regPartner.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        regPartner.getBetrokkenheden().add(partner);

        return regPartner;
    }

    protected LandGebied getNederland() {
        return TestLandGebiedBuilder.maker()
            .metCode(LandGebiedCodeAttribuut.NEDERLAND).maak();
    }

    protected LandGebied getBuitenland() {
        return TestLandGebiedBuilder.maker()
            .metCode(new LandGebiedCodeAttribuut((short) (LandGebiedCodeAttribuut.NL_LAND_CODE_SHORT + 1))).maak();
    }

    protected Gemeente getGemeente() {
        return TestGemeenteBuilder.maker().metCode(1234).maak();
    }

    protected Plaats getWoonplaats() {
        return TestPlaatsBuilder.maker().metCode(1234).metNaam("test").maak();
    }

    protected BuitenlandsePlaatsAttribuut getBuitenlandsePlaats() {
        return new BuitenlandsePlaatsAttribuut("Test buitenlandse plaats");
    }

    protected BuitenlandseRegioAttribuut getBuitenlandseRegio() {
        return new BuitenlandseRegioAttribuut("Test buitenlandse regio");
    }

    protected LocatieomschrijvingAttribuut getLocatieOmschrijving() {
        return new LocatieomschrijvingAttribuut("Test locatie omschrijving");
    }
}
