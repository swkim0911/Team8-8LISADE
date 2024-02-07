package com.lisade.togeduck.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Festival extends BaseEntity {

    private enum Category {
        SPORTS, MUSICAL, CONCERT, FAN_MEETING, ANIMATION, ETC
    }

    private enum Status {
        RECRUITMENT, TERMINATION
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "location", nullable = false, length = 200)
    private String location;

    @Column(name = "x_pos", nullable = false)
    private Double xPos;

    @Column(name = "y_pos", nullable = false)
    private Double yPos;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @OneToMany(mappedBy = "festival", cascade = CascadeType.ALL)
    private List<FestivalImage> festivalImages;

    @OneToMany(mappedBy = "festival")
    private List<Route> routes;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;
}
