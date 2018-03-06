<?php
namespace AppBundle\Entity;

use AppBundle\Utilities\LogHelper;
use Doctrine\ORM\Mapping as ORM;

/**
 * Appointment
 *
 * @ORM\Table(name="appointment", indexes={@ORM\Index(name="patient_id", columns={"patient_id"})})
 * @ORM\Entity(repositoryClass="AppBundle\Repository\AppointmentRepository")
 */
class Appointment
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
     * @ORM\Column(name="description", type="string", length=1000, nullable=false)
     */
    private $description;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="time", type="datetime", nullable=false)
     */
    private $time;

    /**
     * @var int
     *
     * @ORM\Column(name="category", type="smallint", nullable=false)
     */
    private $category;

    /**
     * @var \Patient
     *
     * @ORM\ManyToOne(targetEntity="Patient", inversedBy="appointments")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="patient_id", referencedColumnName="id",  nullable=false)
     * })
     */
    private $patient;

    // Getters / Setters

    public function setId($id) 
    {
        $this->id =$id;
        return $this;
    }

    public function getId()
     {
         return $this->id;
     }

     public function setPatient($patient)
     {
       $this->patient = $patient;
       return $this;
     }

     public function getPatient()
     {
         return $this->patient;
     }

     public function setDescription($description)
     {
         $this->description = $description;
         return $this;
     }

     public function getDescription()
     {
         return $this->description;
     }

     public function setTime($time)
     {
         $this->time = $time;
         return $this;
     }

     public function getTime()
     {
         $this->time;
         return $this;
     }

     public function setCategory($category)
     {
         $this->category = $category;
         return $this;
     }

     public function getCategory()
     {
         return $this->category;
     }


}
