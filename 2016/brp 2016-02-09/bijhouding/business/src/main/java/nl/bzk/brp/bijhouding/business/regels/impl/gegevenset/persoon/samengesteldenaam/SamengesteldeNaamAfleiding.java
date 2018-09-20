/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.samengesteldenaam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import nl.bzk.brp.bijhouding.business.regels.AbstractAfleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.AfleidingResultaat;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.naamgebruik.NaamgebruikAfleiding;
import nl.bzk.brp.bijhouding.business.util.Geldigheidsperiode;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.MaterieleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.AbstractMaterieelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;
import org.apache.commons.collections.CollectionUtils;

/**
 * Implementatie voor de afleidingsregel voor het afleiden van samengestelde naam
 * uit voornamen en geslachtsnaam componenten.
 */
//TODO: Generieke super klasse indien meerdere van dergelijke afleidingen gemaakt worden.
// Idee: algemene flow in super klasse modelleren:
// 1. Precondities voor afleiding
// 2. Verzamel historie die als input dient
// 3. Check of de actie daadwerkelijk iets gewijzigd heeft in de lijst van 2.
// 4. Bereken de periodes die opnieuw afgeleid moeten worden
// 5. Doe voor elk van de periodes van 4. de daadwerkelijke afleiding
public class SamengesteldeNaamAfleiding extends AbstractAfleidingsregel<PersoonHisVolledig> {

