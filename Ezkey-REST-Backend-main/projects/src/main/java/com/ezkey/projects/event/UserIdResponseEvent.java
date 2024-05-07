package com.ezkey.projects.event;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserIdResponseEvent implements Event{
    Integer userId;
    String userFullName;
}
