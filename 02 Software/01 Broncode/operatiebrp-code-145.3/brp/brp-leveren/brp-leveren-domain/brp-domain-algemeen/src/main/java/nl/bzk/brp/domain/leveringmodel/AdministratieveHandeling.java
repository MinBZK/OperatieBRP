/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import org.springframework.util.Assert;

/**
 * Domein object voor administratieve handeling.
 */
public final class AdministratieveHandeling {

    /**
     * OnderhoudAfnemerindicatieContext en SynchroniseerPersoonContext
     */
    private Long id;

    private SoortAdministratieveHandeling soort;
    private String partijCode;
    private String toelichtingOntlening;
    private ZonedDateTime tijdstipRegistratie;
    private ZonedDateTime tijdstipLevering;
    private Map<Long, Actie> acties;
    private MetaObject metaObject;

    private AdministratieveHandeling() {
    }

    /**
     * @return een converter
     */
    public static Converter converter() {
        return Converter.INSTANCE;
    }

    /**
     * @return een converter
     */
    public static Comparator<AdministratieveHandeling> comparator() {
        return AdministratievehandelingComparator.INSTANCE;
    }

    /**
     * @return technisch id van de handeling
     */
    public Long getId() {
        return id;
    }

    /**
     * @return soort administratieve handeling
     */
    public SoortAdministratieveHandeling getSoort() {
        return soort;
    }

    /**
     * @return partij
     */
    public String getPartijCode() {
        return partijCode;
    }

    /**
     * @return toelichtingOntlening
     */
    public String getToelichtingOntlening() {
        return toelichtingOntlening;
    }

    /**
     * @return tijdstipRegistratie
     */
    public ZonedDateTime getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * @return tijdstipLevering
     */
    public ZonedDateTime getTijdstipLevering() {
        return tijdstipLevering;
    }

    /**
     * @return de acties van de handeling
     */
    public Collection<Actie> getActies() {
        return Sets.newHashSet(acties.values());
    }

    /**
     * Geeft de Actie met het gegeven id.
     * @param actieId het actie id
     * @return een Actie
     */
    public Actie getActie(final long actieId) {
        if (!acties.containsKey(actieId)) {
            throw new IllegalArgumentException("Actie bestaat niet: " + actieId);
        }
        return acties.get(actieId);
    }

    /**
     * @return het metaobject van de administratievehandeling
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

        final AdministratieveHandeling admhnd = (AdministratieveHandeling) o;
        return Objects.equal(id, admhnd.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     * Converter voor MetaObject naar AdministratieveHandeling.
     */
    public static final class Converter {

        private static final Converter INSTANCE = new Converter();

        private static final GroepElement IDENTITEIT = ElementHelper
                .getGroepElement(Element.ADMINISTRATIEVEHANDELING_IDENTITEIT.getId());

        /**
         * Constructor
         */
        private Converter() {
        }

        /**
         * Converteert het Administratievehandeling metaobject.
         * @param metaObject het metaobject
         * @return de handeling
         */
        public AdministratieveHandeling converteer(final MetaObject metaObject) {

            if (Element.ADMINISTRATIEVEHANDELING.getId() != metaObject.getObjectElement().getId()) {
                throw new IllegalStateException("Verkeerd type element: " + metaObject.getObjectElement());
            }

            final AdministratieveHandeling ah = new AdministratieveHandeling();
            ah.id = metaObject.getObjectsleutel();
            ah.metaObject = metaObject;
            final MetaGroep identiteitGroep = metaObject.getGroep(IDENTITEIT);
            Assert.notNull(identiteitGroep, "Administratieve handeling conversiefout : identiteitgroep is null.");
            final MetaRecord identiteitRecord = identiteitGroep.getRecords().iterator().next();
            final MetaAttribuut soortNaamAttr = identiteitRecord
                    .getAttribuut(Element.ADMINISTRATIEVEHANDELING_SOORTNAAM);
            if (soortNaamAttr != null) {
                ah.soort = SoortAdministratieveHandeling.parseId(soortNaamAttr.getWaarde());
            }
            final MetaAttribuut partijCodeAttr = identiteitRecord.getAttribuut(Element.ADMINISTRATIEVEHANDELING_PARTIJCODE);
            if (partijCodeAttr != null) {
                ah.partijCode = partijCodeAttr.getWaarde();
            }
            final MetaAttribuut tijdstipRegistratieAttr = identiteitRecord
                    .getAttribuut(Element.ADMINISTRATIEVEHANDELING_TIJDSTIPREGISTRATIE);
            if (tijdstipRegistratieAttr != null) {
                ah.tijdstipRegistratie = tijdstipRegistratieAttr.getWaarde();
            }
            final MetaAttribuut toelichtingOntleningAttr = identiteitRecord.
                    getAttribuut(Element.ADMINISTRATIEVEHANDELING_TOELICHTINGONTLENING);
            if (toelichtingOntleningAttr != null) {
                ah.toelichtingOntlening = toelichtingOntleningAttr.getWaarde();
            }
            final Map<Long, Actie> tempActies = Maps.newHashMap();
            for (final MetaObject actieMetaObject : metaObject.getObjecten()) {
                if (actieMetaObject.getObjectElement().getElement() == Element.ACTIE) {
                    final Actie actie = Actie.converter().converteer(actieMetaObject, ah);
                    tempActies.put(actie.getId(), actie);
                }
            }
            ah.acties = ImmutableMap.copyOf(tempActies);
            return ah;
        }
    }


    /**
     * AdministratievehandelingComparator. De comparator voor HisPersoonAfgeleidAdministratiefModel op basis van de tijdstip laatste wijziging.
     */
    @Bedrijfsregel(Regel.R1804)
    private static final class AdministratievehandelingComparator implements Comparator<AdministratieveHandeling>, Serializable {

        private static final long serialVersionUID = 1L;
        private static final Comparator<AdministratieveHandeling> INSTANCE = new AdministratievehandelingComparator();

        @Override
        public int compare(final AdministratieveHandeling handeling1, final AdministratieveHandeling handeling2) {
            int resultaat = handeling1.getTijdstipRegistratie().compareTo(handeling2.getTijdstipRegistratie());
            if (resultaat == 0) {
                resultaat = handeling1.getId().compareTo(handeling2.getId());
            }
            return resultaat;
        }
    }

}
