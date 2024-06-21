package com.example.demo.dto.bugreport;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 31.05.2024
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Data transfer object for bug report")
public class BugReportRequest {
    @NotEmpty(message = "Maybe you forget subject of report?")
    @NotNull(message = "You can't do report without subject!")
    @Schema(description = "Subject of bug report", example = "Wrong status code for request bla-bla-bla")
    String subject;

    @NotEmpty (message = "Maybe you forget link to report?")
    @NotNull (message = "You can't do report without a link to google doc")
    @Pattern(regexp = "https://docs\\.google\\.com/.*", message = "URL must start with 'https://docs.google.com/'")
    @Schema(description = "Link to bug report in Google Docs", example = "https://docs.google.com/spreadsheets/d/1oSkwblablablabla")
    String reportLink;
}
