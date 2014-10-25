/* global angular,hljs */
(function () {
    var springCloudAws = angular.module('SpringCloudAws', []);

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

        initMessageToProcess();

        self.sendMessage = function () {
            SqsService.sendMessage(self.model.messageToProcess);
            initMessageToProcess();
        };

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

    springCloudAws.run(function () {
        hljs.initHighlightingOnLoad();
    });
}());