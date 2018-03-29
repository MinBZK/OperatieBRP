/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import org.springframework.util.Assert;

/**
 * Representatie van een actie binnen de BRP.
 */
@JsonAutoDetect
public final class Actie {

    private static final String FOUT_MELDING = "Actie.Id niet gevuld";

    private Long id;
    private SoortActie soort;
    private AdministratieveHandeling administratieveHandeling;
    private String partijCode;
    private ZonedDateTime tijdstipRegistratie;
    private Integer datumOntlening;
    private Set<Actiebron> bronnen;
    private MetaObject metaObject;

    private Actie() {
    }

    /**
     * @return de builder voor Actie
     */
    public static Converter converter() {
        return Actie.Converter.INSTANCE;
    }

    /**
     * @return technisch id van de actie
     */
    public Long getId() {
        return id;
    }

    /**
     * @return het soort actie
     */
    public SoortActie getSoort() {
        return soort;
    }

    /**
     * @return de administratievehandeling waartoe de actie behoort
     */
    public AdministratieveHandeling getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * @return de partij welke de actie aangemaakt heeft
     */
    public String getPartijCode() {
        return partijCode;
    }

    /**
     * @return tijdstip registratie van de actie
     */
    public ZonedDateTime getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * @return datum ontlening van de actie
     */
    public Integer getDatumOntlening() {
        return datumOntlening;
    }

    /**
     * @return bronnen van de actie
     */
    public Set<Actiebron> getBronnen() {
        return bronnen;
    }

    /**
     * @return het MetaObject van de Actie
     */
    public MetaObject getMetaObject() {
        return metaObject;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Actie actie = (Actie) o;
        return Objects.equals(id, actie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }

    /**
     * Converter voor MetaObject naar Actie.
     */
    static final class Converter {

        private static final Converter INSTANCE = new Converter();

        private static final GroepElement IDENTITEIT = ElementHelper.getGroepElement(Element.ACTIE_IDENTITEIT.getId());

        private Converter() {
        }

        /**
         * Converteert het Actie metaobject.
         * @param metaObject het actie metaobject
         * @param administratieveHandeling de bovenliggende administratievehandeling
         * @return de handeling
         */
        Actie converteer(final MetaObject metaObject, final AdministratieveHandeling administratieveHandeling) {
            final Actie actie = new Actie();
            actie.metaObject = metaObject;
            actie.administratieveHandeling = administratieveHandeling;
            actie.id = metaObject.getObjectsleutel();
            Assert.notNull(actie.id, FOUT_MELDING);
            final MetaGroep identiteitGroep = metaObject.getGroep(IDENTITEIT);
            Assert.notNull(identiteitGroep, "Actie conversiefout : identiteitgroep is null.");
            final MetaRecord record = identiteitGroep.getRecords().iterator().next();
            //soort
            final MetaAttribuut soortAttr = record.getAttribuut(Element.ACTIE_SOORTNAAM);
            if (soortAttr != null) {
                actie.soort = SoortActie.parseId(soortAttr.<Number>getWaarde().intValue());
            }
            //partijcode
            final MetaAttribuut partijAttr = record.getAttribuut(Element.ACTIE_PARTIJCODE);
            if (partijAttr != null) {
                actie.partijCode = partijAttr.getWaarde();
            }
            //tsreg
            final MetaAttribuut tsregAttr = record.getAttribuut(Element.ACTIE_TIJDSTIPREGISTRATIE);
            if (tsregAttr != null) {
                actie.tijdstipRegistratie = tsregAttr.getWaarde();
            }
            final MetaAttribuut datumOntleningAttr = record.getAttribuut(Element.ACTIE_DATUMONTLENING);
            if (datumOntleningAttr != null) {
                actie.datumOntlening = datumOntleningAttr.<Integer>getWaarde();
            }

            //er is voor gekozen om actiebronnen hierarchisch onder de actie te modeleren
            final List<Actiebron> actiebronList = Lists.newLinkedList();
            for (final MetaObject actiebronMetaObject : metaObject.getObjecten()) {
                actiebronList.add(Actiebron.converter().converteer(actiebronMetaObject, actie));
            }
            actie.bronnen = ImmutableSet.copyOf(Sets.newHashSet(actiebronList));
            return actie;
        }
    }
}
