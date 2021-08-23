package cn.video.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoVO {
    private String videoUrl;

    private String downLoadUrl;

    private boolean isDownload;
}
