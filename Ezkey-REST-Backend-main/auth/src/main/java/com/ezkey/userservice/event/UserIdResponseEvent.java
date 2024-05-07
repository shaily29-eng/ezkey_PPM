package com.ezkey.userservice.event;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserIdResponseEvent implements Event {
    Integer userId;
    String userFullName;
}
