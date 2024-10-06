package com.backend.feedservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationsMarkRequest {
    private List<String> notificationIds;
}
