angular.module('fieldControllers', [])
	.controller('AanduidingInhoudingVermissingReisdocumentFieldController', ['$scope', 'AanduidingInhoudingVermissingReisdocument',
   		function($scope, Resource) {
			$scope.options = Resource.resource().query();
	}])
	.controller('AangeverFieldController', ['$scope', 'Aangever',
		function($scope, Resource) {
		$scope.options = Resource.resource().query();
	}])
	.controller('AdellijkeTitelFieldController', ['$scope', 'AdellijkeTitel',
		function($scope, Resource) {
		$scope.options = Resource.resource().query();
	}])
	.controller('GemeenteFieldController', ['$scope', 'Gemeente',
		function($scope, Resource) {
		$scope.options = Resource.resource().query();
	}])
	.controller('GeslachtsaanduidingFieldController', ['$scope', 'Geslachtsaanduiding',
		function($scope, Resource) {
		$scope.options = Resource.resource().query();
	}])
	.controller('NadereBijhoudingsaardFieldController', ['$scope', 'NadereBijhoudingsaard',
 		function($scope, Resource) {
 		$scope.options = Resource.resource().query();
 	}])
	.controller('PartijFieldController', ['$scope', 'Partij',
		function($scope, Resource) {
		$scope.options = Resource.resource().query();
	}])
	.controller('PredicaatFieldController', ['$scope', 'Predicaat',
		function($scope, Resource) {
		$scope.options = Resource.resource().query();
	}])
	.controller('RedenEindeRelatieFieldController', ['$scope', 'RedenEindeRelatie',
		function($scope, Resource) {
		$scope.options = Resource.resource().query();
	}])
	.controller('RedenWijzigingVerblijfFieldController', ['$scope', 'RedenWijzigingVerblijf',
		function($scope, Resource) {
		$scope.options = Resource.resource().query();
	}])
	.controller('SoortDocumentFieldController', ['$scope', 'SoortDocument',
		function($scope, Resource) {
		$scope.options = Resource.resource().query();
	}])
	.controller('SoortNederlandsReisdocumentFieldController', ['$scope', 'SoortNederlandsReisdocument',
		function($scope, Resource) {
		$scope.options = Resource.resource().query();
	}])
	.controller('SoortPartijFieldController', ['$scope', 'SoortPartij',
		function($scope, Resource) {
		$scope.options = Resource.resource().query();
	}])
	.controller('SoortRechtsgrondFieldController', ['$scope', 'SoortRechtsgrond',
		function($scope, Resource) {
		$scope.options = Resource.resource().query();
	}])
;