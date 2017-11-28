angular.module("json-toon", [])
.factory('RecursionHelper', ['$compile', function($compile){
    return {
        /**
         * Manually compiles the element, fixing the recursion loop.
         * @param element
         * @param [link] A post-link function, or an object with function(s) registered via pre and post properties.
         * @returns An object containing the linking functions.
         */
        compile: function(element, link){
            // Normalize the link parameter
            if(angular.isFunction(link)){
                link = { post: link };
            }

            // Break the recursion loop by removing the contents
            var contents = element.contents().remove();
            var compiledContents;
            return {
                pre: (link && link.pre) ? link.pre : null,
                /**
                 * Compiles and re-adds the contents
                 */
                post: function(scope, element){
                    // Compile the contents
                    if(!compiledContents){
                        compiledContents = $compile(contents);
                    }
                    // Re-add the compiled contents to the element
                    compiledContents(scope, function(clone){
                        element.append(clone);
                    });

                    // Call the post-linking function, if any
                    if(link && link.post){
                        link.post.apply(null, arguments);
                    }
                }
            };
        }
    };
}])

.directive("jsonToon", ['$window', '$location', 'RecursionHelper', '$sce', 'context', function ($window, $location, RecursionHelper, $sce, context) {
        var templateString =
        '<div ng-repeat="sleutel in sleutels(gegevens)" ng-init="waarde = gegevens[sleutel]"> ' +
        '    <div ng-if="isObject(waarde)">' +
        '        <div class="panel panel-default jtsmall">' +
        '            <div class="panel-heading jtsmall">' +
        '                <a class="jtsmall collapsed" data-toggle="collapse" data-target="{{\'#objectWeergave\' + waarde.$indexObject}}" aria-expanded="false" aria-controls="{{\'#objectWeergave\' + waarde.$indexObject}}">{{sleutel}}</a>'+
        '            </div>' +
        '            <div class="collapse" id="{{\'objectWeergave\' + waarde.$indexObject}}">' +
        '                <div class="panel-body">' +
        '                    <json-toon gegevens="waarde"></json-toon>' +
        '                </div>' +
        '            </div>' +
        '        </div>' +
        '    </div> ' +
        '    <div ng-if="isGroep(waarde)">' +
        '        <div class="panel panel-default jtsmall">' +
        '            <div class="panel-heading jtsmall">' +
        '                <label class="jtsmall">{{sleutel}}</label>'+
        '            </div>' +
        '            <div class="panel-body">' +
        '                <json-toon gegevens="waarde"></json-toon>' +
        '            </div>' +
        '        </div>' +
        '    </div> ' +
        '    <div ng-if="isTabel(waarde)">' +
        '        <div class="panel panel-default jtsmall">' +
        '            <div class="panel-heading jtsmall">' +
        '                <a class="jtsmall collapsed" ng-if="waarde.$type === \'tabel\'" data-toggle="collapse" data-target="{{\'#objectWeergave\' + waarde.$indexObject}}" aria-expanded="false" aria-controls="{{\'#objectWeergave\' + waarde.$indexObject}}">{{sleutel}}</a>'+
        '                <label ng-if="waarde.$type === \'tabelGroep\'">{{sleutel}}</label>' +
        '            </div>' +
        '        </div>' +
        '        <div class="{{waarde.$type === \'tabel\' ? \'collapse\' : \'\'}}" id="{{\'objectWeergave\' + waarde.$indexObject}}">' +
        '            <div class="panel-body">' +
        '                <table class="table table-bordered table-striped table-condensed">' +
        '                    <thead>' +
        '                        <tr>' +
        '                            <th ng-repeat="titel in waarde.kolomtitels track by $index" ng-if="toonSleutel(titel)"><span>{{titel}}</span></th>' +
        '                            <th ng-if="waarde.kolomtitels.indexOf(\'$detail\') > -1"></th>' +
        '                        </tr>' +
        '                    </thead>' +
        '                    <tbody>' +
        '                        <tr class="{{record.$css}}" ng-repeat-start="record in waarde.data track by $index">' +
        '                            <td ng-repeat="titel in waarde.kolomtitels track by $index" ng-if="toonSleutel(titel)">' +
        '                                <span ng-switch="record[titel].$type">' +
        '                                    <span ng-switch-when="cssLink">' +
        '                                        <a ng-click="markeerClass(record[titel].link)">{{record[titel].linkTekst}}</a>' +
        '                                    </span>' +
        '				                     <span ng-switch-when="link">' +
        '               				         <a href="{{linkWaardeBuitenDetail(record[titel].link, record[titel].linkId)}}">{{record[titel].linkTekst}}</a>' +
        '   				                 </span>' +
        '                                    <span ng-switch-default>' +
        '                                        {{toonVeldwaarde(record[titel])}}' +
        '                                    </span>' +
        '                                </span>' +
        '                            </td>' +
        '                            <td class="col-xs-1" ng-if="record.$detail"><a class="jtsmall collapsed" data-toggle="collapse" data-target="{{\'#arrayWeergave\' + record.$indexObject}}" aria-expanded="false" aria-controls="{{\'#arrayWeergave\' + record.$indexObject}}">...</a></td>' +
        '                        </tr>' +
        '                        <tr ng-repeat-end class="collapse" id="{{\'arrayWeergave\' + record.$indexObject}}">' +
        '                            <td colspan="100%"><span><json-toon gegevens="record.$detail"></span></json-toon></td>' +
        '                        </tr>' +
        '                    </tbody>' +
        '                </table>' +
        '            </div>' +
        '        </div>' +
        '    </div>' +
        '    <div ng-if="isArray(waarde)">' +
        '        <div class="row">' +
        '            <div class="col-xs-12 col-sm-3 col-md-2"><label>{{sleutel}}</label></div>' +
        '            <div class="col-xs-12 col-sm-9 col-md-10">' +
        '                <div ng-repeat="elem in waarde">' +
        '                    <div class="panel panel-default jtsmall">' +
        '                        <div class="panel-heading jtsmall">' +
        '                            <a class="jtsmall collapsed" data-toggle="collapse" data-target="{{\'#arrayWeergave\' + elem.$indexObject}}" aria-expanded="false" aria-controls="{{\'#arrayWeergave\' + elem.$indexObject}}">{{elem.$sleutel}}</a>' +
        '                        </div>' +
        '                        <div class="collapse" id="{{\'arrayWeergave\' + elem.$indexObject}}">' +
        '                            <div class="panel-body">' +
        '                                <json-toon gegevens="elem"></json-toon>' +
        '                            </div>' +
        '                        </div>' +
        '                    </div>' +
        '                </div>' +
        '            </div>' +
        '        </div>' +
        '    </div> ' +
        '    <div ng-if="isGroepArray(waarde)">' +
        '        <div class="row">' +
        '            <div class="col-xs-12">' +
        '                <div ng-repeat="elem in waarde">' +
        '                    <div class="panel panel-default jtsmall">' +
        '                        <div class="panel-heading jtsmall">' +
        '                            <label class="jtsmall">{{elem.$sleutel}}</label>' +
        '                        </div>' +
        '                        <div class="panel-body">' +
        '                            <json-toon gegevens="elem"></json-toon>' +
        '                        </div>' +
        '                    </div>' +
        '                </div>' +
        '            </div>' +
        '        </div>' +
        '    </div> ' +
        '    <div ng-if="!isObject(waarde) && !isArray(waarde) && !isGroep(waarde) && !isGroepArray(waarde) && !isTabel(waarde)"> ' +
        '        <div ng-if="toonSleutel(sleutel)">' +
        '            <div class="row"> ' +
        '                <div class="col-xs-12 col-sm-3 col-md-2"><label>{{sleutel}}</label></div> ' +
        '                <span ng-switch="waarde.$type">' +
        '                    <span ng-switch-when="cssLink">' +
        '                        <div class="col-xs-12 col-sm-9 col-md-10"><a ng-click="markeerClass(waarde.link)">{{waarde.linkTekst}}</a></div>' +
        '                    </span>' +
        '                    <span ng-switch-when="link">' +
        '                        <div class="col-xs-12 col-sm-9 col-md-10"><a href="{{linkWaarde(waarde.parameterwaarde)}}">{{waarde.linktekst}}</a></div>' +
        '                    </span>' +
        '                    <span ng-switch-default>' +
        '                        <div class="col-xs-12 col-sm-9 col-md-10">{{toonVeldwaarde(waarde)}}</div>' +
        '                    </span>' +
        '                </span>' +
        '            </div> ' +
        '        </div> ' +
        '    </div>' +
        '</div>';

        return {
            restrict: 'E',
            scope: {
                gegevens: '='
            },
            template: templateString,
            compile: function(element) {

                return RecursionHelper.compile(element, function (scope) {
                    scope.sleutels = function(obj) {
                        if (obj instanceof Object) {
                            return Object.keys(obj);
                        } else {
                            return [];
                        }
                    };

                    scope.isObject = function(obj) {
                        return (obj instanceof Object && !(obj instanceof Array) && !obj.$type);
                    };

                    scope.isGroep = function(obj) {
                        return (obj instanceof Object && !(obj instanceof Array) && obj.$type === 'groep');
                    };

                    scope.isArray = function(obj) {
                        return ((obj instanceof Array) && (obj.length === 0 || !scope.isGroep(obj[0])));
                    };

                    scope.isGroepArray = function(obj) {
                        return ((obj instanceof Array) && obj.length > 0 && scope.isGroep(obj[0]));
                    };

                    scope.isTabel = function(obj) {
                        return (obj instanceof Object && !(obj instanceof Array) && (obj.$type === 'tabel' || obj.$type === 'tabelGroep'));
                    };

                    scope.toonSleutel = function(sleutel) {
                        return sleutel.indexOf('$') === -1;
                    };

                    scope.toonVeldwaarde = function(waarde) {
                        if (!(waarde instanceof Object)) {
                            return waarde;
                        } else {
                            return '';
                        }
                    };

                    scope.isLinkVeld = function(waarde) {
                        return (waarde instanceof Object && !(waarde instanceof Array) && (waarde.$type === 'cssLink' || waarde.$type === 'link'));
                    };

                    scope.verzamelInteklappenElementen = function() {
                        var resultaat = [];
                        // collapse in verwijderen om alles in te klappen
                        var inElementen = angular.element(".in");
                        angular.forEach(inElementen, function(element) {
                            var angElement = angular.element(element);
                            var child = angElement.parent().children()[0].children[0].children[0];
                            if (resultaat.indexOf(child) === -1) {
                                resultaat.push(child);
                            }
                        });
                        return resultaat;
                    };

                    scope.klapAllesIn = function() {
                        angular.forEach(scope.verzamelInteklappenElementen(), function(element) {
                            var childElement = angular.element(element);
                            childElement.trigger("click");
                        });
                    };

                    scope.markeerClass = function(cssClass) {
                        // Reeds uitgeklapte elementen
                        var uitgeklapteElementen = scope.verzamelInteklappenElementen();
                        // Nieuw uit te klappen elementen
                        var aanTeKlikkenElementen = [];

                        // verwijder eerdere markeringen
                        var oudeElementen = angular.element(".brp-markeer-on");
                        angular.forEach(oudeElementen, function(element) {
                            var angElement = angular.element(element);
                            angElement.removeClass("brp-markeer-on");
                        });

                        // plaats nieuwe markeringen en verzamel uit te klappen elementen
                        if (cssClass && cssClass !== "") {
                            var zoekClass = '.' + cssClass;
                            var elementen = angular.element(zoekClass);
                            angular.forEach(elementen, function(element) {
                                var angElement = angular.element(element);
                                angElement.addClass("brp-markeer-on");
                                var parent = angElement.parent().parent().parent().parent();
                                var child = parent.parent().children()[0].children[0].children[0];
                                if (aanTeKlikkenElementen.indexOf(child) === -1) {
                                    aanTeKlikkenElementen.push(child);
                                }
                            });
                        }

                        // Onterecht uigeklapte elementen weer inklappen
                        angular.forEach(uitgeklapteElementen.filter(function(el) {
                            return aanTeKlikkenElementen.indexOf(el) === -1;
                        }), function(element) {
                            var childElement = angular.element(element);
                            childElement.trigger("click");
                        });

                        // Nog ingeklapte elementen uit klappen
                        angular.forEach(aanTeKlikkenElementen.filter(function(el) {
                            return uitgeklapteElementen.indexOf(el) === -1;
                        }), function(element) {
                            var childElement = angular.element(element);
                            childElement.trigger("click");
                        });
                        $window.scrollTo(0,0);
                    };

                    scope.linkWaarde = function(idWaarde) {
                        return $window.location.pathname + '#' + $location.path() + '/' + idWaarde;
                    };

                    scope.linkWaardeBuitenDetail = function(link, linkId) {
                    	if(context.vorig[linkId] === undefined) {
                	    context.vorig[linkId]=$location.path() + '/' + context.params.id;
	                }
                    	return $window.location.pathname + '#/' + link + '?refid=' + linkId;
                    };
                });
            }
        };
    }]);
