<?php

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;

/**
 * Patient
 *
 * @ORM\Table(name="patient")
 * @ORM\Entity(repositoryClass="AppBundle\Repository\PatientRepository")
 */
class Patient
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var string
     *
     * @ORM\Column(name="name", type="string", length=500, nullable=false)
     */
    private $name;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="birthday", type="date", nullable=false)
     */
    private $birthday;

    /**
     * @var string
     *
     * @ORM\Column(name="phone", type="string", length=50, nullable=false)
     */
    private $phone;

    /**
     * @ORM\OneToMany(targetEntity="Appointment", mappedBy="patient")
     * @var Collection
     **/
    private $appointments;

    // Getters / Setters

   public function getId()
   {
       return $this->id;
   }

   public function setId($id)
   {
       $this->id = $id;
       return $this;
   }

   public function setName($name)
   {
       $this->name = $name;
       return $this;
   }

   public function getName()
   {
       return $this->name;
   }

   public function setBirthday($birthday)
   {
       $this->birthday = $birthday;
       return $this;
   }

   public function getBirthday()
   {
       return $this->birthday;
   }

   public function setPhone($phone)
   {
       $this->phone = $phone;
       return $this;
   }

   public function getPhone()
   {
       return $this->phone;
   }

   public function getAppointments()
   {
         return $this->$appointments;
   }

}
