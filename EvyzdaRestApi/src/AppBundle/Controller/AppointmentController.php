<?php
namespace AppBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use AppBundle\Utilities\ResponseHelper;
use AppBundle\Utilities\LogHelper;

/**
 * Created: 3/4/18.
 * Author: jesus.castro
 */
class AppointmentController extends Controller {

  private static $SERVICE = 'service.appointment';
  private static $SERVICE_PATIENT = 'service.patient';

  function getAppointmentsAction(Request $request) {
      $results = $this->get(self::$SERVICE)->getAppointments();
      return ResponseHelper::buildResponse($results, 200);
  }

  function postAppointmentAction(Request $request) {
    return $this->handlePostOrPutRequest($request, false);
  }

  function putAppointmentAction(Request $request) {
    return $this->handlePostOrPutRequest($request, true);
  }

  function deleteAppointmentAction(Request $request) {
    $this->get(self::$SERVICE)->deleteAppointment($request->get("id"));
    return ResponseHelper::buildDefaultResponse();
  }

  private function handlePostOrPutRequest(Request $request, $isUpdate) {
    $service = $this->get(self::$SERVICE);
    $isValid = $service->validateAppointmentTime(
      $isUpdate ? $request->get("id") : PHP_INT_MIN, $request->get("time"));
    if ($isValid) {
        $service->setPatientService($this->get(self::$SERVICE_PATIENT));
        $service->saveAppointment($request, $isUpdate);
    }
    $msg = $isValid ? "Ok" : "this date is already registered. Please, select another date.";
    return ResponseHelper::buildResponse(array("message" => $msg), $isValid ? 200 : 400);
  }

}
