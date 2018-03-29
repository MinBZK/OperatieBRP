/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.dsl.autorisatie;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhoudingsautorisatieSoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhoudingsautorisatieSoortAdministratieveHandelingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangBijhoudingsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.test.common.dsl.DslSectie;
import nl.bzk.brp.test.common.dsl.DslSectieParser;
import org.springframework.core.io.Resource;

/**
 * Databaseverzoek voor het invoeren van de bijhoudingsautorisatie.
 */
public class BijhoudingAutorisatieDslVerzoek implements Consumer<EntityManager> {

    private final Resource dslResource;
    private final String afleverpunt;
    private List<ToegangBijhoudingsautorisatie> autorisaties = Lists.newLinkedList();

    /**
     * Constructor.
     *
     * @param dslResource dsl resource
     * @param afleverpunt een statisch afleverpunt
     */
    public BijhoudingAutorisatieDslVerzoek(Resource dslResource, String afleverpunt) {
        this.dslResource = dslResource;
        this.afleverpunt = afleverpunt;
    }

    @Override
    public void accept(EntityManager entityManager) {
        final List<DslSectie> list = DslSectieParser.parse(dslResource);
        final ListIterator<DslSectie> sectieIterator = list.listIterator();

        final Function<String, Partij> partijResolver = partijNaam -> {
            final TypedQuery<Partij> query = entityManager.
                    createQuery(String.format("select p from Partij p where p.naam = '%s'", partijNaam), Partij.class);
            return query.getSingleResult();
        };

        final Function<String, Partij> partijResolverOpOin = partijOin -> {
            final TypedQuery<Partij> query = entityManager.
                    createQuery(String.format("select p from Partij p where p.oin = '%s'", partijOin), Partij.class);
            return query.getSingleResult();
        };

        //persist toegangbijhaut
        final DslSectie sectie = sectieIterator.next();
        sectie.assertMetNaam(ToegangBijhoudingautorisatieParser.SECTIE_TOEGANG_BIJHOUDINGAUTORISATIE);
        final ToegangBijhoudingsautorisatie toegangLeveringsAutorisatie =
                ToegangBijhoudingautorisatieParser.parse(sectie, partijResolver, partijResolverOpOin, afleverpunt);
        entityManager.persist(toegangLeveringsAutorisatie);
        autorisaties.add(toegangLeveringsAutorisatie);

        //persist bijhoudingautorisatie soort adm hnd
        //default
        final BijhoudingsautorisatieSoortAdministratieveHandeling defaultBijhoudingautorisatieSrtAdmHnd =
                new BijhoudingsautorisatieSoortAdministratieveHandeling(toegangLeveringsAutorisatie.getBijhoudingsautorisatie(),
                        SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND);

        final BijhoudingsautorisatieSoortAdministratieveHandelingHistorie baSrtAdmHndHistorie =
                new BijhoudingsautorisatieSoortAdministratieveHandelingHistorie(defaultBijhoudingautorisatieSrtAdmHnd,
                        DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(DatumUtil.nuAlsZonedDateTime()));
        defaultBijhoudingautorisatieSrtAdmHnd.addBijhoudingsautorisatieSoortAdministratieveHandelingHistorie(baSrtAdmHndHistorie);

        entityManager.persist(defaultBijhoudingautorisatieSrtAdmHnd);
        //overige
        while (sectieIterator.hasNext()) {
            final DslSectie sectieBijAutSrtAdmHnd = sectieIterator.next();
            sectieBijAutSrtAdmHnd.assertMetNaam(BijhoudingautorisatieSrtAdmHndParser.SECTIE_BIJHAUT_SRTADMHND);
            sectieIterator.previous();
            final BijhoudingsautorisatieSoortAdministratieveHandeling bijhoudingautorisatieSrtAdmHnd =
                    BijhoudingautorisatieSrtAdmHndParser.parse(sectieBijAutSrtAdmHnd, toegangLeveringsAutorisatie.getBijhoudingsautorisatie());
            entityManager.persist(bijhoudingautorisatieSrtAdmHnd);
        }

        entityManager.flush();
    }

    public List<ToegangBijhoudingsautorisatie> getAutorisaties() {
        return autorisaties;
    }


}
