/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerantwoordingCategorie;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;

/**
 * Representeert gegevensrij binnen en MetaGroep.
 */
public final class MetaRecord extends MetaModel {
    private long voorkomensleutel;
    private MetaGroep parentGroep;
    private Actie actieInhoud;
    private Actie actieVerval;
    private Actie actieAanpassingGeldigheid;
    private Actie actieVervalTbvLeveringMutaties;
    private Boolean indicatieTbvLeveringMutaties;
    private String nadereAanduidingVerval;
    private Integer datumAanvangGeldigheid;
    private Integer datumEindeGeldigheid;

    /**
     * De attributen van dit voorkomen
     */
    private Map<AttribuutElement, MetaAttribuut> attributen;

    /**
     * Constructor voor een MetaRecord.
     */
    private MetaRecord() {
    }

    @Override
    public MetaModel getParent() {
        return parentGroep;
    }

    /**
     * Maakt een builder object om een MetaObject op te bouwen.
     * @return een builder
     */
    public static Builder maakBuilder() {
        return new Builder(null);
    }

    /**
     * @return technische sleutel van dit record
     */
    public long getVoorkomensleutel() {
        return voorkomensleutel;
    }

    /**
     * @return de generieke attributen
     */
    public Map<AttribuutElement, MetaAttribuut> getAttributen() {
        return attributen;
    }

    /**
     * @param attribuutElement het element van het attribuut dat gezocht wordt
     * @return het meta atribuut, of null indien het niet bestaat op dit record.
     */
    public MetaAttribuut getAttribuut(final Element attribuutElement) {
        return getAttribuut(ElementHelper.getAttribuutElement(attribuutElement));
    }

    /**
     * @param attribuutElement het element van het attribuut dat gezocht wordt
     * @return het meta atribuut, of null indien het niet bestaat op dit record.
     */
    public MetaAttribuut getAttribuut(final AttribuutElement attribuutElement) {
        return attributen.get(attribuutElement);
    }

    /**
     * @return de groep tot welke dit record behoort
     */
    public MetaGroep getParentGroep() {
        return parentGroep;
    }

    /**
     * @return de verantwoording voor aanpassing geldigheid
     */
    public Actie getActieAanpassingGeldigheid() {
        return actieAanpassingGeldigheid;
    }

    /**
     * @return actie inhoud
     */
    public Actie getActieInhoud() {
        return actieInhoud;
    }

    /**
     * @return actie verval, Gevuld indien de groep actieverantwoording heeft, null indien niet vervallen.
     */
    public Actie getActieVerval() {
        return actieVerval;
    }

    /**
     * @return de nadere aanduiding verval, indien vervallen.
     */
    public String getNadereAanduidingVerval() {
        return nadereAanduidingVerval;
    }

    /**
     * @return isIndicatieTbvLeveringMutaties
     */
    public Boolean isIndicatieTbvLeveringMutaties() {
        return indicatieTbvLeveringMutaties;
    }

    /**
     * @return de actie gevuld vanuit migratie indien historie niet correct bepaald kon worden.
     */
    public Actie getActieVervalTbvLeveringMutaties() {
        return actieVervalTbvLeveringMutaties;
    }

    /**
     * @return materiele datum aanvang
     */
    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * @return materiele datum einde geldigheid
     */
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * @return het tijdstip registratie attribuut
     */
    public ZonedDateTime getTijdstipRegistratie() {
        if (getParentGroep().getGroepElement().getVerantwoordingCategorie() == VerantwoordingCategorie.D) {
            final AttribuutElement attribuutElement = getParentGroep().getGroepElement()
                    .getAttribuutMetElementNaam(AttribuutElement.DATUM_TIJD_REGISTRATIE);
            final MetaAttribuut attribuut = getAttribuut(attribuutElement);
            return attribuut.getWaarde();
        } else {
            return actieInhoud.getTijdstipRegistratie();
        }
    }

    /**
     * @return het datum tijd verval attribuut
     */
    public ZonedDateTime getDatumTijdVerval() {
        ZonedDateTime tsVerval = null;
        if (getParentGroep().getGroepElement().getVerantwoordingCategorie() == VerantwoordingCategorie.D) {
            final AttribuutElement attribuutElement = getParentGroep().getGroepElement().getAttribuutMetElementNaam(AttribuutElement.DATUM_TIJD_VERVAL);
            final MetaAttribuut attribuut = getAttribuut(attribuutElement);
            if (attribuut != null) {
                tsVerval = attribuut.getWaarde();
            }
        } else {
            tsVerval = actieVerval == null ? null : actieVerval.getTijdstipRegistratie();
        }
        return tsVerval;
    }

    /**
     * @return de waarde van het tijdstip registratie attribuut
     */
    public ZonedDateTime getTijdstipRegistratieAttribuut() {
        return actieInhoud == null ? null : actieInhoud.getTijdstipRegistratie();
    }

