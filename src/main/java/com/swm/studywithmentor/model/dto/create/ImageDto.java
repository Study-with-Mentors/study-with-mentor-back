package com.swm.studywithmentor.model.dto.create;

import com.swm.studywithmentor.model.dto.BaseDto;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto extends BaseDto {
    private String url;
}
