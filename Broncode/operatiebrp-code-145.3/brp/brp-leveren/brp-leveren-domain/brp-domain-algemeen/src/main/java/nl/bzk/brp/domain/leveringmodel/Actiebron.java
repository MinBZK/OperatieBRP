/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import java.util.Objects;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import org.springframework.util.Assert;

/**
 * Actiebron beschrijft de relatie tussen Actie en Document.
 */
public final class Actiebron {

    private Long id;
    private Actie actie;
    private Document document;
    private String rechtsgrondCode;
    private String rechtsgrondomschrijving;
    private MetaObject metaObject;

    private Actiebron() {
    }

    /**
     * @return technische sleutel
     */
    public Long getId() {
        return id;
    }

    /**
     * @return de parent actie
     */
    public Actie getActie() {
        return actie;
    }

    /**
     * @return het onderliggende document
     */
    public Document getDocument() {
        return document;
    }

    /**
     * @return de rechtsgrond
     */
    public String getRechtsgrond() {
        return rechtsgrondCode;
    }

    /**
     * @return de rechtsgrondomschrijving
     */
    public String getRechtsgrondomschrijving() {
        return rechtsgrondomschrijving;
    }

    /**
     * @return actiebron als metaobject.
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
        final Actiebron actieBron = (Actiebron) o;
        return Objects.equals(id, actieBron.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * @return een ActieBron builder.
     */
    public static Converter converter() {
        return Converter.INSTANCE;
    }

    /**
     * Converter voor MetaObject naar Actiebron.
     */
    static final class Converter {

        private static final Converter INSTANCE = new Converter();

        private static final GroepElement IDENTITEIT = ElementHelper.getGroepElement(Element.ACTIEBRON_IDENTITEIT.getId());

        private Converter() {
        }

        /**
         * Converteert het Actiebron metaobject.
         * @param metaObject het Actiebron metaobject
         * @param actie de bovenliggende actie
         * @return de handeling
         */
        Actiebron converteer(final MetaObject metaObject, final Actie actie) {

            final Actiebron actiebron = new Actiebron();
            actiebron.actie = actie;
            actiebron.id = metaObject.getObjectsleutel();
            actiebron.metaObject = metaObject;

            final MetaGroep identiteit = metaObject.getGroep(IDENTITEIT);
            Assert.notNull(identiteit, "Actiebron conversiefout : identiteitgroep is null.");
            final MetaRecord record = identiteit.getRecords().iterator().next();
            //rechtsgrondcode
            final MetaAttribuut rechtsgrondCodeAttr = record.getAttribuut(Element.ACTIEBRON_RECHTSGRONDCODE);
            if (rechtsgrondCodeAttr != null) {
                actiebron.rechtsgrondCode = rechtsgrondCodeAttr.<String>getWaarde();
            }
            //rechtsgrondomschrijving
            final MetaAttribuut rechtsgrondOmschrijvingAttr = record.getAttribuut(Element.ACTIEBRON_RECHTSGRONDOMSCHRIJVING);
            if (rechtsgrondOmschrijvingAttr != null) {
                actiebron.rechtsgrondomschrijving = rechtsgrondOmschrijvingAttr.getWaarde();
            }
            //er is voor gekozen om document onder de actiebron te modeleren
            final Set<MetaObject> objecten = metaObject.getObjecten();
            if (!objecten.isEmpty()) {
                final MetaObject next = objecten.iterator().next();
                actiebron.document = Document.converter().converteer(next);
            }
            return actiebron;
        }
    }
}
