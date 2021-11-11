package models;

import java.util.Date;

public class Observation {
   private Long id;
   private String placa;
   private Date date;
   private String observation;

   public Observation(Long id, String placa, Date date, String observation) {
      this.id=id;
      this.placa=placa;
      this.date = date;
      this.observation = observation;
   }

   public Observation() {
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getPlaca() {
      return placa;
   }

   public void setPlaca(String placa) {
      this.placa = placa;
   }

   public Date getDate() {
      return date;
   }

   public void setDate(Date date) {
      this.date = date;
   }

   public String getObservation() {
      return observation;
   }

   public void setObservation(String observation) {
      this.observation = observation;
   }
}
