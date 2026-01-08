package com.aihealthcare.aihealthcare.domain;

import com.aihealthcare.aihealthcare.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_logs")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ChatLogEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "session_id")
    private String sessionId;

    // "USER", "ASSISTANT", "SYSTEM"
    private String role;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
