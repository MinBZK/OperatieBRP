/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import java.math.BigInteger;

import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb02Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdellijkeTitelPredicaatType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AkteGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GeboorteGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GeldigheidGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GeslachtGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GeslachtsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.IdentificatienummersGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.NaamGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieOntbindingGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieSluitingGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieSoortGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SoortRelatieType;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;

/**
 * Bevat algemene methoden om berichten te vertalen.
 *
 * @param <T>
 *            output bericht
 * @param <U>
 *            input bericht
 */
abstract class AbstractBerichtVerwerker<T, U> implements BerichtVerwerker<T, U> {

    /**
     * Maak persoonType voor bericht.
     * @param input orginele bericht
     * @param categorie categorie waaruit persoon komt
     * @return gevuld persoon
     */
    protected PersoonType maakPersoonType(final Tb02Bericht input, final Lo3CategorieEnum categorie) {
        final PersoonType persoonType = new PersoonType();
        persoonType.setIdentificatienummers(maakGroep01(input, categorie));
        persoonType.setNaam(maakGroep02(input, categorie));
        persoonType.setGeboorte(maakGroep03(input, categorie));
        persoonType.setGeslacht(maakGroep04(input, categorie));
        return persoonType;
    }

    /**
     * maakt groep 01.
     * @param input orginele bericht
     * @param categorie categorie van de groep
     * @return gevuld type
     */
    protected IdentificatienummersGroepType maakGroep01(final Tb02Bericht input, final Lo3CategorieEnum categorie) {
        final String element0110 = input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_0110);
        final String element0120 = input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_0120);
        if (element0110 == null && element0120 == null) {
            return null;
        } else {
            final IdentificatienummersGroepType identificatienummersGroepType = new IdentificatienummersGroepType();
            identificatienummersGroepType.setANummer(input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_0110));
            identificatienummersGroepType.setBurgerservicenummer(input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_0120));
            return identificatienummersGroepType;
        }
    }

    /**
     * maakt groep 02.
     * @param input orginele bericht
     * @param categorie categorie van de groep
     * @return gevuld type
     */
    protected NaamGroepType maakGroep02(final Tb02Bericht input, final Lo3CategorieEnum categorie) {
        final NaamGroepType naamGroepType = new NaamGroepType();
        naamGroepType.setVoornamen(input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_0210));
        final String adellijkeTitel = input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_0220);
        if (adellijkeTitel != null) {
            naamGroepType.setAdellijkeTitelPredicaat(AdellijkeTitelPredicaatType.fromValue(adellijkeTitel));
        }
        naamGroepType.setVoorvoegsel(input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_0230));
        naamGroepType.setGeslachtsnaam(input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_0240));
        return naamGroepType;
    }

    /**
     * maakt groep 03.
     * @param input orginele bericht
     * @param categorie categorie van de groep
     * @return gevuld type
     */
    protected GeboorteGroepType maakGroep03(final Tb02Bericht input, final Lo3CategorieEnum categorie) {
        final GeboorteGroepType geboorteGroepType = new GeboorteGroepType();
        geboorteGroepType.setDatum(new BigInteger(input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_0310)));
        geboorteGroepType.setPlaats(input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_0320));
        geboorteGroepType.setLand(input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_0330));
        return geboorteGroepType;
    }

    /**
     * maakt groep 04.
     * @param input orginele bericht
     * @param categorie categorie van de groep
     * @return gevuld type
     */
    protected GeslachtGroepType maakGroep04(final Tb02Bericht input, final Lo3CategorieEnum categorie) {
        final GeslachtGroepType geslachtGroepType = new GeslachtGroepType();
        geslachtGroepType.setGeslachtsaanduiding(GeslachtsaanduidingType.fromValue(input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_0410)));
        return geslachtGroepType;
    }

    /**
     * maakt groep 06.
     * @param input orginele bericht
     * @param categorie categorie van de groep
     * @return gevuld type
     */
    protected RelatieSluitingGroepType maakGroep06(final Tb02Bericht input, final Lo3CategorieEnum categorie) {
        final RelatieSluitingGroepType sluitingGroepType = new RelatieSluitingGroepType();
        sluitingGroepType.setDatum(new BigInteger(input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_0610)));
        sluitingGroepType.setPlaats(input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_0620));
        sluitingGroepType.setLand(input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_0630));
        return sluitingGroepType;
    }

    /**
     * maakt groep 07.
     * @param input orginele bericht
     * @param categorie categorie van de groep
     * @return gevuld type
     */
    protected RelatieOntbindingGroepType maakGroep07(final Tb02Bericht input, final Lo3CategorieEnum categorie) {
        final RelatieOntbindingGroepType relatieOntbindingGroepType = new RelatieOntbindingGroepType();
        relatieOntbindingGroepType.setDatum(new BigInteger(input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_0710)));
        relatieOntbindingGroepType.setPlaats(input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_0720));
        relatieOntbindingGroepType.setLand(input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_0730));
        relatieOntbindingGroepType.setReden(input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_0740));
        return relatieOntbindingGroepType;
    }

    /**
     * maakt groep 15.
     * @param input orginele bericht
     * @param categorie categorie van de groep
     * @return gevuld type
     */
    protected RelatieSoortGroepType maakGroep15(final Tb02Bericht input, final Lo3CategorieEnum categorie) {
        final RelatieSoortGroepType relatieSoortGroepType = new RelatieSoortGroepType();
        relatieSoortGroepType.setSoort(SoortRelatieType.fromValue(input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_1510)));
        return relatieSoortGroepType;
    }

    /**
     * maakt groep 81.
     * @param input orginele bericht
     * @param categorie categorie van de groep
     * @return gevuld type
     */
    protected AkteGroepType maakGroep81(final Tb02Bericht input, final Lo3CategorieEnum categorie) {
        final AkteGroepType akte = new AkteGroepType();
        akte.setRegistergemeente(input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_8110));
        akte.setAktenummer(input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_8120));
        return akte;
    }

    /**
     * maakt groep 85.
     * @param input orginele bericht
     * @param categorie categorie van de groep
     * @return gevuld type
     */
    protected GeldigheidGroepType maakGroep85(final Tb02Bericht input, final Lo3CategorieEnum categorie) {
        final GeldigheidGroepType geldigheidGroepType = new GeldigheidGroepType();
        geldigheidGroepType.setDatumIngang(new BigInteger(input.getWaarde(categorie, Lo3ElementEnum.ELEMENT_8510)));
        return geldigheidGroepType;
    }
}
