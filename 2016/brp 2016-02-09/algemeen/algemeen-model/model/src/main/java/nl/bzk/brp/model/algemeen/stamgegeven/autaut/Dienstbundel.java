/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PopulatiebeperkingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ExpressietekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnbeperkteOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElement;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElementAutorisatie;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

/**
 * De inzet om maar ��n dienst van elke soort onder een autorisatie te hebben wordt ingeschat als niet houdbaar voor zoeken, bevragen en selecties. Voor
 * die diensten gaan we dus toestaan dat er meerdere voorkomen onder dezelfde autorisatie.
 * <p/>
 * We staan geen meerdere diensten toe bij alles wat met mutatielevering van doen heeft en bij Attendering.
 */
@Table(schema = "AutAut", name = "Dienstbundel")
@Access(value = AccessType.FIELD)
@Entity
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Dienstbundel extends AbstractDienstbundel {

    private static final Set<String> ENUM_NAMEN = new HashSet<String>() {
        {
            for (final ElementEnum elementEnum : ElementEnum.values()) {
                add(elementEnum.name());
            }
        }
    };
    private static final String      IDENTITEIT = "_IDENTITEIT";
    private static final String      STANDAARD  = "_STANDAARD";


    private transient List<ExpressietekstAttribuut> geldigeExpressiesResultaat;
    private transient Set<ElementEnum>              geautoriseerdeGroepenResultaat;


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "dienstbundel", referencedColumnName = "id")
    private Set<Dienst> diensten;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "dienstbundel", referencedColumnName = "id")
    private Set<DienstbundelGroep> dienstbundelGroepen;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "dienstbundel", referencedColumnName = "id")
    private Set<DienstbundelLO3Rubriek> lo3rubrieken;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected Dienstbundel() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param leveringsautorisatie                                   leveringsautorisatie van Dienstbundel.
     * @param naam                                                   naam van Dienstbundel.
     * @param datumIngang                                            datumIngang van Dienstbundel.
     * @param datumEinde                                             datumEinde van Dienstbundel.
     * @param naderePopulatiebeperking                               naderePopulatiebeperking van Dienstbundel.
     * @param indicatieNaderePopulatiebeperkingVolledigGeconverteerd indicatieNaderePopulatiebeperkingVolledigGeconverteerd van Dienstbundel.
     * @param toelichting                                            toelichting van Dienstbundel.
     * @param indicatieGeblokkeerd                                   indicatieGeblokkeerd van Dienstbundel.
     */
    protected Dienstbundel(
        final Leveringsautorisatie leveringsautorisatie,
        final NaamEnumeratiewaardeAttribuut naam,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final PopulatiebeperkingAttribuut naderePopulatiebeperking,
        final NeeAttribuut indicatieNaderePopulatiebeperkingVolledigGeconverteerd,
        final OnbeperkteOmschrijvingAttribuut toelichting,
        final JaAttribuut indicatieGeblokkeerd)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(leveringsautorisatie,
            naam,
            datumIngang,
            datumEinde,
            naderePopulatiebeperking,
            indicatieNaderePopulatiebeperkingVolledigGeconverteerd,
            toelichting,
            indicatieGeblokkeerd);
    }

    public Set<Dienst> getDiensten() {
        return diensten;
    }


    public void setDiensten(final Dienst... diensten) {
        this.diensten = new HashSet<>(Arrays.asList(diensten));
    }

    public Set<DienstbundelGroep> getDienstbundelGroepen() {
        return dienstbundelGroepen;
    }


    public Set<DienstbundelLO3Rubriek> getLo3rubrieken() {
        return lo3rubrieken;
    }


    /**
     * Geeft de attribuut expressies voor de elementen die vandaag geldig zijn.
     *
     * @param rol rol van leveringautorisatie
     * @return een lijst van expressieattributen
     * @brp.bedrijfsregel R2002
     */
    @Regels(Regel.R2002)
    public List<ExpressietekstAttribuut> geefGeldigeExpressies(final Rol rol) {
        if (geldigeExpressiesResultaat != null) {
            return geldigeExpressiesResultaat;
        }

        final List<ExpressietekstAttribuut> expressietekstAttribuutLijst = new LinkedList<>();
        final DatumAttribuut datumVandaag = new DatumAttribuut(new Date());
        final Set<DienstbundelGroep> dienstbundelGroepenSet = getDienstbundelGroepen();
        if (getDienstbundelGroepen() != null) {
            for (DienstbundelGroep dienstbundelGroep : dienstbundelGroepenSet) {
                for (final DienstbundelGroepAttribuutImpl attribuutModel : dienstbundelGroep.getAttributen()) {
                    final Element attribuut = attribuutModel.getAttribuut();
                    if (attribuut.getExpressie() == null || StringUtils.isBlank(attribuut.getExpressie().getWaarde()) || SoortElement.ATTRIBUUT
                        != attribuut.getSoort())
                    {
                        continue;
                    }
                    if (attribuut.getDatumAanvangGeldigheid() != null && attribuut.getDatumAanvangGeldigheid().na(datumVandaag)) {
                        continue;
                    }
                    if (attribuut.getDatumEindeGeldigheid() != null && attribuut.getDatumEindeGeldigheid().voorOfOp(datumVandaag)) {
                        continue;
                    }
                    if (attribuut.getAutorisatie() == SoortElementAutorisatie.NIET_VERSTREKKEN) {
                        continue;
                    }
                    if (attribuut.getAutorisatie() == SoortElementAutorisatie.BIJHOUDINGSGEGEVENS && !magBijhoudingsgegevenGeleverdWorden(rol)) {
                        continue;
                    }
                    expressietekstAttribuutLijst.add(attribuut.getExpressie());
                }
            }
        }
        this.geldigeExpressiesResultaat = expressietekstAttribuutLijst;
        return geldigeExpressiesResultaat;
    }

    public final Set<ElementEnum> geefGeautoriseerdeGroepen() {
        if (geautoriseerdeGroepenResultaat != null) {
            return geautoriseerdeGroepenResultaat;
        }
        final Set<Element> afgeleideGroepen = geefGroepElementenVanAttributen();
        final Set<ElementEnum> geautoriseerdeGroepen = new HashSet<>();
        for (final Element groepElement : afgeleideGroepen) {

            final ElementEnum groepElementEnum = ElementEnum.valueOf(groepElement.geefElementEnumName());
            geautoriseerdeGroepen.add(groepElementEnum);

            // Dit zorgt ervoor dat er voor groepen waarvan een identiteit en standaard groep aanwezig is, ze voor allebei geautoriseerd zijn.
            // Dit is nodig omdat de groepen samen zijn gevoegd / platgeslagen in het operationele model.
            final String vervangenElementNaamStandaard = groepElementEnum.name().replace(STANDAARD, IDENTITEIT);
            final String vervangenElementNaamIdentiteit = groepElementEnum.name().replace(IDENTITEIT, STANDAARD);
            final ElementEnum toebehorendeElementEnum;
            if (groepElementEnum.name().endsWith(STANDAARD) && ENUM_NAMEN.contains(vervangenElementNaamStandaard)) {
                toebehorendeElementEnum = ElementEnum.valueOf(vervangenElementNaamStandaard);
                geautoriseerdeGroepen.add(toebehorendeElementEnum);
            } else if (groepElementEnum.name().endsWith(IDENTITEIT) && ENUM_NAMEN.contains(vervangenElementNaamIdentiteit)) {
                toebehorendeElementEnum = ElementEnum.valueOf(vervangenElementNaamIdentiteit);
                geautoriseerdeGroepen.add(toebehorendeElementEnum);
            }
        }

        //voeg ook groepen toe waarvoor verantwoording vlaggetjes gezet zijn
        if (getDienstbundelGroepen() != null) {
            for (final DienstbundelGroep dienstBundelGroep : getDienstbundelGroepen()) {
                if (dienstBundelGroep.getIndicatieFormeleHistorie().getWaarde()
                    || dienstBundelGroep.getIndicatieMaterieleHistorie().getWaarde()
                    || dienstBundelGroep.getIndicatieVerantwoording().getWaarde())
                {
                    geautoriseerdeGroepen.add(ElementEnum.valueOf(dienstBundelGroep.getGroep().geefElementEnumName()));
                }
            }
        }
        this.geautoriseerdeGroepenResultaat = geautoriseerdeGroepen;
        return geautoriseerdeGroepenResultaat;
    }

    /**
     * @return
     */
    public final Set<ElementEnum> geefGeautoriseerdeGerelateerdeObjectTypen() {
        final Set<Element> groepElementen = geefGroepElementenVanAttributen();
        final Set<ElementEnum> geautoriseerdeObjectTypeEnumSet = new HashSet<>();
        for (final Element groepElement : groepElementen) {
            final ElementEnum objectTypeElementEnum = ElementEnum.valueOf(groepElement.getObjecttype().geefElementEnumName());
            geautoriseerdeObjectTypeEnumSet.add(objectTypeElementEnum);
        }
        return geautoriseerdeObjectTypeEnumSet;
    }


    /**
     * Geeft de populatiebeperking expressie. Deze waarde is nooit leeg. Voor null waarden in de database wordt WAAR geretourneert, in alle andere gevallen
     * wordt de expressie zelf geretourneert
     *
     * @return een expressie String
     */
    public final String getNaderePopulatiebeperkingExpressieString() {
        final PopulatiebeperkingAttribuut populatiebeperkingAttribuut = super.getNaderePopulatiebeperking();
        String expressieWaarde = "WAAR";
        if (populatiebeperkingAttribuut != null && !StringUtils.isBlank(populatiebeperkingAttribuut.getWaarde())) {
            expressieWaarde = populatiebeperkingAttribuut.getWaarde();
        }
        return expressieWaarde;
    }


    /**
     * @param peilDatum peildatum voor testen geldigheid
     * @return indicatie of de dienst geldig is
     */
    public final boolean isDienstGeldigOp(final DatumAttribuut peilDatum) {
        //LET OP: hier zijn geen regels voor, hoeven we niet op te checken
        return true;
    }

    /**
     * @return indicatie of de dienstbundel geblokkeerd is.
     */
    public final boolean isGeblokkeerd() {
        return getIndicatieGeblokkeerd() != null && getIndicatieGeblokkeerd().getWaarde() == Ja.J;
    }

    /**
     * @return geef lo3rubriek namen
     */
    public final List<String> geefLO3rubrieknamen() {
        final List<String> rubriekWaarden = new LinkedList<>();
        for (final DienstbundelLO3Rubriek lo3rubriek : getLo3rubrieken()){
            rubriekWaarden.add(lo3rubriek.getRubriek().getNaam().getWaarde());
        }
        return rubriekWaarden;
    }

    private Set<Element> geefGroepElementenVanAttributen() {
        final Set<Element> groepElementen = new HashSet<>();
        if (dienstbundelGroepen != null) {
            for (final DienstbundelGroep dienstbundelGroep : getDienstbundelGroepen()) {
                for (DienstbundelGroepAttribuutImpl attribuut : dienstbundelGroep.getAttributen()) {
                    final Element attribuutElement = attribuut.getAttribuut();
                    groepElementen.add(attribuutElement.getGroep());
                }
            }
        }
        return groepElementen;
    }

    private boolean magBijhoudingsgegevenGeleverdWorden(final Rol rol) {
        return rol == Rol.BIJHOUDINGSORGAAN_COLLEGE || rol == Rol.BIJHOUDINGSORGAAN_MINISTER;
    }
}
