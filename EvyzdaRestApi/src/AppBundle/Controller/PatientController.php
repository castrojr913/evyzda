<?php

namespace AppBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use AppBundle\Utilities\ResponseHelper;

/**
 * Created: 3/4/18.
 * Author: jesus.castro
 */
class PatientController extends Controller {

    private static $SERVICE = 'service.patient';

    function getPatientsAction(Request $request) {
        $results = $this->get(self::$SERVICE)->getPatients();
        return ResponseHelper::buildResponse($results, 200);
    }

    function postPatientAction(Request $request) {
      $this->get(self::$SERVICE)->savePatient($request, false);
      return ResponseHelper::buildDefaultResponse();
    }

    function putPatientAction(Request $request) {
      $this->get(self::$SERVICE)->savePatient($request, true);
      return ResponseHelper::buildDefaultResponse();
    }

    function deletePatientAction(Request $request) {
      $this->get(self::$SERVICE)->deletePatient($request->get("id"));
      return ResponseHelper::buildDefaultResponse();
    }

}
