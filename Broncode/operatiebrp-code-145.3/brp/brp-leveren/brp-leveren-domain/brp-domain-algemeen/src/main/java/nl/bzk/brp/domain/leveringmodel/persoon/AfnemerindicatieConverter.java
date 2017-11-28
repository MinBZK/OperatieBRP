/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.Afnemerindicatie;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import org.springframework.util.Assert;

/**
 * Vertaalt van Afnemerindicatie naar MetaObject. Dit dient voor persistence (bij plaatsen afnemerindicatie) o mdat MetaObjecten generiek naar de BLOB
 * vertaald kunnen worden. <br> Vertaalt MetaObject terug naar Afnemerindicatie. Dit wordt gebruikt bij het inlezen van de BLOB.
 */
final class AfnemerindicatieConverter {

    private AfnemerindicatieConverter() {
    }

    /**
     * Converteert een MetaObject naar Afnemerindicatie. Dit wordt gebruikt bij het generiek inlezen van BLOB data.
     * @param afnemerIndicatiesMeta set met meta objecten
     * @return een set met afnemerindicatie objecten
     */
    static Set<Afnemerindicatie> converteerNaarAfnemerIndicaties(final Set<MetaObject> afnemerIndicatiesMeta) {

        final Set<Afnemerindicatie> objects = Sets.newHashSet();
        for (final MetaObject metaObject : afnemerIndicatiesMeta) {
            final Set<Afnemerindicatie> afnemerindicaties = converteerNaarAfnemerIndicaties(metaObject);
            objects.addAll(afnemerindicaties);
        }
        return objects;
    }

    private static Set<Afnemerindicatie> converteerNaarAfnemerIndicaties(final MetaObject afnemerIndicatie) {
        final Set<Afnemerindicatie> afnemerindicaties = new HashSet<>();
        Assert.isTrue(Element.PERSOON_AFNEMERINDICATIE.getId() == afnemerIndicatie.getObjectElement().getId(),
                "Persoon.Afnemerindicatie objectid is niet correct.");
        //identiteit groep
        final MetaGroep identiteitGroep = afnemerIndicatie.getGroep(Element.PERSOON_AFNEMERINDICATIE_IDENTITEIT);
        final MetaRecord identiteitRecord = identiteitGroep.getRecords().iterator().next();
        final MetaAttribuut attrLeveringsAutorisatieId = identiteitRecord.getAttribuut(Element.PERSOON_AFNEMERINDICATIE_LEVERINGSAUTORISATIEIDENTIFICATIE);
        final MetaAttribuut attrAfnemerCode = identiteitRecord.getAttribuut(Element.PERSOON_AFNEMERINDICATIE_PARTIJCODE);
        final MetaAttribuut persattr = identiteitRecord.getAttribuut(Element.PERSOON_AFNEMERINDICATIE_PERSOON);
        //standaard groep
        final MetaGroep stdGroep = afnemerIndicatie.getGroep(Element.PERSOON_AFNEMERINDICATIE_STANDAARD);
        if (stdGroep == null) {
            return Collections.emptySet();
        }
        for (MetaRecord standaardRecord : stdGroep.getRecords()) {
            final Afnemerindicatie.Builder afnemerIndicatieBuilder = new Afnemerindicatie.Builder()
                    .metLeveringsAutorisatieId(attrLeveringsAutorisatieId.getWaarde())
                    .metVoorkomenSleutel(standaardRecord.getVoorkomensleutel())
                    .metObjectSleutel(identiteitRecord.getParentGroep().getParentObject().getObjectsleutel())
                    .metPersoonId(persattr.<Number>getWaarde().longValue())
                    .metAfnemerCode(attrAfnemerCode.getWaarde())
                    .metTijdstipRegistratie(
                            standaardRecord.getAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE.getId()))
                                    .getWaarde());

            final MetaAttribuut datumAanvangMaterielePeriodeAttr = standaardRecord
                    .getAttribuut(Element.PERSOON_AFNEMERINDICATIE_DATUMAANVANGMATERIELEPERIODE);
            if (datumAanvangMaterielePeriodeAttr != null) {
                afnemerIndicatieBuilder.metDatumAanvangMaterielePeriode(datumAanvangMaterielePeriodeAttr.<Integer>getWaarde());
            }
            final MetaAttribuut tijdstipVerval = standaardRecord.getAttribuut(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPVERVAL);
            if (tijdstipVerval != null) {
                afnemerIndicatieBuilder.metTijdstipVerval(tijdstipVerval.getWaarde());
            }
            final MetaAttribuut datumEindeVolgenAttr = standaardRecord.getAttribuut(Element.PERSOON_AFNEMERINDICATIE_DATUMEINDEVOLGEN);
            if (datumEindeVolgenAttr != null) {
                afnemerIndicatieBuilder.metDatumEindeVolgen(datumEindeVolgenAttr.<Integer>getWaarde());
            }
            afnemerindicaties.add(afnemerIndicatieBuilder.build());
        }
        return afnemerindicaties;
    }
}
