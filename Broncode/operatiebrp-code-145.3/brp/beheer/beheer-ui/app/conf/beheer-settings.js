angular.module('BeheerInstelling', [])
.service("instelling", [function() {
    this.size = '20';
    this.api_locatie = 'http://localhost:8080/brp-beheer/';
}]);
