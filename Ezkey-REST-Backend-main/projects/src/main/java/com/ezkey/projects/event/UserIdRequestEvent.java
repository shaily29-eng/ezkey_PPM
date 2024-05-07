package com.ezkey.projects.event;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserIdRequestEvent implements Event {
    String userLoginId;
}
