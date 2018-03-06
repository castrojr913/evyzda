<?php

namespace AppBundle\Model;

use AppBundle\Utilities\LogHelper;
use AppBundle\Entity\Appointment;
use AppBundle\Utilities\DateHelper;

/**
 * Created: 3/4/18.
 * Author: jesus.castro
 */
class AppointmentService extends BaseService {

  private $patientService;

  function __construct($entityManager) {
      parent::__construct($entityManager, 'AppBundle:Appointment');
  }

  function setPatientService($patientService) {
    $this->patientService = $patientService;
  }

  function getAppointments() {
      return $this->repository->findAppointments();
  }

  function saveAppointment($values, $isUpdate){
    $entity = new Appointment();
    $patientEntity = $this->patientService->repository->find($values->get('patientId'));
    if ($isUpdate) $entity->setId($values->get('id'));
    $entity->setDescription($values->get('description'));
    $entity->setTime(DateHelper::parseDateTime($values->get('time'), false));
    $entity->setCategory($values->get('category')); // Valores: 1, 2
    $entity->setPatient($patientEntity);
    $this->commit($entity, $isUpdate);
  }

  function deleteAppointment($id) {
    $this->delete($this->repository->find($id));
  }

  function validateAppointmentTime($id, $dateTimeText) {
    $dt = DateHelper::parseDateTime($dateTimeText, false);
    $entities = $this->getAppointments();
    foreach ($entities as $key => $entity) {
      // Comparamos el formato yyyy-mm-dd hh:mm de cada una de las fechas
      // registradas. Se evitar compararse consigo mismo (la misma id).
      if (strcmp($entity["id"], $id) != 0) {
        $date = array(0 => $entity["time"]->format(DateHelper::$DF_YMD_HM),
                      1 => $dt->format(DateHelper::$DF_YMD_HM));
        if ($date[0] === $date[1]) return false;
      }
    }
    return true;
  }

}
