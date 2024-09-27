package ro.ps.showmgmtbackend.dto;

import lombok.*;

import java.util.List;

/**
 * Represents a collection response DTO
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectionResponseDTO<T> {
    private List<T> items;
    private long total;
}
