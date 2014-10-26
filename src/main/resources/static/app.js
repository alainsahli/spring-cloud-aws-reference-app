/* global angular,hljs */
(function () {
    var springCloudAws = angular.module('SpringCloudAws', ['ngRoute']);

    // Global stuff
    springCloudAws.directive('active', function ($location) {
        return {
            link: function (scope, element) {
                function makeActiveIfMatchesCurrentPath () {
                    if ($location.path().indexOf(element.find('a').attr('href').substr(1)) > -1) {
                        element.addClass('active');
                    } else {
                        element.removeClass('active');
                    }
                }

                scope.$on('$routeChangeSuccess', function () {
                    makeActiveIfMatchesCurrentPath();
                });
            }
        };
    });

    springCloudAws.directive('code', function () {
        return {
            restrict: 'E',
            link: function (scope, element) {
                hljs.highlightBlock(element[0]);
            }
        }
    });

    // SQS
    springCloudAws.service('SqsService', function ($http) {
        this.sendMessage = function (message) {
            return $http.post('sqs/message-processing-queue', message);
        };
    });

    springCloudAws.controller('SqsCtrl', function (SqsService, $scope) {
        var self = this;
        self.model = {};
        self.model.responses = [];

        function initMessageToProcess() {
            self.model.messageToProcess = {
                message: undefined,
                priority: 2
            };
        }

        self.sendMessage = function () {
            SqsService.sendMessage(self.model.messageToProcess);
            initMessageToProcess();
        };

        function initView () {
            initMessageToProcess();

            var sock = new SockJS('/sqs-messages');
            sock.onmessage = function (e) {
                var jsonResponse = JSON.parse(e.data);
                self.model.responses.reverse().push(jsonResponse);

                if (self.model.responses.length > 10) {
                    self.model.responses = self.model.responses.slice(self.model.responses.length - 10);
                }

                self.model.responses = self.model.responses.reverse();
                $scope.$apply();
            };
        }
    });

    springCloudAws.filter('priority', function () {
        return function(input) {
            switch (input) {
                case 1:
                    return 'Low';
                case 2:
                    return 'Medium';
                case 3:
                    return 'High';
            }
        }
    });

    springCloudAws.config(function ($routeProvider) {
        $routeProvider.when('/home', {templateUrl: 'pages/home.tpl.html'});
        $routeProvider.when('/sqs', {templateUrl: 'pages/sqs.tpl.html'});
        $routeProvider.when('/sns', {templateUrl: 'pages/sns.tpl.html'});
        $routeProvider.when('/elasticache', {templateUrl: 'pages/elasticache.tpl.html'});
        $routeProvider.when('/rds', {templateUrl: 'pages/rds.tpl.html'});
        $routeProvider.when('/ec2', {templateUrl: 'pages/ec2.tpl.html'});
        $routeProvider.otherwise({redirectTo: '/home'});
    });
}());