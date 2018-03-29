/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.leveringbepaling.filter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Test;


public class PersoonAfnemerindicatieFilterImplTest {

    private MetaObject.Builder persoon = TestBuilders.maakLeegPersoon();

    private PersoonAfnemerindicatieFilterImpl afnemerIndicatieFilter = new PersoonAfnemerindicatieFilterImpl();

    @Test
    public final void dienstAndersDanLeverenAfnemerIndicatie() {

        persoon.metObject(TestBuilders.maakAfnemerindicatie(1, "000999"));
        final Persoonslijst testPersoon = new Persoonslijst(persoon.build(),
                0L);
        final Partij partij = TestPartijBuilder.maakBuilder().metNaam("PartijNaam").metCode("001238").build();
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.SYNCHRONISATIE_PERSOON, 1, partij);
        // act
        final boolean resultaat = afnemerIndicatieFilter.magLeveren(testPersoon, null, autorisatiebundel);

        assertThat(resultaat, is(true));
    }


    @Test
    public final void geenAfnemerIndicatieGevonden() {
        final Persoonslijst testPersoon = new Persoonslijst(persoon.build(),
                0L);
        // arrange
        final Partij partij = TestPartijBuilder.maakBuilder().metNaam("PartijNaam").metCode("001238").build();
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, 123, partij);
        // act
        final boolean resultaat = afnemerIndicatieFilter.magLeveren(testPersoon, null, autorisatiebundel);

        // assert
        assertThat(resultaat, is(false));
    }

    @Test
    public final void welAfnemerIndicatieGevonden() {
        persoon.metObject(TestBuilders.maakAfnemerindicatie(1, "000333"));
        final Persoonslijst testPersoon = new Persoonslijst(
                persoon.build(),
                0L);
        // arrange
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, 1,
                TestPartijBuilder.maakBuilder().metCode("000333").build());
        // act
        final boolean resultaat = afnemerIndicatieFilter.magLeveren(testPersoon, null, autorisatiebundel);

        // assert
        assertThat(resultaat, is(true));
    }

    @Test
    public final void welAfnemerIndicatieGevondenIdMismatch() {
        persoon.metObject(TestBuilders.maakAfnemerindicatie(1, "000333"));
        final Persoonslijst testPersoon = new Persoonslijst(
                persoon.build(),
                0L);
        // arrange
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, 2,
                TestPartijBuilder.maakBuilder().metCode("000333").build());
        // act
        final boolean resultaat = afnemerIndicatieFilter.magLeveren(testPersoon, null, autorisatiebundel);

        // assert
        assertThat(resultaat, is(false));
    }

    @Test
    public final void afnemerIndicatieNietGevondenPartijMismatch() {
        persoon.metObject(TestBuilders.maakAfnemerindicatie(1, "000123"));
        final Persoonslijst testPersoon = new Persoonslijst(persoon.build(),
                0L);
        // arrange
        final Partij partijZonderAfnemerindicatie = TestPartijBuilder.maakBuilder().metCode("999999").build();
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, 1,
                partijZonderAfnemerindicatie);
        // act
        final boolean resultaat = afnemerIndicatieFilter.magLeveren(testPersoon, null, autorisatiebundel);

        // assert
        assertThat(resultaat, is(false));
    }

    @Test
    public final void verlopenAfnemerIndicatieGevonden() {
        persoon.metObject(TestBuilders.maakVerlopenAfnemerIndicaties(1, "000001"));
        final Persoonslijst testPersoon = new Persoonslijst(persoon.build(),
                0L);
        // arrange
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE, 1,
                TestPartijBuilder.maakBuilder().metCode("000001").build());
        // act
        final boolean resultaat = afnemerIndicatieFilter.magLeveren(testPersoon, null, autorisatiebundel);

        // assert
        assertThat(resultaat, is(false));
    }

    private Autorisatiebundel maakAutorisatiebundel(final SoortDienst soortDienst,
                                                    final int leveringautorisatieId,
                                                    final Partij partij) {
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(leveringautorisatieId, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        return new Autorisatiebundel(tla, dienst);
    }
}