    /**
     * @return het datum tijd verval attribuut
     */
    public ZonedDateTime getDatumTijdVervalAttribuut() {
        return actieVerval == null ? null : actieVerval.getTijdstipRegistratie();
    }

    @Override
    public void accept(final ModelVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return getParentGroep().toString();
    }

    /**
     * Builder class om een MetaRecord te construeren.
     */
    public static final class Builder {

        private final MetaGroep.Builder metaGroepBuilder;
        private long voorkomensleutel;
        private List<MetaAttribuut.Builder> attribuutBuilderList = Lists.newLinkedList();

        /**
         * Default constructor.
         * @param metaGroepBuilder de parent groep builder.
         */
        public Builder(final MetaGroep.Builder metaGroepBuilder) {
            this.metaGroepBuilder = metaGroepBuilder;
        }

        /**
         * Zet het technisch id van het record.
         * @param number het id van dit record
         * @return deze recordbuilder
         */
        public Builder metId(final Number number) {
            this.voorkomensleutel = number.longValue();
            return this;
        }

        /**
         * Voegt het formele 'ActieInhoud' attribuut toe. Daarnaast wordt het hiervan afgeleide 'DatumTijdRegistratie' attribuut gezet.
         * @param actie de verwantwoording inhoud
         * @return deze recordbuilder
         */
        public Builder metActieInhoud(final Actie actie) {
            if (actie != null) {
                final AttribuutElement element = metaGroepBuilder.getGroepElement()
                        .getAttribuutMetElementNaam(AttribuutElement.BRP_ACTIE_INHOUD);
                metAttribuut(element, actie);
                //tijdstip registratie is een afgeleid attribuut van actie
                metAttribuut(metaGroepBuilder.getGroepElement()
                        .getAttribuutMetElementNaam(AttribuutElement.DATUM_TIJD_REGISTRATIE), actie
                        .getTijdstipRegistratie());
            }
            return this;
        }

        /**
         * Voegt het formele 'ActieVerval' attribuut toe. Daarnaast wordt het hiervan afgeleide 'DatumTijdVerval' attribuut gezet.
         * @param actie de verantwoording verval
         * @return deze recordbuilder
         */
        public Builder metActieVerval(final Actie actie) {
            if (actie != null) {
                final AttribuutElement element = metaGroepBuilder.getGroepElement()
                        .getActieVervalAttribuut();
                metAttribuut(element, actie);
                //datum tijd verval is een afgeleid attribuut van actie
                metAttribuut(metaGroepBuilder.getGroepElement()
                        .getAttribuutMetElementNaam(AttribuutElement.DATUM_TIJD_VERVAL), actie.getTijdstipRegistratie());
            }
            return this;
        }

        /**
         * Voegt het materiele 'ActieAanpassingGeldigheid' attribuut toe.
         * @param actie de verantwoording aanpassing geldigheid
         * @return deze record builder
         */
        public Builder metActieAanpassingGeldigheid(final Actie actie) {
            if (actie != null) {
                final AttribuutElement element = metaGroepBuilder.getGroepElement()
                        .getActieAanpassingGeldigheidAttribuut();
                metAttribuut(element, actie);
            }
            return this;
        }

        /**
         * Voegt het attribuut 'NadereAanduidingVerval' toe.
         * @param aanduidingVerval de nadere aanduiding verval
         * @return deze recordbuilder
         */
        public Builder metNadereAanduidingVerval(final String aanduidingVerval) {
            if (aanduidingVerval != null) {
                final AttribuutElement element = metaGroepBuilder.getGroepElement()
                        .getNadereAanduidingVervalAttribuut();
                metAttribuut(element, aanduidingVerval);
            }
            return this;
        }

        /**
         * Voegt het materiele 'DatumEindeGeldigheid' attribuut toe.
         * @param datumWaarde een datum evt onbekend.
         * @return deze recordbuilder
         */
        public Builder metDatumAanvangGeldigheid(final Integer datumWaarde) {
            if (datumWaarde != null) {
                final AttribuutElement element = metaGroepBuilder.getGroepElement()
                        .getDatumAanvangGeldigheidAttribuut();
                metAttribuut(element, datumWaarde);
            }
            return this;
        }

        /**
         * Voegt het materiele 'DatumEindeGeldigheid' attribuut toe.
         * @param datumWaarde een datum evt onbekend.
         * @return deze recordbuilder
         */
        public Builder metDatumEindeGeldigheid(final Integer datumWaarde) {
            if (datumWaarde != null) {
                final AttribuutElement element = metaGroepBuilder.getGroepElement()
                        .getDatumEindeGeldigheidAttribuut();
                metAttribuut(element, datumWaarde);
            }
            return this;
        }

        /**
         * Voegt het attribuut 'indicatieTbvLeveringMutaties' toe.
         * @param indicatie boolean indicatie
         * @return deze recordbuilder
         */
        public Builder metIndicatieTbvLeveringMutaties(final Boolean indicatie) {
            if (indicatie != null) {
                final AttribuutElement element =
                        metaGroepBuilder.getGroepElement().getIndicatieTbvLeveringMutatiesAttribuut();
                metAttribuut(element, indicatie);
            }
            return this;
        }

