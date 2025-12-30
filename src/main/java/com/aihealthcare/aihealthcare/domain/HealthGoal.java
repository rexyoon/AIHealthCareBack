package com.aihealthcare.aihealthcare.domain;

import com.aihealthcare.aihealthcare.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "health_goals")
public class HealthGoal{
 @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "goal_id")
    private Long id;
 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "user_id")
 private User user;
 private Double targetWeight;
 private Double targetBloodSugar;
 private LocalDate startDate;
 private LocalDate endDate;
 @Builder
    public HealthGoal (User user, Double targetWeight,Double targetBloodSugar, LocalDate startDate, LocalDate endDate) {
     this.user = user;
     this.targetWeight = targetWeight;
     this.targetBloodSugar = targetBloodSugar;
     this.startDate = LocalDate.now();
     this.endDate = endDate;
 }
 public void updateGoal(Double targetWeight, Double targetBloodSugar, LocalDate startDate, LocalDate endDate) {
  this.targetWeight = targetWeight;
  this.targetBloodSugar = targetBloodSugar;
  this.startDate = endDate;

 }
}