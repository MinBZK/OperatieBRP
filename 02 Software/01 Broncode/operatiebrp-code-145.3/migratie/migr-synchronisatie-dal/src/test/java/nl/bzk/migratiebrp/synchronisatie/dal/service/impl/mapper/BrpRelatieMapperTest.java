/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.StapelVoorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;
import org.junit.Test;

public class BrpRelatieMapperTest extends BrpAbstractTest {
    private static final AdministratieveHandeling ADMINISTRATIEVE_HANDELING = new AdministratieveHandeling(
            new Partij("Bierum", "000008"),
            SoortAdministratieveHandeling.GBA_INITIELE_VULLING, new Timestamp(System.currentTimeMillis()));

    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<>());

    @Inject
    private BrpRelatieMapper.BrpRelatieInhoudMapper brpRelatieInhoudMapper;
    @Inject
    private BrpRelatieMapper brpRelatieMapper;

    @Test
    public void testMapInhoud() {
        final RelatieHistorie historie = maakRelatieHistorie();

        final BrpRelatieInhoud result = brpRelatieInhoudMapper.mapInhoud(historie, brpOnderzoekMapper);

        assertNotNull(result);
        assertEquals(new BrpDatum(20120101, null), result.getDatumAanvang());
        assertEquals(new BrpGemeenteCode("0244"), result.getGemeenteCodeAanvang());
        assertEquals(new BrpString("Leiden", null), result.getWoonplaatsnaamAanvang());
        assertEquals(new BrpString("Liverpool", null), result.getBuitenlandsePlaatsAanvang());
        assertEquals(new BrpString("Het Noorden", null), result.getBuitenlandseRegioAanvang());
        assertEquals(new BrpLandOfGebiedCode("6030"), result.getLandOfGebiedCodeAanvang());
        assertEquals(new BrpString("Net buiten het stadion", null), result.getOmschrijvingLocatieAanvang());
        assertEquals(new BrpRedenEindeRelatieCode('S'), result.getRedenEindeRelatieCode());
        assertEquals(new BrpDatum(20150304, null), result.getDatumEinde());
        assertEquals(new BrpGemeenteCode("0377"), result.getGemeenteCodeEinde());
        assertEquals(new BrpString("'s-Gravendeel", null), result.getWoonplaatsnaamEinde());
        assertEquals(new BrpString("Bainsbury", null), result.getBuitenlandsePlaatsEinde());
        assertEquals(new BrpString("Zuidelijk", null), result.getBuitenlandseRegioEinde());
        assertEquals(new BrpLandOfGebiedCode("2033"), result.getLandOfGebiedCodeEinde());
        assertEquals(new BrpString("Bij het gemeentehuis", null), result.getOmschrijvingLocatieEinde());
    }

    private RelatieHistorie maakRelatieHistorie() {
        final RelatieHistorie historie = new RelatieHistorie(new Relatie(SoortRelatie.HUWELIJK));
        historie.setDatumAanvang(20120101);
        historie.setLandOfGebiedAanvang(new LandOfGebied("6030", "naam"));
        historie.setGemeenteAanvang(new Gemeente((short) 244, "Leiden", "0244", new Partij("partij", "000244")));
        historie.setWoonplaatsnaamAanvang("Leiden");
        historie.setBuitenlandsePlaatsAanvang("Liverpool");
        historie.setBuitenlandseRegioAanvang("Het Noorden");
        historie.setOmschrijvingLocatieAanvang("Net buiten het stadion");
        historie.setRedenBeeindigingRelatie(new RedenBeeindigingRelatie('S', "S"));
        historie.setDatumEinde(20150304);
        historie.setGemeenteEinde(new Gemeente((short) 377, "'s-Gravendeel", "0377", new Partij("partij2", "000377")));
        historie.setWoonplaatsnaamEinde("'s-Gravendeel");
        historie.setBuitenlandsePlaatsEinde("Bainsbury");
        historie.setBuitenlandseRegioEinde("Zuidelijk");
        historie.setLandOfGebiedEinde(new LandOfGebied("2033", "land"));
        historie.setOmschrijvingLocatieEinde("Bij het gemeentehuis");
        return historie;
    }

    /**
     * Deze test test de vulling van de IST. Alle IST elementen zullen ingevuld worden. Dit is niet zoals het in
     * productie gebeurd.
     */
    @Test
    public void testMapIstRelatie() {
        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        final Betrokkenheid ikBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        ikBetrokkenheid.setId((long) 1);
        final Betrokkenheid partner = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        partner.setId((long) 2);

        final RelatieHistorie historie = maakRelatieHistorie();
        historie.setDatumTijdRegistratie(new Timestamp(System.currentTimeMillis()));

        relatie.addBetrokkenheid(ikBetrokkenheid);
        relatie.addBetrokkenheid(partner);
        relatie.addRelatieHistorie(historie);
        voegStapelsAanRelatieToe(relatie);

        final Set<Betrokkenheid> ikBetrokkenheidSet = new HashSet<>();
        ikBetrokkenheidSet.add(ikBetrokkenheid);

        final List<BrpRelatie> relaties = brpRelatieMapper.map(ikBetrokkenheidSet, brpOnderzoekMapper);
        assertNotNull(relaties);
        assertFalse(relaties.isEmpty());
        assertEquals(1, relaties.size());
        final BrpRelatie brpRelatie = relaties.get(0);
        assertNotNull(brpRelatie);

        final BrpStapel<BrpIstRelatieGroepInhoud> istOuder1Stapel = brpRelatie.getIstOuder1Stapel();
        final BrpStapel<BrpIstRelatieGroepInhoud> istOuder2Stapel = brpRelatie.getIstOuder2Stapel();
        final BrpStapel<BrpIstHuwelijkOfGpGroepInhoud> istHuwelijkOfGpStapel = brpRelatie.getIstHuwelijkOfGpStapel();
        final BrpStapel<BrpIstRelatieGroepInhoud> istKindStapel = brpRelatie.getIstKindStapel();
        final BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> istGezagsverhoudingStapel = brpRelatie.getIstGezagsverhoudingStapel();

        assertNotNull(istOuder1Stapel);
        assertNotNull(istOuder2Stapel);
        assertNotNull(istKindStapel);
        assertNotNull(istHuwelijkOfGpStapel);
        assertNotNull(istGezagsverhoudingStapel);
    }

    private void voegStapelsAanRelatieToe(final Relatie relatie) {
        final Lo3CategorieEnum[] categorieen =
                new Lo3CategorieEnum[]{Lo3CategorieEnum.CATEGORIE_02,
                        Lo3CategorieEnum.CATEGORIE_03,
                        Lo3CategorieEnum.CATEGORIE_05,
                        Lo3CategorieEnum.CATEGORIE_09,
                        Lo3CategorieEnum.CATEGORIE_11};

        for (final Lo3CategorieEnum categorie : categorieen) {
            final Stapel stapel = new Stapel(new Persoon(SoortPersoon.INGESCHREVENE), categorie.getCategorie(), 0);
            final StapelVoorkomen voorkomen = new StapelVoorkomen(stapel, 0, ADMINISTRATIEVE_HANDELING);
            voorkomen.setAnummer("1234567890");
            voorkomen.setBsn("987654321");
            stapel.addStapelVoorkomen(voorkomen);
            stapel.addRelatie(relatie);
            relatie.addStapel(stapel);
        }
    }
}