    /**
     * Forwarding constructor.
     *
     * @param persoon de persoon
     * @param actie actie
     */
    public SamengesteldeNaamAfleiding(final PersoonHisVolledig persoon, final ActieModel actie) {
        super(persoon, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00003a;
    }

    @Override
    public AfleidingResultaat leidAf() {
        final PersoonHisVolledig persoonHisVolledig = getModel();
        // Afleiding is alleen geldig voor ingeschrevenen.
        if (persoonHisVolledig.getSoort().getWaarde() == SoortPersoon.INGESCHREVENE) {
            final Set<AbstractMaterieelHistorischMetActieVerantwoording> alleMaterieleHistorie = new HashSet<>();
            // Bereken de DagDeg's waarvoor de afleiding moet worden doorgevoerd.
            final List<Geldigheidsperiode> opnieuwAfTeLeidenPeriodes = new ArrayList<>();

            // Aanname: we gaan uit van maximaal 1 geslachtsnaam component.
            alleMaterieleHistorie.addAll(persoonHisVolledig.getGeslachtsnaamcomponenten().iterator().next().
                    getPersoonGeslachtsnaamcomponentHistorie().getHistorie());
            // Voeg de historie van alle voornamen toe.
            for (final PersoonVoornaamHisVolledig persoonVoornaamHisVolledig : persoonHisVolledig.getVoornamen()) {
                alleMaterieleHistorie.addAll(persoonVoornaamHisVolledig.getPersoonVoornaamHistorie().getHistorie());
            }

            // Alleen doorgaan als er daadwerkelijk iets veranderd is in de input voor de afleiding
            // òf als de samengestelde naam als groep in het bericht zat.
            if (historieIsGeraakt(alleMaterieleHistorie) || samengesteldeNaamZatInBericht()) {
                // Bereken de periodes waarover de afleiding moet gaan.
                opnieuwAfTeLeidenPeriodes.addAll(berekenDagDegPeriodesVoorAfleiding(alleMaterieleHistorie));

                for (final Geldigheidsperiode opnieuwAfTeLeidenPeriode : opnieuwAfTeLeidenPeriodes) {
                    final HisPersoonSamengesteldeNaamModel nieuweSamengesteldeNaam =
                            leidAfVoorPeriode(opnieuwAfTeLeidenPeriode, persoonHisVolledig);
                    persoonHisVolledig.getPersoonSamengesteldeNaamHistorie().voegToe(nieuweSamengesteldeNaam);
                }
            }
        }

        return vervolgAfleidingen(new NaamgebruikAfleiding(persoonHisVolledig, getActie()));
    }

    /**
     * Geeft aan of de samengestelde naam als groep in het bericht aanwezig was.
     * Dit is zo als het actuele record de huidige actie als actie inhoud heeft.
     *
     * @return of de samengestelde naam als groep in het bericht aanwezig was
     */
    private boolean samengesteldeNaamZatInBericht() {
        //TODO: Dit moet wellicht aangepast worden als we ook niet-actualiseringen (correcties) kunnen doen.
        //Dan kan niet altijd getActueleRecord() gebruikt worden,
        //maar moet naar het toegevoegde record gezocht worden in de hele historie
        return getModel().getPersoonSamengesteldeNaamHistorie().getActueleRecord()
                .getVerantwoordingInhoud() == getActie();
    }

    /**
     * Voer de afleiding uit voor de opgegeven periode.
     *
     * @param afleidingsPeriode de periode
     * @param persoonHisVolledig de persoon waarvan de samengestelde naam moet worden afgeleid
     * @return afgeleide his samengestelde naam
     */
    private HisPersoonSamengesteldeNaamModel leidAfVoorPeriode(final Geldigheidsperiode afleidingsPeriode,
                                                               final PersoonHisVolledig persoonHisVolledig)
    {
        final PersoonGeslachtsnaamcomponentHisVolledig geslComp =
                persoonHisVolledig.getGeslachtsnaamcomponenten().iterator().next();
        final PersoonSamengesteldeNaamGroepBericht nieuweSamengesteldeNaamGroep =
                new PersoonSamengesteldeNaamGroepBericht();
        nieuweSamengesteldeNaamGroep.setVoornamen(leidAfVoornamen(persoonHisVolledig.getVoornamen(),
                                                                  afleidingsPeriode));

        //We gaan momenteel uit van één geslachtsnaamcomponent per persoon in de BRP!
        final HisPersoonGeslachtsnaamcomponentModel geslCompVoorDagDeg =
                geslComp.getPersoonGeslachtsnaamcomponentHistorie().getHistorieRecord(
                        new DatumAttribuut(afleidingsPeriode.getDatumAanvangGeldigheid()), DatumTijdAttribuut.nu());

        if (geslCompVoorDagDeg != null) {
            nieuweSamengesteldeNaamGroep.setAdellijkeTitel(geslCompVoorDagDeg.getAdellijkeTitel());
            nieuweSamengesteldeNaamGroep.setGeslachtsnaamstam(new GeslachtsnaamstamAttribuut(
                    geslCompVoorDagDeg.getStam().getWaarde()));
            nieuweSamengesteldeNaamGroep.setPredicaat(geslCompVoorDagDeg.getPredicaat());
            nieuweSamengesteldeNaamGroep.setVoorvoegsel(geslCompVoorDagDeg.getVoorvoegsel());
            nieuweSamengesteldeNaamGroep.setScheidingsteken(geslCompVoorDagDeg.getScheidingsteken());
        }

        //Afgeleid vlag op Ja zetten:
        nieuweSamengesteldeNaamGroep.setIndicatieAfgeleid(JaNeeAttribuut.JA);

        //De indicatie namenreeks vlag is of opgegeven in het bericht of niet.
        //Indien het opgegeven is in het bericht dan nemen we deze over van het actuele record, want het actuele
        //record bevat altijd datgene wat in het bericht staat, anders laten we dit vlaggetje gewoon staan zoals
        //het was, maar dit komt op hetzelfde neer; neem het over van het actuele record.
        //TODO: Dit moet wellicht aangepast worden als we ook niet-actualiseringen (correcties) kunnen doen.
        //Dan kan niet altijd getActueleRecord() gebruikt worden,
        //maar moet naar het toegevoegde record gezocht worden in de hele historie
        nieuweSamengesteldeNaamGroep.setIndicatieNamenreeks(
                persoonHisVolledig.getPersoonSamengesteldeNaamHistorie().getActueleRecord().getIndicatieNamenreeks()
        );

        //Historie velden:
        nieuweSamengesteldeNaamGroep.setDatumAanvangGeldigheid(afleidingsPeriode.getDatumAanvangGeldigheid());
        nieuweSamengesteldeNaamGroep.setDatumEindeGeldigheid(afleidingsPeriode.getDatumEindeGeldigheid());

        return new HisPersoonSamengesteldeNaamModel(persoonHisVolledig, nieuweSamengesteldeNaamGroep,
                                                    nieuweSamengesteldeNaamGroep, getActie());
    }

    /**
     * Deze functie berekent voor welke periodes de samengestelde naam opnieuw moet worden afgeleid.
     *
     * @param alleMaterieleHistorie lijst van alle historie van groepen die bij de afleiding
     *                                       betrokken zijn.
     * @return lijst van periodes waarvoor de afleiding moet worden gedaan
     */
    private List<Geldigheidsperiode> berekenDagDegPeriodesVoorAfleiding(
            final Set<AbstractMaterieelHistorischMetActieVerantwoording> alleMaterieleHistorie)
    {
        final List<Geldigheidsperiode> geldigheidsperiodes = new ArrayList<>();
        final Set<DatumEvtDeelsOnbekendAttribuut> datums = new TreeSet<>();
        final MaterieleHistorieSet<AbstractMaterieelHistorischMetActieVerantwoording> nietVervallenHistorie =
                new MaterieleHistorieSetImpl<>(alleMaterieleHistorie);

        final ActieModel actieModel = getActie();
        for (final MaterieelHistorisch materieleHistorieEntiteit
                : nietVervallenHistorie.selecteerOverlapteActueleRecords(actieModel))
        {
            DatumEvtDeelsOnbekendAttribuut
                    datumAanvang = materieleHistorieEntiteit.getMaterieleHistorie().getDatumAanvangGeldigheid();
            if (datumAanvang.voor(actieModel.getDatumAanvangGeldigheid())) {
                datumAanvang = actieModel.getDatumAanvangGeldigheid();
            }
            datums.add(datumAanvang);

            final DatumEvtDeelsOnbekendAttribuut  datumEinde =
                    materieleHistorieEntiteit.getMaterieleHistorie().getDatumEindeGeldigheid();
            if (datumEinde != null) {
                datums.add(datumEinde);
            }
        }

        final Iterator<DatumEvtDeelsOnbekendAttribuut> datumAanvangIter = datums.iterator();
        final Iterator<DatumEvtDeelsOnbekendAttribuut> datumEindeIter = datums.iterator();

        datumEindeIter.next();
        while (datumAanvangIter.hasNext()) {
            DatumEvtDeelsOnbekendAttribuut datumEinde = null;
            if (datumEindeIter.hasNext()) {
                datumEinde = datumEindeIter.next();
            }
            geldigheidsperiodes.add(new Geldigheidsperiode(datumAanvangIter.next(), datumEinde));
        }

        return geldigheidsperiodes;
    }

    /**
     * Leid een nieuwe voornaam af aan de hand van een lijst van PersoonVoornaam objecten.
     *
     * @param voornamen de voornaam objecten
     * @param afleidingsPeriode de afleidings periode
     * @return de afgeleide voornaam.
     */
    private VoornamenAttribuut leidAfVoornamen(final Set<? extends PersoonVoornaamHisVolledig> voornamen,
                                      final Geldigheidsperiode afleidingsPeriode)
    {
        VoornamenAttribuut voornaamAttribuut = null;
        if (CollectionUtils.isNotEmpty(voornamen)) {
            final StringBuilder voornaamAfgeleid = new StringBuilder();

            // Voornamen moeten gesorteerd worden op volgnummer, en spatie as scheidingsteken.
            final SortedSet<PersoonVoornaamHisVolledig> gesorteerdeVoornamen =
                new TreeSet<PersoonVoornaamHisVolledig>(new Comparator<PersoonVoornaamHisVolledig>() {

                    @Override
                    public int compare(final PersoonVoornaamHisVolledig persVoornaam1,
                                       final PersoonVoornaamHisVolledig persVoornaam2)
                    {
                        return persVoornaam1.getVolgnummer().getWaarde()
                                .compareTo(persVoornaam2.getVolgnummer().getWaarde());
                    }
                });

            // Sorteer de voornamen op volgnummer.
            for (final PersoonVoornaamHisVolledig voornaam : voornamen) {
                gesorteerdeVoornamen.add(voornaam);
            }

            for (final PersoonVoornaamHisVolledig voornaam : gesorteerdeVoornamen) {
                final HisPersoonVoornaamModel recordVoorDagDeg =
                        voornaam.getPersoonVoornaamHistorie().getHistorieRecord(
                                new DatumAttribuut(afleidingsPeriode.getDatumAanvangGeldigheid()), DatumTijdAttribuut.nu());

                if (recordVoorDagDeg != null) {
                    if (voornaamAfgeleid.length() > 0) {
                        voornaamAfgeleid.append(" ");
                    }
                    voornaamAfgeleid.append(recordVoorDagDeg.getNaam().getWaarde());
                }
            }
            // Alleen daadwerkelijk aanmaken als er geldige voornamen gevonden zijn voor de periode.
            if (voornaamAfgeleid.length() > 0) {
                voornaamAttribuut = new VoornamenAttribuut(voornaamAfgeleid.toString());
            }
        }
        return voornaamAttribuut;
    }

    /**
     * Kijkt of er een historie record is in de meegegeven set, die een
     * actie inhoud, actie aanpassing geldigheid of actie verval
     * heeft die gelijk is aan de actie voor deze afleiding.
     *
     * @param historieSet de historie set
     * @return gevonden of niet
     */
    private boolean historieIsGeraakt(final Set<AbstractMaterieelHistorischMetActieVerantwoording> historieSet) {
        boolean resultaat = false;
        for (final MaterieelVerantwoordbaar materieleHistorieEntiteit : historieSet) {
            if (materieleHistorieEntiteit.getVerantwoordingInhoud() == getActie()
                    || materieleHistorieEntiteit.getVerantwoordingAanpassingGeldigheid() == getActie()
                    || materieleHistorieEntiteit.getVerantwoordingVerval() == getActie())
            {
                resultaat = true;
                break;
            }
        }
        return resultaat;
    }

}