        /**
         * Voegt het attribuut ActieVervalTbvLeveringMutaties toe.
         * @param actie het actie object
         * @return deze recordbuilder
         */
        public Builder metActieVervalTbvLeveringMutaties(final Actie actie) {
            if (actie != null) {
                final AttribuutElement element =
                        metaGroepBuilder.getGroepElement().getActieVervalTbvLeveringMutatiesAttribuut();
                metAttribuut(element, actie);
            }
            return this;
        }

        /**
         * Voegt een attribuut (builder) toe.
         * @return deze recordbuilder
         */
        public MetaAttribuut.Builder metAttribuut() {
            final MetaAttribuut.Builder attribuutBuilder = new MetaAttribuut.Builder(this);
            this.attribuutBuilderList.add(attribuutBuilder);
            return attribuutBuilder;
        }

        /**
         * Voegt een lijst van attributen (builders) toe.
         * @param builderList lijst van attrbuut builders
         * @return deze recordbuilder
         */
        public Builder metAttributen(final List<MetaAttribuut.Builder> builderList) {
            this.attribuutBuilderList.addAll(builderList);
            return this;
        }

        /**
         * Voegt een attribuut toe met gegeven element en waarde.
         * @param id attribuutElement
         * @param waarde waarde
         * @return de recordbuilder
         */
        public MetaRecord.Builder metAttribuut(final int id, final Object waarde) {
            return metAttribuut(ElementHelper.getAttribuutElement(id), waarde);
        }


        /**
         * Voegt een attribuut toe met gegeven element en waarde.
         * @param attribuutElement attribuutElement
         * @param waarde waarde
         * @return de recordbuilder
         */
        public MetaRecord.Builder metAttribuut(final AttribuutElement attribuutElement, final Object waarde) {
            if (attribuutElement == null) {
                throw new IllegalStateException(String.format("Groep %s kent element niet.", metaGroepBuilder
                        .getGroepElement().getElementNaam()));
            }
            final MetaAttribuut.Builder attribuutBuilder = new MetaAttribuut.Builder(this);
            attribuutBuilder.metType(attribuutElement);
            attribuutBuilder.metWaarde(waarde);
            this.attribuutBuilderList.add(attribuutBuilder);
            return this;
        }

        /**
         * @return de builder van de parent groep
         */
        public MetaGroep.Builder eindeRecord() {
            return metaGroepBuilder;
        }

        /**
         * Bouwt het MetaRecord definitief.
         * @param gebouwdeGroep de parent groep
         * @return het definitieve record
         */
        public MetaRecord build(final MetaGroep gebouwdeGroep) {
            final MetaRecord gebouwdRecord = new MetaRecord();
            gebouwdRecord.parentGroep = gebouwdeGroep;
            gebouwdRecord.voorkomensleutel = this.voorkomensleutel;

            final Map<AttribuutElement, MetaAttribuut> attributenTemp = Maps.newHashMap();
            final GroepElement groepElement = gebouwdeGroep.getGroepElement();
            for (final MetaAttribuut.Builder builder : attribuutBuilderList) {
                final MetaAttribuut attribuut = builder.build(gebouwdRecord);
                final AttribuutElement attribuutElement = attribuut.getAttribuutElement();
                attributenTemp.put(attribuutElement, attribuut);

                if (attribuutElement == groepElement.getActieInhoudAttribuut()) {
                    gebouwdRecord.actieInhoud = attribuut.getWaarde();
                } else if (attribuutElement == groepElement.getActieVervalAttribuut()) {
                    gebouwdRecord.actieVerval = attribuut.getWaarde();
                } else if (attribuutElement == groepElement.getActieAanpassingGeldigheidAttribuut()) {
                    gebouwdRecord.actieAanpassingGeldigheid = attribuut.getWaarde();
                } else if (attribuutElement == groepElement.getNadereAanduidingVervalAttribuut()) {
                    gebouwdRecord.nadereAanduidingVerval = attribuut.getWaarde();
                } else if (attribuutElement == groepElement.getDatumAanvangGeldigheidAttribuut()) {
                    gebouwdRecord.datumAanvangGeldigheid = attribuut.getWaarde();
                } else if (attribuutElement == groepElement.getDatumEindeGeldigheidAttribuut()) {
                    gebouwdRecord.datumEindeGeldigheid = attribuut.getWaarde();
                } else if (attribuutElement == groepElement.getIndicatieTbvLeveringMutatiesAttribuut()) {
                    gebouwdRecord.indicatieTbvLeveringMutaties = attribuut.getWaarde();
                } else if (attribuutElement == groepElement.getActieVervalTbvLeveringMutatiesAttribuut()) {
                    gebouwdRecord.actieVervalTbvLeveringMutaties = attribuut.getWaarde();
                }
            }
            gebouwdRecord.attributen = Collections.unmodifiableMap(attributenTemp);
            return gebouwdRecord;
        }
    }
}
