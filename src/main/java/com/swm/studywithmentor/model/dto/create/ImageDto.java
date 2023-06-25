package com.swm.studywithmentor.model.dto.create;

import com.swm.studywithmentor.model.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto extends BaseDto {
    private String assetId;
    private String publicId;
    private String url;
}
