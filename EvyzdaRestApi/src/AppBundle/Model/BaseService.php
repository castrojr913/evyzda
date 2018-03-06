<?php

namespace AppBundle\Model;

/**
 * Created: 3/5/18.
 * Author: jesus.castro
 */
class BaseService {

  protected $entityManager;
  protected $repository;

  function __construct($entityManager, $alias) {
      $this->entityManager = $entityManager;
      $this->repository = $entityManager->getRepository($alias);
  }

  protected function commit($entity, $isUpdate) {
    if ($isUpdate) $this->entityManager->merge($entity);
    else $this->entityManager->persist($entity);
    $this->entityManager->flush();
  }

  protected function delete($entity) {
    $this->entityManager->remove($entity);
    $this->entityManager->flush();
  }

}
