package com.lisade.togeduck.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "room_name", nullable = false)
    private String roomName;

    @Column(name = "number_of_members", nullable = false)
    private Integer numberOfMembers;

    @Column(name = "thumbnail_path", nullable = false)
    private String thumbnailPath;

    @OneToMany(mappedBy = "chatRoom")
    private List<UserChatRoom> userChatRooms;
}
