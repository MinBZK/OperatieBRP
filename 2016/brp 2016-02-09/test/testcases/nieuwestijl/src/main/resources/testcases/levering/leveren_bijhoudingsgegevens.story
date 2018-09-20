Meta:
@sprintnummer               82
@epic                       Levering juridische PL
@auteur                     luwid
@jiraIssue                  TEAMBRP-3515
@regels                     R2002
@status                     Onderhanden

Narrative:  Als stelselbeheerder
            wil ik dat afnemers geen bijhoudingsgegevens ontvangen
            zodat ik kan vermijden dat gevoelige gegevens aan afnemers worden verstrekt

!-- free text

Scenario: 1. Matilda is geboren. Een afnemer die geen bijhouder is krijgt bijhoudingsgegeven (GerelateerdeOuder.Ouderschap.IndicatieAdresgevendeOuder
van gerelateerde moeder) NIET te zien.

Given leveringsautorisatie uit /levering_autorisaties/Autorisatie_op_een_bijhoudingsgegeven_voor_geen_bijhouder
Given de personen 212208937, 727750537, 793304921 zijn verwijderd
Given de standaardpersoon Matilda met bsn 793304921 en anr 1746150497 zonder extra gebeurtenissen

When voor persoon 793304921 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Autorisatie op een bijhoudingsgegeven voor geen bijhouder is ontvangen en wordt bekeken

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep      | nummer | attribuut                         | aanwezig |
| ouderschap | 1      | indicatieOuderUitWieKindIsGeboren | nee      |
| ouderschap | 2      | indicatieOuderUitWieKindIsGeboren | nee      |


Scenario: 2. Matilda is geboren. Een afnemer met de rol Bijhoudingsorgaan College krijgt bijhoudingsgegeven (GerelateerdeOuder.Ouderschap
.IndicatieAdresgevendeOuder van gerelateerde moeder) te zien

Given leveringsautorisatie uit /levering_autorisaties/Autorisatie_op_een_bijhoudingsgegeven_voor_geen_bijhouder
Given de personen 212208937, 727750537, 679877897 zijn verwijderd
Given de standaardpersoon Matilda met bsn 679877897 en anr 1752095186 zonder extra gebeurtenissen

When voor persoon 679877897 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Autorisatie op een bijhoudingsgegeven voor bijhouder is ontvangen en wordt bekeken

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep      | nummer | attribuut                         | aanwezig |
| ouderschap | 1      | indicatieOuderUitWieKindIsGeboren | nee      |
| ouderschap | 2      | indicatieOuderUitWieKindIsGeboren | ja       |

Scenario: 3. Matilda is geboren. Een afnemer met de rol Bijhoudingsorgaan Minister krijgt bijhoudingsgegeven (GerelateerdeOuder.Ouderschap
.IndicatieAdresgevendeOuder van gerelateerde moeder) te zien

Given leveringsautorisatie uit /levering_autorisaties/Autorisatie_op_een_bijhoudingsgegeven_voor_geen_bijhouder
Given de personen 212208937, 727750537, 230542049 zijn verwijderd
Given de standaardpersoon Matilda met bsn 230542049 en anr 1357932530 zonder extra gebeurtenissen

When voor persoon 230542049 wordt de laatste handeling geleverd
When het volledigbericht voor leveringsautorisatie Autorisatie op een bijhoudingsgegeven voor bijhouder is ontvangen en wordt bekeken

Then hebben attributen in voorkomens de volgende aanwezigheid:
| groep      | nummer | attribuut                         | aanwezig |
| ouderschap | 1      | indicatieOuderUitWieKindIsGeboren | nee      |
| ouderschap | 2      | indicatieOuderUitWieKindIsGeboren | ja       |
