package models;

import java.util.Date;

public class Observation {
   private Long id;
   private Date date;
   private String observation;

   public Observation(Long id,Date date, String observation) {
      this.id=id;
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
