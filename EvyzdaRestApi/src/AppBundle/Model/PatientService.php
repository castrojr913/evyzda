<?php

namespace AppBundle\Model;

use AppBundle\Entity\Patient;
use AppBundle\Utilities\LogHelper;
use AppBundle\Utilities\DateHelper;

/**
 * Created: 3/5/18.
 * Author: jesus.castro
 */
class PatientService extends BaseService {

    function __construct($entityManager) {
        parent::__construct($entityManager, 'AppBundle:Patient');
    }

    function getPatients() {
        return $this->repository->findPatients();
    }

    function savePatient($values, $isUpdate){
      $entity = new Patient();
      if ($isUpdate) $entity->setId($values->get('id'));
      $entity->setName($values->get('name'));
      $entity->setBirthday(DateHelper::parseDateTime($values->get('birthdate'), false));
      $entity->setPhone($values->get('phone'));
      $this->commit($entity, $isUpdate);
    }

    function deletePatient($id) {
      $this->delete($this->repository->find($id));
    }

}
