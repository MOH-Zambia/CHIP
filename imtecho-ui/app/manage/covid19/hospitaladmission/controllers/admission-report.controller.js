(function () {
    function Covid19AdmissionReport(QueryDAO, Mask, GeneralUtil, $scope, toaster, $stateParams, $filter) {
        let ctrl = this;
        ctrl.appName = GeneralUtil.getAppName();
        ctrl.isPrintClicked = false;
        ctrl.formSDlabTestDateFormat = 'dd/MM/yyyy hh:mm a';
        ctrl.imagesPath = GeneralUtil.getImagesPath();

        const _init = function () {
            var d = $filter('date')(new Date(), ctrl.formSDlabTestDateFormat);
            ctrl.footer = `This report was generated by the ${ctrl.appName} COVID2019 Response System, Dept of Health and Family Welfare, Govt. of Gujarat.<br/>${d}`;
            if (!!$stateParams.id) {
                ctrl.loadAdmission($stateParams.id);
            } else {
                toaster.pop('error', 'Record not found');
            }
        };

        ctrl.loadAdmission = (admissionId) => {
            ctrl.currentAdmissionDisplayMember = {};
            Mask.show();
            QueryDAO.execute({
                code: 'covid19_retrieve_admission_details_for_print',
                parameters: {
                    admissionId: admissionId
                }
            }).then((response) => {
                Object.assign(ctrl.currentAdmissionDisplayMember, response.result[0]);
                ctrl.currentAdmissionDisplayMember.currentDate = new Date();
                var d = ctrl.currentAdmissionDisplayMember.currentDate - ctrl.currentAdmissionDisplayMember.admissionDate;
                if (d > 0) {
                    ctrl.currentAdmissionDisplayMember.days = d / (1000 * 3600 * 24);
                } else {
                    ctrl.currentAdmissionDisplayMember.days = 0;
                }
                ctrl.currentAdmissionDisplayMember.days = Math.trunc(ctrl.currentAdmissionDisplayMember.days);
                if (ctrl.currentAdmissionDisplayMember.status === 'CONFORMED') {
                    ctrl.currentAdmissionDisplayMember.status = 'CONFIRMED';
                }
                ctrl.currentAdmissionDisplayMember.symptomsArray = [];
                ctrl.currentAdmissionDisplayMember.comorbidityArray = [];

                if (ctrl.currentAdmissionDisplayMember.fever) {
                    ctrl.currentAdmissionDisplayMember.symptomsArray.push('Fever');
                }
                if (ctrl.currentAdmissionDisplayMember.cough) {
                    ctrl.currentAdmissionDisplayMember.symptomsArray.push('Cough');
                }
                if (ctrl.currentAdmissionDisplayMember.breathlessness) {
                    ctrl.currentAdmissionDisplayMember.symptomsArray.push('Breathlessness');
                }
                if (ctrl.currentAdmissionDisplayMember.sari) {
                    ctrl.currentAdmissionDisplayMember.symptomsArray.push('SARI');
                }
                if (ctrl.currentAdmissionDisplayMember.hiv) {
                    ctrl.currentAdmissionDisplayMember.comorbidityArray.push('HIV');
                }
                if (ctrl.currentAdmissionDisplayMember.heartPatient) {
                    ctrl.currentAdmissionDisplayMember.comorbidityArray.push('Heart Patient');
                }
                if (ctrl.currentAdmissionDisplayMember.diabetes) {
                    ctrl.currentAdmissionDisplayMember.comorbidityArray.push('Diabetes');
                }
                if (ctrl.currentAdmissionDisplayMember.copd) {
                    ctrl.currentAdmissionDisplayMember.comorbidityArray.push('COPD');
                }
                if (ctrl.currentAdmissionDisplayMember.hypertension) {
                    ctrl.currentAdmissionDisplayMember.comorbidityArray.push('Hypertension');
                }
                if (ctrl.currentAdmissionDisplayMember.renalCondition) {
                    ctrl.currentAdmissionDisplayMember.comorbidityArray.push('Renal Condition');
                }
                if (ctrl.currentAdmissionDisplayMember.immunocompromized) {
                    ctrl.currentAdmissionDisplayMember.comorbidityArray.push('Immunocomprized');
                }
                if (ctrl.currentAdmissionDisplayMember.maligancy) {
                    ctrl.currentAdmissionDisplayMember.comorbidityArray.push('Maligancy');
                }
                if (ctrl.currentAdmissionDisplayMember.gender === 'M') {
                    ctrl.currentAdmissionDisplayMember.genderText = 'Male';
                } else if (ctrl.currentAdmissionDisplayMember.gender === 'F') {
                    ctrl.currentAdmissionDisplayMember.genderText = 'Female';
                } else if (ctrl.currentAdmissionDisplayMember.gender === 'T') {
                    ctrl.currentAdmissionDisplayMember.genderText = 'Transgender';
                }

                ctrl.currentAdmissionDisplayMember.symptoms = ctrl.currentAdmissionDisplayMember.symptomsArray.join(', ');
                ctrl.currentAdmissionDisplayMember.comorbidity = ctrl.currentAdmissionDisplayMember.comorbidityArray.join(', ');
                Mask.show();
                QueryDAO.execute({
                    code: 'covid19_get_admitted_patient_daily_status_print',
                    parameters: {
                        admissionId: admissionId
                    }
                }).then((response1) => {
                    ctrl.currentAdmissionDisplayMember.dailyStatus = [];
                    ctrl.currentAdmissionDisplayMember.dailyStatus = response1.result;
                    Mask.show();
                    QueryDAO.execute({
                        code: 'covid19_get_lab_test_for_admission_print',
                        parameters: {
                            admissionId: admissionId
                        }
                    }).then((response2) => {
                        ctrl.currentAdmissionDisplayMember.labResults = [];
                        ctrl.currentAdmissionDisplayMember.labResults = response2.result;
                        ctrl.downloadPdf();
                    }).catch((error) => {
                        GeneralUtil.showMessageOnApiCallFailure(error);
                    }).finally(() => {
                        Mask.hide();
                    });
                }).catch((error) => {
                    GeneralUtil.showMessageOnApiCallFailure(error);
                }).finally(() => {
                    Mask.hide();
                });
            }).catch((error) => {
                GeneralUtil.showMessageOnApiCallFailure(error);
            }).finally(() => {
                Mask.hide();
            });
        };

        ctrl.downloadPdf = function () {
            ctrl.isPrintClicked = true;
            $("thead tr th").css("position", "inherit");
            Mask.show();
            $('#printableBlock').printThis({
                importCSS: false,
                loadCSS: 'styles/css/printable.css',
                header: '',
                footer: '',
                base: "./",
                printDelay: 333,
                pageTitle: 'Patient Name : ' + ctrl.currentAdmissionDisplayMember.name,
                afterPrint: function () {
                    $scope.$apply(function () {
                        Mask.hide();
                        ctrl.isPrintClicked = false;
                        window.close();
                    });
                }
            });
        };

        _init();
    }
    angular.module('imtecho.controllers').controller('Covid19AdmissionReport', Covid19AdmissionReport);
})();
