(function (angular) {
    var directives = angular.module('beheerApp.directives');

    directives.directive('uiRequired', function () {
        return {
            require: 'ngModel',
            link: function (scope, elm, attrs, ctrl) {
                ctrl.$validators.required = function (modelValue, viewValue) {
                    return !((viewValue && viewValue.length === 0) && attrs.uiRequired === 'true');
                };

                attrs.$observe('uiRequired', function () {
                    ctrl.$setValidity('required', !(attrs.uiRequired === 'true' && ctrl.$viewValue && ctrl.$viewValue.length === 0));
                });
            }
        };
    });
})(angular);